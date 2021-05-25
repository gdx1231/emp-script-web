package com.gdxsoft.web.dao;

import com.gdxsoft.easyweb.datasource.ClassBase;

/**
 * 表nws_cat映射类
 * 
 * @author gdx 时间：Mon May 10 2021 10:29:02 GMT+0800 (中国标准时间)
 */
public class NwsCat extends ClassBase {
	private Long nwsCatId_; // 目录编号
	private String nwsCatName_; // 目录名称
	private Long nwsCatPid_; // 上级编号
	private Integer nwsCatLvl_; // 级别
	private Integer nwsCatOrd_; // 排序
	private String nwsCatTag_; // 类别
	private String nwsCatMemo_; // 备注
	private Integer sitId_; // SIT_ID
	private Integer supId_; // SUP_ID
	private String nwsCatUnid_; // NWS_CAT_UNID
	private String nwsCatNameEn_; // 目录名称英文

	/**
	 * 获取 目录编号
	 *
	 * @return 目录编号
	 */
	public Long getNwsCatId() {
		return this.nwsCatId_;
	}

	/**
	 * 赋值 目录编号
	 * 
	 * @param paraNwsCatId 目录编号
	 */

	public void setNwsCatId(Long paraNwsCatId) {
		super.recordChanged("NWS_CAT_ID", this.nwsCatId_, paraNwsCatId);
		this.nwsCatId_ = paraNwsCatId;
	}

	/**
	 * 获取 目录名称
	 *
	 * @return 目录名称
	 */
	public String getNwsCatName() {
		return this.nwsCatName_;
	}

	/**
	 * 赋值 目录名称
	 * 
	 * @param paraNwsCatName 目录名称
	 */

	public void setNwsCatName(String paraNwsCatName) {
		super.recordChanged("NWS_CAT_NAME", this.nwsCatName_, paraNwsCatName);
		this.nwsCatName_ = paraNwsCatName;
	}

	/**
	 * 获取 上级编号
	 *
	 * @return 上级编号
	 */
	public Long getNwsCatPid() {
		return this.nwsCatPid_;
	}

	/**
	 * 赋值 上级编号
	 * 
	 * @param paraNwsCatPid 上级编号
	 */

	public void setNwsCatPid(Long paraNwsCatPid) {
		super.recordChanged("NWS_CAT_PID", this.nwsCatPid_, paraNwsCatPid);
		this.nwsCatPid_ = paraNwsCatPid;
	}

	/**
	 * 获取 级别
	 *
	 * @return 级别
	 */
	public Integer getNwsCatLvl() {
		return this.nwsCatLvl_;
	}

	/**
	 * 赋值 级别
	 * 
	 * @param paraNwsCatLvl 级别
	 */

	public void setNwsCatLvl(Integer paraNwsCatLvl) {
		super.recordChanged("NWS_CAT_LVL", this.nwsCatLvl_, paraNwsCatLvl);
		this.nwsCatLvl_ = paraNwsCatLvl;
	}

	/**
	 * 获取 排序
	 *
	 * @return 排序
	 */
	public Integer getNwsCatOrd() {
		return this.nwsCatOrd_;
	}

	/**
	 * 赋值 排序
	 * 
	 * @param paraNwsCatOrd 排序
	 */

	public void setNwsCatOrd(Integer paraNwsCatOrd) {
		super.recordChanged("NWS_CAT_ORD", this.nwsCatOrd_, paraNwsCatOrd);
		this.nwsCatOrd_ = paraNwsCatOrd;
	}

	/**
	 * 获取 类别
	 *
	 * @return 类别
	 */
	public String getNwsCatTag() {
		return this.nwsCatTag_;
	}

	/**
	 * 赋值 类别
	 * 
	 * @param paraNwsCatTag 类别
	 */

	public void setNwsCatTag(String paraNwsCatTag) {
		super.recordChanged("NWS_CAT_TAG", this.nwsCatTag_, paraNwsCatTag);
		this.nwsCatTag_ = paraNwsCatTag;
	}

	/**
	 * 获取 备注
	 *
	 * @return 备注
	 */
	public String getNwsCatMemo() {
		return this.nwsCatMemo_;
	}

	/**
	 * 赋值 备注
	 * 
	 * @param paraNwsCatMemo 备注
	 */

	public void setNwsCatMemo(String paraNwsCatMemo) {
		super.recordChanged("NWS_CAT_MEMO", this.nwsCatMemo_, paraNwsCatMemo);
		this.nwsCatMemo_ = paraNwsCatMemo;
	}

	/**
	 * 获取 SIT_ID
	 *
	 * @return SIT_ID
	 */
	public Integer getSitId() {
		return this.sitId_;
	}

	/**
	 * 赋值 SIT_ID
	 * 
	 * @param paraSitId SIT_ID
	 */

	public void setSitId(Integer paraSitId) {
		super.recordChanged("SIT_ID", this.sitId_, paraSitId);
		this.sitId_ = paraSitId;
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
	 * 获取 NWS_CAT_UNID
	 *
	 * @return NWS_CAT_UNID
	 */
	public String getNwsCatUnid() {
		return this.nwsCatUnid_;
	}

	/**
	 * 赋值 NWS_CAT_UNID
	 * 
	 * @param paraNwsCatUnid NWS_CAT_UNID
	 */

	public void setNwsCatUnid(String paraNwsCatUnid) {
		super.recordChanged("NWS_CAT_UNID", this.nwsCatUnid_, paraNwsCatUnid);
		this.nwsCatUnid_ = paraNwsCatUnid;
	}

	/**
	 * 获取 目录名称英文
	 *
	 * @return 目录名称英文
	 */
	public String getNwsCatNameEn() {
		return this.nwsCatNameEn_;
	}

	/**
	 * 赋值 目录名称英文
	 * 
	 * @param paraNwsCatNameEn 目录名称英文
	 */

	public void setNwsCatNameEn(String paraNwsCatNameEn) {
		super.recordChanged("NWS_CAT_NAME_EN", this.nwsCatNameEn_, paraNwsCatNameEn);
		this.nwsCatNameEn_ = paraNwsCatNameEn;
	}
}