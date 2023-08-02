package com.gdxsoft.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** 表OA_PRJ_MARK操作类
 * @author gdx 时间：Wed Jan 16 2019 11:18:55 GMT+0800 (中国标准时间)
 */
public class OaPrjMarkDao extends ClassDaoBase<OaPrjMark> implements IClassDao<OaPrjMark>{

 private static String SQL_SELECT="SELECT * FROM OA_PRJ_MARK WHERE PRJ_MAK_ID=@PRJ_MAK_ID";
 private static String SQL_UPDATE="UPDATE OA_PRJ_MARK SET 	 PRJ_ID = @PRJ_ID, 	 PRJ_MARK_DATE = @PRJ_MARK_DATE, 	 PRJ_MARK_NAME = @PRJ_MARK_NAME, 	 PRJ_MARK_NAME_EN = @PRJ_MARK_NAME_EN, 	 SUP_ID = @SUP_ID, 	 ADM_ID = @ADM_ID, 	 PRJ_MARK_CDATE = @PRJ_MARK_CDATE, 	 PRJ_MARK_MDATE = @PRJ_MARK_MDATE, 	 PRJ_MARK_COLOR = @PRJ_MARK_COLOR, 	 PRJ_MARK_TAG = @PRJ_MARK_TAG WHERE PRJ_MAK_ID=@PRJ_MAK_ID";
 private static String SQL_DELETE="DELETE FROM OA_PRJ_MARK WHERE PRJ_MAK_ID=@PRJ_MAK_ID";
 private static String SQL_INSERT="INSERT INTO OA_PRJ_MARK(PRJ_ID, PRJ_MARK_DATE, PRJ_MARK_NAME, PRJ_MARK_NAME_EN, SUP_ID, ADM_ID, PRJ_MARK_CDATE, PRJ_MARK_MDATE, PRJ_MARK_COLOR, PRJ_MARK_TAG) 	VALUES(@PRJ_ID, @PRJ_MARK_DATE, @PRJ_MARK_NAME, @PRJ_MARK_NAME_EN, @SUP_ID, @ADM_ID, @PRJ_MARK_CDATE, @PRJ_MARK_MDATE, @PRJ_MARK_COLOR, @PRJ_MARK_TAG)";
 public static String TABLE_NAME ="OA_PRJ_MARK";
 public static String[] KEY_LIST = { "PRJ_MAK_ID"   };
 public static String[] FIELD_LIST = { "PRJ_MAK_ID", "PRJ_ID", "PRJ_MARK_DATE", "PRJ_MARK_NAME", "PRJ_MARK_NAME_EN", "SUP_ID", "ADM_ID", "PRJ_MARK_CDATE", "PRJ_MARK_MDATE", "PRJ_MARK_COLOR", "PRJ_MARK_TAG" };
 public OaPrjMarkDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("globaltravel");
 }/**
	 * 生成一条记录
	*@param para 表OA_PRJ_MARK的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(OaPrjMark para){

		RequestValue rv=this.createRequestValue(para);

int autoKey = super.executeUpdateAutoIncrement(SQL_INSERT, rv);
if (autoKey > 0) {
para.setPrjMakId(autoKey);//自增
return true;
} else {
return false;
}
	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表OA_PRJ_MARK的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(OaPrjMark para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
int autoKey = super.executeUpdateAutoIncrement(sql, rv);//自增
if (autoKey > 0) {
para.setPrjMakId(autoKey);
return true;
} else {
return false;
}
}/**
 * 更新一条记录
*@param para 表OA_PRJ_MARK的映射类
	 *@return 是否成功 
	 */
public boolean updateRecord(OaPrjMark para){

  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(SQL_UPDATE, rv);
}
/**
 * 更新一条记录
 * 
 * @param para
 *            表OA_PRJ_MARK的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean updateRecord(OaPrjMark para, HashMap<String, Boolean> updateFields){
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
public String getSqlSelect() {return "SELECT * FROM OA_PRJ_MARK where 1=1";}
public String getSqlUpdate() {return SQL_UPDATE;}
public String getSqlInsert() {return SQL_INSERT;}/**
	 * 根据主键返回一条记录
	*@param paraPrjMakId 标记编号
	*@return 记录类(OaPrjMark)
	*/
public OaPrjMark getRecord(Integer paraPrjMakId){
	RequestValue rv = new RequestValue();
rv.addValue("PRJ_MAK_ID", paraPrjMakId, "Integer", 10);
ArrayList<OaPrjMark> al = super.executeQuery(SQL_SELECT, rv, new OaPrjMark(), FIELD_LIST);
if(al.size()>0){
OaPrjMark o = al.get(0);
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
	public ArrayList<OaPrjMark> getRecords(String whereString){
		String sql="SELECT * FROM OA_PRJ_MARK WHERE " + whereString;
		return super.executeQuery(sql, new OaPrjMark(), FIELD_LIST);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	*@param  fields 指定返回的字段
	*@return 记录集合
	*/
	public ArrayList<OaPrjMark> getRecords(String whereString, List<String> fields){
		String sql = super.createSelectSql(TABLE_NAME, whereString, fields);
		String[] arrFields = new String[fields.size()];
		arrFields = fields.toArray(arrFields);
		return super.executeQuery(sql, new OaPrjMark(), arrFields);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	* @param pkFieldName   主键
	* @param pageSize      每页记录数
	* @param currentPage   当前页
	*@return 记录集合
	*/
	public ArrayList<OaPrjMark> getRecords(String whereString, String pkFieldName, int pageSize, int currentPage){
		String sql="SELECT * FROM OA_PRJ_MARK WHERE " + whereString;
		return super.executeQuery(sql, new OaPrjMark(), FIELD_LIST, pkFieldName, pageSize, currentPage);
	}
/**
	 * 根据主键删除一条记录
	*@param paraPrjMakId 标记编号
	*@return 是否成功
	*/
public boolean deleteRecord(Integer paraPrjMakId){
	RequestValue rv = new RequestValue();
rv.addValue("PRJ_MAK_ID", paraPrjMakId, "Integer", 10);
	return super.executeUpdate(SQL_DELETE, rv);
}
public RequestValue createRequestValue(OaPrjMark para){
RequestValue rv = new RequestValue();
rv.addValue("PRJ_MAK_ID", para.getPrjMakId(), "Integer", 10); // 标记编号
rv.addValue("PRJ_ID", para.getPrjId(), "Integer", 10); // 项目编号
rv.addValue("PRJ_MARK_DATE", para.getPrjMarkDate(), "Date", 23); // 标记日期
rv.addValue("PRJ_MARK_NAME", para.getPrjMarkName(), "String", 100); // 标记名称
rv.addValue("PRJ_MARK_NAME_EN", para.getPrjMarkNameEn(), "String", 100); // 英文
rv.addValue("SUP_ID", para.getSupId(), "Integer", 10); // 供应商
rv.addValue("ADM_ID", para.getAdmId(), "Integer", 10); // 创建人
rv.addValue("PRJ_MARK_CDATE", para.getPrjMarkCdate(), "Date", 23); // 创建日期
rv.addValue("PRJ_MARK_MDATE", para.getPrjMarkMdate(), "Date", 23); // 修改日期
rv.addValue("PRJ_MARK_COLOR", para.getPrjMarkColor(), "String", 20); // 颜色
rv.addValue("PRJ_MARK_TAG", para.getPrjMarkTag(), "String", 30); // 标记
return rv;
	}}