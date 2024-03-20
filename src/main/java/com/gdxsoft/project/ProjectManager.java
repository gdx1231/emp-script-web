package com.gdxsoft.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.datasource.UpdateChange;
import com.gdxsoft.easyweb.datasource.UpdateChangeRow;
import com.gdxsoft.easyweb.datasource.UpdateChanges;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UFormat;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.web.dao.LogMain;
import com.gdxsoft.web.log.LogOaReq;

public class ProjectManager {
	private static Logger LOGGER = LoggerFactory.getLogger(ProjectManager.class);
	private int prjId_;
	private RequestValue rv_;

	private boolean isEn_;
	private DTTable tbTask_;

	public ProjectManager(int prjId, RequestValue rv) {
		this.prjId_ = prjId;
		this.rv_ = rv;
	}

	/**
	 * 更新颜色
	 * 
	 * @param reqId
	 * @param color
	 * @return
	 */
	public JSONObject updateColor(int reqId, String color) {
		JSONObject obj = this.checkTaskInfo(reqId);
		if (!obj.optBoolean("RST")) {
			return obj;
		}
		if (color == null) {
			color = "";
		}
		String REQ_COLOR = obj.optString("REQ_COLOR");
		if (REQ_COLOR == null) {
			REQ_COLOR = "";
		}
		if (color.equals(REQ_COLOR)) {
			obj.put("RST", true);
			obj.put("MSG", "颜色没有更改" + color);
			return obj;
		}

		boolean isHaveChildren = obj.has("CHD") && obj.optInt("CHD") > 0;
		String chdIds = "";
		if (isHaveChildren) {
			chdIds = this.getAllChildrenIds(reqId);
		}
		rv_.addValue("A_COLOR", color);
		rv_.addValue("A_COLOR_OLD", REQ_COLOR);
		StringBuilder sb = new StringBuilder();
		sb.append("update OA_REQ set REQ_COLOR=@A_COLOR where req_id=");
		sb.append(reqId);
		if (chdIds.length() > 0) {
			sb.append(" OR (req_id in (");
			sb.append(chdIds);
			sb.append(") and  case when REQ_COLOR is null then '' else REQ_COLOR end = @A_COLOR_OLD )");
		}
		String sql = sb.toString();

		DataConnection.updateAndClose(sql, "", rv_);

		if (chdIds.length() > 0) {
			chdIds += "," + reqId;
		} else {
			chdIds = "" + reqId;
		}

		obj = this.getTaskData(chdIds);
		obj.put("RST", true);
		return obj;
	}

	private JSONObject checkTaskInfo(int reqId) {
		JSONObject obj = new JSONObject();

		StringBuilder sb0 = new StringBuilder();
		sb0.append("select a.*,b.CHD from OA_REQ  a left join ");
		sb0.append("\n (select req_pid pid, count(*) chd from oa_req where REQ_STATUS!='OA_REQ_DEL' and req_pid=");
		sb0.append(reqId);
		sb0.append("\n group by req_pid) b on a.req_id = b.pid");
		sb0.append("\n where sup_id=@g_sup_id and req_id=");
		sb0.append(reqId);
		String sql0 = sb0.toString();
		DTTable tbTask = DTTable.getJdbcTable(sql0, rv_);

		this.tbTask_ = tbTask;
		if (tbTask.getCount() == 0) {
			obj.put("RST", false);
			obj.put("ERR", "无此数据");
			return obj;
		}

		try {
			obj = tbTask.getRow(0).toJson();
			if (obj.has("REQ_STATUS")) {
				String REQ_STATUS = obj.optString("REQ_STATUS");
				if ("OA_REQ_DEL".equals(REQ_STATUS)) {
					obj.put("RST", false);
					obj.put("ERR", "此节点已删除");
					return obj;
				}
			}
			// 将时间转换为 long
			obj.put("START_TIME", tbTask.getCell(0, "REQ_START_TIME").toTime());
			// 将时间转换为 long
			obj.put("END_TIME", tbTask.getCell(0, "REQ_REV_PLAN_TIME").toTime());
			obj.put("RST", true);
		} catch (Exception err) {
			obj.put("RST", false);
			obj.put("ERR", err);
		}
		return obj;
	}

