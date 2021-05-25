package com.gdxsoft.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** 表SUP_MAIN操作类
 * @author gdx 时间：Tue Feb 12 2019 10:54:35 GMT+0800 (中国标准时间)
 */
public class SupMainDao extends ClassDaoBase<SupMain> implements IClassDao<SupMain>{

 private static String SQL_SELECT="SELECT * FROM SUP_MAIN WHERE SUP_ID=@SUP_ID";
 private static String SQL_UPDATE="UPDATE SUP_MAIN SET 	 SUP_PID = @SUP_PID, 	 ARE_ID = @ARE_ID, 	 SUP_TAG = @SUP_TAG, 	 SUP_MTAG = @SUP_MTAG, 	 SUP_NAME = @SUP_NAME, 	 SUP_ADDR = @SUP_ADDR, 	 SUP_TELE = @SUP_TELE, 	 SUP_FAX = @SUP_FAX, 	 SUP_EMAIL = @SUP_EMAIL, 	 SUP_HTTP = @SUP_HTTP, 	 SUP_MOBILE = @SUP_MOBILE, 	 SUP_QQ = @SUP_QQ, 	 SUP_MSN = @SUP_MSN, 	 SUP_ICQ = @SUP_ICQ, 	 SUP_TAOBAO = @SUP_TAOBAO, 	 SUP_PAYPAL = @SUP_PAYPAL, 	 SUP_ZIP = @SUP_ZIP, 	 SUP_MEMO = @SUP_MEMO, 	 SUP_PACT = @SUP_PACT, 	 SUP_LICENCE = @SUP_LICENCE, 	 SUP_AG_PROFILE = @SUP_AG_PROFILE, 	 SUP_AG_ORDER = @SUP_AG_ORDER, 	 SUP_AG_CODE = @SUP_AG_CODE, 	 SUP_LOGO = @SUP_LOGO, 	 SUP_POST_URL = @SUP_POST_URL, 	 SUP_POST_EMAIL = @SUP_POST_EMAIL, 	 SER_TAG1 = @SER_TAG1, 	 CITY_ID1 = @CITY_ID1, 	 SIGN_TAG = @SIGN_TAG, 	 SIGN_TIME = @SIGN_TIME, 	 SIGN_PRICE = @SIGN_PRICE, 	 SUP_ARE_DES = @SUP_ARE_DES, 	 SUP_UNID = @SUP_UNID, 	 SUP_COIN_ID = @SUP_COIN_ID, 	 SUP_SCM_OPEN = @SUP_SCM_OPEN, 	 SUP_SCM_OPEN_SDATE = @SUP_SCM_OPEN_SDATE, 	 SUP_SCM_OPEN_EDATE = @SUP_SCM_OPEN_EDATE, 	 SUP_GRP_CODE_TMP = @SUP_GRP_CODE_TMP, 	 SUP_SCM_MNUS = @SUP_SCM_MNUS, 	 SUP_SCM_SRVS = @SUP_SCM_SRVS, 	 SUP_SHOP_KEY = @SUP_SHOP_KEY, 	 BUSINESS_NUMBER = @BUSINESS_NUMBER, 	 SUP_CANCELLATION_DAYS = @SUP_CANCELLATION_DAYS, 	 SUP_LAT = @SUP_LAT, 	 SUP_LNG = @SUP_LNG, 	 SUP_OPEN_HOURS = @SUP_OPEN_HOURS, 	 SUP_PORTERAGE = @SUP_PORTERAGE, 	 SUP_QUERY = @SUP_QUERY, 	 SUP_STAR = @SUP_STAR, 	 SUP_STATE = @SUP_STATE, 	 SUP_TERMS = @SUP_TERMS, 	 SUP_TERMS_DAYS = @SUP_TERMS_DAYS, 	 SUP_USE_STATE = @SUP_USE_STATE, 	 SUP_OWNER = @SUP_OWNER, 	 SUP_FACEBOOK = @SUP_FACEBOOK, 	 SUP_TWITTER = @SUP_TWITTER, 	 AUS_DIRECTOROFSALES = @AUS_DIRECTOROFSALES, 	 AUS_CONTRACTRATE = @AUS_CONTRACTRATE, 	 AUS_SELLINGPRICE = @AUS_SELLINGPRICE, 	 SUP_TAID = @SUP_TAID, 	 IS_AUS = @IS_AUS, 	 AUS_RACKRATE = @AUS_RACKRATE, 	 AUS_ACCREDITATION = @AUS_ACCREDITATION, 	 SUP_SALES_ID = @SUP_SALES_ID, 	 AUS_DAYS = @AUS_DAYS, 	 AUS_UNITPRICE2 = @AUS_UNITPRICE2, 	 AUS_UNITPRICE1 = @AUS_UNITPRICE1, 	 AUS_RPTING = @AUS_RPTING, 	 SUP_CREDITLIMIT = @SUP_CREDITLIMIT, 	 SUP_EMPLOYEEID = @SUP_EMPLOYEEID, 	 AUS_INCENTIVERATE = @AUS_INCENTIVERATE, 	 AUS_BKFT = @AUS_BKFT, 	 SUP_DRIVINGLICENCE = @SUP_DRIVINGLICENCE, 	 SUP_MARKUP = @SUP_MARKUP, 	 AUS_FOCPOLICY2 = @AUS_FOCPOLICY2, 	 AUS_FOCPOLICY1 = @AUS_FOCPOLICY1, 	 AUS_FQUOT = @AUS_FQUOT, 	 AUS_ACCOUNTNO = @AUS_ACCOUNTNO, 	 AUS_DISC = @AUS_DISC, 	 AUS_PORTERAGE = @AUS_PORTERAGE, 	 AUS_OVERSEA = @AUS_OVERSEA, 	 AUS_BKFTC = @AUS_BKFTC, 	 SUP_MARGIN = @SUP_MARGIN, 	 AUS_DISCOUNTDAYS = @AUS_DISCOUNTDAYS, 	 AUS_CONTACTTITLE = @AUS_CONTACTTITLE, 	 SUP_NAME_EN = @SUP_NAME_EN WHERE SUP_ID=@SUP_ID";
 private static String SQL_DELETE="DELETE FROM SUP_MAIN WHERE SUP_ID=@SUP_ID";
 private static String SQL_INSERT="INSERT INTO SUP_MAIN(SUP_PID, ARE_ID, SUP_TAG, SUP_MTAG, SUP_NAME, SUP_ADDR, SUP_TELE, SUP_FAX, SUP_EMAIL, SUP_HTTP, SUP_MOBILE, SUP_QQ, SUP_MSN, SUP_ICQ, SUP_TAOBAO, SUP_PAYPAL, SUP_ZIP, SUP_MEMO, SUP_PACT, SUP_LICENCE, SUP_AG_PROFILE, SUP_AG_ORDER, SUP_AG_CODE, SUP_LOGO, SUP_POST_URL, SUP_POST_EMAIL, SER_TAG1, CITY_ID1, SIGN_TAG, SIGN_TIME, SIGN_PRICE, SUP_ARE_DES, SUP_UNID, SUP_COIN_ID, SUP_SCM_OPEN, SUP_SCM_OPEN_SDATE, SUP_SCM_OPEN_EDATE, SUP_GRP_CODE_TMP, SUP_SCM_MNUS, SUP_SCM_SRVS, SUP_SHOP_KEY, BUSINESS_NUMBER, SUP_CANCELLATION_DAYS, SUP_LAT, SUP_LNG, SUP_OPEN_HOURS, SUP_PORTERAGE, SUP_QUERY, SUP_STAR, SUP_STATE, SUP_TERMS, SUP_TERMS_DAYS, SUP_USE_STATE, SUP_OWNER, SUP_FACEBOOK, SUP_TWITTER, AUS_DIRECTOROFSALES, AUS_CONTRACTRATE, AUS_SELLINGPRICE, SUP_TAID, IS_AUS, AUS_RACKRATE, AUS_ACCREDITATION, SUP_SALES_ID, AUS_DAYS, AUS_UNITPRICE2, AUS_UNITPRICE1, AUS_RPTING, SUP_CREDITLIMIT, SUP_EMPLOYEEID, AUS_INCENTIVERATE, AUS_BKFT, SUP_DRIVINGLICENCE, SUP_MARKUP, AUS_FOCPOLICY2, AUS_FOCPOLICY1, AUS_FQUOT, AUS_ACCOUNTNO, AUS_DISC, AUS_PORTERAGE, AUS_OVERSEA, AUS_BKFTC, SUP_MARGIN, AUS_DISCOUNTDAYS, AUS_CONTACTTITLE, SUP_NAME_EN) 	VALUES(@SUP_PID, @ARE_ID, @SUP_TAG, @SUP_MTAG, @SUP_NAME, @SUP_ADDR, @SUP_TELE, @SUP_FAX, @SUP_EMAIL, @SUP_HTTP, @SUP_MOBILE, @SUP_QQ, @SUP_MSN, @SUP_ICQ, @SUP_TAOBAO, @SUP_PAYPAL, @SUP_ZIP, @SUP_MEMO, @SUP_PACT, @SUP_LICENCE, @SUP_AG_PROFILE, @SUP_AG_ORDER, @SUP_AG_CODE, @SUP_LOGO, @SUP_POST_URL, @SUP_POST_EMAIL, @SER_TAG1, @CITY_ID1, @SIGN_TAG, @SIGN_TIME, @SIGN_PRICE, @SUP_ARE_DES, @SUP_UNID, @SUP_COIN_ID, @SUP_SCM_OPEN, @SUP_SCM_OPEN_SDATE, @SUP_SCM_OPEN_EDATE, @SUP_GRP_CODE_TMP, @SUP_SCM_MNUS, @SUP_SCM_SRVS, @SUP_SHOP_KEY, @BUSINESS_NUMBER, @SUP_CANCELLATION_DAYS, @SUP_LAT, @SUP_LNG, @SUP_OPEN_HOURS, @SUP_PORTERAGE, @SUP_QUERY, @SUP_STAR, @SUP_STATE, @SUP_TERMS, @SUP_TERMS_DAYS, @SUP_USE_STATE, @SUP_OWNER, @SUP_FACEBOOK, @SUP_TWITTER, @AUS_DIRECTOROFSALES, @AUS_CONTRACTRATE, @AUS_SELLINGPRICE, @SUP_TAID, @IS_AUS, @AUS_RACKRATE, @AUS_ACCREDITATION, @SUP_SALES_ID, @AUS_DAYS, @AUS_UNITPRICE2, @AUS_UNITPRICE1, @AUS_RPTING, @SUP_CREDITLIMIT, @SUP_EMPLOYEEID, @AUS_INCENTIVERATE, @AUS_BKFT, @SUP_DRIVINGLICENCE, @SUP_MARKUP, @AUS_FOCPOLICY2, @AUS_FOCPOLICY1, @AUS_FQUOT, @AUS_ACCOUNTNO, @AUS_DISC, @AUS_PORTERAGE, @AUS_OVERSEA, @AUS_BKFTC, @SUP_MARGIN, @AUS_DISCOUNTDAYS, @AUS_CONTACTTITLE, @SUP_NAME_EN)";
 public static String TABLE_NAME ="SUP_MAIN";
 public static String[] KEY_LIST = { "SUP_ID"   };
 public static String[] FIELD_LIST = { "SUP_ID", "SUP_PID", "ARE_ID", "SUP_TAG", "SUP_MTAG", "SUP_NAME", "SUP_ADDR", "SUP_TELE", "SUP_FAX", "SUP_EMAIL", "SUP_HTTP", "SUP_MOBILE", "SUP_QQ", "SUP_MSN", "SUP_ICQ", "SUP_TAOBAO", "SUP_PAYPAL", "SUP_ZIP", "SUP_MEMO", "SUP_PACT", "SUP_LICENCE", "SUP_AG_PROFILE", "SUP_AG_ORDER", "SUP_AG_CODE", "SUP_LOGO", "SUP_POST_URL", "SUP_POST_EMAIL", "SER_TAG1", "CITY_ID1", "SIGN_TAG", "SIGN_TIME", "SIGN_PRICE", "SUP_ARE_DES", "SUP_UNID", "SUP_COIN_ID", "SUP_SCM_OPEN", "SUP_SCM_OPEN_SDATE", "SUP_SCM_OPEN_EDATE", "SUP_GRP_CODE_TMP", "SUP_SCM_MNUS", "SUP_SCM_SRVS", "SUP_SHOP_KEY", "BUSINESS_NUMBER", "SUP_CANCELLATION_DAYS", "SUP_LAT", "SUP_LNG", "SUP_OPEN_HOURS", "SUP_PORTERAGE", "SUP_QUERY", "SUP_STAR", "SUP_STATE", "SUP_TERMS", "SUP_TERMS_DAYS", "SUP_USE_STATE", "SUP_OWNER", "SUP_FACEBOOK", "SUP_TWITTER", "AUS_DIRECTOROFSALES", "AUS_CONTRACTRATE", "AUS_SELLINGPRICE", "SUP_TAID", "IS_AUS", "AUS_RACKRATE", "AUS_ACCREDITATION", "SUP_SALES_ID", "AUS_DAYS", "AUS_UNITPRICE2", "AUS_UNITPRICE1", "AUS_RPTING", "SUP_CREDITLIMIT", "SUP_EMPLOYEEID", "AUS_INCENTIVERATE", "AUS_BKFT", "SUP_DRIVINGLICENCE", "SUP_MARKUP", "AUS_FOCPOLICY2", "AUS_FOCPOLICY1", "AUS_FQUOT", "AUS_ACCOUNTNO", "AUS_DISC", "AUS_PORTERAGE", "AUS_OVERSEA", "AUS_BKFTC", "SUP_MARGIN", "AUS_DISCOUNTDAYS", "AUS_CONTACTTITLE", "SUP_NAME_EN" };
 public SupMainDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("globaltravel");
 }/**
	 * 生成一条记录
	*@param para 表SUP_MAIN的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(SupMain para){

		RequestValue rv=this.createRequestValue(para);

int autoKey = super.executeUpdateAutoIncrement(SQL_INSERT, rv);
if (autoKey > 0) {
para.setSupId(autoKey);//自增
return true;
} else {
return false;
}
	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表SUP_MAIN的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(SupMain para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
int autoKey = super.executeUpdateAutoIncrement(sql, rv);//自增
if (autoKey > 0) {
para.setSupId(autoKey);
return true;
} else {
return false;
}
}/**
 * 更新一条记录
*@param para 表SUP_MAIN的映射类
	 *@return 是否成功 
	 */
