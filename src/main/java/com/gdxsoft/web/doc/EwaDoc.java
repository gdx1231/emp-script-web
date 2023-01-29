package com.gdxsoft.web.doc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

import org.json.JSONArray;
import org.json.JSONObject;

import com.gdxsoft.easyweb.data.DTRow;
import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.easyweb.utils.msnet.MListStr;
import com.gdxsoft.easyweb.utils.msnet.MStr;

public class EwaDoc {
	private String _DocUnid;
	private RequestValue _Rv;
	private DataConnection _Cnn;
	private DTTable _DocTb;
	private MStr _DocCnt;
	private ArrayList<String> _Sqls;
	private DTTable[] _Tables;
	private EwaDocPart _MainDocPart;
	private ArrayList<String> _RstLst;
	private HashMap<String, String> _RstMap;

	private HashMap<String, ArrayList<String>> _PartMap;

	public EwaDoc(String unid, RequestValue rv) {
		this._DocUnid = unid;
		this._Rv = rv;
	}

	public String createDoc() {
		_Cnn = new DataConnection();
		_Cnn.setConfigName("");
		_Cnn.setRequestValue(_Rv);
		_DocCnt = new MStr();
		String sql = "SELECT * FROM _EWA_DOC WHERE DOC_UNID='"
				+ this._DocUnid.replace("'", "''") + "'";

		_DocTb = DTTable.getJdbcTable(sql, _Cnn);

		if (isRunErr()) {
			return _DocCnt.toString();
		}

		if (_DocTb.getCount() == 0) {
			_Cnn.close();

			return "编号：" + this._DocUnid + ", 没有对应的数据！";
		}

		_RstLst = new ArrayList<String>();
		_RstMap = new HashMap<String, String>();
		try {
			initDocParameters();
		} catch (Exception e) {
			_Cnn.close();
			this._DocCnt.al("<h1 style='color:red'>初始化参数<br>ERROR: ");
			this._DocCnt.al(e.getMessage());
			this._DocCnt.al("</h1>");

			return _DocCnt.toString();

		}
		try {
			createDocCnt(this._MainDocPart, "");
		} catch (Exception e) {
			_Cnn.close();
			this._DocCnt.al("<h1 style='color:red'>执行<br>ERROR: ");
			this._DocCnt.al(e.getMessage());
			this._DocCnt.al("</h1>");

			return _DocCnt.toString();

		}
		return _DocCnt.toString();
	}

	private void createDocCnt(EwaDocPart part, String index) throws Exception {
		int sqlIndex = part.getSqlIdx();
		DTTable tb = null;
		if (sqlIndex >= 0) {
			String sql = this._Sqls.get(sqlIndex);
			tb = this.returnTable(sql, part);
			if (tb != null && tb.getCount() > 0) {
				part.set_Table(tb);
				tb.setName(sqlIndex + "");
				this._Tables[sqlIndex] = tb;
			}
		}
		if (part.get_Table() == null) {
			String cnt = createDocCnt1(part);
			String newIndex = index + "/" + part.getTag() + ".0";
			this._RstLst.add(newIndex);
			this._RstMap.put(newIndex, cnt);
			for (int m = 0; m < part.getChildren().size(); m++) {
				String newIndex1 = newIndex + "," + m;
				this.createDocCnt(part.getChildren().get(m), newIndex1);
			}
		} else {
			for (int i = 0; i < part.get_Table().getCount(); i++) {
				DTRow r = part.get_Table().getRow(i);
				part.set_CurRow(r);
				String cnt = createDocCnt1(part);
				String newIndex = index + "/" + part.getTag() + "." + i;
				this._RstLst.add(newIndex);
				this._RstMap.put(newIndex, cnt);
				for (int m = 0; m < part.getChildren().size(); m++) {
					String newIndex1 = newIndex + "," + m;
					this.createDocCnt(part.getChildren().get(m), newIndex1);
				}
			}
		}
	}

	private String createDocCnt1(EwaDocPart part) {
		String cnt = part.getOriCnt();
		MListStr al = Utils.getParameters(cnt, "@");
		for (int i = 0; i < al.size(); i++) {
			String para = al.get(i);
			String[] para1 = para.split("\\.");
			if (para1.length != 2) {
				continue;
			}

			int tbIndex = Integer.parseInt(para1[0]);
			String colName = para1[1];
			String val = "";
			if (tbIndex >= 0 && this._Tables.length > tbIndex) {
				DTTable tb = this._Tables[tbIndex];
				if (tb == null || tb.getCount() == 0 || tb.getCurRow() == null) {
					val = para + " 数据不存在";
				} else {
					int idx = tb.getColumns().getNameIndex(colName);
					if (idx == -1) {
						val = para + " 字段不存在";
					} else {
						val = tb.getCurRow().getCell(idx).toString();
						if (val == null) {
							val = "";
						}
					}
				}
			} else {
				val = para + " 引用表不存在【" + tbIndex + "】";
			}
			cnt = cnt.replaceFirst("@" + para, Matcher.quoteReplacement(val));
		}

		return cnt;

	}

