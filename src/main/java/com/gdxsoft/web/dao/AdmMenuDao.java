package com.gdxsoft.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** 表ADM_MENU操作类
 * @author gdx 时间：Fri Jan 18 2019 11:21:49 GMT+0800 (中国标准时间)
 */
public class AdmMenuDao extends ClassDaoBase<AdmMenu> implements IClassDao<AdmMenu>{

 private static String SQL_SELECT="SELECT * FROM ADM_MENU WHERE  1>2";
 private static String SQL_UPDATE="UPDATE ADM_MENU SET 	 MNU_ID = @MNU_ID, 	 MNU_TEXT = @MNU_TEXT, 	 MNU_PID = @MNU_PID, 	 MNU_LVL = @MNU_LVL, 	 MNU_ORD = @MNU_ORD, 	 MNU_CMD = @MNU_CMD, 	 MNU_ICON = @MNU_ICON, 	 MNU_GRP = @MNU_GRP, 	 MNU_TAG = @MNU_TAG, 	 MNU_HLP = @MNU_HLP, 	 MNU_TEXT_EN = @MNU_TEXT_EN, 	 MNU_UNID = @MNU_UNID, 	 MNU_PY = @MNU_PY WHERE 1>2";
 private static String SQL_DELETE="DELETE FROM ADM_MENU WHERE  1>2";
 private static String SQL_INSERT="INSERT INTO ADM_MENU(MNU_TEXT, MNU_PID, MNU_LVL, MNU_ORD, MNU_CMD, MNU_ICON, MNU_GRP, MNU_TAG, MNU_HLP, MNU_TEXT_EN, MNU_UNID, MNU_PY) 	VALUES(@MNU_TEXT, @MNU_PID, @MNU_LVL, @MNU_ORD, @MNU_CMD, @MNU_ICON, @MNU_GRP, @MNU_TAG, @MNU_HLP, @MNU_TEXT_EN, @MNU_UNID, @MNU_PY)";
 public static String TABLE_NAME ="ADM_MENU";
 public static String[] KEY_LIST = {    };
 public static String[] FIELD_LIST = { "MNU_ID", "MNU_TEXT", "MNU_PID", "MNU_LVL", "MNU_ORD", "MNU_CMD", "MNU_ICON", "MNU_GRP", "MNU_TAG", "MNU_HLP", "MNU_TEXT_EN", "MNU_UNID", "MNU_PY" };
 public AdmMenuDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("globaltravel");
 }/**
	 * 生成一条记录
	*@param para 表ADM_MENU的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(AdmMenu para){

		RequestValue rv=this.createRequestValue(para);

int autoKey = super.executeUpdateAutoIncrement(SQL_INSERT, rv);
if (autoKey > 0) {
para.setMnuId(autoKey);//自增
return true;
} else {
return false;
}
	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表ADM_MENU的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(AdmMenu para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
int autoKey = super.executeUpdateAutoIncrement(sql, rv);//自增
if (autoKey > 0) {
para.setMnuId(autoKey);
return true;
} else {
return false;
}
}/**
 * 更新一条记录
*@param para 表ADM_MENU的映射类
	 *@return 是否成功 
	 */
public boolean updateRecord(AdmMenu para){

  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(SQL_UPDATE, rv);
}
/**
 * 更新一条记录
 * 
 * @param para
 *            表ADM_MENU的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean updateRecord(AdmMenu para, HashMap<String, Boolean> updateFields){
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
public String getSqlSelect() {return "SELECT * FROM ADM_MENU where 1=1";}
public String getSqlUpdate() {return SQL_UPDATE;}
public String getSqlInsert() {return SQL_INSERT;}/**
	 * 根据主键返回一条记录
	*@return 记录类(AdmMenu)
	*/
public AdmMenu getRecord(){
	RequestValue rv = new RequestValue();
ArrayList<AdmMenu> al = super.executeQuery(SQL_SELECT, rv, new AdmMenu(), FIELD_LIST);
if(al.size()>0){
AdmMenu o = al.get(0);
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
	public ArrayList<AdmMenu> getRecords(String whereString){
		String sql="SELECT * FROM ADM_MENU WHERE " + whereString;
		return super.executeQuery(sql, new AdmMenu(), FIELD_LIST);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	*@param  fields 指定返回的字段
	*@return 记录集合
	*/
	public ArrayList<AdmMenu> getRecords(String whereString, List<String> fields){
		String sql = super.createSelectSql(TABLE_NAME, whereString, fields);
		String[] arrFields = new String[fields.size()];
		arrFields = fields.toArray(arrFields);
		return super.executeQuery(sql, new AdmMenu(), arrFields);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	* @param pkFieldName   主键
	* @param pageSize      每页记录数
	* @param currentPage   当前页
	*@return 记录集合
	*/
	public ArrayList<AdmMenu> getRecords(String whereString, String pkFieldName, int pageSize, int currentPage){
		String sql="SELECT * FROM ADM_MENU WHERE " + whereString;
		return super.executeQuery(sql, new AdmMenu(), FIELD_LIST, pkFieldName, pageSize, currentPage);
	}
/**
	 * 根据主键删除一条记录
	*@return 是否成功
	*/
public boolean deleteRecord(){
	RequestValue rv = new RequestValue();
	return super.executeUpdate(SQL_DELETE, rv);
}
public RequestValue createRequestValue(AdmMenu para){
RequestValue rv = new RequestValue();
rv.addValue("MNU_ID", para.getMnuId(), "Integer", 10); // MNU_ID
rv.addValue("MNU_TEXT", para.getMnuText(), "String", 120); // MNU_TEXT
rv.addValue("MNU_PID", para.getMnuPid(), "Integer", 10); // MNU_PID
rv.addValue("MNU_LVL", para.getMnuLvl(), "Integer", 10); // MNU_LVL
rv.addValue("MNU_ORD", para.getMnuOrd(), "Integer", 10); // MNU_ORD
rv.addValue("MNU_CMD", para.getMnuCmd(), "String", 500); // MNU_CMD
rv.addValue("MNU_ICON", para.getMnuIcon(), "String", 200); // MNU_ICON
rv.addValue("MNU_GRP", para.getMnuGrp(), "String", 3); // MNU_GRP
rv.addValue("MNU_TAG", para.getMnuTag(), "String", 500); // MNU_TAG
rv.addValue("MNU_HLP", para.getMnuHlp(), "String", 1073741823); // MNU_HLP
rv.addValue("MNU_TEXT_EN", para.getMnuTextEn(), "String", 200); // MNU_TEXT_EN
rv.addValue("MNU_UNID", para.getMnuUnid(), "String", 36); // MNU_UNID
rv.addValue("MNU_PY", para.getMnuPy(), "String", 2147483647); // MNU_PY
return rv;
	}}