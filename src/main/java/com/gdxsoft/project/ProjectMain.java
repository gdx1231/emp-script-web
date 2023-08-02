package com.gdxsoft.project;


import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UPath;

public class ProjectMain extends ProjectBase {

	private PrjGroupFromTmp PrjGroupFromTmp_;

	

	public ProjectMain(RequestValue rv) {
		super(rv);
	}


	public int createProjectFromCamp(int campId) {
		DataConnection cnn = new DataConnection(super.getRv());

		StringBuilder sb = new StringBuilder();
		sb.append("insert into oa_prj(PRJ_NAME,PRJ_CDATE, PRJ_MEMO,PRJ_UNID,SUP_ID");
		sb.append(" , ADM_ID,PRJ_STATUS,PRJ_REF_TABLE,PRJ_REF_ID)");
		sb.append("select camp_name,@sys_date,'自动创建', @sys_unid, @g_sup_id");
		sb.append(" , @g_adm_id, 'USED', 'CAMP_MAIN', @camp_id from camp_main where camp_id=");
		sb.append(campId);
		sb.append(" and sup_id=@g_sup_id");
		String sqlNew = sb.toString();

		int prj_id = cnn.executeUpdateReturnAutoIncrement(sqlNew);
		cnn.close();
		return prj_id;
	}

	/**
	 * 根据 GRP_ID 获取关联的 PRJ_ID
	 * 
	 * @param grpId
	 * @param rv
	 * @return
	 */
	public int getPrjIdFromGroup(int grpId) {
		String tempxmlpath = UPath.getRealPath() + "config/ProjectTemplate/" + PrjGroupFromTmp.TEMPLATE;

		ProjectTemplate tmp = new ProjectTemplate();
		tmp.loadTemplate(tempxmlpath);

		PrjGroupFromTmp pg = new PrjGroupFromTmp(tmp, super.getRv());

		int prj_id = pg.getPrjId(grpId);
		super.setPrjMain(pg.getPrjMain());

		this.PrjGroupFromTmp_ = pg;
		
		return prj_id;
	}

	/**
	 * 获取项目模板
	 * @return
	 */
	public PrjGroupFromTmp getPrjGroupFromTmp() {
		return PrjGroupFromTmp_;
	}

	
}
