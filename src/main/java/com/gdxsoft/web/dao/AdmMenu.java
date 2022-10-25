package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表ADM_MENU映射类
* @author gdx 时间：Fri Jan 18 2019 11:21:47 GMT+0800 (中国标准时间)*/
public class AdmMenu extends ClassBase{private Integer mnuId_; // MNU_ID
private String mnuText_; // MNU_TEXT
private Integer mnuPid_; // MNU_PID
private Integer mnuLvl_; // MNU_LVL
private Integer mnuOrd_; // MNU_ORD
private String mnuCmd_; // MNU_CMD
private String mnuIcon_; // MNU_ICON
private String mnuGrp_; // MNU_GRP
private String mnuTag_; // MNU_TAG
private String mnuHlp_; // MNU_HLP
private String mnuTextEn_; // MNU_TEXT_EN
private String mnuUnid_; // MNU_UNID
private String mnuPy_; // MNU_PY

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
 * 获取 MNU_TEXT
 *
* @return MNU_TEXT
*/
public String getMnuText() {return this.mnuText_;}
/**
* 赋值 MNU_TEXT

* @param paraMnuText
* MNU_TEXT
 */

public void setMnuText(String paraMnuText){
  super.recordChanged("MNU_TEXT", this.mnuText_, paraMnuText);
  this.mnuText_ = paraMnuText;
}


/**
 * 获取 MNU_PID
 *
* @return MNU_PID
*/
public Integer getMnuPid() {return this.mnuPid_;}
/**
* 赋值 MNU_PID

* @param paraMnuPid
* MNU_PID
 */

public void setMnuPid(Integer paraMnuPid){
  super.recordChanged("MNU_PID", this.mnuPid_, paraMnuPid);
  this.mnuPid_ = paraMnuPid;
}


/**
 * 获取 MNU_LVL
 *
* @return MNU_LVL
*/
public Integer getMnuLvl() {return this.mnuLvl_;}
/**
* 赋值 MNU_LVL

* @param paraMnuLvl
* MNU_LVL
 */

public void setMnuLvl(Integer paraMnuLvl){
  super.recordChanged("MNU_LVL", this.mnuLvl_, paraMnuLvl);
  this.mnuLvl_ = paraMnuLvl;
}


/**
 * 获取 MNU_ORD
 *
* @return MNU_ORD
*/
public Integer getMnuOrd() {return this.mnuOrd_;}
/**
* 赋值 MNU_ORD

* @param paraMnuOrd
* MNU_ORD
 */

public void setMnuOrd(Integer paraMnuOrd){
  super.recordChanged("MNU_ORD", this.mnuOrd_, paraMnuOrd);
  this.mnuOrd_ = paraMnuOrd;
}


/**
 * 获取 MNU_CMD
 *
* @return MNU_CMD
*/
public String getMnuCmd() {return this.mnuCmd_;}
/**
* 赋值 MNU_CMD

* @param paraMnuCmd
* MNU_CMD
 */

public void setMnuCmd(String paraMnuCmd){
  super.recordChanged("MNU_CMD", this.mnuCmd_, paraMnuCmd);
  this.mnuCmd_ = paraMnuCmd;
}


/**
 * 获取 MNU_ICON
 *
* @return MNU_ICON
*/
public String getMnuIcon() {return this.mnuIcon_;}
/**
* 赋值 MNU_ICON

* @param paraMnuIcon
* MNU_ICON
 */

public void setMnuIcon(String paraMnuIcon){
  super.recordChanged("MNU_ICON", this.mnuIcon_, paraMnuIcon);
  this.mnuIcon_ = paraMnuIcon;
}


/**
 * 获取 MNU_GRP
 *
* @return MNU_GRP
*/
public String getMnuGrp() {return this.mnuGrp_;}
/**
* 赋值 MNU_GRP

* @param paraMnuGrp
* MNU_GRP
 */

public void setMnuGrp(String paraMnuGrp){
  super.recordChanged("MNU_GRP", this.mnuGrp_, paraMnuGrp);
  this.mnuGrp_ = paraMnuGrp;
}


/**
 * 获取 MNU_TAG
 *
* @return MNU_TAG
*/
public String getMnuTag() {return this.mnuTag_;}
/**
* 赋值 MNU_TAG

* @param paraMnuTag
* MNU_TAG
 */

public void setMnuTag(String paraMnuTag){
  super.recordChanged("MNU_TAG", this.mnuTag_, paraMnuTag);
  this.mnuTag_ = paraMnuTag;
}


/**
 * 获取 MNU_HLP
 *
* @return MNU_HLP
*/
public String getMnuHlp() {return this.mnuHlp_;}
/**
* 赋值 MNU_HLP

* @param paraMnuHlp
* MNU_HLP
 */

public void setMnuHlp(String paraMnuHlp){
  super.recordChanged("MNU_HLP", this.mnuHlp_, paraMnuHlp);
  this.mnuHlp_ = paraMnuHlp;
}


/**
 * 获取 MNU_TEXT_EN
 *
* @return MNU_TEXT_EN
*/
public String getMnuTextEn() {return this.mnuTextEn_;}
/**
* 赋值 MNU_TEXT_EN

* @param paraMnuTextEn
* MNU_TEXT_EN
 */

public void setMnuTextEn(String paraMnuTextEn){
  super.recordChanged("MNU_TEXT_EN", this.mnuTextEn_, paraMnuTextEn);
  this.mnuTextEn_ = paraMnuTextEn;
}


/**
 * 获取 MNU_UNID
 *
* @return MNU_UNID
*/
public String getMnuUnid() {return this.mnuUnid_;}
/**
* 赋值 MNU_UNID

* @param paraMnuUnid
* MNU_UNID
 */

public void setMnuUnid(String paraMnuUnid){
  super.recordChanged("MNU_UNID", this.mnuUnid_, paraMnuUnid);
  this.mnuUnid_ = paraMnuUnid;
}


/**
 * 获取 MNU_PY
 *
* @return MNU_PY
*/
public String getMnuPy() {return this.mnuPy_;}
/**
* 赋值 MNU_PY

* @param paraMnuPy
* MNU_PY
 */

public void setMnuPy(String paraMnuPy){
  super.recordChanged("MNU_PY", this.mnuPy_, paraMnuPy);
  this.mnuPy_ = paraMnuPy;
}/**根据字段名称获取值，如果名称为空或字段未找到，返回空值
 @param filedName 字段名称
 @return 字段值*/
public Object getField(String filedName){
if(filedName == null){ return null; }
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("MNU_ID")){return this.mnuId_;}
if(n.equalsIgnoreCase("MNU_TEXT")){return this.mnuText_;}
if(n.equalsIgnoreCase("MNU_PID")){return this.mnuPid_;}
if(n.equalsIgnoreCase("MNU_LVL")){return this.mnuLvl_;}
if(n.equalsIgnoreCase("MNU_ORD")){return this.mnuOrd_;}
if(n.equalsIgnoreCase("MNU_CMD")){return this.mnuCmd_;}
if(n.equalsIgnoreCase("MNU_ICON")){return this.mnuIcon_;}
if(n.equalsIgnoreCase("MNU_GRP")){return this.mnuGrp_;}
if(n.equalsIgnoreCase("MNU_TAG")){return this.mnuTag_;}
if(n.equalsIgnoreCase("MNU_HLP")){return this.mnuHlp_;}
if(n.equalsIgnoreCase("MNU_TEXT_EN")){return this.mnuTextEn_;}
if(n.equalsIgnoreCase("MNU_UNID")){return this.mnuUnid_;}
if(n.equalsIgnoreCase("MNU_PY")){return this.mnuPy_;}
return null;}
}