package com.gdxsoft.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** 表ADM_ACL_MNU操作类
 * @author gdx 时间：Fri Jan 18 2019 11:21:39 GMT+0800 (中国标准时间)
 */
public class AdmAclMnuDao extends ClassDaoBase<AdmAclMnu> implements IClassDao<AdmAclMnu>{

 private static String SQL_SELECT="SELECT * FROM ADM_ACL_MNU WHERE MNU_ID=@MNU_ID AND ADM_ID=@ADM_ID";
 private static String SQL_UPDATE="UPDATE ADM_ACL_MNU SET 	 SUP_ID = @SUP_ID WHERE MNU_ID=@MNU_ID AND ADM_ID=@ADM_ID";
 private static String SQL_DELETE="DELETE FROM ADM_ACL_MNU WHERE MNU_ID=@MNU_ID AND ADM_ID=@ADM_ID";
 private static String SQL_INSERT="INSERT INTO ADM_ACL_MNU(MNU_ID, ADM_ID, SUP_ID) 	VALUES(@MNU_ID, @ADM_ID, @SUP_ID)";
 public static String TABLE_NAME ="ADM_ACL_MNU";
 public static String[] KEY_LIST = { "MNU_ID", "ADM_ID"   };
 public static String[] FIELD_LIST = { "MNU_ID", "ADM_ID", "SUP_ID" };
 public AdmAclMnuDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("globaltravel");
 }/**
	 * 生成一条记录
	*@param para 表ADM_ACL_MNU的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(AdmAclMnu para){

		RequestValue rv=this.createRequestValue(para);

		return super.executeUpdate(SQL_INSERT, rv);

	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表ADM_ACL_MNU的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(AdmAclMnu para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(sql, rv);
}/**
 * 更新一条记录
*@param para 表ADM_ACL_MNU的映射类
	 *@return 是否成功 
	 */
public boolean updateRecord(AdmAclMnu para){

  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(SQL_UPDATE, rv);
}
/**
 * 更新一条记录
 * 
 * @param para
 *            表ADM_ACL_MNU的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean updateRecord(AdmAclMnu para, HashMap<String, Boolean> updateFields){
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
public String getSqlSelect() {return "SELECT * FROM ADM_ACL_MNU where 1=1";}
public String getSqlUpdate() {return SQL_UPDATE;}
public String getSqlInsert() {return SQL_INSERT;}/**
	 * 根据主键返回一条记录
	*@param paraMnuId MNU_ID
	*@param paraAdmId ADM_ID
	*@return 记录类(AdmAclMnu)
	*/
public AdmAclMnu getRecord(Integer paraMnuId, Integer paraAdmId){
	RequestValue rv = new RequestValue();
rv.addValue("MNU_ID", paraMnuId, "Integer", 10);
rv.addValue("ADM_ID", paraAdmId, "Integer", 10);
ArrayList<AdmAclMnu> al = super.executeQuery(SQL_SELECT, rv, new AdmAclMnu(), FIELD_LIST);
if(al.size()>0){
AdmAclMnu o = al.get(0);
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
	public ArrayList<AdmAclMnu> getRecords(String whereString){
		String sql="SELECT * FROM ADM_ACL_MNU WHERE " + whereString;
		return super.executeQuery(sql, new AdmAclMnu(), FIELD_LIST);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	*@param  fields 指定返回的字段
	*@return 记录集合
	*/
	public ArrayList<AdmAclMnu> getRecords(String whereString, List<String> fields){
		String sql = super.createSelectSql(TABLE_NAME, whereString, fields);
		String[] arrFields = new String[fields.size()];
		arrFields = fields.toArray(arrFields);
		return super.executeQuery(sql, new AdmAclMnu(), arrFields);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	* @param pkFieldName   主键
	* @param pageSize      每页记录数
	* @param currentPage   当前页
	*@return 记录集合
	*/
	public ArrayList<AdmAclMnu> getRecords(String whereString, String pkFieldName, int pageSize, int currentPage){
		String sql="SELECT * FROM ADM_ACL_MNU WHERE " + whereString;
		return super.executeQuery(sql, new AdmAclMnu(), FIELD_LIST, pkFieldName, pageSize, currentPage);
	}
/**
	 * 根据主键删除一条记录
	*@param paraMnuId MNU_ID
	*@param paraAdmId ADM_ID
	*@return 是否成功
	*/
public boolean deleteRecord(Integer paraMnuId, Integer paraAdmId){
	RequestValue rv = new RequestValue();
rv.addValue("MNU_ID", paraMnuId, "Integer", 10);
rv.addValue("ADM_ID", paraAdmId, "Integer", 10);
	return super.executeUpdate(SQL_DELETE, rv);
}
public RequestValue createRequestValue(AdmAclMnu para){
RequestValue rv = new RequestValue();
rv.addValue("MNU_ID", para.getMnuId(), "Integer", 10); // MNU_ID
rv.addValue("ADM_ID", para.getAdmId(), "Integer", 10); // ADM_ID
rv.addValue("SUP_ID", para.getSupId(), "Integer", 10); // SUP_ID
return rv;
	}}