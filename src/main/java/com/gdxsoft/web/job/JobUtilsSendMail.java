package com.gdxsoft.web.job;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;

import com.gdxsoft.easyweb.data.DTRow;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.easyweb.utils.Mail.DKIMCfg;
import com.gdxsoft.easyweb.utils.Mail.DKIMMessage;
import com.gdxsoft.easyweb.utils.Mail.DKIMSigner;
import com.gdxsoft.easyweb.utils.Mail.SendMail;
import com.gdxsoft.easyweb.utils.Mail.SmtpCfgs;
import com.gdxsoft.easyweb.utils.msnet.MStr;


public class JobUtilsSendMail extends JobBase {
	Properties props;
	private boolean is_mail_debug_ = false; // 是否显示debug信息

	private String mailSender; // 代理发件人
	private String mailSenderName; // 代理发件人名称
	private String host;
	private String messageId; // 如:供应商标识.messageID

	private SendMail sendMail;

	private static Logger log = LoggerFactory.getLogger(JobUtilsSendMail.class);

	/**
	 * 邮件的base 格式 http://aaa.com ...
	 * 
	 * @param host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return this.host;
	}

	public JobUtilsSendMail() {
		this.sendMail = new SendMail();
	}
	/**
	 * 根据json配置初始化
	 * 
	 * @param smtpCfg smtp的json(SMTP_HOST, SMTP_PORT, SMTP_UID, SMTP_PWD)
	 * @throws Exception
	 */
	public JobUtilsSendMail(JSONObject smtpCfg) throws Exception {
		String host;
		host = smtpCfg.optString("SMTP_HOST");
		String uid = smtpCfg.optString("SMTP_UID");
		String pwd = smtpCfg.optString("SMTP_PWD");
		Integer port = smtpCfg.optInt("SMTP_PORT");

		this.initProps(host, port, uid, pwd);
	}

	public JobUtilsSendMail(DTRow r) throws Exception {
		String host;
		host = r.getCell("SMTP_HOST").toString();
		String uid = r.getCell("SMTP_UID").toString();
		String pwd = r.getCell("SMTP_PWD").toString();
		Integer port = r.getCell("SMTP_PORT").toInt();
		try {
			this.mailSender = r.getCell("SMTP_SENDER").toString();
			this.mailSenderName = r.getCell("SMTP_SENDER_NAME").toString();
		} catch (Exception err) {
			System.out.println("老结构，没有SMTP_SENDER 和 SMTP_SENDER_NAME");
		}

		this.initProps(host, port, uid, pwd);
	}

	public JobUtilsSendMail(DTRow r, boolean is_mail_debug) throws Exception {
		this.is_mail_debug_ = is_mail_debug;
		String host;
		host = r.getCell("SMTP_HOST").toString();
		String uid = r.getCell("SMTP_UID").toString();
		String pwd = r.getCell("SMTP_PWD").toString();
		Integer port = r.getCell("SMTP_PORT").toInt();
		try {
			this.mailSender = r.getCell("SMTP_SENDER").toString();
			this.mailSenderName = r.getCell("SMTP_SENDER_NAME").toString();
		} catch (Exception err) {
			System.out.println("老结构，没有SMTP_SENDER 和 SMTP_SENDER_NAME");
		}

		this.initProps(host, port, uid, pwd);
	}

	/**
	 * 初始化发送邮件结构
	 * @param host
	 * @param port
	 * @param uid
	 * @param pwd
	 */
	public JobUtilsSendMail(String host, int port, String uid, String pwd) {
		this.initProps(host, port, uid, pwd);
	}

	/**
	 * 初始化SMTP属性
	 * 
	 * @param host
	 * @param port
	 * @param uid
	 * @param pwd
	 */
	private void initProps(String host, int port, String uid, String pwd) {

		this.sendMail = new SendMail();
		this.sendMail.initProps(host, port, uid, pwd);

		log.info("SMTP: " + host + ":" + port + ", uid=" + uid + ", pwd=" + pwd);
	}

