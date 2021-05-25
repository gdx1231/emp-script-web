package com.gdxsoft.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** 表sys_default操作类
 * @author gdx 时间：Sun Apr 26 2020 16:50:01 GMT+0800 (中国标准时间)
 */
public class SysDefaultDao extends ClassDaoBase<SysDefault> implements IClassDao<SysDefault>{

 private static String SQL_SELECT="SELECT * FROM sys_default WHERE TAG=@TAG AND SUP_ID=@SUP_ID";
 private static String SQL_UPDATE="UPDATE sys_default SET 	 DEFAULT_VALUE = @DEFAULT_VALUE, 	 DEFAULT_VALUE2 = @DEFAULT_VALUE2, 	 CDATE = @CDATE, 	 MDATE = @MDATE, 	 ADM_ID = @ADM_ID, 	 CATALOG = @CATALOG, 	 DEFAULT_VALUE3 = @DEFAULT_VALUE3 WHERE TAG=@TAG AND SUP_ID=@SUP_ID";
 private static String SQL_DELETE="DELETE FROM sys_default WHERE TAG=@TAG AND SUP_ID=@SUP_ID";
 private static String SQL_INSERT="INSERT INTO sys_default(TAG, DEFAULT_VALUE, DEFAULT_VALUE2, SUP_ID, CDATE, MDATE, ADM_ID, CATALOG, DEFAULT_VALUE3) 	VALUES(@TAG, @DEFAULT_VALUE, @DEFAULT_VALUE2, @SUP_ID, @CDATE, @MDATE, @ADM_ID, @CATALOG, @DEFAULT_VALUE3)";
 public static String TABLE_NAME ="sys_default";
 public static String[] KEY_LIST = { "TAG", "SUP_ID"   };
 public static String[] FIELD_LIST = { "TAG", "DEFAULT_VALUE", "DEFAULT_VALUE2", "SUP_ID", "CDATE", "MDATE", "ADM_ID", "CATALOG", "DEFAULT_VALUE3" };
 public SysDefaultDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("ex");
 }/**
	 * 生成一条记录
	*@param para 表sys_default的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(SysDefault para){

		RequestValue rv=this.createRequestValue(para);

		return super.executeUpdate(SQL_INSERT, rv);

	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表sys_default的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(SysDefault para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(sql, rv);
}/**
 * 更新一条记录
*@param para 表sys_default的映射类
	 *@return 是否成功 
	 */
public boolean updateRecord(SysDefault para){

  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(SQL_UPDATE, rv);
}
/**
 * 更新一条记录
 * 
 * @param para
 *            表sys_default的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean updateRecord(SysDefault para, HashMap<String, Boolean> updateFields){
  // 没定义主键的话不能更新
  if(KEY_LIST.length==0){return false; } 
  String sql = super.sqlUpdateChanged(TABLE_NAME, KEY_LIST, updateFields);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(sql, rv);
}
public String getSqlDelete() {return SQL_DELETE;}
public String[] getSqlFields() {return FIELD_LIST;}
public String getSqlSelect() {return "SELECT * FROM sys_default where 1=1";}
public String getSqlUpdate() {return SQL_UPDATE;}
public String getSqlInsert() {return SQL_INSERT;}/**
	 * 根据主键返回一条记录
	*@param paraTag TAG
	*@param paraSupId SUP_ID
	*@return 记录类(SysDefault)
	*/
public SysDefault getRecord(String paraTag, Integer paraSupId){
	RequestValue rv = new RequestValue();
rv.addValue("TAG", paraTag, "String", 50);
rv.addValue("SUP_ID", paraSupId, "Integer", 10);
ArrayList<SysDefault> al = super.executeQuery(SQL_SELECT, rv, new SysDefault(), FIELD_LIST);
if(al.size()>0){
SysDefault o = al.get(0);
al.clear();
return o;
}else{
return null;
}
}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	*@return 记录集合
	*/
	public ArrayList<SysDefault> getRecords(String whereString){
		String sql="SELECT * FROM sys_default WHERE " + whereString;
		return super.executeQuery(sql, new SysDefault(), FIELD_LIST);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	*@param  fields 指定返回的字段
	*@return 记录集合
	*/
	public ArrayList<SysDefault> getRecords(String whereString, List<String> fields){
		String sql = super.createSelectSql(TABLE_NAME, whereString, fields);
		String[] arrFields = new String[fields.size()];
		arrFields = fields.toArray(arrFields);
		return super.executeQuery(sql, new SysDefault(), arrFields);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	* @param pkFieldName   主键
	* @param pageSize      每页记录数
	* @param currentPage   当前页
	*@return 记录集合
	*/
	public ArrayList<SysDefault> getRecords(String whereString, String pkFieldName, int pageSize, int currentPage){
		String sql="SELECT * FROM sys_default WHERE " + whereString;
		return super.executeQuery(sql, new SysDefault(), FIELD_LIST, pkFieldName, pageSize, currentPage);
	}
/**
	 * 根据主键删除一条记录
	*@param paraTag TAG
	*@param paraSupId SUP_ID
	*@return 是否成功
	*/
public boolean deleteRecord(String paraTag, Integer paraSupId){
	RequestValue rv = new RequestValue();
rv.addValue("TAG", paraTag, "String", 50);
rv.addValue("SUP_ID", paraSupId, "Integer", 10);
	return super.executeUpdate(SQL_DELETE, rv);
}
public RequestValue createRequestValue(SysDefault para){
RequestValue rv = new RequestValue();
rv.addValue("TAG", para.getTag(), "String", 50); // TAG
rv.addValue("DEFAULT_VALUE", para.getDefaultValue(), "String", 16777215); // DEFAULT_VALUE
rv.addValue("DEFAULT_VALUE2", para.getDefaultValue2(), "Integer", 10); // DEFAULT_VALUE2
rv.addValue("SUP_ID", para.getSupId(), "Integer", 10); // SUP_ID
rv.addValue("CDATE", para.getCdate(), "Date", 19); // CDATE
rv.addValue("MDATE", para.getMdate(), "Date", 19); // MDATE
rv.addValue("ADM_ID", para.getAdmId(), "Integer", 10); // ADM_ID
rv.addValue("CATALOG", para.getCatalog(), "String", 20); // CATALOG
rv.addValue("DEFAULT_VALUE3", para.getDefaultValue3(), "byte[]", 65535); // DEFAULT_VALUE3
return rv;
	}}