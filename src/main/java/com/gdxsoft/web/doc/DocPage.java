package com.gdxsoft.web.doc;


import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.RequestValue;

public class DocPage {
	private String connConfigName; // 数据库链接配置名称
	private DocCreate _DocCreate;

	/**
	 * 获取文档创建类
	 * 
	 * @return
	 */
	public DocCreate getDocCreate() {
		return _DocCreate;
	}

	private String _Title;
	private String _DocTmpUnid;

	private boolean _OnlyParaChdDoc;

	public DocPage() {
		_OnlyParaChdDoc = false;
	}

	public DocPage(boolean onlyParaChd) {
		_OnlyParaChdDoc = onlyParaChd;
	}

	public String getDocTmpUnid() {
		return _DocTmpUnid;
	}

	public void setDocTmpUnid(String docTmpUnid) {
		_DocTmpUnid = docTmpUnid;
	}

	private int _DocValId;

	private void createNotExistsDocData(DataConnection conn, int supId) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO doc_tmp(DOC_TMP_UNID, DOC_TMP_ID, DOC_CAT_ID, DOC_CAT_UNID, DOC_TMP_NAME");
		sb.append("\n, DOC_TMP_CNT, DOC_TMP_SQL, DOC_TMP_CDATE, DOC_TMP_MDATE, SUP_ID, DOC_TMP_ORD, DOC_TMP_TAG");
		sb.append("\n, DOC_TMP_TYPE, DOC_TMP_GROUPBY, DOC_TMP_ORDERBY)");
		sb.append("\n SELECT DOC_TMP_UNID, DOC_TMP_ID, DOC_CAT_ID, DOC_CAT_UNID, DOC_TMP_NAME");
		sb.append(
				"\n, DOC_TMP_CNT, DOC_TMP_SQL, DOC_TMP_CDATE, DOC_TMP_MDATE, " + supId + ", DOC_TMP_ORD, DOC_TMP_TAG");
		sb.append("\n, DOC_TMP_TYPE, DOC_TMP_GROUPBY, DOC_TMP_ORDERBY ");
		sb.append("\n FROM bas_doc_tmp WHERE not DOC_TMP_UNID in (select DOC_TMP_UNID from doc_tmp where sup_id="
				+ supId + ")");
		String sql = sb.toString();
		conn.executeUpdate(sql);
		conn.close();
	}

