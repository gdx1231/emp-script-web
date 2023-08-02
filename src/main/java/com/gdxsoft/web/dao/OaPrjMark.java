package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表OA_PRJ_MARK映射类
* @author gdx 时间：Wed Jan 16 2019 11:18:52 GMT+0800 (中国标准时间)*/
public class OaPrjMark extends ClassBase{private Integer prjMakId_; // 标记编号
private Integer prjId_; // 项目编号
private Date prjMarkDate_; // 标记日期
private String prjMarkName_; // 标记名称
private String prjMarkNameEn_; // 英文
private Integer supId_; // 供应商
private Integer admId_; // 创建人
private Date prjMarkCdate_; // 创建日期
private Date prjMarkMdate_; // 修改日期
private String prjMarkColor_; // 颜色
private String prjMarkTag_; // 标记

/**
 * 获取 标记编号
 *
* @return 标记编号
*/
public Integer getPrjMakId() {return this.prjMakId_;}
/**
* 赋值 标记编号

* @param paraPrjMakId
* 标记编号
 */

public void setPrjMakId(Integer paraPrjMakId){
  super.recordChanged("PRJ_MAK_ID", this.prjMakId_, paraPrjMakId);
  this.prjMakId_ = paraPrjMakId;
}


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
 * 获取 标记日期
 *
* @return 标记日期
*/
public Date getPrjMarkDate() {return this.prjMarkDate_;}
/**
* 赋值 标记日期

* @param paraPrjMarkDate
* 标记日期
 */

public void setPrjMarkDate(Date paraPrjMarkDate){
  super.recordChanged("PRJ_MARK_DATE", this.prjMarkDate_, paraPrjMarkDate);
  this.prjMarkDate_ = paraPrjMarkDate;
}


/**
 * 获取 标记名称
 *
* @return 标记名称
*/
public String getPrjMarkName() {return this.prjMarkName_;}
/**
* 赋值 标记名称

* @param paraPrjMarkName
* 标记名称
 */

public void setPrjMarkName(String paraPrjMarkName){
  super.recordChanged("PRJ_MARK_NAME", this.prjMarkName_, paraPrjMarkName);
  this.prjMarkName_ = paraPrjMarkName;
}


/**
 * 获取 英文
 *
* @return 英文
*/
public String getPrjMarkNameEn() {return this.prjMarkNameEn_;}
/**
* 赋值 英文

* @param paraPrjMarkNameEn
* 英文
 */

public void setPrjMarkNameEn(String paraPrjMarkNameEn){
  super.recordChanged("PRJ_MARK_NAME_EN", this.prjMarkNameEn_, paraPrjMarkNameEn);
  this.prjMarkNameEn_ = paraPrjMarkNameEn;
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
 * 获取 创建日期
 *
* @return 创建日期
*/
public Date getPrjMarkCdate() {return this.prjMarkCdate_;}
/**
* 赋值 创建日期

* @param paraPrjMarkCdate
* 创建日期
 */

public void setPrjMarkCdate(Date paraPrjMarkCdate){
  super.recordChanged("PRJ_MARK_CDATE", this.prjMarkCdate_, paraPrjMarkCdate);
  this.prjMarkCdate_ = paraPrjMarkCdate;
}


/**
 * 获取 修改日期
 *
* @return 修改日期
*/
public Date getPrjMarkMdate() {return this.prjMarkMdate_;}
/**
* 赋值 修改日期

* @param paraPrjMarkMdate
* 修改日期
 */

public void setPrjMarkMdate(Date paraPrjMarkMdate){
  super.recordChanged("PRJ_MARK_MDATE", this.prjMarkMdate_, paraPrjMarkMdate);
  this.prjMarkMdate_ = paraPrjMarkMdate;
}


/**
 * 获取 颜色
 *
* @return 颜色
*/
public String getPrjMarkColor() {return this.prjMarkColor_;}
/**
* 赋值 颜色

* @param paraPrjMarkColor
* 颜色
 */

public void setPrjMarkColor(String paraPrjMarkColor){
  super.recordChanged("PRJ_MARK_COLOR", this.prjMarkColor_, paraPrjMarkColor);
  this.prjMarkColor_ = paraPrjMarkColor;
}


/**
 * 获取 标记
 *
* @return 标记
*/
public String getPrjMarkTag() {return this.prjMarkTag_;}
/**
* 赋值 标记

* @param paraPrjMarkTag
* 标记
 */

public void setPrjMarkTag(String paraPrjMarkTag){
  super.recordChanged("PRJ_MARK_TAG", this.prjMarkTag_, paraPrjMarkTag);
  this.prjMarkTag_ = paraPrjMarkTag;
}/**根据字段名称获取值，如果名称为空或字段未找到，返回空值
 @param filedName 字段名称
 @return 字段值*/
public Object getField(String filedName){
if(filedName == null){ return null; }
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("PRJ_MAK_ID")){return this.prjMakId_;}
if(n.equalsIgnoreCase("PRJ_ID")){return this.prjId_;}
if(n.equalsIgnoreCase("PRJ_MARK_DATE")){return this.prjMarkDate_;}
if(n.equalsIgnoreCase("PRJ_MARK_NAME")){return this.prjMarkName_;}
if(n.equalsIgnoreCase("PRJ_MARK_NAME_EN")){return this.prjMarkNameEn_;}
if(n.equalsIgnoreCase("SUP_ID")){return this.supId_;}
if(n.equalsIgnoreCase("ADM_ID")){return this.admId_;}
if(n.equalsIgnoreCase("PRJ_MARK_CDATE")){return this.prjMarkCdate_;}
if(n.equalsIgnoreCase("PRJ_MARK_MDATE")){return this.prjMarkMdate_;}
if(n.equalsIgnoreCase("PRJ_MARK_COLOR")){return this.prjMarkColor_;}
if(n.equalsIgnoreCase("PRJ_MARK_TAG")){return this.prjMarkTag_;}
return null;}
}