package com.gdxsoft.web.http;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.script.display.frame.FrameParameters;
import com.gdxsoft.easyweb.script.servlets.FileOut;
import com.gdxsoft.easyweb.utils.UFile;
import com.gdxsoft.easyweb.utils.UImages;
import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.easyweb.utils.UUrl;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.easyweb.utils.fileConvert.File2Html;
import com.gdxsoft.easyweb.utils.fileConvert.File2Pdf;

/**
 * 下载或在线查看oa文件<br>
 * 在调用程序里在requst.setAttribute(\"pdfjs\",
 * \"/xxx/pdfjs-1.10.100-dist/web/viewer.html\")
 */
public class HttpFileViewBase {
	private static Logger LOGGER = LoggerFactory.getLogger(HttpFileViewBase.class);

	public final static String RECORD_NOT_EXISTS_CN = "文件呢？去哪遛弯了(数据不存在)";
	public final static String RECORD_NOT_EXISTS_EN = "The data not found";

	public final static String FILE_NOT_EXISTS_CN = "文件呢？去哪遛弯拉了（物理文件缺失）";
	public final static String FILE_NOT_EXISTS_EN = "The file not found";

	public final static String NO_RIGHT_WITH_SUPPLY_CN = "此文件您无权查看或下载，商户不一致";
	public final static String NO_RIGHT_WITH_SUPPLY_EN = "You do not have permission to view or download this file";

	public final static String NEED_LOGIN_CN = "您需要登录商户系统后查看或下载";
	public final static String NEED_LOGIN_EN = "Need login";

	public final static String DENY_DOWNLOAD_CN = "禁止下载";
	public final static String DENY_DOWNLOAD_EN = "Download deny";

	/**
	 * 图片缩放
	 */
	public final static String RESIZE = "resize";
	/**
	 * 下载文件
	 */
	public final static String DOWNLOAD = "download";
	/**
	 * 下载文件
	 */
	public final static String DOWNLOAD_FILE = "download_file";
	/**
	 * 在线查看
	 */
	public final static String INLINE = "inline";

	/**
	 * 缩略图
	 */
	public final static String SMALL = "SMALL";
	/**
	 * 缩略图，错误的拼写，兼容用
	 */
	public final static String SMAILL = "SMAILL";

	/**
	 * 数据不存在消息
	 * 
	 * @param en         中英文
	 * @param skipHeader 是否不包含html标签
	 * @return
	 */
	public static String msgRecordNotExists(boolean en, boolean skipHeader) {
		return msgAppendHtmlHead(en ? RECORD_NOT_EXISTS_EN : RECORD_NOT_EXISTS_CN, skipHeader);
	}

	/**
	 * 物理文件不存在
	 * 
	 * @param en         中英文
	 * @param skipHeader 是否不包含html标签
	 * 
	 * @return
	 */
	public static String msgPhyFileNotExists(boolean en, boolean skipHeader) {
		return msgAppendHtmlHead(en ? FILE_NOT_EXISTS_EN : FILE_NOT_EXISTS_CN, skipHeader);
	}

	/**
	 * 此文件您无权查看或下载，商户不一致
	 * 
	 * @param en         中英文
	 * @param skipHeader 是否不包含html标签
	 * 
	 * @return
	 */
	public static String msgNoRightWithSup(boolean en, boolean skipHeader) {
		return msgAppendHtmlHead(en ? NO_RIGHT_WITH_SUPPLY_EN : NO_RIGHT_WITH_SUPPLY_CN, skipHeader);

	}

	/**
	 * 您需要登录商户系统后查看或下载
	 * 
	 * @param en         中英文
	 * @param skipHeader 是否不包含html标签
	 * 
	 * @return
	 */
	public static String msgNeedLogin(boolean en, boolean skipHeader) {
		return msgAppendHtmlHead(en ? NEED_LOGIN_EN : NEED_LOGIN_CN, skipHeader);
	}

	/**
	 * 禁止下载
	 * 
	 * @param en         中英文
	 * @param skipHeader 是否不包含html标签
	 * 
	 * @return
	 */
	public static String msgDenyDownload(boolean en, boolean skipHeader) {
		// response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return msgAppendHtmlHead(en ? DENY_DOWNLOAD_EN : DENY_DOWNLOAD_CN, skipHeader);

	}

	/**
	 * 输出带有html头部的消息
	 * 
	 * @param content    自己要转义特殊符号
	 * @param skipHeader 是否不包含html标签
	 * 
	 * @return
	 */
	public static String msgAppendHtmlHead(String content, boolean skipHeader) {
		final String top = "<div><div class='tip' bgcolor='white'>";
		final String bottom = "</div></div>";

		StringBuilder sb = new StringBuilder();
		if (!skipHeader) {
			sb.append("<!DOCTYPE HTML><html><head>\n");
			sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\n");
			sb.append("<meta charset=\"UTF-8\">\n");
			sb.append("<body style='background-color:#fff'>");
		}
		sb.append(top);
		sb.append(content);
		sb.append(bottom);

		if (!skipHeader) {
			sb.append("</body></html>");
		}
		return sb.toString();
	}

