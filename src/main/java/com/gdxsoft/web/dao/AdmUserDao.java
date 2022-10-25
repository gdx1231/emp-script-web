package com.gdxsoft.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** 表ADM_USER操作类
 * @author gdx 时间：Mon Jan 07 2019 17:29:06 GMT+0800 (中国标准时间)
 */
public class AdmUserDao extends ClassDaoBase<AdmUser> implements IClassDao<AdmUser>{

 private static String SQL_SELECT="SELECT * FROM ADM_USER WHERE ADM_ID=@ADM_ID";
 private static String SQL_UPDATE="UPDATE ADM_USER SET 	 SUP_ID = @SUP_ID, 	 SEX_TAG = @SEX_TAG, 	 ADM_USR_STA_TAG = @ADM_USR_STA_TAG, 	 ADM_DEP_ID = @ADM_DEP_ID, 	 ADM_LID = @ADM_LID, 	 ADM_PWD = @ADM_PWD, 	 ADM_NAME = @ADM_NAME, 	 ADM_NAME_EN = @ADM_NAME_EN, 	 ADM_IDCARD = @ADM_IDCARD, 	 ADM_TELE = @ADM_TELE, 	 ADM_EMAIL = @ADM_EMAIL, 	 ADM_QQ = @ADM_QQ, 	 ADM_MSN = @ADM_MSN, 	 ADM_ICQ = @ADM_ICQ, 	 ADM_MOBILE = @ADM_MOBILE, 	 ADM_ADDR = @ADM_ADDR, 	 ADM_CDATE = @ADM_CDATE, 	 ADM_MDATE = @ADM_MDATE, 	 ADM_LDATE = @ADM_LDATE, 	 ADM_LIP = @ADM_LIP, 	 ADM_PHOTO = @ADM_PHOTO, 	 CRM_COM_ID = @CRM_COM_ID, 	 PID = @PID, 	 ADM_CHG_PWD = @ADM_CHG_PWD, 	 ADM_SIGN_PIC = @ADM_SIGN_PIC, 	 ADM_UNID = @ADM_UNID, 	 SOURCE = @SOURCE, 	 SOURCE_ID = @SOURCE_ID, 	 ERP_SKIN = @ERP_SKIN, 	 ADM_OP = @ADM_OP, 	 CRM_CUS_ID = @CRM_CUS_ID, 	 WEB_USR_ID = @WEB_USR_ID, 	 SUR_ID = @SUR_ID, 	 IOS_TOKEN = @IOS_TOKEN, 	 AUS_BRANCHID = @AUS_BRANCHID, 	 AUS_POSTALCODE = @AUS_POSTALCODE, 	 AUS_TAID = @AUS_TAID, 	 AUS_TITLE = @AUS_TITLE, 	 AUS_EPASSWORD = @AUS_EPASSWORD, 	 AUS_STATEORPROVINCE = @AUS_STATEORPROVINCE, 	 AUS_AUTH = @AUS_AUTH, 	 AUS_SMTPSVER = @AUS_SMTPSVER, 	 AUS_TAEID = @AUS_TAEID, 	 AUS_PHOTO = @AUS_PHOTO, 	 AUS_POSITION = @AUS_POSITION, 	 AUS_ACTIVE = @AUS_ACTIVE, 	 AUS_CITY = @AUS_CITY, 	 IS_AUS = @IS_AUS, 	 SUP_UNID = @SUP_UNID WHERE ADM_ID=@ADM_ID";
 private static String SQL_DELETE="DELETE FROM ADM_USER WHERE ADM_ID=@ADM_ID";
 private static String SQL_INSERT="INSERT INTO ADM_USER(SUP_ID, SEX_TAG, ADM_USR_STA_TAG, ADM_DEP_ID, ADM_LID, ADM_PWD, ADM_NAME, ADM_NAME_EN, ADM_IDCARD, ADM_TELE, ADM_EMAIL, ADM_QQ, ADM_MSN, ADM_ICQ, ADM_MOBILE, ADM_ADDR, ADM_CDATE, ADM_MDATE, ADM_LDATE, ADM_LIP, ADM_PHOTO, CRM_COM_ID, PID, ADM_CHG_PWD, ADM_SIGN_PIC, ADM_UNID, SOURCE, SOURCE_ID, ERP_SKIN, ADM_OP, CRM_CUS_ID, WEB_USR_ID, SUR_ID, IOS_TOKEN, AUS_BRANCHID, AUS_POSTALCODE, AUS_TAID, AUS_TITLE, AUS_EPASSWORD, AUS_STATEORPROVINCE, AUS_AUTH, AUS_SMTPSVER, AUS_TAEID, AUS_PHOTO, AUS_POSITION, AUS_ACTIVE, AUS_CITY, IS_AUS, SUP_UNID) 	VALUES(@SUP_ID, @SEX_TAG, @ADM_USR_STA_TAG, @ADM_DEP_ID, @ADM_LID, @ADM_PWD, @ADM_NAME, @ADM_NAME_EN, @ADM_IDCARD, @ADM_TELE, @ADM_EMAIL, @ADM_QQ, @ADM_MSN, @ADM_ICQ, @ADM_MOBILE, @ADM_ADDR, @ADM_CDATE, @ADM_MDATE, @ADM_LDATE, @ADM_LIP, @ADM_PHOTO, @CRM_COM_ID, @PID, @ADM_CHG_PWD, @ADM_SIGN_PIC, @ADM_UNID, @SOURCE, @SOURCE_ID, @ERP_SKIN, @ADM_OP, @CRM_CUS_ID, @WEB_USR_ID, @SUR_ID, @IOS_TOKEN, @AUS_BRANCHID, @AUS_POSTALCODE, @AUS_TAID, @AUS_TITLE, @AUS_EPASSWORD, @AUS_STATEORPROVINCE, @AUS_AUTH, @AUS_SMTPSVER, @AUS_TAEID, @AUS_PHOTO, @AUS_POSITION, @AUS_ACTIVE, @AUS_CITY, @IS_AUS, @SUP_UNID)";
 public static String TABLE_NAME ="ADM_USER";
 public static String[] KEY_LIST = { "ADM_ID"   };
 public static String[] FIELD_LIST = { "ADM_ID", "SUP_ID", "SEX_TAG", "ADM_USR_STA_TAG", "ADM_DEP_ID", "ADM_LID", "ADM_PWD", "ADM_NAME", "ADM_NAME_EN", "ADM_IDCARD", "ADM_TELE", "ADM_EMAIL", "ADM_QQ", "ADM_MSN", "ADM_ICQ", "ADM_MOBILE", "ADM_ADDR", "ADM_CDATE", "ADM_MDATE", "ADM_LDATE", "ADM_LIP", "ADM_PHOTO", "CRM_COM_ID", "PID", "ADM_CHG_PWD", "ADM_SIGN_PIC", "ADM_UNID", "SOURCE", "SOURCE_ID", "ERP_SKIN", "ADM_OP", "CRM_CUS_ID", "WEB_USR_ID", "SUR_ID", "IOS_TOKEN", "AUS_BRANCHID", "AUS_POSTALCODE", "AUS_TAID", "AUS_TITLE", "AUS_EPASSWORD", "AUS_STATEORPROVINCE", "AUS_AUTH", "AUS_SMTPSVER", "AUS_TAEID", "AUS_PHOTO", "AUS_POSITION", "AUS_ACTIVE", "AUS_CITY", "IS_AUS", "SUP_UNID" };
 public AdmUserDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("globaltravel");
 }/**
	 * 生成一条记录
	*@param para 表ADM_USER的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(AdmUser para){

		RequestValue rv=this.createRequestValue(para);

int autoKey = super.executeUpdateAutoIncrement(SQL_INSERT, rv);
if (autoKey > 0) {
para.setAdmId(autoKey);//自增
return true;
} else {
return false;
}
	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表ADM_USER的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(AdmUser para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
int autoKey = super.executeUpdateAutoIncrement(SQL_INSERT, rv);//自增
if (autoKey > 0) {
para.setAdmId(autoKey);
return true;
} else {
return false;
}
}/**
 * 更新一条记录
*@param para 表ADM_USER的映射类
	 *@return 是否成功 
	 */
