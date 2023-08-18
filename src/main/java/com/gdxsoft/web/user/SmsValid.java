package com.gdxsoft.web.user;

import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UJSon;
import com.gdxsoft.message.sms.ISms;

public class SmsValid extends ValidBase {
	private static Logger LOGGER = LoggerFactory.getLogger(SmsValid.class);
	private static final String[] cnNums = "０,１,２,３,４,５,６,７,８,９".split(",");

	private ISms sms;

	public SmsValid(RequestValue rv, ISms sms) {
		super(rv);
		this.sms = sms;
	}

	/**
	 * 验证 WEB_USER 用户
	 * 
	 * @param fpUnid
	 * @param validCode
	 * @return
	 */
	public JSONObject validWebUserCode(String fpUnid, String validCode) {
		JSONObject rst = super.checkValidCode(fpUnid, VALID_TYPE_USER_LOGIN, validCode, 5);
		if (rst.optBoolean("RST")) {
			super.changeValidRecordType(fpUnid, VALID_TYPE_USER_LOGIN, VALID_TYPE_WEB_USER_LOGIN);
		}
		return rst;
	}

	/**
	 * 验证 ADM 用户
	 * 
	 * @param fpUnid
	 * @param validCode
	 * @return
	 */
	public JSONObject validAdmUserCode(String fpUnid, String validCode) {
		JSONObject rst = super.checkValidCode(fpUnid, VALID_TYPE_USER_LOGIN, validCode, 5);
		if (rst.optBoolean("RST")) {
			super.changeValidRecordType(fpUnid, VALID_TYPE_USER_LOGIN, ValidBase.VALID_TYPE_ADM_LOGIN);
		}
		return rst;
	}

	public JSONObject validWebUserCreate(String mobilePhone) {
		return validWebUserCreate(mobilePhone, false);
	}

	/**
	 * 创建用户验证信息
	 * 
	 * @param mobilePhone
	 * @return
	 */
	public JSONObject validWebUserCreate(String mobilePhone, boolean enableGrpCostumer) {
		JSONObject rst = this.checkMobilePhone(mobilePhone);
		if (!rst.optBoolean("RST")) {
			return rst;
		}

		DTTable tb = this.getWebUserByPhone(mobilePhone);
		if (tb.getCount() == 0) {
			if (enableGrpCostumer) {
				tb = this.getGrpCostumerByPhone(mobilePhone);
			}
			if (tb.getCount() == 0) {
				rst.put("RST", false);
				rst.put("ERR", "您的手机号没有注册");
				rst.put("CODE", "404");
				return rst;
			}
		}
		if (tb.getCount() > 1) {
			rst.put("RST", false);
			rst.put("ERR", "此手机号重复，不能用于登录，请与客服联系");
			rst.put("CODE", "400");
			return rst;
		}

		long usr_id = -1;
		try {
			usr_id = tb.getCell(0, "USR_ID").toLong();
		} catch (Exception e1) {
			rst.put("RST", false);
			rst.put("ERR", e1.getMessage());
			rst.put("CODE", "500");
			return rst;
		}

		JSONObject templateParam = new JSONObject();
		// 创建6位随机数字
		String validCode = super.randomNumberCode(6);
		templateParam.put("code", validCode);

		rst = this.smsValid(usr_id, mobilePhone, templateParam, validCode);

		return rst;
	}

	/**
	 * 短信通用身份验证 （身份验证验证码, 验证码${code}，您正在进行身份验证，打死不要告诉别人哦！）
	 * 
	 * @param usrId       用户
	 * @param mobilePhone 电话
	 * @param smsSignName 签名
	 * @return
	 */
	public JSONObject smsValidCommon(long usrId, String mobilePhone, String smsSignName) {
		JSONObject templateParam = new JSONObject();
		// 创建6位随机数字
		String validCode = super.randomNumberCode(6);
		templateParam.put("code", validCode);

		JSONObject rst = this.smsValid(usrId, mobilePhone, templateParam, validCode);
		return rst;
	}

