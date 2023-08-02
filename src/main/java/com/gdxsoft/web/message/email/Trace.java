package com.gdxsoft.web.message.email;

import java.net.URLEncoder;
import java.util.Date;

import org.json.JSONObject;

import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UAes;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.web.dao.SysMessageInfo;
import com.gdxsoft.web.dao.SysMessageInfoDao;
import com.gdxsoft.web.dao.SysMessageInfoFaceback;
import com.gdxsoft.web.dao.SysMessageInfoFacebackDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * sys_message_info 的跟踪(表SYS_MESSAGE_INFO_FACEBACK)
 * 
 * @author admin
 *
 */
public class Trace {
	private static Logger LOGGER = LoggerFactory.getLogger(Trace.class);
	/**
	 * 透明5x5png图片
	 */
	public static String TRANSPARENT_IMG = "iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAABdJREFUeNpi/P//PwM6YGLAAigUBAgwADZQAwcsn51XAAAAAElFTkSuQmCC";

	private RequestValue rv_;
	private boolean isOk_;
	private JSONObject decode_;

	public Trace(RequestValue rv) {
		this.rv_ = rv;
	}

	/**
	 * 获取跟踪code
	 * 
	 * @param MESSAGE_ID
	 * @param FBK_ADM_ID
	 * @return
	 */
	public String getTraceCode(int MESSAGE_ID, int FBK_ADM_ID) {
		SysMessageInfoFacebackDao dao = new SysMessageInfoFacebackDao();
		SysMessageInfoFaceback o = dao.getRecord(MESSAGE_ID, FBK_ADM_ID);
		if (o == null) {
			o = new SysMessageInfoFaceback();
			o.setFbkAdmId(FBK_ADM_ID);
			o.setMessageId(MESSAGE_ID);
			o.setFbkUnid(Utils.getGuid());

			dao.newRecord(o);
		}

		JSONObject obj = new JSONObject();
		obj.put("MESSAGE_ID", MESSAGE_ID);
		obj.put("FBK_ADM_ID", FBK_ADM_ID);
		obj.put("FBK_UNID", o.getFbkUnid());
		// 加盐
		obj.put("SALT", Utils.getGuid());
		try {
			String code = UAes.getInstance().encrypt(obj.toString());
			code = URLEncoder.encode(code, "utf-8");
			return code;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	/**
	 * 获取回复地址
	 * 
	 * @param MESSAGE_ID
	 * @param FBK_ADM_ID
	 * @return
	 */
	public String getFacebackUrl(int MESSAGE_ID, int FBK_ADM_ID) {
		StringBuilder url = new StringBuilder(this.getTraceUrl(MESSAGE_ID, FBK_ADM_ID));
		url.append("&method=faceback");
		return url.toString();
	}

	/**
	 * 获取跟踪图片地址
	 * 
	 * @param MESSAGE_ID
	 * @param FBK_ADM_ID
	 * @return
	 */
	public String getTraceImageUrl(int MESSAGE_ID, int FBK_ADM_ID) {
		StringBuilder url = new StringBuilder(this.getTraceUrl(MESSAGE_ID, FBK_ADM_ID));
		url.append("&method=trace");
		return url.toString();
	}

	private String getTraceUrl(int MESSAGE_ID, int FBK_ADM_ID) {
		String code = this.getTraceCode(MESSAGE_ID, FBK_ADM_ID);
		// 限定https协议

		String url = this.rv_.s("EWA.HTTP").replace("http://", "https://");
		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 1);
		}
		url += this.rv_.s("EWA.CP");
		url += "/back_admin/common/sys-message-info-faceback.jsp?code=" + code;
		return url;
	}

	public boolean validCode(String code) {
		String ming;
		try {
			ming = UAes.getInstance().decrypt(code);
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return false;
		}
		try {
			JSONObject obj = new JSONObject(ming);
			this.decode_ = obj;

			return true;
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return false;
		}
	}

