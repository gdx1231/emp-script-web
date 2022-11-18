package com.gdxsoft.web.user;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.gdxsoft.easyweb.conf.ConfSecurities;
import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.script.HtmlControl;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.*;
import com.gdxsoft.message.sms.ISms;
import com.gdxsoft.message.sms.SmsAliImpl;
import com.gdxsoft.web.acl.Login;
import com.gdxsoft.web.dao.WebUser;
import com.gdxsoft.web.dao.WebUserDao;

public class RegisterOrLogin {
	/**
	 * 登录
	 */
	public final static String login = "login";
	/**
	 * 登录或注册
	 */
	public final static String loginOrRegister = "loginOrRegister";
	/**
	 * 提交注册
	 */
	public final static String register = "register";
	/**
	 * 自动登录
	 */
	public final static String auto_login = "auto_login";

	/**
	 * 发送登录验证短信
	 */
	public final static String smslogin_send = "smslogin_send";
	/**
	 * 短信登录 验证
	 */
	public final static String smslogin_valid = "smslogin_valid";
	/**
	 * 发送注册短信
	 */
	public final static String smsregister_send = "smsregister_send";

	/**
	 * 用户登录
	 */
	public final static String user_logined = "user_logined";
	/**
	 * 管理员登录
	 */
	public final static String admin_logined = "admin_logined";

	/**
	 * 根据用户名密码登录
	 */
	public final static String loginByPwd = "loginByPwd";

	private String xmlname = "/customer/webuser/region.xml";
	private String itemname = "WEB_USER.Frame.Login"; // 用户登录

	private String accessKeyId; // 短信供应商提的 accessKeyId
	private String accessKeySecret; // 短信供应商提的 accessKeySecret

	private String smsSignName; // 短信签名
	private String smsValidTemplateCode; // 登录确认验证码

	private IUSymmetricEncyrpt security;

	private int supId;
	private boolean smsAutoRegister;

	private String smsPrivoder = "ALI";
	private boolean autoUserId; // 创建用户时候，USR_ID是否为自增编号，如果不是的话为雪花编号

	public RegisterOrLogin() {
		security = ConfSecurities.getInstance().getDefaultSecurity().createSymmetric();
	}

	/**
	 * 初始化类
	 * 
	 * @param smsPrivoder          短信提供商
	 * @param accessKeyId          短信提供商的accessKey
	 * @param accessKeySecret      短信提供商的 Secret
	 * @param smsSignName          短信签名
	 * @param smsValidTemplateCode 短信验证码的模板编号
	 */
	public RegisterOrLogin(String smsPrivoder, String accessKeyId, String accessKeySecret, String smsSignName,
			String smsValidTemplateCode) {
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
		this.smsSignName = smsSignName;
		this.smsValidTemplateCode = smsValidTemplateCode;
		this.smsPrivoder = smsPrivoder;
		security = ConfSecurities.getInstance().getDefaultSecurity().createSymmetric();
	}

	/**
	 * 根据用户名和密码获取用户
	 * 
	 * @param loginId
	 * @param password
	 * @return
	 */
	public WebUser getWebUserByLogin(String loginId, String password) {
		WebUserDao d = new WebUserDao();
		RequestValue rv = new RequestValue();
		rv.addOrUpdateValue("USR_LID", loginId);
		d.setRv(rv);

		List<String> fields = Arrays.asList("USR_ID,USR_PWD,USR_NAME,USR_LID,USR_MOBILE".split(","));
		List<WebUser> al = d.getRecords("(USR_MOBILE = @USR_LID OR USR_LID=@USR_LID) ORDER BY USR_ID", fields);

		for (int i = 0; i < al.size(); i++) {
			WebUser w = al.get(i);
			String hashedPwd = w.getUsrPwd(); // Arigon2
			// Object result2 = EwaFunctions.executeStaticFunction("PASSWORD_VERIFY",
			// password, hashedPwd);
			// if (result2 != null && result2.toString().equals("true")) {
			// return w;
			// }
			if (UArgon2.verifyPwd(password, hashedPwd)) {
				return w;
			}

		}

		return null;
	}

