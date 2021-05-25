package com.gdxsoft.web.app;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.easyweb.utils.msnet.MStr;
import com.gdxsoft.web.acl.Login;
import com.gdxsoft.web.dao.*;


public class App {
	RequestValue rv;
	SupMain supMain;

	public App(RequestValue rv) {
		this.rv = rv;
	}

	/**
	 * 获取代理的sup_id （-1表示未登录，0为 erp身份， >0为代理）
	 * 
	 * @return -1表示未登录，0为 erp身份， >0为代理
	 */
	public int getAgentSupId() {
		int agentSupId = -1;
		boolean isSupplyLogined = Login.isSupplyLogined(rv);

		if (isSupplyLogined) {
			if (rv.s("g_f_sup_id") != null) {
				agentSupId = rv.getInt("g_f_sup_id");
			} else {
				agentSupId = 0;
			}
		}
		return agentSupId;
	}

	/**
	 * 获取App权限
	 * 
	 * @return
	 */
	public String getRoleType() {
		int agentSupId = this.getAgentSupId();

		boolean isSupplyLogined = Login.isSupplyLogined(rv);
		// 用户角色
		String roleType = "";
		if (rv.s("GRP_COS_ID") != null) {
			roleType = "CUSTOMER"; // 师生（学生/家长）
		} else if (isSupplyLogined) {
			if (agentSupId == -1) { // rv.s("g_f_sup_id");
				roleType = "STAFF"; // 运营方
			} else {
				roleType = "AGENT"; // 代理
			}
		}

		return roleType;
	}

	/**
	 * 小程序引荐人
	 * 
	 * @return
	 * @throws Exception
	 */
	public String refUser() throws Exception {
		MStr sbNo = new MStr();
		// 来源的用户unid
		sbNo.al("var g_REFUSRUNID = null;");
		// 来源用户的 usr_lvl_id - web_user_level
		sbNo.al("var g_REFUSRLVLID = null;");

		MStr sb = new MStr();

		int refUsrLvlId = rv.getInt("REFUSRLVLID");
		String sqlRef = "select usr_id, usr_unid, usr_name from web_user where usr_unid=@REFUSRUNID";
		DTTable tbRefUser = DTTable.getJdbcTable(sqlRef, rv);
		if (tbRefUser.getCount() == 0) {
			return addScript(sbNo.toString());
		}

		int usrId = tbRefUser.getCell(0, "usr_id").toInt();
		String sqlUsrLvl = "select * from web_user_level where usr_lvl_id = @REFUSRLVLID";
		DTTable tbRefUsrLvl = DTTable.getJdbcTable(sqlUsrLvl, rv);
		if (tbRefUsrLvl.getCount() == 0) {
			return addScript(sbNo.toString());
		}
		if (tbRefUsrLvl.getCell(0, "usr_id").toInt().intValue() == usrId) {
			// 用户数据数据一致
			// 来源的用户unid
			sb.al("var g_REFUSRUNID = \"" + Utils.textToJscript(rv.s("REFUSRUNID")) + "\";");
			sb.al("// " + Utils.textToJscript(tbRefUser.getCell(0, "usr_name").toString()));
			// 来源用户的 usr_lvl_id - web_user_level
			sb.al("var g_REFUSRLVLID = " + refUsrLvlId + ";");
			return addScript(sb.toString());
		} else {
			System.out.println("引荐人usr_lvl_id对应的usrid不一致：web_user[" + usrId + "]!=web_user_level[" + refUsrLvlId + "]");
			return addScript(sbNo.toString());
		}
	}

