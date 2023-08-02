package com.gdxsoft.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** 表OA_PRJ_REQ操作类
 * @author gdx 时间：Tue Jan 15 2019 16:09:05 GMT+0800 (中国标准时间)
 */
public class OaPrjReqDao extends ClassDaoBase<OaPrjReq> implements IClassDao<OaPrjReq>{

 private static String SQL_SELECT="SELECT * FROM OA_PRJ_REQ WHERE PRJ_ID=@PRJ_ID AND REQ_ID=@REQ_ID";
 private static String SQL_UPDATE="UPDATE OA_PRJ_REQ SET WHERE PRJ_ID=@PRJ_ID AND REQ_ID=@REQ_ID";
 private static String SQL_DELETE="DELETE FROM OA_PRJ_REQ WHERE PRJ_ID=@PRJ_ID AND REQ_ID=@REQ_ID";
 private static String SQL_INSERT="INSERT INTO OA_PRJ_REQ(PRJ_ID, REQ_ID) 	VALUES(@PRJ_ID, @REQ_ID)";
 public static String TABLE_NAME ="OA_PRJ_REQ";
 public static String[] KEY_LIST = { "PRJ_ID", "REQ_ID"   };
 public static String[] FIELD_LIST = { "PRJ_ID", "REQ_ID" };
 public OaPrjReqDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("globaltravel");
 }/**
	 * 生成一条记录
	*@param para 表OA_PRJ_REQ的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(OaPrjReq para){

		RequestValue rv=this.createRequestValue(para);

		return super.executeUpdate(SQL_INSERT, rv);

	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表OA_PRJ_REQ的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(OaPrjReq para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(sql, rv);
}/**
 * 更新一条记录
*@param para 表OA_PRJ_REQ的映射类
	 *@return 是否成功 
	 */
public boolean updateRecord(OaPrjReq para){

  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(SQL_UPDATE, rv);
}
/**
 * 更新一条记录
 * 
 * @param para
 *            表OA_PRJ_REQ的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean updateRecord(OaPrjReq para, HashMap<String, Boolean> updateFields){
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
public String getSqlSelect() {return "SELECT * FROM OA_PRJ_REQ where 1=1";}
public String getSqlUpdate() {return SQL_UPDATE;}
public String getSqlInsert() {return SQL_INSERT;}/**
	 * 根据主键返回一条记录
	*@param paraPrjId PRJ_ID
	*@param paraReqId REQ_ID
	*@return 记录类(OaPrjReq)
	*/
public OaPrjReq getRecord(Integer paraPrjId, Integer paraReqId){
	RequestValue rv = new RequestValue();
rv.addValue("PRJ_ID", paraPrjId, "Integer", 10);
rv.addValue("REQ_ID", paraReqId, "Integer", 10);
ArrayList<OaPrjReq> al = super.executeQuery(SQL_SELECT, rv, new OaPrjReq(), FIELD_LIST);
if(al.size()>0){
OaPrjReq o = al.get(0);
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
	public ArrayList<OaPrjReq> getRecords(String whereString){
		String sql="SELECT * FROM OA_PRJ_REQ WHERE " + whereString;
		return super.executeQuery(sql, new OaPrjReq(), FIELD_LIST);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	*@param  fields 指定返回的字段
	*@return 记录集合
	*/
	public ArrayList<OaPrjReq> getRecords(String whereString, List<String> fields){
		String sql = super.createSelectSql(TABLE_NAME, whereString, fields);
		String[] arrFields = new String[fields.size()];
		arrFields = fields.toArray(arrFields);
		return super.executeQuery(sql, new OaPrjReq(), arrFields);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	* @param pkFieldName   主键
	* @param pageSize      每页记录数
	* @param currentPage   当前页
	*@return 记录集合
	*/
	public ArrayList<OaPrjReq> getRecords(String whereString, String pkFieldName, int pageSize, int currentPage){
		String sql="SELECT * FROM OA_PRJ_REQ WHERE " + whereString;
		return super.executeQuery(sql, new OaPrjReq(), FIELD_LIST, pkFieldName, pageSize, currentPage);
	}
/**
	 * 根据主键删除一条记录
	*@param paraPrjId PRJ_ID
	*@param paraReqId REQ_ID
	*@return 是否成功
	*/
public boolean deleteRecord(Integer paraPrjId, Integer paraReqId){
	RequestValue rv = new RequestValue();
rv.addValue("PRJ_ID", paraPrjId, "Integer", 10);
rv.addValue("REQ_ID", paraReqId, "Integer", 10);
	return super.executeUpdate(SQL_DELETE, rv);
}
public RequestValue createRequestValue(OaPrjReq para){
RequestValue rv = new RequestValue();
rv.addValue("PRJ_ID", para.getPrjId(), "Integer", 10); // PRJ_ID
rv.addValue("REQ_ID", para.getReqId(), "Integer", 10); // REQ_ID
return rv;
	}}