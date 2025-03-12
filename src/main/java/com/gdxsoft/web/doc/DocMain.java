package com.gdxsoft.web.doc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UFile;
import com.gdxsoft.easyweb.utils.UJSon;
import com.gdxsoft.easyweb.utils.UMail;
import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.easyweb.utils.fileConvert.Html2PdfByChrome;
import com.gdxsoft.easyweb.utils.msnet.MStr;
import com.gdxsoft.web.http.HttpFileViewBase;

public class DocMain {
	private static Logger LOGGER = LoggerFactory.getLogger(DocMain.class);
	RequestValue rv;
	String configName;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public JSONObject handleDoc(HttpServletRequest request, HttpServletResponse response, RequestValue rv)
			throws Exception {
		this.rv = rv;
		this.request = request;
		this.response = response;
		if (rv.s("SAVE_PDF") != null) {
			return this.handleSavePdf();
		}
		if (rv.s("SAVE_PDF_2_ATTS") != null && rv.s("for_proposal") != null) {
			return this.handleSavePdf2Att();
		}

		String s = "";
		DocPage doc = new DocPage();
		String tg1 = rv.getString("TABLE_NAME");
		String tg_2 = rv.getString("REF_ID");
		String tg_3 = rv.getString("TG");
		int tg2 = -1;
		int tg3 = -1;// 隐藏的iframe
		if (tg_2 != null) {
			tg2 = Integer.parseInt(tg_2);
		}
		if (tg_3 != null && tg_3 != "") {
			tg3 = Integer.parseInt(tg_3);
		}
		if (tg1 != null && tg1.length() > 0 && tg2 > 0 && tg3 == -1) {
			s = doc.createDocContent2(rv, tg1, tg2);
		} else {
			try {
				s = doc.createDocContent(rv);
			} catch (Exception err) {
				s = "文件不存在";
			}
		}
		// 判断是否重新生成
		String reload = rv.getString("RELOAD");

		if (reload != null && reload != "" && Integer.parseInt(reload) == 1) {
			s = doc.createDocContent(rv);
		}
		String download = rv.getString("DOWN");
		if (download != null && download.equals("1")) {
			return this.handleDownload(doc, s);

		} else if (download != null && download.equals("app")) {
			return handleDownloadApp(doc, s);
		} else if (download != null && download.equals("pdf")) {
			// 通过chrome headless 创建pdf
			return this.handleDownloadPdf(doc, s);
		}

		String email = rv.getString("email");
		if (email != null) {
			return this.handleSendEmail(doc, s);
		}
		return this.handleShowDoc(doc, s);
	}

	public JSONObject handleShowDoc(DocPage doc, String s) throws IOException {
		// 是否横向显示
		boolean isLandscape = rv.s("landscape") != null;

		String prev = request.getParameter("PREV");
		if (prev == null) {
			prev = "";
		}
		MStr sb = new MStr();

		sb.al("<!DOCTYPE html><html><head>");
		sb.al("<meta name='viewport' content='width=device-width,  initial-scale=1.0, maximum-scale=3.0, minimum-scale=0.2,  user-scalable=yes'>\n");
		// sb.append("<base href='<%=http%>'>\n");
		sb.al("<meta charset='utf-8'>");
		sb.al("<title>" + doc.getTitle() + "</title>");

		String cssJs = HttpFileViewBase.getEwaJsAndCss(false);
		sb.al(cssJs);

		sb.al("<link rel='stylesheet' media='print' href='doc-print.css'>");
		if (isLandscape) {
			sb.al("<link rel='stylesheet' media='print' href='doc-print-landscape.css'>");
		}
		sb.al("<link rel='stylesheet' media='screen' href='doc-screen.css'>");
		sb.al("<script src='doc.js'></script>");

		sb.al("	<body onload='" + (prev.equals("2") ? "bb()" : "aa()") + "'>");
		if (prev.equals("2") && rv.s("hide_buttons") == null) {
			sb.al("<center id='pp'><img title='直接打印'  src='./images/print.png' class='noprint' onclick='window.print();' />");
			sb.al(" <img title='转成Pdf文件（Pdf）'  src='./images/pdf.png'  class='noprint' onclick='pdf()' />");
			sb.al(" <img  title='转成Word文件（Docx）' src='./images/doc.png'  class='noprint' onclick='cvtword()' />");
			sb.al(" <img title='转成OpenOffice文件（Odt）' src='./images/odt.jpg'  class='noprint' onclick='cvtwordodt()' />");
			if (rv.s("HIDE_SHARE_BTN") == null) {
				sb.al(" <img title='分享' src='./images/qrcode.png'  class='noprint' onclick='shareDoc()' />");
				sb.al(" <img title='发送文件'  src='./images/email.png'  class='noprint' onclick='pdf(\"email\")' />");
			}

			sb.al("<hr class='noprint' /></center>");
		}
		sb.al(s.replace("1.5pt", "1pt"));
		if (prev.equals("2") && rv.getString("DOC_TMP_TAG") != null
				&& rv.getString("DOC_TMP_TAG").equals("CONTACT_CARD")) {
			sb.al("<script>pagebreak()</script>");
		}
		// 转为图片库
		sb.al("<script src='" + UPath.getEmpScriptV2Path() + "/third-party/html2canvas/html2canvas.min.js'></script>");

		// 输出脚本
		if (doc.getDocCreate() != null && doc.getDocCreate().getDocTmp() != null) {
			String js = doc.getDocCreate().getDocTmp().getDocTmpJs();
			if (js != null && js.trim().length() > 0) {
				sb.al("<script>");
				sb.al(js);
				sb.al("</script>");
			}
		}
		sb.al("</body></html>");

		PrintWriter out = response.getWriter();
		out.print(sb.toString());

		return UJSon.rstTrue();
	}

