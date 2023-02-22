package com.gdxsoft.web.job;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gdxsoft.easyweb.data.DTRow;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.utils.msnet.MListStr;
import com.gdxsoft.easyweb.utils.msnet.MStr;

/**
 * 检查邮件及提醒<br>
 * OA_REQ_MAIL<br>
 * SYS_ALERT<br>
 * sys_message_info<br>
 * 
 * @author admin
 *
 */
public class JobMailSenderBase extends JobBase {
	private static Logger LOGGER = LoggerFactory.getLogger(JobMailSenderBase.class);

	MStr msgSendMails;

	public JobMailSenderBase(DataConnection cnn, String dbName) {
		super(cnn, dbName);
	}

	public String getMsgSendMails() {
		if (msgSendMails == null) {
			return null;
		}
		return msgSendMails.toString();
	}

	/**
	 * 发送任务监控邮件
	 * 
	 * @param r
	 * @param lst
	 * @throws Exception
	 */
	void sendJobMail(DTRow r, MListStr lst) throws Exception {
		int reqId = Integer.parseInt(r.getCell("req_id").toString());
		lst.add(reqId + "");
		String pid = r.getCell("req_pid").toString();
		String type = r.getCell("REQ_MAIL_TYPE").toString();
		String reqStatus = r.getCell("REQ_STATUS").toString();
		if (reqStatus != null && reqStatus.equals("OA_REQ_DEL")) {
			return; // 过滤已经取消的任务
		}
		OaReqMails jm = new OaReqMails(super._Conn, super._dbName, reqId);
		jm.setRowSup(super._RowSup);
		if (type == null) {
			return;
		}
		if (pid == null) {
			jm = new OaReqMails(super._Conn, super._dbName, reqId);
			jm.setRowSup(super._RowSup);

			if (type.equals("REQ_MAIL_START")) {
				jm.sendStart();
			} else if (type.equals("REQ_MAIL_COMPLETE")) {
				jm.sendComplete();
			} else if (type.equals("REQ_MAIL_DELAY")) {
				jm.sendDelay();
			}
		} else {
			jm = new OaReqMails(super._Conn, super._dbName, Integer.parseInt(pid));
			jm.setRowSup(super._RowSup);

			jm.sendProcess(reqId);
		}

	}

	 
	 
	/**
	 * 发送日程
	 * 
	 * @param row
	 * @return
	 * @throws Exception
	 */
	boolean sendMailCalendar(DTRow row) throws Exception {
		String from = String.valueOf(row.getCell("FROM_EMAIL"));
		String tos = String.valueOf(row.getCell("TARGET_EMAIL"));
		tos = MsgUtils.replaceMailAddrs(tos);

		Date calSDate = (Date) row.getCell("CAL_SDATE").getValue();
		Date calEDate = (Date) row.getCell("CAL_EDATE").getValue();

		if (calSDate == null) {
			return false;
		}
		if (calEDate == null) {
			return false;
		}

		String subject = String.valueOf(row.getCell("MESSAGE_TITLE"));
		String content = String.valueOf(row.getCell("MESSAGE_CONTENT"));

		boolean rst = Outlook.sendCal(subject, content, "", calSDate, calEDate, from, tos);
		return rst;
	}

