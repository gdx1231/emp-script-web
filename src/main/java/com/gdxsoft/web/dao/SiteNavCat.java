package com.gdxsoft.web.dao;

import com.gdxsoft.easyweb.datasource.ClassBase;

import java.util.ArrayList;
import java.util.Date;

/**
 * 表site_nav_cat映射类
 * 
 * @author gdx 时间：Sat Jul 11 2020 20:24:46 GMT+0800 (中国标准时间)
 */
public class SiteNavCat extends ClassBase {
	private Integer sitNavId_; // SIT_NAV_ID
	private String sitNavName_; // 名称
	private String sitNavNameEn_; // 英文名称
	private Integer sitNavPid_; // 父键
	private Integer sitNavOrd_; // 排序
	private Integer sitNavLvl_; // 级别
	private String sitNavMemo_; // 备注
	private String sitNavUrl_; // URL
	private String sitNavStatus_; // 状态
	private String sitNavType_; // 类型
	private Date sitNavCdate_; // 创建时间
	private Date sitNavMdate_; // 修改时间
	private Integer supId_; // SUP_ID
	private Integer admId_; // ADM_ID
	private Integer sitId_; // 所属网站
	private String sitNavAttr_; // 属性
	private String sitNavUrlEn_; // URL-英文
	private String sitNavTag_; // SIT_NAV_TAG
	private String sitNavKeywords_; // SIT_NAV_KEYWORDS
	private String sitNavDesc_; // SIT_NAV_DESC
	private String sitNavTitle_; // SIT_NAV_TITLE
	private String sitNavKeywordsEn_; // SIT_NAV_KEYWORDS_EN
	private String sitNavDescEn_; // SIT_NAV_DESC_EN
	private String sitNavTitleEn_; // SIT_NAV_TITLE_EN
	private String headerGid_; // HEADER_GID
	private String headerGidEn_; // HEADER_GID_EN
	private String sitNavChilden_; // SIT_NAV_CHILDEN
	private String sitNavCss_; // SIT_NAV_CSS
	private String sitNavHot_; // SIT_NAV_HOT

	private ArrayList<SiteNavCat> _Children;
	private String _Breadcrumb;
	private String _BreadcrumbEn;
	private String _BreadcrumbCur;
	private String _BreadcrumbCurEn;

	/**
	 * 获取子节点
	 * 
	 * @return
	 */
	public ArrayList<SiteNavCat> getChildren() {
		if (_Children == null) {
			_Children = new ArrayList<SiteNavCat>();
		}
		return _Children;
	}

	public String getBreadcrumb() {
		return _Breadcrumb;
	}

	public void setBreadcrumb(String breadcrumb) {
		_Breadcrumb = breadcrumb;
	}

	public String getBreadcrumbEn() {
		return _BreadcrumbEn;
	}

	public void setBreadcrumbEn(String breadcrumbEn) {
		_BreadcrumbEn = breadcrumbEn;
	}

	public String getBreadcrumbCur() {
		return _BreadcrumbCur;
	}

	public void setBreadcrumbCur(String breadcrumbCur) {
		_BreadcrumbCur = breadcrumbCur;
	}

	public String getBreadcrumbCurEn() {
		return _BreadcrumbCurEn;
	}

	public void setBreadcrumbCurEn(String breadcrumbCurEn) {
		_BreadcrumbCurEn = breadcrumbCurEn;
	}

	/**
	 * 获取 SIT_NAV_ID
	 *
	 * @return SIT_NAV_ID
	 */
	public Integer getSitNavId() {
		return this.sitNavId_;
	}

	/**
	 * 赋值 SIT_NAV_ID
	 * 
	 * @param paraSitNavId SIT_NAV_ID
	 */

	public void setSitNavId(Integer paraSitNavId) {
		super.recordChanged("SIT_NAV_ID", this.sitNavId_, paraSitNavId);
		this.sitNavId_ = paraSitNavId;
	}

	/**
	 * 获取 名称
	 *
	 * @return 名称
	 */
	public String getSitNavName() {
		return this.sitNavName_;
	}

	/**
	 * 赋值 名称
	 * 
	 * @param paraSitNavName 名称
	 */

	public void setSitNavName(String paraSitNavName) {
		super.recordChanged("SIT_NAV_NAME", this.sitNavName_, paraSitNavName);
		this.sitNavName_ = paraSitNavName;
	}

	/**
	 * 获取 英文名称
	 *
	 * @return 英文名称
	 */
	public String getSitNavNameEn() {
		return this.sitNavNameEn_;
	}

	/**
	 * 赋值 英文名称
	 * 
	 * @param paraSitNavNameEn 英文名称
	 */

	public void setSitNavNameEn(String paraSitNavNameEn) {
		super.recordChanged("SIT_NAV_NAME_EN", this.sitNavNameEn_, paraSitNavNameEn);
		this.sitNavNameEn_ = paraSitNavNameEn;
	}

