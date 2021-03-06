package com.gdxsoft.chatRoom.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**表chat_user映射类
* @author gdx 时间：Fri Jun 04 2021 16:18:09 GMT+0800 (中国标准时间)*/
@ApiModel(value = "chat_user", description = "表chat_user映射类")
public class ChatUser extends ClassBase{private Long chtUsrId_; // 用户编号
private String chtUsrUnid_; // 用户唯一编号
private String chtUsrName_; // 用户名称
private String chtUsrGender_; // 性别
private Long chtUsrPicId_; // 头像编号 chat_att.cht_att_id
private Long chtUsrBgId_; // 背景图片编号 chat_att.cht_att_id
private Integer chtUsrNation_; // 国家
private String chtUsrMobile_; // 手机电话
private Date chtUsrCtime_; // 创建时间
private Date chtUsrMtime_; // 修改时间
private String chtUsrSlogan_; // 标语
private String chtUsrRef_; // 来源数据
private String chtUsrRefId_; // 来源编号
private String chtUsrMemo_; // 备注信息
private Integer chtUsrSupId_; // 商户
private String chtUsrStatus_; // 用户状态

/**
 * 获取 用户编号
 *
* @return 用户编号
*/
@ApiModelProperty(value = "用户编号", required = true)
public Long getChtUsrId() {return this.chtUsrId_;}
/**
* 赋值 用户编号

* @param paraChtUsrId
* 用户编号
 */

public void setChtUsrId(Long paraChtUsrId){
  super.recordChanged("cht_usr_id", this.chtUsrId_, paraChtUsrId);
  this.chtUsrId_ = paraChtUsrId;
}


/**
 * 获取 用户唯一编号
 *
* @return 用户唯一编号
*/
@ApiModelProperty(value = "用户唯一编号", required = true)
public String getChtUsrUnid() {return this.chtUsrUnid_;}
/**
* 赋值 用户唯一编号

* @param paraChtUsrUnid
* 用户唯一编号
 */

public void setChtUsrUnid(String paraChtUsrUnid){
  super.recordChanged("cht_usr_unid", this.chtUsrUnid_, paraChtUsrUnid);
  this.chtUsrUnid_ = paraChtUsrUnid;
}


/**
 * 获取 用户名称
 *
* @return 用户名称
*/
@ApiModelProperty(value = "用户名称", required = true)
public String getChtUsrName() {return this.chtUsrName_;}
/**
* 赋值 用户名称

* @param paraChtUsrName
* 用户名称
 */

public void setChtUsrName(String paraChtUsrName){
  super.recordChanged("cht_usr_name", this.chtUsrName_, paraChtUsrName);
  this.chtUsrName_ = paraChtUsrName;
}


/**
 * 获取 性别
 *
* @return 性别
*/
@ApiModelProperty(value = "性别", required = true)
public String getChtUsrGender() {return this.chtUsrGender_;}
/**
* 赋值 性别

* @param paraChtUsrGender
* 性别
 */

public void setChtUsrGender(String paraChtUsrGender){
  super.recordChanged("cht_usr_gender", this.chtUsrGender_, paraChtUsrGender);
  this.chtUsrGender_ = paraChtUsrGender;
}


/**
 * 获取 头像编号 chat_att.cht_att_id
 *
* @return 头像编号 chat_att.cht_att_id
*/
@ApiModelProperty(value = "头像编号 chat_att.cht_att_id", required = false)
public Long getChtUsrPicId() {return this.chtUsrPicId_;}
/**
* 赋值 头像编号 chat_att.cht_att_id

* @param paraChtUsrPicId
* 头像编号 chat_att.cht_att_id
 */

public void setChtUsrPicId(Long paraChtUsrPicId){
  super.recordChanged("cht_usr_pic_id", this.chtUsrPicId_, paraChtUsrPicId);
  this.chtUsrPicId_ = paraChtUsrPicId;
}


/**
 * 获取 背景图片编号 chat_att.cht_att_id
 *
* @return 背景图片编号 chat_att.cht_att_id
*/
@ApiModelProperty(value = "背景图片编号 chat_att.cht_att_id", required = false)
public Long getChtUsrBgId() {return this.chtUsrBgId_;}
/**
* 赋值 背景图片编号 chat_att.cht_att_id

* @param paraChtUsrBgId
* 背景图片编号 chat_att.cht_att_id
 */

public void setChtUsrBgId(Long paraChtUsrBgId){
  super.recordChanged("cht_usr_bg_id", this.chtUsrBgId_, paraChtUsrBgId);
  this.chtUsrBgId_ = paraChtUsrBgId;
}


/**
 * 获取 国家
 *
* @return 国家
*/
@ApiModelProperty(value = "国家", required = false)
public Integer getChtUsrNation() {return this.chtUsrNation_;}
/**
* 赋值 国家

* @param paraChtUsrNation
* 国家
 */

public void setChtUsrNation(Integer paraChtUsrNation){
  super.recordChanged("cht_usr_nation", this.chtUsrNation_, paraChtUsrNation);
  this.chtUsrNation_ = paraChtUsrNation;
}


/**
 * 获取 手机电话
 *
* @return 手机电话
*/
@ApiModelProperty(value = "手机电话", required = false)
public String getChtUsrMobile() {return this.chtUsrMobile_;}
/**
* 赋值 手机电话

* @param paraChtUsrMobile
* 手机电话
 */

public void setChtUsrMobile(String paraChtUsrMobile){
  super.recordChanged("cht_usr_mobile", this.chtUsrMobile_, paraChtUsrMobile);
  this.chtUsrMobile_ = paraChtUsrMobile;
}


/**
 * 获取 创建时间
 *
* @return 创建时间
*/
@ApiModelProperty(value = "创建时间", required = true)
public Date getChtUsrCtime() {return this.chtUsrCtime_;}
/**
* 赋值 创建时间

* @param paraChtUsrCtime
* 创建时间
 */

public void setChtUsrCtime(Date paraChtUsrCtime){
  super.recordChanged("cht_usr_ctime", this.chtUsrCtime_, paraChtUsrCtime);
  this.chtUsrCtime_ = paraChtUsrCtime;
}


/**
 * 获取 修改时间
 *
* @return 修改时间
*/
@ApiModelProperty(value = "修改时间", required = true)
public Date getChtUsrMtime() {return this.chtUsrMtime_;}
/**
* 赋值 修改时间

* @param paraChtUsrMtime
* 修改时间
 */

public void setChtUsrMtime(Date paraChtUsrMtime){
  super.recordChanged("cht_usr_mtime", this.chtUsrMtime_, paraChtUsrMtime);
  this.chtUsrMtime_ = paraChtUsrMtime;
}


/**
 * 获取 标语
 *
* @return 标语
*/
@ApiModelProperty(value = "标语", required = false)
public String getChtUsrSlogan() {return this.chtUsrSlogan_;}
/**
* 赋值 标语

* @param paraChtUsrSlogan
* 标语
 */

public void setChtUsrSlogan(String paraChtUsrSlogan){
  super.recordChanged("cht_usr_slogan", this.chtUsrSlogan_, paraChtUsrSlogan);
  this.chtUsrSlogan_ = paraChtUsrSlogan;
}


/**
 * 获取 来源数据
 *
* @return 来源数据
*/
@ApiModelProperty(value = "来源数据", required = true)
public String getChtUsrRef() {return this.chtUsrRef_;}
/**
* 赋值 来源数据

* @param paraChtUsrRef
* 来源数据
 */

public void setChtUsrRef(String paraChtUsrRef){
  super.recordChanged("cht_usr_ref", this.chtUsrRef_, paraChtUsrRef);
  this.chtUsrRef_ = paraChtUsrRef;
}


/**
 * 获取 来源编号
 *
* @return 来源编号
*/
@ApiModelProperty(value = "来源编号", required = true)
public String getChtUsrRefId() {return this.chtUsrRefId_;}
/**
* 赋值 来源编号

* @param paraChtUsrRefId
* 来源编号
 */

public void setChtUsrRefId(String paraChtUsrRefId){
  super.recordChanged("cht_usr_ref_id", this.chtUsrRefId_, paraChtUsrRefId);
  this.chtUsrRefId_ = paraChtUsrRefId;
}


/**
 * 获取 备注信息
 *
* @return 备注信息
*/
@ApiModelProperty(value = "备注信息", required = false)
public String getChtUsrMemo() {return this.chtUsrMemo_;}
/**
* 赋值 备注信息

* @param paraChtUsrMemo
* 备注信息
 */

public void setChtUsrMemo(String paraChtUsrMemo){
  super.recordChanged("cht_usr_memo", this.chtUsrMemo_, paraChtUsrMemo);
  this.chtUsrMemo_ = paraChtUsrMemo;
}


/**
 * 获取 商户
 *
* @return 商户
*/
@ApiModelProperty(value = "商户", required = true)
public Integer getChtUsrSupId() {return this.chtUsrSupId_;}
/**
* 赋值 商户

* @param paraChtUsrSupId
* 商户
 */

public void setChtUsrSupId(Integer paraChtUsrSupId){
  super.recordChanged("cht_usr_sup_id", this.chtUsrSupId_, paraChtUsrSupId);
  this.chtUsrSupId_ = paraChtUsrSupId;
}


/**
 * 获取 用户状态
 *
* @return 用户状态
*/
@ApiModelProperty(value = "用户状态", required = true)
public String getChtUsrStatus() {return this.chtUsrStatus_;}
/**
* 赋值 用户状态

* @param paraChtUsrStatus
* 用户状态
 */

public void setChtUsrStatus(String paraChtUsrStatus){
  super.recordChanged("cht_usr_status", this.chtUsrStatus_, paraChtUsrStatus);
  this.chtUsrStatus_ = paraChtUsrStatus;
}
}