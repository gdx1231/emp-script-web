package com.gdxsoft.chatRoom.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** 表chat_user操作类
 * @author gdx 时间：Fri Jun 04 2021 16:18:12 GMT+0800 (中国标准时间)
 */
public class ChatUserDao extends ClassDaoBase<ChatUser> implements IClassDao<ChatUser>{

 private static String SQL_INSERT="INSERT INTO chat_user(cht_usr_id, cht_usr_unid, cht_usr_name, cht_usr_gender, cht_usr_pic_id, cht_usr_bg_id, cht_usr_nation, cht_usr_mobile, cht_usr_ctime, cht_usr_mtime, cht_usr_slogan, cht_usr_ref, cht_usr_ref_id, cht_usr_memo, cht_usr_sup_id, cht_usr_status) 	VALUES(@cht_usr_id, @cht_usr_unid, @cht_usr_name, @cht_usr_gender, @cht_usr_pic_id, @cht_usr_bg_id, @cht_usr_nation, @cht_usr_mobile, @cht_usr_ctime, @cht_usr_mtime, @cht_usr_slogan, @cht_usr_ref, @cht_usr_ref_id, @cht_usr_memo, @cht_usr_sup_id, @cht_usr_status)";
 public static String TABLE_NAME ="chat_user";
 public static String[] KEY_LIST = { "cht_usr_id"   };
 public static String[] FIELD_LIST = { "cht_usr_id", "cht_usr_unid", "cht_usr_name", "cht_usr_gender", "cht_usr_pic_id", "cht_usr_bg_id", "cht_usr_nation", "cht_usr_mobile", "cht_usr_ctime", "cht_usr_mtime", "cht_usr_slogan", "cht_usr_ref", "cht_usr_ref_id", "cht_usr_memo", "cht_usr_sup_id", "cht_usr_status" };
 public ChatUserDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("chat");
   super.setInstanceClass(ChatUser.class);
   super.setTableName(TABLE_NAME);
   super.setFields(FIELD_LIST);
   super.setKeyFields(KEY_LIST);
   super.setSqlInsert(SQL_INSERT);
 }/**
	 * 生成一条记录
	*@param para 表chat_user的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(ChatUser para){

		RequestValue rv=this.createRequestValue(para);

		return super.executeUpdate(SQL_INSERT, rv);

	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表chat_user的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(ChatUser para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(sql, rv);
}/**
	 * 根据主键返回一条记录
	*@param paraChtUsrId 用户编号
	*@return 记录类(ChatUser)
	*/
public ChatUser getRecord(Long paraChtUsrId){
	RequestValue rv = new RequestValue();
rv.addValue("CHT_USR_ID", paraChtUsrId, "Long", 19);
	String sql = super.getSqlSelect();
ArrayList<ChatUser> al = super.executeQuery(sql, rv, new ChatUser(), FIELD_LIST);
if(al.size()>0){
ChatUser o = al.get(0);
al.clear();
return o;
}else{
return null;
}
}
/**
	 * 根据主键删除一条记录
	*@param paraChtUsrId 用户编号
	*@return 是否成功
	*/
public boolean deleteRecord(Long paraChtUsrId){
	RequestValue rv = new RequestValue();
rv.addValue("CHT_USR_ID", paraChtUsrId, "Long", 19);
	return super.executeUpdate(super.createDeleteSql() , rv);
}
}