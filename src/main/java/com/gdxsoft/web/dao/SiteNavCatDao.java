package com.gdxsoft.web.dao;

import com.gdxsoft.easyweb.datasource.ClassDaoBase;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.script.RequestValue;

import java.util.ArrayList;
import java.util.HashMap;
/** 表site_nav_cat操作类
 * @author gdx 时间：Sat Jul 11 2020 20:24:48 GMT+0800 (中国标准时间)
 */
public class SiteNavCatDao extends ClassDaoBase<SiteNavCat> implements IClassDao<SiteNavCat>{

 private static String SQL_INSERT="INSERT INTO site_nav_cat(SIT_NAV_NAME, SIT_NAV_NAME_EN, SIT_NAV_PID, SIT_NAV_ORD, SIT_NAV_LVL, SIT_NAV_MEMO, SIT_NAV_URL, SIT_NAV_STATUS, SIT_NAV_TYPE, SIT_NAV_CDATE, SIT_NAV_MDATE, SUP_ID, ADM_ID, SIT_ID, SIT_NAV_ATTR, SIT_NAV_URL_EN, SIT_NAV_TAG, SIT_NAV_KEYWORDS, SIT_NAV_DESC, SIT_NAV_TITLE, SIT_NAV_KEYWORDS_EN, SIT_NAV_DESC_EN, SIT_NAV_TITLE_EN, HEADER_GID, HEADER_GID_EN, SIT_NAV_CHILDEN, SIT_NAV_CSS, SIT_NAV_HOT) 	VALUES(@SIT_NAV_NAME, @SIT_NAV_NAME_EN, @SIT_NAV_PID, @SIT_NAV_ORD, @SIT_NAV_LVL, @SIT_NAV_MEMO, @SIT_NAV_URL, @SIT_NAV_STATUS, @SIT_NAV_TYPE, @SIT_NAV_CDATE, @SIT_NAV_MDATE, @SUP_ID, @ADM_ID, @SIT_ID, @SIT_NAV_ATTR, @SIT_NAV_URL_EN, @SIT_NAV_TAG, @SIT_NAV_KEYWORDS, @SIT_NAV_DESC, @SIT_NAV_TITLE, @SIT_NAV_KEYWORDS_EN, @SIT_NAV_DESC_EN, @SIT_NAV_TITLE_EN, @HEADER_GID, @HEADER_GID_EN, @SIT_NAV_CHILDEN, @SIT_NAV_CSS, @SIT_NAV_HOT)";
 public static String TABLE_NAME ="site_nav_cat";
 public static String[] KEY_LIST = { "SIT_NAV_ID"   };
 public static String[] FIELD_LIST = { "SIT_NAV_ID", "SIT_NAV_NAME", "SIT_NAV_NAME_EN", "SIT_NAV_PID", "SIT_NAV_ORD", "SIT_NAV_LVL", "SIT_NAV_MEMO", "SIT_NAV_URL", "SIT_NAV_STATUS", "SIT_NAV_TYPE", "SIT_NAV_CDATE", "SIT_NAV_MDATE", "SUP_ID", "ADM_ID", "SIT_ID", "SIT_NAV_ATTR", "SIT_NAV_URL_EN", "SIT_NAV_TAG", "SIT_NAV_KEYWORDS", "SIT_NAV_DESC", "SIT_NAV_TITLE", "SIT_NAV_KEYWORDS_EN", "SIT_NAV_DESC_EN", "SIT_NAV_TITLE_EN", "HEADER_GID", "HEADER_GID_EN", "SIT_NAV_CHILDEN", "SIT_NAV_CSS", "SIT_NAV_HOT" };
 public SiteNavCatDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("ex");
   super.setInstanceClass(SiteNavCat.class);
   super.setTableName(TABLE_NAME);
   super.setFields(FIELD_LIST);
   super.setKeyFields(KEY_LIST);
   super.setSqlInsert(SQL_INSERT);
 }/**
	 * 生成一条记录
	*@param para 表site_nav_cat的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(SiteNavCat para){

		RequestValue rv=this.createRequestValue(para);

int autoKey = super.executeUpdateAutoIncrement(SQL_INSERT, rv);
if (autoKey > 0) {
para.setSitNavId(autoKey);//自增
return true;
} else {
return false;
}
	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表site_nav_cat的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(SiteNavCat para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
int autoKey = super.executeUpdateAutoIncrement(sql, rv);//自增
if (autoKey > 0) {
para.setSitNavId(autoKey);
return true;
} else {
return false;
}
}/**
	 * 根据主键返回一条记录
	*@param paraSitNavId SIT_NAV_ID
	*@return 记录类(SiteNavCat)
	*/
public SiteNavCat getRecord(Integer paraSitNavId){
	RequestValue rv = new RequestValue();
rv.addValue("SIT_NAV_ID", paraSitNavId, "Integer", 10);
	String sql = super.getSqlSelect();
ArrayList<SiteNavCat> al = super.executeQuery(sql, rv, new SiteNavCat(), FIELD_LIST);
if(al.size()>0){
SiteNavCat o = al.get(0);
al.clear();
return o;
}else{
return null;
}
}
/**
	 * 根据主键删除一条记录
	*@param paraSitNavId SIT_NAV_ID
	*@return 是否成功
	*/
public boolean deleteRecord(Integer paraSitNavId){
	RequestValue rv = new RequestValue();
rv.addValue("SIT_NAV_ID", paraSitNavId, "Integer", 10);
	return super.executeUpdate(super.createDeleteSql() , rv);
}
}