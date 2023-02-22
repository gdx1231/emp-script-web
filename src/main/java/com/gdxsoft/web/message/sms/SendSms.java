package com.gdxsoft.web.message.sms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.script.display.HtmlCreator;
import com.gdxsoft.easyweb.utils.UJSon;
import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.easyweb.utils.msnet.MList;
import com.gdxsoft.message.sms.ISms;
import com.gdxsoft.message.sms.SmsTencentImpl;

public class SendSms {
	public static SmsTencentImpl instanceTencent() {
		SmsTencentImpl sms = new SmsTencentImpl();
		sms.setAccessKeyId(UPath.getInitPara("tencent_api_key"));
		sms.setAccessKeySecret(UPath.getInitPara("tencent_api_secret"));
		sms.setSmsSignName(UPath.getInitPara("tencent_sms_sign_name"));
		sms.setSmsAppId(UPath.getInitPara("tencent_sms_app_id"));

		return sms;
	}

	private static Logger LOGGER = LoggerFactory.getLogger(SendSms.class);

	public static JSONObject checkExistsContent(DataConnection cnn, String templateCode, String template,
			long beforeSeconds, int skipSMSJID) {
		String md5 = Utils.md5(templateCode + "GDX" + template);
		JSONObject rst = UJSon.rstTrue();
		rst.put("sms_check_md5", md5);

		RequestValue rv = cnn.getRequestValue();
		// 24小时前
		Date refDate = new Date(System.currentTimeMillis() - beforeSeconds * 1000); // 1天
		rv.addOrUpdateValue("ref_date", refDate, "date", 100);

		String sqlMd5 = "select SMS_JID from sms_job a where a.SMS_JTITLE=@sms_check_md5 and SMS_JCDATE>@ref_date";
		if(skipSMSJID>0) {
			sqlMd5+=" and sms_jid != "+ skipSMSJID;
		}
		DTTable tb = DTTable.getJdbcTable(sqlMd5, cnn);
		if (tb.getCount() == 0) {
			rst.put("repeat", false);

		} else {
			String smsJIds = tb.joinIds("SMS_JID", false);
			rst.put("repeat", true);
			rst.put("sms_jids", smsJIds);
		}
		cnn.close();
		return rst;
	}

	public static JSONObject checkExists(DataConnection cnn, String templateCode, String template, long beforeSeconds,
			Map<String, Integer> phones, int skipSMSJID) {
		JSONObject rst = checkExistsContent(cnn, templateCode, template, beforeSeconds, skipSMSJID);
		if (rst.optBoolean("repeat")) {
			return rst;
		}

		String smsJIds = rst.getString("sms_jids");

		StringBuilder sb = new StringBuilder();
		RequestValue rv = cnn.getRequestValue();
		phones.forEach((phone, v) -> {
			String name = "_phone_" + sb.length();
			if (sb.length() > 0) {
				sb.append(", @").append(name);
			} else {
				sb.append("@").append(name);
			}
			rv.addOrUpdateValue(name, phone);
		});

		String sql2 = "select SMS_JL_PHONE, SMS_JL_ID from sms_job_lst b where b.SMS_JID in (" + smsJIds
				+ ") and SMS_JL_PHONE in ( " + sb.toString() + ")";
		DTTable tb2 = DTTable.getJdbcTable(sql2, cnn);
		cnn.close();

		JSONArray arr = new JSONArray();
		rst.put("phones", arr);
		for (int i = 0; i < tb2.getCount(); i++) {
			String phone = tb2.getCell(i, 0).toString();
			int jlId = tb2.getCell(i, 1).toInt();
			if (phones.containsKey(phone)) {
				phones.put(phone, jlId);
				arr.put(phone);
				rst.put(phone, jlId);
			}

		}

		return rst;
	}

	/**
	 * 检查已发重复短信
	 * 
	 * @param cnn
	 * @param templateCode
	 * @param template
	 * @param beforeSeconds
	 * @param phone
	 * @return
	 */
	public static JSONObject checkExists(DataConnection cnn, String templateCode, String template, long beforeSeconds,
			String phone, int skipSMSJID) {
		Map<String, Integer> phones = new HashMap<>();
		phones.put(phone, 0);

		return checkExists(cnn, templateCode, template, beforeSeconds, phones, skipSMSJID);
	}