	/**
	 * 根据用户名密码登录 <br>
	 * itemName = WEB_USER.Frame.Login<br>
	 * 验证成功会在前端调用JS方法gohome
	 * 
	 * @param rv
	 * @param response
	 * @return
	 */
	public String loginByPwd(RequestValue rv, HttpServletResponse response) {
		HtmlControl ht1 = new HtmlControl();
		ht1.init(xmlname, itemname, "", rv, response);
		if ("ValidCode".equalsIgnoreCase(rv.s("EWA_AJAX"))) {
			return null;
		}
		String html = ht1.getHtml();
		if (rv.s("checkpassword") == null) {
			return html;
		}

		if (html.indexOf("ValidCodeError") > 0) {
			// 验证吗错误
			return UJSon.rstFalse(html).toString();
		}

		String submitPassword = rv.s("usr_pwd");
		String usrLid = rv.s("USR_LID"); // 登录名或电话
		WebUser user = this.getWebUserByLogin(usrLid, submitPassword);

		JSONObject result = new JSONObject();
		if (user == null) {
			UJSon.rstSetFalse(result, "错误的用户/密码");
			return result.toString();
		}

		this.responseWebUserLoginInfo(user, rv, response);

		UJSon.rstSetTrue(result, null);
		return result.toString();
	}

