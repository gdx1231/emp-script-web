package com.gdxsoft.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** 表web_user_level操作类
 * @author gdx 时间：Wed Jul 08 2020 14:14:14 GMT+0800 (中国标准时间)
 */
public class WebUserLevelDao extends ClassDaoBase<WebUserLevel> implements IClassDao<WebUserLevel>{

 private static String SQL_SELECT="SELECT * FROM web_user_level WHERE usr_lvl_id=@usr_lvl_id";
 private static String SQL_UPDATE="UPDATE web_user_level SET 	 usr_id = @usr_id, 	 usr_unid = @usr_unid, 	 usr_pid = @usr_pid, 	 usr_punid = @usr_punid, 	 create_date = @create_date, 	 sup_id = @sup_id, 	 usr_lvl = @usr_lvl, 	 usr_lvl_ip = @usr_lvl_ip, 	 usr_lvl_ua = @usr_lvl_ua, 	 usr_lvl_memo = @usr_lvl_memo WHERE usr_lvl_id=@usr_lvl_id";
 private static String SQL_DELETE="DELETE FROM web_user_level WHERE usr_lvl_id=@usr_lvl_id";
 private static String SQL_INSERT="INSERT INTO web_user_level(usr_id, usr_unid, usr_pid, usr_punid, create_date, sup_id, usr_lvl, usr_lvl_ip, usr_lvl_ua, usr_lvl_memo) 	VALUES(@usr_id, @usr_unid, @usr_pid, @usr_punid, @create_date, @sup_id, @usr_lvl, @usr_lvl_ip, @usr_lvl_ua, @usr_lvl_memo)";
 public static String TABLE_NAME ="web_user_level";
 public static String[] KEY_LIST = { "usr_lvl_id"   };
 public static String[] FIELD_LIST = { "usr_lvl_id", "usr_id", "usr_unid", "usr_pid", "usr_punid", "create_date", "sup_id", "usr_lvl", "usr_lvl_ip", "usr_lvl_ua", "usr_lvl_memo" };
 public WebUserLevelDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("ex");
 }/**
	 * 生成一条记录
	*@param para 表web_user_level的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(WebUserLevel para){

		RequestValue rv=this.createRequestValue(para);

int autoKey = super.executeUpdateAutoIncrement(SQL_INSERT, rv);
if (autoKey > 0) {
para.setUsrLvlId(autoKey);//自增
return true;
} else {
return false;
}
	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表web_user_level的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(WebUserLevel para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
int autoKey = super.executeUpdateAutoIncrement(sql, rv);//自增
if (autoKey > 0) {
para.setUsrLvlId(autoKey);
return true;
} else {
return false;
}
}/**
 * 更新一条记录
*@param para 表web_user_level的映射类
	 *@return 是否成功 
	 */
public boolean updateRecord(WebUserLevel para){

  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(SQL_UPDATE, rv);
}
/**
 * 更新一条记录
 * 
 * @param para
 *            表web_user_level的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean updateRecord(WebUserLevel para, HashMap<String, Boolean> updateFields){
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
public String getSqlSelect() {return "SELECT * FROM web_user_level where 1=1";}
public String getSqlUpdate() {return SQL_UPDATE;}
public String getSqlInsert() {return SQL_INSERT;}/**
	 * 根据主键返回一条记录
	*@param paraUsrLvlId usr_lvl_id
	*@return 记录类(WebUserLevel)
	*/
public WebUserLevel getRecord(Integer paraUsrLvlId){
	RequestValue rv = new RequestValue();
rv.addValue("USR_LVL_ID", paraUsrLvlId, "Integer", 10);
ArrayList<WebUserLevel> al = super.executeQuery(SQL_SELECT, rv, new WebUserLevel(), FIELD_LIST);
if(al.size()>0){
WebUserLevel o = al.get(0);
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
	public ArrayList<WebUserLevel> getRecords(String whereString){
		String sql="SELECT * FROM web_user_level WHERE " + whereString;
		return super.executeQuery(sql, new WebUserLevel(), FIELD_LIST);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	*@param  fields 指定返回的字段
	*@return 记录集合
	*/
	public ArrayList<WebUserLevel> getRecords(String whereString, List<String> fields){
		String sql = super.createSelectSql(TABLE_NAME, whereString, fields);
		String[] arrFields = new String[fields.size()];
		arrFields = fields.toArray(arrFields);
		return super.executeQuery(sql, new WebUserLevel(), arrFields);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	* @param pkFieldName   主键
	* @param pageSize      每页记录数
	* @param currentPage   当前页
	*@return 记录集合
	*/
	public ArrayList<WebUserLevel> getRecords(String whereString, String pkFieldName, int pageSize, int currentPage){
		String sql="SELECT * FROM web_user_level WHERE " + whereString;
		return super.executeQuery(sql, new WebUserLevel(), FIELD_LIST, pkFieldName, pageSize, currentPage);
	}
/**
	 * 根据主键删除一条记录
	*@param paraUsrLvlId usr_lvl_id
	*@return 是否成功
	*/
public boolean deleteRecord(Integer paraUsrLvlId){
	RequestValue rv = new RequestValue();
rv.addValue("USR_LVL_ID", paraUsrLvlId, "Integer", 10);
	return super.executeUpdate(SQL_DELETE, rv);
}
public RequestValue createRequestValue(WebUserLevel para){
RequestValue rv = new RequestValue();
rv.addValue("USR_LVL_ID", para.getUsrLvlId(), "Integer", 10); // usr_lvl_id
rv.addValue("USR_ID", para.getUsrId(), "Integer", 10); // 用户id
rv.addValue("USR_UNID", para.getUsrUnid(), "String", 36); // usr_unid
rv.addValue("USR_PID", para.getUsrPid(), "Integer", 10); // 父级用户id
rv.addValue("USR_PUNID", para.getUsrPunid(), "String", 36); // usr_punid
rv.addValue("CREATE_DATE", para.getCreateDate(), "Date", 19); // create_date
rv.addValue("SUP_ID", para.getSupId(), "Integer", 10); // sup_id
rv.addValue("USR_LVL", para.getUsrLvl(), "Integer", 10); // 等级
rv.addValue("USR_LVL_IP", para.getUsrLvlIp(), "String", 50); // 来源ip
rv.addValue("USR_LVL_UA", para.getUsrLvlUa(), "String", 500); // 浏览器
rv.addValue("USR_LVL_MEMO", para.getUsrLvlMemo(), "String", 500); // 备注
return rv;
	}}