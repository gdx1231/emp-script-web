package com.gdxsoft.chatRoom.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**表chat_cnt映射类
* @author gdx 时间：Fri Jun 04 2021 17:26:58 GMT+0800 (中国标准时间)*/
@ApiModel(value = "chat_cnt", description = "表chat_cnt映射类")
public class ChatCnt extends ClassBase{private Long chtId_; // 内容编号
private String chtCnt_; // 内容
private String chtCntTxt_; // 内容（纯文本）

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
 * 获取 内容
 *
* @return 内容
*/
@ApiModelProperty(value = "内容", required = false)
public String getChtCnt() {return this.chtCnt_;}
/**
* 赋值 内容

* @param paraChtCnt
* 内容
 */

public void setChtCnt(String paraChtCnt){
  super.recordChanged("cht_cnt", this.chtCnt_, paraChtCnt);
  this.chtCnt_ = paraChtCnt;
}


/**
 * 获取 内容（纯文本）
 *
* @return 内容（纯文本）
*/
@ApiModelProperty(value = "内容（纯文本）", required = false)
public String getChtCntTxt() {return this.chtCntTxt_;}
/**
* 赋值 内容（纯文本）

* @param paraChtCntTxt
* 内容（纯文本）
 */

public void setChtCntTxt(String paraChtCntTxt){
  super.recordChanged("cht_cnt_txt", this.chtCntTxt_, paraChtCntTxt);
  this.chtCntTxt_ = paraChtCntTxt;
}
}