package com.gdxsoft.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** 表OA_PRJ操作类
 * @author gdx 时间：Thu Mar 28 2019 10:37:18 GMT+0800 (中国标准时间)
 */
public class OaPrjDao extends ClassDaoBase<OaPrj> implements IClassDao<OaPrj>{

 private static String SQL_SELECT="SELECT * FROM OA_PRJ WHERE PRJ_ID=@PRJ_ID";
 private static String SQL_UPDATE="UPDATE OA_PRJ SET 	 PRJ_NAME = @PRJ_NAME, 	 PRJ_NAME_EN = @PRJ_NAME_EN, 	 PRJ_MEMO = @PRJ_MEMO, 	 PRJ_MEMO_EN = @PRJ_MEMO_EN, 	 PRJ_MASTER_ID = @PRJ_MASTER_ID, 	 PRJ_CDATE = @PRJ_CDATE, 	 PRJ_MDATE = @PRJ_MDATE, 	 PRJ_UNID = @PRJ_UNID, 	 SUP_ID = @SUP_ID, 	 ADM_ID = @ADM_ID, 	 PRJ_STATUS = @PRJ_STATUS, 	 PRJ_ADM = @PRJ_ADM, 	 PRJ_REF_TABLE = @PRJ_REF_TABLE, 	 PRJ_REF_ID = @PRJ_REF_ID, 	 PRJ_IS_TEMPLATE = @PRJ_IS_TEMPLATE, 	 PRJ_START_TIME = @PRJ_START_TIME, 	 RRJ_END_TIME = @RRJ_END_TIME, 	 PRJ_RUN_STATUS = @PRJ_RUN_STATUS, 	 PRJ_SRC_XML_TEMP = @PRJ_SRC_XML_TEMP, 	 PRJ_SRC_XML_HASH = @PRJ_SRC_XML_HASH WHERE PRJ_ID=@PRJ_ID";
 private static String SQL_DELETE="DELETE FROM OA_PRJ WHERE PRJ_ID=@PRJ_ID";
 private static String SQL_INSERT="INSERT INTO OA_PRJ(PRJ_NAME, PRJ_NAME_EN, PRJ_MEMO, PRJ_MEMO_EN, PRJ_MASTER_ID, PRJ_CDATE, PRJ_MDATE, PRJ_UNID, SUP_ID, ADM_ID, PRJ_STATUS, PRJ_ADM, PRJ_REF_TABLE, PRJ_REF_ID, PRJ_IS_TEMPLATE, PRJ_START_TIME, RRJ_END_TIME, PRJ_RUN_STATUS, PRJ_SRC_XML_TEMP, PRJ_SRC_XML_HASH) 	VALUES(@PRJ_NAME, @PRJ_NAME_EN, @PRJ_MEMO, @PRJ_MEMO_EN, @PRJ_MASTER_ID, @PRJ_CDATE, @PRJ_MDATE, @PRJ_UNID, @SUP_ID, @ADM_ID, @PRJ_STATUS, @PRJ_ADM, @PRJ_REF_TABLE, @PRJ_REF_ID, @PRJ_IS_TEMPLATE, @PRJ_START_TIME, @RRJ_END_TIME, @PRJ_RUN_STATUS, @PRJ_SRC_XML_TEMP, @PRJ_SRC_XML_HASH)";
 public static String TABLE_NAME ="OA_PRJ";
 public static String[] KEY_LIST = { "PRJ_ID"   };
 public static String[] FIELD_LIST = { "PRJ_ID", "PRJ_NAME", "PRJ_NAME_EN", "PRJ_MEMO", "PRJ_MEMO_EN", "PRJ_MASTER_ID", "PRJ_CDATE", "PRJ_MDATE", "PRJ_UNID", "SUP_ID", "ADM_ID", "PRJ_STATUS", "PRJ_ADM", "PRJ_REF_TABLE", "PRJ_REF_ID", "PRJ_IS_TEMPLATE", "PRJ_START_TIME", "RRJ_END_TIME", "PRJ_RUN_STATUS", "PRJ_SRC_XML_TEMP", "PRJ_SRC_XML_HASH" };
 public OaPrjDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("globaltravel");
 }/**
	 * 生成一条记录
	*@param para 表OA_PRJ的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(OaPrj para){

		RequestValue rv=this.createRequestValue(para);

int autoKey = super.executeUpdateAutoIncrement(SQL_INSERT, rv);
if (autoKey > 0) {
para.setPrjId(autoKey);//自增
return true;
} else {
return false;
}
	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表OA_PRJ的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(OaPrj para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
int autoKey = super.executeUpdateAutoIncrement(sql, rv);//自增
if (autoKey > 0) {
para.setPrjId(autoKey);
return true;
} else {
return false;
}
}/**
 * 更新一条记录
*@param para 表OA_PRJ的映射类
	 *@return 是否成功 
	 */
