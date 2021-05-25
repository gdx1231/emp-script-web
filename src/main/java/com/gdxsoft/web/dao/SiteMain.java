package com.gdxsoft.web.dao;

import com.gdxsoft.easyweb.datasource.ClassBase;

import java.util.Date;

/**
 * 表SITE_MAIN映射类
 * 
 * @author gdx 时间：Sat Jul 11 2020 20:19:14 GMT+0800 (中国标准时间)
 */
public class SiteMain extends ClassBase {
	private Integer sitId_; // 网站编号
	private String sitName_; // 网站名称
	private String sitNameEn_; // 英文名称
	private String sitMetaDes_; // META描述
	private String sitMetaDesEn_; // META描述英文
	private String sitMetaKeys_; // META关键字
	private String sitMetaKeysEn_; // META关键字英文
	private String sitCw_; // 网站版权说明
	private String sitCwEn_; // 网站版权说明英文
	private String sitMemo_; // 网站备注
	private Integer sitSupId_; // 供应商编号
	private String sitContact_; // 联系人
	private String sitTele_; // 联系电话
	private String sitUrls_; // 网站URLS
	private Date sitCdate_; // 创建日期
	private Date sitMdate_; // 修改日期
	private String sitUnid_; // 全局编号
	private String sitStatus_; // 状态
	private String sitMailOrderName_; // SIT_MAIL_ORDER_NAME
	private String sitMailOrderEmail_; // SIT_MAIL_ORDER_EMAIL
	private String sitMailDefName_; // SIT_MAIL_DEF_NAME
	private String sitMailDefEmail_; // SIT_MAIL_DEF_EMAIL
	private String sitLogo_; // SIT_LOGO
	private String sitIcon32_; // SIT_ICON_32
	private String sitIcon180_; // SIT_ICON_180
	private String sitIcon192_; // SIT_ICON_192
	private String sitIcon270_; // SIT_ICON_270
	private String sitBeian_; // SIT_BEIAN

	/**
	 * 获取 网站编号
	 *
	 * @return 网站编号
	 */
	public Integer getSitId() {
		return this.sitId_;
	}

	/**
	 * 赋值 网站编号
	 * 
	 * @param paraSitId 网站编号
	 */

	public void setSitId(Integer paraSitId) {
		super.recordChanged("SIT_ID", this.sitId_, paraSitId);
		this.sitId_ = paraSitId;
	}

	/**
	 * 获取 网站名称
	 *
	 * @return 网站名称
	 */
	public String getSitName() {
		return this.sitName_;
	}

	/**
	 * 赋值 网站名称
	 * 
	 * @param paraSitName 网站名称
	 */

	public void setSitName(String paraSitName) {
		super.recordChanged("SIT_NAME", this.sitName_, paraSitName);
		this.sitName_ = paraSitName;
	}

	/**
	 * 获取 英文名称
	 *
	 * @return 英文名称
	 */
	public String getSitNameEn() {
		return this.sitNameEn_;
	}

	/**
	 * 赋值 英文名称
	 * 
	 * @param paraSitNameEn 英文名称
	 */

	public void setSitNameEn(String paraSitNameEn) {
		super.recordChanged("SIT_NAME_EN", this.sitNameEn_, paraSitNameEn);
		this.sitNameEn_ = paraSitNameEn;
	}

	/**
	 * 获取 META描述
	 *
	 * @return META描述
	 */
	public String getSitMetaDes() {
		return this.sitMetaDes_;
	}

	/**
	 * 赋值 META描述
	 * 
	 * @param paraSitMetaDes META描述
	 */

	public void setSitMetaDes(String paraSitMetaDes) {
		super.recordChanged("SIT_META_DES", this.sitMetaDes_, paraSitMetaDes);
		this.sitMetaDes_ = paraSitMetaDes;
	}

	/**
	 * 获取 META描述英文
	 *
	 * @return META描述英文
	 */
	public String getSitMetaDesEn() {
		return this.sitMetaDesEn_;
	}

	/**
	 * 赋值 META描述英文
	 * 
	 * @param paraSitMetaDesEn META描述英文
	 */

