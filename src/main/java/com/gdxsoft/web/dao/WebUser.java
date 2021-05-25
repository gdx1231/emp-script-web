package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表WEB_USER映射类
* @author gdx 时间：Tue Mar 10 2020 19:56:15 GMT+0800 (中国标准时间)*/
public class WebUser extends ClassBase{private Integer usrId_; // 编号
private String usrTag_; // USR_TAG
private String areId_; // 地区编号
private String usrLid_; // 登录名
private String usrPwd_; // 密码
private String usrName_; // 姓名
private String usrIdcard_; // 身份证
private String usrTele_; // 电话
private String usrEmail_; // 邮件
private String usrQq_; // QQ
private String usrMsn_; // MSN
private String usrIcq_; // ICQ
private String usrMobile_; // 移动电话
private String usrAddr_; // 住址
private Date usrCdate_; // 创建日期
private Date usrMdate_; // 修改日期
private Date usrLdate_; // 登录日期
private String usrLip_; // 登录IP
private Integer usrLnum_; // 登录次数
private String usrCompany_; // 单位
private String usrFrom_; // 来源
private String usrTitle_; // 称呼
private String usrPic_; // 照片
private String usrPassport_; // 护照
private String usrScm_; // 合作伙伴
private String usrScmUid_; // 合作伙伴用户编号
private String usrScmXml_; // 供应商用户XML描述
private String usrUnid_; // USR_UNID
private Integer sitId_; // SIT_ID
private Integer supId_; // SUP_ID
private Integer usrPid_; // USR_PID
private String recommendName_; // 推荐人
private String recommendPhone_; // 推荐人电话
private String recommendVip_; // 推荐人卡号
private String authWeixinId_; // AUTH_WEIXIN_ID
private String usrSex_; // USR_SEX
private String bbsSysFace_; // BBS_SYS_FACE
private Integer bbsLvl_; // BBS_LVL
private Integer bbsHomeVisCount_; // BBS_HOME_VIS_COUNT
private byte[] usrPicBin_; // USR_PIC_BIN
private String usrNameEn_; // USR_NAME_EN
private String usrParentName_; // USR_PARENT_NAME
private String bbsFaceMethod_; // BBS_FACE_METHOD
private Integer isWeixinSubscribe_; // IS_WEIXIN_SUBSCRIBE
private Integer bbsPostNum_; // BBS_POST_NUM
private Date usrDbo_; // USR_DBO
private Integer bbsReplyNum_; // BBS_REPLY_NUM
private Integer bbsScoreNum_; // BBS_SCORE_NUM
private String bbsNickName_; // BBS_NICK_NAME
private String authWeixinJson_; // AUTH_WEIXIN_JSON
private Integer wxGrp_; // WX_GRP
private String wxId_; // WX_ID
private String favorite_; // FAVORITE
private String active_; // ACTIVE
private String langageLevel_; // LANGAGE_LEVEL
private String about_; // ABOUT
private String buddy_; // BUDDY
private String sync_; // _SYNC_
private String usrTimezone_; // USR_TIMEZONE
private String usrMobileCountryCode_; // USR_MOBILE_COUNTRY_CODE

/**
 * 获取 编号
 *
* @return 编号
*/
public Integer getUsrId() {return this.usrId_;}
/**
* 赋值 编号

* @param paraUsrId
* 编号
 */

public void setUsrId(Integer paraUsrId){
  super.recordChanged("USR_ID", this.usrId_, paraUsrId);
  this.usrId_ = paraUsrId;
}


/**
 * 获取 USR_TAG
 *
* @return USR_TAG
*/
public String getUsrTag() {return this.usrTag_;}
/**
* 赋值 USR_TAG

* @param paraUsrTag
* USR_TAG
 */

public void setUsrTag(String paraUsrTag){
  super.recordChanged("USR_TAG", this.usrTag_, paraUsrTag);
  this.usrTag_ = paraUsrTag;
}


/**
 * 获取 地区编号
 *
* @return 地区编号
*/
public String getAreId() {return this.areId_;}
/**
* 赋值 地区编号

* @param paraAreId
* 地区编号
 */

public void setAreId(String paraAreId){
  super.recordChanged("ARE_ID", this.areId_, paraAreId);
  this.areId_ = paraAreId;
}


/**
 * 获取 登录名
 *
* @return 登录名
*/
public String getUsrLid() {return this.usrLid_;}
/**
* 赋值 登录名

* @param paraUsrLid
* 登录名
 */

public void setUsrLid(String paraUsrLid){
  super.recordChanged("USR_LID", this.usrLid_, paraUsrLid);
  this.usrLid_ = paraUsrLid;
}


/**
 * 获取 密码
 *
* @return 密码
*/
public String getUsrPwd() {return this.usrPwd_;}
/**
* 赋值 密码

* @param paraUsrPwd
* 密码
 */

public void setUsrPwd(String paraUsrPwd){
  super.recordChanged("USR_PWD", this.usrPwd_, paraUsrPwd);
  this.usrPwd_ = paraUsrPwd;
}


/**
 * 获取 姓名
 *
* @return 姓名
*/
public String getUsrName() {return this.usrName_;}
/**
* 赋值 姓名

* @param paraUsrName
* 姓名
 */

public void setUsrName(String paraUsrName){
  super.recordChanged("USR_NAME", this.usrName_, paraUsrName);
  this.usrName_ = paraUsrName;
}


/**
 * 获取 身份证
 *
* @return 身份证
*/
public String getUsrIdcard() {return this.usrIdcard_;}
/**
* 赋值 身份证

* @param paraUsrIdcard
* 身份证
 */

public void setUsrIdcard(String paraUsrIdcard){
  super.recordChanged("USR_IDCARD", this.usrIdcard_, paraUsrIdcard);
  this.usrIdcard_ = paraUsrIdcard;
}


/**
 * 获取 电话
 *
* @return 电话
*/
public String getUsrTele() {return this.usrTele_;}
/**
* 赋值 电话

* @param paraUsrTele
* 电话
 */

public void setUsrTele(String paraUsrTele){
  super.recordChanged("USR_TELE", this.usrTele_, paraUsrTele);
  this.usrTele_ = paraUsrTele;
}


/**
 * 获取 邮件
 *
* @return 邮件
*/
public String getUsrEmail() {return this.usrEmail_;}
/**
* 赋值 邮件

* @param paraUsrEmail
* 邮件
 */

public void setUsrEmail(String paraUsrEmail){
  super.recordChanged("USR_EMAIL", this.usrEmail_, paraUsrEmail);
  this.usrEmail_ = paraUsrEmail;
}


/**
 * 获取 QQ
 *
* @return QQ
*/
public String getUsrQq() {return this.usrQq_;}
/**
* 赋值 QQ

* @param paraUsrQq
* QQ
 */

public void setUsrQq(String paraUsrQq){
  super.recordChanged("USR_QQ", this.usrQq_, paraUsrQq);
  this.usrQq_ = paraUsrQq;
}


/**
 * 获取 MSN
 *
* @return MSN
*/
public String getUsrMsn() {return this.usrMsn_;}
/**
* 赋值 MSN

* @param paraUsrMsn
* MSN
 */

public void setUsrMsn(String paraUsrMsn){
  super.recordChanged("USR_MSN", this.usrMsn_, paraUsrMsn);
  this.usrMsn_ = paraUsrMsn;
}


/**
 * 获取 ICQ
 *
* @return ICQ
*/
public String getUsrIcq() {return this.usrIcq_;}
/**
* 赋值 ICQ

* @param paraUsrIcq
* ICQ
 */

public void setUsrIcq(String paraUsrIcq){
  super.recordChanged("USR_ICQ", this.usrIcq_, paraUsrIcq);
  this.usrIcq_ = paraUsrIcq;
}


/**
 * 获取 移动电话
 *
* @return 移动电话
*/
public String getUsrMobile() {return this.usrMobile_;}
/**
* 赋值 移动电话

* @param paraUsrMobile
* 移动电话
 */

public void setUsrMobile(String paraUsrMobile){
  super.recordChanged("USR_MOBILE", this.usrMobile_, paraUsrMobile);
  this.usrMobile_ = paraUsrMobile;
}


/**
 * 获取 住址
 *
* @return 住址
*/
public String getUsrAddr() {return this.usrAddr_;}
/**
* 赋值 住址

* @param paraUsrAddr
* 住址
 */

public void setUsrAddr(String paraUsrAddr){
  super.recordChanged("USR_ADDR", this.usrAddr_, paraUsrAddr);
  this.usrAddr_ = paraUsrAddr;
}


/**
 * 获取 创建日期
 *
* @return 创建日期
*/
public Date getUsrCdate() {return this.usrCdate_;}
/**
* 赋值 创建日期

* @param paraUsrCdate
* 创建日期
 */

public void setUsrCdate(Date paraUsrCdate){
  super.recordChanged("USR_CDATE", this.usrCdate_, paraUsrCdate);
  this.usrCdate_ = paraUsrCdate;
}


/**
 * 获取 修改日期
 *
* @return 修改日期
*/
public Date getUsrMdate() {return this.usrMdate_;}
/**
* 赋值 修改日期

* @param paraUsrMdate
* 修改日期
 */

public void setUsrMdate(Date paraUsrMdate){
  super.recordChanged("USR_MDATE", this.usrMdate_, paraUsrMdate);
  this.usrMdate_ = paraUsrMdate;
}


/**
 * 获取 登录日期
 *
* @return 登录日期
*/
public Date getUsrLdate() {return this.usrLdate_;}
/**
* 赋值 登录日期

* @param paraUsrLdate
* 登录日期
 */

public void setUsrLdate(Date paraUsrLdate){
  super.recordChanged("USR_LDATE", this.usrLdate_, paraUsrLdate);
  this.usrLdate_ = paraUsrLdate;
}


/**
 * 获取 登录IP
 *
* @return 登录IP
*/
public String getUsrLip() {return this.usrLip_;}
/**
* 赋值 登录IP

* @param paraUsrLip
* 登录IP
 */

public void setUsrLip(String paraUsrLip){
  super.recordChanged("USR_LIP", this.usrLip_, paraUsrLip);
  this.usrLip_ = paraUsrLip;
}


/**
 * 获取 登录次数
 *
* @return 登录次数
*/
public Integer getUsrLnum() {return this.usrLnum_;}
/**
* 赋值 登录次数

* @param paraUsrLnum
* 登录次数
 */

public void setUsrLnum(Integer paraUsrLnum){
  super.recordChanged("USR_LNUM", this.usrLnum_, paraUsrLnum);
  this.usrLnum_ = paraUsrLnum;
}


/**
 * 获取 单位
 *
* @return 单位
*/
public String getUsrCompany() {return this.usrCompany_;}
/**
* 赋值 单位

* @param paraUsrCompany
* 单位
 */

public void setUsrCompany(String paraUsrCompany){
  super.recordChanged("USR_COMPANY", this.usrCompany_, paraUsrCompany);
  this.usrCompany_ = paraUsrCompany;
}


/**
 * 获取 来源
 *
* @return 来源
*/
public String getUsrFrom() {return this.usrFrom_;}
/**
* 赋值 来源

* @param paraUsrFrom
* 来源
 */

public void setUsrFrom(String paraUsrFrom){
  super.recordChanged("USR_FROM", this.usrFrom_, paraUsrFrom);
  this.usrFrom_ = paraUsrFrom;
}


/**
 * 获取 称呼
 *
* @return 称呼
*/
public String getUsrTitle() {return this.usrTitle_;}
/**
* 赋值 称呼

* @param paraUsrTitle
* 称呼
 */

public void setUsrTitle(String paraUsrTitle){
  super.recordChanged("USR_TITLE", this.usrTitle_, paraUsrTitle);
  this.usrTitle_ = paraUsrTitle;
}


/**
 * 获取 照片
 *
* @return 照片
*/
public String getUsrPic() {return this.usrPic_;}
/**
* 赋值 照片

* @param paraUsrPic
* 照片
 */

public void setUsrPic(String paraUsrPic){
  super.recordChanged("USR_PIC", this.usrPic_, paraUsrPic);
  this.usrPic_ = paraUsrPic;
}


/**
 * 获取 护照
 *
* @return 护照
*/
public String getUsrPassport() {return this.usrPassport_;}
/**
* 赋值 护照

* @param paraUsrPassport
* 护照
 */

public void setUsrPassport(String paraUsrPassport){
  super.recordChanged("USR_PASSPORT", this.usrPassport_, paraUsrPassport);
  this.usrPassport_ = paraUsrPassport;
}


/**
 * 获取 合作伙伴
 *
* @return 合作伙伴
*/
public String getUsrScm() {return this.usrScm_;}
/**
* 赋值 合作伙伴

* @param paraUsrScm
* 合作伙伴
 */

public void setUsrScm(String paraUsrScm){
  super.recordChanged("USR_SCM", this.usrScm_, paraUsrScm);
  this.usrScm_ = paraUsrScm;
}


/**
 * 获取 合作伙伴用户编号
 *
* @return 合作伙伴用户编号
*/
public String getUsrScmUid() {return this.usrScmUid_;}
/**
* 赋值 合作伙伴用户编号

* @param paraUsrScmUid
* 合作伙伴用户编号
 */

public void setUsrScmUid(String paraUsrScmUid){
  super.recordChanged("USR_SCM_UID", this.usrScmUid_, paraUsrScmUid);
  this.usrScmUid_ = paraUsrScmUid;
}


/**
 * 获取 供应商用户XML描述
 *
* @return 供应商用户XML描述
*/
public String getUsrScmXml() {return this.usrScmXml_;}
/**
* 赋值 供应商用户XML描述

* @param paraUsrScmXml
* 供应商用户XML描述
 */

public void setUsrScmXml(String paraUsrScmXml){
  super.recordChanged("USR_SCM_XML", this.usrScmXml_, paraUsrScmXml);
  this.usrScmXml_ = paraUsrScmXml;
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
 * 获取 SIT_ID
 *
* @return SIT_ID
*/
public Integer getSitId() {return this.sitId_;}
/**
* 赋值 SIT_ID

* @param paraSitId
* SIT_ID
 */

public void setSitId(Integer paraSitId){
  super.recordChanged("SIT_ID", this.sitId_, paraSitId);
  this.sitId_ = paraSitId;
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
 * 获取 USR_PID
 *
* @return USR_PID
*/
public Integer getUsrPid() {return this.usrPid_;}
/**
* 赋值 USR_PID

* @param paraUsrPid
* USR_PID
 */

public void setUsrPid(Integer paraUsrPid){
  super.recordChanged("USR_PID", this.usrPid_, paraUsrPid);
  this.usrPid_ = paraUsrPid;
}


/**
 * 获取 推荐人
 *
* @return 推荐人
*/
public String getRecommendName() {return this.recommendName_;}
/**
* 赋值 推荐人

* @param paraRecommendName
* 推荐人
 */

public void setRecommendName(String paraRecommendName){
  super.recordChanged("RECOMMEND_NAME", this.recommendName_, paraRecommendName);
  this.recommendName_ = paraRecommendName;
}


/**
 * 获取 推荐人电话
 *
* @return 推荐人电话
*/
public String getRecommendPhone() {return this.recommendPhone_;}
/**
* 赋值 推荐人电话

* @param paraRecommendPhone
* 推荐人电话
 */

public void setRecommendPhone(String paraRecommendPhone){
  super.recordChanged("RECOMMEND_PHONE", this.recommendPhone_, paraRecommendPhone);
  this.recommendPhone_ = paraRecommendPhone;
}


/**
 * 获取 推荐人卡号
 *
* @return 推荐人卡号
*/
public String getRecommendVip() {return this.recommendVip_;}
/**
* 赋值 推荐人卡号

* @param paraRecommendVip
* 推荐人卡号
 */

public void setRecommendVip(String paraRecommendVip){
  super.recordChanged("RECOMMEND_VIP", this.recommendVip_, paraRecommendVip);
  this.recommendVip_ = paraRecommendVip;
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
 * 获取 USR_SEX
 *
* @return USR_SEX
*/
public String getUsrSex() {return this.usrSex_;}
/**
* 赋值 USR_SEX

* @param paraUsrSex
* USR_SEX
 */

public void setUsrSex(String paraUsrSex){
  super.recordChanged("USR_SEX", this.usrSex_, paraUsrSex);
  this.usrSex_ = paraUsrSex;
}


/**
 * 获取 BBS_SYS_FACE
 *
* @return BBS_SYS_FACE
*/
public String getBbsSysFace() {return this.bbsSysFace_;}
/**
* 赋值 BBS_SYS_FACE

* @param paraBbsSysFace
* BBS_SYS_FACE
 */

public void setBbsSysFace(String paraBbsSysFace){
  super.recordChanged("BBS_SYS_FACE", this.bbsSysFace_, paraBbsSysFace);
  this.bbsSysFace_ = paraBbsSysFace;
}


/**
 * 获取 BBS_LVL
 *
* @return BBS_LVL
*/
public Integer getBbsLvl() {return this.bbsLvl_;}
/**
* 赋值 BBS_LVL

* @param paraBbsLvl
* BBS_LVL
 */

public void setBbsLvl(Integer paraBbsLvl){
  super.recordChanged("BBS_LVL", this.bbsLvl_, paraBbsLvl);
  this.bbsLvl_ = paraBbsLvl;
}


/**
 * 获取 BBS_HOME_VIS_COUNT
 *
* @return BBS_HOME_VIS_COUNT
*/
public Integer getBbsHomeVisCount() {return this.bbsHomeVisCount_;}
/**
* 赋值 BBS_HOME_VIS_COUNT

* @param paraBbsHomeVisCount
* BBS_HOME_VIS_COUNT
 */

public void setBbsHomeVisCount(Integer paraBbsHomeVisCount){
  super.recordChanged("BBS_HOME_VIS_COUNT", this.bbsHomeVisCount_, paraBbsHomeVisCount);
  this.bbsHomeVisCount_ = paraBbsHomeVisCount;
}


/**
 * 获取 USR_PIC_BIN
 *
* @return USR_PIC_BIN
*/
public byte[] getUsrPicBin() {return this.usrPicBin_;}
/**
* 赋值 USR_PIC_BIN

* @param paraUsrPicBin
* USR_PIC_BIN
 */

public void setUsrPicBin(byte[] paraUsrPicBin){
  super.recordChanged("USR_PIC_BIN", this.usrPicBin_, paraUsrPicBin);
  this.usrPicBin_ = paraUsrPicBin;
}


/**
 * 获取 USR_NAME_EN
 *
* @return USR_NAME_EN
*/
public String getUsrNameEn() {return this.usrNameEn_;}
/**
* 赋值 USR_NAME_EN

* @param paraUsrNameEn
* USR_NAME_EN
 */

public void setUsrNameEn(String paraUsrNameEn){
  super.recordChanged("USR_NAME_EN", this.usrNameEn_, paraUsrNameEn);
  this.usrNameEn_ = paraUsrNameEn;
}


/**
 * 获取 USR_PARENT_NAME
 *
* @return USR_PARENT_NAME
*/
public String getUsrParentName() {return this.usrParentName_;}
/**
* 赋值 USR_PARENT_NAME

* @param paraUsrParentName
* USR_PARENT_NAME
 */

public void setUsrParentName(String paraUsrParentName){
  super.recordChanged("USR_PARENT_NAME", this.usrParentName_, paraUsrParentName);
  this.usrParentName_ = paraUsrParentName;
}


/**
 * 获取 BBS_FACE_METHOD
 *
* @return BBS_FACE_METHOD
*/
public String getBbsFaceMethod() {return this.bbsFaceMethod_;}
/**
* 赋值 BBS_FACE_METHOD

* @param paraBbsFaceMethod
* BBS_FACE_METHOD
 */

public void setBbsFaceMethod(String paraBbsFaceMethod){
  super.recordChanged("BBS_FACE_METHOD", this.bbsFaceMethod_, paraBbsFaceMethod);
  this.bbsFaceMethod_ = paraBbsFaceMethod;
}


/**
 * 获取 IS_WEIXIN_SUBSCRIBE
 *
* @return IS_WEIXIN_SUBSCRIBE
*/
public Integer getIsWeixinSubscribe() {return this.isWeixinSubscribe_;}
/**
* 赋值 IS_WEIXIN_SUBSCRIBE

* @param paraIsWeixinSubscribe
* IS_WEIXIN_SUBSCRIBE
 */

public void setIsWeixinSubscribe(Integer paraIsWeixinSubscribe){
  super.recordChanged("IS_WEIXIN_SUBSCRIBE", this.isWeixinSubscribe_, paraIsWeixinSubscribe);
  this.isWeixinSubscribe_ = paraIsWeixinSubscribe;
}


/**
 * 获取 BBS_POST_NUM
 *
* @return BBS_POST_NUM
*/
public Integer getBbsPostNum() {return this.bbsPostNum_;}
/**
* 赋值 BBS_POST_NUM

* @param paraBbsPostNum
* BBS_POST_NUM
 */

public void setBbsPostNum(Integer paraBbsPostNum){
  super.recordChanged("BBS_POST_NUM", this.bbsPostNum_, paraBbsPostNum);
  this.bbsPostNum_ = paraBbsPostNum;
}


/**
 * 获取 USR_DBO
 *
* @return USR_DBO
*/
public Date getUsrDbo() {return this.usrDbo_;}
/**
* 赋值 USR_DBO

* @param paraUsrDbo
* USR_DBO
 */

public void setUsrDbo(Date paraUsrDbo){
  super.recordChanged("USR_DBO", this.usrDbo_, paraUsrDbo);
  this.usrDbo_ = paraUsrDbo;
}


/**
 * 获取 BBS_REPLY_NUM
 *
* @return BBS_REPLY_NUM
*/
public Integer getBbsReplyNum() {return this.bbsReplyNum_;}
/**
* 赋值 BBS_REPLY_NUM

* @param paraBbsReplyNum
* BBS_REPLY_NUM
 */

public void setBbsReplyNum(Integer paraBbsReplyNum){
  super.recordChanged("BBS_REPLY_NUM", this.bbsReplyNum_, paraBbsReplyNum);
  this.bbsReplyNum_ = paraBbsReplyNum;
}


/**
 * 获取 BBS_SCORE_NUM
 *
* @return BBS_SCORE_NUM
*/
public Integer getBbsScoreNum() {return this.bbsScoreNum_;}
/**
* 赋值 BBS_SCORE_NUM

* @param paraBbsScoreNum
* BBS_SCORE_NUM
 */

public void setBbsScoreNum(Integer paraBbsScoreNum){
  super.recordChanged("BBS_SCORE_NUM", this.bbsScoreNum_, paraBbsScoreNum);
  this.bbsScoreNum_ = paraBbsScoreNum;
}


/**
 * 获取 BBS_NICK_NAME
 *
* @return BBS_NICK_NAME
*/
public String getBbsNickName() {return this.bbsNickName_;}
/**
* 赋值 BBS_NICK_NAME

* @param paraBbsNickName
* BBS_NICK_NAME
 */

public void setBbsNickName(String paraBbsNickName){
  super.recordChanged("BBS_NICK_NAME", this.bbsNickName_, paraBbsNickName);
  this.bbsNickName_ = paraBbsNickName;
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
 * 获取 WX_GRP
 *
* @return WX_GRP
*/
public Integer getWxGrp() {return this.wxGrp_;}
/**
* 赋值 WX_GRP

* @param paraWxGrp
* WX_GRP
 */

public void setWxGrp(Integer paraWxGrp){
  super.recordChanged("WX_GRP", this.wxGrp_, paraWxGrp);
  this.wxGrp_ = paraWxGrp;
}


/**
 * 获取 WX_ID
 *
* @return WX_ID
*/
public String getWxId() {return this.wxId_;}
/**
* 赋值 WX_ID

* @param paraWxId
* WX_ID
 */

public void setWxId(String paraWxId){
  super.recordChanged("WX_ID", this.wxId_, paraWxId);
  this.wxId_ = paraWxId;
}


/**
 * 获取 FAVORITE
 *
* @return FAVORITE
*/
public String getFavorite() {return this.favorite_;}
/**
* 赋值 FAVORITE

* @param paraFavorite
* FAVORITE
 */

public void setFavorite(String paraFavorite){
  super.recordChanged("FAVORITE", this.favorite_, paraFavorite);
  this.favorite_ = paraFavorite;
}


/**
 * 获取 ACTIVE
 *
* @return ACTIVE
*/
public String getActive() {return this.active_;}
/**
* 赋值 ACTIVE

* @param paraActive
* ACTIVE
 */

public void setActive(String paraActive){
  super.recordChanged("ACTIVE", this.active_, paraActive);
  this.active_ = paraActive;
}


/**
 * 获取 LANGAGE_LEVEL
 *
* @return LANGAGE_LEVEL
*/
public String getLangageLevel() {return this.langageLevel_;}
/**
* 赋值 LANGAGE_LEVEL

* @param paraLangageLevel
* LANGAGE_LEVEL
 */

public void setLangageLevel(String paraLangageLevel){
  super.recordChanged("LANGAGE_LEVEL", this.langageLevel_, paraLangageLevel);
  this.langageLevel_ = paraLangageLevel;
}


/**
 * 获取 ABOUT
 *
* @return ABOUT
*/
public String getAbout() {return this.about_;}
/**
* 赋值 ABOUT

* @param paraAbout
* ABOUT
 */

public void setAbout(String paraAbout){
  super.recordChanged("ABOUT", this.about_, paraAbout);
  this.about_ = paraAbout;
}


/**
 * 获取 BUDDY
 *
* @return BUDDY
*/
public String getBuddy() {return this.buddy_;}
/**
* 赋值 BUDDY

* @param paraBuddy
* BUDDY
 */

public void setBuddy(String paraBuddy){
  super.recordChanged("BUDDY", this.buddy_, paraBuddy);
  this.buddy_ = paraBuddy;
}


/**
 * 获取 _SYNC_
 *
* @return _SYNC_
*/
public String getSync() {return this.sync_;}
/**
* 赋值 _SYNC_

* @param paraSync
* _SYNC_
 */

public void setSync(String paraSync){
  super.recordChanged("_SYNC_", this.sync_, paraSync);
  this.sync_ = paraSync;
}


/**
 * 获取 USR_TIMEZONE
 *
* @return USR_TIMEZONE
*/
public String getUsrTimezone() {return this.usrTimezone_;}
/**
* 赋值 USR_TIMEZONE

* @param paraUsrTimezone
* USR_TIMEZONE
 */

public void setUsrTimezone(String paraUsrTimezone){
  super.recordChanged("USR_TIMEZONE", this.usrTimezone_, paraUsrTimezone);
  this.usrTimezone_ = paraUsrTimezone;
}


/**
 * 获取 USR_MOBILE_COUNTRY_CODE
 *
* @return USR_MOBILE_COUNTRY_CODE
*/
public String getUsrMobileCountryCode() {return this.usrMobileCountryCode_;}
/**
* 赋值 USR_MOBILE_COUNTRY_CODE

* @param paraUsrMobileCountryCode
* USR_MOBILE_COUNTRY_CODE
 */

public void setUsrMobileCountryCode(String paraUsrMobileCountryCode){
  super.recordChanged("USR_MOBILE_COUNTRY_CODE", this.usrMobileCountryCode_, paraUsrMobileCountryCode);
  this.usrMobileCountryCode_ = paraUsrMobileCountryCode;
}/**根据字段名称获取值，如果名称为空或字段未找到，返回空值
 @param filedName 字段名称
 @return 字段值*/
public Object getField(String filedName){
if(filedName == null){ return null; }
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("USR_ID")){return this.usrId_;}
if(n.equalsIgnoreCase("USR_TAG")){return this.usrTag_;}
if(n.equalsIgnoreCase("ARE_ID")){return this.areId_;}
if(n.equalsIgnoreCase("USR_LID")){return this.usrLid_;}
if(n.equalsIgnoreCase("USR_PWD")){return this.usrPwd_;}
if(n.equalsIgnoreCase("USR_NAME")){return this.usrName_;}
if(n.equalsIgnoreCase("USR_IDCARD")){return this.usrIdcard_;}
if(n.equalsIgnoreCase("USR_TELE")){return this.usrTele_;}
if(n.equalsIgnoreCase("USR_EMAIL")){return this.usrEmail_;}
if(n.equalsIgnoreCase("USR_QQ")){return this.usrQq_;}
if(n.equalsIgnoreCase("USR_MSN")){return this.usrMsn_;}
if(n.equalsIgnoreCase("USR_ICQ")){return this.usrIcq_;}
if(n.equalsIgnoreCase("USR_MOBILE")){return this.usrMobile_;}
if(n.equalsIgnoreCase("USR_ADDR")){return this.usrAddr_;}
if(n.equalsIgnoreCase("USR_CDATE")){return this.usrCdate_;}
if(n.equalsIgnoreCase("USR_MDATE")){return this.usrMdate_;}
if(n.equalsIgnoreCase("USR_LDATE")){return this.usrLdate_;}
if(n.equalsIgnoreCase("USR_LIP")){return this.usrLip_;}
if(n.equalsIgnoreCase("USR_LNUM")){return this.usrLnum_;}
if(n.equalsIgnoreCase("USR_COMPANY")){return this.usrCompany_;}
if(n.equalsIgnoreCase("USR_FROM")){return this.usrFrom_;}
if(n.equalsIgnoreCase("USR_TITLE")){return this.usrTitle_;}
if(n.equalsIgnoreCase("USR_PIC")){return this.usrPic_;}
if(n.equalsIgnoreCase("USR_PASSPORT")){return this.usrPassport_;}
if(n.equalsIgnoreCase("USR_SCM")){return this.usrScm_;}
if(n.equalsIgnoreCase("USR_SCM_UID")){return this.usrScmUid_;}
if(n.equalsIgnoreCase("USR_SCM_XML")){return this.usrScmXml_;}
if(n.equalsIgnoreCase("USR_UNID")){return this.usrUnid_;}
if(n.equalsIgnoreCase("SIT_ID")){return this.sitId_;}
if(n.equalsIgnoreCase("SUP_ID")){return this.supId_;}
if(n.equalsIgnoreCase("USR_PID")){return this.usrPid_;}
if(n.equalsIgnoreCase("RECOMMEND_NAME")){return this.recommendName_;}
if(n.equalsIgnoreCase("RECOMMEND_PHONE")){return this.recommendPhone_;}
if(n.equalsIgnoreCase("RECOMMEND_VIP")){return this.recommendVip_;}
if(n.equalsIgnoreCase("AUTH_WEIXIN_ID")){return this.authWeixinId_;}
if(n.equalsIgnoreCase("USR_SEX")){return this.usrSex_;}
if(n.equalsIgnoreCase("BBS_SYS_FACE")){return this.bbsSysFace_;}
if(n.equalsIgnoreCase("BBS_LVL")){return this.bbsLvl_;}
if(n.equalsIgnoreCase("BBS_HOME_VIS_COUNT")){return this.bbsHomeVisCount_;}
if(n.equalsIgnoreCase("USR_PIC_BIN")){return this.usrPicBin_;}
if(n.equalsIgnoreCase("USR_NAME_EN")){return this.usrNameEn_;}
if(n.equalsIgnoreCase("USR_PARENT_NAME")){return this.usrParentName_;}
if(n.equalsIgnoreCase("BBS_FACE_METHOD")){return this.bbsFaceMethod_;}
if(n.equalsIgnoreCase("IS_WEIXIN_SUBSCRIBE")){return this.isWeixinSubscribe_;}
if(n.equalsIgnoreCase("BBS_POST_NUM")){return this.bbsPostNum_;}
if(n.equalsIgnoreCase("USR_DBO")){return this.usrDbo_;}
if(n.equalsIgnoreCase("BBS_REPLY_NUM")){return this.bbsReplyNum_;}
if(n.equalsIgnoreCase("BBS_SCORE_NUM")){return this.bbsScoreNum_;}
if(n.equalsIgnoreCase("BBS_NICK_NAME")){return this.bbsNickName_;}
if(n.equalsIgnoreCase("AUTH_WEIXIN_JSON")){return this.authWeixinJson_;}
if(n.equalsIgnoreCase("WX_GRP")){return this.wxGrp_;}
if(n.equalsIgnoreCase("WX_ID")){return this.wxId_;}
if(n.equalsIgnoreCase("FAVORITE")){return this.favorite_;}
if(n.equalsIgnoreCase("ACTIVE")){return this.active_;}
if(n.equalsIgnoreCase("LANGAGE_LEVEL")){return this.langageLevel_;}
if(n.equalsIgnoreCase("ABOUT")){return this.about_;}
if(n.equalsIgnoreCase("BUDDY")){return this.buddy_;}
if(n.equalsIgnoreCase("_SYNC_")){return this.sync_;}
if(n.equalsIgnoreCase("USR_TIMEZONE")){return this.usrTimezone_;}
if(n.equalsIgnoreCase("USR_MOBILE_COUNTRY_CODE")){return this.usrMobileCountryCode_;}
return null;}
}