	public JSONObject handleSendEmail(DocPage doc, String s) {
		String ef = rv.getString("ef"); // 发件人
		String et = rv.getString("et"); // 收件人
		String es = rv.getString("es"); // 邮件主题
		String efn = rv.getString("efn");// 发件人姓名
		String etn = rv.getString("etn");// 收件人姓名
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<head>");
		if (doc.getTitle() != null && doc.getTitle().length() > 0) {
			sb.append("<title>" + doc.getTitle() + "</title>");
		}
		sb.append("<meta http-equiv='content-type' content='text/html; charset=UTF-8'>");
		sb.append("</head>");
		sb.append("<body><table border=0 width=760 align=center><tr><td>");
		sb.append(s);
		sb.append("</td></tr></table></body>");
		sb.append("</html>");
		UMail.sendHtmlMail(ef, efn, et, etn, es, sb.toString());
		return UJSon.rstTrue();
	}

	public JSONObject handleDownloadPdf(DocPage doc, String s) throws IOException {
		String http = "//" + request.getServerName();
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<title>" + doc.getTitle() + "</title>");
		sb.append("<base href='" + http + "'>");
		sb.append("<meta http-equiv='content-type' content='text/html; charset=UTF-8'>");
		// print css
		sb.append("<style media=print>\n");
		sb.append(".noprint {\n");
		sb.append("	display: none;\n");
		sb.append("}\n");
		sb.append("body {\n");
		sb.append(
				" font-family: 'HanHei SC', 'PingFang SC', 'Helvetica Neue', 'Helvetica', 'STHeitiSC-Light', 'Hiragino Sans GB', 'Microsoft YaHei', tahoma, Verdana, Arial, sans-serif, arial, STHeiti, 宋体;\n");
		sb.append("	-webkit-box-shadow: none;\n");
		sb.append("	-moz-box-shadow: none;\n");
		sb.append("	box-shadow: none;\n");
		sb.append("	-webkit-print-color-adjust: exact;\n");
		sb.append("	background: transparent;font-size:14px;\n");
		sb.append("} table {border-collapse: collapse;}\n");
		sb.append("@page {\n");

		// 是否横向显示
		boolean isLandscape = rv.s("landscape") == null ? false : true;

		if (!isLandscape) {
			sb.append("	size: A4;\n");
		} else {
			sb.append("	size: A4 landscape;\n");
		}
		sb.append("	margin: 50px 80px;\n");
		sb.append("}\n");
		sb.append("@page :right { \n");
		sb.append("	@bottom-left { \n");
		sb.append("		margin:10pt 0 30pt 0;\n");
		sb.append("		border-top: .25pt solid #666;\n");
		sb.append("		content: string(doctitle);\n");
		sb.append("		font-size: 9pt;\n");
		sb.append("		color: #333;\n");
		sb.append("	}\n");
		sb.append("	@bottom-right {\n");
		sb.append("		margin: 10pt 0 30pt 0;\n");
		sb.append("		border-top: .25pt solid #666;\n");
		sb.append("		content: counter(page);\n");
		sb.append("		font-size: 9pt;\n");
		sb.append("	}\n");
		sb.append("	@top-right {\n");
		sb.append("		content: string(doctitle);\n");
		sb.append("		margin: 30pt 0 10pt 0;\n");
		sb.append("		font-size: 9pt;\n");
		sb.append("		color: #333;\n");
		sb.append("	}\n");
		sb.append("}\n");
		sb.append("</style></head>");
		sb.append("<body>");
		sb.append(s);
		sb.append("</body>");
		sb.append("</html>");

		String name = "/chrome-pdf/" + rv.s("sys_unid");
		String html = UPath.getPATH_UPLOAD() + name + ".html";
		String pdf = UPath.getPATH_UPLOAD() + name + ".pdf";
		String url = UPath.getPATH_UPLOAD_URL() + name + ".pdf";

		UFile.createNewTextFile(html, sb.toString());

		Html2PdfByChrome chrome = new Html2PdfByChrome();
		chrome.convert2PDF(html, pdf);

		response.sendRedirect(url);
		return UJSon.rstTrue();
	}