	public void setSitMetaDesEn(String paraSitMetaDesEn) {
		super.recordChanged("SIT_META_DES_EN", this.sitMetaDesEn_, paraSitMetaDesEn);
		this.sitMetaDesEn_ = paraSitMetaDesEn;
	}

	/**
	 * 获取 META关键字
	 *
	 * @return META关键字
	 */
	public String getSitMetaKeys() {
		return this.sitMetaKeys_;
	}

	/**
	 * 赋值 META关键字
	 * 
	 * @param paraSitMetaKeys META关键字
	 */

	public void setSitMetaKeys(String paraSitMetaKeys) {
		super.recordChanged("SIT_META_KEYS", this.sitMetaKeys_, paraSitMetaKeys);
		this.sitMetaKeys_ = paraSitMetaKeys;
	}

	/**
	 * 获取 META关键字英文
	 *
	 * @return META关键字英文
	 */
	public String getSitMetaKeysEn() {
		return this.sitMetaKeysEn_;
	}

	/**
	 * 赋值 META关键字英文
	 * 
	 * @param paraSitMetaKeysEn META关键字英文
	 */

	public void setSitMetaKeysEn(String paraSitMetaKeysEn) {
		super.recordChanged("SIT_META_KEYS_EN", this.sitMetaKeysEn_, paraSitMetaKeysEn);
		this.sitMetaKeysEn_ = paraSitMetaKeysEn;
	}

	/**
	 * 获取 网站版权说明
	 *
	 * @return 网站版权说明
	 */
	public String getSitCw() {
		return this.sitCw_;
	}

	/**
	 * 赋值 网站版权说明
	 * 
	 * @param paraSitCw 网站版权说明
	 */

	public void setSitCw(String paraSitCw) {
		super.recordChanged("SIT_CW", this.sitCw_, paraSitCw);
		this.sitCw_ = paraSitCw;
	}

	/**
	 * 获取 网站版权说明英文
	 *
	 * @return 网站版权说明英文
	 */
	public String getSitCwEn() {
		return this.sitCwEn_;
	}

	/**
	 * 赋值 网站版权说明英文
	 * 
	 * @param paraSitCwEn 网站版权说明英文
	 */

	public void setSitCwEn(String paraSitCwEn) {
		super.recordChanged("SIT_CW_EN", this.sitCwEn_, paraSitCwEn);
		this.sitCwEn_ = paraSitCwEn;
	}

	/**
	 * 获取 网站备注
	 *
	 * @return 网站备注
	 */
	public String getSitMemo() {
		return this.sitMemo_;
	}

	/**
	 * 赋值 网站备注
	 * 
	 * @param paraSitMemo 网站备注
	 */

	public void setSitMemo(String paraSitMemo) {
		super.recordChanged("SIT_MEMO", this.sitMemo_, paraSitMemo);
		this.sitMemo_ = paraSitMemo;
	}

	/**
	 * 获取 供应商编号
	 *
	 * @return 供应商编号
	 */
	public Integer getSitSupId() {
		return this.sitSupId_;
	}

	/**
	 * 赋值 供应商编号
	 * 
	 * @param paraSitSupId 供应商编号
	 */

	public void setSitSupId(Integer paraSitSupId) {
		super.recordChanged("SIT_SUP_ID", this.sitSupId_, paraSitSupId);
		this.sitSupId_ = paraSitSupId;
	}

	/**
	 * 获取 联系人
	 *
	 * @return 联系人
	 */
	public String getSitContact() {
		return this.sitContact_;
	}

	/**
	 * 赋值 联系人
	 * 
	 * @param paraSitContact 联系人
	 */

	public void setSitContact(String paraSitContact) {
		super.recordChanged("SIT_CONTACT", this.sitContact_, paraSitContact);
		this.sitContact_ = paraSitContact;
	}

	/**
	 * 获取 联系电话
	 *
	 * @return 联系电话
	 */
	public String getSitTele() {
		return this.sitTele_;
	}

	/**
	 * 赋值 联系电话
	 * 
	 * @param paraSitTele 联系电话
	 */

	public void setSitTele(String paraSitTele) {
		super.recordChanged("SIT_TELE", this.sitTele_, paraSitTele);
		this.sitTele_ = paraSitTele;
	}

