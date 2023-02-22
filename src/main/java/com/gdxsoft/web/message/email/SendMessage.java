package com.gdxsoft.web.message.email;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.HtmlControl;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.script.display.frame.FrameParameters;
import com.gdxsoft.easyweb.utils.USnowflake;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.easyweb.utils.Mail.Addr;
import com.gdxsoft.easyweb.utils.Mail.SendMail;
import com.gdxsoft.easyweb.utils.msnet.MList;

public class SendMessage {
	private static Logger LOGGER = LoggerFactory.getLogger(SendMessage.class);

	public static String DEFAULT_EMAIL = "undefined@undefined.gdx";
	public static String DEFAULT_NAME = "undefined";

	public static final String TO = "TO";
	public static final String TO_EMAIL = "TO_EMAIL";
	public static final String TO_NAME = "TO_NAME";

	public static final String CC = "CC";
	public static final String CC_EMAIL = "CC_EMAIL";
	public static final String CC_NAME = "CC_NAME";

	public static final String BCC = "BCC";
	public static final String BCC_EMAIL = "BCC_EMAIL";
	public static final String BCC_NAME = "BCC_NAME";

	public static final String FROM = "FROM";
	public static final String FROM_EMAIL = "FROM_EMAIL";
	public static final String FROM_NAME = "FROM_NAME";

	public static final String SENDER = "SENDER";
	public static final String SENDER_EMAIL = "SENDER_EMAIL";
	public static final String SENDER_NAME = "SENDER_NAME";

	public static final String MSG_REF_TABLE = "MSG_REF_TABLE";
	public static final String MSG_REF_ID = "MSG_REF_ID";

	/**
	 * 保存邮件到队列中，重复邮件不写入
	 * 
	 * @param xmlName
	 * @param itemName
	 * @param rv
	 * @return
	 */
	public static String mailQueue(String xmlName, String itemName, RequestValue rv) {
		SendMessage sm = new SendMessage();
		sm.init(xmlName, itemName, rv);
		if (sm.repeat) {
			LOGGER.info("重复的邮件：{},{},{}", sm.messageId, sm.sendMail.getSubject(), sm.messageMd5);
			return "REPEAT";
		}
		int id = sm.saveToQueue();
		if (id == -1) {
			return sm.lastError;
		} else {
			return "OK";
		}
	}

	/**
	 * 直接发送邮件，重复邮件不发送
	 * 
	 * @param xmlName
	 * @param itemName
	 * @param rv
	 * @return
	 */
	public static String mailSend(String xmlName, String itemName, RequestValue rv) {
		SendMessage sm = new SendMessage();
		sm.init(xmlName, itemName, rv);
		if (sm.repeat) {
			return "REPEAT";
		}
		int id = sm.sendNow();
		if (sm.sendMail.getLastError() != null) {
			return "ERR:" + sm.sendMail.getLastError().getLocalizedMessage();
		}
		if (id == -1) {
			return "ERR:" + sm.lastError;
		} else {
			return "OK";
		}
	}

	private SendMail sendMail;
	private RequestValue rv;
	private String lastError;
	private boolean repeat;
	private Integer messageId;
	private String messageMd5;
	private DataConnection cnn;

	public SendMessage() {
		sendMail = new SendMail();
	}

	public int sendNow() {

		if (this.sendMail.send()) {
			rv.addOrUpdateValue("MESSAGE_STATUS", "YES");
		} else {
			rv.addOrUpdateValue("MESSAGE_STATUS", "ERR");
			rv.addOrUpdateValue("MESSSAGE_LOG", this.sendMail.getLastError());
		}
		return this.saveToQueue();
	}