	/**
	 * 获取 父键
	 *
	 * @return 父键
	 */
	public Integer getSitNavPid() {
		return this.sitNavPid_;
	}

	/**
	 * 赋值 父键
	 * 
	 * @param paraSitNavPid 父键
	 */

	public void setSitNavPid(Integer paraSitNavPid) {
		super.recordChanged("SIT_NAV_PID", this.sitNavPid_, paraSitNavPid);
		this.sitNavPid_ = paraSitNavPid;
	}

	/**
	 * 获取 排序
	 *
	 * @return 排序
	 */
	public Integer getSitNavOrd() {
		return this.sitNavOrd_;
	}

	/**
	 * 赋值 排序
	 * 
	 * @param paraSitNavOrd 排序
	 */

	public void setSitNavOrd(Integer paraSitNavOrd) {
		super.recordChanged("SIT_NAV_ORD", this.sitNavOrd_, paraSitNavOrd);
		this.sitNavOrd_ = paraSitNavOrd;
	}

	/**
	 * 获取 级别
	 *
	 * @return 级别
	 */
	public Integer getSitNavLvl() {
		return this.sitNavLvl_;
	}

	/**
	 * 赋值 级别
	 * 
	 * @param paraSitNavLvl 级别
	 */

	public void setSitNavLvl(Integer paraSitNavLvl) {
		super.recordChanged("SIT_NAV_LVL", this.sitNavLvl_, paraSitNavLvl);
		this.sitNavLvl_ = paraSitNavLvl;
	}

	/**
	 * 获取 备注
	 *
	 * @return 备注
	 */
	public String getSitNavMemo() {
		return this.sitNavMemo_;
	}

	/**
	 * 赋值 备注
	 * 
	 * @param paraSitNavMemo 备注
	 */

	public void setSitNavMemo(String paraSitNavMemo) {
		super.recordChanged("SIT_NAV_MEMO", this.sitNavMemo_, paraSitNavMemo);
		this.sitNavMemo_ = paraSitNavMemo;
	}

	/**
	 * 获取 URL
	 *
	 * @return URL
	 */
	public String getSitNavUrl() {
		return this.sitNavUrl_;
	}

	/**
	 * 赋值 URL
	 * 
	 * @param paraSitNavUrl URL
	 */

	public void setSitNavUrl(String paraSitNavUrl) {
		super.recordChanged("SIT_NAV_URL", this.sitNavUrl_, paraSitNavUrl);
		this.sitNavUrl_ = paraSitNavUrl;
	}

	/**
	 * 获取 状态
	 *
	 * @return 状态
	 */
	public String getSitNavStatus() {
		return this.sitNavStatus_;
	}

	/**
	 * 赋值 状态
	 * 
	 * @param paraSitNavStatus 状态
	 */

	public void setSitNavStatus(String paraSitNavStatus) {
		super.recordChanged("SIT_NAV_STATUS", this.sitNavStatus_, paraSitNavStatus);
		this.sitNavStatus_ = paraSitNavStatus;
	}

	/**
	 * 获取 类型
	 *
	 * @return 类型
	 */
	public String getSitNavType() {
		return this.sitNavType_;
	}

	/**
	 * 赋值 类型
	 * 
	 * @param paraSitNavType 类型
	 */

	public void setSitNavType(String paraSitNavType) {
		super.recordChanged("SIT_NAV_TYPE", this.sitNavType_, paraSitNavType);
		this.sitNavType_ = paraSitNavType;
	}

	/**
	 * 获取 创建时间
	 *
	 * @return 创建时间
	 */
	public Date getSitNavCdate() {
		return this.sitNavCdate_;
	}

	/**
	 * 赋值 创建时间
	 * 
	 * @param paraSitNavCdate 创建时间
	 */

	public void setSitNavCdate(Date paraSitNavCdate) {
		super.recordChanged("SIT_NAV_CDATE", this.sitNavCdate_, paraSitNavCdate);
		this.sitNavCdate_ = paraSitNavCdate;
	}

	/**
	 * 获取 修改时间
	 *
	 * @return 修改时间
	 */
	public Date getSitNavMdate() {
		return this.sitNavMdate_;
	}

	/**
	 * 赋值 修改时间
	 * 
	 * @param paraSitNavMdate 修改时间
	 */

	public void setSitNavMdate(Date paraSitNavMdate) {
		super.recordChanged("SIT_NAV_MDATE", this.sitNavMdate_, paraSitNavMdate);
		this.sitNavMdate_ = paraSitNavMdate;
	}

	/**
	 * 获取 SUP_ID
	 *
	 * @return SUP_ID
	 */
	public Integer getSupId() {
		return this.supId_;
	}

