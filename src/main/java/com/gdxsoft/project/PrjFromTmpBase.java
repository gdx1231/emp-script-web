package com.gdxsoft.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.data.DTColumn;
import com.gdxsoft.easyweb.data.DTRow;
import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UFormat;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.easyweb.utils.msnet.MListStr;
import com.gdxsoft.web.dao.*;

public class PrjFromTmpBase extends ProjectBase {
	private static Logger LOGGER = LoggerFactory.getLogger(PrjFromTmpBase.class);
	ProjectTemplate tmp_;
	List<DTTable> tbs_;
	Map<String, OaPrjCheckLast> mapChecks_;
	private ProjectNodes existsNodes_; // 已经存在的 OA_REQ数据

	private boolean tmpVersionChanged_; // 模板版本是否发生变化
	private HashMap<String, Boolean> updateRelatesMap_; // 更新单个Task所有相关的任务

	public PrjFromTmpBase(RequestValue rv) {
		super(rv);
		this.tbs_ = new ArrayList<DTTable>();
	}

	public PrjFromTmpBase(ProjectTemplate tmp, RequestValue rv) {
		super(rv);
		this.tmp_ = tmp;
		this.tbs_ = new ArrayList<DTTable>();
	}

	/**
	 * 执行主数据程序，获取全局变量
	 */
	public void runMainData() {
		DataConnection cnn = new DataConnection(rv_);
		String mainDataSql = tmp_.getMainDataSql();
		if (mainDataSql != null && mainDataSql.trim().length() > 0) {
			String[] sqls = mainDataSql.split(";");

			try {
				List<DTTable> tables = cnn.runMultiSqls(sqls);
				this.addToTbs(tables);
			} catch (Exception e) {
				LOGGER.error(e.getLocalizedMessage());
			}
		}

		cnn.close();
	}

	/**
	 * 获取项目关联的检查数据数据
	 */
	public Map<String, OaPrjCheckLast> loadLastChecks() {
		mapChecks_ = new HashMap<String, OaPrjCheckLast>();

		OaPrjCheckLastDao d1 = new OaPrjCheckLastDao();
		ArrayList<OaPrjCheckLast> al = d1.getRecords("prj_id = " + this.prjMain_.getPrjId());
		for (int i = 0; i < al.size(); i++) {
			OaPrjCheckLast item = al.get(i);
			this.mapChecks_.put(item.getPrjCheckTag(), item);
		}

		return this.mapChecks_;
	}

	private void addToTbs(List<DTTable> tables) {
		for (int i = 0; i < tables.size(); i++) {
			this.tbs_.add(tables.get(i));
		}
	}

	public void checkOaReqsData() throws Exception {
		// 加载已经存在的 OA_REQ数据
		this.existsNodes_ = new ProjectNodes(this.prjMain_.getPrjId());
		existsNodes_.initData();

		// 获取项目关联的检查数据数据
		this.loadLastChecks();

		// recursive call new or update oa_req data
		this.recursiveNewOrUpdateOaReqs(tmp_.getTaskRootLst(), this.rv_, null);
		this.checkMarksData();

		// 不是单独更新节点的话，更新未使用节点的状态
		if (this.updateRelatesMap_ == null) {
			// 更新未被启用的节点未删除状态
			Map<Integer, ProjectNodeData> map = this.existsNodes_.getMapByReqId();
			List<ProjectNodeData> unUseds = new ArrayList<ProjectNodeData>();
			for (Integer reqId : map.keySet()) {
				if (!map.get(reqId).isAlive()) {
					unUseds.add(map.get(reqId));
				}
			}
			if (unUseds.size() > 0) {
				this.existsNodes_.updateStatusUnUsed(unUseds);
			}
		}
	}

