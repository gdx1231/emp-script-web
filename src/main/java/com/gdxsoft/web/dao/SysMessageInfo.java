package com.gdxsoft.web.dao;

import java.util.*;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表SYS_MESSAGE_INFO映射类
 * @author gdx 时间：Wed Aug 02 2023 09:12:24 GMT+0800 (中国标准时间)*/
public class SysMessageInfo extends ClassBase { // 编号, int identity, length:10, null:false, pk:true
    private Integer messageId_;
    // 来自供应商, int, length:10, null:true, pk:false
    private Integer fromSupId_;
    // 来自用户, int, length:10, null:true, pk:false
    private Integer fromUsrId_;
    // FROM_NAME, nvarchar, length:50, null:true, pk:false
    private String fromName_;
    // 目标用户, int, length:10, null:true, pk:false
    private Integer targetUsrId_;
    // 目标供应商, int, length:10, null:true, pk:false
    private Integer targetSupId_;
    // TARGET_NAME, nvarchar, length:50, null:true, pk:false
    private String targetName_;
    // 来源地址, varchar, length:200, null:true, pk:false
    private String fromEmail_;
    // 目标地址, varchar, length:2000, null:true, pk:false
    private String targetEmail_;
    // 目标类型, varchar, length:20, null:true, pk:false
    private String targetType_;
    // 标题, nvarchar, length:500, null:true, pk:false
    private String messageTitle_;
    // 内容, nvarchar, length:2147483647, null:true, pk:false
    private String messageContent_;
    // 状态, varchar, length:20, null:true, pk:false
    private String messageStatus_;
    // 创建时间, datetime, length:23, null:true, pk:false
    private Date createDate_;
    // 发送时间, datetime, length:23, null:true, pk:false
    private Date sendDate_;
    // 模块类型, varchar, length:20, null:true, pk:false
    private String moduleType_;
    // IS_READ, nvarchar, length:20, null:true, pk:false
    private String isRead_;
    // REF_ID, varchar, length:51, null:true, pk:false
    private String refId_;
    // REF_TABLE, varchar, length:200, null:true, pk:false
    private String refTable_;
    // CAL_SDATE, datetime, length:23, null:true, pk:false
    private Date calSdate_;
    // CAL_EDATE, datetime, length:23, null:true, pk:false
    private Date calEdate_;
    // MAIL_TYPE, varchar, length:20, null:true, pk:false
    private String mailType_;
    // ATTS, ntext, length:1073741823, null:true, pk:false
    private String atts_;
    // MESSSAGE_LOG, nvarchar, length:2000, null:true, pk:false
    private String messsageLog_;
    // FACEBACK_URL, varchar, length:1000, null:true, pk:false
    private String facebackUrl_;
    // REPLAY_TO_EMAIL, varchar, length:200, null:true, pk:false
    private String replayToEmail_;
    // REPLAY_TO_NAME, nvarchar, length:50, null:true, pk:false
    private String replayToName_;
    // SINGLE_TO_EMAIL, varchar, length:200, null:true, pk:false
    private String singleToEmail_;
    // SINGLE_TO_NAME, nvarchar, length:50, null:true, pk:false
    private String singleToName_;
    // SMTP_CFG, varchar, length:1000, null:true, pk:false
    private String smtpCfg_;
    // MQ_MSG_ID, char, length:32, null:true, pk:false
    private String mqMsgId_;
    // MQ_MSG, nvarchar, length:2147483647, null:true, pk:false
    private String mqMsg_;
    // CC_EMAILS, nvarchar, length:2147483647, null:true, pk:false
    private String ccEmails_;
    // CC_NAMES, nvarchar, length:2147483647, null:true, pk:false
    private String ccNames_;
    // BCC_EMAILS, nvarchar, length:2147483647, null:true, pk:false
    private String bccEmails_;
    // BCC_NAMES, nvarchar, length:2147483647, null:true, pk:false
    private String bccNames_;
    // MESSAGE_MD5, varchar, length:32, null:true, pk:false
    private String messageMd5_;

