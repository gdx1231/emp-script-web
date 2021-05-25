package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表web_user_level映射类
* @author gdx 时间：Wed Jul 08 2020 14:14:12 GMT+0800 (中国标准时间)*/
public class WebUserLevel extends ClassBase{private Integer usrLvlId_; // usr_lvl_id
private Integer usrId_; // 用户id
private String usrUnid_; // usr_unid
private Integer usrPid_; // 父级用户id
private String usrPunid_; // usr_punid
private Date createDate_; // create_date
private Integer supId_; // sup_id
private Integer usrLvl_; // 等级
private String usrLvlIp_; // 来源ip
private String usrLvlUa_; // 浏览器
private String usrLvlMemo_; // 备注

/**
 * 获取 usr_lvl_id
 *
* @return usr_lvl_id
*/
public Integer getUsrLvlId() {return this.usrLvlId_;}
/**
* 赋值 usr_lvl_id

* @param paraUsrLvlId
* usr_lvl_id
 */

public void setUsrLvlId(Integer paraUsrLvlId){
  super.recordChanged("usr_lvl_id", this.usrLvlId_, paraUsrLvlId);
  this.usrLvlId_ = paraUsrLvlId;
}


/**
 * 获取 用户id
 *
* @return 用户id
*/
public Integer getUsrId() {return this.usrId_;}
/**
* 赋值 用户id

* @param paraUsrId
* 用户id
 */

public void setUsrId(Integer paraUsrId){
  super.recordChanged("usr_id", this.usrId_, paraUsrId);
  this.usrId_ = paraUsrId;
}


/**
 * 获取 usr_unid
 *
* @return usr_unid
*/
public String getUsrUnid() {return this.usrUnid_;}
/**
* 赋值 usr_unid

* @param paraUsrUnid
* usr_unid
 */

public void setUsrUnid(String paraUsrUnid){
  super.recordChanged("usr_unid", this.usrUnid_, paraUsrUnid);
  this.usrUnid_ = paraUsrUnid;
}


/**
 * 获取 父级用户id
 *
* @return 父级用户id
*/
public Integer getUsrPid() {return this.usrPid_;}
/**
* 赋值 父级用户id

* @param paraUsrPid
* 父级用户id
 */

public void setUsrPid(Integer paraUsrPid){
  super.recordChanged("usr_pid", this.usrPid_, paraUsrPid);
  this.usrPid_ = paraUsrPid;
}


/**
 * 获取 usr_punid
 *
* @return usr_punid
*/
public String getUsrPunid() {return this.usrPunid_;}
/**
* 赋值 usr_punid

* @param paraUsrPunid
* usr_punid
 */

public void setUsrPunid(String paraUsrPunid){
  super.recordChanged("usr_punid", this.usrPunid_, paraUsrPunid);
  this.usrPunid_ = paraUsrPunid;
}


/**
 * 获取 create_date
 *
* @return create_date
*/
public Date getCreateDate() {return this.createDate_;}
/**
* 赋值 create_date

* @param paraCreateDate
* create_date
 */

public void setCreateDate(Date paraCreateDate){
  super.recordChanged("create_date", this.createDate_, paraCreateDate);
  this.createDate_ = paraCreateDate;
}


/**
 * 获取 sup_id
 *
* @return sup_id
*/
public Integer getSupId() {return this.supId_;}
/**
* 赋值 sup_id

* @param paraSupId
* sup_id
 */

public void setSupId(Integer paraSupId){
  super.recordChanged("sup_id", this.supId_, paraSupId);
  this.supId_ = paraSupId;
}


/**
 * 获取 等级
 *
* @return 等级
*/
public Integer getUsrLvl() {return this.usrLvl_;}
/**
* 赋值 等级

* @param paraUsrLvl
* 等级
 */

public void setUsrLvl(Integer paraUsrLvl){
  super.recordChanged("usr_lvl", this.usrLvl_, paraUsrLvl);
  this.usrLvl_ = paraUsrLvl;
}


/**
 * 获取 来源ip
 *
* @return 来源ip
*/
public String getUsrLvlIp() {return this.usrLvlIp_;}
/**
* 赋值 来源ip

* @param paraUsrLvlIp
* 来源ip
 */

public void setUsrLvlIp(String paraUsrLvlIp){
  super.recordChanged("usr_lvl_ip", this.usrLvlIp_, paraUsrLvlIp);
  this.usrLvlIp_ = paraUsrLvlIp;
}


/**
 * 获取 浏览器
 *
* @return 浏览器
*/
public String getUsrLvlUa() {return this.usrLvlUa_;}
/**
* 赋值 浏览器

* @param paraUsrLvlUa
* 浏览器
 */

public void setUsrLvlUa(String paraUsrLvlUa){
  super.recordChanged("usr_lvl_ua", this.usrLvlUa_, paraUsrLvlUa);
  this.usrLvlUa_ = paraUsrLvlUa;
}


/**
 * 获取 备注
 *
* @return 备注
*/
public String getUsrLvlMemo() {return this.usrLvlMemo_;}
/**
* 赋值 备注

* @param paraUsrLvlMemo
* 备注
 */

public void setUsrLvlMemo(String paraUsrLvlMemo){
  super.recordChanged("usr_lvl_memo", this.usrLvlMemo_, paraUsrLvlMemo);
  this.usrLvlMemo_ = paraUsrLvlMemo;
}/**根据字段名称获取值，如果名称为空或字段未找到，返回空值
 @param filedName 字段名称
 @return 字段值*/
public Object getField(String filedName){
if(filedName == null){ return null; }
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("usr_lvl_id")){return this.usrLvlId_;}
if(n.equalsIgnoreCase("usr_id")){return this.usrId_;}
if(n.equalsIgnoreCase("usr_unid")){return this.usrUnid_;}
if(n.equalsIgnoreCase("usr_pid")){return this.usrPid_;}
if(n.equalsIgnoreCase("usr_punid")){return this.usrPunid_;}
if(n.equalsIgnoreCase("create_date")){return this.createDate_;}
if(n.equalsIgnoreCase("sup_id")){return this.supId_;}
if(n.equalsIgnoreCase("usr_lvl")){return this.usrLvl_;}
if(n.equalsIgnoreCase("usr_lvl_ip")){return this.usrLvlIp_;}
if(n.equalsIgnoreCase("usr_lvl_ua")){return this.usrLvlUa_;}
if(n.equalsIgnoreCase("usr_lvl_memo")){return this.usrLvlMemo_;}
return null;}
}