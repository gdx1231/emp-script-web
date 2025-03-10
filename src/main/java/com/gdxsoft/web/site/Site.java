package com.gdxsoft.web.site;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.web.dao.SiteMain;
import com.gdxsoft.web.dao.SiteMainDao;
import com.gdxsoft.web.dao.SiteNavCat;
import com.gdxsoft.web.dao.SiteNavCatDao;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 网站信息
 *
 * @author admin
 */
public class Site {

	public static HashMap<String, Site> SITE_MAP = new HashMap<String, Site>();

	/**
	 * 根据用户浏览器判断用户默认的语言
	 * <ol>
	 * <li>参数：ewa_lang</li>
	 * <li>Cookie：APP_LANG</li>
	 * <li>浏览器：accept-language</li>
	 * <li>rv.getLang</li>
	 * </ol>
	 * 
	 * @param rv
	 * @param response
	 * @return enus/zhcn
	 */
	public static String checkUserLanguage(RequestValue rv, HttpServletResponse response) {
		String lang = "zhcn";
		if (rv.s("ewa_lang") != null) {
			lang = rv.s("ewa_lang");
			if (!lang.equals("enus")) {
				lang = "zhcn";
			}
			// 取消用户的语言设定 APP_LANG
			javax.servlet.http.Cookie ck = new javax.servlet.http.Cookie("APP_LANG", "");
			ck.setMaxAge(0);
			ck.setPath("/");

			if (response != null) {
				response.addCookie(ck);
			}
		} else if (rv.s("APP_LANG") != null) {
			// cookie中设定的（language-setting.jsp）
			lang = rv.s("APP_LANG");
			if (!lang.equals("enus")) {
				lang = "zhcn";
			}
		} else if (rv.s("ewa_lang") == null && rv.getRequest() != null) {
			// 根据浏览器判断用户默认的语言
			String accept_language = rv.getRequest().getHeader("accept-language");
			if (accept_language != null) {
				String[] accept_languages = accept_language.toLowerCase().split(",");
				if (accept_languages[0].indexOf("zh") < 0) { // 非中文
					lang = "enus"; // 英文
				}
			}
		} else {
			lang = rv.getLang();
		}

		return lang;
	}

	/**
	 * 创建Navs的Ul/li/a 的html
	 *
	 * @param al
	 * @param lang   语言
	 * @param target 打开目标
	 * @return
	 */
	public static String createNavsUl(ArrayList<SiteNavCat> al, String lang, String target) {
		StringBuilder sb = new StringBuilder();
		sb.append("<ul class='ewa-nav-footer'>");
		String t = (target == null || target.trim().length() == 0) ? ""
				: " target=\"" + Utils.textToInputValue(target) + "\" ";
		boolean isEn = (lang != null && lang.equalsIgnoreCase("enus"));
		for (int i = 0; i < al.size(); i++) {
			SiteNavCat nav = al.get(i);
			sb.append("<li class='ewa-nav-item'><a class='ewa-nav-item-a'");
			sb.append(t);
			sb.append(" href=\"");
			String url = null;
			if (isEn) {
				url = nav.getSitNavUrlEn();
			}
			if (url == null || url.trim().length() == 0) {
				url = nav.getSitNavUrl();
			}
			if (url == null || url.trim().length() == 0) {
				url = "javascript:void(0)";
			}
			String title = null;
			if (isEn) {
				title = nav.getSitNavNameEn();
			}
			if (title == null || title.trim().length() == 0) {
				title = nav.getSitNavName();
			}

			sb.append(url);
			sb.append("\" ");

			String attr = nav.getSitNavAttr();
			if (attr != null && attr.trim().length() > 0) {
				sb.append(attr);
			}
			sb.append(" >");
			sb.append(Utils.textToInputValue(title));
			sb.append("</a></li>");
		}
		sb.append("</ul>");
		return sb.toString();
	}

