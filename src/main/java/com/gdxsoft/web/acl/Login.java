package com.gdxsoft.web.acl;

import com.gdxsoft.easyweb.cache.CachedValue;
import com.gdxsoft.easyweb.cache.CachedValueManager;
import com.gdxsoft.easyweb.data.DTTable;
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
	 * 获取我的所有岗位
	 * 
	 * @param myAdmId
	 * @return
	 */
	public static String getMyPosts(int myAdmId) {
		RequestValue rv = new RequestValue();

		// 拼接一个字符串key，用于缓存的键值
		String key = "getMyPosts-" + myAdmId;

		// 从缓存管理器中获取key对应的缓存值cv
		CachedValue cv = CachedValueManager.getValue(key);
		if (cv != null) {
			// 如果cv不为空，说明已经有缓存结果
			return cv.getValue().toString();
		}
		// 将myAdmId作为参数添加或更新到rv中，键名为"MY_ADM_ID"
		rv.addOrUpdateValue("MY_ADM_ID", myAdmId);
		String sql0 = "SELECT DEP_POS_ID FROM adm_r_udp WHERE ADM_ID = @MY_ADM_ID  ";
		// 根据sql0和rv执行数据库查询，并返回一个DTTable对象tb
		DTTable tb = DTTable.getJdbcTable(sql0, rv);
		// 如果tb没有记录，说明没有岗位信息
		if (tb.getCount() == 0) {
			CachedValueManager.addValue(key, "", 60);
			return "";
		}
		// 调用tb的joinIds方法，根据DEP_POS_ID字段拼接所有岗位ID，并赋值给posts字符串
		String posts = tb.joinIds("DEP_POS_ID", false);
		CachedValueManager.addValue(key, posts);

		return posts;
	}

	/**
	 * 获取我的所有下级
	 * 
	 * @param myAdmId
	 * @return
	 */
	public static String getMySubAdmins(int myAdmId) {
		// 定义一个静态方法，接收一个整数参数myAdmId，返回一个字符串
		RequestValue rv = new RequestValue(); // 创建一个RequestValue对象rv
		String key = "getMySubAdmins-" + myAdmId; // 拼接一个字符串key，用于缓存的键值
		CachedValue cv = CachedValueManager.getValue(key); // 从缓存管理器中获取key对应的缓存值cv
		if (cv != null) { // 如果cv不为空，说明已经有缓存结果
			return cv.getValue().toString(); // 直接返回cv的值转换为字符串
		}

		rv.addOrUpdateValue("MY_ADM_ID", myAdmId); // 将myAdmId作为参数添加或更新到rv中，键名为"MY_ADM_ID"
		String sql0 = "SELECT distinct ADM_DEP_ID AS dep_id FROM V_ADM_USER_POST_DEPT "
				+ " WHERE ADM_ID=@MY_ADM_ID and POS_IS_MASTER='Y'"; // 定义一个sql0语句，查询管理员ID为myAdmId且职位为主要的部门ID，并取别名为dep_id
		DTTable tb = DTTable.getJdbcTable(sql0, rv); // 根据sql0和rv执行数据库查询，并返回一个DTTable对象tb
		if (tb.getCount() == 0) { // 如果tb没有记录，说明没有部门信息
			CachedValueManager.addValue(key, String.valueOf(myAdmId), 60); // 将myAdmId转换为字符串并作为值添加到缓存管理器中，键名为key，有效期为60秒
			return String.valueOf(myAdmId); // 直接返回myAdmId转换为字符串
		}

		StringBuilder sb = new StringBuilder(); // 创建一个StringBuilder对象sb，用于拼接字符串
		String depts = tb.joinIds("dep_id", false); // 调用tb的joinIds方法，根据dep_id字段拼接所有部门ID，并赋值给depts字符串
		sb.append(depts); // 将depts追加到sb中
		for (int i = 0; i < 100; i++) { // 循环100次（实际上可能不需要这么多次）
			depts = getSubDepts(depts); // 调用getSubDepts方法，传入depts参数，获取所有下级部门ID，并赋值给depts字符串
			if (depts.length() == 0) { // 如果depts为空，说明没有更多下级部门了
				break; // 跳出循环
			}
			sb.append(",").append(depts); // 将逗号和depts追加到sb中
		}

		String sql1 = "select adm_id from adm_user where adm_dep_id in (" + sb.toString() + ")";
		/*
		 * 定义一个sql1语句， 查询所有部门ID在sb转换为字符串后包含的范围内的管理员ID， 并取别名为adm_id
		 */

		DTTable tb1 = DTTable.getJdbcTable(sql1);
		/*
		 * 根据sql1执行数据库查询， 并返回一个DTTable对象tb1
		 */

		String subAdmIds = tb1.joinIds("adm_id", false);
		/*
		 * 调用tb1的joinIds方法， 根据adm_id字段拼接所有下属管理员ID， 并赋值给subAdmIds字符串
		 */

		CachedValueManager.addValue(key, subAdmIds);
		/*
		 * 将subAdmIds作为值添加到缓存管理器中， 键名为key
		 */

		return tb1.joinIds("adm_id", false);
		/* 返回subAdmIds与上一行相同 */
	}

	private static String getSubDepts(String depts) {
		// SQL查询语句，用于获取子部门ID
		String sql = "select dep_id from adm_dept where dep_pid in (" + depts + ")";
		// 使用DTTable类执行SQL查询并获取结果
		DTTable tb = DTTable.getJdbcTable(sql);
		// 将结果中的子部门ID拼接成一个字符串
		String subdepts = tb.joinIds("dep_id", false);
		// 返回子部门ID字符串
		return subdepts;
	}

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
		if (rv.getRequest() == null) {
			return "404";
		}
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
	 * 
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
	 * 
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
	 * 
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
