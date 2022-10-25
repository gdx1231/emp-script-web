package com.gdxsoft.web.dao;

import java.util.*;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;

/**
 * 表BAS_TAG操作类
 * 
 * @author gdx 时间：Sat Jul 11 2020 19:53:56 GMT+0800 (中国标准时间)
 */
public class BasTagDao extends ClassDaoBase<BasTag> implements IClassDao<BasTag> {

	private static String SQL_INSERT = "INSERT INTO BAS_TAG(BAS_TAG, BT_ID, BAS_TAG_NAME, BAS_TAG_HTML, BAS_TAG_MEMO, BAS_TAG_ORD, BAS_TAG_GRP, BAS_TAG_PARA1, BAS_TAG_PARA2, BAS_TAG_PARA3, BAS_TAG_PARA4, BAS_TAG_PARA5, BAS_TAG_PARA6, BAS_TAG_PARA7, BAS_TAG_PARA8, BAS_TAG_PARA9, BAS_TAG_PARA10, BAS_TAG_NAME_EN, BAS_TAG_HTML_EN, BAS_TAG_MEMO_EN, GRP_TYPE) 	VALUES(@BAS_TAG, @BT_ID, @BAS_TAG_NAME, @BAS_TAG_HTML, @BAS_TAG_MEMO, @BAS_TAG_ORD, @BAS_TAG_GRP, @BAS_TAG_PARA1, @BAS_TAG_PARA2, @BAS_TAG_PARA3, @BAS_TAG_PARA4, @BAS_TAG_PARA5, @BAS_TAG_PARA6, @BAS_TAG_PARA7, @BAS_TAG_PARA8, @BAS_TAG_PARA9, @BAS_TAG_PARA10, @BAS_TAG_NAME_EN, @BAS_TAG_HTML_EN, @BAS_TAG_MEMO_EN, @GRP_TYPE)";
	public static String TABLE_NAME = "BAS_TAG";
	public static String[] KEY_LIST = { "BAS_TAG" };
	public static String[] FIELD_LIST = { "BAS_TAG", "BT_ID", "BAS_TAG_NAME", "BAS_TAG_HTML", "BAS_TAG_MEMO",
			"BAS_TAG_ORD", "BAS_TAG_GRP", "BAS_TAG_PARA1", "BAS_TAG_PARA2", "BAS_TAG_PARA3", "BAS_TAG_PARA4",
			"BAS_TAG_PARA5", "BAS_TAG_PARA6", "BAS_TAG_PARA7", "BAS_TAG_PARA8", "BAS_TAG_PARA9", "BAS_TAG_PARA10",
			"BAS_TAG_NAME_EN", "BAS_TAG_HTML_EN", "BAS_TAG_MEMO_EN", "GRP_TYPE" };

	public BasTagDao() {
		// 设置数据库连接配置名称，在 ewa_conf.xml中定义
		// super.setConfigName("ow_main");
		super.setInstanceClass(BasTag.class);
		super.setTableName(TABLE_NAME);
		super.setFields(FIELD_LIST);
		super.setKeyFields(KEY_LIST);
		super.setSqlInsert(SQL_INSERT);
	}

	/**
	 * 生成一条记录
	 * 
	 * @param para 表BAS_TAG的映射类
	 * 
	 * @return 是否成功
	 */

	public boolean newRecord(BasTag para) {

		RequestValue rv = this.createRequestValue(para);

		return super.executeUpdate(SQL_INSERT, rv);

	}

	/**
	 * 生成一条记录
	 * 
	 * @param para         表BAS_TAG的映射类
	 * @param updateFields 变化的字段Map
	 * @return
	 */
	public boolean newRecord(BasTag para, HashMap<String, Boolean> updateFields) {
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
	 * @param paraBasTag BAS_TAG
	 * @return 记录类(BasTag)
	 */
	public BasTag getRecord(String paraBasTag) {
		RequestValue rv = new RequestValue();
		rv.addValue("BAS_TAG", paraBasTag, "String", 50);
		String sql = super.getSqlSelect();
		ArrayList<BasTag> al = super.executeQuery(sql, rv, new BasTag(), FIELD_LIST);
		if (al.size() > 0) {
			BasTag o = al.get(0);
			al.clear();
			return o;
		} else {
			return null;
		}
	}

	/**
	 * 根据主键删除一条记录
	 * 
	 * @param paraBasTag BAS_TAG
	 * @return 是否成功
	 */
	public boolean deleteRecord(String paraBasTag) {
		RequestValue rv = new RequestValue();
		rv.addValue("BAS_TAG", paraBasTag, "String", 50);
		return super.executeUpdate(super.createDeleteSql(), rv);
	}
}