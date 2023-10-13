package com.gdxsoft.web.app;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.script.RequestValue;

public interface IApp2Cfg {
	/**
	 * 创建4x4菜单
	 * 
	 * @param tbCfgIndex
	 * @return
	 * @throws Exception
	 */
	String createMenu4(DTTable tbCfgIndex) throws Exception;

	/**
	 * 获取cfgs的 sql
	 * 
	 * @return
	 */
	String getCfgsSql();

	/**
	 * 获取cfgs的表
	 * 
	 * @return
	 */
	DTTable getCfgsTable();

	/**
	 * 获取cfgs的脚本
	 * 
	 * @return
	 */
	String getCfgsJs();

	/**
	 * 获取主菜单
	 * 
	 * @return
	 */
	String getHomeIndexSql();

	DTTable getHomeIndexTable();

	/**
	 * 获取首页4格菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	String getHomeIndexMenu4() throws Exception;

	String getFooterSql();

	/**
	 * 获取footer菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	String getFooter() throws Exception;

	/**
	 * @return the rv_
	 */
	RequestValue getRv();

	/**
	 * @return the isEn_
	 */
	boolean isEn();

	/**
	 * @return the isLogined_
	 */
	boolean isLogined();

	/**
	 * @return the isAgent_
	 */
	boolean isAgent();

	/**
	 * @return the isInNativeApp_
	 */
	boolean isInNativeApp();

	/**
	 * 检查页面语言
	 */
	public void checkLanguage();

	/**
	 * 检查是否原生app，检查标记在 NATIVE_TAG中定义
	 */
	public void checkNativeApp();

	/**
	 * 检查是否登录
	 */
	public void checkLogined();

	/**
	 * 数据库链接池名称
	 * 
	 * @return
	 */
	public String getConfigName();

	/**
	 * 数据库链接池名称
	 * 
	 * @param configName
	 */
	public void setConfigName(String configName);
}