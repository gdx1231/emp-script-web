package com.gdxsoft.web.log;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.JSONObject;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.message.sms.ISms;
import com.gdxsoft.web.dao.SmsJob;
import com.gdxsoft.web.dao.SmsJobDao;
import com.gdxsoft.web.dao.SupMain;
import com.gdxsoft.web.dao.SupMainDao;
import com.gdxsoft.web.dao.SysMessageInfo;
import com.gdxsoft.web.job.JobMailScan;


/**
 * 日志的消息订阅
 *
 * @author admin
 *
 */
public class LogConsumer {
	private static final Map<Integer, JobMailScan> MAIL_SENDERS = new ConcurrentHashMap<>();
	private static Logger LOGGER = LoggerFactory.getLogger(LogConsumer.class);

	/**
	 * 获取发送邮件的数据库名称和smtp地址配置
	 *
	 * @param sup_id
	 * @return
	 * @throws Exception
	 */
	private static JobMailScan getJobMailSender(int sup_id) throws Exception {
		JobMailScan cached = MAIL_SENDERS.get(sup_id);
		if (cached != null) {
			return cached;
		}

		SupMainDao d1 = new SupMainDao();
		SupMain supMain = d1.getRecord(sup_id);

		String sql = "select * from oneworld_main_data..bas_sup_main where sup_unid='"
				+ supMain.getSupUnid().replace("'", "''") + "'";
		DTTable tb = DTTable.getJdbcTable(sql);
		String dbName = tb.getCell(0, "INIT_DATABASE").toString();

		DataConnection cnn = new DataConnection("", null);
		JobMailScan jms = new JobMailScan(cnn, dbName);
		jms.setRowSup(tb.getRow(0));

		JobMailScan prev = MAIL_SENDERS.putIfAbsent(sup_id, jms);
		if (prev != null) {
			// 另一个线程已经先放入了，关闭当前创建的连接
			cnn.close();
			return prev;
		}

		return jms;
	}

	/**
	 * 发送邮件
	 * 
	 * @param info
	 */
	private static void execSendMail(SysMessageInfo info) {
		try {
			JobMailScan sender = getJobMailSender(info.getFromSupId());
			String sql = "select * from SYS_MESSAGE_INFO where MESSAGE_ID=" + info.getMessageId();
			DTTable tb = DTTable.getJdbcTable(sql);

//			boolean isok = sender.sendMail(tb.getRow(0));
//			sender.updateStatus(info.getMessageId(), isok);

		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		}
	}

	 

	/**
	 * 发送短信
	 * 
	 * @param info
	 */
	private static void execSendSms(ISms sms,SmsJob info) {
		SmsJobDao d = new SmsJobDao();

		String phoneNumber = info.getSmsPhones();
		String outId = info.getSmsJid().toString();
		String smsTemplateCode = info.getSmsTemplateCode();
		String smsSignName = info.getSmsSignName();
		sms.setSmsSignName(smsSignName);
		sms.setSmsTemplateCode(smsTemplateCode);
		
		try {
			JSONObject data = new JSONObject(info.getSmsTemplateJson());
			// 发送短信
			JSONObject result = sms.sendSmsAndGetResponse(phoneNumber, data, outId);
			String rst = result.toString();
			if (rst.length() > 500) {
				rst = rst.substring(0, 500);
			}
			info.startRecordChanged();
			info.setSmsJcnt(rst);
			info.setSmsJmdate(new Date());
			info.setSmsJstatus("OK");

			d.updateRecord(info, info.getMapFieldChanged());

			LOGGER.info("SMS：" + phoneNumber + ", " + data);

		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		}

	}
   

	 

}