	public String createDocContent(RequestValue rv, int supId) throws Exception {
		DocCreate doc = null;

		DataConnection conn = new DataConnection();
		conn.setRequestValue(rv);
		conn.setConfigName(this.connConfigName == null ? "globaltravel" : this.connConfigName);
		String dodTmpUnid;
		String isNew = rv.getString("ISNEW");
		if (rv.getString("doc_val_mid") == null) {
			isNew = "1";
		}
		String title = "------";
		try {
			if (rv.getString("DOC_TMP_ID") == null) {
				String tag = rv.getString("DOC_TMP_TAG");
				if (tag == null) {
					return "ERROR PARAMETERS";
				}
				String sql1 = "select DOC_TMP_UNID,DOC_TMP_NAME from doc_tmp where doc_tmp_tag=@DOC_TMP_TAG AND SUP_ID="
						+ supId;
				DTTable table1 = DTTable.getJdbcTable(sql1, conn);
				if (table1.getCount() == 0) {
					// 如果数据不存在，则创建数据
					this.createNotExistsDocData(conn, supId);
					table1 = DTTable.getJdbcTable(sql1, conn);
				}
				if (table1.getCount() == 0) {
					// 数据还是不存在，说明用户提交的DOC_TMP_TAG有错误
					conn.close();
					return "ERROR PARAMETERS(1) DOC_TMP_TAG error";
				}
				dodTmpUnid = table1.getCell(0, "DOC_TMP_UNID").toString();
				// System.out.println(dodTmpid);
				title = table1.getCell(0, "DOC_TMP_NAME").toString();
				// System.out.println(title);
			} else {
				dodTmpUnid = rv.getString("DOC_TMP_UNID");
				if (dodTmpUnid == null) {
					String sql1 = "select DOC_TMP_UNID,DOC_TMP_NAME from doc_tmp where doc_tmp_id=@DOC_TMP_ID AND SUP_ID="
							+ supId;
					DTTable table1 = DTTable.getJdbcTable(sql1, conn);
					if (table1.getCount() == 0) {
						conn.close();
						return "ERROR PARAMETERS(1) tag error";
					}
					dodTmpUnid = table1.getCell(0, "DOC_TMP_UNID").toString();
				}
			}

			// 重新加载
			int docValMid = -1;
			if (isNew != null) { // 新建文档
				doc = new DocCreate(dodTmpUnid, supId, conn, this._OnlyParaChdDoc);
				try {
					docValMid = doc.createNew();
				} catch (Throwable err) {
					if (err.getMessage() != null) {
						return err.getMessage();
					} else {
						return err.getCause().toString();
					}
				}
			} else {
				docValMid = Integer.parseInt(rv.getString("doc_val_mid"));
			}
			String reload = rv.getString("RELOAD");
			if (reload == null) {
				reload = "";
			}
			String sql = "SELECT * FROM DOC_MAIN WHERE DOC_VAL_MID=" + docValMid;
			DTTable table = DTTable.getJdbcTable(sql, conn);
			String s;
			if (table.getCount() == 0 || reload.equals("1") || table.getRow(0).getCell("DOC_CNT").getString() == null
					|| table.getRow(0).getCell("DOC_CNT").getString().trim().length() == 0) {
				if (doc == null) {
					doc = new DocCreate(dodTmpUnid, supId, conn, _OnlyParaChdDoc);
				}
				s = doc.create(docValMid);
			} else {
				s = table.getRow(0).getCell("DOC_CNT").getString();
			}

			this._DocCreate = doc;

			this._Title = title;
			this._DocTmpUnid = dodTmpUnid;
			this._DocValId = docValMid;
			s = DocUtils.reptDefines(s, rv);
			return s;
		} catch (Exception err1) {
			throw err1;
		} finally {
			conn.close();
		}
	}

	public String createDocContent(RequestValue rv) throws Exception {
		int supId = rv.getInt("G_SUP_ID");
		return this.createDocContent(rv, supId);
	}

	public String createDocContent2(RequestValue rv, String tg1, int tg2) throws Exception {
		String s = "";
		DataConnection conn = new DataConnection();
		conn.setRequestValue(rv);
		conn.setConfigName(this.connConfigName == null ? "globaltravel" : this.connConfigName);
		String sql1 = "select DOC_CNT from DOC_REF where REF_ID=" + tg2 + " AND TABLE_NAME='" + tg1.replace("'", "")
				+ "'";
		DTTable table1 = DTTable.getJdbcTable(sql1, conn);
		conn.close();
		if (table1.getCount() == 0) {
			s = createDocContent(rv);
			// conn.close();
			return s;
		}
		s = table1.getCell(0, "DOC_CNT").toString();
		return s;
	}

	/**
	 * @return the _Title
	 */
	public String getTitle() {
		return _Title;
	}

	/**
	 * @param title the _Title to set
	 */
	public void setTitle(String title) {
		_Title = title;
	}

	/**
	 * @return the _DocValId
	 */
	public int getDocValMId() {
		return _DocValId;
	}

	/**
	 * @param docValId the _DocValId to set
	 */
	public void setDocValMId(int docValId) {
		_DocValId = docValId;
	}

	public boolean isOnlyParaChdDoc() {
		return _OnlyParaChdDoc;
	}

	public void setOnlyParaChdDoc(boolean a) {
		_OnlyParaChdDoc = a;
	}

	/**
	 * 数据库链接配置名称
	 * 
	 * @return the connConfigName
	 */
	public String getConnConfigName() {
		return connConfigName;
	}

	/**
	 * 数据库链接配置名称
	 * 
	 * @param connConfigName the connConfigName to set
	 */
	public void setConnConfigName(String connConfigName) {
		this.connConfigName = connConfigName;
	}
}
