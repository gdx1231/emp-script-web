package com.gdxsoft.spider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.RequestValue;

public class Spider {
	private static Logger LOOGER = Logger.getLogger(Spider.class);
	private JSONObject cfg_;
	private JSONObject cfgCommonListPart_;
	private JSONObject cfgCommonDocParts_;

	private String phyPath_; // 列表和文章及图片保存的物理路径
	private String urlPath_;// 替换文章的图片地址的根url

	private Map<SpiderNewsList, List<SpiderNewsContent>> mapNewsList_;
	// 根据url保留 SpiderNewsList
	private Map<String, SpiderNewsList> mapUrl_;
	private JSONObject cfgSql_;

	public Spider(JSONObject cfg) {
		this.cfg_ = cfg;

		this.phyPath_ = cfg.getString("phy_path");
		this.urlPath_ = cfg.getString("url_path");

		if (this.cfg_.has("list_part")) {
			this.cfgCommonListPart_ = this.cfg_.optJSONObject("list_part");
		}
		if (this.cfg_.has("doc_parts")) {
			this.cfgCommonDocParts_ = this.cfg_.optJSONObject("doc_parts");
		}

		if (this.cfg_.has("doc_sql")) {
			this.cfgSql_ = this.cfg_.optJSONObject("doc_sql");
		}
		mapUrl_ = new HashMap<String, SpiderNewsList>();
		mapNewsList_ = new HashMap<SpiderNewsList, List<SpiderNewsContent>>();
	}

	private void executeSql(SpiderNewsContent newsContent) {
		String ds = this.cfgSql_.getString("datasource");
		String check = this.cfgSql_.getString("check");
		String insert = this.cfgSql_.getString("insert");
		String update = this.cfgSql_.getString("update");

		RequestValue rv = new RequestValue();

		DataConnection cnn = new DataConnection();
		cnn.setConfigName(ds);
		cnn.setRequestValue(rv);

		rv.addValue("url", newsContent.getUrl());
		rv.addValue("hash_name", newsContent.getHashName(newsContent.getUrl()));

		if (newsContent.getRelativeImages() != null && newsContent.getRelativeImages().size() > 0) {
			rv.addValue("relative_img_0", newsContent.getRelativeImages().get(0));
		}
		// 将文章分解的结果（如标题，正文，作者，发布时间...），同时包含附加的
		// , SPIDER_TITLE, SPIDER_DELIVED_DATE , addTag0, addTag1放到 rv中
		rv.addValues(newsContent.getResult());

		DTTable tb = DTTable.getJdbcTable(check, cnn);

		if (tb.getCount() == 0) {
			cnn.executeMultipleUpdate(insert);
		} else {
			rv.addValues(tb);
			cnn.executeMultipleUpdate(update);
		}
		cnn.close();
	}

	/**
	 * 检查数据库中是否存在此文件
	 * 
	 * @param newsContent
	 * @return
	 */
	private boolean checkExistsDocFromSql(SpiderNewsContent newsContent) {
		String ds = this.cfgSql_.getString("datasource");
		String check = this.cfgSql_.getString("check");

		RequestValue rv = new RequestValue();
		rv.addValue("url", newsContent.getUrl());
		rv.addValue("hash_name", newsContent.getHashName(newsContent.getUrl()));

		DTTable tb = DTTable.getJdbcTable(check, ds, rv);
		return tb.getCount() > 0;
	}

	public void doScans() throws IOException, URISyntaxException {
		JSONArray list_url = cfg_.getJSONArray("list_url");

		for (int i = 0; i < list_url.length(); i++) {
			JSONObject cfgList = list_url.getJSONObject(i);
			this.doScanNewsList(cfgList);

		}
	}

