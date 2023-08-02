package com.gdxsoft.web.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** 表OA_REQ操作类
 * @author gdx 时间：Mon Feb 18 2019 17:17:35 GMT+0800 (中国标准时间)
 */
public class OaReqDao extends ClassDaoBase<OaReq> implements IClassDao<OaReq>{

 private static String SQL_SELECT="SELECT * FROM OA_REQ WHERE REQ_ID=@REQ_ID";
 private static String SQL_UPDATE="UPDATE OA_REQ SET 	 REQ_PID = @REQ_PID, 	 REQ_SUBJECT = @REQ_SUBJECT, 	 REQ_CNT = @REQ_CNT, 	 REQ_MEMO = @REQ_MEMO, 	 REQ_STATUS = @REQ_STATUS, 	 REQ_TYPE = @REQ_TYPE, 	 REQ_CDATE = @REQ_CDATE, 	 REQ_MDATE = @REQ_MDATE, 	 REQ_ADM_ID = @REQ_ADM_ID, 	 REQ_DEP_ID = @REQ_DEP_ID, 	 REQ_REV_ADM_ID = @REQ_REV_ADM_ID, 	 REQ_REV_DEP_ID = @REQ_REV_DEP_ID, 	 REQ_REV_DEPS = @REQ_REV_DEPS, 	 REQ_REV_OK_TIME = @REQ_REV_OK_TIME, 	 REQ_REV_PLAN_TIME = @REQ_REV_PLAN_TIME, 	 SUP_ID = @SUP_ID, 	 REQ_REV_ADMS_NAME = @REQ_REV_ADMS_NAME, 	 REQ_DELAY_MAIL_TIME = @REQ_DELAY_MAIL_TIME, 	 REQ_UNID = @REQ_UNID, 	 REQ_FROM_MSG_ID = @REQ_FROM_MSG_ID, 	 REF_TAG = @REF_TAG, 	 REF_ID = @REF_ID, 	 REF_ID1 = @REF_ID1, 	 REF_TAB = @REF_TAB, 	 REF_TAB_ID = @REF_TAB_ID, 	 TSK_ID = @TSK_ID, 	 REQ_TYPE1 = @REQ_TYPE1, 	 REQ_OA_DIR = @REQ_OA_DIR, 	 REQ_START_TIME = @REQ_START_TIME, 	 REQ_SUBJECT_EN = @REQ_SUBJECT_EN, 	 REQ_MEMO_EN = @REQ_MEMO_EN, 	 REQ_PROGRESS = @REQ_PROGRESS, 	 REQ_COLOR = @REQ_COLOR, 	 REQ_ORD = @REQ_ORD, 	 REQ_PLACE = @REQ_PLACE, 	 REQ_PLACE_EN = @REQ_PLACE_EN, 	 ENQ_JNY_SER_SUB_ID = @ENQ_JNY_SER_SUB_ID, 	 REQ_TAR_VAL = @REQ_TAR_VAL, 	 REQ_COM_VAL = @REQ_COM_VAL, 	 REQ_LOCKED = @REQ_LOCKED, 	 REQ_LINK = @REQ_LINK WHERE REQ_ID=@REQ_ID";
 private static String SQL_DELETE="DELETE FROM OA_REQ WHERE REQ_ID=@REQ_ID";
 private static String SQL_INSERT="INSERT INTO OA_REQ(REQ_PID, REQ_SUBJECT, REQ_CNT, REQ_MEMO, REQ_STATUS, REQ_TYPE, REQ_CDATE, REQ_MDATE, REQ_ADM_ID, REQ_DEP_ID, REQ_REV_ADM_ID, REQ_REV_DEP_ID, REQ_REV_DEPS, REQ_REV_OK_TIME, REQ_REV_PLAN_TIME, SUP_ID, REQ_REV_ADMS_NAME, REQ_DELAY_MAIL_TIME, REQ_UNID, REQ_FROM_MSG_ID, REF_TAG, REF_ID, REF_ID1, REF_TAB, REF_TAB_ID, TSK_ID, REQ_TYPE1, REQ_OA_DIR, REQ_START_TIME, REQ_SUBJECT_EN, REQ_MEMO_EN, REQ_PROGRESS, REQ_COLOR, REQ_ORD, REQ_PLACE, REQ_PLACE_EN, ENQ_JNY_SER_SUB_ID, REQ_TAR_VAL, REQ_COM_VAL, REQ_LOCKED, REQ_LINK) 	VALUES(@REQ_PID, @REQ_SUBJECT, @REQ_CNT, @REQ_MEMO, @REQ_STATUS, @REQ_TYPE, @REQ_CDATE, @REQ_MDATE, @REQ_ADM_ID, @REQ_DEP_ID, @REQ_REV_ADM_ID, @REQ_REV_DEP_ID, @REQ_REV_DEPS, @REQ_REV_OK_TIME, @REQ_REV_PLAN_TIME, @SUP_ID, @REQ_REV_ADMS_NAME, @REQ_DELAY_MAIL_TIME, @REQ_UNID, @REQ_FROM_MSG_ID, @REF_TAG, @REF_ID, @REF_ID1, @REF_TAB, @REF_TAB_ID, @TSK_ID, @REQ_TYPE1, @REQ_OA_DIR, @REQ_START_TIME, @REQ_SUBJECT_EN, @REQ_MEMO_EN, @REQ_PROGRESS, @REQ_COLOR, @REQ_ORD, @REQ_PLACE, @REQ_PLACE_EN, @ENQ_JNY_SER_SUB_ID, @REQ_TAR_VAL, @REQ_COM_VAL, @REQ_LOCKED, @REQ_LINK)";
 public static String TABLE_NAME ="OA_REQ";
 public static String[] KEY_LIST = { "REQ_ID"   };
 public static String[] FIELD_LIST = { "REQ_ID", "REQ_PID", "REQ_SUBJECT", "REQ_CNT", "REQ_MEMO", "REQ_STATUS", "REQ_TYPE", "REQ_CDATE", "REQ_MDATE", "REQ_ADM_ID", "REQ_DEP_ID", "REQ_REV_ADM_ID", "REQ_REV_DEP_ID", "REQ_REV_DEPS", "REQ_REV_OK_TIME", "REQ_REV_PLAN_TIME", "SUP_ID", "REQ_REV_ADMS_NAME", "REQ_DELAY_MAIL_TIME", "REQ_UNID", "REQ_FROM_MSG_ID", "REF_TAG", "REF_ID", "REF_ID1", "REF_TAB", "REF_TAB_ID", "TSK_ID", "REQ_TYPE1", "REQ_OA_DIR", "REQ_START_TIME", "REQ_SUBJECT_EN", "REQ_MEMO_EN", "REQ_PROGRESS", "REQ_COLOR", "REQ_ORD", "REQ_PLACE", "REQ_PLACE_EN", "ENQ_JNY_SER_SUB_ID", "REQ_TAR_VAL", "REQ_COM_VAL", "REQ_LOCKED", "REQ_LINK" };
 public OaReqDao(){ 
   // 设置数据库连接配置名称，在 ewa_conf.xml中定义
   // super.setConfigName("globaltravel");
 }/**
	 * 生成一条记录
	*@param para 表OA_REQ的映射类

	 *@return 是否成功
	*/

