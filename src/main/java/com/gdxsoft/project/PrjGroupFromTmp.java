package com.gdxsoft.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.web.dao.*;

public class PrjGroupFromTmp extends PrjFromTmpBase {
	private static Logger LOGGER = LoggerFactory.getLogger(PrjGroupFromTmp.class);
	public static String TEMPLATE = "Group.xml";
	private int grpId_;
	private GrpMain grpMain_;

	/**
	 * 检查已存在的 PRJ_MAIN的条件
	 * 
	 * @param grpId
	 * @return
	 */
	public static String existsProjectWhere(int grpId) {
		StringBuilder sb = new StringBuilder();
		sb.append("PRJ_REF_TABLE='GRP_MAIN' and PRJ_REF_ID='");
		sb.append(grpId);
		sb.append("' AND PRJ_SRC_XML_TEMP='");
		sb.append(TEMPLATE.replace("'", "''"));
		sb.append("'");
		String where = sb.toString();

		return where;
	}

	public PrjGroupFromTmp(RequestValue rv) {
		super(rv);
		String tempxmlpath = UPath.getRealPath() + "config/ProjectTemplate/" + TEMPLATE;
		ProjectTemplate tmp = new ProjectTemplate();
		tmp.loadTemplate(tempxmlpath);

		super.tmp_ = tmp;
	}

	public PrjGroupFromTmp(ProjectTemplate tmp, RequestValue rv) {
		super(tmp, rv);

	}

	public void run(int grpId) throws Exception {
		this.getPrjId(grpId);
		super.runMainData();
		super.checkOaReqsData();
	}

	/**
	 * 更新单个Task 和 所有相关的父子节点
	 * 
	 * @param grpId
	 * @param taskId
	 * @throws Exception 
	 */
	public void updateTask(int grpId, String taskId) throws Exception {
		this.getPrjId(grpId);
		super.buildTaskRelatives(taskId);
		super.runMainData();
		super.checkOaReqsData();
	}

	/**
	 * 检查项目是否存在
	 * 
	 * @param grpId 团编号
	 * @return
	 */
	public boolean checkExistsPrj(int grpId) {
		String where = existsProjectWhere(grpId);
		boolean isPrjMain = super.checkPrjMain(where);

		return isPrjMain;
	}

	/**
	 * 根据 GRP_ID 获取PRJ_ID
	 * 
	 * @param grpId
	 * @return
	 */
	public int getPrjId(int grpId) {
		this.grpId_ = grpId;

		GrpMainDao d = new GrpMainDao();
		this.grpMain_ = d.getRecord(this.grpId_);

		boolean isPrjMain = this.checkExistsPrj(grpId);

		String name = "项目：" + this.grpMain_.getGrpHyCode();
		String name_en = "PM：" + this.grpMain_.getGrpHyCode();
		if (!isPrjMain) {
			OaPrj prj = super.createBlankPrjMain();
			prj.setPrjName(name);
			prj.setPrjNameEn(name_en);

			prj.setPrjRefTable("GRP_MAIN");
			prj.setPrjRefId(this.grpId_ + "");

			prj.setPrjSrcXmlTemp(this.tmp_.getName());
			// 文件的hash，用于判断版本变化
			prj.setPrjSrcXmlHash(this.tmp_.getCfgFileHash());

			super.newOrUpdatePrjMain(prj);
		} else {
			OaPrj prj = super.getPrjMain();
			prj.startRecordChanged();
			// 可能出现 团号变更
			prj.setPrjName(name);
			prj.setPrjNameEn(name_en);

			int oldHash = 0;
			if (prj.getPrjSrcXmlHash() != null) {
				oldHash = prj.getPrjSrcXmlHash();
			}

			// 配置文件发生变化，将影响到 checkOaReqsData 是否强制完整扫描
			if (oldHash != this.tmp_.getCfgFileHash()) {
				LOGGER.info(TEMPLATE + "版本变化：" + oldHash + " -> " + this.tmp_.getCfgFileHash());
				super.setTmpVersionChanged(true);
				prj.setPrjSrcXmlHash(this.tmp_.getCfgFileHash());
			} else {
				LOGGER.info(TEMPLATE + "版本 ：" + oldHash);
				super.setTmpVersionChanged(false);
			}

			if (prj.getMapFieldChanged().size() > 0) {
				super.newOrUpdatePrjMain(prj);
			}
		}
		this.prjId_ = this.prjMain_.getPrjId();
		return this.prjMain_.getPrjId();
	}

}