    /**
     * 获取 编号
     *
     * @return 编号, int identity, length:10, null:false, pk:true
     */
    public Integer getMessageId() {
        return this.messageId_;
    }
    /**
    * 赋值 编号

    * @param paraMessageId
    * 编号, int identity, length:10, null:false, pk:true
     */

    public void setMessageId(Integer paraMessageId) {
        super.recordChanged("MESSAGE_ID", this.messageId_, paraMessageId);
        this.messageId_ = paraMessageId;
    }


    /**
     * 获取 来自供应商
     *
     * @return 来自供应商, int, length:10, null:true, pk:false
     */
    public Integer getFromSupId() {
        return this.fromSupId_;
    }
    /**
    * 赋值 来自供应商

    * @param paraFromSupId
    * 来自供应商, int, length:10, null:true, pk:false
     */

    public void setFromSupId(Integer paraFromSupId) {
        super.recordChanged("FROM_SUP_ID", this.fromSupId_, paraFromSupId);
        this.fromSupId_ = paraFromSupId;
    }


    /**
     * 获取 来自用户
     *
     * @return 来自用户, int, length:10, null:true, pk:false
     */
    public Integer getFromUsrId() {
        return this.fromUsrId_;
    }
    /**
    * 赋值 来自用户

    * @param paraFromUsrId
    * 来自用户, int, length:10, null:true, pk:false
     */

    public void setFromUsrId(Integer paraFromUsrId) {
        super.recordChanged("FROM_USR_ID", this.fromUsrId_, paraFromUsrId);
        this.fromUsrId_ = paraFromUsrId;
    }


    /**
     * 获取 FROM_NAME
     *
     * @return FROM_NAME, nvarchar, length:50, null:true, pk:false
     */
    public String getFromName() {
        return this.fromName_;
    }
    /**
    * 赋值 FROM_NAME

    * @param paraFromName
    * FROM_NAME, nvarchar, length:50, null:true, pk:false
     */

    public void setFromName(String paraFromName) {
        super.recordChanged("FROM_NAME", this.fromName_, paraFromName);
        this.fromName_ = paraFromName;
    }


    /**
     * 获取 目标用户
     *
     * @return 目标用户, int, length:10, null:true, pk:false
     */
    public Integer getTargetUsrId() {
        return this.targetUsrId_;
    }
    /**
    * 赋值 目标用户

    * @param paraTargetUsrId
    * 目标用户, int, length:10, null:true, pk:false
     */

    public void setTargetUsrId(Integer paraTargetUsrId) {
        super.recordChanged("TARGET_USR_ID", this.targetUsrId_, paraTargetUsrId);
        this.targetUsrId_ = paraTargetUsrId;
    }


    /**
     * 获取 目标供应商
     *
     * @return 目标供应商, int, length:10, null:true, pk:false
     */
    public Integer getTargetSupId() {
        return this.targetSupId_;
    }
    /**
    * 赋值 目标供应商

    * @param paraTargetSupId
    * 目标供应商, int, length:10, null:true, pk:false
     */

    public void setTargetSupId(Integer paraTargetSupId) {
        super.recordChanged("TARGET_SUP_ID", this.targetSupId_, paraTargetSupId);
        this.targetSupId_ = paraTargetSupId;
    }


    /**
     * 获取 TARGET_NAME
     *
     * @return TARGET_NAME, nvarchar, length:50, null:true, pk:false
     */
    public String getTargetName() {
        return this.targetName_;
    }
    /**
    * 赋值 TARGET_NAME

    * @param paraTargetName
    * TARGET_NAME, nvarchar, length:50, null:true, pk:false
     */

    public void setTargetName(String paraTargetName) {
        super.recordChanged("TARGET_NAME", this.targetName_, paraTargetName);
        this.targetName_ = paraTargetName;
    }


    /**
     * 获取 来源地址
     *
     * @return 来源地址, varchar, length:200, null:true, pk:false
     */
    public String getFromEmail() {
        return this.fromEmail_;
    }
    /**
    * 赋值 来源地址

    * @param paraFromEmail
    * 来源地址, varchar, length:200, null:true, pk:false
     */

    public void setFromEmail(String paraFromEmail) {
        super.recordChanged("FROM_EMAIL", this.fromEmail_, paraFromEmail);
        this.fromEmail_ = paraFromEmail;
    }


