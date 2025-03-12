package com.gdxsoft.web.doc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.jodconverter.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.data.DTCell;
import com.gdxsoft.easyweb.data.DTRow;
import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.HtmlControl;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.script.display.items.ItemImage;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.easyweb.utils.msnet.MListStr;
import com.gdxsoft.easyweb.utils.msnet.MStr;
import com.gdxsoft.easyweb.utils.msnet.MTable;

public class DocCreate {
	private static Logger LOGGER = LoggerFactory.getLogger(DocCreate.class);
	private MTable _Subs;
	private DocTmp _Tmp;

	/**
	 * 获取文档模板
	 * 
	 * @return
	 */
	public DocTmp getDocTmp() {
		return _Tmp;
	}

	private DataConnection _Conn;

	private DTTable _TableMainVal;
	private DTTable _TableParas;
	private DTTable _TableSql;

	private int _DocValMId;
	private String _DocTmpUnid;
	private String _DocGrpUnid;

	private boolean _IsLoadByGrpId;
	private String _Error;
	private String _DocType; // 文档类型
	private boolean _IsSub;
	private DocCreate _DocParent;
	private HashMap<String, String> _SubDocParas;
	private int _SupId;
	private boolean _OnlyParaChdDoc;

	private String _DtccRefTable;
	private String _DtccRefId;

	public DocCreate(String dodTmpUnid, int supId, DataConnection cnn) {
		this._DocTmpUnid = dodTmpUnid;
		this._Conn = cnn;
		this._IsLoadByGrpId = false;
		this._SupId = supId;
		try {
			this.init();
		} catch (Throwable e) {
			if (this._Conn != null) {
				this._Conn.close();
			}
			LOGGER.error("dodTmpUnid: {}, supId: {}", dodTmpUnid, supId, e);
			this._Error = e.getMessage();
		}
	}

	public DocCreate(String dodTmpUnid, int supId, DataConnection cnn, boolean OnlyParaChd) {
		this._OnlyParaChdDoc = OnlyParaChd;
		this._DocTmpUnid = dodTmpUnid;
		this._Conn = cnn;
		this._IsLoadByGrpId = false;
		this._SupId = supId;
		try {
			this.init();
		} catch (Throwable e) {
			if (this._Conn != null) {
				this._Conn.close();
			}
			LOGGER.error("dodTmpUnid: {}, supId: {}, OnlyParaChd: {}", dodTmpUnid, supId, OnlyParaChd, e);
			this._Error = e.getMessage();
		}
	}

	public void init() throws Throwable {
		this.loadSub();
		// 参数表
		String sql = "SELECT * FROM BAS_DOC_PARA WHERE DOC_TMP_UNID='" + _Tmp.getDocTmpUnid().replace("'", "''") + "'";
		_TableParas = DTTable.getJdbcTable(sql, this._Conn);

		// 获取子文档参数
		_SubDocParas = new HashMap<String, String>();
		for (int i = 0; i < this._TableParas.getCount(); i++) {
			DTRow r = this._TableParas.getRow(i);
			String paraName = r.getCell("DOC_PARA_CODE").getString();
			String paraRef = r.getCell("DOC_PARA_REF").getString();
			if (paraRef != null && paraRef.equalsIgnoreCase("DOC_REF_DOCSUB")) {
				String chdTmpUNID = r.getCell("DOC_PARA_RUNID").getString();
				_SubDocParas.put(paraName.trim().toUpperCase(), chdTmpUNID);
			}
		}
	}

