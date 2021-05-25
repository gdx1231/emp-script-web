package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表WEB_USER_FPWD映射类
* @author gdx 时间：Fri Jun 29 2018 11:39:24 GMT+0800 (中国标准时间)*/
public class WebUserFpwd extends ClassBase{private String fpUnid_; // FP_UNID
private Integer usrId_; // USR_ID
private Date fpCdate_; // 提交时间
private Date fpEdate_; // 过期时间
private Date fpUdate_; // 激活时间
private String fpValidcode_; // FP_VALIDCODE
private String fpType_; // FP_TYPE
private Integer fpInc_; // FP_INC
private String fpResult_; // FP_RESULT
private String fpLog_; // FP_LOG
private String fpIp_; // FP_IP
private String fpAgent_; // FP_AGENT

/**
* 获取 FP_UNID
*
* @return FP_UNID
*/
public String getFpUnid() {return this.fpUnid_;}
/**
* 赋值 FP_UNID

* @param paraFpUnid
* FP_UNID
*/

public void setFpUnid(String paraFpUnid){
 super.recordChanged("FP_UNID", this.fpUnid_, paraFpUnid);
 this.fpUnid_ = paraFpUnid;
}


/**
* 获取 USR_ID
*
* @return USR_ID
*/
public Integer getUsrId() {return this.usrId_;}
/**
* 赋值 USR_ID

* @param paraUsrId
* USR_ID
*/

public void setUsrId(Integer paraUsrId){
 super.recordChanged("USR_ID", this.usrId_, paraUsrId);
 this.usrId_ = paraUsrId;
}


/**
* 获取 提交时间
*
* @return 提交时间
*/
public Date getFpCdate() {return this.fpCdate_;}
/**
* 赋值 提交时间

* @param paraFpCdate
* 提交时间
*/

public void setFpCdate(Date paraFpCdate){
 super.recordChanged("FP_CDATE", this.fpCdate_, paraFpCdate);
 this.fpCdate_ = paraFpCdate;
}


/**
* 获取 过期时间
*
* @return 过期时间
*/
public Date getFpEdate() {return this.fpEdate_;}
/**
* 赋值 过期时间

* @param paraFpEdate
* 过期时间
*/

public void setFpEdate(Date paraFpEdate){
 super.recordChanged("FP_EDATE", this.fpEdate_, paraFpEdate);
 this.fpEdate_ = paraFpEdate;
}


/**
* 获取 激活时间
*
* @return 激活时间
*/
public Date getFpUdate() {return this.fpUdate_;}
/**
* 赋值 激活时间

* @param paraFpUdate
* 激活时间
*/

public void setFpUdate(Date paraFpUdate){
 super.recordChanged("FP_UDATE", this.fpUdate_, paraFpUdate);
 this.fpUdate_ = paraFpUdate;
}


/**
* 获取 FP_VALIDCODE
*
* @return FP_VALIDCODE
*/
public String getFpValidcode() {return this.fpValidcode_;}
/**
* 赋值 FP_VALIDCODE

* @param paraFpValidcode
* FP_VALIDCODE
*/

public void setFpValidcode(String paraFpValidcode){
 super.recordChanged("FP_VALIDCODE", this.fpValidcode_, paraFpValidcode);
 this.fpValidcode_ = paraFpValidcode;
}


/**
* 获取 FP_TYPE
*
* @return FP_TYPE
*/
public String getFpType() {return this.fpType_;}
/**
* 赋值 FP_TYPE

* @param paraFpType
* FP_TYPE
*/

public void setFpType(String paraFpType){
 super.recordChanged("FP_TYPE", this.fpType_, paraFpType);
 this.fpType_ = paraFpType;
}


/**
* 获取 FP_INC
*
* @return FP_INC
*/
public Integer getFpInc() {return this.fpInc_;}
/**
* 赋值 FP_INC

* @param paraFpInc
* FP_INC
*/

public void setFpInc(Integer paraFpInc){
 super.recordChanged("FP_INC", this.fpInc_, paraFpInc);
 this.fpInc_ = paraFpInc;
}


/**
* 获取 FP_RESULT
*
* @return FP_RESULT
*/
public String getFpResult() {return this.fpResult_;}
/**
* 赋值 FP_RESULT

* @param paraFpResult
* FP_RESULT
*/

public void setFpResult(String paraFpResult){
 super.recordChanged("FP_RESULT", this.fpResult_, paraFpResult);
 this.fpResult_ = paraFpResult;
}


/**
* 获取 FP_LOG
*
* @return FP_LOG
*/
public String getFpLog() {return this.fpLog_;}
/**
* 赋值 FP_LOG

* @param paraFpLog
* FP_LOG
*/

public void setFpLog(String paraFpLog){
 super.recordChanged("FP_LOG", this.fpLog_, paraFpLog);
 this.fpLog_ = paraFpLog;
}


/**
* 获取 FP_IP
*
* @return FP_IP
*/
public String getFpIp() {return this.fpIp_;}
/**
* 赋值 FP_IP

* @param paraFpIp
* FP_IP
*/

public void setFpIp(String paraFpIp){
 super.recordChanged("FP_IP", this.fpIp_, paraFpIp);
 this.fpIp_ = paraFpIp;
}


/**
* 获取 FP_AGENT
*
* @return FP_AGENT
*/
public String getFpAgent() {return this.fpAgent_;}
/**
* 赋值 FP_AGENT

* @param paraFpAgent
* FP_AGENT
*/

public void setFpAgent(String paraFpAgent){
 super.recordChanged("FP_AGENT", this.fpAgent_, paraFpAgent);
 this.fpAgent_ = paraFpAgent;
}/**根据字段名称获取值，如果名称为空或字段未找到，返回空值
@param filedName 字段名称
@return 字段值*/
public Object getField(String filedName){
if(filedName == null){ return null; }
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("FP_UNID")){return this.fpUnid_;}
if(n.equalsIgnoreCase("USR_ID")){return this.usrId_;}
if(n.equalsIgnoreCase("FP_CDATE")){return this.fpCdate_;}
if(n.equalsIgnoreCase("FP_EDATE")){return this.fpEdate_;}
if(n.equalsIgnoreCase("FP_UDATE")){return this.fpUdate_;}
if(n.equalsIgnoreCase("FP_VALIDCODE")){return this.fpValidcode_;}
if(n.equalsIgnoreCase("FP_TYPE")){return this.fpType_;}
if(n.equalsIgnoreCase("FP_INC")){return this.fpInc_;}
if(n.equalsIgnoreCase("FP_RESULT")){return this.fpResult_;}
if(n.equalsIgnoreCase("FP_LOG")){return this.fpLog_;}
if(n.equalsIgnoreCase("FP_IP")){return this.fpIp_;}
if(n.equalsIgnoreCase("FP_AGENT")){return this.fpAgent_;}
return null;}
}