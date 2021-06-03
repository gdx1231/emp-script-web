package com.gdxsoft.web.dao;

import java.util.ArrayList;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;

/**
 * 表api_main操作类
 * 
 * @author gdx 时间：Wed Jun 02 2021 13:41:35 GMT+0800 (中国标准时间)
 */
public class ApiMainDao extends ClassDaoBase<ApiMain> implements IClassDao<ApiMain> {

	private static String SQL_INSERT = "INSERT INTO api_main(API_KEY, API_SIGN_CODE, SUP_UNID, API_CDATE, API_MDATE, API_FROM, API_TO, API_VALID_IPS, API_MEMO, API_SUPPLY_URL) 	VALUES(@API_KEY, @API_SIGN_CODE, @SUP_UNID, @API_CDATE, @API_MDATE, @API_FROM, @API_TO, @API_VALID_IPS, @API_MEMO, @API_SUPPLY_URL)";
	public static String TABLE_NAME = "api_main";
	public static String[] KEY_LIST = { "SUP_UNID" };
	public static String[] FIELD_LIST = { "API_KEY", "API_SIGN_CODE", "SUP_UNID", "API_CDATE", "API_MDATE", "API_FROM",
			"API_TO", "API_VALID_IPS", "API_MEMO", "API_SUPPLY_URL" };

	public ApiMainDao() {
		// 设置数据库连接配置名称，在 ewa_conf.xml中定义
		// super.setConfigName("cm");
		super.setInstanceClass(ApiMain.class);
		super.setTableName(TABLE_NAME);
		super.setFields(FIELD_LIST);
		super.setKeyFields(KEY_LIST);
		super.setSqlInsert(SQL_INSERT);
	}

	/**
	 * 生成一条记录
	 * 
	 * @param para 表api_main的映射类
	 * 
	 * @return 是否成功
	 */

	public boolean newRecord(ApiMain para) {

		RequestValue rv = this.createRequestValue(para);

		return super.executeUpdate(SQL_INSERT, rv);

	}

	/**
	 * 生成一条记录
	 * 
	 * @param para         表api_main的映射类
	 * @param updateFields 变化的字段Map
	 * @return
	 */
	public boolean newRecord(ApiMain para, HashMap<String, Boolean> updateFields) {
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
	 * @param paraSupUnid 供应商编号
	 * @return 记录类(ApiMain)
	 */
	public ApiMain getRecord(String paraSupUnid) {
		RequestValue rv = new RequestValue();
		rv.addValue("SUP_UNID", paraSupUnid, "String", 36);
		String sql = super.getSqlSelect();
		ArrayList<ApiMain> al = super.executeQuery(sql, rv, new ApiMain(), FIELD_LIST);
		if (al.size() > 0) {
			ApiMain o = al.get(0);
			al.clear();
			return o;
		} else {
			return null;
		}
	}

	/**
	 * 根据主键删除一条记录
	 * 
	 * @param paraSupUnid 供应商编号
	 * @return 是否成功
	 */
	public boolean deleteRecord(String paraSupUnid) {
		RequestValue rv = new RequestValue();
		rv.addValue("SUP_UNID", paraSupUnid, "String", 36);
		return super.executeUpdate(super.createDeleteSql(), rv);
	}
}