	private void scanNewsListNext(SpiderNewsList newsList, String des) throws IOException, URISyntaxException {
		if (newsList.getListNext() == null) {
			return;
		}
		for (int k = 0; k < newsList.getListNext().size(); k++) {
			String nextUrl = newsList.getListNext().get(k);
			// 已经扫描的不再扫描
			if (mapUrl_.containsKey(nextUrl)) {
				continue;
			}

			// 下一页的获取文章列表的配置
			JSONObject nextCfg = new JSONObject(newsList.getCfgListPart());
			// 删除获取下一页的内容
			// nextCfg.remove("next");
			nextCfg.put("url", nextUrl);
			nextCfg.put("des", des);

			this.doScanNewsList(nextCfg);
		}
	}

	/**
	 * 扫描新闻列表
	 * 
	 * @param cfgList
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void doScanNewsList(JSONObject cfgList) throws IOException, URISyntaxException {
		String url = cfgList.getString("url");
		// 已经扫描的不再扫描
		if (mapUrl_.containsKey(url)) {
			return;
		}
		JSONObject list_part;
		if (!cfgList.has("list_part")) {
			list_part = cfgCommonListPart_;
		} else {
			list_part = cfgList.getJSONObject("list_part");
		}

		SpiderNewsList newsList = new SpiderNewsList(url, phyPath_, urlPath_);
		newsList.setCfgUrl(cfgList);

		// 每次重新获取新的列表
		newsList.setSkipExists(true);
		// 检索新闻列表
		newsList.scan(list_part);

		if (newsList.isError()) {
			return;
		}

		List<SpiderNewsContent> lstSpiderNewsContent = new ArrayList<SpiderNewsContent>();
		mapNewsList_.put(newsList, lstSpiderNewsContent);
		mapUrl_.put(url, newsList);

		this.doScanDocs(newsList, cfgList);

		String des = cfgList.getString("des");
		this.scanNewsListNext(newsList, des);
	}

	/**
	 * 获取列表的所有文章
	 * 
	 * @param newsList
	 * @param cfgList
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private void doScanDocs(SpiderNewsList newsList, JSONObject cfgList) throws IOException, URISyntaxException {
		if (newsList.getlistLinks() == null) {
			return;
		}

		JSONObject doc_parts;
		if (!cfgList.has("doc_parts")) {
			doc_parts = cfgCommonDocParts_;
		} else {
			doc_parts = cfgList.getJSONObject("doc_parts");
		}

		// 初始化删除部分
		if (this.cfg_.has("doc_parts_init")) {
			doc_parts.put("init", this.cfg_.getJSONObject("doc_parts_init"));
		}

		List<SpiderNewsContent> lstSpiderNewsContent = mapNewsList_.get(newsList);

		// 这个栏目的描述
		String desOfTheNewsList = newsList.getCfgUrl().optString("des");

		// 是否重新覆盖已经有的数据，当前临时从Esl.main中设置
		boolean isRetry = doc_parts.optBoolean("retry");
		// 根据新闻列表检索文章
		for (int m = 0; m < newsList.getlistLinks().size(); m++) {
			SpiderLink link = newsList.getlistLinks().get(m);
			SpiderNewsContent newsContent = new SpiderNewsContent(link.getUrl(), phyPath_, urlPath_);
			newsContent.setSpiderLink(link);
			// 设置描述，用于数据库写入用
			newsContent.setAddTag0(desOfTheNewsList);

			boolean isScanDoc = true;
			boolean isExists = newsContent.checkFileExists(link.getUrl());

			if (isRetry == false && isExists) {
				// 是否在数据库中
				boolean isExistsInSql = this.checkExistsDocFromSql(newsContent);
				if (isExistsInSql) {
					isScanDoc = false;
				}
			}

			if (isScanDoc) {
				LOOGER.info("Parse：" + link);
				newsContent.praseContent(doc_parts);
				lstSpiderNewsContent.add(newsContent);
				this.executeSql(newsContent);
			} else {
				LOOGER.info("Skip：" + link);
			}
		}
	}
}