	/**
	 * 检查时间标记数据
	 * @throws Exception 
	 */
	public void checkMarksData() throws Exception {
		OaPrjMarkDao d = new OaPrjMarkDao();
		ArrayList<OaPrjMark> al0 = d.getRecords("prj_id=" + this.prjMain_.getPrjId());

		Map<String, OaPrjMark> map = new HashMap<String, OaPrjMark>();
		for (int i = 0; i < al0.size(); i++) {
			OaPrjMark item = al0.get(i);
			if (item.getPrjMarkTag() != null) {
				map.put(item.getPrjMarkTag(), item);
			}
		}

		for (int i = 0; i < this.tmp_.getMarkLst().size(); i++) {
			ProjectTemplateMark mark = this.tmp_.getMarkLst().get(i);
			OaPrjMark item = map.get(mark.getId());
			Date d1 = this.getDate(mark.getBegin(), this.rv_);
			if (d1 == null) {
				// 日期不存在，例如：团未设定日程
				continue;
			}
			if (item == null) {
				// 创建新的Mark
				item = new OaPrjMark();
				item.startRecordChanged();

				item.setPrjId(this.prjMain_.getPrjId());

				item.setPrjMarkTag(mark.getId());
				item.setAdmId(this.admId_);
				item.setSupId(this.supId_);
				item.setPrjMarkCdate(new Date());
				item.setPrjMarkName(mark.getDes());
				item.setPrjMarkNameEn(mark.getDesEn());
				item.setPrjMarkColor(mark.getColor());
				item.setPrjMarkDate(d1);

				d.newRecord(item);
			} else {
				// 修改Mak
				item.startRecordChanged();
				item.setPrjMarkName(mark.getDes());
				item.setPrjMarkNameEn(mark.getDesEn());
				item.setPrjMarkColor(mark.getColor());
				item.setPrjMarkDate(d1);
				if (item.getMapFieldChanged().size() > 0) {
					item.setPrjMarkMdate(new Date());
					d.updateRecord(item, item.getMapFieldChanged());
				}
			}
		}
	}

	/**
	 * 建立单个Task 和 其所有相关的父子节点
	 * 
	 * @param taskId
	 */
	public void buildTaskRelatives(String taskId) {
		ProjectTemplateTask taskThis = this.tmp_.getTask(taskId);
		HashMap<String, Boolean> taskmap = new HashMap<String, Boolean>();
		do {
			taskmap.put(taskThis.getId(), true);
			taskThis = taskThis.getParent();
		} while (taskThis != null);
		// 所有相关的父子节点
		this.updateRelatesMap_ = taskmap;
	}

	/**
	 * 检查是否需要更新数据
	 * 
	 * @param task
	 * @return
	 */
	private boolean checkTaskNeedRefereshData(ProjectTemplateTask task, RequestValue rv) {
		if (this.tmpVersionChanged_) {
			// 模板版本是否发生变化
			return true;
		}

		// 更新单个Task时，所有相关的父子节点
		if (this.updateRelatesMap_ != null) {
			return this.updateRelatesMap_.containsKey(task.getId());
		}

		String checkSql = task.getDataCheckSql();
		if (checkSql == null || checkSql.trim().length() == 0) {
			return true;
		}

		DTTable tb = DTTable.getJdbcTable(checkSql, rv);
		if (tb == null || !tb.isOk()) {
			LOGGER.error("检查是否需要更新数据，错误");
			return true;
		}
		if (tb.getCount() == 0) {
			return true;
		}

		OaPrjCheckLastDao d1 = new OaPrjCheckLastDao();
		OaPrjCheckLast chk = this.mapChecks_.get(task.getId());
		boolean is_new = false;
		if (chk == null) {
			chk = new OaPrjCheckLast();
			chk.startRecordChanged();
			is_new = true;
			chk.setPrjCheckTag(task.getId());
			chk.setPrjId(this.getPrjId());
		} else {
			chk.startRecordChanged();
		}
		for (int i = 0; i < tb.getColumns().getCount(); i++) {
			DTColumn col = tb.getColumns().getColumn(i);
			if (col.getName().equalsIgnoreCase("MAX_ID")) {
				Integer max_id = tb.getCell(0, i).toInt();
				chk.setPrjChkMaxId(max_id);
			} else if (col.getName().equalsIgnoreCase("MAX_TIME")) {
				Date max_time = tb.getCell(0, i).toDate();
				chk.setPrjChkMaxTime(max_time);
			}
		}
		if (chk.getMapFieldChanged().size() > 0) { // 如果 MapFieldChanged,则表示数据发生更改
			chk.setPrjChkDate(new Date());
			if (is_new) {
				d1.newRecord(chk, chk.getMapFieldChanged());
			} else {
				d1.updateRecord(chk, chk.getMapFieldChanged());
			}
			return true;
		} else {
			// 递归更新未刷新的节点为 存活 状态
			this.recursiveUpdateUnRefreshTasksAlive(task);
			return false;
		}

	}

