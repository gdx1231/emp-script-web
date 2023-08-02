package com.gdxsoft.web.fileViewer;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.utils.UFile;
import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.easyweb.utils.UXml;
import com.gdxsoft.easyweb.utils.Utils;

public class BinToPhy {

	private NodeList tableLst;
	private DataConnection conn;
	private URL tableBinXmlFilePath;

	public NodeList getTableLst() {
		return tableLst;
	}

	public URL getTableBinXmlFilePath() {
		return tableBinXmlFilePath;
	}

	public BinToPhy(URL tableBinXmlFilePath) {
		this.tableBinXmlFilePath = tableBinXmlFilePath;
	}

	public Element getCfg(String id) {
		Element itemFind = null;
		for (int i = 0; i < this.tableLst.getLength(); i++) {
			Element item = (Element) this.tableLst.item(i);
			String id1 = item.getAttribute("id"); // 表名
			if (id.equalsIgnoreCase(id1)) {
				itemFind = item;
				break;
			}
		}
		return itemFind;
	}

	public void init() throws ParserConfigurationException, SAXException, IOException {
		String text = IOUtils.toString(tableBinXmlFilePath, StandardCharsets.UTF_8);
		Document doc = UXml.asDocument(text);

		NodeList nl = doc.getElementsByTagName("table");

		this.tableLst = nl;
	}

	public void convert() {
		conn = new DataConnection();
		conn.setConfigName("");

		try {
			for (int i = 0; i < this.tableLst.getLength(); i++) {
				Element item = (Element) this.tableLst.item(i);
				int len = 10;
				while (len == 10) {
					len = this.convert(item);
				}
			}
		} catch (Exception err) {
			System.err.println(err.getMessage());
		} finally {
			conn.close();
		}
	}

	public int convert(Element item) throws Exception {
		String tableName = item.getAttribute("name"); // 表名
		String keyField = item.getAttribute("keyf"); // 主键
		String binField = item.getAttribute("binf"); // 二进制字段
		String fileField = item.getAttribute("filef"); // 物理文件字段
		String extField = item.getAttribute("extf"); // 扩展名字段
		String lenField = item.getAttribute("lenf"); // 扩展名字段
		String md5Field = item.getAttribute("md5f"); // md5扩展名

		StringBuilder sb = new StringBuilder();
		sb.append("select top 10 * from ");
		sb.append(tableName);
		sb.append(" where ");
		sb.append(fileField);
		sb.append(" is null");
		String sql = sb.toString();

		DTTable tb = DTTable.getJdbcTable(sql, conn);
		if (tb.getCount() == 0) {
			return 0;
		}

		StringBuilder sbsqls = new StringBuilder();
		String fileRoot = "/business/" + tableName.toLowerCase() + "/";
		for (int i = 0; i < tb.getCount(); i++) {
			Object bin = tb.getCell(i, binField).getValue();
			String key = tb.getCell(i, keyField).toString();
			String phyName;
			String ext = null;
			String md5 = null;
			int len = 0;
			if (bin == null) {
				phyName = "";
			} else {
				byte[] buf = (byte[]) bin;
				len = buf.length;
				if (extField != null && extField.trim().length() > 0) {
					ext = tb.getCell(i, extField).getString();
				}
				if (ext == null || ext.trim().length() == 0) {
					ext = UFile.getExtFromFileBytes(buf);
				}
				if (ext == null || ext.equals("") || ext.equals(".")) {
					ext = "bin";
				}
				if (ext == "zip") {
					ext = "docx";
				}
				md5 = Utils.md5(buf);
				String spt0 = md5.length() > 6 ? md5.substring(0, 6) : md5;
				String spt1 = md5.length() > 6 ? md5.substring(6) : "_";
				String fileName = UFile.createSplitDirPath(spt0, 2) + spt1 + "/" + key + "." + ext;
				fileName = fileName.toLowerCase();
				phyName = fileRoot + fileName;
				String p1 = UPath.getPATH_UPLOAD() + phyName;
				System.out.println(p1);
				UFile.createBinaryFile(p1, buf, false);
			}
			StringBuilder up = new StringBuilder();
			up.append("update ");
			up.append(tableName);
			up.append(" set ");
			up.append(fileField);
			up.append("='");
			up.append(phyName.replace("'", "''"));
			up.append("' ");
			if (phyName.length() > 0) {
				if (ext != null && extField != null && extField.trim().length() > 0) {
					up.append("\n ,");
					up.append(extField);
					up.append("='");
					up.append(ext.replace("'", "''"));
					up.append("' ");
				}
				if (lenField != null && lenField.trim().length() > 0) {
					up.append("\n ,");
					up.append(lenField);
					up.append("=");
					up.append(len);
				}
				if (md5 != null && md5Field != null && md5Field.trim().length() > 0) {
					up.append("\n ,");
					up.append(md5Field);
					up.append("='");
					up.append(md5.replace("'", "''"));
					up.append("' ");
				}
			}
			up.append(" where ");
			up.append(keyField);
			up.append("='");
			up.append(key.replace("'", "''"));
			up.append("';\n");
			sbsqls.append(up);
		}
		conn.executeUpdate(sbsqls.toString());

		return tb.getCount();
	}
}
