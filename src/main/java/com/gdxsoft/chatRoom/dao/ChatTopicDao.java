package com.gdxsoft.chatRoom.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** 表chat_topic操作类
 * @author gdx 时间：Tue Jun 01 2021 09:25:04 GMT+0800 (中国标准时间)
 */
public class ChatTopicDao extends ClassDaoBase<ChatTopic> implements IClassDao<ChatTopic>{

 private static String SQL_INSERT="INSERT INTO chat_topic(cht_id, cht_rom_id, cht_lvl, cht_type, cht_status, cht_ctime, cht_mtime, cht_usr_id, cht_ip, cht_ua) 	VALUES(@cht_id, @cht_rom_id, @cht_lvl, @cht_type, @cht_status, @cht_ctime, @cht_mtime, @cht_usr_id, @cht_ip, @cht_ua)";
 public static String TABLE_NAME ="chat_topic";
 public static String[] KEY_LIST = { "cht_id"   };
 public static String[] FIELD_LIST = { "cht_id", "cht_rom_id", "cht_lvl", "cht_type", "cht_status", "cht_ctime", "cht_mtime", "cht_usr_id", "cht_ip", "cht_ua" };
 public ChatTopicDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("chat");
   super.setInstanceClass(ChatTopic.class);
   super.setTableName(TABLE_NAME);
   super.setFields(FIELD_LIST);
   super.setKeyFields(KEY_LIST);
   super.setSqlInsert(SQL_INSERT);
 }/**
	 * 生成一条记录
	*@param para 表chat_topic的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(ChatTopic para){

		RequestValue rv=this.createRequestValue(para);

		return super.executeUpdate(SQL_INSERT, rv);

	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表chat_topic的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(ChatTopic para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(sql, rv);
}/**
	 * 根据主键返回一条记录
	*@param paraChtId 内容编号
	*@return 记录类(ChatTopic)
	*/
public ChatTopic getRecord(Long paraChtId){
	RequestValue rv = new RequestValue();
rv.addValue("CHT_ID", paraChtId, "Long", 19);
	String sql = super.getSqlSelect();
ArrayList<ChatTopic> al = super.executeQuery(sql, rv, new ChatTopic(), FIELD_LIST);
if(al.size()>0){
ChatTopic o = al.get(0);
al.clear();
return o;
}else{
return null;
}
}
/**
	 * 根据主键删除一条记录
	*@param paraChtId 内容编号
	*@return 是否成功
	*/
public boolean deleteRecord(Long paraChtId){
	RequestValue rv = new RequestValue();
rv.addValue("CHT_ID", paraChtId, "Long", 19);
	return super.executeUpdate(super.createDeleteSql() , rv);
}
}