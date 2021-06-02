package com.gdxsoft.chatRoom.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** 表chat_room操作类
 * @author gdx 时间：Wed Jun 02 2021 11:30:10 GMT+0800 (中国标准时间)
 */
public class ChatRoomDao extends ClassDaoBase<ChatRoom> implements IClassDao<ChatRoom>{

 private static String SQL_INSERT="INSERT INTO chat_room(cht_rom_id, cht_rom_name, cht_rom_name_en, cht_rom_memo, cht_rom_memo_en, cht_rom_status, cht_rom_creator, cht_rom_owner, cht_rom_ctime, cht_rom_mtime, cht_rom_unid, cht_rom_type, cht_rom_ref, cht_rom_ref_id, cht_rom_tag0, cht_rom_tag1, cht_rom_tag2, cht_rom_sup_id, cht_last_id, cht_last_time) 	VALUES(@cht_rom_id, @cht_rom_name, @cht_rom_name_en, @cht_rom_memo, @cht_rom_memo_en, @cht_rom_status, @cht_rom_creator, @cht_rom_owner, @cht_rom_ctime, @cht_rom_mtime, @cht_rom_unid, @cht_rom_type, @cht_rom_ref, @cht_rom_ref_id, @cht_rom_tag0, @cht_rom_tag1, @cht_rom_tag2, @cht_rom_sup_id, @cht_last_id, @cht_last_time)";
 public static String TABLE_NAME ="chat_room";
 public static String[] KEY_LIST = { "cht_rom_id"   };
 public static String[] FIELD_LIST = { "cht_rom_id", "cht_rom_name", "cht_rom_name_en", "cht_rom_memo", "cht_rom_memo_en", "cht_rom_status", "cht_rom_creator", "cht_rom_owner", "cht_rom_ctime", "cht_rom_mtime", "cht_rom_unid", "cht_rom_type", "cht_rom_ref", "cht_rom_ref_id", "cht_rom_tag0", "cht_rom_tag1", "cht_rom_tag2", "cht_rom_sup_id", "cht_last_id", "cht_last_time" };
 public ChatRoomDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("chat");
   super.setInstanceClass(ChatRoom.class);
   super.setTableName(TABLE_NAME);
   super.setFields(FIELD_LIST);
   super.setKeyFields(KEY_LIST);
   super.setSqlInsert(SQL_INSERT);
 }/**
	 * 生成一条记录
	*@param para 表chat_room的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(ChatRoom para){

		RequestValue rv=this.createRequestValue(para);

		return super.executeUpdate(SQL_INSERT, rv);

	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表chat_room的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(ChatRoom para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(sql, rv);
}/**
	 * 根据主键返回一条记录
	*@param paraChtRomId 聊天室
	*@return 记录类(ChatRoom)
	*/
public ChatRoom getRecord(Long paraChtRomId){
	RequestValue rv = new RequestValue();
rv.addValue("CHT_ROM_ID", paraChtRomId, "Long", 19);
	String sql = super.getSqlSelect();
ArrayList<ChatRoom> al = super.executeQuery(sql, rv, new ChatRoom(), FIELD_LIST);
if(al.size()>0){
ChatRoom o = al.get(0);
al.clear();
return o;
}else{
return null;
}
}
/**
	 * 根据主键删除一条记录
	*@param paraChtRomId 聊天室
	*@return 是否成功
	*/
public boolean deleteRecord(Long paraChtRomId){
	RequestValue rv = new RequestValue();
rv.addValue("CHT_ROM_ID", paraChtRomId, "Long", 19);
	return super.executeUpdate(super.createDeleteSql() , rv);
}
}