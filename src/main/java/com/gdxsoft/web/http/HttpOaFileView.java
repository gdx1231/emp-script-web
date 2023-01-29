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
import com.gdxsoft.easyweb.utils.fileConvert.File2Pdf;

/**
 * 下载或在线查看oa文件<br>
 * 在调用程序里在requst.setAttribute(\"pdfjs\",
 * \"/xxx/pdfjs-1.10.100-dist/web/viewer.html\")
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
		 * App app = new App(rv); boolean isPc = !(app.isAndroid() || app.isIphone() ||
		 * app.isIpad());
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

		String oafUrl = tb.getCell(0, "OAF_URL").toString();
		String ext = tb.getCell(0, "OAF_EXT").toString();
		if (ext == null) {
			ext = "bin";
		}

		String title = tb.getCell(0, "OAF_NAME").toString();
		if (!title.toLowerCase().endsWith("." + ext.toLowerCase())) {
			title += "." + ext;
		}

		String phy = oafUrl.replace(UPath.getPATH_UPLOAD_URL(), "").replace("//", "/");
		String f1 = UPath.getPATH_UPLOAD() + phy;
		File file = new File(f1);
		f1 = file.getAbsolutePath();

		if (!file.exists()) {
			return ("<html><body style='background-color:#fff'><div><div class='tip' bgcolor='white'>"
					+ "文件呢？去哪遛弯拉（物理文件缺失）</div></div></body></html>");
		}

		boolean isDl = "COM_YES".equalsIgnoreCase(tb.getCell(0, "OAF_IS_DL").toString());
		if (rv.s("download") != null) {
			return this.downloadFile(isDl, file, title, request, response);
		}
		if (rv.s("inline") != null) {

			if (rv.s("pdf") != null) { // 查看文档转换的pdf文件
				file = new File(file.getAbsolutePath() + ".pdf");
			}

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
		String url = uu.getUrl(true);

		String fileType = FileOut.MAP.getOrDefault(ext.toLowerCase().trim(), "");
		if (ext.trim().equalsIgnoreCase("doc") || ext.trim().equalsIgnoreCase("docx")
				|| ext.trim().equalsIgnoreCase("rtf") || ext.trim().equalsIgnoreCase("xls")
				|| ext.trim().equalsIgnoreCase("xlsx") || ext.trim().equalsIgnoreCase("ppt")
				|| ext.trim().equalsIgnoreCase("pptx")) {
			try {
				// return this.viewDocument(file, ext, title, url, skipHeader);
				return this.viewDocumentAsPdf(file, ext, title, url, skipHeader);
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
	public String downloadFile(boolean isDl, File file, String fileName, HttpServletRequest request,
			HttpServletResponse response) {
		if (isDl) {
			FileOut fo = new FileOut(request, response);
			fo.initFile(file);
			fo.download(fileName);
			return null;
		} else {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return "<html><body style='background-color:#fff'><div><div bgcolor='white'>不允许下载</div></div></body></html>";
		}
	}

	/**
	 * 在线输出文件二进制
	 * 
	 * @param isDl
	 * @param file
	 * @param fileName
	 * @param request
	 * @param response
	 * @return
	 */
	public String inlineFile(boolean isDl, File file, String fileName, HttpServletRequest request,
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

	public String viewImage(String title, String url, boolean skipHeader) {
		StringBuilder sbHtml = new StringBuilder(skipHeader ? "" : this.createHtml(title));

		sbHtml.append(
				"<link rel=\"stylesheet\" href=\"/EmpScriptV2/EWA_STYLE/skins/default/css.css\" type=\"text/css\" />\n");
		sbHtml.append(
				"<link rel=\"stylesheet\" href=\"/EmpScriptV2/third-party/font-awesome/font-awesome-4.7.0/css/font-awesome.min.css\" type=\"text/css\" />\n");
		sbHtml.append("<script type=\"text/javascript\" src=\"/EmpScriptV2/EWA_STYLE/js/ewa.min.js\"></script>\n");
		sbHtml.append(
				"<script type=\"text/javascript\" src='/EmpScriptV2/third-party/jquery/jquery-3.6.0.min.js'></script>\n");
		sbHtml.append("<script type=\"text/javascript\">EWA.RV_STATIC_PATH='/EmpScriptV2/';</script>\n");
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
		sbHtml.append("		<div id='resize' class='fa fa-th-large'></div>\n");
		sbHtml.append("		<div id='plus' class='fa fa-plus'></div>\n");
		sbHtml.append("		<div id='minus' class='fa fa-minus'></div>\n");
		sbHtml.append("		<div id='rotateLeft' class='fa fa-undo'></div>\n");
		sbHtml.append("		<div id='rotateRight' class='fa fa-undo fa-flip-horizontal'></div>\n");
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
		sbHtml.append(skipHeader ? "" : "</div></body></html>");
		return sbHtml.toString();
	}

	public String viewVideo(String title, String url, boolean skipHeader) {
		StringBuilder sbHtml = new StringBuilder(skipHeader ? "" : this.createHtml(title));
		sbHtml.append("<div class='file-view-vod EWA_TABLE' id='doc-view'>");
		sbHtml.append("<div style='text-align:center'><video src=\"" + url + "\" controls=\"controls\"></video></div>");
		sbHtml.append("</div>");
		sbHtml.append(skipHeader ? "" : "</div></body></html>");
		return sbHtml.toString();
	}

	public String viewPdf(String title, String url, boolean skipHeader) {
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

	public String createHtml(String title) {
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
