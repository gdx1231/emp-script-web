package com.gdxsoft.web.app;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.script.RequestValue;

/**
 * 根据 是否代理显示 菜单
 * 
 * @author admin
 *
 */
public class App2CfgBase {

	RequestValue rv_;
	boolean isEn_;
	boolean isLogined_;
	boolean isAgent_;
	boolean isInNativeApp_;
	String configName;
	/**
	 * 数据库链接池名称
	 * @return
	 */
	public String getConfigName() {
		return configName;
	}

	/**
	 * 数据库链接池名称
	 * @param configName
	 */
	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public App2CfgBase(RequestValue rv) {
		this.rv_ = rv;

		this.checkLanguage();

		this.checkNativeApp();

		this.checkLogined();
	}

	/**
	 * 检查页面语言
	 */
	public void checkLanguage() {
		// 检查页面语言
		String _tmp_lang123 = rv_.getLang();

		boolean is_en_123 = false;
		if (_tmp_lang123 != null && _tmp_lang123.equalsIgnoreCase("enus")) {
			is_en_123 = true;
		}
		this.isEn_ = is_en_123;
	}

	/**
	 * 检查是否原生app
	 */
	public void checkNativeApp() {
		String user_agent123 = rv_.s("SYS_USER_AGENT");
		boolean is_in_native_app = false;
		if (user_agent123 != null) {
			user_agent123 = user_agent123.toLowerCase();
			if (user_agent123.indexOf(App.NATIVE_TAG) > 0) {
				is_in_native_app = true;
			}
		}

		this.isInNativeApp_ = is_in_native_app;
	}

	/**
	 * 检查是否登录
	 */
	public void checkLogined() {
		String sqlAdm = "select * from adm_user where adm_id=@g_adm_id";
		DTTable tbAdm = DTTable.getJdbcTable(sqlAdm, this.configName, rv_);
		if (tbAdm.getCount() == 0) {
			this.isLogined_ = false;
		} else {
			try {
				boolean is_agent = !tbAdm.getCell(0, "pid").isNull();
				this.isAgent_ = is_agent;
			} catch (Exception e) {
			}
		}
	}

	public String createFooterMenus(DTTable tbFooter) throws Exception {
		StringBuilder sb = new StringBuilder();
		int length = tbFooter.getCount();
		String styleWidth = "";
		if (length != 5) {
			styleWidth = " style='width:" + (100.0 / length) + "%;' ";
		}
		for (int i = 0; i < tbFooter.getCount(); i++) {
			String cmd = tbFooter.getCell(i, "id").toString();
			String attrs = " id='" + cmd + "' ";
			String NEED_LOGIN = tbFooter.getCell(i, "NEED_LOGIN").toString();

			String title = tbFooter.getCell(i, "title").toString();
			String title_en = tbFooter.getCell(i, "title_en").toString();
			String icon = tbFooter.getCell(i, "fa").toString();
			if (cmd.equals("foot-main1")) {
				cmd = "main1"; // 主页
			}

			if (cmd.equals("main1")) {
				title = "主页";
				title_en = "Home";
				icon = icon.replace("fa-bank", "fa-home");
			}
			if ("Y".equals(NEED_LOGIN)) {
				attrs += " needlogin='1' cmd='" + cmd + "' ";
				// cmd = "login?after=" + cmd;
			}
			String txt = this.isEn_ ? title_en : title;

			String tmp = "<td " + styleWidth + "><a href='#" + cmd + "' class='ft-item' " + attrs + "> <i class='"
					+ icon + "'></i> <span class='ft-item-txt'>" + txt + "</span></a></td>";
			sb.append(tmp);
		}
		return sb.toString();
	}

	/**
	 * 创建4格菜单
	 * 
	 * @param tbCfgIndex
	 * @return
	 * @throws Exception
	 */
	public String createMenu4(DTTable tbCfgIndex) throws Exception {
		StringBuilder sbCfgIndex = new StringBuilder();
		for (int i = 0; i < tbCfgIndex.getCount(); i++) {
			String id = tbCfgIndex.getCell(i, "id").toString();
			String title = tbCfgIndex.getCell(i, "title").toString();
			String title_en = tbCfgIndex.getCell(i, "title_en").toString();
			String name = this.isEn_ ? title_en : title;
			String icon = tbCfgIndex.getCell(i, "fa").toString();
			String CONTENT_PATH = tbCfgIndex.getCell(i, "CONTENT_PATH").toString();
			if (CONTENT_PATH == null) {
				CONTENT_PATH = "";
			} else {
				CONTENT_PATH = CONTENT_PATH.trim();
			}
			String href;

			if (this.isInNativeApp_ && id.endsWith("11111111index-classroom")) {
				href = "javascript: goto_native_classroom()";
			} else {
				href = "#" + id;
			}

			StringBuilder sb = new StringBuilder();
			sb.append(" <a cp='");
			sb.append(CONTENT_PATH);
			sb.append("' class='index-menu-item' id='home_");
			sb.append(id);
			sb.append("' href='");
			sb.append(href);
			sb.append("'><i class='");
			sb.append(icon);
			sb.append("'></i><span>");
			sb.append(name);
			sb.append("</span></a>");
			// ID和href不能一样值，否则位置会跑
			String item = sb.toString();
			sbCfgIndex.append(item);
		}

		if (tbCfgIndex.getCount() % 4 != 0) {
			int last_len = 4 - tbCfgIndex.getCount() % 4;
			for (int i = 0; i < last_len; i++) {
				sbCfgIndex.append("<a class='index-menu-item'></a>");
			}
		}
		return sbCfgIndex.toString();
	}

	public String getCfgsSql() {
		return null;
	}

	public DTTable getCfgsTable() {
		return DTTable.getJdbcTable(this.getCfgsSql(), this.configName, rv_);
	}

	public String getCfgsJs() {
		DTTable tbCfg = this.getCfgsTable();
		return "<script>var __INIT_CFGS=" + tbCfg.toJSONArray() + ";</script>";
	}

	public String getHomeIndexSql() {
		return null;
	}

	public DTTable getHomeIndexTable() {
		DTTable tb = DTTable.getJdbcTable(getHomeIndexSql(), this.configName, rv_);
		return tb;
	}

	/**
	 * 获取首页4格菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getHomeIndexMenu4() throws Exception {
		DTTable tb = this.getHomeIndexTable();
		String menus = this.createMenu4(tb);
		return menus;
	}

	public String getFooterSql() {
		return null;
	}

	/**
	 * 获取footer菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getFooter() throws Exception {
		String sqlCfgIndex = this.getFooterSql();
		DTTable tbFooter = DTTable.getJdbcTable(sqlCfgIndex, this.configName, this.rv_);

		String footerHtml = this.createFooterMenus(tbFooter);

		return footerHtml;
	}

	public RequestValue getRv() {
		return rv_;
	}

	public boolean isEn() {
		return isEn_;
	}

	public boolean isLogined() {
		return isLogined_;
	}

	public boolean isAgent() {
		return isAgent_;
	}

	public boolean isInNativeApp() {
		return isInNativeApp_;
	}

}
