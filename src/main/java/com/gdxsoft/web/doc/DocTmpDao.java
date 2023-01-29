package com.gdxsoft.web.doc;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;

/**
 * 表DOC_TMP操作类
 * 
 * @author gdx 时间：Wed Aug 21 2019 10:11:01 GMT+0800 (中国标准时间)
 */
public class DocTmpDao extends ClassDaoBase<DocTmp> implements IClassDao<DocTmp> {

	private static String SQL_SELECT = "SELECT * FROM DOC_TMP WHERE DOC_TMP_UNID=@DOC_TMP_UNID AND SUP_ID=@SUP_ID";
	private static String SQL_UPDATE = "UPDATE DOC_TMP SET 	 DOC_TMP_ID = @DOC_TMP_ID, 	 DOC_CAT_ID = @DOC_CAT_ID, 	 DOC_CAT_UNID = @DOC_CAT_UNID, 	 DOC_TMP_NAME = @DOC_TMP_NAME, 	 DOC_TMP_CNT = @DOC_TMP_CNT, 	 DOC_TMP_SQL = @DOC_TMP_SQL, 	 DOC_TMP_CDATE = @DOC_TMP_CDATE, 	 DOC_TMP_MDATE = @DOC_TMP_MDATE, 	 DOC_TMP_ORD = @DOC_TMP_ORD, 	 DOC_TMP_TAG = @DOC_TMP_TAG, 	 DOC_TMP_TYPE = @DOC_TMP_TYPE, 	 DOC_TMP_GROUPBY = @DOC_TMP_GROUPBY, 	 DOC_TMP_ORDERBY = @DOC_TMP_ORDERBY, 	 DOC_TMP_JS = @DOC_TMP_JS WHERE DOC_TMP_UNID=@DOC_TMP_UNID AND SUP_ID=@SUP_ID";
	private static String SQL_DELETE = "DELETE FROM DOC_TMP WHERE DOC_TMP_UNID=@DOC_TMP_UNID AND SUP_ID=@SUP_ID";
	private static String SQL_INSERT = "INSERT INTO DOC_TMP(DOC_TMP_UNID, DOC_TMP_ID, DOC_CAT_ID, DOC_CAT_UNID, DOC_TMP_NAME, DOC_TMP_CNT, DOC_TMP_SQL, DOC_TMP_CDATE, DOC_TMP_MDATE, SUP_ID, DOC_TMP_ORD, DOC_TMP_TAG, DOC_TMP_TYPE, DOC_TMP_GROUPBY, DOC_TMP_ORDERBY, DOC_TMP_JS) 	VALUES(@DOC_TMP_UNID, @DOC_TMP_ID, @DOC_CAT_ID, @DOC_CAT_UNID, @DOC_TMP_NAME, @DOC_TMP_CNT, @DOC_TMP_SQL, @DOC_TMP_CDATE, @DOC_TMP_MDATE, @SUP_ID, @DOC_TMP_ORD, @DOC_TMP_TAG, @DOC_TMP_TYPE, @DOC_TMP_GROUPBY, @DOC_TMP_ORDERBY, @DOC_TMP_JS)";
	public static String TABLE_NAME = "DOC_TMP";
	public static String[] KEY_LIST = { "DOC_TMP_UNID", "SUP_ID" };
	public static String[] FIELD_LIST = { "DOC_TMP_UNID", "DOC_TMP_ID", "DOC_CAT_ID", "DOC_CAT_UNID", "DOC_TMP_NAME",
			"DOC_TMP_CNT", "DOC_TMP_SQL", "DOC_TMP_CDATE", "DOC_TMP_MDATE", "SUP_ID", "DOC_TMP_ORD", "DOC_TMP_TAG",
			"DOC_TMP_TYPE", "DOC_TMP_GROUPBY", "DOC_TMP_ORDERBY", "DOC_TMP_JS" };

	public DocTmpDao() {
		// 设置数据库连接配置名称，在 ewa_conf.xml中定义
		// super.setConfigName("globaltravel");
	}

	/**
	 * 生成一条记录
	 * 
	 * @param para 表DOC_TMP的映射类
	 * 
	 * @return 是否成功
	 */

	public boolean newRecord(DocTmp para) {

		RequestValue rv = this.createRequestValue(para);

		return super.executeUpdate(SQL_INSERT, rv);

	}

