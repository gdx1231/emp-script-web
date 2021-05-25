package com.gdxsoft.web.dao;

import com.gdxsoft.easyweb.datasource.ClassDaoBase;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.script.RequestValue;

import java.util.ArrayList;

/**
 * 表SRV_CAT_LST操作类
 * 
 * @author guolei 时间：Thu Aug 11 17:56:43 UTC+0800 2011
 */
public class SrvCatLstDao extends ClassDaoBase<SrvCatLst> implements IClassDao<SrvCatLst> {
	private static String SQL_SELECT = "SELECT * FROM SRV_CAT_LST WHERE SRV_CAT_LST_ID=@SRV_CAT_LST_ID";
	private static String SQL_UPDATE = "UPDATE SRV_CAT_LST SET 	 SRV_CAT_ID = @SRV_CAT_ID, 	 BAS_TAG = @BAS_TAG, 	 SRV_TYP_TAG = @SRV_TYP_TAG, 	 SRV_CAT_LST_NAME = @SRV_CAT_LST_NAME, 	 SRV_CAT_LST_CDATE = @SRV_CAT_LST_CDATE, 	 SRV_CAT_LST_MDATE = @SRV_CAT_LST_MDATE, 	 SRV_CAT_LST_MEMO = @SRV_CAT_LST_MEMO, 	 SRV_CAT_LST_ORD = @SRV_CAT_LST_ORD WHERE SRV_CAT_LST_ID=@SRV_CAT_LST_ID";
	private static String SQL_DELETE = "DELETE FROM SRV_CAT_LST WHERE SRV_CAT_LST_ID=@SRV_CAT_LST_ID";
	private static String SQL_INSERT = "INSERT INTO SRV_CAT_LST(SRV_CAT_ID, SRV_CAT_LST_ID, BAS_TAG, SRV_TYP_TAG, SRV_CAT_LST_NAME, SRV_CAT_LST_CDATE, SRV_CAT_LST_MDATE, SRV_CAT_LST_MEMO, SRV_CAT_LST_ORD) 	VALUES(@SRV_CAT_ID, @SRV_CAT_LST_ID, @BAS_TAG, @SRV_TYP_TAG, @SRV_CAT_LST_NAME, @SRV_CAT_LST_CDATE, @SRV_CAT_LST_MDATE, @SRV_CAT_LST_MEMO, @SRV_CAT_LST_ORD)";
	public static String[] FIELD_LIST = { "SRV_CAT_ID", "SRV_CAT_LST_ID", "BAS_TAG", "SRV_TYP_TAG", "SRV_CAT_LST_NAME",
			"SRV_CAT_LST_CDATE", "SRV_CAT_LST_MDATE", "SRV_CAT_LST_MEMO", "SRV_CAT_LST_ORD" };

	public SrvCatLstDao() {
		// 设置数据库连接配置名称（globaltravel)，在EwaConnection.xml中定义
		super.setConfigName("globaltravel");
		super.setFields(FIELD_LIST);
		super.setInstanceClass(SrvCatLst.class);
	}

	/**
	 * 生成一条记录
	 * 
	 * @param para 表SRV_CAT_LST的映射类
	 * @return 是否成功
	 */
	public boolean newRecord(SrvCatLst para) {
		RequestValue rv = this.createRequestValue(para);
		return super.executeUpdate(SQL_INSERT, rv);
	}

	/**
	 * 更新一条记录
	 * 
	 * @param para 表SRV_CAT_LST的映射类
	 * @return 是否成功
	 */
	public boolean updateRecord(SrvCatLst para) {
		RequestValue rv = this.createRequestValue(para);
		return super.executeUpdate(SQL_UPDATE, rv);
	}

	public String getSqlDelete() {
		return SQL_DELETE;
	}

	public String[] getSqlFields() {
		return FIELD_LIST;
	}

	public String getSqlSelect() {
		return "SELECT * FROM SRV_CAT_LST where 1=1";
	}

	public String getSqlUpdate() {
		return SQL_UPDATE;
	}

	public String getSqlInsert() {
		return SQL_INSERT;
	}

	/**
	 * 根据主键返回一条记录
	 * 
	 * @param paraSrvCatLstId 目录明细编号
	 * @return 记录类(SrvCatLst)
	 */
	public SrvCatLst getRecord(Integer paraSrvCatLstId) {
		RequestValue rv = new RequestValue();
		rv.addValue("SRV_CAT_LST_ID", paraSrvCatLstId, "Integer", 10);
		ArrayList<SrvCatLst> al = super.executeQuery(SQL_SELECT, rv, new SrvCatLst(), FIELD_LIST);
		if (al.size() > 0) {
			SrvCatLst o = al.get(0);
			al.clear();
			return o;
		} else {
			return null;
		}
	}

	/**
	 * 根据查询条件返回多条记录（限制为500条）
	 * 
	 * @param whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	 * @return 记录集合
	 */
	public ArrayList<SrvCatLst> getRecords(String whereString) {
		String sql = "SELECT * FROM SRV_CAT_LST WHERE " + whereString;
		return super.executeQuery(sql, new SrvCatLst(), FIELD_LIST);
	}

	/**
	 * 根据查询条件返回多条记录（限制为500条）
	 * 
	 * @param whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	 * @param pkFieldName 主键
	 * @param pageSize    每页记录数
	 * @param currentPage 当前页
	 * @return 记录集合
	 */
	public ArrayList<SrvCatLst> getRecords(String whereString, String pkFieldName, int pageSize, int currentPage) {
		String sql = "SELECT * FROM SRV_CAT_LST WHERE " + whereString;
		return super.executeQuery(sql, new SrvCatLst(), FIELD_LIST, pkFieldName, pageSize, currentPage);
	}

}