	/**
	 * 获取透明跟踪图(base64)
	 * 
	 * @param code
	 * @return
	 */
	public String getTransImage(String code) {
		this.isOk_ = false;

		if (this.decode_ == null) {
			boolean isValid = this.validCode(code);
			if (!isValid) {
				return TRANSPARENT_IMG;
			}
		}

		JSONObject obj = this.decode_;

		String FBK_UNID = obj.optString("FBK_UNID");
		int MESSAGE_ID = obj.optInt("MESSAGE_ID");
		int FBK_ADM_ID = obj.optInt("FBK_ADM_ID");

		boolean isok = this.recordShow(MESSAGE_ID, FBK_ADM_ID, FBK_UNID);
		this.isOk_ = isok;

		return TRANSPARENT_IMG;

	}

	/**
	 * 记录回复并跳转url（只记录第一次）
	 * 
	 * @param code
	 * @return
	 */
	public String recordAndJumpFaceback(String code) {
		this.isOk_ = false;

		if (this.decode_ == null) {
			boolean isValid = this.validCode(code);
			if (!isValid) {
				return null;
			}
		}

		JSONObject obj = this.decode_;

		String FBK_UNID = obj.optString("FBK_UNID");
		int MESSAGE_ID = obj.optInt("MESSAGE_ID");
		int FBK_ADM_ID = obj.optInt("FBK_ADM_ID");

		boolean isok = this.recordFaceback(MESSAGE_ID, FBK_ADM_ID, FBK_UNID);
		this.isOk_ = isok;

		SysMessageInfoDao dao = new SysMessageInfoDao();
		SysMessageInfo msg = dao.getRecord(MESSAGE_ID);

		String url = msg.getFacebackUrl();
		if (url != null) {
			if (url.indexOf("?") > 0) {
				url += "&";
			} else {
				url += "?";
			}
			url += "FBK_MESSAGE_ID=" + MESSAGE_ID + "&FBK_UNID=" + FBK_UNID;
		}
		return msg.getFacebackUrl();
	}

	/**
	 * 记录回复的结果（只记录第一次）
	 * 
	 * @param MESSAGE_ID
	 * @param FBK_ADM_ID
	 * @param FBK_UNID
	 * @return
	 */
	private boolean recordFaceback(int MESSAGE_ID, int FBK_ADM_ID, String FBK_UNID) {
		SysMessageInfoFacebackDao dao = new SysMessageInfoFacebackDao();
		SysMessageInfoFaceback o = dao.getRecord(MESSAGE_ID, FBK_ADM_ID);
		if (o == null || o.getFbkTime() != null || !o.getFbkUnid().equals(FBK_UNID)) {
			// 只记录第一次
			return false;
		}
		o.startRecordChanged();
		o.setFbkTime(new Date());
		o.setFbkIp(this.rv_.s("SYS_REMOTEIP"));
		o.setFbkAgent(this.rv_.s("SYS_USER_AGENT"));

		dao.updateRecord(o, o.getMapFieldChanged());
		return true;
	}

	/**
	 * 记录跟踪结果，用img跟踪 （只记录第一次）
	 * 
	 * @param MESSAGE_ID
	 * @param FBK_ADM_ID
	 * @param FBK_UNID
	 * @return
	 */
	private boolean recordShow(int MESSAGE_ID, int FBK_ADM_ID, String FBK_UNID) {
		SysMessageInfoFacebackDao dao = new SysMessageInfoFacebackDao();
		SysMessageInfoFaceback o = dao.getRecord(MESSAGE_ID, FBK_ADM_ID);
		if (o == null || o.getFbkShowTime() != null || !o.getFbkUnid().equals(FBK_UNID)) {
			// 只记录第一次
			return false;
		}
		o.startRecordChanged();
		o.setFbkShowTime(new Date());
		o.setFbkShowIp(this.rv_.s("SYS_REMOTEIP"));
		o.setFbkShowAgent(this.rv_.s("SYS_USER_AGENT"));
		o.setFbkShowReffer(this.rv_.s("SYS_REMOTE_REFERER"));

		dao.updateRecord(o, o.getMapFieldChanged());
		return true;
	}

	/**
	 * 是否执行成功
	 * 
	 * @return the isOk
	 */
	public boolean isOk() {
		return isOk_;
	}

	/**
	 * 获取解码的json
	 * 
	 * @return the decode
	 */
	public JSONObject getDecode() {
		return decode_;
	}

}