	/**
	 * 是否为文档格式，doc, docx, rtf, xls, xlsx, ppt, pptx, wps, odt, ods, odp, txt, html
	 * 
	 * @param ext 扩展名
	 * @return
	 */
	public static boolean isDocuement(String ext) {
		return "doc".equalsIgnoreCase(ext) || "docx".equalsIgnoreCase(ext) || "rtf".equalsIgnoreCase(ext)
				|| "xls".equalsIgnoreCase(ext) || "xlsx".equalsIgnoreCase(ext) || "ppt".equalsIgnoreCase(ext)
				|| "pptx".equalsIgnoreCase(ext) || "wps".equalsIgnoreCase(ext) // wps
				|| "odt".equalsIgnoreCase(ext) // openoffice word
				|| "ods".equalsIgnoreCase(ext) // openoffice excel
				|| "odp".equalsIgnoreCase(ext) // openoffice ppt
				|| "txt".equalsIgnoreCase(ext) // text
				|| "html".equalsIgnoreCase(ext) // html
		;
	}

	/**
	 * pdf文件
	 * 
	 * @param ext
	 * @return
	 */
	public static boolean isPdf(String ext) {
		return "pdf".equalsIgnoreCase(ext);
	}

	/**
	 * 图片
	 * 
	 * @param ext
	 * @return
	 */
	public static boolean isImage(String ext) {
		String fileType = FileOut.MAP.getOrDefault(ext.toLowerCase(), "");

		return fileType.indexOf("image/") == 0;
	}

	/**
	 * 视频
	 * 
	 * @param ext
	 * @return
	 */
	public static boolean isVideo(String ext) {
		String fileType = FileOut.MAP.getOrDefault(ext.toLowerCase(), "");

		return fileType.indexOf("video/") == 0;
	}

	/**
	 * 音频
	 * 
	 * @param ext
	 * @return
	 */
	public static boolean isAudio(String ext) {
		String fileType = FileOut.MAP.getOrDefault(ext.toLowerCase(), "");

		return fileType.indexOf("audio/") == 0;
	}

	private String pdfJs;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private RequestValue rv;
	private boolean skipHeader;
	private boolean en;
	private int cacheLife; // 缓存时间 单位秒
	private boolean small; // 是否为缩略图
	private boolean download; // 是否为下载模式
	private boolean inline; // 是否为inline模式

	private boolean supportAvif = false;
	private boolean supportWebp = true;
	private boolean supportHeic = false;

	private boolean forceUsingPdfJs = false; // 强制使用pdfjs查看pdf文件

	private Dimension resize;

	private String htmlTop; // 附加地页面头部在<body>后面
	private String htmlBottom; // 附加的尾部在</body>上面

	public Dimension getResize() {
		return resize;
	}

	public void setResize(Dimension resize) {
		this.resize = resize;
	}

	public void initParameters() {
		small = rv.s("SMAILL") != null || rv.s("SMALL") != null;
		skipHeader = rv.s(FrameParameters.EWA_APP) != null || rv.s(FrameParameters.EWA_AJAX) != null;
		en = rv.isEn();
		this.download = rv.s(DOWNLOAD) != null || rv.s(DOWNLOAD_FILE) != null;
		this.inline = rv.s(INLINE) != null;

		if (rv.isNotBlank(RESIZE)) {
			resize = UImages.parseSize(rv.s(RESIZE));
		}

	}

	/**
	 * 处理文件
	 * 
	 * @param file          文件
	 * @param ext           扩展名
	 * @param title         标题
	 * @param allowDownload 允许下载
	 * @param ifNoneMatch   304检查头
	 * @return
	 * @throws IOException
	 */
	public String handleFile(File file, String ext, String title, boolean allowDownload, String ifNoneMatch)
			throws IOException {

		if (this.resize != null && isImage(ext) && !small) {
			File resizedFile = this.createResized(file, resize.width, resize.height, 70);
			file = resizedFile;
		}
		if (this.isDownload()) {
			if (!allowDownload) {
				return HttpFileViewBase.msgDenyDownload(en, skipHeader);
			}
			return this.downloadFile(file, title, request, response);
		}
		if (this.isInline()) {
			if (rv.s("pdf") != null) { // 查看文档转换的pdf文件
				file = new File(file.getAbsolutePath() + ".pdf");
			}
			return this.inlineFile(file, request, response);
		}
		if (ifNoneMatch != null && ifNoneMatch.length() > 0) {
			// 比较文件是否有下载或改变
			String requestIfNoneMatch = request.getHeader("If-None-Match");
			if (ifNoneMatch.equals(requestIfNoneMatch)) {
				// 304
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				return null;
			}
		}
		response.setHeader("ETag", ifNoneMatch);
		if (this.cacheLife > 0) {
			response.setHeader("Cache-Control", "max-age=" + cacheLife);
		}
		if (small) {
			this.handleSmall(file, ext);
			return null;
		}

		String fileType = FileOut.MAP.getOrDefault(ext.toLowerCase(), "");

		UUrl uu = new UUrl(request);
		uu.add("inline", "1");
		String url = uu.getUrl(true);
		if (HttpFileViewBase.isDocuement(ext)) {
			// return this.viewDocument(file, ext, title, url, skipHeader);
			return this.viewDocumentAsPdf(file, ext, title, url, skipHeader);
		} else if (HttpFileViewBase.isImage(ext)) {
			return this.viewImage(title, url, skipHeader);
		} else if (HttpFileViewBase.isVideo(ext)) { // 视频
			return this.viewVideo(title, url, skipHeader);
		} else if (HttpFileViewBase.isPdf(ext)) {
			return this.viewPdf(title, url, skipHeader);
		} else if (HttpFileViewBase.isAudio(ext)) { // 音频
			return this.viewAudio(title, url, skipHeader);
		} else { // 下载文件
			if (!allowDownload) {
				return HttpFileViewBase.msgDenyDownload(en, skipHeader);
			}
			return this.downloadFile(file, fileType, request, response);
		}
	}

