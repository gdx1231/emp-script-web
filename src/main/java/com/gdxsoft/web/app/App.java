package com.gdxsoft.web.app;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.script.display.frame.FrameParameters;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.easyweb.utils.msnet.MStr;
import com.gdxsoft.web.dao.*;

public class App {
	RequestValue rv;
	SupMain supMain;
	boolean inWeixin = false; // 微信

	boolean inNativeApp = false; // 在原生App中
	boolean iphone = false; // 是否iphone
	boolean android = false; // 是否安卓手机
	boolean inMini = false; // 是小程序调用

	boolean ipad = false;
	/**
	 * 浏览器UserAgent头部包含的原生app标记
	 */
	public static String NATIVE_TAG = "/native";

	public App(RequestValue rv) {
		this.rv = rv;

		if (rv.s("SYS_USER_AGENT") != null) {
			String _g_user_agent = rv.s("SYS_USER_AGENT").toLowerCase();
			inWeixin = _g_user_agent.indexOf("micromessenger") > 0;
			inNativeApp = _g_user_agent.indexOf(NATIVE_TAG) > 0;
			iphone = _g_user_agent.indexOf("iphone") > 0;
			android = _g_user_agent.indexOf("android") > 0;
			ipad = _g_user_agent.indexOf("ipad") > 0;
			inMini = _g_user_agent.indexOf("miniprogram") > 0;
		}
	}

	/**
	 * 创建App运行环境信息，小程序、微信、原生app、iphone，android信息
	 * 
	 * @return
	 */
	public String createAppEnvJs() {
		MStr sb = new MStr();

		sb.al("var g_is_in_weixin = " + inWeixin + ";");
		sb.al("var g_is_in_native = " + inNativeApp + ";");
		sb.al("var g_is_in_mini = " + inMini + ";");
		sb.al("var g_is_iphone = " + iphone + ";");
		sb.al("var g_is_ipad = " + ipad + ";");
		sb.al("var g_is_ios = " + (ipad || iphone) + ";");
		sb.al("var g_is_android = " + android + ";");

		return sb.toString();
	}

	public String createAppEnvCss() {
		MStr sb = new MStr();
		if (inWeixin) {
			sb.a(" weixin");
		}
		if (inNativeApp) {
			sb.a(" native");
		}
		if (inMini) {
			sb.a(" mini");
		}
		if (iphone) {
			sb.a(" iphone");
		}
		if (ipad) {
			sb.a(" ipad");
		}
		if (ipad || iphone) {
			sb.a(" ios");
		}
		if (android) {
			sb.a(" android");
		}

		return sb.toString();
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

		String ewaLang = rv.s("ewa_lang");

		String lang = null;

		String sysEwaLang = null;
		String appLang = null;
		String acceptLang = null;

		if (ewaLang == null) {
			// 1. 先从cookie中获取
			appLang = rv.s("APP_LANG");// cookie中设定的（language-setting.jsp）

			// 2. 再从session中获取
			if (session != null && session.getAttribute(FrameParameters.SYS_EWA_LANG) != null) {
				sysEwaLang = session.getAttribute(FrameParameters.SYS_EWA_LANG).toString();
			}
			// 3. 从浏览器的语言获取
			String accept_language = request.getHeader("accept-language");
			if (accept_language != null) {
				String[] accept_languages = accept_language.toLowerCase().split(",");
				if (accept_languages[0].indexOf("zh") < 0) { // 非中文
					acceptLang = FrameParameters.ENUS; // 英文
				}
			}

			if (appLang != null && appLang.trim().length() > 0) { // cookie中设定的（language-setting.jsp）
				lang = appLang;
			} else if (sysEwaLang != null && sysEwaLang.trim().length() > 0) { // session中设定的
				lang = sysEwaLang;
			} else if (acceptLang != null) { // 浏览器的语言
				lang = acceptLang;
			}

			if (!FrameParameters.ENUS.equals(lang)) {
				// 默认字符集为简体中文
				lang = FrameParameters.ZHCN;
			}

		} else {
			lang = rv.getLang();
		}
		if (session != null && (sysEwaLang == null || !lang.equals(sysEwaLang))) {
			session.setAttribute(FrameParameters.SYS_EWA_LANG, lang); // 设置语言
			rv.addOrUpdateValue(FrameParameters.SYS_EWA_LANG, lang); // 设置语言
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

	/**
	 * 在微信中
	 * 
	 * @return
	 */
	public boolean isInWeixin() {
		return inWeixin;
	}

	/**
	 * 在原生App中
	 * 
	 * @return
	 */
	public boolean isInNativeApp() {
		return inNativeApp;
	}

	/**
	 * 是iPhone
	 * 
	 * @return
	 */
	public boolean isIphone() {
		return iphone;
	}

	/**
	 * 是Android
	 * 
	 * @return
	 */
	public boolean isAndroid() {
		return android;
	}

	/**
	 * 在小程序中
	 * 
	 * @return
	 */
	public boolean isInMini() {
		return inMini;
	}

	/**
	 * 是否是ipad
	 * 
	 * @return
	 */
	public boolean isIpad() {
		return ipad;
	}

}