	/**
	 * 生成一条记录
	 * 
	 * @param para         表DOC_TMP的映射类
	 * @param updateFields 变化的字段Map
	 * @return
	 */
	public boolean newRecord(DocTmp para, HashMap<String, Boolean> updateFields) {
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
	 * @param para 表DOC_TMP的映射类
	 * @return 是否成功
	 */
	public boolean updateRecord(DocTmp para) {

		RequestValue rv = this.createRequestValue(para);
		return super.executeUpdate(SQL_UPDATE, rv);
	}

	/**
	 * 更新一条记录
	 * 
	 * @param para         表DOC_TMP的映射类
	 * @param updateFields 变化的字段Map
	 * @return
	 */
	public boolean updateRecord(DocTmp para, HashMap<String, Boolean> updateFields) {
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
		return "SELECT * FROM DOC_TMP where 1=1";
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
	 * @param paraSupId      SUP_ID
	 * @param paraDocTmpUnid DOC_TMP_UNID
	 * 
	 * @return 记录类(DocTmp)
	 */
	public DocTmp getRecord(Integer paraSupId, String paraDocTmpUnid) {
		RequestValue rv = new RequestValue();
		rv.addValue("DOC_TMP_UNID", paraDocTmpUnid, "String", 36);
		rv.addValue("SUP_ID", paraSupId, "Integer", 10);
		ArrayList<DocTmp> al = super.executeQuery(SQL_SELECT, rv, new DocTmp(), FIELD_LIST);
		if (al.size() > 0) {
			DocTmp o = al.get(0);
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
	public ArrayList<DocTmp> getRecords(String whereString) {
		String sql = "SELECT * FROM DOC_TMP WHERE " + whereString;
		return super.executeQuery(sql, new DocTmp(), FIELD_LIST);
	}

	/**
	 * 根据查询条件返回多条记录（限制为500条）
	 * 
	 * @param whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	 * @param fields      指定返回的字段
	 * @return 记录集合
	 */
	public ArrayList<DocTmp> getRecords(String whereString, List<String> fields) {
		String sql = super.createSelectSql(TABLE_NAME, whereString, fields);
		String[] arrFields = new String[fields.size()];
		arrFields = fields.toArray(arrFields);
		return super.executeQuery(sql, new DocTmp(), arrFields);
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
	public ArrayList<DocTmp> getRecords(String whereString, String pkFieldName, int pageSize, int currentPage) {
		String sql = "SELECT * FROM DOC_TMP WHERE " + whereString;
		return super.executeQuery(sql, new DocTmp(), FIELD_LIST, pkFieldName, pageSize, currentPage);
	}

	/**
	 * 根据主键删除一条记录
	 * 
	 * @param paraDocTmpUnid DOC_TMP_UNID
	 * @param paraSupId      SUP_ID
	 * @return 是否成功
	 */
	public boolean deleteRecord(String paraDocTmpUnid, Integer paraSupId) {
		RequestValue rv = new RequestValue();
		rv.addValue("DOC_TMP_UNID", paraDocTmpUnid, "String", 36);
		rv.addValue("SUP_ID", paraSupId, "Integer", 10);
		return super.executeUpdate(SQL_DELETE, rv);
	}

	public RequestValue createRequestValue(DocTmp para) {
		RequestValue rv = new RequestValue();
		rv.addValue("DOC_TMP_UNID", para.getDocTmpUnid(), "String", 36); // DOC_TMP_UNID
		rv.addValue("DOC_TMP_ID", para.getDocTmpId(), "Integer", 10); // 模板编号
		rv.addValue("DOC_CAT_ID", para.getDocCatId(), "Integer", 10); // 目录编号
		rv.addValue("DOC_CAT_UNID", para.getDocCatUnid(), "String", 36); // DOC_CAT_UNID
		rv.addValue("DOC_TMP_NAME", para.getDocTmpName(), "String", 250); // 模板名称
		rv.addValue("DOC_TMP_CNT", para.getDocTmpCnt(), "String", 1073741823); // 内容
		rv.addValue("DOC_TMP_SQL", para.getDocTmpSql(), "String", 1073741823); // 脚本
		rv.addValue("DOC_TMP_CDATE", para.getDocTmpCdate(), "Date", 23); // 创建日期
		rv.addValue("DOC_TMP_MDATE", para.getDocTmpMdate(), "Date", 23); // 修改日期
		rv.addValue("SUP_ID", para.getSupId(), "Integer", 10); // SUP_ID
		rv.addValue("DOC_TMP_ORD", para.getDocTmpOrd(), "Integer", 10); // 排序
		rv.addValue("DOC_TMP_TAG", para.getDocTmpTag(), "String", 20); // 标记
		rv.addValue("DOC_TMP_TYPE", para.getDocTmpType(), "String", 20); // DOC_TMP_TYPE
		rv.addValue("DOC_TMP_GROUPBY", para.getDocTmpGroupby(), "String", 50); // DOC_TMP_GROUPBY
		rv.addValue("DOC_TMP_ORDERBY", para.getDocTmpOrderby(), "String", 50); // DOC_TMP_ORDERBY
		rv.addValue("DOC_TMP_JS", para.getDocTmpJs(), "String", 2147483647); // DOC_TMP_JS
		return rv;
	}
}