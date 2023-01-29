package com.gdxsoft.web.http;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.web.acl.Login;

/**
 * 下载或在线查看SYS_ATTS文件<br>
 * 在调用程序里在requst.setAttribute(\"pdfjs\",
 * \"/xxx/pdfjs-1.10.100-dist/web/viewer.html\")
 */
public class HttpOaSysAttView extends HttpFileViewBase implements IHttp {
	private static Logger LOGGER = LoggerFactory.getLogger(HttpOaSysAttView.class);

	public HttpOaSysAttView(String pdfJs) {
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
		 
		StringBuilder sb = new StringBuilder();
		if (rv.s("db") != null && rv.s("db").trim().length() > 0) {
			String db = rv.s("db");
			if (db.indexOf("'") >= 0 || db.indexOf(" ") >= 0) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return "ha ha ha";
			}
			sb.append("SELECT * from " + db + ". sys_atts WHERE 1=1 \n");
		} else {
			sb.append("SELECT * from sys_atts WHERE 1=1 \n");
		}

		if (StringUtils.isNotBlank(rv.s("file_id"))) {
			sb.append(" AND file_id = @file_id ");
		}

		if (StringUtils.isNotBlank(rv.s("File_UnId"))) {
			// unid不验证身份了
			// 下载暂时不进行安全效验，但要修改
			sb.append(" AND file_UNID = @File_UnId");
		}

		DTTable tb = DTTable.getJdbcTable(sb.toString(), "", rv);
		if (tb.getCount() == 0) {
			return HttpFileViewBase.msgRecordNotExists(super.isEn(), super.isSkipHeader());
		}
		// 是否为公开文件
		boolean ispublicFile = "COM_YES".equalsIgnoreCase(tb.getCell(0, "file_para1").toString());
		if (!ispublicFile) {
			boolean right = false;
			if (Login.isSupplyLogined(rv)) {
				int supId = Login.getLoginedSupId(rv);
				int fileSupId = tb.getCell(0, "sup_id").toInt();
				if (supId == fileSupId) { // 登录的sup_id和文件的sup_id一致
					right = true;
				}
				if (!right) {
					return HttpFileViewBase.msgNoRightWithSup(super.isEn(), super.isSkipHeader());
				}
			} else {
				return HttpFileViewBase.msgNeedLogin(super.isEn(), super.isSkipHeader());
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

		// 检查物理文件是否存在
		String phy = url.replace(UPath.getPATH_UPLOAD_URL(), "").replace("//", "/");
		String f1 = UPath.getPATH_UPLOAD() + phy;
		File file = new File(f1);
		f1 = file.getAbsolutePath();

		if (!file.exists()) {
			return HttpFileViewBase.msgPhyFileNotExists(super.isEn(), super.isSkipHeader());
		}

		//总是允许下载文件
		boolean isDl = true;

		String id = tb.getCell(0, "file_id").toString();
		String ifNoneMatch = "W/sysatts" + id;
		try {
			return super.handleFile(file, ext, title, isDl, ifNoneMatch);
		} catch (Exception err) {
			LOGGER.error(err.getMessage());
			return err.getLocalizedMessage();
		}
	}

}
