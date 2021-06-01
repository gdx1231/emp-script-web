package com.gdxsoft.chatRoom;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.HtmlControl;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.web.websocket.IHandleMsg;
import com.gdxsoft.web.websocket.IndexWebSocket;
import com.gdxsoft.web.websocket.IndexWebSocketContainer;

/**
 * WebScocket 话题消息的处理
 *
 */
public class HandleChatImpl extends Thread implements IHandleMsg {
	private static ConcurrentHashMap<String, ConcurrentHashMap<String, Boolean>> TOPIC_USERS = new ConcurrentHashMap<String, ConcurrentHashMap<String, Boolean>>();
	private static Logger LOGGER = Logger.getLogger(HandleChatImpl.class);
	public final static String METHOD = "chat";

	// 推送帖子列表消息标志
	public final static String CHAT_BROAD_MSG_ID = "chat_broad_msg";
	// 广播删除帖子
	public final static String CHAT_BROAD_DELETE_ID = "chat_broad_delete_it";
	private IndexWebSocket socket_;
	private JSONObject command_;
	private String action_;
	// 话题的UNID
	private String topicUnid_;

	/**
	 * 初始化对象
	 * 
	 * @param socket  IndexWebSocket
	 * @param command 调用的命令
	 */
	public HandleChatImpl(IndexWebSocket socket, JSONObject command) {
		this.socket_ = socket;
		this.command_ = command;
		this.action_ = command_.optString("ACTION");
		if (this.action_ == null) {
			this.action_ = "";
		}
		this.topicUnid_ = command_.optString("BBS_TOP_UNID");
		if (this.topicUnid_ == null) {
			this.topicUnid_ = "";
		}

	}

	@Override
	public void run() {
		JSONObject result = null;
		try {
			result = this.doAction();
		} catch (Exception err) {
			result = new JSONObject();
			result.put("RST", false);
			result.put("ERR", err.getMessage());
			LOGGER.error(err);
		}
		this.socket_.sendToClient(result.toString());
	}

	private JSONObject doAction() {
		JSONObject result = new JSONObject();
		result.put("METHOD", METHOD);
		// 客户端提交的ID
		result.put("ID", this.command_.optString("ID"));
		result.put("ACTION", this.action_);
		result.put("SOCKET_ID", this.socket_.getUnid());
		try {
			if (this.action_.equalsIgnoreCase("post")) {
				this.doActionRegister();
				String html = this.doActionPost();
				result.put("HTML", html);
			} else if (this.action_.equalsIgnoreCase("Register")) {
				String html = this.doActionRegister();
				result.put("HTML", html);
			} else if (this.action_.equalsIgnoreCase("UnRegister")) {
				String html = this.doActionUnRegister();
				result.put("HTML", html);
			} else if (this.action_.equalsIgnoreCase("monitor")) {
				// 用例：当上传完图片后刷新新的数据
				this.doActionRegister();

				String html = this.doActionMonitor();
				JSONArray msgs = new JSONArray(html);
				this.boradMessage(msgs, CHAT_BROAD_MSG_ID);
				result.put("MSG", "消息在广播中获取");
			} else if (this.action_.equalsIgnoreCase("deleteit")) {
				// 删除内容
				JSONObject resultDo = this.doActionDeleteIt();
				if (!resultDo.optBoolean("RST")) {
					result.put("RST", false);
					result.put("ERR", resultDo.optString("ERR"));
					return result;
				}
			}
			result.put("RST", true);
		} catch (Exception err) {
			result.put("RST", false);
			result.put("ERR", err.getMessage());
		}
		return result;
	}