	/**
	 * 获取站点设置，每(maxMs 指定的时间 毫秒) 获取一次
	 *
	 * @param siteUnid 站点的唯一表示
	 * @param maxMs    重新获取数据间隔
	 * @return
	 */
	public static Site getInstance(String siteUnid, long maxMs) {
		return getInstance(siteUnid, null, maxMs);
	}

	/**
	 * 获取站点设置，每(maxMs 指定的时间 毫秒) 获取一次
	 *
	 * @param siteUnid   站点的唯一表示
	 * @param configName 数据库连接池名称
	 * @param maxMs      重新获取数据间隔
	 * @return
	 */
	public static Site getInstance(String siteUnid, String configName, long maxMs) {
		String key = siteUnid + "-----------" + configName;
		try {
			Site s = null;
			if (SITE_MAP.containsKey(key)) {
				s = SITE_MAP.get(key);
				if (s.checkOverTime(maxMs)) {
					s.init(siteUnid);
					SITE_MAP.put(siteUnid, s);
				}
			} else {
				s = new Site();
				s.setConfigName(configName);
				s.init(siteUnid);
				SITE_MAP.put(key, s);
			}
			return s;
		} catch (Exception err) {
			return null;
		}
	}

	/**
	 * 获取站点设置，每10分钟获取一次
	 *
	 * @param siteUnid
	 * @return
	 */
	public static Site getInstance(String siteUnid) {
		long maxMs = 60 * 1000 * 10;
		return getInstance(siteUnid, maxMs);
	}

	public Site() {

	}

	private String configName;
	private String navItemClass;
	private String navLinkClass;

	/**
	 * 初始化
	 *
	 * @param siteUnid 网站的UNID
	 */
	public void init(String siteUnid) {
		this._Navs = new HashMap<String, ArrayList<SiteNavCat>>();
		this._Breadcrumb = new HashMap<String, SiteNavCat>();
		this._IsOk = this.initMain(siteUnid);
		if (this._IsOk) {
			// this.initNavsTop(siteUnid, "SITE_NAVS_TOP");
			// this.initNavsTop(siteUnid, "SITE_NAVS_FOOTER");
			// this.initNavsTop(siteUnid, "SITE_NAVS_HEADER");

			initNavs();
			this._LastTime = System.currentTimeMillis();

			SITE_MAP.put(siteUnid, this);
		}
	}

	/**
	 * 创建导航栏
	 * 
	 * @param alr
	 * @param defaultContext 默认的连接前缀
	 * @param isEn
	 * @param islogined
	 * @return
	 */
	public String createLinks(List<SiteNavCat> alr, String defaultContext, boolean isEn, boolean islogined) {
		if (alr == null) {
			return "";
		}
		StringBuilder sbNavR = new StringBuilder();
		for (int i = 0; i < alr.size(); i++) {
			SiteNavCat cat = alr.get(i);
			String tp = cat.getSitNavType();
			if (tp != null && tp.trim().length() > 0) {
				if (islogined && tp.equals("LOGININ")) {
					continue;
				}
				if (!islogined && tp.equals("LOGINOUT")) {
					continue;
				}
			}
			String u = this.createLink(cat, defaultContext, isEn);

			// 如果存在子菜单
			if (cat.getChildren().size() > 0) {
				String name = isEn ? cat.getSitNavNameEn() : cat.getSitNavName();
				sbNavR.append("<li class='dropdown'><a href='" + u + "' class='dropdown-toggle' data-toggle='dropdown'>"
						+ name + "<strong class='caret'></strong></a>");
				sbNavR.append("<ul class='dropdown-menu'>");

				List<SiteNavCat> alch = cat.getChildren();
				for (int j = 0; j < alch.size(); j++) {
					SiteNavCat cat_ch = alch.get(j);
					String uch = this.createLink(cat_ch, defaultContext, isEn);
					String li = this.createLinkHtml(cat, isEn, uch);
					sbNavR.append(li);
				}

				sbNavR.append("</ul></li>");
			} else {
				String li = this.createLinkHtml(cat, isEn, u);
				sbNavR.append(li);
			}
		}
		return sbNavR.toString();
	}