	/**
	 * 递归更新未刷新的节点为 存活 状态
	 * 
	 * @param task
	 */
	private void recursiveUpdateUnRefreshTasksAlive(ProjectTemplateTask task) {
		List<ProjectNodeData> lst = this.existsNodes_.getByTaskId(task.getId());
		if (lst == null) {
			return;
		}
		for (int i = 0; i < lst.size(); i++) {
			ProjectNodeData data = lst.get(i);
			data.setAlive(true);
			data.getOaReq().startRecordChanged();
			data.getOaReq().setReqStatus("OA_REQ_NEW");
			if (data.getOaReq().getMapFieldChanged().size() > 0) {
				this.existsNodes_.newOrUpdateOaReq(data.getOaReq());
			}
		}
		for (int i = 0; i < task.getChildren().size(); i++) {
			ProjectTemplateTask chdTask = task.getChildren().get(i);
			this.recursiveUpdateUnRefreshTasksAlive(chdTask);

		}
	}

	/**
	 * 根据 Task获取已存在数据
	 * 
	 * @param task
	 * @param repeateKey
	 * @return
	 */
	private ProjectNodeData getExistsData(ProjectTemplateTask task, String repeateKey) {
		String taskId = task.getId();
		ProjectNodeData existsNodeData = null;
		if (repeateKey != null) {
			// oa_req 利用字段 REF_TAB保存 循环数据 的 主键值
			existsNodeData = this.existsNodes_.getByTaskIdRepeatKey(taskId, repeateKey);
		} else {
			List<ProjectNodeData> lst = this.existsNodes_.getByTaskId(taskId);
			if (lst == null) {
				return null;
			}
			if (lst.size() > 0) {
				existsNodeData = lst.get(0);
				if (lst.size() > 1) {
					LOGGER.warn(lst.size() + ">1");
				}
			}
		}

		return existsNodeData;
	}