	public int createNew() throws Throwable {
		this.init();

		RequestValue rv = this._Conn.getRequestValue();
		String sql;
		sql = "INSERT INTO DOC_VAL_MAIN(DOC_GRP_UNID, DOC_TMP_UNID, DOC_VAL_CDATE, SUP_ID, ADM_ID, DOC_UNID) "
				+ "VALUES(@DOC_GRP_UNID, @DOC_TMP_UNID, @SYS_DATE, @G_SUP_ID, @G_ADM_ID, @SYS_UNID)";
		this._Conn.executeUpdate(sql);
		sql = "SELECT DOC_VAL_MID FROM DOC_VAL_MAIN WHERE DOC_UNID=@SYS_UNID";
		DTTable table = DTTable.getJdbcTable(sql, this._Conn);
		int docValMid = Integer.parseInt(table.getRow(0).getCell(0).getString());
		ArrayList<String> sqlA = new ArrayList<String>();
		for (int i = 0; i < this._TableParas.getCount(); i++) {
			DTRow r = this._TableParas.getRow(i);
			String paraRef = r.getCell("DOC_PARA_REF").getString();
			String field = r.getCell("DOC_PARA_CODE").getString();
			String paraId = r.getCell("DOC_PARA_UNID").toString();
			paraId = "'" + paraId.replace("'", "''") + "'";
			if (paraRef != null && paraRef.equalsIgnoreCase("DOC_REF_DOCSUB")) {
				String chdTmpUnId = r.getCell("DOC_PARA_RUNID").getString();
				sql = "SELECT * FROM BAS_DOC_TMP_GRP WHERE DOC_TMP_UNID='" + chdTmpUnId.replace("'", "''") + "'";
				DTTable tbTmpGrp = DTTable.getJdbcTable(sql, this._Conn);

				String v;
				if (tbTmpGrp.getCount() > 0) {
					DTRow rTmpGrp = tbTmpGrp.getRow(0);
					v = "'" + rTmpGrp.getCell("DOC_GRP_UNID").toString().replace("'", "''") + "'";
				} else {
					v = "null";
				}
				String sqlIns = "INSERT INTO DOC_VAL(DOC_PARA_UNID, DOC_VAL_MID, DOC_VAL)" + " VALUES(" + paraId + ", "
						+ docValMid + ", " + v + ");";
				sqlA.add(sqlIns);
			} else {
				String v = rv.getString(field);
				if (v == null) {
					v = "null";
				} else {
					v = "'" + v.replace("'", "''") + "'";
				}
				String sqlIns = "INSERT INTO DOC_VAL(DOC_PARA_UNID, DOC_VAL_MID, DOC_VAL)" + " VALUES(" + paraId + ", "
						+ docValMid + ", " + v + ");";
				sqlA.add(sqlIns);
			}
		}
		if (sqlA.size() > 0) {
			for (int i = 0; i < sqlA.size(); i++) {
				this._Conn.executeUpdate(sqlA.get(i));
			}
		}
		return docValMid;
	}

	public String create(int docValMid) {
		this._DocValMId = docValMid;
		try {
			this.loadData();

			if (this._IsSub == false) { // 最外层文档
				DTRow r = this._TableMainVal.getRow(0);
				this.addTableParaToRv(r);
				if (this._TableSql != null && this._TableSql.getCount() > 0
						&& this._Conn.getRequestValue().s("ADD_MAIN_PARA") != null) {
					DTRow r2 = this._TableSql.getRow(0);
					this.addTableParaToRv(r2);
				}
			}
			String html = null;
			if (this._DocType.equalsIgnoreCase("DOC_TYPE_ONE")) { // 主文档
				html = this.createOne();
			} else if (this._DocType.equalsIgnoreCase("DOC_TYPE_LST")) { // 列表文档
				html = this.createList();
			} else if (this._DocType.equalsIgnoreCase("DOC_TYPE_EWA")) { // 列表文档
				html = this.createEwa();
			} else {
				html = "UNDEFINED DOC TYPE";
			}
			return html;
		} catch (Throwable err) {
			return err.getMessage();
		}
	}

	String createByDocGrpId(String grpUnid) {
		_DocGrpUnid = grpUnid;
		this._IsLoadByGrpId = true;
		try {
			this.loadDataByDocGrp();
			String html = null;
			if (this._DocType.equalsIgnoreCase("DOC_TYPE_ONE")) { // 主文档
				html = this.createOne();
			} else if (this._DocType.equalsIgnoreCase("DOC_TYPE_LST")) { // 列表文档
				html = this.createList();
			} else if (this._DocType.equalsIgnoreCase("DOC_TYPE_EWA")) { // 列表文档
				html = this.createEwa();
			} else {
				html = "UNDEFINED DOC TYPE";
			}
			return html;
		} catch (Throwable err) {
			return err.getMessage();
		}
	}

