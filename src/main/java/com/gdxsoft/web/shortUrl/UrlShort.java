package com.gdxsoft.web.shortUrl;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;

/**
 * 表url_short映射类
 * 
 * @author gdx 时间：Tue Dec 07 2021 13:42:09 GMT+0800 (中国标准时间)
 */
public class UrlShort extends ClassBase {
	private Long urlId_; // url_id
	private String urlUid_; // url_uid
	private String urlFull_; // url_full
	private String urlMd5_; // url_full md5
	private String urlStatus_; // url_status
	private Date urlCdate_; // url_cdate
	private Date urlVdate_; // url_vdate
	private Integer supId_; // sup_id
	private Integer admId_; // adm_id

	/**
	 * 获取 url_id
	 *
	 * @return url_id
	 */
	public Long getUrlId() {
		return this.urlId_;
	}

	/**
	 * 赋值 url_id
	 * 
	 * @param paraUrlId url_id
	 */

	public void setUrlId(Long paraUrlId) {
		super.recordChanged("url_id", this.urlId_, paraUrlId);
		this.urlId_ = paraUrlId;
	}

	/**
	 * 获取 url_uid
	 *
	 * @return url_uid
	 */
	public String getUrlUid() {
		return this.urlUid_;
	}

	/**
	 * 赋值 url_uid
	 * 
	 * @param paraUrlUid url_uid
	 */

	public void setUrlUid(String paraUrlUid) {
		super.recordChanged("url_uid", this.urlUid_, paraUrlUid);
		this.urlUid_ = paraUrlUid;
	}

	/**
	 * 获取 url_full
	 *
	 * @return url_full
	 */
	public String getUrlFull() {
		return this.urlFull_;
	}

	/**
	 * 赋值 url_full
	 * 
	 * @param paraUrlFull url_full
	 */

	public void setUrlFull(String paraUrlFull) {
		super.recordChanged("url_full", this.urlFull_, paraUrlFull);
		this.urlFull_ = paraUrlFull;
	}

	/**
	 * 获取 url_full md5
	 *
	 * @return url_full md5
	 */
	public String getUrlMd5() {
		return this.urlMd5_;
	}

	/**
	 * 赋值 url_full md5
	 * 
	 * @param paraUrlMd5 url_full md5
	 */

	public void setUrlMd5(String paraUrlMd5) {
		super.recordChanged("url_md5", this.urlMd5_, paraUrlMd5);
		this.urlMd5_ = paraUrlMd5;
	}

	/**
	 * 获取 url_status
	 *
	 * @return url_status
	 */
	public String getUrlStatus() {
		return this.urlStatus_;
	}

	/**
	 * 赋值 url_status
	 * 
	 * @param paraUrlStatus url_status
	 */

	public void setUrlStatus(String paraUrlStatus) {
		super.recordChanged("url_status", this.urlStatus_, paraUrlStatus);
		this.urlStatus_ = paraUrlStatus;
	}

	/**
	 * 获取 url_cdate
	 *
	 * @return url_cdate
	 */
	public Date getUrlCdate() {
		return this.urlCdate_;
	}

	/**
	 * 赋值 url_cdate
	 * 
	 * @param paraUrlCdate url_cdate
	 */

	public void setUrlCdate(Date paraUrlCdate) {
		super.recordChanged("url_cdate", this.urlCdate_, paraUrlCdate);
		this.urlCdate_ = paraUrlCdate;
	}

	/**
	 * 获取 url_vdate
	 *
	 * @return url_vdate
	 */
	public Date getUrlVdate() {
		return this.urlVdate_;
	}

	/**
	 * 赋值 url_vdate
	 * 
	 * @param paraUrlVdate url_vdate
	 */

	public void setUrlVdate(Date paraUrlVdate) {
		super.recordChanged("url_vdate", this.urlVdate_, paraUrlVdate);
		this.urlVdate_ = paraUrlVdate;
	}

	/**
	 * 获取 sup_id
	 *
	 * @return sup_id
	 */
	public Integer getSupId() {
		return this.supId_;
	}

	/**
	 * 赋值 sup_id
	 * 
	 * @param paraSupId sup_id
	 */

	public void setSupId(Integer paraSupId) {
		super.recordChanged("sup_id", this.supId_, paraSupId);
		this.supId_ = paraSupId;
	}

	/**
	 * 获取 adm_id
	 *
	 * @return adm_id
	 */
	public Integer getAdmId() {
		return this.admId_;
	}

	/**
	 * 赋值 adm_id
	 * 
	 * @param paraAdmId adm_id
	 */

	public void setAdmId(Integer paraAdmId) {
		super.recordChanged("adm_id", this.admId_, paraAdmId);
		this.admId_ = paraAdmId;
	}
}