	/**
	 * 递归创建所有的 TemplateTask 对应的 OA_REQ <br>
	 * 每一个Task的 rv 都 Clone 父体的rv, 同时传递到下一级
	 * 
	 * @param tasks                任务
	 * @param rv                   参考参数表（用于创建每一个task 克隆体的来源）
	 * @param parentRepeatNodeData 父节点上如果是循环的话 parentRepeatNodeData
	 */
	public void recursiveNewOrUpdateOaReqs(List<ProjectTemplateTask> tasks, RequestValue rv,
			ProjectNodeData parentRepeatNodeData) {
		if (tasks == null || tasks.size() == 0) {
			return;
		}
		// oa_req 利用字段 REF_TAB保存 循环数据 的 主键值
		String repeateKey = parentRepeatNodeData == null ? null : parentRepeatNodeData.getOaReq().getRefTab();
		for (int i = 0; i < tasks.size(); i++) {
			ProjectTemplateTask task = tasks.get(i);
			if (task.getId().equals("CLIENT")) {
				task.getId().trim();
			}

			if (!this.checkTaskNeedRefereshData(task, rv)) {
				// 检查无需刷新数据
				continue;
			}
			if (task.isRepeat()) {
				// 循环数据
				this.newOrUpdateOaReqRepeats(task, rv);
				continue;
			}

			ProjectNodeData existsNodeData = this.getExistsData(task, repeateKey);

			OaReq oaReq;
			if (existsNodeData == null) { // 创建任务信息
				oaReq = this.createBlankOaReq(task, rv);
				if (parentRepeatNodeData != null) {
					// oa_req 利用字段 REF_TAB保存 循环数据 的 主键值
					String parentReptKey = parentRepeatNodeData.getOaReq().getRefTab();
					oaReq.setRefTab(parentReptKey);
				}

				// 创建新记录
				this.existsNodes_.newOrUpdateOaReq(oaReq);

				existsNodeData = new ProjectNodeData(oaReq);
				this.existsNodes_.addNodeData(existsNodeData);
				this.existsNodes_.buildParent(existsNodeData);
			} else {
				oaReq = existsNodeData.getOaReq();
			}
			// 设置为存活状态
			existsNodeData.setAlive(true);
			// 设置当前的任务关联的 数据
			task.setCurrentNodeData(existsNodeData);

			// 执行任务节点数据查询并 更新数据并返回 Clone 父体的 rv
			RequestValue rvClone = this.updateOaReqData(oaReq, task, rv);
			if (rvClone == null) {
				continue;
			}

			// 更新参数
			if (oaReq.getMapFieldChanged().size() > 0) {
				oaReq.setReqMdate(new Date());
				this.existsNodes_.newOrUpdateOaReq(oaReq);
			}

			if (task.getChildren().size() == 0 && i == tasks.size() - 1) {
				// 更新所有上级节点时间
				this.pm_.updateAllParentSummaryTime(oaReq.getReqId());
			} else {
				// Clone 父体的rv 传递到下一级
				this.recursiveNewOrUpdateOaReqs(task.getChildren(), rvClone, parentRepeatNodeData);
			}
		}
	}

	/**
	 * 根据查询返回的数据 循环 修改或创建 Task 对应的 OA_REQ
	 * 
	 * @param task
	 * @param rv
	 */
	public void newOrUpdateOaReqRepeats(ProjectTemplateTask task, RequestValue rv) {

		// 首先执行 data_sql查询/更新
		RequestValue rv1 = this.executeTaskDataSql(task, rv);

		// 每行数据的主键
		String repeatKey = task.getRepeatKey();

		// 查询返回的数据的SQL
		String sqlRepeat = task.getDataRepeatSql();

		DTTable tb = DTTable.getJdbcTable(sqlRepeat, rv1);
		try {
			for (int i = 0; i < tb.getCount(); i++) {
				DTRow r = tb.getRow(i);

				RequestValue rv2 = rv1.clone();
				rv2.addValues(r);

				String rowKey = r.getCell(repeatKey).toString();

				ProjectNodeData existsNodeData = this.getExistsData(task, rowKey);

				rv2 = this.executeTaskDataSql(task, rv2);
				OaReq req;
				if (existsNodeData == null) {
					// 创建新的数据
					req = this.createBlankOaReq(task, rv2);
					req.setReqType(task.getId());
					req.setRefTab(rowKey);
					this.existsNodes_.newOrUpdateOaReq(req);

					existsNodeData = new ProjectNodeData(req);
					this.existsNodes_.addNodeData(existsNodeData);
					this.existsNodes_.buildParent(existsNodeData);
				} else {
					req = existsNodeData.getOaReq();

				}
				existsNodeData.setAlive(true);

				this.updateOaReqParameters(req, task, rv2);

				// 设定节点的顺序，按照数据返回的顺序
				int taskOrd = req.getReqOrd();
				int reqOrd = taskOrd * 1000 + i;
				req.setReqOrd(reqOrd);

				if (req.getMapFieldChanged().size() > 0) {
					req.setReqMdate(new Date());
					this.existsNodes_.newOrUpdateOaReq(req);
				}

				// 当前的数据
				task.setCurrentNodeData(existsNodeData);

				// 处理子节点
				if (task.getChildren() != null && task.getChildren().size() > 0) {
					this.recursiveNewOrUpdateOaReqs(task.getChildren(), rv2, existsNodeData);
				}

				if (i == tb.getCount() - 1) {
					// 更新所有上级节点时间
					this.pm_.updateAllParentSummaryTime(req.getReqId());
				}
			}

		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
		}
	}

