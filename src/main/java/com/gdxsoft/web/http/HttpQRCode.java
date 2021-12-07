package com.gdxsoft.web.http;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UConvert;
import com.gdxsoft.easyweb.utils.UFile;
import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.web.qrcode.QRCode;

/**
 * 输出二维码 url参数：<br>
 * msg 二维码信息<br>
 * width 二维码宽带和高度<br>
 * logo 附加到二维码中心的图片url，必须是本地上传的图片在UPath.getPATH_UPLOAD()定义的目录里<br>
 * show 显示方式：base64 返回图片的base64编码，url图片的网址，其它，图片的二进制
 * 
 */
public class HttpQRCode implements IHttp {
	private static Logger LOGGER = LoggerFactory.getLogger(HttpQRCode.class);

	@Override
	public String response(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestValue rv = new RequestValue(request);

		String msg = rv.getString("msg");
		int w = 300;
		if (rv.s("width") != null) {
			try {
				w = rv.getInt("width");
				if (w > 1000) {
					w = 1000;
				} else if (w < 100) {
					w = 100;
				}
			} catch (Exception err) {
				return ("oh.");
			}
		}
		// 拼接参数，用于判断是否已经创建
		StringBuilder hash_string = new StringBuilder();
		hash_string.append("msg=");
		hash_string.append(msg);
		hash_string.append("~~~~width=");
		hash_string.append(w);

		// 必须是本地上传的图片在UPath.getPATH_UPLOAD()定义的目录里
		String logo = rv.s("logo");
		if (logo == null) {
			logo = "";
		} else {
			logo = logo.trim();
		}

		File fileLogo = null;
		if (logo.length() > 0) {
			logo = logo.trim();
			if (logo.toLowerCase().startsWith("file://") || logo.indexOf("./") >= 0 || logo.indexOf(".\\") >= 0
					|| logo.indexOf("..") >= 0) {
				return "oh. invalid logo url";
			}
			hash_string.append("~~~~logo=");
			
			// 检查logo文件是否存在
			String logo_path = UPath.getPATH_UPLOAD() + "/" + logo;
			fileLogo = new File(logo_path);
			if (!fileLogo.exists()) {
				return "logo ?";
			}
			hash_string.append(fileLogo.getAbsolutePath());
		}
		// 获取参数的md5
		String md5 = Utils.md5(hash_string.toString());
		String IfNoneMatch = request.getHeader("If-None-Match");
		if (IfNoneMatch != null) {
			if (("W/" + md5).equals(IfNoneMatch)) {
				// 304 文件没有修改
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				return null;
			}
		}

		// 二维码 Qcode编码
		byte[] img = null;
		String[] paths = QRCode.getQRCodeSavedPath(md5, "jpeg");
		String path = paths[0];
		File exists = new File(path);
		if (exists.exists()) {
			try {
				// 读取已经存在的二维码
				img = UFile.readFileBytes(exists.getAbsolutePath());
			} catch (Exception err) {
				LOGGER.warn(err.getMessage());
			}
		}
		if (img == null) {
			// 创建新的二维码
			if (logo.length() == 0) {
				img = QRCode.createQRCode(msg, w);
			} else {
				img = QRCode.createQRCode(msg, w, fileLogo);
			}
			UFile.createBinaryFile(path, img, true);
		}
		StringBuilder sb = new StringBuilder();
		int cache_life = 3000;// 3000s
		response.addHeader("ETag", "W/" + md5);
		response.setHeader("Cache-Control", "max-age=" + cache_life);
		String show = rv.s("show");
		if ("base64".equals(show)) {
			String base64 = UConvert.ToBase64String(img);
			sb.append("data:image/jpeg;base64,");
			sb.append(base64);
			return sb.toString();
		} else if ("url".equals(show)) {
			return paths[1];
		} else {
			response.setContentType("image/jpeg");
			response.setContentLength(img.length);
			response.getOutputStream().write(img);
			return null;
		}
	}

}
