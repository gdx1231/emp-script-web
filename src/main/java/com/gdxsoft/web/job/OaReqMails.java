package com.gdxsoft.web.job;

import java.util.HashMap;

import com.gdxsoft.easyweb.data.DTRow;
import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.easyweb.utils.msnet.MListStr;
import com.gdxsoft.easyweb.utils.msnet.MStr;

public class OaReqMails extends JobBase {
	private String HOST; // 192.168.1.10
	private String JOB_EXP;// = "@192.168.1.10";
	private String NAME_PREFIX;// = "pf";

	private static final String MAIL_HEAD = "<table style='font-size:14px' width=600 border=\"0\" cellpadding=1 cellspacing=1 bgcolor='darkgray'>\n"
			+ "<tr><td width=\"150\" bgcolor='white'><b>任务名称：</b></td><td bgcolor='white'>@REQ_SUBJECT</td></tr>\n"
			+ "<tr><td width=\"150\" bgcolor='white'><b>任务编号：</b></td><td bgcolor='white'>@REQ_ID</td></tr>\n"
			+ "<tr><td width=\"150\" bgcolor='white'><b>任务类型：</b></td><td bgcolor='white'>@bas_tag_name</td></tr>\n"
			+ "<tr><td width=\"150\" bgcolor='white'><b>发起人：</b></td><td bgcolor='white'>@REQ_ADM_ID_NAME </td></tr>\n"
			+ "<tr><td width=\"150\" bgcolor='white'><b>发起时间：</b></td><td bgcolor='white'>@REQ_START</td></tr>\n"
			+ "<tr><td width=\"150\" bgcolor='white'><b>负责人：</b></td><td bgcolor='white'>@REQ_REV_ADM_ID_NAME</td></tr>\n"
			+ "<tr><td width=\"150\" bgcolor='white'><b>参与人：</b></td><td bgcolor='white'>@NAMES </td></tr>\n"
			+ "<tr><td width=\"150\" bgcolor='white'><b>计划完成时间：</b></td><td bgcolor='white'>@REQ_REV_PLAN </td></tr>\n"
			+ "<tr><td width=\"150\" bgcolor='white'><b>实际完成时间：</b></td><td bgcolor='white'>@REQ_REV_OK </td></tr>\n"
			+ "<tr><td width=\"150\" bgcolor='white'><b>任务描述：</b></td><td bgcolor='white'>@REQ_MEMO</td></tr>\n"
			+ "<tr><td width=\"150\" bgcolor='white'><b>附件数：</b></td><td bgcolor='white'>@atts</td></tr></table>\n";

	private static final String MAIL_PROCESS = "<table style='font-size:14px' width=600 border=\"0\" cellpadding=1 cellspacing=1 bgcolor='darkgray'>"
			+ "<tr><td  bgcolor='white'>【@req_subject】 @req_date @REQ_ADM_ID_NAME</td></tr><tr><td bgcolor='white'>@req_memo</td></tr></table>";

	private static final String SQL = "SELECT Z.NAMES, A.*,B.ADM_NAME REQ_ADM_ID_NAME, B.ADM_EMAIL REQ_EMAIL \n"
			+ " , B.ADM_USR_STA_TAG, ADM_NAME REQ_REV_ADM_ID_NAME, C.ADM_EMAIL AS REQ_REV_EMAIL \n"
			+ " , REQ_CDATE REQ_START, REQ_REV_PLAN_TIME REQ_REV_PLAN, REQ_REV_OK_TIME REQ_REV_OK \n"
			+ " , REQ_CDATE REQ_DATE, ATTS, F.BAS_TAG_NAME \n"
			+ " , B.ADM_USR_STA_TAG REQ_ADM_TAG,C.ADM_USR_STA_TAG REQ_REV_ADM_TAG FROM OA_REQ A \n"
			+ "		INNER JOIN ADM_USER B ON A.REQ_ADM_ID=B.ADM_ID \n"
			+ " 	LEFT JOIN ADM_USER C ON A.REQ_REV_ADM_ID=C.ADM_ID \n"
			+ " 	LEFT JOIN ADM_DEPT D ON C.ADM_DEP_ID = D.DEP_ID \n"
			+ " 	LEFT JOIN (SELECT OAF_REF_ID,COUNT(*) ATTS FROM OA_FILE WHERE OAF_REF='OA_REQ' GROUP BY OAF_REF_ID ) E ON A.REQ_ID=E.OAF_REF_ID \n"
			+ " 	LEFT JOIN BAS_TAG F ON A.REQ_TYPE=F.BAS_TAG \n"
			+ " 	LEFT JOIN .OA_REQ_DEPT Z ON A.REQ_ID=Z.REQ_ID \n";

