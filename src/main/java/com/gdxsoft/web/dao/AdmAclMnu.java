package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表ADM_ACL_MNU映射类
* @author gdx 时间：Fri Jan 18 2019 11:21:33 GMT+0800 (中国标准时间)*/
public class AdmAclMnu extends ClassBase{private Integer mnuId_; // MNU_ID
private Integer admId_; // ADM_ID
private Integer supId_; // SUP_ID

/**
 * 获取 MNU_ID
 *
* @return MNU_ID
*/
public Integer getMnuId() {return this.mnuId_;}
/**
* 赋值 MNU_ID

* @param paraMnuId
* MNU_ID
 */

public void setMnuId(Integer paraMnuId){
  super.recordChanged("MNU_ID", this.mnuId_, paraMnuId);
  this.mnuId_ = paraMnuId;
}


/**
 * 获取 ADM_ID
 *
* @return ADM_ID
*/
public Integer getAdmId() {return this.admId_;}
/**
* 赋值 ADM_ID

* @param paraAdmId
* ADM_ID
 */

public void setAdmId(Integer paraAdmId){
  super.recordChanged("ADM_ID", this.admId_, paraAdmId);
  this.admId_ = paraAdmId;
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
}/**根据字段名称获取值，如果名称为空或字段未找到，返回空值
 @param filedName 字段名称
 @return 字段值*/
public Object getField(String filedName){
if(filedName == null){ return null; }
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("MNU_ID")){return this.mnuId_;}
if(n.equalsIgnoreCase("ADM_ID")){return this.admId_;}
if(n.equalsIgnoreCase("SUP_ID")){return this.supId_;}
return null;}
}