	/**
	 * 执行Task的data_sql的数据库查询/更新
	 * 
	 * @param task 任务模板
	 * @param rv   用于参考的参数表
	 * @return 执行数据查询回更改rv的参数，因此创建rv的clone对象，不破坏原来的参数
	 */
	private RequestValue executeTaskDataSql(ProjectTemplateTask task, RequestValue rv) {
		String sql = task.getDataSql();
		RequestValue rv1;
		if (sql != null && sql.trim().length() > 0) {
			// 本次执行数据查询回更改rv的参数，因此创建rv的clone对象，不破坏原来的参数
			rv1 = rv.clone();
			try {
				DataConnection.runMultiSqlsAndClose(sql, "", rv1);
			} catch (Exception err) {
				LOGGER.error(err.getLocalizedMessage());
				return null;
			}
		} else {
			// 不执行SQL ，不用创建clone
			rv1 = rv;
		}

		return rv1;
	}

	/**
	 * 更新OA_REQ的运行数据
	 * 
	 * @param oaReq
	 * @return
	 */
	public RequestValue updateOaReqData(OaReq oaReq, ProjectTemplateTask task, RequestValue rv) {

		if (task.getId().equals("travel")) {
			task.getId().trim();
		}
		RequestValue rv1 = executeTaskDataSql(task, rv);
		if (rv1 == null) {
			return null;
		}
		try {
			this.updateOaReqParameters(oaReq, task, rv1);
			return rv1;
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return null;
		}
	}

