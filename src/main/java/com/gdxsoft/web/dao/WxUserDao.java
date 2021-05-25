package com.gdxsoft.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** 表wx_user操作类
 * @author gdx 时间：Wed Jul 08 2020 14:45:46 GMT+0800 (中国标准时间)
 */
public class WxUserDao extends ClassDaoBase<WxUser> implements IClassDao<WxUser>{

 private static String SQL_SELECT="SELECT * FROM wx_user WHERE WX_CFG_NO=@WX_CFG_NO AND AUTH_WEIXIN_ID=@AUTH_WEIXIN_ID AND USR_UNID=@USR_UNID";
 private static String SQL_UPDATE="UPDATE wx_user SET 	 IS_WEIXIN_SUBSCRIBE = @IS_WEIXIN_SUBSCRIBE, 	 AUTH_WEIXIN_JSON = @AUTH_WEIXIN_JSON, 	 CDATE = @CDATE, 	 MDATE = @MDATE, 	 SUP_ID = @SUP_ID, 	 WX_GRP = @WX_GRP, 	 BIND_STATUS = @BIND_STATUS, 	 BIND_USR_ID = @BIND_USR_ID, 	 BIND_REMOVE_USR_ID = @BIND_REMOVE_USR_ID, 	 BIND_REMOVE_USR_UNID = @BIND_REMOVE_USR_UNID, 	 BIND_DATE = @BIND_DATE, 	 BIND_IP = @BIND_IP, 	 BIND_JSON = @BIND_JSON, 	 USER_AGENT = @USER_AGENT, 	 WX_UNION_ID = @WX_UNION_ID WHERE WX_CFG_NO=@WX_CFG_NO AND AUTH_WEIXIN_ID=@AUTH_WEIXIN_ID AND USR_UNID=@USR_UNID";
 private static String SQL_DELETE="DELETE FROM wx_user WHERE WX_CFG_NO=@WX_CFG_NO AND AUTH_WEIXIN_ID=@AUTH_WEIXIN_ID AND USR_UNID=@USR_UNID";
 private static String SQL_INSERT="INSERT INTO wx_user(WX_CFG_NO, AUTH_WEIXIN_ID, USR_UNID, IS_WEIXIN_SUBSCRIBE, AUTH_WEIXIN_JSON, CDATE, MDATE, SUP_ID, WX_GRP, BIND_STATUS, BIND_USR_ID, BIND_REMOVE_USR_ID, BIND_REMOVE_USR_UNID, BIND_DATE, BIND_IP, BIND_JSON, USER_AGENT, WX_UNION_ID) 	VALUES(@WX_CFG_NO, @AUTH_WEIXIN_ID, @USR_UNID, @IS_WEIXIN_SUBSCRIBE, @AUTH_WEIXIN_JSON, @CDATE, @MDATE, @SUP_ID, @WX_GRP, @BIND_STATUS, @BIND_USR_ID, @BIND_REMOVE_USR_ID, @BIND_REMOVE_USR_UNID, @BIND_DATE, @BIND_IP, @BIND_JSON, @USER_AGENT, @WX_UNION_ID)";
 public static String TABLE_NAME ="wx_user";
 public static String[] KEY_LIST = { "WX_CFG_NO", "AUTH_WEIXIN_ID", "USR_UNID"   };
 public static String[] FIELD_LIST = { "WX_CFG_NO", "AUTH_WEIXIN_ID", "USR_UNID", "IS_WEIXIN_SUBSCRIBE", "AUTH_WEIXIN_JSON", "CDATE", "MDATE", "SUP_ID", "WX_GRP", "BIND_STATUS", "BIND_USR_ID", "BIND_REMOVE_USR_ID", "BIND_REMOVE_USR_UNID", "BIND_DATE", "BIND_IP", "BIND_JSON", "USER_AGENT", "WX_UNION_ID" };
 public WxUserDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("ex");
 }/**
	 * 生成一条记录
	*@param para 表wx_user的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(WxUser para){

		RequestValue rv=this.createRequestValue(para);

		return super.executeUpdate(SQL_INSERT, rv);

	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表wx_user的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(WxUser para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(sql, rv);
}/**
 * 更新一条记录
*@param para 表wx_user的映射类
	 *@return 是否成功 
	 */
public boolean updateRecord(WxUser para){

  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(SQL_UPDATE, rv);
}
/**
 * 更新一条记录
 * 
 * @param para
 *            表wx_user的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean updateRecord(WxUser para, HashMap<String, Boolean> updateFields){
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
public String getSqlSelect() {return "SELECT * FROM wx_user where 1=1";}
public String getSqlUpdate() {return SQL_UPDATE;}
public String getSqlInsert() {return SQL_INSERT;}/**
	 * 根据主键返回一条记录
	*@param paraWxCfgNo WX_CFG_NO
	*@param paraAuthWeixinId AUTH_WEIXIN_ID
	*@param paraUsrUnid USR_UNID
	*@return 记录类(WxUser)
	*/
