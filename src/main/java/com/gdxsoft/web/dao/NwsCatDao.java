package com.gdxsoft.web.dao;

import com.gdxsoft.easyweb.datasource.ClassDaoBase;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.script.RequestValue;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 表nws_cat操作类
 * 
 * @author gdx 时间：Mon May 10 2021 10:29:04 GMT+0800 (中国标准时间)
 */
public class NwsCatDao extends ClassDaoBase<NwsCat> implements IClassDao<NwsCat> {

	private static String SQL_INSERT = "INSERT INTO nws_cat(NWS_CAT_NAME, NWS_CAT_PID, NWS_CAT_LVL, NWS_CAT_ORD, NWS_CAT_TAG, NWS_CAT_MEMO, SIT_ID, SUP_ID, NWS_CAT_UNID, NWS_CAT_NAME_EN) 	VALUES(@NWS_CAT_NAME, @NWS_CAT_PID, @NWS_CAT_LVL, @NWS_CAT_ORD, @NWS_CAT_TAG, @NWS_CAT_MEMO, @SIT_ID, @SUP_ID, @NWS_CAT_UNID, @NWS_CAT_NAME_EN)";
	public static String TABLE_NAME = "nws_cat";
	public static String[] KEY_LIST = { "NWS_CAT_ID" };
	public static String[] FIELD_LIST = { "NWS_CAT_ID", "NWS_CAT_NAME", "NWS_CAT_PID", "NWS_CAT_LVL", "NWS_CAT_ORD",
			"NWS_CAT_TAG", "NWS_CAT_MEMO", "SIT_ID", "SUP_ID", "NWS_CAT_UNID", "NWS_CAT_NAME_EN" };

	public NwsCatDao() {
		// 设置数据库连接配置名称，在 ewa_conf.xml中定义
		// super.setConfigName("gw");
		super.setInstanceClass(NwsCat.class);
		super.setTableName(TABLE_NAME);
		super.setFields(FIELD_LIST);
		super.setKeyFields(KEY_LIST);
		super.setSqlInsert(SQL_INSERT);
	}

	/**
	 * 生成一条记录
	 * 
	 * @param para 表nws_cat的映射类
	 * 
	 * @return 是否成功
	 */

	public boolean newRecord(NwsCat para) {

		RequestValue rv = this.createRequestValue(para);

		long autoKey = super.executeUpdateAutoIncrementLong(SQL_INSERT, rv);
		if (autoKey > 0) {
			para.setNwsCatId(autoKey);// 自增
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 生成一条记录
	 * 
	 * @param para         表nws_cat的映射类
	 * @param updateFields 变化的字段Map
	 * @return
	 */
	public boolean newRecord(NwsCat para, HashMap<String, Boolean> updateFields) {
		String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
		if (sql == null) { // 没有可更新数据
			return false;
		}
		RequestValue rv = this.createRequestValue(para);
		long autoKey = super.executeUpdateAutoIncrementLong(SQL_INSERT, rv);
		if (autoKey > 0) {
			para.setNwsCatId(autoKey);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据主键返回一条记录
	 * 
	 * @param paraNwsCatId 目录编号
	 * @return 记录类(NwsCat)
	 */
	public NwsCat getRecord(Long paraNwsCatId) {
		RequestValue rv = new RequestValue();
		rv.addValue("NWS_CAT_ID", paraNwsCatId, "Long", 19);
		String sql = super.getSqlSelect();
		ArrayList<NwsCat> al = super.executeQuery(sql, rv, new NwsCat(), FIELD_LIST);
		if (al.size() > 0) {
			NwsCat o = al.get(0);
			al.clear();
			return o;
		} else {
			return null;
		}
	}

	/**
	 * 根据主键删除一条记录
	 * 
	 * @param paraNwsCatId 目录编号
	 * @return 是否成功
	 */
	public boolean deleteRecord(Long paraNwsCatId) {
		RequestValue rv = new RequestValue();
		rv.addValue("NWS_CAT_ID", paraNwsCatId, "Long", 19);
		return super.executeUpdate(super.createDeleteSql(), rv);
	}
}