	/**
	 * /** 更新时间，父节点和 完成率
	 * 
	 * @param reqId
	 * @param reqPid
	 * @param startTime
	 * @param endTime
	 * @param progress
	 * @return
	 * @throws Exception
	 */
	public JSONObject updateMini(int reqId, int reqPid, Date startTime, Date endTime, double progress) throws Exception {
		JSONObject obj = this.checkTaskInfo(reqId);
		if (!obj.optBoolean("RST")) {
			return obj;
		}

		if (progress > 1) {
			progress = 1;
		} else if (progress < 0) {
			progress = 0;
		}

		boolean isHaveChildren = obj.has("CHD") && obj.optInt("CHD") > 0;
		Date oldStartTime = new Date(obj.optLong("START_TIME"));
		Date oldEndTime = new Date(obj.optLong("END_TIME"));
		double oldProgress = obj.has("REQ_PROGRESS") ? obj.optDouble("REQ_PROGRESS") : 0;
		int oldPid = obj.has("REQ_PID") ? obj.optInt("REQ_PID") : 0;

		// 完成进程之间的差值
		double diff_progress = Math.abs(progress - oldProgress);
		// 开始时间之间的差值
		long diff_start = startTime.getTime() - oldStartTime.getTime();
		// 结束时间之间的差值
		long diff_end = endTime.getTime() - oldEndTime.getTime();

		if (oldPid == reqPid && diff_start == 0 && diff_end == 0 && diff_progress < 0.001) {
			obj.put("RST", true);
			obj.put("MSG", "数据没有改变");
			return obj;
		}

		UpdateChanges ucs = new UpdateChanges();
		ucs.setTbBefore(this.tbTask_);

		rv_.addValue("a_REQ_START_TIME", startTime, "date", 100);
		rv_.addValue("a_REQ_REV_PLAN_TIME", endTime, "date", 100);
		rv_.addValue("a_REQ_PID", reqPid);
		rv_.addValue("a_REQ_PROGRESS", progress);

		StringBuilder sb = new StringBuilder();
		sb.append("update OA_REQ set REQ_MDATE=@sys_date");
		if (diff_start != 0) {
			sb.append(", REQ_START_TIME=@a_REQ_START_TIME");

		}
		if (diff_end != 0) {
			sb.append(", REQ_REV_PLAN_TIME=@a_REQ_REV_PLAN_TIME");

		}
		if (oldPid != reqPid) {
			sb.append(", REQ_PID = case when @a_REQ_PID =0 then null else @a_REQ_PID end ");
		}
		if (diff_progress > 0.001) {
			sb.append(", REQ_PROGRESS = @a_REQ_PROGRESS");

			if (progress == 1) {
				// 任务完成时间
				sb.append(", REQ_REV_OK_TIME = @sys_date");
			}
		}
		sb.append("  where sup_id=@g_sup_id and req_id=");
		sb.append(reqId);
		String sql1 = sb.toString();
		DataConnection.updateAndClose(sql1, "", rv_);

		// 提交数据变化日志
		this.checkTaskInfo(reqId);
		ucs.setTbAfter(tbTask_);
		this.logChange(ucs);

		// 用于获取返回数据的id
		String ids = reqId + "";
		// 修改所有子节点时间
		if (isHaveChildren) {
			if (diff_start != 0 || diff_end != 0) {
				String chdIds = this.getAllChildrenIds(reqId);
				if (chdIds.length() > 0) {
					int one_minitu = 1000 * 60;
					StringBuilder sb1 = new StringBuilder();
					sb1.append("update OA_REQ set REQ_START_TIME=DATEADD(mi,");
					sb1.append(diff_start / one_minitu); // 转换成分钟 ms会溢出
					sb1.append(", REQ_START_TIME), REQ_REV_PLAN_TIME=DATEADD(mi,");
					sb1.append(diff_end / one_minitu); // 转换成分钟 ms会溢出
					sb1.append(", REQ_REV_PLAN_TIME) where req_id in (");
					sb1.append(chdIds);
					sb1.append(")");
					String sql2 = sb1.toString();
					DataConnection.updateAndClose(sql2, "", rv_);
					// System.out.println(sql2);
					ids += "," + chdIds;
				}
			}
		}

		// 更新所有上级时间
		String ids1 = this.updateAllParentSummaryTime(reqId);
		if (ids1 != null) {
			ids += "," + ids1;
		}
		obj = this.getTaskData(ids);

		obj.put("RST", true);
		return obj;
	}

