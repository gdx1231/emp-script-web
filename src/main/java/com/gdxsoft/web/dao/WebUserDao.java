package com.gdxsoft.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;

/**
 * 表WEB_USER操作类
 * 
 * @author gdx 时间：Tue Mar 10 2020 19:56:48 GMT+0800 (中国标准时间)
 */
public class WebUserDao extends ClassDaoBase<WebUser> implements IClassDao<WebUser> {

	private static String SQL_SELECT = "SELECT * FROM WEB_USER WHERE USR_ID=@USR_ID";
	private static String SQL_UPDATE = "UPDATE WEB_USER SET 	 USR_TAG = @USR_TAG, 	 ARE_ID = @ARE_ID, 	 USR_LID = @USR_LID, 	 USR_PWD = @USR_PWD, 	 USR_NAME = @USR_NAME, 	 USR_IDCARD = @USR_IDCARD, 	 USR_TELE = @USR_TELE, 	 USR_EMAIL = @USR_EMAIL, 	 USR_QQ = @USR_QQ, 	 USR_MSN = @USR_MSN, 	 USR_ICQ = @USR_ICQ, 	 USR_MOBILE = @USR_MOBILE, 	 USR_ADDR = @USR_ADDR, 	 USR_CDATE = @USR_CDATE, 	 USR_MDATE = @USR_MDATE, 	 USR_LDATE = @USR_LDATE, 	 USR_LIP = @USR_LIP, 	 USR_LNUM = @USR_LNUM, 	 USR_COMPANY = @USR_COMPANY, 	 USR_FROM = @USR_FROM, 	 USR_TITLE = @USR_TITLE, 	 USR_PIC = @USR_PIC, 	 USR_PASSPORT = @USR_PASSPORT, 	 USR_SCM = @USR_SCM, 	 USR_SCM_UID = @USR_SCM_UID, 	 USR_SCM_XML = @USR_SCM_XML, 	 USR_UNID = @USR_UNID, 	 SIT_ID = @SIT_ID, 	 SUP_ID = @SUP_ID, 	 USR_PID = @USR_PID, 	 RECOMMEND_NAME = @RECOMMEND_NAME, 	 RECOMMEND_PHONE = @RECOMMEND_PHONE, 	 RECOMMEND_VIP = @RECOMMEND_VIP, 	 AUTH_WEIXIN_ID = @AUTH_WEIXIN_ID, 	 USR_SEX = @USR_SEX, 	 BBS_SYS_FACE = @BBS_SYS_FACE, 	 BBS_LVL = @BBS_LVL, 	 BBS_HOME_VIS_COUNT = @BBS_HOME_VIS_COUNT, 	 USR_PIC_BIN = @USR_PIC_BIN, 	 USR_NAME_EN = @USR_NAME_EN, 	 USR_PARENT_NAME = @USR_PARENT_NAME, 	 BBS_FACE_METHOD = @BBS_FACE_METHOD, 	 IS_WEIXIN_SUBSCRIBE = @IS_WEIXIN_SUBSCRIBE, 	 BBS_POST_NUM = @BBS_POST_NUM, 	 USR_DBO = @USR_DBO, 	 BBS_REPLY_NUM = @BBS_REPLY_NUM, 	 BBS_SCORE_NUM = @BBS_SCORE_NUM, 	 BBS_NICK_NAME = @BBS_NICK_NAME, 	 AUTH_WEIXIN_JSON = @AUTH_WEIXIN_JSON, 	 WX_GRP = @WX_GRP, 	 WX_ID = @WX_ID, 	 FAVORITE = @FAVORITE, 	 ACTIVE = @ACTIVE, 	 LANGAGE_LEVEL = @LANGAGE_LEVEL, 	 ABOUT = @ABOUT, 	 BUDDY = @BUDDY, 	 _SYNC_ = @_SYNC_, 	 USR_TIMEZONE = @USR_TIMEZONE, 	 USR_MOBILE_COUNTRY_CODE = @USR_MOBILE_COUNTRY_CODE WHERE USR_ID=@USR_ID";
	private static String SQL_DELETE = "DELETE FROM WEB_USER WHERE USR_ID=@USR_ID";
	private static String SQL_INSERT = "INSERT INTO WEB_USER(USR_ID,USR_TAG, ARE_ID, USR_LID, USR_PWD, USR_NAME, USR_IDCARD, USR_TELE, USR_EMAIL, USR_QQ, USR_MSN, USR_ICQ, USR_MOBILE, USR_ADDR, USR_CDATE, USR_MDATE, USR_LDATE, USR_LIP, USR_LNUM, USR_COMPANY, USR_FROM, USR_TITLE, USR_PIC, USR_PASSPORT, USR_SCM, USR_SCM_UID, USR_SCM_XML, USR_UNID, SIT_ID, SUP_ID, USR_PID, RECOMMEND_NAME, RECOMMEND_PHONE, RECOMMEND_VIP, AUTH_WEIXIN_ID, USR_SEX, BBS_SYS_FACE, BBS_LVL, BBS_HOME_VIS_COUNT, USR_PIC_BIN, USR_NAME_EN, USR_PARENT_NAME, BBS_FACE_METHOD, IS_WEIXIN_SUBSCRIBE, BBS_POST_NUM, USR_DBO, BBS_REPLY_NUM, BBS_SCORE_NUM, BBS_NICK_NAME, AUTH_WEIXIN_JSON, WX_GRP, WX_ID, FAVORITE, ACTIVE, LANGAGE_LEVEL, ABOUT, BUDDY, _SYNC_, USR_TIMEZONE, USR_MOBILE_COUNTRY_CODE) 	VALUES(@USR_TAG, @ARE_ID, @USR_LID, @USR_PWD, @USR_NAME, @USR_IDCARD, @USR_TELE, @USR_EMAIL, @USR_QQ, @USR_MSN, @USR_ICQ, @USR_MOBILE, @USR_ADDR, @USR_CDATE, @USR_MDATE, @USR_LDATE, @USR_LIP, @USR_LNUM, @USR_COMPANY, @USR_FROM, @USR_TITLE, @USR_PIC, @USR_PASSPORT, @USR_SCM, @USR_SCM_UID, @USR_SCM_XML, @USR_UNID, @SIT_ID, @SUP_ID, @USR_PID, @RECOMMEND_NAME, @RECOMMEND_PHONE, @RECOMMEND_VIP, @AUTH_WEIXIN_ID, @USR_SEX, @BBS_SYS_FACE, @BBS_LVL, @BBS_HOME_VIS_COUNT, @USR_PIC_BIN, @USR_NAME_EN, @USR_PARENT_NAME, @BBS_FACE_METHOD, @IS_WEIXIN_SUBSCRIBE, @BBS_POST_NUM, @USR_DBO, @BBS_REPLY_NUM, @BBS_SCORE_NUM, @BBS_NICK_NAME, @AUTH_WEIXIN_JSON, @WX_GRP, @WX_ID, @FAVORITE, @ACTIVE, @LANGAGE_LEVEL, @ABOUT, @BUDDY, @_SYNC_, @USR_TIMEZONE, @USR_MOBILE_COUNTRY_CODE)";
	public static String TABLE_NAME = "WEB_USER";
	public static String[] KEY_LIST = { "USR_ID" };
	public static String[] FIELD_LIST = { "USR_ID", "USR_TAG", "ARE_ID", "USR_LID", "USR_PWD", "USR_NAME", "USR_IDCARD",
			"USR_TELE", "USR_EMAIL", "USR_QQ", "USR_MSN", "USR_ICQ", "USR_MOBILE", "USR_ADDR", "USR_CDATE", "USR_MDATE",
			"USR_LDATE", "USR_LIP", "USR_LNUM", "USR_COMPANY", "USR_FROM", "USR_TITLE", "USR_PIC", "USR_PASSPORT",
			"USR_SCM", "USR_SCM_UID", "USR_SCM_XML", "USR_UNID", "SIT_ID", "SUP_ID", "USR_PID", "RECOMMEND_NAME",
			"RECOMMEND_PHONE", "RECOMMEND_VIP", "AUTH_WEIXIN_ID", "USR_SEX", "BBS_SYS_FACE", "BBS_LVL",
			"BBS_HOME_VIS_COUNT", "USR_PIC_BIN", "USR_NAME_EN", "USR_PARENT_NAME", "BBS_FACE_METHOD",
			"IS_WEIXIN_SUBSCRIBE", "BBS_POST_NUM", "USR_DBO", "BBS_REPLY_NUM", "BBS_SCORE_NUM", "BBS_NICK_NAME",
			"AUTH_WEIXIN_JSON", "WX_GRP", "WX_ID", "FAVORITE", "ACTIVE", "LANGAGE_LEVEL", "ABOUT", "BUDDY", "_SYNC_",
			"USR_TIMEZONE", "USR_MOBILE_COUNTRY_CODE" };

