package com.gdxsoft.web.doc;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.PageValue;
import com.gdxsoft.easyweb.script.RequestValue;

public class DocUtils {

	/**
	 * 保存合同到合同文件中
	 * 
	 * @param pactId    合同编号
	 * @param grpId     团号
	 * @param supId     自己
	 * @param supDownId 供应商
	 * @param docTmpTag 文件标记
	 * @return
	 */
	public static boolean doc2Pact(int pactId, int grpId, int supId, int supDownId, String docTmpTag) {
		RequestValue rv = new RequestValue();
		rv.addValue("doc_tmp_tag", docTmpTag);
		rv.addValue("grp_id", grpId);
		rv.addValue("sup_id", supId);
		rv.addValue("g_sup_id", supId);
		rv.addValue("sup_down_id", supDownId);
		rv.addValue("pact_Id", pactId);

		DataConnection conn = new DataConnection();
		conn.setRequestValue(rv);
		conn.setConfigName("globaltravel");

		DocPage doc = new DocPage();
		String cnt;
		try {
			cnt = doc.createDocContent(rv);
		} catch (Exception e) {
			cnt = e.getMessage();
		}

		byte[] buf = cnt.getBytes();
		PageValue pv = new PageValue();
		pv.setName("BUF");
		pv.setValue(buf);
		pv.setDataType("binary");
		pv.setLength(buf.length);
		rv.addValue(pv);

		String sql = "SELECT  PACT_F_ID FROM PACT_FILE WHERE PACT_ID=@PACT_ID AND PACT_F_TYPE='PACT_F_HTML'";
		DTTable table1 = DTTable.getJdbcTable(sql, conn);
		if (table1.getCount() == 0) {
			sql = "INSERT INTO PACT_FILE(PACT_ID, PACT_F_TYPE, PACT_F_FILE, PACT_F_CDATE, SUP_ID)"
					+ "VALUES(@PACT_ID, 'PACT_F_HTML', @BUF,  GETDATE(), @SUP_ID)";
		} else {
			sql = "UPDATE PACT_FILE SET PACT_F_FILE=@BUF WHERE PACT_F_ID=" + table1.getCell(0, 0).toString();
		}
		conn.executeUpdate(sql);
		conn.close();
		return true;
	}

	public static String reptDefines(String source, RequestValue rv) {
		String findStr = "##{";
		String findEnd = "}##";
		int loc1 = source.indexOf(findStr);
		int loc2 = source.indexOf(findEnd, loc1);
		int inc = 0;
		HashMap<String, String> map = new HashMap<String, String>();
		while (loc2 > loc1 && loc1 > 0) {
			String tmp = source.substring(loc1, loc2 + findEnd.length());
			String tmp1 = tmp.replace("<span>", "").replace("</span>", "").replace("\n", "").replace("\r", "");
			tmp1 = "{" + tmp1.replace(findStr, "").replace(findEnd, "") + "}";

			// System.out.println(tmp1);
			try {
				String tmp2 = reptJson(tmp1, rv);
				if (!map.containsKey(tmp)) {
					map.put(tmp, tmp2);
				}
			} catch (Exception e) {

			}
			loc1 = source.indexOf(findStr, loc2);
			loc2 = source.indexOf(findEnd, loc1);
			if (inc > 100) {
				break;
			}
			inc++;
		}

		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String val = map.get(key);
			source = source.replace(key, val);
		}
		return source;
	}

	public static String reptJson(String json, RequestValue rv) throws Exception {
		JSONObject obj = new JSONObject(json);
		// ##{T:'D2', ADD:"STYLE='WIDTH: 197px; HEIGHT: 197px;'", CODE:""}
		String t = obj.getString("T");
		if (t.equals("D2")) { // d2 code
			String add = obj.getString("ADD");
			String code = obj.getString("CODE").trim();
			String s = java.net.URLEncoder.encode(code, "utf-8");
//			String cc = RequestValue.HOST_BASE;
//			if (cc.endsWith("//")) {
//				cc = cc.substring(0, cc.length() - 1);
//			}
			int port = rv.getRequest().getServerPort();
			String cc = "//" + rv.getRequest().getServerName() + (port == 80 || port == 443 ? "" : ":" + port) + "/";

			String dd = rv.getContextPath();
			dd = dd.replace("/", "");
			String tmp = "<div id='div_2code'><img src=\"" + cc + dd + "/back_admin/doc/d2code.jsp?msg=" + s + "\" "
					+ add + "></div>";

			return tmp;
		}
		return json;
	}
}
