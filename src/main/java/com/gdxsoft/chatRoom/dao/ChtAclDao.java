package com.gdxsoft.chatRoom.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** 表cht_acl操作类
 * @author gdx 时间：Tue Jun 01 2021 09:25:21 GMT+0800 (中国标准时间)
 */
public class ChtAclDao extends ClassDaoBase<ChtAcl> implements IClassDao<ChtAcl>{

 private static String SQL_INSERT="INSERT INTO cht_acl(cht_rom_id, cht_usr_id, cht_acl_master, cht_acl_top, cht_acl_time) 	VALUES(@cht_rom_id, @cht_usr_id, @cht_acl_master, @cht_acl_top, @cht_acl_time)";
 public static String TABLE_NAME ="cht_acl";
 public static String[] KEY_LIST = { "cht_rom_id", "cht_usr_id"   };
 public static String[] FIELD_LIST = { "cht_rom_id", "cht_usr_id", "cht_acl_master", "cht_acl_top", "cht_acl_time" };
 public ChtAclDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("chat");
   super.setInstanceClass(ChtAcl.class);
   super.setTableName(TABLE_NAME);
   super.setFields(FIELD_LIST);
   super.setKeyFields(KEY_LIST);
   super.setSqlInsert(SQL_INSERT);
 }/**
	 * 生成一条记录
	*@param para 表cht_acl的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(ChtAcl para){

		RequestValue rv=this.createRequestValue(para);

		return super.executeUpdate(SQL_INSERT, rv);

	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表cht_acl的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(ChtAcl para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(sql, rv);
}/**
	 * 根据主键返回一条记录
	*@param paraChtRomId 房间号
	*@param paraChtUsrId 用户
	*@return 记录类(ChtAcl)
	*/
public ChtAcl getRecord(Long paraChtRomId, Long paraChtUsrId){
	RequestValue rv = new RequestValue();
rv.addValue("CHT_ROM_ID", paraChtRomId, "Long", 19);
rv.addValue("CHT_USR_ID", paraChtUsrId, "Long", 19);
	String sql = super.getSqlSelect();
ArrayList<ChtAcl> al = super.executeQuery(sql, rv, new ChtAcl(), FIELD_LIST);
if(al.size()>0){
ChtAcl o = al.get(0);
al.clear();
return o;
}else{
return null;
}
}
/**
	 * 根据主键删除一条记录
	*@param paraChtRomId 房间号
	*@param paraChtUsrId 用户
	*@return 是否成功
	*/
public boolean deleteRecord(Long paraChtRomId, Long paraChtUsrId){
	RequestValue rv = new RequestValue();
rv.addValue("CHT_ROM_ID", paraChtRomId, "Long", 19);
rv.addValue("CHT_USR_ID", paraChtUsrId, "Long", 19);
	return super.executeUpdate(super.createDeleteSql() , rv);
}
}