package com.gdxsoft.web.job;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.data.DTRow;
import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.utils.Mail.Addr;
import com.gdxsoft.easyweb.utils.msnet.MListStr;

/**
 * 检查邮件及提醒<br>
 * OA_REQ_MAIL<br>
 * SYS_ALERT<br>
 * sys_message_info<br>
 * 
 * @author admin
 *
 */
public class JobOaReq extends JobBase {
	private static Logger LOGGER = LoggerFactory.getLogger(JobOaReq.class);
	private Addr sender; // 发件人

	public JobOaReq(DataConnection cnn) {
		super(cnn, null);
	}

	public void runTask() {
		try {
			log("发送工作邮件任务(OA_REQ_MAIL)");
			this.oaReqMailSendMails(_Conn);
		} catch (Exception e) {
			log("sendJobMails: " + e.getMessage());
		}
		try {
			log("发送延迟工作邮件任务(OA_REQ)");
			this.oaReqSendDelaysMails(super._Conn);
		} catch (Exception e) {
			log("sendJobMails: " + e.getMessage());
		}
		super._Conn.close();
	}

	public void oaReqSendDelaysMails(DataConnection conn) throws Exception {
		final long oneMinute = 60 * 1000;
		final long oneHour = 60 * oneMinute;

		// 23小时59分钟
		Date before23hour = new Date(System.currentTimeMillis() - 23 * oneHour + 59 * oneMinute);
		// 14天前
		Date lastDate = new Date(System.currentTimeMillis() - 14 * 24 * oneHour);

		conn.getRequestValue().addOrUpdateValue("before23hour", before23hour, "date", 100);
		conn.getRequestValue().addOrUpdateValue("lastDate", lastDate, "date", 100);
		// 工作延迟的邮件
		StringBuilder sb1 = new StringBuilder();
		sb1.append("SELECT REQ_ID,REQ_PID, 'REQ_MAIL_DELAY' REQ_MAIL_TYPE,REQ_STATUS,REQ_REV_PLAN_TIME\n");
		sb1.append(" FROM OA_REQ WHERE REQ_PID IS NULL\n");
		sb1.append(" 	AND REQ_REV_OK_TIME IS NULL\n");
		sb1.append(" 	AND NOT REQ_REV_PLAN_TIME IS NULL\n");
		// 未完成的计划时间在 14天前到今天
		sb1.append(" 	AND REQ_REV_PLAN_TIME between  @lastDate and @sys_date \n");
		sb1.append(" 	AND REQ_STATUS != 'OA_REQ_DEL' \n");
		sb1.append(" 	and (REQ_DELAY_MAIL_TIME is null or REQ_DELAY_MAIL_TIME > @before23hour)");

		String sqlDelay = sb1.toString();
		DTTable tbDelay = DTTable.getJdbcTable(sqlDelay, conn);
		if (tbDelay.getCount() == 0) {
			return;
		}

		// 工作延迟
		MListStr lstDelay = new MListStr();
		for (int i = 0; i < tbDelay.getCount(); i++) {
			DTRow r = tbDelay.getRow(i);
			long planTime = r.getCell("REQ_REV_PLAN_TIME").toTime();
			long pastHours = (System.currentTimeMillis() - planTime) / oneHour;
			if (pastHours < 24 || pastHours == 3 * 24 || pastHours == 14 * 24) {
				// 分别在1天，3天和14天各发一次提醒
				try {
					this.sendJobMail(r, lstDelay);
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
				}
			}
		}

		// 工作延迟
		if (lstDelay.getList().size() > 0) {
			String sql = " UPDATE OA_REQ SET REQ_DELAY_MAIL_TIME = GETDATE() WHERE REQ_ID IN (" + lstDelay.join(",")
					+ ")";
			conn.executeUpdate(sql);
		}
	}

	/**
	 * 发送任务监控邮件
	 */
	public void oaReqMailSendMails(DataConnection conn) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT A.REQ_ID,B.REQ_PID,A.REQ_MAIL_TYPE,REQ_STATUS FROM OA_REQ_MAIL A \n");
		sb.append(" inner join oa_req b on a.req_id=b.req_id \n");
		sb.append(" WHERE NOT A.REQ_ID IN ( \n");
		sb.append("	 	select REQ_ID from oa_req where req_adm_id=REQ_REV_ADM_ID \n");
		sb.append("			and (req_rev_deps is null or req_rev_deps ='') \n");
		sb.append("	) AND  \n");
		sb.append(" 	REQ_MAIL_sdate IS NULL  \n");
		sb.append(" 	AND REQ_STATUS<>'OA_REQ_DEL' ");

		String sql = sb.toString();
		DTTable tb = DTTable.getJdbcTable(sql, conn);
		if (tb.getCount() == 0) {
			return;
		}

		MListStr lst = new MListStr();
		for (int i = 0; i < tb.getCount(); i++) {
			DTRow r = tb.getRow(i);
			try {
				this.sendJobMail(r, lst);

			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}

		//
		if (lst.getList().size() > 0) {
			sql = "UPDATE OA_REQ_MAIL SET REQ_MAIL_SDATE=GETDATE() WHERE REQ_ID IN (" + lst.join(",") + ")";
			conn.executeUpdate(sql);
		}

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
			jm.setSender(sender);
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
			jm.setSender(sender);

			jm.sendProcess(reqId);
		}

	}

	/**
	 * @return the sender
	 */
	public Addr getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(Addr sender) {
		this.sender = sender;
	}
}
