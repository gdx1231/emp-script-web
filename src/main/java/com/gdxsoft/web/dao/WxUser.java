package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表wx_user映射类
* @author gdx 时间：Wed Jul 08 2020 14:45:44 GMT+0800 (中国标准时间)*/
public class WxUser extends ClassBase{private String wxCfgNo_; // WX_CFG_NO
private String authWeixinId_; // AUTH_WEIXIN_ID
private String usrUnid_; // USR_UNID
private String isWeixinSubscribe_; // 是否关注微信号
private String authWeixinJson_; // AUTH_WEIXIN_JSON
private Date cdate_; // CDATE
private Date mdate_; // MDATE
private Integer supId_; // SUP_ID
private Integer wxGrp_; // 微信组
private String bindStatus_; // 绑定状态
private Integer bindUsrId_; // 绑定的用户ID
private Integer bindRemoveUsrId_; // 去除的用户ID
private String bindRemoveUsrUnid_; // 去除用户当前的UNID
private Date bindDate_; // 绑定日期
private String bindIp_; // 绑定IP
private String bindJson_; // 绑定的JSON
private String userAgent_; // USER_AGENT
private String wxUnionId_; // 微信的 UNIONID

/**
 * 获取 WX_CFG_NO
 *
* @return WX_CFG_NO
*/
public String getWxCfgNo() {return this.wxCfgNo_;}
/**
* 赋值 WX_CFG_NO

* @param paraWxCfgNo
* WX_CFG_NO
 */

public void setWxCfgNo(String paraWxCfgNo){
  super.recordChanged("WX_CFG_NO", this.wxCfgNo_, paraWxCfgNo);
  this.wxCfgNo_ = paraWxCfgNo;
}


/**
 * 获取 AUTH_WEIXIN_ID
 *
* @return AUTH_WEIXIN_ID
*/
public String getAuthWeixinId() {return this.authWeixinId_;}
/**
* 赋值 AUTH_WEIXIN_ID

* @param paraAuthWeixinId
* AUTH_WEIXIN_ID
 */

public void setAuthWeixinId(String paraAuthWeixinId){
  super.recordChanged("AUTH_WEIXIN_ID", this.authWeixinId_, paraAuthWeixinId);
  this.authWeixinId_ = paraAuthWeixinId;
}


/**
 * 获取 USR_UNID
 *
* @return USR_UNID
*/
public String getUsrUnid() {return this.usrUnid_;}
/**
* 赋值 USR_UNID

* @param paraUsrUnid
* USR_UNID
 */

public void setUsrUnid(String paraUsrUnid){
  super.recordChanged("USR_UNID", this.usrUnid_, paraUsrUnid);
  this.usrUnid_ = paraUsrUnid;
}


/**
 * 获取 是否关注微信号
 *
* @return 是否关注微信号
*/
public String getIsWeixinSubscribe() {return this.isWeixinSubscribe_;}
/**
* 赋值 是否关注微信号

* @param paraIsWeixinSubscribe
* 是否关注微信号
 */

public void setIsWeixinSubscribe(String paraIsWeixinSubscribe){
  super.recordChanged("IS_WEIXIN_SUBSCRIBE", this.isWeixinSubscribe_, paraIsWeixinSubscribe);
  this.isWeixinSubscribe_ = paraIsWeixinSubscribe;
}


/**
 * 获取 AUTH_WEIXIN_JSON
 *
* @return AUTH_WEIXIN_JSON
*/
public String getAuthWeixinJson() {return this.authWeixinJson_;}
/**
* 赋值 AUTH_WEIXIN_JSON

* @param paraAuthWeixinJson
* AUTH_WEIXIN_JSON
 */

public void setAuthWeixinJson(String paraAuthWeixinJson){
  super.recordChanged("AUTH_WEIXIN_JSON", this.authWeixinJson_, paraAuthWeixinJson);
  this.authWeixinJson_ = paraAuthWeixinJson;
}


/**
 * 获取 CDATE
 *
* @return CDATE
*/
public Date getCdate() {return this.cdate_;}
/**
* 赋值 CDATE

* @param paraCdate
* CDATE
 */

public void setCdate(Date paraCdate){
  super.recordChanged("CDATE", this.cdate_, paraCdate);
  this.cdate_ = paraCdate;
}


/**
 * 获取 MDATE
 *
* @return MDATE
*/
public Date getMdate() {return this.mdate_;}
/**
* 赋值 MDATE

* @param paraMdate
* MDATE
 */

public void setMdate(Date paraMdate){
  super.recordChanged("MDATE", this.mdate_, paraMdate);
  this.mdate_ = paraMdate;
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
 * 获取 微信组
 *
* @return 微信组
*/
public Integer getWxGrp() {return this.wxGrp_;}
/**
* 赋值 微信组

* @param paraWxGrp
* 微信组
 */

public void setWxGrp(Integer paraWxGrp){
  super.recordChanged("WX_GRP", this.wxGrp_, paraWxGrp);
  this.wxGrp_ = paraWxGrp;
}


/**
 * 获取 绑定状态
 *
* @return 绑定状态
*/
public String getBindStatus() {return this.bindStatus_;}
/**
* 赋值 绑定状态

* @param paraBindStatus
* 绑定状态
 */

public void setBindStatus(String paraBindStatus){
  super.recordChanged("BIND_STATUS", this.bindStatus_, paraBindStatus);
  this.bindStatus_ = paraBindStatus;
}


/**
 * 获取 绑定的用户ID
 *
* @return 绑定的用户ID
*/
public Integer getBindUsrId() {return this.bindUsrId_;}
/**
* 赋值 绑定的用户ID

* @param paraBindUsrId
* 绑定的用户ID
 */

public void setBindUsrId(Integer paraBindUsrId){
  super.recordChanged("BIND_USR_ID", this.bindUsrId_, paraBindUsrId);
  this.bindUsrId_ = paraBindUsrId;
}


/**
 * 获取 去除的用户ID
 *
* @return 去除的用户ID
*/
public Integer getBindRemoveUsrId() {return this.bindRemoveUsrId_;}
/**
* 赋值 去除的用户ID

* @param paraBindRemoveUsrId
* 去除的用户ID
 */

public void setBindRemoveUsrId(Integer paraBindRemoveUsrId){
  super.recordChanged("BIND_REMOVE_USR_ID", this.bindRemoveUsrId_, paraBindRemoveUsrId);
  this.bindRemoveUsrId_ = paraBindRemoveUsrId;
}


/**
 * 获取 去除用户当前的UNID
 *
* @return 去除用户当前的UNID
*/
public String getBindRemoveUsrUnid() {return this.bindRemoveUsrUnid_;}
/**
* 赋值 去除用户当前的UNID

* @param paraBindRemoveUsrUnid
* 去除用户当前的UNID
 */

public void setBindRemoveUsrUnid(String paraBindRemoveUsrUnid){
  super.recordChanged("BIND_REMOVE_USR_UNID", this.bindRemoveUsrUnid_, paraBindRemoveUsrUnid);
  this.bindRemoveUsrUnid_ = paraBindRemoveUsrUnid;
}


/**
 * 获取 绑定日期
 *
* @return 绑定日期
*/
public Date getBindDate() {return this.bindDate_;}
/**
* 赋值 绑定日期

* @param paraBindDate
* 绑定日期
 */

public void setBindDate(Date paraBindDate){
  super.recordChanged("BIND_DATE", this.bindDate_, paraBindDate);
  this.bindDate_ = paraBindDate;
}


/**
 * 获取 绑定IP
 *
* @return 绑定IP
*/
public String getBindIp() {return this.bindIp_;}
/**
* 赋值 绑定IP

* @param paraBindIp
* 绑定IP
 */

public void setBindIp(String paraBindIp){
  super.recordChanged("BIND_IP", this.bindIp_, paraBindIp);
  this.bindIp_ = paraBindIp;
}


/**
 * 获取 绑定的JSON
 *
* @return 绑定的JSON
*/
public String getBindJson() {return this.bindJson_;}
/**
* 赋值 绑定的JSON

* @param paraBindJson
* 绑定的JSON
 */

public void setBindJson(String paraBindJson){
  super.recordChanged("BIND_JSON", this.bindJson_, paraBindJson);
  this.bindJson_ = paraBindJson;
}


/**
 * 获取 USER_AGENT
 *
* @return USER_AGENT
*/
public String getUserAgent() {return this.userAgent_;}
/**
* 赋值 USER_AGENT

* @param paraUserAgent
* USER_AGENT
 */

public void setUserAgent(String paraUserAgent){
  super.recordChanged("USER_AGENT", this.userAgent_, paraUserAgent);
  this.userAgent_ = paraUserAgent;
}


/**
 * 获取 微信的 UNIONID
 *
* @return 微信的 UNIONID
*/
public String getWxUnionId() {return this.wxUnionId_;}
/**
* 赋值 微信的 UNIONID

* @param paraWxUnionId
* 微信的 UNIONID
 */

public void setWxUnionId(String paraWxUnionId){
  super.recordChanged("WX_UNION_ID", this.wxUnionId_, paraWxUnionId);
  this.wxUnionId_ = paraWxUnionId;
}/**根据字段名称获取值，如果名称为空或字段未找到，返回空值
 @param filedName 字段名称
 @return 字段值*/
public Object getField(String filedName){
if(filedName == null){ return null; }
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("WX_CFG_NO")){return this.wxCfgNo_;}
if(n.equalsIgnoreCase("AUTH_WEIXIN_ID")){return this.authWeixinId_;}
if(n.equalsIgnoreCase("USR_UNID")){return this.usrUnid_;}
if(n.equalsIgnoreCase("IS_WEIXIN_SUBSCRIBE")){return this.isWeixinSubscribe_;}
if(n.equalsIgnoreCase("AUTH_WEIXIN_JSON")){return this.authWeixinJson_;}
if(n.equalsIgnoreCase("CDATE")){return this.cdate_;}
if(n.equalsIgnoreCase("MDATE")){return this.mdate_;}
if(n.equalsIgnoreCase("SUP_ID")){return this.supId_;}
if(n.equalsIgnoreCase("WX_GRP")){return this.wxGrp_;}
if(n.equalsIgnoreCase("BIND_STATUS")){return this.bindStatus_;}
if(n.equalsIgnoreCase("BIND_USR_ID")){return this.bindUsrId_;}
if(n.equalsIgnoreCase("BIND_REMOVE_USR_ID")){return this.bindRemoveUsrId_;}
if(n.equalsIgnoreCase("BIND_REMOVE_USR_UNID")){return this.bindRemoveUsrUnid_;}
if(n.equalsIgnoreCase("BIND_DATE")){return this.bindDate_;}
if(n.equalsIgnoreCase("BIND_IP")){return this.bindIp_;}
if(n.equalsIgnoreCase("BIND_JSON")){return this.bindJson_;}
if(n.equalsIgnoreCase("USER_AGENT")){return this.userAgent_;}
if(n.equalsIgnoreCase("WX_UNION_ID")){return this.wxUnionId_;}
return null;}
}