	/**
	 * 赋值 SUP_ID
	 * 
	 * @param paraSupId SUP_ID
	 */

	public void setSupId(Integer paraSupId) {
		super.recordChanged("SUP_ID", this.supId_, paraSupId);
		this.supId_ = paraSupId;
	}

	/**
	 * 获取 ADM_ID
	 *
	 * @return ADM_ID
	 */
	public Integer getAdmId() {
		return this.admId_;
	}

	/**
	 * 赋值 ADM_ID
	 * 
	 * @param paraAdmId ADM_ID
	 */

	public void setAdmId(Integer paraAdmId) {
		super.recordChanged("ADM_ID", this.admId_, paraAdmId);
		this.admId_ = paraAdmId;
	}

	/**
	 * 获取 所属网站
	 *
	 * @return 所属网站
	 */
	public Integer getSitId() {
		return this.sitId_;
	}

	/**
	 * 赋值 所属网站
	 * 
	 * @param paraSitId 所属网站
	 */

	public void setSitId(Integer paraSitId) {
		super.recordChanged("SIT_ID", this.sitId_, paraSitId);
		this.sitId_ = paraSitId;
	}

	/**
	 * 获取 属性
	 *
	 * @return 属性
	 */
	public String getSitNavAttr() {
		return this.sitNavAttr_;
	}

	/**
	 * 赋值 属性
	 * 
	 * @param paraSitNavAttr 属性
	 */

	public void setSitNavAttr(String paraSitNavAttr) {
		super.recordChanged("SIT_NAV_ATTR", this.sitNavAttr_, paraSitNavAttr);
		this.sitNavAttr_ = paraSitNavAttr;
	}

	/**
	 * 获取 URL-英文
	 *
	 * @return URL-英文
	 */
	public String getSitNavUrlEn() {
		return this.sitNavUrlEn_;
	}

	/**
	 * 赋值 URL-英文
	 * 
	 * @param paraSitNavUrlEn URL-英文
	 */

	public void setSitNavUrlEn(String paraSitNavUrlEn) {
		super.recordChanged("SIT_NAV_URL_EN", this.sitNavUrlEn_, paraSitNavUrlEn);
		this.sitNavUrlEn_ = paraSitNavUrlEn;
	}

	/**
	 * 获取 SIT_NAV_TAG
	 *
	 * @return SIT_NAV_TAG
	 */
	public String getSitNavTag() {
		return this.sitNavTag_;
	}

	/**
	 * 赋值 SIT_NAV_TAG
	 * 
	 * @param paraSitNavTag SIT_NAV_TAG
	 */

	public void setSitNavTag(String paraSitNavTag) {
		super.recordChanged("SIT_NAV_TAG", this.sitNavTag_, paraSitNavTag);
		this.sitNavTag_ = paraSitNavTag;
	}

	/**
	 * 获取 SIT_NAV_KEYWORDS
	 *
	 * @return SIT_NAV_KEYWORDS
	 */
	public String getSitNavKeywords() {
		return this.sitNavKeywords_;
	}

	/**
	 * 赋值 SIT_NAV_KEYWORDS
	 * 
	 * @param paraSitNavKeywords SIT_NAV_KEYWORDS
	 */

	public void setSitNavKeywords(String paraSitNavKeywords) {
		super.recordChanged("SIT_NAV_KEYWORDS", this.sitNavKeywords_, paraSitNavKeywords);
		this.sitNavKeywords_ = paraSitNavKeywords;
	}

	/**
	 * 获取 SIT_NAV_DESC
	 *
	 * @return SIT_NAV_DESC
	 */
	public String getSitNavDesc() {
		return this.sitNavDesc_;
	}

	/**
	 * 赋值 SIT_NAV_DESC
	 * 
	 * @param paraSitNavDesc SIT_NAV_DESC
	 */

	public void setSitNavDesc(String paraSitNavDesc) {
		super.recordChanged("SIT_NAV_DESC", this.sitNavDesc_, paraSitNavDesc);
		this.sitNavDesc_ = paraSitNavDesc;
	}

	/**
	 * 获取 SIT_NAV_TITLE
	 *
	 * @return SIT_NAV_TITLE
	 */
	public String getSitNavTitle() {
		return this.sitNavTitle_;
	}

	/**
	 * 赋值 SIT_NAV_TITLE
	 * 
	 * @param paraSitNavTitle SIT_NAV_TITLE
	 */

	public void setSitNavTitle(String paraSitNavTitle) {
		super.recordChanged("SIT_NAV_TITLE", this.sitNavTitle_, paraSitNavTitle);
		this.sitNavTitle_ = paraSitNavTitle;
	}

	/**
	 * 获取 SIT_NAV_KEYWORDS_EN
	 *
	 * @return SIT_NAV_KEYWORDS_EN
	 */
	public String getSitNavKeywordsEn() {
		return this.sitNavKeywordsEn_;
	}

