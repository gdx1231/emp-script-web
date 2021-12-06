package com.gdxsoft.web.module;

public class HtModBackAdmin extends HtModules {

	private static HtModBackAdmin INST;

	public static HtModBackAdmin getIntance() {
		return INST;
	}
	
	static {
		INST = new HtModBackAdmin();
		INST.setName("backAdmin");
		INST.setMemo("后台管理系统的配置内容");

		INST.init();
	}

	private void init() {
		// 后台首页-调用菜单页面
		HtModule mm = new HtModule("index", "/meta-data/menu/menu.xml", "adm_menu.Menu.Modify", null);
		super.addModule(mm);

		// 后台登录页面
		HtModule login = new HtModule("login", "/meta-data/organization/admin.xml", "ADM_USER.F.Login", null);
		super.addModule(login);

	}

	/**
	 * 获取菜单模块
	 * 
	 * @return
	 */
	public HtModule getModelIndex() {
		return this.getModule("index");
	}

	/**
	 * 获取登录模块
	 * 
	 * @return
	 */
	public HtModule getModelLogin() {
		return this.getModule("login");
	}
}