	public int saveToQueue() {
		if (this.cnn == null) {
			this.cnn = new DataConnection(this.rv);
		}

		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO sys_message_info (");
		sb.append("   FROM_SUP_ID, FROM_USR_ID, FROM_EMAIL, FROM_NAME, TARGET_NAME, TARGET_EMAIL\n");
		sb.append(" , TARGET_TYPE, MESSAGE_TITLE, MESSAGE_CONTENT, MESSAGE_STATUS, CREATE_DATE\n");
		sb.append(" , SEND_DATE, MODULE_TYPE, IS_READ, REF_ID, REF_TABLE, MAIL_TYPE, ATTS\n");
		sb.append(" , MESSSAGE_LOG, FACEBACK_URL, REPLAY_TO_EMAIL, REPLAY_TO_NAME\n");
		sb.append(" , SINGLE_TO_EMAIL, SINGLE_TO_NAME, SMTP_CFG, MQ_MSG_ID, MQ_MSG\n");
		sb.append(" , CC_EMAILS,CC_NAMES,BCC_EMAILS,BCC_NAMES, MESSAGE_MD5,MAIL_MESSAGE_ID\n");
		sb.append(") VALUES(\n");
		sb.append("   @G_SUP_ID, @G_ADM_ID, @FROM_EMAIL, @FROM_NAME.50, @TARGET_NAME, @TARGET_EMAIL");
		sb.append(" , @TARGET_TYPE, @MESSAGE_TITLE.500, @MESSAGE_CONTENT, @MESSAGE_STATUS, @sys_DATE\n");
		sb.append(" , @SEND_DATE, @MODULE_TYPE, @IS_READ, @MSG_REF_ID, @MSG_REF_TABLE.200, @MAIL_TYPE, @ATTS\n");
		sb.append(" , @MESSSAGE_LOG, @FACEBACK_URL, @REPLAY_TO_EMAIL, @REPLAY_TO_NAME\n");
		sb.append(" , @SINGLE_TO_EMAIL, @SINGLE_TO_NAME, @SMTP_CFG, @MQ_MSG_ID, @MQ_MSG\n");
		sb.append(" , @CC_EMAILS, @CC_NAMES, @BCC_EMAILS, @BCC_NAMES, @MESSAGE_MD5, @MAIL_MESSAGE_ID\n");
		sb.append(")\n -- auto MESSAGE_ID");
		String sql = sb.toString();

		rv.addOrUpdateValue("MAIL_MESSAGE_ID", this.sendMail.getMessageId());

		int msgId = cnn.executeUpdateReturnAutoIncrement(sql);
		if (cnn.getErrorMsg() != null) {
			this.lastError = cnn.getErrorMsg();
		}
		cnn.close();

		return msgId;
	}

	/**
	 * 保存到队列
	 * 
	 * @param subject 邮件标题
	 * @param content 正文
	 * @param tos     收件人(email, name)
	 * @param ccs     抄送人(email, name)
	 * @param atts    附件(name, path)
	 * @return messageId
	 */
	public int saveToQueue(String subject, String content, Map<String, String> tos, Map<String, String> ccs,
			Map<String, String> atts) {
		this.sendMail.setFrom(DEFAULT_EMAIL, DEFAULT_NAME).setSubject(subject).setHtmlContent(content);
		tos.forEach((email, name) -> {
			this.sendMail.addTo(email, name);
		});
		if (ccs != null) {
			ccs.forEach((email, name) -> {
				this.sendMail.addCc(email, name);
			});
		}
		if (atts != null) {
			atts.forEach((name, path) -> {
				this.sendMail.addAttach(name, path);
			});
		}
		if (this.addParametersToRvAndCheckExists()) {
			return this.messageId;
		} else {
			return this.saveToQueue();
		}
	}

	private boolean addParametersToRvAndCheckExists() {
		StringBuilder sbMd5 = new StringBuilder();

		rv.addOrUpdateValue("MESSAGE_STATUS", "NO");

		sbMd5.append(rv.s(MSG_REF_TABLE)).append("GDX");
		sbMd5.append(rv.s(MSG_REF_ID)).append("GDX");

		rv.addOrUpdateValue("MESSAGE_TITLE", sendMail.getSubject());
		sbMd5.append(sendMail.getSubject()).append("GDX");

		rv.addOrUpdateValue("MESSAGE_CONTENT", sendMail.getHtmlContent());
		sbMd5.append(sendMail.getHtmlContent()).append("GDX");

		rv.addOrUpdateValue("FROM_NAME", this.sendMail.getFrom().getName());
		sbMd5.append(this.sendMail.getFrom().getName()).append("GDX");

		rv.addOrUpdateValue("FROM_EMAIL", this.sendMail.getFrom().getEmail());
		sbMd5.append(this.sendMail.getFrom().getEmail()).append("GDX");

		StringBuilder sbBcc = new StringBuilder();
		StringBuilder sbBccName = new StringBuilder();
		this.sendMail.getBccs().forEach((k, v) -> {
			if (sbBcc.length() > 0) {
				sbBcc.append(";");
				sbBccName.append(";");
			}
			sbBcc.append(v.getEmail());
			sbBccName.append(StringUtils.isBlank(v.getName()) ? v.getEmail() : v.getName());
		});
		rv.addOrUpdateValue("BCC_EMAILS", sbBcc.toString());
		sbMd5.append(sbBcc.toString()).append("GDX");
		rv.addOrUpdateValue("BCC_NAMES", sbBccName.toString());
		sbMd5.append(sbBccName.toString()).append("GDX");

		StringBuilder sbTo = new StringBuilder();
		StringBuilder sbToName = new StringBuilder();
		this.sendMail.getTos().forEach((k, v) -> {
			if (sbTo.length() > 0) {
				sbTo.append(";");
				sbToName.append(";");
			}
			sbTo.append(v.getEmail());
			sbToName.append(StringUtils.isBlank(v.getName()) ? v.getEmail() : v.getName());
		});
		rv.addOrUpdateValue("TARGET_EMAIL", sbTo.toString());
		sbMd5.append(sbTo.toString()).append("GDX");
		rv.addOrUpdateValue("TARGET_NAME", sbToName.toString());
		sbMd5.append(sbToName.toString()).append("GDX");

		StringBuilder ccTo = new StringBuilder();
		StringBuilder ccName = new StringBuilder();
		this.sendMail.getCcs().forEach((k, v) -> {
			if (sbTo.length() > 0) {
				ccTo.append(";");
				ccName.append(";");
			}
			ccTo.append(v.getEmail());
			ccName.append(StringUtils.isBlank(v.getName()) ? v.getEmail() : v.getName());
		});
		rv.addOrUpdateValue("CC_EMAILS", ccTo.toString());
		sbMd5.append(ccTo.toString()).append("GDX");
		rv.addOrUpdateValue("CC_NAMES", ccName.toString());
		sbMd5.append(ccName.toString()).append("GDX");

		this.messageMd5 = Utils.md5(sbMd5.toString());
		rv.addOrUpdateValue("MESSAGE_MD5", this.messageMd5);

		DataConnection cnn = new DataConnection(rv);

		String sqlMd5 = "select message_id from sys_message_info where message_md5=@MESSAGE_MD5";
		DTTable tb = DTTable.getJdbcTable(sqlMd5, cnn);
		if (tb.getCount() > 0) {
			// 重复的邮件，不添加了
			this.messageId = tb.getCell(0, 0).toInt();
			this.repeat = true;
		}
		cnn.close();
		return this.repeat;
	}

	public void init(String xmlName, String itemName, RequestValue refRequestValue) {
		this.rv = refRequestValue.clone();
		HtmlControl ht = new HtmlControl();

		this.rv.getPageValues().remove(FrameParameters.EWA_ACTION);
		this.rv.getPageValues().remove(FrameParameters.EWA_POST);
		this.rv.getPageValues().remove(FrameParameters.EWA_NO_CONTENT);

		String paras = "EWA_AJAX=INSTALL&EWA_SKIP_TEST1=1";
		ht.init(xmlName, itemName, paras, rv, null);
		String content = ht.getHtml();

		// 过滤script标签
		String scriptRegex = "<script[^>]*?>[\\s\\S]*?<\\/script>";
		// html注释
		String regEx_o = "<\\!--.*-->";
		content = content.replaceAll(scriptRegex, "").replaceAll(regEx_o, "").trim();

		MList tbList = ht.getHtmlCreator().getHtmlClass().getItemValues().getDTTables();

		this.sendMail.setHtmlContent(content).setSubject(ht.getTitle());
		int totalFrom = 0;
		for (int i = 0; i < tbList.size(); i++) {
			DTTable tb = (DTTable) tbList.get(i);
			addTo(tb);
			addCc(tb);
			addBcc(tb);
			addSender(tb);
			totalFrom += addFrom(tb);
		}

		if (totalFrom == 0) {
			this.sendMail.setFrom(DEFAULT_EMAIL, DEFAULT_NAME);
		}

		String mailMessageId = "gdxosft.com_EWA_" + USnowflake.nextId();
		this.sendMail.setMessageId(mailMessageId);

		if (rv.s(MSG_REF_TABLE) == null) {
			rv.addOrUpdateValue(MSG_REF_TABLE, itemName + "," + xmlName);
		}

		addParametersToRvAndCheckExists();
	}

	private int addSender(DTTable tb) {
		return addUsers(tb, SENDER_EMAIL, SENDER_NAME, SENDER);
	}

	private int addFrom(DTTable tb) {
		return addUsers(tb, FROM_EMAIL, FROM_NAME, FROM);
	}

	private int addBcc(DTTable tb) {
		return addUsers(tb, BCC_EMAIL, BCC_NAME, BCC);
	}

	private int addCc(DTTable tb) {
		return addUsers(tb, CC_EMAIL, CC_NAME, CC);
	}

	private int addTo(DTTable tb) {
		return addUsers(tb, TO_EMAIL, TO_NAME, TO);
	}

	private int addUsers(DTTable tb, String emailField, String nameField, String receptionType) {
		if (tb.getCount() == 0) {
			return 0;
		}
		int emailIndex = tb.getColumns().getNameIndex(emailField);
		if (emailIndex == -1) {
			return 0;
		}
		int count = 0;
		int nameIndex = tb.getColumns().getNameIndex(nameField);
		for (int i = 0; i < tb.getCount(); i++) {
			String email = tb.getCell(i, emailIndex).toString();
			if (email == null || email.trim().length() == 0) {
				continue;
			}
			if (nameIndex == -1) {
				addReception(email, null, receptionType);
			} else {
				String name = tb.getCell(i, nameIndex).toString();
				addReception(email, name, receptionType);
			}
			count++;
		}
		return count;
	}

	private void addReception(String email, String name, String receptionType) {
		Addr addr = new Addr(email, name);
		if ("to".equalsIgnoreCase(receptionType)) {
			sendMail.addTo(addr);
		} else if ("cc".equalsIgnoreCase(receptionType)) {
			sendMail.addCc(addr);
		} else if ("bcc".equalsIgnoreCase(receptionType)) {
			sendMail.addBcc(addr);
		} else if ("sender".equalsIgnoreCase(receptionType)) {
			sendMail.setSender(addr);
		} else if ("from".equalsIgnoreCase(receptionType)) {
			sendMail.setFrom(addr);
		}
	}

	/**
	 * @return the sendMail
	 */
	public SendMail getSendMail() {
		return sendMail;
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
	 * @return the repeat
	 */
	public boolean isRepeat() {
		return repeat;
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

	public DataConnection getCnn() {
		return cnn;
	}

	public void setCnn(DataConnection cnn) {
		this.cnn = cnn;
	}
}
