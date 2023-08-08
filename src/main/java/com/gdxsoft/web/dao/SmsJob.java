package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表SMS_JOB映射类
* @author gdx 时间：Wed Feb 13 2019 15:29:20 GMT+0800 (中国标准时间)*/
public class SmsJob extends ClassBase{private Integer smsJid_; // 编号
private String smsJtitle_; // 标题
private String smsJcnt_; // 内容
private Date smsJcdate_; // 创建时间
private Date smsJmdate_; // 修改时间
private String smsJstatus_; // 状态
private String smsPhones_; // 电话号码
private Integer admId_; // 管理员
private Integer supId_; // 供应商
private String smsRefTable_; // 来源表
private String smsRefId_; // 来源ID
private String smsProvider_; // 短信供应商
private String smsTemplateCode_; // 短信模板编号
private String smsSignName_; // 短信签名
private String smsTemplateJson_; // 模板数据JSON
private String smsOutId_; // 短信发出ID
private String mqMsgId_; // RocketMQ Id
private String mqMsg_; // RocketMQ 原始信息

/**
 * 获取 编号
 *
* @return 编号
*/
public Integer getSmsJid() {return this.smsJid_;}
/**
* 赋值 编号

* @param paraSmsJid
* 编号
 */

public void setSmsJid(Integer paraSmsJid){
  super.recordChanged("SMS_JID", this.smsJid_, paraSmsJid);
  this.smsJid_ = paraSmsJid;
}


/**
 * 获取 标题
 *
* @return 标题
*/
public String getSmsJtitle() {return this.smsJtitle_;}
/**
* 赋值 标题

* @param paraSmsJtitle
* 标题
 */

public void setSmsJtitle(String paraSmsJtitle){
  super.recordChanged("SMS_JTITLE", this.smsJtitle_, paraSmsJtitle);
  this.smsJtitle_ = paraSmsJtitle;
}


/**
 * 获取 内容
 *
* @return 内容
*/
public String getSmsJcnt() {return this.smsJcnt_;}
/**
* 赋值 内容

* @param paraSmsJcnt
* 内容
 */

public void setSmsJcnt(String paraSmsJcnt){
  super.recordChanged("SMS_JCNT", this.smsJcnt_, paraSmsJcnt);
  this.smsJcnt_ = paraSmsJcnt;
}


/**
 * 获取 创建时间
 *
* @return 创建时间
*/
public Date getSmsJcdate() {return this.smsJcdate_;}
/**
* 赋值 创建时间

* @param paraSmsJcdate
* 创建时间
 */

public void setSmsJcdate(Date paraSmsJcdate){
  super.recordChanged("SMS_JCDATE", this.smsJcdate_, paraSmsJcdate);
  this.smsJcdate_ = paraSmsJcdate;
}


/**
 * 获取 修改时间
 *
* @return 修改时间
*/
public Date getSmsJmdate() {return this.smsJmdate_;}
/**
* 赋值 修改时间

* @param paraSmsJmdate
* 修改时间
 */

public void setSmsJmdate(Date paraSmsJmdate){
  super.recordChanged("SMS_JMDATE", this.smsJmdate_, paraSmsJmdate);
  this.smsJmdate_ = paraSmsJmdate;
}


/**
 * 获取 状态
 *
* @return 状态
*/
public String getSmsJstatus() {return this.smsJstatus_;}
/**
* 赋值 状态

* @param paraSmsJstatus
* 状态
 */

public void setSmsJstatus(String paraSmsJstatus){
  super.recordChanged("SMS_JSTATUS", this.smsJstatus_, paraSmsJstatus);
  this.smsJstatus_ = paraSmsJstatus;
}


/**
 * 获取 电话号码
 *
* @return 电话号码
*/
public String getSmsPhones() {return this.smsPhones_;}
/**
* 赋值 电话号码

* @param paraSmsPhones
* 电话号码
 */

public void setSmsPhones(String paraSmsPhones){
  super.recordChanged("SMS_PHONES", this.smsPhones_, paraSmsPhones);
  this.smsPhones_ = paraSmsPhones;
}


/**
 * 获取 管理员
 *
* @return 管理员
*/
public Integer getAdmId() {return this.admId_;}
/**
* 赋值 管理员

* @param paraAdmId
* 管理员
 */

public void setAdmId(Integer paraAdmId){
  super.recordChanged("ADM_ID", this.admId_, paraAdmId);
  this.admId_ = paraAdmId;
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
 * 获取 来源表
 *
* @return 来源表
*/
public String getSmsRefTable() {return this.smsRefTable_;}
/**
* 赋值 来源表

* @param paraSmsRefTable
* 来源表
 */

public void setSmsRefTable(String paraSmsRefTable){
  super.recordChanged("SMS_REF_TABLE", this.smsRefTable_, paraSmsRefTable);
  this.smsRefTable_ = paraSmsRefTable;
}


/**
 * 获取 来源ID
 *
* @return 来源ID
*/
public String getSmsRefId() {return this.smsRefId_;}
/**
* 赋值 来源ID

* @param paraSmsRefId
* 来源ID
 */

public void setSmsRefId(String paraSmsRefId){
  super.recordChanged("SMS_REF_ID", this.smsRefId_, paraSmsRefId);
  this.smsRefId_ = paraSmsRefId;
}


/**
 * 获取 短信供应商
 *
* @return 短信供应商
*/
public String getSmsProvider() {return this.smsProvider_;}
/**
* 赋值 短信供应商

* @param paraSmsProvider
* 短信供应商
 */

public void setSmsProvider(String paraSmsProvider){
  super.recordChanged("SMS_PROVIDER", this.smsProvider_, paraSmsProvider);
  this.smsProvider_ = paraSmsProvider;
}


/**
 * 获取 短信模板编号
 *
* @return 短信模板编号
*/
public String getSmsTemplateCode() {return this.smsTemplateCode_;}
/**
* 赋值 短信模板编号

* @param paraSmsTemplateCode
* 短信模板编号
 */

public void setSmsTemplateCode(String paraSmsTemplateCode){
  super.recordChanged("SMS_TEMPLATE_CODE", this.smsTemplateCode_, paraSmsTemplateCode);
  this.smsTemplateCode_ = paraSmsTemplateCode;
}


/**
 * 获取 短信签名
 *
* @return 短信签名
*/
public String getSmsSignName() {return this.smsSignName_;}
/**
* 赋值 短信签名

* @param paraSmsSignName
* 短信签名
 */

public void setSmsSignName(String paraSmsSignName){
  super.recordChanged("SMS_SIGN_NAME", this.smsSignName_, paraSmsSignName);
  this.smsSignName_ = paraSmsSignName;
}


/**
 * 获取 模板数据JSON
 *
* @return 模板数据JSON
*/
public String getSmsTemplateJson() {return this.smsTemplateJson_;}
/**
* 赋值 模板数据JSON

* @param paraSmsTemplateJson
* 模板数据JSON
 */

public void setSmsTemplateJson(String paraSmsTemplateJson){
  super.recordChanged("SMS_TEMPLATE_JSON", this.smsTemplateJson_, paraSmsTemplateJson);
  this.smsTemplateJson_ = paraSmsTemplateJson;
}


/**
 * 获取 短信发出ID
 *
* @return 短信发出ID
*/
public String getSmsOutId() {return this.smsOutId_;}
/**
* 赋值 短信发出ID

* @param paraSmsOutId
* 短信发出ID
 */

public void setSmsOutId(String paraSmsOutId){
  super.recordChanged("SMS_OUT_ID", this.smsOutId_, paraSmsOutId);
  this.smsOutId_ = paraSmsOutId;
}


/**
 * 获取 RocketMQ Id
 *
* @return RocketMQ Id
*/
public String getMqMsgId() {return this.mqMsgId_;}
/**
* 赋值 RocketMQ Id

* @param paraMqMsgId
* RocketMQ Id
 */

public void setMqMsgId(String paraMqMsgId){
  super.recordChanged("MQ_MSG_ID", this.mqMsgId_, paraMqMsgId);
  this.mqMsgId_ = paraMqMsgId;
}


/**
 * 获取 RocketMQ 原始信息
 *
* @return RocketMQ 原始信息
*/
public String getMqMsg() {return this.mqMsg_;}
/**
* 赋值 RocketMQ 原始信息

* @param paraMqMsg
* RocketMQ 原始信息
 */

public void setMqMsg(String paraMqMsg){
  super.recordChanged("MQ_MSG", this.mqMsg_, paraMqMsg);
  this.mqMsg_ = paraMqMsg;
}/**根据字段名称获取值，如果名称为空或字段未找到，返回空值
 @param filedName 字段名称
 @return 字段值*/
public Object getField(String filedName){
if(filedName == null){ return null; }
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("SMS_JID")){return this.smsJid_;}
if(n.equalsIgnoreCase("SMS_JTITLE")){return this.smsJtitle_;}
if(n.equalsIgnoreCase("SMS_JCNT")){return this.smsJcnt_;}
if(n.equalsIgnoreCase("SMS_JCDATE")){return this.smsJcdate_;}
if(n.equalsIgnoreCase("SMS_JMDATE")){return this.smsJmdate_;}
if(n.equalsIgnoreCase("SMS_JSTATUS")){return this.smsJstatus_;}
if(n.equalsIgnoreCase("SMS_PHONES")){return this.smsPhones_;}
if(n.equalsIgnoreCase("ADM_ID")){return this.admId_;}
if(n.equalsIgnoreCase("SUP_ID")){return this.supId_;}
if(n.equalsIgnoreCase("SMS_REF_TABLE")){return this.smsRefTable_;}
if(n.equalsIgnoreCase("SMS_REF_ID")){return this.smsRefId_;}
if(n.equalsIgnoreCase("SMS_PROVIDER")){return this.smsProvider_;}
if(n.equalsIgnoreCase("SMS_TEMPLATE_CODE")){return this.smsTemplateCode_;}
if(n.equalsIgnoreCase("SMS_SIGN_NAME")){return this.smsSignName_;}
if(n.equalsIgnoreCase("SMS_TEMPLATE_JSON")){return this.smsTemplateJson_;}
if(n.equalsIgnoreCase("SMS_OUT_ID")){return this.smsOutId_;}
if(n.equalsIgnoreCase("MQ_MSG_ID")){return this.mqMsgId_;}
if(n.equalsIgnoreCase("MQ_MSG")){return this.mqMsg_;}
return null;}
}