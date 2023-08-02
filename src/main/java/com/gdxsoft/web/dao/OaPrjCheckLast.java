package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表OA_PRJ_CHECK_LAST映射类
* @author gdx 时间：Mon Jan 21 2019 15:42:27 GMT+0800 (中国标准时间)*/
public class OaPrjCheckLast extends ClassBase{private Integer prjId_; // 项目编号
private String prjCheckTag_; // 检查标记
private Integer prjChkMaxId_; // 最大编号
private Date prjChkMaxTime_; // 最后时间
private Date prjChkDate_; // 创建/修改时间

/**
 * 获取 项目编号
 *
* @return 项目编号
*/
public Integer getPrjId() {return this.prjId_;}
/**
* 赋值 项目编号

* @param paraPrjId
* 项目编号
 */

public void setPrjId(Integer paraPrjId){
  super.recordChanged("PRJ_ID", this.prjId_, paraPrjId);
  this.prjId_ = paraPrjId;
}


/**
 * 获取 检查标记
 *
* @return 检查标记
*/
public String getPrjCheckTag() {return this.prjCheckTag_;}
/**
* 赋值 检查标记

* @param paraPrjCheckTag
* 检查标记
 */

public void setPrjCheckTag(String paraPrjCheckTag){
  super.recordChanged("PRJ_CHECK_TAG", this.prjCheckTag_, paraPrjCheckTag);
  this.prjCheckTag_ = paraPrjCheckTag;
}


/**
 * 获取 最大编号
 *
* @return 最大编号
*/
public Integer getPrjChkMaxId() {return this.prjChkMaxId_;}
/**
* 赋值 最大编号

* @param paraPrjChkMaxId
* 最大编号
 */

public void setPrjChkMaxId(Integer paraPrjChkMaxId){
  super.recordChanged("PRJ_CHK_MAX_ID", this.prjChkMaxId_, paraPrjChkMaxId);
  this.prjChkMaxId_ = paraPrjChkMaxId;
}


/**
 * 获取 最后时间
 *
* @return 最后时间
*/
public Date getPrjChkMaxTime() {return this.prjChkMaxTime_;}
/**
* 赋值 最后时间

* @param paraPrjChkMaxTime
* 最后时间
 */

public void setPrjChkMaxTime(Date paraPrjChkMaxTime){
  super.recordChanged("PRJ_CHK_MAX_TIME", this.prjChkMaxTime_, paraPrjChkMaxTime);
  this.prjChkMaxTime_ = paraPrjChkMaxTime;
}


/**
 * 获取 创建/修改时间
 *
* @return 创建/修改时间
*/
public Date getPrjChkDate() {return this.prjChkDate_;}
/**
* 赋值 创建/修改时间

* @param paraPrjChkDate
* 创建/修改时间
 */

public void setPrjChkDate(Date paraPrjChkDate){
  super.recordChanged("PRJ_CHK_DATE", this.prjChkDate_, paraPrjChkDate);
  this.prjChkDate_ = paraPrjChkDate;
}/**根据字段名称获取值，如果名称为空或字段未找到，返回空值
 @param filedName 字段名称
 @return 字段值*/
public Object getField(String filedName){
if(filedName == null){ return null; }
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("PRJ_ID")){return this.prjId_;}
if(n.equalsIgnoreCase("PRJ_CHECK_TAG")){return this.prjCheckTag_;}
if(n.equalsIgnoreCase("PRJ_CHK_MAX_ID")){return this.prjChkMaxId_;}
if(n.equalsIgnoreCase("PRJ_CHK_MAX_TIME")){return this.prjChkMaxTime_;}
if(n.equalsIgnoreCase("PRJ_CHK_DATE")){return this.prjChkDate_;}
return null;}
}