    /**
     * 获取 目标地址
     *
     * @return 目标地址, varchar, length:2000, null:true, pk:false
     */
    public String getTargetEmail() {
        return this.targetEmail_;
    }
    /**
    * 赋值 目标地址

    * @param paraTargetEmail
    * 目标地址, varchar, length:2000, null:true, pk:false
     */

    public void setTargetEmail(String paraTargetEmail) {
        super.recordChanged("TARGET_EMAIL", this.targetEmail_, paraTargetEmail);
        this.targetEmail_ = paraTargetEmail;
    }


    /**
     * 获取 目标类型
     *
     * @return 目标类型, varchar, length:20, null:true, pk:false
     */
    public String getTargetType() {
        return this.targetType_;
    }
    /**
    * 赋值 目标类型

    * @param paraTargetType
    * 目标类型, varchar, length:20, null:true, pk:false
     */

    public void setTargetType(String paraTargetType) {
        super.recordChanged("TARGET_TYPE", this.targetType_, paraTargetType);
        this.targetType_ = paraTargetType;
    }


    /**
     * 获取 标题
     *
     * @return 标题, nvarchar, length:500, null:true, pk:false
     */
    public String getMessageTitle() {
        return this.messageTitle_;
    }
    /**
    * 赋值 标题

    * @param paraMessageTitle
    * 标题, nvarchar, length:500, null:true, pk:false
     */

    public void setMessageTitle(String paraMessageTitle) {
        super.recordChanged("MESSAGE_TITLE", this.messageTitle_, paraMessageTitle);
        this.messageTitle_ = paraMessageTitle;
    }


    /**
     * 获取 内容
     *
     * @return 内容, nvarchar, length:2147483647, null:true, pk:false
     */
    public String getMessageContent() {
        return this.messageContent_;
    }
    /**
    * 赋值 内容

    * @param paraMessageContent
    * 内容, nvarchar, length:2147483647, null:true, pk:false
     */

    public void setMessageContent(String paraMessageContent) {
        super.recordChanged("MESSAGE_CONTENT", this.messageContent_, paraMessageContent);
        this.messageContent_ = paraMessageContent;
    }


    /**
     * 获取 状态
     *
     * @return 状态, varchar, length:20, null:true, pk:false
     */
    public String getMessageStatus() {
        return this.messageStatus_;
    }
    /**
    * 赋值 状态

    * @param paraMessageStatus
    * 状态, varchar, length:20, null:true, pk:false
     */

    public void setMessageStatus(String paraMessageStatus) {
        super.recordChanged("MESSAGE_STATUS", this.messageStatus_, paraMessageStatus);
        this.messageStatus_ = paraMessageStatus;
    }


    /**
     * 获取 创建时间
     *
     * @return 创建时间, datetime, length:23, null:true, pk:false
     */
    public Date getCreateDate() {
        return this.createDate_;
    }
    /**
    * 赋值 创建时间

    * @param paraCreateDate
    * 创建时间, datetime, length:23, null:true, pk:false
     */

    public void setCreateDate(Date paraCreateDate) {
        super.recordChanged("CREATE_DATE", this.createDate_, paraCreateDate);
        this.createDate_ = paraCreateDate;
    }


    /**
     * 获取 发送时间
     *
     * @return 发送时间, datetime, length:23, null:true, pk:false
     */
    public Date getSendDate() {
        return this.sendDate_;
    }
    /**
    * 赋值 发送时间

    * @param paraSendDate
    * 发送时间, datetime, length:23, null:true, pk:false
     */

    public void setSendDate(Date paraSendDate) {
        super.recordChanged("SEND_DATE", this.sendDate_, paraSendDate);
        this.sendDate_ = paraSendDate;
    }


    /**
     * 获取 模块类型
     *
     * @return 模块类型, varchar, length:20, null:true, pk:false
     */
    public String getModuleType() {
        return this.moduleType_;
    }
    /**
    * 赋值 模块类型

    * @param paraModuleType
    * 模块类型, varchar, length:20, null:true, pk:false
     */

