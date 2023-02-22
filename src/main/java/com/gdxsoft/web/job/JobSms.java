package com.gdxsoft.web.job;

import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.data.DTRow;
import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.message.sms.ISms;
import com.gdxsoft.message.sms.SmsMyMobKit;
import com.gdxsoft.web.message.sms.SendSms;

/**
 * 发送短信 表：sms_job
 * 
 * @author admin
 *
 */
public class JobSms extends JobBase {
	private static Logger log = LoggerFactory.getLogger(JobMailSenderBase.class);

	private ISms sms;
	private static String[] HZ = { "０", "１", "２", "３", "４", "５", "６", "７", "８", "９" };

	private HashMap<Integer, Boolean> maps = new HashMap<Integer, Boolean>();

	public JobSms(DataConnection cnn, String dbName) {
		super(cnn, dbName);
	}

	/**
	 * 检查发送的短信是否重复
	 * 
	 * @param phoneNo
	 * @param content
	 * @return
	 */
	private boolean checkRepeat(String phoneNo, String content, int currentJid) {
		String s1 = phoneNo + "---" + content;
		int hash = s1.hashCode(); // // 记录Hash值，避免重复发送
		if (maps.containsKey(hash)) {
			return true;
		}

		// 避免重复发送
		maps.put(hash, true);

		StringBuilder sb = new StringBuilder();
		sb.append("select top 1 sj.SMS_JID from sms_job  sj \n");
		sb.append("   inner join sms_job_lst sjl on sj.sms_jid=sjl.sms_jid ");
		sb.append("	where sj.sms_jstatus = 'SMS_JOB_SEND' \n");
		sb.append("		and sms_jl_phone='");
		sb.append(phoneNo.replace("'", ""));
		sb.append("' \n 	and SMS_JTITLE = '");
		sb.append(content.replace("'", ""));
		sb.append("' \n 	and   SMS_JCDATE > @dt_Before "); // 两小时以内已经发送的

		long twoHours = 2 * 60 * 60 * 1000;// 两小时
		Date dtBefore = new Date(System.currentTimeMillis() - twoHours);
		super._Conn.getRequestValue().addOrUpdateValue("dt_Before", dtBefore, "date", 100);

		String sql =  sb.toString() ;
		DTTable tb = DTTable.getJdbcTable(sql, super._Conn);
		if (tb.getCount() > 0) {
			// 两小时以内已经发送
			return true;
		} else {
			return false;
		}
	}

	public void sendSmsByJob2() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("select sj.*, sjl.SMS_JL_PHONE, sjl.SMS_JL_ID, sjl.SMS_JL_REFID, sjl.SMS_JL_REF_TABLE");
		sb.append(" from sms_job sj ");
		sb.append(" inner join  sms_job_lst sjl on sj.sms_jid=sjl.sms_jid");
		sb.append(" where sj.sms_jstatus = 'SMS_JOB_NEW'");
		sb.append("  and sms_jl_phone is not null");

		String sql = sb.toString() ;
		int pagesize = 100;
		int cur = 1;
		DTTable lst = DTTable.getJdbcTable(sql, "SMS_JL_ID", pagesize, cur, super._Conn);
		for (int i = 0; i < lst.getCount(); i++) {
			this.sendSms(lst.getRow(i));
		}
		this.log(" send sms " + lst.getCount());
	}

	/**
	 * 发送短信
	 * 
	 * @param r
	 * @throws Exception
	 */
	private void sendSms(DTRow r) throws Exception {
		String phoneNo = r.getCell("SMS_JL_PHONE").getString();
		String content = r.getCell("SMS_JCNT").getString();
		String json = r.getCell("SMS_TEMPLATE_JSON").toString();
		int jid = r.getCell("SMS_JID").toInt();
		String sql;
		if (phoneNo == null || phoneNo.trim().length() == 0) {
			sql = "update sms_job set sms_jstatus='SMS_JOB_NO_PHONE',sms_jmdate=@sys_date where sms_jid=" + jid;
			super._Conn.executeUpdate(sql);
			return;
		}
		phoneNo = replaceHzNum(phoneNo);

		String smsProvier = r.getCell("SMS_PROVIDER").toString();
		String smsSignName = r.getCell("SMS_SIGN_NAME").toString();
		int smsJlId = r.getCell("SMS_JL_ID").toInt();

		boolean isSended = true;
		if ("MYMOBKIT".equalsIgnoreCase(smsProvier)) {
			isSended = this.checkRepeat(phoneNo,  content , jid);
		} else {
			long before = 2 * 60 * 60;
			JSONObject rst = SendSms.checkExists(super._Conn, this.sms.getSmsTemplateCode(), json, before, phoneNo,
					jid);
			isSended = rst.optBoolean("repeat");
		}
		if (isSended) {
			// 重复发送
			log.info("REPEAT SMS:" + phoneNo + "," + content);
			sql = "update sms_job set sms_jstatus='SMS_JOB_REPEAT',sms_jmdate=@sys_date where sms_jid=" + jid;

			super._Conn.getRequestValue().resetDateTime();
			super._Conn.executeUpdate(sql);
			return;
		}
		try {
			String status = "SMS_JOB_SEND";
			String smsResult = "";

			// 通过Android手机的 MyMobKit App发送自定义短信
			if ("MYMOBKIT".equalsIgnoreCase(smsProvier)) {
				log.info("SEND-MYMOBKIT-sms_job:" + phoneNo + "," + content);
				JSONObject sendRst = SmsMyMobKit.sendSms(phoneNo, smsSignName, content, false);
				smsResult = sendRst.toString();
				if (!sendRst.optBoolean("isSuccessful")) {
					status = "SMS_JOB_FAIL";
					log.error("ERR, " + phoneNo + ":" + smsResult);
				}
			} else {
				log.info("SEND-sms_job:" + phoneNo + "," + json);
				JSONObject templateParameters = new JSONObject(content);
				JSONObject sendRst = this.sms.sendSms(phoneNo, templateParameters, "");
				smsResult = sendRst.toString();
				if (!sendRst.optBoolean("RST")) {
					status = "SMS_JOB_FAIL";
					log.error("ERR, " + phoneNo + ":" + smsResult);
				}
			}
			 
			sql = "update sms_job set sms_jstatus='" + status + "', sms_jmdate=@sys_date where sms_jid=" + jid;

			String sql1 = "update sms_job_lst set SMS_RESULT='" + smsResult.replace("'", "''")
					+ "' where SMS_JL_ID=" + smsJlId;

			super._Conn.getRequestValue().resetDateTime();
			super._Conn.executeUpdate(sql);
			super._Conn.executeUpdate(sql1);
		} catch (Exception err) {
			log.error(err.getMessage());
			throw err;
		} finally {
			super._Conn.close();
		}
	}

	public static String replaceHzNum(String no) {
		for (int i = 0; i < HZ.length; i++) {
			no = no.replace(HZ[i], i + "");
		}
		return no;
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
}
