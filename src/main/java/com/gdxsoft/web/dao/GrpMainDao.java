package com.gdxsoft.web.dao;

import java.util.*;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;

/**
 * 表GRP_MAIN操作类
 * 
 * @author gdx 时间：Thu Jul 14 2022 17:51:14 GMT+0800 (中国标准时间)
 */
public class GrpMainDao extends ClassDaoBase<GrpMain> implements IClassDao<GrpMain> {

	private static String SQL_INSERT = "INSERT INTO GRP_MAIN(GRP_NAME, GRP_CODE, GRP_WAIT_DAYS, GRP_PRC_SIN, GRP_NUM, GRP_MEMO, GRP_NIGHT, GRP_CDATE, GRP_MDATE, GRP_SDATE, GRP_STOP, GRP_STA, ADM_ID, SUP_ID, OP_ID, GRP_TYPE, GRP_STATE, GRP_VISA, GRP_FLOW, GRP_COIN, GRP_HY_CODE, GRP_RUN_MODEL, GRP_SIMPLE_NAME, GRP_GUIDE_PRICE, SUP_DOWN_ID, grp_income, grp_devote, grp_base, grp_ratio, GRP_INCOUNTRY, GRP_T, GRP_P_D, GRP_AIR, GRP_AIR_WAY, GRP_AIR_DATE, GRP_PICK, GRP_TEL_CH, GRP_LEADER_NAME, GRP_LEADER_TEL, GRP_LINK_TEL1, GRP_LINK_TEL2, GRP_VIS_MEMO1, GRP_VIS_MEMO2, GRP_BACK_DATE, GRP_ARV_DATE, GRP_OP_STA, GRP_BACK_TIME, GRP_FORM_ID, CLAC_STATUS, DESTINE_STATE, GRP_CTL_TIME, GRP_SETTL_UNID, GRP_REMARK, GRP_PUSH_MONEY, INSURE, GRP_OPEN_ID, DESTINE_TIME, DESTINE_END_TIME, GRP_SQ_ID, ARRIVE_TIME, GRP_UNID, SRV_ID, GRP_BACK_ADM_ID, GRP_BACK_AIR, GRP_BACK_AIR_WAY, GRP_BACK_AIR_DATE, GRP_OUT_SETTING_OK, GRP_SEND_ADM_ID, GRP_SEND_MEMO, GRP_SEND_TIME, GRP_SYNC_UID, LINE_ID, GRP_CANCEL, _SYNC_, DEP_ID, GRP_CAMP_COUNTRY, GRP_CAMP_ID, GRP_CAMP_BEGIN_DATE, GRP_CAMP_END_DATE, GRP_CAMP_ACCOMMODATION_TYPE, GRP_CAMP_COURSE_TYPE, CP_VISA_STATUS, CP_STATUS, CP_AIR_TICKET, GRP_CAMP_SET_STATUS, GRP_CAMP_AGE, GRP_FIRST_HOTEL, GRP_EMBASSY, GRP_COUNTRYS, GRP_PID, GRP_HOME_STATE, GRP_HOME_ADM_ID, GRP_HOME_OP_ID, GRP_HOME_EDATE, GRP_HOME_OP_EDATE, GRP_SHOW_TAG, GRP_CAMP_UNID, GRP_VIS_DEF, GRP_CAMP_DEF, GRP_AIR_DEF, GRP_NAME_EN, AUS_ADT, AUS_CHD, AUS_CHDNBRATE, AUS_CHDRATE, AUS_FOC, AUS_INFANTRATE, AUS_MARGIN, AUS_MARKUP, AUS_SNL, AUS_TOURCLASS, AUS_TWN, AUS_UNDER18, IS_AUS, AUS_SEATERS, AUS_RATING, EXAM_ID, GRP_HEADER_TEACHER, GRP_TEACHER_CHINESE, GRP_TEACHER_FOREIGN, GRP_SCHOOL_NAME, GRP_CLASS_NAME, GRP_CLASSROOM, GRP_TECHING_MATERIAL, GRP_REF_TABLE, GRP_REF_ID, UNIV_UNID) 	VALUES(@GRP_NAME, @GRP_CODE, @GRP_WAIT_DAYS, @GRP_PRC_SIN, @GRP_NUM, @GRP_MEMO, @GRP_NIGHT, @GRP_CDATE, @GRP_MDATE, @GRP_SDATE, @GRP_STOP, @GRP_STA, @ADM_ID, @SUP_ID, @OP_ID, @GRP_TYPE, @GRP_STATE, @GRP_VISA, @GRP_FLOW, @GRP_COIN, @GRP_HY_CODE, @GRP_RUN_MODEL, @GRP_SIMPLE_NAME, @GRP_GUIDE_PRICE, @SUP_DOWN_ID, @grp_income, @grp_devote, @grp_base, @grp_ratio, @GRP_INCOUNTRY, @GRP_T, @GRP_P_D, @GRP_AIR, @GRP_AIR_WAY, @GRP_AIR_DATE, @GRP_PICK, @GRP_TEL_CH, @GRP_LEADER_NAME, @GRP_LEADER_TEL, @GRP_LINK_TEL1, @GRP_LINK_TEL2, @GRP_VIS_MEMO1, @GRP_VIS_MEMO2, @GRP_BACK_DATE, @GRP_ARV_DATE, @GRP_OP_STA, @GRP_BACK_TIME, @GRP_FORM_ID, @CLAC_STATUS, @DESTINE_STATE, @GRP_CTL_TIME, @GRP_SETTL_UNID, @GRP_REMARK, @GRP_PUSH_MONEY, @INSURE, @GRP_OPEN_ID, @DESTINE_TIME, @DESTINE_END_TIME, @GRP_SQ_ID, @ARRIVE_TIME, @GRP_UNID, @SRV_ID, @GRP_BACK_ADM_ID, @GRP_BACK_AIR, @GRP_BACK_AIR_WAY, @GRP_BACK_AIR_DATE, @GRP_OUT_SETTING_OK, @GRP_SEND_ADM_ID, @GRP_SEND_MEMO, @GRP_SEND_TIME, @GRP_SYNC_UID, @LINE_ID, @GRP_CANCEL, @_SYNC_, @DEP_ID, @GRP_CAMP_COUNTRY, @GRP_CAMP_ID, @GRP_CAMP_BEGIN_DATE, @GRP_CAMP_END_DATE, @GRP_CAMP_ACCOMMODATION_TYPE, @GRP_CAMP_COURSE_TYPE, @CP_VISA_STATUS, @CP_STATUS, @CP_AIR_TICKET, @GRP_CAMP_SET_STATUS, @GRP_CAMP_AGE, @GRP_FIRST_HOTEL, @GRP_EMBASSY, @GRP_COUNTRYS, @GRP_PID, @GRP_HOME_STATE, @GRP_HOME_ADM_ID, @GRP_HOME_OP_ID, @GRP_HOME_EDATE, @GRP_HOME_OP_EDATE, @GRP_SHOW_TAG, @GRP_CAMP_UNID, @GRP_VIS_DEF, @GRP_CAMP_DEF, @GRP_AIR_DEF, @GRP_NAME_EN, @AUS_ADT, @AUS_CHD, @AUS_CHDNBRATE, @AUS_CHDRATE, @AUS_FOC, @AUS_INFANTRATE, @AUS_MARGIN, @AUS_MARKUP, @AUS_SNL, @AUS_TOURCLASS, @AUS_TWN, @AUS_UNDER18, @IS_AUS, @AUS_SEATERS, @AUS_RATING, @EXAM_ID, @GRP_HEADER_TEACHER, @GRP_TEACHER_CHINESE, @GRP_TEACHER_FOREIGN, @GRP_SCHOOL_NAME, @GRP_CLASS_NAME, @GRP_CLASSROOM, @GRP_TECHING_MATERIAL, @GRP_REF_TABLE, @GRP_REF_ID, @UNIV_UNID)";
	public static String TABLE_NAME = "GRP_MAIN";
	public static String[] KEY_LIST = { "GRP_ID" };
	public static String[] FIELD_LIST = { "GRP_ID", "GRP_NAME", "GRP_CODE", "GRP_WAIT_DAYS", "GRP_PRC_SIN", "GRP_NUM",
			"GRP_MEMO", "GRP_NIGHT", "GRP_CDATE", "GRP_MDATE", "GRP_SDATE", "GRP_STOP", "GRP_STA", "ADM_ID", "SUP_ID",
			"OP_ID", "GRP_TYPE", "GRP_STATE", "GRP_VISA", "GRP_FLOW", "GRP_COIN", "GRP_HY_CODE", "GRP_RUN_MODEL",
			"GRP_SIMPLE_NAME", "GRP_GUIDE_PRICE", "SUP_DOWN_ID", "grp_income", "grp_devote", "grp_base", "grp_ratio",
			"GRP_INCOUNTRY", "GRP_T", "GRP_P_D", "GRP_AIR", "GRP_AIR_WAY", "GRP_AIR_DATE", "GRP_PICK", "GRP_TEL_CH",
			"GRP_LEADER_NAME", "GRP_LEADER_TEL", "GRP_LINK_TEL1", "GRP_LINK_TEL2", "GRP_VIS_MEMO1", "GRP_VIS_MEMO2",
			"GRP_BACK_DATE", "GRP_ARV_DATE", "GRP_OP_STA", "GRP_BACK_TIME", "GRP_FORM_ID", "CLAC_STATUS",
			"DESTINE_STATE", "GRP_CTL_TIME", "GRP_SETTL_UNID", "GRP_REMARK", "GRP_PUSH_MONEY", "INSURE", "GRP_OPEN_ID",
			"DESTINE_TIME", "DESTINE_END_TIME", "GRP_SQ_ID", "ARRIVE_TIME", "GRP_UNID", "SRV_ID", "GRP_BACK_ADM_ID",
			"GRP_BACK_AIR", "GRP_BACK_AIR_WAY", "GRP_BACK_AIR_DATE", "GRP_OUT_SETTING_OK", "GRP_SEND_ADM_ID",
			"GRP_SEND_MEMO", "GRP_SEND_TIME", "GRP_SYNC_UID", "LINE_ID", "GRP_CANCEL", "_SYNC_", "DEP_ID",
			"GRP_CAMP_COUNTRY", "GRP_CAMP_ID", "GRP_CAMP_BEGIN_DATE", "GRP_CAMP_END_DATE",
			"GRP_CAMP_ACCOMMODATION_TYPE", "GRP_CAMP_COURSE_TYPE", "CP_VISA_STATUS", "CP_STATUS", "CP_AIR_TICKET",
			"GRP_CAMP_SET_STATUS", "GRP_CAMP_AGE", "GRP_FIRST_HOTEL", "GRP_EMBASSY", "GRP_COUNTRYS", "GRP_PID",
			"GRP_HOME_STATE", "GRP_HOME_ADM_ID", "GRP_HOME_OP_ID", "GRP_HOME_EDATE", "GRP_HOME_OP_EDATE",
			"GRP_SHOW_TAG", "GRP_CAMP_UNID", "GRP_VIS_DEF", "GRP_CAMP_DEF", "GRP_AIR_DEF", "GRP_NAME_EN", "AUS_ADT",
			"AUS_CHD", "AUS_CHDNBRATE", "AUS_CHDRATE", "AUS_FOC", "AUS_INFANTRATE", "AUS_MARGIN", "AUS_MARKUP",
			"AUS_SNL", "AUS_TOURCLASS", "AUS_TWN", "AUS_UNDER18", "IS_AUS", "AUS_SEATERS", "AUS_RATING", "EXAM_ID",
			"GRP_HEADER_TEACHER", "GRP_TEACHER_CHINESE", "GRP_TEACHER_FOREIGN", "GRP_SCHOOL_NAME", "GRP_CLASS_NAME",
			"GRP_CLASSROOM", "GRP_TECHING_MATERIAL", "GRP_REF_TABLE", "GRP_REF_ID", "UNIV_UNID" };