public WxUser getRecord(String paraWxCfgNo, String paraAuthWeixinId, String paraUsrUnid){
	RequestValue rv = new RequestValue();
rv.addValue("WX_CFG_NO", paraWxCfgNo, "String", 50);
rv.addValue("AUTH_WEIXIN_ID", paraAuthWeixinId, "String", 150);
rv.addValue("USR_UNID", paraUsrUnid, "String", 36);
ArrayList<WxUser> al = super.executeQuery(SQL_SELECT, rv, new WxUser(), FIELD_LIST);
if(al.size()>0){
WxUser o = al.get(0);
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
	public ArrayList<WxUser> getRecords(String whereString){
		String sql="SELECT * FROM wx_user WHERE " + whereString;
		return super.executeQuery(sql, new WxUser(), FIELD_LIST);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	*@param  fields 指定返回的字段
	*@return 记录集合
	*/
	public ArrayList<WxUser> getRecords(String whereString, List<String> fields){
		String sql = super.createSelectSql(TABLE_NAME, whereString, fields);
		String[] arrFields = new String[fields.size()];
		arrFields = fields.toArray(arrFields);
		return super.executeQuery(sql, new WxUser(), arrFields);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	* @param pkFieldName   主键
	* @param pageSize      每页记录数
	* @param currentPage   当前页
	*@return 记录集合
	*/
	public ArrayList<WxUser> getRecords(String whereString, String pkFieldName, int pageSize, int currentPage){
		String sql="SELECT * FROM wx_user WHERE " + whereString;
		return super.executeQuery(sql, new WxUser(), FIELD_LIST, pkFieldName, pageSize, currentPage);
	}
/**
	 * 根据主键删除一条记录
	*@param paraWxCfgNo WX_CFG_NO
	*@param paraAuthWeixinId AUTH_WEIXIN_ID
	*@param paraUsrUnid USR_UNID
	*@return 是否成功
	*/
public boolean deleteRecord(String paraWxCfgNo, String paraAuthWeixinId, String paraUsrUnid){
	RequestValue rv = new RequestValue();
rv.addValue("WX_CFG_NO", paraWxCfgNo, "String", 50);
rv.addValue("AUTH_WEIXIN_ID", paraAuthWeixinId, "String", 150);
rv.addValue("USR_UNID", paraUsrUnid, "String", 36);
	return super.executeUpdate(SQL_DELETE, rv);
}
public RequestValue createRequestValue(WxUser para){
RequestValue rv = new RequestValue();
rv.addValue("WX_CFG_NO", para.getWxCfgNo(), "String", 50); // WX_CFG_NO
rv.addValue("AUTH_WEIXIN_ID", para.getAuthWeixinId(), "String", 150); // AUTH_WEIXIN_ID
rv.addValue("USR_UNID", para.getUsrUnid(), "String", 36); // USR_UNID
rv.addValue("IS_WEIXIN_SUBSCRIBE", para.getIsWeixinSubscribe(), "String", 50); // 是否关注微信号
rv.addValue("AUTH_WEIXIN_JSON", para.getAuthWeixinJson(), "String", 8000); // AUTH_WEIXIN_JSON
rv.addValue("CDATE", para.getCdate(), "Date", 19); // CDATE
rv.addValue("MDATE", para.getMdate(), "Date", 19); // MDATE
rv.addValue("SUP_ID", para.getSupId(), "Integer", 10); // SUP_ID
rv.addValue("WX_GRP", para.getWxGrp(), "Integer", 10); // 微信组
rv.addValue("BIND_STATUS", para.getBindStatus(), "String", 20); // 绑定状态
rv.addValue("BIND_USR_ID", para.getBindUsrId(), "Integer", 10); // 绑定的用户ID
rv.addValue("BIND_REMOVE_USR_ID", para.getBindRemoveUsrId(), "Integer", 10); // 去除的用户ID
rv.addValue("BIND_REMOVE_USR_UNID", para.getBindRemoveUsrUnid(), "String", 36); // 去除用户当前的UNID
rv.addValue("BIND_DATE", para.getBindDate(), "Date", 19); // 绑定日期
rv.addValue("BIND_IP", para.getBindIp(), "String", 40); // 绑定IP
rv.addValue("BIND_JSON", para.getBindJson(), "String", 2000); // 绑定的JSON
rv.addValue("USER_AGENT", para.getUserAgent(), "String", 500); // USER_AGENT
rv.addValue("WX_UNION_ID", para.getWxUnionId(), "String", 40); // 微信的 UNIONID
return rv;
	}}