	public boolean isValidMethod(String method) {
		if (user_logined.equalsIgnoreCase(method)) {
			return true;
		} else if (admin_logined.equalsIgnoreCase(method)) {
			return true;
		} else if (smslogin_send.equalsIgnoreCase(method)) { // 短信登录 验证
			return true;
		} else if (smsregister_send.equalsIgnoreCase(method)) { // 短信登录 验证
			return true;
		} else if (register.equalsIgnoreCase(method)) { // 提交注册
			return true;
		} else if (smslogin_valid.equalsIgnoreCase(method)) { // 短信登录 验证
			return true;
		} else if (login.equalsIgnoreCase(method)) { // 短信登录 验证
			return true;
		} else if (auto_login.equalsIgnoreCase(method)) { // 自动登录
			return true;
		} else if (loginOrRegister.equalsIgnoreCase(method)) { // 自动登录
			return true;
		} else if (loginByPwd.equalsIgnoreCase(method)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * WEB_USER.F.RegisterOrLogin
	 * 
	 * @param rv
	 * @param response
	 * @return
	 */
	public HtmlControl loginOrRegister(RequestValue rv, HttpServletResponse response) {
		HtmlControl ht1 = new HtmlControl();
		ht1.init(xmlname, "WEB_USER.F.RegisterOrLogin", "", rv, response);
		return ht1;
	}

	/**
	 * 登录处理<br>
	 * user_logined<br>
	 * admin_logined<br>
	 * smslogin_send<br>
	 * smsregister_send<br>
	 * smslogin_valid<br>
	 * register<br>
	 * login<br>
	 * auto_login<br>
	 * 
	 * @param method   模式
	 * @param rv
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String doMethod(String method, RequestValue rv, HttpServletResponse response) throws Exception {
		if (loginOrRegister.equals(method)) {
			return loginOrRegister(rv, response).getHtml();
		} else if (user_logined.equalsIgnoreCase(method)) {
			JSONObject obj = UJSon.rstTrue();
			obj.put("user_logined", Login.isUserLogined(rv));
			return obj.toString();
		} else if (admin_logined.equalsIgnoreCase(method)) {
			JSONObject obj = UJSon.rstTrue();
			obj.put("admin_logined", Login.isUserLogined(rv));
			return obj.toString();
		} else if (smslogin_send.equalsIgnoreCase(method)) { // 短信登录 验证
			JSONObject s1 = this.methodSmsloginSend(rv, response);
			return s1.toString();
		} else if (smsregister_send.equalsIgnoreCase(method)) { // 短信登录 验证
			JSONObject s1 = this.methodSmsRegisterSend(rv, response);
			return s1.toString();
		} else if (register.equalsIgnoreCase(method)) { // 提交注册
			JSONObject s1 = this.methodRegister(rv, response);
			String js = "submitResult(" + s1 + ")";
			return js;
		} else if (smslogin_valid.equalsIgnoreCase(method)) { // 短信登录 验证
			JSONObject rst = this.methodSmsLoginValid(rv, response);
			return rst.toString();
		} else if (login.equalsIgnoreCase(method)) { // 短信登录 验证
			JSONObject rst = this.methodLogin(rv, response, "");
			if (rv.s("json_result") != null) {
				// app自动登录调用，返回json结果
				return rst.toString();
			} else {
				String js = "loginResult(" + rst + ")";
				return js;
			}
		} else if (auto_login.equalsIgnoreCase(method)) { // 自动登录
			String result = this.methodAutoLogin(rv, response);
			return result.toString();
		} else if (loginByPwd.equalsIgnoreCase(method)) {
			return this.loginByPwd(rv, response);
		} else {
			return UJSon.rstFalse("未知的method: " + method).toString();
		}
	}

	/**
	 * 登录处理<br>
	 * user_logined<br>
	 * admin_logined<br>
	 * smslogin_send<br>
	 * smsregister_send<br>
	 * smslogin_valid<br>
	 * register<br>
	 * login<br>
	 * auto_login<br>
	 * 
	 * @param rv
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String doMethod(RequestValue rv, HttpServletResponse response) throws Exception {
		String method = rv.s("method");
		return this.doMethod(method, rv, response);
	}

	public SmsValid createSmsValid(RequestValue rv) {
		ISms sms = null;
		if ("ALI".equalsIgnoreCase(this.smsPrivoder)) {
			// 阿里云发短信
			sms = new SmsAliImpl();
		} else {
			// 目前只实现了阿里短信，未来可以扩展
			sms = new SmsAliImpl();
		}
		sms.setAccessKeyId(accessKeyId);
		sms.setAccessKeySecret(accessKeySecret);

		sms.setSmsSignName(smsSignName);
		sms.setSmsTemplateCode(smsValidTemplateCode);

		SmsValid sv = new SmsValid(rv, sms);

		return sv;
	}

	/**
	 * 登录验证
	 * 
	 * @param rv
	 * @param response
	 * @param paras
	 * @return
	 */
	public JSONObject methodLogin(RequestValue rv, HttpServletResponse response, String paras) {
		JSONObject rst;
		if ("WEB_USR_LOGIN_PHONE".equals(rv.s("swithLoginType"))) { // 手机密码登录
			Object validCodeSystem = rv.getRequest().getSession() == null ? null
					: rv.getRequest().getSession().getAttribute("_EWA_VAILD_CODE_SESSION");
			String validCodeUser = rv.s("valid");
			if (validCodeSystem == null || validCodeUser == null
					|| !validCodeUser.equalsIgnoreCase(validCodeSystem.toString())) {
				rst = UJSon.rstFalse("验证码错误");
				rst.put("CODE", "501");
				return rst;
			}
		}
		DoLogin login = this.createDoLogin(rv, response);
		// 通过默认的AES加密的信息，来自 小程序的调用
		String encode = rv.s("encode");
		if (StringUtils.isNoneBlank(encode)) {
			// {"T":1573398648125,"B2B_USR_ID":3144}
			String decode = login.decodeLoginAesCode(encode);
			if (decode != null) {
				paras += decode;
			}
		}
		try {
			rst = this.doLogin(rv, response, "EWA_ACTION=OnPagePost&EWA_POST=1&EWA_VALIDCODE_CHECK=NOT_CHECK" + paras);
		} catch (Exception err) {
			rst = UJSon.rstFalse(err.getMessage());
			rst.put("CODE", "500");
		}
		return rst;
	}

	/**
	 * 短信登录 验证
	 * 
	 * @param rv
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public JSONObject methodSmsLoginValid(RequestValue rv, HttpServletResponse response) throws Exception {
		// 短信登录 验证
		JSONObject rst = new JSONObject();
		String smsCode = rv.s("SMS_CODE");
		String fpUnid = rv.s("FP_UNID");
		SmsValid sv = this.createSmsValid(rv);
		JSONObject obj = sv.validWebUserCode(fpUnid, smsCode);
		if (!obj.optBoolean("RST")) {
			return obj;
		}
		long usrId = obj.optLong("USR_ID");

		WebUser user = new WebUserDao().getRecord(usrId);
		if (user == null) {
			return UJSon.rstFalse("用户信息丢失：" + usrId);
		}

		this.responseWebUserLoginInfo(user, rv, response);
		UJSon.rstSetTrue(rst, smsCode);
		rst.put("user", user);
		return rst;
	}

	/**
	 * 记录用户登录信息到session和cookie
	 * 
	 * @param user
	 * @param rv
	 * @param response
	 */
	private void responseWebUserLoginInfo(WebUser user, RequestValue rv, HttpServletResponse response) {
		this.addSessionAndEncryptCookie("G_USR_ID", user.getUsrId(), rv, response);
		this.addSessionAndEncryptCookie("G_USR_NAME", user.getUsrName(), rv, response);
		if (supId > 0) {
			this.addSessionAndEncryptCookie("G_SUP_ID", this.supId, rv, response);
		}
	}

	/**
	 * 发送注册短信
	 * 
	 * @param rv
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public JSONObject methodSmsRegisterSend(RequestValue rv, HttpServletResponse response) throws Exception {
		// 短信登录 验证
		String mobilePhone = rv.s("USR_MOBILE");
		DoLogin login = this.createDoLogin(rv, response);
		SmsValid sv = login.getSmsValid();
		DTTable tb = sv.getWebUserByPhone(mobilePhone);
		if (tb.getCount() > 0) {
			return UJSon.rstFalse("此手机号已经注册: " + mobilePhone);
		}
		JSONObject rst = sv.smsValidCommon(-1, mobilePhone, this.smsSignName);

		if (rst == null) {
			return null;
		} else {
			return rst;
		}
	}

	/**
	 * 发送登录短信
	 * 
	 * @param rv
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public JSONObject methodSmsloginSend(RequestValue rv, HttpServletResponse response) throws Exception {
		// 短信登录 验证
		String USR_MOBILE = rv.s("USR_MOBILE");
		DoLogin login = this.createDoLogin(rv, response);
		JSONObject rst = login.smsSendLoginCode(USR_MOBILE, this.smsAutoRegister);
		if (rst == null) {
			return null;
		} else {
			return rst;
		}
	}

	/**
	 * 短信注册
	 * 
	 * @param rv
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public JSONObject methodRegister(RequestValue rv, HttpServletResponse response) throws Exception {
		// 短信登录 验证
		JSONObject rst = new JSONObject();
		String smsCode = rv.s("SMS_CODE");
		String fpUnid = rv.s("FP_UNID");
		DoLogin login = this.createDoLogin(rv, response);
		rst = login.getSmsValid().validWebUserCode(fpUnid, smsCode);

		if (!rst.optBoolean("RST")) {
			return rst;
		}
		String mobilePhone = rv.s("USR_MOBILE");
		String usr_pwd = rv.s("usr_pwd");
		String usr_name = rv.s("usr_name");
		long userId = login.createNewUser(mobilePhone, usr_name, usr_pwd);

		rst.put("USR_ID", userId + "");

		return rst;

	}

	public String methodAutoLogin(RequestValue rv, HttpServletResponse response) throws Exception {
		JSONObject rst = new JSONObject();

		String aesCode = rv.s("code");
		try {
			DoLogin login = new DoLogin(rv, response);
			rst = login.autoLoginAndGetNewCode(aesCode, 7);
		} catch (Exception err) {
			rst.put("RST", false);
			rst.put("ERR", err.getMessage());
		}

		// out.println(rst);
		return rst.toString();
	}

	public JSONObject doLogin(RequestValue rv, HttpServletResponse response, String paras) throws Exception {
		DoLogin login = this.createDoLogin(rv, response);

		// 默认会有 /ex
		// login.getCookiePaths().add("/gyap2020");

		JSONObject rst = login.userLogin(paras);

		if (!rst.optBoolean("RST")) { // 登录失败
			rst.put("ERR", "登录失败");
			return rst;
		}

		DTTable tb_user = login.getTbUser();
		long usrId = tb_user.getCell(0, "USR_ID").toLong();

		int maxDays = 7;
		boolean isAutoLogin = rv.s("AUTOLOGIN") != null;
		if (rv.s("freeLoginDays") != null) { // 免登录天数
			try {
				maxDays = rv.getInt("freeLoginDays");
			} catch (Exception err) {
				System.err.println("login-or-register.jsp freeLoginDays " + err.getMessage());
			}
			if (maxDays > 0 && maxDays < 100) {
				isAutoLogin = true;
			} else {
				maxDays = -1;
				isAutoLogin = false;
			}
		}

		String aesAutoLoginData = null;
		if (isAutoLogin) {
			// 创建自动登录的凭证
			aesAutoLoginData = login.createAesLoginCode(usrId, maxDays);
			// 写 cookie 自动登录凭证
			login.addAutoLoginToWebBrower(aesAutoLoginData);
			rst.put("CODE", aesAutoLoginData);
		}

		// pc端发起学生通过小程序登录的调用
		if (rv.s("SL_VCODE") != null && rv.s("SL_FPUNID") != null && rv.s("SL_VTYPE") != null) {
			String fpUnid = rv.s("SL_FPUNID");
			String validCode = rv.s("SL_VCODE");
			String validType = rv.s("SL_VTYPE");

			ValidBase vb = new ValidBase(rv);

			JSONObject validRst = vb.checkValidCode(fpUnid, validType, validCode, 10001);
			if (validRst.optBoolean("RST")) {
				// 因为小程序登录创建时，用户编号为-1，现在将用户编号设为 真是用户编号，以便pc端进行登录
				vb.changeValidUserId(fpUnid, usrId);
			}
		}

		String ref = rv.s("ref");
		// 跳转到ref地址
		if (StringUtils.isNotBlank(ref) && rv.s("EWA_AJAX") == null) {
			response.sendRedirect(ref);
			return null;
		}

		return rst;
	}

	public DoLogin createDoLogin(RequestValue rv, HttpServletResponse response) {
		DoLogin login = new DoLogin(rv, response);
		login.setLoginXmlName(xmlname);
		login.setLoginItemName(itemname);

		SmsValid smsValid = this.createSmsValid(rv);
		login.setSmsValid(smsValid);

		login.setAutoUserId(autoUserId);
		return login;
	}

	public void addSessionAndEncryptCookie(String cookieName, Object cookieValue, RequestValue rv,
			HttpServletResponse response) {
		rv.getSession().setAttribute(cookieName, cookieValue);
		this.getCookieHandle(rv).addCookie(cookieName, cookieValue.toString(), response);
	}

	private UCookies getCookieHandle(RequestValue rv) {
		UCookies us = new UCookies(security);
		us.setHttpOnly(true);
		us.setPath(rv.getContextPath());
		us.setSecret(true);

		return us;
	}

	public String getXmlName() {
		return xmlname;
	}

	public void setXmlName(String xmlname) {
		this.xmlname = xmlname;
	}

	public String getItemName() {
		return itemname;
	}

	public void setItemName(String itemname) {
		this.itemname = itemname;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getSmsSignName() {
		return smsSignName;
	}

	public void setSmsSignName(String smsSignName) {
		this.smsSignName = smsSignName;
	}

	/**
	 * 短信验证的模板编号
	 * 
	 * @return
	 */
	public String getSmsValidTemplateCode() {
		return smsValidTemplateCode;
	}

	/**
	 * 短信验证的模板编号
	 * 
	 * @param smsValidTemplateCode
	 */
	public void setSmsValidTemplateCode(String smsValidTemplateCode) {
		this.smsValidTemplateCode = smsValidTemplateCode;
	}

	/**
	 * @return the security
	 */
	public IUSymmetricEncyrpt getSecurity() {
		return security;
	}

	/**
	 * @param security the security to set
	 */
	public void setSecurity(IUSymmetricEncyrpt security) {
		this.security = security;
	}

	public int getSupId() {
		return supId;
	}

	public void setSupId(int supId) {
		this.supId = supId;
	}

	/**
	 * 用户不存在时，自动注册
	 * 
	 * @return the smsAutoRegister
	 */
	public boolean isSmsAutoRegister() {
		return smsAutoRegister;
	}

	/**
	 * 用户不存在时，自动注册
	 * 
	 * @param smsAutoRegister the smsAutoRegister to set
	 */
	public void setSmsAutoRegister(boolean smsAutoRegister) {
		this.smsAutoRegister = smsAutoRegister;
	}

	/**
	 * @return the smsPrivoder
	 */
	public String getSmsPrivoder() {
		return smsPrivoder;
	}

	/**
	 * @param smsPrivoder the smsPrivoder to set
	 */
	public void setSmsPrivoder(String smsPrivoder) {
		this.smsPrivoder = smsPrivoder;
	}

	/**
	 * 创建用户时候，USR_ID是否为自增编号，如果不是的话为雪花编号
	 * 
	 * @return the autoUserId
	 */
	public boolean isAutoUserId() {
		return autoUserId;
	}

	/**
	 * 创建用户时候，USR_ID是否为自增编号，如果不是的话为雪花编号
	 * 
	 * @param autoUserId the autoUserId to set
	 */
	public void setAutoUserId(boolean autoUserId) {
		this.autoUserId = autoUserId;
	}
}