	/**
	 * 发送邮件
	 * 
	 * @param row sys_message_info的数据行
	 * @return
	 * @throws Exception
	 */
	public boolean sendMail(DTRow row, String atts) throws Exception {
		String from = String.valueOf(row.getCell("FROM_EMAIL"));
		String fromName = String.valueOf(row.getCell("FROM_NAME"));
		String to = String.valueOf(row.getCell("TARGET_EMAIL"));
		String toName = String.valueOf(row.getCell("TARGET_NAME"));
		String subject = String.valueOf(row.getCell("MESSAGE_TITLE"));
		String content = String.valueOf(row.getCell("MESSAGE_CONTENT"));
		String messageId = String.valueOf(row.getCell("MESSAGE_ID"));

		String supTag = "";
		try {
			supTag = super.getRowSup().getCell("SUP_TAG").toString() == null ? ""
					: super.getRowSup().getCell("SUP_TAG").toString();
		} catch (Exception err) {
			supTag = "";
			LOGGER.info("老结构，没有 SUP_TAG");
		}
		// 跟踪需要，单一发送独立邮件到独立的邮箱，单是显示收件人为多个
		String singleToName = row.getCell("SINGLE_TO_NAME").toString();
		String singleToEmail = row.getCell("SINGLE_TO_EMAIL").toString();

		// 发件人和回复的人不是同一个人，用于高信任域代替实际人发邮件，例如用lei.guo@oneworld.cc代替2877274@qq.com
		String REPLAY_TO_NAME = row.getCell("REPLAY_TO_NAME").toString();
		String REPLAY_TO_EMAIL = row.getCell("REPLAY_TO_EMAIL").toString();

		messageId = supTag + "." + messageId;

		if (fromName == null) {
			fromName = from + "";
		}
		if (toName == null) {
			toName = to;
		}

		String toaddString = MsgUtils.replaceMailAddrs(to);

		if (fromName == null || fromName.equals("")) {
			fromName = from;
		}
		if (toName == null) {
			toName = toaddString;
		}
		msg("ID:" + row.getCell("MESSAGE_ID").toString() + " 发送到:" + to + "\n");

		JSONObject smtpCfgJson = null;
		// 检查是否有指定的SMTP配置
		if (row.getTable().getColumns().testName("SMTP_CFG")) {
			String smtpCfg = row.getCell("SMTP_CFG").toString();
			if (smtpCfg != null && smtpCfg.trim().length() > 0) {
				try {
					smtpCfgJson = new JSONObject(smtpCfg);
				} catch (Exception err) {
					LOGGER.error(err.getMessage());
				}
			}
		}

		JobUtilsSendMail sendMail = null;
		if (smtpCfgJson == null) {
			//sendMail = new JobUtilsSendMail(super.getRowSup());
			sendMail = new JobUtilsSendMail();
		} else {
			// smtp的json(SMTP_HOST, SMTP_PORT, SMTP_UID, SMTP_PWD)
			sendMail = new JobUtilsSendMail(smtpCfgJson);
			this.log(smtpCfgJson.toString());
		}

		sendMail.setMessageId(messageId);
		com.gdxsoft.easyweb.utils.Mail.SendMail sender = sendMail.getSendMail();

		// 发件人和回复的人不是同一个人，用于高信任域代替实际人发邮件
		// 例如用lei.guo@oneworld.cc代替2877274@qq.com
		if (REPLAY_TO_EMAIL != null && REPLAY_TO_EMAIL.trim().length() > 0) {
			sender.addReplyTo(REPLAY_TO_EMAIL, REPLAY_TO_NAME);
		}
		// 跟踪需要，单一发送独立邮件到独立的邮箱，单是显示收件人为多个
		if (singleToEmail != null && singleToEmail.trim().length() > 0) {
			sender.setSingleTo(singleToEmail, singleToName);
		}

		// 无附件发送邮件
		if (atts == null || atts.trim().length() == 0) {
			boolean ok = sendMail.sendHtmlMail(from, fromName, toaddString, toName, subject, content);

			this.log("发送邮件[sys_message_info]，" + ok + ": " + from + "->" + toaddString + ", " + subject);
			if (ok) {
				msg("，成功");
			} else {
				msg("，失败");
			}
			return ok;
		}

		// 有附件发送邮件
		ArrayList<String> attFiles = new ArrayList<String>();
		try {
			attFiles = MsgUtils.attachments(atts);
		} catch (Exception e) {
			msg("，失败：" + e.getLocalizedMessage());
			return false;
		}
		String msg = sendMail.sendHtmlMailA(from, fromName, toaddString, toName, subject, content, attFiles);

		if (msg == null) {
			msg("，成功");
			return true;
		} else {
			LOGGER.error("SENDMAIL[" + row.getCell("MESSAGE_ID").toString() + "] : " + msg);
			msg("，失败：" + msg);
			return false;
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @param row sys_message_info的数据行
	 * @return
	 * @throws Exception
	 */
	public boolean sendMail(DTRow row) throws Exception {
		// 附件
		String atts = row.getCell("ATTS").toString();
		return this.sendMail(row, atts);
	}

	private void msg(String msg) {
		if (msgSendMails != null) {
			msgSendMails.a(msg);
		} else {
			LOGGER.info(msg);
		}
	}

}