	/**
	 * 立即发送短信，利用当前的HtmlCreator
	 * 
	 * @param sms          短信接口
	 * @param hc           当前的HtmlCreator
	 * @param templateCode 短信模板编号
	 * @return
	 */
	public static String smsSend(ISms sms, HtmlCreator hc, String templateCode) {
		sms.setSmsTemplateCode(templateCode);

		SendSms ss = new SendSms();
		ss.setSms(sms);
		ss.init(hc);

		int smsJId = ss.sendNow();

		return smsJId + "";
	}

	/**
	 * 发送短信到队列，利用当前的HtmlCreator
	 * 
	 * @param sms          短信接口
	 * @param hc           当前的HtmlCreator
	 * @param templateCode 短信模板编号
	 * @return
	 */
	public static String smsQueue(ISms sms, HtmlCreator hc, String templateCode) {
		sms.setSmsTemplateCode(templateCode);

		SendSms ss = new SendSms();
		ss.setSms(sms);
		ss.init(hc);

		int smsJId = ss.saveToQueue();

		return smsJId + "";
	}

	public static final String PHONE = "PHONE";
	public static final String MSG_REF_TABLE = "MSG_REF_TABLE";
	public static final String MSG_REF_ID = "MSG_REF_ID";
	public static final String SMS_DATA = "SMS_DATA";

	private ISms sms;
	private RequestValue rv;
	private String lastError;
	private Integer messageId;
	private String messageMd5;
	private int smsJId;

	private JSONObject smsTemplateParameter;

	private Map<String, Integer> phones = new HashMap<>();

	public int sendNow() {
		List<String> sendPhones = new ArrayList<>();
		StringBuilder sbUnSendPhones = new StringBuilder();
		phones.forEach((k, v) -> {
			if (v == 0) {
				sendPhones.add(k);
			} else {
				if (sbUnSendPhones.length() > 0) {
					sbUnSendPhones.append(", ");
				}
				sbUnSendPhones.append(k);
			}
		});

		if (sendPhones.size() == 0) {
			rv.addOrUpdateValue("MESSAGE_STATUS", "YES");
			rv.addOrUpdateValue("MESSSAGE_LOG", "没有可用的电话，" + sbUnSendPhones + "重复发送");
			LOGGER.warn("没有可用的电话，{} 重复发送", sbUnSendPhones.toString());
		} else {
			try {
				JSONObject result = this.sms.sendSms(sendPhones, smsTemplateParameter, MSG_REF_ID);
				rv.addOrUpdateValue("MESSAGE_STATUS", "YES");
				rv.addOrUpdateValue("MESSSAGE_LOG", result.toString());
			} catch (Exception e) {
				LOGGER.error("{}", e.getLocalizedMessage());
				rv.addOrUpdateValue("MESSAGE_STATUS", "ERR");
				rv.addOrUpdateValue("MESSSAGE_LOG", e.getLocalizedMessage());
			}
		}
		return this.saveToQueue();
	}

