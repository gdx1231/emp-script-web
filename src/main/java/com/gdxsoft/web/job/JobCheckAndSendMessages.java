/**
 * 
 */
package com.gdxsoft.web.job;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.data.DTRow;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.utils.Mail.Addr;

/**
 * @author admin
 *
 */
public class JobCheckAndSendMessages {
	private static Logger log = LoggerFactory.getLogger(JobCheckAndSendMessages.class);
	static long minute = 1000L * 60;
	private DTRow _row;

	private Addr oaReqSender;

	/**
	 * 发送短信 表：sms_job
	 * 
	 * @param cnn
	 * @param dbName
	 */
	public void runSmsSend(DataConnection cnn ) {
		JobSms sms = new JobSms(cnn);
		sms.setRowSup(_row);
		log.info("  发送短信 表：sms_job runSmsSend");
		try {
			sms.sendSmsByJob2();
		} catch (Exception e) {
			log.error("runSmsSend" + e.getMessage());
		}
	}

	/**
	 * 工作任务跟踪 oa_req
	 * @param cnn
	 */
	public void runJobOaReq(DataConnection cnn ) {
		log.info("工作任务跟踪");
		JobOaReq job = new JobOaReq(cnn);
		job.setRowSup(_row);
		try {
			job.runTask();
		} catch (Exception e) {
			log.error("runJobOaReq: " + e.getMessage());
		}
	}
	
	/**
	 * 邮件发送与提醒短信发送
	 * 
	 * @param cnn
	 * @param dbName
	 */
	public void runMailSend(DataConnection cnn ) {
		log.info("  邮件发送与提醒短信发送 runMailSend");
		JobMailSender job = new JobMailSender(cnn);
		job.setRowSup(_row);
		try {
			job.runTask();
		} catch (Exception e) {
			log.error("  runMailSend: " + e.getMessage());
		}
	}

	/**
	 * 检查工作邮箱邮件
	 * 
	 * @param cnn
	 * @param dbName
	 */
	public void runMailScan(DataConnection cnn, String dbName) {
		log.info("  检查工作邮箱邮件 runMailScan");

		// private String HOST = "192.168.1.10";
		// private String USERNAME = "root@192.168.1.10";
		// private String PASSWORD = "gdx1231";
		// private String JOB_EXP = "@192.168.1.10";

		JobMailScan m = new JobMailScan(cnn, dbName);
		m.setRowSup(_row);

		try {
			m.checkJobMails();
		} catch (Exception err) {
			log.error("JobMailScan: " + err.getMessage());
		}

	}

	/**
	 * 检查退信
	 * 
	 * @param cnn
	 */
	public void runBounceMail(DataConnection cnn) {
		log.info("  检查退信 runBounceMail");
		JobMailBounce b = new JobMailBounce(cnn);
		try {
			b.start();
		} catch (Exception err) {
			log.error("JobMailBounce: " + err.getMessage());
		}
	}

	/**
	 * @return the oaReqSender
	 */
	public Addr getOaReqSender() {
		return oaReqSender;
	}

	/**
	 * @param oaReqSender the oaReqSender to set
	 */
	public void setOaReqSender(Addr oaReqSender) {
		this.oaReqSender = oaReqSender;
	}
}