	public boolean newRecord(OaReq para){

		RequestValue rv=this.createRequestValue(para);

int autoKey = super.executeUpdateAutoIncrement(SQL_INSERT, rv);
if (autoKey > 0) {
para.setReqId(autoKey);//自增
return true;
} else {
return false;
}
	}

/**
 * 生成一条记录
 * 
 * @param para
 *            表OA_REQ的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean newRecord(OaReq para, HashMap<String, Boolean> updateFields){
  String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
int autoKey = super.executeUpdateAutoIncrement(sql, rv);//自增
if (autoKey > 0) {
para.setReqId(autoKey);
return true;
} else {
return false;
}
}/**
 * 更新一条记录
*@param para 表OA_REQ的映射类
	 *@return 是否成功 
	 */
public boolean updateRecord(OaReq para){

  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(SQL_UPDATE, rv);
}
/**
 * 更新一条记录
 * 
 * @param para
 *            表OA_REQ的映射类
 * @param updateFields
 *            变化的字段Map
 * @return
 */
public boolean updateRecord(OaReq para, HashMap<String, Boolean> updateFields){
  // 没定义主键的话不能更新
  if(KEY_LIST.length==0){return false; } 
  String sql = super.sqlUpdateChanged(TABLE_NAME, KEY_LIST, updateFields);
  if (sql == null) { //没有可更新数据
  	return false;
  }
  RequestValue rv = this.createRequestValue(para);
  return super.executeUpdate(sql, rv);
}
public String getSqlDelete() {return SQL_DELETE;}
public String[] getSqlFields() {return FIELD_LIST;}
public String getSqlSelect() {return "SELECT * FROM OA_REQ where 1=1";}
public String getSqlUpdate() {return SQL_UPDATE;}
public String getSqlInsert() {return SQL_INSERT;}/**
	 * 根据主键返回一条记录
	*@param paraReqId 需求编号
	*@return 记录类(OaReq)
	*/
public OaReq getRecord(Integer paraReqId){
	RequestValue rv = new RequestValue();
rv.addValue("REQ_ID", paraReqId, "Integer", 10);
ArrayList<OaReq> al = super.executeQuery(SQL_SELECT, rv, new OaReq(), FIELD_LIST);
if(al.size()>0){
OaReq o = al.get(0);
al.clear();
return o;
}else{
return null;
}
}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	*@return 记录集合
	*/
	public ArrayList<OaReq> getRecords(String whereString){
		String sql="SELECT * FROM OA_REQ WHERE " + whereString;
		return super.executeQuery(sql, new OaReq(), FIELD_LIST);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	*@param  fields 指定返回的字段
	*@return 记录集合
	*/
	public ArrayList<OaReq> getRecords(String whereString, List<String> fields){
		String sql = super.createSelectSql(TABLE_NAME, whereString, fields);
		String[] arrFields = new String[fields.size()];
		arrFields = fields.toArray(arrFields);
		return super.executeQuery(sql, new OaReq(), arrFields);
	}
/**
	 * 根据查询条件返回多条记录（限制为500条）
	*@param  whereString 查询条件，注意过滤“'”符号，避免SQL注入攻击
	* @param pkFieldName   主键
	* @param pageSize      每页记录数
	* @param currentPage   当前页
	*@return 记录集合
	*/
	public ArrayList<OaReq> getRecords(String whereString, String pkFieldName, int pageSize, int currentPage){
		String sql="SELECT * FROM OA_REQ WHERE " + whereString;
		return super.executeQuery(sql, new OaReq(), FIELD_LIST, pkFieldName, pageSize, currentPage);
	}
/**
	 * 根据主键删除一条记录
	*@param paraReqId 需求编号
	*@return 是否成功
	*/
public boolean deleteRecord(Integer paraReqId){
	RequestValue rv = new RequestValue();
rv.addValue("REQ_ID", paraReqId, "Integer", 10);
	return super.executeUpdate(SQL_DELETE, rv);
}
public RequestValue createRequestValue(OaReq para){
RequestValue rv = new RequestValue();
rv.addValue("REQ_ID", para.getReqId(), "Integer", 10); // 需求编号
rv.addValue("REQ_PID", para.getReqPid(), "Integer", 10); // 上级编号
rv.addValue("REQ_SUBJECT", para.getReqSubject(), "String", 150); // 主题
rv.addValue("REQ_CNT", para.getReqCnt(), "String", 1073741823); // REQ_CNT
rv.addValue("REQ_MEMO", para.getReqMemo(), "String", 2147483647); // 描述
rv.addValue("REQ_STATUS", para.getReqStatus(), "String", 50); // 状态
rv.addValue("REQ_TYPE", para.getReqType(), "String", 50); // 类型
rv.addValue("REQ_CDATE", para.getReqCdate(), "Date", 23); // 创建日期
rv.addValue("REQ_MDATE", para.getReqMdate(), "Date", 23); // 修改日期
rv.addValue("REQ_ADM_ID", para.getReqAdmId(), "Integer", 10); // 提交者
rv.addValue("REQ_DEP_ID", para.getReqDepId(), "Integer", 10); // 需求部门
rv.addValue("REQ_REV_ADM_ID", para.getReqRevAdmId(), "Integer", 10); // 承接人
rv.addValue("REQ_REV_DEP_ID", para.getReqRevDepId(), "Integer", 10); // 承接部门
rv.addValue("REQ_REV_DEPS", para.getReqRevDeps(), "String", 2147483647); // 参与部门
rv.addValue("REQ_REV_OK_TIME", para.getReqRevOkTime(), "Date", 23); // 完成时间
rv.addValue("REQ_REV_PLAN_TIME", para.getReqRevPlanTime(), "Date", 23); // 计划时间
rv.addValue("SUP_ID", para.getSupId(), "Integer", 10); // 供应商
rv.addValue("REQ_REV_ADMS_NAME", para.getReqRevAdmsName(), "String", 1073741823); // REQ_REV_ADMS_NAME
rv.addValue("REQ_DELAY_MAIL_TIME", para.getReqDelayMailTime(), "Date", 23); // REQ_DELAY_MAIL_TIME
rv.addValue("REQ_UNID", para.getReqUnid(), "String", 36); // REQ_UNID
rv.addValue("REQ_FROM_MSG_ID", para.getReqFromMsgId(), "Integer", 10); // REQ_FROM_MSG_ID
rv.addValue("REF_TAG", para.getRefTag(), "String", 50); // REF_TAG
rv.addValue("REF_ID", para.getRefId(), "Integer", 10); // REF_ID
rv.addValue("REF_ID1", para.getRefId1(), "Integer", 10); // REF_ID1
rv.addValue("REF_TAB", para.getRefTab(), "String", 50); // REF_TAB
rv.addValue("REF_TAB_ID", para.getRefTabId(), "Integer", 10); // REF_TAB_ID
rv.addValue("TSK_ID", para.getTskId(), "Integer", 10); // TSK_ID
rv.addValue("REQ_TYPE1", para.getReqType1(), "String", 20); // REQ_TYPE1
rv.addValue("REQ_OA_DIR", para.getReqOaDir(), "Integer", 10); // REQ_OA_DIR
rv.addValue("REQ_START_TIME", para.getReqStartTime(), "Date", 23); // REQ_START_TIME
rv.addValue("REQ_SUBJECT_EN", para.getReqSubjectEn(), "String", 300); // REQ_SUBJECT_EN
rv.addValue("REQ_MEMO_EN", para.getReqMemoEn(), "String", 2147483647); // REQ_MEMO_EN
rv.addValue("REQ_PROGRESS", para.getReqProgress(), "Double", 53); // REQ_PROGRESS
rv.addValue("REQ_COLOR", para.getReqColor(), "String", 20); // REQ_COLOR
rv.addValue("REQ_ORD", para.getReqOrd(), "Integer", 10); // REQ_ORD
rv.addValue("REQ_PLACE", para.getReqPlace(), "String", 1000); // REQ_PLACE
rv.addValue("REQ_PLACE_EN", para.getReqPlaceEn(), "String", 2000); // REQ_PLACE_EN
rv.addValue("ENQ_JNY_SER_SUB_ID", para.getEnqJnySerSubId(), "Integer", 10); // ENQ_JNY_SER_SUB_ID
rv.addValue("REQ_TAR_VAL", para.getReqTarVal(), "Double", 19); // 计划目标值
rv.addValue("REQ_COM_VAL", para.getReqComVal(), "Double", 19); // 计划完成值
rv.addValue("REQ_LOCKED", para.getReqLocked(), "String", 20); // 任务锁定
rv.addValue("REQ_LINK", para.getReqLink(), "String", 400); // 跳转外部地址
return rv;
	}}