	/**
	 * 提交数据变化日志
	 * 
	 * @param changes
	 * @param task
	 */
	public void logChange(UpdateChanges ucs) {
		ucs.setKeysExp("REQ_ID"); // 设定主键

		List<LogMain> lstLogMain = new ArrayList<LogMain>();
		List<LogOaReq> lstLogOaReq = new ArrayList<LogOaReq>();
		for (int ii = 0; ii < ucs.getTbBefore().getCount(); ii++) {
			UpdateChangeRow rowChange = ucs.getRowChange(ii);
			HashMap<String, UpdateChange> map = rowChange.getChanges();
			List<String> changes = new ArrayList<String>();

			for (String changedField : map.keySet()) {
				UpdateChange change = map.get(changedField);
				this.createChangesDescription(changedField, change, changes);
			}
			if (changes.size() == 0) {
				return;
			}

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < changes.size(); i++) {
				if (i > 0) {
					sb.append("\n");
				}
				sb.append(changes.get(i));
			}

			LogOaReq log = new LogOaReq(this.rv_);
			LogMain l = log.log(sb.toString(), "zhcn");
			String reqId;
			try {
				reqId = rowChange.getBefore().getCell("REQ_ID").toString();
			} catch (Exception e) {
				reqId = e.getMessage();
			}
			// 设置来源主键
			l.setLogSrcId0(reqId);

			lstLogMain.add(l);
			lstLogOaReq.add(log);
		}

		 
	}

	/**
	 * 创建变化的描述
	 * 
	 * @param key     字段
	 * @param change  变化对象
	 * @param changes
	 */
	private void createChangesDescription(String key, UpdateChange change, List<String> changes) {
		String dateformatString = "yyyy-MM-dd HH:mm";
		if (key.equalsIgnoreCase("REQ_SUBJECT")) {
			String oldName = change.getBefore().toString();
			String newName = change.getAfter().toString();
			changes.add("修改主题：" + newName + "(原来是：" + oldName + ")");
		} else if (key.equalsIgnoreCase("REQ_SUBJECT_EN")) {
			String oldName = change.getBefore().toString();
			String newName = change.getAfter().toString();
			changes.add("修改英文主题：" + newName + "(原来是：" + oldName + ")");
		} else if (key.equalsIgnoreCase("REQ_START_TIME")) {
			Date oldStartTime = (Date) change.getBefore();
			Date newStartTime = (Date) change.getAfter();
			changes.add("开始时间：" + Utils.getDateString(newStartTime, dateformatString) + "(原来是："
					+ Utils.getDateString(oldStartTime, dateformatString) + ")");
		} else if (key.equalsIgnoreCase("REQ_REV_PLAN_TIME")) {
			Date oldEndTime = (Date) change.getBefore();
			Date newEndTime = (Date) change.getAfter();
			changes.add("结束时间：" + Utils.getDateString(newEndTime, dateformatString) + "(原来是："
					+ Utils.getDateString(oldEndTime, dateformatString) + ")");
		} else if (key.equalsIgnoreCase("REQ_PROGRESS")) {
			double oldProgress = (Double) change.getBefore();
			double newProgress = (Double) change.getAfter();
			double diff_progress = Math.abs(newProgress - oldProgress);
			if (diff_progress > 0.001) {
				String olds;
				try {
					olds = oldProgress == 0 ? "未开始" : oldProgress >= 1 ? "完成" : UFormat.formatPercent(oldProgress);
					String news = newProgress == 0 ? "未开始" : newProgress >= 1 ? "完成" : UFormat.formatPercent(newProgress);
					// 任务完成时间
					changes.add("任务进度：" + news + "(原来是：" + olds + ")");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} else if (key.equalsIgnoreCase("REQ_COLOR")) {
			String colorOld = change.getBefore().toString();
			String colorNew = change.getAfter().toString();
			changes.add("修改颜色：" + colorNew + "(原来是：" + colorOld + ")");
		} else if (key.equalsIgnoreCase("REQ_REV_ADM_ID")) {
			// 负责人
			String sql = "select adm_id, adm_name from adm_user where adm_id in (" + change.getBefore() + ", " + change.getAfter()
					+ ")";
			DTTable tbAdms = DTTable.getJdbcTable(sql);
			String oldAdm = "", newAdm = "";
			for (int i = 0; i < tbAdms.getCount(); i++) {
				if (tbAdms.getCell(i, 0).toString().equals(change.getBefore().toString())) {
					oldAdm = tbAdms.getCell(i, 1).toString();
				} else {
					newAdm = tbAdms.getCell(i, 1).toString();
				}
			}
			changes.add("负责人为：" + newAdm + "(原来是：" + oldAdm + ")");
		} else if (key.equalsIgnoreCase("REQ_REV_DEPS")) {
			// 参与人
			StringBuilder sbOld = new StringBuilder();
			if (change.getBefore() != null && change.getBefore().toString().length() > 0) {
				String sql = "select adm_id, adm_name from adm_user where adm_id in (" + change.getBefore()
						+ ") order by adm_name";
				DTTable tbAdms = DTTable.getJdbcTable(sql);
				for (int i = 0; i < tbAdms.getCount(); i++) {
					if (i > 0) {
						sbOld.append(", ");
					}
					sbOld.append(tbAdms.getCell(i, 1).toString());
				}
			}
			StringBuilder sbNew = new StringBuilder();
			if (change.getAfter() != null && change.getAfter().toString().length() > 0) {
				String sql = "select adm_id, adm_name from adm_user where adm_id in (" + change.getAfter()
						+ ") order by adm_name";
				DTTable tbAdms = DTTable.getJdbcTable(sql);
				for (int i = 0; i < tbAdms.getCount(); i++) {
					if (i > 0) {
						sbNew.append(", ");
					}
					sbNew.append(tbAdms.getCell(i, 1).toString());

				}
			}
			changes.add("参与者为：" + sbNew + "(原来是：" + sbOld + ")");
		} else if (key.equalsIgnoreCase("REQ_PLACE")) { // 地点变化
			String place = change.getBefore().toString();
			String placeNew = change.getAfter().toString();
			if (place == null) {
				place = "";
			}
			if (placeNew == null) {
				placeNew = "";
			}
			if (placeNew.equals(place)) {
				return;
			}

			changes.add("修改地点：" + placeNew + "(原来是：" + place + ")");
		} else if (key.equalsIgnoreCase("REQ_PLACE_EN")) { // 地点变化(英文)
			String place = change.getBefore().toString();
			String placeNew = change.getAfter().toString();
			if (place == null) {
				place = "";
			}
			if (placeNew == null) {
				placeNew = "";
			}
			if (placeNew.equals(place)) {
				return;
			}

			changes.add("修改地点：" + placeNew + "(原来是：" + place + ")");
		}
	}

	/**
	 * 更新项目的开始和结束时间
	 */
	public void updateProjectStartEndDate() {
		StringBuilder sb = new StringBuilder();
		sb.append("select max(REQ_REV_PLAN_TIME) ed, min(REQ_START_TIME) sd from oa_prj_req a ");
		sb.append(" inner join oa_req b on a.req_id =b.req_id and a.prj_id=");
		sb.append(this.prjId_);
		sb.append(" and b.REQ_STATUS != 'OA_REQ_DEL' ");
		String sql = sb.toString();
		DTTable tb = DTTable.getJdbcTable(sql, this.rv_);
		if (tb.getCount() > 0 && !tb.getCell(0, 0).isNull() && !tb.getCell(0, 1).isNull()) {
			// 开始
			this.rv_.addValue("___SD", tb.getCell(0, 1).getValue(), "Date", 100);
			// 结束
			this.rv_.addValue("___ED", tb.getCell(0, 0).getValue(), "Date", 100);

			StringBuilder sb1 = new StringBuilder();
			sb1.append("update oa_prj set PRJ_START_TIME = @___SD, RRJ_END_TIME=@___ED where prj_id=");
			sb1.append(this.prjId_);
			String sql1 = sb1.toString();
			DataConnection.updateAndClose(sql1, "", rv_);
		}
	}

	/**
	 * 获取所有的子节点
	 * 
	 * @param reqId
	 * @return
	 */
	public String getAllChildrenIds(int reqId) {
		int inc = 0;
		boolean isRun = true;
		String pids = reqId + "";
		StringBuilder sbIds = new StringBuilder();
		while (isRun) {
			inc++;
			StringBuilder sb1 = new StringBuilder();
			sb1.append("select req_id from oa_req where sup_id=@g_sup_id and REQ_STATUS!='OA_REQ_DEL' and req_pid in(");
			sb1.append(pids);
			sb1.append(")");
			String sql = sb1.toString();
			DTTable tb = DTTable.getJdbcTable(sql, rv_);
			if (tb.getCount() == 0) {
				isRun = false;
				break;
			} else {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < tb.getCount(); i++) {
					if (sb.length() > 0) {
						sb.append(",");
					}
					sb.append(tb.getCell(i, 0).toInt());
				}
				pids = sb.toString();
				if (sbIds.length() > 0) {
					sbIds.append(",");
				}
				sbIds.append(pids);
			}
			if (inc > 20) {
				LOGGER.error("太多次的查询" + inc);
				return null;
			}
		}

		return sbIds.toString();
	}

	/**
	 * 获取所有父节点
	 * 
	 * @param reqId         当前节点
	 * @param isOnlySummary 只有摘要节点
	 * @return
	 */
	public String getAllParentIds(int reqId, boolean isOnlySummary) {
		int inc = 0;
		boolean isRun = true;
		StringBuilder sbIds = new StringBuilder();
		int reqId1 = reqId;
		String sql0 = isOnlySummary
				? "select a.req_pid,b.chd from oa_req a left join (select req_pid pid, count(*) chd from oa_req where REQ_STATUS!='OA_REQ_DEL' group by req_pid) b on a.req_pid = b.pid where sup_id=@g_sup_id and req_id="
				: "select req_pid from oa_req where sup_id=@g_sup_id and req_id=";
		while (isRun) {
			inc++;
			String sql = sql0 + reqId1;
			DTTable tb = DTTable.getJdbcTable(sql, rv_);
			if (tb.getCount() == 0 || tb.getCell(0, 0).isNull()) {
				isRun = false;
				break;
			} else {
				int pid = tb.getCell(0, 0).toInt();
				if (!isOnlySummary || !tb.getCell(0, 1).isNull()) {
					if (sbIds.length() > 0) {
						sbIds.append(",");
					}
					sbIds.append(pid);
				}
				reqId1 = pid;
			}
			if (inc > 20) {
				LOGGER.error("太多次的查询" + inc);
				return null;
			}
		}

		return sbIds.toString();
	}

	/**
	 * 更新主任务的开始和截至时间
	 * 
	 * @param summaryTaskId
	 */
	public void updateSummaryTaskTime(int summaryTaskId) {
		String childrenIds = this.getAllChildrenIds(summaryTaskId);
		if (childrenIds == null || childrenIds.trim().length() == 0) {
			return;
		}
		// 更新来自所有的子节点中最小的开始时间和最大的结束时间
		String sql = "update oa_req set REQ_START_TIME=a.a, REQ_REV_PLAN_TIME=a.b  from (select min(REQ_START_TIME) a ,max(REQ_REV_PLAN_TIME) b from oa_req where req_id in ("
				+ childrenIds + ") and REQ_STATUS !='OA_REQ_DEL') a where req_id = " + summaryTaskId;

		String sql2 = "select sum(case when REQ_PROGRESS is null or REQ_PROGRESS <0 then 0 when REQ_PROGRESS>1 then 1 else REQ_PROGRESS end) as TOTAL,"+
				" count(*) as NUM, min(REQ_PROGRESS) MIN_PGS from oa_req where req_pid="
				+ summaryTaskId + " and REQ_STATUS !='OA_REQ_DEL'";

		DTTable tb = DTTable.getJdbcTable(sql2);	

		String process="null";
		if (tb.getCount() > 0 && !tb.getCell(0, 0).isNull() && !tb.getCell(0, 1).isNull()) {
			if (tb.getCell(0, 1).toInt() > 0) {
				process = (tb.getCell(0, 0).toDouble() / tb.getCell(0, 1).toInt()) + "";
			}
		}
		
		//更新进度为最小的
		/*
		if(!tb.getCell(0, 2).isNull() && tb.getCell(0, 2).toInt()>0) {
			process=tb.getCell(0, 2).toDouble()+"";
		}
		*/
		String sql3 = "update oa_req set REQ_PROGRESS=" + process + " where req_id = " + summaryTaskId;
		DataConnection.updateAndClose(sql3, "", rv_);

		DataConnection.updateAndClose(sql, "", rv_);
	}

	/**
	 * 更新全部上级的摘要项目的时间
	 * 
	 * @param reqId
	 * @return
	 */
	public String updateAllParentSummaryTime(int reqId) {
		String parentIds = this.getAllParentIds(reqId, true);
		if (parentIds == null || parentIds.trim().length() == 0) {
			return null;
		}
		// System.out.println(reqId + "=" + parentIds);
		String[] ids = parentIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			int summaryTaskId = Integer.parseInt(ids[i]);
			this.updateSummaryTaskTime(summaryTaskId);
		}

		return parentIds;
	}

	/**
	 * 节点升级
	 * 
	 * @param req_id
	 * @return
	 */
	public JSONObject upLevel(int req_id) {
		RequestValue g_rv = this.rv_;
		JSONObject obj = new JSONObject();
		String sql = "select req_pid from OA_REQ where sup_id=@g_sup_id and req_id=" + req_id;
		DTTable tb = DTTable.getJdbcTable(sql, g_rv);
		if (tb.getCount() == 0) {
			obj.put("RST", false);
			obj.put("ERR", "数据不存在");

			return obj;
		}
		if (tb.getCell(0, 0).isNull() || tb.getCell(0, 0).toInt() == 0) {
			obj.put("RST", false);
			obj.put("MSG", "已经到头了");
			return obj;
		}
		// 上级节点
		String sql1 = "select req_pid from OA_REQ where sup_id=@g_sup_id and req_id=" + tb.getCell(0, 0).toInt();
		DTTable tb1 = DTTable.getJdbcTable(sql1, g_rv);
		int new_pid = 0;
		if (tb1.getCount() > 0 && !tb1.getCell(0, 0).isNull()) {
			new_pid = tb1.getCell(0, 0).toInt();
		}
		String sql2 = "update OA_REQ set req_pid = " + (new_pid > 0 ? new_pid + "" : "null")
				+ " where sup_id=@g_sup_id and req_id=" + req_id;
		DataConnection.updateAndClose(sql2, "", g_rv);
		obj.put("RST", true);
		return obj;
	}

	/**
	 * 节点降级
	 * 
	 * @param req_id
	 * @param pid
	 * @return
	 */
	public JSONObject downLevel(int req_id, int pid) {
		RequestValue g_rv = this.rv_;
		JSONObject obj = new JSONObject();
		String sql = "select req_pid from OA_REQ where sup_id=@g_sup_id and req_id=" + req_id;
		DTTable tb = DTTable.getJdbcTable(sql, g_rv);
		if (tb.getCount() == 0) {
			obj.put("RST", false);
			obj.put("ERR", "数据不存在");
			return obj;
		}

		// 上级节点
		String sql1 = "select req_id from OA_REQ where sup_id=@g_sup_id and req_id=" + pid;
		DTTable tb1 = DTTable.getJdbcTable(sql1, g_rv);
		if (tb1.getCount() == 0) {
			obj.put("RST", false);
			obj.put("ERR", "上级节点数据不存在");
			return obj;
		}
		int new_pid = tb1.getCell(0, 0).toInt();

		String sql2 = "update OA_REQ set req_pid = " + new_pid + " where sup_id=@g_sup_id and req_id=" + req_id;
		DataConnection.updateAndClose(sql2, "", g_rv);
		obj.put("RST", true);
		return obj;
	}

	/**
	 * 获取全部任务数据
	 * 
	 * @return
	 */
	public JSONObject getTaskData() {
		return this.getTaskData(null);
	}

	/**
	 * 获取指定编号的任务数据
	 * 
	 * @param req_ids
	 * @return
	 */
	public JSONObject getTaskData(String req_ids) {
		JSONObject obj = new JSONObject();
		// 初始化调用数据
		String sql = this.getTaskSql();
		if (req_ids != null && req_ids.trim().length() > 0) {
			String[] req_ids1 = req_ids.split(",");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < req_ids1.length; i++) {
				String id = req_ids1[i].trim();
				if (id.length() > 0) {
					if (sb.length() > 0) {
						sb.append(",");
					}
					try {
						sb.append(Integer.parseInt(id));
					} catch (Exception err) {
						obj.put("RST", false);
						obj.put("ERR	", err);
						return obj;
					}
				}
			}
			sql += " and a.req_id in (" + sb + ")";
		} else {
			// 开始时间
			if (this.rv_.s("first_dt") != null) {
				sql += " and a.REQ_START_TIME >= @first_dt ";
			}
			// 结束时间
			if (this.rv_.s("last_dt") != null) {
				sql += " and a.REQ_REV_PLAN_TIME <= @last_dt ";
			}
		}

		obj = this.getTaskDataInner(sql);

		return obj;
	}

	/**
	 * 获取所有批量添加的子数据
	 * 
	 * @param reqPid
	 * @param chd_unid
	 * @return
	 */
	public JSONObject getAddedChildrenTaskData(int reqPid, String chd_unid) {
		JSONObject obj = new JSONObject();
		// 初始化调用数据
		String sql = this.getTaskSql();
		sql += " and a.REQ_PID=" + reqPid + " and a.REF_TAG='" + chd_unid.replace("'", "''") + "'";

		obj = getTaskDataInner(sql);
		return obj;
	}

	private JSONObject getTaskDataInner(String sql) {
		RequestValue g_rv = this.rv_;
		JSONObject obj = new JSONObject();

		sql += " order by req_pid, req_ord, REQ_START_TIME";
		DTTable tb = DTTable.getJdbcTable(sql, g_rv);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tb.getCount(); i++) {
			int req_id = tb.getCell(i, 0).toInt();
			if (i > 0) {
				sb.append(",");
			}
			sb.append(req_id);
		}
		JSONArray links;
		if (sb.length() > 0) {
			String sqllinks = "select REQ_LNK_ID as id, REQ_SRC_ID as source, REQ_TAR_ID as target, REQ_LNK_TYPE as type from OA_REQ_LINK where REQ_SRC_ID in("
					+ sb + ") or REQ_TAR_ID in (" + sb + ")";
			DTTable tbLinks = DTTable.getJdbcTable(sqllinks);
			links = tbLinks.toJSONArray();
		} else {
			links = new JSONArray();
		}
		// { data:rows, collections: { links : links } }

		obj.put("data", tb.toJSONArray());
		JSONObject objcollections = new JSONObject();
		objcollections.put("links", links);
		obj.put("collections", objcollections);
		return obj;
	}

	public String getTaskSql() {
		StringBuilder sb = new StringBuilder();
		sb.append("select a.REQ_ID as id");
		sb.append("\n, CASE ");
		sb.append("\n 		WHEN @EWA_LANG='enus' THEN CONVERT(varchar(5), REQ_START_TIME,101)+ ', ' + REQ_SUBJECT_EN + (CASE ");
		sb.append("\n 				WHEN REQ_MEMO_EN='' THEN '' ");
		sb.append("\n 				ELSE ', '+REQ_MEMO_EN ");
		sb.append("\n 			END)  ");
		sb.append("\n 		ELSE CONVERT(varchar(5), REQ_START_TIME,101)+ ', ' + REQ_SUBJECT + (CASE ");
		sb.append("\n 				WHEN REQ_MEMO='' THEN '' ");
		sb.append("\n 				ELSE ', '+REQ_MEMO ");
		sb.append("\n 			END)");
		sb.append("\n 	END as text");
		sb.append("\n, CONVERT(varchar(16), REQ_START_TIME, 120) as start_date"); // 开始时间
		sb.append("\n, case  "); // 完成进度
		sb.append("\n 		when a.REQ_PROGRESS is null then 0  ");
		sb.append("\n 		when a.REQ_PROGRESS>1 then 1  ");
		sb.append("\n 		when a.REQ_PROGRESS <0 then 0  ");
		sb.append("\n 		else REQ_PROGRESS  ");
		sb.append("\n end as progress");
		sb.append("\n, CONVERT(varchar(16), REQ_REV_PLAN_TIME, 120) as end_date "); // 截至时间
		sb.append("\n, CASE WHEN a.REQ_ID = a.REQ_PID THEN 0 ELSE a.REQ_PID END as parent"); // 上级编号
		sb.append("\n, convert(bit,1) as [open], a.REQ_COLOR, isnull(a.REQ_ORD, 9999999) as [order] ");
		sb.append("\n, CASE WHEN @EWA_LANG='enus' THEN C.ADM_NAME_EN ELSE ADM_NAME END as leader"); // 主管
		sb.append("\n, CASE WHEN @EWA_LANG='enus' THEN E.NAMES ELSE D.NAMES END as participant");// 参与人
		sb.append("\n, CASE WHEN @EWA_LANG='enus' THEN A.REQ_PLACE_EN ELSE A.REQ_PLACE END as place");// 地点

		sb.append("\n, REQ_LOCKED"); // 任务锁定状态

		sb.append("\n from OA_REQ a inner join OA_PRJ_REQ b on a.REQ_ID = b.REQ_ID ");
		sb.append("\n LEFT JOIN ADM_USER C on a.REQ_REV_ADM_ID = C.ADM_ID ");
		sb.append("\n LEFT JOIN OA_REQ_DEPT D on a.REQ_ID = D.REQ_ID AND D.DEP_ID=-1"); // 参与人 中文
		sb.append("\n LEFT JOIN OA_REQ_DEPT E on a.REQ_ID = E.REQ_ID AND E.DEP_ID=-2"); // 参与人 英文
		sb.append("\n where a.sup_id=@g_sup_id and b.prj_id = " + this.prjId_ + " AND REQ_STATUS!='OA_REQ_DEL'");
		String sql = sb.toString();
		return sql;
	}

	/**
	 * 创建连接
	 * 
	 * @param source_id 来源
	 * @param target_id 目的
	 * @param link_type 连接类型 finish_to_finish: "2" finish_to_start: "0"
	 *                  start_to_finish: "3" start_to_start: "1"
	 * @return
	 */
	public JSONObject createLink(int source_id, int target_id, int link_type) {
		JSONObject obj = new JSONObject();
		String sql = "insert into OA_REQ_LINK(REQ_SRC_ID,REQ_TAR_ID,REQ_LNK_TYPE) values(" + source_id + "," + target_id + ","
				+ link_type + ")";
		DataConnection cnn = new DataConnection();
		cnn.setConfigName("");
		int REQ_LNK_ID = cnn.executeUpdateReturnAutoIncrement(sql);
		cnn.close();

		obj.put("RST", true);
		obj.put("id", REQ_LNK_ID);

		return obj;
	}

	/**
	 * 删除连接
	 * 
	 * @param source_id 来源
	 * @param target_id 目的
	 * @return
	 */
	public JSONObject deleteLink(int source_id, int target_id) {
		JSONObject obj = new JSONObject();
		String sql = "delete from OA_REQ_LINK where REQ_SRC_ID=" + source_id + " AND REQ_TAR_ID=" + target_id;
		DataConnection.updateAndClose(sql, "", null);
		obj.put("RST", true);

		return obj;
	}

	public boolean isEn() {
		return isEn_;
	}

	public void setEn(boolean isEn) {
		this.isEn_ = isEn;
	}

	public int getPrjId() {
		return prjId_;
	}
}
