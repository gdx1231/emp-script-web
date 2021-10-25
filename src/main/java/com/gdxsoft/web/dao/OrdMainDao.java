package com.gdxsoft.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** 表ord_main操作类
 * @author gdx 时间：Fri May 08 2020 11:28:07 GMT+0800 (中国标准时间)
 */
public class OrdMainDao extends ClassDaoBase<OrdMain> implements IClassDao<OrdMain>{

 private static String SQL_SELECT="SELECT * FROM ord_main WHERE ORD_ID=@ORD_ID";
 private static String SQL_UPDATE="UPDATE ord_main SET 	 SUP_ID = @SUP_ID, 	 USR_ID = @USR_ID, 	 ORD_NAME = @ORD_NAME, 	 ORD_USR_STA_TAG = @ORD_USR_STA_TAG, 	 ORD_ADM_STA_TAG = @ORD_ADM_STA_TAG, 	 ORD_PAY_TAG = @ORD_PAY_TAG, 	 SRV_TYP_TAG = @SRV_TYP_TAG, 	 ORD_PAY_STA_TAG = @ORD_PAY_STA_TAG, 	 ORD_PRICE = @ORD_PRICE, 	 ORD_PAY_MONEY = @ORD_PAY_MONEY, 	 ORD_SPEC = @ORD_SPEC, 	 ORD_INV_NEED = @ORD_INV_NEED, 	 ORD_INV_TITLE = @ORD_INV_TITLE, 	 ORD_USR_NAME = @ORD_USR_NAME, 	 ORD_USR_PHONE = @ORD_USR_PHONE, 	 ORD_DATE = @ORD_DATE, 	 ORD_CDATE = @ORD_CDATE, 	 ORD_MDATE = @ORD_MDATE, 	 ORD_TYPE_MAIN = @ORD_TYPE_MAIN, 	 ORD_EXT0 = @ORD_EXT0, 	 ORD_EXT1 = @ORD_EXT1, 	 ORD_EXT2 = @ORD_EXT2, 	 ORD_EXT3 = @ORD_EXT3, 	 ORD_EXT4 = @ORD_EXT4, 	 ORD_EXT5 = @ORD_EXT5, 	 ORD_EXT6 = @ORD_EXT6, 	 ORD_EXT7 = @ORD_EXT7, 	 ORD_EXT8 = @ORD_EXT8, 	 ORD_EXT9 = @ORD_EXT9, 	 ORD_MAIL_ADDR = @ORD_MAIL_ADDR, 	 ORD_MAIL_ZIP = @ORD_MAIL_ZIP, 	 ORD_IS_INNER = @ORD_IS_INNER, 	 ORD_INN_ADMID = @ORD_INN_ADMID, 	 ORD_INN_MEMO = @ORD_INN_MEMO, 	 SCM_PRJ_ID = @SCM_PRJ_ID, 	 ORD_UID = @ORD_UID, 	 ORD_FROM_SUPID = @ORD_FROM_SUPID, 	 ORD_INV_CNT = @ORD_INV_CNT, 	 ORD_COIN = @ORD_COIN, 	 SITE_UNID = @SITE_UNID, 	 SER_ID = @SER_ID, 	 ORI_ORD_PRICE = @ORI_ORD_PRICE, 	 ORI_ORD_COIN = @ORI_ORD_COIN, 	 ORD_FROM_ADMID = @ORD_FROM_ADMID, 	 PAY_TAG_SELECTED = @PAY_TAG_SELECTED, 	 ORD_USR_EMAIL = @ORD_USR_EMAIL, 	 ORD_PICK_UP = @ORD_PICK_UP, 	 ORD_FROM_USR_ID = @ORD_FROM_USR_ID, 	 ORD_HTTP_IP = @ORD_HTTP_IP, 	 ORD_HTTP_UA = @ORD_HTTP_UA, 	 ORD_HTTP_REF = @ORD_HTTP_REF, 	 ORD_HTTP_JSP = @ORD_HTTP_JSP WHERE ORD_ID=@ORD_ID";
 private static String SQL_DELETE="DELETE FROM ord_main WHERE ORD_ID=@ORD_ID";
 private static String SQL_INSERT="INSERT INTO ord_main(SUP_ID, USR_ID, ORD_NAME, ORD_USR_STA_TAG, ORD_ADM_STA_TAG, ORD_PAY_TAG, SRV_TYP_TAG, ORD_PAY_STA_TAG, ORD_PRICE, ORD_PAY_MONEY, ORD_SPEC, ORD_INV_NEED, ORD_INV_TITLE, ORD_USR_NAME, ORD_USR_PHONE, ORD_DATE, ORD_CDATE, ORD_MDATE, ORD_TYPE_MAIN, ORD_EXT0, ORD_EXT1, ORD_EXT2, ORD_EXT3, ORD_EXT4, ORD_EXT5, ORD_EXT6, ORD_EXT7, ORD_EXT8, ORD_EXT9, ORD_MAIL_ADDR, ORD_MAIL_ZIP, ORD_IS_INNER, ORD_INN_ADMID, ORD_INN_MEMO, SCM_PRJ_ID, ORD_UID, ORD_FROM_SUPID, ORD_INV_CNT, ORD_COIN, SITE_UNID, SER_ID, ORI_ORD_PRICE, ORI_ORD_COIN, ORD_FROM_ADMID, PAY_TAG_SELECTED, ORD_USR_EMAIL, ORD_PICK_UP, ORD_FROM_USR_ID, ORD_HTTP_IP, ORD_HTTP_UA, ORD_HTTP_REF, ORD_HTTP_JSP) 	VALUES(@SUP_ID, @USR_ID, @ORD_NAME, @ORD_USR_STA_TAG, @ORD_ADM_STA_TAG, @ORD_PAY_TAG, @SRV_TYP_TAG, @ORD_PAY_STA_TAG, @ORD_PRICE, @ORD_PAY_MONEY, @ORD_SPEC, @ORD_INV_NEED, @ORD_INV_TITLE, @ORD_USR_NAME, @ORD_USR_PHONE, @ORD_DATE, @ORD_CDATE, @ORD_MDATE, @ORD_TYPE_MAIN, @ORD_EXT0, @ORD_EXT1, @ORD_EXT2, @ORD_EXT3, @ORD_EXT4, @ORD_EXT5, @ORD_EXT6, @ORD_EXT7, @ORD_EXT8, @ORD_EXT9, @ORD_MAIL_ADDR, @ORD_MAIL_ZIP, @ORD_IS_INNER, @ORD_INN_ADMID, @ORD_INN_MEMO, @SCM_PRJ_ID, @ORD_UID, @ORD_FROM_SUPID, @ORD_INV_CNT, @ORD_COIN, @SITE_UNID, @SER_ID, @ORI_ORD_PRICE, @ORI_ORD_COIN, @ORD_FROM_ADMID, @PAY_TAG_SELECTED, @ORD_USR_EMAIL, @ORD_PICK_UP, @ORD_FROM_USR_ID, @ORD_HTTP_IP, @ORD_HTTP_UA, @ORD_HTTP_REF, @ORD_HTTP_JSP)";
 public static String TABLE_NAME ="ord_main";
 public static String[] KEY_LIST = { "ORD_ID"   };
 public static String[] FIELD_LIST = { "ORD_ID", "SUP_ID", "USR_ID", "ORD_NAME", "ORD_USR_STA_TAG", "ORD_ADM_STA_TAG", "ORD_PAY_TAG", "SRV_TYP_TAG", "ORD_PAY_STA_TAG", "ORD_PRICE", "ORD_PAY_MONEY", "ORD_SPEC", "ORD_INV_NEED", "ORD_INV_TITLE", "ORD_USR_NAME", "ORD_USR_PHONE", "ORD_DATE", "ORD_CDATE", "ORD_MDATE", "ORD_TYPE_MAIN", "ORD_EXT0", "ORD_EXT1", "ORD_EXT2", "ORD_EXT3", "ORD_EXT4", "ORD_EXT5", "ORD_EXT6", "ORD_EXT7", "ORD_EXT8", "ORD_EXT9", "ORD_MAIL_ADDR", "ORD_MAIL_ZIP", "ORD_IS_INNER", "ORD_INN_ADMID", "ORD_INN_MEMO", "SCM_PRJ_ID", "ORD_UID", "ORD_FROM_SUPID", "ORD_INV_CNT", "ORD_COIN", "SITE_UNID", "SER_ID", "ORI_ORD_PRICE", "ORI_ORD_COIN", "ORD_FROM_ADMID", "PAY_TAG_SELECTED", "ORD_USR_EMAIL", "ORD_PICK_UP", "ORD_FROM_USR_ID", "ORD_HTTP_IP", "ORD_HTTP_UA", "ORD_HTTP_REF", "ORD_HTTP_JSP" };
 public OrdMainDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("ex");
 }/**
	 * 生成一条记录
	*@param para 表ord_main的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(OrdMain para){

		RequestValue rv=this.createRequestValue(para);

int autoKey = super.executeUpdateAutoIncrement(SQL_INSERT, rv);
if (autoKey > 0) {
para.setOrdId(autoKey);//自增
return true;
} else {
return false;
}
	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表ord_main的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(OrdMain para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
int autoKey = super.executeUpdateAutoIncrement(sql, rv);//自增
if (autoKey > 0) {
para.setOrdId(autoKey);
return true;
} else {
return false;
}
}/**
 * 更新一条记录
*@param para 表ord_main的映射类
	 *@return 是否成功 
	 */
