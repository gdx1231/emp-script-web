/**
 * 
 */
package com.gdxsoft.web.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.gdxsoft.easyweb.data.DTRow;
import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.PageValue;
import com.gdxsoft.easyweb.script.PageValueTag;
import com.gdxsoft.easyweb.script.RequestValue;

/**
 * @author admin
 *
 */
public class Schedule {
	private static Logger LOGGER = LoggerFactory.getLogger(Schedule.class);
	private RequestValue rv;
	private Map<String, PageValue> paras = new HashMap<>();
	private List<Execute> executes = new ArrayList<>();

	private String checkSql;
	// 检查主表 schedule_main 的 s_check_val 的SQL表达式，作为变量 schedule_check_value，固定名称
	private String checkValue;
	private String checkValueContent;

	private String name;
	private String id;

	private int sIndex;
	private Schedules parent;

	public boolean runSchedule() {
		if (StringUtils.isBlank(this.checkSql)) {
			LOGGER.error("检查语句为空<{}>", this.name);
			return false;
		}
		if (StringUtils.isBlank(this.checkValue)) {
			LOGGER.error("检查值语句为空<{}>", this.name);
			return false;
		}
		if (executes.size() == 0) {
			LOGGER.error("执行语句为空<{}>", this.name);
			return false;
		}

		try {
			List<DTTable> tables = DataConnection.runMultiSqlsAndClose(checkValue, parent.getConnStr(), rv.clone());
			if (tables.size() == 0) {
				LOGGER.error("没有返回数据表 <{}>", this.checkValue);
				return false;
			}
			// 最后一个表的第0行，第0个字段
			checkValueContent = tables.get(tables.size() - 1).getCell(0, 0).toString();

			// 用于check执行
			this.rv.addOrUpdateValue("schedule_check_value", checkValueContent);

		} catch (Exception e) {
			LOGGER.error("执行错误 {}, <{}>", e.getMessage(), this.checkValue);
			return false;
		}

		DTTable dtCheck = null;
		try {
			List<DTTable> tables = DataConnection.runMultiSqlsAndClose(checkSql, parent.getConnStr(), rv.clone());
			if (tables.size() == 0) {
				LOGGER.error("没有返回数据表 <{}>", this.checkSql);
				return false;
			}
			// 最后一个表
			dtCheck = tables.get(tables.size() - 1);
		} catch (Exception e) {
			LOGGER.error("执行错误 {}, <{}>", e.getMessage(), this.checkSql);
			return false;
		}

		if (dtCheck.getCount() == 0) {
			return true;
		}

		this.recordMain();
		for (int i = 0; i < dtCheck.getCount(); i++) {
			this.executeRow(dtCheck.getRow(i));
		}

		return true;
	}

	/**
	 * 记录主表
	 * 
	 * @return
	 */
	private int recordMain() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO schedule_main (\n");
		sb.append("s_id, s_name, s_check_val, s_check_time, s_check_sql, s_result, s_error) VALUES (\n");
		sb.append("@schedule_id, @schedule_name, @schedule_check_value, @sys_date, @schedule_check_sql, 'OK', null)");
		String sql = sb.toString();
		this.sIndex = DataConnection.insertAndReturnAutoIdInt(sql, parent.getConnStr(), rv);

		rv.addOrUpdateValue("schedule_main_index", sIndex);
		return sIndex;
	}