	/**
	 * 删除帖子
	 * 
	 * @return
	 */
	private JSONObject doActionDeleteIt() {
		RequestValue rv = this.getCloneRv();
		JSONObject obj = new JSONObject();
		String sql1 = "SELECT BBS_TOP_UNID A FROM bbs_topic WHERE BBS_TOP_UNID=@BBS_TOP_UNID_DELETE AND BBS_TOP_LVL>1 AND USR_UNID=@G_ADM_UNID";

		DTTable tb = DTTable.getJdbcTable(sql1, rv);
		String BBS_TOP_UNID;
		if (tb.getCount() == 0) {
			obj.put("RST", false);
			obj.put("ERR", "您无权删除此内容");
			return obj;
		}

		// 执行删除
		BBS_TOP_UNID = tb.getCell(0, 0).toString();
		String sql = "UPDATE bbs_topic SET BBS_TOP_STATUS='DEL' WHERE BBS_TOP_UNID=@BBS_TOP_UNID_DELETE AND BBS_TOP_LVL>1 AND USR_UNID=@G_ADM_UNID";
		DataConnection.updateAndClose(sql, "", rv);
		obj.put("RST", true);
		obj.put("BBS_TOP_UNID", BBS_TOP_UNID);

		// 广播删除帖子的消息
		JSONObject broadcastDelete = new JSONObject();
		broadcastDelete.put("BBS_TOP_UNID_DELETE", BBS_TOP_UNID);
		JSONArray msgs = new JSONArray();
		msgs.put(broadcastDelete);
		this.boradMessage(msgs, CHAT_BROAD_DELETE_ID);

		return obj;
	}

	private String doActionMonitor() {
		String itemname = "BBS_TOPIC.LF.relay";
		String paras = "EWA_AJAX=JSON&EWA_PAGECUR=1&EWA_PAGESIZE=20";
		HtmlControl ht = this.getHtmlControl(itemname, paras);
		return ht.getHtml();
	}

	/**
	 * 提交话题内容
	 * 
	 * @return
	 */
	private String doActionPost() {
		String paras = "EWA_ACTION=OnPagePost&EWA_MTYPE=N&ewa_ajax=json";
		String itemname = "BBS_TOPIC.F.relay";
		HtmlControl ht = this.getHtmlControl(itemname, paras);
		String it_bbs_top_unid = ht.getRequestValue().s("sys_unid");

		// 广播刚才创建的消息内容
		this.doActionPostBroad(it_bbs_top_unid);

		return ht.getHtml();
	}

	/**
	 * 广播刚才创建的消息内容
	 * 
	 * @param it_bbs_top_unid
	 */
	private void doActionPostBroad(String it_bbs_top_unid) {
		if (this.topicUnid_ == null || this.topicUnid_.trim().length() == 0) {
			return;
		}

		if (!TOPIC_USERS.containsKey(this.topicUnid_)) {
			return;
		}

		String itemname = "BBS_TOPIC.F.relay";
		String paras1 = "EWA_ACTION=OnPageLoad&EWA_MTYPE=M&ewa_ajax=json&IT_BBS_TOP_UNID=" + it_bbs_top_unid;
		HtmlControl ht1 = this.getHtmlControl(itemname, paras1);
		JSONArray msgs = new JSONArray(ht1.getHtml());
		this.boradMessage(msgs, CHAT_BROAD_MSG_ID);
	}

