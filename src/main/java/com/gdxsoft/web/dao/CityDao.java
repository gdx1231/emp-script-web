package com.gdxsoft.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** 表CITY操作类
 * @author gdx 时间：Sat Jul 11 2020 16:13:31 GMT+0800 (中国标准时间)
 */
public class CityDao extends ClassDaoBase<City> implements IClassDao<City>{

 private static String SQL_SELECT="SELECT * FROM CITY WHERE CITY_ID=@CITY_ID";
 private static String SQL_UPDATE="UPDATE CITY SET 	 CITY_NAME = @CITY_NAME, 	 CITY_NAME_EN = @CITY_NAME_EN, 	 COUNTRY_ID = @COUNTRY_ID, 	 CITY_CODE = @CITY_CODE, 	 CITY_PSOT_CODE = @CITY_PSOT_CODE, 	 CITY_LVL = @CITY_LVL, 	 CITY_MEMO = @CITY_MEMO, 	 CITY_A = @CITY_A, 	 CITY_TAG = @CITY_TAG, 	 CITY_UTC = @CITY_UTC, 	 CITY_DST_FROM = @CITY_DST_FROM, 	 CITY_DST_TO = @CITY_DST_TO, 	 CITY_LNG = @CITY_LNG, 	 CITY_LAT = @CITY_LAT, 	 CITY_GPS_DES = @CITY_GPS_DES, 	 CITY_HOT = @CITY_HOT, 	 Q_CID = @Q_CID, 	 COUNTRY_COIN = @COUNTRY_COIN, 	 STATE_ID = @STATE_ID, 	 CITY_PID = @CITY_PID, 	 CITY_LVL_NEW = @CITY_LVL_NEW, 	 CITY_STATUS = @CITY_STATUS, 	 COUNTRY_TELE = @COUNTRY_TELE, 	 CITY_SCODE = @CITY_SCODE WHERE CITY_ID=@CITY_ID";
 private static String SQL_DELETE="DELETE FROM CITY WHERE CITY_ID=@CITY_ID";
 private static String SQL_INSERT="INSERT INTO CITY(CITY_NAME, CITY_NAME_EN, COUNTRY_ID, CITY_CODE, CITY_PSOT_CODE, CITY_LVL, CITY_MEMO, CITY_A, CITY_TAG, CITY_UTC, CITY_DST_FROM, CITY_DST_TO, CITY_LNG, CITY_LAT, CITY_GPS_DES, CITY_HOT, Q_CID, COUNTRY_COIN, STATE_ID, CITY_PID, CITY_LVL_NEW, CITY_STATUS, COUNTRY_TELE, CITY_SCODE) 	VALUES(@CITY_NAME, @CITY_NAME_EN, @COUNTRY_ID, @CITY_CODE, @CITY_PSOT_CODE, @CITY_LVL, @CITY_MEMO, @CITY_A, @CITY_TAG, @CITY_UTC, @CITY_DST_FROM, @CITY_DST_TO, @CITY_LNG, @CITY_LAT, @CITY_GPS_DES, @CITY_HOT, @Q_CID, @COUNTRY_COIN, @STATE_ID, @CITY_PID, @CITY_LVL_NEW, @CITY_STATUS, @COUNTRY_TELE, @CITY_SCODE)";
 public static String TABLE_NAME ="CITY";
 public static String[] KEY_LIST = { "CITY_ID"   };
 public static String[] FIELD_LIST = { "CITY_ID", "CITY_NAME", "CITY_NAME_EN", "COUNTRY_ID", "CITY_CODE", "CITY_PSOT_CODE", "CITY_LVL", "CITY_MEMO", "CITY_A", "CITY_TAG", "CITY_UTC", "CITY_DST_FROM", "CITY_DST_TO", "CITY_LNG", "CITY_LAT", "CITY_GPS_DES", "CITY_HOT", "Q_CID", "COUNTRY_COIN", "STATE_ID", "CITY_PID", "CITY_LVL_NEW", "CITY_STATUS", "COUNTRY_TELE", "CITY_SCODE" };
 public CityDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("ow_main");
   super.setInstanceClass(City.class);
 }/**
	 * 生成一条记录
	*@param para 表CITY的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(City para){

		RequestValue rv=this.createRequestValue(para);

int autoKey = super.executeUpdateAutoIncrement(SQL_INSERT, rv);
if (autoKey > 0) {
para.setCityId(autoKey);//自增
return true;
} else {
return false;
}
	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表CITY的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(City para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
int autoKey = super.executeUpdateAutoIncrement(sql, rv);//自增
if (autoKey > 0) {
para.setCityId(autoKey);
return true;
} else {
return false;
}
}/**
 * 更新一条记录
*@param para 表CITY的映射类
	 *@return 是否成功 
	 */
