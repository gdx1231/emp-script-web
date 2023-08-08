package com.gdxsoft.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gdxsoft.web.dao.*;


public class ProjectNodes {

	private int prjId_;
	private Map<Integer, ProjectNodeData> mapByReqId_; // 根据REQ_ID索引的数据

	private Map<String, List<ProjectNodeData>> mapByTaskId_; // 根据 ReqType索引的数据

	private OaReqDao oaReqDao = new OaReqDao();

	public ProjectNodes(int prjId) {
		this.prjId_ = prjId;
	}

	/**
	 * 更新未使用的OA_REQ列表
	 * 
	 * @param unUsedList
	 * @return
	 */
	public int updateStatusUnUsed(List<ProjectNodeData> unUsedList) {
		if (unUsedList == null) {
			return -1;
		}
		for (int i = 0; i < unUsedList.size(); i++) {
			OaReq item = unUsedList.get(i).getOaReq();
			item.startRecordChanged();
			item.setReqStatus("OA_REQ_DEL");
			if (item.getMapFieldChanged().size() > 0) {
				item.setReqMdate(new Date());
				oaReqDao.updateRecord(item, item.getMapFieldChanged());
			}
		}

		return unUsedList.size();
	}

	/**
	 * 创建获取新建 OA_REQ
	 * 
	 * @param req
	 * @return
	 */
	public boolean newOrUpdateOaReq(OaReq req) {
		if (req.getReqId() == null) { // 创建
			oaReqDao.newRecord(req, req.getMapFieldChanged());

			// System.out.println(req.getReqType() + "," + req.getReqId());

			// 创建 OA_PRJ与 OA_REQ的关系 OA_PRJ_REQ
			OaPrjReq oaPrjReq = new OaPrjReq();
			oaPrjReq.setPrjId(this.prjId_);
			oaPrjReq.setReqId(req.getReqId());

			OaPrjReqDao dOaPrjReqDao = new OaPrjReqDao();
			dOaPrjReqDao.newRecord(oaPrjReq);

			return true;
		} else { // 更新
			oaReqDao.updateRecord(req, req.getMapFieldChanged());
			return false;
		}
	}

	/**
	 * 加载已经存在的 OA_REQ数据
	 * 
	 * @return
	 */
	public void initData() {

		// 获取项目的所有节点数据
		StringBuilder w0 = new StringBuilder();
		w0.append("req_id in (select req_id from oa_prj_req where PRJ_ID=" + this.prjId_ + ") ORDER BY REQ_ID");
		ArrayList<OaReq> al0 = oaReqDao.getRecords(w0.toString());

		// 清除记录，避免内存占用
		oaReqDao.getConn().getResultSetList().clear();

		// 根据REQ_ID索引的数据
		mapByReqId_ = new HashMap<Integer, ProjectNodeData>();
		// 根据 ReqType索引的数据
		mapByTaskId_ = new HashMap<String, List<ProjectNodeData>>();

		for (int i = 0; i < al0.size(); i++) {
			OaReq item = al0.get(i);
			ProjectNodeData nodeData = new ProjectNodeData(item);
			this.addNodeData(nodeData);
		}

		// 建立父子关系
		for (Integer reqId : mapByReqId_.keySet()) {
			ProjectNodeData nodeData = mapByReqId_.get(reqId);
			this.buildParent(nodeData);
		}
	}

	/**
	 * 添加节点
	 * 
	 * @param nodeData
	 */
	public void addNodeData(ProjectNodeData nodeData) {
		mapByReqId_.put(nodeData.getOaReq().getReqId(), nodeData);
		String taskId = nodeData.getOaReq().getReqType(); // 表oa_req.REQ_TYPE 对应 ProjectTemplateTask.TASK_ID
		if (!mapByTaskId_.containsKey(taskId)) {
			List<ProjectNodeData> lst = new ArrayList<ProjectNodeData>();
			mapByTaskId_.put(taskId, lst);
		}
		mapByTaskId_.get(taskId).add(nodeData);
	}

	/**
	 * 建立父子关系
	 * 
	 * @param nodeData
	 * @return
	 */
	public int buildParent(ProjectNodeData nodeData) {
		Integer pid = nodeData.getOaReq().getReqPid();
		if (pid == null || pid == 0) {
			return 0;
		}

		ProjectNodeData parent = this.getByReqId(pid);
		if (parent == null) {
			return -1;
		}

		parent.getChildren().add(nodeData);
		nodeData.setParent(parent);

		return 1;
	}

	/**
	 * 根据 taskId 获取 NodeData
	 * 
	 * @param taskId ProjectTemplateTask.taskId_
	 * @return
	 */
	public ProjectNodeData getByTaskIdRepeatKey(String taskId, String repeateKey) {
		List<ProjectNodeData> lst = this.getByTaskId(taskId);
		if (lst == null) {
			return null;
		}

		for (int i = 0; i < lst.size(); i++) {
			ProjectNodeData o = lst.get(i);
			String key = o.getOaReq().getRefTab();
			if (key == null) {
				if (repeateKey == null) {
					return o;
				}
			} else {
				if (key.equals(repeateKey)) {
					return o;
				}
			}
		}
		return null;
	}

	/**
	 * 根据 taskId 获取 NodeData
	 * 
	 * @param taskId ProjectTemplateTask.taskId_
	 * @return
	 */
	public List<ProjectNodeData> getByTaskId(String taskId) {
		if (mapByTaskId_.containsKey(taskId)) {
			return mapByTaskId_.get(taskId);
		} else {
			return null;
		}
	}

	/**
	 * 根据 REQ_ID获取 NodeData
	 * 
	 * @param reqId 表OA_REQ的主键
	 * @return
	 */
	public ProjectNodeData getByReqId(int reqId) {
		if (mapByReqId_.containsKey(reqId)) {
			return mapByReqId_.get(reqId);
		} else {
			return null;
		}
	}

	/**
	 * 项目编号
	 * 
	 * @return
	 */
	public int getPrjId() {
		return prjId_;
	}

	/**
	 * 根据REQ_ID索引的数据
	 * 
	 * @return
	 */
	public Map<Integer, ProjectNodeData> getMapByReqId() {
		return mapByReqId_;
	}

	/**
	 * 根据 ReqType索引的数据
	 * 
	 * @return
	 */
	public Map<String, List<ProjectNodeData>> getMapByTaskId() {
		return mapByTaskId_;
	}
}