	private String createEwa() throws Throwable {
		String xmlname = this._Tmp.getDocTmpGroupby();
		String itemname = this._Tmp.getDocTmpOrderby();
		String paras = this._Tmp.getDocTmpSql();

		HtmlControl ht = new HtmlControl();
		RequestValue rv = this._Conn.getRequestValue();
		MListStr al = Utils.getParameters(paras, "@");
		for (int i = 0; i < al.size(); i++) {
			String key = al.get(i);
			if (key.trim().length() == 0) {
				continue;
			}
			String val = rv.s(key);
			paras = paras.replace("@" + key, val == null ? "" : val);
		}
		ht.init(xmlname, itemname, paras, rv.getRequest(), rv.getSession(), null);

		return ht.getHtml();
	}

	/**
	 * 生成单文档
	 * 
	 * @return
	 * @throws Throwable
	 */
	private String createOne() throws Throwable {
		String tmp = _Tmp.getDocTmpCnt();
		MListStr al = Utils.getParameters(tmp, "@");

		// 当前数据
		DTRow selfRow = null;
		if (this._TableSql != null && this._TableSql.getCount() > 0) {
			selfRow = this._TableSql.getRow(0);
		}

		// 获取子文档HTML
		Iterator<String> it = this._SubDocParas.keySet().iterator();
		HashMap<String, String> map = new HashMap<String, String>();
		while (it.hasNext()) {
			String key = it.next();
			try {
				String chdTmpUnId = this._SubDocParas.get(key);
				String docGrpUnId = this.getPara(key, selfRow, false);

				// 子文档HTML
				String s = this.getChild(chdTmpUnId, docGrpUnId);
				map.put(key.toUpperCase(), s);
			} catch (Exception err) {
				map.put(key.toUpperCase(), err.getMessage());
			}

		}

		for (int i = 0; i < al.size(); i++) {
			String key = al.get(i);
			if (key.trim().length() == 0) {
				continue;
			}
			String val;
			if (map.containsKey(key.toUpperCase())) {
				val = map.get(key.toUpperCase());
			} else {
				val = this.getPara(key, selfRow, false);
			}
			tmp = tmp.replace("@" + key, val == null ? "" : val);
		}
		return tmp;
	}

	private String createList() throws Throwable {
		String tmp = _Tmp.getDocTmpCnt();

		int a = tmp.indexOf("<!--S-->");
		int b = tmp.indexOf("<!--E-->");
		String rept = tmp;
		String start = "";
		String end = "";
		if (a >= 0 && b > a) {
			rept = tmp.substring(a + 8, b);
			start = tmp.substring(0, a);
			end = tmp.substring(b);
		}

		if (this._TableSql.getCount() == 0) {

			return start + end;
		}
		MStr sb = new MStr();
		String tmpGroupBy = "---------------------------";
		String curGroupby = "";
		boolean isGroupBy = _Tmp.getDocTmpGroupby() == null || _Tmp.getDocTmpGroupby().equals("") ? false : true;

		for (int i = 0; i < this._TableSql.getCount(); i++) {
			DTRow r = _TableSql.getRow(i);
			if (isGroupBy) {
				curGroupby = r.getCell(_Tmp.getDocTmpGroupby()).getString();
				if (curGroupby == null) {
					curGroupby = "";
				}
			}
			if (!tmpGroupBy.equals(curGroupby)) {
				if (i > 0) {
					sb.a(end);
				}
				String sStart = createListRow(start, r);
				sb.a(sStart);
				tmpGroupBy = curGroupby;
			}
			String rept1 = createListRow(rept, r);
			sb.al(rept1);
		}
		sb.a(end);
		return sb.toString();
	}

