package com.gdxsoft.web.module;

public class HtModBusiness extends HtModules {

	public static String PREFIX = "business";
	private static HtModBusiness INST;

	public static HtModBusiness getIntance() {
		return INST;
	}

	static {
		INST = new HtModBusiness();
		INST.setName("business");
		INST.setMemo("商户系统");

		INST.init();
	}

	private void init() {
		// 后台首页-调用菜单页面
		HtModule mm = new HtModule(PREFIX + "index", "/business/menu/menu.xml", "ADM_MENU.F.Index", null);
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
