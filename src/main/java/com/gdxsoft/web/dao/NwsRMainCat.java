package com.gdxsoft.web.dao;

import com.gdxsoft.easyweb.datasource.ClassBase;
/**表nws_r_main_cat映射类
* @author gdx 时间：Mon May 10 2021 10:57:50 GMT+0800 (中国标准时间)*/
public class NwsRMainCat extends ClassBase{private Long nwsCatId_; // 目录编号
private Long nwsId_; // 新闻编号
private Integer nrmOrd_; // 排序

/**
 * 获取 目录编号
 *
* @return 目录编号
*/
public Long getNwsCatId() {return this.nwsCatId_;}
/**
* 赋值 目录编号

* @param paraNwsCatId
* 目录编号
 */

public void setNwsCatId(Long paraNwsCatId){
  super.recordChanged("NWS_CAT_ID", this.nwsCatId_, paraNwsCatId);
  this.nwsCatId_ = paraNwsCatId;
}


/**
 * 获取 新闻编号
 *
* @return 新闻编号
*/
public Long getNwsId() {return this.nwsId_;}
/**
* 赋值 新闻编号

* @param paraNwsId
* 新闻编号
 */

public void setNwsId(Long paraNwsId){
  super.recordChanged("NWS_ID", this.nwsId_, paraNwsId);
  this.nwsId_ = paraNwsId;
}


/**
 * 获取 排序
 *
* @return 排序
*/
public Integer getNrmOrd() {return this.nrmOrd_;}
/**
* 赋值 排序

* @param paraNrmOrd
* 排序
 */

public void setNrmOrd(Integer paraNrmOrd){
  super.recordChanged("NRM_ORD", this.nrmOrd_, paraNrmOrd);
  this.nrmOrd_ = paraNrmOrd;
}
}