	/**
	 * 生成列表没行数据
	 * 
	 * @param tmp
	 * @param row
	 * @return
	 * @throws Throwable
	 */
	private String createListRow(String tmp, DTRow row) throws Throwable {
		MListStr al = Utils.getParameters(tmp, "@");
		String tmp1 = tmp;
		addTableParaToRv(row);
		for (int i = 0; i < al.size(); i++) {
			String paraName = al.get(i);
			String key = paraName.toUpperCase().trim();
			String val;
			if (this._SubDocParas.containsKey(key)) {
				String chdTmpUnId = this._SubDocParas.get(key);
				DTRow r1 = null;
				if (this._TableMainVal != null && this._TableMainVal.getCount() > 0) {
					r1 = this._TableMainVal.getRow(0);
				}
				String docGrpUnId = this.getPara(key, r1, false);

				val = this.getChild(chdTmpUnId, docGrpUnId);
			} else {
				val = this.getPara(al.get(i), row, true);

			}
			if (val == null)
				val = "&nbsp;";
			tmp1 = tmp1.replace("@" + al.get(i), val == null ? "" : val);
		}
		return tmp1;
	}

	/**
	 * 将行数据加载到RV中
	 * 
	 * @param r
	 */
	void addTableParaToRv(DTRow r) {
		DTTable tb = r.getTable();
		RequestValue rv = this._Conn.getRequestValue();
		for (int i = 0; i < tb.getColumns().getCount(); i++) {
			String key = tb.getColumns().getColumn(i).getName();
			Object val = r.getCell(i).getValue();
			// if (val == null) {
			// continue;
			// }
			rv.getPageValues().remove(key);
			rv.addValue(key, val);
		}
	}

	private String getChild(String chdTmpUnId, String docGrpUnid) {
		DocCreate d = (DocCreate) _Subs.get(chdTmpUnId);
		return d.createByDocGrpId(docGrpUnid);
	}

	private String getPara(String para, DTRow r, boolean isLst) throws Throwable {
		RequestValue rv = this._Conn.getRequestValue();
		if (para.equals("DAY_INC")) {
			int zzz = 1;
			zzz++;
		}

		if (r != null) {
			if (r.getTable().getColumns().testName(para)) {
				int idx = r.getTable().getColumns().getNameIndex(para);
				String v = r.getCell(idx).getString();
				if (para.equals("DAY_INC") && rv.getString("OUT_DATE") != null) {
					String sql = "SELECT dbo.fn_ymd_usa(CONVERT(DATETIME,'" + rv.getString("OUT_DATE") + "') + " + v
							+ "-1) A";
					DTTable dt = DTTable.getJdbcTable(sql, this._Conn);
					if (dt.getCount() > 0) {
						return dt.getRow(0).getCell(0).toString();
					}
				}
				DTCell c = r.getCell(idx);
				Object val = c.getValue();
				if (val == null) {
					return null;
				}
				String type = c.getColumn().getTypeName();
				String objClassName = val.getClass().toString();
				if (type == null) {
					type = "";
				} else {
					type = type.toUpperCase();
				}

				if (type.indexOf("MONEY") >= 0) {
					String mm = c.getString();
					if (mm != null && mm.endsWith(".0000")) {
						return c.getString().replace(".0000", ".00");
					}
				} else if (objClassName.indexOf("[B") >= 0) {
					byte[] buf = (byte[]) val;
					v = ItemImage.getImageOri(rv.getContextPath(), buf);
					v = " src=\"" + v + "\" ";
					return v;
				}

				return val.toString();
			}
		}
		return rv.getString(para);

	}

	DTRow getParaRow(String para) {
		try {
			for (int i = 0; i < this._TableParas.getCount(); i++) {
				DTRow r = this._TableParas.getRow(i);
				String name = r.getCell("DOC_PARA_CODE").toString();
				if (name.toUpperCase().trim().equalsIgnoreCase(para.trim())) {
					return r;
				}
			}
		} catch (Exception err) {
			return null;
		}
		return null;
	}

