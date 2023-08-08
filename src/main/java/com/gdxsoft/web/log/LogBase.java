package com.gdxsoft.web.log;

import java.util.Date;

import org.json.JSONObject;

import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.script.display.HtmlClass;
import com.gdxsoft.easyweb.script.display.ItemValues;
import com.gdxsoft.easyweb.script.display.SysParameters;
import com.gdxsoft.web.dao.AdmUser;
import com.gdxsoft.web.dao.LogMain;
import com.gdxsoft.web.dao.LogMainDao;
import com.gdxsoft.web.dao.SmsJob;
import com.gdxsoft.web.dao.SysMessageInfo;


public class LogBase {
	/**
	 * 短信签名
	 */
	public static String SMS_SIGN_NAME = "环球青少年大使计划";

	private static String[] HZ = { "０", "１", "２", "３", "４", "５", "６", "７", "８", "９" };
	public final static String MQ_TOPIC = "LOG_MAIN";
	String logSrc_ = "未初始化";
	String logSrcId0_ = "不知道呀";
	String logSrcId1_ = "";

	private RequestValue rv_;

	public LogBase() {
	}

	public LogBase(RequestValue rv) {
		rv_ = rv;
	}

	/**
	 * 创建 WEBSOCKET 的消息
	 * 
	 * @param title       标题
	 * @param broadcastId 广播 BROADCAST_ID
	 * @param link        打开的地址
	 * @return
	 */
	public JSONObject createWebSocketInfo(String title, String broadcastId, String link) {

		JSONObject jsonWebSocket = new JSONObject();
		jsonWebSocket.put("BROADCAST_ID", broadcastId);
		jsonWebSocket.put("MSG", title);

		jsonWebSocket.put("LINK", link);
		return jsonWebSocket;

	}

	/**
	 * 创建短信信息
	 * 
	 * @param from            来源用户
	 * @param to              目的用户
	 * @param smsTemplateCode 短信模板CODE
	 * @param smsJson         短信模板数据
	 * @param smsSignName     短信签名
	 * @param refId           来源编号
	 * @return
	 */
	public SmsJob createSmsInfo(AdmUser from, AdmUser to, String smsTemplateCode, JSONObject smsJson,
			String smsSignName, Object refId) {
		// 电话
		String mobile = to.getAdmMobile();
		if (mobile == null || mobile.trim().length() == 0) {
			// 电话不存在
			return null;
		}
		if (mobile.indexOf(",") > 0) {
			mobile = mobile.split(",")[0];
		} else if (mobile.indexOf(";") > 0) {
			mobile = mobile.split(";")[0];
		}

		// 替换汉字数字
		mobile = LogBase.replacePhoneHz(mobile);
		if (mobile.startsWith("+86")) {
			mobile = mobile.replace("+86", "");
		}
		mobile = mobile.replace(" ", "").replace("-", "").trim();

		SmsJob info = new SmsJob();
		// 阿里云短信模板 SMS_157680578 团款收款确认-修改版
		// ${GRP_HY_CODE}的团款，客户：${SUP_NAME}，团款：${GFM_SUM}元，财务审核结果：${GFM_PAY_TAG}。
		info.setSmsTemplateCode(smsTemplateCode);
		info.setSmsTemplateJson(smsJson.toString());
		// 短信签名
		info.setSmsSignName(smsSignName);

		info.setSmsPhones(mobile);

		info.setSmsProvider("ALI");

		info.setSmsJcdate(new Date());

		info.setSupId(from.getSupId());
		info.setAdmId(from.getAdmId());

		info.setSmsRefTable(logSrc_);
		if (refId != null) {
			info.setSmsRefId(refId.toString());
		}
		info.setSmsJstatus("NEW");

		return info;
	}

	/**
	 * 创建发送 Android 信息
	 * 
	 * @param from  来源用户
	 * @param to    目标用户
	 * @param title 标题
	 * @param body  正文
	 * @param refId 来源编号
	 * @return
	 */
	public SysMessageInfo createAndroidInfo(AdmUser from, AdmUser to, String title, String body, Object refId) {

		SysMessageInfo info = new SysMessageInfo();
		// info.setTargetEmail(ios_token);

		info.setTargetType("MSG_ANDROID");
		info.setCreateDate(new Date());

		info.setFromUsrId(from.getAdmId());
		info.setFromSupId(from.getSupId());

		info.setTargetUsrId(to.getAdmId());
		info.setTargetSupId(to.getSupId());

		info.setRefTable(logSrc_);
		if (refId != null) {
			info.setRefId(refId.toString());
		}

		info.setMessageTitle(title);
		info.setMessageTitle(body);

		info.setMessageStatus("NEW");
		
		// 未实现
		return null;

	}

