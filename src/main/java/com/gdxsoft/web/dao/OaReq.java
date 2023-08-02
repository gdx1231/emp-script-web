package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表OA_REQ映射类
* @author gdx 时间：Mon Feb 18 2019 17:17:32 GMT+0800 (中国标准时间)*/
public class OaReq extends ClassBase{private Integer reqId_; // 需求编号
private Integer reqPid_; // 上级编号
private String reqSubject_; // 主题
private String reqCnt_; // REQ_CNT
private String reqMemo_; // 描述
private String reqStatus_; // 状态
private String reqType_; // 类型
private Date reqCdate_; // 创建日期
private Date reqMdate_; // 修改日期
private Integer reqAdmId_; // 提交者
private Integer reqDepId_; // 需求部门
private Integer reqRevAdmId_; // 承接人
private Integer reqRevDepId_; // 承接部门
private String reqRevDeps_; // 参与部门
private Date reqRevOkTime_; // 完成时间
private Date reqRevPlanTime_; // 计划时间
private Integer supId_; // 供应商
private String reqRevAdmsName_; // REQ_REV_ADMS_NAME
private Date reqDelayMailTime_; // REQ_DELAY_MAIL_TIME
private String reqUnid_; // REQ_UNID
private Integer reqFromMsgId_; // REQ_FROM_MSG_ID
private String refTag_; // REF_TAG
private Integer refId_; // REF_ID
private Integer refId1_; // REF_ID1
private String refTab_; // REF_TAB
private Integer refTabId_; // REF_TAB_ID
private Integer tskId_; // TSK_ID
private String reqType1_; // REQ_TYPE1
private Integer reqOaDir_; // REQ_OA_DIR
private Date reqStartTime_; // REQ_START_TIME
private String reqSubjectEn_; // REQ_SUBJECT_EN
private String reqMemoEn_; // REQ_MEMO_EN
private Double reqProgress_; // REQ_PROGRESS
private String reqColor_; // REQ_COLOR
private Integer reqOrd_; // REQ_ORD
private String reqPlace_; // REQ_PLACE
private String reqPlaceEn_; // REQ_PLACE_EN
private Integer enqJnySerSubId_; // ENQ_JNY_SER_SUB_ID
private Double reqTarVal_; // 计划目标值
private Double reqComVal_; // 计划完成值
private String reqLocked_; // 任务锁定
private String reqLink_; // 跳转外部地址

/**
 * 获取 需求编号
 *
* @return 需求编号
*/
public Integer getReqId() {return this.reqId_;}
/**
* 赋值 需求编号

* @param paraReqId
* 需求编号
 */

public void setReqId(Integer paraReqId){
  super.recordChanged("REQ_ID", this.reqId_, paraReqId);
  this.reqId_ = paraReqId;
}


/**
 * 获取 上级编号
 *
* @return 上级编号
*/
public Integer getReqPid() {return this.reqPid_;}
/**
* 赋值 上级编号

* @param paraReqPid
* 上级编号
 */

public void setReqPid(Integer paraReqPid){
  super.recordChanged("REQ_PID", this.reqPid_, paraReqPid);
  this.reqPid_ = paraReqPid;
}


/**
 * 获取 主题
 *
* @return 主题
*/
public String getReqSubject() {return this.reqSubject_;}
/**
* 赋值 主题

* @param paraReqSubject
* 主题
 */

public void setReqSubject(String paraReqSubject){
  super.recordChanged("REQ_SUBJECT", this.reqSubject_, paraReqSubject);
  this.reqSubject_ = paraReqSubject;
}


/**
 * 获取 REQ_CNT
 *
* @return REQ_CNT
*/
public String getReqCnt() {return this.reqCnt_;}
/**
* 赋值 REQ_CNT

* @param paraReqCnt
* REQ_CNT
 */

public void setReqCnt(String paraReqCnt){
  super.recordChanged("REQ_CNT", this.reqCnt_, paraReqCnt);
  this.reqCnt_ = paraReqCnt;
}


/**
 * 获取 描述
 *
* @return 描述
*/
public String getReqMemo() {return this.reqMemo_;}
/**
* 赋值 描述

* @param paraReqMemo
* 描述
 */

public void setReqMemo(String paraReqMemo){
  super.recordChanged("REQ_MEMO", this.reqMemo_, paraReqMemo);
  this.reqMemo_ = paraReqMemo;
}


/**
 * 获取 状态
 *
* @return 状态
*/
public String getReqStatus() {return this.reqStatus_;}
/**
* 赋值 状态

* @param paraReqStatus
* 状态
 */

public void setReqStatus(String paraReqStatus){
  super.recordChanged("REQ_STATUS", this.reqStatus_, paraReqStatus);
  this.reqStatus_ = paraReqStatus;
}


/**
 * 获取 类型
 *
* @return 类型
*/
public String getReqType() {return this.reqType_;}
/**
* 赋值 类型

* @param paraReqType
* 类型
 */

public void setReqType(String paraReqType){
  super.recordChanged("REQ_TYPE", this.reqType_, paraReqType);
  this.reqType_ = paraReqType;
}


/**
 * 获取 创建日期
 *
* @return 创建日期
*/
public Date getReqCdate() {return this.reqCdate_;}
/**
* 赋值 创建日期

* @param paraReqCdate
* 创建日期
 */

public void setReqCdate(Date paraReqCdate){
  super.recordChanged("REQ_CDATE", this.reqCdate_, paraReqCdate);
  this.reqCdate_ = paraReqCdate;
}


/**
 * 获取 修改日期
 *
* @return 修改日期
*/
public Date getReqMdate() {return this.reqMdate_;}
/**
* 赋值 修改日期

* @param paraReqMdate
* 修改日期
 */

public void setReqMdate(Date paraReqMdate){
  super.recordChanged("REQ_MDATE", this.reqMdate_, paraReqMdate);
  this.reqMdate_ = paraReqMdate;
}


/**
 * 获取 提交者
 *
* @return 提交者
*/
public Integer getReqAdmId() {return this.reqAdmId_;}
/**
* 赋值 提交者

* @param paraReqAdmId
* 提交者
 */

public void setReqAdmId(Integer paraReqAdmId){
  super.recordChanged("REQ_ADM_ID", this.reqAdmId_, paraReqAdmId);
  this.reqAdmId_ = paraReqAdmId;
}


/**
 * 获取 需求部门
 *
* @return 需求部门
*/
public Integer getReqDepId() {return this.reqDepId_;}
/**
* 赋值 需求部门

* @param paraReqDepId
* 需求部门
 */

public void setReqDepId(Integer paraReqDepId){
  super.recordChanged("REQ_DEP_ID", this.reqDepId_, paraReqDepId);
  this.reqDepId_ = paraReqDepId;
}


/**
 * 获取 承接人
 *
* @return 承接人
*/
public Integer getReqRevAdmId() {return this.reqRevAdmId_;}
/**
* 赋值 承接人

* @param paraReqRevAdmId
* 承接人
 */

public void setReqRevAdmId(Integer paraReqRevAdmId){
  super.recordChanged("REQ_REV_ADM_ID", this.reqRevAdmId_, paraReqRevAdmId);
  this.reqRevAdmId_ = paraReqRevAdmId;
}


/**
 * 获取 承接部门
 *
* @return 承接部门
*/
public Integer getReqRevDepId() {return this.reqRevDepId_;}
/**
* 赋值 承接部门

* @param paraReqRevDepId
* 承接部门
 */

public void setReqRevDepId(Integer paraReqRevDepId){
  super.recordChanged("REQ_REV_DEP_ID", this.reqRevDepId_, paraReqRevDepId);
  this.reqRevDepId_ = paraReqRevDepId;
}


/**
 * 获取 参与部门
 *
* @return 参与部门
*/
public String getReqRevDeps() {return this.reqRevDeps_;}
/**
* 赋值 参与部门

* @param paraReqRevDeps
* 参与部门
 */

public void setReqRevDeps(String paraReqRevDeps){
  super.recordChanged("REQ_REV_DEPS", this.reqRevDeps_, paraReqRevDeps);
  this.reqRevDeps_ = paraReqRevDeps;
}


/**
 * 获取 完成时间
 *
* @return 完成时间
*/
public Date getReqRevOkTime() {return this.reqRevOkTime_;}
/**
* 赋值 完成时间

* @param paraReqRevOkTime
* 完成时间
 */

public void setReqRevOkTime(Date paraReqRevOkTime){
  super.recordChanged("REQ_REV_OK_TIME", this.reqRevOkTime_, paraReqRevOkTime);
  this.reqRevOkTime_ = paraReqRevOkTime;
}


/**
 * 获取 计划时间
 *
* @return 计划时间
*/
public Date getReqRevPlanTime() {return this.reqRevPlanTime_;}
/**
* 赋值 计划时间

* @param paraReqRevPlanTime
* 计划时间
 */

public void setReqRevPlanTime(Date paraReqRevPlanTime){
  super.recordChanged("REQ_REV_PLAN_TIME", this.reqRevPlanTime_, paraReqRevPlanTime);
  this.reqRevPlanTime_ = paraReqRevPlanTime;
}


/**
 * 获取 供应商
 *
* @return 供应商
*/
public Integer getSupId() {return this.supId_;}
/**
* 赋值 供应商

* @param paraSupId
* 供应商
 */

public void setSupId(Integer paraSupId){
  super.recordChanged("SUP_ID", this.supId_, paraSupId);
  this.supId_ = paraSupId;
}


/**
 * 获取 REQ_REV_ADMS_NAME
 *
* @return REQ_REV_ADMS_NAME
*/
public String getReqRevAdmsName() {return this.reqRevAdmsName_;}
/**
* 赋值 REQ_REV_ADMS_NAME

* @param paraReqRevAdmsName
* REQ_REV_ADMS_NAME
 */

public void setReqRevAdmsName(String paraReqRevAdmsName){
  super.recordChanged("REQ_REV_ADMS_NAME", this.reqRevAdmsName_, paraReqRevAdmsName);
  this.reqRevAdmsName_ = paraReqRevAdmsName;
}


/**
 * 获取 REQ_DELAY_MAIL_TIME
 *
* @return REQ_DELAY_MAIL_TIME
*/
public Date getReqDelayMailTime() {return this.reqDelayMailTime_;}
/**
* 赋值 REQ_DELAY_MAIL_TIME

* @param paraReqDelayMailTime
* REQ_DELAY_MAIL_TIME
 */

public void setReqDelayMailTime(Date paraReqDelayMailTime){
  super.recordChanged("REQ_DELAY_MAIL_TIME", this.reqDelayMailTime_, paraReqDelayMailTime);
  this.reqDelayMailTime_ = paraReqDelayMailTime;
}


/**
 * 获取 REQ_UNID
 *
* @return REQ_UNID
*/
public String getReqUnid() {return this.reqUnid_;}
/**
* 赋值 REQ_UNID

* @param paraReqUnid
* REQ_UNID
 */

public void setReqUnid(String paraReqUnid){
  super.recordChanged("REQ_UNID", this.reqUnid_, paraReqUnid);
  this.reqUnid_ = paraReqUnid;
}


/**
 * 获取 REQ_FROM_MSG_ID
 *
* @return REQ_FROM_MSG_ID
*/
public Integer getReqFromMsgId() {return this.reqFromMsgId_;}
/**
* 赋值 REQ_FROM_MSG_ID

* @param paraReqFromMsgId
* REQ_FROM_MSG_ID
 */

public void setReqFromMsgId(Integer paraReqFromMsgId){
  super.recordChanged("REQ_FROM_MSG_ID", this.reqFromMsgId_, paraReqFromMsgId);
  this.reqFromMsgId_ = paraReqFromMsgId;
}


/**
 * 获取 REF_TAG
 *
* @return REF_TAG
*/
public String getRefTag() {return this.refTag_;}
/**
* 赋值 REF_TAG

* @param paraRefTag
* REF_TAG
 */

public void setRefTag(String paraRefTag){
  super.recordChanged("REF_TAG", this.refTag_, paraRefTag);
  this.refTag_ = paraRefTag;
}


/**
 * 获取 REF_ID
 *
* @return REF_ID
*/
public Integer getRefId() {return this.refId_;}
/**
* 赋值 REF_ID

* @param paraRefId
* REF_ID
 */

public void setRefId(Integer paraRefId){
  super.recordChanged("REF_ID", this.refId_, paraRefId);
  this.refId_ = paraRefId;
}


/**
 * 获取 REF_ID1
 *
* @return REF_ID1
*/
public Integer getRefId1() {return this.refId1_;}
/**
* 赋值 REF_ID1

* @param paraRefId1
* REF_ID1
 */

public void setRefId1(Integer paraRefId1){
  super.recordChanged("REF_ID1", this.refId1_, paraRefId1);
  this.refId1_ = paraRefId1;
}


/**
 * 获取 REF_TAB
 *
* @return REF_TAB
*/
public String getRefTab() {return this.refTab_;}
/**
* 赋值 REF_TAB

* @param paraRefTab
* REF_TAB
 */

public void setRefTab(String paraRefTab){
  super.recordChanged("REF_TAB", this.refTab_, paraRefTab);
  this.refTab_ = paraRefTab;
}


/**
 * 获取 REF_TAB_ID
 *
* @return REF_TAB_ID
*/
public Integer getRefTabId() {return this.refTabId_;}
/**
* 赋值 REF_TAB_ID

* @param paraRefTabId
* REF_TAB_ID
 */

public void setRefTabId(Integer paraRefTabId){
  super.recordChanged("REF_TAB_ID", this.refTabId_, paraRefTabId);
  this.refTabId_ = paraRefTabId;
}


/**
 * 获取 TSK_ID
 *
* @return TSK_ID
*/
public Integer getTskId() {return this.tskId_;}
/**
* 赋值 TSK_ID

* @param paraTskId
* TSK_ID
 */

public void setTskId(Integer paraTskId){
  super.recordChanged("TSK_ID", this.tskId_, paraTskId);
  this.tskId_ = paraTskId;
}


/**
 * 获取 REQ_TYPE1
 *
* @return REQ_TYPE1
*/
public String getReqType1() {return this.reqType1_;}
/**
* 赋值 REQ_TYPE1

* @param paraReqType1
* REQ_TYPE1
 */

public void setReqType1(String paraReqType1){
  super.recordChanged("REQ_TYPE1", this.reqType1_, paraReqType1);
  this.reqType1_ = paraReqType1;
}


/**
 * 获取 REQ_OA_DIR
 *
* @return REQ_OA_DIR
*/
public Integer getReqOaDir() {return this.reqOaDir_;}
/**
* 赋值 REQ_OA_DIR

* @param paraReqOaDir
* REQ_OA_DIR
 */

public void setReqOaDir(Integer paraReqOaDir){
  super.recordChanged("REQ_OA_DIR", this.reqOaDir_, paraReqOaDir);
  this.reqOaDir_ = paraReqOaDir;
}


/**
 * 获取 REQ_START_TIME
 *
* @return REQ_START_TIME
*/
public Date getReqStartTime() {return this.reqStartTime_;}
/**
* 赋值 REQ_START_TIME

* @param paraReqStartTime
* REQ_START_TIME
 */

public void setReqStartTime(Date paraReqStartTime){
  super.recordChanged("REQ_START_TIME", this.reqStartTime_, paraReqStartTime);
  this.reqStartTime_ = paraReqStartTime;
}


/**
 * 获取 REQ_SUBJECT_EN
 *
* @return REQ_SUBJECT_EN
*/
public String getReqSubjectEn() {return this.reqSubjectEn_;}
/**
* 赋值 REQ_SUBJECT_EN

* @param paraReqSubjectEn
* REQ_SUBJECT_EN
 */

public void setReqSubjectEn(String paraReqSubjectEn){
  super.recordChanged("REQ_SUBJECT_EN", this.reqSubjectEn_, paraReqSubjectEn);
  this.reqSubjectEn_ = paraReqSubjectEn;
}


/**
 * 获取 REQ_MEMO_EN
 *
* @return REQ_MEMO_EN
*/
public String getReqMemoEn() {return this.reqMemoEn_;}
/**
* 赋值 REQ_MEMO_EN

* @param paraReqMemoEn
* REQ_MEMO_EN
 */

public void setReqMemoEn(String paraReqMemoEn){
  super.recordChanged("REQ_MEMO_EN", this.reqMemoEn_, paraReqMemoEn);
  this.reqMemoEn_ = paraReqMemoEn;
}


/**
 * 获取 REQ_PROGRESS
 *
* @return REQ_PROGRESS
*/
public Double getReqProgress() {return this.reqProgress_;}
/**
* 赋值 REQ_PROGRESS

* @param paraReqProgress
* REQ_PROGRESS
 */

public void setReqProgress(Double paraReqProgress){
  super.recordChanged("REQ_PROGRESS", this.reqProgress_, paraReqProgress);
  this.reqProgress_ = paraReqProgress;
}


/**
 * 获取 REQ_COLOR
 *
* @return REQ_COLOR
*/
public String getReqColor() {return this.reqColor_;}
/**
* 赋值 REQ_COLOR

* @param paraReqColor
* REQ_COLOR
 */

public void setReqColor(String paraReqColor){
  super.recordChanged("REQ_COLOR", this.reqColor_, paraReqColor);
  this.reqColor_ = paraReqColor;
}


/**
 * 获取 REQ_ORD
 *
* @return REQ_ORD
*/
public Integer getReqOrd() {return this.reqOrd_;}
/**
* 赋值 REQ_ORD

* @param paraReqOrd
* REQ_ORD
 */

public void setReqOrd(Integer paraReqOrd){
  super.recordChanged("REQ_ORD", this.reqOrd_, paraReqOrd);
  this.reqOrd_ = paraReqOrd;
}


/**
 * 获取 REQ_PLACE
 *
* @return REQ_PLACE
*/
public String getReqPlace() {return this.reqPlace_;}
/**
* 赋值 REQ_PLACE

* @param paraReqPlace
* REQ_PLACE
 */

public void setReqPlace(String paraReqPlace){
  super.recordChanged("REQ_PLACE", this.reqPlace_, paraReqPlace);
  this.reqPlace_ = paraReqPlace;
}


/**
 * 获取 REQ_PLACE_EN
 *
* @return REQ_PLACE_EN
*/
public String getReqPlaceEn() {return this.reqPlaceEn_;}
/**
* 赋值 REQ_PLACE_EN

* @param paraReqPlaceEn
* REQ_PLACE_EN
 */

public void setReqPlaceEn(String paraReqPlaceEn){
  super.recordChanged("REQ_PLACE_EN", this.reqPlaceEn_, paraReqPlaceEn);
  this.reqPlaceEn_ = paraReqPlaceEn;
}


/**
 * 获取 ENQ_JNY_SER_SUB_ID
 *
* @return ENQ_JNY_SER_SUB_ID
*/
public Integer getEnqJnySerSubId() {return this.enqJnySerSubId_;}
/**
* 赋值 ENQ_JNY_SER_SUB_ID

* @param paraEnqJnySerSubId
* ENQ_JNY_SER_SUB_ID
 */

public void setEnqJnySerSubId(Integer paraEnqJnySerSubId){
  super.recordChanged("ENQ_JNY_SER_SUB_ID", this.enqJnySerSubId_, paraEnqJnySerSubId);
  this.enqJnySerSubId_ = paraEnqJnySerSubId;
}


/**
 * 获取 计划目标值
 *
* @return 计划目标值
*/
public Double getReqTarVal() {return this.reqTarVal_;}
/**
* 赋值 计划目标值

* @param paraReqTarVal
* 计划目标值
 */

public void setReqTarVal(Double paraReqTarVal){
  super.recordChanged("REQ_TAR_VAL", this.reqTarVal_, paraReqTarVal);
  this.reqTarVal_ = paraReqTarVal;
}


/**
 * 获取 计划完成值
 *
* @return 计划完成值
*/
public Double getReqComVal() {return this.reqComVal_;}
/**
* 赋值 计划完成值

* @param paraReqComVal
* 计划完成值
 */

public void setReqComVal(Double paraReqComVal){
  super.recordChanged("REQ_COM_VAL", this.reqComVal_, paraReqComVal);
  this.reqComVal_ = paraReqComVal;
}


/**
 * 获取 任务锁定
 *
* @return 任务锁定
*/
public String getReqLocked() {return this.reqLocked_;}
/**
* 赋值 任务锁定

* @param paraReqLocked
* 任务锁定
 */

public void setReqLocked(String paraReqLocked){
  super.recordChanged("REQ_LOCKED", this.reqLocked_, paraReqLocked);
  this.reqLocked_ = paraReqLocked;
}


/**
 * 获取 跳转外部地址
 *
* @return 跳转外部地址
*/
public String getReqLink() {return this.reqLink_;}
/**
* 赋值 跳转外部地址

* @param paraReqLink
* 跳转外部地址
 */

public void setReqLink(String paraReqLink){
  super.recordChanged("REQ_LINK", this.reqLink_, paraReqLink);
  this.reqLink_ = paraReqLink;
}/**根据字段名称获取值，如果名称为空或字段未找到，返回空值
 @param filedName 字段名称
 @return 字段值*/
public Object getField(String filedName){
if(filedName == null){ return null; }
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("REQ_ID")){return this.reqId_;}
if(n.equalsIgnoreCase("REQ_PID")){return this.reqPid_;}
if(n.equalsIgnoreCase("REQ_SUBJECT")){return this.reqSubject_;}
if(n.equalsIgnoreCase("REQ_CNT")){return this.reqCnt_;}
if(n.equalsIgnoreCase("REQ_MEMO")){return this.reqMemo_;}
if(n.equalsIgnoreCase("REQ_STATUS")){return this.reqStatus_;}
if(n.equalsIgnoreCase("REQ_TYPE")){return this.reqType_;}
if(n.equalsIgnoreCase("REQ_CDATE")){return this.reqCdate_;}
if(n.equalsIgnoreCase("REQ_MDATE")){return this.reqMdate_;}
if(n.equalsIgnoreCase("REQ_ADM_ID")){return this.reqAdmId_;}
if(n.equalsIgnoreCase("REQ_DEP_ID")){return this.reqDepId_;}
if(n.equalsIgnoreCase("REQ_REV_ADM_ID")){return this.reqRevAdmId_;}
if(n.equalsIgnoreCase("REQ_REV_DEP_ID")){return this.reqRevDepId_;}
if(n.equalsIgnoreCase("REQ_REV_DEPS")){return this.reqRevDeps_;}
if(n.equalsIgnoreCase("REQ_REV_OK_TIME")){return this.reqRevOkTime_;}
if(n.equalsIgnoreCase("REQ_REV_PLAN_TIME")){return this.reqRevPlanTime_;}
if(n.equalsIgnoreCase("SUP_ID")){return this.supId_;}
if(n.equalsIgnoreCase("REQ_REV_ADMS_NAME")){return this.reqRevAdmsName_;}
if(n.equalsIgnoreCase("REQ_DELAY_MAIL_TIME")){return this.reqDelayMailTime_;}
if(n.equalsIgnoreCase("REQ_UNID")){return this.reqUnid_;}
if(n.equalsIgnoreCase("REQ_FROM_MSG_ID")){return this.reqFromMsgId_;}
if(n.equalsIgnoreCase("REF_TAG")){return this.refTag_;}
if(n.equalsIgnoreCase("REF_ID")){return this.refId_;}
if(n.equalsIgnoreCase("REF_ID1")){return this.refId1_;}
if(n.equalsIgnoreCase("REF_TAB")){return this.refTab_;}
if(n.equalsIgnoreCase("REF_TAB_ID")){return this.refTabId_;}
if(n.equalsIgnoreCase("TSK_ID")){return this.tskId_;}
if(n.equalsIgnoreCase("REQ_TYPE1")){return this.reqType1_;}
if(n.equalsIgnoreCase("REQ_OA_DIR")){return this.reqOaDir_;}
if(n.equalsIgnoreCase("REQ_START_TIME")){return this.reqStartTime_;}
if(n.equalsIgnoreCase("REQ_SUBJECT_EN")){return this.reqSubjectEn_;}
if(n.equalsIgnoreCase("REQ_MEMO_EN")){return this.reqMemoEn_;}
if(n.equalsIgnoreCase("REQ_PROGRESS")){return this.reqProgress_;}
if(n.equalsIgnoreCase("REQ_COLOR")){return this.reqColor_;}
if(n.equalsIgnoreCase("REQ_ORD")){return this.reqOrd_;}
if(n.equalsIgnoreCase("REQ_PLACE")){return this.reqPlace_;}
if(n.equalsIgnoreCase("REQ_PLACE_EN")){return this.reqPlaceEn_;}
if(n.equalsIgnoreCase("ENQ_JNY_SER_SUB_ID")){return this.enqJnySerSubId_;}
if(n.equalsIgnoreCase("REQ_TAR_VAL")){return this.reqTarVal_;}
if(n.equalsIgnoreCase("REQ_COM_VAL")){return this.reqComVal_;}
if(n.equalsIgnoreCase("REQ_LOCKED")){return this.reqLocked_;}
if(n.equalsIgnoreCase("REQ_LINK")){return this.reqLink_;}
return null;}
}