	/**
	 * 加载子文档
	 * 
	 * @throws Exception
	 */
	private void loadSub() throws Exception {
		DocTmpDao dao = new DocTmpDao();
		dao.setConfigName(this._Conn.getCurrentConfig().getName());
		this._Tmp = dao.getRecord(this._SupId, this._DocTmpUnid);
		if (this._Tmp == null) {
			LOGGER.error("模板未发现_SupId {}, _DocTmpUnid {}", this._SupId, this._DocTmpUnid);
			throw new Exception("模板未发现");
		}
		// 替换为自定义模板内容
		RequestValue rv = this._Conn.getRequestValue();
		if (!(StringUtils.isBlank(rv.s("DTCC_REF_TABLE")) || StringUtils.isBlank(rv.s("DTCC_REF_ID")))) {
			String sqlDtcc = "SELECT DOC_TMP_CNT FROM DOC_TMP_CUSTOM_CNT\n"
					+ "where DTCC_REF_TABLE=@DTCC_REF_TABLE and DTCC_REF_ID=@DTCC_REF_ID\n" + "AND DOC_TMP_UNID='"
					+ this._Tmp.getDocTmpUnid().replace("'", "''") + "' AND SUP_ID=@G_SUP_ID\n"
					+ "AND DTCC_STATUS='COM_YES'\n";
			DTTable dtDtcc = DTTable.getJdbcTable(sqlDtcc, rv);
			if (dtDtcc.getCount() == 1) {
				this._Tmp.setDocTmpCnt(dtDtcc.getCell(0, "DOC_TMP_CNT").toString());
			}
		}
		String docType = this._Tmp.getDocTmpType();
		if (docType == null) {
			docType = "";
		}
		this._DocType = docType;
		// 子文档
		/*
		 * 改为仅加载参数相关的子文档
		 */

		String sql;
		if (_OnlyParaChdDoc) {
			sql = "select b.DOC_TMP_UNID from BAS_DOC_PARA a "
					+ " INNER JOIN DOC_TMP B ON A.DOC_PARA_RUNID=B.DOC_TMP_UNID " + " where a.DOC_TMP_UNID='"
					+ this._Tmp.getDocTmpUnid().replace("'", "''") + "'"
					+ " AND DOC_PARA_RUNID IS NOT NULL AND DOC_PARA_RUNID<>''";
		} else {
			sql = "SELECT DOC_TMP_UNID FROM DOC_TMP A " + " INNER JOIN BAS_DOC_CAT B ON A.DOC_CAT_UNID=B.DOC_CAT_UNID "
					+ " WHERE B.DOC_CAT_PUNID  ='" + this._Tmp.getDocCatUnid().replace("'", "''") + "'";
		}

		DTTable valTb = DTTable.getJdbcTable(sql, this._Conn);
		this._Subs = new MTable();
		for (int i = 0; i < valTb.getCount(); i++) {
			String unid = valTb.getRow(i).getCell(0).toString();
			if (this._Conn.getResultSetList() != null && this._Conn.getResultSetList().size() > 100) {
				this._Conn.close();
				LOGGER.error("太多的递归调用次数，死循环？DocTmpUnid={}", this._Tmp.getDocTmpUnid());
				throw new Exception("太多的递归调用次数，死循环？DocTmpUnid=" + this._Tmp.getDocTmpUnid());
			}

			DocCreate d = new DocCreate(unid, _SupId, this._Conn, this._OnlyParaChdDoc);
			d._IsSub = true;
			d._DocParent = this;
			_Subs.add(unid, d);
		}
	}

	private void loadData() throws Throwable {
		docSqlData();

		// doc_val_main 数据
		String sql = "SELECT * FROM DOC_VAL_MAIN WHERE DOC_VAL_MID=" + _DocValMId;

		this._TableMainVal = DTTable.getJdbcTable(sql, this._Conn);

		if (this._TableMainVal.getCount() == 0) {
			return;
		}
		sql = "SELECT A.*,B.DOC_PARA_CODE FROM DOC_VAL A "
				+ "LEFT JOIN BAS_DOC_PARA B ON A.DOC_PARA_UNID=B.DOC_PARA_UNID" + "  WHERE  DOC_VAL_MID=" + _DocValMId;

		DTTable valTb = DTTable.getJdbcTable(sql, this._Conn);
		this.joinTable(valTb);
	}

