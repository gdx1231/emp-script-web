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
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.web.acl.Login;
import com.gdxsoft.web.http.HttpFileViewBase;
import com.gdxsoft.web.http.HttpOaSysAttView;
import com.gdxsoft.web.http.IHttp;

public class OAFileView extends HttpFileViewBase implements IHttp {
	private static Logger LOGGER = LoggerFactory.getLogger(HttpOaSysAttView.class);

	private BinToPhy bp;

	private boolean skipSupCheck; // 手动设定不验证sup_id

	private String phyFilePath; // 物理文件路径
	private String md5; // md5值
	private String ext; // 扩展名
	private String name; // 显示或下载的文件名
	private long fileSize; // 文件大小

	private boolean recordExists; // 记录是否存在
	private boolean fileExists; // 物理文件是否存在

	public OAFileView(String pdfJs, URL tableBinXmlFilePath) {
		super.setPdfJs(pdfJs);
		this.bp = new BinToPhy(tableBinXmlFilePath);
	}

	/**
	 * 创建查询的SQL语句
	 * 
	 * @param item
	 * @param skipCheck
	 * @return
	 */
	public String createSql(Element item, boolean skipCheck) {
		String tableName = item.getAttribute("name"); // 表名
		String keyField = item.getAttribute("keyf"); // 主键
		// String lenField = item.getAttribute("lenf"); // 扩展名字段
		String supField = item.getAttribute("supf"); // sup_id字段

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

		// 检查文件是否属于指定的商户，可指定多个字段，为或的关系，用“,"分割
		if (!skipCheck && supField != null && supField.trim().length() > 0) {
			String[] supFields = supField.split(",");
			StringBuilder sb1 = new StringBuilder();
			sb1.append("(");
			for (int i = 0; i < supFields.length; i++) {
				if (i > 0) {
					sb1.append(" or ");
				}
				sb1.append(supFields[i]).append(" = @g_sup_id");
			}
			sb1.append(")");
			sbSql.append(" and ").append(sb1);
		}

		return sbSql.toString();
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

		String sql = this.createSql(item, is_skip_check);
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

		this.recordExists = true;

		String keyField = item.getAttribute("keyf"); // 主键
		String fileField = item.getAttribute("filef"); // 物理文件字段
		String extField = item.getAttribute("extf"); // 扩展名字段
		// String lenField = item.getAttribute("lenf"); // 扩展名字段
		String md5Field = item.getAttribute("md5f"); // md5扩展名
		String cached = item.getAttribute("cached"); // 缓存时间
		String titleField = item.getAttribute("titlef"); // 文件名

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

		this.fileExists = true;

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
		String[] keyFields = keyField.split(",");
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
			if (super.getResize() != null) {
				// 缩放
				String exp = md5 + "," + super.getResize().height + "x" + super.getResize().width;
				md5 = Utils.md5(exp);
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
		this.ext = ext;
		this.md5 = md5;
		this.name = name;
		this.phyFilePath = file.getAbsolutePath();
		this.fileSize = file.length();

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
		f = new File(pathUpload + "/" + path);
		if (f.exists()) {
			return f;
		}
		LOGGER.error("找不到文件：path={}, pathUpload={}, fullpath={}", path, pathUpload, f.getAbsolutePath());
		return null;
	}

	/**
	 * 检查文件是否需要检查权限
	 * 
	 * @param tbName
	 * @return
	 * @throws IOException
	 */
	public boolean skipCheck(String tbName) throws IOException {
		if (this.skipSupCheck) { // 手动设定不验证sup_id
			return true;
		}
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

	/**
	 * 手动设定不验证sup_id
	 * 
	 * @return
	 */
	public boolean isSkipSupCheck() {
		return skipSupCheck;
	}

	/**
	 * 手动设定不验证sup_id
	 * 
	 * @param skipSupCheck
	 */
	public void setSkipSupCheck(boolean skipSupCheck) {
		this.skipSupCheck = skipSupCheck;
	}

	/**
	 * 获取物理文件路径
	 * 
	 * @return the phyFilePath
	 */
	public String getPhyFilePath() {
		return phyFilePath;
	}

	/**
	 * 获取md5值
	 * 
	 * @return the md5
	 */
	public String getMd5() {
		return md5;
	}

	/**
	 * 获取扩展名
	 * 
	 * @return the ext
	 */
	public String getExt() {
		return ext;
	}

	/**
	 * 获取显示或下载的文件名
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获取文件大小
	 * 
	 * @return the fileSize
	 */
	public long getFileSize() {
		return fileSize;
	}

	/**
	 * 检查记录是否存在
	 * 
	 * @return
	 */
	public boolean isRecordExists() {
		return recordExists;
	}

	/**
	 * 物理文件是否存在
	 * 
	 * @return
	 */
	public boolean isFileExists() {
		return fileExists;
	}
}
