package com.gdxsoft.web.weixin;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.gdxsoft.easyweb.data.DTRow;
import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.weixin.QyConfig;
import com.gdxsoft.weixin.QyDepartment;
import com.gdxsoft.weixin.QyStaff;

public class QyWeiXin {

	private QyConfig qy_;
	private RequestValue rv_;

	public QyWeiXin() {
	}

	/**
	 * 初始化
	 * 
	 * @param rv
	 * @throws Exception
	 */
	public void init(RequestValue rv) throws Exception {
		this.rv_ = rv;
		String sqlSupMain = "SELECT * FROM BAS_WX_CFG WHERE REL_SUP_UNID=@G_SUP_UNID AND WX_CFG_NO=@WX_CFG_NO";
		DataConnection g_cnn = new DataConnection();
		g_cnn.setConfigName("");
		g_cnn.setRequestValue(rv);

		DTTable tbSupMain = DTTable.getCachedTable(sqlSupMain, 100, g_cnn);
		g_cnn.close();

		if (tbSupMain.getCount() == 0) {
			throw new Exception("找不到业务微信配置数据");
		}
		String corpid = tbSupMain.getCell(0, "WX_APP_ID").toString();
		String corpsecret = tbSupMain.getCell(0, "WX_APP_SECRET").toString();
		String WX_CFG_TYPE = tbSupMain.getCell(0, "WX_CFG_TYPE").toString();
		if (WX_CFG_TYPE == null || !WX_CFG_TYPE.equals("WX_TYPE_GONGSIHAO")) {
			throw new Exception("非公司号");
		}

		if (tbSupMain.getCell(0, "WX_AGENT_ID").getValue() == null) {
			qy_ = QyConfig.instance(corpid, corpsecret);
		} else {
			// 企业自定义程序
			qy_ = QyConfig.instance(corpid, corpsecret, tbSupMain.getCell(0, "WX_AGENT_ID").toInt());
		}
		String tag = rv_.s("wx_cfg_no") + ".ADM";
		rv_.addValue("TAG_ADM", tag);

		String tag1 = rv.s("wx_cfg_no") + ".DEPT";
		rv.addValue("TAG_DEPT", tag1);
	}