	private int _ReqId;

	public OaReqMails(DataConnection cnn, String dbName, int reqId) {
		super(cnn, dbName);
		this._ReqId = reqId;
	}

	/**
	 * 获取任务主数据
	 * 
	 * @return
	 */
	private DTTable getReqTb() {
		String sql = SQL + " WHERE A.REQ_ID=" + this._ReqId;
		DTTable tb = DTTable.getJdbcTable(sql, super._Conn);
		super._Conn.close();
		return tb;
	}

	/**
	 * 获取任务列表
	 * 
	 * @return
	 */
	private DTTable getReqTbProcess() {
		String sql = SQL + " WHERE REQ_PID=" + this._ReqId + " ORDER BY REQ_ID DESC";
		DTTable tb = DTTable.getJdbcTable(sql, super._Conn);
		super._Conn.close();
		return tb;
	}

	/**
	 * 获取抄送列表
	 * 
	 * @return
	 */
	private HashMap<String, String> getCcs() {
		String sql = "SELECT ADM_NAME, ADM_EMAIL FROM ADM_USER WHERE ADM_USR_STA_TAG='OK' AND ADM_ID IN "
				+ "(SELECT DEP_ID FROM .OA_REQ_DEPT WHERE REQ_ID=" + this._ReqId + ")";

		DTTable tb1 = DTTable.getJdbcTable(sql, super._Conn);
		super._Conn.close();

		HashMap<String, String> ccs = new HashMap<String, String>();
		for (int i = 0; i < tb1.getCount(); i++) {
			ccs.put(tb1.getCell(i, 1).toString(), tb1.getCell(i, 0).toString());
		}
		return ccs;
	}

	/**
	 * 获取接受人列表
	 * 
	 * @param tb
	 * @return
	 * @throws Exception
	 */
	private HashMap<String, String> getTos(DTTable tb) throws Exception {
		HashMap<String, String> tos = new HashMap<String, String>();
		String jobManager = tb.getCell(0, "REQ_EMAIL").toString();
		String jobManagerName = tb.getCell(0, "REQ_ADM_ID_NAME").toString();
		tos.put(jobManager, jobManagerName);
		String jobRev = tb.getCell(0, "REQ_REV_EMAIL").toString();
		if (jobRev != null && !jobRev.equals(jobManager)) {
			String jobRevName = tb.getCell(0, "REQ_REV_ADM_ID_NAME").toString();
			tos.put(jobRev, jobRevName);
		}

		return tos;

	}

	/**
	 * 任务的文件
	 * 
	 * @return
	 */
	private HashMap<String, String> getAtts() {
		return this.getAtts(this._ReqId);
	}

	private HashMap<String, String> getAtts(int reqId) {
		HashMap<String, String> atts = new HashMap<String, String>();
		String sql = "SELECT OaF_NAME , OAF_EXT, OAF_URL path FROM OA_FILE"
				+ " WHERE OAF_REF='OA_REQ' and OAF_REF_ID=" + reqId;

		String path0 = UPath.getRealContextPath();
		DTTable tb2 = DTTable.getJdbcTable(sql, super._Conn);

		super._Conn.close();

		for (int i = 0; i < tb2.getCount(); i++) {
			atts.put(tb2.getCell(i, 0).toString(), path0 + "/" + tb2.getCell(i, 1).toString());
		}
		return atts;
	}

