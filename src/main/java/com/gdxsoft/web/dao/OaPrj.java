package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表OA_PRJ映射类
* @author gdx 时间：Thu Mar 28 2019 10:37:08 GMT+0800 (中国标准时间)*/
public class OaPrj extends ClassBase{private Integer prjId_; // 编号
private String prjName_; // 项目名称
private String prjNameEn_; // 项目名称英文
private String prjMemo_; // 项目备注
private String prjMemoEn_; // 项目备注英文
private Integer prjMasterId_; // 项目负责人
private Date prjCdate_; // 创建时间
private Date prjMdate_; // 修改时间
private String prjUnid_; // UNID
private Integer supId_; // 商户
private Integer admId_; // 创建人
private String prjStatus_; // 状态
private String prjAdm_; // 参与人名单，分割
private String prjRefTable_; // 项目来源表
private String prjRefId_; // 项目来源ID
private String prjIsTemplate_; // 是否为模板
private Date prjStartTime_; // 项目启动时间
private Date rrjEndTime_; // 项目结束时间
private String prjRunStatus_; // 运行状态
private String prjSrcXmlTemp_; // 来源模板
private Integer prjSrcXmlHash_; // 来源模板的Hash

/**
 * 获取 编号
 *
* @return 编号
*/
public Integer getPrjId() {return this.prjId_;}
/**
* 赋值 编号

* @param paraPrjId
* 编号
 */

public void setPrjId(Integer paraPrjId){
  super.recordChanged("PRJ_ID", this.prjId_, paraPrjId);
  this.prjId_ = paraPrjId;
}


/**
 * 获取 项目名称
 *
* @return 项目名称
*/
public String getPrjName() {return this.prjName_;}
/**
* 赋值 项目名称

* @param paraPrjName
* 项目名称
 */

public void setPrjName(String paraPrjName){
  super.recordChanged("PRJ_NAME", this.prjName_, paraPrjName);
  this.prjName_ = paraPrjName;
}


/**
 * 获取 项目名称英文
 *
* @return 项目名称英文
*/
public String getPrjNameEn() {return this.prjNameEn_;}
/**
* 赋值 项目名称英文

* @param paraPrjNameEn
* 项目名称英文
 */

public void setPrjNameEn(String paraPrjNameEn){
  super.recordChanged("PRJ_NAME_EN", this.prjNameEn_, paraPrjNameEn);
  this.prjNameEn_ = paraPrjNameEn;
}


/**
 * 获取 项目备注
 *
* @return 项目备注
*/
public String getPrjMemo() {return this.prjMemo_;}
/**
* 赋值 项目备注

* @param paraPrjMemo
* 项目备注
 */

public void setPrjMemo(String paraPrjMemo){
  super.recordChanged("PRJ_MEMO", this.prjMemo_, paraPrjMemo);
  this.prjMemo_ = paraPrjMemo;
}


/**
 * 获取 项目备注英文
 *
* @return 项目备注英文
*/
public String getPrjMemoEn() {return this.prjMemoEn_;}
/**
* 赋值 项目备注英文

* @param paraPrjMemoEn
* 项目备注英文
 */

public void setPrjMemoEn(String paraPrjMemoEn){
  super.recordChanged("PRJ_MEMO_EN", this.prjMemoEn_, paraPrjMemoEn);
  this.prjMemoEn_ = paraPrjMemoEn;
}


/**
 * 获取 项目负责人
 *
* @return 项目负责人
*/
public Integer getPrjMasterId() {return this.prjMasterId_;}
/**
* 赋值 项目负责人

* @param paraPrjMasterId
* 项目负责人
 */

public void setPrjMasterId(Integer paraPrjMasterId){
  super.recordChanged("PRJ_MASTER_ID", this.prjMasterId_, paraPrjMasterId);
  this.prjMasterId_ = paraPrjMasterId;
}


/**
 * 获取 创建时间
 *
* @return 创建时间
*/
public Date getPrjCdate() {return this.prjCdate_;}
/**
* 赋值 创建时间

* @param paraPrjCdate
* 创建时间
 */

public void setPrjCdate(Date paraPrjCdate){
  super.recordChanged("PRJ_CDATE", this.prjCdate_, paraPrjCdate);
  this.prjCdate_ = paraPrjCdate;
}


/**
 * 获取 修改时间
 *
* @return 修改时间
*/
public Date getPrjMdate() {return this.prjMdate_;}
/**
* 赋值 修改时间

* @param paraPrjMdate
* 修改时间
 */

public void setPrjMdate(Date paraPrjMdate){
  super.recordChanged("PRJ_MDATE", this.prjMdate_, paraPrjMdate);
  this.prjMdate_ = paraPrjMdate;
}


/**
 * 获取 UNID
 *
* @return UNID
*/
public String getPrjUnid() {return this.prjUnid_;}
/**
* 赋值 UNID

* @param paraPrjUnid
* UNID
 */

public void setPrjUnid(String paraPrjUnid){
  super.recordChanged("PRJ_UNID", this.prjUnid_, paraPrjUnid);
  this.prjUnid_ = paraPrjUnid;
}


/**
 * 获取 商户
 *
* @return 商户
*/
public Integer getSupId() {return this.supId_;}
/**
* 赋值 商户

* @param paraSupId
* 商户
 */

public void setSupId(Integer paraSupId){
  super.recordChanged("SUP_ID", this.supId_, paraSupId);
  this.supId_ = paraSupId;
}


/**
 * 获取 创建人
 *
* @return 创建人
*/
public Integer getAdmId() {return this.admId_;}
/**
* 赋值 创建人

* @param paraAdmId
* 创建人
 */

public void setAdmId(Integer paraAdmId){
  super.recordChanged("ADM_ID", this.admId_, paraAdmId);
  this.admId_ = paraAdmId;
}


/**
 * 获取 状态
 *
* @return 状态
*/
public String getPrjStatus() {return this.prjStatus_;}
/**
* 赋值 状态

* @param paraPrjStatus
* 状态
 */

public void setPrjStatus(String paraPrjStatus){
  super.recordChanged("PRJ_STATUS", this.prjStatus_, paraPrjStatus);
  this.prjStatus_ = paraPrjStatus;
}


/**
 * 获取 参与人名单，分割
 *
* @return 参与人名单，分割
*/
public String getPrjAdm() {return this.prjAdm_;}
/**
* 赋值 参与人名单，分割

* @param paraPrjAdm
* 参与人名单，分割
 */

public void setPrjAdm(String paraPrjAdm){
  super.recordChanged("PRJ_ADM", this.prjAdm_, paraPrjAdm);
  this.prjAdm_ = paraPrjAdm;
}


/**
 * 获取 项目来源表
 *
* @return 项目来源表
*/
public String getPrjRefTable() {return this.prjRefTable_;}
/**
* 赋值 项目来源表

* @param paraPrjRefTable
* 项目来源表
 */

public void setPrjRefTable(String paraPrjRefTable){
  super.recordChanged("PRJ_REF_TABLE", this.prjRefTable_, paraPrjRefTable);
  this.prjRefTable_ = paraPrjRefTable;
}


/**
 * 获取 项目来源ID
 *
* @return 项目来源ID
*/
public String getPrjRefId() {return this.prjRefId_;}
/**
* 赋值 项目来源ID

* @param paraPrjRefId
* 项目来源ID
 */

public void setPrjRefId(String paraPrjRefId){
  super.recordChanged("PRJ_REF_ID", this.prjRefId_, paraPrjRefId);
  this.prjRefId_ = paraPrjRefId;
}


/**
 * 获取 是否为模板
 *
* @return 是否为模板
*/
public String getPrjIsTemplate() {return this.prjIsTemplate_;}
/**
* 赋值 是否为模板

* @param paraPrjIsTemplate
* 是否为模板
 */

public void setPrjIsTemplate(String paraPrjIsTemplate){
  super.recordChanged("PRJ_IS_TEMPLATE", this.prjIsTemplate_, paraPrjIsTemplate);
  this.prjIsTemplate_ = paraPrjIsTemplate;
}


/**
 * 获取 项目启动时间
 *
* @return 项目启动时间
*/
public Date getPrjStartTime() {return this.prjStartTime_;}
/**
* 赋值 项目启动时间

* @param paraPrjStartTime
* 项目启动时间
 */

public void setPrjStartTime(Date paraPrjStartTime){
  super.recordChanged("PRJ_START_TIME", this.prjStartTime_, paraPrjStartTime);
  this.prjStartTime_ = paraPrjStartTime;
}


/**
 * 获取 项目结束时间
 *
* @return 项目结束时间
*/
public Date getRrjEndTime() {return this.rrjEndTime_;}
/**
* 赋值 项目结束时间

* @param paraRrjEndTime
* 项目结束时间
 */

public void setRrjEndTime(Date paraRrjEndTime){
  super.recordChanged("RRJ_END_TIME", this.rrjEndTime_, paraRrjEndTime);
  this.rrjEndTime_ = paraRrjEndTime;
}


/**
 * 获取 运行状态
 *
* @return 运行状态
*/
public String getPrjRunStatus() {return this.prjRunStatus_;}
/**
* 赋值 运行状态

* @param paraPrjRunStatus
* 运行状态
 */

public void setPrjRunStatus(String paraPrjRunStatus){
  super.recordChanged("PRJ_RUN_STATUS", this.prjRunStatus_, paraPrjRunStatus);
  this.prjRunStatus_ = paraPrjRunStatus;
}


/**
 * 获取 来源模板
 *
* @return 来源模板
*/
public String getPrjSrcXmlTemp() {return this.prjSrcXmlTemp_;}
/**
* 赋值 来源模板

* @param paraPrjSrcXmlTemp
* 来源模板
 */

public void setPrjSrcXmlTemp(String paraPrjSrcXmlTemp){
  super.recordChanged("PRJ_SRC_XML_TEMP", this.prjSrcXmlTemp_, paraPrjSrcXmlTemp);
  this.prjSrcXmlTemp_ = paraPrjSrcXmlTemp;
}


/**
 * 获取 来源模板的Hash
 *
* @return 来源模板的Hash
*/
public Integer getPrjSrcXmlHash() {return this.prjSrcXmlHash_;}
/**
* 赋值 来源模板的Hash

* @param paraPrjSrcXmlHash
* 来源模板的Hash
 */

public void setPrjSrcXmlHash(Integer paraPrjSrcXmlHash){
  super.recordChanged("PRJ_SRC_XML_HASH", this.prjSrcXmlHash_, paraPrjSrcXmlHash);
  this.prjSrcXmlHash_ = paraPrjSrcXmlHash;
}/**根据字段名称获取值，如果名称为空或字段未找到，返回空值
 @param filedName 字段名称
 @return 字段值*/
public Object getField(String filedName){
if(filedName == null){ return null; }
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("PRJ_ID")){return this.prjId_;}
if(n.equalsIgnoreCase("PRJ_NAME")){return this.prjName_;}
if(n.equalsIgnoreCase("PRJ_NAME_EN")){return this.prjNameEn_;}
if(n.equalsIgnoreCase("PRJ_MEMO")){return this.prjMemo_;}
if(n.equalsIgnoreCase("PRJ_MEMO_EN")){return this.prjMemoEn_;}
if(n.equalsIgnoreCase("PRJ_MASTER_ID")){return this.prjMasterId_;}
if(n.equalsIgnoreCase("PRJ_CDATE")){return this.prjCdate_;}
if(n.equalsIgnoreCase("PRJ_MDATE")){return this.prjMdate_;}
if(n.equalsIgnoreCase("PRJ_UNID")){return this.prjUnid_;}
if(n.equalsIgnoreCase("SUP_ID")){return this.supId_;}
if(n.equalsIgnoreCase("ADM_ID")){return this.admId_;}
if(n.equalsIgnoreCase("PRJ_STATUS")){return this.prjStatus_;}
if(n.equalsIgnoreCase("PRJ_ADM")){return this.prjAdm_;}
if(n.equalsIgnoreCase("PRJ_REF_TABLE")){return this.prjRefTable_;}
if(n.equalsIgnoreCase("PRJ_REF_ID")){return this.prjRefId_;}
if(n.equalsIgnoreCase("PRJ_IS_TEMPLATE")){return this.prjIsTemplate_;}
if(n.equalsIgnoreCase("PRJ_START_TIME")){return this.prjStartTime_;}
if(n.equalsIgnoreCase("RRJ_END_TIME")){return this.rrjEndTime_;}
if(n.equalsIgnoreCase("PRJ_RUN_STATUS")){return this.prjRunStatus_;}
if(n.equalsIgnoreCase("PRJ_SRC_XML_TEMP")){return this.prjSrcXmlTemp_;}
if(n.equalsIgnoreCase("PRJ_SRC_XML_HASH")){return this.prjSrcXmlHash_;}
return null;}
}