package com.gdxsoft.web.dao;

import com.gdxsoft.easyweb.datasource.ClassDaoBase;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.script.RequestValue;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 表SITE_MAIN操作类
 * 
 * @author gdx 时间：Sat Jul 11 2020 20:19:17 GMT+0800 (中国标准时间)
 */
public class SiteMainDao extends ClassDaoBase<SiteMain> implements IClassDao<SiteMain> {

	private static String SQL_INSERT = "INSERT INTO SITE_MAIN(SIT_NAME, SIT_NAME_EN, SIT_META_DES, SIT_META_DES_EN, SIT_META_KEYS, SIT_META_KEYS_EN, SIT_CW, SIT_CW_EN, SIT_MEMO, SIT_SUP_ID, SIT_CONTACT, SIT_TELE, SIT_URLS, SIT_CDATE, SIT_MDATE, SIT_UNID, SIT_STATUS, SIT_MAIL_ORDER_NAME, SIT_MAIL_ORDER_EMAIL, SIT_MAIL_DEF_NAME, SIT_MAIL_DEF_EMAIL, SIT_LOGO, SIT_ICON_32, SIT_ICON_180, SIT_ICON_192, SIT_ICON_270, SIT_BEIAN) 	VALUES(@SIT_NAME, @SIT_NAME_EN, @SIT_META_DES, @SIT_META_DES_EN, @SIT_META_KEYS, @SIT_META_KEYS_EN, @SIT_CW, @SIT_CW_EN, @SIT_MEMO, @SIT_SUP_ID, @SIT_CONTACT, @SIT_TELE, @SIT_URLS, @SIT_CDATE, @SIT_MDATE, @SIT_UNID, @SIT_STATUS, @SIT_MAIL_ORDER_NAME, @SIT_MAIL_ORDER_EMAIL, @SIT_MAIL_DEF_NAME, @SIT_MAIL_DEF_EMAIL, @SIT_LOGO, @SIT_ICON_32, @SIT_ICON_180, @SIT_ICON_192, @SIT_ICON_270, @SIT_BEIAN)";
	public static String TABLE_NAME = "SITE_MAIN";
	public static String[] KEY_LIST = { "SIT_ID" };
	public static String[] FIELD_LIST = { "SIT_ID", "SIT_NAME", "SIT_NAME_EN", "SIT_META_DES", "SIT_META_DES_EN",
			"SIT_META_KEYS", "SIT_META_KEYS_EN", "SIT_CW", "SIT_CW_EN", "SIT_MEMO", "SIT_SUP_ID", "SIT_CONTACT",
			"SIT_TELE", "SIT_URLS", "SIT_CDATE", "SIT_MDATE", "SIT_UNID", "SIT_STATUS", "SIT_MAIL_ORDER_NAME",
			"SIT_MAIL_ORDER_EMAIL", "SIT_MAIL_DEF_NAME", "SIT_MAIL_DEF_EMAIL", "SIT_LOGO", "SIT_ICON_32",
			"SIT_ICON_180", "SIT_ICON_192", "SIT_ICON_270", "SIT_BEIAN" };

	public SiteMainDao() {
		// 设置数据库连接配置名称，在 ewa_conf.xml中定义
		// super.setConfigName("globaltravel");
		super.setInstanceClass(SiteMain.class);
		super.setTableName(TABLE_NAME);
		super.setFields(FIELD_LIST);
		super.setKeyFields(KEY_LIST);
		super.setSqlInsert(SQL_INSERT);
	}

	/**
	 * 生成一条记录
	 * 
	 * @param para 表SITE_MAIN的映射类
	 * 
	 * @return 是否成功
	 */

	public boolean newRecord(SiteMain para) {

		RequestValue rv = this.createRequestValue(para);

		int autoKey = super.executeUpdateAutoIncrement(SQL_INSERT, rv);
		if (autoKey > 0) {
			para.setSitId(autoKey);// 自增
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 生成一条记录
	 * 
	 * @param para         表SITE_MAIN的映射类
	 * @param updateFields 变化的字段Map
	 * @return
	 */
	public boolean newRecord(SiteMain para, HashMap<String, Boolean> updateFields) {
		String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
		if (sql == null) { // 没有可更新数据
			return false;
		}
		RequestValue rv = this.createRequestValue(para);
		int autoKey = super.executeUpdateAutoIncrement(sql, rv);// 自增
		if (autoKey > 0) {
			para.setSitId(autoKey);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据主键返回一条记录
	 * 
	 * @param paraSitId 网站编号
	 * @return 记录类(SiteMain)
	 */
	public SiteMain getRecord(Integer paraSitId) {
		RequestValue rv = new RequestValue();
		rv.addValue("SIT_ID", paraSitId, "Integer", 10);
		String sql = super.getSqlSelect();
		ArrayList<SiteMain> al = super.executeQuery(sql, rv, new SiteMain(), FIELD_LIST);
		if (al.size() > 0) {
			SiteMain o = al.get(0);
			al.clear();
			return o;
		} else {
			return null;
		}
	}

	/**
	 * 根据主键删除一条记录
	 * 
	 * @param paraSitId 网站编号
	 * @return 是否成功
	 */
	public boolean deleteRecord(Integer paraSitId) {
		RequestValue rv = new RequestValue();
		rv.addValue("SIT_ID", paraSitId, "Integer", 10);
		return super.executeUpdate(super.createDeleteSql(), rv);
	}
}