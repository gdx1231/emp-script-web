package com.gdxsoft.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;

/**
 * 表WEB_USER_FPWD操作类
 * 
 * @author gdx 时间：Fri Jun 29 2018 11:40:33 GMT+0800 (中国标准时间)
 */
public class WebUserFpwdDao extends ClassDaoBase<WebUserFpwd> implements IClassDao<WebUserFpwd> {

	private static String SQL_SELECT = "SELECT * FROM WEB_USER_FPWD WHERE FP_UNID=@FP_UNID";
	private static String SQL_UPDATE = "UPDATE WEB_USER_FPWD SET    USR_ID = @USR_ID,    FP_CDATE = @FP_CDATE,    FP_EDATE = @FP_EDATE,    FP_UDATE = @FP_UDATE,    FP_VALIDCODE = @FP_VALIDCODE,    FP_TYPE = @FP_TYPE,    FP_INC = @FP_INC,    FP_RESULT = @FP_RESULT,    FP_LOG = @FP_LOG,    FP_IP = @FP_IP,    FP_AGENT = @FP_AGENT WHERE FP_UNID=@FP_UNID";
	private static String SQL_DELETE = "DELETE FROM WEB_USER_FPWD WHERE FP_UNID=@FP_UNID";
	private static String SQL_INSERT = "INSERT INTO WEB_USER_FPWD(FP_UNID, USR_ID, FP_CDATE, FP_EDATE, FP_UDATE, FP_VALIDCODE, FP_TYPE, FP_INC, FP_RESULT, FP_LOG, FP_IP, FP_AGENT)   VALUES(@FP_UNID, @USR_ID, @FP_CDATE, @FP_EDATE, @FP_UDATE, @FP_VALIDCODE, @FP_TYPE, @FP_INC, @FP_RESULT, @FP_LOG, @FP_IP, @FP_AGENT)";
	public static String TABLE_NAME = "WEB_USER_FPWD";
	public static String[] KEY_LIST = { "FP_UNID" };
	public static String[] FIELD_LIST = { "FP_UNID", "USR_ID", "FP_CDATE", "FP_EDATE", "FP_UDATE", "FP_VALIDCODE",
			"FP_TYPE", "FP_INC", "FP_RESULT", "FP_LOG", "FP_IP", "FP_AGENT" };

	public WebUserFpwdDao() {
		// 设置数据库连接配置名称，在 ewa_conf.xml中定义
		super.setConfigName("globaltravel");
	}

	/**
	 * 生成一条记录
	 * 
	 * @param para
	 *            表WEB_USER_FPWD的映射类
	 * 
	 * @return 是否成功
	 */

	public boolean newRecord(WebUserFpwd para) {

		RequestValue rv = this.createRequestValue(para);

		return super.executeUpdate(SQL_INSERT, rv);

	}

	/**
	 * 生成一条记录
	 * 
	 * @param para
	 *            表WEB_USER_FPWD的映射类
	 * @param updateFields
	 *            变化的字段Map
	 * @return
	 */
	public boolean newRecord(WebUserFpwd para, HashMap<String, Boolean> updateFields) {
		String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
		if (sql == null) { // 没有可更新数据
			return false;
		}
		RequestValue rv = this.createRequestValue(para);
		return super.executeUpdate(sql, rv);
	}

	/**
	 * 更新一条记录
	 * 
	 * @param para
	 *            表WEB_USER_FPWD的映射类
	 * @return 是否成功
	 */
	public boolean updateRecord(WebUserFpwd para) {

		RequestValue rv = this.createRequestValue(para);
		return super.executeUpdate(SQL_UPDATE, rv);
	}

	/**
	 * 更新一条记录
	 * 
	 * @param para
	 *            表WEB_USER_FPWD的映射类
	 * @param updateFields
	 *            变化的字段Map
	 * @return
	 */
	public boolean updateRecord(WebUserFpwd para, HashMap<String, Boolean> updateFields) {
		// 没定义主键的话不能更新
		if (KEY_LIST.length == 0) {
			return false;
		}
		String sql = super.sqlUpdateChanged(TABLE_NAME, KEY_LIST, updateFields);
		if (sql == null) { // 没有可更新数据
			return false;
		}
		RequestValue rv = this.createRequestValue(para);
		return super.executeUpdate(sql, rv);
	}

	public String getSqlDelete() {
		return SQL_DELETE;
	}

