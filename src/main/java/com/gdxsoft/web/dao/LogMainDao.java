package com.gdxsoft.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** 表LOG_MAIN操作类
 * @author gdx 时间：Thu Feb 14 2019 16:17:35 GMT+0800 (中国标准时间)
 */
public class LogMainDao extends ClassDaoBase<LogMain> implements IClassDao<LogMain>{

 private static String SQL_SELECT="SELECT * FROM LOG_MAIN WHERE LOG_ID=@LOG_ID";
 private static String SQL_UPDATE="UPDATE LOG_MAIN SET 	 LOG_SRC = @LOG_SRC, 	 LOG_SRC_ID0 = @LOG_SRC_ID0, 	 LOG_SRC_ID1 = @LOG_SRC_ID1, 	 LOG_TYPE = @LOG_TYPE, 	 LOG_LANG = @LOG_LANG, 	 LOG_MEMO = @LOG_MEMO, 	 LOG_PARA0 = @LOG_PARA0, 	 LOG_PARA1 = @LOG_PARA1, 	 LOG_PARA2 = @LOG_PARA2, 	 LOG_PARA3 = @LOG_PARA3, 	 LOG_PARA4 = @LOG_PARA4, 	 LOG_PARA5 = @LOG_PARA5, 	 LOG_PARA6 = @LOG_PARA6, 	 LOG_PARA7 = @LOG_PARA7, 	 LOG_PARA8 = @LOG_PARA8, 	 LOG_PARA9 = @LOG_PARA9, 	 LOG_XMLNAME = @LOG_XMLNAME, 	 LOG_ITEMNAME = @LOG_ITEMNAME, 	 LOG_ACTION = @LOG_ACTION, 	 LOG_IP = @LOG_IP, 	 LOG_AGENT = @LOG_AGENT, 	 LOG_TIME = @LOG_TIME, 	 SUP_ID = @SUP_ID, 	 ADM_ID = @ADM_ID, 	 MQ_MSG_ID = @MQ_MSG_ID, 	 MQ_MSG = @MQ_MSG, 	 LOG_READ = @LOG_READ, 	 LOG_TO_ADM_ID = @LOG_TO_ADM_ID WHERE LOG_ID=@LOG_ID";
 private static String SQL_DELETE="DELETE FROM LOG_MAIN WHERE LOG_ID=@LOG_ID";
 private static String SQL_INSERT="INSERT INTO LOG_MAIN(LOG_SRC, LOG_SRC_ID0, LOG_SRC_ID1, LOG_TYPE, LOG_LANG, LOG_MEMO, LOG_PARA0, LOG_PARA1, LOG_PARA2, LOG_PARA3, LOG_PARA4, LOG_PARA5, LOG_PARA6, LOG_PARA7, LOG_PARA8, LOG_PARA9, LOG_XMLNAME, LOG_ITEMNAME, LOG_ACTION, LOG_IP, LOG_AGENT, LOG_TIME, SUP_ID, ADM_ID, MQ_MSG_ID, MQ_MSG, LOG_READ, LOG_TO_ADM_ID) 	VALUES(@LOG_SRC, @LOG_SRC_ID0, @LOG_SRC_ID1, @LOG_TYPE, @LOG_LANG, @LOG_MEMO, @LOG_PARA0, @LOG_PARA1, @LOG_PARA2, @LOG_PARA3, @LOG_PARA4, @LOG_PARA5, @LOG_PARA6, @LOG_PARA7, @LOG_PARA8, @LOG_PARA9, @LOG_XMLNAME, @LOG_ITEMNAME, @LOG_ACTION, @LOG_IP, @LOG_AGENT, @LOG_TIME, @SUP_ID, @ADM_ID, @MQ_MSG_ID, @MQ_MSG, @LOG_READ, @LOG_TO_ADM_ID)";
 public static String TABLE_NAME ="LOG_MAIN";
 public static String[] KEY_LIST = { "LOG_ID"   };
 public static String[] FIELD_LIST = { "LOG_ID", "LOG_SRC", "LOG_SRC_ID0", "LOG_SRC_ID1", "LOG_TYPE", "LOG_LANG", "LOG_MEMO", "LOG_PARA0", "LOG_PARA1", "LOG_PARA2", "LOG_PARA3", "LOG_PARA4", "LOG_PARA5", "LOG_PARA6", "LOG_PARA7", "LOG_PARA8", "LOG_PARA9", "LOG_XMLNAME", "LOG_ITEMNAME", "LOG_ACTION", "LOG_IP", "LOG_AGENT", "LOG_TIME", "SUP_ID", "ADM_ID", "MQ_MSG_ID", "MQ_MSG", "LOG_READ", "LOG_TO_ADM_ID" };
 public LogMainDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("globaltravel");
 }/**
	 * 生成一条记录
	*@param para 表LOG_MAIN的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(LogMain para){

		RequestValue rv=this.createRequestValue(para);

int autoKey = super.executeUpdateAutoIncrement(SQL_INSERT, rv);
if (autoKey > 0) {
para.setLogId(autoKey);//自增
return true;
} else {
return false;
}
	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表LOG_MAIN的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(LogMain para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
int autoKey = super.executeUpdateAutoIncrement(sql, rv);//自增
if (autoKey > 0) {
para.setLogId(autoKey);
return true;
} else {
return false;
}
}/**
 * 更新一条记录
*@param para 表LOG_MAIN的映射类
	 *@return 是否成功 
	 */
