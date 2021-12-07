package com.gdxsoft.web.shortUrl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gdxsoft.easyweb.utils.USnowflake;
import com.gdxsoft.easyweb.utils.Utils;

/**
 * 短地址
 * 
 * @author admin
 *
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
	 * 添加URL
	 * 
	 * @param url
	 * @param supId
	 * @param admId
	 * @return
	 */
	public UrlShort addUrl(String url, int supId, int admId) {
		UrlShortDao d1 = new UrlShortDao();

		UrlShort o = new UrlShort();
		o.setUrlId(USnowflake.nextId());
		
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
	 * 创建唯一编号
	 * 
	 * @return
	 */
	public String createUid() {
		int inc = 0;
		String uid = null;
		while (uid == null) {
			uid = this.chcekAndGetUnique();
			inc++;
			if (inc > 10) {
				return "太多次的尝试";
			}
		}
		return uid;
	}

	/**
	 * 获取唯一的值
	 * 
	 * @return
	 */
	private String chcekAndGetUnique() {
		Map<String, Boolean> unids = new HashMap<String, Boolean>();
		for (int i = 0; i < 30; i++) {
			String uid = Utils.randomStr(10);
			unids.put(uid, true);
		}

		UrlShortDao d1 = new UrlShortDao();
		StringBuilder sb = new StringBuilder();
		sb.append(" URL_STATUS='USED' AND URL_UID in (");
		int inc = 0;
		String firstUnid = null;
		for (String unid : unids.keySet()) {
			if (inc == 0) {
				firstUnid = unid;
			} else {
				sb.append("\n	, ");
			}
			sb.append("'" + unid + "'");
			inc++;
		}
		sb.append(")");
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

		for (int i = 0; i < exists.size(); i++) {
			UrlShort o1 = exists.get(i);
			if (unids.containsKey(o1.getUrlUid())) {
				unids.remove(o1.getUrlUid());
			}
		}
		for (String unid : unids.keySet()) {
			return unid;
		}

		return null;

	}
}
