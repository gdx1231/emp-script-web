package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表sys_default映射类
* @author gdx 时间：Sun Apr 26 2020 16:49:59 GMT+0800 (中国标准时间)*/
public class SysDefault extends ClassBase{private String tag_; // TAG
private String defaultValue_; // DEFAULT_VALUE
private Integer defaultValue2_; // DEFAULT_VALUE2
private Integer supId_; // SUP_ID
private Date cdate_; // CDATE
private Date mdate_; // MDATE
private Integer admId_; // ADM_ID
private String catalog_; // CATALOG
private byte[] defaultValue3_; // DEFAULT_VALUE3

/**
 * 获取 TAG
 *
* @return TAG
*/
public String getTag() {return this.tag_;}
/**
* 赋值 TAG

* @param paraTag
* TAG
 */

public void setTag(String paraTag){
  super.recordChanged("TAG", this.tag_, paraTag);
  this.tag_ = paraTag;
}


/**
 * 获取 DEFAULT_VALUE
 *
* @return DEFAULT_VALUE
*/
public String getDefaultValue() {return this.defaultValue_;}
/**
* 赋值 DEFAULT_VALUE

* @param paraDefaultValue
* DEFAULT_VALUE
 */

public void setDefaultValue(String paraDefaultValue){
  super.recordChanged("DEFAULT_VALUE", this.defaultValue_, paraDefaultValue);
  this.defaultValue_ = paraDefaultValue;
}


/**
 * 获取 DEFAULT_VALUE2
 *
* @return DEFAULT_VALUE2
*/
public Integer getDefaultValue2() {return this.defaultValue2_;}
/**
* 赋值 DEFAULT_VALUE2

* @param paraDefaultValue2
* DEFAULT_VALUE2
 */

public void setDefaultValue2(Integer paraDefaultValue2){
  super.recordChanged("DEFAULT_VALUE2", this.defaultValue2_, paraDefaultValue2);
  this.defaultValue2_ = paraDefaultValue2;
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
 * 获取 CATALOG
 *
* @return CATALOG
*/
public String getCatalog() {return this.catalog_;}
/**
* 赋值 CATALOG

* @param paraCatalog
* CATALOG
 */

public void setCatalog(String paraCatalog){
  super.recordChanged("CATALOG", this.catalog_, paraCatalog);
  this.catalog_ = paraCatalog;
}


/**
 * 获取 DEFAULT_VALUE3
 *
* @return DEFAULT_VALUE3
*/
public byte[] getDefaultValue3() {return this.defaultValue3_;}
/**
* 赋值 DEFAULT_VALUE3

* @param paraDefaultValue3
* DEFAULT_VALUE3
 */

public void setDefaultValue3(byte[] paraDefaultValue3){
  super.recordChanged("DEFAULT_VALUE3", this.defaultValue3_, paraDefaultValue3);
  this.defaultValue3_ = paraDefaultValue3;
}/**根据字段名称获取值，如果名称为空或字段未找到，返回空值
 @param filedName 字段名称
 @return 字段值*/
public Object getField(String filedName){
if(filedName == null){ return null; }
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("TAG")){return this.tag_;}
if(n.equalsIgnoreCase("DEFAULT_VALUE")){return this.defaultValue_;}
if(n.equalsIgnoreCase("DEFAULT_VALUE2")){return this.defaultValue2_;}
if(n.equalsIgnoreCase("SUP_ID")){return this.supId_;}
if(n.equalsIgnoreCase("CDATE")){return this.cdate_;}
if(n.equalsIgnoreCase("MDATE")){return this.mdate_;}
if(n.equalsIgnoreCase("ADM_ID")){return this.admId_;}
if(n.equalsIgnoreCase("CATALOG")){return this.catalog_;}
if(n.equalsIgnoreCase("DEFAULT_VALUE3")){return this.defaultValue3_;}
return null;}
}