	/**
	 * 获取 网站URLS
	 *
	 * @return 网站URLS
	 */
	public String getSitUrls() {
		return this.sitUrls_;
	}

	/**
	 * 赋值 网站URLS
	 * 
	 * @param paraSitUrls 网站URLS
	 */

	public void setSitUrls(String paraSitUrls) {
		super.recordChanged("SIT_URLS", this.sitUrls_, paraSitUrls);
		this.sitUrls_ = paraSitUrls;
	}

	/**
	 * 获取 创建日期
	 *
	 * @return 创建日期
	 */
	public Date getSitCdate() {
		return this.sitCdate_;
	}

	/**
	 * 赋值 创建日期
	 * 
	 * @param paraSitCdate 创建日期
	 */

	public void setSitCdate(Date paraSitCdate) {
		super.recordChanged("SIT_CDATE", this.sitCdate_, paraSitCdate);
		this.sitCdate_ = paraSitCdate;
	}

	/**
	 * 获取 修改日期
	 *
	 * @return 修改日期
	 */
	public Date getSitMdate() {
		return this.sitMdate_;
	}

	/**
	 * 赋值 修改日期
	 * 
	 * @param paraSitMdate 修改日期
	 */

	public void setSitMdate(Date paraSitMdate) {
		super.recordChanged("SIT_MDATE", this.sitMdate_, paraSitMdate);
		this.sitMdate_ = paraSitMdate;
	}

	/**
	 * 获取 全局编号
	 *
	 * @return 全局编号
	 */
	public String getSitUnid() {
		return this.sitUnid_;
	}

	/**
	 * 赋值 全局编号
	 * 
	 * @param paraSitUnid 全局编号
	 */

	public void setSitUnid(String paraSitUnid) {
		super.recordChanged("SIT_UNID", this.sitUnid_, paraSitUnid);
		this.sitUnid_ = paraSitUnid;
	}

	/**
	 * 获取 状态
	 *
	 * @return 状态
	 */
	public String getSitStatus() {
		return this.sitStatus_;
	}

	/**
	 * 赋值 状态
	 * 
	 * @param paraSitStatus 状态
	 */

	public void setSitStatus(String paraSitStatus) {
		super.recordChanged("SIT_STATUS", this.sitStatus_, paraSitStatus);
		this.sitStatus_ = paraSitStatus;
	}

	/**
	 * 获取 SIT_MAIL_ORDER_NAME
	 *
	 * @return SIT_MAIL_ORDER_NAME
	 */
	public String getSitMailOrderName() {
		return this.sitMailOrderName_;
	}

	/**
	 * 赋值 SIT_MAIL_ORDER_NAME
	 * 
	 * @param paraSitMailOrderName SIT_MAIL_ORDER_NAME
	 */

	public void setSitMailOrderName(String paraSitMailOrderName) {
		super.recordChanged("SIT_MAIL_ORDER_NAME", this.sitMailOrderName_, paraSitMailOrderName);
		this.sitMailOrderName_ = paraSitMailOrderName;
	}

	/**
	 * 获取 SIT_MAIL_ORDER_EMAIL
	 *
	 * @return SIT_MAIL_ORDER_EMAIL
	 */
	public String getSitMailOrderEmail() {
		return this.sitMailOrderEmail_;
	}

	/**
	 * 赋值 SIT_MAIL_ORDER_EMAIL
	 * 
	 * @param paraSitMailOrderEmail SIT_MAIL_ORDER_EMAIL
	 */

	public void setSitMailOrderEmail(String paraSitMailOrderEmail) {
		super.recordChanged("SIT_MAIL_ORDER_EMAIL", this.sitMailOrderEmail_, paraSitMailOrderEmail);
		this.sitMailOrderEmail_ = paraSitMailOrderEmail;
	}

	/**
	 * 获取 SIT_MAIL_DEF_NAME
	 *
	 * @return SIT_MAIL_DEF_NAME
	 */
	public String getSitMailDefName() {
		return this.sitMailDefName_;
	}

	/**
	 * 赋值 SIT_MAIL_DEF_NAME
	 * 
	 * @param paraSitMailDefName SIT_MAIL_DEF_NAME
	 */

