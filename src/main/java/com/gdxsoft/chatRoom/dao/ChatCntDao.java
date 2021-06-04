package com.gdxsoft.chatRoom.dao;

import java.util.ArrayList;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;

/**
 * 表chat_cnt操作类
 * 
 * @author gdx 时间：Tue Jun 01 2021 09:25:09 GMT+0800 (中国标准时间)
 */
public class ChatCntDao extends ClassDaoBase<ChatCnt> implements IClassDao<ChatCnt> {

	private static String SQL_INSERT = "INSERT INTO chat_cnt(cht_id, cht_cnt, cht_cnt_txt) 	VALUES(@cht_id, @cht_cnt, @cht_cnt_txt)";
	public static String TABLE_NAME = "chat_cnt";
	public static String[] KEY_LIST = { "cht_id" };
	public static String[] FIELD_LIST = { "cht_id", "cht_cnt", "cht_cnt_txt" };

	public ChatCntDao() {
		// 设置数据库连接配置名称，在 ewa_conf.xml中定义
		// super.setConfigName("chat");
		super.setInstanceClass(ChatCnt.class);
		super.setTableName(TABLE_NAME);
		super.setFields(FIELD_LIST);
		super.setKeyFields(KEY_LIST);
		super.setSqlInsert(SQL_INSERT);
	}

	/**
	 * 生成一条记录
	 * 
	 * @param para 表chat_cnt的映射类
	 * 
	 * @return 是否成功
	 */

	public boolean newRecord(ChatCnt para) {

		RequestValue rv = this.createRequestValue(para);

		return super.executeUpdate(SQL_INSERT, rv);

	}

	/**
	 * 生成一条记录
	 * 
	 * @param para         表chat_cnt的映射类
	 * @param updateFields 变化的字段Map
	 * @return
	 */
	public boolean newRecord(ChatCnt para, HashMap<String, Boolean> updateFields) {
		String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
		if (sql == null) { // 没有可更新数据
			return false;
		}
		RequestValue rv = this.createRequestValue(para);
		return super.executeUpdate(sql, rv);
	}

	/**
	 * 根据主键返回一条记录
	 * 
	 * @param paraChtId 内容编号
	 * @return 记录类(ChatCnt)
	 */
	public ChatCnt getRecord(Long paraChtId) {
		RequestValue rv = new RequestValue();
		rv.addValue("CHT_ID", paraChtId, "Long", 19);
		String sql = super.getSqlSelect();
		ArrayList<ChatCnt> al = super.executeQuery(sql, rv, new ChatCnt(), FIELD_LIST);
		if (al.size() > 0) {
			ChatCnt o = al.get(0);
			al.clear();
			return o;
		} else {
			return null;
		}
	}

	/**
	 * 根据主键删除一条记录
	 * 
	 * @param paraChtId 内容编号
	 * @return 是否成功
	 */
	public boolean deleteRecord(Long paraChtId) {
		RequestValue rv = new RequestValue();
		rv.addValue("CHT_ID", paraChtId, "Long", 19);
		return super.executeUpdate(super.createDeleteSql(), rv);
	}
}