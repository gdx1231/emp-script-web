package com.gdxsoft.web.weixin;

import org.json.JSONObject;

import com.gdxsoft.easyweb.data.DTRow;
import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.weixin.WeiXinMsg;
import com.gdxsoft.weixin.WeiXinMsgEvent;
import com.gdxsoft.weixin.WeiXinMsgEventType;
import com.gdxsoft.weixin.WeiXinMsgRecv;
import com.gdxsoft.weixin.WeiXinMsgType;
import com.gdxsoft.weixin.WeiXinUser;

/**
 * 处理微信推送的事件
 * 
 * @author admin
 *
 */
public class WeiXinPostMsgHandle {

	private WeiXin weixin_;
	private RequestValue rv_;
	private DataConnection cnn_;

	public WeiXinPostMsgHandle(WeiXin weixin, RequestValue rv) {
		this.weixin_ = weixin;
		this.rv_ = rv;
	}

	public void handle(WeiXinMsg msg) {
		if (msg == null) {
			return;
		}
		this.cnn_ = new DataConnection();
		this.cnn_.setConfigName("");

		try {
			if (msg.isEvent()) { // 事件信息
				WeiXinMsgEvent evt = msg.getEvent();
				if (evt == null) {
					return;
				}
				String evtType = evt.getEvent();
				if (evtType == null) {
					return;
				} else if (evtType.equals(WeiXinMsgEventType.subscribe)) {
					this.handleSubScribe(evt);
					return;
				} else if (evtType.equals(WeiXinMsgEventType.unsubscribe)) {
					this.handleUnsubscribe(evt);
					return;
				} else if (evtType.equals(WeiXinMsgEventType.user_get_card)) {
					this.handleUserGetCard(evt);

				} else if (evtType.equals(WeiXinMsgEventType.CLICK)) {
					// 点击事件
					this.handleClick(evt);
				}
				this.updateSubScribe(evt.getFromUserName());
			} else {
				WeiXinMsgRecv recv = msg.getRecv();
				String recvType = recv.getMsgType();
				if (recvType == null) {
					return;
				}
				if (recvType.equals(WeiXinMsgType.text)) {
					this.handleRecvText(recv);
				}
				this.updateSubScribe(recv.getFromUserName());
			}
		} catch (Exception err) {
			System.err.println(err.getMessage());
		} finally {
			this.cnn_.close();
		}
	}

	/**
	 * 更新用户关注信息。由于关注信息的滞后性，当用户点击菜单或推送资料则表示用户已经关注
	 * 
	 * @param FromUserName
	 */
	private void updateSubScribe(String FromUserName) {
		try {
			if (FromUserName == null || FromUserName.trim().length() == 0) {
				return;
			}
			String usr_unid = this.getUser(FromUserName);
			if (usr_unid == null || usr_unid.trim().length() == 0) {
				return;
			}
			StringBuilder builder = new StringBuilder();

			// 更新微信用户表关注信息
			builder.append("update wx_user set IS_WEIXIN_SUBSCRIBE=1 where usr_unid='");
			builder.append(usr_unid.replace("'", "''"));
			builder.append("' and WX_CFG_NO='");
			builder.append(this.weixin_.getWxCfgNo().replace("'", "''"));
			builder.append("' and (IS_WEIXIN_SUBSCRIBE is null or IS_WEIXIN_SUBSCRIBE!='1')");

			builder.append("\n\n");
			this.cnn_.executeUpdateNoParameter(builder.toString());

			builder.setLength(0);
			// 更新用户表关注
			builder.append("update web_user set IS_WEIXIN_SUBSCRIBE=1 where usr_unid='");
			builder.append(usr_unid.replace("'", "''"));
			builder.append("' and (IS_WEIXIN_SUBSCRIBE is null or IS_WEIXIN_SUBSCRIBE!='1')");
			String sql = builder.toString();
			this.cnn_.executeUpdateNoParameter(sql);
		} catch (Exception e) {
			System.err.println("WeiXinPostMsgHandle.updateSubScribe: " + e.getMessage());
		}
	}

	/**
	 * 用户点击菜单
	 * 
	 * @param evt
	 */
	private void handleClick(WeiXinMsgEvent evt) {
		String key = evt.getEventKey();
		if (key == null || key.equals("")) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("select * from wx_but where BUT_UID='");
		sb.append(key.replace("'", "''"));
		sb.append("'  and WX_CFG_NO='");
		sb.append(this.weixin_.getWxCfgNo().replace("'", "''"));
		sb.append("'");
		String sql = sb.toString();
		DTTable tb = DTTable.getJdbcTable(sql, this.cnn_);

		if (tb.getCount() == 0) {
			return;
		}

		try {
			String BUT_MEMO = tb.getCell(0, "BUT_MEMO").toString();
			if (BUT_MEMO == null || BUT_MEMO.trim().length() == 0) {
				return;
			}
			this.sendMsg(BUT_MEMO, evt.getFromUserName());

		} catch (Exception e) {
			System.out.println("WeiXinPostMsgHandle.handleClick " + e.getMessage());
		}
	}