	/**
	 * 通用短信验证码
	 * 
	 * @param usrId             用户
	 * @param mobilePhone       电话
	 * @param smsSignName       签名
	 * @param smsTemplateCode   短信模板编号
	 * @param smsTemplateParams 短信参数
	 * @param validCode         验证码
	 * @return
	 */
	public JSONObject smsValid(long usrId, String mobilePhone, JSONObject smsTemplateParams, String validCode) {

		JSONObject rst = this.checkMobilePhone(mobilePhone);
		if (!rst.optBoolean("RST")) {
			return rst;
		}
		// 检查频次
		rst = this.checkSmsFrequency(mobilePhone, this.sms.getSmsTemplateCode());
		if (!rst.optBoolean("RST")) {
			return rst;
		}

		rst.put("USR_ID", usrId);
		rst.put("PHONE", mobilePhone);
		rst.put("SIGN_NAME", this.sms.getSmsSignName());
		rst.put("TEMPLATE_CODE", this.sms.getSmsTemplateCode());

		JSONObject recordRst = super.createValidRecord(usrId, validCode, VALID_TYPE_USER_LOGIN, 15, null);
		if (!recordRst.optBoolean("RST")) {
			return recordRst;
		}

		// WEB_USR_FPWD 的 FP_UNID
		String fpUnid = recordRst.optString("FP_UNID");
		rst.put("FP_UNID", fpUnid);
		rst.put("RECORD_RST", recordRst);
		JSONObject sendRst;
		if (this.rv_ == null || this.rv_.s("not_send_sms") == null) {
			try {
				// 正确的
				// {"SEND_MESSAGE":"OK","SIGN_NAME":"计划","TEMPLATE_PARAM":{"code":"9112111"},
				// "SEND_COCDE":"OK","SEND_REQUEST_ID":"920B7B3A-ABCE-4AEB-88DB-A0EA1C06CDD6",
				// "OUT_ID":"","BIZ_ID":"603008608467712573^0","PHONE_NUMBER":"13910409333",
				// "TEMPLATE_CODE":"SMS_95610127"}

				// 错误的
				// {"SEND_MESSAGE":"+8613910409333 invalid mobile number",
				// "SIGN_NAME":"计划",
				// "TEMPLATE_PARAM":{"code":"9112111"},
				// "SEND_COCDE":"isv.MOBILE_NUMBER_ILLEGAL",
				// "SEND_REQUEST_ID":"AF938897-C9D8-4CAF-947F-63E2B89E3951",
				// "OUT_ID":"","PHONE_NUMBER":"+8613910409333","TEMPLATE_CODE":"SMS_95610127"}
				sendRst = this.sms.sendSms(mobilePhone, smsTemplateParams, "");
				// 记录到日志中，用于检查频次
				RequestValue rv0 = new RequestValue();
				rv0.addOrUpdateValue("mobilePhone", mobilePhone);
				rv0.addOrUpdateValue("TemplateCode", this.sms.getSmsTemplateCode());
				rv0.addOrUpdateValue("TemplateParams", smsTemplateParams.toString());
				rv0.addOrUpdateValue("SMS_PROVIDER",  sms.getProvider());
				String sqlFrequency = "insert into SMS_JOB(SMS_PROVIDER, SMS_JSTATUS, SMS_JCDATE,SMS_REF_TABLE,SMS_REF_ID,SMS_TEMPLATE_CODE, SMS_PHONES)"
						+ " values(@SMS_PROVIDER, 'SMS_JOB_SEND', @sys_date, 'SMS_VALID', @mobilePhone, @TemplateCode, @TemplateParams)";
				DataConnection.updateAndClose(sqlFrequency, "", rv0);
			} catch (Exception e) {
				rst.put("RST", false);
				rst.put("ERR", "短信接口系统错误");
				rst.put("ERR1", e.getMessage());
				
				LOGGER.error(rst.toString());
				return rst;
			}
		} else {
			// 用于测试，不发送短信
			sendRst = new JSONObject();
			sendRst.put("SEND_MESSAGE", "OK");
			sendRst.put("not_send_sms", "1");
			sendRst.put("smsTemplateParams", smsTemplateParams);
		}

		String SEND_MESSAGE = sendRst.optString("SEND_MESSAGE");
		rst.put("SEND_RESULT", sendRst);

		if ("OK".equalsIgnoreCase(SEND_MESSAGE)) {
			rst.put("RST", true);
		} else {
			rst.put("RST", false);
			rst.put("ERR", SEND_MESSAGE);
			// 删除已经创建的 WEB_USR_FPWD 记录
			super.removeValidReocrd(fpUnid, VALID_TYPE_USER_LOGIN);
		}
		return rst;
	}

	/**
	 * 检查短信频次
	 * 
	 * @param mobilePhone
	 * @param smsTemplateCode
	 * @return
	 */
	public JSONObject checkSmsFrequency(String mobilePhone, String smsTemplateCode) {
		if (smsTemplateCode == null) {
			smsTemplateCode = "null";
		}
		String w1 = " SMS_REF_TABLE='SMS_VALID' and SMS_REF_ID='" + mobilePhone.replace("'", "")
				+ "' and SMS_TEMPLATE_CODE='" + smsTemplateCode.replace("'", "") + "'";
		// 一个小时内
		// String sqlcheck = "select max(SMS_JCDATE) LAST, count(*) as NUM from SMS_JOB
		// where SMS_JCDATE > dateadd(hh,-1,getdate()) and "
		// + w1;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, -1);
		Date check_date_onehour = cal.getTime();

		super.rv_.addOrUpdateValue("check_date_onehour", check_date_onehour, "Date", 100);
		String sqlcheck = "select max(SMS_JCDATE) LAST, count(*) as NUM from SMS_JOB where SMS_JCDATE > @check_date_onehour and "
				+ w1;
		DTTable tbLast = DTTable.getJdbcTable(sqlcheck, super.rv_);
		if (tbLast.getCount() == 0 || tbLast.getCell(0, 0).isNull()) {
			return UJSon.rstTrue(null);
		}
		if (System.currentTimeMillis() - tbLast.getCell(0, 0).toTime() < 120 * 1000) {
			// 两次间隔<120s
			return UJSon.rstFalse("时间间隔太短");
		}

		if (!tbLast.getCell(0, 1).isNull() && tbLast.getCell(0, 1).toInt() > 5) {
			// 一小时不超过5次
			return UJSon.rstFalse("操作太频繁(小时) > 5");
		}

		// 一天
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.HOUR, -24);
		Date check_date_day = cal1.getTime();
		super.rv_.addOrUpdateValue("check_date_day", check_date_day, "Date", 100);
		String sqlcheck1 = "select   count(*) as NUM from SMS_JOB where SMS_JCDATE >  @check_date_day and " + w1;
		DTTable tbLast1 = DTTable.getJdbcTable(sqlcheck1, super.rv_);
		if (!tbLast1.getCell(0, 0).isNull() && tbLast1.getCell(0, 0).toInt() > 10) {
			// 一天内不超过10次
			return UJSon.rstFalse("操作太频繁(天) > 10");
		}

