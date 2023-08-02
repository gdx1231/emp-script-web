package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表OA_PRJ_REQ映射类
* @author gdx 时间：Tue Jan 15 2019 16:09:02 GMT+0800 (中国标准时间)*/
public class OaPrjReq extends ClassBase{private Integer prjId_; // PRJ_ID
private Integer reqId_; // REQ_ID

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
 * 获取 REQ_ID
 *
* @return REQ_ID
*/
public Integer getReqId() {return this.reqId_;}
/**
* 赋值 REQ_ID

* @param paraReqId
* REQ_ID
 */

public void setReqId(Integer paraReqId){
  super.recordChanged("REQ_ID", this.reqId_, paraReqId);
  this.reqId_ = paraReqId;
}/**根据字段名称获取值，如果名称为空或字段未找到，返回空值
 @param filedName 字段名称
 @return 字段值*/
public Object getField(String filedName){
if(filedName == null){ return null; }
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("PRJ_ID")){return this.prjId_;}
if(n.equalsIgnoreCase("REQ_ID")){return this.reqId_;}
return null;}
}