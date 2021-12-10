package com.gdxsoft.web.acl;

import com.gdxsoft.easyweb.script.HtmlControl;
import com.gdxsoft.easyweb.script.PageValue;
import com.gdxsoft.easyweb.script.PageValueTag;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UCookies;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class Login {

	/**
	 * 从Cookie和Session中清除登录凭证
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void clearLoginCredentials(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpSession session = request.getSession();
		if (session != null) {
			Enumeration<String> names = session.getAttributeNames();
			while (names.hasMoreElements()) {
				session.removeAttribute(names.nextElement());
			}
		}
		List<String> skipNames = Arrays.asList("APP_LANG", "JSESSIONID", "EWA_TIMEDIFF");
		UCookies.clearCookies(request, response, skipNames);

	}

	/**
	 * 是否为英文系统
	 * 
	 * @param rv
	 * @return
	 */
	public static boolean isEn(RequestValue rv) {
		return "enus".equals(rv.getLang());
	}

	/**
	 * 跳转到登录页面(供应商)
	 * 
	 * @param rv
	 * @return
	 */
	public static String gotoLoginSupply(RequestValue rv) {
		return gotoLogin(rv, rv.getContextPath() + "/login.jsp");
	}

	/**
	 * 跳转到登录页面(用户)
	 * 
	 * @param rv
	 * @return
	 */
	public static String gotoLoginUser(RequestValue rv) {
		return gotoLogin(rv, rv.getContextPath() + "/login_user.jsp");
	}

	/**
	 * 跳转登录页面
	 * 
	 * @param rv
	 * @param loginUrl 登录页面
	 * @return
	 */
	public static String gotoLogin(RequestValue rv, String loginUrl) {
		String _tmp_lang = rv.getLang();
		String uLoginLeft = loginUrl + (loginUrl.indexOf("?") < 0 ? "?" : "&") + "EWA_LANG=" + _tmp_lang;
		String ref = rv.getRequest().getContextPath() + rv.getRequest().getServletPath();
		if (rv.getRequest().getQueryString() != null) {
			ref += "?" + rv.getRequest().getQueryString();
		}
		try {
			ref = URLEncoder.encode(ref, "utf-8");
		} catch (UnsupportedEncodingException e) {

		}
		uLoginLeft += "&ref=" + ref;

		return uLoginLeft;
	}

	/**
	 * 检查用户是否登录
	 * 
	 * @return
	 */
	public static boolean isUserLogined(RequestValue rv) {
		Long userId = getLoginedUserId(rv);
		return userId != null;
	}

	/**
	 * 检查商户是否登录(G_SUP_ID和G_ADM_ID)
	 * 
	 * @return
	 */
	public static boolean isSupplyLogined(RequestValue rv) {
		Integer supId = getLoginedSupId(rv);
		if (supId == null) {
			return false;
		}
		Integer admId = getLoginedAdmId(rv);
		if (admId == null) {
			return false;
		}

		return true;

	}

	/**
	 * 商户(G_SUP_ID)在session 或 COOKIE_ENCYRPT
	 * @param rv
	 * @return
	 */
	public static Integer getLoginedSupId(RequestValue rv) {
		String v = getLoginedParameter(rv, "G_SUP_ID");
		if (v != null) {
			try {
				return Integer.parseInt(v);
			} catch (Exception err) {
				return null;
			}
		}
		return null;
	}

	/**
	 * 管理员(G_ADM_ID)在session 或 COOKIE_ENCYRPT
	 * @param rv
	 * @return
	 */
	public static Integer getLoginedAdmId(RequestValue rv) {
		String v = getLoginedParameter(rv, "G_ADM_ID");
		if (v != null) {
			try {
				return Integer.parseInt(v);
			} catch (Exception err) {
				return null;
			}
		}
		return null;
	}

	/**
	 * 用户(G_SUP_ID)在session 或 COOKIE_ENCYRPT
	 * @param rv
	 * @return
	 */
	public static Long getLoginedUserId(RequestValue rv) {
		String v = getLoginedParameter(rv, "G_USR_ID");
		if (v != null) {
			try {
				return Long.parseLong(v);
			} catch (Exception err) {
				return null;
			}
		}
		return null;
	}

	public static String getLoginedParameter(RequestValue rv, String name) {
		PageValue pv = rv.getPageValues().getPageValue(name);
		if (pv != null && (pv.getPVTag() == PageValueTag.SESSION || pv.getPVTag() == PageValueTag.COOKIE_ENCYRPT)) {
			return pv.getStringValue();
		}
		return null;
	}

	/**
	 * Api自动登录
	 * 
	 * @param rv
	 * @param response
	 * @return
	 */
	public static boolean apiAutoLogin(RequestValue rv, HttpServletResponse response) {
		String loginToken = rv.s("loginToken");
		if (rv.s("loginToken") == null || loginToken.trim().length() == 0) {
			return false;
		}

		try {
			String[] tokens = loginToken.split("\\#");
			if (tokens.length != 2) {
				System.out.println("凭证错误 loginToken ");
				return false;
			}

			String fp_unid = tokens[0];
			String r_code = tokens[1];

			// 提交登录页面
			String xmlname = "";
			String itemname = "";

			xmlname = "|2014_b2b|web|user.xml";
			itemname = "ADM_USER.Frame.Login";

			// b2b 供应商自动登录
			String paras = "EWA_POST=1&EWA_VALIDCODE_CHECK=NOT_CHECK&fp_unid=" + fp_unid + "&FP_VALIDCODE=" + r_code;
			HtmlControl ht = new HtmlControl();
			ht.init(xmlname, itemname, paras, rv, response);
			String html = ht.getHtml();

			if (html != null && html.indexOf("loginok()") > 0) {
				return true;
			} else {
				System.out.println(html);
				return false;
			}
		} catch (Exception err) {
			System.out.println(err.getMessage());
			return false;
		}
	}

}
