package com.gdxsoft.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** 表ord_main_lst操作类
 * @author gdx 时间：Sat Apr 25 2020 17:49:12 GMT+0800 (中国标准时间)
 */
public class OrdMainLstDao extends ClassDaoBase<OrdMainLst> implements IClassDao<OrdMainLst>{

 private static String SQL_SELECT="SELECT * FROM ord_main_lst WHERE ORD_LST_ID=@ORD_LST_ID";
 private static String SQL_UPDATE="UPDATE ord_main_lst SET 	 ORD_ID = @ORD_ID, 	 SRV_ID = @SRV_ID, 	 SUP_ID = @SUP_ID, 	 ORD_LST_NAME = @ORD_LST_NAME, 	 ORD_LST_MONEY = @ORD_LST_MONEY, 	 ORD_LST_NUM1 = @ORD_LST_NUM1, 	 ORD_LST_MONEY1 = @ORD_LST_MONEY1, 	 ORD_LST_NUM2 = @ORD_LST_NUM2, 	 ORD_LST_MONEY2 = @ORD_LST_MONEY2, 	 ORD_LST_NUM3 = @ORD_LST_NUM3, 	 ORD_LST_MONEY3 = @ORD_LST_MONEY3, 	 ORD_LST_MEMO1 = @ORD_LST_MEMO1, 	 ORD_LST_MEMO2 = @ORD_LST_MEMO2, 	 ORD_LST_MEMO3 = @ORD_LST_MEMO3, 	 ORD_LST_MEMO4 = @ORD_LST_MEMO4, 	 ORD_LST_MEMO5 = @ORD_LST_MEMO5, 	 ORD_LST_IDX = @ORD_LST_IDX, 	 ORD_LST_ADMID = @ORD_LST_ADMID, 	 SCM_PRJ_SUB_ID = @SCM_PRJ_SUB_ID, 	 ORD_LST_COIN = @ORD_LST_COIN, 	 ORD_LST_DATE = @ORD_LST_DATE, 	 ORD_LST_DATE_END = @ORD_LST_DATE_END, 	 ORD_LST_DISCOUNT = @ORD_LST_DISCOUNT, 	 ORD_LST_PID = @ORD_LST_PID, 	 ORD_LST_ROOM = @ORD_LST_ROOM, 	 ord_ser_id = @ord_ser_id, 	 ord_ser_key_id = @ord_ser_key_id, 	 ord_ref_product_id = @ord_ref_product_id, 	 ord_ref_product_price_id = @ord_ref_product_price_id, 	 ord_ref_source_table = @ord_ref_source_table, 	 ord_ref_source_id = @ord_ref_source_id, 	 ORI_ORD_LST_MONEY1 = @ORI_ORD_LST_MONEY1, 	 ORI_ORD_LST_COIN = @ORI_ORD_LST_COIN, 	 ORD_LST_POINT = @ORD_LST_POINT WHERE ORD_LST_ID=@ORD_LST_ID";
 private static String SQL_DELETE="DELETE FROM ord_main_lst WHERE ORD_LST_ID=@ORD_LST_ID";
 private static String SQL_INSERT="INSERT INTO ord_main_lst(ORD_ID, SRV_ID, SUP_ID, ORD_LST_NAME, ORD_LST_MONEY, ORD_LST_NUM1, ORD_LST_MONEY1, ORD_LST_NUM2, ORD_LST_MONEY2, ORD_LST_NUM3, ORD_LST_MONEY3, ORD_LST_MEMO1, ORD_LST_MEMO2, ORD_LST_MEMO3, ORD_LST_MEMO4, ORD_LST_MEMO5, ORD_LST_IDX, ORD_LST_ADMID, SCM_PRJ_SUB_ID, ORD_LST_COIN, ORD_LST_DATE, ORD_LST_DATE_END, ORD_LST_DISCOUNT, ORD_LST_PID, ORD_LST_ROOM, ord_ser_id, ord_ser_key_id, ord_ref_product_id, ord_ref_product_price_id, ord_ref_source_table, ord_ref_source_id, ORI_ORD_LST_MONEY1, ORI_ORD_LST_COIN, ORD_LST_POINT) 	VALUES(@ORD_ID, @SRV_ID, @SUP_ID, @ORD_LST_NAME, @ORD_LST_MONEY, @ORD_LST_NUM1, @ORD_LST_MONEY1, @ORD_LST_NUM2, @ORD_LST_MONEY2, @ORD_LST_NUM3, @ORD_LST_MONEY3, @ORD_LST_MEMO1, @ORD_LST_MEMO2, @ORD_LST_MEMO3, @ORD_LST_MEMO4, @ORD_LST_MEMO5, @ORD_LST_IDX, @ORD_LST_ADMID, @SCM_PRJ_SUB_ID, @ORD_LST_COIN, @ORD_LST_DATE, @ORD_LST_DATE_END, @ORD_LST_DISCOUNT, @ORD_LST_PID, @ORD_LST_ROOM, @ord_ser_id, @ord_ser_key_id, @ord_ref_product_id, @ord_ref_product_price_id, @ord_ref_source_table, @ord_ref_source_id, @ORI_ORD_LST_MONEY1, @ORI_ORD_LST_COIN, @ORD_LST_POINT)";
 public static String TABLE_NAME ="ord_main_lst";
 public static String[] KEY_LIST = { "ORD_LST_ID"   };
 public static String[] FIELD_LIST = { "ORD_LST_ID", "ORD_ID", "SRV_ID", "SUP_ID", "ORD_LST_NAME", "ORD_LST_MONEY", "ORD_LST_NUM1", "ORD_LST_MONEY1", "ORD_LST_NUM2", "ORD_LST_MONEY2", "ORD_LST_NUM3", "ORD_LST_MONEY3", "ORD_LST_MEMO1", "ORD_LST_MEMO2", "ORD_LST_MEMO3", "ORD_LST_MEMO4", "ORD_LST_MEMO5", "ORD_LST_IDX", "ORD_LST_ADMID", "SCM_PRJ_SUB_ID", "ORD_LST_COIN", "ORD_LST_DATE", "ORD_LST_DATE_END", "ORD_LST_DISCOUNT", "ORD_LST_PID", "ORD_LST_ROOM", "ord_ser_id", "ord_ser_key_id", "ord_ref_product_id", "ord_ref_product_price_id", "ord_ref_source_table", "ord_ref_source_id", "ORI_ORD_LST_MONEY1", "ORI_ORD_LST_COIN", "ORD_LST_POINT" };
 public OrdMainLstDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("ex");
 }/**
	 * 生成一条记录
	*@param para 表ord_main_lst的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(OrdMainLst para){

		RequestValue rv=this.createRequestValue(para);

int autoKey = super.executeUpdateAutoIncrement(SQL_INSERT, rv);
if (autoKey > 0) {
para.setOrdLstId(autoKey);//自增
return true;
} else {
return false;
}
	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表ord_main_lst的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(OrdMainLst para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
int autoKey = super.executeUpdateAutoIncrement(sql, rv);//自增
if (autoKey > 0) {
para.setOrdLstId(autoKey);
return true;
} else {
return false;
}
}/**
 * 更新一条记录
*@param para 表ord_main_lst的映射类
	 *@return 是否成功 
	 */
