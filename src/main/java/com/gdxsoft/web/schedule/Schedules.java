package com.gdxsoft.web.schedule;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.gdxsoft.easyweb.utils.UXml;

public class Schedules {
	private static Logger LOGGER = LoggerFactory.getLogger(Schedules.class);
	public static String PATH = "/schedule.xml";
	private static Schedules INST;

	public static Schedules getInstance(URL url) {
		if (INST != null) {
			return INST;
		}
		loadInstance(url);
		return INST;

	}

	private synchronized static void loadInstance(URL url) {
		Schedules ss = new Schedules();
		try {
			ss.loadCfgs(url);
			INST = ss;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return;
		}

	}

	private String connStr = "";
	private Map<String, Schedule> schedules = new HashMap<>();

	public void loadCfgs(URL url) throws IOException {
		Document doc = null;
		String xmlContent = IOUtils.toString(url, StandardCharsets.UTF_8);
		doc = UXml.asDocument(xmlContent);

		NodeList nl = doc.getElementsByTagName("schedule");
		for (int i = 0; i < nl.getLength(); i++) {
			Element ele = (Element) nl.item(i);

			Schedule s = new Schedule();
			s.init(ele);
			schedules.put(s.getId(), s);

		}
	}

	public void runSchedules() {
		for (String key : schedules.keySet()) {
			Schedule s = schedules.get(key);
			s.setParent(this);
			s.runSchedule();
		}
	}

	/**
	 * 数据库连接池
	 * 
	 * @return
	 */
	public String getConnStr() {
		return connStr;
	}

	/**
	 * 数据库连接池
	 * 
	 * @param connStr
	 */
	public void setConnStr(String connStr) {
		this.connStr = connStr;
	}
}
