package com.gdxsoft.chatRoom.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表chat_topic映射类
* @author gdx 时间：Tue Jun 01 2021 09:25:03 GMT+0800 (中国标准时间)*/
public class ChatTopic extends ClassBase{private Long chtId_; // 内容编号
private Long chtRomId_; // 房间号
private Integer chtLvl_; // 楼层
private String chtType_; // 类型
private String chtStatus_; // 状态
private Date chtCtime_; // 创建时间
private Date chtMtime_; // 修改时间
private Long chtUsrId_; // 发帖人
private String chtIp_; // 来源ip
private String chtUa_; // 浏览器代理

/**
 * 获取 内容编号
 *
* @return 内容编号
*/
public Long getChtId() {return this.chtId_;}
/**
* 赋值 内容编号

* @param paraChtId
* 内容编号
 */

public void setChtId(Long paraChtId){
  super.recordChanged("cht_id", this.chtId_, paraChtId);
  this.chtId_ = paraChtId;
}


/**
 * 获取 房间号
 *
* @return 房间号
*/
public Long getChtRomId() {return this.chtRomId_;}
/**
* 赋值 房间号

* @param paraChtRomId
* 房间号
 */

public void setChtRomId(Long paraChtRomId){
  super.recordChanged("cht_rom_id", this.chtRomId_, paraChtRomId);
  this.chtRomId_ = paraChtRomId;
}


/**
 * 获取 楼层
 *
* @return 楼层
*/
public Integer getChtLvl() {return this.chtLvl_;}
/**
* 赋值 楼层

* @param paraChtLvl
* 楼层
 */

public void setChtLvl(Integer paraChtLvl){
  super.recordChanged("cht_lvl", this.chtLvl_, paraChtLvl);
  this.chtLvl_ = paraChtLvl;
}


/**
 * 获取 类型
 *
* @return 类型
*/
public String getChtType() {return this.chtType_;}
/**
* 赋值 类型

* @param paraChtType
* 类型
 */

public void setChtType(String paraChtType){
  super.recordChanged("cht_type", this.chtType_, paraChtType);
  this.chtType_ = paraChtType;
}


/**
 * 获取 状态
 *
* @return 状态
*/
public String getChtStatus() {return this.chtStatus_;}
/**
* 赋值 状态

* @param paraChtStatus
* 状态
 */

public void setChtStatus(String paraChtStatus){
  super.recordChanged("cht_status", this.chtStatus_, paraChtStatus);
  this.chtStatus_ = paraChtStatus;
}


/**
 * 获取 创建时间
 *
* @return 创建时间
*/
public Date getChtCtime() {return this.chtCtime_;}
/**
* 赋值 创建时间

* @param paraChtCtime
* 创建时间
 */

public void setChtCtime(Date paraChtCtime){
  super.recordChanged("cht_ctime", this.chtCtime_, paraChtCtime);
  this.chtCtime_ = paraChtCtime;
}


/**
 * 获取 修改时间
 *
* @return 修改时间
*/
public Date getChtMtime() {return this.chtMtime_;}
/**
* 赋值 修改时间

* @param paraChtMtime
* 修改时间
 */

public void setChtMtime(Date paraChtMtime){
  super.recordChanged("cht_mtime", this.chtMtime_, paraChtMtime);
  this.chtMtime_ = paraChtMtime;
}


/**
 * 获取 发帖人
 *
* @return 发帖人
*/
public Long getChtUsrId() {return this.chtUsrId_;}
/**
* 赋值 发帖人

* @param paraChtUsrId
* 发帖人
 */

public void setChtUsrId(Long paraChtUsrId){
  super.recordChanged("cht_usr_id", this.chtUsrId_, paraChtUsrId);
  this.chtUsrId_ = paraChtUsrId;
}


/**
 * 获取 来源ip
 *
* @return 来源ip
*/
public String getChtIp() {return this.chtIp_;}
/**
* 赋值 来源ip

* @param paraChtIp
* 来源ip
 */

public void setChtIp(String paraChtIp){
  super.recordChanged("cht_ip", this.chtIp_, paraChtIp);
  this.chtIp_ = paraChtIp;
}


/**
 * 获取 浏览器代理
 *
* @return 浏览器代理
*/
public String getChtUa() {return this.chtUa_;}
/**
* 赋值 浏览器代理

* @param paraChtUa
* 浏览器代理
 */

public void setChtUa(String paraChtUa){
  super.recordChanged("cht_ua", this.chtUa_, paraChtUa);
  this.chtUa_ = paraChtUa;
}
}