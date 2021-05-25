package com.gdxsoft.web.dao;

import com.gdxsoft.easyweb.datasource.ClassDaoBase;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.script.RequestValue;

import java.util.ArrayList;
import java.util.HashMap;
/** 表nws_r_main_cat操作类
 * @author gdx 时间：Mon May 10 2021 10:57:52 GMT+0800 (中国标准时间)
 */
public class NwsRMainCatDao extends ClassDaoBase<NwsRMainCat> implements IClassDao<NwsRMainCat>{

 private static String SQL_INSERT="INSERT INTO nws_r_main_cat(NWS_CAT_ID, NWS_ID, NRM_ORD) 	VALUES(@NWS_CAT_ID, @NWS_ID, @NRM_ORD)";
 public static String TABLE_NAME ="nws_r_main_cat";
 public static String[] KEY_LIST = { "NWS_CAT_ID", "NWS_ID"   };
 public static String[] FIELD_LIST = { "NWS_CAT_ID", "NWS_ID", "NRM_ORD" };
 public NwsRMainCatDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("gw");
   super.setInstanceClass(NwsRMainCat.class);
   super.setTableName(TABLE_NAME);
   super.setFields(FIELD_LIST);
   super.setKeyFields(KEY_LIST);
   super.setSqlInsert(SQL_INSERT);
 }/**
	 * 生成一条记录
	*@param para 表nws_r_main_cat的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(NwsRMainCat para){

		RequestValue rv=this.createRequestValue(para);

		return super.executeUpdate(SQL_INSERT, rv);

	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表nws_r_main_cat的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(NwsRMainCat para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(sql, rv);
}/**
	 * 根据主键返回一条记录
	*@param paraNwsCatId 目录编号
	*@param paraNwsId 新闻编号
	*@return 记录类(NwsRMainCat)
	*/
public NwsRMainCat getRecord(Long paraNwsCatId, Long paraNwsId){
	RequestValue rv = new RequestValue();
rv.addValue("NWS_CAT_ID", paraNwsCatId, "Long", 19);
rv.addValue("NWS_ID", paraNwsId, "Long", 19);
	String sql = super.getSqlSelect();
ArrayList<NwsRMainCat> al = super.executeQuery(sql, rv, new NwsRMainCat(), FIELD_LIST);
if(al.size()>0){
NwsRMainCat o = al.get(0);
al.clear();
return o;
}else{
return null;
}
}
/**
	 * 根据主键删除一条记录
	*@param paraNwsCatId 目录编号
	*@param paraNwsId 新闻编号
	*@return 是否成功
	*/
public boolean deleteRecord(Long paraNwsCatId, Long paraNwsId){
	RequestValue rv = new RequestValue();
rv.addValue("NWS_CAT_ID", paraNwsCatId, "Long", 19);
rv.addValue("NWS_ID", paraNwsId, "Long", 19);
	return super.executeUpdate(super.createDeleteSql() , rv);
}
}