package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表ORD_PAY映射类
* @author gdx 时间：Sat Jul 11 2020 20:36:06 GMT+0800 (中国标准时间)*/
public class OrdPay extends ClassBase{private Integer ordPayId_; // 支付编号
private Integer ordId_; // 订单编号
private String basTag_; // 标记
private Integer supId_; // 供应商编号
private Double ordPayMoney_; // 支付
private String ordPayOk_; // 完成
private Date ordPayDate_; // 开始日期
private Date ordPayOkDate_; // 结束日期
private String ordPayMemo_; // 备注
private Integer admId_; // 操作员
private String ordPayPara1_; // 参数1
private String ordPayPara2_; // 参数2
private String ordPayPara3_; // 参数3
private String ordPayPara4_; // 参数4
private String ordPayPara5_; // 参数5
private String ordPayPara6_; // 参数6
private String ordPayPara7_; // 参数7
private String ordPayPara8_; // 参数8
private byte[] ordPayPara9_; // 参数9
private byte[] ordPayPara10_; // 参数10
private String ordOlXml_; // 网关支付XML
private String ordOlOid_; // 网关支付形成的订单号
private String ordOlProvider_; // 在线支付供应商

/**
 * 获取 支付编号
 *
* @return 支付编号
*/
public Integer getOrdPayId() {return this.ordPayId_;}
/**
* 赋值 支付编号

* @param paraOrdPayId
* 支付编号
 */

public void setOrdPayId(Integer paraOrdPayId){
  super.recordChanged("ORD_PAY_ID", this.ordPayId_, paraOrdPayId);
  this.ordPayId_ = paraOrdPayId;
}


/**
 * 获取 订单编号
 *
* @return 订单编号
*/
public Integer getOrdId() {return this.ordId_;}
/**
* 赋值 订单编号

* @param paraOrdId
* 订单编号
 */

public void setOrdId(Integer paraOrdId){
  super.recordChanged("ORD_ID", this.ordId_, paraOrdId);
  this.ordId_ = paraOrdId;
}


/**
 * 获取 标记
 *
* @return 标记
*/
public String getBasTag() {return this.basTag_;}
/**
* 赋值 标记

* @param paraBasTag
* 标记
 */

public void setBasTag(String paraBasTag){
  super.recordChanged("BAS_TAG", this.basTag_, paraBasTag);
  this.basTag_ = paraBasTag;
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
 * 获取 完成
 *
* @return 完成
*/
public String getOrdPayOk() {return this.ordPayOk_;}
/**
* 赋值 完成

* @param paraOrdPayOk
* 完成
 */

public void setOrdPayOk(String paraOrdPayOk){
  super.recordChanged("ORD_PAY_OK", this.ordPayOk_, paraOrdPayOk);
  this.ordPayOk_ = paraOrdPayOk;
}


/**
 * 获取 开始日期
 *
* @return 开始日期
*/
public Date getOrdPayDate() {return this.ordPayDate_;}
/**
* 赋值 开始日期

* @param paraOrdPayDate
* 开始日期
 */

public void setOrdPayDate(Date paraOrdPayDate){
  super.recordChanged("ORD_PAY_DATE", this.ordPayDate_, paraOrdPayDate);
  this.ordPayDate_ = paraOrdPayDate;
}


/**
 * 获取 结束日期
 *
* @return 结束日期
*/
public Date getOrdPayOkDate() {return this.ordPayOkDate_;}
/**
* 赋值 结束日期

* @param paraOrdPayOkDate
* 结束日期
 */

public void setOrdPayOkDate(Date paraOrdPayOkDate){
  super.recordChanged("ORD_PAY_OK_DATE", this.ordPayOkDate_, paraOrdPayOkDate);
  this.ordPayOkDate_ = paraOrdPayOkDate;
}


/**
 * 获取 备注
 *
* @return 备注
*/
public String getOrdPayMemo() {return this.ordPayMemo_;}
/**
* 赋值 备注

* @param paraOrdPayMemo
* 备注
 */

public void setOrdPayMemo(String paraOrdPayMemo){
  super.recordChanged("ORD_PAY_MEMO", this.ordPayMemo_, paraOrdPayMemo);
  this.ordPayMemo_ = paraOrdPayMemo;
}


/**
 * 获取 操作员
 *
* @return 操作员
*/
public Integer getAdmId() {return this.admId_;}
/**
* 赋值 操作员

* @param paraAdmId
* 操作员
 */

public void setAdmId(Integer paraAdmId){
  super.recordChanged("ADM_ID", this.admId_, paraAdmId);
  this.admId_ = paraAdmId;
}


/**
 * 获取 参数1
 *
* @return 参数1
*/
public String getOrdPayPara1() {return this.ordPayPara1_;}
/**
* 赋值 参数1

* @param paraOrdPayPara1
* 参数1
 */

public void setOrdPayPara1(String paraOrdPayPara1){
  super.recordChanged("ORD_PAY_PARA1", this.ordPayPara1_, paraOrdPayPara1);
  this.ordPayPara1_ = paraOrdPayPara1;
}


/**
 * 获取 参数2
 *
* @return 参数2
*/
public String getOrdPayPara2() {return this.ordPayPara2_;}
/**
* 赋值 参数2

* @param paraOrdPayPara2
* 参数2
 */

public void setOrdPayPara2(String paraOrdPayPara2){
  super.recordChanged("ORD_PAY_PARA2", this.ordPayPara2_, paraOrdPayPara2);
  this.ordPayPara2_ = paraOrdPayPara2;
}


/**
 * 获取 参数3
 *
* @return 参数3
*/
public String getOrdPayPara3() {return this.ordPayPara3_;}
/**
* 赋值 参数3

* @param paraOrdPayPara3
* 参数3
 */

public void setOrdPayPara3(String paraOrdPayPara3){
  super.recordChanged("ORD_PAY_PARA3", this.ordPayPara3_, paraOrdPayPara3);
  this.ordPayPara3_ = paraOrdPayPara3;
}


/**
 * 获取 参数4
 *
* @return 参数4
*/
public String getOrdPayPara4() {return this.ordPayPara4_;}
/**
* 赋值 参数4

* @param paraOrdPayPara4
* 参数4
 */

public void setOrdPayPara4(String paraOrdPayPara4){
  super.recordChanged("ORD_PAY_PARA4", this.ordPayPara4_, paraOrdPayPara4);
  this.ordPayPara4_ = paraOrdPayPara4;
}


/**
 * 获取 参数5
 *
* @return 参数5
*/
public String getOrdPayPara5() {return this.ordPayPara5_;}
/**
* 赋值 参数5

* @param paraOrdPayPara5
* 参数5
 */

public void setOrdPayPara5(String paraOrdPayPara5){
  super.recordChanged("ORD_PAY_PARA5", this.ordPayPara5_, paraOrdPayPara5);
  this.ordPayPara5_ = paraOrdPayPara5;
}


/**
 * 获取 参数6
 *
* @return 参数6
*/
public String getOrdPayPara6() {return this.ordPayPara6_;}
/**
* 赋值 参数6

* @param paraOrdPayPara6
* 参数6
 */

public void setOrdPayPara6(String paraOrdPayPara6){
  super.recordChanged("ORD_PAY_PARA6", this.ordPayPara6_, paraOrdPayPara6);
  this.ordPayPara6_ = paraOrdPayPara6;
}


/**
 * 获取 参数7
 *
* @return 参数7
*/
public String getOrdPayPara7() {return this.ordPayPara7_;}
/**
* 赋值 参数7

* @param paraOrdPayPara7
* 参数7
 */

public void setOrdPayPara7(String paraOrdPayPara7){
  super.recordChanged("ORD_PAY_PARA7", this.ordPayPara7_, paraOrdPayPara7);
  this.ordPayPara7_ = paraOrdPayPara7;
}


/**
 * 获取 参数8
 *
* @return 参数8
*/
public String getOrdPayPara8() {return this.ordPayPara8_;}
/**
* 赋值 参数8

* @param paraOrdPayPara8
* 参数8
 */

public void setOrdPayPara8(String paraOrdPayPara8){
  super.recordChanged("ORD_PAY_PARA8", this.ordPayPara8_, paraOrdPayPara8);
  this.ordPayPara8_ = paraOrdPayPara8;
}


/**
 * 获取 参数9
 *
* @return 参数9
*/
public byte[] getOrdPayPara9() {return this.ordPayPara9_;}
/**
* 赋值 参数9

* @param paraOrdPayPara9
* 参数9
 */

public void setOrdPayPara9(byte[] paraOrdPayPara9){
  super.recordChanged("ORD_PAY_PARA9", this.ordPayPara9_, paraOrdPayPara9);
  this.ordPayPara9_ = paraOrdPayPara9;
}


/**
 * 获取 参数10
 *
* @return 参数10
*/
public byte[] getOrdPayPara10() {return this.ordPayPara10_;}
/**
* 赋值 参数10

* @param paraOrdPayPara10
* 参数10
 */

public void setOrdPayPara10(byte[] paraOrdPayPara10){
  super.recordChanged("ORD_PAY_PARA10", this.ordPayPara10_, paraOrdPayPara10);
  this.ordPayPara10_ = paraOrdPayPara10;
}


/**
 * 获取 网关支付XML
 *
* @return 网关支付XML
*/
public String getOrdOlXml() {return this.ordOlXml_;}
/**
* 赋值 网关支付XML

* @param paraOrdOlXml
* 网关支付XML
 */

public void setOrdOlXml(String paraOrdOlXml){
  super.recordChanged("ORD_OL_XML", this.ordOlXml_, paraOrdOlXml);
  this.ordOlXml_ = paraOrdOlXml;
}


/**
 * 获取 网关支付形成的订单号
 *
* @return 网关支付形成的订单号
*/
public String getOrdOlOid() {return this.ordOlOid_;}
/**
* 赋值 网关支付形成的订单号

* @param paraOrdOlOid
* 网关支付形成的订单号
 */

public void setOrdOlOid(String paraOrdOlOid){
  super.recordChanged("ORD_OL_OID", this.ordOlOid_, paraOrdOlOid);
  this.ordOlOid_ = paraOrdOlOid;
}


/**
 * 获取 在线支付供应商
 *
* @return 在线支付供应商
*/
public String getOrdOlProvider() {return this.ordOlProvider_;}
/**
* 赋值 在线支付供应商

* @param paraOrdOlProvider
* 在线支付供应商
 */

public void setOrdOlProvider(String paraOrdOlProvider){
  super.recordChanged("ORD_OL_PROVIDER", this.ordOlProvider_, paraOrdOlProvider);
  this.ordOlProvider_ = paraOrdOlProvider;
}
}