package com.gdxsoft.spider;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.gdxsoft.easyweb.utils.UFile;
import com.gdxsoft.easyweb.utils.UNet;

public class SpiderBase {
	private Document doc_;
	private String root_;
	private String context_;
	private String url_;
	private boolean isSkipExists_; // 是否忽略已经存在的，默认false
	private String phyPath_; // 列表和文章及图片保存的物理路径
	private String urlPath_;// 替换文章的图片地址的根url

	private Map<String, String> hashNameMap_;

	private static Logger LOOGER = Logger.getLogger(SpiderBase.class);

	public SpiderBase(String url, String phyPath, String urlPath) {
		this.url_ = url;
		this.phyPath_ = phyPath;
		this.urlPath_ = urlPath;

		this.hashNameMap_ = new HashMap<String, String>();
	}

	public void log(Object msg) {
		LOOGER.info(msg);
	}

	/**
	 * 下载html document
	 * 
	 * @param url
	 *            网址
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public Document downloadDoc() throws IOException, URISyntaxException {
		String exists = this.isSkipExists_ ? null : this.readExists(this.url_);
		Document doc;
		if (exists != null) {
			LOOGER.info("exists: " + this.url_);
			doc = Jsoup.parse(exists);
			doc.setBaseUri(url_);
			doc.getElementsByTag("script").remove();
			doc.getElementsByTag("style").remove();
		} else {
			LOOGER.info("GET: " + this.url_);
			int timeout = 100 * 1000; // 100 s
			doc = Jsoup.connect(this.url_).userAgent(UNet.AGENT).timeout(timeout).get();
			this.saveCache(url_, doc.outerHtml());
		}
		this.praseRootAndContext(doc);
		this.doc_ = doc;
		return doc;
	}

	/**
	 * 下载图片
	 * 
	 * @param sourceUrl
	 * @return
	 */
	public String downloadImg(String sourceUrl) {
		String my_url = this.getHashUrl(sourceUrl);

		if (!this.checkFileExists(sourceUrl)) {
			// this.log("DL: " + sourceUrl);
			UNet net = new UNet();
			net.setIsShowLog(true);
			try {
				byte[] buf = net.downloadData(sourceUrl);
				this.saveCache(sourceUrl, buf);
			} catch (Exception err) {
				LOOGER.error(err);
			}
		} else {
			// LOOGER.info("EXISTS: " + sourceUrl);
		}
		return my_url;
	}

	/**
	 * 根据url获取物理文件位置
	 * 
	 * @param url
	 * @return
	 */
	public String getUrlPhyPath(String url) {
		String hashName = this.getHashName(url);
		String path = this.phyPath_ + "/" + hashName;

		return path;
	}

