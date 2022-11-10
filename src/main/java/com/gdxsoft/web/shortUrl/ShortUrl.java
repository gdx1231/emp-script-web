package com.gdxsoft.web.shortUrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gdxsoft.easyweb.utils.Utils;

/**
 * 短地址
 */
public class ShortUrl {

	/**
	 * 获取短地址
	 * 
	 * @param uid
	 * @return
	 */
	public static UrlShort getUrlShort(String uid) {
		if (uid == null || uid.trim().length() == 0) {
			return null;
		}
		UrlShortDao d1 = new UrlShortDao();
		String w1 = "URL_STATUS='USED' AND URL_UID='" + uid.trim().replace("'", "''") + "' order by url_id desc";
		ArrayList<UrlShort> al = d1.getRecords(w1);

		if (al.size() == 0) {
			return null;
		}

		return al.get(0);
	}

	/**
	 * 获得已经存在的短 地址
	 * 
	 * @param url 地址
	 * @return
	 */
	public static ArrayList<UrlShort> getUrls(String url) {
		String md5 = Utils.md5(url);
		UrlShortDao d1 = new UrlShortDao();
		String where = " URL_MD5 = '" + md5 + "' order by URL_ID desc";
		return d1.getRecords(where);
	}

	/**
	 * 获得已经存在的 短 地址
	 * 
	 * @param url
	 * @param admId
	 * @return
	 */
	public static ArrayList<UrlShort> getUrls(String url, int admId) {
		String md5 = Utils.md5(url);
		UrlShortDao d1 = new UrlShortDao();
		String where = " URL_MD5 = '" + md5 + "' and adm_id=" + admId + " order by URL_ID desc";
		return d1.getRecords(where);
	}

	/**
	 * 添加URL，返回数字和文字短地址
	 * 
	 * @param url
	 * @param supId
	 * @param admId
	 * @return
	 */
	public UrlShort addUrl(String url, Integer supId, Integer admId) {
		UrlShortDao d1 = new UrlShortDao();

		UrlShort o = new UrlShort();

		o.setAdmId(admId);
		o.setSupId(supId);
		o.setUrlCdate(new Date());
		o.setUrlFull(url);
		o.setUrlStatus("USED");
		String paraUrlUid = this.createUid();
		o.setUrlUid(paraUrlUid);

		String md5 = Utils.md5(url);

		o.setUrlMd5(md5);

		d1.newRecord(o);

		return o;
	}

	/**
	 * 添加URL
	 * 
	 * @param url
	 * @param supId
	 * @param admId
	 * @param onlyNumber 是否仅数字
	 * @return
	 */
	public UrlShort addUrl(String url, Integer supId, Integer admId, boolean onlyNumber) {
		UrlShortDao d1 = new UrlShortDao();

		UrlShort o = new UrlShort();

		o.setAdmId(admId);
		o.setSupId(supId);
		o.setUrlCdate(new Date());
		o.setUrlFull(url);
		o.setUrlStatus("USED");
		String paraUrlUid = this.createUid(onlyNumber);
		o.setUrlUid(paraUrlUid);

		String md5 = Utils.md5(url);

		o.setUrlMd5(md5);

		d1.newRecord(o);

		return o;
	}

	/**
	 * 创建唯一编号，数字和文字
	 * 
	 * @return
	 */
	public String createUid() {

		return createUid(false);
	}

	/**
	 * 创建唯一编号
	 * 
	 * @param onlyNumber 是否仅仅是数字
	 * @return
	 */
	public String createUid(boolean onlyNumber) {
		int inc = 0;
		String uid = null;
		while (uid == null) {
			uid = this.chcekAndGetUnique(onlyNumber);
			inc++;
			if (inc > 10) {
				return "太多次的尝试";
			}
		}
		return uid;
	}

	/**
	 * 利用随机数生成字符串
	 * 
	 * @param length 生成的长度
	 * 
	 * @return 随机数生成字符串
	 */
	public String randomNumber(int length) {
		StringBuilder sb = new StringBuilder();
		char[] CHARS = "0123456789".toCharArray();

		int max = CHARS.length;
		for (int i = 0; i < length; i++) {
			String a = Math.random() * max + "";
			int b = Integer.parseInt(a.split("\\.")[0]);
			char c = CHARS[b];
			sb.append(c);
		}
		return sb.toString();
	}

	private String getRandomCode(int length, boolean onlyNumber) {
		return onlyNumber ? randomNumber(length) : Utils.randomStr(length);
	}

	/**
	 * 获取唯一的值
	 * 
	 * @return
	 */
	private String chcekAndGetUnique(boolean onlyNumber) {
		List<String> unids = new ArrayList<String>();
		String firstUnid = null;

		int inc = 0;
		StringBuilder sb = new StringBuilder();
		sb.append(" URL_STATUS='USED' AND URL_UID in (");

		for (int i = 0; i < 50; i++) {
			String uid = this.getRandomCode(4, onlyNumber);
			firstUnid = uid;
			unids.add(uid);
			if (i == 0) {
				firstUnid = uid;
			} else {
				sb.append("\n	, ");
			}
			sb.append("'" + uid + "'");
		}
		for (int i = 0; i < 40; i++) {
			String uid = this.getRandomCode(5, onlyNumber);
			unids.add(uid);
			sb.append("\n	, ");
			sb.append("'" + uid + "'");
		}
		for (int i = 0; i < 20; i++) {
			String uid = this.getRandomCode(6, onlyNumber);
			unids.add(uid);
			sb.append("\n	, ");
			sb.append("'" + uid + "'");
		}
		for (int i = 0; i < 20; i++) {
			String uid = this.getRandomCode(7, onlyNumber);
			unids.add(uid);
			sb.append("\n	, ");
			sb.append("'" + uid + "'");
		}
		for (int i = 0; i < 20; i++) {
			String uid = this.getRandomCode(8, onlyNumber);
			unids.add(uid);
			sb.append("\n	, ");
			sb.append("'" + uid + "'");
		}
		for (int i = 0; i < 20; i++) {
			String uid = this.getRandomCode(9, onlyNumber);
			unids.add(uid);
			sb.append("\n	, ");
			sb.append("'" + uid + "'");
		}
		for (int i = 0; i < 20; i++) {
			String uid = this.getRandomCode(10, onlyNumber);
			unids.add(uid);
			sb.append("\n	, ");
			sb.append("'" + uid + "'");
		}
		sb.append(")");
		UrlShortDao d1 = new UrlShortDao();
		String whereString = sb.toString();
		List<String> fields = new ArrayList<String>();
		fields.add("URL_ID");
		fields.add("URL_UID");
		ArrayList<UrlShort> exists = d1.getRecords(whereString, fields);
		if (exists.size() == 0) {
			return firstUnid;
		}
		if (exists.size() == inc) {
			return null;
		}
		if (exists.size() == unids.size()) {
			return null;
		}
		Map<String, Boolean> map = new HashMap<>();
		for (int i = 0; i < exists.size(); i++) {
			UrlShort o1 = exists.get(i);
			map.put(o1.getUrlUid(), true);
		}
		for (int i = 0; i < unids.size(); i++) {
			String uid = unids.get(i);
			if (!map.containsKey(uid)) {
				return uid;
			}
		}
		return null;

	}
}