public boolean updateRecord(OrdMain para){

  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(SQL_UPDATE, rv);
}
/**
 * 更新一条记录
 * 
 * @param para
 *            表ord_main的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean updateRecord(OrdMain para, HashMap<String, Boolean> updateFields){
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
public String getSqlSelect() {return "SELECT * FROM ord_main where 1=1";}
public String getSqlUpdate() {return SQL_UPDATE;}
public String getSqlInsert() {return SQL_INSERT;}/**
	 * 根据主键返回一条记录
	*@param paraOrdId ORD_ID
	*@return 记录类(OrdMain)
	*/
public OrdMain getRecord(Integer paraOrdId){
	RequestValue rv = new RequestValue();
rv.addValue("ORD_ID", paraOrdId, "Integer", 10);
ArrayList<OrdMain> al = super.executeQuery(SQL_SELECT, rv, new OrdMain(), FIELD_LIST);
if(al.size()>0){
OrdMain o = al.get(0);
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
	public ArrayList<OrdMain> getRecords(String whereString){
		String sql="SELECT * FROM ord_main WHERE " + whereString;
		return super.executeQuery(sql, new OrdMain(), FIELD_LIST);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	*@param  fields 指定返回的字段
	*@return 记录集合
	*/
	public ArrayList<OrdMain> getRecords(String whereString, List<String> fields){
		String sql = super.createSelectSql(TABLE_NAME, whereString, fields);
		String[] arrFields = new String[fields.size()];
		arrFields = fields.toArray(arrFields);
		return super.executeQuery(sql, new OrdMain(), arrFields);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	* @param pkFieldName   主键
	* @param pageSize      每页记录数
	* @param currentPage   当前页
	*@return 记录集合
	*/
	public ArrayList<OrdMain> getRecords(String whereString, String pkFieldName, int pageSize, int currentPage){
		String sql="SELECT * FROM ord_main WHERE " + whereString;
		return super.executeQuery(sql, new OrdMain(), FIELD_LIST, pkFieldName, pageSize, currentPage);
	}
/**
	 * 根据主键删除一条记录
	*@param paraOrdId ORD_ID
	*@return 是否成功
	*/
public boolean deleteRecord(Integer paraOrdId){
	RequestValue rv = new RequestValue();
rv.addValue("ORD_ID", paraOrdId, "Integer", 10);
	return super.executeUpdate(SQL_DELETE, rv);
}
public RequestValue createRequestValue(OrdMain para){
RequestValue rv = new RequestValue();
rv.addValue("ORD_ID", para.getOrdId(), "Integer", 10); // ORD_ID
rv.addValue("SUP_ID", para.getSupId(), "Integer", 10); // 处理订单供应商
rv.addValue("USR_ID", para.getUsrId(), "Integer", 10); // 编号
rv.addValue("ORD_NAME", para.getOrdName(), "String", 1000); // ORD_NAME
rv.addValue("ORD_USR_STA_TAG", para.getOrdUsrStaTag(), "String", 20); // 订单用户处理状态
rv.addValue("ORD_ADM_STA_TAG", para.getOrdAdmStaTag(), "String", 20); // 管理员处理订单状态
rv.addValue("ORD_PAY_TAG", para.getOrdPayTag(), "String", 20); // 付款标记
rv.addValue("SRV_TYP_TAG", para.getSrvTypTag(), "String", 20); // 订单类标记
rv.addValue("ORD_PAY_STA_TAG", para.getOrdPayStaTag(), "String", 20); // 订单支付状态
rv.addValue("ORD_PRICE", para.getOrdPrice(), "Double", 10); // 价格
rv.addValue("ORD_PAY_MONEY", para.getOrdPayMoney(), "Double", 10); // 支付
rv.addValue("ORD_SPEC", para.getOrdSpec(), "String", 1000); // 用户嘱咐
rv.addValue("ORD_INV_NEED", para.getOrdInvNeed(), "String", 20); // 需要发票
rv.addValue("ORD_INV_TITLE", para.getOrdInvTitle(), "String", 400); // 发票抬头
rv.addValue("ORD_USR_NAME", para.getOrdUsrName(), "String", 40); // 订单用户名称
rv.addValue("ORD_USR_PHONE", para.getOrdUsrPhone(), "String", 100); // 订单用户电话
rv.addValue("ORD_DATE", para.getOrdDate(), "Date", 19); // 订单日期
rv.addValue("ORD_CDATE", para.getOrdCdate(), "Date", 19); // 订单创建日期
rv.addValue("ORD_MDATE", para.getOrdMdate(), "Date", 19); // 订单修改日期
rv.addValue("ORD_TYPE_MAIN", para.getOrdTypeMain(), "String", 10); // 订单主要类型
rv.addValue("ORD_EXT0", para.getOrdExt0(), "String", 50); // 扩展参数1
rv.addValue("ORD_EXT1", para.getOrdExt1(), "String", 50); // 扩展参数2
rv.addValue("ORD_EXT2", para.getOrdExt2(), "String", 50); // 扩展参数3
rv.addValue("ORD_EXT3", para.getOrdExt3(), "String", 50); // ORD_EXT3
rv.addValue("ORD_EXT4", para.getOrdExt4(), "String", 50); // ORD_EXT4
rv.addValue("ORD_EXT5", para.getOrdExt5(), "String", 50); // ORD_EXT5
rv.addValue("ORD_EXT6", para.getOrdExt6(), "String", 50); // ORD_EXT6
rv.addValue("ORD_EXT7", para.getOrdExt7(), "String", 50); // ORD_EXT7
rv.addValue("ORD_EXT8", para.getOrdExt8(), "String", 50); // ORD_EXT8
rv.addValue("ORD_EXT9", para.getOrdExt9(), "String", 50); // ORD_EXT9
rv.addValue("ORD_MAIL_ADDR", para.getOrdMailAddr(), "String", 400); // 邮件地址
rv.addValue("ORD_MAIL_ZIP", para.getOrdMailZip(), "String", 10); // 邮编
rv.addValue("ORD_IS_INNER", para.getOrdIsInner(), "String", 1); // 是否内部订单
rv.addValue("ORD_INN_ADMID", para.getOrdInnAdmid(), "Integer", 10); // 内部订单管理员
rv.addValue("ORD_INN_MEMO", para.getOrdInnMemo(), "String", 1000); // 订单内部备注
rv.addValue("SCM_PRJ_ID", para.getScmPrjId(), "Integer", 10); // 内部项目编号
rv.addValue("ORD_UID", para.getOrdUid(), "String", 36); // ORD_UID
rv.addValue("ORD_FROM_SUPID", para.getOrdFromSupid(), "Integer", 10); // ORD_FROM_SUPID
rv.addValue("ORD_INV_CNT", para.getOrdInvCnt(), "String", 200); // ORD_INV_CNT
rv.addValue("ORD_COIN", para.getOrdCoin(), "Integer", 10); // ORD_COIN
rv.addValue("SITE_UNID", para.getSiteUnid(), "String", 36); // SITE_UNID
rv.addValue("SER_ID", para.getSerId(), "Integer", 10); // 来源服务
rv.addValue("ORI_ORD_PRICE", para.getOriOrdPrice(), "Double", 10); // 原始总价
rv.addValue("ORI_ORD_COIN", para.getOriOrdCoin(), "Integer", 10); // 原始货币
rv.addValue("ORD_FROM_ADMID", para.getOrdFromAdmid(), "Integer", 10); // 供应商下单人
rv.addValue("PAY_TAG_SELECTED", para.getPayTagSelected(), "String", 20); // 选择的支付方式
rv.addValue("ORD_USR_EMAIL", para.getOrdUsrEmail(), "String", 100); // 订单人联系邮件
rv.addValue("ORD_PICK_UP", para.getOrdPickUp(), "String", 1000); // ORD_PICK_UP
rv.addValue("ORD_FROM_USR_ID", para.getOrdFromUsrId(), "Integer", 10); // 来源用户
rv.addValue("ORD_HTTP_IP", para.getOrdHttpIp(), "String", 50); // ip地址
rv.addValue("ORD_HTTP_UA", para.getOrdHttpUa(), "String", 500); // 浏览器
rv.addValue("ORD_HTTP_REF", para.getOrdHttpRef(), "String", 500); // 订单来源网页
rv.addValue("ORD_HTTP_JSP", para.getOrdHttpJsp(), "String", 500); // 下单执行文件
return rv;
	}}