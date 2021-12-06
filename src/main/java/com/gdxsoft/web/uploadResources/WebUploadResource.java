package com.gdxsoft.web.uploadResources;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdxsoft.easyweb.utils.UUrl;

public class WebUploadResource {
	public static final int ONE_HOYR = 3600;
	public static final int ONE_DAY = 3600 * 24;
	public static final int ONE_WEEK = 3600 * 24 * 7;

	/**
	 * 30 天
	 */
	public static final int DAYS_30 = 3600 * 24 * 30;
	public static final int ONE_YEAR = 3600 * 24 * 365;

	private String uploadContextPath;
	private int lifeSeconds;
	private String encodingName = "utf-8";

	/**
	 * 初始化上传资源
	 * 
	 * @param contextPath 上传目录，对应的是 ewa_conf.xml中的"img_tmp_path_url" <br>
	 *                    <font color=red><b>不包含</b></font>your_app_context_path的值：<br>
	 *                    例如：&lt;path name="img_tmp_path_url" value="/your_app_context_path/ups/" /&gt;<br>
	 *                    对应的是/ups/
	 * @param lifeSeconds 缓存时长（秒数）
	 */
	public WebUploadResource(String uploadContextPath, int lifeSeconds) {
		if (!uploadContextPath.endsWith("/")) {
			uploadContextPath = uploadContextPath + "/";
		}
		this.uploadContextPath = uploadContextPath;

	}

	/**
	 * 读取上传目录的上传资源内容
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String readUploadResource(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UUrl u = new UUrl(request);

		String path = u.getName().replace(uploadContextPath, "/");

		UploadResource r = UploadResources.getResource(path);
		response.setStatus(r.getStatus());
		if (r.getStatus() != 200) {
			return null;
		}
		response.setContentType(r.getType());
		if (lifeSeconds > 0) {
			response.addHeader("cache-control", "max-age=" + lifeSeconds);
		}
		if (r.isBinary()) {
			response.getOutputStream().write(r.getBuffer());
		} else {
			response.setCharacterEncoding(encodingName);
			response.getWriter().print(r.getContent());
		}
		return null;
	}

	/**
	 * 上传资源对应的路径，例如： /uploads/
	 * 
	 * @return
	 */
	public String getUploadContextPath() {
		return uploadContextPath;
	}

	/**
	 * 上传资源对应的路径，例如： /uploads/
	 * 
	 * @param contextPath
	 */
	public void setUploadContextPath(String contextPath) {
		this.uploadContextPath = contextPath;
	}

	/**
	 * 资源对应的过期秒数，例如：86400
	 * 
	 * @return
	 */
	public int getLifeSeconds() {
		return lifeSeconds;
	}

	public void setLifeSeconds(int lifeSeconds) {
		this.lifeSeconds = lifeSeconds;
	}

	/**
	 * 输出文本的编码
	 * 
	 * @return
	 */
	public String getEncodingName() {
		return encodingName;
	}

	/**
	 * 输出文本的编码
	 * 
	 * @param encodingName
	 */
	public void setEncodingName(String encodingName) {
		this.encodingName = encodingName;
	}
}
