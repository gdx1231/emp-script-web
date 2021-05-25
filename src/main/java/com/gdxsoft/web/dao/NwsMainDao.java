package com.gdxsoft.web.dao;

import com.gdxsoft.easyweb.datasource.ClassDaoBase;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.script.RequestValue;

import java.util.ArrayList;
import java.util.HashMap;
/** 表nws_main操作类
 * @author gdx 时间：Mon May 10 2021 12:01:40 GMT+0800 (中国标准时间)
 */
public class NwsMainDao extends ClassDaoBase<NwsMain> implements IClassDao<NwsMain>{

 private static String SQL_INSERT="INSERT INTO nws_main(ADM_ID, NWS_SUBJECT, NWS_MEMO, NWS_KEYWORDS, NWS_CNT, NWS_CDATE, NWS_MDATE, NWS_DDATE, NWS_EDATE, NWS_TAG, NWS_SRC1, NWS_SRC2, NWS_AUTH1, NWS_AUTH2, NWS_GUID, NWS_HOT, NWS_HOME, SOLR, NWS_PIC_SIZES, SUP_ID, NWS_VOD, NWS_HEAD_PIC, NWS_CNT_TXT, NWS_REF0, NWS_REF1, NWS_TAG0, NWS_TAG1) 	VALUES(@ADM_ID, @NWS_SUBJECT, @NWS_MEMO, @NWS_KEYWORDS, @NWS_CNT, @NWS_CDATE, @NWS_MDATE, @NWS_DDATE, @NWS_EDATE, @NWS_TAG, @NWS_SRC1, @NWS_SRC2, @NWS_AUTH1, @NWS_AUTH2, @NWS_GUID, @NWS_HOT, @NWS_HOME, @SOLR, @NWS_PIC_SIZES, @SUP_ID, @NWS_VOD, @NWS_HEAD_PIC, @NWS_CNT_TXT, @NWS_REF0, @NWS_REF1, @NWS_TAG0, @NWS_TAG1)";
 public static String TABLE_NAME ="nws_main";
 public static String[] KEY_LIST = { "NWS_ID"   };
 public static String[] FIELD_LIST = { "NWS_ID", "ADM_ID", "NWS_SUBJECT", "NWS_MEMO", "NWS_KEYWORDS", "NWS_CNT", "NWS_CDATE", "NWS_MDATE", "NWS_DDATE", "NWS_EDATE", "NWS_TAG", "NWS_SRC1", "NWS_SRC2", "NWS_AUTH1", "NWS_AUTH2", "NWS_GUID", "NWS_HOT", "NWS_HOME", "SOLR", "NWS_PIC_SIZES", "SUP_ID", "NWS_VOD", "NWS_HEAD_PIC", "NWS_CNT_TXT", "NWS_REF0", "NWS_REF1", "NWS_TAG0", "NWS_TAG1" };
 public NwsMainDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("gw");
   super.setInstanceClass(NwsMain.class);
   super.setTableName(TABLE_NAME);
   super.setFields(FIELD_LIST);
   super.setKeyFields(KEY_LIST);
   super.setSqlInsert(SQL_INSERT);
 }/**
	 * 生成一条记录
	*@param para 表nws_main的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(NwsMain para){

		RequestValue rv=this.createRequestValue(para);

long autoKey = super.executeUpdateAutoIncrementLong(SQL_INSERT, rv);
if (autoKey > 0) {
para.setNwsId(autoKey);//自增
return true;
} else {
return false;
}
	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表nws_main的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(NwsMain para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
long autoKey = super.executeUpdateAutoIncrementLong(SQL_INSERT, rv);
if (autoKey > 0) {
para.setNwsId(autoKey);
return true;
} else {
return false;
}
}/**
	 * 根据主键返回一条记录
	*@param paraNwsId NWS_ID
	*@return 记录类(NwsMain)
	*/
public NwsMain getRecord(Long paraNwsId){
	RequestValue rv = new RequestValue();
rv.addValue("NWS_ID", paraNwsId, "Long", 19);
	String sql = super.getSqlSelect();
ArrayList<NwsMain> al = super.executeQuery(sql, rv, new NwsMain(), FIELD_LIST);
if(al.size()>0){
NwsMain o = al.get(0);
al.clear();
return o;
}else{
return null;
}
}
/**
	 * 根据主键删除一条记录
	*@param paraNwsId NWS_ID
	*@return 是否成功
	*/
public boolean deleteRecord(Long paraNwsId){
	RequestValue rv = new RequestValue();
rv.addValue("NWS_ID", paraNwsId, "Long", 19);
	return super.executeUpdate(super.createDeleteSql() , rv);
}
}