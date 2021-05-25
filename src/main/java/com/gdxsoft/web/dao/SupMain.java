package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表SUP_MAIN映射类
* @author gdx 时间：Tue Feb 12 2019 10:53:53 GMT+0800 (中国标准时间)*/
public class SupMain extends ClassBase{private Integer supId_; // 供应商编号
private Integer supPid_; // SUP_PID
private String areId_; // 地区编号
private String supTag_; // 供应商标记
private String supMtag_; // 标记
private String supName_; // 名称
private String supAddr_; // 地址
private String supTele_; // 电话
private String supFax_; // 传真
private String supEmail_; // 邮件
private String supHttp_; // 网站
private String supMobile_; // 手机
private String supQq_; // QQ号
private String supMsn_; // MSN
private String supIcq_; // ICQ
private String supTaobao_; // 淘宝号
private String supPaypal_; // PayPal
private String supZip_; // 邮编
private String supMemo_; // 备注
private byte[] supPact_; // SUP_PACT
private byte[] supLicence_; // SUP_LICENCE
private String supAgProfile_; // SUP_AG_PROFILE
private String supAgOrder_; // SUP_AG_ORDER
private String supAgCode_; // SUP_AG_CODE
private byte[] supLogo_; // SUP_LOGO
private String supPostUrl_; // SUP_POST_URL
private String supPostEmail_; // SUP_POST_EMAIL
private String serTag1_; // SER_TAG1
private Integer cityId1_; // CITY_ID1
private String signTag_; // SIGN_TAG
private Date signTime_; // SIGN_TIME
private String signPrice_; // SIGN_PRICE
private String supAreDes_; // SUP_ARE_DES
private String supUnid_; // SUP_UNID
private Integer supCoinId_; // SUP_COIN_ID
private String supScmOpen_; // SUP_SCM_OPEN
private Date supScmOpenSdate_; // SUP_SCM_OPEN_SDATE
private Date supScmOpenEdate_; // SUP_SCM_OPEN_EDATE
private String supGrpCodeTmp_; // SUP_GRP_CODE_TMP
private String supScmMnus_; // SUP_SCM_MNUS
private String supScmSrvs_; // SUP_SCM_SRVS
private byte[] supShopKey_; // SUP_SHOP_KEY
private String businessNumber_; // BUSINESS_NUMBER
private Integer supCancellationDays_; // SUP_CANCELLATION_DAYS
private Integer supLat_; // SUP_LAT
private Integer supLng_; // SUP_LNG
private String supOpenHours_; // SUP_OPEN_HOURS
private Double supPorterage_; // SUP_PORTERAGE
private String supQuery_; // SUP_QUERY
private String supStar_; // SUP_STAR
private String supState_; // SUP_STATE
private String supTerms_; // SUP_TERMS
private Integer supTermsDays_; // SUP_TERMS_DAYS
private String supUseState_; // SUP_USE_STATE
private String supOwner_; // SUP_OWNER
private String supFacebook_; // SUP_FACEBOOK
private String supTwitter_; // SUP_TWITTER
private String ausDirectorofsales_; // AUS_DIRECTOROFSALES
private Double ausContractrate_; // AUS_CONTRACTRATE
private Double ausSellingprice_; // AUS_SELLINGPRICE
private Integer supTaid_; // SUP_TAID
private Integer isAus_; // IS_AUS
private Double ausRackrate_; // AUS_RACKRATE
private String ausAccreditation_; // AUS_ACCREDITATION
private Integer supSalesId_; // SUP_SALES_ID
private Integer ausDays_; // AUS_DAYS
private Double ausUnitprice2_; // AUS_UNITPRICE2
private Double ausUnitprice1_; // AUS_UNITPRICE1
private String ausRpting_; // AUS_RPTING
private Double supCreditlimit_; // SUP_CREDITLIMIT
private Integer supEmployeeid_; // SUP_EMPLOYEEID
private Double ausIncentiverate_; // AUS_INCENTIVERATE
private Double ausBkft_; // AUS_BKFT
private String supDrivinglicence_; // SUP_DRIVINGLICENCE
private Double supMarkup_; // SUP_MARKUP
private Integer ausFocpolicy2_; // AUS_FOCPOLICY2
private Integer ausFocpolicy1_; // AUS_FOCPOLICY1
private Integer ausFquot_; // AUS_FQUOT
private String ausAccountno_; // AUS_ACCOUNTNO
private Double ausDisc_; // AUS_DISC
private Double ausPorterage_; // AUS_PORTERAGE
private String ausOversea_; // AUS_OVERSEA
private Double ausBkftc_; // AUS_BKFTC
private Double supMargin_; // SUP_MARGIN
private Integer ausDiscountdays_; // AUS_DISCOUNTDAYS
private String ausContacttitle_; // AUS_CONTACTTITLE
private String supNameEn_; // SUP_NAME_EN

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
 * 获取 SUP_PID
 *
* @return SUP_PID
*/
public Integer getSupPid() {return this.supPid_;}
/**
* 赋值 SUP_PID

* @param paraSupPid
* SUP_PID
 */

public void setSupPid(Integer paraSupPid){
  super.recordChanged("SUP_PID", this.supPid_, paraSupPid);
  this.supPid_ = paraSupPid;
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
 * 获取 供应商标记
 *
* @return 供应商标记
*/
public String getSupTag() {return this.supTag_;}
/**
* 赋值 供应商标记

* @param paraSupTag
* 供应商标记
 */

public void setSupTag(String paraSupTag){
  super.recordChanged("SUP_TAG", this.supTag_, paraSupTag);
  this.supTag_ = paraSupTag;
}


/**
 * 获取 标记
 *
* @return 标记
*/
public String getSupMtag() {return this.supMtag_;}
/**
* 赋值 标记

* @param paraSupMtag
* 标记
 */

public void setSupMtag(String paraSupMtag){
  super.recordChanged("SUP_MTAG", this.supMtag_, paraSupMtag);
  this.supMtag_ = paraSupMtag;
}


/**
 * 获取 名称
 *
* @return 名称
*/
public String getSupName() {return this.supName_;}
/**
* 赋值 名称

* @param paraSupName
* 名称
 */

public void setSupName(String paraSupName){
  super.recordChanged("SUP_NAME", this.supName_, paraSupName);
  this.supName_ = paraSupName;
}


/**
 * 获取 地址
 *
* @return 地址
*/
public String getSupAddr() {return this.supAddr_;}
/**
* 赋值 地址

* @param paraSupAddr
* 地址
 */

public void setSupAddr(String paraSupAddr){
  super.recordChanged("SUP_ADDR", this.supAddr_, paraSupAddr);
  this.supAddr_ = paraSupAddr;
}


/**
 * 获取 电话
 *
* @return 电话
*/
public String getSupTele() {return this.supTele_;}
/**
* 赋值 电话

* @param paraSupTele
* 电话
 */

public void setSupTele(String paraSupTele){
  super.recordChanged("SUP_TELE", this.supTele_, paraSupTele);
  this.supTele_ = paraSupTele;
}


/**
 * 获取 传真
 *
* @return 传真
*/
public String getSupFax() {return this.supFax_;}
/**
* 赋值 传真

* @param paraSupFax
* 传真
 */

public void setSupFax(String paraSupFax){
  super.recordChanged("SUP_FAX", this.supFax_, paraSupFax);
  this.supFax_ = paraSupFax;
}


/**
 * 获取 邮件
 *
* @return 邮件
*/
public String getSupEmail() {return this.supEmail_;}
/**
* 赋值 邮件

* @param paraSupEmail
* 邮件
 */

public void setSupEmail(String paraSupEmail){
  super.recordChanged("SUP_EMAIL", this.supEmail_, paraSupEmail);
  this.supEmail_ = paraSupEmail;
}


/**
 * 获取 网站
 *
* @return 网站
*/
public String getSupHttp() {return this.supHttp_;}
/**
* 赋值 网站

* @param paraSupHttp
* 网站
 */

public void setSupHttp(String paraSupHttp){
  super.recordChanged("SUP_HTTP", this.supHttp_, paraSupHttp);
  this.supHttp_ = paraSupHttp;
}


/**
 * 获取 手机
 *
* @return 手机
*/
public String getSupMobile() {return this.supMobile_;}
/**
* 赋值 手机

* @param paraSupMobile
* 手机
 */

public void setSupMobile(String paraSupMobile){
  super.recordChanged("SUP_MOBILE", this.supMobile_, paraSupMobile);
  this.supMobile_ = paraSupMobile;
}


/**
 * 获取 QQ号
 *
* @return QQ号
*/
public String getSupQq() {return this.supQq_;}
/**
* 赋值 QQ号

* @param paraSupQq
* QQ号
 */

public void setSupQq(String paraSupQq){
  super.recordChanged("SUP_QQ", this.supQq_, paraSupQq);
  this.supQq_ = paraSupQq;
}


/**
 * 获取 MSN
 *
* @return MSN
*/
public String getSupMsn() {return this.supMsn_;}
/**
* 赋值 MSN

* @param paraSupMsn
* MSN
 */

public void setSupMsn(String paraSupMsn){
  super.recordChanged("SUP_MSN", this.supMsn_, paraSupMsn);
  this.supMsn_ = paraSupMsn;
}


/**
 * 获取 ICQ
 *
* @return ICQ
*/
public String getSupIcq() {return this.supIcq_;}
/**
* 赋值 ICQ

* @param paraSupIcq
* ICQ
 */

public void setSupIcq(String paraSupIcq){
  super.recordChanged("SUP_ICQ", this.supIcq_, paraSupIcq);
  this.supIcq_ = paraSupIcq;
}


/**
 * 获取 淘宝号
 *
* @return 淘宝号
*/
public String getSupTaobao() {return this.supTaobao_;}
/**
* 赋值 淘宝号

* @param paraSupTaobao
* 淘宝号
 */

public void setSupTaobao(String paraSupTaobao){
  super.recordChanged("SUP_TAOBAO", this.supTaobao_, paraSupTaobao);
  this.supTaobao_ = paraSupTaobao;
}


/**
 * 获取 PayPal
 *
* @return PayPal
*/
public String getSupPaypal() {return this.supPaypal_;}
/**
* 赋值 PayPal

* @param paraSupPaypal
* PayPal
 */

public void setSupPaypal(String paraSupPaypal){
  super.recordChanged("SUP_PAYPAL", this.supPaypal_, paraSupPaypal);
  this.supPaypal_ = paraSupPaypal;
}


/**
 * 获取 邮编
 *
* @return 邮编
*/
public String getSupZip() {return this.supZip_;}
/**
* 赋值 邮编

* @param paraSupZip
* 邮编
 */

public void setSupZip(String paraSupZip){
  super.recordChanged("SUP_ZIP", this.supZip_, paraSupZip);
  this.supZip_ = paraSupZip;
}


/**
 * 获取 备注
 *
* @return 备注
*/
public String getSupMemo() {return this.supMemo_;}
/**
* 赋值 备注

* @param paraSupMemo
* 备注
 */

public void setSupMemo(String paraSupMemo){
  super.recordChanged("SUP_MEMO", this.supMemo_, paraSupMemo);
  this.supMemo_ = paraSupMemo;
}


/**
 * 获取 SUP_PACT
 *
* @return SUP_PACT
*/
public byte[] getSupPact() {return this.supPact_;}
/**
* 赋值 SUP_PACT

* @param paraSupPact
* SUP_PACT
 */

public void setSupPact(byte[] paraSupPact){
  super.recordChanged("SUP_PACT", this.supPact_, paraSupPact);
  this.supPact_ = paraSupPact;
}


/**
 * 获取 SUP_LICENCE
 *
* @return SUP_LICENCE
*/
public byte[] getSupLicence() {return this.supLicence_;}
/**
* 赋值 SUP_LICENCE

* @param paraSupLicence
* SUP_LICENCE
 */

public void setSupLicence(byte[] paraSupLicence){
  super.recordChanged("SUP_LICENCE", this.supLicence_, paraSupLicence);
  this.supLicence_ = paraSupLicence;
}


/**
 * 获取 SUP_AG_PROFILE
 *
* @return SUP_AG_PROFILE
*/
public String getSupAgProfile() {return this.supAgProfile_;}
/**
* 赋值 SUP_AG_PROFILE

* @param paraSupAgProfile
* SUP_AG_PROFILE
 */

public void setSupAgProfile(String paraSupAgProfile){
  super.recordChanged("SUP_AG_PROFILE", this.supAgProfile_, paraSupAgProfile);
  this.supAgProfile_ = paraSupAgProfile;
}


/**
 * 获取 SUP_AG_ORDER
 *
* @return SUP_AG_ORDER
*/
public String getSupAgOrder() {return this.supAgOrder_;}
/**
* 赋值 SUP_AG_ORDER

* @param paraSupAgOrder
* SUP_AG_ORDER
 */

public void setSupAgOrder(String paraSupAgOrder){
  super.recordChanged("SUP_AG_ORDER", this.supAgOrder_, paraSupAgOrder);
  this.supAgOrder_ = paraSupAgOrder;
}


/**
 * 获取 SUP_AG_CODE
 *
* @return SUP_AG_CODE
*/
public String getSupAgCode() {return this.supAgCode_;}
/**
* 赋值 SUP_AG_CODE

* @param paraSupAgCode
* SUP_AG_CODE
 */

public void setSupAgCode(String paraSupAgCode){
  super.recordChanged("SUP_AG_CODE", this.supAgCode_, paraSupAgCode);
  this.supAgCode_ = paraSupAgCode;
}


/**
 * 获取 SUP_LOGO
 *
* @return SUP_LOGO
*/
public byte[] getSupLogo() {return this.supLogo_;}
/**
* 赋值 SUP_LOGO

* @param paraSupLogo
* SUP_LOGO
 */

public void setSupLogo(byte[] paraSupLogo){
  super.recordChanged("SUP_LOGO", this.supLogo_, paraSupLogo);
  this.supLogo_ = paraSupLogo;
}


/**
 * 获取 SUP_POST_URL
 *
* @return SUP_POST_URL
*/
public String getSupPostUrl() {return this.supPostUrl_;}
/**
* 赋值 SUP_POST_URL

* @param paraSupPostUrl
* SUP_POST_URL
 */

public void setSupPostUrl(String paraSupPostUrl){
  super.recordChanged("SUP_POST_URL", this.supPostUrl_, paraSupPostUrl);
  this.supPostUrl_ = paraSupPostUrl;
}


/**
 * 获取 SUP_POST_EMAIL
 *
* @return SUP_POST_EMAIL
*/
public String getSupPostEmail() {return this.supPostEmail_;}
/**
* 赋值 SUP_POST_EMAIL

* @param paraSupPostEmail
* SUP_POST_EMAIL
 */

public void setSupPostEmail(String paraSupPostEmail){
  super.recordChanged("SUP_POST_EMAIL", this.supPostEmail_, paraSupPostEmail);
  this.supPostEmail_ = paraSupPostEmail;
}


/**
 * 获取 SER_TAG1
 *
* @return SER_TAG1
*/
public String getSerTag1() {return this.serTag1_;}
/**
* 赋值 SER_TAG1

* @param paraSerTag1
* SER_TAG1
 */

public void setSerTag1(String paraSerTag1){
  super.recordChanged("SER_TAG1", this.serTag1_, paraSerTag1);
  this.serTag1_ = paraSerTag1;
}


/**
 * 获取 CITY_ID1
 *
* @return CITY_ID1
*/
public Integer getCityId1() {return this.cityId1_;}
/**
* 赋值 CITY_ID1

* @param paraCityId1
* CITY_ID1
 */

public void setCityId1(Integer paraCityId1){
  super.recordChanged("CITY_ID1", this.cityId1_, paraCityId1);
  this.cityId1_ = paraCityId1;
}


/**
 * 获取 SIGN_TAG
 *
* @return SIGN_TAG
*/
public String getSignTag() {return this.signTag_;}
/**
* 赋值 SIGN_TAG

* @param paraSignTag
* SIGN_TAG
 */

public void setSignTag(String paraSignTag){
  super.recordChanged("SIGN_TAG", this.signTag_, paraSignTag);
  this.signTag_ = paraSignTag;
}


/**
 * 获取 SIGN_TIME
 *
* @return SIGN_TIME
*/
public Date getSignTime() {return this.signTime_;}
/**
* 赋值 SIGN_TIME

* @param paraSignTime
* SIGN_TIME
 */

public void setSignTime(Date paraSignTime){
  super.recordChanged("SIGN_TIME", this.signTime_, paraSignTime);
  this.signTime_ = paraSignTime;
}


/**
 * 获取 SIGN_PRICE
 *
* @return SIGN_PRICE
*/
public String getSignPrice() {return this.signPrice_;}
/**
* 赋值 SIGN_PRICE

* @param paraSignPrice
* SIGN_PRICE
 */

public void setSignPrice(String paraSignPrice){
  super.recordChanged("SIGN_PRICE", this.signPrice_, paraSignPrice);
  this.signPrice_ = paraSignPrice;
}


/**
 * 获取 SUP_ARE_DES
 *
* @return SUP_ARE_DES
*/
public String getSupAreDes() {return this.supAreDes_;}
/**
* 赋值 SUP_ARE_DES

* @param paraSupAreDes
* SUP_ARE_DES
 */

public void setSupAreDes(String paraSupAreDes){
  super.recordChanged("SUP_ARE_DES", this.supAreDes_, paraSupAreDes);
  this.supAreDes_ = paraSupAreDes;
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
}


/**
 * 获取 SUP_COIN_ID
 *
* @return SUP_COIN_ID
*/
public Integer getSupCoinId() {return this.supCoinId_;}
/**
* 赋值 SUP_COIN_ID

* @param paraSupCoinId
* SUP_COIN_ID
 */

public void setSupCoinId(Integer paraSupCoinId){
  super.recordChanged("SUP_COIN_ID", this.supCoinId_, paraSupCoinId);
  this.supCoinId_ = paraSupCoinId;
}


/**
 * 获取 SUP_SCM_OPEN
 *
* @return SUP_SCM_OPEN
*/
public String getSupScmOpen() {return this.supScmOpen_;}
/**
* 赋值 SUP_SCM_OPEN

* @param paraSupScmOpen
* SUP_SCM_OPEN
 */

public void setSupScmOpen(String paraSupScmOpen){
  super.recordChanged("SUP_SCM_OPEN", this.supScmOpen_, paraSupScmOpen);
  this.supScmOpen_ = paraSupScmOpen;
}


/**
 * 获取 SUP_SCM_OPEN_SDATE
 *
* @return SUP_SCM_OPEN_SDATE
*/
public Date getSupScmOpenSdate() {return this.supScmOpenSdate_;}
/**
* 赋值 SUP_SCM_OPEN_SDATE

* @param paraSupScmOpenSdate
* SUP_SCM_OPEN_SDATE
 */

public void setSupScmOpenSdate(Date paraSupScmOpenSdate){
  super.recordChanged("SUP_SCM_OPEN_SDATE", this.supScmOpenSdate_, paraSupScmOpenSdate);
  this.supScmOpenSdate_ = paraSupScmOpenSdate;
}


/**
 * 获取 SUP_SCM_OPEN_EDATE
 *
* @return SUP_SCM_OPEN_EDATE
*/
public Date getSupScmOpenEdate() {return this.supScmOpenEdate_;}
/**
* 赋值 SUP_SCM_OPEN_EDATE

* @param paraSupScmOpenEdate
* SUP_SCM_OPEN_EDATE
 */

public void setSupScmOpenEdate(Date paraSupScmOpenEdate){
  super.recordChanged("SUP_SCM_OPEN_EDATE", this.supScmOpenEdate_, paraSupScmOpenEdate);
  this.supScmOpenEdate_ = paraSupScmOpenEdate;
}


/**
 * 获取 SUP_GRP_CODE_TMP
 *
* @return SUP_GRP_CODE_TMP
*/
public String getSupGrpCodeTmp() {return this.supGrpCodeTmp_;}
/**
* 赋值 SUP_GRP_CODE_TMP

* @param paraSupGrpCodeTmp
* SUP_GRP_CODE_TMP
 */

public void setSupGrpCodeTmp(String paraSupGrpCodeTmp){
  super.recordChanged("SUP_GRP_CODE_TMP", this.supGrpCodeTmp_, paraSupGrpCodeTmp);
  this.supGrpCodeTmp_ = paraSupGrpCodeTmp;
}


/**
 * 获取 SUP_SCM_MNUS
 *
* @return SUP_SCM_MNUS
*/
public String getSupScmMnus() {return this.supScmMnus_;}
/**
* 赋值 SUP_SCM_MNUS

* @param paraSupScmMnus
* SUP_SCM_MNUS
 */

public void setSupScmMnus(String paraSupScmMnus){
  super.recordChanged("SUP_SCM_MNUS", this.supScmMnus_, paraSupScmMnus);
  this.supScmMnus_ = paraSupScmMnus;
}


/**
 * 获取 SUP_SCM_SRVS
 *
* @return SUP_SCM_SRVS
*/
public String getSupScmSrvs() {return this.supScmSrvs_;}
/**
* 赋值 SUP_SCM_SRVS

* @param paraSupScmSrvs
* SUP_SCM_SRVS
 */

public void setSupScmSrvs(String paraSupScmSrvs){
  super.recordChanged("SUP_SCM_SRVS", this.supScmSrvs_, paraSupScmSrvs);
  this.supScmSrvs_ = paraSupScmSrvs;
}


/**
 * 获取 SUP_SHOP_KEY
 *
* @return SUP_SHOP_KEY
*/
public byte[] getSupShopKey() {return this.supShopKey_;}
/**
* 赋值 SUP_SHOP_KEY

* @param paraSupShopKey
* SUP_SHOP_KEY
 */

public void setSupShopKey(byte[] paraSupShopKey){
  super.recordChanged("SUP_SHOP_KEY", this.supShopKey_, paraSupShopKey);
  this.supShopKey_ = paraSupShopKey;
}


/**
 * 获取 BUSINESS_NUMBER
 *
* @return BUSINESS_NUMBER
*/
public String getBusinessNumber() {return this.businessNumber_;}
/**
* 赋值 BUSINESS_NUMBER

* @param paraBusinessNumber
* BUSINESS_NUMBER
 */

public void setBusinessNumber(String paraBusinessNumber){
  super.recordChanged("BUSINESS_NUMBER", this.businessNumber_, paraBusinessNumber);
  this.businessNumber_ = paraBusinessNumber;
}


/**
 * 获取 SUP_CANCELLATION_DAYS
 *
* @return SUP_CANCELLATION_DAYS
*/
public Integer getSupCancellationDays() {return this.supCancellationDays_;}
/**
* 赋值 SUP_CANCELLATION_DAYS

* @param paraSupCancellationDays
* SUP_CANCELLATION_DAYS
 */

public void setSupCancellationDays(Integer paraSupCancellationDays){
  super.recordChanged("SUP_CANCELLATION_DAYS", this.supCancellationDays_, paraSupCancellationDays);
  this.supCancellationDays_ = paraSupCancellationDays;
}


/**
 * 获取 SUP_LAT
 *
* @return SUP_LAT
*/
public Integer getSupLat() {return this.supLat_;}
/**
* 赋值 SUP_LAT

* @param paraSupLat
* SUP_LAT
 */

public void setSupLat(Integer paraSupLat){
  super.recordChanged("SUP_LAT", this.supLat_, paraSupLat);
  this.supLat_ = paraSupLat;
}


/**
 * 获取 SUP_LNG
 *
* @return SUP_LNG
*/
public Integer getSupLng() {return this.supLng_;}
/**
* 赋值 SUP_LNG

* @param paraSupLng
* SUP_LNG
 */

public void setSupLng(Integer paraSupLng){
  super.recordChanged("SUP_LNG", this.supLng_, paraSupLng);
  this.supLng_ = paraSupLng;
}


/**
 * 获取 SUP_OPEN_HOURS
 *
* @return SUP_OPEN_HOURS
*/
public String getSupOpenHours() {return this.supOpenHours_;}
/**
* 赋值 SUP_OPEN_HOURS

* @param paraSupOpenHours
* SUP_OPEN_HOURS
 */

public void setSupOpenHours(String paraSupOpenHours){
  super.recordChanged("SUP_OPEN_HOURS", this.supOpenHours_, paraSupOpenHours);
  this.supOpenHours_ = paraSupOpenHours;
}


/**
 * 获取 SUP_PORTERAGE
 *
* @return SUP_PORTERAGE
*/
public Double getSupPorterage() {return this.supPorterage_;}
/**
* 赋值 SUP_PORTERAGE

* @param paraSupPorterage
* SUP_PORTERAGE
 */

public void setSupPorterage(Double paraSupPorterage){
  super.recordChanged("SUP_PORTERAGE", this.supPorterage_, paraSupPorterage);
  this.supPorterage_ = paraSupPorterage;
}


/**
 * 获取 SUP_QUERY
 *
* @return SUP_QUERY
*/
public String getSupQuery() {return this.supQuery_;}
/**
* 赋值 SUP_QUERY

* @param paraSupQuery
* SUP_QUERY
 */

public void setSupQuery(String paraSupQuery){
  super.recordChanged("SUP_QUERY", this.supQuery_, paraSupQuery);
  this.supQuery_ = paraSupQuery;
}


/**
 * 获取 SUP_STAR
 *
* @return SUP_STAR
*/
public String getSupStar() {return this.supStar_;}
/**
* 赋值 SUP_STAR

* @param paraSupStar
* SUP_STAR
 */

public void setSupStar(String paraSupStar){
  super.recordChanged("SUP_STAR", this.supStar_, paraSupStar);
  this.supStar_ = paraSupStar;
}


/**
 * 获取 SUP_STATE
 *
* @return SUP_STATE
*/
public String getSupState() {return this.supState_;}
/**
* 赋值 SUP_STATE

* @param paraSupState
* SUP_STATE
 */

public void setSupState(String paraSupState){
  super.recordChanged("SUP_STATE", this.supState_, paraSupState);
  this.supState_ = paraSupState;
}


/**
 * 获取 SUP_TERMS
 *
* @return SUP_TERMS
*/
public String getSupTerms() {return this.supTerms_;}
/**
* 赋值 SUP_TERMS

* @param paraSupTerms
* SUP_TERMS
 */

public void setSupTerms(String paraSupTerms){
  super.recordChanged("SUP_TERMS", this.supTerms_, paraSupTerms);
  this.supTerms_ = paraSupTerms;
}


/**
 * 获取 SUP_TERMS_DAYS
 *
* @return SUP_TERMS_DAYS
*/
public Integer getSupTermsDays() {return this.supTermsDays_;}
/**
* 赋值 SUP_TERMS_DAYS

* @param paraSupTermsDays
* SUP_TERMS_DAYS
 */

public void setSupTermsDays(Integer paraSupTermsDays){
  super.recordChanged("SUP_TERMS_DAYS", this.supTermsDays_, paraSupTermsDays);
  this.supTermsDays_ = paraSupTermsDays;
}


/**
 * 获取 SUP_USE_STATE
 *
* @return SUP_USE_STATE
*/
public String getSupUseState() {return this.supUseState_;}
/**
* 赋值 SUP_USE_STATE

* @param paraSupUseState
* SUP_USE_STATE
 */

public void setSupUseState(String paraSupUseState){
  super.recordChanged("SUP_USE_STATE", this.supUseState_, paraSupUseState);
  this.supUseState_ = paraSupUseState;
}


/**
 * 获取 SUP_OWNER
 *
* @return SUP_OWNER
*/
public String getSupOwner() {return this.supOwner_;}
/**
* 赋值 SUP_OWNER

* @param paraSupOwner
* SUP_OWNER
 */

public void setSupOwner(String paraSupOwner){
  super.recordChanged("SUP_OWNER", this.supOwner_, paraSupOwner);
  this.supOwner_ = paraSupOwner;
}


/**
 * 获取 SUP_FACEBOOK
 *
* @return SUP_FACEBOOK
*/
public String getSupFacebook() {return this.supFacebook_;}
/**
* 赋值 SUP_FACEBOOK

* @param paraSupFacebook
* SUP_FACEBOOK
 */

public void setSupFacebook(String paraSupFacebook){
  super.recordChanged("SUP_FACEBOOK", this.supFacebook_, paraSupFacebook);
  this.supFacebook_ = paraSupFacebook;
}


/**
 * 获取 SUP_TWITTER
 *
* @return SUP_TWITTER
*/
public String getSupTwitter() {return this.supTwitter_;}
/**
* 赋值 SUP_TWITTER

* @param paraSupTwitter
* SUP_TWITTER
 */

public void setSupTwitter(String paraSupTwitter){
  super.recordChanged("SUP_TWITTER", this.supTwitter_, paraSupTwitter);
  this.supTwitter_ = paraSupTwitter;
}


/**
 * 获取 AUS_DIRECTOROFSALES
 *
* @return AUS_DIRECTOROFSALES
*/
public String getAusDirectorofsales() {return this.ausDirectorofsales_;}
/**
* 赋值 AUS_DIRECTOROFSALES

* @param paraAusDirectorofsales
* AUS_DIRECTOROFSALES
 */

public void setAusDirectorofsales(String paraAusDirectorofsales){
  super.recordChanged("AUS_DIRECTOROFSALES", this.ausDirectorofsales_, paraAusDirectorofsales);
  this.ausDirectorofsales_ = paraAusDirectorofsales;
}


/**
 * 获取 AUS_CONTRACTRATE
 *
* @return AUS_CONTRACTRATE
*/
public Double getAusContractrate() {return this.ausContractrate_;}
/**
* 赋值 AUS_CONTRACTRATE

* @param paraAusContractrate
* AUS_CONTRACTRATE
 */

public void setAusContractrate(Double paraAusContractrate){
  super.recordChanged("AUS_CONTRACTRATE", this.ausContractrate_, paraAusContractrate);
  this.ausContractrate_ = paraAusContractrate;
}


/**
 * 获取 AUS_SELLINGPRICE
 *
* @return AUS_SELLINGPRICE
*/
public Double getAusSellingprice() {return this.ausSellingprice_;}
/**
* 赋值 AUS_SELLINGPRICE

* @param paraAusSellingprice
* AUS_SELLINGPRICE
 */

public void setAusSellingprice(Double paraAusSellingprice){
  super.recordChanged("AUS_SELLINGPRICE", this.ausSellingprice_, paraAusSellingprice);
  this.ausSellingprice_ = paraAusSellingprice;
}


/**
 * 获取 SUP_TAID
 *
* @return SUP_TAID
*/
public Integer getSupTaid() {return this.supTaid_;}
/**
* 赋值 SUP_TAID

* @param paraSupTaid
* SUP_TAID
 */

public void setSupTaid(Integer paraSupTaid){
  super.recordChanged("SUP_TAID", this.supTaid_, paraSupTaid);
  this.supTaid_ = paraSupTaid;
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
 * 获取 AUS_RACKRATE
 *
* @return AUS_RACKRATE
*/
public Double getAusRackrate() {return this.ausRackrate_;}
/**
* 赋值 AUS_RACKRATE

* @param paraAusRackrate
* AUS_RACKRATE
 */

public void setAusRackrate(Double paraAusRackrate){
  super.recordChanged("AUS_RACKRATE", this.ausRackrate_, paraAusRackrate);
  this.ausRackrate_ = paraAusRackrate;
}


/**
 * 获取 AUS_ACCREDITATION
 *
* @return AUS_ACCREDITATION
*/
public String getAusAccreditation() {return this.ausAccreditation_;}
/**
* 赋值 AUS_ACCREDITATION

* @param paraAusAccreditation
* AUS_ACCREDITATION
 */

public void setAusAccreditation(String paraAusAccreditation){
  super.recordChanged("AUS_ACCREDITATION", this.ausAccreditation_, paraAusAccreditation);
  this.ausAccreditation_ = paraAusAccreditation;
}


/**
 * 获取 SUP_SALES_ID
 *
* @return SUP_SALES_ID
*/
public Integer getSupSalesId() {return this.supSalesId_;}
/**
* 赋值 SUP_SALES_ID

* @param paraSupSalesId
* SUP_SALES_ID
 */

public void setSupSalesId(Integer paraSupSalesId){
  super.recordChanged("SUP_SALES_ID", this.supSalesId_, paraSupSalesId);
  this.supSalesId_ = paraSupSalesId;
}


/**
 * 获取 AUS_DAYS
 *
* @return AUS_DAYS
*/
public Integer getAusDays() {return this.ausDays_;}
/**
* 赋值 AUS_DAYS

* @param paraAusDays
* AUS_DAYS
 */

public void setAusDays(Integer paraAusDays){
  super.recordChanged("AUS_DAYS", this.ausDays_, paraAusDays);
  this.ausDays_ = paraAusDays;
}


/**
 * 获取 AUS_UNITPRICE2
 *
* @return AUS_UNITPRICE2
*/
public Double getAusUnitprice2() {return this.ausUnitprice2_;}
/**
* 赋值 AUS_UNITPRICE2

* @param paraAusUnitprice2
* AUS_UNITPRICE2
 */

public void setAusUnitprice2(Double paraAusUnitprice2){
  super.recordChanged("AUS_UNITPRICE2", this.ausUnitprice2_, paraAusUnitprice2);
  this.ausUnitprice2_ = paraAusUnitprice2;
}


/**
 * 获取 AUS_UNITPRICE1
 *
* @return AUS_UNITPRICE1
*/
public Double getAusUnitprice1() {return this.ausUnitprice1_;}
/**
* 赋值 AUS_UNITPRICE1

* @param paraAusUnitprice1
* AUS_UNITPRICE1
 */

public void setAusUnitprice1(Double paraAusUnitprice1){
  super.recordChanged("AUS_UNITPRICE1", this.ausUnitprice1_, paraAusUnitprice1);
  this.ausUnitprice1_ = paraAusUnitprice1;
}


/**
 * 获取 AUS_RPTING
 *
* @return AUS_RPTING
*/
public String getAusRpting() {return this.ausRpting_;}
/**
* 赋值 AUS_RPTING

* @param paraAusRpting
* AUS_RPTING
 */

public void setAusRpting(String paraAusRpting){
  super.recordChanged("AUS_RPTING", this.ausRpting_, paraAusRpting);
  this.ausRpting_ = paraAusRpting;
}


/**
 * 获取 SUP_CREDITLIMIT
 *
* @return SUP_CREDITLIMIT
*/
public Double getSupCreditlimit() {return this.supCreditlimit_;}
/**
* 赋值 SUP_CREDITLIMIT

* @param paraSupCreditlimit
* SUP_CREDITLIMIT
 */

public void setSupCreditlimit(Double paraSupCreditlimit){
  super.recordChanged("SUP_CREDITLIMIT", this.supCreditlimit_, paraSupCreditlimit);
  this.supCreditlimit_ = paraSupCreditlimit;
}


/**
 * 获取 SUP_EMPLOYEEID
 *
* @return SUP_EMPLOYEEID
*/
public Integer getSupEmployeeid() {return this.supEmployeeid_;}
/**
* 赋值 SUP_EMPLOYEEID

* @param paraSupEmployeeid
* SUP_EMPLOYEEID
 */

public void setSupEmployeeid(Integer paraSupEmployeeid){
  super.recordChanged("SUP_EMPLOYEEID", this.supEmployeeid_, paraSupEmployeeid);
  this.supEmployeeid_ = paraSupEmployeeid;
}


/**
 * 获取 AUS_INCENTIVERATE
 *
* @return AUS_INCENTIVERATE
*/
public Double getAusIncentiverate() {return this.ausIncentiverate_;}
/**
* 赋值 AUS_INCENTIVERATE

* @param paraAusIncentiverate
* AUS_INCENTIVERATE
 */

public void setAusIncentiverate(Double paraAusIncentiverate){
  super.recordChanged("AUS_INCENTIVERATE", this.ausIncentiverate_, paraAusIncentiverate);
  this.ausIncentiverate_ = paraAusIncentiverate;
}


/**
 * 获取 AUS_BKFT
 *
* @return AUS_BKFT
*/
public Double getAusBkft() {return this.ausBkft_;}
/**
* 赋值 AUS_BKFT

* @param paraAusBkft
* AUS_BKFT
 */

public void setAusBkft(Double paraAusBkft){
  super.recordChanged("AUS_BKFT", this.ausBkft_, paraAusBkft);
  this.ausBkft_ = paraAusBkft;
}


/**
 * 获取 SUP_DRIVINGLICENCE
 *
* @return SUP_DRIVINGLICENCE
*/
public String getSupDrivinglicence() {return this.supDrivinglicence_;}
/**
* 赋值 SUP_DRIVINGLICENCE

* @param paraSupDrivinglicence
* SUP_DRIVINGLICENCE
 */

public void setSupDrivinglicence(String paraSupDrivinglicence){
  super.recordChanged("SUP_DRIVINGLICENCE", this.supDrivinglicence_, paraSupDrivinglicence);
  this.supDrivinglicence_ = paraSupDrivinglicence;
}


/**
 * 获取 SUP_MARKUP
 *
* @return SUP_MARKUP
*/
public Double getSupMarkup() {return this.supMarkup_;}
/**
* 赋值 SUP_MARKUP

* @param paraSupMarkup
* SUP_MARKUP
 */

public void setSupMarkup(Double paraSupMarkup){
  super.recordChanged("SUP_MARKUP", this.supMarkup_, paraSupMarkup);
  this.supMarkup_ = paraSupMarkup;
}


/**
 * 获取 AUS_FOCPOLICY2
 *
* @return AUS_FOCPOLICY2
*/
public Integer getAusFocpolicy2() {return this.ausFocpolicy2_;}
/**
* 赋值 AUS_FOCPOLICY2

* @param paraAusFocpolicy2
* AUS_FOCPOLICY2
 */

public void setAusFocpolicy2(Integer paraAusFocpolicy2){
  super.recordChanged("AUS_FOCPOLICY2", this.ausFocpolicy2_, paraAusFocpolicy2);
  this.ausFocpolicy2_ = paraAusFocpolicy2;
}


/**
 * 获取 AUS_FOCPOLICY1
 *
* @return AUS_FOCPOLICY1
*/
public Integer getAusFocpolicy1() {return this.ausFocpolicy1_;}
/**
* 赋值 AUS_FOCPOLICY1

* @param paraAusFocpolicy1
* AUS_FOCPOLICY1
 */

public void setAusFocpolicy1(Integer paraAusFocpolicy1){
  super.recordChanged("AUS_FOCPOLICY1", this.ausFocpolicy1_, paraAusFocpolicy1);
  this.ausFocpolicy1_ = paraAusFocpolicy1;
}


/**
 * 获取 AUS_FQUOT
 *
* @return AUS_FQUOT
*/
public Integer getAusFquot() {return this.ausFquot_;}
/**
* 赋值 AUS_FQUOT

* @param paraAusFquot
* AUS_FQUOT
 */

public void setAusFquot(Integer paraAusFquot){
  super.recordChanged("AUS_FQUOT", this.ausFquot_, paraAusFquot);
  this.ausFquot_ = paraAusFquot;
}


/**
 * 获取 AUS_ACCOUNTNO
 *
* @return AUS_ACCOUNTNO
*/
public String getAusAccountno() {return this.ausAccountno_;}
/**
* 赋值 AUS_ACCOUNTNO

* @param paraAusAccountno
* AUS_ACCOUNTNO
 */

public void setAusAccountno(String paraAusAccountno){
  super.recordChanged("AUS_ACCOUNTNO", this.ausAccountno_, paraAusAccountno);
  this.ausAccountno_ = paraAusAccountno;
}


/**
 * 获取 AUS_DISC
 *
* @return AUS_DISC
*/
public Double getAusDisc() {return this.ausDisc_;}
/**
* 赋值 AUS_DISC

* @param paraAusDisc
* AUS_DISC
 */

public void setAusDisc(Double paraAusDisc){
  super.recordChanged("AUS_DISC", this.ausDisc_, paraAusDisc);
  this.ausDisc_ = paraAusDisc;
}


/**
 * 获取 AUS_PORTERAGE
 *
* @return AUS_PORTERAGE
*/
public Double getAusPorterage() {return this.ausPorterage_;}
/**
* 赋值 AUS_PORTERAGE

* @param paraAusPorterage
* AUS_PORTERAGE
 */

public void setAusPorterage(Double paraAusPorterage){
  super.recordChanged("AUS_PORTERAGE", this.ausPorterage_, paraAusPorterage);
  this.ausPorterage_ = paraAusPorterage;
}


/**
 * 获取 AUS_OVERSEA
 *
* @return AUS_OVERSEA
*/
public String getAusOversea() {return this.ausOversea_;}
/**
* 赋值 AUS_OVERSEA

* @param paraAusOversea
* AUS_OVERSEA
 */

public void setAusOversea(String paraAusOversea){
  super.recordChanged("AUS_OVERSEA", this.ausOversea_, paraAusOversea);
  this.ausOversea_ = paraAusOversea;
}


/**
 * 获取 AUS_BKFTC
 *
* @return AUS_BKFTC
*/
public Double getAusBkftc() {return this.ausBkftc_;}
/**
* 赋值 AUS_BKFTC

* @param paraAusBkftc
* AUS_BKFTC
 */

public void setAusBkftc(Double paraAusBkftc){
  super.recordChanged("AUS_BKFTC", this.ausBkftc_, paraAusBkftc);
  this.ausBkftc_ = paraAusBkftc;
}


/**
 * 获取 SUP_MARGIN
 *
* @return SUP_MARGIN
*/
public Double getSupMargin() {return this.supMargin_;}
/**
* 赋值 SUP_MARGIN

* @param paraSupMargin
* SUP_MARGIN
 */

public void setSupMargin(Double paraSupMargin){
  super.recordChanged("SUP_MARGIN", this.supMargin_, paraSupMargin);
  this.supMargin_ = paraSupMargin;
}


/**
 * 获取 AUS_DISCOUNTDAYS
 *
* @return AUS_DISCOUNTDAYS
*/
public Integer getAusDiscountdays() {return this.ausDiscountdays_;}
/**
* 赋值 AUS_DISCOUNTDAYS

* @param paraAusDiscountdays
* AUS_DISCOUNTDAYS
 */

public void setAusDiscountdays(Integer paraAusDiscountdays){
  super.recordChanged("AUS_DISCOUNTDAYS", this.ausDiscountdays_, paraAusDiscountdays);
  this.ausDiscountdays_ = paraAusDiscountdays;
}


/**
 * 获取 AUS_CONTACTTITLE
 *
* @return AUS_CONTACTTITLE
*/
public String getAusContacttitle() {return this.ausContacttitle_;}
/**
* 赋值 AUS_CONTACTTITLE

* @param paraAusContacttitle
* AUS_CONTACTTITLE
 */

public void setAusContacttitle(String paraAusContacttitle){
  super.recordChanged("AUS_CONTACTTITLE", this.ausContacttitle_, paraAusContacttitle);
  this.ausContacttitle_ = paraAusContacttitle;
}


/**
 * 获取 SUP_NAME_EN
 *
* @return SUP_NAME_EN
*/
public String getSupNameEn() {return this.supNameEn_;}
/**
* 赋值 SUP_NAME_EN

* @param paraSupNameEn
* SUP_NAME_EN
 */

public void setSupNameEn(String paraSupNameEn){
  super.recordChanged("SUP_NAME_EN", this.supNameEn_, paraSupNameEn);
  this.supNameEn_ = paraSupNameEn;
}/**根据字段名称获取值，如果名称为空或字段未找到，返回空值
 @param filedName 字段名称
 @return 字段值*/
public Object getField(String filedName){
if(filedName == null){ return null; }
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("SUP_ID")){return this.supId_;}
if(n.equalsIgnoreCase("SUP_PID")){return this.supPid_;}
if(n.equalsIgnoreCase("ARE_ID")){return this.areId_;}
if(n.equalsIgnoreCase("SUP_TAG")){return this.supTag_;}
if(n.equalsIgnoreCase("SUP_MTAG")){return this.supMtag_;}
if(n.equalsIgnoreCase("SUP_NAME")){return this.supName_;}
if(n.equalsIgnoreCase("SUP_ADDR")){return this.supAddr_;}
if(n.equalsIgnoreCase("SUP_TELE")){return this.supTele_;}
if(n.equalsIgnoreCase("SUP_FAX")){return this.supFax_;}
if(n.equalsIgnoreCase("SUP_EMAIL")){return this.supEmail_;}
if(n.equalsIgnoreCase("SUP_HTTP")){return this.supHttp_;}
if(n.equalsIgnoreCase("SUP_MOBILE")){return this.supMobile_;}
if(n.equalsIgnoreCase("SUP_QQ")){return this.supQq_;}
if(n.equalsIgnoreCase("SUP_MSN")){return this.supMsn_;}
if(n.equalsIgnoreCase("SUP_ICQ")){return this.supIcq_;}
if(n.equalsIgnoreCase("SUP_TAOBAO")){return this.supTaobao_;}
if(n.equalsIgnoreCase("SUP_PAYPAL")){return this.supPaypal_;}
if(n.equalsIgnoreCase("SUP_ZIP")){return this.supZip_;}
if(n.equalsIgnoreCase("SUP_MEMO")){return this.supMemo_;}
if(n.equalsIgnoreCase("SUP_PACT")){return this.supPact_;}
if(n.equalsIgnoreCase("SUP_LICENCE")){return this.supLicence_;}
if(n.equalsIgnoreCase("SUP_AG_PROFILE")){return this.supAgProfile_;}
if(n.equalsIgnoreCase("SUP_AG_ORDER")){return this.supAgOrder_;}
if(n.equalsIgnoreCase("SUP_AG_CODE")){return this.supAgCode_;}
if(n.equalsIgnoreCase("SUP_LOGO")){return this.supLogo_;}
if(n.equalsIgnoreCase("SUP_POST_URL")){return this.supPostUrl_;}
if(n.equalsIgnoreCase("SUP_POST_EMAIL")){return this.supPostEmail_;}
if(n.equalsIgnoreCase("SER_TAG1")){return this.serTag1_;}
if(n.equalsIgnoreCase("CITY_ID1")){return this.cityId1_;}
if(n.equalsIgnoreCase("SIGN_TAG")){return this.signTag_;}
if(n.equalsIgnoreCase("SIGN_TIME")){return this.signTime_;}
if(n.equalsIgnoreCase("SIGN_PRICE")){return this.signPrice_;}
if(n.equalsIgnoreCase("SUP_ARE_DES")){return this.supAreDes_;}
if(n.equalsIgnoreCase("SUP_UNID")){return this.supUnid_;}
if(n.equalsIgnoreCase("SUP_COIN_ID")){return this.supCoinId_;}
if(n.equalsIgnoreCase("SUP_SCM_OPEN")){return this.supScmOpen_;}
if(n.equalsIgnoreCase("SUP_SCM_OPEN_SDATE")){return this.supScmOpenSdate_;}
if(n.equalsIgnoreCase("SUP_SCM_OPEN_EDATE")){return this.supScmOpenEdate_;}
if(n.equalsIgnoreCase("SUP_GRP_CODE_TMP")){return this.supGrpCodeTmp_;}
if(n.equalsIgnoreCase("SUP_SCM_MNUS")){return this.supScmMnus_;}
if(n.equalsIgnoreCase("SUP_SCM_SRVS")){return this.supScmSrvs_;}
if(n.equalsIgnoreCase("SUP_SHOP_KEY")){return this.supShopKey_;}
if(n.equalsIgnoreCase("BUSINESS_NUMBER")){return this.businessNumber_;}
if(n.equalsIgnoreCase("SUP_CANCELLATION_DAYS")){return this.supCancellationDays_;}
if(n.equalsIgnoreCase("SUP_LAT")){return this.supLat_;}
if(n.equalsIgnoreCase("SUP_LNG")){return this.supLng_;}
if(n.equalsIgnoreCase("SUP_OPEN_HOURS")){return this.supOpenHours_;}
if(n.equalsIgnoreCase("SUP_PORTERAGE")){return this.supPorterage_;}
if(n.equalsIgnoreCase("SUP_QUERY")){return this.supQuery_;}
if(n.equalsIgnoreCase("SUP_STAR")){return this.supStar_;}
if(n.equalsIgnoreCase("SUP_STATE")){return this.supState_;}
if(n.equalsIgnoreCase("SUP_TERMS")){return this.supTerms_;}
if(n.equalsIgnoreCase("SUP_TERMS_DAYS")){return this.supTermsDays_;}
if(n.equalsIgnoreCase("SUP_USE_STATE")){return this.supUseState_;}
if(n.equalsIgnoreCase("SUP_OWNER")){return this.supOwner_;}
if(n.equalsIgnoreCase("SUP_FACEBOOK")){return this.supFacebook_;}
if(n.equalsIgnoreCase("SUP_TWITTER")){return this.supTwitter_;}
if(n.equalsIgnoreCase("AUS_DIRECTOROFSALES")){return this.ausDirectorofsales_;}
if(n.equalsIgnoreCase("AUS_CONTRACTRATE")){return this.ausContractrate_;}
if(n.equalsIgnoreCase("AUS_SELLINGPRICE")){return this.ausSellingprice_;}
if(n.equalsIgnoreCase("SUP_TAID")){return this.supTaid_;}
if(n.equalsIgnoreCase("IS_AUS")){return this.isAus_;}
if(n.equalsIgnoreCase("AUS_RACKRATE")){return this.ausRackrate_;}
if(n.equalsIgnoreCase("AUS_ACCREDITATION")){return this.ausAccreditation_;}
if(n.equalsIgnoreCase("SUP_SALES_ID")){return this.supSalesId_;}
if(n.equalsIgnoreCase("AUS_DAYS")){return this.ausDays_;}
if(n.equalsIgnoreCase("AUS_UNITPRICE2")){return this.ausUnitprice2_;}
if(n.equalsIgnoreCase("AUS_UNITPRICE1")){return this.ausUnitprice1_;}
if(n.equalsIgnoreCase("AUS_RPTING")){return this.ausRpting_;}
if(n.equalsIgnoreCase("SUP_CREDITLIMIT")){return this.supCreditlimit_;}
if(n.equalsIgnoreCase("SUP_EMPLOYEEID")){return this.supEmployeeid_;}
if(n.equalsIgnoreCase("AUS_INCENTIVERATE")){return this.ausIncentiverate_;}
if(n.equalsIgnoreCase("AUS_BKFT")){return this.ausBkft_;}
if(n.equalsIgnoreCase("SUP_DRIVINGLICENCE")){return this.supDrivinglicence_;}
if(n.equalsIgnoreCase("SUP_MARKUP")){return this.supMarkup_;}
if(n.equalsIgnoreCase("AUS_FOCPOLICY2")){return this.ausFocpolicy2_;}
if(n.equalsIgnoreCase("AUS_FOCPOLICY1")){return this.ausFocpolicy1_;}
if(n.equalsIgnoreCase("AUS_FQUOT")){return this.ausFquot_;}
if(n.equalsIgnoreCase("AUS_ACCOUNTNO")){return this.ausAccountno_;}
if(n.equalsIgnoreCase("AUS_DISC")){return this.ausDisc_;}
if(n.equalsIgnoreCase("AUS_PORTERAGE")){return this.ausPorterage_;}
if(n.equalsIgnoreCase("AUS_OVERSEA")){return this.ausOversea_;}
if(n.equalsIgnoreCase("AUS_BKFTC")){return this.ausBkftc_;}
if(n.equalsIgnoreCase("SUP_MARGIN")){return this.supMargin_;}
if(n.equalsIgnoreCase("AUS_DISCOUNTDAYS")){return this.ausDiscountdays_;}
if(n.equalsIgnoreCase("AUS_CONTACTTITLE")){return this.ausContacttitle_;}
if(n.equalsIgnoreCase("SUP_NAME_EN")){return this.supNameEn_;}
return null;}
}