	/**
	 * 更新 OA_REQ 的参数
	 * 
	 * @param oaReq 任务对应的 OA_REQ
	 * @param rv1   参数
	 * @throws Exception 
	 */
	private void updateOaReqParameters(OaReq oaReq, ProjectTemplateTask task, RequestValue rv1) throws Exception {

		if (oaReq.getReqId() != null) { // 新创建的已经开始记录 startRecordChanged
			oaReq.startRecordChanged();
		}
		// 测试是否隐含
		if (task.getHiddenOn() != null && task.getHiddenOn().trim().length() > 0) {
			String v = this.createDes(task.getHiddenOn(), rv1);
			if (v != null && (v.equals("1") || v.equalsIgnoreCase("yes") || v.equalsIgnoreCase("true"))) {
				oaReq.setReqStatus("OA_REQ_DEL");
				return;
			} else {
				oaReq.setReqStatus("OA_REQ_NEW");
			}
		} else {
			oaReq.setReqStatus("OA_REQ_NEW");
		}

		if ("annual_year_profit".equals(oaReq.getReqType())) {
			oaReq.getReqType().trim(); // debug
		}
		// 任务开始时间
		Date dtBegin = this.getDate(task.getBegin(), rv1);
		if (dtBegin == null && task.getBeginDefault() != null) {
			// 默认任务开始时时间
			dtBegin = this.getDate(task.getBeginDefault(), rv1);
		}
		oaReq.setReqStartTime(dtBegin);

		// 任务结束时间
		Date dtEnd = this.getDate(task.getEnd(), rv1);
		if (dtEnd == null && task.getEndDefault() != null) {
			// 默认任务结束时间
			dtEnd = this.getDate(task.getEndDefault(), rv1);
		}
		oaReq.setReqRevPlanTime(dtEnd);

		// 计算任务的初始化值，如果不设定的话则不修改
		if (task.getValInit() != null && task.getValInit().trim().length() > 0) {
			Double valInit = this.getDoubleVal(task.getValInit(), rv1);
			// 初始化值
			oaReq.setReqTarVal(valInit);
		}

		// 运行值，如果不设定的话则不修改
		if (task.getValRunning() != null && task.getValRunning().trim().length() > 0) {
			Double valRunning = this.getDoubleVal(task.getValRunning(), rv1);
			oaReq.setReqComVal(valRunning); // 运行值
		}

		// 完成比例
		if (oaReq.getReqTarVal() == null || oaReq.getReqTarVal() == 0) {
			oaReq.setReqProgress(1.0);
		} else if (oaReq.getReqComVal() != null) {
			double progress = 0;
			if (oaReq.getReqTarVal() <= 0) {
				progress = 0; // 初始化值为0的话设置为 0
			} else {
				progress = oaReq.getReqComVal() / oaReq.getReqTarVal();
				// 取消误差
				if (progress < 1 && 1 - progress < 0.001) {
					progress = 1;
				}
			}
			oaReq.setReqProgress(progress);
		} else {
			oaReq.setReqProgress(null);
		}

		Integer admId = this.getIntVal(task.getAdm(), rv1);
		oaReq.setReqRevAdmId(admId); // 任务负责人

		// 记录项目关联的用户
		this.prjAdm(admId);

		// 任务名称
		String des = this.createDes(task.getDes(), rv1);
		oaReq.setReqSubject(des == null ? "没有定义内容" : des);

		// 任务名称英文
		String desEn = this.createDes(task.getDesEn(), rv1);
		oaReq.setReqSubjectEn(desEn);

		// 任务地点
		String addr = this.createDes(task.getAddr(), rv1);
		oaReq.setReqPlace(addr);
		// 任务地点 - 英文
		String addrEn = this.createDes(task.getAddrEn(), rv1);
		oaReq.setReqPlaceEn(addrEn);

		String link = this.createDes(task.getLink(), rv1);
		oaReq.setReqLink(link);

		if (task.getParent() != null) { // 设置父节点的数据
			int pid = task.getParent().getCurrentNodeData().getOaReq().getReqId();
			oaReq.setReqPid(pid);
		} else {
			oaReq.setReqPid(null);
		}

		// 颜色
		String color = this.createDes(task.getColor(), rv1);
		oaReq.setReqColor(color);

		oaReq.setReqOrd(task.getIndex());
	}

	/**
	 * 记录adm_id与项目的关系
	 * 
	 * @param admId
	 */
	private void prjAdm(Integer admId) {
		if (admId == null) {
			return;
		}
		OaPrjAdmDao d = new OaPrjAdmDao();
		OaPrjAdm o = d.getRecord(this.prjMain_.getPrjId(), admId);
		if (o == null) {
			o = new OaPrjAdm();
			o.setAdmId(admId);
			o.setPrjId(this.prjMain_.getPrjId());

			d.newRecord(o);
		}
	}

	public String createDes(String exp, RequestValue rv1) {
		if (exp == null) {
			return null;
		}
		MListStr al = Utils.getParameters(exp, "@");
		for (int i = 0; i < al.size(); i++) {
			String paramName = al.get(i);
			String paramValue = null;

			paramValue = rv1.s(paramName);

			if (paramValue == null) {
				paramValue = "";
			} else if (paramName.toUpperCase().endsWith("_MONEY")) { // 货币类型
				paramValue = UFormat.formatMoney(paramValue);
			}

			exp = exp.replaceFirst("@" + paramName, Matcher.quoteReplacement(paramValue));
		}

		return exp;
	}

