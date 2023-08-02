package com.gdxsoft.project;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.script.PageValueTag;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.web.dao.*;

/**
 * 销售年度任务分解，来源模板AnnualSales.xml
 * 
 * @author admin
 *
 */
public class PrjAnnualSalesFromTmp extends PrjFromTmpBase {
	private static ReentrantLock LOCK = new ReentrantLock();
	private static Logger LOGGER = LoggerFactory.getLogger(PrjAnnualSalesFromTmp.class);
	public static HashMap<String, Long> LOCK_MAP = new HashMap<String, Long>();

	public static String TEMPLATE = "AnnualSales.xml";

	public PrjAnnualSalesFromTmp(RequestValue rv) {
		super(rv);
		String tempxmlpath = UPath.getRealPath() + "config/ProjectTemplate/" + TEMPLATE;
		ProjectTemplate tmp = new ProjectTemplate();
		tmp.loadTemplate(tempxmlpath);

		super.tmp_ = tmp;
	}

	/**
	 * 获取项目信息
	 * 
	 * @param salesId 销售编号
	 * @param year    年度
	 * @return
	 */
	public int getPrjId(int salesId, int year) {

		AdmUserDao d1 = new AdmUserDao();
		AdmUser adm = d1.getRecord(salesId);
		String des = adm.getAdmName() + " " + super.getTmp().getDes();
		String des_en = adm.getAdmNameEn() + " " + super.getTmp().getDesEn();
		String refId = salesId + "," + year;
		String refTag = "ANNUAL_SALES_TARGET";
		int prj_id = super.createOrGetProject(refTag, refId, TEMPLATE);

		OaPrj prj = super.getPrjMain();
		prj.startRecordChanged();
		prj.setPrjName(des);
		prj.setPrjNameEn(des_en);
		prj.setPrjMasterId(salesId);

		super.newOrUpdatePrjMain(prj);

		return prj_id;
	}

	public void run(int salesId, int year) {
		String locke_id = salesId + "." + year;
		if (LOCK_MAP.containsKey(locke_id)) {
			long t0 = LOCK_MAP.get(locke_id);
			// 低于30s
			if (System.currentTimeMillis() - t0 < 30 * 1000) {
				LOGGER.info("Exist:" + locke_id);
				return;
			}
		}
		try {
			LOCK.lockInterruptibly();
			LOCK_MAP.put(locke_id, System.currentTimeMillis());
		} catch (InterruptedException e) {
			LOGGER.error(e.getLocalizedMessage());
			return;
		} finally {
			LOCK.unlock();
		}

		super.getRv().getPageValues().remove("year");
		super.getRv().addValue("year", year, PageValueTag.SYSTEM);
		
		super.getRv().getPageValues().remove("sales_id");
		super.getRv().addValue("sales_id", salesId, PageValueTag.SYSTEM);

		try {
			this.getPrjId(salesId, year);
			super.runMainData();
			super.checkOaReqsData();
		} catch (Exception err) {
			LOGGER.error(err.getLocalizedMessage());
		} finally {
			try {
				LOCK.lockInterruptibly();
				LOCK_MAP.remove(locke_id);
			} catch (InterruptedException e) {
				LOGGER.error(e.getLocalizedMessage());
				return;
			} finally {
				LOCK.unlock();
			}

		}
	}

}
