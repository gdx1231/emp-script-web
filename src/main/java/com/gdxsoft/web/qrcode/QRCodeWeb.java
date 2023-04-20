package com.gdxsoft.web.qrcode;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UConvert;
import com.gdxsoft.easyweb.utils.UFile;
import com.gdxsoft.easyweb.utils.UJSon;
import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.web.shortUrl.ShortUrl;
import com.gdxsoft.web.shortUrl.UrlShort;

public class QRCodeWeb {
	private RequestValue rv;
	private String hostPrefix; // 短地址前缀，例如 https://gdxsoft.xyz/s/

	public QRCodeWeb(RequestValue rv, String hostPrefix) {
		this.rv = rv;
		this.hostPrefix = hostPrefix;
	}

	public File getLogo(String logo) throws Exception {
		if (logo.indexOf("./") >= 0 || logo.indexOf(".\\") >= 0 || logo.indexOf("..") >= 0) {
			throw new Exception("Invalid logo, NOT alow ./ or .. ");
		}

		String logo_path = UPath.getPATH_UPLOAD() + "/" + logo;
		File fileLogo = new File(logo_path);
		if (!fileLogo.exists()) {
			throw new Exception("Logo not found: " + logo);
		}

		return fileLogo;
	}

	private boolean checkIfNoneMatch(String md5) {
		String IfNoneMatch = rv.getRequest().getHeader("If-None-Match");
		if (IfNoneMatch != null) {
			if (("W/" + md5).equals(IfNoneMatch)) {
				// 304 文件没有修改
				is304 = true;
				return true;
			}
		}
		is304 = false;
		return false;
	}

	public JSONObject createQRCode(String msg, int width, boolean useShortUrl, boolean onlyNumber, byte[] logoBuffer) {
		JSONObject obj = new JSONObject();
		UJSon.rstSetTrue(obj, null);
		obj.put("original_msg", msg);

		if (width > 1000) {
			width = 1000;
		} else if (width < 100) {
			width = 100;
		}
		obj.put("width", width);

		// 拼接参数，用于判断是否已经创建
		StringBuilder hash_string = new StringBuilder();
		hash_string.append("msg=");
		hash_string.append(msg);
		hash_string.append("~~~~width=");
		hash_string.append(width);
		hash_string.append("~~~~logo=");
		hash_string.append(Utils.md5(logoBuffer));

		if (rv.s("use_short_url") != null) { // 使用短地址
			hash_string.append("~~~~use_short_url=yes");
		}

		// 获取参数的md5
		String md5 = Utils.md5(hash_string.toString());
		this.hashString = hash_string.toString();
		obj.put("md5", md5);
		this.md5 = md5;

		boolean ifNoneMatch = this.checkIfNoneMatch(md5);
		if (ifNoneMatch) {
			// 304 文件没有修改
			obj.put("httpcode", 304);
			return obj;
		}

		// 二维码 Qcode编码
		byte[] img = null;
		String[] paths = QRCode.getQRCodeSavedPath(md5, "jpeg");
		String path = paths[0];
		File exists = new File(path);
		if (exists.exists()) {
			try {
				img = UFile.readFileBytes(exists.getAbsolutePath());
			} catch (Exception err) {

			}
		}

		if (img == null) {
			if (useShortUrl) { // 创建段地址
				msg = this.createShortUrl(msg, onlyNumber, rv);
				obj.put("shortUrl", msg);
			}
			try {
				img = QRCode.createQRCode(msg, width, logoBuffer);
			} catch (IOException e) {
				UJSon.rstSetFalse(obj, e.getMessage());
				return obj;
			}

			try {
				UFile.createBinaryFile(path, img, true);
			} catch (Exception e) {
				UJSon.rstSetFalse(obj, e.getMessage());
				return obj;
			}
		}

		this.setQrImage(img);
		this.qrImageUrl = paths[1];

		obj.put("msg", msg);
		obj.put("img", paths[1]);
		UJSon.rstSetTrue(obj, null);

		return obj;
	}

