package com.gdxsoft.chatRoom.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**表cht_acl映射类
* @author gdx 时间：Wed Jun 02 2021 11:30:37 GMT+0800 (中国标准时间)*/
@ApiModel(value = "cht_acl", description = "表cht_acl映射类")
public class ChtAcl extends ClassBase{private Long chtRomId_; // 房间号
private Long chtUsrId_; // 用户
private String chtAclMaster_; // 管理员
private String chtAclTop_; // 顶置话题
private Date chtAclTime_; // 加入时间

/**
 * 获取 房间号
 *
* @return 房间号
*/
@ApiModelProperty(value = "房间号", required = true)
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
 * 获取 用户
 *
* @return 用户
*/
@ApiModelProperty(value = "用户", required = true)
public Long getChtUsrId() {return this.chtUsrId_;}
/**
* 赋值 用户

* @param paraChtUsrId
* 用户
 */

public void setChtUsrId(Long paraChtUsrId){
  super.recordChanged("cht_usr_id", this.chtUsrId_, paraChtUsrId);
  this.chtUsrId_ = paraChtUsrId;
}


/**
 * 获取 管理员
 *
* @return 管理员
*/
@ApiModelProperty(value = "管理员", required = true)
public String getChtAclMaster() {return this.chtAclMaster_;}
/**
* 赋值 管理员

* @param paraChtAclMaster
* 管理员
 */

public void setChtAclMaster(String paraChtAclMaster){
  super.recordChanged("cht_acl_master", this.chtAclMaster_, paraChtAclMaster);
  this.chtAclMaster_ = paraChtAclMaster;
}


/**
 * 获取 顶置话题
 *
* @return 顶置话题
*/
@ApiModelProperty(value = "顶置话题", required = true)
public String getChtAclTop() {return this.chtAclTop_;}
/**
* 赋值 顶置话题

* @param paraChtAclTop
* 顶置话题
 */

public void setChtAclTop(String paraChtAclTop){
  super.recordChanged("cht_acl_top", this.chtAclTop_, paraChtAclTop);
  this.chtAclTop_ = paraChtAclTop;
}


/**
 * 获取 加入时间
 *
* @return 加入时间
*/
@ApiModelProperty(value = "加入时间", required = true)
public Date getChtAclTime() {return this.chtAclTime_;}
/**
* 赋值 加入时间

* @param paraChtAclTime
* 加入时间
 */

public void setChtAclTime(Date paraChtAclTime){
  super.recordChanged("cht_acl_time", this.chtAclTime_, paraChtAclTime);
  this.chtAclTime_ = paraChtAclTime;
}
}