	/**
	 * 用户发来消息
	 * 
	 * @param recv
	 * @throws Exception
	 */
	private void handleRecvText(WeiXinMsgRecv recv) throws Exception {
		if (recv.getContent() == null) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM WX_REPLY_RULE WHERE RULE_STATUS='COM_YES' and WX_CFG_NO='");
		sb.append(this.weixin_.getWxCfgNo().replace("'", "''"));
		sb.append("' order by rule_ord");
		String sql = sb.toString();
		DTTable tb = DTTable.getJdbcTable(sql, this.cnn_);

		if (tb.getCount() == 0) {
			return;
		}
		DTRow rowDefault = null;
		boolean isKeyWord = false;
		for (int i = 0; i < tb.getCount(); i++) {
			String RULE_TYPE = tb.getCell(i, "RULE_TYPE").toString();
			if (RULE_TYPE.equals("WX_RULE_DEFAULT")) {
				rowDefault = tb.getRow(i);
				continue;
			}
			// 关键字
			if (RULE_TYPE.equals("WX_RULE_KEYWORDS")) {
				String RULE_MEMO = tb.getCell(i, "RULE_MEMO").toString();
				boolean isok = checkKeywords(RULE_MEMO, recv.getContent());

				if (isok) {
					this.sendMsg(tb.getRow(i), recv.getFromUserName());
					isKeyWord = true;
				}
			}
		}
		// 发送默认消息
		if (rowDefault != null && !isKeyWord) {
			this.sendMsg(rowDefault, recv.getFromUserName());
		}
	}

	/**
	 * 回复消息到用户
	 * 
	 * @param role
	 * @param open_id
	 * @throws Exception
	 */
	private void sendMsg(DTRow role, String open_id) throws Exception {
		String media_id = role.getCell("RULE_MEDIA_ID").toString();
		String RULE_CONTEXT = role.getCell("RULE_CONTEXT").toString();

		if (media_id == null || media_id.trim().length() == 0) {
			this.sendMsg(RULE_CONTEXT, open_id);
		} else {
			this.weixin_.getWeiXinCfg().sendWeiXinMsgPreviewNews(media_id, open_id);
		}

		// 关联红包发送
		try {
			String red_uid = role.getCell("RULE_RED_UID").toString();
			if (red_uid != null && red_uid.trim().length() > 0) {
				String usr_unid = this.getUser(open_id);
				this.sendRedPackage(usr_unid, red_uid);
			}
		} catch (Exception err) {
			System.err.println("WeiXinPostMsgHandle.sendRedPackage: " + err.getMessage());
		}
	}

	/**
	 * 发文字消息
	 * 
	 * @param content
	 * @param open_id
	 */
	private void sendMsg(String content, String open_id) {
		if (content == null || content.trim().length() == 0) {
			return;
		}
		boolean isok = this.weixin_.getWeiXinCfg().sendWeiXinServiceMsgText(open_id, content);
		if (!isok) {
			System.err.println("WeiXinPostMsgHandle.sendMsg: " + this.weixin_.getWeiXinCfg().getLastErr());
		}
	}

	/**
	 * 发送红包
	 * 
	 * @param usr_unid
	 *            用户unid
	 * @param red_uid
	 *            红包uid
	 */
	private void sendRedPackage(String usr_unid, String red_uid) {
		String userip = "127.0.0.1";
		if (this.rv_ != null) {
			userip = this.rv_.s("SYS_REMOTEIP");
		}
		JSONObject rst = this.weixin_.sendRedPackage(usr_unid, red_uid, userip);
		System.out.println(rst);
	}

