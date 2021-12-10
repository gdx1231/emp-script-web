package com.gdxsoft.web.http;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.script.servlets.FileOut;
import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.easyweb.utils.UUrl;
import com.gdxsoft.web.acl.Login;

/**
 * 下载或在线查看oa文件<br>
 * 在调用程序里在requst.setAttribute(\"pdfjs\", \"/xxx/pdfjs-1.10.100-dist/web/viewer.html\")
 */
public class HttpOaSysAttView extends HttpOaFileView implements IHttp {
	private static Logger LOGGER = LoggerFactory.getLogger(HttpOaSysAttView.class);

	public HttpOaSysAttView(String pdfJs) {
		super(pdfJs);
	}

	@Override
	public String response(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");

		RequestValue rv = new RequestValue(request);
		String unid = rv.s("File_UnId") == null ? "" : rv.s("File_UnId").replace("'", "").replace(" ", "");

		StringBuilder sb = new StringBuilder();
		if (rv.s("db") != null) {
			sb.append("SELECT * from ~db. sys_atts WHERE file_id=@file_id \n");
		} else {
			sb.append("SELECT * from sys_atts WHERE file_id=@file_id \n");
		}
		if (unid.length() > 0) {
			// unid不验证身份了
			// 下载暂时不进行安全效验，但要修改
			sb.append(" AND file_UNID = @File_UnId");
		}

		DTTable tb = DTTable.getJdbcTable(sb.toString(), "", rv);
		if (tb.getCount() == 0) {
			return ("<html><body style='background-color:#fff'><div><div class='tip' bgcolor='white'>"
					+ "文件呢？去哪遛弯拉(数据不存在)</div></div></body></html>");
		}
		// 是否为公开文件
		boolean ispublicFile = "COM_YES".equalsIgnoreCase(tb.getCell(0, "file_para1").toString());
		if (!ispublicFile) {
			boolean right = false;
			if (Login.isSupplyLogined(rv)) {
				int supId = Login.getLoginedSupId(rv);
				int fileSupId = tb.getCell(0, "sup_id").toInt();
				if (supId == fileSupId) { //登录的sup_id和文件的sup_id一致
					right = true;
				}
				if (!right) {
					return ("<html><body style='background-color:#fff'><div><div class='tip' bgcolor='white'>"
							+ "此文件您无权查看或下载，商户不一致</div></div></body></html>");
				}
			} else {
				return ("<html><body style='background-color:#fff'><div><div class='tip' bgcolor='white'>"
						+ "您需要登录商户系统后查看或下载</div></div></body></html>");
			}
			
		}
		String url = tb.getCell(0, "file_path").toString();
		String ext = tb.getCell(0, "file_ext").toString();
		if (ext == null) {
			ext = "bin";
		}

		String title = tb.getCell(0, "file_name").toString();
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

		boolean isDl = true;
		if (rv.s("download") != null) {
			return this.downloadFile(isDl, file, title, request, response);
		} else if (rv.s("inline") != null) {
			if (rv.s("pdf") != null) { // 查看文档转换的pdf文件
				file = new File(file.getAbsolutePath() + ".pdf");
			}
			return this.inlineFile(isDl, file, title, request, response);
		}

		String id = tb.getCell(0, "file_id").toString();
		String IfNoneMatch = request.getHeader("If-None-Match");
		if (IfNoneMatch != null) {
			if (("W/sysatts" + id).equals(IfNoneMatch)) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				return null;
			}
		}

		boolean skipHeader = rv.s("EWA_APP") != null || rv.s("EWA_AJAX") != null;
		UUrl uu = new UUrl(request);
		uu.add("inline", "1");
		url = uu.getUrl(true);

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

}
