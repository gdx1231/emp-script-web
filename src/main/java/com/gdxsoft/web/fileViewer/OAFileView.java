package com.gdxsoft.web.fileViewer;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.web.acl.Login;
import com.gdxsoft.web.http.HttpFileViewBase;
import com.gdxsoft.web.http.HttpOaSysAttView;
import com.gdxsoft.web.http.IHttp;

public class OAFileView extends HttpFileViewBase implements IHttp {
	private static Logger LOGGER = LoggerFactory.getLogger(HttpOaSysAttView.class);

	private BinToPhy bp;

	public OAFileView(String pdfJs, URL tableBinXmlFilePath) {
		super.setPdfJs(pdfJs);
		this.bp = new BinToPhy(tableBinXmlFilePath);
	}

	@Override
	public String response(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		RequestValue rv = new RequestValue(request);
		super.setRequest(request);
		super.setResponse(response);
		super.setRv(rv);

		super.initParameters();

		String tbName = rv.s("tb");
		if (tbName == null) {
			return "tb?";
		}

		bp.init();
		Element item = bp.getCfg(tbName);
		if (item == null) {
			return HttpFileViewBase.msgAppendHtmlHead(isEn() ? "Unkonw method" : "未知模式", isSkipHeader());
		}

		String tableName = item.getAttribute("name"); // 表名
		String keyField = item.getAttribute("keyf"); // 主键
		String fileField = item.getAttribute("filef"); // 物理文件字段
		String extField = item.getAttribute("extf"); // 扩展名字段
		// String lenField = item.getAttribute("lenf"); // 扩展名字段
		String md5Field = item.getAttribute("md5f"); // md5扩展名
		String supField = item.getAttribute("supf"); // sup_id字段
		String cached = item.getAttribute("cached"); // 缓存时间
		String titleField = item.getAttribute("titlef"); // 文件名

		boolean is_skip_check = this.skipCheck(tbName);

		if (!is_skip_check && !Login.isSupplyLogined(super.getRv())) {
			if (isSmall()) {
				response.sendRedirect(UPath.getEmpScriptV2Path() + "/EWA_STYLE/images/pic_no.jpg");
			} else {
				// 去登录
				super.getResponse().sendRedirect(Login.gotoLoginSupply(super.getRv()));
			}
			return null;
		}

		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select * from ");
		sbSql.append(tableName);
		sbSql.append(" where ");

		String[] keyFields = keyField.split(",");
		for (int i = 0; i < keyFields.length; i++) {
			String f1 = keyFields[i].trim();
			if (i > 0) {
				sbSql.append(" AND ");
			}
			sbSql.append(f1);
			sbSql.append(" = @");
			sbSql.append(f1);
		}

		if ("sys_atts".equals(tableName)) {
			if (!super.getRv().isBlank("unid")) {
				sbSql.append(" AND file_unid=@unid");
			}
			if (!super.getRv().isBlank("md5")) {
				sbSql.append(" AND file_md5=@md5");
			}
			if (!super.getRv().isBlank("f")) {
				sbSql.append(" AND file_from=@f");
			}
			if (!super.getRv().isBlank("para0")) {
				sbSql.append(" AND file_para0=@para0");
			}
			if (!super.getRv().isBlank("para1")) {
				sbSql.append(" AND file_para1=@para1");
			}
			if (!super.getRv().isBlank("para2")) {
				sbSql.append(" AND file_para2=@para2");
			}
		}

		String sql = sbSql.toString();
		if (!tbName.equalsIgnoreCase("lbs_photos") && supField != null && supField.trim().length() > 0) {
			if (!is_skip_check) {
				sql += " and " + supField + " = @g_sup_id";
			}
		}
		DTTable tb = DTTable.getJdbcTable(sql, "", rv);

		// 记录是否存在
		if (!tb.isOk() || tb.getCount() == 0) {
			if (!tb.isOk()) {
				LOGGER.error(sql);
			}
			if (isSmall()) {
				response.sendRedirect(UPath.getEmpScriptV2Path() + "/EWA_STYLE/images/pic_no.jpg");
				return null;
			}
			return HttpFileViewBase.msgRecordNotExists(super.isEn(), super.isSkipHeader());
		}

		// 物理文件地址
		String phy = tb.getCell(0, fileField).toString();
		if (phy == null || phy.trim().length() == 0) {
			if (isSmall()) {
				response.sendRedirect(UPath.getEmpScriptV2Path() + "/EWA_STYLE/images/pic_no.jpg");
				return null;
			}
			return HttpFileViewBase.msgPhyFileNotExists(super.isEn(), super.isSkipHeader());
		}

		// 物理文件是否存在
		File file = this.getPhyFilePath(phy);
		if (file == null) {
			if (isSmall()) {
				response.sendRedirect(UPath.getEmpScriptV2Path() + "/EWA_STYLE/images/pic_no.jpg");
				return null;
			}
			return HttpFileViewBase.msgPhyFileNotExists(super.isEn(), super.isSkipHeader());
		}

		int cache_life = 30000;// 30s
		if (cached != null && cached.trim().length() > 0) {
			try {
				cache_life = Integer.parseInt(cached);
			} catch (Exception err) {
				cache_life = 30000;
			}
		}
		// 缓存时间 单位秒
		super.setCacheLife(cache_life);

		String ext = tb.getCell(0, extField).toString();
		if (ext == null) {
			ext = "bin";
		}
		String key = "";
		for (int i = 0; i < keyFields.length; i++) {
			String f1 = keyFields[i].trim();
			if (i > 0) {
				key += "_";
			}
			key += tb.getCell(0, f1).toString();
		}

		String md5 = null;
		if (md5Field != null && md5Field.trim().length() > 0) {
			md5 = tb.getCell(0, md5Field).toString();
			if (md5 != null && md5.trim().length() == 0) {
				md5 = null;
			}
		}
		String ifNoneMatch = md5 == null ? null : "W/" + md5;

		String name; // 显示或下载的文件名
		String ext1 = "." + ext.trim().toLowerCase();
		if (super.getRv().isNotBlank("download_name")) { // 制定了下载文件名称
			name = super.getRv().s("download_name");
			if (!name.toLowerCase().endsWith(ext1)) {
				name = name + "." + ext;
			} else if (name.equalsIgnoreCase(ext1)) {
				name = "未命名." + ext;
			}
		} else {
			if (titleField != null && titleField.trim().length() > 0) {
				name = tb.getCell(0, titleField).toString();
				if (ext != null) {
					if (!name.toLowerCase().endsWith("." + ext.toLowerCase())) {
						name = name + ext1;
					}
				}
			} else {
				name = tbName + "_" + key;
				if (ext != null) {
					name = name + ext1;
				}
			}
		}
		return super.handleFile(file, ext, name, true, ifNoneMatch);

	}