	/**
	 * 获取整数
	 * 
	 * @param exp
	 * @param rv1
	 * @return
	 * @throws Exception 
	 */
	public Integer getIntVal(String exp, RequestValue rv1) throws Exception {
		if (exp == null) {
			return null;
		}
		ProjectTemplateValueExp ptvExp = new ProjectTemplateValueExp(exp);
		Integer val = null;
		if ("RV".equals(ptvExp.getMethod())) {
			String key = ptvExp.getRvKey();
			if (rv1.s(key) == null) {
				return null;
			}
			try {
				val = rv1.getInt(key);
			} catch (Exception err) {
				LOGGER.error(err.getLocalizedMessage());
				return null;
			}
		} else if ("TASK".equals(ptvExp.getMethod())) {
			ProjectTemplateTask task = this.tmp_.getTask(ptvExp.getRefId());
			if (task == null) {
				LOGGER.error("错误的引用：" + exp);
				return null;
			}
			OaReq refReq = this.getOaReqByTask(task);
			if (refReq == null) {
				LOGGER.error("引用关联的REQ_ID 不存在：" + exp);
				return null;
			}
			if ("adm".equals(ptvExp.getRefAttr())) {
				val = refReq.getReqRevAdmId();
			} else {
				LOGGER.error("未知的属性：" + exp);
				return null;
			}
		} else if ("SPECIFIC".equals(ptvExp.getMethod())) {
			try {
				val = Integer.parseInt(ptvExp.getSpecificVal());
			} catch (Exception err) {
				LOGGER.error(err.getLocalizedMessage());
				return null;
			}

		} else {
			LOGGER.error("未知的方法：" + exp);
			return null;
		}
		return ProjectCalc.computeIntExp(ptvExp.getExpCompute(), val);

	}

	private OaReq getOaReqByTask(ProjectTemplateTask task) {
		List<ProjectNodeData> lst = this.existsNodes_.getByTaskId(task.getId());
		if (lst == null || lst.size() == 0) {
			return null;
		} else {
			return lst.get(0).getOaReq();
		}
	}

	public Double getDoubleVal(String exp, RequestValue rv1) throws Exception {
		if (exp == null || exp.trim().length() == 0) {
			return null;
		}
		ProjectTemplateValueExp ptvExp = new ProjectTemplateValueExp(exp);
		Double val = null;
		if ("RV".equals(ptvExp.getMethod())) {
			String key = ptvExp.getRvKey();
			if (rv1.s(key) == null) {
				return null;
			}
			try {
				val = rv1.getDouble(key);
			} catch (Exception err) {
				LOGGER.error(err.getLocalizedMessage());
				return null;
			}
		} else if ("TASK".equals(ptvExp.getMethod())) {
			ProjectTemplateTask task = this.tmp_.getTask(ptvExp.getRefId());
			if (task == null) {
				LOGGER.error("错误的引用：" + exp);
				return null;
			}
			OaReq refReq = this.getOaReqByTask(task);
			if (refReq == null) {
				LOGGER.error("引用关联的REQ_ID 不存在：" + exp);
				return null;
			}
			if ("val_init".equals(ptvExp.getRefAttr())) {// 初始化值
				val = refReq.getReqTarVal();
			} else if ("val_running".equals(ptvExp.getRefAttr())) {// 运行值
				val = refReq.getReqComVal();
			} else {
				LOGGER.error("未知的属性：" + exp);
				return null;
			}
		} else if ("SPECIFIC".equals(ptvExp.getMethod())) {
			try {
				val = Double.parseDouble(ptvExp.getSpecificVal());
			} catch (Exception err) {
				LOGGER.error(err.getLocalizedMessage());
				return null;
			}

		} else {
			LOGGER.error("未知的方法：" + exp);
			return null;
		}
		return ProjectCalc.computeDoubleExp(ptvExp.getExpCompute(), val);

	}