	/**
	 * 发生Html邮件
	 * 
	 * @param from     发件人
	 * @param fromName 发件人姓名
	 * @param to       收件人
	 * @param toName   收件人姓名
	 * @param subject  主题
	 * @param content  内容
	 * @return 是否成功
	 */
	public boolean sendHtmlMail(String from, String fromName, String to, String toName, String subject,
			String content) {
		this.initSendMailCommon(from);

		if (content == null) {
			content = "";
		}
		MStr s = new MStr();
		if (content.toUpperCase().indexOf("<BASE") < 0) {
			s.al("<html><head>");
			s.al("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
			if (this.host != null) {
				s.al("<base href=\"" + this.host + "\">");
			}
			s.al("</head></body>");
		}
		if (this.host != null) {
			s.al("<base href=\"" + this.host + "\">");
		}
		s.a(content);
		s.al("</body></html>");

		String tos[] = to.trim().split(";");
		String toNames[] = toName == null ? null : toName.trim().split(";");

		StringBuilder sb = new StringBuilder();
		sb.append("from: ");
		sb.append(from);
		sb.append(", to: ");
		sb.append(to);
		sb.append(", subject: ");
		sb.append(subject);
		String msg = sb.toString();
		log.info(msg);

		return this.sendMail.setFrom(from, fromName).setSubject(subject).setHtmlContent(s.toString())
				.addTos(tos, toNames).send();

	}

	private void initSendMailCommon(String fromEmail) {
		if (this.mailSender != null && this.mailSender.trim().length() > 0) {
			this.sendMail.setSender(this.mailSender, this.mailSenderName);

		}
		if (this.is_mail_debug_) {
			this.sendMail.setMailDebug(is_mail_debug_);
		}

		if (this.messageId != null && this.messageId.trim().length() > 0) {
			this.sendMail.setMessageId(this.messageId);
		}

		DKIMCfg cfg = SmtpCfgs.getDkim(fromEmail);
		if (cfg != null) {
			this.sendMail.setDkim(cfg);
			log.info(cfg.getDomain());
		}
	}

	public String sendHtmlMailA(String from, String fromName, String to, String toName, String subject,
			String content) {
		return sendHtmlMailA(from, fromName, to, toName, subject, content, null);
	}

	public String sendHtmlMailA(String from, String fromName, String to, String toName, String subject, String content,
			ArrayList<String> atts) {

		this.initSendMailCommon(from);

		MStr s = new MStr();
		if (content.toUpperCase().indexOf("<BASE") < 0) {
			s.al("<!DOCTYPE html>");
			s.al("<html>");
			s.al("<head>");
			s.al("<meta charset=\"UTF-8\">");
			s.a("<title>");
			s.a(Utils.textToInputValue(subject));
			s.al("</title>");
			s.al("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=3\">");
			s.al("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
			if (this.host != null) {
				s.al("<base href=\"" + this.host + "\">");
			}
			s.al("</head>");
			s.al("</body>");
		}
		if (this.host != null) {
			s.al("<base href=\"" + this.host + "\">");
		}
		s.a(content);
		s.al("</body></html>");
		String tos[] = to.trim().split(";");
		String toNames[] = toName == null ? null : toName.trim().split(";");

		if (atts != null && atts.size() > 0) {
			for (int i = 0; i < atts.size(); i++) {
				String path = atts.get(i);
				File f;
				String des = null;

				if (path.trim().startsWith("{") && path.trim().endsWith("}")) { // 附件为JSON
					JSONObject obj = new JSONObject(path);
					f = new File(obj.getString("url"));
					des = obj.getString("name");
				} else {
					f = new File(path);

				}
				if (des == null || des.trim().length() == 0) {
					des = f.getName();
				}
				if (f.exists() && f.isFile()) {
					this.sendMail.addAttach(des, f);
				}

			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append("from: ");
		sb.append(from);
		sb.append(", to: ");
		sb.append(to);
		sb.append(", subject: ");
		sb.append(subject);
		String msg = sb.toString();
		log.info(msg);

		this.sendMail.setFrom(from, fromName).setSubject(subject).setHtmlContent(s.toString()).addTos(tos, toNames);
		boolean isok = this.sendMail.send();
		if (isok) {
			return null;
		} else {
			return this.sendMail.getLastError().getMessage();
		}
	}

	/**
	 * 发送带附件的邮件
	 * 
	 * @param from     发件人地址
	 * @param fromName 发件人姓名
	 * @param tos      收件人地址Map，key=地址，val=姓名
	 * @param ccs      抄送地址Map，key=地址，val=姓名
	 * @param subject  标题
	 * @param content  内容
	 * @param atts     附件Map，key=文件名，val=物理地址
	 * @return
	 */
	public String sendHtmlMailB(String from, String fromName, HashMap<String, String> tos, HashMap<String, String> ccs,
			String subject, String content, HashMap<String, String> atts) {

		this.initSendMailCommon(from);

		if (content == null) {
			content = "";
		}

		if (this.mailSender != null && this.mailSender.trim().length() > 0) {
			this.sendMail.setSender(this.mailSender, this.mailSenderName);

		}
		if (this.is_mail_debug_) {
			this.sendMail.setMailDebug(is_mail_debug_);
		}

		if (this.messageId != null && this.messageId.trim().length() > 0) {
			this.sendMail.setMessageId(this.messageId);
		}

		for (String email : tos.keySet()) {
			this.sendMail.addTo(email, tos.get(email));
		}

		for (String email : ccs.keySet()) {
			this.sendMail.addCc(email, ccs.get(email));
		}

		for (String des : atts.keySet()) {
			this.sendMail.addAttach(des, atts.get(des));
		}

		MStr s = new MStr();
		if (content.toUpperCase().indexOf("<BASE") < 0) {
			s.al("<html><head>");
			s.al("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
			if (this.host != null) {
				s.al("<base href=\"" + this.host + "\">");
			}
			s.al("</head></body>");
		}
		if (this.host != null) {
			s.al("<base href=\"" + this.host + "\">");
		}
		s.a(content);
		s.al("</body></html>");

		StringBuilder sb = new StringBuilder();
		sb.append("from: ");
		sb.append(from);
		sb.append(", to: ");
		sb.append(tos);
		sb.append(", subject: ");
		sb.append(subject);
		String msg = sb.toString();
		log.info(msg);

		boolean isok = this.sendMail.setFrom(from, fromName).setSubject(subject).setHtmlContent(s.toString()).send();

		if (isok) {
			return null;
		} else {
			return this.sendMail.getLastError().getMessage();
		}

	}

	public MimeMessage dkimSign(MimeMessage mm) throws Exception {
		InternetAddress addr = (InternetAddress) mm.getFrom()[0];
		DKIMSigner dkimSigner = super.getDKIMSignerByEmail(addr.getAddress());
		if (dkimSigner != null) {
			dkimSigner.setIdentity(addr.getAddress());
			mm = new DKIMMessage(mm, dkimSigner);
		}
		return mm;
	}

	/**
	 * 邮件 sender参数， 设置邮件的sender参数，看看能不能躲过垃圾邮件检测
	 * 
	 * @return
	 */
	public String getMailSender() {
		return mailSender;
	}

	/**
	 * 邮件 sender参数， 设置邮件的sender参数，看看能不能躲过垃圾邮件检测
	 * 
	 * @param mailSender 邮件格式 类似: guolei@sina.com
	 */
	public void setMailSender(String mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * 邮件 sender 的用户名参数， 设置邮件的sender参数，看看能不能躲过垃圾邮件检测
	 * 
	 * @return
	 */
	public String getMailSenderName() {
		return mailSenderName;
	}

	/**
	 * 邮件 sender 的用户名参数， 设置邮件的sender参数，看看能不能躲过垃圾邮件检测
	 * 
	 * @param mailSenderName 名字，例如：郭磊、靳朝鹏、王峰...
	 */
	public void setMailSenderName(String mailSenderName) {
		this.mailSenderName = mailSenderName;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String msgId) {
		this.messageId = msgId;
	}

	/**
	 * 获取发送邮件对象
	 * 
	 * @return the sendMail
	 */
	public SendMail getSendMail() {
		return sendMail;
	}

}
