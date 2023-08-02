package com.gdxsoft.project;

import java.util.ArrayList;
import java.util.Date;

import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.web.dao.*;

public class ProjectBase {
	int prjId_;

	int supId_;
	int admId_;
	RequestValue rv_;

	OaPrj prjMain_;
	ProjectManager pm_;

	public ProjectBase(RequestValue rv) {
		this.supId_ = rv.getInt("g_sup_Id");
		this.admId_ = rv.getInt("g_adm_Id");
		this.rv_ = rv;

	}

	/**
	 * 创建或获取 项目信息
	 * 
	 * @param prjRefTable 来源参考
	 * @param prjRefId    来源Id
	 * @return
	 */
	public int createOrGetProject(String prjRefTable, String prjRefId) {
		return this.createOrGetProject(prjRefTable, prjRefId, null);
	}

	/**
	 * 创建或获取 项目信息
	 * 
	 * @param prjRefTable 来源参考
	 * @param prjRefId    来源Id
	 * @param xmlTemplate 来源模板
	 * @return
	 */
	public int createOrGetProject(String prjRefTable, String prjRefId, String xmlTemplate) {
		StringBuilder sb = new StringBuilder();
		sb.append("PRJ_REF_TABLE ='");
		sb.append(prjRefTable.replace("'", "''"));
		sb.append("' and prj_Ref_Id='");
		sb.append(prjRefId.replace("'", "''"));
		sb.append("' and sup_id=");
		sb.append(this.getSupId());

		if (xmlTemplate != null) {
			sb.append(" and  PRJ_SRC_XML_TEMP='");
			sb.append(xmlTemplate.replace("'", "''"));
			sb.append("'");
		}

		String where = sb.toString();
		if (this.checkPrjMain(where)) {
			return this.getPrjId();
		}

		OaPrj pm = this.createBlankPrjMain();

		pm.setPrjRefId(prjRefId);
		pm.setPrjRefTable(prjRefTable);
		pm.setPrjUnid(Utils.getGuid());
		pm.setPrjName("");
		pm.setPrjMasterId(this.getAdmId());
		pm.setPrjSrcXmlTemp(xmlTemplate);

		this.newOrUpdatePrjMain(pm);

		//将PRJ_ID放到主参数表中
		if (this.getRv() != null) {
			this.getRv().addValue("PRJ_ID", this.getPrjId());
		}

		return this.getPrjId();
	}

	/**
	 * 更新 项目状态 PRJ_STATUS = 'USED'
	 * 
	 * @param prjId
	 */
	public void updateProjectStatus() {
		OaPrj prj = this.getPrjMain();
		if (!"USED".equals(prj.getPrjStatus())) {
			prj.startRecordChanged();
			prj.setPrjStatus("USED");
			prj.setPrjMdate(new Date());

			this.newOrUpdatePrjMain(prj);
		}
	}

	/**
	 * 检查项目信息是否存在
	 * 
	 * @param where
	 * @return
	 */
	public boolean checkPrjMain(String where) {
		OaPrjDao d0 = new OaPrjDao();
		d0.setRv(rv_);

		ArrayList<OaPrj> al0 = d0.getRecords(where);
		if (al0.size() == 0) {
			return false;
		}
		this.prjMain_ = al0.get(0);

		this.pm_ = new ProjectManager(prjMain_.getPrjId(), this.rv_);

		return true;
	}

	/**
	 * 创建一个基础的项目信息
	 * 
	 * @return
	 */
	public OaPrj createBlankPrjMain() {
		OaPrj p = new OaPrj();
		p.setAdmId(this.admId_);
		p.setSupId(this.supId_);
		p.setPrjCdate(new Date());
		p.setPrjStatus("USED");

		return p;
	}

	/**
	 * 创建或更新prj_main
	 * 
	 * @param prj
	 * @return true新建，false更新
	 */
	public boolean newOrUpdatePrjMain(OaPrj prj) {
		OaPrjDao d0 = new OaPrjDao();

		if (prj.getPrjId() == null) {
			d0.newRecord(prj);
			this.prjMain_ = prj;
			this.pm_ = new ProjectManager(prj.getPrjId(), this.rv_);
			return true;
		} else {
			d0.updateRecord(prj, prj.getMapFieldChanged());
			return false;
		}
	}

	/**
	 * 项目类
	 * 
	 * @return
	 */
	public OaPrj getPrjMain() {
		return prjMain_;
	}

	/**
	 * 项目编号
	 * 
	 * @return
	 */
	public int getPrjId() {
		return this.getPrjMain().getPrjId();
	}

	public int getSupId() {
		return supId_;
	}

	public void setSupId(int supId) {
		this.supId_ = supId;
	}

	public int getAdmId() {
		return admId_;
	}

	public void setAdmId(int admId) {
		this.admId_ = admId;
	}

	public RequestValue getRv() {
		return rv_;
	}

	public void setRv(RequestValue rv) {
		this.rv_ = rv;
	}

	public void setPrjMain(OaPrj prjMain) {
		this.prjMain_ = prjMain;
	}

	/**
	 * 项目管理类
	 * 
	 * @return
	 */
	public ProjectManager getPm() {
		return pm_;
	}

	public void setPm(ProjectManager pm) {
		this.pm_ = pm;
	}
}