	public WebUserDao() {
		// 设置数据库连接配置名称，在 ewa_conf.xml中定义
		// super.setConfigName("pf");
	}

	/**
	 * 生成一条记录
	 * 
	 * @param para 表WEB_USER的映射类
	 * 
	 * @return 是否成功
	 */

	public boolean newRecord(WebUser para) {

		RequestValue rv = this.createRequestValue(para);

		super.executeUpdate(SQL_INSERT, rv);

		return true;

	}

	/**
	 * 生成一条记录
	 * 
	 * @param para         表WEB_USER的映射类
	 * @param updateFields 变化的字段Map
	 * @return
	 */
	public boolean newRecord(WebUser para, HashMap<String, Boolean> updateFields) {
		String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
		if (sql == null) { // 没有可更新数据
			return false;
		}
		RequestValue rv = this.createRequestValue(para);
		super.executeUpdate(sql, rv);

		return true;
	}

	/**
	 * 更新一条记录
	 * 
	 * @param para 表WEB_USER的映射类
	 * @return 是否成功
	 */
	public boolean updateRecord(WebUser para) {

		RequestValue rv = this.createRequestValue(para);
		return super.executeUpdate(SQL_UPDATE, rv);
	}

	/**
	 * 更新一条记录
	 * 
	 * @param para         表WEB_USER的映射类
	 * @param updateFields 变化的字段Map
	 * @return
	 */
	public boolean updateRecord(WebUser para, HashMap<String, Boolean> updateFields) {
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
		return "SELECT * FROM WEB_USER where 1=1";
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
	 * @param paraUsrId 编号
	 * @return 记录类(WebUser)
	 */
	public WebUser getRecord(Integer paraUsrId) {
		RequestValue rv = new RequestValue();
		rv.addValue("USR_ID", paraUsrId, "Integer", 10);
		ArrayList<WebUser> al = super.executeQuery(SQL_SELECT, rv, new WebUser(), FIELD_LIST);
		if (al.size() > 0) {
			WebUser o = al.get(0);
			al.clear();
			return o;
		} else {
			return null;
		}
	}

	public WebUser getRecord(Long paraUsrId) {
		RequestValue rv = new RequestValue();
		rv.addValue("USR_ID", paraUsrId, "Long", 10);
		ArrayList<WebUser> al = super.executeQuery(SQL_SELECT, rv, new WebUser(), FIELD_LIST);
		if (al.size() > 0) {
			WebUser o = al.get(0);
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
	public ArrayList<WebUser> getRecords(String whereString) {
		String sql = "SELECT * FROM WEB_USER WHERE " + whereString;
		return super.executeQuery(sql, new WebUser(), FIELD_LIST);
	}

	/**
	 * 根据查询条件返回多条记录（限制为500条）
	 * 
	 * @param whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	 * @param fields      指定返回的字段
	 * @return 记录集合
	 */
	public ArrayList<WebUser> getRecords(String whereString, List<String> fields) {
		String sql = super.createSelectSql(TABLE_NAME, whereString, fields);
		String[] arrFields = new String[fields.size()];
		arrFields = fields.toArray(arrFields);
		return super.executeQuery(sql, new WebUser(), arrFields);
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
	public ArrayList<WebUser> getRecords(String whereString, String pkFieldName, int pageSize, int currentPage) {
		String sql = "SELECT * FROM WEB_USER WHERE " + whereString;
		return super.executeQuery(sql, new WebUser(), FIELD_LIST, pkFieldName, pageSize, currentPage);
	}

	/**
	 * 根据主键删除一条记录
	 * 
	 * @param paraUsrId 编号
	 * @return 是否成功
	 */
	public boolean deleteRecord(Integer paraUsrId) {
		RequestValue rv = new RequestValue();
		rv.addValue("USR_ID", paraUsrId, "Integer", 10);
		return super.executeUpdate(SQL_DELETE, rv);
	}

	public RequestValue createRequestValue(WebUser para) {
		RequestValue rv = new RequestValue();
		rv.addValue("USR_ID", para.getUsrId(), "Long", 110); // 编号
		rv.addValue("USR_TAG", para.getUsrTag(), "String", 3); // USR_TAG
		rv.addValue("ARE_ID", para.getAreId(), "String", 21); // 地区编号
		rv.addValue("USR_LID", para.getUsrLid(), "String", 150); // 登录名
		rv.addValue("USR_PWD", para.getUsrPwd(), "String", 50); // 密码
		rv.addValue("USR_NAME", para.getUsrName(), "String", 50); // 姓名
		rv.addValue("USR_IDCARD", para.getUsrIdcard(), "String", 18); // 身份证
		rv.addValue("USR_TELE", para.getUsrTele(), "String", 100); // 电话
		rv.addValue("USR_EMAIL", para.getUsrEmail(), "String", 100); // 邮件
		rv.addValue("USR_QQ", para.getUsrQq(), "String", 100); // QQ
		rv.addValue("USR_MSN", para.getUsrMsn(), "String", 100); // MSN
		rv.addValue("USR_ICQ", para.getUsrIcq(), "String", 100); // ICQ
		rv.addValue("USR_MOBILE", para.getUsrMobile(), "String", 100); // 移动电话
		rv.addValue("USR_ADDR", para.getUsrAddr(), "String", 100); // 住址
		rv.addValue("USR_CDATE", para.getUsrCdate(), "Date", 23); // 创建日期
		rv.addValue("USR_MDATE", para.getUsrMdate(), "Date", 23); // 修改日期
		rv.addValue("USR_LDATE", para.getUsrLdate(), "Date", 23); // 登录日期
		rv.addValue("USR_LIP", para.getUsrLip(), "String", 144); // 登录IP
		rv.addValue("USR_LNUM", para.getUsrLnum(), "Integer", 10); // 登录次数
		rv.addValue("USR_COMPANY", para.getUsrCompany(), "String", 200); // 单位
		rv.addValue("USR_FROM", para.getUsrFrom(), "String", 300); // 来源
		rv.addValue("USR_TITLE", para.getUsrTitle(), "String", 20); // 称呼
		rv.addValue("USR_PIC", para.getUsrPic(), "String", 500); // 照片
		rv.addValue("USR_PASSPORT", para.getUsrPassport(), "String", 30); // 护照
		rv.addValue("USR_SCM", para.getUsrScm(), "String", 20); // 合作伙伴
		rv.addValue("USR_SCM_UID", para.getUsrScmUid(), "String", 50); // 合作伙伴用户编号
		rv.addValue("USR_SCM_XML", para.getUsrScmXml(), "String", 1000); // 供应商用户XML描述
		rv.addValue("USR_UNID", para.getUsrUnid(), "String", 36); // USR_UNID
		rv.addValue("SIT_ID", para.getSitId(), "Integer", 10); // SIT_ID
		rv.addValue("SUP_ID", para.getSupId(), "Integer", 10); // SUP_ID
		rv.addValue("USR_PID", para.getUsrPid(), "Integer", 10); // USR_PID
		rv.addValue("RECOMMEND_NAME", para.getRecommendName(), "String", 50); // 推荐人
		rv.addValue("RECOMMEND_PHONE", para.getRecommendPhone(), "String", 50); // 推荐人电话
		rv.addValue("RECOMMEND_VIP", para.getRecommendVip(), "String", 50); // 推荐人卡号
		rv.addValue("AUTH_WEIXIN_ID", para.getAuthWeixinId(), "String", 100); // AUTH_WEIXIN_ID
		rv.addValue("USR_SEX", para.getUsrSex(), "String", 20); // USR_SEX
		rv.addValue("BBS_SYS_FACE", para.getBbsSysFace(), "String", 200); // BBS_SYS_FACE
		rv.addValue("BBS_LVL", para.getBbsLvl(), "Integer", 10); // BBS_LVL
		rv.addValue("BBS_HOME_VIS_COUNT", para.getBbsHomeVisCount(), "Integer", 10); // BBS_HOME_VIS_COUNT
		rv.addValue("USR_PIC_BIN", para.getUsrPicBin(), "byte[]", 2147483647); // USR_PIC_BIN
		rv.addValue("USR_NAME_EN", para.getUsrNameEn(), "String", 200); // USR_NAME_EN
		rv.addValue("USR_PARENT_NAME", para.getUsrParentName(), "String", 20); // USR_PARENT_NAME
		rv.addValue("BBS_FACE_METHOD", para.getBbsFaceMethod(), "String", 1); // BBS_FACE_METHOD
		rv.addValue("IS_WEIXIN_SUBSCRIBE", para.getIsWeixinSubscribe(), "Integer", 10); // IS_WEIXIN_SUBSCRIBE
		rv.addValue("BBS_POST_NUM", para.getBbsPostNum(), "Integer", 10); // BBS_POST_NUM
		rv.addValue("USR_DBO", para.getUsrDbo(), "Date", 23); // USR_DBO
		rv.addValue("BBS_REPLY_NUM", para.getBbsReplyNum(), "Integer", 10); // BBS_REPLY_NUM
		rv.addValue("BBS_SCORE_NUM", para.getBbsScoreNum(), "Integer", 10); // BBS_SCORE_NUM
		rv.addValue("BBS_NICK_NAME", para.getBbsNickName(), "String", 10); // BBS_NICK_NAME
		rv.addValue("AUTH_WEIXIN_JSON", para.getAuthWeixinJson(), "String", 2147483647); // AUTH_WEIXIN_JSON
		rv.addValue("WX_GRP", para.getWxGrp(), "Integer", 10); // WX_GRP
		rv.addValue("WX_ID", para.getWxId(), "String", 200); // WX_ID
		rv.addValue("FAVORITE", para.getFavorite(), "String", 200); // FAVORITE
		rv.addValue("ACTIVE", para.getActive(), "String", 200); // ACTIVE
		rv.addValue("LANGAGE_LEVEL", para.getLangageLevel(), "String", 200); // LANGAGE_LEVEL
		rv.addValue("ABOUT", para.getAbout(), "String", 1000); // ABOUT
		rv.addValue("BUDDY", para.getBuddy(), "String", 200); // BUDDY
		rv.addValue("_SYNC_", para.getSync(), "String", 50); // _SYNC_
		rv.addValue("USR_TIMEZONE", para.getUsrTimezone(), "String", 20); // USR_TIMEZONE
		rv.addValue("USR_MOBILE_COUNTRY_CODE", para.getUsrMobileCountryCode(), "String", 5); // USR_MOBILE_COUNTRY_CODE
		return rv;
	}
}