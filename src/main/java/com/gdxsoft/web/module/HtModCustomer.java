package com.gdxsoft.web.module;

public class HtModCustomer extends HtModules {

	public static String PREFIX = "customer";
	private static HtModCustomer INST;

	public static HtModCustomer getIntance() {
		return INST;
	}

	static {
		INST = new HtModCustomer();
		INST.setName("customer");
		INST.setMemo("用户系统");

		INST.init();
	}

	private void init() {
		// 后台首页-调用菜单页面
		HtModule mm = new HtModule(PREFIX + "index", "/customer/menu/menu.xml", "ADM_MENU.F.Index", null);
		super.addModule(mm);

	}

	/**
	 * 获取菜单模块
	 * 
	 * @return
	 */
	public HtModule getModelIndex() {
		return this.getModule(PREFIX + "index");
	}

}
