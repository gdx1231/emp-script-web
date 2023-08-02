package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表OA_PRJ_ADM映射类
* @author gdx 时间：Tue Jan 15 2019 18:15:07 GMT+0800 (中国标准时间)*/
public class OaPrjAdm extends ClassBase{private Integer prjId_; // PRJ_ID
private Integer admId_; // ADM_ID

/**
 * 获取 PRJ_ID
 *
* @return PRJ_ID
*/
public Integer getPrjId() {return this.prjId_;}
/**
* 赋值 PRJ_ID

* @param paraPrjId
* PRJ_ID
 */

public void setPrjId(Integer paraPrjId){
  super.recordChanged("PRJ_ID", this.prjId_, paraPrjId);
  this.prjId_ = paraPrjId;
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
}/**根据字段名称获取值，如果名称为空或字段未找到，返回空值
 @param filedName 字段名称
 @return 字段值*/
public Object getField(String filedName){
if(filedName == null){ return null; }
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("PRJ_ID")){return this.prjId_;}
if(n.equalsIgnoreCase("ADM_ID")){return this.admId_;}
return null;}
}