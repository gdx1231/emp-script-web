package com.gdxsoft.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** 表SMS_JOB操作类
 * @author gdx 时间：Wed Feb 13 2019 15:29:43 GMT+0800 (中国标准时间)
 */
public class SmsJobDao extends ClassDaoBase<SmsJob> implements IClassDao<SmsJob>{

 private static String SQL_SELECT="SELECT * FROM SMS_JOB WHERE SMS_JID=@SMS_JID";
 private static String SQL_UPDATE="UPDATE SMS_JOB SET 	 SMS_JTITLE = @SMS_JTITLE, 	 SMS_JCNT = @SMS_JCNT, 	 SMS_JCDATE = @SMS_JCDATE, 	 SMS_JMDATE = @SMS_JMDATE, 	 SMS_JSTATUS = @SMS_JSTATUS, 	 SMS_PHONES = @SMS_PHONES, 	 ADM_ID = @ADM_ID, 	 SUP_ID = @SUP_ID, 	 SMS_REF_TABLE = @SMS_REF_TABLE, 	 SMS_REF_ID = @SMS_REF_ID, 	 SMS_PROVIDER = @SMS_PROVIDER, 	 SMS_TEMPLATE_CODE = @SMS_TEMPLATE_CODE, 	 SMS_SIGN_NAME = @SMS_SIGN_NAME, 	 SMS_TEMPLATE_JSON = @SMS_TEMPLATE_JSON, 	 SMS_OUT_ID = @SMS_OUT_ID, 	 MQ_MSG_ID = @MQ_MSG_ID, 	 MQ_MSG = @MQ_MSG WHERE SMS_JID=@SMS_JID";
 private static String SQL_DELETE="DELETE FROM SMS_JOB WHERE SMS_JID=@SMS_JID";
 private static String SQL_INSERT="INSERT INTO SMS_JOB(SMS_JTITLE, SMS_JCNT, SMS_JCDATE, SMS_JMDATE, SMS_JSTATUS, SMS_PHONES, ADM_ID, SUP_ID, SMS_REF_TABLE, SMS_REF_ID, SMS_PROVIDER, SMS_TEMPLATE_CODE, SMS_SIGN_NAME, SMS_TEMPLATE_JSON, SMS_OUT_ID, MQ_MSG_ID, MQ_MSG) 	VALUES(@SMS_JTITLE, @SMS_JCNT, @SMS_JCDATE, @SMS_JMDATE, @SMS_JSTATUS, @SMS_PHONES, @ADM_ID, @SUP_ID, @SMS_REF_TABLE, @SMS_REF_ID, @SMS_PROVIDER, @SMS_TEMPLATE_CODE, @SMS_SIGN_NAME, @SMS_TEMPLATE_JSON, @SMS_OUT_ID, @MQ_MSG_ID, @MQ_MSG)";
 public static String TABLE_NAME ="SMS_JOB";
 public static String[] KEY_LIST = { "SMS_JID"   };
 public static String[] FIELD_LIST = { "SMS_JID", "SMS_JTITLE", "SMS_JCNT", "SMS_JCDATE", "SMS_JMDATE", "SMS_JSTATUS", "SMS_PHONES", "ADM_ID", "SUP_ID", "SMS_REF_TABLE", "SMS_REF_ID", "SMS_PROVIDER", "SMS_TEMPLATE_CODE", "SMS_SIGN_NAME", "SMS_TEMPLATE_JSON", "SMS_OUT_ID", "MQ_MSG_ID", "MQ_MSG" };
 public SmsJobDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("globaltravel");
 }/**
	 * 生成一条记录
	*@param para 表SMS_JOB的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(SmsJob para){

		RequestValue rv=this.createRequestValue(para);

int autoKey = super.executeUpdateAutoIncrement(SQL_INSERT, rv);
if (autoKey > 0) {
para.setSmsJid(autoKey);//自增
return true;
} else {
return false;
}
	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表SMS_JOB的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(SmsJob para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
int autoKey = super.executeUpdateAutoIncrement(sql, rv);//自增
if (autoKey > 0) {
para.setSmsJid(autoKey);
return true;
} else {
return false;
}
}/**
 * 更新一条记录
*@param para 表SMS_JOB的映射类
	 *@return 是否成功 
	 */