	private void loadDataByDocGrp() throws Throwable {
		docSqlData();
		if (this._DocGrpUnid == null) {
			return;
		}
		// doc_val_main 数据
		String sql = "SELECT * FROM DOC_VAL_MAIN WHERE DOC_VAL_MID IN("
				+ "SELECT DOC_VAL_MID FROM DOC_VAL_MAIN WHERE DOC_GRP_UNID='" + this._DocGrpUnid.replace("'", "''")
				+ "')";

		this._TableMainVal = DTTable.getJdbcTable(sql, this._Conn);

		if (this._TableMainVal.getCount() == 0) {
			return;
		}
		sql = "SELECT A.*,B.DOC_PARA_CODE FROM DOC_VAL A "
				+ "LEFT JOIN BAS_DOC_PARA B ON A.DOC_PARA_UNID=B.DOC_PARA_UNID" + "  WHERE  DOC_VAL_MID IN("
				+ "SELECT DOC_VAL_MID FROM DOC_VAL_MAIN WHERE DOC_GRP_UNID='" + this._DocGrpUnid.replace("'", "''")
				+ "')";
		DTTable valTb = DTTable.getJdbcTable(sql, this._Conn);
		this.joinTable(valTb);
	}

	private void docSqlData() {
		String sql = _Tmp.getDocTmpSql();
		// SQL定义的数据
		if (sql == null || sql.trim().length() == 0) { // 加载SQL指定的数据
			return;
		}
		String[] sqla = sql.split(";");
		for (int i = 0; i < sqla.length; i++) {
			String sqlb = sqla[i].trim();
			if (sqlb.trim().length() == 0) {
				continue;
			}
			if (sqlb.toUpperCase().startsWith("SELECT")) {
				this._TableSql = DTTable.getJdbcTable(sqlb, this._Conn);
			} else {
				this._Conn.executeUpdate(sqlb);
			}
		}
	}

	void joinTable(DTTable valTb) throws Throwable {
		if (valTb.getCount() == 0) {
			return;
		}

		String[] fromKeys = { "DOC_VAL_MID" };

		String[] fields = new String[_TableParas.getCount()];
		for (int i = 0; i < _TableParas.getCount(); i++) {
			DTRow r = _TableParas.getRow(i);
			fields[i] = r.getCell("DOC_PARA_CODE").getString();
		}
		String namedField = "DOC_PARA_CODE";
		String valueField = "DOC_VAL";

		_TableMainVal.joinHor(valTb, fromKeys, fromKeys, fields, namedField, valueField);
	}

	/**
	 * @return the _DocValMId
	 */
	public int getDocValMId() {
		return _DocValMId;
	}

	/**
	 * @param docValMId the _DocValMId to set
	 */
	public void setDocValMId(int docValMId) {
		_DocValMId = docValMId;
	}

	/**
	 * @return the _DocTmpId
	 */
	public String getDocTmpUnid() {
		return this._DocTmpUnid;
	}

	/**
	 * @param docTmpId the _DocTmpId to set
	 */
	public void setDocTmpId(String docTmpUnid) {
		_DocTmpUnid = docTmpUnid;
	}

	/**
	 * @return the _Error
	 */
	public String getError() {
		return _Error;
	}

	public boolean isOnlyParaChdDoc() {
		return _OnlyParaChdDoc;
	}

	public void setOnlyParaChdDoc(boolean a) {
		_OnlyParaChdDoc = a;
	}

	public String getDtccRefTable() {
		return _DtccRefTable;
	}

	public void setDtccRefTable(String DtccRefTable) {
		this._DtccRefTable = DtccRefTable;
	}

	public String getDtccRefId() {
		return _DtccRefId;
	}

	public void setDtccRefId(String DtccRefId) {
		this._DtccRefId = DtccRefId;
	}
}
