package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表ord_main_lst映射类
* @author gdx 时间：Sat Apr 25 2020 17:49:10 GMT+0800 (中国标准时间)*/
public class OrdMainLst extends ClassBase{private Integer ordLstId_; // ORD_LST_ID
private Integer ordId_; // 订单号
private Integer srvId_; // 本系统无效了
private Integer supId_; // 服务提供商编号
private String ordLstName_; // 名称
private Double ordLstMoney_; // 总价
private Integer ordLstNum1_; // 数量1
private Double ordLstMoney1_; // 单价1
private Integer ordLstNum2_; // 数量2
private Double ordLstMoney2_; // 单价2
private Integer ordLstNum3_; // 数量3
private Double ordLstMoney3_; // 单价3
private String ordLstMemo1_; // 备注1
private String ordLstMemo2_; // 备注2
private String ordLstMemo3_; // 备注3
private String ordLstMemo4_; // 备注4
private String ordLstMemo5_; // 备注5
private Integer ordLstIdx_; // 序号
private Integer ordLstAdmid_; // 管理员编号
private Integer scmPrjSubId_; // 项目来源项目子编号
private Integer ordLstCoin_; // ORD_LST_COIN
private Date ordLstDate_; // ORD_LST_DATE
private Date ordLstDateEnd_; // ORD_LST_DATE_END
private Double ordLstDiscount_; // 折扣
private Integer ordLstPid_; // ORD_LST_PID
private Integer ordLstRoom_; // ORD_LST_ROOM
private Integer ordSerId_; // 来源服务
private Integer ordSerKeyId_; // 来源ser_key_id
private Integer ordRefProductId_; // 来源product_main的id
private Integer ordRefProductPriceId_; // 价格编号id
private String ordRefSourceTable_; // 服务来源表例如：bas_spot, guide_main, camp_home ..
private String ordRefSourceId_; // 来源表的主键, 例如spot_id , gui_id, hom_id …
private Double oriOrdLstMoney1_; // 原始单价
private Integer oriOrdLstCoin_; // 原始货币
private Double ordLstPoint_; // 汇率

/**
 * 获取 ORD_LST_ID
 *
* @return ORD_LST_ID
*/
public Integer getOrdLstId() {return this.ordLstId_;}
/**
* 赋值 ORD_LST_ID

* @param paraOrdLstId
* ORD_LST_ID
 */

public void setOrdLstId(Integer paraOrdLstId){
  super.recordChanged("ORD_LST_ID", this.ordLstId_, paraOrdLstId);
  this.ordLstId_ = paraOrdLstId;
}


/**
 * 获取 订单号
 *
* @return 订单号
*/
public Integer getOrdId() {return this.ordId_;}
/**
* 赋值 订单号

* @param paraOrdId
* 订单号
 */

public void setOrdId(Integer paraOrdId){
  super.recordChanged("ORD_ID", this.ordId_, paraOrdId);
  this.ordId_ = paraOrdId;
}


/**
 * 获取 本系统无效了
 *
* @return 本系统无效了
*/
public Integer getSrvId() {return this.srvId_;}
/**
* 赋值 本系统无效了

* @param paraSrvId
* 本系统无效了
 */

public void setSrvId(Integer paraSrvId){
  super.recordChanged("SRV_ID", this.srvId_, paraSrvId);
  this.srvId_ = paraSrvId;
}


/**
 * 获取 服务提供商编号
 *
* @return 服务提供商编号
*/
public Integer getSupId() {return this.supId_;}
/**
* 赋值 服务提供商编号

* @param paraSupId
* 服务提供商编号
 */

public void setSupId(Integer paraSupId){
  super.recordChanged("SUP_ID", this.supId_, paraSupId);
  this.supId_ = paraSupId;
}


/**
 * 获取 名称
 *
* @return 名称
*/
public String getOrdLstName() {return this.ordLstName_;}
/**
* 赋值 名称

* @param paraOrdLstName
* 名称
 */

public void setOrdLstName(String paraOrdLstName){
  super.recordChanged("ORD_LST_NAME", this.ordLstName_, paraOrdLstName);
  this.ordLstName_ = paraOrdLstName;
}


/**
 * 获取 总价
 *
* @return 总价
*/
public Double getOrdLstMoney() {return this.ordLstMoney_;}
/**
* 赋值 总价

* @param paraOrdLstMoney
* 总价
 */

public void setOrdLstMoney(Double paraOrdLstMoney){
  super.recordChanged("ORD_LST_MONEY", this.ordLstMoney_, paraOrdLstMoney);
  this.ordLstMoney_ = paraOrdLstMoney;
}


/**
 * 获取 数量1
 *
* @return 数量1
*/
public Integer getOrdLstNum1() {return this.ordLstNum1_;}
/**
* 赋值 数量1

* @param paraOrdLstNum1
* 数量1
 */

public void setOrdLstNum1(Integer paraOrdLstNum1){
  super.recordChanged("ORD_LST_NUM1", this.ordLstNum1_, paraOrdLstNum1);
  this.ordLstNum1_ = paraOrdLstNum1;
}


/**
 * 获取 单价1
 *
* @return 单价1
*/
public Double getOrdLstMoney1() {return this.ordLstMoney1_;}
/**
* 赋值 单价1

* @param paraOrdLstMoney1
* 单价1
 */

public void setOrdLstMoney1(Double paraOrdLstMoney1){
  super.recordChanged("ORD_LST_MONEY1", this.ordLstMoney1_, paraOrdLstMoney1);
  this.ordLstMoney1_ = paraOrdLstMoney1;
}


/**
 * 获取 数量2
 *
* @return 数量2
*/
public Integer getOrdLstNum2() {return this.ordLstNum2_;}
/**
* 赋值 数量2

* @param paraOrdLstNum2
* 数量2
 */

public void setOrdLstNum2(Integer paraOrdLstNum2){
  super.recordChanged("ORD_LST_NUM2", this.ordLstNum2_, paraOrdLstNum2);
  this.ordLstNum2_ = paraOrdLstNum2;
}


/**
 * 获取 单价2
 *
* @return 单价2
*/
public Double getOrdLstMoney2() {return this.ordLstMoney2_;}
/**
* 赋值 单价2

* @param paraOrdLstMoney2
* 单价2
 */

public void setOrdLstMoney2(Double paraOrdLstMoney2){
  super.recordChanged("ORD_LST_MONEY2", this.ordLstMoney2_, paraOrdLstMoney2);
  this.ordLstMoney2_ = paraOrdLstMoney2;
}


/**
 * 获取 数量3
 *
* @return 数量3
*/
public Integer getOrdLstNum3() {return this.ordLstNum3_;}
/**
* 赋值 数量3

* @param paraOrdLstNum3
* 数量3
 */

public void setOrdLstNum3(Integer paraOrdLstNum3){
  super.recordChanged("ORD_LST_NUM3", this.ordLstNum3_, paraOrdLstNum3);
  this.ordLstNum3_ = paraOrdLstNum3;
}


/**
 * 获取 单价3
 *
* @return 单价3
*/
public Double getOrdLstMoney3() {return this.ordLstMoney3_;}
/**
* 赋值 单价3

* @param paraOrdLstMoney3
* 单价3
 */

public void setOrdLstMoney3(Double paraOrdLstMoney3){
  super.recordChanged("ORD_LST_MONEY3", this.ordLstMoney3_, paraOrdLstMoney3);
  this.ordLstMoney3_ = paraOrdLstMoney3;
}


/**
 * 获取 备注1
 *
* @return 备注1
*/
public String getOrdLstMemo1() {return this.ordLstMemo1_;}
/**
* 赋值 备注1

* @param paraOrdLstMemo1
* 备注1
 */

public void setOrdLstMemo1(String paraOrdLstMemo1){
  super.recordChanged("ORD_LST_MEMO1", this.ordLstMemo1_, paraOrdLstMemo1);
  this.ordLstMemo1_ = paraOrdLstMemo1;
}


/**
 * 获取 备注2
 *
* @return 备注2
*/
public String getOrdLstMemo2() {return this.ordLstMemo2_;}
/**
* 赋值 备注2

* @param paraOrdLstMemo2
* 备注2
 */

public void setOrdLstMemo2(String paraOrdLstMemo2){
  super.recordChanged("ORD_LST_MEMO2", this.ordLstMemo2_, paraOrdLstMemo2);
  this.ordLstMemo2_ = paraOrdLstMemo2;
}


/**
 * 获取 备注3
 *
* @return 备注3
*/
public String getOrdLstMemo3() {return this.ordLstMemo3_;}
/**
* 赋值 备注3

* @param paraOrdLstMemo3
* 备注3
 */

public void setOrdLstMemo3(String paraOrdLstMemo3){
  super.recordChanged("ORD_LST_MEMO3", this.ordLstMemo3_, paraOrdLstMemo3);
  this.ordLstMemo3_ = paraOrdLstMemo3;
}


/**
 * 获取 备注4
 *
* @return 备注4
*/
public String getOrdLstMemo4() {return this.ordLstMemo4_;}
/**
* 赋值 备注4

* @param paraOrdLstMemo4
* 备注4
 */

public void setOrdLstMemo4(String paraOrdLstMemo4){
  super.recordChanged("ORD_LST_MEMO4", this.ordLstMemo4_, paraOrdLstMemo4);
  this.ordLstMemo4_ = paraOrdLstMemo4;
}


/**
 * 获取 备注5
 *
* @return 备注5
*/
public String getOrdLstMemo5() {return this.ordLstMemo5_;}
/**
* 赋值 备注5

* @param paraOrdLstMemo5
* 备注5
 */

public void setOrdLstMemo5(String paraOrdLstMemo5){
  super.recordChanged("ORD_LST_MEMO5", this.ordLstMemo5_, paraOrdLstMemo5);
  this.ordLstMemo5_ = paraOrdLstMemo5;
}


/**
 * 获取 序号
 *
* @return 序号
*/
public Integer getOrdLstIdx() {return this.ordLstIdx_;}
/**
* 赋值 序号

* @param paraOrdLstIdx
* 序号
 */

public void setOrdLstIdx(Integer paraOrdLstIdx){
  super.recordChanged("ORD_LST_IDX", this.ordLstIdx_, paraOrdLstIdx);
  this.ordLstIdx_ = paraOrdLstIdx;
}


/**
 * 获取 管理员编号
 *
* @return 管理员编号
*/
public Integer getOrdLstAdmid() {return this.ordLstAdmid_;}
/**
* 赋值 管理员编号

* @param paraOrdLstAdmid
* 管理员编号
 */

public void setOrdLstAdmid(Integer paraOrdLstAdmid){
  super.recordChanged("ORD_LST_ADMID", this.ordLstAdmid_, paraOrdLstAdmid);
  this.ordLstAdmid_ = paraOrdLstAdmid;
}


/**
 * 获取 项目来源项目子编号
 *
* @return 项目来源项目子编号
*/
public Integer getScmPrjSubId() {return this.scmPrjSubId_;}
/**
* 赋值 项目来源项目子编号

* @param paraScmPrjSubId
* 项目来源项目子编号
 */

public void setScmPrjSubId(Integer paraScmPrjSubId){
  super.recordChanged("SCM_PRJ_SUB_ID", this.scmPrjSubId_, paraScmPrjSubId);
  this.scmPrjSubId_ = paraScmPrjSubId;
}


/**
 * 获取 ORD_LST_COIN
 *
* @return ORD_LST_COIN
*/
public Integer getOrdLstCoin() {return this.ordLstCoin_;}
/**
* 赋值 ORD_LST_COIN

* @param paraOrdLstCoin
* ORD_LST_COIN
 */

public void setOrdLstCoin(Integer paraOrdLstCoin){
  super.recordChanged("ORD_LST_COIN", this.ordLstCoin_, paraOrdLstCoin);
  this.ordLstCoin_ = paraOrdLstCoin;
}


/**
 * 获取 ORD_LST_DATE
 *
* @return ORD_LST_DATE
*/
public Date getOrdLstDate() {return this.ordLstDate_;}
/**
* 赋值 ORD_LST_DATE

* @param paraOrdLstDate
* ORD_LST_DATE
 */

public void setOrdLstDate(Date paraOrdLstDate){
  super.recordChanged("ORD_LST_DATE", this.ordLstDate_, paraOrdLstDate);
  this.ordLstDate_ = paraOrdLstDate;
}


/**
 * 获取 ORD_LST_DATE_END
 *
* @return ORD_LST_DATE_END
*/
public Date getOrdLstDateEnd() {return this.ordLstDateEnd_;}
/**
* 赋值 ORD_LST_DATE_END

* @param paraOrdLstDateEnd
* ORD_LST_DATE_END
 */

public void setOrdLstDateEnd(Date paraOrdLstDateEnd){
  super.recordChanged("ORD_LST_DATE_END", this.ordLstDateEnd_, paraOrdLstDateEnd);
  this.ordLstDateEnd_ = paraOrdLstDateEnd;
}


/**
 * 获取 折扣
 *
* @return 折扣
*/
public Double getOrdLstDiscount() {return this.ordLstDiscount_;}
/**
* 赋值 折扣

* @param paraOrdLstDiscount
* 折扣
 */

public void setOrdLstDiscount(Double paraOrdLstDiscount){
  super.recordChanged("ORD_LST_DISCOUNT", this.ordLstDiscount_, paraOrdLstDiscount);
  this.ordLstDiscount_ = paraOrdLstDiscount;
}


/**
 * 获取 ORD_LST_PID
 *
* @return ORD_LST_PID
*/
public Integer getOrdLstPid() {return this.ordLstPid_;}
/**
* 赋值 ORD_LST_PID

* @param paraOrdLstPid
* ORD_LST_PID
 */

public void setOrdLstPid(Integer paraOrdLstPid){
  super.recordChanged("ORD_LST_PID", this.ordLstPid_, paraOrdLstPid);
  this.ordLstPid_ = paraOrdLstPid;
}


/**
 * 获取 ORD_LST_ROOM
 *
* @return ORD_LST_ROOM
*/
public Integer getOrdLstRoom() {return this.ordLstRoom_;}
/**
* 赋值 ORD_LST_ROOM

* @param paraOrdLstRoom
* ORD_LST_ROOM
 */

public void setOrdLstRoom(Integer paraOrdLstRoom){
  super.recordChanged("ORD_LST_ROOM", this.ordLstRoom_, paraOrdLstRoom);
  this.ordLstRoom_ = paraOrdLstRoom;
}


/**
 * 获取 来源服务
 *
* @return 来源服务
*/
public Integer getOrdSerId() {return this.ordSerId_;}
/**
* 赋值 来源服务

* @param paraOrdSerId
* 来源服务
 */

public void setOrdSerId(Integer paraOrdSerId){
  super.recordChanged("ord_ser_id", this.ordSerId_, paraOrdSerId);
  this.ordSerId_ = paraOrdSerId;
}


/**
 * 获取 来源ser_key_id
 *
* @return 来源ser_key_id
*/
public Integer getOrdSerKeyId() {return this.ordSerKeyId_;}
/**
* 赋值 来源ser_key_id

* @param paraOrdSerKeyId
* 来源ser_key_id
 */

public void setOrdSerKeyId(Integer paraOrdSerKeyId){
  super.recordChanged("ord_ser_key_id", this.ordSerKeyId_, paraOrdSerKeyId);
  this.ordSerKeyId_ = paraOrdSerKeyId;
}


/**
 * 获取 来源product_main的id
 *
* @return 来源product_main的id
*/
public Integer getOrdRefProductId() {return this.ordRefProductId_;}
/**
* 赋值 来源product_main的id

* @param paraOrdRefProductId
* 来源product_main的id
 */

public void setOrdRefProductId(Integer paraOrdRefProductId){
  super.recordChanged("ord_ref_product_id", this.ordRefProductId_, paraOrdRefProductId);
  this.ordRefProductId_ = paraOrdRefProductId;
}


/**
 * 获取 价格编号id
 *
* @return 价格编号id
*/
public Integer getOrdRefProductPriceId() {return this.ordRefProductPriceId_;}
/**
* 赋值 价格编号id

* @param paraOrdRefProductPriceId
* 价格编号id
 */

public void setOrdRefProductPriceId(Integer paraOrdRefProductPriceId){
  super.recordChanged("ord_ref_product_price_id", this.ordRefProductPriceId_, paraOrdRefProductPriceId);
  this.ordRefProductPriceId_ = paraOrdRefProductPriceId;
}


/**
 * 获取 服务来源表例如：bas_spot, guide_main, camp_home ..
 *
* @return 服务来源表例如：bas_spot, guide_main, camp_home ..
*/
public String getOrdRefSourceTable() {return this.ordRefSourceTable_;}
/**
* 赋值 服务来源表例如：bas_spot, guide_main, camp_home ..

* @param paraOrdRefSourceTable
* 服务来源表例如：bas_spot, guide_main, camp_home ..
 */

public void setOrdRefSourceTable(String paraOrdRefSourceTable){
  super.recordChanged("ord_ref_source_table", this.ordRefSourceTable_, paraOrdRefSourceTable);
  this.ordRefSourceTable_ = paraOrdRefSourceTable;
}


/**
 * 获取 来源表的主键, 例如spot_id , gui_id, hom_id …
 *
* @return 来源表的主键, 例如spot_id , gui_id, hom_id …
*/
public String getOrdRefSourceId() {return this.ordRefSourceId_;}
/**
* 赋值 来源表的主键, 例如spot_id , gui_id, hom_id …

* @param paraOrdRefSourceId
* 来源表的主键, 例如spot_id , gui_id, hom_id …
 */

public void setOrdRefSourceId(String paraOrdRefSourceId){
  super.recordChanged("ord_ref_source_id", this.ordRefSourceId_, paraOrdRefSourceId);
  this.ordRefSourceId_ = paraOrdRefSourceId;
}


/**
 * 获取 原始单价
 *
* @return 原始单价
*/
public Double getOriOrdLstMoney1() {return this.oriOrdLstMoney1_;}
/**
* 赋值 原始单价

* @param paraOriOrdLstMoney1
* 原始单价
 */

public void setOriOrdLstMoney1(Double paraOriOrdLstMoney1){
  super.recordChanged("ORI_ORD_LST_MONEY1", this.oriOrdLstMoney1_, paraOriOrdLstMoney1);
  this.oriOrdLstMoney1_ = paraOriOrdLstMoney1;
}


/**
 * 获取 原始货币
 *
* @return 原始货币
*/
public Integer getOriOrdLstCoin() {return this.oriOrdLstCoin_;}
/**
* 赋值 原始货币

* @param paraOriOrdLstCoin
* 原始货币
 */

public void setOriOrdLstCoin(Integer paraOriOrdLstCoin){
  super.recordChanged("ORI_ORD_LST_COIN", this.oriOrdLstCoin_, paraOriOrdLstCoin);
  this.oriOrdLstCoin_ = paraOriOrdLstCoin;
}


/**
 * 获取 汇率
 *
* @return 汇率
*/
public Double getOrdLstPoint() {return this.ordLstPoint_;}
/**
* 赋值 汇率

* @param paraOrdLstPoint
* 汇率
 */

public void setOrdLstPoint(Double paraOrdLstPoint){
  super.recordChanged("ORD_LST_POINT", this.ordLstPoint_, paraOrdLstPoint);
  this.ordLstPoint_ = paraOrdLstPoint;
}/**根据字段名称获取值，如果名称为空或字段未找到，返回空值
 @param filedName 字段名称
 @return 字段值*/
public Object getField(String filedName){
if(filedName == null){ return null; }
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("ORD_LST_ID")){return this.ordLstId_;}
if(n.equalsIgnoreCase("ORD_ID")){return this.ordId_;}
if(n.equalsIgnoreCase("SRV_ID")){return this.srvId_;}
if(n.equalsIgnoreCase("SUP_ID")){return this.supId_;}
if(n.equalsIgnoreCase("ORD_LST_NAME")){return this.ordLstName_;}
if(n.equalsIgnoreCase("ORD_LST_MONEY")){return this.ordLstMoney_;}
if(n.equalsIgnoreCase("ORD_LST_NUM1")){return this.ordLstNum1_;}
if(n.equalsIgnoreCase("ORD_LST_MONEY1")){return this.ordLstMoney1_;}
if(n.equalsIgnoreCase("ORD_LST_NUM2")){return this.ordLstNum2_;}
if(n.equalsIgnoreCase("ORD_LST_MONEY2")){return this.ordLstMoney2_;}
if(n.equalsIgnoreCase("ORD_LST_NUM3")){return this.ordLstNum3_;}
if(n.equalsIgnoreCase("ORD_LST_MONEY3")){return this.ordLstMoney3_;}
if(n.equalsIgnoreCase("ORD_LST_MEMO1")){return this.ordLstMemo1_;}
if(n.equalsIgnoreCase("ORD_LST_MEMO2")){return this.ordLstMemo2_;}
if(n.equalsIgnoreCase("ORD_LST_MEMO3")){return this.ordLstMemo3_;}
if(n.equalsIgnoreCase("ORD_LST_MEMO4")){return this.ordLstMemo4_;}
if(n.equalsIgnoreCase("ORD_LST_MEMO5")){return this.ordLstMemo5_;}
if(n.equalsIgnoreCase("ORD_LST_IDX")){return this.ordLstIdx_;}
if(n.equalsIgnoreCase("ORD_LST_ADMID")){return this.ordLstAdmid_;}
if(n.equalsIgnoreCase("SCM_PRJ_SUB_ID")){return this.scmPrjSubId_;}
if(n.equalsIgnoreCase("ORD_LST_COIN")){return this.ordLstCoin_;}
if(n.equalsIgnoreCase("ORD_LST_DATE")){return this.ordLstDate_;}
if(n.equalsIgnoreCase("ORD_LST_DATE_END")){return this.ordLstDateEnd_;}
if(n.equalsIgnoreCase("ORD_LST_DISCOUNT")){return this.ordLstDiscount_;}
if(n.equalsIgnoreCase("ORD_LST_PID")){return this.ordLstPid_;}
if(n.equalsIgnoreCase("ORD_LST_ROOM")){return this.ordLstRoom_;}
if(n.equalsIgnoreCase("ord_ser_id")){return this.ordSerId_;}
if(n.equalsIgnoreCase("ord_ser_key_id")){return this.ordSerKeyId_;}
if(n.equalsIgnoreCase("ord_ref_product_id")){return this.ordRefProductId_;}
if(n.equalsIgnoreCase("ord_ref_product_price_id")){return this.ordRefProductPriceId_;}
if(n.equalsIgnoreCase("ord_ref_source_table")){return this.ordRefSourceTable_;}
if(n.equalsIgnoreCase("ord_ref_source_id")){return this.ordRefSourceId_;}
if(n.equalsIgnoreCase("ORI_ORD_LST_MONEY1")){return this.oriOrdLstMoney1_;}
if(n.equalsIgnoreCase("ORI_ORD_LST_COIN")){return this.oriOrdLstCoin_;}
if(n.equalsIgnoreCase("ORD_LST_POINT")){return this.ordLstPoint_;}
return null;}
}