	/**
	 * 获取日期
	 * 
	 * @param exp
	 * @param rv1
	 * @return
	 * @throws Exception 
	 */
	public Date getDate(String exp, RequestValue rv1) throws Exception {
		if (exp == null) {
			return null;
		}

		ProjectTemplateValueExp ptvExp = new ProjectTemplateValueExp(exp);
		Date dt = null;
		if ("RV".equals(ptvExp.getMethod())) {
			String key = ptvExp.getRvKey();
			if (key.equals("SD")) {
				// debug
				key = key.trim();
			}
			if (rv1.s(key) == null) {
				return null;
			}

			try {
				dt = rv1.getDate(key);
			} catch (Exception err) {
				LOGGER.error(err.getLocalizedMessage());
				return null;
			}

		} else if ("TASK".equals(ptvExp.getMethod())) {
			ProjectTemplateTask task = this.tmp_.getTask(ptvExp.getRefId());
			if (task == null) {
				LOGGER.error("错误的引用：" + exp);
				return null;
			}
			OaReq refReq = this.getOaReqByTask(task);
			if (refReq == null) {
				LOGGER.error("引用关联的REQ_ID 不存在：" + exp);
				return null;
			}
			if ("end".equals(ptvExp.getRefAttr())) {
				dt = refReq.getReqRevPlanTime();
			} else if ("begin".equals(ptvExp.getRefAttr())) {
				dt = refReq.getReqStartTime();
			} else {
				LOGGER.error("未知的属性：" + exp);
				return null;
			}
		} else {
			LOGGER.error("未知的方法：" + exp);
			return null;
		}
		return ProjectCalc.computeDateExp(ptvExp.getExpCompute(), dt);
	}

	/**
	 * 创建空白的OA_REQ
	 * 
	 * @param task
	 * @return
	 */
	private OaReq createBlankOaReq(ProjectTemplateTask task, RequestValue rv) {
		OaReq req = new OaReq();
		req.startRecordChanged();

		req.setReqCdate(new Date());
		req.setSupId(this.supId_);
		req.setReqType(task.getId());
		req.setReqOrd(task.getIndex());
		req.setReqStatus("USED");
		req.setReqAdmId(this.admId_);

		req.setReqUnid(Utils.getGuid());

		// 全部锁定，什么都不能更改
		req.setReqLocked("TASK_LOCKED_ALL");

		if (task.getParent() != null) { // 设置父节点的数据
			int pid = task.getParent().getCurrentNodeData().getOaReq().getReqId();
			req.setReqPid(pid);
		}

		req.setReqSubject("创建空白的OA_REQ");
		req.setReqMemo("");
		req.setReqMemoEn("");

		return req;
	}

	/**
	 * 获取模板
	 * 
	 * @return
	 */
	public ProjectTemplate getTmp() {
		return tmp_;
	}

	/**
	 * 获取所有表
	 * 
	 * @return
	 */
	public List<DTTable> getTbs() {
		return tbs_;
	}

	/**
	 * 获取项目关联的检查数据数据
	 * 
	 * @return
	 */
	public Map<String, OaPrjCheckLast> getMapChecks() {
		return mapChecks_;
	}

	/**
	 * 模板版本是否发生变化
	 * 
	 * @return
	 */
	public boolean isTmpVersionChanged() {
		return tmpVersionChanged_;
	}

	/**
	 * 模板版本是否发生变化
	 * 
	 * @param tmpVersionChanged
	 */
	public void setTmpVersionChanged(boolean tmpVersionChanged) {
		this.tmpVersionChanged_ = tmpVersionChanged;
	}

	/**
	 * 更新单个Task所有相关的任务
	 * 
	 * @return
	 */
	public HashMap<String, Boolean> getUpdateRelatesMap() {
		return updateRelatesMap_;
	}

	/**
	 * 获取更新的任务id表达式，用“,”分割
	 * 
	 * @return
	 */
	public String getUpdatedReqIds() {
		Map<Integer, ProjectNodeData> map = existsNodes_.getMapByReqId();
		StringBuilder sb = new StringBuilder();
		for (Integer reqid : map.keySet()) {
			if (map.get(reqid).isAlive()) {
				if (sb.length() > 0) {
					sb.append(",");
				}
				sb.append(reqid);
			}
		}

		return sb.toString();
	}

	/**
	 * 已经存在的 OA_REQ数据
	 * 
	 * @return
	 */
	public ProjectNodes getExistsNodes() {
		return existsNodes_;
	}
}
