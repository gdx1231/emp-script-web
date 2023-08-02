package com.gdxsoft.web.shortUrl;

import java.util.*;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;

/**
 * 表url_short操作类
 * 
 * @author gdx 时间：Tue Dec 07 2021 13:43:12 GMT+0800 (中国标准时间)
 */
public class UrlShortDao extends ClassDaoBase<UrlShort> implements IClassDao<UrlShort> {

	private static String SQL_INSERT = "INSERT INTO url_short(url_uid, url_full, url_md5, url_status, url_cdate, url_vdate, sup_id, adm_id) "
			+ "  VALUES(@url_uid, @url_full, @url_md5, @url_status, @url_cdate, @url_vdate, @sup_id, @adm_id)";
	public static String TABLE_NAME = "url_short";
	public static String[] KEY_LIST = { "url_id" };
	public static String[] FIELD_LIST = { "url_id", "url_uid", "url_full", "url_md5", "url_status", "url_cdate",
			"url_vdate", "sup_id", "adm_id" };

	public UrlShortDao() {
		// 设置数据库连接配置名称，在 ewa_conf.xml中定义
		// super.setConfigName("work");
		super.setInstanceClass(UrlShort.class);
		super.setTableName(TABLE_NAME);
		super.setFields(FIELD_LIST);
		super.setKeyFields(KEY_LIST);
		super.setSqlInsert(SQL_INSERT);
	}

	/**
	 * 生成一条记录
	 * 
	 * @param para 表url_short的映射类
	 * 
	 * @return 是否成功
	 */

	public boolean newRecord(UrlShort para) {

		RequestValue rv = this.createRequestValue(para);

		return super.executeUpdate(SQL_INSERT, rv);

	}

	/**
	 * 生成一条记录
	 *
	 * @param para         表url_short的映射类
	 * @param updateFields 变化的字段Map
	 * @return
	 */
	public boolean newRecord(UrlShort para, HashMap<String, Boolean> updateFields) {
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
	 * @param paraUrlId url_id
	 * @return 记录类(UrlShort)
	 */
	public UrlShort getRecord(Long paraUrlId) {
		RequestValue rv = new RequestValue();
		rv.addValue("URL_ID", paraUrlId, "Long", 19);
		String sql = super.getSqlSelect();
		ArrayList<UrlShort> al = super.executeQuery(sql, rv, new UrlShort(), FIELD_LIST);
		if (al.size() > 0) {
			UrlShort o = al.get(0);
			al.clear();
			return o;
		} else {
			return null;
		}
	}

	/**
	 * 根据主键删除一条记录
	 * 
	 * @param paraUrlId url_id
	 * @return 是否成功
	 */
	public boolean deleteRecord(Long paraUrlId) {
		RequestValue rv = new RequestValue();
		rv.addValue("URL_ID", paraUrlId, "Long", 19);
		return super.executeUpdate(super.createDeleteSql(), rv);
	}
}