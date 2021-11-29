package com.gdxsoft.web.module;

public class ModBackAdmin extends Modules {

	 private static ModBackAdmin INST;
	 
	 static {
		 INST = new ModBackAdmin();
		 INST.setName("backAdmin");
		 INST.setMemo("后台管理系统的配置内容");
	 }
	 
	 
	 private void init() {
		 
	 }
}
