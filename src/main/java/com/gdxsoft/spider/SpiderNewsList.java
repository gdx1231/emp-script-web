package com.gdxsoft.spider;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 读取新闻列表
 * 
 * @author admin
 *
 */
public class SpiderNewsList extends SpiderBase {
	private static Logger LOOGER = Logger.getLogger(SpiderNewsList.class);
	private List<SpiderLink> listLinks_;
	private List<String> listNext_;
	private JSONObject cfgListPart_; // 分解新闻列表的配置
	private JSONObject cfgUrl_; // 扫描新闻列表的json配置，例如 {url: xxx, des: xxx}
	private boolean isError_;
	private String error_;

	public SpiderNewsList(String url, String phyPath, String urlPath) {
		super(url, phyPath, urlPath);
	}

	/**
	 * 扫描并获取新闻列表的链接和日期（如果有的话）
	 * 
	 * @param list_part
	 */
	public void scan(JSONObject list_part) {
		this.cfgListPart_ = list_part;
		Document doc;
		try {
			doc = super.downloadDoc();
		} catch (Exception e) {
			isError_ = true;
			this.error_ = e.getMessage();
			LOOGER.error(e);
			return;
		}
		
		Elements eles;
		try {
			String select = list_part.getString("select"); // jq exp
			eles = doc.select(select);
		} catch (Exception e) {
			isError_ = true;
			this.error_ = e.getMessage();
			LOOGER.error(e);
			return;
		}
		// 需要移除的参数，比如t,r此类的随机参数，避免出现相同的文章的url的hash不一致
		String selectremoverandoms = list_part.optString("selectremoverandoms");
		String[] removeParams = null;
		if (selectremoverandoms != null && selectremoverandoms.trim().length() > 0) {
			removeParams = selectremoverandoms.split(",");
		}

		listLinks_ = new ArrayList<SpiderLink>();
		List<SpiderLink> links = new ArrayList<SpiderLink>();

		for (int i = 0; i < eles.size(); i++) {
			Element ele = eles.get(i);
			String originHref = ele.attr("href");

			if (originHref.toLowerCase().trim().indexOf("javascript:") == 0) {
				continue;
			}

			String href = fixHref(originHref);
			if (removeParams != null) {
				href = super.removeUrlParameters(href, removeParams);
			}
			SpiderLink link = new SpiderLink();
			link.setUrl(href); // url
			link.setTitle(ele.text()); // text

			listLinks_.add(link);
			links.add(link);
		}

		this.scanRelativeDate(links);

		this.sacnNext();
	}

	/**
	 * 检测关联的连接
	 */
	private void scanRelativeDate(List<SpiderLink> links) {
		String selectrq = cfgListPart_.optString("selectrq"); // jq exp

		if (selectrq == null || selectrq.trim().length() == 0) {
			return;
		}
		// char nbsp = 160; // nbsp 空格trim不了
		// StringBuilder sb = new StringBuilder();
		// sb.append(nbsp);
		// String strNbsp = sb.toString();

		Elements eles = super.getDoc().select(selectrq);
		for (int i = 0; i < eles.size(); i++) {
			String s = eles.get(i).text().trim();// .replace(strNbsp, "");
			s = s.replaceAll("[\\s\\u00A0]+$", "");
			links.get(i).setDelivedDate(s);
		}
	}

	/**
	 * 获取后面页码的所有链接
	 * 
	 * @param list_part
	 */
	private void sacnNext() {
		listNext_ = new ArrayList<String>();
		if (!this.cfgListPart_.has("next")) {
			return;
		}
		String nextJq = this.cfgListPart_.getString("next");
		if (nextJq.isEmpty()) {
			return;
		}
		try {
			Elements eles = super.getDoc().select(nextJq);
			for (int i = 0; i < eles.size(); i++) {
				Element ele = eles.get(i);
				String originHref = ele.attr("href");
				String href = fixHref(originHref);

				listNext_.add(href);

			}
		} catch (Exception err) {
			LOOGER.error(err);
		}

	}

	/**
	 * 获取新闻链接列表
	 * 
	 * @return the listContent_
	 */
	public List<SpiderLink> getlistLinks() {
		return listLinks_;
	}

	/**
	 * 获取后面的页面
	 * 
	 * @return the listNext_
	 */
	public List<String> getListNext() {
		return listNext_;
	}

	/**
	 * 检索文章列表的配置
	 * 
	 * @return the cfgListPart_
	 */
	public JSONObject getCfgListPart() {
		return cfgListPart_;
	}

	/**
	 * 扫描新闻列表的json配置，例如 {url: xxx, des: xxx}
	 * 
	 * @return the cfgUrl_
	 */
	public JSONObject getCfgUrl() {
		return cfgUrl_;
	}

	/**
	 * 扫描新闻列表的json配置，例如 {url: xxx, des: xxx}
	 * 
	 * @param cfgUrl_
	 *            the cfgUrl_ to set
	 */
	public void setCfgUrl(JSONObject cfgUrl) {
		this.cfgUrl_ = cfgUrl;
	}

	/**
	 * 是否执行中出现错误
	 * 
	 * @return the isFalse_
	 */
	public boolean isError() {
		return isError_;
	}

	/**
	 * 错误信息
	 * 
	 * @return the error_
	 */
	public String getError() {
		return error_;
	}

}
