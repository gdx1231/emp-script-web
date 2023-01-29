package com.gdxsoft.web.http;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UPath;

/**
 * 下载或在线查看oa文件<br>
 * 在调用程序里在requst.setAttribute(\"pdfjs\",
 * \"/xxx/pdfjs-1.10.100-dist/web/viewer.html\")
 */
public class HttpOaFileView extends HttpFileViewBase implements IHttp {
	private static Logger LOGGER = LoggerFactory.getLogger(HttpOaFileView.class);

	public HttpOaFileView(String pdfJs) {
		super.setPdfJs(pdfJs);
	}

	@Override
	public String response(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");

		RequestValue rv = new RequestValue(request);
		super.setRequest(request);
		super.setResponse(response);
		super.setRv(rv);

		super.initParameters();

		 
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
			// 无此记录
			return HttpFileViewBase.msgRecordNotExists(super.isEn(), super.isSkipHeader());
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
			// 物理文件不存在
			return HttpFileViewBase.msgPhyFileNotExists(super.isEn(), super.isSkipHeader());
		}

		// 是否允许下载文件
		boolean isDl = "COM_YES".equalsIgnoreCase(tb.getCell(0, "OAF_IS_DL").toString());

		String id = tb.getCell(0, "OAF_ID").toString();
		String ifNoneMatch = "W/OAF" + id;

		try {
			return super.handleFile(file, ext, title, isDl, ifNoneMatch);
		} catch (Exception err) {
			LOGGER.error(err.getMessage());
			return err.getLocalizedMessage();
		}
	}

}