	public int saveToQueue() {
		DataConnection cnn = new DataConnection(rv);
		String sqlhead = "insert into sms_job_lst(SMS_JID, ADM_ID, SUP_ID, SMS_JL_PHONE)"
				+ "VALUES(@SMS_JID, @G_ADM_ID, @G_SUP_ID, ";

		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO sms_job (");
		sb.append("  SMS_JTITLE, SMS_JCNT, SMS_JCDATE, SMS_JSTATUS \n");
		sb.append(", SMS_PHONES, ADM_ID, SUP_ID, SMS_REF_TABLE, SMS_REF_ID \n");
		sb.append(", SMS_PROVIDER, SMS_TEMPLATE_CODE, SMS_SIGN_NAME \n");
		sb.append(", SMS_TEMPLATE_JSON, SMS_OUT_ID, MQ_MSG_ID, MQ_MSG \n");
		sb.append(") values ( \n");
		sb.append("  @sms_check_md5, @SMS_JCNT, @sys_DATE, @MESSAGE_STATUS \n");
		sb.append(", @SMS_PHONES, @G_ADM_ID, @G_SUP_ID, @MSG_REF_TABLE.100, @MSG_REF_ID \n");
		sb.append(", @SMS_PROVIDER, @SMS_TEMPLATE_CODE, @SMS_SIGN_NAME \n");
		sb.append(", @SMS_TEMPLATE_JSON, @SMS_OUT_ID, @MQ_MSG_ID, @MESSSAGE_LOG \n");
		sb.append(")\n -- auto SMS_JID");
		String sql = sb.toString();

		StringBuilder sb1 = new StringBuilder();
		List<String> sqlBats = new ArrayList<String>();
		this.phones.forEach((phone, v) -> {
			if (sb1.length() > 0) {
				sb1.append(", ");
			}
			sb1.append(phone + ":" + v);

			String sql1 = sqlhead + cnn.sqlParameterStringExp(phone) + ")";
			sqlBats.add(sql1);

		});

		rv.addOrUpdateValue("SMS_TEMPLATE_CODE", sms.getSmsTemplateCode());
		rv.addOrUpdateValue("SMS_SIGN_NAME", sms.getSmsSignName());
		rv.addOrUpdateValue("SMS_PROVIDER", sms.getProvider());
		rv.addOrUpdateValue("SMS_PHONES", sb1.toString());
		rv.addOrUpdateValue("SMS_TEMPLATE_JSON", this.smsTemplateParameter.toString());

		int msgId = cnn.executeUpdateReturnAutoIncrement(sql);
		if (cnn.getErrorMsg() != null) {
			this.lastError = cnn.getErrorMsg();
			cnn.close();
			return -1;
		}

		rv.addOrUpdateValue("SMS_JID", msgId);
		for (int i = 0; i < sqlBats.size(); i++) {
			cnn.executeUpdateReturnAutoIncrement(sqlBats.get(i));
		}

		cnn.close();

		return msgId;
	}

	private void checkExists() {
		rv.addOrUpdateValue("MESSAGE_STATUS", "NO");
		DataConnection cnn = new DataConnection(rv);
		// 24小时前
		long before = 24 * 60 * 60;
		SendSms.checkExists(cnn, this.sms.getSmsTemplateCode(), this.smsTemplateParameter.toString(), before,
				this.phones, -1);
	}

	public void init(HtmlCreator hc) {
		this.rv = hc.getRequestValue().clone();
		MList tbList = hc.getHtmlClass().getItemValues().getDTTables();

		for (int i = 0; i < tbList.size(); i++) {
			DTTable tb = (DTTable) tbList.get(i);
			this.addUsers(tb);
			this.addSmsTemplateParameter(tb);
		}
		if (this.smsTemplateParameter == null) {
			this.smsTemplateParameter = new JSONObject();
		}
		if (rv.s(MSG_REF_TABLE) == null) {
			rv.addOrUpdateValue(MSG_REF_TABLE, hc.getSysParas().getItemName() + "," + hc.getSysParas().getXmlName());
		}
		this.checkExists();
	}

	private void addSmsTemplateParameter(DTTable tb) {
		if (tb.getCount() != 1) {
			return;
		}
		// 包含字段名称为 SMS_DATA的数据
		int fieldIndex = tb.getColumns().getNameIndex(SMS_DATA);
		if (fieldIndex == -1) {
			return;
		}
		this.smsTemplateParameter = tb.getRow(0).toJson();
		this.smsTemplateParameter.remove(tb.getColumns().getColumn(fieldIndex).getName());
	}

	private int addUsers(DTTable tb) {
		if (tb.getCount() == 0) {
			return 0;
		}
		int emailIndex = tb.getColumns().getNameIndex(PHONE);
		if (emailIndex == -1) {
			return 0;
		}
		int count = 0;
		for (int i = 0; i < tb.getCount(); i++) {
			String phone = tb.getCell(i, emailIndex).toString();
			if (phone == null || phone.trim().length() == 0) {
				continue;
			}
			this.phones.put(phone, 0);

			count++;
		}
		return count;
	}

	/**
	 * @return the rv
	 */
	public RequestValue getRv() {
		return rv;
	}

	/**
	 * @return the lastError
	 */
	public String getLastError() {
		return lastError;
	}

	/**
	 * @return the messageId
	 */
	public Integer getMessageId() {
		return messageId;
	}

	/**
	 * @return the messageMd5
	 */
	public String getMessageMd5() {
		return messageMd5;
	}

	/**
	 * @return the sms
	 */
	public ISms getSms() {
		return sms;
	}

	/**
	 * @param sms the sms to set
	 */
	public void setSms(ISms sms) {
		this.sms = sms;
	}

	/**
	 * @return the smsTemplateParameter
	 */
	public JSONObject getSmsTemplateParameter() {
		return smsTemplateParameter;
	}

	/**
	 * @param smsTemplateParameter the smsTemplateParameter to set
	 */
	public void setSmsTemplateParameter(JSONObject smsTemplateParameter) {
		this.smsTemplateParameter = smsTemplateParameter;
	}

	/**
	 * @return the smsJId
	 */
	public int getSmsJId() {
		return smsJId;
	}

	/**
	 * @return the phones
	 */
	public Map<String, Integer> getPhones() {
		return phones;
	}
}