	private DTTable returnTable(String sql, EwaDocPart part) throws Exception {
		String[] sqls = sql.split(";");
		RequestValue rv = new RequestValue(_Rv.getRequest(), _Rv.getSession());
		this.createPartRv(part, rv);
		DataConnection cnn = new DataConnection();
		cnn.setConfigName("");
		cnn.setRequestValue(rv);
		DTTable tb = null;
		for (int i = 0; i < sqls.length; i++) {
			String sql1 = sqls[i].trim();
			if (sql1.length() == 0) {
				continue;
			}
			if (sql1.toUpperCase().indexOf("SELECT") == 0) {
				DTTable tb1 = DTTable.getJdbcTable(sql1, cnn);
				if (tb == null) {
					tb = tb1;
				}

			} else {
				cnn.executeUpdate(sql1);
			}
			if (cnn.getErrorMsg() != null) {
				cnn.close();
				throw new Exception(cnn.getErrorMsg());
			}
		}
		cnn.close();

		return tb;
	}

	private void createPartRv(EwaDocPart part, RequestValue rv) {

		if (part.get_CurRow() != null) {
			rv.addValues(part.get_CurRow());
		}
		if (part.getParent() != part) {
			this.createPartRv(part.getParent(), rv);
		}
	}

	private void initDocParameters() throws Exception {
		String sqlJson = this._DocTb.getCell(0, "DOC_SQL").toString();
		_Sqls = new ArrayList<String>();

		if (sqlJson != null && sqlJson.trim().length() > 0) {
			JSONArray arr = new JSONArray(sqlJson);
			for (int i = 0; i < arr.length(); i++) {
				JSONObject item = arr.getJSONObject(i);
				_Sqls.add(item.getString("sql"));
			}
			this._Tables = new DTTable[_Sqls.size()];
		} else {
			this._Tables = new DTTable[0];
		}

		// 查找所有的标签
		String findStr0 = "<!--";
		String findStr1 = "-->";
		String docCnt = this._DocTb.getCell(0, "DOC_CNT_WORK").toString();
		int loc0 = docCnt.indexOf(findStr0);
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		ArrayList<String> alIndex = new ArrayList<String>();
		while (loc0 >= 0) {
			int loc1 = docCnt.indexOf(findStr1, loc0 + findStr0.length());
			if (loc1 < 0 || loc1 < loc0) {
				break;
			}

			String tag = docCnt.substring(loc0, loc1 + findStr1.length());
			String tag1 = tag.replace(findStr0, "").replace(findStr1, "")
					.trim().toUpperCase();

			ArrayList<String> al = new ArrayList<String>();
			al.add(tag1); // 节点名称
			al.add(tag); // 待标签<!--xxx-->
			al.add(loc0 + ""); // 字符开始位置
			al.add(loc1 + findStr1.length() + ""); // 字符结束位置

			if (map.containsKey(tag1)) {
				throw new Exception("定义节点重复《" + tag + "》");
			}
			map.put(tag1, al);
			if (tag1.indexOf("S") >= 0) {
				alIndex.add(tag1);
			}

			loc0 = docCnt.indexOf(findStr0, loc1);

		}
		EwaDocPart part = new EwaDocPart();
		part.setParent(part);
		part.setlocStart(0);
		part.setlocEnd(docCnt.length());
		part.setSqlIdx(-1);
		part.setOriCnt(docCnt);
		part.setTag(_DocUnid);

		for (int i = 0; i < alIndex.size(); i++) {
			String startTag = alIndex.get(i);

			String endTag = startTag.replace("S", "E");
			if (!map.containsKey(endTag)) {
				continue;
			}

			ArrayList<String> alStart = map.get(startTag);
			ArrayList<String> alEnd = map.get(endTag);

			createPart(part, alStart, alEnd);

		}
		_MainDocPart = part;
		_PartMap = map;
	}

	private void createPart(EwaDocPart part, ArrayList<String> alStart,
			ArrayList<String> alEnd) {
		String startTag = alStart.get(1);
		String endTag = alEnd.get(1);
		String partCnt = part.getOriCnt();
		int start = partCnt.indexOf(startTag);
		int end = partCnt.indexOf(endTag);

		String tag0 = alStart.get(0);
		int sqlIndex = -1;
		try {
			sqlIndex = Integer.parseInt(tag0.replace("S", ""));
		} catch (Exception err) {

		}

		if (start < 0) {
			for (int i = 0; i < part.getChildren().size(); i++) {
				EwaDocPart partChild = part.getChildren().get(i);
				createPart(partChild, alStart, alEnd);
			}
		} else {
			EwaDocPart part1 = new EwaDocPart();
			part1.setSqlIdx(sqlIndex);

			String tag = "{" + Utils.getGuid() + "}";
			part1.setTag(tag);
			String cnt = partCnt.substring(start, end);
			part1.setOriCnt(cnt);
			part1.setParent(part);

			part.getChildren().add(part1);

			String newPartCnt = partCnt.replace(cnt, tag);
			part.setOriCnt(newPartCnt);
		}
	}

	private boolean isRunErr() {
		if (_Cnn.getErrorMsg() != null) {
			_Cnn.close();
			this._DocCnt.al("<h1 style='color:red'>ERROR: ");
			this._DocCnt.al(_Cnn.getErrorMsg());
			this._DocCnt.al("</h1>");

			return true;
		}
		return false;
	}
}