	/**
	 * 启动打开课程 ，由分享页面创建
	 * 
	 * @param rv
	 * @return
	 * @throws Exception
	 */
	public String startEolLesson(RequestValue rv) throws Exception {
		MStr sb = new MStr();

		String sqlLesson = "select lesson_id, lesson_uid from camp_lesson where lesson_id=@STARTLESSONID"
				+ " and STATUS='USED' ";
		if (rv.s("preview_lesson_info") == null) {
			// not in preview mode
			// 对于预览的课程不限制发布状态和审核状态
			sqlLesson += " and LESSON_DLV_STATUS='COM_YES' and LESSON_AUDIT_STATUS='COM_YES'";
		}
		DTTable tbLesson = DTTable.getJdbcTable(sqlLesson, rv);
		if (tbLesson.getCount() > 0) {
			// 获取相应的团，在销售中的
			String sqlGrp = "select grp_unid from grp_main where GRP_TYPE='GRP_TYPE_CAMP_LESSON' "
					+ " and GRP_STATE='USED' and GRP_FORM_ID=" + tbLesson.getCell(0, "lesson_id").toInt()
					+ " and grp_sta='GRP_LESSON_STA_OK' and DESTINE_TIME<=@sys_date and DESTINE_END_TIME>@sys_date"
					+ " order by grp_sdate desc";
			DTTable tbGrp = DTTable.getJdbcTable(sqlGrp, "grp_id", 1, 1, "", rv);
			if (tbGrp.getCount() > 0) {
				sb.al("var g_startlessonuid = '" + tbLesson.getCell(0, "lesson_uid").toString() + "';");
				sb.al("var g_startgrpunid = '" + tbGrp.getCell(0, "grp_unid").toString() + "';");
			}
		}

		return addScript(sb.toString());
	}

	/**
	 * 启动打开课程团
	 * 
	 * @param rv
	 * @return
	 * @throws Exception
	 */
	public String startEolGrp(RequestValue rv) throws Exception {
		MStr sb = new MStr();
		String sqlGrp = "select a.GRP_UNID,b.LESSON_UID from grp_main a\n"
				+ " inner join camp_lesson b on a.GRP_FORM_ID = b.LESSON_ID\n";
		/*
		 * if(rv.s("agunid")!=""){ sqlGrp+
		 * ="inner join sup_main c on a.SUP_DOWN_ID=c.SUP_ID and c.SUP_UNID=@agunid"; }
		 */
		sqlGrp += " where a.grp_state='USED' and a.grp_id=@STARTGRPID\n"
				+ " and a.grp_sta='GRP_LESSON_STA_OK' and a.DESTINE_TIME<=@sys_date and a.DESTINE_END_TIME>@sys_date\n"
				+ " and b.STATUS='USED' and b.LESSON_DLV_STATUS='COM_YES' and b.LESSON_AUDIT_STATUS='COM_YES'\n";
		DTTable tbGrp = DTTable.getJdbcTable(sqlGrp, rv);
		if (tbGrp.getCount() > 0) {
			sb.al("var g_startlessonuid = '" + tbGrp.getCell(0, "LESSON_UID").toString() + "';");
			sb.al("var g_startgrpunid = '" + tbGrp.getCell(0, "GRP_UNID").toString() + "';");
		}

		return addScript(sb.toString());
	}

	/**
	 * 启动打开项目 ，由分享页面创建
	 * 
	 * @param rv
	 * @return
	 * @throws Exception
	 */
	public String startEolProject(RequestValue rv) throws Exception {
		MStr sb = new MStr();

		String sql = "select PRJ_ID,PRJ_UID from gyap_enroll_project\n"
				+ "where PRJ_ID = @STARTPRJID and PRJ_STATUS = 'USED'\n"
				+ "and (PRJ_EFFECTIVE is null or PRJ_EFFECTIVE<=@sys_date)\n"
				+ "and (PRJ_EXPIRED is null or PRJ_EXPIRED>@sys_date)\n";
		DTTable tb = DTTable.getJdbcTable(sql, rv);
		if (tb.getCount() > 0) {
			sb.al("var g_startprjuid = '" + tb.getCell(0, "PRJ_UID").toString() + "';");
		}

		return addScript(sb.toString());
	}

	public String addScript(String js) {
		if (js == null || js.trim().length() == 0) {
			return "";
		}
		MStr sb = new MStr();
		sb.al("<script type=\"text/javascript\">");
		sb.al(js);
		sb.al("</script>");

		return sb.toString();
	}

	public WebUser getWebUser(int usrId) {
		WebUserDao d = new WebUserDao();
		return d.getRecord(usrId);
	}