    public void setModuleType(String paraModuleType) {
        super.recordChanged("MODULE_TYPE", this.moduleType_, paraModuleType);
        this.moduleType_ = paraModuleType;
    }


    /**
     * 获取 IS_READ
     *
     * @return IS_READ, nvarchar, length:20, null:true, pk:false
     */
    public String getIsRead() {
        return this.isRead_;
    }
    /**
    * 赋值 IS_READ

    * @param paraIsRead
    * IS_READ, nvarchar, length:20, null:true, pk:false
     */

    public void setIsRead(String paraIsRead) {
        super.recordChanged("IS_READ", this.isRead_, paraIsRead);
        this.isRead_ = paraIsRead;
    }


    /**
     * 获取 REF_ID
     *
     * @return REF_ID, varchar, length:51, null:true, pk:false
     */
    public String getRefId() {
        return this.refId_;
    }
    /**
    * 赋值 REF_ID

    * @param paraRefId
    * REF_ID, varchar, length:51, null:true, pk:false
     */

    public void setRefId(String paraRefId) {
        super.recordChanged("REF_ID", this.refId_, paraRefId);
        this.refId_ = paraRefId;
    }


    /**
     * 获取 REF_TABLE
     *
     * @return REF_TABLE, varchar, length:200, null:true, pk:false
     */
    public String getRefTable() {
        return this.refTable_;
    }
    /**
    * 赋值 REF_TABLE

    * @param paraRefTable
    * REF_TABLE, varchar, length:200, null:true, pk:false
     */

    public void setRefTable(String paraRefTable) {
        super.recordChanged("REF_TABLE", this.refTable_, paraRefTable);
        this.refTable_ = paraRefTable;
    }


    /**
     * 获取 CAL_SDATE
     *
     * @return CAL_SDATE, datetime, length:23, null:true, pk:false
     */
    public Date getCalSdate() {
        return this.calSdate_;
    }
    /**
    * 赋值 CAL_SDATE

    * @param paraCalSdate
    * CAL_SDATE, datetime, length:23, null:true, pk:false
     */

    public void setCalSdate(Date paraCalSdate) {
        super.recordChanged("CAL_SDATE", this.calSdate_, paraCalSdate);
        this.calSdate_ = paraCalSdate;
    }


    /**
     * 获取 CAL_EDATE
     *
     * @return CAL_EDATE, datetime, length:23, null:true, pk:false
     */
    public Date getCalEdate() {
        return this.calEdate_;
    }
    /**
    * 赋值 CAL_EDATE

    * @param paraCalEdate
    * CAL_EDATE, datetime, length:23, null:true, pk:false
     */

    public void setCalEdate(Date paraCalEdate) {
        super.recordChanged("CAL_EDATE", this.calEdate_, paraCalEdate);
        this.calEdate_ = paraCalEdate;
    }


    /**
     * 获取 MAIL_TYPE
     *
     * @return MAIL_TYPE, varchar, length:20, null:true, pk:false
     */
    public String getMailType() {
        return this.mailType_;
    }
    /**
    * 赋值 MAIL_TYPE

    * @param paraMailType
    * MAIL_TYPE, varchar, length:20, null:true, pk:false
     */

    public void setMailType(String paraMailType) {
        super.recordChanged("MAIL_TYPE", this.mailType_, paraMailType);
        this.mailType_ = paraMailType;
    }


    /**
     * 获取 ATTS
     *
     * @return ATTS, ntext, length:1073741823, null:true, pk:false
     */
    public String getAtts() {
        return this.atts_;
    }
    /**
    * 赋值 ATTS

    * @param paraAtts
    * ATTS, ntext, length:1073741823, null:true, pk:false
     */

    public void setAtts(String paraAtts) {
        super.recordChanged("ATTS", this.atts_, paraAtts);
        this.atts_ = paraAtts;
    }


    /**
     * 获取 MESSSAGE_LOG
     *
     * @return MESSSAGE_LOG, nvarchar, length:2000, null:true, pk:false
     */
    public String getMesssageLog() {
        return this.messsageLog_;
    }
    /**
    * 赋值 MESSSAGE_LOG

    * @param paraMesssageLog
    * MESSSAGE_LOG, nvarchar, length:2000, null:true, pk:false
     */

