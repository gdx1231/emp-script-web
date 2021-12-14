package com.gdxsoft.web.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.HtmlControl;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UAes;
import com.gdxsoft.easyweb.utils.UCookies;
import com.gdxsoft.easyweb.utils.UDes;
import com.gdxsoft.easyweb.utils.USnowflake;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.web.acl.Login;

public class DoLogin {
	private static Logger LOGGER = LoggerFactory.getLogger(DoLogin.class);
	private RequestValue rv_;
	private HttpServletResponse response_;

	private DTTable tbUser_;
	// 最后输出的cookies
	private Map<String, Cookie> lastCookies;

	// 同时登录到 其他目录下的cookie地址
	private List<String> cookiePaths;

	private SmsValid smsValid;

	private String loginXmlName;
	private String loginItemName;

	/**
	 * 执行登录
	 * 
	 * @param rv
	 * @param response
	 */
	public DoLogin(RequestValue rv, HttpServletResponse response) {
		this.rv_ = rv;
		this.response_ = response;

		cookiePaths = new ArrayList<>();
	}

	/**
	 * 执行自动登录
	 * 
	 * @return 是否已经登录
	 */
	public boolean doAutoLogin() {
		boolean isUserLogined = Login.isUserLogined(rv_);
		if (isUserLogined) {
			return true;
		}
		// 有自动登录的凭证
		String altName = "AUTO_LOGIN_TOKEN";
		String token = rv_.s(altName);
		// 判断是否有自动登录的 token
		if (StringUtils.isBlank(token)) {
			return false;
		}

		// 进行自动登录
		JSONObject autoLoginRst = this.autoLoginAndGetNewCode(token, 7);

		if (autoLoginRst.optBoolean("RST")) {
			String code = autoLoginRst.optString("CODE");
			// 替换登录凭证
			this.addAutoLoginToWebBrower(code);
			return true;
		} else {
			// 删除错误的凭证
			this.addAutoLoginToWebBrower(null);
			return false;
		}
	}

	/**
	 * 将自动登录凭证 写到cookie
	 * 
	 * @param autoLoginRst
	 * @return
	 */
	public void addAutoLoginToWebBrower(String code) {
		String altName = "AUTO_LOGIN_TOKEN";
		// 自动登录并继续创建登录凭证
		UCookies uc = new UCookies("/", 7 * 24 * 60 * 60);

		if (code == null) {
			uc.deleteCookie(altName, response_);
		} else {
			uc.addCookie(altName, code, response_);
		}
	}