public boolean updateRecord(OrdMainLst para){

  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(SQL_UPDATE, rv);
}
/**
 * 更新一条记录
 * 
 * @param para
 *            表ord_main_lst的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean updateRecord(OrdMainLst para, HashMap<String, Boolean> updateFields){
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
public String getSqlSelect() {return "SELECT * FROM ord_main_lst where 1=1";}
public String getSqlUpdate() {return SQL_UPDATE;}
public String getSqlInsert() {return SQL_INSERT;}/**
	 * 根据主键返回一条记录
	*@param paraOrdLstId ORD_LST_ID
	*@return 记录类(OrdMainLst)
	*/
public OrdMainLst getRecord(Integer paraOrdLstId){
	RequestValue rv = new RequestValue();
rv.addValue("ORD_LST_ID", paraOrdLstId, "Integer", 10);
ArrayList<OrdMainLst> al = super.executeQuery(SQL_SELECT, rv, new OrdMainLst(), FIELD_LIST);
if(al.size()>0){
OrdMainLst o = al.get(0);
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
	public ArrayList<OrdMainLst> getRecords(String whereString){
		String sql="SELECT * FROM ord_main_lst WHERE " + whereString;
		return super.executeQuery(sql, new OrdMainLst(), FIELD_LIST);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	*@param  fields 指定返回的字段
	*@return 记录集合
	*/
	public ArrayList<OrdMainLst> getRecords(String whereString, List<String> fields){
		String sql = super.createSelectSql(TABLE_NAME, whereString, fields);
		String[] arrFields = new String[fields.size()];
		arrFields = fields.toArray(arrFields);
		return super.executeQuery(sql, new OrdMainLst(), arrFields);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	* @param pkFieldName   主键
	* @param pageSize      每页记录数
	* @param currentPage   当前页
	*@return 记录集合
	*/
	public ArrayList<OrdMainLst> getRecords(String whereString, String pkFieldName, int pageSize, int currentPage){
		String sql="SELECT * FROM ord_main_lst WHERE " + whereString;
		return super.executeQuery(sql, new OrdMainLst(), FIELD_LIST, pkFieldName, pageSize, currentPage);
	}
/**
	 * 根据主键删除一条记录
	*@param paraOrdLstId ORD_LST_ID
	*@return 是否成功
	*/
public boolean deleteRecord(Integer paraOrdLstId){
	RequestValue rv = new RequestValue();
rv.addValue("ORD_LST_ID", paraOrdLstId, "Integer", 10);
	return super.executeUpdate(SQL_DELETE, rv);
}
public RequestValue createRequestValue(OrdMainLst para){
RequestValue rv = new RequestValue();
rv.addValue("ORD_LST_ID", para.getOrdLstId(), "Integer", 10); // ORD_LST_ID
rv.addValue("ORD_ID", para.getOrdId(), "Integer", 10); // 订单号
rv.addValue("SRV_ID", para.getSrvId(), "Integer", 10); // 本系统无效了
rv.addValue("SUP_ID", para.getSupId(), "Integer", 10); // 服务提供商编号
rv.addValue("ORD_LST_NAME", para.getOrdLstName(), "String", 200); // 名称
rv.addValue("ORD_LST_MONEY", para.getOrdLstMoney(), "Double", 10); // 总价
rv.addValue("ORD_LST_NUM1", para.getOrdLstNum1(), "Integer", 10); // 数量1
rv.addValue("ORD_LST_MONEY1", para.getOrdLstMoney1(), "Double", 10); // 单价1
rv.addValue("ORD_LST_NUM2", para.getOrdLstNum2(), "Integer", 10); // 数量2
rv.addValue("ORD_LST_MONEY2", para.getOrdLstMoney2(), "Double", 10); // 单价2
rv.addValue("ORD_LST_NUM3", para.getOrdLstNum3(), "Integer", 10); // 数量3
rv.addValue("ORD_LST_MONEY3", para.getOrdLstMoney3(), "Double", 10); // 单价3
rv.addValue("ORD_LST_MEMO1", para.getOrdLstMemo1(), "String", 1000); // 备注1
rv.addValue("ORD_LST_MEMO2", para.getOrdLstMemo2(), "String", 1000); // 备注2
rv.addValue("ORD_LST_MEMO3", para.getOrdLstMemo3(), "String", 1000); // 备注3
rv.addValue("ORD_LST_MEMO4", para.getOrdLstMemo4(), "String", 1000); // 备注4
rv.addValue("ORD_LST_MEMO5", para.getOrdLstMemo5(), "String", 1000); // 备注5
rv.addValue("ORD_LST_IDX", para.getOrdLstIdx(), "Integer", 10); // 序号
rv.addValue("ORD_LST_ADMID", para.getOrdLstAdmid(), "Integer", 10); // 管理员编号
rv.addValue("SCM_PRJ_SUB_ID", para.getScmPrjSubId(), "Integer", 10); // 项目来源项目子编号
rv.addValue("ORD_LST_COIN", para.getOrdLstCoin(), "Integer", 10); // ORD_LST_COIN
rv.addValue("ORD_LST_DATE", para.getOrdLstDate(), "Date", 19); // ORD_LST_DATE
rv.addValue("ORD_LST_DATE_END", para.getOrdLstDateEnd(), "Date", 19); // ORD_LST_DATE_END
rv.addValue("ORD_LST_DISCOUNT", para.getOrdLstDiscount(), "Double", 10); // 折扣
rv.addValue("ORD_LST_PID", para.getOrdLstPid(), "Integer", 10); // ORD_LST_PID
rv.addValue("ORD_LST_ROOM", para.getOrdLstRoom(), "Integer", 10); // ORD_LST_ROOM
rv.addValue("ORD_SER_ID", para.getOrdSerId(), "Integer", 10); // 来源服务
rv.addValue("ORD_SER_KEY_ID", para.getOrdSerKeyId(), "Integer", 10); // 来源ser_key_id
rv.addValue("ORD_REF_PRODUCT_ID", para.getOrdRefProductId(), "Integer", 10); // 来源product_main的id
rv.addValue("ORD_REF_PRODUCT_PRICE_ID", para.getOrdRefProductPriceId(), "Integer", 10); // 价格编号id
rv.addValue("ORD_REF_SOURCE_TABLE", para.getOrdRefSourceTable(), "String", 50); // 服务来源表例如：bas_spot, guide_main, camp_home ..
rv.addValue("ORD_REF_SOURCE_ID", para.getOrdRefSourceId(), "String", 50); // 来源表的主键, 例如spot_id , gui_id, hom_id …
rv.addValue("ORI_ORD_LST_MONEY1", para.getOriOrdLstMoney1(), "Double", 10); // 原始单价
rv.addValue("ORI_ORD_LST_COIN", para.getOriOrdLstCoin(), "Integer", 10); // 原始货币
rv.addValue("ORD_LST_POINT", para.getOrdLstPoint(), "Double", 12); // 汇率
return rv;
	}}