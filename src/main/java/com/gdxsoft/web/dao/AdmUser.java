package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表ADM_USER映射类
* @author gdx 时间：Mon Jan 07 2019 17:29:04 GMT+0800 (中国标准时间)*/
public class AdmUser extends ClassBase{private Integer admId_; // 编号
private Integer supId_; // 供应商编号
private String sexTag_; // 性别标记
private String admUsrStaTag_; // ADM_USR_STA_TAG
private Integer admDepId_; // 所属部门
private String admLid_; // 登录名
private String admPwd_; // 密码
private String admName_; // 姓名
private String admNameEn_; // 英文名
private String admIdcard_; // 身份证
private String admTele_; // 电话
private String admEmail_; // 邮件
private String admQq_; // QQ号
private String admMsn_; // MSN号
private String admIcq_; // ICQ
private String admMobile_; // 移动电话
private String admAddr_; // 地址
private Date admCdate_; // 创建日期
private Date admMdate_; // 修改日期
private Date admLdate_; // 登录日期
private String admLip_; // 登录IP
private byte[] admPhoto_; // 照片
private Integer crmComId_; // CRM_COM_ID
private Integer pid_; // PID
private String admChgPwd_; // ADM_CHG_PWD
private byte[] admSignPic_; // 签名文件
private String admUnid_; // ADM_UNID
private String source_; // SOURCE
private Integer sourceId_; // SOURCE_ID
private String erpSkin_; // ERP_SKIN
private String admOp_; // ADM_OP
private Integer crmCusId_; // CRM_CUS_ID
private Integer webUsrId_; // WEB_USR_ID
private Integer surId_; // 所属考核问卷id
private String iosToken_; // IOS PUSH 设备号码
private Integer ausBranchid_; // AUS_BRANCHID
private String ausPostalcode_; // AUS_POSTALCODE
private Integer ausTaid_; // AUS_TAID
private String ausTitle_; // AUS_TITLE
private String ausEpassword_; // AUS_EPASSWORD
private String ausStateorprovince_; // AUS_STATEORPROVINCE
private String ausAuth_; // AUS_AUTH
private String ausSmtpsver_; // AUS_SMTPSVER
private Integer ausTaeid_; // AUS_TAEID
private String ausPhoto_; // AUS_PHOTO
private String ausPosition_; // AUS_POSITION
private String ausActive_; // AUS_ACTIVE
private String ausCity_; // AUS_CITY
private Integer isAus_; // IS_AUS
private String supUnid_; // SUP_UNID

/**
 * 获取 编号
 *
* @return 编号
*/
public Integer getAdmId() {return this.admId_;}
/**
* 赋值 编号

* @param paraAdmId
* 编号
 */

public void setAdmId(Integer paraAdmId){
  super.recordChanged("ADM_ID", this.admId_, paraAdmId);
  this.admId_ = paraAdmId;
}


/**
 * 获取 供应商编号
 *
* @return 供应商编号
*/
public Integer getSupId() {return this.supId_;}
/**
* 赋值 供应商编号

* @param paraSupId
* 供应商编号
 */

public void setSupId(Integer paraSupId){
  super.recordChanged("SUP_ID", this.supId_, paraSupId);
  this.supId_ = paraSupId;
}


/**
 * 获取 性别标记
 *
* @return 性别标记
*/
public String getSexTag() {return this.sexTag_;}
/**
* 赋值 性别标记

* @param paraSexTag
* 性别标记
 */

public void setSexTag(String paraSexTag){
  super.recordChanged("SEX_TAG", this.sexTag_, paraSexTag);
  this.sexTag_ = paraSexTag;
}


/**
 * 获取 ADM_USR_STA_TAG
 *
* @return ADM_USR_STA_TAG
*/
public String getAdmUsrStaTag() {return this.admUsrStaTag_;}
/**
* 赋值 ADM_USR_STA_TAG

* @param paraAdmUsrStaTag
* ADM_USR_STA_TAG
 */

public void setAdmUsrStaTag(String paraAdmUsrStaTag){
  super.recordChanged("ADM_USR_STA_TAG", this.admUsrStaTag_, paraAdmUsrStaTag);
  this.admUsrStaTag_ = paraAdmUsrStaTag;
}


/**
 * 获取 所属部门
 *
* @return 所属部门
*/
public Integer getAdmDepId() {return this.admDepId_;}
/**
* 赋值 所属部门

* @param paraAdmDepId
* 所属部门
 */

public void setAdmDepId(Integer paraAdmDepId){
  super.recordChanged("ADM_DEP_ID", this.admDepId_, paraAdmDepId);
  this.admDepId_ = paraAdmDepId;
}


/**
 * 获取 登录名
 *
* @return 登录名
*/
public String getAdmLid() {return this.admLid_;}
/**
* 赋值 登录名

* @param paraAdmLid
* 登录名
 */

public void setAdmLid(String paraAdmLid){
  super.recordChanged("ADM_LID", this.admLid_, paraAdmLid);
  this.admLid_ = paraAdmLid;
}


/**
 * 获取 密码
 *
* @return 密码
*/
public String getAdmPwd() {return this.admPwd_;}
/**
* 赋值 密码

* @param paraAdmPwd
* 密码
 */

public void setAdmPwd(String paraAdmPwd){
  super.recordChanged("ADM_PWD", this.admPwd_, paraAdmPwd);
  this.admPwd_ = paraAdmPwd;
}


/**
 * 获取 姓名
 *
* @return 姓名
*/
public String getAdmName() {return this.admName_;}
/**
* 赋值 姓名

* @param paraAdmName
* 姓名
 */

public void setAdmName(String paraAdmName){
  super.recordChanged("ADM_NAME", this.admName_, paraAdmName);
  this.admName_ = paraAdmName;
}


/**
 * 获取 英文名
 *
* @return 英文名
*/
public String getAdmNameEn() {return this.admNameEn_;}
/**
* 赋值 英文名

* @param paraAdmNameEn
* 英文名
 */

public void setAdmNameEn(String paraAdmNameEn){
  super.recordChanged("ADM_NAME_EN", this.admNameEn_, paraAdmNameEn);
  this.admNameEn_ = paraAdmNameEn;
}


/**
 * 获取 身份证
 *
* @return 身份证
*/
public String getAdmIdcard() {return this.admIdcard_;}
/**
* 赋值 身份证

* @param paraAdmIdcard
* 身份证
 */

public void setAdmIdcard(String paraAdmIdcard){
  super.recordChanged("ADM_IDCARD", this.admIdcard_, paraAdmIdcard);
  this.admIdcard_ = paraAdmIdcard;
}


/**
 * 获取 电话
 *
* @return 电话
*/
public String getAdmTele() {return this.admTele_;}
/**
* 赋值 电话

* @param paraAdmTele
* 电话
 */

public void setAdmTele(String paraAdmTele){
  super.recordChanged("ADM_TELE", this.admTele_, paraAdmTele);
  this.admTele_ = paraAdmTele;
}


/**
 * 获取 邮件
 *
* @return 邮件
*/
public String getAdmEmail() {return this.admEmail_;}
/**
* 赋值 邮件

* @param paraAdmEmail
* 邮件
 */

public void setAdmEmail(String paraAdmEmail){
  super.recordChanged("ADM_EMAIL", this.admEmail_, paraAdmEmail);
  this.admEmail_ = paraAdmEmail;
}


/**
 * 获取 QQ号
 *
* @return QQ号
*/
public String getAdmQq() {return this.admQq_;}
/**
* 赋值 QQ号

* @param paraAdmQq
* QQ号
 */

public void setAdmQq(String paraAdmQq){
  super.recordChanged("ADM_QQ", this.admQq_, paraAdmQq);
  this.admQq_ = paraAdmQq;
}


/**
 * 获取 MSN号
 *
* @return MSN号
*/
public String getAdmMsn() {return this.admMsn_;}
/**
* 赋值 MSN号

* @param paraAdmMsn
* MSN号
 */

public void setAdmMsn(String paraAdmMsn){
  super.recordChanged("ADM_MSN", this.admMsn_, paraAdmMsn);
  this.admMsn_ = paraAdmMsn;
}


/**
 * 获取 ICQ
 *
* @return ICQ
*/
public String getAdmIcq() {return this.admIcq_;}
/**
* 赋值 ICQ

* @param paraAdmIcq
* ICQ
 */

public void setAdmIcq(String paraAdmIcq){
  super.recordChanged("ADM_ICQ", this.admIcq_, paraAdmIcq);
  this.admIcq_ = paraAdmIcq;
}


/**
 * 获取 移动电话
 *
* @return 移动电话
*/
public String getAdmMobile() {return this.admMobile_;}
/**
* 赋值 移动电话

* @param paraAdmMobile
* 移动电话
 */

public void setAdmMobile(String paraAdmMobile){
  super.recordChanged("ADM_MOBILE", this.admMobile_, paraAdmMobile);
  this.admMobile_ = paraAdmMobile;
}


/**
 * 获取 地址
 *
* @return 地址
*/
public String getAdmAddr() {return this.admAddr_;}
/**
* 赋值 地址

* @param paraAdmAddr
* 地址
 */

public void setAdmAddr(String paraAdmAddr){
  super.recordChanged("ADM_ADDR", this.admAddr_, paraAdmAddr);
  this.admAddr_ = paraAdmAddr;
}


/**
 * 获取 创建日期
 *
* @return 创建日期
*/
public Date getAdmCdate() {return this.admCdate_;}
/**
* 赋值 创建日期

* @param paraAdmCdate
* 创建日期
 */

public void setAdmCdate(Date paraAdmCdate){
  super.recordChanged("ADM_CDATE", this.admCdate_, paraAdmCdate);
  this.admCdate_ = paraAdmCdate;
}


/**
 * 获取 修改日期
 *
* @return 修改日期
*/
public Date getAdmMdate() {return this.admMdate_;}
/**
* 赋值 修改日期

* @param paraAdmMdate
* 修改日期
 */

public void setAdmMdate(Date paraAdmMdate){
  super.recordChanged("ADM_MDATE", this.admMdate_, paraAdmMdate);
  this.admMdate_ = paraAdmMdate;
}


/**
 * 获取 登录日期
 *
* @return 登录日期
*/
public Date getAdmLdate() {return this.admLdate_;}
/**
* 赋值 登录日期

* @param paraAdmLdate
* 登录日期
 */

public void setAdmLdate(Date paraAdmLdate){
  super.recordChanged("ADM_LDATE", this.admLdate_, paraAdmLdate);
  this.admLdate_ = paraAdmLdate;
}


/**
 * 获取 登录IP
 *
* @return 登录IP
*/
public String getAdmLip() {return this.admLip_;}
/**
* 赋值 登录IP

* @param paraAdmLip
* 登录IP
 */

public void setAdmLip(String paraAdmLip){
  super.recordChanged("ADM_LIP", this.admLip_, paraAdmLip);
  this.admLip_ = paraAdmLip;
}


/**
 * 获取 照片
 *
* @return 照片
*/
public byte[] getAdmPhoto() {return this.admPhoto_;}
/**
* 赋值 照片

* @param paraAdmPhoto
* 照片
 */

public void setAdmPhoto(byte[] paraAdmPhoto){
  super.recordChanged("ADM_PHOTO", this.admPhoto_, paraAdmPhoto);
  this.admPhoto_ = paraAdmPhoto;
}


/**
 * 获取 CRM_COM_ID
 *
* @return CRM_COM_ID
*/
public Integer getCrmComId() {return this.crmComId_;}
/**
* 赋值 CRM_COM_ID

* @param paraCrmComId
* CRM_COM_ID
 */

public void setCrmComId(Integer paraCrmComId){
  super.recordChanged("CRM_COM_ID", this.crmComId_, paraCrmComId);
  this.crmComId_ = paraCrmComId;
}


/**
 * 获取 PID
 *
* @return PID
*/
public Integer getPid() {return this.pid_;}
/**
* 赋值 PID

* @param paraPid
* PID
 */

public void setPid(Integer paraPid){
  super.recordChanged("PID", this.pid_, paraPid);
  this.pid_ = paraPid;
}


/**
 * 获取 ADM_CHG_PWD
 *
* @return ADM_CHG_PWD
*/
public String getAdmChgPwd() {return this.admChgPwd_;}
/**
* 赋值 ADM_CHG_PWD

* @param paraAdmChgPwd
* ADM_CHG_PWD
 */

public void setAdmChgPwd(String paraAdmChgPwd){
  super.recordChanged("ADM_CHG_PWD", this.admChgPwd_, paraAdmChgPwd);
  this.admChgPwd_ = paraAdmChgPwd;
}


/**
 * 获取 签名文件
 *
* @return 签名文件
*/
public byte[] getAdmSignPic() {return this.admSignPic_;}
/**
* 赋值 签名文件

* @param paraAdmSignPic
* 签名文件
 */

public void setAdmSignPic(byte[] paraAdmSignPic){
  super.recordChanged("ADM_SIGN_PIC", this.admSignPic_, paraAdmSignPic);
  this.admSignPic_ = paraAdmSignPic;
}


/**
 * 获取 ADM_UNID
 *
* @return ADM_UNID
*/
public String getAdmUnid() {return this.admUnid_;}
/**
* 赋值 ADM_UNID

* @param paraAdmUnid
* ADM_UNID
 */

public void setAdmUnid(String paraAdmUnid){
  super.recordChanged("ADM_UNID", this.admUnid_, paraAdmUnid);
  this.admUnid_ = paraAdmUnid;
}


/**
 * 获取 SOURCE
 *
* @return SOURCE
*/
public String getSource() {return this.source_;}
/**
* 赋值 SOURCE

* @param paraSource
* SOURCE
 */

public void setSource(String paraSource){
  super.recordChanged("SOURCE", this.source_, paraSource);
  this.source_ = paraSource;
}


/**
 * 获取 SOURCE_ID
 *
* @return SOURCE_ID
*/
public Integer getSourceId() {return this.sourceId_;}
/**
* 赋值 SOURCE_ID

* @param paraSourceId
* SOURCE_ID
 */

public void setSourceId(Integer paraSourceId){
  super.recordChanged("SOURCE_ID", this.sourceId_, paraSourceId);
  this.sourceId_ = paraSourceId;
}


/**
 * 获取 ERP_SKIN
 *
* @return ERP_SKIN
*/
public String getErpSkin() {return this.erpSkin_;}
/**
* 赋值 ERP_SKIN

* @param paraErpSkin
* ERP_SKIN
 */

public void setErpSkin(String paraErpSkin){
  super.recordChanged("ERP_SKIN", this.erpSkin_, paraErpSkin);
  this.erpSkin_ = paraErpSkin;
}


/**
 * 获取 ADM_OP
 *
* @return ADM_OP
*/
public String getAdmOp() {return this.admOp_;}
/**
* 赋值 ADM_OP

* @param paraAdmOp
* ADM_OP
 */

public void setAdmOp(String paraAdmOp){
  super.recordChanged("ADM_OP", this.admOp_, paraAdmOp);
  this.admOp_ = paraAdmOp;
}


/**
 * 获取 CRM_CUS_ID
 *
* @return CRM_CUS_ID
*/
public Integer getCrmCusId() {return this.crmCusId_;}
/**
* 赋值 CRM_CUS_ID

* @param paraCrmCusId
* CRM_CUS_ID
 */

public void setCrmCusId(Integer paraCrmCusId){
  super.recordChanged("CRM_CUS_ID", this.crmCusId_, paraCrmCusId);
  this.crmCusId_ = paraCrmCusId;
}


/**
 * 获取 WEB_USR_ID
 *
* @return WEB_USR_ID
*/
public Integer getWebUsrId() {return this.webUsrId_;}
/**
* 赋值 WEB_USR_ID

* @param paraWebUsrId
* WEB_USR_ID
 */

public void setWebUsrId(Integer paraWebUsrId){
  super.recordChanged("WEB_USR_ID", this.webUsrId_, paraWebUsrId);
  this.webUsrId_ = paraWebUsrId;
}


/**
 * 获取 所属考核问卷id
 *
* @return 所属考核问卷id
*/
public Integer getSurId() {return this.surId_;}
/**
* 赋值 所属考核问卷id

* @param paraSurId
* 所属考核问卷id
 */

public void setSurId(Integer paraSurId){
  super.recordChanged("SUR_ID", this.surId_, paraSurId);
  this.surId_ = paraSurId;
}


/**
 * 获取 IOS PUSH 设备号码
 *
* @return IOS PUSH 设备号码
*/
public String getIosToken() {return this.iosToken_;}
/**
* 赋值 IOS PUSH 设备号码

* @param paraIosToken
* IOS PUSH 设备号码
 */

public void setIosToken(String paraIosToken){
  super.recordChanged("IOS_TOKEN", this.iosToken_, paraIosToken);
  this.iosToken_ = paraIosToken;
}


/**
 * 获取 AUS_BRANCHID
 *
* @return AUS_BRANCHID
*/
public Integer getAusBranchid() {return this.ausBranchid_;}
/**
* 赋值 AUS_BRANCHID

* @param paraAusBranchid
* AUS_BRANCHID
 */

public void setAusBranchid(Integer paraAusBranchid){
  super.recordChanged("AUS_BRANCHID", this.ausBranchid_, paraAusBranchid);
  this.ausBranchid_ = paraAusBranchid;
}


/**
 * 获取 AUS_POSTALCODE
 *
* @return AUS_POSTALCODE
*/
public String getAusPostalcode() {return this.ausPostalcode_;}
/**
* 赋值 AUS_POSTALCODE

* @param paraAusPostalcode
* AUS_POSTALCODE
 */

public void setAusPostalcode(String paraAusPostalcode){
  super.recordChanged("AUS_POSTALCODE", this.ausPostalcode_, paraAusPostalcode);
  this.ausPostalcode_ = paraAusPostalcode;
}


/**
 * 获取 AUS_TAID
 *
* @return AUS_TAID
*/
public Integer getAusTaid() {return this.ausTaid_;}
/**
* 赋值 AUS_TAID

* @param paraAusTaid
* AUS_TAID
 */

public void setAusTaid(Integer paraAusTaid){
  super.recordChanged("AUS_TAID", this.ausTaid_, paraAusTaid);
  this.ausTaid_ = paraAusTaid;
}


/**
 * 获取 AUS_TITLE
 *
* @return AUS_TITLE
*/
public String getAusTitle() {return this.ausTitle_;}
/**
* 赋值 AUS_TITLE

* @param paraAusTitle
* AUS_TITLE
 */

public void setAusTitle(String paraAusTitle){
  super.recordChanged("AUS_TITLE", this.ausTitle_, paraAusTitle);
  this.ausTitle_ = paraAusTitle;
}


/**
 * 获取 AUS_EPASSWORD
 *
* @return AUS_EPASSWORD
*/
public String getAusEpassword() {return this.ausEpassword_;}
/**
* 赋值 AUS_EPASSWORD

* @param paraAusEpassword
* AUS_EPASSWORD
 */

public void setAusEpassword(String paraAusEpassword){
  super.recordChanged("AUS_EPASSWORD", this.ausEpassword_, paraAusEpassword);
  this.ausEpassword_ = paraAusEpassword;
}


/**
 * 获取 AUS_STATEORPROVINCE
 *
* @return AUS_STATEORPROVINCE
*/
public String getAusStateorprovince() {return this.ausStateorprovince_;}
/**
* 赋值 AUS_STATEORPROVINCE

* @param paraAusStateorprovince
* AUS_STATEORPROVINCE
 */

public void setAusStateorprovince(String paraAusStateorprovince){
  super.recordChanged("AUS_STATEORPROVINCE", this.ausStateorprovince_, paraAusStateorprovince);
  this.ausStateorprovince_ = paraAusStateorprovince;
}


/**
 * 获取 AUS_AUTH
 *
* @return AUS_AUTH
*/
public String getAusAuth() {return this.ausAuth_;}
/**
* 赋值 AUS_AUTH

* @param paraAusAuth
* AUS_AUTH
 */

public void setAusAuth(String paraAusAuth){
  super.recordChanged("AUS_AUTH", this.ausAuth_, paraAusAuth);
  this.ausAuth_ = paraAusAuth;
}


/**
 * 获取 AUS_SMTPSVER
 *
* @return AUS_SMTPSVER
*/
public String getAusSmtpsver() {return this.ausSmtpsver_;}
/**
* 赋值 AUS_SMTPSVER

* @param paraAusSmtpsver
* AUS_SMTPSVER
 */

public void setAusSmtpsver(String paraAusSmtpsver){
  super.recordChanged("AUS_SMTPSVER", this.ausSmtpsver_, paraAusSmtpsver);
  this.ausSmtpsver_ = paraAusSmtpsver;
}


/**
 * 获取 AUS_TAEID
 *
* @return AUS_TAEID
*/
public Integer getAusTaeid() {return this.ausTaeid_;}
/**
* 赋值 AUS_TAEID

* @param paraAusTaeid
* AUS_TAEID
 */

public void setAusTaeid(Integer paraAusTaeid){
  super.recordChanged("AUS_TAEID", this.ausTaeid_, paraAusTaeid);
  this.ausTaeid_ = paraAusTaeid;
}


/**
 * 获取 AUS_PHOTO
 *
* @return AUS_PHOTO
*/
public String getAusPhoto() {return this.ausPhoto_;}
/**
* 赋值 AUS_PHOTO

* @param paraAusPhoto
* AUS_PHOTO
 */

public void setAusPhoto(String paraAusPhoto){
  super.recordChanged("AUS_PHOTO", this.ausPhoto_, paraAusPhoto);
  this.ausPhoto_ = paraAusPhoto;
}


/**
 * 获取 AUS_POSITION
 *
* @return AUS_POSITION
*/
public String getAusPosition() {return this.ausPosition_;}
/**
* 赋值 AUS_POSITION

* @param paraAusPosition
* AUS_POSITION
 */

public void setAusPosition(String paraAusPosition){
  super.recordChanged("AUS_POSITION", this.ausPosition_, paraAusPosition);
  this.ausPosition_ = paraAusPosition;
}


/**
 * 获取 AUS_ACTIVE
 *
* @return AUS_ACTIVE
*/
public String getAusActive() {return this.ausActive_;}
/**
* 赋值 AUS_ACTIVE

* @param paraAusActive
* AUS_ACTIVE
 */

public void setAusActive(String paraAusActive){
  super.recordChanged("AUS_ACTIVE", this.ausActive_, paraAusActive);
  this.ausActive_ = paraAusActive;
}


/**
 * 获取 AUS_CITY
 *
* @return AUS_CITY
*/
public String getAusCity() {return this.ausCity_;}
/**
* 赋值 AUS_CITY

* @param paraAusCity
* AUS_CITY
 */

public void setAusCity(String paraAusCity){
  super.recordChanged("AUS_CITY", this.ausCity_, paraAusCity);
  this.ausCity_ = paraAusCity;
}


/**
 * 获取 IS_AUS
 *
* @return IS_AUS
*/
public Integer getIsAus() {return this.isAus_;}
/**
* 赋值 IS_AUS

* @param paraIsAus
* IS_AUS
 */

public void setIsAus(Integer paraIsAus){
  super.recordChanged("IS_AUS", this.isAus_, paraIsAus);
  this.isAus_ = paraIsAus;
}


/**
 * 获取 SUP_UNID
 *
* @return SUP_UNID
*/
public String getSupUnid() {return this.supUnid_;}
/**
* 赋值 SUP_UNID

* @param paraSupUnid
* SUP_UNID
 */

public void setSupUnid(String paraSupUnid){
  super.recordChanged("SUP_UNID", this.supUnid_, paraSupUnid);
  this.supUnid_ = paraSupUnid;
}/**根据字段名称获取值，如果名称为空或字段未找到，返回空值
 @param filedName 字段名称
 @return 字段值*/
public Object getField(String filedName){
if(filedName == null){ return null; }
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("ADM_ID")){return this.admId_;}
if(n.equalsIgnoreCase("SUP_ID")){return this.supId_;}
if(n.equalsIgnoreCase("SEX_TAG")){return this.sexTag_;}
if(n.equalsIgnoreCase("ADM_USR_STA_TAG")){return this.admUsrStaTag_;}
if(n.equalsIgnoreCase("ADM_DEP_ID")){return this.admDepId_;}
if(n.equalsIgnoreCase("ADM_LID")){return this.admLid_;}
if(n.equalsIgnoreCase("ADM_PWD")){return this.admPwd_;}
if(n.equalsIgnoreCase("ADM_NAME")){return this.admName_;}
if(n.equalsIgnoreCase("ADM_NAME_EN")){return this.admNameEn_;}
if(n.equalsIgnoreCase("ADM_IDCARD")){return this.admIdcard_;}
if(n.equalsIgnoreCase("ADM_TELE")){return this.admTele_;}
if(n.equalsIgnoreCase("ADM_EMAIL")){return this.admEmail_;}
if(n.equalsIgnoreCase("ADM_QQ")){return this.admQq_;}
if(n.equalsIgnoreCase("ADM_MSN")){return this.admMsn_;}
if(n.equalsIgnoreCase("ADM_ICQ")){return this.admIcq_;}
if(n.equalsIgnoreCase("ADM_MOBILE")){return this.admMobile_;}
if(n.equalsIgnoreCase("ADM_ADDR")){return this.admAddr_;}
if(n.equalsIgnoreCase("ADM_CDATE")){return this.admCdate_;}
if(n.equalsIgnoreCase("ADM_MDATE")){return this.admMdate_;}
if(n.equalsIgnoreCase("ADM_LDATE")){return this.admLdate_;}
if(n.equalsIgnoreCase("ADM_LIP")){return this.admLip_;}
if(n.equalsIgnoreCase("ADM_PHOTO")){return this.admPhoto_;}
if(n.equalsIgnoreCase("CRM_COM_ID")){return this.crmComId_;}
if(n.equalsIgnoreCase("PID")){return this.pid_;}
if(n.equalsIgnoreCase("ADM_CHG_PWD")){return this.admChgPwd_;}
if(n.equalsIgnoreCase("ADM_SIGN_PIC")){return this.admSignPic_;}
if(n.equalsIgnoreCase("ADM_UNID")){return this.admUnid_;}
if(n.equalsIgnoreCase("SOURCE")){return this.source_;}
if(n.equalsIgnoreCase("SOURCE_ID")){return this.sourceId_;}
if(n.equalsIgnoreCase("ERP_SKIN")){return this.erpSkin_;}
if(n.equalsIgnoreCase("ADM_OP")){return this.admOp_;}
if(n.equalsIgnoreCase("CRM_CUS_ID")){return this.crmCusId_;}
if(n.equalsIgnoreCase("WEB_USR_ID")){return this.webUsrId_;}
if(n.equalsIgnoreCase("SUR_ID")){return this.surId_;}
if(n.equalsIgnoreCase("IOS_TOKEN")){return this.iosToken_;}
if(n.equalsIgnoreCase("AUS_BRANCHID")){return this.ausBranchid_;}
if(n.equalsIgnoreCase("AUS_POSTALCODE")){return this.ausPostalcode_;}
if(n.equalsIgnoreCase("AUS_TAID")){return this.ausTaid_;}
if(n.equalsIgnoreCase("AUS_TITLE")){return this.ausTitle_;}
if(n.equalsIgnoreCase("AUS_EPASSWORD")){return this.ausEpassword_;}
if(n.equalsIgnoreCase("AUS_STATEORPROVINCE")){return this.ausStateorprovince_;}
if(n.equalsIgnoreCase("AUS_AUTH")){return this.ausAuth_;}
if(n.equalsIgnoreCase("AUS_SMTPSVER")){return this.ausSmtpsver_;}
if(n.equalsIgnoreCase("AUS_TAEID")){return this.ausTaeid_;}
if(n.equalsIgnoreCase("AUS_PHOTO")){return this.ausPhoto_;}
if(n.equalsIgnoreCase("AUS_POSITION")){return this.ausPosition_;}
if(n.equalsIgnoreCase("AUS_ACTIVE")){return this.ausActive_;}
if(n.equalsIgnoreCase("AUS_CITY")){return this.ausCity_;}
if(n.equalsIgnoreCase("IS_AUS")){return this.isAus_;}
if(n.equalsIgnoreCase("SUP_UNID")){return this.supUnid_;}
return null;}
}