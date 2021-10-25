/**
 * 
 */
package com.gdxsoft.web.weixin;

import org.json.JSONObject;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.RequestValue;

/**
 * 微信绑定用户类
 * 
 * @author admin
 *
 */
public class WeiXinBindCustomer {

	/**
	 * 微信绑定用户类
	 */
	public WeiXinBindCustomer() {
	}

	/**
	 * 尝试发红包
	 * 
	 * @param rv
	 * @return
	 * @throws Exception
	 */
	public JSONObject doTryRedPackage(RequestValue rv) throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("RST", false);
		// 获取微信红包数据 RED_TYPE='WX_RED_TYPE_BIND'，并且不在自动回复规则关联的红包中
		String sql = "select * from wx_redpack_main where RED_TYPE='WX_RED_TYPE_BIND' and red_status='com_yes'"
				+ " and wx_cfg_no=@wx_cfg_no and sup_id=@g_sup_id and RED_START<=getdate() and RED_END>=getdate()"
				+ " and not RED_UID in (select  a.RULE_RED_UID from WX_REPLY_RULE a where RULE_RED_UID is not null)";

		DTTable tbred = DTTable.getJdbcTable(sql, "", rv);
		String red_unid = "";
		if (tbred.getCount() > 0) {
			red_unid = tbred.getCell(0, "red_uid").toString();
			obj.put("RST", true);
			String url = "weixin_red_package_yyy.jsp?wx_cfg_no=" + rv.s("wx_cfg_no") + "&red_uid=" + red_unid;
			obj.put("URL", url);
		} else {
			obj.put("ERR", "运气不好，活动已经结束:)");
		}