	/**
	 * 当参数带small时候，输出small图片
	 * 
	 * @param file
	 * @param ext
	 * @throws IOException
	 */
	public void handleSmall(File file, String ext) throws IOException {
		// EmpScriptV2 静态文件默认目录
		String emp = UPath.getEmpScriptV2Path();
		if (HttpFileViewBase.isImage(ext)) {
			File thumbnail = this.createResized(file, 128, 128, 71);
			if (thumbnail == null) {
				response.sendRedirect(emp + "/EWA_STYLE/images/transparent.png");
				return;
			}
			this.inlineFile(thumbnail, request, response);
		} else {
			// 如果不是图片，直接输出默认图标
			String url = this.getFileIcon(ext);
			response.sendRedirect(url);

		}

	}

	/**
	 * 获取文件图标
	 * 
	 * @param ext
	 * @return
	 */
	public String getFileIcon(String ext) {
		// EmpScriptV2 静态文件默认目录
		String emp = UPath.getEmpScriptV2Path();
		if (HttpFileViewBase.isDocuement(ext)) {
			if (ext.trim().equalsIgnoreCase("doc") || ext.trim().equalsIgnoreCase("docx")
					|| ext.trim().equalsIgnoreCase("rtf") || ext.trim().equalsIgnoreCase("wps")) {
				return emp + "/EWA_STYLE/images/file_png/MSWD.png";
			} else if (ext.trim().equalsIgnoreCase("xls") || ext.trim().equalsIgnoreCase("xlsx")) {
				return emp + "/EWA_STYLE/images/file_png/XCEL.png";
			} else if (ext.trim().equalsIgnoreCase("ppt") || ext.trim().equalsIgnoreCase("pptx")) {
				return emp + "/EWA_STYLE/images/file_png/PPT3.png";
			} else if (ext.trim().equalsIgnoreCase("html") || ext.trim().equalsIgnoreCase("html")) {
				return emp + "/EWA_STYLE/images/file_png/html.png";
			} else if (ext.trim().equalsIgnoreCase("txt")) {
				return emp + "/EWA_STYLE/images/file_png/Notepad.png";
			} else if (ext.trim().equalsIgnoreCase("odt") || ext.trim().equalsIgnoreCase("ods")
					|| ext.trim().equalsIgnoreCase("odp")) {
				return emp + "/EWA_STYLE/images/file_png/openoffice.png";
			} else {
				return emp + "/EWA_STYLE/images/file_png/EDIT.png";
			}
		} else if (HttpFileViewBase.isImage(ext)) {
			return emp + "/EWA_STYLE/images/file_png/Image.png";
		} else if (HttpFileViewBase.isVideo(ext)) { // 视频
			return emp + "/EWA_STYLE/images/file_png/vod.png";
		} else if (HttpFileViewBase.isPdf(ext)) {
			return emp + "/EWA_STYLE/images/file_png/ACR_App.png";
		} else if (HttpFileViewBase.isAudio(ext)) {
			return emp + "/EWA_STYLE/images/file_png/mp3.png";
		} else {
			return emp + "/EWA_STYLE/images/file_png/gnote.png";
		}
	}

	/**
	 * 创建图片缩略图
	 * 
	 * @param orginalFile 原始文件
	 * @param width       宽
	 * @param height      高
	 * @param quality     质量
	 * @return 缩略图
	 */
	public File createResized(File orginalFile, int width, int height, int quality) {
		String accept = this.rv.getRequest().getHeader("accept"); // 获取请求头中的accept字段
		String imgExt = "jpeg"; // 默认图片格式为jpeg
		if (UImages.checkImageMagick() && accept != null) { // 如果accept字段不为空
			if (supportAvif && accept.indexOf("image/avif") >= 0) { // ms edge 暂时不支持
				imgExt = "avif"; // 如果支持avif格式并且accept字段包含image/avif，就选择avif格式
			} else if (supportWebp && accept.indexOf("image/webp") >= 0) {
				imgExt = "webp"; // 如果支持webp格式并且accept字段包含image/webp，就选择webp格式
			} else if (supportHeic && accept.indexOf("image/heic") >= 0) {
				imgExt = "heic"; // 如果支持heic格式并且accept字段包含image/heic，就选择heic格式
			}
		}
		// 根据原始文件和缩放参数生成一个新的文件名
		String exitspic = UImages.getResizedImageName(orginalFile, width, height, imgExt);
		// 创建一个新的文件对象
		File fileSmallPic = new File(exitspic);

		if (fileSmallPic.exists()) { // 如果新的文件已经存在
			return fileSmallPic; // 就直接返回该文件对象
		}
		try {
			// 否则调用UImages类的方法来创建一个缩放后的图片文件，并返回其路径
			String f2 = UImages.createSmallImage(orginalFile.getAbsolutePath(), width, height, imgExt, 70);
			// 根据路径创建一个新的文件对象并返回
			return new File(f2);
		} catch (Exception err1) { // 如果发生异常
			LOGGER.error("{}->{},{}", orginalFile, fileSmallPic, err1.getMessage()); // 记录错误信息到日志中
			return null; // 返回空值
		}

	}