    public void setMesssageLog(String paraMesssageLog) {
        super.recordChanged("MESSSAGE_LOG", this.messsageLog_, paraMesssageLog);
        this.messsageLog_ = paraMesssageLog;
    }


    /**
     * 获取 FACEBACK_URL
     *
     * @return FACEBACK_URL, varchar, length:1000, null:true, pk:false
     */
    public String getFacebackUrl() {
        return this.facebackUrl_;
    }
    /**
    * 赋值 FACEBACK_URL

    * @param paraFacebackUrl
    * FACEBACK_URL, varchar, length:1000, null:true, pk:false
     */

    public void setFacebackUrl(String paraFacebackUrl) {
        super.recordChanged("FACEBACK_URL", this.facebackUrl_, paraFacebackUrl);
        this.facebackUrl_ = paraFacebackUrl;
    }


    /**
     * 获取 REPLAY_TO_EMAIL
     *
     * @return REPLAY_TO_EMAIL, varchar, length:200, null:true, pk:false
     */
    public String getReplayToEmail() {
        return this.replayToEmail_;
    }
    /**
    * 赋值 REPLAY_TO_EMAIL

    * @param paraReplayToEmail
    * REPLAY_TO_EMAIL, varchar, length:200, null:true, pk:false
     */

    public void setReplayToEmail(String paraReplayToEmail) {
        super.recordChanged("REPLAY_TO_EMAIL", this.replayToEmail_, paraReplayToEmail);
        this.replayToEmail_ = paraReplayToEmail;
    }


    /**
     * 获取 REPLAY_TO_NAME
     *
     * @return REPLAY_TO_NAME, nvarchar, length:50, null:true, pk:false
     */
    public String getReplayToName() {
        return this.replayToName_;
    }
    /**
    * 赋值 REPLAY_TO_NAME

    * @param paraReplayToName
    * REPLAY_TO_NAME, nvarchar, length:50, null:true, pk:false
     */

    public void setReplayToName(String paraReplayToName) {
        super.recordChanged("REPLAY_TO_NAME", this.replayToName_, paraReplayToName);
        this.replayToName_ = paraReplayToName;
    }


    /**
     * 获取 SINGLE_TO_EMAIL
     *
     * @return SINGLE_TO_EMAIL, varchar, length:200, null:true, pk:false
     */
    public String getSingleToEmail() {
        return this.singleToEmail_;
    }
    /**
    * 赋值 SINGLE_TO_EMAIL

    * @param paraSingleToEmail
    * SINGLE_TO_EMAIL, varchar, length:200, null:true, pk:false
     */

    public void setSingleToEmail(String paraSingleToEmail) {
        super.recordChanged("SINGLE_TO_EMAIL", this.singleToEmail_, paraSingleToEmail);
        this.singleToEmail_ = paraSingleToEmail;
    }


    /**
     * 获取 SINGLE_TO_NAME
     *
     * @return SINGLE_TO_NAME, nvarchar, length:50, null:true, pk:false
     */
    public String getSingleToName() {
        return this.singleToName_;
    }
    /**
    * 赋值 SINGLE_TO_NAME

    * @param paraSingleToName
    * SINGLE_TO_NAME, nvarchar, length:50, null:true, pk:false
     */

    public void setSingleToName(String paraSingleToName) {
        super.recordChanged("SINGLE_TO_NAME", this.singleToName_, paraSingleToName);
        this.singleToName_ = paraSingleToName;
    }


    /**
     * 获取 SMTP_CFG
     *
     * @return SMTP_CFG, varchar, length:1000, null:true, pk:false
     */
    public String getSmtpCfg() {
        return this.smtpCfg_;
    }
    /**
    * 赋值 SMTP_CFG

    * @param paraSmtpCfg
    * SMTP_CFG, varchar, length:1000, null:true, pk:false
     */

    public void setSmtpCfg(String paraSmtpCfg) {
        super.recordChanged("SMTP_CFG", this.smtpCfg_, paraSmtpCfg);
        this.smtpCfg_ = paraSmtpCfg;
    }


