package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表LOG_MAIN映射类
* @author gdx 时间：Thu Feb 14 2019 16:17:28 GMT+0800 (中国标准时间)*/
public class LogMain extends ClassBase{private Integer logId_; // 编号
private String logSrc_; // 来源
private String logSrcId0_; // 来源编号0
private String logSrcId1_; // 来源编号1
private String logType_; // 类型
private String logLang_; // 语言
private String logMemo_; // 说明
private String logPara0_; // 参数0
private String logPara1_; // 参数1
private String logPara2_; // 参数2
private String logPara3_; // 参数3
private String logPara4_; // 参数4
private String logPara5_; // 参数5
private String logPara6_; // 参数6
private String logPara7_; // 参数7
private String logPara8_; // 参数8
private String logPara9_; // 参数9
private String logXmlname_; // 配置XMLNAME
private String logItemname_; // 配置ITEMNAME
private String logAction_; // 执行的Action
private String logIp_; // 来源IP
private String logAgent_; // 浏览器代理头
private Date logTime_; // 时间
private Integer supId_; // SUP_ID
private Integer admId_; // 操作者
private String mqMsgId_; // MQ的编号
private String mqMsg_; // MQ消息体
private String logRead_; // 已读标记
private Integer logToAdmId_; // 消息对应的用户

/**
 * 获取 编号
 *
* @return 编号
*/
public Integer getLogId() {return this.logId_;}
/**
* 赋值 编号

* @param paraLogId
* 编号
 */

public void setLogId(Integer paraLogId){
  super.recordChanged("LOG_ID", this.logId_, paraLogId);
  this.logId_ = paraLogId;
}


/**
 * 获取 来源
 *
* @return 来源
*/
public String getLogSrc() {return this.logSrc_;}
/**
* 赋值 来源

* @param paraLogSrc
* 来源
 */

public void setLogSrc(String paraLogSrc){
  super.recordChanged("LOG_SRC", this.logSrc_, paraLogSrc);
  this.logSrc_ = paraLogSrc;
}


/**
 * 获取 来源编号0
 *
* @return 来源编号0
*/
public String getLogSrcId0() {return this.logSrcId0_;}
/**
* 赋值 来源编号0

* @param paraLogSrcId0
* 来源编号0
 */

public void setLogSrcId0(String paraLogSrcId0){
  super.recordChanged("LOG_SRC_ID0", this.logSrcId0_, paraLogSrcId0);
  this.logSrcId0_ = paraLogSrcId0;
}


/**
 * 获取 来源编号1
 *
* @return 来源编号1
*/
public String getLogSrcId1() {return this.logSrcId1_;}
/**
* 赋值 来源编号1

* @param paraLogSrcId1
* 来源编号1
 */

public void setLogSrcId1(String paraLogSrcId1){
  super.recordChanged("LOG_SRC_ID1", this.logSrcId1_, paraLogSrcId1);
  this.logSrcId1_ = paraLogSrcId1;
}


/**
 * 获取 类型
 *
* @return 类型
*/
public String getLogType() {return this.logType_;}
/**
* 赋值 类型

* @param paraLogType
* 类型
 */

public void setLogType(String paraLogType){
  super.recordChanged("LOG_TYPE", this.logType_, paraLogType);
  this.logType_ = paraLogType;
}


/**
 * 获取 语言
 *
* @return 语言
*/
public String getLogLang() {return this.logLang_;}
/**
* 赋值 语言

* @param paraLogLang
* 语言
 */

public void setLogLang(String paraLogLang){
  super.recordChanged("LOG_LANG", this.logLang_, paraLogLang);
  this.logLang_ = paraLogLang;
}


/**
 * 获取 说明
 *
* @return 说明
*/
public String getLogMemo() {return this.logMemo_;}
/**
* 赋值 说明

* @param paraLogMemo
* 说明
 */

public void setLogMemo(String paraLogMemo){
  super.recordChanged("LOG_MEMO", this.logMemo_, paraLogMemo);
  this.logMemo_ = paraLogMemo;
}


/**
 * 获取 参数0
 *
* @return 参数0
*/
public String getLogPara0() {return this.logPara0_;}
/**
* 赋值 参数0

* @param paraLogPara0
* 参数0
 */

public void setLogPara0(String paraLogPara0){
  super.recordChanged("LOG_PARA0", this.logPara0_, paraLogPara0);
  this.logPara0_ = paraLogPara0;
}


/**
 * 获取 参数1
 *
* @return 参数1
*/
public String getLogPara1() {return this.logPara1_;}
/**
* 赋值 参数1

* @param paraLogPara1
* 参数1
 */

public void setLogPara1(String paraLogPara1){
  super.recordChanged("LOG_PARA1", this.logPara1_, paraLogPara1);
  this.logPara1_ = paraLogPara1;
}


/**
 * 获取 参数2
 *
* @return 参数2
*/
public String getLogPara2() {return this.logPara2_;}
/**
* 赋值 参数2

* @param paraLogPara2
* 参数2
 */

public void setLogPara2(String paraLogPara2){
  super.recordChanged("LOG_PARA2", this.logPara2_, paraLogPara2);
  this.logPara2_ = paraLogPara2;
}


/**
 * 获取 参数3
 *
* @return 参数3
*/
public String getLogPara3() {return this.logPara3_;}
/**
* 赋值 参数3

* @param paraLogPara3
* 参数3
 */

public void setLogPara3(String paraLogPara3){
  super.recordChanged("LOG_PARA3", this.logPara3_, paraLogPara3);
  this.logPara3_ = paraLogPara3;
}


/**
 * 获取 参数4
 *
* @return 参数4
*/
public String getLogPara4() {return this.logPara4_;}
/**
* 赋值 参数4

* @param paraLogPara4
* 参数4
 */

public void setLogPara4(String paraLogPara4){
  super.recordChanged("LOG_PARA4", this.logPara4_, paraLogPara4);
  this.logPara4_ = paraLogPara4;
}


/**
 * 获取 参数5
 *
* @return 参数5
*/
public String getLogPara5() {return this.logPara5_;}
/**
* 赋值 参数5

* @param paraLogPara5
* 参数5
 */

public void setLogPara5(String paraLogPara5){
  super.recordChanged("LOG_PARA5", this.logPara5_, paraLogPara5);
  this.logPara5_ = paraLogPara5;
}


/**
 * 获取 参数6
 *
* @return 参数6
*/
public String getLogPara6() {return this.logPara6_;}
/**
* 赋值 参数6

* @param paraLogPara6
* 参数6
 */

public void setLogPara6(String paraLogPara6){
  super.recordChanged("LOG_PARA6", this.logPara6_, paraLogPara6);
  this.logPara6_ = paraLogPara6;
}


/**
 * 获取 参数7
 *
* @return 参数7
*/
public String getLogPara7() {return this.logPara7_;}
/**
* 赋值 参数7

* @param paraLogPara7
* 参数7
 */

public void setLogPara7(String paraLogPara7){
  super.recordChanged("LOG_PARA7", this.logPara7_, paraLogPara7);
  this.logPara7_ = paraLogPara7;
}


/**
 * 获取 参数8
 *
* @return 参数8
*/
public String getLogPara8() {return this.logPara8_;}
/**
* 赋值 参数8

* @param paraLogPara8
* 参数8
 */

public void setLogPara8(String paraLogPara8){
  super.recordChanged("LOG_PARA8", this.logPara8_, paraLogPara8);
  this.logPara8_ = paraLogPara8;
}


/**
 * 获取 参数9
 *
* @return 参数9
*/
public String getLogPara9() {return this.logPara9_;}
/**
* 赋值 参数9

* @param paraLogPara9
* 参数9
 */

public void setLogPara9(String paraLogPara9){
  super.recordChanged("LOG_PARA9", this.logPara9_, paraLogPara9);
  this.logPara9_ = paraLogPara9;
}


/**
 * 获取 配置XMLNAME
 *
* @return 配置XMLNAME
*/
public String getLogXmlname() {return this.logXmlname_;}
/**
* 赋值 配置XMLNAME

* @param paraLogXmlname
* 配置XMLNAME
 */

public void setLogXmlname(String paraLogXmlname){
  super.recordChanged("LOG_XMLNAME", this.logXmlname_, paraLogXmlname);
  this.logXmlname_ = paraLogXmlname;
}


/**
 * 获取 配置ITEMNAME
 *
* @return 配置ITEMNAME
*/
public String getLogItemname() {return this.logItemname_;}
/**
* 赋值 配置ITEMNAME

* @param paraLogItemname
* 配置ITEMNAME
 */

public void setLogItemname(String paraLogItemname){
  super.recordChanged("LOG_ITEMNAME", this.logItemname_, paraLogItemname);
  this.logItemname_ = paraLogItemname;
}


/**
 * 获取 执行的Action
 *
* @return 执行的Action
*/
public String getLogAction() {return this.logAction_;}
/**
* 赋值 执行的Action

* @param paraLogAction
* 执行的Action
 */

public void setLogAction(String paraLogAction){
  super.recordChanged("LOG_ACTION", this.logAction_, paraLogAction);
  this.logAction_ = paraLogAction;
}


/**
 * 获取 来源IP
 *
* @return 来源IP
*/
public String getLogIp() {return this.logIp_;}
/**
* 赋值 来源IP

* @param paraLogIp
* 来源IP
 */

public void setLogIp(String paraLogIp){
  super.recordChanged("LOG_IP", this.logIp_, paraLogIp);
  this.logIp_ = paraLogIp;
}


/**
 * 获取 浏览器代理头
 *
* @return 浏览器代理头
*/
public String getLogAgent() {return this.logAgent_;}
/**
* 赋值 浏览器代理头

* @param paraLogAgent
* 浏览器代理头
 */

public void setLogAgent(String paraLogAgent){
  super.recordChanged("LOG_AGENT", this.logAgent_, paraLogAgent);
  this.logAgent_ = paraLogAgent;
}


/**
 * 获取 时间
 *
* @return 时间
*/
public Date getLogTime() {return this.logTime_;}
/**
* 赋值 时间

* @param paraLogTime
* 时间
 */

public void setLogTime(Date paraLogTime){
  super.recordChanged("LOG_TIME", this.logTime_, paraLogTime);
  this.logTime_ = paraLogTime;
}


/**
 * 获取 SUP_ID
 *
* @return SUP_ID
*/
public Integer getSupId() {return this.supId_;}
/**
* 赋值 SUP_ID

* @param paraSupId
* SUP_ID
 */

public void setSupId(Integer paraSupId){
  super.recordChanged("SUP_ID", this.supId_, paraSupId);
  this.supId_ = paraSupId;
}


/**
 * 获取 操作者
 *
* @return 操作者
*/
public Integer getAdmId() {return this.admId_;}
/**
* 赋值 操作者

* @param paraAdmId
* 操作者
 */

public void setAdmId(Integer paraAdmId){
  super.recordChanged("ADM_ID", this.admId_, paraAdmId);
  this.admId_ = paraAdmId;
}


/**
 * 获取 MQ的编号
 *
* @return MQ的编号
*/
public String getMqMsgId() {return this.mqMsgId_;}
/**
* 赋值 MQ的编号

* @param paraMqMsgId
* MQ的编号
 */

public void setMqMsgId(String paraMqMsgId){
  super.recordChanged("MQ_MSG_ID", this.mqMsgId_, paraMqMsgId);
  this.mqMsgId_ = paraMqMsgId;
}


/**
 * 获取 MQ消息体
 *
* @return MQ消息体
*/
public String getMqMsg() {return this.mqMsg_;}
/**
* 赋值 MQ消息体

* @param paraMqMsg
* MQ消息体
 */

public void setMqMsg(String paraMqMsg){
  super.recordChanged("MQ_MSG", this.mqMsg_, paraMqMsg);
  this.mqMsg_ = paraMqMsg;
}


/**
 * 获取 已读标记
 *
* @return 已读标记
*/
public String getLogRead() {return this.logRead_;}
/**
* 赋值 已读标记

* @param paraLogRead
* 已读标记
 */

public void setLogRead(String paraLogRead){
  super.recordChanged("LOG_READ", this.logRead_, paraLogRead);
  this.logRead_ = paraLogRead;
}


/**
 * 获取 消息对应的用户
 *
* @return 消息对应的用户
*/
public Integer getLogToAdmId() {return this.logToAdmId_;}
/**
* 赋值 消息对应的用户

* @param paraLogToAdmId
* 消息对应的用户
 */

public void setLogToAdmId(Integer paraLogToAdmId){
  super.recordChanged("LOG_TO_ADM_ID", this.logToAdmId_, paraLogToAdmId);
  this.logToAdmId_ = paraLogToAdmId;
}/**根据字段名称获取值，如果名称为空或字段未找到，返回空值
 @param filedName 字段名称
 @return 字段值*/
public Object getField(String filedName){
if(filedName == null){ return null; }
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("LOG_ID")){return this.logId_;}
if(n.equalsIgnoreCase("LOG_SRC")){return this.logSrc_;}
if(n.equalsIgnoreCase("LOG_SRC_ID0")){return this.logSrcId0_;}
if(n.equalsIgnoreCase("LOG_SRC_ID1")){return this.logSrcId1_;}
if(n.equalsIgnoreCase("LOG_TYPE")){return this.logType_;}
if(n.equalsIgnoreCase("LOG_LANG")){return this.logLang_;}
if(n.equalsIgnoreCase("LOG_MEMO")){return this.logMemo_;}
if(n.equalsIgnoreCase("LOG_PARA0")){return this.logPara0_;}
if(n.equalsIgnoreCase("LOG_PARA1")){return this.logPara1_;}
if(n.equalsIgnoreCase("LOG_PARA2")){return this.logPara2_;}
if(n.equalsIgnoreCase("LOG_PARA3")){return this.logPara3_;}
if(n.equalsIgnoreCase("LOG_PARA4")){return this.logPara4_;}
if(n.equalsIgnoreCase("LOG_PARA5")){return this.logPara5_;}
if(n.equalsIgnoreCase("LOG_PARA6")){return this.logPara6_;}
if(n.equalsIgnoreCase("LOG_PARA7")){return this.logPara7_;}
if(n.equalsIgnoreCase("LOG_PARA8")){return this.logPara8_;}
if(n.equalsIgnoreCase("LOG_PARA9")){return this.logPara9_;}
if(n.equalsIgnoreCase("LOG_XMLNAME")){return this.logXmlname_;}
if(n.equalsIgnoreCase("LOG_ITEMNAME")){return this.logItemname_;}
if(n.equalsIgnoreCase("LOG_ACTION")){return this.logAction_;}
if(n.equalsIgnoreCase("LOG_IP")){return this.logIp_;}
if(n.equalsIgnoreCase("LOG_AGENT")){return this.logAgent_;}
if(n.equalsIgnoreCase("LOG_TIME")){return this.logTime_;}
if(n.equalsIgnoreCase("SUP_ID")){return this.supId_;}
if(n.equalsIgnoreCase("ADM_ID")){return this.admId_;}
if(n.equalsIgnoreCase("MQ_MSG_ID")){return this.mqMsgId_;}
if(n.equalsIgnoreCase("MQ_MSG")){return this.mqMsg_;}
if(n.equalsIgnoreCase("LOG_READ")){return this.logRead_;}
if(n.equalsIgnoreCase("LOG_TO_ADM_ID")){return this.logToAdmId_;}
return null;}
}