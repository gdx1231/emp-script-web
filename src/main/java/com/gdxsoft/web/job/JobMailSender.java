package com.gdxsoft.web.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.data.DTRow;
import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.web.message.email.SendSysMessageInfoMail;

/**
 * 检查邮件及提醒<br>
 * OA_REQ_MAIL<br>
 * SYS_ALERT<br>
 * sys_message_info<br>
 * 
 * @author admin
 *
 */
public class JobMailSender extends JobBase {
	private static Logger LOGGER = LoggerFactory.getLogger(JobMailSender.class);

	private SendSysMessageInfoMail ssim;

	public JobMailSender(DataConnection cnn) {
		super(cnn, null);

		ssim = new SendSysMessageInfoMail(cnn);
	}

	public void runTask() {

		try {
			LOGGER.info("发送自动邮件任务(sys_message_info)");// 从 sys_message_info 中获取邮件发送
			sysMessageInfoSendMails(super._Conn);
		} catch (Exception e) {
			LOGGER.error("SendMails: {}", e.getMessage());
		}

		super._Conn.close();

		// SysAgent sa = new SysAgent();
		// sa.doWork();
	}

	/**
	 * 从 sys_message_info 中获取邮件发送
	 * 
	 * @param conn
	 * @throws Exception
	 */
	public void sysMessageInfoSendMails(DataConnection conn) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("select message_id, mail_type from sys_message_info where (message_status = 'NO' ");
		sb.append(" OR message_status IS NULL) \n");
		sb.append(" and from_email != '' \n");
		sb.append(" and target_email != '' \n");
		sb.append(" order by message_id");
		String sql = sb.toString();

		DTTable table = DTTable.getJdbcTable(sql, conn);

		for (int i = 0; i < table.getCount(); i++) {
			DTRow row = table.getRow(i);
			String mailType = row.getCell("MAIL_TYPE").toString();
			int messageId = row.getCell("message_id").toInt();

			if ("CALENDER".equalsIgnoreCase(mailType)) {
				// 会议邮件
				ssim.sendMailCalendar(messageId);
			} else {
				ssim.sendMail(messageId);
			}

		}
	}

}