	/**
	 * 获取部门信息列表，同时更新本地记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONObject departmentsList() throws Exception {
		List<QyDepartment> list = qy_.getWxDepartments();
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();

		obj.put("RST", true);
		obj.put("LIST", arr);

		String sql1 = "DELETE FROM ADM_USER_DEFAULT WHERE def_tag=@TAG_DEPT";
		DataConnection.updateAndClose(sql1, "", rv_);

		for (int i = 0; i < list.size(); i++) {
			QyDepartment dept = list.get(i);

			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO ADM_USER_DEFAULT(DEF_TAG, ADM_ID, DEF_VAL_TEXT, DEF_CDATE)");
			sb.append("VALUES(@TAG_DEPT, ");
			sb.append(dept.getId());
			sb.append(", '");
			sb.append(dept.toJson().toString().replace("'", "''"));
			sb.append("', @sys_date)");

			DataConnection.updateAndClose(sb.toString(), "", rv_);
			arr.put(dept.toJson());
		}

		return obj;
	}

	/**
	 * 移除部门在公司号的信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONObject departmentsDelete() throws Exception {
		StringBuilder sb1 = new StringBuilder();
		sb1.append("SELECT A.* FROM ADM_DEPT A INNER JOIN ADM_USER_DEFAULT B ");
		sb1.append("  ON A.DEP_ID = B.ADM_ID AND B. def_tag=@TAG_DEPT ");
		sb1.append(" WHERE SUP_ID=@G_SUP_ID and dep_id in (@ids_split) ");
		sb1.append("  ORDER BY DEP_LVL,DEP_ORD");

		DTTable tbDepts = DTTable.getJdbcTable(sb1.toString(), rv_);

		JSONObject obj = new JSONObject();
		if (tbDepts.getCount() == 0) {
			throw new Exception("没有需要删除的部门，已删除不用删除。");
		}

		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ADM_USER_DEFAULT WHERE def_tag=@TAG_DEPT and adm_id in(0 ");

		JSONArray arr = new JSONArray();

		obj.put("RST", true);
		obj.put("LIST", arr);

		int inc = 0;
		for (int i = tbDepts.getCount() - 1; i >= 0; i--) {
			int dep_id = tbDepts.getCell(i, "DEP_ID").toInt();
			String name = tbDepts.getCell(i, "DEP_NAME").toString();

			boolean rst = qy_.deleteWxDepartment(dep_id);
			JSONObject item = new JSONObject();
			item.put("RST", rst);
			item.put("DEP_ID", dep_id);
			item.put("DEP_NAME", name);

			arr.put(item);

			if (rst) {
				inc++;
				sb.append("," + dep_id);
			} else {
				item.put("ERR", qy_.getLastErr());
				qy_.setError(null);
			}
		}

		if (inc > 0) {
			sb.append(")");
			DataConnection.updateAndClose(sb.toString(), "", rv_);
		}

		return obj;
	}

	/**
	 * 创建部门在企业号
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONObject departmentsAdd() throws Exception {
		String sql = "SELECT A.* FROM ADM_DEPT A WHERE SUP_ID=@G_SUP_ID and dep_id in (@ids_split)"
				+ " and not dep_id in (select adm_id from ADM_USER_DEFAULT B where B. def_tag=@TAG_DEPT)"
				+ " ORDER BY DEP_LVL,DEP_ORD";

		DTTable tbDepts = DTTable.getJdbcTable(sql, rv_);
		JSONObject obj = new JSONObject();
		if (tbDepts.getCount() == 0) {
			throw new Exception("没有需要添加的部门，已添加不用重复添加。");
		}

		JSONArray arr = new JSONArray();

		obj.put("RST", true);
		obj.put("LIST", arr);

		int ord = 1000000;
		for (int i = 0; i < tbDepts.getCount(); i++) {
			DTRow r = tbDepts.getRow(i);

			ord--;

			QyDepartment dept = this.map2Department(r, ord);

			int id = qy_.createWxDepartment(dept);
			JSONObject item = new JSONObject();

			item.put("RST", id > 0);
			item.put("DEP_ID", dept.getId());
			item.put("DEP_NAME", dept.getName());

			arr.put(item);

			if (id < 0) {
				item.put("ERR", qy_.getLastErr());
				qy_.setError(null);
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append("INSERT INTO ADM_USER_DEFAULT(DEF_TAG, ADM_ID, DEF_VAL_TEXT, DEF_CDATE)");
				sb.append("VALUES(@TAG_DEPT, ");
				sb.append(dept.getId());
				sb.append(", '");
				sb.append(dept.toJson().toString().replace("'", "''"));
				sb.append("', @sys_date)");
				DataConnection.updateAndClose(sb.toString(), "", rv_);
			}
		}

		return obj;
	}

	/**
	 * 更新企业号的部门信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONObject departmentsUpdate() throws Exception {
		String sql = "SELECT A.* FROM ADM_DEPT A WHERE SUP_ID=@G_SUP_ID and dep_id in (@ids_split)"
				+ " and dep_id in (select adm_id from ADM_USER_DEFAULT B where B. def_tag=@TAG_DEPT)"
				+ " ORDER BY DEP_LVL,DEP_ORD";

		DTTable tbDepts = DTTable.getJdbcTable(sql, rv_);
		JSONObject obj = new JSONObject();
		if (tbDepts.getCount() == 0) {
			throw new Exception("没有需要更新的部门");
		}

		JSONArray arr = new JSONArray();

		obj.put("RST", true);
		obj.put("LIST", arr);

		int ord = 1000000;
		for (int i = 0; i < tbDepts.getCount(); i++) {
			DTRow r = tbDepts.getRow(i);

			ord--;

			QyDepartment dept = this.map2Department(r, ord);

			boolean id = qy_.updateWxDepartment(dept);
			JSONObject item = new JSONObject();

			item.put("RST", id);
			item.put("DEP_ID", dept.getId());
			item.put("DEP_NAME", dept.getName());

			arr.put(item);

			if (!id) {
				item.put("ERR", qy_.getLastErr());
				qy_.setError(null);
			}
		}

		return obj;
	}

	private QyDepartment map2Department(DTRow r, int ord) throws Exception {
		int dep_id = r.getCell("DEP_ID").toInt();
		int pid = r.getCell("DEP_PID").toInt();
		if (pid == 0) {
			pid = 1;
		}
		String name = r.getCell("DEP_NAME").toString();

		QyDepartment dept = new QyDepartment();
		dept.setId(dep_id);
		dept.setName(name);
		dept.setOrder(ord);
		dept.setParentid(pid);

		return dept;
	}

	/**
	 * 获取已存在用户列表，并记录在本地
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONObject staffsList() throws Exception {
		List<QyStaff> list = qy_.getDepartmentStaffs(1, true, 0, false);
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ADM_USER_DEFAULT(DEF_TAG, ADM_ID, DEF_VAL_STR, DEF_VAL_TEXT, DEF_CDATE)\n");
		sb.append("VALUES(@TAG_ADM, @P_ADM_ID,@P_USER_ID, @P_JSON, @sys_date)");
		String sqlNew = sb.toString();

		// 先删除
		String sql0 = "DELETE from ADM_USER_DEFAULT where DEF_TAG = @TAG_ADM";
		DataConnection.updateAndClose(sql0, "", rv_);

		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();

		obj.put("RST", true);
		obj.put("LIST", arr);

		for (int i = 0; i < list.size(); i++) {
			QyStaff staff = list.get(i);

			JSONObject item = new JSONObject();
			arr.put(item);

			int adm_id = -1;
			try {
				adm_id = Integer.parseInt(staff.getUserid());
				String sqlExp = "select adm_id from adm_user where ADM_ID=" + adm_id;
				DTTable tbExp = DTTable.getJdbcTable(sqlExp);
				if (tbExp.getCount() == 0) {
					item.put("RST", false);
					item.put("ERR", staff.toJson());
					continue;
				}
			} catch (Exception err) {
				if (staff.getEmail() == null) {
					staff.setEmail("");
				}

				StringBuilder sb1 = new StringBuilder();
				sb1.append("select adm_id from adm_user where adm_name='");
				sb1.append(staff.getName().replace("'", "''"));
				sb1.append("' and (adm_mobile='");
				sb1.append(staff.getMobile().replace("'", "''"));
				sb1.append("' or adm_email='");
				sb1.append(staff.getEmail().replace("'", "''"));
				sb1.append("')");

				DTTable tbExp = DTTable.getJdbcTable(sb1.toString());

				if (tbExp.getCount() == 0) {
					item.put("RST", false);
					item.put("ERR", staff.toJson());
					continue;
				}
				adm_id = tbExp.getCell(0, 0).toInt();
			}

			rv_.getPageValues().remove("P_ADM_ID");
			rv_.getPageValues().remove("P_JSON");
			rv_.getPageValues().remove("P_USER_ID");

			rv_.addValue("P_ADM_ID", adm_id);
			rv_.addValue("P_JSON", staff.toJson());
			rv_.addValue("P_USER_ID", staff.getUserid());

			DataConnection.updateAndClose(sqlNew, "", rv_);

			item.put("RST", true);
			item.put("ADM_ID", adm_id);
			item.put("ADM_NAME", staff.getName());
			item.put("JSON", staff.toJson());
		}

		return obj;
	}

	/**
	 * 用户映射到微信企业号里
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONObject staffsAdd() throws Exception {
		StringBuilder sb1 = new StringBuilder();
		sb1.append("SELECT * FROM V_ADM_USER_POST_DEPT WHERE ");
		sb1.append(" NOT ADM_ID IN (select adm_id from ADM_USER_DEFAULT B where B. def_tag=@TAG_ADM)  ");
		sb1.append(" AND SUP_ID=@G_SUP_ID AND ADM_ID in (@ids_split)");
		String sql = sb1.toString();

		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ADM_USER_DEFAULT(DEF_TAG, ADM_ID, DEF_VAL_STR, DEF_VAL_TEXT, DEF_CDATE)\n");
		sb.append("VALUES(@TAG_ADM, @P_ADM_ID,@P_USER_ID, @P_JSON, @sys_date)");
		String sqlNew = sb.toString();

		DTTable tbDepts = DTTable.getJdbcTable(sql, rv_);

		JSONObject obj = new JSONObject();
		if (tbDepts.getCount() == 0) {
			throw new Exception("没有需要同步的用户，已同步不再重复同步！");
		}

		JSONArray arr = new JSONArray();

		obj.put("RST", true);
		obj.put("LIST", arr);

		for (int i = 0; i < tbDepts.getCount(); i++) {
			DTRow r = tbDepts.getRow(i);
			String adm_id = r.getCell("ADM_ID").toString();

			QyStaff dept = map2Staff(r);

			boolean id = qy_.createWxStaff(dept);

			JSONObject item = new JSONObject();

			item.put("RST", id);
			item.put("ADM_ID", adm_id);
			item.put("ADM_NAME", dept.getName());

			arr.put(item);

			if (id) {
				rv_.getPageValues().remove("P_ADM_ID");
				rv_.getPageValues().remove("P_JSON");
				rv_.getPageValues().remove("P_USER_ID");

				rv_.addValue("P_ADM_ID", adm_id);
				rv_.addValue("P_JSON", dept.toJson());
				rv_.addValue("P_USER_ID", dept.getUserid());

				DataConnection.updateAndClose(sqlNew, "", rv_);
			} else {
				item.put("ERR", qy_.getLastErr());
				qy_.setError(null);
			}
		}

		return obj;
	}

	/**
	 * 更新用户信息到微信企业号里
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONObject staffsUpdate() throws Exception {

		StringBuilder sb1 = new StringBuilder();
		sb1.append("SELECT A.*,B.DEF_VAL_STR FROM V_ADM_USER_POST_DEPT A");
		sb1.append(" INNER JOIN ADM_USER_DEFAULT B ON A.ADM_ID=B.ADM_ID AND B.def_tag=@TAG_ADM");
		sb1.append(" WHERE SUP_ID=@G_SUP_ID AND a.ADM_ID in (@ids_split)");

		DTTable tbDepts = DTTable.getJdbcTable(sb1.toString(), rv_);

		JSONObject obj = new JSONObject();
		if (tbDepts.getCount() == 0) {
			throw new Exception("没有需要同步的用户");
		}

		JSONArray arr = new JSONArray();

		obj.put("RST", true);
		obj.put("LIST", arr);

		for (int i = 0; i < tbDepts.getCount(); i++) {
			DTRow r = tbDepts.getRow(i);
			String adm_id = r.getCell("ADM_ID").toString();
			String weixin_user_id = r.getCell("DEF_VAL_STR").toString();
			QyStaff dept = map2Staff(r);
			// 针对创建人会有默认的用户编号，特殊处理
			if (weixin_user_id != null && !weixin_user_id.equals(adm_id)) {
				dept.setUserid(weixin_user_id);
			}
			boolean id = qy_.updateWxStaff(dept);

			JSONObject item = new JSONObject();

			item.put("RST", id);
			item.put("ADM_ID", adm_id);
			item.put("ADM_NAME", dept.getName());

			arr.put(item);

			if (!id) {
				item.put("ERR", qy_.getLastErr());
				qy_.setError(null);
			}
		}

		return obj;
	}

	/**
	 * 删除用户同步
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONObject staffsDelete() throws Exception {

		StringBuilder sb1 = new StringBuilder();
		sb1.append("SELECT A.ADM_NAME, A.ADM_ID, B.DEF_VAL_STR FROM ADM_USER A ");
		sb1.append(" INNER JOIN ADM_USER_DEFAULT B ON A.ADM_ID = B.ADM_ID AND B. def_tag=@TAG_ADM ");
		sb1.append(" WHERE A.SUP_ID=@G_SUP_ID and A.ADM_ID IN (@ids_split)");
		String sql = sb1.toString();

		DTTable tbDepts = DTTable.getJdbcTable(sql, rv_);

		if (tbDepts.getCount() == 0) {
			throw new Exception("没有需要删除的用户，已删除不再重复删除！");
		}

		JSONObject obj = new JSONObject();
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ADM_USER_DEFAULT WHERE def_tag=@TAG_ADM and adm_id in(0 ");
		int inc = 0;

		JSONArray arr = new JSONArray();
		obj.put("RST", true);
		obj.put("LIST", arr);
		for (int i = 0; i < tbDepts.getCount(); i++) {
			String user_id = tbDepts.getCell(i, "DEF_VAL_STR").toString();
			int adm_id = tbDepts.getCell(i, "adm_id").toInt();
			String name = tbDepts.getCell(i, "ADM_NAME").toString();
			if (user_id.equals("19")) {
				continue;
			}

			boolean rst = qy_.deleteWxStaff(user_id);

			JSONObject item = new JSONObject();
			item.put("RST", rst);
			item.put("ADM_ID", adm_id);
			item.put("ADM_NAME", name);
			arr.put(item);

			if (rst) {
				inc++;
				sb.append("," + adm_id);
			} else {
				item.put("ERR", qy_.getLastErr());
				qy_.setError(null);
			}
		}

		if (inc > 0) {
			sb.append(")");
			DataConnection.updateAndClose(sb.toString(), "", rv_);
		}

		return obj;
	}

	/**
	 * 映射用户信息
	 * 
	 * @param r
	 * @return
	 * @throws Exception
	 */
	private QyStaff map2Staff(DTRow r) throws Exception {
		QyStaff dept = new QyStaff();

		String adm_id = r.getCell("ADM_ID").toString();
		dept.setUserid(adm_id);

		String name = r.getCell("ADM_NAME").toString();
		dept.setName(name);

		String mobile = r.getCell("ADM_MOBILE").toString();
		if (mobile == null)
			mobile = "";
		mobile = mobile.replace("\\", ";").replace("/", ";").split(";")[0];

		if (mobile.startsWith("+86")) {
			mobile = mobile.replace("+86", "").trim();
		}
		mobile = mobile.replace("(", "").replace(")", "").replace("-", "");
		mobile = mobile.replace(" ", "");
		dept.setMobile(mobile);

		String email = r.getCell("ADM_EMAIL").toString();
		dept.setEmail(email);

		String telephone = r.getCell("ADM_TELE").toString();
		dept.setTelephone(telephone);

		String englist_name = r.getCell("ADM_NAME_EN").toString();
		dept.setEnglishName(englist_name);

		String position = r.getCell("DEP_POS_NAME").toString();
		dept.setPosition(position);

		String POS_IS_MASTER = r.getCell("POS_IS_MASTER").toString();
		int is_leader = 0;
		if (POS_IS_MASTER != null && POS_IS_MASTER.equals("Y")) {
			is_leader = 1;
		}
		dept.setIsleader(is_leader);

		int dep_id = r.getCell("adm_dep_id").toInt();
		int[] depts = new int[1];
		depts[0] = dep_id;
		dept.setDepartment(depts);

		dept.setEnable(4);

		String sex = r.getCell("sex_tag").toString();
		if (sex == null || sex.equals("U")) {
			dept.setGender(0);
		} else if (sex.equals("F")) {
			dept.setGender(2);
		} else {
			dept.setGender(1);
		}

		return dept;
	}

	/**
	 * @return the qy_
	 */
	public QyConfig getQy() {
		return qy_;
	}

	/**
	 * @return the rv_
	 */
	public RequestValue getRv() {
		return rv_;
	}
}
