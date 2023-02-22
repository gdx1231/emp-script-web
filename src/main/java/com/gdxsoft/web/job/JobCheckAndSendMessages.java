/**
 * 
 */
package com.gdxsoft.web.job;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.data.DTRow;
import com.gdxsoft.easyweb.datasource.DataConnection;

/**
 * @author admin
 *
 */
public class JobCheckAndSendMessages {
	private static Logger log = LoggerFactory.getLogger(JobMailSenderBase.class);
	static long minute = 1000L * 60;
	private DTRow _row;


	public void checkJobMessages(DataConnection cnn, String supName, String databaseName) {

		log.info("---- job start ----->  ");
		log.info("---- 开始：" + supName + " [" + databaseName + "] -----");
		runMailSend(cnn, databaseName);
		runSmsSend(cnn, databaseName);
		this.runBounceMail(cnn);
		cnn.close();
		log.info(" <---- job end -----");
	}

	/**
	 * 发送短信 表：sms_job
	 * 
	 * @param cnn
	 * @param dbName
	 */
	private void runSmsSend(DataConnection cnn, String dbName) {
		JobSms sms = new JobSms(cnn, dbName);
		sms.setRowSup(_row);
		log.info("  发送短信 表：sms_job runSmsSend");
		try {
			sms.sendSmsByJob2();
		} catch (Exception e) {
			log.error("  runSmsSend" + e.getMessage());
		}
	}

	/**
	 * 邮件发送与提醒短信发送
	 * 
	 * @param cnn
	 * @param dbName
	 */
	private void runMailSend(DataConnection cnn, String dbName) {
		log.info("  邮件发送与提醒短信发送 runMailSend");
		JobMailSender job = new JobMailSender(cnn, dbName);
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
	private void runBounceMail(DataConnection cnn) {
		log.info("  检查退信 runBounceMail");
		JobMailBounce b = new JobMailBounce(cnn);
		try {
			b.start();
		} catch (Exception err) {
			log.error("JobMailBounce: " + err.getMessage());
		}
	}
}