	/**
	 * 任务开始
	 * 
	 * @return
	 */
	public boolean sendStart() {
		DTTable tb = this.getReqTb();
		if (tb.getCount() == 0)
			return false;
		try {
			initPop3Parameters();
		} catch (Exception e1) {
			this.log("initPop3Parameters:" + e1.getMessage());
			return false;
		}

		String cnt = "";
		String fromName = "任务监控(" + this._ReqId + ")";
		String fromMail = this.NAME_PREFIX + "JOB-" + this._ReqId + this.JOB_EXP; // pfJOB-1201@192.168.1.10
		try {
			cnt = this.createContent(tb.getRow(0), MAIL_HEAD);
			String subject = "【任务开始】：" + tb.getCell(0, "req_subject").toString();

			HashMap<String, String> ccs = this.getCcs();
			HashMap<String, String> tos = this.getTos(tb);
			HashMap<String, String> atts = this.getAtts();

			JobUtilsSendMail SendMail = new JobUtilsSendMail(super.getRowSup());
			String rst = SendMail.sendHtmlMailB(fromMail, fromName, tos, ccs, subject, cnt, atts);
			this.log("【任务开始】:" + fromMail + "," + rst);
			return true;
		} catch (Exception e) {
			cnt = e.getMessage();
			this.log("【任务开始】:" + fromMail + e.getMessage());
			return false;
		}

	}

	/**
	 * 任务延迟
	 * 
	 * @return
	 */
	public boolean sendDelay() {
		DTTable tb = this.getReqTb();
		if (tb.getCount() == 0)
			return false;
		try {
			initPop3Parameters();
		} catch (Exception e1) {
			this.log("initPop3Parameters:" + e1.getMessage());
			return false;
		}

		String cnt = "";
		String fromName = "任务监控(" + this._ReqId + ")";
		String fromMail = this.NAME_PREFIX + "JOB-" + this._ReqId + this.JOB_EXP; // pfJOB-1201@192.168.1.10
		try {
			cnt = this.createContent(tb.getRow(0), MAIL_HEAD);
			String subject = "【任务延迟】：" + tb.getCell(0, "req_subject").toString();

			HashMap<String, String> ccs = this.getCcs();
			HashMap<String, String> tos = this.getTos(tb);

			JobUtilsSendMail SendMail = new JobUtilsSendMail(super.getRowSup());
			String rst = SendMail.sendHtmlMailB(fromMail, fromName, tos, ccs, subject, cnt, null);
			this.log("【任务延迟】:" + fromMail + "," + rst);
			return true;

		} catch (Exception e) {
			cnt = e.getMessage();
			this.log("【任务开始】:" + fromMail + e.getMessage());
			return false;
		}

	}

	/**
	 * 任务结束邮件
	 * 
	 * @return
	 */
	public boolean sendComplete() {
		DTTable tb = this.getReqTb();
		if (tb.getCount() == 0)
			return false;
		try {
			initPop3Parameters();
		} catch (Exception e1) {
			this.log("initPop3Parameters:" + e1.getMessage());
			return false;
		}

		String cnt = "";
		String fromName = "任务监控(" + this._ReqId + ")";
		String fromMail = this.NAME_PREFIX + "JOB-" + this._ReqId + this.JOB_EXP; // pfJOB-1201@192.168.1.10
		try {
			cnt = this.createContent(tb.getRow(0), MAIL_HEAD);
			String subject = "【任务结束】：" + tb.getCell(0, "req_subject").toString();

			HashMap<String, String> ccs = this.getCcs();
			HashMap<String, String> tos = this.getTos(tb);

			JobUtilsSendMail SendMail = new JobUtilsSendMail(super.getRowSup());
			String rst = SendMail.sendHtmlMailB(fromMail, fromName, tos, ccs, subject, cnt, null);
			this.log("【任务结束】:" + fromMail + "," + rst);
			return true;

		} catch (Exception e) {
			cnt = e.getMessage();
			this.log("【任务结束】:" + fromMail + e.getMessage());
			return false;
		}

	}