public boolean updateRecord(SmsJob para){

  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(SQL_UPDATE, rv);
}
/**
 * 更新一条记录
 * 
 * @param para
 *            表SMS_JOB的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean updateRecord(SmsJob para, HashMap<String, Boolean> updateFields){
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
public String getSqlSelect() {return "SELECT * FROM SMS_JOB where 1=1";}
public String getSqlUpdate() {return SQL_UPDATE;}
public String getSqlInsert() {return SQL_INSERT;}/**
	 * 根据主键返回一条记录
	*@param paraSmsJid 编号
	*@return 记录类(SmsJob)
	*/
public SmsJob getRecord(Integer paraSmsJid){
	RequestValue rv = new RequestValue();
rv.addValue("SMS_JID", paraSmsJid, "Integer", 10);
ArrayList<SmsJob> al = super.executeQuery(SQL_SELECT, rv, new SmsJob(), FIELD_LIST);
if(al.size()>0){
SmsJob o = al.get(0);
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
	public ArrayList<SmsJob> getRecords(String whereString){
		String sql="SELECT * FROM SMS_JOB WHERE " + whereString;
		return super.executeQuery(sql, new SmsJob(), FIELD_LIST);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	*@param  fields 指定返回的字段
	*@return 记录集合
	*/
	public ArrayList<SmsJob> getRecords(String whereString, List<String> fields){
		String sql = super.createSelectSql(TABLE_NAME, whereString, fields);
		String[] arrFields = new String[fields.size()];
		arrFields = fields.toArray(arrFields);
		return super.executeQuery(sql, new SmsJob(), arrFields);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	* @param pkFieldName   主键
	* @param pageSize      每页记录数
	* @param currentPage   当前页
	*@return 记录集合
	*/
	public ArrayList<SmsJob> getRecords(String whereString, String pkFieldName, int pageSize, int currentPage){
		String sql="SELECT * FROM SMS_JOB WHERE " + whereString;
		return super.executeQuery(sql, new SmsJob(), FIELD_LIST, pkFieldName, pageSize, currentPage);
	}
/**
	 * 根据主键删除一条记录
	*@param paraSmsJid 编号
	*@return 是否成功
	*/
public boolean deleteRecord(Integer paraSmsJid){
	RequestValue rv = new RequestValue();
rv.addValue("SMS_JID", paraSmsJid, "Integer", 10);
	return super.executeUpdate(SQL_DELETE, rv);
}
public RequestValue createRequestValue(SmsJob para){
RequestValue rv = new RequestValue();
rv.addValue("SMS_JID", para.getSmsJid(), "Integer", 10); // 编号
rv.addValue("SMS_JTITLE", para.getSmsJtitle(), "String", 50); // 标题
rv.addValue("SMS_JCNT", para.getSmsJcnt(), "String", 500); // 内容
rv.addValue("SMS_JCDATE", para.getSmsJcdate(), "Date", 23); // 创建时间
rv.addValue("SMS_JMDATE", para.getSmsJmdate(), "Date", 23); // 修改时间
rv.addValue("SMS_JSTATUS", para.getSmsJstatus(), "String", 20); // 状态
rv.addValue("SMS_PHONES", para.getSmsPhones(), "String", 2147483647); // 电话号码
rv.addValue("ADM_ID", para.getAdmId(), "Integer", 10); // 管理员
rv.addValue("SUP_ID", para.getSupId(), "Integer", 10); // 供应商
rv.addValue("SMS_REF_TABLE", para.getSmsRefTable(), "String", 50); // 来源表
rv.addValue("SMS_REF_ID", para.getSmsRefId(), "String", 50); // 来源ID
rv.addValue("SMS_PROVIDER", para.getSmsProvider(), "String", 20); // 短信供应商
rv.addValue("SMS_TEMPLATE_CODE", para.getSmsTemplateCode(), "String", 30); // 短信模板编号
rv.addValue("SMS_SIGN_NAME", para.getSmsSignName(), "String", 20); // 短信签名
rv.addValue("SMS_TEMPLATE_JSON", para.getSmsTemplateJson(), "String", 2147483647); // 模板数据JSON
rv.addValue("SMS_OUT_ID", para.getSmsOutId(), "String", 50); // 短信发出ID
rv.addValue("MQ_MSG_ID", para.getMqMsgId(), "String", 32); // RocketMQ Id
rv.addValue("MQ_MSG", para.getMqMsg(), "String", 2147483647); // RocketMQ 原始信息
return rv;
	}}