	/**
	 * 获取物理文件
	 * 
	 * @param path
	 * @return
	 */
	private File getPhyFilePath(String path) {
		File f = new File(path);
		if (f.exists()) {
			return f;
		}

		String pathUpload;
		if (UPath.getPATH_UPLOAD().endsWith("\\/")) {
			pathUpload = UPath.getPATH_UPLOAD().substring(0, UPath.getPATH_UPLOAD().length() - 2);
		} else {
			pathUpload = UPath.getPATH_UPLOAD();
		}
		if (path.startsWith(UPath.getPATH_UPLOAD_URL())) {
			f = new File(pathUpload + "/" + path.replace(UPath.getPATH_UPLOAD_URL(), ""));
			if (f.exists()) {
				return f;
			}
		}
		f = new File(pathUpload + path);
		if (f.exists()) {
			return f;
		}
		return null;
	}

	private boolean skipCheck(String tbName) throws IOException {
		if (tbName.equalsIgnoreCase("lbs_photos")) {
			return true;
		}

		// 投资人报告
		if (tbName.equalsIgnoreCase("sys_atts") && "oa_req.ambers.report".equalsIgnoreCase(super.getRv().s("f"))
				&& "LP_REPORT_STEP_C".equalsIgnoreCase(super.getRv().s("para0")) && !super.getRv().isBlank("unid")) {
			return true;
		}

		boolean is_skip_check = false;
		String referer = super.getRv().s("SYS_REMOTE_REFERER");
		if (referer == null)
			referer = "";
		super.getResponse().setHeader("X-R", referer);
		if ((tbName.equalsIgnoreCase("VIS_TAB_MAIN.vimg") && referer.indexOf("/app-2017/") > 0)
				|| (tbName.equalsIgnoreCase("GRP_FIN_MAIN") && referer.indexOf("/app-2017/") > 0)) {
			// 来源 app2017 不用检查管理员登录
			super.getResponse().setHeader("X-R1", "2017");
			is_skip_check = true;
		}

		return is_skip_check;
	}

}