public boolean updateRecord(OaPrj para){

  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(SQL_UPDATE, rv);
}
/**
 * 更新一条记录
 * 
 * @param para
 *            表OA_PRJ的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean updateRecord(OaPrj para, HashMap<String, Boolean> updateFields){
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
public String getSqlSelect() {return "SELECT * FROM OA_PRJ where 1=1";}
public String getSqlUpdate() {return SQL_UPDATE;}
public String getSqlInsert() {return SQL_INSERT;}/**
	 * 根据主键返回一条记录
	*@param paraPrjId 编号
	*@return 记录类(OaPrj)
	*/
public OaPrj getRecord(Integer paraPrjId){
	RequestValue rv = new RequestValue();
rv.addValue("PRJ_ID", paraPrjId, "Integer", 10);
ArrayList<OaPrj> al = super.executeQuery(SQL_SELECT, rv, new OaPrj(), FIELD_LIST);
if(al.size()>0){
OaPrj o = al.get(0);
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
	public ArrayList<OaPrj> getRecords(String whereString){
		String sql="SELECT * FROM OA_PRJ WHERE " + whereString;
		return super.executeQuery(sql, new OaPrj(), FIELD_LIST);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	*@param  fields 指定返回的字段
	*@return 记录集合
	*/
	public ArrayList<OaPrj> getRecords(String whereString, List<String> fields){
		String sql = super.createSelectSql(TABLE_NAME, whereString, fields);
		String[] arrFields = new String[fields.size()];
		arrFields = fields.toArray(arrFields);
		return super.executeQuery(sql, new OaPrj(), arrFields);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	* @param pkFieldName   主键
	* @param pageSize      每页记录数
	* @param currentPage   当前页
	*@return 记录集合
	*/
	public ArrayList<OaPrj> getRecords(String whereString, String pkFieldName, int pageSize, int currentPage){
		String sql="SELECT * FROM OA_PRJ WHERE " + whereString;
		return super.executeQuery(sql, new OaPrj(), FIELD_LIST, pkFieldName, pageSize, currentPage);
	}
/**
	 * 根据主键删除一条记录
	*@param paraPrjId 编号
	*@return 是否成功
	*/
public boolean deleteRecord(Integer paraPrjId){
	RequestValue rv = new RequestValue();
rv.addValue("PRJ_ID", paraPrjId, "Integer", 10);
	return super.executeUpdate(SQL_DELETE, rv);
}
public RequestValue createRequestValue(OaPrj para){
RequestValue rv = new RequestValue();
rv.addValue("PRJ_ID", para.getPrjId(), "Integer", 10); // 编号
rv.addValue("PRJ_NAME", para.getPrjName(), "String", 100); // 项目名称
rv.addValue("PRJ_NAME_EN", para.getPrjNameEn(), "String", 200); // 项目名称英文
rv.addValue("PRJ_MEMO", para.getPrjMemo(), "String", 2147483647); // 项目备注
rv.addValue("PRJ_MEMO_EN", para.getPrjMemoEn(), "String", 2147483647); // 项目备注英文
rv.addValue("PRJ_MASTER_ID", para.getPrjMasterId(), "Integer", 10); // 项目负责人
rv.addValue("PRJ_CDATE", para.getPrjCdate(), "Date", 23); // 创建时间
rv.addValue("PRJ_MDATE", para.getPrjMdate(), "Date", 23); // 修改时间
rv.addValue("PRJ_UNID", para.getPrjUnid(), "String", 36); // UNID
rv.addValue("SUP_ID", para.getSupId(), "Integer", 10); // 商户
rv.addValue("ADM_ID", para.getAdmId(), "Integer", 10); // 创建人
rv.addValue("PRJ_STATUS", para.getPrjStatus(), "String", 20); // 状态
rv.addValue("PRJ_ADM", para.getPrjAdm(), "String", 2147483647); // 参与人名单，分割
rv.addValue("PRJ_REF_TABLE", para.getPrjRefTable(), "String", 50); // 项目来源表
rv.addValue("PRJ_REF_ID", para.getPrjRefId(), "String", 50); // 项目来源ID
rv.addValue("PRJ_IS_TEMPLATE", para.getPrjIsTemplate(), "String", 1); // 是否为模板
rv.addValue("PRJ_START_TIME", para.getPrjStartTime(), "Date", 23); // 项目启动时间
rv.addValue("RRJ_END_TIME", para.getRrjEndTime(), "Date", 23); // 项目结束时间
rv.addValue("PRJ_RUN_STATUS", para.getPrjRunStatus(), "String", 20); // 运行状态
rv.addValue("PRJ_SRC_XML_TEMP", para.getPrjSrcXmlTemp(), "String", 200); // 来源模板
rv.addValue("PRJ_SRC_XML_HASH", para.getPrjSrcXmlHash(), "Integer", 10); // 来源模板的Hash
return rv;
	}}