	/**
	 * 下载文件
	 * 
	 * @param isDl     是否允许下载
	 * @param file     文件
	 * @param fileName 文件名
	 * @param request
	 * @param response
	 * @return
	 */
	public String downloadFile(File file, String fileName, HttpServletRequest request, HttpServletResponse response) {
		FileOut fo = new FileOut(request, response);
		fo.initFile(file);
		fo.download(fileName);
		return null;
	}

	/**
	 * 在线输出文件二进制
	 * 
	 * @param file
	 * @param fileName
	 * @param request
	 * @param response
	 * @return
	 */
	public String inlineFile(File file, HttpServletRequest request, HttpServletResponse response) {
		FileOut fo = new FileOut(request, response);

		fo.initFile(file);

		long cachedLife = 3600 * 60 * 24; // 24小时

		fo.outFileBytesInline(true, cachedLife);
		return null;

	}

	/**
	 * 在线查看文档
	 * 
	 * @param file
	 * @param ext
	 * @param title
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public String viewDocumentAsPdf(File file, String ext, String title, String url, boolean skipHeader)
			throws IOException {

		File parentDir = file.getParentFile();
		String filename = file.getName();

		File2Pdf pdf = new File2Pdf();
		String htmlPath = parentDir.getAbsolutePath() + "/" + filename + ".pdf";
		File fPdf = new File(htmlPath);

		if (!fPdf.exists()) {

			pdf.convert2PDF(file, fPdf);
		}

		String urlPdf = url + "&pdf=" + filename + ".pdf";

		return this.viewPdf(title, urlPdf, skipHeader);
	}

	/**
	 * 在线查看文档
	 * 
	 * @param file
	 * @param ext
	 * @param title
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public String viewDocument(File file, String ext, String title, String url, boolean skipHeader) throws IOException {
		String f1 = file.getAbsolutePath();

		File parentDir = file.getParentFile();
		String filename = file.getName();

		File2Html html = new File2Html();
		String htmlPath = parentDir.getAbsolutePath() + "/" + filename + ".html/" + filename + ".html";
		String baseUrl = url + ".html/";
		File fPdf = new File(htmlPath);
		File fParent = fPdf.getParentFile();

		if (!fPdf.exists()) {
			if (fParent.exists() && fParent.isFile()) {
				fParent.delete();
			}

			fParent.mkdirs();
			html.convert2Html(f1, fPdf.getAbsolutePath());
		}

		Document doc = Jsoup.parse(fPdf, "gbk");
		Elements eles1 = doc.getElementsByTag("meta");
		String charset = "gbk";
		for (int i = 0; i < eles1.size(); i++) {
			String http_equiv = eles1.get(i).attr("http-equiv");
			if (http_equiv != null && http_equiv.equalsIgnoreCase("CONTENT-TYPE")) {
				String att_content = eles1.get(i).attr("content");
				if (att_content != null) {
					int loc0 = att_content.toLowerCase().indexOf("charset=");
					if (loc0 >= 0) {
						charset = att_content.substring(loc0 + 8);
						charset = charset.split(";")[0];
						break;
					}
				}
			}
		}
		if (!charset.toLowerCase().equals("gbk")) {
			doc = Jsoup.parse(fPdf, charset);
		}

		if (ext.equalsIgnoreCase("ppt") || ext.equalsIgnoreCase("pptx")) {
			// ppt文件或输出一堆图片，合并这些图片
			File[] files = fParent.listFiles();
			ArrayList<String> al = new ArrayList<String>();
			for (int i = 0; i < files.length; i++) {
				al.add(null);
			}
			for (int i = 0; i < files.length; i++) {
				File img = files[i];
				String imgName = img.getName().toLowerCase();
				if (imgName.endsWith(".jpg") || imgName.endsWith(".png") || imgName.endsWith(".jpeg")) {
					String nameNoExt = UFile.getFileNoExt(imgName);
					nameNoExt = nameNoExt.toLowerCase().replace("img", "");

					// 按照顺序排列
					int imgIndex = Integer.parseInt(nameNoExt);
					String s = "<div class='file-view-ppt'><img src=\"" + baseUrl + img.getName() + "\" alt=\""
							+ img.getName() + "\"></div>";
					al.add(imgIndex, s);
				}
			}
			StringBuilder sb1 = new StringBuilder();
			for (int i = 0; i < al.size(); i++) {
				String s = al.get(i);
				if (s != null) {
					sb1.append(s);
				}
			}
			doc.getElementsByTag("body").html(sb1.toString());
		} else {
			Elements eleImgs = doc.getElementsByTag("img");
			for (int i = 0; i < eleImgs.size(); i++) {
				Element img = eleImgs.get(i);
				String src = baseUrl + img.attr("src");
				img.attr("SRC", src);
			}
		}

		Elements eles = doc.getElementsByTag("body");
		String html1 = eles.get(0).html();
		StringBuilder sbHtml = new StringBuilder( this.createHtml(title));
		sbHtml.append("<div class='oa-doc-view EWA_TABLE' id='doc-view' style='width:760px;margin:0 auto'>");
		sbHtml.append(html1);
		sbHtml.append("</div>");
		//sbHtml.append(skipHeader ? "" : "</div></body></html>");
		sbHtml.append(createFooterHtml());
		return sbHtml.toString();
	}

	public String viewImage(String title, String url, boolean skipHeader) {
		StringBuilder sbHtml = new StringBuilder( this.createHtml(title));

		sbHtml.append("<style type=\"text/css\">\n");
		sbHtml.append("body {\n");
		sbHtml.append("	background-color: rgb(38, 38, 38);\n");
		sbHtml.append("	overflow: hidden;\n");
		sbHtml.append("}\n");
		sbHtml.append("\n");
		sbHtml.append("#preview {\n");
		sbHtml.append("	width: 100%;\n");
		sbHtml.append("	height: 100%;\n");
		sbHtml.append("	background-size: contain;\n");
		sbHtml.append("	background-repeat: no-repeat;\n");
		sbHtml.append("	background-position: center;\n");
		sbHtml.append("	cursor: move;\n");
		sbHtml.append("	position: absolute;\n");
		sbHtml.append("	left: 0;\n");
		sbHtml.append("	top: 0;\n");
		sbHtml.append("}\n");
		sbHtml.append("\n");
		sbHtml.append("#controls {\n");
		sbHtml.append("	position: fixed;\n");
		sbHtml.append("	right: 40px;\n");
		sbHtml.append("	bottom: 40px;\n");
		sbHtml.append("	width: 40px;\n");
		sbHtml.append("	z-index: 999999;\n");
		sbHtml.append("}\n");
		sbHtml.append("\n");
		sbHtml.append("#controls div {\n");
		sbHtml.append("	width: 50px;\n");
		sbHtml.append("	height: 50px;\n");
		sbHtml.append("	border-radius: 100%;\n");
		sbHtml.append("	background-color: #f1f1f1;\n");
		sbHtml.append("	box-shadow: 1px 1px 11px #fff;\n");
		sbHtml.append("	line-height: 50px;\n");
		sbHtml.append("	text-align: center;\n");
		sbHtml.append("	font-size: 18px;\n");
		sbHtml.append("	margin-bottom: 10px;\n");
		sbHtml.append("	color: #999;\n");
		sbHtml.append("	cursor: pointer\n");
		sbHtml.append("}\n");
		sbHtml.append("\n");
		sbHtml.append("#controls div:hover {\n");
		sbHtml.append("	color: #000;\n");
		sbHtml.append("}\n");
		sbHtml.append("\n");
		sbHtml.append("#large_box {\n");
		sbHtml.append("	position: absolute;\n");
		sbHtml.append("	left: -9000px;\n");
		sbHtml.append("	top: -9000px;\n");
		sbHtml.append("	right: -9000px;\n");
		sbHtml.append("	bottom: -9000px;\n");
		sbHtml.append("	z-index: -1;\n");
		sbHtml.append("}\n");
		sbHtml.append("</style>\n");
		sbHtml.append("	<div id=\"large_box\"></div>\n");
		sbHtml.append("	<div id='preview' style='background-image:url(" + url + ")'></div>\n");
		sbHtml.append("\n");
		sbHtml.append("	<div id='controls'>\n");
		sbHtml.append("		<div id='download' class='fa fa-download' title='下载'></div>\n");
		sbHtml.append("		<div id='print' class='fa fa-print' title='打印'></div>\n");
		sbHtml.append("		<div id='resize' class='fa fa-th-large' title='还原'></div>\n");
		sbHtml.append("		<div id='plus' class='fa fa-plus' title='放大'></div>\n");
		sbHtml.append("		<div id='minus' class='fa fa-minus' title='缩小'></div>\n");
		sbHtml.append("		<div id='rotateLeft' class='fa fa-undo' title='左旋'></div>\n");
		sbHtml.append("		<div id='rotateRight' class='fa fa-undo fa-flip-horizontal' title='右旋'></div>\n");
		sbHtml.append("	</div>\n");
		sbHtml.append("	<script type=\"text/javascript\">\n");
		sbHtml.append("	\n");
		sbHtml.append("		$('#controls div').on(\"click\", function (e) {\n");
		sbHtml.append("			if(this.id=='resize'){\n");
		sbHtml.append("				$('#preview').attr('deg',0).attr('scale',1);\n");
		sbHtml.append("				$('#preview').css('top',0).css('left',0);\n");
		sbHtml.append("				show_transform();\n");
		sbHtml.append("			} else if (this.id == 'plus') {\n");
		sbHtml.append("				scale(0.1);\n");
		sbHtml.append("			} else if (this.id == 'minus') {\n");
		sbHtml.append("				scale(-0.1);\n");
		sbHtml.append("			} else if (this.id == 'rotateLeft') {\n");
		sbHtml.append("				rotate(-90);\n");
		sbHtml.append("			} else if (this.id == 'rotateRight') {\n");
		sbHtml.append("				rotate(90);\n");
		sbHtml.append("			} else if (this.id == 'download') {\n");
		sbHtml.append("				window.location.href=window.location.href+'&download=1';\n");
		sbHtml.append("			} else if (this.id == 'print') {\n");
		sbHtml.append("				let win=window.open(window.location.href+'&inline=1');\n");
		sbHtml.append("    			let t2 = 0;\n");
		sbHtml.append("    			let t = setInterval(() => {\n");
		sbHtml.append("        			if (t2++ > 1000) {\n");
		sbHtml.append("            			window.clearInterval(t);\n");
		sbHtml.append("            			return;\n");
		sbHtml.append("        			}\n");
		sbHtml.append("        			if (win.document && win.document.readyState == \"complete\") {\n");
		sbHtml.append("            			window.clearInterval(t);\n");
		sbHtml.append("            			win.print();\n");
		sbHtml.append("        			}\n");
		sbHtml.append("    			}, 100);\n");
		sbHtml.append("			}\n");
		sbHtml.append("		});\n");
		sbHtml.append("		function rotate(deg) {\n");
		sbHtml.append("			var deg_prev = $('#preview').attr('deg') || 0;\n");
		sbHtml.append("			var deg_new = deg_prev * 1 + deg;\n");
		sbHtml.append("			if (deg_new > 360) {\n");
		sbHtml.append("				deg_new -= 360;\n");
		sbHtml.append("			} else if (deg_new < -360) {\n");
		sbHtml.append("				deg_new += 360;\n");
		sbHtml.append("			}\n");
		sbHtml.append("			$('#preview').attr('deg', deg_new);\n");
		sbHtml.append("			show_transform();\n");
		sbHtml.append("		}\n");
		sbHtml.append("		function scale(scale, ismousewheel) {\n");
		sbHtml.append("			var scale_prev = $('#preview').attr('scale') || 1;\n");
		sbHtml.append("			scale_new = scale_prev * 1 + scale;\n");
		sbHtml.append("			if (scale_new > 3) {\n");
		sbHtml.append("				scale_new = 3;\n");
		sbHtml.append("			} else if (scale_new < 0.3) {\n");
		sbHtml.append("				scale_new = 0.3;\n");
		sbHtml.append("			}\n");
		sbHtml.append("			$('#preview').attr('scale', scale_new);\n");
		sbHtml.append("			show_transform(ismousewheel);\n");
		sbHtml.append("		}\n");
		sbHtml.append("		function show_transform(ismousewheel) {\n");
		sbHtml.append("			var scale = $('#preview').attr('scale') || 1;\n");
		sbHtml.append("			var deg = $('#preview').attr('deg') || 0;\n");
		sbHtml.append("\n");
		sbHtml.append("			var css={\n");
		sbHtml.append("				\"transition-duration\": ismousewheel?0:\"0.5s\",\n");
		sbHtml.append("				'transform': 'rotate(' + deg + 'deg) scale(' + scale + ')'\n");
		sbHtml.append("			}\n");
		sbHtml.append("			$('#preview').css(css);\n");
		sbHtml.append("			setTimeout(function(){\n");
		sbHtml.append("				$('#preview').css(\"transition-duration\",\"\");\n");
		sbHtml.append("			},510)\n");
		sbHtml.append("		}\n");
		sbHtml.append("		(function(){\n");
		sbHtml.append("			document.body.onmousewheel=function(e){\n");
		sbHtml.append("				if(e.deltaY<0){ //向下\n");
		sbHtml.append("					scale(0.1,true);\n");
		sbHtml.append("				}	else {\n");
		sbHtml.append("					scale(-0.1,true);\n");
		sbHtml.append("				}\n");
		sbHtml.append("			};\n");
		sbHtml.append("	        window.mv2 = new EWA.UI.Move(),\n");
		sbHtml.append("	        mv2.Init(mv2);\n");
		sbHtml.append("	        var img = $X('preview');\n");
		sbHtml.append("	        var p = $X('large_box');\n");
		sbHtml.append("	        mv2.AddMoveObject(img, img, function() {\n");
		sbHtml.append("	        }, p);\n");
		sbHtml.append("		})();\n");
		sbHtml.append("	</script>\n");

//		sbHtml.append("<div class='oa-doc-view EWA_TABLE' id='doc-view'>");
//		sbHtml.append("<div class='file-view-image' style='text-align:center'>");
//		sbHtml.append("<img style='max-width:100%;max-height:100%' src='" + url + "'>");
//		sbHtml.append("</div></div>");

		sbHtml.append(createFooterHtml());
		return sbHtml.toString();
	}

	private String createFooterHtml() {
		StringBuilder sbHtml = new StringBuilder();
		if (!this.skipHeader) {
			sbHtml.append("</div>");
		}
		if (this.getHtmlBottom() != null) {
			sbHtml.append(this.getHtmlBottom());
		}
		if (!this.skipHeader) {
			sbHtml.append("</body></html>");
		}
		

		return sbHtml.toString();
	}
	

	public String viewVideo(String title, String url, boolean skipHeader) {
		StringBuilder sbHtml = new StringBuilder(  this.createHtml(title));
		sbHtml.append("<div class='file-view-vod EWA_TABLE' id='doc-view'>");
		sbHtml.append("<div style='text-align:center'><video src=\"" + url + "\" controls=\"controls\"></video></div>");
		sbHtml.append("</div>");
		sbHtml.append(createFooterHtml());
		return sbHtml.toString();
	}

	public String viewAudio(String title, String url, boolean skipHeader) {
		StringBuilder sbHtml = new StringBuilder(  this.createHtml(title));
		sbHtml.append("<div class='file-view-audio EWA_TABLE' id='audio-view'>");
		sbHtml.append("<div style='text-align:center'><audio src=\"" + url + "\" controls=\"controls\"></audio></div>");
		sbHtml.append("</div>");
		sbHtml.append(createFooterHtml());
		return sbHtml.toString();
	}

	public String viewPdf(String title, String url, boolean skipHeader) {
		StringBuilder sbHtml = new StringBuilder( this.createHtml(title));
		String id = "pdf_" + System.currentTimeMillis();

		sbHtml.append("<div id='" + id + "' style='height:100%; overflow:hidden'>");
		// chrome edge safari
		String embed = "<embed src=\"" + url + "\" class=\"pdfobject\" type=\"application/pdf\" title=\""
				+ Utils.textToInputValue(title) + "\" style=\"overflow: auto; width: 100%; height: 100%;\">";
		// firefox pdf.js viewer.html?file=xxx.pdf
		String u = this.pdfJs + (this.pdfJs.indexOf("?") > 0 ? "&" : "?") + "file=" + Utils.textToUrl(url);
		String pdfJs = "<iframe id=\"fra_pdf\" height=\"100%\" frameborder=\"0\" width=\"100%\" src=\"" + u
				+ "\"></iframe>";
		sbHtml.append("<script>(function(){");

		if (forceUsingPdfJs) {
			sbHtml.append("var s='" + pdfJs + "';document.getElementById('" + id + "').innerHTML = s; ");
		} else {
			// 根据 navigator.pdfViewerEnabled 进行输出
			sbHtml.append("var s=navigator.mimeTypes['application/pdf']||navigator.pdfViewerEnabled?'" + embed + "':'"
					+ pdfJs + "';document.getElementById('" + id + "').innerHTML = s; ");
		}

		sbHtml.append("})();</script>");
		sbHtml.append(createFooterHtml());
		return sbHtml.toString();
	}

	/**
	 * 获取默认的EWA的js和css<br>
	 * font-awesome.css<br>
	 * ewa.min.js<br>
	 * jquery-3.6.0.min.js<br>
	 * skins/default/css.css，通过includeDefaultCss来指定是否出现<br>
	 * 
	 * @param includeDefaultCss 是否包含默认 skins/default/css.css
	 * @return
	 */
	public static String getEwaJsAndCss(boolean includeDefaultCss) {
		StringBuilder sb = new StringBuilder();
		// EmpScriptV2 静态文件默认目录
		String emp = UPath.getEmpScriptV2Path();

		if (includeDefaultCss) {
			// ewa css
			sb.append("<link rel=\"stylesheet\" href='");
			sb.append(emp);
			sb.append("/EWA_STYLE/skins/default/css.css' type='text/css'>\n");
		}
		// fontawesome css
		sb.append("<link rel=\"stylesheet\" href='");
		sb.append(emp);
		sb.append("/third-party/font-awesome/font-awesome-4.7.0/css/font-awesome.min.css' type='text/css'>\n");

		// ewa.min.js
		sb.append("<script type=\"text/javascript\" src='");
		sb.append(emp);
		sb.append("/EWA_STYLE/js/ewa.min.js'></script>\n");

		// jq
		sb.append("<script type=\"text/javascript\" src='");
		sb.append(emp);
		sb.append("/third-party/jquery/jquery-3.6.0.min.js'></script>\n");

		return sb.toString();
	}

