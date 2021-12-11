package com.gdxsoft.spider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理新闻文章
 * 
 * @author admin
 *
 */
public class SpiderNewsContent extends SpiderBase {
	private static Logger LOOGER = LoggerFactory.getLogger(SpiderNewsContent.class);
	private JSONObject result_;

	private String addTag0_;
	private String addTag1_;
	private SpiderLink spiderLink_;

	private List<String> relativeImages_; // 文档关联的图片

	public SpiderNewsContent(String url, String phyPath, String urlPath) {
		super(url, phyPath, urlPath);
		relativeImages_ = new ArrayList<String>();

	}

	/**
	 * 根据配置，分解文档部分，如标题，正文，作者，发布时间...
	 * 
	 * @param cfg
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void praseContent(JSONObject cfg) throws IOException, URISyntaxException {
		result_ = new JSONObject();
		super.downloadDoc();

		// 预先处理初始化部分
		if (cfg.has("init")) {
			JSONObject node = cfg.getJSONObject("init");
			LOOGER.info("执行初始化步骤" + node.toString());
			this.parseInit(node);
		}

		Iterator<?> it = cfg.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			if (key.equals("init") || key.equals("retry")) {
				continue;
			}

			JSONObject node = cfg.getJSONObject(key);
			String rst = this.parseNode(node);
			result_.put(key, rst);
		}

	}

	/**
	 * 预先处理文件 "init":{ "removes":[".newscontent div.pk",".newscontent div.clear"] },
	 * 
	 * @param nodeCfg
	 */
	public void parseInit(JSONObject nodeCfg) {
		if (nodeCfg.has("removes")) {
			// 删除的节点
			JSONArray arr = nodeCfg.getJSONArray("removes");
			for (int i = 0; i < arr.length(); i++) {
				String jq = arr.getString(i);
				if (jq.trim().length() == 0) {
					continue;
				}
				try {
					super.getDoc().select(jq).remove();
				} catch (Exception err) {
					LOOGER.error(err.getMessage());
				}
			}
		}
	}

	/**
	 * 分解节点
	 * 
	 * @param nodeCfg
	 * @return
	 */
	public String parseNode(JSONObject nodeCfg) {
		String exp = nodeCfg.getString("select");
		if (exp == null || exp.isEmpty()) {
			return null;
		}
		String queryType = nodeCfg.optString("select_method");
		String text = nodeCfg.optString("text");

		if (queryType == null || queryType.trim().length() == 0 || queryType.equalsIgnoreCase("jq")
				|| queryType.equalsIgnoreCase("jqery")) {

			Elements eles = super.getDoc().select(exp);

			if (eles == null || eles.size() == 0) {
				return null;
			}
			if (text == null || text.trim().length() == 0 || text.equalsIgnoreCase("text")) {
				return this.getPartText(eles);
			} else if (text.equalsIgnoreCase("js")) {
				String js = nodeCfg.optString("js");
				String s1;
				if (js.indexOf("[HTML]") >= 0) {
					// 脚本中标注了[HTML]标签，可以用 /*[HTML]*/进行标注，以便处理HTML文本
					s1 = this.getPartHtml(eles);
				} else {
					// 用纯文本处理
					s1 = this.getPartText(eles);
				}
				return this.getPartJs(s1, js);
			} else {
				return this.getPartHtml(eles);
			}

		}

		return "not!";
	}

	/**
	 * 用脚本提取文本
	 * 
	 * @param s1
	 * @param js
	 * @return
	 */
	private String getPartJs(String s1, String js) {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");

		// 添加全局变量
		JSONObject jsonGlobal = new JSONObject();
		jsonGlobal.put("URL", super.getUrl());
		StringBuilder sb = new StringBuilder();
		sb.append("var G = ");
		sb.append(jsonGlobal);
		sb.append("; \n\n\n\n");

		sb.append(js);

		try {
			engine.eval(sb.toString());
			Invocable inv = (Invocable) engine;
			Object value = inv.invokeFunction("main", s1);

			return value.toString();
		} catch (ScriptException | NoSuchMethodException e) {
			LOOGER.error(e.getMessage());
			return null;
		}

	}

	/**
	 * 获取文字
	 * 
	 * @param eles
	 * @return
	 */
	private String getPartText(Elements eles) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < eles.size(); i++) {
			Element ele = eles.get(i);
			sb.append(ele.text());
		}

		return sb.toString();
	}

	/**
	 * 获取html
	 * 
	 * @param eles
	 * @return
	 */
	private String getPartHtml(Elements eles) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < eles.size(); i++) {
			Element ele = eles.get(i);

			this.parseImgs(ele);
			if (eles.size() == 1) {
				sb.append(ele.html());
			} else { // 多个元素附加外部标签
				sb.append(ele.outerHtml());
			}
		}

		return sb.toString();
	}

	/**
	 * 解析图片
	 * 
	 * @param ele
	 */
	private void parseImgs(Element ele) {
		Elements imgs = ele.getElementsByTag("img");

		for (int m = 0; m < imgs.size(); m++) {
			Element img = imgs.get(m);
			String src = img.attr("src");
			String src1 = super.fixHref(src);
			// System.out.println(src1);
			// String hashName = this.getHashName(src1);
			// img.attr("sp_src", src1);
			// img.attr("sp_hash", hashName);
			// img.attr("src", "");

			try {
				String my_img_src = super.downloadImg(src1);
				img.attr("src", my_img_src);
				img.attr("ref", src1);

				relativeImages_.add(my_img_src);
			} catch (Exception err) {
				LOOGER.error(err.getMessage());
				relativeImages_.add(src1);
			}

			super.removeEleEvts(img);
		}
	}

	/**
	 * 获取分析文档的结果（如标题，正文，作者，发布时间...），附加上addTag1 和 addTag1, SPIDER_TITLE, SPIDER_DELIVED_DATE
	 * 
	 * @return the result_
	 */
	public JSONObject getResult() {
		if (this.result_ != null) {
			this.result_.put("__ADD_TAG0", this.addTag0_);
			this.result_.put("__ADD_TAG1", this.addTag1_);

			if (this.getSpiderLink() != null) {
				this.result_.put("SPIDER_TITLE", this.getSpiderLink().getTitle());
				this.result_.put("SPIDER_DELIVED_DATE", this.getSpiderLink().getDelivedDate());
			}
		}
		return result_;
	}

	/**
	 * 附加属性0，用于数据库操作用
	 * 
	 * @return the addTag0_
	 */
	public String getAddTag0() {
		return addTag0_;
	}

	/**
	 * 附加属性0，用于数据库操作用
	 * 
	 * @param addTag0_ the addTag0_ to set
	 */
	public void setAddTag0(String addTag0) {
		this.addTag0_ = addTag0;
	}

	/**
	 * 附加属性1，用于数据库操作用
	 * 
	 * @return the addTag1_
	 */
	public String getAddTag1() {
		return addTag1_;
	}

	/**
	 * 附加属性1，用于数据库操作用
	 * 
	 * @param addTag1_ the addTag1_ to set
	 */
	public void setAddTag1(String addTag1) {
		this.addTag1_ = addTag1;
	}

	public void setSpiderLink(SpiderLink link) {
		this.spiderLink_ = link;

	}

	/**
	 * 连接的新闻连接
	 * 
	 * @return the spiderLink_
	 */
	public SpiderLink getSpiderLink() {
		return spiderLink_;
	}

	/**
	 * 文档关联的图片(url地址)
	 * 
	 * @return the relativeImages
	 */
	public List<String> getRelativeImages() {
		return relativeImages_;
	}

}
