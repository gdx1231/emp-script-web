package com.gdxsoft.web.message.email;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gdxsoft.easyweb.data.DTRow;
import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.utils.Mail.SendMail;
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
public class SendSysMessageInfoMail {
	private static Logger LOGGER = LoggerFactory.getLogger(SendSysMessageInfoMail.class);

	MStr msgSendMails;
	DataConnection conn;

	public SendSysMessageInfoMail(DataConnection cnn) {
		conn = cnn;
	}

	public String getMsgSendMails() {
		if (msgSendMails == null) {
			return null;
		}
		return msgSendMails.toString();
	}

	public boolean sendMailCalendar(int messageId) throws Exception {
		String sql = "select * from sys_message_info where message_id=" + messageId;
		DTTable tb = DTTable.getJdbcTable(sql, conn);

		return this.sendMailCalendar(tb.getRow(0));
	}

	/**
	 * 发送日程
	 * 
	 * @param row
	 * @return
	 * @throws Exception
	 */
	public boolean sendMailCalendar(DTRow row) throws Exception {
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

	private SendMail createSender(DTRow row) {
		SendMail sender = null;
		JSONObject smtpCfgJson = null;
		// 检查是否有指定的SMTP配置
		int colIndex = row.getTable().getColumns().getNameIndex("SMTP_CFG");
		if (colIndex < 0) {
			return new SendMail();
		}
		String smtpCfg = row.getCell(colIndex).toString();
		if (StringUtils.isBlank(smtpCfg)) {
			return new SendMail();
		}

		try {
			smtpCfgJson = new JSONObject(smtpCfg);

			String host = smtpCfgJson.optString("SMTP_HOST");
			String uid = smtpCfgJson.optString("SMTP_UID");
			String pwd = smtpCfgJson.optString("SMTP_PWD");
			Integer port = smtpCfgJson.optInt("SMTP_PORT");

			sender = new SendMail(host, port, uid, pwd);
		} catch (Exception err) {
			sender = new SendMail();
			LOGGER.error(err.getMessage());
		}

		return sender;
	}

	public boolean sendMail(int messageId) throws Exception {
		String sql = "select * from sys_message_info where message_id=" + messageId;
		DTTable tb = DTTable.getJdbcTable(sql, conn);

		return this.sendMail(tb.getRow(0));
	}

	/**
	 * 发送邮件
	 * 
	 * @param row sys_message_info的数据行
	 * @return
	 * @throws Exception
	 */
	public boolean sendMail(DTRow row) throws Exception {
		SendMail sender = createSender(row);
		int messageId = row.getCell("message_id").toInt();

		String from = String.valueOf(row.getCell("FROM_EMAIL"));
		String fromName = String.valueOf(row.getCell("FROM_NAME"));
		sender.setFrom(from, fromName);

		String subject = String.valueOf(row.getCell("MESSAGE_TITLE"));
		String content = String.valueOf(row.getCell("MESSAGE_CONTENT"));
		sender.setSubject(subject).setHtmlContent(content);

		String to = String.valueOf(row.getCell("TARGET_EMAIL"));
		String toName = String.valueOf(row.getCell("TARGET_NAME"));
		int tos = this.addTos(sender, to, toName);

		String CC_EMAILS = row.getCell("CC_EMAILS").toString();
		String CC_NAMES = row.getCell("CC_NAMES").toString();
		int ccs = this.addCcs(sender, CC_EMAILS, CC_NAMES);

		String BCC_EMAILS = row.getCell("BCC_EMAILS").toString();
		String BCC_NAMES = row.getCell("BCC_NAMES").toString();
		int bccs = this.addBccs(sender, BCC_EMAILS, BCC_NAMES);
		if (tos + ccs + bccs == 0) {
			LOGGER.error("收件人为空");
			return false;
		}

		// 跟踪需要，单一发送独立邮件到独立的邮箱，单是显示收件人为多个
		String singleToName = row.getCell("SINGLE_TO_NAME").toString();
		String singleToEmail = row.getCell("SINGLE_TO_EMAIL").toString();

		// 发件人和回复的人不是同一个人，用于高信任域代替实际人发邮件，
		// 例如用lei.guo@oneworld.cc代替2877274@qq.com
		String REPLAY_TO_NAME = row.getCell("REPLAY_TO_NAME").toString();
		String REPLAY_TO_EMAIL = row.getCell("REPLAY_TO_EMAIL").toString();

		// 发件人和回复的人不是同一个人，用于高信任域代替实际人发邮件
		// 例如用lei.guo@oneworld.cc代替2877274@qq.com
		if (REPLAY_TO_EMAIL != null && REPLAY_TO_EMAIL.trim().length() > 0) {
			sender.addReplyTo(REPLAY_TO_EMAIL, REPLAY_TO_NAME);
		}
		// 跟踪需要，单一发送独立邮件到独立的邮箱，单是显示收件人为多个
		if (singleToEmail != null && singleToEmail.trim().length() > 0) {
			sender.setSingleTo(singleToEmail, singleToName);
		}

		// 附件
		String atts = row.getCell("ATTS").toString();
		// 有附件发送邮件
		try {
			List<String> attFiles = MsgUtils.attachments(atts);
			for (int i = 0; i < attFiles.size(); i++) {
				sender.addAttach(attFiles.get(i));
			}
		} catch (Exception e) {
			msg("，失败：" + e.getLocalizedMessage());
			return false;
		}

		// 无附件发送邮件
		boolean ok = sender.send();
		LOGGER.info("发送邮件[sys_message_info]: {}, {}, {}, {}", ok, from, to, subject);
		if (ok) {
			msg("，成功");
		} else {
			msg("，失败");
		}

		this.updateStatus(messageId, ok, ok ? null : sender.getLastError().getLocalizedMessage());

		return ok;
	}

	private void updateStatus(int messageId, boolean isOk, String messageLog) throws Exception {

		conn.getRequestValue().addOrUpdateValue("MESSSAGE_LOG", messageLog);

		String sql = "update sys_message_info set message_status='" + (isOk ? "YES" : "ERR")
				+ "', send_date=@sys_date, MESSSAGE_LOG=@MESSSAGE_LOG WHERE MESSAGE_ID=" + messageId;

		this.conn.executeUpdate(sql);
	}

	private void msg(String msg) {
		if (msgSendMails != null) {
			msgSendMails.a(msg);
		} else {
			LOGGER.info(msg);
		}
	}

	private int addTos(SendMail sender, String tos, String toNames) {
		if (StringUtils.isBlank(tos)) {
			return 0;
		}
		String[] tos1 = tos.split(";");
		String[] toNames1 = toNames.split(";");
		int inc = 0;
		for (int i = 0; i < tos1.length; i++) {
			if (toNames1.length == tos1.length) {
				sender.addTo(tos1[i], toNames1[i]);
			} else {
				sender.addTo(tos1[i]);
			}
			inc++;
		}
		return inc;
	}

	private int addBccs(SendMail sender, String bccs, String bccNames) {
		if (StringUtils.isBlank(bccs)) {
			return 0;
		}
		String[] tos = bccs.split(";");
		String[] toNames = (bccNames == null ? "" : bccNames).split(";");
		int inc = 0;
		for (int i = 0; i < tos.length; i++) {
			if (toNames.length == tos.length) {
				sender.addBcc(tos[i], toNames[i]);
			} else {
				sender.addBcc(tos[i]);
			}
			inc++;
		}
		return inc;
	}

	private int addCcs(SendMail sender, String ccs, String ccNames) {
		if (StringUtils.isBlank(ccs)) {
			return 0;
		}
		String[] tos = ccs.split(";");
		String[] toNames = (ccNames == null ? "" : ccNames).split(";");
		int inc = 0;
		for (int i = 0; i < tos.length; i++) {
			if (toNames.length == tos.length) {
				sender.addCc(tos[i], toNames[i]);
			} else {
				sender.addCc(tos[i]);
			}
			inc++;
		}

		return inc;
	}

}