public boolean updateRecord(AdmUser para){

  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(SQL_UPDATE, rv);
}
/**
 * 更新一条记录
 * 
 * @param para
 *            表ADM_USER的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean updateRecord(AdmUser para, HashMap<String, Boolean> updateFields){
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
public String getSqlSelect() {return "SELECT * FROM ADM_USER where 1=1";}
public String getSqlUpdate() {return SQL_UPDATE;}
public String getSqlInsert() {return SQL_INSERT;}/**
	 * 根据主键返回一条记录
	*@param paraAdmId 编号
	*@return 记录类(AdmUser)
	*/
public AdmUser getRecord(Integer paraAdmId){
	RequestValue rv = new RequestValue();
rv.addValue("ADM_ID", paraAdmId, "Integer", 10);
ArrayList<AdmUser> al = super.executeQuery(SQL_SELECT, rv, new AdmUser(), FIELD_LIST);
if(al.size()>0){
AdmUser o = al.get(0);
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
	public ArrayList<AdmUser> getRecords(String whereString){
		String sql="SELECT * FROM ADM_USER WHERE " + whereString;
		return super.executeQuery(sql, new AdmUser(), FIELD_LIST);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	*@param  fields 指定返回的字段
	*@return 记录集合
	*/
	public ArrayList<AdmUser> getRecords(String whereString, List<String> fields){
		String sql = super.createSelectSql(TABLE_NAME, whereString, fields);
		String[] arrFields = new String[fields.size()];
		arrFields = fields.toArray(arrFields);
		return super.executeQuery(sql, new AdmUser(), arrFields);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	* @param pkFieldName   主键
	* @param pageSize      每页记录数
	* @param currentPage   当前页
	*@return 记录集合
	*/
	public ArrayList<AdmUser> getRecords(String whereString, String pkFieldName, int pageSize, int currentPage){
		String sql="SELECT * FROM ADM_USER WHERE " + whereString;
		return super.executeQuery(sql, new AdmUser(), FIELD_LIST, pkFieldName, pageSize, currentPage);
	}
/**
	 * 根据主键删除一条记录
	*@param paraAdmId 编号
	*@return 是否成功
	*/
public boolean deleteRecord(Integer paraAdmId){
	RequestValue rv = new RequestValue();
rv.addValue("ADM_ID", paraAdmId, "Integer", 10);
	return super.executeUpdate(SQL_DELETE, rv);
}
public RequestValue createRequestValue(AdmUser para){
RequestValue rv = new RequestValue();
rv.addValue("ADM_ID", para.getAdmId(), "Integer", 10); // 编号
rv.addValue("SUP_ID", para.getSupId(), "Integer", 10); // 供应商编号
rv.addValue("SEX_TAG", para.getSexTag(), "String", 1); // 性别标记
rv.addValue("ADM_USR_STA_TAG", para.getAdmUsrStaTag(), "String", 3); // ADM_USR_STA_TAG
rv.addValue("ADM_DEP_ID", para.getAdmDepId(), "Integer", 10); // 所属部门
rv.addValue("ADM_LID", para.getAdmLid(), "String", 100); // 登录名
rv.addValue("ADM_PWD", para.getAdmPwd(), "String", 50); // 密码
rv.addValue("ADM_NAME", para.getAdmName(), "String", 50); // 姓名
rv.addValue("ADM_NAME_EN", para.getAdmNameEn(), "String", 50); // 英文名
rv.addValue("ADM_IDCARD", para.getAdmIdcard(), "String", 18); // 身份证
rv.addValue("ADM_TELE", para.getAdmTele(), "String", 100); // 电话
rv.addValue("ADM_EMAIL", para.getAdmEmail(), "String", 100); // 邮件
rv.addValue("ADM_QQ", para.getAdmQq(), "String", 100); // QQ号
rv.addValue("ADM_MSN", para.getAdmMsn(), "String", 100); // MSN号
rv.addValue("ADM_ICQ", para.getAdmIcq(), "String", 100); // ICQ
rv.addValue("ADM_MOBILE", para.getAdmMobile(), "String", 100); // 移动电话
rv.addValue("ADM_ADDR", para.getAdmAddr(), "String", 100); // 地址
rv.addValue("ADM_CDATE", para.getAdmCdate(), "Date", 23); // 创建日期
rv.addValue("ADM_MDATE", para.getAdmMdate(), "Date", 23); // 修改日期
rv.addValue("ADM_LDATE", para.getAdmLdate(), "Date", 23); // 登录日期
rv.addValue("ADM_LIP", para.getAdmLip(), "String", 45); // 登录IP
rv.addValue("ADM_PHOTO", para.getAdmPhoto(), "byte[]", 2147483647); // 照片
rv.addValue("CRM_COM_ID", para.getCrmComId(), "Integer", 10); // CRM_COM_ID
rv.addValue("PID", para.getPid(), "Integer", 10); // PID
rv.addValue("ADM_CHG_PWD", para.getAdmChgPwd(), "String", 1); // ADM_CHG_PWD
rv.addValue("ADM_SIGN_PIC", para.getAdmSignPic(), "byte[]", 2147483647); // 签名文件
rv.addValue("ADM_UNID", para.getAdmUnid(), "String", 36); // ADM_UNID
rv.addValue("SOURCE", para.getSource(), "String", 50); // SOURCE
rv.addValue("SOURCE_ID", para.getSourceId(), "Integer", 10); // SOURCE_ID
rv.addValue("ERP_SKIN", para.getErpSkin(), "String", 50); // ERP_SKIN
rv.addValue("ADM_OP", para.getAdmOp(), "String", 20); // ADM_OP
rv.addValue("CRM_CUS_ID", para.getCrmCusId(), "Integer", 10); // CRM_CUS_ID
rv.addValue("WEB_USR_ID", para.getWebUsrId(), "Integer", 10); // WEB_USR_ID
rv.addValue("SUR_ID", para.getSurId(), "Integer", 10); // 所属考核问卷id
rv.addValue("IOS_TOKEN", para.getIosToken(), "String", 64); // IOS PUSH 设备号码
rv.addValue("AUS_BRANCHID", para.getAusBranchid(), "Integer", 10); // AUS_BRANCHID
rv.addValue("AUS_POSTALCODE", para.getAusPostalcode(), "String", 20); // AUS_POSTALCODE
rv.addValue("AUS_TAID", para.getAusTaid(), "Integer", 10); // AUS_TAID
rv.addValue("AUS_TITLE", para.getAusTitle(), "String", 50); // AUS_TITLE
rv.addValue("AUS_EPASSWORD", para.getAusEpassword(), "String", 100); // AUS_EPASSWORD
rv.addValue("AUS_STATEORPROVINCE", para.getAusStateorprovince(), "String", 20); // AUS_STATEORPROVINCE
rv.addValue("AUS_AUTH", para.getAusAuth(), "String", 255); // AUS_AUTH
rv.addValue("AUS_SMTPSVER", para.getAusSmtpsver(), "String", 100); // AUS_SMTPSVER
rv.addValue("AUS_TAEID", para.getAusTaeid(), "Integer", 10); // AUS_TAEID
rv.addValue("AUS_PHOTO", para.getAusPhoto(), "String", 300); // AUS_PHOTO
rv.addValue("AUS_POSITION", para.getAusPosition(), "String", 50); // AUS_POSITION
rv.addValue("AUS_ACTIVE", para.getAusActive(), "String", 1); // AUS_ACTIVE
rv.addValue("AUS_CITY", para.getAusCity(), "String", 50); // AUS_CITY
rv.addValue("IS_AUS", para.getIsAus(), "Integer", 10); // IS_AUS
rv.addValue("SUP_UNID", para.getSupUnid(), "String", 36); // SUP_UNID
return rv;
	}}