    /**
     * 获取 MQ_MSG_ID
     *
     * @return MQ_MSG_ID, char, length:32, null:true, pk:false
     */
    public String getMqMsgId() {
        return this.mqMsgId_;
    }
    /**
    * 赋值 MQ_MSG_ID

    * @param paraMqMsgId
    * MQ_MSG_ID, char, length:32, null:true, pk:false
     */

    public void setMqMsgId(String paraMqMsgId) {
        super.recordChanged("MQ_MSG_ID", this.mqMsgId_, paraMqMsgId);
        this.mqMsgId_ = paraMqMsgId;
    }


    /**
     * 获取 MQ_MSG
     *
     * @return MQ_MSG, nvarchar, length:2147483647, null:true, pk:false
     */
    public String getMqMsg() {
        return this.mqMsg_;
    }
    /**
    * 赋值 MQ_MSG

    * @param paraMqMsg
    * MQ_MSG, nvarchar, length:2147483647, null:true, pk:false
     */

    public void setMqMsg(String paraMqMsg) {
        super.recordChanged("MQ_MSG", this.mqMsg_, paraMqMsg);
        this.mqMsg_ = paraMqMsg;
    }


    /**
     * 获取 CC_EMAILS
     *
     * @return CC_EMAILS, nvarchar, length:2147483647, null:true, pk:false
     */
    public String getCcEmails() {
        return this.ccEmails_;
    }
    /**
    * 赋值 CC_EMAILS

    * @param paraCcEmails
    * CC_EMAILS, nvarchar, length:2147483647, null:true, pk:false
     */

    public void setCcEmails(String paraCcEmails) {
        super.recordChanged("CC_EMAILS", this.ccEmails_, paraCcEmails);
        this.ccEmails_ = paraCcEmails;
    }


    /**
     * 获取 CC_NAMES
     *
     * @return CC_NAMES, nvarchar, length:2147483647, null:true, pk:false
     */
    public String getCcNames() {
        return this.ccNames_;
    }
    /**
    * 赋值 CC_NAMES

    * @param paraCcNames
    * CC_NAMES, nvarchar, length:2147483647, null:true, pk:false
     */

    public void setCcNames(String paraCcNames) {
        super.recordChanged("CC_NAMES", this.ccNames_, paraCcNames);
        this.ccNames_ = paraCcNames;
    }


    /**
     * 获取 BCC_EMAILS
     *
     * @return BCC_EMAILS, nvarchar, length:2147483647, null:true, pk:false
     */
    public String getBccEmails() {
        return this.bccEmails_;
    }
    /**
    * 赋值 BCC_EMAILS

    * @param paraBccEmails
    * BCC_EMAILS, nvarchar, length:2147483647, null:true, pk:false
     */

    public void setBccEmails(String paraBccEmails) {
        super.recordChanged("BCC_EMAILS", this.bccEmails_, paraBccEmails);
        this.bccEmails_ = paraBccEmails;
    }


    /**
     * 获取 BCC_NAMES
     *
     * @return BCC_NAMES, nvarchar, length:2147483647, null:true, pk:false
     */
    public String getBccNames() {
        return this.bccNames_;
    }
    /**
    * 赋值 BCC_NAMES

    * @param paraBccNames
    * BCC_NAMES, nvarchar, length:2147483647, null:true, pk:false
     */

    public void setBccNames(String paraBccNames) {
        super.recordChanged("BCC_NAMES", this.bccNames_, paraBccNames);
        this.bccNames_ = paraBccNames;
    }


    /**
     * 获取 MESSAGE_MD5
     *
     * @return MESSAGE_MD5, varchar, length:32, null:true, pk:false
     */
    public String getMessageMd5() {
        return this.messageMd5_;
    }
    /**
    * 赋值 MESSAGE_MD5

    * @param paraMessageMd5
    * MESSAGE_MD5, varchar, length:32, null:true, pk:false
     */

    public void setMessageMd5(String paraMessageMd5) {
        super.recordChanged("MESSAGE_MD5", this.messageMd5_, paraMessageMd5);
        this.messageMd5_ = paraMessageMd5;
    }
}