package com.gdxsoft.web.http;

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

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.script.servlets.FileOut;
import com.gdxsoft.easyweb.utils.UFile;
import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.easyweb.utils.UUrl;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.easyweb.utils.fileConvert.File2Html;

/**
 * 下载或在线查看oa文件<br>
 * 在调用程序里在requst.setAttribute(\"pdfjs\", \"/xxx/pdfjs-1.10.100-dist/web/viewer.html\")
 */
public class HttpOaFileView implements IHttp {
	private static Logger LOGGER = LoggerFactory.getLogger(HttpOaFileView.class);
	private String pdfJs;

	public HttpOaFileView(String pdfJs) {
		this.pdfJs = pdfJs;
	}

	@Override
	public String response(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");

		RequestValue rv = new RequestValue(request);

		/*
		 * App app = new App(rv); boolean isPc = !(app.isAndroid() || app.isIphone() || app.isIpad());
		 */
		String unid = rv.s("unid") == null ? "" : rv.s("unid").replace("'", "").replace(" ", "");

		// * OA 文件查看
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT OAF_ID, OAF_EXT, OAF_NAME,OAF_TYPE,OAF_IS_DL,OAF_IS_SHOW");
		sb.append("   , OAF_URL ,OAF_UNID  ,OAF_SIZE \n");
		sb.append("FROM OA_FILE WHERE 1=1 \n");
		// unid不验证身份了
		if (unid.length() > 0) {
			sb.append(" AND OAF_UNID=@UNID");
		} else {
			if (rv.s("DOWNLOAD") != null) {
				// 下载暂时不进行安全效验，但要修改
			} else {
				sb.append("	AND  SUP_ID = @G_SUP_ID");
			}

			if (rv.s("PDF_OAF_ID") == null || rv.s("PDF_OAF_ID").trim().length() == 0) {
				if (unid.length() > 0) {
					sb.append(" AND OAF_UNID=@UNID");
				} else {
					sb.append("	AND OAF_ID = @OAF_ID");
				}
			} else {
				sb.append("	AND OAF_ID = @PDF_OAF_ID");
			}
		}

		DTTable tb = DTTable.getJdbcTable(sb.toString(), "", rv);
		if (tb.getCount() == 0) {
			return ("<html><body style='background-color:#fff'><div><div class='tip' bgcolor='white'>"
					+ "文件呢？去哪遛弯拉(数据不存在)</div></div></body></html>");
		}

		String url = tb.getCell(0, "OAF_URL").toString();
		String ext = tb.getCell(0, "OAF_EXT").toString();
		if (ext == null) {
			ext = "bin";
		}

		String title = tb.getCell(0, "OAF_NAME").toString();
		if (!title.toLowerCase().endsWith("." + ext.toLowerCase())) {
			title += "." + ext;
		}

		String phy = url.replace(UPath.getPATH_UPLOAD_URL(), "").replace("//", "/");
		String f1 = UPath.getPATH_UPLOAD() + phy;
		File file = new File(f1);
		f1 = file.getAbsolutePath();

		if (!file.exists()) {
			return ("<html><body style='background-color:#fff'><div><div class='tip' bgcolor='white'>"
					+ "文件呢？去哪遛弯拉（物理文件缺失）</div></div></body></html>");
		}

		String isDl = tb.getCell(0, "OAF_IS_DL").isNull() ? "" : tb.getCell(0, "OAF_IS_DL").toString();
		if (rv.s("download") != null) {
			return this.downloadFile(isDl, file, title, request, response);
		}
		if (rv.s("inline") != null) {
			return this.inlineFile(isDl, file, title, request, response);
		}

		String id = tb.getCell(0, "OAF_ID").toString();
		String IfNoneMatch = request.getHeader("If-None-Match");
		if (IfNoneMatch != null) {
			if (("W/OAF" + id).equals(IfNoneMatch)) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				return null;
			}
		}

		boolean skipHeader = rv.s("EWA_APP") != null || rv.s("EWA_AJAX") != null;
		UUrl uu = new UUrl(request);
		uu.add("inline", "1");
		url = uu.getUrl(true);