public boolean updateRecord(LogMain para){

  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(SQL_UPDATE, rv);
}
/**
 * 更新一条记录
 * 
 * @param para
 *            表LOG_MAIN的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean updateRecord(LogMain para, HashMap<String, Boolean> updateFields){
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
public String getSqlSelect() {return "SELECT * FROM LOG_MAIN where 1=1";}
public String getSqlUpdate() {return SQL_UPDATE;}
public String getSqlInsert() {return SQL_INSERT;}/**
	 * 根据主键返回一条记录
	*@param paraLogId 编号
	*@return 记录类(LogMain)
	*/
public LogMain getRecord(Integer paraLogId){
	RequestValue rv = new RequestValue();
rv.addValue("LOG_ID", paraLogId, "Integer", 10);
ArrayList<LogMain> al = super.executeQuery(SQL_SELECT, rv, new LogMain(), FIELD_LIST);
if(al.size()>0){
LogMain o = al.get(0);
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
	public ArrayList<LogMain> getRecords(String whereString){
		String sql="SELECT * FROM LOG_MAIN WHERE " + whereString;
		return super.executeQuery(sql, new LogMain(), FIELD_LIST);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	*@param  fields 指定返回的字段
	*@return 记录集合
	*/
	public ArrayList<LogMain> getRecords(String whereString, List<String> fields){
		String sql = super.createSelectSql(TABLE_NAME, whereString, fields);
		String[] arrFields = new String[fields.size()];
		arrFields = fields.toArray(arrFields);
		return super.executeQuery(sql, new LogMain(), arrFields);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	* @param pkFieldName   主键
	* @param pageSize      每页记录数
	* @param currentPage   当前页
	*@return 记录集合
	*/
	public ArrayList<LogMain> getRecords(String whereString, String pkFieldName, int pageSize, int currentPage){
		String sql="SELECT * FROM LOG_MAIN WHERE " + whereString;
		return super.executeQuery(sql, new LogMain(), FIELD_LIST, pkFieldName, pageSize, currentPage);
	}
/**
	 * 根据主键删除一条记录
	*@param paraLogId 编号
	*@return 是否成功
	*/
public boolean deleteRecord(Integer paraLogId){
	RequestValue rv = new RequestValue();
rv.addValue("LOG_ID", paraLogId, "Integer", 10);
	return super.executeUpdate(SQL_DELETE, rv);
}
public RequestValue createRequestValue(LogMain para){
RequestValue rv = new RequestValue();
rv.addValue("LOG_ID", para.getLogId(), "Integer", 10); // 编号
rv.addValue("LOG_SRC", para.getLogSrc(), "String", 30); // 来源
rv.addValue("LOG_SRC_ID0", para.getLogSrcId0(), "String", 50); // 来源编号0
rv.addValue("LOG_SRC_ID1", para.getLogSrcId1(), "String", 50); // 来源编号1
rv.addValue("LOG_TYPE", para.getLogType(), "String", 50); // 类型
rv.addValue("LOG_LANG", para.getLogLang(), "String", 10); // 语言
rv.addValue("LOG_MEMO", para.getLogMemo(), "String", 2147483647); // 说明
rv.addValue("LOG_PARA0", para.getLogPara0(), "String", 2147483647); // 参数0
rv.addValue("LOG_PARA1", para.getLogPara1(), "String", 2147483647); // 参数1
rv.addValue("LOG_PARA2", para.getLogPara2(), "String", 2147483647); // 参数2
rv.addValue("LOG_PARA3", para.getLogPara3(), "String", 2147483647); // 参数3
rv.addValue("LOG_PARA4", para.getLogPara4(), "String", 2147483647); // 参数4
rv.addValue("LOG_PARA5", para.getLogPara5(), "String", 2147483647); // 参数5
rv.addValue("LOG_PARA6", para.getLogPara6(), "String", 2147483647); // 参数6
rv.addValue("LOG_PARA7", para.getLogPara7(), "String", 2147483647); // 参数7
rv.addValue("LOG_PARA8", para.getLogPara8(), "String", 2147483647); // 参数8
rv.addValue("LOG_PARA9", para.getLogPara9(), "String", 2147483647); // 参数9
rv.addValue("LOG_XMLNAME", para.getLogXmlname(), "String", 200); // 配置XMLNAME
rv.addValue("LOG_ITEMNAME", para.getLogItemname(), "String", 200); // 配置ITEMNAME
rv.addValue("LOG_ACTION", para.getLogAction(), "String", 50); // 执行的Action
rv.addValue("LOG_IP", para.getLogIp(), "String", 30); // 来源IP
rv.addValue("LOG_AGENT", para.getLogAgent(), "String", 2000); // 浏览器代理头
rv.addValue("LOG_TIME", para.getLogTime(), "Date", 23); // 时间
rv.addValue("SUP_ID", para.getSupId(), "Integer", 10); // SUP_ID
rv.addValue("ADM_ID", para.getAdmId(), "Integer", 10); // 操作者
rv.addValue("MQ_MSG_ID", para.getMqMsgId(), "String", 32); // MQ的编号
rv.addValue("MQ_MSG", para.getMqMsg(), "String", 2147483647); // MQ消息体
rv.addValue("LOG_READ", para.getLogRead(), "String", 1); // 已读标记
rv.addValue("LOG_TO_ADM_ID", para.getLogToAdmId(), "Integer", 10); // 消息对应的用户
return rv;
	}}