	/**
	 * 检查url对应的物理文件是否存在
	 * 
	 * @param url
	 * @return
	 */
	public boolean checkFileExists(String url) {
		String p = this.getUrlPhyPath(url);

		File f = new File(p);

		if (f.exists() && f.length() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 保存文件
	 * 
	 * @param url
	 * @param content
	 */
	public boolean saveCache(String url, String content) {
		String hashName = this.getHashName(url);
		String path = this.phyPath_ + "/" + hashName;

		try {
			UFile.createNewTextFile(path, content);
			return true;
		} catch (IOException e) {
			this.log(e);

			return false;
		}
	}

	/**
	 * 保存文件
	 * 
	 * @param url
	 * @param content
	 */
	public boolean saveCache(String url, byte[] buf) {
		String path = this.getUrlPhyPath(url);

		try {
			UFile.createBinaryFile(path, buf, true);

			return true;
		} catch (Exception e) {
			this.log(e);

			return false;
		}

	}

	/**
	 * 读取已经存在的文本文件
	 * 
	 * @param url
	 * @return
	 */
	public String readExists(String url) {
		String path = this.getUrlPhyPath(url);
		if (this.checkFileExists(url)) {
			try {
				return UFile.readFileText(path);
			} catch (Exception e) {
				this.log(e);
			}
		}
		return null;
	}

	/**
	 * 去除url中的参数
	 * 
	 * @param url
	 * @param removedParamNames
	 * @return
	 */
	public String removeUrlParameters(String url, String[] removedParamNames) {
		if (url == null || url.isEmpty() || removedParamNames == null || removedParamNames.length == 0) {
			return url;
		}
		int loc0 = url.indexOf("?");
		if (loc0 < 0) {
			return url;
		}
		String c = url.substring(0, loc0);
		// 参数，+1表示不带？号
		String q = url.substring(loc0 + 1);

		String[] params = q.split("\\&");
		HashMap<String, String> remvedParamsMap = new HashMap<String, String>();
		for (int i = 0; i < removedParamNames.length; i++) {
			String paramName = removedParamNames[i].trim();
			remvedParamsMap.put(paramName.toUpperCase(), paramName);
		}

		StringBuilder sb = new StringBuilder(c);
		int inc = 0;
		for (int i = 0; i < params.length; i++) {
			String param = params[i];
			String[] paramExp = param.split("\\=");
			if (remvedParamsMap.containsKey(paramExp[0].toUpperCase().trim())) {
				continue;
			}
			sb.append(inc == 0 ? "?" : "&");
			sb.append(param);
			inc++;
		}

		return sb.toString();
	}

	public String urlEncode(String s) {
		if (s == null || s.isEmpty()) {
			return s;
		}
		try {
			return java.net.URLEncoder.encode(s, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return s;
		}
	}

	/**
	 * 处理url中特殊的字符
	 * 
	 * @param source
	 * @return
	 */
	public String fixSpecialCodeFromHref(String source) {
		String[] location = source.split("\\?");
		// 处理url中的中文
		String[] sources = (location[0] + "__GGGDXX__").split("\\/");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < sources.length; i++) {
			String s = sources[i];

			if (i == sources.length - 1) {
				s = s.replace("__GGGDXX__", "");
			}

			sb.append(i > 0 ? "/" : "");

			if (s.indexOf(":") >= 0 || s.toLowerCase().equals("http:") || s.toLowerCase().equals("https:")) {
				sb.append(s);
			} else {
				sb.append(urlEncode(s));
			}
		}

		if (location.length > 1) {
			try {
				String[] params = location[1].split("\\&");

				for (int i = 0; i < params.length; i++) {
					String param = params[i];
					String[] paramExp = param.split("\\=");
					sb.append(i == 0 ? "?" : "&");
					sb.append(urlEncode(paramExp[0]));
					if (paramExp.length > 1) {
						sb.append("=");
						sb.append(urlEncode(paramExp[1]));
					}
				}
			} catch (Exception err) {
				System.out.println(err);
			}
		} else if (source.endsWith("?")) {
			sb.append("?");
		}

		return sb.toString().replace("+", "%20");
	}

	/**
	 * 修正href网址
	 * 
	 * @param source
	 * @return
	 */
	public String fixHref(String source) {

		if (source == null || source.trim().length() == 0) {
			return source;
		}

		if (source.indexOf("小二班") >= 0) {
			System.out.println(source);
		}

		if (source.toLowerCase().indexOf("http://") == 0 || source.toLowerCase().indexOf("https://") == 0) {
			return fixSpecialCodeFromHref(source);
		}

		if (source.startsWith("/")) {
			return fixSpecialCodeFromHref(this.root_ + source);
		} else if (source.startsWith("?")) {
			return fixSpecialCodeFromHref(this.url_.split("\\?")[0] + source);
		} else {
			return fixSpecialCodeFromHref(this.context_ + source);
		}
	}

	/**
	 * 创建hash文件名
	 * 
	 * @param url
	 * @return
	 */
	public String getHashName(String url) {
		if (this.hashNameMap_.containsKey(url)) {
			return this.hashNameMap_.get(url);
		}

		String hash = "GdX" + (url.hashCode() + "").replace("-", "_");
		String[] parts = url.split("\\.");
		String ext = "";
		if (parts.length > 1) {
			ext = "." + parts[parts.length - 1];
			if (ext.indexOf("/") >= 0) {
				ext = "";
			} else {
				ext = ext.split("\\?")[0];
				ext = ext.replace("&", "");
			}
		}
		ext = ext.replace("/", "_").replace("\\", "_");
		String p = hash + ext;
		this.hashNameMap_.put(url, p);
		return p;
	}

	/**
	 * 获取远程url对应的本地的url，用于图片src的替换等..
	 * 
	 * @param url
	 *            远程url
	 * @return
	 */
	public String getHashUrl(String url) {
		String hashname = this.getHashName(url);
		return this.urlPath_ + hashname;
	}

	/**
	 * 删除对象的所有on事件
	 * 
	 * @param ele
	 */
	public void removeEleEvts(Element ele) {
		if (ele == null) {
			return;
		}
		Iterator<Attribute> it = ele.attributes().iterator();
		List<String> lst = new ArrayList<String>();
		while (it.hasNext()) {
			Attribute keyAttr = it.next();
			if (keyAttr.getKey().toLowerCase().indexOf("on") == 0) {
				lst.add(keyAttr.getKey());
			}
		}

		for (int i = 0; i < lst.size(); i++) {
			ele.attributes().remove(lst.get(i));
		}
	}

	public void praseRootAndContext(Document doc) throws URISyntaxException {
		String fack = "GGDDXX129sdjkfsdkfsdf912";
		String title = doc.baseUri();
		java.net.URI u = new java.net.URI(title);

		this.root_ = u.getScheme() + "://" + u.getHost() + (u.getPort() > 0 ? ":" + u.getPort() : "");
		this.context_ = this.root_;

		// example /Category_19/Index.aspx
		String[] contents = (u.getPath() + fack).split("\\/");

		for (int i = 0; i < contents.length - 1; i++) {
			String ctx = contents[i].replace(fack, "");
			this.context_ += ctx + "/";

		}
	}

	/**
	 * @return the root_
	 */
	public String getUrlRoot() {
		return root_;
	}

	/**
	 * @param root_
	 *            the root_ to set
	 */
	public void setUrlRoot(String root) {
		this.root_ = root;
	}

	/**
	 * @return the context_
	 */
	public String getUrlContext() {
		return context_;
	}

	/**
	 * @param context_
	 *            the context_ to set
	 */
	public void setUrlContext(String context) {
		this.context_ = context;
	}

	/**
	 * @return the doc_
	 */
	public Document getDoc() {
		return doc_;
	}

	/**
	 * @param doc_
	 *            the doc_ to set
	 */
	public void setDoc(Document doc) {
		this.doc_ = doc;
	}

	/**
	 * 文档的网址
	 * 
	 * @return the url_
	 */
	public String getUrl() {
		return url_;
	}

	/**
	 * 文件保存的物理路径
	 * 
	 * @return the phyPath_
	 */
	public String getPhyPath() {
		return phyPath_;
	}

	/**
	 * 文件保存的物理路径
	 * 
	 * @param phyPath_
	 *            the phyPath_ to set
	 */
	public void setPhyPath(String phyPath) {
		this.phyPath_ = phyPath;
	}

	/**
	 * @return the urlPath_
	 */
	public String getUrlPath() {
		return urlPath_;
	}

	/**
	 * @param urlPath_
	 *            the urlPath_ to set
	 */
	public void setUrlPath(String urlPath) {
		this.urlPath_ = urlPath;
	}

	/**
	 * 是否忽略已经存在的下载，默认false，如果是true的话是重新下载
	 * 
	 * @return the isSkipExists_
	 */
	public boolean isSkipExists() {
		return isSkipExists_;
	}

	/**
	 * 是否忽略已经存在的下载，默认false，如果是true的话是重新下载
	 * 
	 * @param isSkipExists_
	 *            the isSkipExists_ to set
	 */
	public void setSkipExists(boolean isSkipExists) {
		this.isSkipExists_ = isSkipExists;
	}
}