	/**
	 * 创建连接
	 * 
	 * @param cat            目录
	 * @param defaultContext 默认的连接前缀
	 * @param isEn           中英文
	 * @return
	 */
	public String createLink(SiteNavCat cat, String defaultContext, boolean isEn) {
		String url = isEn ? cat.getSitNavUrlEn() : cat.getSitNavUrl();
		if (url == null) {
			url = "";
		}
		String u = "";
		if (url.indexOf("javascript:") >= 0 || url.indexOf("http:") >= 0 || url.indexOf("https:") >= 0) {
			u = url;
		} else if (url.indexOf("/") == 0) {
			u = url;
		} else {
			u = defaultContext + (defaultContext.endsWith("/") ? "" : "/") + url;
		}
		return u;
	}

	/**
	 * 创建连接的 Html
	 * 
	 * @param cat  目录
	 * @param isEn 中英文
	 * @param url  连接地址
	 * @return
	 */
	public String createLinkHtml(SiteNavCat cat, boolean isEn, String url) {
		String name = isEn ? cat.getSitNavNameEn() : cat.getSitNavName();

		StringBuilder sbNav = new StringBuilder();
		sbNav.append("<li id=\"nav").append(cat.getSitNavId()).append("\"");
		if (this.navItemClass != null) {
			sbNav.append(" class=\"").append(this.navItemClass).append("\"");
		}
		sbNav.append("><a");
		if (this.navLinkClass != null) {
			sbNav.append(" class=\"").append(this.navLinkClass).append("\"");
		}
		sbNav.append(" id='nNv" + cat.getSitNavId() + "' href='");
		sbNav.append(url);

		sbNav.append("' ");
		if ("_blank".equals(cat.getSitNavAttr())) {
			sbNav.append("target='_blank'");
		} else {
			// 样式，属性等
			sbNav.append(cat.getSitNavAttr());
		}
		if (cat.getSitNavTarget() != null && cat.getSitNavTarget().trim().length() > 0) {
			sbNav.append("target=\"" + Utils.textToInputValue(cat.getSitNavTarget()) + "\"");
		}
		sbNav.append(" >");
		sbNav.append(name);
		sbNav.append("</a></li>\n");

		return sbNav.toString();
	}

	/**
	 * 检查缓存内容是否过期
	 *
	 * @param maxMs
	 * @return
	 */
	public boolean checkOverTime(long maxMs) {
		long t1 = System.currentTimeMillis() - this._LastTime;
		return t1 > maxMs;
	}

	private void initNavs() {
		String sqlTypes = "select distinct SIT_NAV_TYPE from  SITE_NAV_CAT where  SIT_NAV_LVL=1 AND sit_id="
				+ this._SiteMain.getSitId();
		DTTable tb = DTTable.getJdbcTable(sqlTypes, this.configName);
		for (int i = 0; i < tb.getCount(); i++) {
			String tag = tb.getCell(i, 0).toString();
			if (tag == null || tag.equals("")) {
				continue;
			}
			this.initNavsTop(this._SiteMain.getSitUnid(), tag);
		}
	}