	/**
	 * 创建发送 IOS 信息
	 * 
	 * @param from  来源用户
	 * @param to    目标用户
	 * @param title 标题
	 * @param body  正文
	 * @param refId 来源编号
	 * @return
	 */
	public SysMessageInfo createIosInfo(AdmUser from, AdmUser to, String title, String body, Object refId) {
		String ios_token = to.getIosToken();
		if (ios_token == null || ios_token.trim().length() == 0) {
			return null;
		}

		SysMessageInfo info = new SysMessageInfo();
		info.setTargetEmail(ios_token);

		info.setTargetType("MSG_IOS");
		info.setCreateDate(new Date());

		info.setFromUsrId(from.getAdmId());
		info.setFromSupId(from.getSupId());

		info.setTargetUsrId(to.getAdmId());
		info.setTargetSupId(to.getSupId());

		info.setRefTable(logSrc_);
		if (refId != null) {
			info.setRefId(refId.toString());
		}

		info.setMessageTitle(title);
		info.setMessageTitle(body);

		info.setMessageStatus("NEW");

		return info;
	}

	/**
	 * 创建邮件消息
	 * 
	 * @param from  来源用户
	 * @param to    目标用户
	 * @param title 标题
	 * @param body  正文
	 * @param refId 来源id
	 * @return
	 */
	public SysMessageInfo createEmailInfo(AdmUser from, AdmUser to, String title, String body, Object refId) {
		String to_email = to.getAdmEmail();
		if (to_email == null || to_email.trim().length() == 0) {
			return null;
		}

		SysMessageInfo info = new SysMessageInfo();
		info.setFromEmail(from.getAdmEmail());
		info.setTargetEmail(to_email);

		info.setTargetType("MSG_TYPE_EMAIL");
		info.setCreateDate(new Date());

		info.setFromUsrId(from.getAdmId());
		info.setFromSupId(from.getSupId());

		info.setTargetUsrId(to.getAdmId());
		info.setTargetSupId(to.getSupId());

		info.setRefTable(logSrc_);
		if (refId != null) {
			info.setRefId(refId.toString());
		}
		info.setMessageTitle(title);
		info.setMessageContent(body);
		return info;
	}

	/**
	 * 设置日志的 默认参数
	 * 
	 * @param l           日志对象
	 * @param LOG_SRC     来源
	 * @param LOG_SRC_ID0 来源字段名称0
	 * @param LOG_SRC_ID1 来源字段名称1
	 */
	public void setCommonParas(LogMain l) {
		// 操作者
		if (rv_.s("G_ADM_ID") != null) {
			int adm_id = rv_.getInt("G_ADM_ID");
			l.setAdmId(adm_id);
		}

		// 公司
		if (rv_.s("G_SUP_ID") != null) {
			int sup_id = rv_.getInt("G_SUP_ID");
			l.setSupId(sup_id);
		}

		// 数据来源
		l.setLogSrc(this.logSrc_);

		// 数据来源编号0
		l.setLogSrcId0(rv_.s(this.logSrcId0_));

		if (this.logSrcId1_ != null && this.logSrcId1_.trim().length() > 0) {
			// 数据来源编号1
			l.setLogSrcId1(rv_.s(this.logSrcId1_));
		}

		l.setLogTime(new Date());
		l.setLogIp(rv_.s("SYS_REMOTEIP"));

		// 浏览器代理头
		String paraLogAgent = rv_.s("SYS_USER_AGENT");
		if (paraLogAgent != null && paraLogAgent.length() > 2000) {
			paraLogAgent = paraLogAgent.substring(0, 2000);
		}
		l.setLogAgent(paraLogAgent);

		if (rv_.s("XMLNAME") != null) {
			l.setLogXmlname(rv_.s("XMLNAME"));
			l.setLogItemname(rv_.s("itemname"));
		}

		if (rv_.s("EWA_ACTION") != null) {
			l.setLogAction(rv_.s("EWA_ACTION"));
		}

	}

	public void add(LogMain l) {
		LogMainDao d = new LogMainDao();
		d.newRecord(l);
	}

	/**
	 * 替换参数
	 * 
	 * @param content
	 * @return
	 */
	public String replaceParameters(String content) {
		if (content == null || content.trim().length() == 0) {
			return content;
		}
		if (content.indexOf("@") == -1) {
			// 没有参数可替换
			return content;
		}
		ItemValues ivs = new ItemValues();
		HtmlClass htmlClass = new HtmlClass();
		SysParameters sps = new SysParameters();
		sps.setRequestValue(rv_);
		htmlClass.setSysParas(sps);
		ivs.setHtmlClass(htmlClass);
		htmlClass.setItemValues(ivs);

		String s = ivs.replaceParameters(content, false, true);
		return s;
	}

	   

	public static String replacePhoneHz(String s) {
		if (s == null) {
			return s;
		}
		s = s.trim();
		if (s.length() == 0) {
			return s;
		}

		for (int i = 0; i < HZ.length; i++) {
			s = s.replace(HZ[i], i + "");
		}
		return s;
	}

	/**
	 * 日志的来源
	 * 
	 * @return
	 */
	public String getLogSrc() {
		return logSrc_;
	}

	/**
	 * 日志的来源ID的字段名0
	 * 
	 * @return
	 */
	public String getLogSrcId0() {
		return logSrcId0_;
	}

	/**
	 * 日志的来源ID的字段名1，可以为🈳
	 * 
	 * @return
	 */
	public String getLogSrcId1() {
		return logSrcId1_;
	}

	public RequestValue getRv() {
		return rv_;
	}

	public void setRv(RequestValue rv) {
		this.rv_ = rv;
	}
}
