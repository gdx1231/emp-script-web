package com.gdxsoft.web.job;


import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Flags.Flag;
import javax.mail.internet.MimeMessage;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.utils.UFile;
import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.easyweb.utils.Mail.Addr;
import com.gdxsoft.easyweb.utils.Mail.Attachment;
import com.gdxsoft.easyweb.utils.Mail.MailDecode;
import com.gdxsoft.easyweb.utils.msnet.MStr;

/**
 * 检查工作邮箱邮件
 * 
 * @author admin
 *
 */
public class JobMailScan extends JobBase {

	private String HOST = "192.168.1.10";
	private String USERNAME = "root@192.168.1.10";
	private String PASSWORD = "gdx1231";
	private String JOB_EXP = "@192.168.1.10";
	private int PORT = 110;
	private String _PathRoot;
	private String NAME_PREFIX = "pf";
	private boolean _IsStop = false;

	/**
	 * 检查工作邮箱邮件
	 * 
	 * @param cnn
	 * @param dbName
	 * @param host
	 * @param uid
	 * @param pwd
	 * @param jobExp
	 */
	public JobMailScan(DataConnection cnn, String dbName) {
		super(cnn, dbName);
		File f = new File(UPath.getPATH_IMG_CACHE());
		this._PathRoot = f.getAbsolutePath();

	}

	private void initPop3Parameters() throws Exception {
		HOST = super._RowSup.getCell("JOB_POP3_HOST").toString();
		USERNAME = super._RowSup.getCell("JOB_POP3_UID").toString();
		PASSWORD = super._RowSup.getCell("JOB_POP3_PWD").toString();
		PORT = super._RowSup.getCell("JOB_POP3_PORT").toInt();
		JOB_EXP = "@" + USERNAME.split("@")[1];
		NAME_PREFIX = USERNAME.split("@")[0];
	}

