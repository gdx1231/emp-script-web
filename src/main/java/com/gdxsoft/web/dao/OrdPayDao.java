package com.gdxsoft.web.dao;

import java.util.ArrayList;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;

/**
 * 表ORD_PAY操作类
 * 
 * @author gdx 时间：Sat Jul 11 2020 20:36:08 GMT+0800 (中国标准时间)
 */
public class OrdPayDao extends ClassDaoBase<OrdPay> implements IClassDao<OrdPay> {

	private static String SQL_INSERT = "INSERT INTO ORD_PAY(ORD_ID, BAS_TAG, SUP_ID, ORD_PAY_MONEY, ORD_PAY_OK, ORD_PAY_DATE, ORD_PAY_OK_DATE, ORD_PAY_MEMO, ADM_ID, ORD_PAY_PARA1, ORD_PAY_PARA2, ORD_PAY_PARA3, ORD_PAY_PARA4, ORD_PAY_PARA5, ORD_PAY_PARA6, ORD_PAY_PARA7, ORD_PAY_PARA8, ORD_PAY_PARA9, ORD_PAY_PARA10, ORD_OL_XML, ORD_OL_OID, ORD_OL_PROVIDER) 	VALUES(@ORD_ID, @BAS_TAG, @SUP_ID, @ORD_PAY_MONEY, @ORD_PAY_OK, @ORD_PAY_DATE, @ORD_PAY_OK_DATE, @ORD_PAY_MEMO, @ADM_ID, @ORD_PAY_PARA1, @ORD_PAY_PARA2, @ORD_PAY_PARA3, @ORD_PAY_PARA4, @ORD_PAY_PARA5, @ORD_PAY_PARA6, @ORD_PAY_PARA7, @ORD_PAY_PARA8, @ORD_PAY_PARA9, @ORD_PAY_PARA10, @ORD_OL_XML, @ORD_OL_OID, @ORD_OL_PROVIDER)";
	public static String TABLE_NAME = "ORD_PAY";
	public static String[] KEY_LIST = { "ORD_PAY_ID" };
	public static String[] FIELD_LIST = { "ORD_PAY_ID", "ORD_ID", "BAS_TAG", "SUP_ID", "ORD_PAY_MONEY", "ORD_PAY_OK",
			"ORD_PAY_DATE", "ORD_PAY_OK_DATE", "ORD_PAY_MEMO", "ADM_ID", "ORD_PAY_PARA1", "ORD_PAY_PARA2",
			"ORD_PAY_PARA3", "ORD_PAY_PARA4", "ORD_PAY_PARA5", "ORD_PAY_PARA6", "ORD_PAY_PARA7", "ORD_PAY_PARA8",
			"ORD_PAY_PARA9", "ORD_PAY_PARA10", "ORD_OL_XML", "ORD_OL_OID", "ORD_OL_PROVIDER" };

	public OrdPayDao() {
		// 设置数据库连接配置名称，在 ewa_conf.xml中定义
		// super.setConfigName("globaltravel");
		super.setInstanceClass(OrdPay.class);
		super.setTableName(TABLE_NAME);
		super.setFields(FIELD_LIST);
		super.setKeyFields(KEY_LIST);
		super.setSqlInsert(SQL_INSERT);
	}

	/**
	 * 生成一条记录
	 * 
	 * @param para 表ORD_PAY的映射类
	 * 
	 * @return 是否成功
	 */

	public boolean newRecord(OrdPay para) {

		RequestValue rv = this.createRequestValue(para);

		int autoKey = super.executeUpdateAutoIncrement(SQL_INSERT, rv);
		if (autoKey > 0) {
			para.setOrdPayId(autoKey);// 自增
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 生成一条记录
	 * 
	 * @param para         表ORD_PAY的映射类
	 * @param updateFields 变化的字段Map
	 * @return
	 */
	public boolean newRecord(OrdPay para, HashMap<String, Boolean> updateFields) {
		String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
		if (sql == null) { // 没有可更新数据
			return false;
		}
		RequestValue rv = this.createRequestValue(para);
		int autoKey = super.executeUpdateAutoIncrement(sql, rv);// 自增
		if (autoKey > 0) {
			para.setOrdPayId(autoKey);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据主键返回一条记录
	 * 
	 * @param paraOrdPayId 支付编号
	 * @return 记录类(OrdPay)
	 */
	public OrdPay getRecord(Integer paraOrdPayId) {
		RequestValue rv = new RequestValue();
		rv.addValue("ORD_PAY_ID", paraOrdPayId, "Integer", 10);
		String sql = super.getSqlSelect();
		ArrayList<OrdPay> al = super.executeQuery(sql, rv, new OrdPay(), FIELD_LIST);
		if (al.size() > 0) {
			OrdPay o = al.get(0);
			al.clear();
			return o;
		} else {
			return null;
		}
	}

	/**
	 * 根据主键删除一条记录
	 * 
	 * @param paraOrdPayId 支付编号
	 * @return 是否成功
	 */
	public boolean deleteRecord(Integer paraOrdPayId) {
		RequestValue rv = new RequestValue();
		rv.addValue("ORD_PAY_ID", paraOrdPayId, "Integer", 10);
		return super.executeUpdate(super.createDeleteSql(), rv);
	}
}