	private void initNavsTop(String siteUnid, String tag) {

		String sql = "SIT_NAV_STATUS='OK' AND SIT_ID=" + this._SiteMain.getSitId() + " AND SIT_NAV_TYPE='"
				+ tag.replace("'", "''") + "' AND SIT_NAV_LVL=1 ";
		SiteNavCatDao dao = new SiteNavCatDao();
		if (this.configName != null) {
			dao.setConfigName(configName);
		}
		ArrayList<SiteNavCat> al = dao.getRecords(sql);
		if (al.isEmpty()) {
			return;
		}
		sql = "SIT_NAV_STATUS='OK' AND SIT_ID=" + this._SiteMain.getSitId() + " AND SIT_NAV_PID="
				+ al.get(0).getSitNavId() + " order by SIT_NAV_ORD";
		ArrayList<SiteNavCat> al1 = dao.getRecords(sql);

		this._Navs.put(tag, al1);
		if (al1.size() == 0) {
			return;
		}
		ArrayList<SiteNavCat> al2 = al1;

		for (int i = 0; i < 1000; i++) { // 避免死循环
			al2 = this.loadChidren(al2, dao);
			if (al2 == null || al2.size() == 0) {
				break;
			}

		}
	}

	private ArrayList<SiteNavCat> loadChidren(ArrayList<SiteNavCat> al1, SiteNavCatDao dao) {
		String sql = "SIT_NAV_STATUS='OK' AND SIT_ID=" + this._SiteMain.getSitId() + " AND SIT_NAV_PID IN(";
		if (al1.size() == 0) {
			return null;
		}
		HashMap<Integer, SiteNavCat> map = new HashMap<Integer, SiteNavCat>();
		for (int i = 0; i < al1.size(); i++) {
			SiteNavCat o = al1.get(i);
			if (i == 0) {
				sql += o.getSitNavId();
			} else {
				sql += "," + o.getSitNavId();
			}
			map.put(o.getSitNavId(), o);
			createBreadcrumb(o);
		}
		ArrayList<SiteNavCat> al2 = dao.getRecords(sql + ")  order by SIT_NAV_ORD");
		if (al2.size() == 0) {
			return null;
		}
		for (int i = 0; i < al2.size(); i++) {
			SiteNavCat o = al2.get(i);
			map.get(o.getSitNavPid()).getChildren().add(o);
			createBreadcrumb(o, map.get(o.getSitNavPid()));
		}

		return al2;
	}

	// 创建页面当前位置导航
	private void createBreadcrumb(SiteNavCat o) {
		createBreadcrumb(o, null);
	}

	private void createBreadcrumb(SiteNavCat o, SiteNavCat po) {
		String tag;
		if (o.getSitNavTag() == null || o.getSitNavTag().toString().trim().length() == 0) {
			tag = o.getSitNavId() + "";
		} else {
			tag = o.getSitNavTag();
		}

		if (_Breadcrumb.containsKey(tag)) {
			return;
		}

		String url = o.getSitNavUrl() == null || o.getSitNavUrl().toString().trim().length() == 0 ? "#"
				: o.getSitNavUrl().toString().trim();
		if (url.equals("#")) {
			url = "javascript:void(0)";
		}
		// String
		// crumb="<li><a href=\""+url+"\">"+o.getSitNavName()+"</a></li>";
		// String crumbcur="<li>"+o.getSitNavName()+"</li>";
		String crumb = "<a href=\"" + url + "\">" + o.getSitNavName() + "</a>";
		String crumbcur = "<span>" + o.getSitNavName() + "</span>";
		if (po != null) {
			crumb = po.getBreadcrumb() + "<span class='crumb-next'>&gt;</span>" + crumb;
			crumbcur = po.getBreadcrumb() + "<span class='crumb-next'>&gt;</span>" + crumbcur;
		}
		o.setBreadcrumb(crumb);
		o.setBreadcrumbCur(crumbcur);

		String urlen = o.getSitNavUrlEn() == null || o.getSitNavUrlEn().toString().trim().length() == 0 ? "#"
				: o.getSitNavUrlEn().toString().trim();
		if (urlen.equals("#")) {
			urlen = "javascript:void(0)";
		}
		// String
		// crumbEn="<li><a href=\""+urlen+"\">"+o.getSitNavNameEn()+"</a></li>";
		// String crumbcurEn="<li>"+o.getSitNavNameEn()+"</li>";
		String crumbEn = "<a href=\"" + urlen + "\">" + o.getSitNavNameEn() + "</a>";
		String crumbcurEn = "<span>" + o.getSitNavNameEn() + "</span>";
		if (po != null) {
			crumbEn = po.getBreadcrumbEn() + "<span class='crumb-next'>&gt;</span>" + crumbEn;
			crumbcurEn = po.getBreadcrumbEn() + "<span class='crumb-next'>&gt;</span>" + crumbcurEn;
		}
		o.setBreadcrumbEn(crumbEn);
		o.setBreadcrumbCurEn(crumbcurEn);
		this._Breadcrumb.put(tag, o);
	}