	public WebUser getWebUser(String usrUnid) {
		WebUserDao d = new WebUserDao();
		String w = "usr_unid='" + usrUnid.replace("'", "") + "'";
		ArrayList<WebUser> al = d.getRecords(w);
		if (al.size() == 0) {
			return null;
		} else {
			return al.get(0);
		}
	}

	/**
	 * 根据 参数 agunid 获取代理信息
	 * 
	 * @param defaultAgentUnid
	 * @return
	 */
	public SupMain getSupMainByAgunid(String defaultAgentUnid) {
		SupMainDao d = new SupMainDao();
		String agunid = rv.s("agunid"); // 代理的sup_unid
		if (agunid == null || agunid.trim().length() == 0) {
			agunid = defaultAgentUnid;
		}
		String where = " sup_unid='" + agunid.replace("'", "") + "'";
		ArrayList<SupMain> supMains = d.getRecords(where);

		if (supMains.size() == 0) {
			this.supMain = null;
			return null;
		}
		this.supMain = supMains.get(0);
		return this.supMain;
	}

	/**
	 * 根据 获取代理信息
	 * 
	 * @param supId
	 * @return
	 */
	public SupMain getSupMain(int supId) {
		SupMainDao d = new SupMainDao();

		return d.getRecord(supId);
	}

	/**
	 * 获取代理的logo
	 * 
	 * @param defaultLogo 默认的logo
	 * @return
	 */
	public String getAgentLogo(String defaultLogo) {
		SysDefaultDao d = new SysDefaultDao();
		SysDefault o = d.getRecord("SYS_DEF_LOGO", this.supMain.getSupId());
		if (o == null) {
			return defaultLogo;
		}

		// 代理的logo
		String logo = o.getDefaultValue();
		if (logo == null || logo.trim().length() == 0) {
			return defaultLogo;
		} else {
			return logo;
		}
	}

	/**
	 * app启动时 根据 ewa_lang, cookie(APP_LANG)和 request.getHeader("accept-language")
	 * 来判断语言设定
	 * 
	 * @param response
	 * @return
	 */
	public String appStartLang(HttpServletResponse response) {
		HttpServletRequest request = rv.getRequest();
		HttpSession session = rv.getSession();

		String lang = "zhcn"; // 默认中文
		if (rv.s("ewa_lang") != null) {
			lang = rv.s("ewa_lang");
			if (!lang.equals("enus")) {
				lang = "zhcn";
			}
			// System.out.println(" 取消用户的语言设定 APP_LANG " + rv.s("ewa_lang"));

			// 取消用户的语言设定 APP_LANG
			javax.servlet.http.Cookie ck = new javax.servlet.http.Cookie("APP_LANG", "");
			ck.setMaxAge(0);
			ck.setPath("/");

			response.addCookie(ck);

		} else if (rv.s("APP_LANG") != null) { // cookie中设定的（language-setting.jsp）
			lang = rv.s("APP_LANG");
			if (!lang.equals("enus")) {
				lang = "zhcn";
			}
		} else if (rv.s("ewa_lang") == null) {
			String accept_language = request.getHeader("accept-language");
			if (accept_language != null) {
				String[] accept_languages = accept_language.toLowerCase().split(",");
				if (accept_languages[0].indexOf("zh") < 0) { // 非中文
					lang = "enus"; // 英文
				}
			}
		} else {
			lang = rv.getLang();
		}

		Object obj_ewa_lang = session.getAttribute("SYS_EWA_LANG");
		if (obj_ewa_lang == null || !lang.equals(obj_ewa_lang.toString())) {
			// System.out.println("设定语言为：" + lang);
			request.setAttribute("SYS_EWA_LANG", lang); // 设置英文
			rv.addOrUpdateValue("SYS_EWA_LANG", lang); // 设置英文
		}
		return lang;
	}

	/**
	 * @return the rv
	 */
	public RequestValue getRv() {
		return rv;
	}

	/**
	 * @return the supMain
	 */
	public SupMain getSupMain() {
		return supMain;
	}

}