		return UJSon.rstTrue(null);
	}

	public DTTable getWebUserByPhone(String mobilePhone) {
		StringBuilder sb = new StringBuilder();
		sb.append("select USR_ID, USR_NAME,USR_TELE, USR_MOBILE from web_user where usr_tele='");
		sb.append(mobilePhone.replace("'", ""));
		sb.append("' or usr_mobile='");
		sb.append(mobilePhone.replace("'", ""));
		sb.append("' order by usr_id ");
		String sql = sb.toString();
		DTTable tb = DTTable.getJdbcTable(sql);

		return tb;
	}

	public DTTable getGrpCostumerByPhone(String mobilePhone) {
		String sql = "select b.USR_ID,a.COS_PHONE,a.COS_NAME,a.GRP_COS_ID from grp_costumer a\n"
				+ "inner join web_user b on a.cos_id=b.usr_id\n" + "where a.COS_STATUS = 'COS_NEW' and a.COS_PHONE='"
				+ (mobilePhone.replace("'", "")) + "'\n" + "order by a.grp_cos_id desc\n";
		DTTable dt = DTTable.getJdbcTable(sql, "GRP_COS_ID", 1, 1, "");
		return dt;
	}

	/**
	 * 根据 电话号码 获取管理员的信息
	 * 
	 * @param mobilePhone 手机号码
	 * @return
	 */
	public DTTable getAdmUserByPhone(String mobilePhone) {
		return this.getAdmUserByPhone(mobilePhone, -1);
	}

	/**
	 * 根据 电话号码 获取管理员的信息
	 * 
	 * @param mobilePhone 手机号码
	 * @param supId       代理编号
	 * @return
	 */
	public DTTable getAdmUserByPhone(String mobilePhone, int supId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select ADM_ID, ADM_NAME, ADM_TELE, ADM_MOBILE, SUP_ID from adm_user where (ADM_TELE='");
		sb.append(mobilePhone.replace("'", ""));
		sb.append("' or ADM_MOBILE='");
		sb.append(mobilePhone.replace("'", ""));
		sb.append("' or ADM_TELE='+86 ");
		sb.append(mobilePhone.replace("'", ""));
		sb.append("' or ADM_MOBILE='+86 ");
		sb.append(mobilePhone.replace("'", ""));
		sb.append("')");
		if (supId >= 0) {
			sb.append(" and sup_id=" + supId);
		}
		sb.append(" order by adm_id ");
		String sql = sb.toString();
		DTTable tb = DTTable.getJdbcTable(sql);

		return tb;
	}

	/**
	 * 检查电话号码合法性
	 * 
	 * @param mobilePhone
	 * @return
	 */
	public JSONObject checkMobilePhone(String mobilePhone) {
		JSONObject rst = new JSONObject();
		String USR_MOBILE = mobilePhone;
		rst.put("PHONE", USR_MOBILE);
		if (USR_MOBILE == null || USR_MOBILE.trim().length() == 0) {
			rst.put("RST", false);
			rst.put("ERR", "请提供手机号");
			return rst;
		}
		USR_MOBILE = USR_MOBILE.trim().replace(" ", "").replace("-", "");
		if (USR_MOBILE.startsWith("+")) {
			rst.put("RST", false);
			rst.put("ERR", "请不要输入国家号码（+）");
			return rst;
		}

		// 替换中文数字

		for (int i = 0; i < cnNums.length; i++) {
			USR_MOBILE = USR_MOBILE.replace(cnNums[i], i + "");
		}

		if (USR_MOBILE.length() != 11) {
			rst.put("RST", false);
			rst.put("ERR", "请提供11位中国手机号码");
			return rst;
		}

		for (int i = 0; i < mobilePhone.length(); i++) {
			char c = mobilePhone.charAt(i);

			if (c < '0' || c > '9') {
				rst.put("RST", false);
				rst.put("ERR", "号码中包含非数字");
				return rst;
			}
		}
		rst.put("RST", true);
		return rst;
	}

	public ISms getSms() {
		return sms;
	}

	public void setSms(ISms sms) {
		this.sms = sms;
	}
}
