package com.gdxsoft.web.user;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.*;
import com.gdxsoft.message.sms.ISms;
import com.gdxsoft.message.sms.SmsAliImpl;

public class RegisterOrLogin {
	private String xmlname = "/customer/webuser/region.xml";
	private String itemname = "WEB_USER.Frame.Login"; // 用户登录

	private String accessKeyId; // 短信供应商提的 accessKeyId
	private String accessKeySecret; // 短信供应商提的 accessKeySecret

	private String smsSignName; // 短信签名
	private String smsTemplateCode; // 登录确认验证码

	public RegisterOrLogin() {

	}

	public RegisterOrLogin(String accessKeyId, String accessKeySecret, String smsSignName, String smsTemplateCode) {
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
		this.smsSignName = smsSignName;
		this.smsTemplateCode = smsTemplateCode;
	}

	public String doMethod(RequestValue rv, HttpServletResponse response) throws Exception {
		String method = rv.s("method");

		if ("smslogin_send".equals(method)) { // 短信登录 验证
			JSONObject s1 = this.methodSmsloginSend(rv, response);
			return s1.toString();
		} else if ("smsregister_send".equals(method)) { // 短信登录 验证
			JSONObject s1 = this.methodSmsRegisterSend(rv, response);
			return s1.toString();
		} else if ("register".equals(method)) { // 提交注册
			JSONObject s1 = this.methodRegister(rv, response);
			String js = "submitResult(" + s1 + ")";
			return js;
		} else if ("smslogin_valid".equals(method)) { // 短信登录 验证
			JSONObject rst = this.methodSmsLoginValid(rv, response);
			return rst.toString();
		} else if ("login".equals(method)) { // 短信登录 验证
			JSONObject rst = this.methodLogin(rv, response, "");
			if (rv.s("json_result") != null) {
				// app自动登录调用，返回json结果
				return rst.toString();
			} else {
				String js = "loginResult(" + rst + ")";
				return js;
			}

		} else if ("auto_login".equals(method)) { // 自动登录
			String result = this.methodAutoLogin(rv, response);
			return result.toString();
		} else {
			return UJSon.rstFalse("未知的method: " + method).toString();
		}
	}

	public SmsValid createSmsValid(RequestValue rv) {
		// 阿里云发短信
		ISms sms = new SmsAliImpl();

		sms.setAccessKeyId(accessKeyId);
		sms.setAccessKeySecret(accessKeySecret);

		sms.setSmsSignName(smsSignName);
		sms.setSmsTemplateCode(smsTemplateCode);

		SmsValid sv = new SmsValid(rv, sms);

		return sv;
	}

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

	public JSONObject methodSmsLoginValid(RequestValue rv, HttpServletResponse response) throws Exception {
		DoLogin login = this.createDoLogin(rv, response);

		// 短信登录 验证
		JSONObject rst = new JSONObject();
		String smsCode = rv.s("SMS_CODE");
		String fpUnid = rv.s("FP_UNID");

		rst = login.smsValidAndLogin(fpUnid, smsCode);

		if (!rst.optBoolean("RST")) {
			return rst;
		}

		// 验证成功 调用登录进行登录
		String paras = "FP_UNID=" + fpUnid + "&FP_VALIDCODE=" + smsCode
				+ "&APP=1&EWA_ACTION=OnPagePost&EWA_POST=1&EWA_VALIDCODE_CHECK=NOT_CHECK";
		rst = this.doLogin(rv, response, paras);

		System.out.println(rst);
		return rst;
	}

	/*
	 * 发送注册短信
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
	 */
	public JSONObject methodSmsloginSend(RequestValue rv, HttpServletResponse response) throws Exception {
		// 短信登录 验证
		String USR_MOBILE = rv.s("USR_MOBILE");
		DoLogin login = this.createDoLogin(rv, response);
		JSONObject rst = login.smsSendLoginCode(USR_MOBILE, false);
		if (rst == null) {
			return null;
		} else {
			return rst;
		}
	}

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

		// 在inc_sms.jsp定义
		SmsValid smsValid = this.createSmsValid(rv);
		login.setSmsValid(smsValid);

		return login;
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

	public String getSmsTemplateCode() {
		return smsTemplateCode;
	}

	public void setSmsTemplateCode(String smsTemplateCode) {
		this.smsTemplateCode = smsTemplateCode;
	}
}