	public String createHtml(String title) {
		StringBuilder sb = new StringBuilder();
		if(this.skipHeader) {
			if (this.htmlTop != null) {
				sb.append(this.htmlTop);
			}
			
			return sb.toString();
		}
		
		sb.append("<!DOCTYPE HTML>\n");
		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\n");
		sb.append("<meta charset=\"UTF-8\">\n");

		// EmpScriptV2 静态文件默认目录
		String jsCss = HttpFileViewBase.getEwaJsAndCss(true);

		sb.append(jsCss);

		sb.append("<title>" + Utils.textToInputValue(title) + "</title>\n");

		sb.append("<style>\n");
		sb.append("html{\n");
		sb.append("	width:100%;\n");
		sb.append("	height:100%;\n");
		sb.append("}\n");
		sb.append("body {\n");
		sb.append("    min-width: 320px;\n");
		sb.append("    height: 100vh;\n");
		sb.append("    margin: 0;\n");
		sb.append("    padding: 0;\n");
		sb.append("    overflow: auto;\n");
		sb.append("    position: relative;\n");
		sb.append("}\n");
		sb.append("	.header-wrap{\n");
		sb.append("		text-align:center;\n");
		sb.append("		padding:15px 0;\n");
		sb.append("		border-bottom:1px solid #ccc;\n");
		sb.append("		background-color:#fff;\n");
		sb.append("	}\n");
		sb.append("	.header-wrap a{\n");
		sb.append("		color:#08c;\n");
		sb.append("		font-size:16px;\n");
		sb.append("		margin:0 15px;\n");
		sb.append("	}\n");
		sb.append("	.header-wrap a:hover{\n");
		sb.append("		color:#f60;\n");
		sb.append("	}\n");
		sb.append("	.container{\n");
		sb.append("		background-color:#fff;\n");
		sb.append("	}	\n");
		sb.append("	.EWA_TABLE{\n");
		sb.append("		background-color:#fff;\n");
		sb.append("	}\n");
		sb.append("</style>\n");
		sb.append("<body>\n");
		if (this.htmlTop != null) {
			sb.append(this.htmlTop);
		}
		sb.append("<div style='height:100%' class=\"container\">");
		return sb.toString();
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getPdfJs() {
		return pdfJs;
	}

	public void setPdfJs(String pdfJs) {
		this.pdfJs = pdfJs;
	}

	/**
	 * @return the rv
	 */
	public RequestValue getRv() {
		return rv;
	}

	/**
	 * @param rv the rv to set
	 */
	public void setRv(RequestValue rv) {
		this.rv = rv;
	}

	/**
	 * @return the skipHeader
	 */
	public boolean isSkipHeader() {
		return skipHeader;
	}

	/**
	 * @param skipHeader the skipHeader to set
	 */
	public void setSkipHeader(boolean skipHeader) {
		this.skipHeader = skipHeader;
	}

	/**
	 * @return the en
	 */
	public boolean isEn() {
		return en;
	}

	/**
	 * @param en the en to set
	 */
	public void setEn(boolean en) {
		this.en = en;
	}

	/**
	 * 缓存时间 单位秒
	 * 
	 * @return the cacheLife
	 */
	public int getCacheLife() {
		return cacheLife;
	}

	/**
	 * 缓存时间 单位秒
	 * 
	 * @param cacheLife the cacheLife to set
	 */
	public void setCacheLife(int cacheLife) {
		this.cacheLife = cacheLife;
	}

	/**
	 * @return the small
	 */
	public boolean isSmall() {
		return small;
	}

	/**
	 * @param small the small to set
	 */
	public void setSmall(boolean small) {
		this.small = small;
	}

	/**
	 * @return the download
	 */
	public boolean isDownload() {
		return download;
	}

	/**
	 * @param download the download to set
	 */
	public void setDownload(boolean download) {
		this.download = download;
	}

	/**
	 * @return the inline
	 */
	public boolean isInline() {
		return inline;
	}

	/**
	 * @param inline the inline to set
	 */
	public void setInline(boolean inline) {
		this.inline = inline;
	}

	/**
	 * @return the supportAvif
	 */
	public boolean isSupportAvif() {
		return supportAvif;
	}

	/**
	 * @param supportAvif the supportAvif to set
	 */
	public void setSupportAvif(boolean supportAvif) {
		this.supportAvif = supportAvif;
	}

	/**
	 * @return the supportWebp
	 */
	public boolean isSupportWebp() {
		return supportWebp;
	}

	/**
	 * @param supportWebp the supportWebp to set
	 */
	public void setSupportWebp(boolean supportWebp) {
		this.supportWebp = supportWebp;
	}

	/**
	 * @return the supportHeic
	 */
	public boolean isSupportHeic() {
		return supportHeic;
	}

	/**
	 * @param supportHeic the supportHeic to set
	 */
	public void setSupportHeic(boolean supportHeic) {
		this.supportHeic = supportHeic;
	}

	/**
	 * 强制使用pdf.js查看pdf文件
	 * 
	 * @return
	 */
	public boolean isForceUsingPdfJs() {
		return forceUsingPdfJs;
	}

	/**
	 * 强制使用pdf.js查看pdf文件
	 * 
	 * @param forceUsingPdfJs
	 */
	public void setForceUsingPdfJs(boolean forceUsingPdfJs) {
		this.forceUsingPdfJs = forceUsingPdfJs;
	}

	/**
	 * 附加地页面头部在&lt;body&gt;后面
	 * 
	 * @return
	 */
	public String getHtmlTop() {
		return htmlTop;
	}

	/**
	 * 附加地页面头部在&lt;body&gt;后面
	 * 
	 * @param htmlTop
	 */
	public void setHtmlTop(String htmlTop) {
		this.htmlTop = htmlTop;
	}

	/**
	 * 附加的尾部在&lt;/body&gt;上面
	 * 
	 * @return
	 */
	public String getHtmlBottom() {
		return htmlBottom;
	}

	/**
	 * 附加的尾部在&lt;/body&gt;上面
	 * 
	 * @param htmlBottom
	 */
	public void setHtmlBottom(String htmlBottom) {
		this.htmlBottom = htmlBottom;
	}
}
