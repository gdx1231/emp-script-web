package com.gdxsoft.web.dao;

import com.gdxsoft.easyweb.datasource.ClassBase;

import java.util.Date;
/**表nws_main映射类
* @author gdx 时间：Mon May 10 2021 12:01:37 GMT+0800 (中国标准时间)*/
public class NwsMain extends ClassBase{private Long nwsId_; // NWS_ID
private Integer admId_; // 编号
private String nwsSubject_; // 标题
private String nwsMemo_; // 摘要
private String nwsKeywords_; // 关键字
private String nwsCnt_; // NWS_CNT
private Date nwsCdate_; // 创建日期
private Date nwsMdate_; // 修改日期
private Date nwsDdate_; // 发布日期
private Date nwsEdate_; // 截至日期
private String nwsTag_; // 标记
private String nwsSrc1_; // 来源1
private String nwsSrc2_; // 网站跳转地址
private String nwsAuth1_; // 作者1
private String nwsAuth2_; // 作者2
private String nwsGuid_; // 全局编号
private Integer nwsHot_; // 热点
private String nwsHome_; // 首页发布
private String solr_; // SOLR
private String nwsPicSizes_; // 图库图片自定义图片尺寸
private Integer supId_; // SUP_ID
private String nwsVod_; // 视频地址
private String nwsHeadPic_; // 题图地址
private String nwsCntTxt_; // 纯文本数据
private String nwsRef0_; // NWS_REF0
private String nwsRef1_; // NWS_REF1
private String nwsTag0_; // NWS_TAG0
private String nwsTag1_; // NWS_TAG1

/**
 * 获取 NWS_ID
 *
* @return NWS_ID
*/
public Long getNwsId() {return this.nwsId_;}
/**
* 赋值 NWS_ID

* @param paraNwsId
* NWS_ID
 */

public void setNwsId(Long paraNwsId){
  super.recordChanged("NWS_ID", this.nwsId_, paraNwsId);
  this.nwsId_ = paraNwsId;
}


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
 * 获取 标题
 *
* @return 标题
*/
public String getNwsSubject() {return this.nwsSubject_;}
/**
* 赋值 标题

* @param paraNwsSubject
* 标题
 */

public void setNwsSubject(String paraNwsSubject){
  super.recordChanged("NWS_SUBJECT", this.nwsSubject_, paraNwsSubject);
  this.nwsSubject_ = paraNwsSubject;
}


/**
 * 获取 摘要
 *
* @return 摘要
*/
public String getNwsMemo() {return this.nwsMemo_;}
/**
* 赋值 摘要

* @param paraNwsMemo
* 摘要
 */

public void setNwsMemo(String paraNwsMemo){
  super.recordChanged("NWS_MEMO", this.nwsMemo_, paraNwsMemo);
  this.nwsMemo_ = paraNwsMemo;
}


/**
 * 获取 关键字
 *
* @return 关键字
*/
public String getNwsKeywords() {return this.nwsKeywords_;}
/**
* 赋值 关键字

* @param paraNwsKeywords
* 关键字
 */

public void setNwsKeywords(String paraNwsKeywords){
  super.recordChanged("NWS_KEYWORDS", this.nwsKeywords_, paraNwsKeywords);
  this.nwsKeywords_ = paraNwsKeywords;
}


/**
 * 获取 NWS_CNT
 *
* @return NWS_CNT
*/
public String getNwsCnt() {return this.nwsCnt_;}
/**
* 赋值 NWS_CNT

* @param paraNwsCnt
* NWS_CNT
 */

public void setNwsCnt(String paraNwsCnt){
  super.recordChanged("NWS_CNT", this.nwsCnt_, paraNwsCnt);
  this.nwsCnt_ = paraNwsCnt;
}


/**
 * 获取 创建日期
 *
* @return 创建日期
*/
public Date getNwsCdate() {return this.nwsCdate_;}
/**
* 赋值 创建日期

* @param paraNwsCdate
* 创建日期
 */

public void setNwsCdate(Date paraNwsCdate){
  super.recordChanged("NWS_CDATE", this.nwsCdate_, paraNwsCdate);
  this.nwsCdate_ = paraNwsCdate;
}


/**
 * 获取 修改日期
 *
* @return 修改日期
*/
public Date getNwsMdate() {return this.nwsMdate_;}
/**
* 赋值 修改日期

* @param paraNwsMdate
* 修改日期
 */

public void setNwsMdate(Date paraNwsMdate){
  super.recordChanged("NWS_MDATE", this.nwsMdate_, paraNwsMdate);
  this.nwsMdate_ = paraNwsMdate;
}


/**
 * 获取 发布日期
 *
* @return 发布日期
*/
public Date getNwsDdate() {return this.nwsDdate_;}
/**
* 赋值 发布日期

* @param paraNwsDdate
* 发布日期
 */

public void setNwsDdate(Date paraNwsDdate){
  super.recordChanged("NWS_DDATE", this.nwsDdate_, paraNwsDdate);
  this.nwsDdate_ = paraNwsDdate;
}


/**
 * 获取 截至日期
 *
* @return 截至日期
*/
public Date getNwsEdate() {return this.nwsEdate_;}
/**
* 赋值 截至日期

* @param paraNwsEdate
* 截至日期
 */

public void setNwsEdate(Date paraNwsEdate){
  super.recordChanged("NWS_EDATE", this.nwsEdate_, paraNwsEdate);
  this.nwsEdate_ = paraNwsEdate;
}


/**
 * 获取 标记
 *
* @return 标记
*/
public String getNwsTag() {return this.nwsTag_;}
/**
* 赋值 标记

* @param paraNwsTag
* 标记
 */

public void setNwsTag(String paraNwsTag){
  super.recordChanged("NWS_TAG", this.nwsTag_, paraNwsTag);
  this.nwsTag_ = paraNwsTag;
}


/**
 * 获取 来源1
 *
* @return 来源1
*/
public String getNwsSrc1() {return this.nwsSrc1_;}
/**
* 赋值 来源1

* @param paraNwsSrc1
* 来源1
 */

public void setNwsSrc1(String paraNwsSrc1){
  super.recordChanged("NWS_SRC1", this.nwsSrc1_, paraNwsSrc1);
  this.nwsSrc1_ = paraNwsSrc1;
}


/**
 * 获取 网站跳转地址
 *
* @return 网站跳转地址
*/
public String getNwsSrc2() {return this.nwsSrc2_;}
/**
* 赋值 网站跳转地址

* @param paraNwsSrc2
* 网站跳转地址
 */

public void setNwsSrc2(String paraNwsSrc2){
  super.recordChanged("NWS_SRC2", this.nwsSrc2_, paraNwsSrc2);
  this.nwsSrc2_ = paraNwsSrc2;
}


/**
 * 获取 作者1
 *
* @return 作者1
*/
public String getNwsAuth1() {return this.nwsAuth1_;}
/**
* 赋值 作者1

* @param paraNwsAuth1
* 作者1
 */

public void setNwsAuth1(String paraNwsAuth1){
  super.recordChanged("NWS_AUTH1", this.nwsAuth1_, paraNwsAuth1);
  this.nwsAuth1_ = paraNwsAuth1;
}


/**
 * 获取 作者2
 *
* @return 作者2
*/
public String getNwsAuth2() {return this.nwsAuth2_;}
/**
* 赋值 作者2

* @param paraNwsAuth2
* 作者2
 */

public void setNwsAuth2(String paraNwsAuth2){
  super.recordChanged("NWS_AUTH2", this.nwsAuth2_, paraNwsAuth2);
  this.nwsAuth2_ = paraNwsAuth2;
}


/**
 * 获取 全局编号
 *
* @return 全局编号
*/
public String getNwsGuid() {return this.nwsGuid_;}
/**
* 赋值 全局编号

* @param paraNwsGuid
* 全局编号
 */

public void setNwsGuid(String paraNwsGuid){
  super.recordChanged("NWS_GUID", this.nwsGuid_, paraNwsGuid);
  this.nwsGuid_ = paraNwsGuid;
}


/**
 * 获取 热点
 *
* @return 热点
*/
public Integer getNwsHot() {return this.nwsHot_;}
/**
* 赋值 热点

* @param paraNwsHot
* 热点
 */

public void setNwsHot(Integer paraNwsHot){
  super.recordChanged("NWS_HOT", this.nwsHot_, paraNwsHot);
  this.nwsHot_ = paraNwsHot;
}


/**
 * 获取 首页发布
 *
* @return 首页发布
*/
public String getNwsHome() {return this.nwsHome_;}
/**
* 赋值 首页发布

* @param paraNwsHome
* 首页发布
 */

public void setNwsHome(String paraNwsHome){
  super.recordChanged("NWS_HOME", this.nwsHome_, paraNwsHome);
  this.nwsHome_ = paraNwsHome;
}


/**
 * 获取 SOLR
 *
* @return SOLR
*/
public String getSolr() {return this.solr_;}
/**
* 赋值 SOLR

* @param paraSolr
* SOLR
 */

public void setSolr(String paraSolr){
  super.recordChanged("SOLR", this.solr_, paraSolr);
  this.solr_ = paraSolr;
}


/**
 * 获取 图库图片自定义图片尺寸
 *
* @return 图库图片自定义图片尺寸
*/
public String getNwsPicSizes() {return this.nwsPicSizes_;}
/**
* 赋值 图库图片自定义图片尺寸

* @param paraNwsPicSizes
* 图库图片自定义图片尺寸
 */

public void setNwsPicSizes(String paraNwsPicSizes){
  super.recordChanged("NWS_PIC_SIZES", this.nwsPicSizes_, paraNwsPicSizes);
  this.nwsPicSizes_ = paraNwsPicSizes;
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
 * 获取 视频地址
 *
* @return 视频地址
*/
public String getNwsVod() {return this.nwsVod_;}
/**
* 赋值 视频地址

* @param paraNwsVod
* 视频地址
 */

public void setNwsVod(String paraNwsVod){
  super.recordChanged("NWS_VOD", this.nwsVod_, paraNwsVod);
  this.nwsVod_ = paraNwsVod;
}


/**
 * 获取 题图地址
 *
* @return 题图地址
*/
public String getNwsHeadPic() {return this.nwsHeadPic_;}
/**
* 赋值 题图地址

* @param paraNwsHeadPic
* 题图地址
 */

public void setNwsHeadPic(String paraNwsHeadPic){
  super.recordChanged("NWS_HEAD_PIC", this.nwsHeadPic_, paraNwsHeadPic);
  this.nwsHeadPic_ = paraNwsHeadPic;
}


/**
 * 获取 纯文本数据
 *
* @return 纯文本数据
*/
public String getNwsCntTxt() {return this.nwsCntTxt_;}
/**
* 赋值 纯文本数据

* @param paraNwsCntTxt
* 纯文本数据
 */

public void setNwsCntTxt(String paraNwsCntTxt){
  super.recordChanged("NWS_CNT_TXT", this.nwsCntTxt_, paraNwsCntTxt);
  this.nwsCntTxt_ = paraNwsCntTxt;
}


/**
 * 获取 NWS_REF0
 *
* @return NWS_REF0
*/
public String getNwsRef0() {return this.nwsRef0_;}
/**
* 赋值 NWS_REF0

* @param paraNwsRef0
* NWS_REF0
 */

public void setNwsRef0(String paraNwsRef0){
  super.recordChanged("NWS_REF0", this.nwsRef0_, paraNwsRef0);
  this.nwsRef0_ = paraNwsRef0;
}


/**
 * 获取 NWS_REF1
 *
* @return NWS_REF1
*/
public String getNwsRef1() {return this.nwsRef1_;}
/**
* 赋值 NWS_REF1

* @param paraNwsRef1
* NWS_REF1
 */

public void setNwsRef1(String paraNwsRef1){
  super.recordChanged("NWS_REF1", this.nwsRef1_, paraNwsRef1);
  this.nwsRef1_ = paraNwsRef1;
}


/**
 * 获取 NWS_TAG0
 *
* @return NWS_TAG0
*/
public String getNwsTag0() {return this.nwsTag0_;}
/**
* 赋值 NWS_TAG0

* @param paraNwsTag0
* NWS_TAG0
 */

public void setNwsTag0(String paraNwsTag0){
  super.recordChanged("NWS_TAG0", this.nwsTag0_, paraNwsTag0);
  this.nwsTag0_ = paraNwsTag0;
}


/**
 * 获取 NWS_TAG1
 *
* @return NWS_TAG1
*/
public String getNwsTag1() {return this.nwsTag1_;}
/**
* 赋值 NWS_TAG1

* @param paraNwsTag1
* NWS_TAG1
 */

public void setNwsTag1(String paraNwsTag1){
  super.recordChanged("NWS_TAG1", this.nwsTag1_, paraNwsTag1);
  this.nwsTag1_ = paraNwsTag1;
}
}