	/**
	 * 检查关键字是否存在
	 * 
	 * @param keywords
	 * @param fromText
	 * @return
	 */
	private boolean checkKeywords(String keywords, String fromText) {
		if (keywords == null) {
			return false;
		}
		keywords = keywords.trim();
		if (keywords.length() == 0) {
			return false;
		}
		String[] keys = keywords.replace("，", ",").split(",");
		for (int i = 0; i < keys.length; i++) {
			String key = keys[i].trim().replace(" ", "");
			if (fromText.indexOf(key) >= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 用户获取卡券事件
	 * 
	 * @param evt
	 */
	private void handleUserGetCard(WeiXinMsgEvent evt) {

	}

	/**
	 * 取消关注事件
	 * 
	 * @param evt
	 */
	private void handleUnsubscribe(WeiXinMsgEvent evt) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("update WX_USER set IS_WEIXIN_SUBSCRIBE=0 where ");

		if (WeiXin.getDbType().equals("mssql")) {
			// AUTH_WEIXIN_ID 大小写敏感，不能用参数方式，因为参数是unicode，二进制转换会不对
			stringBuilder.append("AUTH_WEIXIN_ID = '");
			stringBuilder.append(evt.getFromUserName().replace("'", "''"));
			stringBuilder.append("'");
		} else {
			// AUTH_WEIXIN_ID 大小写敏感，mysql 设置字段未 utf8bin类型
			stringBuilder.append("AUTH_WEIXIN_ID = '");
			stringBuilder.append(evt.getFromUserName().replace("'", "''"));
			stringBuilder.append("'");
		}
		String sql = stringBuilder.toString();
		// 更新用户状态是不关注
		this.cnn_.executeUpdateNoParameter(sql);
	}

	/**
	 * 关注事件
	 * 
	 * @param msg
	 * @throws Exception
	 */
	private void handleSubScribe(WeiXinMsgEvent evt) throws Exception {
		// <xml><ToUserName><![CDATA[gh_cf9dff2e1bb8]]></ToUserName>
		// <FromUserName><![CDATA[o3UUauAgIctQnhcs9k9Z3OIqLtlM]]></FromUserName>
		// <CreateTime>1432304163</CreateTime>
		// <MsgType><![CDATA[event]]></MsgType>
		// <Event><![CDATA[subscribe]]></Event>
		// <EventKey><![CDATA[]]></EventKey> </xml>

		String usr_unid = this.getUser(evt.getFromUserName());
		if (usr_unid == null) { // 首次关注
			WeiXinUser user = this.weixin_.getWeiXinCfg().getWeiXinUserInfo(evt.getFromUserName());
			DTRow row = this.weixin_.newOrGetUser(user);
			usr_unid = row.getCell("usr_unid").toString();
		}

		// 更新用户状态是关注
		StringBuilder builder = new StringBuilder();
		builder.append("update WX_USER set IS_WEIXIN_SUBSCRIBE=1 where ");
		if (WeiXin.getDbType().equals("mssql")) {
			// AUTH_WEIXIN_ID 大小写敏感，不能用参数方式，因为参数是unicode，二进制转换会不对
			// alter table WX_USER ALTER COLUMN AUTH_WEIXIN_ID varchar(150)
			// COLLATE Chinese_PRC_CS_AI not null --字符集是Chinese_PRC_CS_AI，大小写敏感
			builder.append("  AUTH_WEIXIN_ID =  '");
			builder.append(evt.getFromUserName().replace("'", "''"));
			builder.append("' ");
		} else {
			// AUTH_WEIXIN_ID 大小写敏感，mysql 设置字段未 utf8bin类型
			builder.append("AUTH_WEIXIN_ID = '");
			builder.append(evt.getFromUserName().replace("'", "''"));
			builder.append("'");
		}
		String sql = builder.toString();

		this.cnn_.executeUpdateNoParameter(sql);

		// 自动回复消息
		StringBuilder sbReply = new StringBuilder();
		sbReply.append("SELECT * FROM WX_REPLY_RULE WHERE RULE_STATUS='COM_YES' and WX_CFG_NO='");
		sbReply.append(this.weixin_.getWxCfgNo().replace("'", "''"));
		sbReply.append("' and RULE_TYPE='WX_RULE_FOCUS' order by rule_ord");
		DTTable tb = DTTable.getJdbcTable(sbReply.toString(), this.cnn_);

		if (tb.getCount() == 0) {
			return;
		}
		for (int i = 0; i < tb.getCount(); i++) {
			this.sendMsg(tb.getRow(i), evt.getFromUserName());
		}
	}

	/**
	 * 获取 WEB_USER的USR_UNID
	 * 
	 * @param FromUserName
	 * @return
	 */
	private String getUser(String FromUserName) {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT a.USR_UNID FROM WX_USER a where ");

		if (WeiXin.getDbType().equals("mssql")) {
			// AUTH_WEIXIN_ID 大小写敏感，不能用参数方式，因为参数是unicode，二进制转换会不对
			// alter table WX_USER ALTER COLUMN AUTH_WEIXIN_ID varchar(150)
			// COLLATE Chinese_PRC_CS_AI not null
			// --字符集是Chinese_PRC_CS_AI，大小写敏感
			builder.append("AUTH_WEIXIN_ID = '");
			builder.append(FromUserName.replace("'", "''"));
			builder.append("'");
		} else {
			// AUTH_WEIXIN_ID 大小写敏感，mysql 设置字段未 utf8bin类型
			builder.append("AUTH_WEIXIN_ID = '");
			builder.append(FromUserName.replace("'", "''"));
			builder.append("'");
		}
		String sql = builder.toString();
		DTTable tbUser = DTTable.getJdbcTable(sql, "");

		if (tbUser.getCount() == 0) {
			return null;
		} else {
			return tbUser.getCell(0, 0).toString();
		}
	}

}