	public JSONObject handleDownloadApp(DocPage doc, String s) throws IOException {
		PrintWriter out = response.getWriter();
		if (rv.s("showdocument") != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("<!DOCTYPE HTML>\n");
			sb.append("<html>\n");
			sb.append("<head>\n");
			sb.append("<meta charset=utf-8>\n");
			String cssJs = HttpFileViewBase.getEwaJsAndCss(false);
			sb.append(cssJs);
			sb.append("<body style='margin:0'>");
			out.println(sb);
		}
		String tag = rv.s("DOC_TMP_TAG");
		if (tag == null) {
			tag = "";
		} else {
			tag = tag.toLowerCase().replace("<", "").replace("\"", "").replace(">", "").replace("'", "");
		}
		out.println("<div class='doc ewa-app-doc " + tag + "'>");
		out.println(s);
		out.println("</div>");

		// 输出脚本
		if (doc.getDocCreate() != null && doc.getDocCreate().getDocTmp() != null) {
			String js = doc.getDocCreate().getDocTmp().getDocTmpJs();
			if (js != null && js.trim().length() > 0) {
				out.println("<script>");
				out.println(js);
				out.println("</script>");
			}
		}

		return UJSon.rstTrue();
	}

	public JSONObject handleDownload(DocPage doc, String s) throws IOException {
		String http = "//" + request.getServerName();
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<base href='" + http + "'>");
		sb.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
		sb.append("<style type='text/css'>#cnt *{font-size:14.8px;font-family: arial} #cnt h2{font-size: 18px;} ");
		sb.append("p{margin:0px; padding:0cm;}</style>");
		sb.append("</head>");
		sb.append("<body><div id='cnt'>");
		sb.append(s);
		sb.append("</div></body>");
		sb.append("</html>");

		byte[] buf = sb.toString().getBytes("utf-8");
		String filename = doc.getDocTmpUnid() + "_" + doc.getDocValMId() + ".doc";
		response.setHeader("Location", filename);
		response.setHeader("Cache-Control", "max-age=0");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename);
		// filename应该是编码后的(utf-8)
		response.setContentLength(buf.length);
		response.setContentType("image/oct");
		response.getOutputStream().write(buf);
		return UJSon.rstTrue();
	}

	public JSONObject handleSavePdf2Att() throws Exception {
		StringBuilder sb0 = new StringBuilder();
		sb0.append("SELECT * FROM OA_FILE WHERE \n");
		sb0.append("OAF_REF_TMP_UNID=@DOC_SHARE_KEY\n");
		sb0.append("and OAF_DOC_TMP_TAG=@DOC_TMP_TAG\n");
		sb0.append("AND OAF_REF_TYPE='DOC_SHARE'\n");
		sb0.append("AND SUP_ID=@G_SUP_ID AND OAF_STATUS='USED'");
		String sqlFilePdf = sb0.toString();
		DTTable dtFilePdf = DTTable.getJdbcTable(sqlFilePdf, this.configName, rv);
		if (dtFilePdf.getCount() == 0) {
			return (UJSon.rstFalse("没找到文件"));
		}
		int oafId = dtFilePdf.getCell(0, "OAF_ID").toInt();

		StringBuilder sb1 = new StringBuilder();
		sb1.append("select 1 from sys_atts a\n");
		sb1.append("inner join oa_file b on a.file_para0=b.oaf_unid\n");
		sb1.append("where b.oaf_id=");
		sb1.append(oafId);
		sb1.append("\n and a.file_keyword='BY_USER_SIGN' and a.FILE_STATUS='COM_YES'");
		String sqlCheckPdf = sb1.toString();
		DTTable dtCheckPdf = DTTable.getJdbcTable(sqlCheckPdf, this.configName, rv);

		String saveSql;
		if (dtCheckPdf.getCount() == 0) {
			StringBuilder sb2 = new StringBuilder();
			sb2.append("INSERT INTO SYS_ATTS\n");
			sb2.append("(FILE_PATH, FILE_REAL_PATH, FILE_NAME, FILE_DES, FILE_UNID\n");
			sb2.append(", FILE_CDATE, FILE_EXT, FILE_FROM, FILE_RID, FILE_RID1\n");
			sb2.append(", FILE_RID2, FILE_PARA0, FILE_PARA1, FILE_PARA2, FILE_KEYWORD\n");
			sb2.append(", FILE_STATUS, SUP_ID, FILE_SIZE, ADM_ID, FILE_MD5)\n");
			sb2.append("select DCC_FILE_URL,DCC_FILE_PATH,DCC_EXPORT_NAME+'.pdf','',newid()\n");
			sb2.append(", getdate(), 'pdf','grp_costumer',gc.grp_cos_id, b.co_id\n");
			sb2.append(",null,oaf.oaf_unid,'V_EDU_REQ_TYPE_PROPOSAL',DOC_TMP_TAG ,'BY_USER_SIGN'\n");
			sb2.append(", 'COM_YES', DCC_SUP_ID,DCC_FILE_LENGTH ,-1,DCC_FILE_MD5\n");
			sb2.append("from DOC_CONV_CACHE a\n");
			sb2.append(
					"inner join V_EDU_REQ_TYPE_PROPOSAL b on a.DCC_RID = b.CO_UNID and b.CO_TYPE ='EDU_REQ_TYPE_PROPOSAL'\n");
			sb2.append("inner join GRP_COSTUMER gc on b.REF_ID=gc.GRP_COS_ID\n");
			sb2.append("inner join OA_FILE oaf on gc.GRP_COS_ID=oaf.OAF_REF_ID  and oaf.OAF_REF='GRP_COSTUMER'\n");
			sb2.append("and a.DOC_TMP_TAG=oaf.OAF_DOC_TMP_TAG  and oaf.OAF_STATUS='USED'\n");
			sb2.append("where a.DCC_FILE_URL=@PDF_URL\n");
			sb2.append("and b.CO_UNID=@DOC_SHARE_KEY\n");
			sb2.append("and oaf.OAF_ID=");
			sb2.append(oafId);
			saveSql = sb2.toString();
			DataConnection.updateAndClose(saveSql, this.configName, rv);
		}
		return (UJSon.rstTrue());
	}

	public JSONObject handleSavePdf() throws Exception {
		int oafId;
		String saveSql;
		if (rv.s("DOC_SHARE_KEY") == null) {
			return (UJSon.rstTrue("缺少参数DOC_SHARE_KEY"));
		}
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM OA_FILE WHERE \n");
		sb.append("OAF_REF_TMP_UNID=@DOC_SHARE_KEY\n");
		sb.append("and OAF_DOC_TMP_TAG=@DOC_TMP_TAG\n");
		sb.append("AND OAF_REF_TYPE='DOC_SHARE'\n");
		sb.append("AND SUP_ID=@G_SUP_ID AND OAF_STATUS='USED'");
		String sqlPdf = sb.toString();
		DTTable dtPdf = DTTable.getJdbcTable(sqlPdf, configName, rv);
		if (dtPdf.getCount() > 0) {
			oafId = dtPdf.getCell(0, "OAF_ID").toInt();
			StringBuilder sb1 = new StringBuilder();
			sb1.append("UPDATE OA_FILE SET OAF_NAME=@OAF_NAME,OAF_CDATE=@SYS_DATE");
			sb1.append(",OAF_STATUS='USED',ADM_ID=@G_ADM_ID,OAF_URL=@OAF_URL\n");
			sb1.append(",OAF_PARA1=@OAF_PARA1\n");
			sb1.append(",OAF_REF=case when @DOC_SHARE_REF is null then OAF_REF else @DOC_SHARE_REF end\n");
			sb1.append(",OAF_REF_ID=isnull(@DOC_SHARE_REF_ID,OAF_REF_ID) \n");
			sb1.append("WHERE OAF_ID=");
			sb1.append(oafId);
			saveSql = sb1.toString();
		} else {
			StringBuilder sb1 = new StringBuilder();
			sb1.append("INSERT INTO OA_FILE (OAF_UNID, OAF_NAME, OAF_EXT, OAF_MEMO, \n");
			sb1.append("OAF_URL, OAF_CDATE, SUP_ID, ADM_ID, OAF_REF,OAF_REF_ID, OAF_IS_DL, OAF_IS_SHOW, OAF_TYPE,\n");
			sb1.append("OAF_REF_TYPE, OAF_REF_TMP_UNID,OAF_DOC_TMP_TAG,\n");
			sb1.append("OAF_STATUS,OAF_PARA1)\n");
			sb1.append("VALUES(@sys_unid,@OAF_NAME, 'pdf', '分享文档自动生成',\n");
			sb1.append("@OAF_URL, @sys_date, @G_SUP_ID, @G_ADM_ID, isnull(@DOC_SHARE_REF,''),@DOC_SHARE_REF_ID,\n");
			sb1.append(" 'COM_YES', 'COM_YES', 'OAF_TYPE_FILE',\n");
			sb1.append("'DOC_SHARE', @DOC_SHARE_KEY,@DOC_TMP_TAG,\n");
			sb1.append("'USED',@OAF_PARA1)\n");
			saveSql = sb1.toString();
		}
		DataConnection.updateAndClose(saveSql, configName, rv);
		return (UJSon.rstTrue());
	}
}