public boolean updateRecord(City para){

  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(SQL_UPDATE, rv);
}
/**
 * 更新一条记录
 * 
 * @param para
 *            表CITY的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean updateRecord(City para, HashMap<String, Boolean> updateFields){
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
public String getSqlSelect() {return "SELECT * FROM CITY where 1=1";}
public String getSqlUpdate() {return SQL_UPDATE;}
public String getSqlInsert() {return SQL_INSERT;}/**
	 * 根据主键返回一条记录
	*@param paraCityId CITY_ID
	*@return 记录类(City)
	*/
public City getRecord(Integer paraCityId){
	RequestValue rv = new RequestValue();
rv.addValue("CITY_ID", paraCityId, "Integer", 10);
ArrayList<City> al = super.executeQuery(SQL_SELECT, rv, new City(), FIELD_LIST);
if(al.size()>0){
City o = al.get(0);
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
	public ArrayList<City> getRecords(String whereString){
		String sql="SELECT * FROM CITY WHERE " + whereString;
		return super.executeQuery(sql, new City(), FIELD_LIST);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	*@param  fields 指定返回的字段
	*@return 记录集合
	*/
	public ArrayList<City> getRecords(String whereString, List<String> fields){
		String sql = super.createSelectSql(TABLE_NAME, whereString, fields);
		String[] arrFields = new String[fields.size()];
		arrFields = fields.toArray(arrFields);
		return super.executeQuery(sql, new City(), arrFields);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	* @param pkFieldName   主键
	* @param pageSize      每页记录数
	* @param currentPage   当前页
	*@return 记录集合
	*/
	public ArrayList<City> getRecords(String whereString, String pkFieldName, int pageSize, int currentPage){
		String sql="SELECT * FROM CITY WHERE " + whereString;
		return super.executeQuery(sql, new City(), FIELD_LIST, pkFieldName, pageSize, currentPage);
	}
/**
	 * 根据主键删除一条记录
	*@param paraCityId CITY_ID
	*@return 是否成功
	*/
public boolean deleteRecord(Integer paraCityId){
	RequestValue rv = new RequestValue();
rv.addValue("CITY_ID", paraCityId, "Integer", 10);
	return super.executeUpdate(SQL_DELETE, rv);
}
public RequestValue createRequestValue(City para){
RequestValue rv = new RequestValue();
rv.addValue("CITY_ID", para.getCityId(), "Integer", 10); // CITY_ID
rv.addValue("CITY_NAME", para.getCityName(), "String", 50); // CITY_NAME
rv.addValue("CITY_NAME_EN", para.getCityNameEn(), "String", 50); // CITY_NAME_EN
rv.addValue("COUNTRY_ID", para.getCountryId(), "Integer", 10); // COUNTRY_ID
rv.addValue("CITY_CODE", para.getCityCode(), "String", 50); // CITY_CODE
rv.addValue("CITY_PSOT_CODE", para.getCityPsotCode(), "String", 50); // CITY_PSOT_CODE
rv.addValue("CITY_LVL", para.getCityLvl(), "Integer", 10); // CITY_LVL
rv.addValue("CITY_MEMO", para.getCityMemo(), "String", 1500); // CITY_MEMO
rv.addValue("CITY_A", para.getCityA(), "String", 10); // CITY_A
rv.addValue("CITY_TAG", para.getCityTag(), "String", 50); // CITY_TAG
rv.addValue("CITY_UTC", para.getCityUtc(), "String", 50); // CITY_UTC
rv.addValue("CITY_DST_FROM", para.getCityDstFrom(), "String", 50); // CITY_DST_FROM
rv.addValue("CITY_DST_TO", para.getCityDstTo(), "String", 50); // CITY_DST_TO
rv.addValue("CITY_LNG", para.getCityLng(), "Integer", 30); // CITY_LNG
rv.addValue("CITY_LAT", para.getCityLat(), "Integer", 30); // CITY_LAT
rv.addValue("CITY_GPS_DES", para.getCityGpsDes(), "Integer", 30); // CITY_GPS_DES
rv.addValue("CITY_HOT", para.getCityHot(), "Integer", 10); // CITY_HOT
rv.addValue("Q_CID", para.getQCid(), "Integer", 10); // Q_CID
rv.addValue("COUNTRY_COIN", para.getCountryCoin(), "Integer", 10); // COUNTRY_COIN
rv.addValue("STATE_ID", para.getStateId(), "Integer", 10); // STATE_ID
rv.addValue("CITY_PID", para.getCityPid(), "String", 30); // CITY_PID
rv.addValue("CITY_LVL_NEW", para.getCityLvlNew(), "Integer", 10); // CITY_LVL_NEW
rv.addValue("CITY_STATUS", para.getCityStatus(), "String", 10); // CITY_STATUS
rv.addValue("COUNTRY_TELE", para.getCountryTele(), "String", 10); // COUNTRY_TELE
rv.addValue("CITY_SCODE", para.getCityScode(), "String", 10); // CITY_SCODE
return rv;
	}}