	private int recordSub(Execute kv, String errMessge) {
		RequestValue rvClone = this.rv.clone();
		rvClone.resetDateTime();
		rvClone.resetSysUnid();

		if (StringUtils.isBlank(errMessge)) {
			rvClone.addOrUpdateValue("schedule_sub_result", "OK");
		} else {
			rvClone.addOrUpdateValue("schedule_sub_error", errMessge);
			rvClone.addOrUpdateValue("schedule_sub_result", "ERR");
		}
		rvClone.addOrUpdateValue("schedule_sub_id", kv.getId());
		rvClone.addOrUpdateValue("schedule_sub_name", kv.getName());
		rvClone.addOrUpdateValue("schedule_sub_sql", kv.getSql());
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO oa_work.schedule_sub(\n");
		sb.append("s_index, s_sub_id, s_sub_name, s_sub_sql, s_sub_result, s_sub_time, s_sub_error)VALUES(\n");
		sb.append("@schedule_main_index, @schedule_sub_id, @schedule_sub_name, @schedule_sub_sql");
		sb.append(", 'OK', @sys_date, @schedule_sub_error)");
		String sql = sb.toString();

		int subIndex = DataConnection.insertAndReturnAutoIdInt(sql, parent.getConnStr(), rvClone);

		return subIndex;
	}

	private void executeRow(DTRow r) {
		for (int i = 0; i < this.executes.size(); i++) {
			Execute kv = this.executes.get(i);

			// 为了避免相互干扰，每次重建rv
			RequestValue rv1 = rv.clone();
			rv1.resetDateTime();
			rv1.resetSysUnid();
			rv1.addValues(r);

			try {
				this.executeSub(kv, rv1);
				this.recordSub(kv, null);
			} catch (Exception e) {
				this.recordSub(kv, e.getMessage());
			}
		}

	}

	private List<DTTable> executeSub(Execute kv, RequestValue rvClone) throws Exception {
		String sql = kv.getSql();

		List<DTTable> tables = DataConnection.runMultiSqlsAndClose(sql, parent.getConnStr(), rvClone);

		return tables;
	}

	public void init(Element ele) {
		rv = new RequestValue();

		this.id = ele.getAttribute("id");
		// 作为变量 schedule_id
		rv.addOrUpdateValue("schedule_id", id);

		this.name = ele.getAttribute("name");
		// 作为变量 schedule_name
		rv.addOrUpdateValue("schedule_name", name);

		this.checkSql = this.getText(ele, "checkSql");
		// 作为变量 schedule_check
		rv.addOrUpdateValue("schedule_check_sql", checkSql);

		// 检查主表 schedule_main 的 s_check_val 的SQL表达式，作为变量 schedule_check_value，固定名称
		this.checkValue = this.getText(ele, "checkValue");
		rv.addOrUpdateValue("schedule_check_value_sql", checkValue);

		NodeList ps = ele.getElementsByTagName("para");
		for (int i = 0; i < ps.getLength(); i++) {
			Element e = (Element) ps.item(i);
			String name = e.getAttribute("name");
			String value = e.getAttribute("value");
			String dataType = e.getAttribute("dataType");

			PageValue pv = new PageValue(name, value);
			pv.setDataType(dataType);

			paras.put(name.toUpperCase(), pv);
			
			// 便于权限判断
			pv.setPVTag(PageValueTag.SESSION);

			rv.addValue(pv);
		}
		ps = ele.getElementsByTagName("execute");
		for (int i = 0; i < ps.getLength(); i++) {
			Element e = (Element) ps.item(i);
			String id = e.getAttribute("id");
			String name = e.getAttribute("name");
			String sql = e.getTextContent();

			Execute kvp = new Execute();
			kvp.setId(id);
			kvp.setName(name);
			kvp.setSql(sql);

			executes.add(kvp);
		}
	}

	private String getText(Element ele, String tag) {
		NodeList nl = ele.getElementsByTagName(tag);
		if (nl.getLength() == 0) {
			return null;
		}

		return nl.item(0).getTextContent().trim();
	}

	/**
	 * @return the rv
	 */
	public RequestValue getRv() {
		return rv;
	}

	/**
	 * @return the paras
	 */
	public Map<String, PageValue> getParas() {
		return paras;
	}

	/**
	 * @return the executes
	 */
	public List<Execute> getExecutes() {
		return executes;
	}

	/**
	 * @return the check
	 */
	public String getCheckSql() {
		return checkSql;
	}

	/**
	 * @return the checkValue
	 */
	public String getCheckValue() {
		return checkValue;
	}

	/**
	 * @return the checkValueContent
	 */
	public String getCheckValueContent() {
		return checkValueContent;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the sIndex
	 */
	public int getsIndex() {
		return sIndex;
	}

	public void setParent(Schedules schedules) {
		this.parent = schedules;
	}
}
