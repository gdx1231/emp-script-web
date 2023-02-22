package com.gdxsoft.web.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UFile;
import com.gdxsoft.easyweb.utils.UPath;
import javax.mail.*;
import javax.mail.internet.MimeMessage;

public class JobMailBounce extends JobBase {
	private String MailBoxPath;
	private String BounceMailDeliver;
	private String BounceMailNDR;

	public JobMailBounce() {

	}

	public JobMailBounce(DataConnection cnn, String dbName) {
		super(cnn, dbName);
	}

	public JobMailBounce(DataConnection cnn) {
		this._dbName = "";
		this._Conn = cnn;
	}

	public void start() {
		String isChk = UPath.getInitPara("BOUNCE_CHECK");
		if (isChk == null || !isChk.equalsIgnoreCase("true")) {
			this.log("退信检查参数（BOUNCE_CHECK）不等于true,不检查退信");
			return;
		}
		MailBoxPath = UPath.getInitPara("BOUNCE_MAILBOX");
		if (MailBoxPath == null) {
			this.log("请在配置文件中，initparas设置退信目录，BOUNCE_MAILBOX");
			return;
		}
		this.BounceMailDeliver = UPath.getInitPara("BOUNCE_MAIL_DELIVER");
		this.BounceMailNDR = UPath.getInitPara("BOUNCE_MAIL_NDR");
		if (this.BounceMailDeliver == null || this.BounceMailNDR == null) {
			this.log("请在配置文件中，initparas设置BOUNCE_MAIL_DELIVER，BOUNCE_MAIL_NDR参数");
			return;
		}

		File file = new File(MailBoxPath);
		File[] tempList = file.listFiles();
		for (int i = 0; i < tempList.length; i++) {
			try {
				checkBounceMail(tempList[i]);
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
		this.log("处理了" + tempList.length + "封退信");
	}

	public void checkBounceMail(File file) throws IOException, Exception {
		String filePath = file.getAbsolutePath();
		String fileName = file.getName();
		String bounceContent = null;
		String strMsgId = null;
		InputStream inMsg = null;
		try {
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			inMsg = new FileInputStream(filePath);
			Message msg = new MimeMessage(session, inMsg);
			bounceContent = msg.getContent().toString();
			String tmpCnt = bounceContent.replace("\r\n", "\r");
			String[] arrCnt = tmpCnt.split("\r");
			for (int i = arrCnt.length - 1; i >= 0; i--) {
				if (arrCnt[i].indexOf("X-EWA-MESSAGE-ID") == 0) {
					strMsgId = arrCnt[i].split(":")[1].trim();
					break;
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (inMsg != null) {
				inMsg.close();
			}
		}
		String fileTo = BounceMailNDR;
		if (strMsgId != null) {
			String[] arrMsgId = strMsgId.split("\\.");
			String sql = "select SUP_UNID,SUP_TAG,INIT_DATABASE from BAS_SUP_MAIN where SUP_TAG=@SUP_TAG";
			RequestValue rv = new RequestValue();
			rv.addValue("SUP_TAG", arrMsgId[0]);
			_Conn.setRequestValue(rv);
			DTTable dt = DTTable.getJdbcTable(sql, _Conn);
			_Conn.setRequestValue(null);
			if (dt.isOk() && dt.getCount() == 1) {
				String dbName = dt.getCell(0, "INIT_DATABASE").toString() == null ? ""
						: dt.getCell(0, "INIT_DATABASE").toString().trim();
				if (dbName.length() > 0) {
					int msgId = Integer.parseInt(arrMsgId[1]);
					if (createBounceRecord(dbName, bounceContent, msgId, fileName)) {
						fileTo = BounceMailDeliver;
					}
				}
			}
		}
		UFile.copyFile(filePath, fileTo + fileName);
		UFile.delete(filePath);
	}

	private boolean createBounceRecord(String dbName, String bounceContent, int msgId, String fileName) {
		this._dbName = dbName;
		boolean isOk = false;
		RequestValue rv = new RequestValue();
		rv.addValue("MESSAGE_ID", msgId);
		String sqlChk = "select MESSAGE_ID from SYS_MESSAGE_INFO "
				+ " WHERE MESSAGE_ID=@MESSAGE_ID AND TARGET_TYPE='MSG_TYPE_EMAIL'"
				+ " AND NOT EXISTS(SELECT * FROM SYS_MESSAGE_BOUNCE WHERE MESSAGE_ID=@MESSAGE_ID)";
		sqlChk = sqlChk.replace("{DB}", dbName);
		this._Conn.setRequestValue(rv);
		DTTable dt = DTTable.getJdbcTable(sqlChk, _Conn);
		if (!dt.isOk() || dt.getCount() != 1) {
			this.log(" createBounceRecord: 退信错误,MESSAGE_ID重复或不存在，DbName:" + dbName + ",MessageId:" + msgId
					+ ",FileName:" + fileName);
		} else {
			rv.addValue("BOUNCE_CONTENT", bounceContent);
			rv.addValue("BOUNCE_FILENAME", fileName);
			rv.addValue("BOUNCE_DBNAME", dbName);
			String sql = "insert into SYS_MESSAGE_BOUNCE(MESSAGE_ID,BOUNCE_CONTENT,CDATE,BOUNCE_FILENAME)";
			sql += " select MESSAGE_ID,@BOUNCE_CONTENT,getdate(),@BOUNCE_FILENAME from SYS_MESSAGE_INFO "
					+ " WHERE MESSAGE_ID=@MESSAGE_ID AND TARGET_TYPE='MSG_TYPE_EMAIL'"
					+ " AND NOT EXISTS(SELECT * FROM {DB}..SYS_MESSAGE_BOUNCE WHERE MESSAGE_ID=@MESSAGE_ID)";
			sql += ";update  SYS_MESSAGE_INFO set MESSAGE_STATUS='NDR' where MESSAGE_ID=@MESSAGE_ID";

			sql = sql.replace("{DB}", dbName);

			_Conn.setRequestValue(rv);
			if (_Conn.executeUpdate(sql)) {
				sql = "insert into  SYS_MESSAGE_BOUNCE_ALL(DBNAME,MESSAGE_ID,"
						+ "FROM_EMAIL,TARGET_EMAIL,MESSAGE_TITLE,MESSAGE_CONTENT,BOUNCE_CONTENT,"
						+ "CDATE,BOUNCE_FILENAME)";
				sql += " select @BOUNCE_DBNAME,MESSAGE_ID,FROM_EMAIL,TARGET_EMAIL,"
						+ "MESSAGE_TITLE,MESSAGE_CONTENT,@BOUNCE_CONTENT,getdate(),@BOUNCE_FILENAME "
						+ "from  SYS_MESSAGE_INFO " + " WHERE MESSAGE_ID=@MESSAGE_ID AND TARGET_TYPE='MSG_TYPE_EMAIL'"
						+ " AND NOT EXISTS(SELECT * FROM OneWorld_Main_data..SYS_MESSAGE_BOUNCE_ALL "
						+ " WHERE MESSAGE_ID=@MESSAGE_ID and DBNAME=@BOUNCE_DBNAME)";
				sql = sql.replace("{DB}", dbName);
				_Conn.executeUpdate(sql);
				isOk = true;
			} else {
				this.log(" createBounceRecord: 退信错误之SQL执行错误，dbName=" + dbName + ",MesageId=" + msgId + ",fileName="
						+ fileName);
				isOk = false;
			}
		}
		_Conn.setRequestValue(null);
		this.log(" createBounceRecord: 退信,MessageId:" + msgId + ",FileName:" + fileName);
		this._dbName = "";
		return isOk;
	}

}