	private boolean initMain(String siteUnid) {
		String _inc_paras_sql = "SIT_UNID='" + siteUnid + "' AND SIT_STATUS='OK'";

		// 网站初始化参数
		SiteMainDao _inc_paras_siteMainDao = new SiteMainDao();
		_inc_paras_siteMainDao.setConfigName(this.configName);

		ArrayList<SiteMain> _inc_paras_al = _inc_paras_siteMainDao.getRecords(_inc_paras_sql);
		if (_inc_paras_al.isEmpty()) {
			this._IsOk = false;
			this._ErrMsg = "NO DATA";
			return false;
		}
		_SiteMain = _inc_paras_al.get(0);
		return true;
	}

	private long _LastTime;

	/**
	 * 上次调用时间
	 *
	 * @return
	 */
	public long getLastTime() {
		return _LastTime;
	}

	private SiteMain _SiteMain;
	private boolean _IsOk;
	private String _ErrMsg;

	private HashMap<String, ArrayList<SiteNavCat>> _Navs;

	private HashMap<String, SiteNavCat> _Breadcrumb;

	/**
	 * 错误信息
	 *
	 * @return
	 */
	public String getErrMsg() {
		return _ErrMsg;
	}

	/**
	 * 网站主信息
	 *
	 * @return
	 */
	public SiteMain getSiteMain() {
		return _SiteMain;
	}

	/**
	 * 获取导航信息
	 *
	 * @param name
	 * @return
	 */
	public ArrayList<SiteNavCat> getNavsByName(String name) {
		return this._Navs.get(name);
	}

	/**
	 * 头部导航信息
	 *
	 * @return
	 */
	public ArrayList<SiteNavCat> getNavsTop() {
		return this._Navs.get("SITE_NAVS_TOP");
	}

	/**
	 * 页脚导航
	 *
	 * @return
	 */
	public ArrayList<SiteNavCat> getNavsFooter() {
		return this._Navs.get("SITE_NAVS_FOOTER");
	}

	public ArrayList<SiteNavCat> getNavsHeader() {
		return this._Navs.get("SITE_NAVS_HEADER");
	}

	/**
	 * 是否正确
	 *
	 * @return
	 */
	public boolean isOk() {
		return this._IsOk;
	}

	public HashMap<String, SiteNavCat> getBreadcrumb() {
		return _Breadcrumb;
	}

	/**
	 * 数据库连接池名称
	 * 
	 * @return the configName
	 */
	public String getConfigName() {
		return configName;
	}

	/**
	 * 数据库连接池名称
	 * 
	 * @param configName the configName to set
	 */
	public void setConfigName(String configName) {
		this.configName = configName;
	}

	/**
	 * 导航的css的class
	 * 
	 * @return
	 */
	public String getNavItemClass() {
		return navItemClass;
	}

	/**
	 * 导航的css的class
	 * 
	 * @param navItemClass
	 */
	public void setNavItemClass(String navItemClass) {
		this.navItemClass = navItemClass;
	}

	/**
	 * 导航链接的css的class
	 * 
	 * @return
	 */
	public String getNavLinkClass() {
		return navLinkClass;
	}

	/**
	 * 导航链接的css的class
	 * 
	 * @param navLinkClass
	 */
	public void setNavLinkClass(String navLinkClass) {
		this.navLinkClass = navLinkClass;
	}
}