	/**
	 * 创建二维码
	 * 
	 * @param msg         消息
	 * @param width       宽带
	 * @param useShortUrl 是否用短地址
	 * @param logo        中间的小图
	 * @return
	 */
	public JSONObject createQRCode(String msg, int width, boolean useShortUrl, boolean onlyNumber, String logo) {
		JSONObject obj = new JSONObject();
		UJSon.rstSetTrue(obj, null);
		obj.put("original_msg", msg);

		if (width > 1000) {
			width = 1000;
		} else if (width < 100) {
			width = 100;
		}
		obj.put("width", width);

		// 拼接参数，用于判断是否已经创建
		StringBuilder hash_string = new StringBuilder();
		hash_string.append("msg=");
		hash_string.append(msg);
		hash_string.append("~~~~width=");
		hash_string.append(width);

		if (logo == null) {
			logo = "";
		} else {
			logo = logo.trim();
		}

		File fileLogo = null;

		if (logo.length() > 0) {
			hash_string.append("~~~~logo=");
			try {
				fileLogo = this.getLogo(logo);
			} catch (Exception e) {
				UJSon.rstSetFalse(obj, e.getMessage());
				return obj;
			}
			hash_string.append(fileLogo.getAbsolutePath());
		}
		if (rv.s("use_short_url") != null) { // 使用短地址
			hash_string.append("~~~~use_short_url=yes");
		}
		obj.put("logo", logo);

		// 获取参数的md5
		String md5 = Utils.md5(hash_string.toString());
		this.hashString = hash_string.toString();
		this.md5 = md5;
		obj.put("md5", md5);

		boolean ifNoneMatch = this.checkIfNoneMatch(md5);
		if (ifNoneMatch) {
			// 304 文件没有修改
			obj.put("httpcode", 304);
			return obj;
		}

		// 二维码 Qcode编码
		byte[] img = null;
		String[] paths = QRCode.getQRCodeSavedPath(md5, "jpeg");
		String path = paths[0];
		File exists = new File(path);
		if (exists.exists()) {
			try {
				img = UFile.readFileBytes(exists.getAbsolutePath());
			} catch (Exception err) {

			}
		}

		if (img == null) {
			if (useShortUrl) { // 创建段地址
				msg = this.createShortUrl(msg, onlyNumber, rv);
			}
			if (logo.length() == 0) {
				img = QRCode.createQRCode(msg, width);
			} else {
				try {
					img = QRCode.createQRCode(msg, width, fileLogo);
				} catch (IOException e) {
					UJSon.rstSetFalse(obj, e.getMessage());
					return obj;
				}
			}
			try {
				UFile.createBinaryFile(path, img, true);
			} catch (Exception e) {
				UJSon.rstSetFalse(obj, e.getMessage());
				return obj;
			}
		}
		this.setQrImage(img);
		this.qrImagePath = path;
		this.qrImageUrl = paths[1];
		obj.put("msg", msg);
		obj.put("img", paths[1]);
		UJSon.rstSetTrue(obj, null);

		return obj;
	}

	public String createShortUrl(String longUrl, boolean onlyNumber, RequestValue rv) {
		UrlShort us;
		List<UrlShort> al = ShortUrl.getUrls(longUrl);
		if (al.size() == 0) {
			ShortUrl su = new ShortUrl();
			Integer supId = rv.isNotNull("g_sup_id") ? rv.getInt("g_sup_id") : null;
			Integer admId = rv.isNotNull("g_adm_Id") ? rv.getInt("g_adm_Id") : null;
			us = su.addUrl(longUrl, supId, admId, onlyNumber);
		} else {
			us = al.get(0);
		}

		return hostPrefix + us.getUrlUid();
	}

	public Map<String, String> getResponseHeaders(int cacheLife) {
		Map<String, String> headers = new HashMap<String, String>();
		// int cache_life = 3000;//3000s
		headers.put("ETag", "W/" + md5);
		headers.put("Cache-Control", "max-age=" + cacheLife);
		return headers;
	}

	public String toImageBase64() {
		if (this.qrImage == null) {
			return null;
		}
		return "data:image/jpeg;base64," + UConvert.ToBase64String(qrImage);
	}

	private byte[] qrImage;

	public byte[] getQrImage() {
		return qrImage;
	}

	void setQrImage(byte[] qrImage) {
		this.qrImage = qrImage;
	}

	private String qrImagePath;

	public String getQrImagePath() {
		return qrImagePath;
	}

	private String md5;

	public String getMd5() {
		return md5;
	}

	private String hashString;

	public String getHashString() {
		return hashString;
	}

	private boolean is304;

	public boolean is304() {
		return this.is304;
	}

	private String qrImageUrl;

	public String getQrImageUrl() {
		return qrImageUrl;
	}
}