	public void setSitMailDefName(String paraSitMailDefName) {
		super.recordChanged("SIT_MAIL_DEF_NAME", this.sitMailDefName_, paraSitMailDefName);
		this.sitMailDefName_ = paraSitMailDefName;
	}

	/**
	 * 获取 SIT_MAIL_DEF_EMAIL
	 *
	 * @return SIT_MAIL_DEF_EMAIL
	 */
	public String getSitMailDefEmail() {
		return this.sitMailDefEmail_;
	}

	/**
	 * 赋值 SIT_MAIL_DEF_EMAIL
	 * 
	 * @param paraSitMailDefEmail SIT_MAIL_DEF_EMAIL
	 */

	public void setSitMailDefEmail(String paraSitMailDefEmail) {
		super.recordChanged("SIT_MAIL_DEF_EMAIL", this.sitMailDefEmail_, paraSitMailDefEmail);
		this.sitMailDefEmail_ = paraSitMailDefEmail;
	}

	/**
	 * 获取 SIT_LOGO
	 *
	 * @return SIT_LOGO
	 */
	public String getSitLogo() {
		return this.sitLogo_;
	}

	/**
	 * 赋值 SIT_LOGO
	 * 
	 * @param paraSitLogo SIT_LOGO
	 */

	public void setSitLogo(String paraSitLogo) {
		super.recordChanged("SIT_LOGO", this.sitLogo_, paraSitLogo);
		this.sitLogo_ = paraSitLogo;
	}

	/**
	 * 获取 SIT_ICON_32
	 *
	 * @return SIT_ICON_32
	 */
	public String getSitIcon32() {
		return this.sitIcon32_;
	}

	/**
	 * 赋值 SIT_ICON_32
	 * 
	 * @param paraSitIcon32 SIT_ICON_32
	 */

	public void setSitIcon32(String paraSitIcon32) {
		super.recordChanged("SIT_ICON_32", this.sitIcon32_, paraSitIcon32);
		this.sitIcon32_ = paraSitIcon32;
	}

	/**
	 * 获取 SIT_ICON_180
	 *
	 * @return SIT_ICON_180
	 */
	public String getSitIcon180() {
		return this.sitIcon180_;
	}

	/**
	 * 赋值 SIT_ICON_180
	 * 
	 * @param paraSitIcon180 SIT_ICON_180
	 */

	public void setSitIcon180(String paraSitIcon180) {
		super.recordChanged("SIT_ICON_180", this.sitIcon180_, paraSitIcon180);
		this.sitIcon180_ = paraSitIcon180;
	}

	/**
	 * 获取 SIT_ICON_192
	 *
	 * @return SIT_ICON_192
	 */
	public String getSitIcon192() {
		return this.sitIcon192_;
	}

	/**
	 * 赋值 SIT_ICON_192
	 * 
	 * @param paraSitIcon192 SIT_ICON_192
	 */

	public void setSitIcon192(String paraSitIcon192) {
		super.recordChanged("SIT_ICON_192", this.sitIcon192_, paraSitIcon192);
		this.sitIcon192_ = paraSitIcon192;
	}

	/**
	 * 获取 SIT_ICON_270
	 *
	 * @return SIT_ICON_270
	 */
	public String getSitIcon270() {
		return this.sitIcon270_;
	}

	/**
	 * 赋值 SIT_ICON_270
	 * 
	 * @param paraSitIcon270 SIT_ICON_270
	 */

	public void setSitIcon270(String paraSitIcon270) {
		super.recordChanged("SIT_ICON_270", this.sitIcon270_, paraSitIcon270);
		this.sitIcon270_ = paraSitIcon270;
	}

	/**
	 * 获取 SIT_BEIAN
	 *
	 * @return SIT_BEIAN
	 */
	public String getSitBeian() {
		return this.sitBeian_;
	}

	/**
	 * 赋值 SIT_BEIAN
	 * 
	 * @param paraSitBeian SIT_BEIAN
	 */

	public void setSitBeian(String paraSitBeian) {
		super.recordChanged("SIT_BEIAN", this.sitBeian_, paraSitBeian);
		this.sitBeian_ = paraSitBeian;
	}
}