	/**
	 * 赋值 SIT_NAV_KEYWORDS_EN
	 * 
	 * @param paraSitNavKeywordsEn SIT_NAV_KEYWORDS_EN
	 */

	public void setSitNavKeywordsEn(String paraSitNavKeywordsEn) {
		super.recordChanged("SIT_NAV_KEYWORDS_EN", this.sitNavKeywordsEn_, paraSitNavKeywordsEn);
		this.sitNavKeywordsEn_ = paraSitNavKeywordsEn;
	}

	/**
	 * 获取 SIT_NAV_DESC_EN
	 *
	 * @return SIT_NAV_DESC_EN
	 */
	public String getSitNavDescEn() {
		return this.sitNavDescEn_;
	}

	/**
	 * 赋值 SIT_NAV_DESC_EN
	 * 
	 * @param paraSitNavDescEn SIT_NAV_DESC_EN
	 */

	public void setSitNavDescEn(String paraSitNavDescEn) {
		super.recordChanged("SIT_NAV_DESC_EN", this.sitNavDescEn_, paraSitNavDescEn);
		this.sitNavDescEn_ = paraSitNavDescEn;
	}

	/**
	 * 获取 SIT_NAV_TITLE_EN
	 *
	 * @return SIT_NAV_TITLE_EN
	 */
	public String getSitNavTitleEn() {
		return this.sitNavTitleEn_;
	}

	/**
	 * 赋值 SIT_NAV_TITLE_EN
	 * 
	 * @param paraSitNavTitleEn SIT_NAV_TITLE_EN
	 */

	public void setSitNavTitleEn(String paraSitNavTitleEn) {
		super.recordChanged("SIT_NAV_TITLE_EN", this.sitNavTitleEn_, paraSitNavTitleEn);
		this.sitNavTitleEn_ = paraSitNavTitleEn;
	}

	/**
	 * 获取 HEADER_GID
	 *
	 * @return HEADER_GID
	 */
	public String getHeaderGid() {
		return this.headerGid_;
	}

	/**
	 * 赋值 HEADER_GID
	 * 
	 * @param paraHeaderGid HEADER_GID
	 */

	public void setHeaderGid(String paraHeaderGid) {
		super.recordChanged("HEADER_GID", this.headerGid_, paraHeaderGid);
		this.headerGid_ = paraHeaderGid;
	}

	/**
	 * 获取 HEADER_GID_EN
	 *
	 * @return HEADER_GID_EN
	 */
	public String getHeaderGidEn() {
		return this.headerGidEn_;
	}

	/**
	 * 赋值 HEADER_GID_EN
	 * 
	 * @param paraHeaderGidEn HEADER_GID_EN
	 */

	public void setHeaderGidEn(String paraHeaderGidEn) {
		super.recordChanged("HEADER_GID_EN", this.headerGidEn_, paraHeaderGidEn);
		this.headerGidEn_ = paraHeaderGidEn;
	}

	/**
	 * 获取 SIT_NAV_CHILDEN
	 *
	 * @return SIT_NAV_CHILDEN
	 */
	public String getSitNavChilden() {
		return this.sitNavChilden_;
	}

	/**
	 * 赋值 SIT_NAV_CHILDEN
	 * 
	 * @param paraSitNavChilden SIT_NAV_CHILDEN
	 */

	public void setSitNavChilden(String paraSitNavChilden) {
		super.recordChanged("SIT_NAV_CHILDEN", this.sitNavChilden_, paraSitNavChilden);
		this.sitNavChilden_ = paraSitNavChilden;
	}

	/**
	 * 获取 SIT_NAV_CSS
	 *
	 * @return SIT_NAV_CSS
	 */
	public String getSitNavCss() {
		return this.sitNavCss_;
	}

	/**
	 * 赋值 SIT_NAV_CSS
	 * 
	 * @param paraSitNavCss SIT_NAV_CSS
	 */

	public void setSitNavCss(String paraSitNavCss) {
		super.recordChanged("SIT_NAV_CSS", this.sitNavCss_, paraSitNavCss);
		this.sitNavCss_ = paraSitNavCss;
	}

	/**
	 * 获取 SIT_NAV_HOT
	 *
	 * @return SIT_NAV_HOT
	 */
	public String getSitNavHot() {
		return this.sitNavHot_;
	}

	/**
	 * 赋值 SIT_NAV_HOT
	 * 
	 * @param paraSitNavHot SIT_NAV_HOT
	 */

	public void setSitNavHot(String paraSitNavHot) {
		super.recordChanged("SIT_NAV_HOT", this.sitNavHot_, paraSitNavHot);
		this.sitNavHot_ = paraSitNavHot;
	}
}