		String fileType = FileOut.MAP.getOrDefault(ext, "");
		if (ext.trim().equalsIgnoreCase("doc") || ext.trim().equalsIgnoreCase("docx")
				|| ext.trim().equalsIgnoreCase("rtf") || ext.trim().equalsIgnoreCase("xls")
				|| ext.trim().equalsIgnoreCase("xlsx") || ext.trim().equalsIgnoreCase("ppt")
				|| ext.trim().equalsIgnoreCase("pptx")) {
			try {
				return this.viewDocument(file, ext, title, url, skipHeader);
			} catch (Exception e11) {
				LOGGER.error(e11.getLocalizedMessage());
				return (e11.getMessage());
			}
		} else if (fileType.indexOf("image/") == 0) {
			return this.viewImage(title, url, skipHeader);
		} else if (fileType.indexOf("video/") == 0) { // 视频
			return this.viewVideo(title, url, skipHeader);
		} else if (ext.trim().equalsIgnoreCase("pdf")) {
			return this.viewPdf(title, url, skipHeader);
		} else { // 下载文件
			return this.downloadFile(isDl, file, fileType, request, response);
		}
	}

	private String downloadFile(String isDl, File file, String fileName, HttpServletRequest request,
			HttpServletResponse response) {
		if (isDl.equals("COM_YES")) {
			FileOut fo = new FileOut(request, response);
			fo.initFile(file);
			fo.download(fileName);
			return null;
		} else {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return "<html><body style='background-color:#fff'><div><div bgcolor='white'>不允许下载</div></div></body></html>";
		}
	}

	private String inlineFile(String isDl, File file, String fileName, HttpServletRequest request,
			HttpServletResponse response) {
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
	private String viewDocument(File file, String ext, String title, String url, boolean skipHeader)
			throws IOException {
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

		if (ext.trim().equalsIgnoreCase("ppt") || ext.trim().equalsIgnoreCase("pptx")) {
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
		StringBuilder sbHtml = new StringBuilder(skipHeader ? "" : this.createHtml(title));
		sbHtml.append("<div class='oa-doc-view EWA_TABLE' id='doc-view' style='width:760px;margin:0 auto'>");
		sbHtml.append(html1);
		sbHtml.append("</div>");
		sbHtml.append(skipHeader ? "" : "</div></body></html>");
		return sbHtml.toString();
	}

	private String viewImage(String title, String url, boolean skipHeader) {
		StringBuilder sbHtml = new StringBuilder(skipHeader ? "" : this.createHtml(title));
		sbHtml.append("<div class='oa-doc-view EWA_TABLE' id='doc-view'>");
		sbHtml.append("<div class='file-view-ppt' style='text-align:center'><img src='" + url + "'></div>");
		sbHtml.append("</div>");
		sbHtml.append(skipHeader ? "" : "</div></body></html>");
		return sbHtml.toString();
	}

	private String viewVideo(String title, String url, boolean skipHeader) {
		StringBuilder sbHtml = new StringBuilder(skipHeader ? "" : this.createHtml(title));
		sbHtml.append("<div class='file-view-vod EWA_TABLE' id='doc-view'>");
		sbHtml.append("<div style='text-align:center'><video src=\"" + url + "\" controls=\"controls\"></video></div>");
		sbHtml.append("</div>");
		sbHtml.append(skipHeader ? "" : "</div></body></html>");
		return sbHtml.toString();
	}

	private String viewPdf(String title, String url, boolean skipHeader) {
		StringBuilder sbHtml = new StringBuilder(skipHeader ? "" : this.createHtml(title));
		String id = "pdf_" + System.currentTimeMillis();

		sbHtml.append("<div id='" + id + "' style='height:100%'>");
		// chrome edge safari
		String embed = "<embed src=\"" + url + "\" class=\"pdfobject\" type=\"application/pdf\" title=\""
				+ Utils.textToInputValue(title) + "\" style=\"overflow: auto; width: 100%; height: 100%;\">";
		// firefox
		String u = this.pdfJs + "?file=" + Utils.textToUrl(url);
		String pdfJs = "<iframe id=\"fra_pdf\" height=\"100%\" frameborder=\"0\" width=\"100%\" src=\"" + u
				+ "\"></iframe>";

		// 根据 navigator.pdfViewerEnabled 进行输出
		sbHtml.append("<script>(function(){ var s=navigator.pdfViewerEnabled?'" + embed + "':'" + pdfJs
				+ "';document.getElementById('" + id + "').innerHTML = s; })();</script>");
	
		sbHtml.append(skipHeader ? "" : "</div></body></html>");

		return sbHtml.toString();
	}

	private String createHtml(String title) {
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE HTML>\n");
		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\n");
		sb.append("<meta charset=\"UTF-8\">\n");
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
		sb.append("<body>\n<div style='height:100%' class=\"container\">");
		return sb.toString();
	}
}