public boolean updateRecord(SupMain para){

  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(SQL_UPDATE, rv);
}
/**
 * 更新一条记录
 * 
 * @param para
 *            表SUP_MAIN的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean updateRecord(SupMain para, HashMap<String, Boolean> updateFields){
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
public String getSqlSelect() {return "SELECT * FROM SUP_MAIN where 1=1";}
public String getSqlUpdate() {return SQL_UPDATE;}
public String getSqlInsert() {return SQL_INSERT;}/**
	 * 根据主键返回一条记录
	*@param paraSupId 供应商编号
	*@return 记录类(SupMain)
	*/
public SupMain getRecord(Integer paraSupId){
	RequestValue rv = new RequestValue();
rv.addValue("SUP_ID", paraSupId, "Integer", 10);
ArrayList<SupMain> al = super.executeQuery(SQL_SELECT, rv, new SupMain(), FIELD_LIST);
if(al.size()>0){
SupMain o = al.get(0);
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
	public ArrayList<SupMain> getRecords(String whereString){
		String sql="SELECT * FROM SUP_MAIN WHERE " + whereString;
		return super.executeQuery(sql, new SupMain(), FIELD_LIST);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	*@param  fields 指定返回的字段
	*@return 记录集合
	*/
	public ArrayList<SupMain> getRecords(String whereString, List<String> fields){
		String sql = super.createSelectSql(TABLE_NAME, whereString, fields);
		String[] arrFields = new String[fields.size()];
		arrFields = fields.toArray(arrFields);
		return super.executeQuery(sql, new SupMain(), arrFields);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	* @param pkFieldName   主键
	* @param pageSize      每页记录数
	* @param currentPage   当前页
	*@return 记录集合
	*/
	public ArrayList<SupMain> getRecords(String whereString, String pkFieldName, int pageSize, int currentPage){
		String sql="SELECT * FROM SUP_MAIN WHERE " + whereString;
		return super.executeQuery(sql, new SupMain(), FIELD_LIST, pkFieldName, pageSize, currentPage);
	}
/**
	 * 根据主键删除一条记录
	*@param paraSupId 供应商编号
	*@return 是否成功
	*/
public boolean deleteRecord(Integer paraSupId){
	RequestValue rv = new RequestValue();
rv.addValue("SUP_ID", paraSupId, "Integer", 10);
	return super.executeUpdate(SQL_DELETE, rv);
}
public RequestValue createRequestValue(SupMain para){
RequestValue rv = new RequestValue();
rv.addValue("SUP_ID", para.getSupId(), "Integer", 10); // 供应商编号
rv.addValue("SUP_PID", para.getSupPid(), "Integer", 10); // SUP_PID
rv.addValue("ARE_ID", para.getAreId(), "String", 21); // 地区编号
rv.addValue("SUP_TAG", para.getSupTag(), "String", 150); // 供应商标记
rv.addValue("SUP_MTAG", para.getSupMtag(), "String", 20); // 标记
rv.addValue("SUP_NAME", para.getSupName(), "String", 200); // 名称
rv.addValue("SUP_ADDR", para.getSupAddr(), "String", 399); // 地址
rv.addValue("SUP_TELE", para.getSupTele(), "String", 100); // 电话
rv.addValue("SUP_FAX", para.getSupFax(), "String", 100); // 传真
rv.addValue("SUP_EMAIL", para.getSupEmail(), "String", 100); // 邮件
rv.addValue("SUP_HTTP", para.getSupHttp(), "String", 100); // 网站
rv.addValue("SUP_MOBILE", para.getSupMobile(), "String", 100); // 手机
rv.addValue("SUP_QQ", para.getSupQq(), "String", 100); // QQ号
rv.addValue("SUP_MSN", para.getSupMsn(), "String", 100); // MSN
rv.addValue("SUP_ICQ", para.getSupIcq(), "String", 100); // ICQ
rv.addValue("SUP_TAOBAO", para.getSupTaobao(), "String", 100); // 淘宝号
rv.addValue("SUP_PAYPAL", para.getSupPaypal(), "String", 100); // PayPal
rv.addValue("SUP_ZIP", para.getSupZip(), "String", 20); // 邮编
rv.addValue("SUP_MEMO", para.getSupMemo(), "String", 1073741823); // 备注
rv.addValue("SUP_PACT", para.getSupPact(), "byte[]", 2147483647); // SUP_PACT
rv.addValue("SUP_LICENCE", para.getSupLicence(), "byte[]", 2147483647); // SUP_LICENCE
rv.addValue("SUP_AG_PROFILE", para.getSupAgProfile(), "String", 2147483647); // SUP_AG_PROFILE
rv.addValue("SUP_AG_ORDER", para.getSupAgOrder(), "String", 150); // SUP_AG_ORDER
rv.addValue("SUP_AG_CODE", para.getSupAgCode(), "String", 50); // SUP_AG_CODE
rv.addValue("SUP_LOGO", para.getSupLogo(), "byte[]", 2147483647); // SUP_LOGO
rv.addValue("SUP_POST_URL", para.getSupPostUrl(), "String", 200); // SUP_POST_URL
rv.addValue("SUP_POST_EMAIL", para.getSupPostEmail(), "String", 200); // SUP_POST_EMAIL
rv.addValue("SER_TAG1", para.getSerTag1(), "String", 50); // SER_TAG1
rv.addValue("CITY_ID1", para.getCityId1(), "Integer", 10); // CITY_ID1
rv.addValue("SIGN_TAG", para.getSignTag(), "String", 50); // SIGN_TAG
rv.addValue("SIGN_TIME", para.getSignTime(), "Date", 23); // SIGN_TIME
rv.addValue("SIGN_PRICE", para.getSignPrice(), "String", 50); // SIGN_PRICE
rv.addValue("SUP_ARE_DES", para.getSupAreDes(), "String", 150); // SUP_ARE_DES
rv.addValue("SUP_UNID", para.getSupUnid(), "String", 36); // SUP_UNID
rv.addValue("SUP_COIN_ID", para.getSupCoinId(), "Integer", 10); // SUP_COIN_ID
rv.addValue("SUP_SCM_OPEN", para.getSupScmOpen(), "String", 20); // SUP_SCM_OPEN
rv.addValue("SUP_SCM_OPEN_SDATE", para.getSupScmOpenSdate(), "Date", 23); // SUP_SCM_OPEN_SDATE
rv.addValue("SUP_SCM_OPEN_EDATE", para.getSupScmOpenEdate(), "Date", 23); // SUP_SCM_OPEN_EDATE
rv.addValue("SUP_GRP_CODE_TMP", para.getSupGrpCodeTmp(), "String", 50); // SUP_GRP_CODE_TMP
rv.addValue("SUP_SCM_MNUS", para.getSupScmMnus(), "String", 100); // SUP_SCM_MNUS
rv.addValue("SUP_SCM_SRVS", para.getSupScmSrvs(), "String", 1000); // SUP_SCM_SRVS
rv.addValue("SUP_SHOP_KEY", para.getSupShopKey(), "byte[]", 256); // SUP_SHOP_KEY
rv.addValue("BUSINESS_NUMBER", para.getBusinessNumber(), "String", 50); // BUSINESS_NUMBER
rv.addValue("SUP_CANCELLATION_DAYS", para.getSupCancellationDays(), "Integer", 10); // SUP_CANCELLATION_DAYS
rv.addValue("SUP_LAT", para.getSupLat(), "Integer", 30); // SUP_LAT
rv.addValue("SUP_LNG", para.getSupLng(), "Integer", 30); // SUP_LNG
rv.addValue("SUP_OPEN_HOURS", para.getSupOpenHours(), "String", 200); // SUP_OPEN_HOURS
rv.addValue("SUP_PORTERAGE", para.getSupPorterage(), "Double", 19); // SUP_PORTERAGE
rv.addValue("SUP_QUERY", para.getSupQuery(), "String", 20); // SUP_QUERY
rv.addValue("SUP_STAR", para.getSupStar(), "String", 20); // SUP_STAR
rv.addValue("SUP_STATE", para.getSupState(), "String", 20); // SUP_STATE
rv.addValue("SUP_TERMS", para.getSupTerms(), "String", 20); // SUP_TERMS
rv.addValue("SUP_TERMS_DAYS", para.getSupTermsDays(), "Integer", 10); // SUP_TERMS_DAYS
rv.addValue("SUP_USE_STATE", para.getSupUseState(), "String", 20); // SUP_USE_STATE
rv.addValue("SUP_OWNER", para.getSupOwner(), "String", 50); // SUP_OWNER
rv.addValue("SUP_FACEBOOK", para.getSupFacebook(), "String", 199); // SUP_FACEBOOK
rv.addValue("SUP_TWITTER", para.getSupTwitter(), "String", 199); // SUP_TWITTER
rv.addValue("AUS_DIRECTOROFSALES", para.getAusDirectorofsales(), "String", 50); // AUS_DIRECTOROFSALES
rv.addValue("AUS_CONTRACTRATE", para.getAusContractrate(), "Double", 53); // AUS_CONTRACTRATE
rv.addValue("AUS_SELLINGPRICE", para.getAusSellingprice(), "Double", 19); // AUS_SELLINGPRICE
rv.addValue("SUP_TAID", para.getSupTaid(), "Integer", 10); // SUP_TAID
rv.addValue("IS_AUS", para.getIsAus(), "Integer", 10); // IS_AUS
rv.addValue("AUS_RACKRATE", para.getAusRackrate(), "Double", 19); // AUS_RACKRATE
rv.addValue("AUS_ACCREDITATION", para.getAusAccreditation(), "String", 50); // AUS_ACCREDITATION
rv.addValue("SUP_SALES_ID", para.getSupSalesId(), "Integer", 10); // SUP_SALES_ID
rv.addValue("AUS_DAYS", para.getAusDays(), "Integer", 5); // AUS_DAYS
rv.addValue("AUS_UNITPRICE2", para.getAusUnitprice2(), "Double", 19); // AUS_UNITPRICE2
rv.addValue("AUS_UNITPRICE1", para.getAusUnitprice1(), "Double", 19); // AUS_UNITPRICE1
rv.addValue("AUS_RPTING", para.getAusRpting(), "String", 1); // AUS_RPTING
rv.addValue("SUP_CREDITLIMIT", para.getSupCreditlimit(), "Double", 19); // SUP_CREDITLIMIT
rv.addValue("SUP_EMPLOYEEID", para.getSupEmployeeid(), "Integer", 10); // SUP_EMPLOYEEID
rv.addValue("AUS_INCENTIVERATE", para.getAusIncentiverate(), "Double", 53); // AUS_INCENTIVERATE
rv.addValue("AUS_BKFT", para.getAusBkft(), "Double", 19); // AUS_BKFT
rv.addValue("SUP_DRIVINGLICENCE", para.getSupDrivinglicence(), "String", 50); // SUP_DRIVINGLICENCE
rv.addValue("SUP_MARKUP", para.getSupMarkup(), "Double", 19); // SUP_MARKUP
rv.addValue("AUS_FOCPOLICY2", para.getAusFocpolicy2(), "Integer", 10); // AUS_FOCPOLICY2
rv.addValue("AUS_FOCPOLICY1", para.getAusFocpolicy1(), "Integer", 10); // AUS_FOCPOLICY1
rv.addValue("AUS_FQUOT", para.getAusFquot(), "Integer", 18); // AUS_FQUOT
rv.addValue("AUS_ACCOUNTNO", para.getAusAccountno(), "String", 50); // AUS_ACCOUNTNO
rv.addValue("AUS_DISC", para.getAusDisc(), "Double", 53); // AUS_DISC
rv.addValue("AUS_PORTERAGE", para.getAusPorterage(), "Double", 19); // AUS_PORTERAGE
rv.addValue("AUS_OVERSEA", para.getAusOversea(), "String", 1); // AUS_OVERSEA
rv.addValue("AUS_BKFTC", para.getAusBkftc(), "Double", 19); // AUS_BKFTC
rv.addValue("SUP_MARGIN", para.getSupMargin(), "Double", 53); // SUP_MARGIN
rv.addValue("AUS_DISCOUNTDAYS", para.getAusDiscountdays(), "Integer", 10); // AUS_DISCOUNTDAYS
rv.addValue("AUS_CONTACTTITLE", para.getAusContacttitle(), "String", 255); // AUS_CONTACTTITLE
rv.addValue("SUP_NAME_EN", para.getSupNameEn(), "String", 150); // SUP_NAME_EN
return rv;
	}}