	/**
	 * 生成邮件 的过程部分
	 * 
	 * @return
	 */
	private String createContentProcess() {
		DTTable tb = this.getReqTbProcess();
		if (tb.getCount() == 0)
			return "NO DATA";
		MStr s = new MStr();
		for (int i = 0; i < tb.getCount(); i++) {
			try {
				String s1 = this.createContent(tb.getRow(i), MAIL_PROCESS);
				s.al(s1);
			} catch (Exception e) {
				s.al(e.getMessage());
			}

		}

		return s.toString();
	}

	/**
	 * 任务计划过程 邮件
	 * 
	 * @param subReqId
	 * @return
	 */
	public boolean sendProcess(int subReqId) {
		DTTable tb = this.getReqTb();
		if (tb.getCount() == 0)
			return false;

		try {
			initPop3Parameters();
		} catch (Exception e1) {
			this.log("initPop3Parameters:" + e1.getMessage());
			return false;
		}

		String cnt = "";
		String fromName = "任务监控(" + this._ReqId + ")";
		String fromMail = this.NAME_PREFIX + "JOB-" + this._ReqId + this.JOB_EXP; // pfJOB-1201@192.168.1.10
		try {
			cnt = this.createContent(tb.getRow(0), MAIL_HEAD);
			String cnt1 = this.createContentProcess();
			cnt += cnt1;

			String subject = "【任务计划过程】：" + tb.getCell(0, "req_subject").toString();

			HashMap<String, String> ccs = this.getCcs();
			HashMap<String, String> tos = this.getTos(tb);
			HashMap<String, String> atts = this.getAtts(subReqId);

			JobUtilsSendMail SendMail = new JobUtilsSendMail(super.getRowSup());

			String rst = SendMail.sendHtmlMailB(fromMail, fromName, tos, ccs, subject, cnt, atts);
			this.log("【任务计划过程】:" + fromMail + "," + rst);
			return true;
		} catch (Exception e) {
			cnt = e.getMessage();
			this.log("【任务计划过程】:" + fromMail + e.getMessage());
			return false;
		}

	}

	/**
	 * 生成邮件内容
	 * 
	 * @param r
	 * @param template
	 * @return
	 * @throws Exception
	 */
	private String createContent(DTRow r, String template) throws Exception {
		MListStr al = Utils.getParameters(template, "@");
		for (int i = 0; i < al.size(); i++) {
			String val = "";
			String para = al.get(i);

			if (r.getTable().getColumns().testName(para)) {
				val = r.getCell(para).toString();
			}
			if (val == null) {
				val = "&nbsp;";
			}
			if (val.trim().length() == 0) {
				val = "&nbsp;";
			}
			/*
			 * if (para.equals("REQ_ID")) { val =
			 * "<a target=_blank href='http://erp1.oneworld.cc/" + NAME_PREFIX +
			 * "/index.jsp?open_job=1&req_id=" + val + "'>" + val + "</a>"; }
			 */
			template = template.replace("@" + para, val);

		}
		return template;
	}

	private void initPop3Parameters() throws Exception {
		if (HOST == null) {
			HOST = super._RowSup.getCell("SMTP_HOST").toString();
			String sendrUSERNAME = super._RowSup.getCell("JOB_POP3_UID").toString();
			JOB_EXP = "@" + sendrUSERNAME.split("@")[1];
			NAME_PREFIX = sendrUSERNAME.split("@")[0];
		}
	}
}
