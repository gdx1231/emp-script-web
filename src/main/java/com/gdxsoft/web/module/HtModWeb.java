package com.gdxsoft.web.module;

public class HtModWeb extends HtModules {

	public static String PREFIX = "web";
	private static HtModWeb INST;

	public static HtModWeb getIntance() {
		return INST;
	}

	static {
		INST = new HtModWeb();
		INST.setName("web");
		INST.setMemo("网站系统");

		INST.init();
	}

	
	private void init() {
		String xmlName="/font-web/page.xml";
		// 网站页面，同时调用site_nav_cat数据和news_main数据（用于页脚）
		HtModule mm = new HtModule(PREFIX + "index", xmlName, "sit_main.F.V", null);
		super.addModule(mm);

		// 后台登录页面
		HtModule login = new HtModule(PREFIX + "login", "/business/orgniziation/admin.xml", "ADM_USER.F.Login", null);
		super.addModule(login);

	}

	/**
	 * 获取菜单模块
	 * 
	 * @return
	 */
	public HtModule getModelIndex() {
		return this.getModule(PREFIX + "index");
	}

	/**
	 * 获取登录模块
	 * 
	 * @return
	 */
	public HtModule getModelLogin() {
		return this.getModule(PREFIX + "login");
	}
}
