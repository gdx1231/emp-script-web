package com.gdxsoft.spider;

public class SpiderLink {

	private String url_;
	private String title_;
	private String delivedDate_;

	/**
	 * 连接地址
	 * 
	 * @return the url_
	 */
	public String getUrl() {
		return url_;
	}

	/**
	 * 连接地址
	 * 
	 * @param url_
	 *            the url_ to set
	 */
	public void setUrl(String url) {
		this.url_ = url;
	}

	/**
	 * 标题
	 * 
	 * @return the title_
	 */
	public String getTitle() {
		return title_;
	}

	/**
	 * 标题
	 * 
	 * @param title_
	 *            the title_ to set
	 */
	public void setTitle(String title) {
		this.title_ = title;
	}

	/**
	 * 发布日期
	 * 
	 * @return the delivedDate_
	 */
	public String getDelivedDate() {
		return delivedDate_;
	}

	/**
	 * 发布日期
	 * 
	 * @param delivedDate_
	 *            the delivedDate_ to set
	 */
	public void setDelivedDate(String delivedDate) {
		this.delivedDate_ = delivedDate;
	}

}
