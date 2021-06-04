package com.gdxsoft.chatRoom.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** 表chat_att操作类
 * @author gdx 时间：Fri Jun 04 2021 17:27:10 GMT+0800 (中国标准时间)
 */
public class ChatAttDao extends ClassDaoBase<ChatAtt> implements IClassDao<ChatAtt>{

 private static String SQL_INSERT="INSERT INTO chat_att(cht_att_id, cht_usr_id, cht_id, cht_att_name, cht_att_src_name, cht_att_path, cht_att_size, cht_att_ext, cht_att_md5, cht_att_time, cht_att_status, cht_att_url, cht_att_sup_id) 	VALUES(@cht_att_id, @cht_usr_id, @cht_id, @cht_att_name, @cht_att_src_name, @cht_att_path, @cht_att_size, @cht_att_ext, @cht_att_md5, @cht_att_time, @cht_att_status, @cht_att_url, @cht_att_sup_id)";
 public static String TABLE_NAME ="chat_att";
 public static String[] KEY_LIST = { "cht_att_id"   };
 public static String[] FIELD_LIST = { "cht_att_id", "cht_usr_id", "cht_id", "cht_att_name", "cht_att_src_name", "cht_att_path", "cht_att_size", "cht_att_ext", "cht_att_md5", "cht_att_time", "cht_att_status", "cht_att_url", "cht_att_sup_id" };
 public ChatAttDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("chat");
   super.setInstanceClass(ChatAtt.class);
   super.setTableName(TABLE_NAME);
   super.setFields(FIELD_LIST);
   super.setKeyFields(KEY_LIST);
   super.setSqlInsert(SQL_INSERT);
 }/**
	 * 生成一条记录
	*@param para 表chat_att的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(ChatAtt para){

		RequestValue rv=this.createRequestValue(para);

		return super.executeUpdate(SQL_INSERT, rv);

	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表chat_att的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(ChatAtt para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(sql, rv);
}/**
	 * 根据主键返回一条记录
	*@param paraChtAttId 附件编号
	*@return 记录类(ChatAtt)
	*/
public ChatAtt getRecord(Long paraChtAttId){
	RequestValue rv = new RequestValue();
rv.addValue("CHT_ATT_ID", paraChtAttId, "Long", 19);
	String sql = super.getSqlSelect();
ArrayList<ChatAtt> al = super.executeQuery(sql, rv, new ChatAtt(), FIELD_LIST);
if(al.size()>0){
ChatAtt o = al.get(0);
al.clear();
return o;
}else{
return null;
}
}
/**
	 * 根据主键删除一条记录
	*@param paraChtAttId 附件编号
	*@return 是否成功
	*/
public boolean deleteRecord(Long paraChtAttId){
	RequestValue rv = new RequestValue();
rv.addValue("CHT_ATT_ID", paraChtAttId, "Long", 19);
	return super.executeUpdate(super.createDeleteSql() , rv);
}
}