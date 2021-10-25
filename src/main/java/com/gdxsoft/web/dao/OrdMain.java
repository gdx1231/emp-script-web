package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表ord_main映射类
* @author gdx 时间：Fri May 08 2020 11:28:04 GMT+0800 (中国标准时间)*/
public class OrdMain extends ClassBase{private Integer ordId_; // ORD_ID
private Integer supId_; // 处理订单供应商
private Integer usrId_; // 编号
private String ordName_; // ORD_NAME
private String ordUsrStaTag_; // 订单用户处理状态
private String ordAdmStaTag_; // 管理员处理订单状态
private String ordPayTag_; // 付款标记
private String srvTypTag_; // 订单类标记
private String ordPayStaTag_; // 订单支付状态
private Double ordPrice_; // 价格
private Double ordPayMoney_; // 支付
private String ordSpec_; // 用户嘱咐
private String ordInvNeed_; // 需要发票
private String ordInvTitle_; // 发票抬头
private String ordUsrName_; // 订单用户名称
private String ordUsrPhone_; // 订单用户电话
private Date ordDate_; // 订单日期
private Date ordCdate_; // 订单创建日期
private Date ordMdate_; // 订单修改日期
private String ordTypeMain_; // 订单主要类型
private String ordExt0_; // 扩展参数1
private String ordExt1_; // 扩展参数2
private String ordExt2_; // 扩展参数3
private String ordExt3_; // ORD_EXT3
private String ordExt4_; // ORD_EXT4
private String ordExt5_; // ORD_EXT5
private String ordExt6_; // ORD_EXT6
private String ordExt7_; // ORD_EXT7
private String ordExt8_; // ORD_EXT8
private String ordExt9_; // ORD_EXT9
private String ordMailAddr_; // 邮件地址
private String ordMailZip_; // 邮编
private String ordIsInner_; // 是否内部订单
private Integer ordInnAdmid_; // 内部订单管理员
private String ordInnMemo_; // 订单内部备注
private Integer scmPrjId_; // 内部项目编号
private String ordUid_; // ORD_UID
private Integer ordFromSupid_; // ORD_FROM_SUPID
private String ordInvCnt_; // ORD_INV_CNT
private Integer ordCoin_; // ORD_COIN
private String siteUnid_; // SITE_UNID
private Integer serId_; // 来源服务
private Double oriOrdPrice_; // 原始总价
private Integer oriOrdCoin_; // 原始货币
private Integer ordFromAdmid_; // 供应商下单人
private String payTagSelected_; // 选择的支付方式
private String ordUsrEmail_; // 订单人联系邮件
private String ordPickUp_; // ORD_PICK_UP
private Integer ordFromUsrId_; // 来源用户
private String ordHttpIp_; // ip地址
private String ordHttpUa_; // 浏览器
private String ordHttpRef_; // 订单来源网页
private String ordHttpJsp_; // 下单执行文件

/**
 * 获取 ORD_ID
 *
* @return ORD_ID
*/
public Integer getOrdId() {return this.ordId_;}
/**
* 赋值 ORD_ID

* @param paraOrdId
* ORD_ID
 */

public void setOrdId(Integer paraOrdId){
  super.recordChanged("ORD_ID", this.ordId_, paraOrdId);
  this.ordId_ = paraOrdId;
}


/**
 * 获取 处理订单供应商
 *
* @return 处理订单供应商
*/
public Integer getSupId() {return this.supId_;}
/**
* 赋值 处理订单供应商

* @param paraSupId
* 处理订单供应商
 */

public void setSupId(Integer paraSupId){
  super.recordChanged("SUP_ID", this.supId_, paraSupId);
  this.supId_ = paraSupId;
}


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
 * 获取 ORD_NAME
 *
* @return ORD_NAME
*/
public String getOrdName() {return this.ordName_;}
/**
* 赋值 ORD_NAME

* @param paraOrdName
* ORD_NAME
 */

public void setOrdName(String paraOrdName){
  super.recordChanged("ORD_NAME", this.ordName_, paraOrdName);
  this.ordName_ = paraOrdName;
}


/**
 * 获取 订单用户处理状态
 *
* @return 订单用户处理状态
*/
public String getOrdUsrStaTag() {return this.ordUsrStaTag_;}
/**
* 赋值 订单用户处理状态

* @param paraOrdUsrStaTag
* 订单用户处理状态
 */

public void setOrdUsrStaTag(String paraOrdUsrStaTag){
  super.recordChanged("ORD_USR_STA_TAG", this.ordUsrStaTag_, paraOrdUsrStaTag);
  this.ordUsrStaTag_ = paraOrdUsrStaTag;
}


/**
 * 获取 管理员处理订单状态
 *
* @return 管理员处理订单状态
*/
public String getOrdAdmStaTag() {return this.ordAdmStaTag_;}
/**
* 赋值 管理员处理订单状态

* @param paraOrdAdmStaTag
* 管理员处理订单状态
 */

public void setOrdAdmStaTag(String paraOrdAdmStaTag){
  super.recordChanged("ORD_ADM_STA_TAG", this.ordAdmStaTag_, paraOrdAdmStaTag);
  this.ordAdmStaTag_ = paraOrdAdmStaTag;
}


/**
 * 获取 付款标记
 *
* @return 付款标记
*/
public String getOrdPayTag() {return this.ordPayTag_;}
/**
* 赋值 付款标记

* @param paraOrdPayTag
* 付款标记
 */

public void setOrdPayTag(String paraOrdPayTag){
  super.recordChanged("ORD_PAY_TAG", this.ordPayTag_, paraOrdPayTag);
  this.ordPayTag_ = paraOrdPayTag;
}


/**
 * 获取 订单类标记
 *
* @return 订单类标记
*/
public String getSrvTypTag() {return this.srvTypTag_;}
/**
* 赋值 订单类标记

* @param paraSrvTypTag
* 订单类标记
 */

public void setSrvTypTag(String paraSrvTypTag){
  super.recordChanged("SRV_TYP_TAG", this.srvTypTag_, paraSrvTypTag);
  this.srvTypTag_ = paraSrvTypTag;
}


/**
 * 获取 订单支付状态
 *
* @return 订单支付状态
*/
public String getOrdPayStaTag() {return this.ordPayStaTag_;}
/**
* 赋值 订单支付状态

* @param paraOrdPayStaTag
* 订单支付状态
 */

public void setOrdPayStaTag(String paraOrdPayStaTag){
  super.recordChanged("ORD_PAY_STA_TAG", this.ordPayStaTag_, paraOrdPayStaTag);
  this.ordPayStaTag_ = paraOrdPayStaTag;
}


/**
 * 获取 价格
 *
* @return 价格
*/
public Double getOrdPrice() {return this.ordPrice_;}
/**
* 赋值 价格

* @param paraOrdPrice
* 价格
 */

public void setOrdPrice(Double paraOrdPrice){
  super.recordChanged("ORD_PRICE", this.ordPrice_, paraOrdPrice);
  this.ordPrice_ = paraOrdPrice;
}


/**
 * 获取 支付
 *
* @return 支付
*/
public Double getOrdPayMoney() {return this.ordPayMoney_;}
/**
* 赋值 支付

* @param paraOrdPayMoney
* 支付
 */

public void setOrdPayMoney(Double paraOrdPayMoney){
  super.recordChanged("ORD_PAY_MONEY", this.ordPayMoney_, paraOrdPayMoney);
  this.ordPayMoney_ = paraOrdPayMoney;
}


/**
 * 获取 用户嘱咐
 *
* @return 用户嘱咐
*/
public String getOrdSpec() {return this.ordSpec_;}
/**
* 赋值 用户嘱咐

* @param paraOrdSpec
* 用户嘱咐
 */

public void setOrdSpec(String paraOrdSpec){
  super.recordChanged("ORD_SPEC", this.ordSpec_, paraOrdSpec);
  this.ordSpec_ = paraOrdSpec;
}


/**
 * 获取 需要发票
 *
* @return 需要发票
*/
public String getOrdInvNeed() {return this.ordInvNeed_;}
/**
* 赋值 需要发票

* @param paraOrdInvNeed
* 需要发票
 */

public void setOrdInvNeed(String paraOrdInvNeed){
  super.recordChanged("ORD_INV_NEED", this.ordInvNeed_, paraOrdInvNeed);
  this.ordInvNeed_ = paraOrdInvNeed;
}


/**
 * 获取 发票抬头
 *
* @return 发票抬头
*/
public String getOrdInvTitle() {return this.ordInvTitle_;}
/**
* 赋值 发票抬头

* @param paraOrdInvTitle
* 发票抬头
 */

public void setOrdInvTitle(String paraOrdInvTitle){
  super.recordChanged("ORD_INV_TITLE", this.ordInvTitle_, paraOrdInvTitle);
  this.ordInvTitle_ = paraOrdInvTitle;
}


/**
 * 获取 订单用户名称
 *
* @return 订单用户名称
*/
public String getOrdUsrName() {return this.ordUsrName_;}
/**
* 赋值 订单用户名称

* @param paraOrdUsrName
* 订单用户名称
 */

public void setOrdUsrName(String paraOrdUsrName){
  super.recordChanged("ORD_USR_NAME", this.ordUsrName_, paraOrdUsrName);
  this.ordUsrName_ = paraOrdUsrName;
}


/**
 * 获取 订单用户电话
 *
* @return 订单用户电话
*/
public String getOrdUsrPhone() {return this.ordUsrPhone_;}
/**
* 赋值 订单用户电话

* @param paraOrdUsrPhone
* 订单用户电话
 */

public void setOrdUsrPhone(String paraOrdUsrPhone){
  super.recordChanged("ORD_USR_PHONE", this.ordUsrPhone_, paraOrdUsrPhone);
  this.ordUsrPhone_ = paraOrdUsrPhone;
}


/**
 * 获取 订单日期
 *
* @return 订单日期
*/
public Date getOrdDate() {return this.ordDate_;}
/**
* 赋值 订单日期

* @param paraOrdDate
* 订单日期
 */

public void setOrdDate(Date paraOrdDate){
  super.recordChanged("ORD_DATE", this.ordDate_, paraOrdDate);
  this.ordDate_ = paraOrdDate;
}


/**
 * 获取 订单创建日期
 *
* @return 订单创建日期
*/
public Date getOrdCdate() {return this.ordCdate_;}
/**
* 赋值 订单创建日期

* @param paraOrdCdate
* 订单创建日期
 */

public void setOrdCdate(Date paraOrdCdate){
  super.recordChanged("ORD_CDATE", this.ordCdate_, paraOrdCdate);
  this.ordCdate_ = paraOrdCdate;
}


/**
 * 获取 订单修改日期
 *
* @return 订单修改日期
*/
public Date getOrdMdate() {return this.ordMdate_;}
/**
* 赋值 订单修改日期

* @param paraOrdMdate
* 订单修改日期
 */

public void setOrdMdate(Date paraOrdMdate){
  super.recordChanged("ORD_MDATE", this.ordMdate_, paraOrdMdate);
  this.ordMdate_ = paraOrdMdate;
}


/**
 * 获取 订单主要类型
 *
* @return 订单主要类型
*/
public String getOrdTypeMain() {return this.ordTypeMain_;}
/**
* 赋值 订单主要类型

* @param paraOrdTypeMain
* 订单主要类型
 */

public void setOrdTypeMain(String paraOrdTypeMain){
  super.recordChanged("ORD_TYPE_MAIN", this.ordTypeMain_, paraOrdTypeMain);
  this.ordTypeMain_ = paraOrdTypeMain;
}


/**
 * 获取 扩展参数1
 *
* @return 扩展参数1
*/
public String getOrdExt0() {return this.ordExt0_;}
/**
* 赋值 扩展参数1

* @param paraOrdExt0
* 扩展参数1
 */

public void setOrdExt0(String paraOrdExt0){
  super.recordChanged("ORD_EXT0", this.ordExt0_, paraOrdExt0);
  this.ordExt0_ = paraOrdExt0;
}


/**
 * 获取 扩展参数2
 *
* @return 扩展参数2
*/
public String getOrdExt1() {return this.ordExt1_;}
/**
* 赋值 扩展参数2

* @param paraOrdExt1
* 扩展参数2
 */

public void setOrdExt1(String paraOrdExt1){
  super.recordChanged("ORD_EXT1", this.ordExt1_, paraOrdExt1);
  this.ordExt1_ = paraOrdExt1;
}


/**
 * 获取 扩展参数3
 *
* @return 扩展参数3
*/
public String getOrdExt2() {return this.ordExt2_;}
/**
* 赋值 扩展参数3

* @param paraOrdExt2
* 扩展参数3
 */

public void setOrdExt2(String paraOrdExt2){
  super.recordChanged("ORD_EXT2", this.ordExt2_, paraOrdExt2);
  this.ordExt2_ = paraOrdExt2;
}


/**
 * 获取 ORD_EXT3
 *
* @return ORD_EXT3
*/
public String getOrdExt3() {return this.ordExt3_;}
/**
* 赋值 ORD_EXT3

* @param paraOrdExt3
* ORD_EXT3
 */

public void setOrdExt3(String paraOrdExt3){
  super.recordChanged("ORD_EXT3", this.ordExt3_, paraOrdExt3);
  this.ordExt3_ = paraOrdExt3;
}


/**
 * 获取 ORD_EXT4
 *
* @return ORD_EXT4
*/
public String getOrdExt4() {return this.ordExt4_;}
/**
* 赋值 ORD_EXT4

* @param paraOrdExt4
* ORD_EXT4
 */

public void setOrdExt4(String paraOrdExt4){
  super.recordChanged("ORD_EXT4", this.ordExt4_, paraOrdExt4);
  this.ordExt4_ = paraOrdExt4;
}


/**
 * 获取 ORD_EXT5
 *
* @return ORD_EXT5
*/
public String getOrdExt5() {return this.ordExt5_;}
/**
* 赋值 ORD_EXT5

* @param paraOrdExt5
* ORD_EXT5
 */

public void setOrdExt5(String paraOrdExt5){
  super.recordChanged("ORD_EXT5", this.ordExt5_, paraOrdExt5);
  this.ordExt5_ = paraOrdExt5;
}


/**
 * 获取 ORD_EXT6
 *
* @return ORD_EXT6
*/
public String getOrdExt6() {return this.ordExt6_;}
/**
* 赋值 ORD_EXT6

* @param paraOrdExt6
* ORD_EXT6
 */

public void setOrdExt6(String paraOrdExt6){
  super.recordChanged("ORD_EXT6", this.ordExt6_, paraOrdExt6);
  this.ordExt6_ = paraOrdExt6;
}


/**
 * 获取 ORD_EXT7
 *
* @return ORD_EXT7
*/
public String getOrdExt7() {return this.ordExt7_;}
/**
* 赋值 ORD_EXT7

* @param paraOrdExt7
* ORD_EXT7
 */

public void setOrdExt7(String paraOrdExt7){
  super.recordChanged("ORD_EXT7", this.ordExt7_, paraOrdExt7);
  this.ordExt7_ = paraOrdExt7;
}


/**
 * 获取 ORD_EXT8
 *
* @return ORD_EXT8
*/
public String getOrdExt8() {return this.ordExt8_;}
/**
* 赋值 ORD_EXT8

* @param paraOrdExt8
* ORD_EXT8
 */

public void setOrdExt8(String paraOrdExt8){
  super.recordChanged("ORD_EXT8", this.ordExt8_, paraOrdExt8);
  this.ordExt8_ = paraOrdExt8;
}


/**
 * 获取 ORD_EXT9
 *
* @return ORD_EXT9
*/
public String getOrdExt9() {return this.ordExt9_;}
/**
* 赋值 ORD_EXT9

* @param paraOrdExt9
* ORD_EXT9
 */

public void setOrdExt9(String paraOrdExt9){
  super.recordChanged("ORD_EXT9", this.ordExt9_, paraOrdExt9);
  this.ordExt9_ = paraOrdExt9;
}


/**
 * 获取 邮件地址
 *
* @return 邮件地址
*/
public String getOrdMailAddr() {return this.ordMailAddr_;}
/**
* 赋值 邮件地址

* @param paraOrdMailAddr
* 邮件地址
 */

public void setOrdMailAddr(String paraOrdMailAddr){
  super.recordChanged("ORD_MAIL_ADDR", this.ordMailAddr_, paraOrdMailAddr);
  this.ordMailAddr_ = paraOrdMailAddr;
}


/**
 * 获取 邮编
 *
* @return 邮编
*/
public String getOrdMailZip() {return this.ordMailZip_;}
/**
* 赋值 邮编

* @param paraOrdMailZip
* 邮编
 */

public void setOrdMailZip(String paraOrdMailZip){
  super.recordChanged("ORD_MAIL_ZIP", this.ordMailZip_, paraOrdMailZip);
  this.ordMailZip_ = paraOrdMailZip;
}


/**
 * 获取 是否内部订单
 *
* @return 是否内部订单
*/
public String getOrdIsInner() {return this.ordIsInner_;}
/**
* 赋值 是否内部订单

* @param paraOrdIsInner
* 是否内部订单
 */

public void setOrdIsInner(String paraOrdIsInner){
  super.recordChanged("ORD_IS_INNER", this.ordIsInner_, paraOrdIsInner);
  this.ordIsInner_ = paraOrdIsInner;
}


/**
 * 获取 内部订单管理员
 *
* @return 内部订单管理员
*/
public Integer getOrdInnAdmid() {return this.ordInnAdmid_;}
/**
* 赋值 内部订单管理员

* @param paraOrdInnAdmid
* 内部订单管理员
 */

public void setOrdInnAdmid(Integer paraOrdInnAdmid){
  super.recordChanged("ORD_INN_ADMID", this.ordInnAdmid_, paraOrdInnAdmid);
  this.ordInnAdmid_ = paraOrdInnAdmid;
}


/**
 * 获取 订单内部备注
 *
* @return 订单内部备注
*/
public String getOrdInnMemo() {return this.ordInnMemo_;}
/**
* 赋值 订单内部备注

* @param paraOrdInnMemo
* 订单内部备注
 */

public void setOrdInnMemo(String paraOrdInnMemo){
  super.recordChanged("ORD_INN_MEMO", this.ordInnMemo_, paraOrdInnMemo);
  this.ordInnMemo_ = paraOrdInnMemo;
}


/**
 * 获取 内部项目编号
 *
* @return 内部项目编号
*/
public Integer getScmPrjId() {return this.scmPrjId_;}
/**
* 赋值 内部项目编号

* @param paraScmPrjId
* 内部项目编号
 */

public void setScmPrjId(Integer paraScmPrjId){
  super.recordChanged("SCM_PRJ_ID", this.scmPrjId_, paraScmPrjId);
  this.scmPrjId_ = paraScmPrjId;
}


/**
 * 获取 ORD_UID
 *
* @return ORD_UID
*/
public String getOrdUid() {return this.ordUid_;}
/**
* 赋值 ORD_UID

* @param paraOrdUid
* ORD_UID
 */

public void setOrdUid(String paraOrdUid){
  super.recordChanged("ORD_UID", this.ordUid_, paraOrdUid);
  this.ordUid_ = paraOrdUid;
}


/**
 * 获取 ORD_FROM_SUPID
 *
* @return ORD_FROM_SUPID
*/
public Integer getOrdFromSupid() {return this.ordFromSupid_;}
/**
* 赋值 ORD_FROM_SUPID

* @param paraOrdFromSupid
* ORD_FROM_SUPID
 */

public void setOrdFromSupid(Integer paraOrdFromSupid){
  super.recordChanged("ORD_FROM_SUPID", this.ordFromSupid_, paraOrdFromSupid);
  this.ordFromSupid_ = paraOrdFromSupid;
}


/**
 * 获取 ORD_INV_CNT
 *
* @return ORD_INV_CNT
*/
public String getOrdInvCnt() {return this.ordInvCnt_;}
/**
* 赋值 ORD_INV_CNT

* @param paraOrdInvCnt
* ORD_INV_CNT
 */

public void setOrdInvCnt(String paraOrdInvCnt){
  super.recordChanged("ORD_INV_CNT", this.ordInvCnt_, paraOrdInvCnt);
  this.ordInvCnt_ = paraOrdInvCnt;
}


/**
 * 获取 ORD_COIN
 *
* @return ORD_COIN
*/
public Integer getOrdCoin() {return this.ordCoin_;}
/**
* 赋值 ORD_COIN

* @param paraOrdCoin
* ORD_COIN
 */

public void setOrdCoin(Integer paraOrdCoin){
  super.recordChanged("ORD_COIN", this.ordCoin_, paraOrdCoin);
  this.ordCoin_ = paraOrdCoin;
}


/**
 * 获取 SITE_UNID
 *
* @return SITE_UNID
*/
public String getSiteUnid() {return this.siteUnid_;}
/**
* 赋值 SITE_UNID

* @param paraSiteUnid
* SITE_UNID
 */

public void setSiteUnid(String paraSiteUnid){
  super.recordChanged("SITE_UNID", this.siteUnid_, paraSiteUnid);
  this.siteUnid_ = paraSiteUnid;
}


/**
 * 获取 来源服务
 *
* @return 来源服务
*/
public Integer getSerId() {return this.serId_;}
/**
* 赋值 来源服务

* @param paraSerId
* 来源服务
 */

public void setSerId(Integer paraSerId){
  super.recordChanged("SER_ID", this.serId_, paraSerId);
  this.serId_ = paraSerId;
}


/**
 * 获取 原始总价
 *
* @return 原始总价
*/
public Double getOriOrdPrice() {return this.oriOrdPrice_;}
/**
* 赋值 原始总价

* @param paraOriOrdPrice
* 原始总价
 */

public void setOriOrdPrice(Double paraOriOrdPrice){
  super.recordChanged("ORI_ORD_PRICE", this.oriOrdPrice_, paraOriOrdPrice);
  this.oriOrdPrice_ = paraOriOrdPrice;
}


/**
 * 获取 原始货币
 *
* @return 原始货币
*/
public Integer getOriOrdCoin() {return this.oriOrdCoin_;}
/**
* 赋值 原始货币

* @param paraOriOrdCoin
* 原始货币
 */

public void setOriOrdCoin(Integer paraOriOrdCoin){
  super.recordChanged("ORI_ORD_COIN", this.oriOrdCoin_, paraOriOrdCoin);
  this.oriOrdCoin_ = paraOriOrdCoin;
}


/**
 * 获取 供应商下单人
 *
* @return 供应商下单人
*/
public Integer getOrdFromAdmid() {return this.ordFromAdmid_;}
/**
* 赋值 供应商下单人

* @param paraOrdFromAdmid
* 供应商下单人
 */

public void setOrdFromAdmid(Integer paraOrdFromAdmid){
  super.recordChanged("ORD_FROM_ADMID", this.ordFromAdmid_, paraOrdFromAdmid);
  this.ordFromAdmid_ = paraOrdFromAdmid;
}


/**
 * 获取 选择的支付方式
 *
* @return 选择的支付方式
*/
public String getPayTagSelected() {return this.payTagSelected_;}
/**
* 赋值 选择的支付方式

* @param paraPayTagSelected
* 选择的支付方式
 */

public void setPayTagSelected(String paraPayTagSelected){
  super.recordChanged("PAY_TAG_SELECTED", this.payTagSelected_, paraPayTagSelected);
  this.payTagSelected_ = paraPayTagSelected;
}


/**
 * 获取 订单人联系邮件
 *
* @return 订单人联系邮件
*/
public String getOrdUsrEmail() {return this.ordUsrEmail_;}
/**
* 赋值 订单人联系邮件

* @param paraOrdUsrEmail
* 订单人联系邮件
 */

public void setOrdUsrEmail(String paraOrdUsrEmail){
  super.recordChanged("ORD_USR_EMAIL", this.ordUsrEmail_, paraOrdUsrEmail);
  this.ordUsrEmail_ = paraOrdUsrEmail;
}


/**
 * 获取 ORD_PICK_UP
 *
* @return ORD_PICK_UP
*/
public String getOrdPickUp() {return this.ordPickUp_;}
/**
* 赋值 ORD_PICK_UP

* @param paraOrdPickUp
* ORD_PICK_UP
 */

public void setOrdPickUp(String paraOrdPickUp){
  super.recordChanged("ORD_PICK_UP", this.ordPickUp_, paraOrdPickUp);
  this.ordPickUp_ = paraOrdPickUp;
}


/**
 * 获取 来源用户
 *
* @return 来源用户
*/
public Integer getOrdFromUsrId() {return this.ordFromUsrId_;}
/**
* 赋值 来源用户

* @param paraOrdFromUsrId
* 来源用户
 */

public void setOrdFromUsrId(Integer paraOrdFromUsrId){
  super.recordChanged("ORD_FROM_USR_ID", this.ordFromUsrId_, paraOrdFromUsrId);
  this.ordFromUsrId_ = paraOrdFromUsrId;
}


/**
 * 获取 ip地址
 *
* @return ip地址
*/
public String getOrdHttpIp() {return this.ordHttpIp_;}
/**
* 赋值 ip地址

* @param paraOrdHttpIp
* ip地址
 */

public void setOrdHttpIp(String paraOrdHttpIp){
  super.recordChanged("ORD_HTTP_IP", this.ordHttpIp_, paraOrdHttpIp);
  this.ordHttpIp_ = paraOrdHttpIp;
}


/**
 * 获取 浏览器
 *
* @return 浏览器
*/
public String getOrdHttpUa() {return this.ordHttpUa_;}
/**
* 赋值 浏览器

* @param paraOrdHttpUa
* 浏览器
 */

public void setOrdHttpUa(String paraOrdHttpUa){
  super.recordChanged("ORD_HTTP_UA", this.ordHttpUa_, paraOrdHttpUa);
  this.ordHttpUa_ = paraOrdHttpUa;
}


/**
 * 获取 订单来源网页
 *
* @return 订单来源网页
*/
public String getOrdHttpRef() {return this.ordHttpRef_;}
/**
* 赋值 订单来源网页

* @param paraOrdHttpRef
* 订单来源网页
 */

public void setOrdHttpRef(String paraOrdHttpRef){
  super.recordChanged("ORD_HTTP_REF", this.ordHttpRef_, paraOrdHttpRef);
  this.ordHttpRef_ = paraOrdHttpRef;
}


/**
 * 获取 下单执行文件
 *
* @return 下单执行文件
*/
public String getOrdHttpJsp() {return this.ordHttpJsp_;}
/**
* 赋值 下单执行文件

* @param paraOrdHttpJsp
* 下单执行文件
 */

public void setOrdHttpJsp(String paraOrdHttpJsp){
  super.recordChanged("ORD_HTTP_JSP", this.ordHttpJsp_, paraOrdHttpJsp);
  this.ordHttpJsp_ = paraOrdHttpJsp;
}/**根据字段名称获取值，如果名称为空或字段未找到，返回空值
 @param filedName 字段名称
 @return 字段值*/
public Object getField(String filedName){
if(filedName == null){ return null; }
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("ORD_ID")){return this.ordId_;}
if(n.equalsIgnoreCase("SUP_ID")){return this.supId_;}
if(n.equalsIgnoreCase("USR_ID")){return this.usrId_;}
if(n.equalsIgnoreCase("ORD_NAME")){return this.ordName_;}
if(n.equalsIgnoreCase("ORD_USR_STA_TAG")){return this.ordUsrStaTag_;}
if(n.equalsIgnoreCase("ORD_ADM_STA_TAG")){return this.ordAdmStaTag_;}
if(n.equalsIgnoreCase("ORD_PAY_TAG")){return this.ordPayTag_;}
if(n.equalsIgnoreCase("SRV_TYP_TAG")){return this.srvTypTag_;}
if(n.equalsIgnoreCase("ORD_PAY_STA_TAG")){return this.ordPayStaTag_;}
if(n.equalsIgnoreCase("ORD_PRICE")){return this.ordPrice_;}
if(n.equalsIgnoreCase("ORD_PAY_MONEY")){return this.ordPayMoney_;}
if(n.equalsIgnoreCase("ORD_SPEC")){return this.ordSpec_;}
if(n.equalsIgnoreCase("ORD_INV_NEED")){return this.ordInvNeed_;}
if(n.equalsIgnoreCase("ORD_INV_TITLE")){return this.ordInvTitle_;}
if(n.equalsIgnoreCase("ORD_USR_NAME")){return this.ordUsrName_;}
if(n.equalsIgnoreCase("ORD_USR_PHONE")){return this.ordUsrPhone_;}
if(n.equalsIgnoreCase("ORD_DATE")){return this.ordDate_;}
if(n.equalsIgnoreCase("ORD_CDATE")){return this.ordCdate_;}
if(n.equalsIgnoreCase("ORD_MDATE")){return this.ordMdate_;}
if(n.equalsIgnoreCase("ORD_TYPE_MAIN")){return this.ordTypeMain_;}
if(n.equalsIgnoreCase("ORD_EXT0")){return this.ordExt0_;}
if(n.equalsIgnoreCase("ORD_EXT1")){return this.ordExt1_;}
if(n.equalsIgnoreCase("ORD_EXT2")){return this.ordExt2_;}
if(n.equalsIgnoreCase("ORD_EXT3")){return this.ordExt3_;}
if(n.equalsIgnoreCase("ORD_EXT4")){return this.ordExt4_;}
if(n.equalsIgnoreCase("ORD_EXT5")){return this.ordExt5_;}
if(n.equalsIgnoreCase("ORD_EXT6")){return this.ordExt6_;}
if(n.equalsIgnoreCase("ORD_EXT7")){return this.ordExt7_;}
if(n.equalsIgnoreCase("ORD_EXT8")){return this.ordExt8_;}
if(n.equalsIgnoreCase("ORD_EXT9")){return this.ordExt9_;}
if(n.equalsIgnoreCase("ORD_MAIL_ADDR")){return this.ordMailAddr_;}
if(n.equalsIgnoreCase("ORD_MAIL_ZIP")){return this.ordMailZip_;}
if(n.equalsIgnoreCase("ORD_IS_INNER")){return this.ordIsInner_;}
if(n.equalsIgnoreCase("ORD_INN_ADMID")){return this.ordInnAdmid_;}
if(n.equalsIgnoreCase("ORD_INN_MEMO")){return this.ordInnMemo_;}
if(n.equalsIgnoreCase("SCM_PRJ_ID")){return this.scmPrjId_;}
if(n.equalsIgnoreCase("ORD_UID")){return this.ordUid_;}
if(n.equalsIgnoreCase("ORD_FROM_SUPID")){return this.ordFromSupid_;}
if(n.equalsIgnoreCase("ORD_INV_CNT")){return this.ordInvCnt_;}
if(n.equalsIgnoreCase("ORD_COIN")){return this.ordCoin_;}
if(n.equalsIgnoreCase("SITE_UNID")){return this.siteUnid_;}
if(n.equalsIgnoreCase("SER_ID")){return this.serId_;}
if(n.equalsIgnoreCase("ORI_ORD_PRICE")){return this.oriOrdPrice_;}
if(n.equalsIgnoreCase("ORI_ORD_COIN")){return this.oriOrdCoin_;}
if(n.equalsIgnoreCase("ORD_FROM_ADMID")){return this.ordFromAdmid_;}
if(n.equalsIgnoreCase("PAY_TAG_SELECTED")){return this.payTagSelected_;}
if(n.equalsIgnoreCase("ORD_USR_EMAIL")){return this.ordUsrEmail_;}
if(n.equalsIgnoreCase("ORD_PICK_UP")){return this.ordPickUp_;}
if(n.equalsIgnoreCase("ORD_FROM_USR_ID")){return this.ordFromUsrId_;}
if(n.equalsIgnoreCase("ORD_HTTP_IP")){return this.ordHttpIp_;}
if(n.equalsIgnoreCase("ORD_HTTP_UA")){return this.ordHttpUa_;}
if(n.equalsIgnoreCase("ORD_HTTP_REF")){return this.ordHttpRef_;}
if(n.equalsIgnoreCase("ORD_HTTP_JSP")){return this.ordHttpJsp_;}
return null;}
}