	public String[] getSqlFields() {
		return FIELD_LIST;
	}

	public String getSqlSelect() {
		return "SELECT * FROM WEB_USER_FPWD where 1=1";
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
	 * @param paraFpUnid
	 *            FP_UNID
	 * @return 记录类(WebUserFpwd)
	 */
	public WebUserFpwd getRecord(String paraFpUnid) {
		RequestValue rv = new RequestValue();
		rv.addValue("FP_UNID", paraFpUnid, "String", 36);
		ArrayList<WebUserFpwd> al = super.executeQuery(SQL_SELECT, rv, new WebUserFpwd(), FIELD_LIST);
		if (al.size() > 0) {
			WebUserFpwd o = al.get(0);
			al.clear();
			return o;
		} else {
			return null;
		}
	}

	/**
	 * 根据查询条件返回多条记录（限制为500条）
	 * 
	 * @param whereString
	 *            查询条件，注意过滤“'”符号，避免SQL注入攻击
	 * @return 记录集合
	 */
	public ArrayList<WebUserFpwd> getRecords(String whereString) {
		String sql = "SELECT * FROM WEB_USER_FPWD WHERE " + whereString;
		return super.executeQuery(sql, new WebUserFpwd(), FIELD_LIST);
	}

	/**
	 * 根据查询条件返回多条记录（限制为500条）
	 * 
	 * @param whereString
	 *            查询条件，注意过滤“'”符号，避免SQL注入攻击
	 * @param fields
	 *            指定返回的字段
	 * @return 记录集合
	 */
	public ArrayList<WebUserFpwd> getRecords(String whereString, List<String> fields) {
		String sql = super.createSelectSql(TABLE_NAME, whereString, fields);
		String[] arrFields = new String[fields.size()];
		arrFields = fields.toArray(arrFields);
		return super.executeQuery(sql, new WebUserFpwd(), arrFields);
	}

	/**
	 * 根据查询条件返回多条记录（限制为500条）
	 * 
	 * @param whereString
	 *            查询条件，注意过滤“'”符号，避免SQL注入攻击
	 * @param pkFieldName
	 *            主键
	 * @param pageSize
	 *            每页记录数
	 * @param currentPage
	 *            当前页
	 * @return 记录集合
	 */
	public ArrayList<WebUserFpwd> getRecords(String whereString, String pkFieldName, int pageSize, int currentPage) {
		String sql = "SELECT * FROM WEB_USER_FPWD WHERE " + whereString;
		return super.executeQuery(sql, new WebUserFpwd(), FIELD_LIST, pkFieldName, pageSize, currentPage);
	}

	/**
	 * 根据主键删除一条记录
	 * 
	 * @param paraFpUnid
	 *            FP_UNID
	 * @return 是否成功
	 */
	public boolean deleteRecord(String paraFpUnid) {
		RequestValue rv = new RequestValue();
		rv.addValue("FP_UNID", paraFpUnid, "String", 36);
		return super.executeUpdate(SQL_DELETE, rv);
	}

	public RequestValue createRequestValue(WebUserFpwd para) {
		RequestValue rv = new RequestValue();
		rv.addValue("FP_UNID", para.getFpUnid(), "String", 36); // FP_UNID
		rv.addValue("USR_ID", para.getUsrId(), "Integer", 10); // USR_ID
		rv.addValue("FP_CDATE", para.getFpCdate(), "Date", 23); // 提交时间
		rv.addValue("FP_EDATE", para.getFpEdate(), "Date", 23); // 过期时间
		rv.addValue("FP_UDATE", para.getFpUdate(), "Date", 23); // 激活时间
		rv.addValue("FP_VALIDCODE", para.getFpValidcode(), "String", 20); // FP_VALIDCODE
		rv.addValue("FP_TYPE", para.getFpType(), "String", 20); // FP_TYPE
		rv.addValue("FP_INC", para.getFpInc(), "Integer", 10); // FP_INC
		rv.addValue("FP_RESULT", para.getFpResult(), "String", 1); // FP_RESULT
		rv.addValue("FP_LOG", para.getFpLog(), "String", 1000); // FP_LOG
		rv.addValue("FP_IP", para.getFpIp(), "String", 50); // FP_IP
		rv.addValue("FP_AGENT", para.getFpAgent(), "String", 200); // FP_AGENT
		return rv;
	}
}
