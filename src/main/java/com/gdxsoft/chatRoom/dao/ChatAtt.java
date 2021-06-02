package com.gdxsoft.chatRoom.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**表chat_att映射类
* @author gdx 时间：Wed Jun 02 2021 11:30:51 GMT+0800 (中国标准时间)*/
@ApiModel(value = "chat_att", description = "表chat_att映射类")
public class ChatAtt extends ClassBase{private Long chtAttId_; // 附件编号
private Long chtUsrId_; // 用户编号
private Long chtId_; // 内容编号
private String chtAttName_; // 文件名
private String chtAttSrcName_; // 来源名称
private String chtAttPath_; // 保存路径
private Integer chtAttSize_; // 文件大小
private String chtAttExt_; // 扩展名
private String chtAttMd5_; // md5
private Date chtAttTime_; // 创建时间
private String chtAttStatus_; // 状态
private String chtAttUrl_; // 下载地址

/**
 * 获取 附件编号
 *
* @return 附件编号
*/
@ApiModelProperty(value = "附件编号", required = true)
public Long getChtAttId() {return this.chtAttId_;}
/**
* 赋值 附件编号

* @param paraChtAttId
* 附件编号
 */

public void setChtAttId(Long paraChtAttId){
  super.recordChanged("cht_att_id", this.chtAttId_, paraChtAttId);
  this.chtAttId_ = paraChtAttId;
}


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
 * 获取 内容编号
 *
* @return 内容编号
*/
@ApiModelProperty(value = "内容编号", required = true)
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
 * 获取 文件名
 *
* @return 文件名
*/
@ApiModelProperty(value = "文件名", required = false)
public String getChtAttName() {return this.chtAttName_;}
/**
* 赋值 文件名

* @param paraChtAttName
* 文件名
 */

public void setChtAttName(String paraChtAttName){
  super.recordChanged("cht_att_name", this.chtAttName_, paraChtAttName);
  this.chtAttName_ = paraChtAttName;
}


/**
 * 获取 来源名称
 *
* @return 来源名称
*/
@ApiModelProperty(value = "来源名称", required = false)
public String getChtAttSrcName() {return this.chtAttSrcName_;}
/**
* 赋值 来源名称

* @param paraChtAttSrcName
* 来源名称
 */

public void setChtAttSrcName(String paraChtAttSrcName){
  super.recordChanged("cht_att_src_name", this.chtAttSrcName_, paraChtAttSrcName);
  this.chtAttSrcName_ = paraChtAttSrcName;
}


/**
 * 获取 保存路径
 *
* @return 保存路径
*/
@ApiModelProperty(value = "保存路径", required = true)
public String getChtAttPath() {return this.chtAttPath_;}
/**
* 赋值 保存路径

* @param paraChtAttPath
* 保存路径
 */

public void setChtAttPath(String paraChtAttPath){
  super.recordChanged("cht_att_path", this.chtAttPath_, paraChtAttPath);
  this.chtAttPath_ = paraChtAttPath;
}


/**
 * 获取 文件大小
 *
* @return 文件大小
*/
@ApiModelProperty(value = "文件大小", required = true)
public Integer getChtAttSize() {return this.chtAttSize_;}
/**
* 赋值 文件大小

* @param paraChtAttSize
* 文件大小
 */

public void setChtAttSize(Integer paraChtAttSize){
  super.recordChanged("cht_att_size", this.chtAttSize_, paraChtAttSize);
  this.chtAttSize_ = paraChtAttSize;
}


/**
 * 获取 扩展名
 *
* @return 扩展名
*/
@ApiModelProperty(value = "扩展名", required = true)
public String getChtAttExt() {return this.chtAttExt_;}
/**
* 赋值 扩展名

* @param paraChtAttExt
* 扩展名
 */

public void setChtAttExt(String paraChtAttExt){
  super.recordChanged("cht_att_ext", this.chtAttExt_, paraChtAttExt);
  this.chtAttExt_ = paraChtAttExt;
}


/**
 * 获取 md5
 *
* @return md5
*/
@ApiModelProperty(value = "md5", required = true)
public String getChtAttMd5() {return this.chtAttMd5_;}
/**
* 赋值 md5

* @param paraChtAttMd5
* md5
 */

public void setChtAttMd5(String paraChtAttMd5){
  super.recordChanged("cht_att_md5", this.chtAttMd5_, paraChtAttMd5);
  this.chtAttMd5_ = paraChtAttMd5;
}


/**
 * 获取 创建时间
 *
* @return 创建时间
*/
@ApiModelProperty(value = "创建时间", required = true)
public Date getChtAttTime() {return this.chtAttTime_;}
/**
* 赋值 创建时间

* @param paraChtAttTime
* 创建时间
 */

public void setChtAttTime(Date paraChtAttTime){
  super.recordChanged("cht_att_time", this.chtAttTime_, paraChtAttTime);
  this.chtAttTime_ = paraChtAttTime;
}


/**
 * 获取 状态
 *
* @return 状态
*/
@ApiModelProperty(value = "状态", required = true)
public String getChtAttStatus() {return this.chtAttStatus_;}
/**
* 赋值 状态

* @param paraChtAttStatus
* 状态
 */

public void setChtAttStatus(String paraChtAttStatus){
  super.recordChanged("cht_att_status", this.chtAttStatus_, paraChtAttStatus);
  this.chtAttStatus_ = paraChtAttStatus;
}


/**
 * 获取 下载地址
 *
* @return 下载地址
*/
@ApiModelProperty(value = "下载地址", required = false)
public String getChtAttUrl() {return this.chtAttUrl_;}
/**
* 赋值 下载地址

* @param paraChtAttUrl
* 下载地址
 */

public void setChtAttUrl(String paraChtAttUrl){
  super.recordChanged("cht_att_url", this.chtAttUrl_, paraChtAttUrl);
  this.chtAttUrl_ = paraChtAttUrl;
}
}