		return obj;
	}

	/**
	 * 查询绑定数据
	 * 
	 * @param rv
	 * @return
	 * @throws Exception
	 */
	public JSONObject doAuth(RequestValue rv) throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("RST", false);
		String rzfs = rv.s("rzfs");
		if (rzfs == null || rzfs.trim().length() == 0) {

			obj.put("ERR", "认证方式没有选择");
			return obj;
		}

		String name = rv.s("name");
		if (name == null || name.trim().length() == 0) {
			obj.put("RST", false);
			obj.put("ERR", "真实姓名未提供");
			return obj;
		}

		String rz = rv.s("rz");
		if (rz == null || rz.trim().length() == 0) {
			obj.put("RST", false);
			obj.put("ERR", "内容未提供");
			return obj;
		}

		String sql = "select A.GRP_COS_ID, A.GRP_ID, A.COS_ID"
				+ ", C.GRP_NAME, C.GRP_HY_CODE, DBO.FN_YMD(C .GRP_SDATE) DT from grp_costumer A "
				+ " LEFT JOIN VIS_TAB_BASE B ON A.BAS_VIS_ID=B.VIS_ID " + " INNER JOIN GRP_MAIN C ON A.GRP_ID=C.GRP_ID "
				+ " where GRP_STATE='USED' AND A.sup_id=@g_sup_id and cos_name=@name and ";
		if (rzfs.equals("sfz")) {// 身份证
			sql += " (A.ID_CARD=@rz OR B.BAS_APP_NATIONAL_ID=@RZ) ";
		} else if (rzfs.equals("sfz6")) {// 身份证后6位
			sql += " (right(A.ID_CARD,6)=@rz OR right(B.BAS_APP_NATIONAL_ID,6)=@RZ) ";
		} else if (rzfs.equals("hz")) {// 护照
			sql += " B.BAS_PPT_NUM=@rz";
		} else if (rzfs.equals("dh")) {// 电话
			sql += " B.BAS_MOBILE_TEL=@rz";
		} else {// 指定的模式错误
			sql += " 1>2 ";
		}
		sql += " order by a.grp_cos_id desc";

		DTTable tbUser = DTTable.getJdbcTable(sql, "", rv);

		if (tbUser.getCount() == 0) {
			obj.put("RST", false);
			obj.put("ERR", "找不到用户信息");
			return obj;
		}
		// int grp_id = tbUser.getCell(0, "GRP_ID").toInt();
		// String sqlGrp = "select * from grp_main where grp_id=" + grp_id
		// + " and sup_id=@g_sup_id";
		obj.put("RST", true);
		obj.put("DATA", tbUser.toJSONArray().getJSONObject(0));

		obj.put("rzfs", rzfs);
		obj.put("rz", rz);
		obj.put("name", name);
		rv.getSession().setAttribute("WEIXIN_BIND_TK", obj);

		return obj;
	}

	/**
	 * 绑定微信用户与团用户
	 * 
	 * @param rv
	 * @return
	 */
	public JSONObject doBind(RequestValue rv) throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("RST", false);

		if (rv.getSession().getAttribute("WEIXIN_BIND_TK") == null) {
			obj.put("ERR", "数据丢失，请关闭后重新认证");
			System.out.println(obj);
			return obj;
		}

		if (rv.s("sf") == null || rv.s("sf").trim().length() == 0) {
			obj.put("ERR", "未指定和绑定用户之间的关系");
			System.out.println(obj);
			return obj;
		}

		JSONObject data = (JSONObject) rv.getSession().getAttribute("WEIXIN_BIND_TK");

		StringBuilder builder = new StringBuilder();
		builder.append("SELECT b.usr_id,b.usr_pic,b.usr_addr FROM WX_USER ");
		builder.append(" 	A inner join web_user b on a.usr_unid=b.usr_unid ");
		builder.append(" where A.WX_CFG_NO=@WX_CFG_NO ");
		builder.append("  	AND a.AUTH_WEIXIN_ID = '");
		builder.append(rv.s("G_WX_OPEN_ID").replace("'", "''"));
		builder.append("' and a.usr_unid=@g_usr_unid ");
		String sql = builder.toString();

		DTTable tbUserOld = DTTable.getJdbcTable(sql, "", rv);

		if (tbUserOld.getCount() == 0) {
			obj.put("ERR", "当前用户信息不存在");
			System.out.println(obj);
			return obj;
		}

		String sql2 = "select USR_ID from web_user where usr_id=" + data.getJSONObject("DATA").getInt("COS_ID");

		DTTable tbUserNew = DTTable.getJdbcTable(sql2, "", rv);

		if (tbUserNew.getCount() == 0) {
			obj.put("ERR", "要绑定用户不存在");
			System.out.println(obj);
			return obj;
		}
		// 当前关联的用户
		int old_usr_id = tbUserOld.getCell(0, 0).toInt();

		// 团用户
		int new_usr_id = tbUserNew.getCell(0, 0).toInt();

		if (old_usr_id == new_usr_id) {
			obj.put("ERR", "已经绑定，无需重新绑定");
			System.out.println(obj);
			return obj;
		}

		rv.addValue("BIND_JSON", data.toString());
		String updateSql;
		if (rv.s("sf").equals("USR_REL_ZJ")) {
			updateSql = this.bindSelf(old_usr_id, new_usr_id, rv);
		} else {
			updateSql = this.bindOther(old_usr_id, new_usr_id, rv.s("sf"), rv);
		}
		DataConnection cnn = new DataConnection();
		cnn.setConfigName("");
		cnn.setRequestValue(rv);
		try {
			cnn.transBegin();
			cnn.executeUpdate(updateSql);
			if (cnn.getErrorMsg() != null) {
				cnn.transRollback();
				obj.put("ERR", cnn.getErrorMsg());
				System.out.println(obj);
			} else {
				cnn.transCommit();
				obj.put("RST", true);
				obj.put("new_user_id", new_usr_id);

			}
		} catch (Exception err) {
			obj.put("ERR", "系统错误" + err.getMessage());
			System.out.println(err.getMessage());
		} finally {
			cnn.close();
		}

		return obj;
	}

	/**
	 * 绑定其它关系
	 * 
	 * @param old_usr_id
	 *            当前微信关联用户
	 * @param new_usr_id
	 *            团用户
	 * @param rel_tag
	 *            关系
	 * @param rv
	 * @return
	 */
	private String bindOther(int old_usr_id, int new_usr_id, String rel_tag, RequestValue rv) {
		StringBuilder update = new StringBuilder();
		String sqlCheckExists = "select 1 a from WEB_USER_RELATION where USR_ID1=" + old_usr_id + " and USR_ID2="
				+ new_usr_id;
		DTTable tbExists = DTTable.getJdbcTable(sqlCheckExists);

		// 用户关系表数据
		if (tbExists.getCount() == 0) {
			update.append("INSERT INTO WEB_USER_RELATION(USR_ID1, USR_ID2, REL_TAG)VALUES(" + old_usr_id + ", "
					+ new_usr_id + ", '" + rel_tag.replace("'", "''") + "')");
		} else {
			update.append("UPDATE WEB_USER_RELATION SET   REL_TAG ='" + rel_tag.replace("'", "''") + "' WHERE USR_ID1="
					+ old_usr_id + " AND USR_ID2=" + new_usr_id);
		}
		// 记录变更过程
		StringBuilder sb = new StringBuilder();
		sb.append("\n\n UPDATE WX_USER SET\n");
		sb.append("   BIND_STATUS = 'COM_YES',\n");
		sb.append("   BIND_USR_ID = " + old_usr_id + ",\n");
		sb.append("   BIND_DATE = @SYS_DATE,\n");
		sb.append("   BIND_REMOVE_USR_UNID = '其它绑定',\n");
		sb.append("   BIND_IP = @SYS_REMOTEIP,\n");
		sb.append("   BIND_JSON = @BIND_JSON\n");
		sb.append("WHERE WX_CFG_NO=@WX_CFG_NO AND  AUTH_WEIXIN_ID =  '");
		sb.append(rv.s("G_WX_OPEN_ID").replace("'", "''") + "'  ");
		sb.append("  and USR_UNID=@G_USR_UNID\n");

		update.append(sb.toString());
		return update.toString();
	}

	/**
	 * 绑定自己
	 * 
	 * @param old_usr_id
	 *            当前微信关联用户
	 * @param new_usr_id
	 *            团用户
	 * @param rv
	 * @return
	 */
	private String bindSelf(int old_usr_id, int new_usr_id, RequestValue rv) {
		StringBuilder update = new StringBuilder();

		// 将原来的web_user的unid变更
		update.append("update web_user set SUP_ID=@G_SUP_ID, usr_unid=@sys_unid where usr_id=" + old_usr_id);
		// 当前的web_user的unid变换为当前的 g_unid
		update.append("\n\n update web_user set usr_unid=@g_usr_unid where usr_id=" + new_usr_id);

		// 将旧用户信息复制到新用户
		StringBuilder sb1 = new StringBuilder();
		sb1.append("\n\nUPDATE WEB_USER  SET\n");
		sb1.append(
				"   USR_LID = ISNULL(case when WEB_USER.USR_LID='' then null else WEB_USER.USR_LID end, A.USR_LID),\n");
		sb1.append(
				"   USR_PWD = ISNULL(case when WEB_USER.USR_PWD='' then null else WEB_USER.USR_PWD end, A.USR_PWD),\n");
		sb1.append("   USR_TAG = ISNULL(WEB_USER.USR_TAG, A.USR_TAG),\n");
		sb1.append("   ARE_ID = ISNULL(WEB_USER.ARE_ID, A.ARE_ID),\n");
		sb1.append("   USR_NAME = ISNULL(WEB_USER.USR_NAME, A.USR_NAME),\n");
		sb1.append("   USR_IDCARD = ISNULL(WEB_USER.USR_IDCARD, A.USR_IDCARD),\n");
		sb1.append("   USR_TELE = ISNULL(WEB_USER.USR_TELE, A.USR_TELE),\n");
		sb1.append("   USR_EMAIL = ISNULL(WEB_USER.USR_EMAIL, A.USR_EMAIL),\n");
		sb1.append("   USR_QQ = ISNULL(WEB_USER.USR_QQ, A.USR_QQ),\n");
		sb1.append("   USR_MSN = ISNULL(WEB_USER.USR_MSN, A.USR_MSN),\n");
		sb1.append("   USR_ICQ = ISNULL(WEB_USER.USR_ICQ, A.USR_ICQ),\n");
		sb1.append("   USR_MOBILE = ISNULL(WEB_USER.USR_MOBILE, A.USR_MOBILE),\n");
		sb1.append("   USR_ADDR = ISNULL(WEB_USER.USR_ADDR, A.USR_ADDR),\n");
		sb1.append("   USR_LNUM = ISNULL(WEB_USER.USR_LNUM, A.USR_LNUM),\n");
		sb1.append("   USR_COMPANY = ISNULL(WEB_USER.USR_COMPANY, A.USR_COMPANY),\n");
		sb1.append("   USR_FROM = ISNULL(WEB_USER.USR_FROM, A.USR_FROM),\n");
		sb1.append("   USR_TITLE = ISNULL(WEB_USER.USR_TITLE, A.USR_TITLE),\n");
		sb1.append("   USR_PIC = ISNULL(WEB_USER.USR_PIC, A.USR_PIC),\n");
		sb1.append("   USR_PASSPORT = ISNULL(WEB_USER.USR_PASSPORT, A.USR_PASSPORT),\n");
		sb1.append("   SIT_ID = ISNULL(WEB_USER.SIT_ID, A.SIT_ID),\n");
		sb1.append("   SUP_ID = ISNULL(WEB_USER.SUP_ID, A.SUP_ID),\n");
		sb1.append("   USR_PID = ISNULL(WEB_USER.USR_PID, A.USR_PID),\n");
		sb1.append("   RECOMMEND_NAME = ISNULL(WEB_USER.RECOMMEND_NAME, A.RECOMMEND_NAME),\n");
		sb1.append("   RECOMMEND_PHONE = ISNULL(WEB_USER.RECOMMEND_PHONE, A.RECOMMEND_PHONE),\n");
		sb1.append("   RECOMMEND_VIP = ISNULL(WEB_USER.RECOMMEND_VIP, A.RECOMMEND_VIP),\n");
		sb1.append("   USR_SEX = ISNULL(WEB_USER.USR_SEX, A.USR_SEX),\n");
		sb1.append("   USR_NAME_EN = ISNULL(WEB_USER.USR_NAME_EN, A.USR_NAME_EN),\n");
		sb1.append("   USR_PARENT_NAME = ISNULL(WEB_USER.USR_PARENT_NAME, A.USR_PARENT_NAME),\n");
		sb1.append("   USR_DBO = ISNULL(WEB_USER.USR_DBO, A.USR_DBO),\n");
		sb1.append("   USR_PIC_BIN = ISNULL(WEB_USER.USR_PIC_BIN, A.USR_PIC_BIN),\n");
		sb1.append("   BBS_NICK_NAME = ISNULL(WEB_USER.BBS_NICK_NAME, A.BBS_NICK_NAME),\n");
		sb1.append("   BBS_SYS_FACE = ISNULL(WEB_USER.BBS_SYS_FACE, A.BBS_SYS_FACE),\n");
		sb1.append("   BBS_FACE_METHOD = ISNULL(WEB_USER.BBS_FACE_METHOD, A.BBS_FACE_METHOD),\n");
		sb1.append("   BBS_POST_NUM = ISNULL(WEB_USER.BBS_POST_NUM, A.BBS_POST_NUM),\n");
		sb1.append("   BBS_REPLY_NUM = ISNULL(WEB_USER.BBS_REPLY_NUM, A.BBS_REPLY_NUM),\n");
		sb1.append("   BBS_SCORE_NUM = ISNULL(WEB_USER.BBS_SCORE_NUM, A.BBS_SCORE_NUM),\n");
		sb1.append("   BBS_LVL = ISNULL(WEB_USER.BBS_LVL, A.BBS_LVL),\n");
		sb1.append("   BBS_HOME_VIS_COUNT = ISNULL(WEB_USER.BBS_HOME_VIS_COUNT, A.BBS_HOME_VIS_COUNT),\n");
		sb1.append("   AUTH_WEIXIN_ID = ISNULL(WEB_USER.AUTH_WEIXIN_ID, A.AUTH_WEIXIN_ID),\n");
		sb1.append("   AUTH_WEIXIN_JSON = ISNULL(WEB_USER.AUTH_WEIXIN_JSON, A.AUTH_WEIXIN_JSON),\n");
		sb1.append("   IS_WEIXIN_SUBSCRIBE = ISNULL(WEB_USER.IS_WEIXIN_SUBSCRIBE, A.IS_WEIXIN_SUBSCRIBE),\n");
		sb1.append("   WX_GRP = ISNULL(WEB_USER.WX_GRP, A.WX_GRP)\n");
		sb1.append(" FROM (SELECT * FROM web_user WHERE usr_id=" + old_usr_id + ") A \n");
		sb1.append("   WHERE WEB_USER.USR_ID=" + new_usr_id);

		// 记录变更过程
		StringBuilder sb = new StringBuilder();
		sb.append("\n\n UPDATE WX_USER SET\n");
		sb.append("   BIND_STATUS = 'COM_YES',\n");
		sb.append("   BIND_USR_ID = " + new_usr_id + ",\n");
		sb.append("   BIND_REMOVE_USR_ID = " + old_usr_id + ",\n");
		sb.append("   BIND_REMOVE_USR_UNID = @sys_unid,\n");
		sb.append("   BIND_DATE = @SYS_DATE,\n");
		sb.append("   BIND_IP = @SYS_REMOTEIP,\n");
		sb.append("   BIND_JSON = @BIND_JSON\n");
		sb.append("WHERE WX_CFG_NO=@WX_CFG_NO AND  AUTH_WEIXIN_ID = '");
		sb.append(rv.s("G_WX_OPEN_ID").replace("'", "''") + "' and USR_UNID=@G_USR_UNID");

		update.append(sb.toString());
		update.append(sb1.toString());

		return update.toString();
	}
}
