package com.gdxsoft.project;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gdxsoft.easyweb.datasource.UpdateChanges;
import com.gdxsoft.easyweb.log.ILog;
import com.gdxsoft.easyweb.log.LogBase;
import com.gdxsoft.easyweb.script.RequestValue;

/**
 * 记录项目的任务修改日志
 * 
 * @author admin
 *
 */
public class ProjectOaReqLog extends LogBase implements ILog {
	private static Logger LOGGER = LoggerFactory.getLogger(ProjectOaReqLog.class);

	public void Write() {

		List<UpdateChanges> changes = null;
		try {
			changes = super.getCreator().getHtmlClass().getAction().getLstChanges();

		} catch (Exception e1) {
			LOGGER.error(e1.getLocalizedMessage());
			return;
		}

		if (changes == null || changes.size() == 0) {
			return;
		}
		RequestValue rv = super.getCreator().getRequestValue();
		int prj_id = rv.getInt("PRJ_ID");
		ProjectManager pm = new ProjectManager(prj_id, rv);

		for (int i = 0; i < changes.size(); i++) {
			UpdateChanges ucs = changes.get(i);
			if (ucs.getSqlPart().getTableName().equalsIgnoreCase("OA_REQ")) {
				pm.logChange(ucs);
			} else {
				// 没有定义处理方式
				LOGGER.error(ucs.getSqlPart().getTableName());
			}
		}

	}

}