	public void checkJobMails() {
		Store store = null;

		try {
			initPop3Parameters();
		} catch (Exception e2) {
			this.log("initPop3Parameters，" + e2.getMessage());
			return;
		}
		Properties props = new Properties();

		try {
			props.setProperty("mail.pop3.disabletop", "true");
			Session session = Session.getInstance(props, null);
			// session.setDebug(true);
			store = session.getStore("pop3");
			this.log("OPEN POP3, " + HOST + ":" + PORT + "," + USERNAME);
			store.connect(HOST, PORT, USERNAME, PASSWORD);
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_WRITE);
			Message message[] = folder.getMessages();
			this.log("OPEN POP3, MSG=" + message.length);

			for (int i = message.length - 1; i >= 0; i--) {
				try {
					this.checkJobMail(message[i]);
					// 设置邮件删除标记
					message[i].setFlag(Flag.DELETED, true);

					if (this._IsStop) {
						break;
					}
				} catch (Exception e1) {
					this.log("JOBMAIL:" + e1.getMessage());
				}

			}
			// 删除邮件
			// folder.expunge();
			folder.close(true);

		} catch (Exception err) {
			this.log("JOBMAIL:" + err);
		} finally {
			if (store != null) {
				try {
					store.close();
				} catch (MessagingException e) {
					this.log("JOBMAIL:" + e.getMessage());
				}
			}
			super._Conn.close();
		}

	}

	private void checkJobMail(Message message) throws Exception {
		Calendar cal = Calendar.getInstance();

		String path0 = "/att_file/oa/scan_mail/" + cal.get(Calendar.YEAR) + "/"
				+ cal.get(Calendar.MONTH) + "/";
		String path = _PathRoot + path0;

		MailDecode pmm = new MailDecode((MimeMessage) message, path);
		String msgId = pmm.getMessageId();
		if (msgId == null) {
			this.log("MAIL:NOT found MsgId");
			return;
		}

		int id0 = msgId.hashCode();

		String sql1 = "select 1 a from oa_req where REQ_FROM_MSG_ID="
				+ id0;

		// 检查邮件是否被处理过
		DTTable tb11 = DTTable.getJdbcTable(sql1, super._Conn);

		if (tb11.getCount() > 0) {
			this.log("MAIL:" + msgId + " 已经处理");
			this._IsStop = true;
			return;
		}

		List<Addr> to = pmm.getTo();
		List<Addr> cc = pmm.getCc();

		// 查找任务编号
		int jobId = -1;
		for (int i = 0; i < to.size(); i++) {
			Integer id = getJobId(to.get(i).getEmail());
			if (id != null) {
				jobId = id;
				break;
			}
		}
		if (jobId == -1) {
			for (int i = 0; i < cc.size(); i++) {
				Integer id = getJobId(cc.get(i).getEmail());
				if (id != null) {
					jobId = id;
					break;
				}
			}
		}
		if (jobId == -1) {
			return;
		}

		// 查找发件人对应的ADM_ID;
		Addr from = pmm.getFrom();
		Integer[] admId = this.getSenderAdmId(from.getEmail());

		if (admId == null) {
			// 没有对应的ADM_ID; 错误邮件？或是多域名邮件
			return;

		}
		pmm.saveAttachments();
		String subject = pmm.getSubject();
		String bodyText=pmm.getBodyText();
		if(bodyText==null){
			bodyText="";
		}
		if(subject==null || subject.trim().length()==0){
			subject="NO TITLE";
		}
		String unid = Utils.getGuid();
		MStr sql = new MStr();
		String sqkl0 = "INSERT INTO OA_REQ(REQ_PID,REQ_MEMO, REQ_SUBJECT, REQ_CDATE, "
				+ "REQ_UNID, SUP_ID, REQ_ADM_ID,REQ_FROM_MSG_ID) ";
		sql.a(sqkl0);

		sql.a(" VALUES(");
		sql.a(jobId);
		sql.a(",'" + bodyText.replace("'", "''") + "','[邮件]"
				+ subject.replace("'", "''") + "'");
		sql.a(",getdate()");
		sql.a(",'" + unid + "'");
		sql.a("," + admId[1]);
		sql.a("," + admId[0]);
		sql.a("," + id0);
		sql.a(")");
		String sql2 = sql.toString();
		super._Conn.executeUpdateNoParameter(sql2);
		super._Conn.close();

		// 获取编号
		sql.setLength(0);
		sql.a("SELECT REQ_ID FROM OA_REQ WHERE REQ_UNID='" + unid + "'");
		DTTable tb = DTTable.getJdbcTable(sql2, super._Conn);
		super._Conn.close();

		String reqId = tb.getCell(0, 0).toString();

		//邮件HTML
		String cnt = pmm.getBodyHtml();
		if (cnt == null) {
			cnt = bodyText;
		}
		// 保存附件
		sql.setLength(0);
		for (int i = 0; i < pmm.getAtts().size(); i++) {
			Attachment att = pmm.getAtts().get(i);
			String path1 = att.getSavePathAndName();
			String url = path1
					.replace(_PathRoot, UPath.getPATH_IMG_CACHE_URL());
			String s = "INSERT INTO OA_FILE(OAF_UNID,OAF_NAME,OAF_EXT,OAF_SIZE,OAF_URL,SUP_ID,ADM_ID,OAF_REF,OAF_REF_ID)"
					+ " VALUES('"
					+ att.getSaveName().replace("'", "''")
					+ "','"
					+ att.getAttachName().replace("'", "''")
					+ "','"
					+ UFile.getFileExt(att.getSaveName()).replace("'", "''")
					+ "',"
					+ att.getSize()
					+ ",'"
					+ url.replace("'", "''")
					+ "',"
					+ admId[1]
					+ ","
					+ admId[0]
					+ ",'oa_req',"
					+ reqId
					+ ");";
			sql.al(s);
			if (att.isInline()) {
				// 替换内联标记
				String u1 = "@EWA.CP" + url;
				cnt = cnt.replace("cid:" + att.getInlineId(), u1);
			}
		}

		// 更新内容
		String s1 = "UPDATE OA_REQ SET REQ_CNT='"
				+ cnt.replace("'", "''") + "' WHERE REQ_ID=" + reqId;
		sql.al(s1);

		// 设置完成任务 2013-04-16 郭磊增加
		if (cnt.indexOf("#完成#") >= 0) {
			// 更新内容
			s1 = ";\n\n UPDATE OA_REQ SET REQ_STATUS='OA_REQ_OK', REQ_REV_OK_TIME=getdate() WHERE REQ_ID="
					+ jobId;
			sql.al(s1);
		}
		super._Conn.executeUpdateNoParameter(sql.toString());

		super._Conn.close();
	}

	/**
	 * 根据邮件查找用户名与供应商ID,返回数组，0-AdmId,1-SupId
	 * 
	 * @param email
	 * @return
	 */
	private Integer[] getSenderAdmId(String email) {
		String sql = "SELECT ADM_ID,SUP_ID FROM ADM_USER WHERE ADM_EMAIL='"
				+ email.replace("'", "''") + "'";

		DTTable tb = DTTable.getJdbcTable(sql, super._Conn);

		if (tb.getCount() > 0) {
			int adm_id = Integer.parseInt(tb.getCell(0, 0).toString());
			int sup_id = Integer.parseInt(tb.getCell(0, 1).toString());
			Integer[] rets = new Integer[2];
			rets[0] = adm_id;
			rets[1] = sup_id;
			return rets;
		} else {
			this.log("找不到" + email + "对应的用户");
			return null;
		}

	}

	private Integer getJobId(String email) {
		String s = email.toUpperCase().trim();

		if (!s.endsWith(JOB_EXP.trim().toUpperCase())) {
			return null;
		}
		if (!s.startsWith(NAME_PREFIX.toUpperCase() + "JOB-")) {
			return null;
		}

		s = s.replace(NAME_PREFIX.toUpperCase() + "JOB-", "");
		s = s.replace(JOB_EXP.trim().toUpperCase(), "");

		try {
			int id = Integer.parseInt(s);
			return id;
		} catch (Exception e) {
			return null;
		}
	}

}
