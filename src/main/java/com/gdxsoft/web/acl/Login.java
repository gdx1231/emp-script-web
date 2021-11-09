package com.gdxsoft.web.acl;

import com.gdxsoft.easyweb.script.HtmlControl;
import com.gdxsoft.easyweb.script.PageValue;
import com.gdxsoft.easyweb.script.PageValueTag;
import com.gdxsoft.easyweb.script.RequestValue;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Login {

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
		// 检查登录
		boolean is_user_logined = false;
		PageValue pv = rv.getPageValues().getPageValue("G_USR_ID");
		if (pv == null || !(pv.getPVTag() == PageValueTag.SESSION || pv.getPVTag() == PageValueTag.COOKIE_ENCYRPT)) {
			is_user_logined = false;

		} else {
			is_user_logined = true;

		}
		return is_user_logined;
	}

	/**
	 * 检查b2b供应商是否登录
	 * 
	 * @return
	 */
	public static boolean isSupplyLogined(RequestValue rv) {
		// 检查登录
		boolean is_user_logined = false;
		PageValue pv = rv.getPageValues().getPageValue("G_ADM_ID");
		PageValue pv1 = rv.getPageValues().getPageValue("G_SUP_ID");
		if (pv == null || !(pv.getPVTag() == PageValueTag.SESSION || pv.getPVTag() == PageValueTag.COOKIE_ENCYRPT)) {
			is_user_logined = false;

		} else {
			is_user_logined = true;

		}
		if (is_user_logined) {
			if (pv1 == null
					|| !(pv1.getPVTag() == PageValueTag.SESSION || pv1.getPVTag() == PageValueTag.COOKIE_ENCYRPT)) {
				is_user_logined = false;
			}
		}
		return is_user_logined;
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