	/**
	 * 自动登录 ，如果成功的话，返回下次自动登录的 CODE（ASE128加密）
	 * 
	 * @param aesCode 登录用的 CODE （ASE128加密）
	 * @param maxDays 有效期（天数）
	 * @return
	 */
	public JSONObject autoLoginAndGetNewCode(String aesCode, int maxDays) {
		JSONObject rst = new JSONObject();
		JSONObject auth = null;
		try {
			String decode = UAes.getInstance().decrypt(aesCode);
			auth = new JSONObject(decode);
			LOGGER.debug("AL: {}", auth);
		} catch (Exception e) {
			rst.put("RST", false);
			rst.put("ERR", "解码错误");
			LOGGER.error(e.getMessage());

			return rst;
		}

		ValidBase vb = new ValidBase(this.rv_);

		// U->FP_UNID , C->FP_VALIDCODE, T->CREATE_TIME
		String fpUnid = auth.optString("U");
		String fpValidCode = auth.optString("C");

		long createTime = auth.optLong("T");

		long maxMinutes = maxDays * 24 * 60; // 最长的分钟
		if ((System.currentTimeMillis() - createTime) > maxMinutes * 60 * 1000) {
			rst.put("RST", false);
			rst.put("ERR", "超过" + maxDays + "天未登录");

			// 删除 凭证
			this.removeLoginData(fpUnid);

			return rst;
		}

		DTTable tbValid = vb.getValidRecord(fpUnid, ValidBase.VALID_TYPE_WEB_USER_LOGIN);
		if (tbValid.getCount() == 0) {
			rst.put("RST", false);
			rst.put("ERR", "凭证不存在");
			return rst;
		}

		rst = this.loginByFpValidCode(fpUnid, fpValidCode, rv_.getContextPath());

		if (!rst.optBoolean("RST")) {
			// 删除 凭证
			vb.removeValidReocrd(fpUnid, ValidBase.VALID_TYPE_WEB_USER_LOGIN);
			return rst;
		}
		// 登录后的用户
		DTTable tb_user = this.tbUser_;
		try {
			int usrId = tb_user.getCell(0, "usr_id").toInt();

			// 下次登录用code
			String code = this.createAesLoginCode(usrId, maxDays);

			rst.put("CODE", code);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		// 删除 凭证
		this.removeLoginData(fpUnid);
		return rst;
	}

	/**
	 * 创建 AES加密的登录用 json (FP_UNID, FP_VALIDCODE, CREATE_TIME)
	 * 
	 * @param usrId   用户
	 * @param maxDays 最多天数
	 * @return
	 */
	public String createAesLoginCode(long usrId, int maxDays) {
		long maxMinutes = maxDays * 60 * 24;

		JSONObject data = this.createLoginData(usrId, maxMinutes);
		String code = this.createAesLoginCodeByData(data);

		return code;
	}

	/**
	 * 创建 AES加密的登录用 json (U->FP_UNID , C->FP_VALIDCODE, T->CREATE_TIME)
	 * 
	 * @param autoLogin 包含 U->FP_UNID , C->FP_VALIDCODE, T->CREATE_TIME 的登录数据
	 * @return
	 */
	public String createAesLoginCodeByData(JSONObject autoLogin) {
		String FP_VALIDCODE = autoLogin.optString("FP_VALIDCODE");
		String FP_UNID = autoLogin.optString("FP_UNID");

		JSONObject newAutoLogin = new JSONObject();
		newAutoLogin.put("C", FP_VALIDCODE);
		newAutoLogin.put("U", FP_UNID);
		newAutoLogin.put("FP_VALIDCODE", FP_VALIDCODE);
		newAutoLogin.put("FP_UNID", FP_UNID);
		newAutoLogin.put("T", System.currentTimeMillis());

		// System.out.println("CR: " + newAutoLogin);

		try {
			String code = UAes.getInstance().encrypt(newAutoLogin.toString());
			return code;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 创建登录数据 ( USR_ID, FP_VALIDCODE, FP_TYPE, FP_UNID, FP_IP, FP_AGENT, FP_LOG)
	 * 
	 * @param usrId      用户编号
	 * @param maxMinutes 有效期限（分钟）
	 * @return
	 */
	public JSONObject createLoginData(long usrId, long maxMinutes) {
		ValidBase vb = new ValidBase(this.rv_);
		String validCode = vb.randomAlphaCode(20);

		JSONObject rst = vb.createValidRecord(usrId, validCode, ValidBase.VALID_TYPE_WEB_USER_LOGIN, maxMinutes,
				"自动登录数据");
		rst.put("FP_VALIDCODE", validCode);
		return rst;
	}

	/**
	 * 删除登录数据(VALID_TYPE_WEB_USER_LOGIN)
	 * 
	 * @param fpUnid
	 */
	public void removeLoginData(String fpUnid) {
		ValidBase vb = new ValidBase(this.rv_);
		vb.removeValidReocrd(fpUnid, ValidBase.VALID_TYPE_WEB_USER_LOGIN);
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param mobilePhone 电话
	 * @param regUser     是否注册不存在的电话
	 * @return
	 */
	public JSONObject smsSendLoginCode(String mobilePhone, boolean regUser) {
		// 发送短信验证码
		JSONObject rst = new JSONObject();
		SmsValid sv = this.getSmsValid();

		// 检查电话号码合法性
		rst = sv.checkMobilePhone(mobilePhone);
		if (!rst.optBoolean("RST")) {
			return rst;
		}

		// 获取电话对应的用户
		DTTable tb = sv.getWebUserByPhone(mobilePhone);
		if (tb.getCount() == 0) {
			if (regUser) {
				this.createNewUser(mobilePhone, "", Utils.randomStr(30));
			}
		}
		rst = sv.validWebUserCreate(mobilePhone);

		return rst;
	}

	/**
	 * 创建新用户
	 * 
	 * @param mobilePhone 手机
	 * @param userName    用户名
	 * @param userPwd     密码
	 * @return
	 */
	public long createNewUser(String mobilePhone, String userName, String userPwd) {
		StringBuilder sb = new StringBuilder();
		long userId = USnowflake.nextId();
		rv_.addOrUpdateValue("tmp_userId", userId);
		rv_.addOrUpdateValue("tmp_mobilePhone", mobilePhone);
		rv_.addOrUpdateValue("tmp_username", userName);
		rv_.addOrUpdateValue("tmp_userpassword", userPwd);

		sb.append("insert into web_user (usr_id, usr_lid, usr_pwd, USR_MOBILE \n");
		sb.append("	, USR_CDATE, USR_MDATE, USR_UNID, usr_name) \n");
		sb.append("values(@tmp_userId, '', ewa_func.password_hash(@usr_pwd) , @tmp_mobilePhone \n");
		sb.append(", @sys_date, @sys_date, @sys_unid, @tmp_username)");

		// 此电话没有注册过，注册此电话的用户
		DataConnection.updateAndClose(sb.toString(), "", rv_);

		return userId;
	}

	/**
	 * 发送短信验证码
	 * 
	 * @return
	 */
	public JSONObject smsSendLoginCode(String mobilePhone) {
		// 发送短信验证码
		JSONObject rst = new JSONObject();
		SmsValid sv = this.getSmsValid();

		// 检查电话号码合法性
		rst = sv.checkMobilePhone(mobilePhone);
		if (!rst.optBoolean("RST")) {

			return rst;
		}

		/*
		 * // 获取电话对应的用户 DTTable tb = sv.getWebUserByPhone(mobilePhone); if (tb.getCount() == 0) { long userId =
		 * this.createNewUser(mobilePhone); rst.put("newUserId", userId); }
		 */
		rst = sv.validWebUserCreate(mobilePhone);

		return rst;
	}

	/**
	 * 验证短信并进行登录
	 * 
	 * @param fpUnid  web_user_fpwd的 FP_UNID
	 * @param smsCode web_user_fpwd的 FP_VALIDCODE
	 * @return
	 */
	public JSONObject smsValidAndLogin(String fpUnid, String smsCode) {
		// 短信登录 验证
		JSONObject rst = new JSONObject();

		SmsValid sv = this.getSmsValid();
		rst = sv.validWebUserCode(fpUnid, smsCode);

		if (!rst.optBoolean("RST")) {
			return rst;
		}

		// 当前的contentpath进行登录
		rst = this.loginByFpValidCode(fpUnid, smsCode, rv_.getContextPath());
		if (!rst.optBoolean("RST")) {
			return rst;
		}

		return rst;
	}

	/**
	 * 修改path后。输出 loginByFpValidCode 登录后的cookie
	 * 
	 * @param path cookie的 path
	 */
	public void addLoginCookes(String path) {
		// this.lastCookies 在 loginByFpValidCode执行后产生
		this.lastCookies.forEach((name, cookie) -> {
			cookie.setPath(path);
			if (cookie.getMaxAge() == 0) {
				cookie.setMaxAge(-1);
			}
			this.response_.addCookie(cookie);
		});
	}

	/**
	 * 通过默认的 DES 加密的信息进行登录
	 * 
	 * @param encode 标准的 DES 加密凭证
	 * @return 解码的信息 b2b_usr_id=1334&b2b_usr_unid=ff01...
	 */
	public String decodeLoginDesCode(String encode) {
		if (StringUtils.isBlank(encode)) {
			return null;
		}

		String paras = "";
		UDes des;
		try {
			des = new UDes();
			JSONObject json = new JSONObject(des.decrypt(encode));

			paras += "&B2B_USR_ID=" + json.optString("B2B_USR_ID");
			paras += "&B2B_USR_UNID=" + json.optString("B2B_USR_UNID");
		} catch (Exception e) {
			return null;
		}

		return paras;
	}

	/**
	 * 通过默认的AES加密的信息进行登录，例如：来自 back_admin/competition-get-navigate.jsp
	 * 
	 * @param encode 标准aes加密信息
	 * @return 解码的信息 b2b_usr_id=1334&b2b_usr_unid=ff01...
	 */
	public String decodeLoginAesCode(String encode) {
		// {"T":1573398648125,"B2B_USR_ID":3144}
		if (StringUtils.isBlank(encode)) {
			return null;
		}

		// 通过默认的AES加密的信息，来自 back_admin/competition-get-navigate.jsp
		try {
			JSONObject json = new JSONObject(UAes.getInstance().decrypt(encode));
			long time = json.optLong("T");
			if (json.has("TIME")) {
				time = json.optLong("TIME");
			}
			if (System.currentTimeMillis() - time > 1000 * 1000) { // 超过1000s
				return null;
			}

			StringBuilder stringBuilder = new StringBuilder();
			Iterator<String> keys = json.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				String val = json.optString(key);

				stringBuilder.append("&");
				stringBuilder.append(key);
				stringBuilder.append("=");
				stringBuilder.append(val);
			}
			return stringBuilder.toString();
		} catch (Exception err) {
			return null;
		}

	}

	/**
	 * 执行登录操作 /front-web/webuser/region.xml，WEB_USER.Frame.Login<br>
	 * 同时输出的目录，通过 cookiePaths增加
	 * 
	 * @param paras 提交执行的参数
	 * @return
	 */
	public JSONObject userLogin(String paras) {
		JSONObject rst = new JSONObject();
		HtmlControl ht = new HtmlControl();

		String xmlname = this.getLoginXmlName();
		String itemname = this.getLoginItemName();

		// WEB_USER_FPWD FP_TYPE='WEIXIN_LOGIN'
		ht.init(xmlname, itemname, paras, rv_.getRequest(), rv_.getSession(), this.response_);

		String s = ht.getHtml();
		rst.put("H", s);
		rst.put("PARAS", paras);

		UCookies uc = new UCookies();
		uc.setHttpOnly(false);
		uc.setPath("/");
		uc.setSecret(false);

		if (s.indexOf("goUrl") >= 0) {
			rst.put("RST", true);

			DTTable[] tbs = ht.getTables();
			for (int i = 0; i < tbs.length; i++) {
				DTTable tb = tbs[i];
				if (tb.getCount() > 0 && tb.getColumns().testName("usr_id")) {
					tbUser_ = tb;
					break;
				}
			}
			
			if (tbUser_ == null) {
				rst.put("RST", false);
				rst.put("ERR", "没有用户数据发现");
				return rst;
			}
			
			try {
				// 记录输出的cookie值
				this.lastCookies = ht.getHtmlCreator().getHtmlClass().getAction().getOutCookes();
				// 同时登录到 其他目录下的 cookie，例如/ex， /b2b等
				cookiePaths.forEach(path -> this.addLoginCookes(path));
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}

			// 在浏览器创建一个 登录成功的cookie, 20s存活时间，用于浏览器判断是否登录成功，刷新当前页面
			// 一般用于多个页面的情况，当其中一个页面登录了，其他页面判断是否登录并刷新
			uc.setMaxAgeSeconds(20);
			uc.addCookie("STUDENT_LOGINED_NOTIFY", "1", response_);
		} else {
			rst.put("RST", false);
			rst.put("ERR", s);
			uc.setMaxAgeSeconds(0);
			uc.addCookie("STUDENT_LOGINED_NOTIFY", null, response_);
		}

		return rst;
	}

	/**
	 * 根据 FP_UNID 和 FP_VALIDCODE进行登录，数据在 web_user_fpwd FP_TYPE='WEIXIN_LOGIN'
	 * 
	 * @param fpUnid     FP_UNID
	 * @param validCode  FP_VALIDCODE
	 * @param cookiePath 登录执行的 contextPath
	 * @return
	 */
	public JSONObject loginByFpValidCode(String fpUnid, String validCode, String cookiePath) {

		// 验证成功 调用登录进行登录
		StringBuilder sb = new StringBuilder();
		sb.append("FP_UNID=");
		sb.append(fpUnid);
		sb.append("&FP_VALIDCODE=");
		sb.append(validCode);
		sb.append("&APP=1&EWA_ACTION=OnPagePost&EWA_POST=1&EWA_VALIDCODE_CHECK=NOT_CHECK");

		if (cookiePath != null && cookiePath.trim().length() > 0) {
			sb.append("&EWA_COOKIE_DOMAIN=");
			sb.append(cookiePath);
		}

		String paras = sb.toString();

		JSONObject rst = this.userLogin(paras);

		return rst;
	}

	public boolean loginByPassportAndName(String passport, String name) {

		return false;
	}

	/**
	 * 获取登录后创建的用户数据
	 * 
	 * @return
	 */
	public DTTable getTbUser() {
		return tbUser_;
	}

	/**
	 * 最后输出的cookies
	 * 
	 * @return the lastCookies
	 */
	public Map<String, Cookie> getLastCookies() {
		return lastCookies;
	}

	/**
	 * 同时登录到 其他目录下的cookie地址
	 * 
	 * @return the cookiePaths
	 */
	public List<String> getCookiePaths() {
		return cookiePaths;
	}

	public SmsValid getSmsValid() {
		return smsValid;
	}

	/**
	 * 外部设置发短信的对象
	 * 
	 * @param smsValid
	 */
	public void setSmsValid(SmsValid smsValid) {
		this.smsValid = smsValid;
	}

	public String getLoginXmlName() {
		return loginXmlName;
	}

	public void setLoginXmlName(String loginXmlName) {
		this.loginXmlName = loginXmlName;
	}

	public String getLoginItemName() {
		return loginItemName;
	}

	public void setLoginItemName(String loginItemName) {
		this.loginItemName = loginItemName;
	}

}
