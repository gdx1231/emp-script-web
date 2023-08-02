package com.gdxsoft.web.dao;

import java.util.*;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表SYS_MESSAGE_INFO_FACEBACK映射类
 * @author gdx 时间：Wed Aug 02 2023 09:10:30 GMT+0800 (中国标准时间)*/
public class SysMessageInfoFaceback extends ClassBase { // 消息编号, int, length:10, null:false, pk:true
    private Integer messageId_;
    // 用户编号, int, length:10, null:false, pk:true
    private Integer fbkAdmId_;
    // 回复时间, datetime, length:23, null:true, pk:false
    private Date fbkTime_;
    // ip, varchar, length:40, null:true, pk:false
    private String fbkIp_;
    // 浏览器代理, varchar, length:2000, null:true, pk:false
    private String fbkAgent_;
    // unid, char, length:36, null:true, pk:false
    private String fbkUnid_;
    // 打开时间, datetime, length:23, null:true, pk:false
    private Date fbkShowTime_;
    // 打开IP, varchar, length:40, null:true, pk:false
    private String fbkShowIp_;
    // 打开代理, varchar, length:2000, null:true, pk:false
    private String fbkShowAgent_;
    // 跟踪的浏览器来源参考, varchar, length:8000, null:true, pk:false
    private String fbkShowReffer_;

    /**
     * 获取 消息编号
     *
     * @return 消息编号, int, length:10, null:false, pk:true
     */
    public Integer getMessageId() {
        return this.messageId_;
    }
    /**
    * 赋值 消息编号

    * @param paraMessageId
    * 消息编号, int, length:10, null:false, pk:true
     */

    public void setMessageId(Integer paraMessageId) {
        super.recordChanged("MESSAGE_ID", this.messageId_, paraMessageId);
        this.messageId_ = paraMessageId;
    }


    /**
     * 获取 用户编号
     *
     * @return 用户编号, int, length:10, null:false, pk:true
     */
    public Integer getFbkAdmId() {
        return this.fbkAdmId_;
    }
    /**
    * 赋值 用户编号

    * @param paraFbkAdmId
    * 用户编号, int, length:10, null:false, pk:true
     */

    public void setFbkAdmId(Integer paraFbkAdmId) {
        super.recordChanged("FBK_ADM_ID", this.fbkAdmId_, paraFbkAdmId);
        this.fbkAdmId_ = paraFbkAdmId;
    }


    /**
     * 获取 回复时间
     *
     * @return 回复时间, datetime, length:23, null:true, pk:false
     */
    public Date getFbkTime() {
        return this.fbkTime_;
    }
    /**
    * 赋值 回复时间

    * @param paraFbkTime
    * 回复时间, datetime, length:23, null:true, pk:false
     */

    public void setFbkTime(Date paraFbkTime) {
        super.recordChanged("FBK_TIME", this.fbkTime_, paraFbkTime);
        this.fbkTime_ = paraFbkTime;
    }


    /**
     * 获取 ip
     *
     * @return ip, varchar, length:40, null:true, pk:false
     */
    public String getFbkIp() {
        return this.fbkIp_;
    }
    /**
    * 赋值 ip

    * @param paraFbkIp
    * ip, varchar, length:40, null:true, pk:false
     */

    public void setFbkIp(String paraFbkIp) {
        super.recordChanged("FBK_IP", this.fbkIp_, paraFbkIp);
        this.fbkIp_ = paraFbkIp;
    }


    /**
     * 获取 浏览器代理
     *
     * @return 浏览器代理, varchar, length:2000, null:true, pk:false
     */
    public String getFbkAgent() {
        return this.fbkAgent_;
    }
    /**
    * 赋值 浏览器代理

    * @param paraFbkAgent
    * 浏览器代理, varchar, length:2000, null:true, pk:false
     */

    public void setFbkAgent(String paraFbkAgent) {
        super.recordChanged("FBK_AGENT", this.fbkAgent_, paraFbkAgent);
        this.fbkAgent_ = paraFbkAgent;
    }


    /**
     * 获取 unid
     *
     * @return unid, char, length:36, null:true, pk:false
     */
    public String getFbkUnid() {
        return this.fbkUnid_;
    }
    /**
    * 赋值 unid

    * @param paraFbkUnid
    * unid, char, length:36, null:true, pk:false
     */

    public void setFbkUnid(String paraFbkUnid) {
        super.recordChanged("FBK_UNID", this.fbkUnid_, paraFbkUnid);
        this.fbkUnid_ = paraFbkUnid;
    }


    /**
     * 获取 打开时间
     *
     * @return 打开时间, datetime, length:23, null:true, pk:false
     */
    public Date getFbkShowTime() {
        return this.fbkShowTime_;
    }
    /**
    * 赋值 打开时间

    * @param paraFbkShowTime
    * 打开时间, datetime, length:23, null:true, pk:false
     */

    public void setFbkShowTime(Date paraFbkShowTime) {
        super.recordChanged("FBK_SHOW_TIME", this.fbkShowTime_, paraFbkShowTime);
        this.fbkShowTime_ = paraFbkShowTime;
    }


    /**
     * 获取 打开IP
     *
     * @return 打开IP, varchar, length:40, null:true, pk:false
     */
    public String getFbkShowIp() {
        return this.fbkShowIp_;
    }
    /**
    * 赋值 打开IP

    * @param paraFbkShowIp
    * 打开IP, varchar, length:40, null:true, pk:false
     */

    public void setFbkShowIp(String paraFbkShowIp) {
        super.recordChanged("FBK_SHOW_IP", this.fbkShowIp_, paraFbkShowIp);
        this.fbkShowIp_ = paraFbkShowIp;
    }


    /**
     * 获取 打开代理
     *
     * @return 打开代理, varchar, length:2000, null:true, pk:false
     */
    public String getFbkShowAgent() {
        return this.fbkShowAgent_;
    }
    /**
    * 赋值 打开代理

    * @param paraFbkShowAgent
    * 打开代理, varchar, length:2000, null:true, pk:false
     */

    public void setFbkShowAgent(String paraFbkShowAgent) {
        super.recordChanged("FBK_SHOW_AGENT", this.fbkShowAgent_, paraFbkShowAgent);
        this.fbkShowAgent_ = paraFbkShowAgent;
    }


    /**
     * 获取 跟踪的浏览器来源参考
     *
     * @return 跟踪的浏览器来源参考, varchar, length:8000, null:true, pk:false
     */
    public String getFbkShowReffer() {
        return this.fbkShowReffer_;
    }
    /**
    * 赋值 跟踪的浏览器来源参考

    * @param paraFbkShowReffer
    * 跟踪的浏览器来源参考, varchar, length:8000, null:true, pk:false
     */

    public void setFbkShowReffer(String paraFbkShowReffer) {
        super.recordChanged("FBK_SHOW_REFFER", this.fbkShowReffer_, paraFbkShowReffer);
        this.fbkShowReffer_ = paraFbkShowReffer;
    }
}