	/**
	 * 广播消息列表
	 * 
	 * @param msgs
	 */
	private void boradMessage(JSONArray msgs, String broadcastId) {
		if (msgs.length() == 0) {
			return;
		}

		ConcurrentHashMap<String, Boolean> map = TOPIC_USERS.get(this.topicUnid_);
		for (String key : map.keySet()) {
			// 从真正的容器中找到socket
			IndexWebSocket socket = IndexWebSocketContainer.getSocketByUnid(key);
			if (socket == null) {
				map.remove(key);
				continue;
			}

			JSONObject result = new JSONObject();
			// js用于处理广播消息的方法id
			result.put("BROADCAST_ID", broadcastId);

			String adm_unid = socket.getRv().s("G_ADM_UNID");
			if (adm_unid == null) {
				adm_unid = "";
			}
			
			String usr_unid = socket.getRv().s("G_USR_UNID");
			if (usr_unid == null) {
				  usr_unid = socket.getRv().s("G_WEB_USR_UNID");
			}
			if (usr_unid == null) {
				  usr_unid = "";
			}
			for (int i = 0; i < msgs.length(); i++) {
				JSONObject item = msgs.getJSONObject(i);
				String bbsUsrUnid = item.optString("USR_UNID");
				String bbsTopHot =  item.optString("BBS_TOP_HOT");
				
				String me = "other";
				if("CUSTOMER".equals(bbsTopHot)) {
					if(usr_unid.equals(bbsUsrUnid)) {
						me = "me";
					}
				} else {
					if(adm_unid.equals(bbsUsrUnid)) {
						me = "me";
					}
				}
				
				item.put("ME", me); // 设置为自己还是别人
				item.put("_G_ADM_UNID", adm_unid);
				item.put("_G_USR_UNID", usr_unid);
				item.put("_", "me 由 HandleChatImpl 创建");
			}

			result.put("LIST", msgs);
			socket.sendToClient(result.toString());
		}
	}

	/**
	 * socket 注册到某个主题下
	 * 
	 * @return
	 * @throws Exception
	 */
	private String doActionRegister() throws Exception {
		if (this.topicUnid_ == null || this.topicUnid_.trim().length() == 0) {
			throw new Exception("Topic不存在");
		}
		ConcurrentHashMap<String, Boolean> map;
		if (!TOPIC_USERS.containsKey(this.topicUnid_)) {
			map = new ConcurrentHashMap<String, Boolean>();
			map.put(this.socket_.getUnid(), true);
			TOPIC_USERS.put(this.topicUnid_, map);

			return "registered";
		} else {
			map = TOPIC_USERS.get(this.topicUnid_);
			if (!map.containsKey(this.socket_.getUnid())) {
				map.put(this.socket_.getUnid(), true);
				return "registered";
			} else {
				return "alreday registered";
			}
		}
	}

	/**
	 * 结束 socket在某个主题下的注册
	 * 
	 * @return
	 * @throws Exception
	 */
	private String doActionUnRegister() throws Exception {
		if (this.topicUnid_ == null || this.topicUnid_.trim().length() == 0) {
			throw new Exception("Topic不存在");
		}
		if (!TOPIC_USERS.containsKey(this.topicUnid_)) {
			throw new Exception("Topic容器不存在");
		}
		ConcurrentHashMap<String, Boolean> map = TOPIC_USERS.get(this.topicUnid_);
		if (map.containsKey(this.socket_.getUnid())) {
			map.remove(this.socket_.getUnid());
			return "unregistered";
		} else {
			return "no registered";
		}
	}

	private HtmlControl getHtmlControl(String itemName, String params) {
		HtmlControl ht_crm = new HtmlControl();
		String xmlname = "/2014_rob/app-2017/bbs.xml";

		RequestValue rv_clone = this.getCloneRv();

		ht_crm.init(xmlname, itemName, params, rv_clone, null);

		return ht_crm;
	}

	/**
	 * 获取Rv，因为Socket是长连接，因此RequetValue在首次连接就固定下来，<br>
	 * 因此每次操作需要将Socket带的Rv克隆出来，并附加command参数
	 * 
	 * @return
	 */
	private RequestValue getCloneRv() {
		RequestValue rv_clone = this.socket_.getRv().clone();
		Iterator<?> it = this.command_.keys();
		while (it.hasNext()) {
			String key = it.next().toString();
			String val = this.command_.optString(key);
			rv_clone.getPageValues().remove(key);
			rv_clone.addValue(key, val);
		}

		rv_clone.resetDateTime();
		rv_clone.resetSysUnid();
		return rv_clone;
	}

	@Override
	public String getMethod() {
		return METHOD;
	}

}