	public GrpMainDao() {
		// 设置数据库连接配置名称，在 ewa_conf.xml中定义
		// super.setConfigName("globaltravel");
		super.setInstanceClass(GrpMain.class);
		super.setTableName(TABLE_NAME);
		super.setFields(FIELD_LIST);
		super.setKeyFields(KEY_LIST);
		super.setSqlInsert(SQL_INSERT);
	}

	/**
	 * 生成一条记录
	 * 
	 * @param para 表GRP_MAIN的映射类
	 * 
	 * @return 是否成功
	 */

	public boolean newRecord(GrpMain para) {

		RequestValue rv = this.createRequestValue(para);

		int autoKey = super.executeUpdateAutoIncrement(SQL_INSERT, rv);
		if (autoKey > 0) {
			para.setGrpId(autoKey);// 自增
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 生成一条记录
	 * 
	 * @param para         表GRP_MAIN的映射类
	 * @param updateFields 变化的字段Map
	 * @return
	 */
	public boolean newRecord(GrpMain para, HashMap<String, Boolean> updateFields) {
		String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
		if (sql == null) { // 没有可更新数据
			return false;
		}
		RequestValue rv = this.createRequestValue(para);
		int autoKey = super.executeUpdateAutoIncrement(sql, rv);
		if (autoKey > 0) {
			para.setGrpId(autoKey);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据主键返回一条记录
	 * 
	 * @param paraGrpId 团编号
	 * @return 记录类(GrpMain)
	 */
	public GrpMain getRecord(Integer paraGrpId) {
		RequestValue rv = new RequestValue();
		rv.addValue("GRP_ID", paraGrpId, "Integer", 10);
		String sql = super.getSqlSelect();
		ArrayList<GrpMain> al = super.executeQuery(sql, rv, new GrpMain(), FIELD_LIST);
		if (al.size() > 0) {
			GrpMain o = al.get(0);
			al.clear();
			return o;
		} else {
			return null;
		}
	}

	/**
	 * 根据主键删除一条记录
	 * 
	 * @param paraGrpId 团编号
	 * @return 是否成功
	 */
	public boolean deleteRecord(Integer paraGrpId) {
		RequestValue rv = new RequestValue();
		rv.addValue("GRP_ID", paraGrpId, "Integer", 10);
		return super.executeUpdate(super.createDeleteSql(), rv);
	}
}