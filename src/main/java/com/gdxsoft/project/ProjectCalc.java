package com.gdxsoft.project;

import java.util.Date;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ProjectCalc {
	private static Logger LOGGER = LoggerFactory.getLogger(ProjectCalc.class);

	public static Double computeDoubleExp(String computeExp, Double val) throws Exception {
		if (computeExp == null || computeExp.trim().length() == 0) {
			return val;
		}
		if (val == null) {
			return null;
		}
		String js = "0" + computeExp;
		ScriptEngine engine = getScriptEngine();

		// 执行JavaScript代码
		try {
			Object o = engine.eval(js);
			if (o == null) {
				LOGGER.error(js);
				return null;
			}
			return Double.parseDouble(o.toString());
		} catch (ScriptException e) {
			LOGGER.error(e.getLocalizedMessage());
			LOGGER.error(js);
			return null;
		}
	}

	public static Integer computeIntExp(String computeExp, Integer val) throws Exception {
		if (computeExp == null || computeExp.trim().length() == 0) {
			return val;
		}
		if (val == null) {
			return null;
		}
		String js = "0" + computeExp;
		ScriptEngine engine = getScriptEngine();
		// 执行JavaScript代码
		try {
			Object o = engine.eval(js);
			if (o == null) {
				LOGGER.error(js);
				return null;
			}
			return Integer.parseInt(o.toString());
		} catch (ScriptException e) {
			LOGGER.error(e.getLocalizedMessage());
			LOGGER.error(js);
			return null;
		}
	}

	public static Date computeDateExp(String computeExp, Date date) throws Exception {
		if (computeExp == null || computeExp.trim().length() == 0) {
			return date;
		}
		if (date == null) {
			return null;
		}
		String js = "0" + computeExp;
		// 获取JavaScript执行引擎
		ScriptEngine engine = getScriptEngine();
		// 执行JavaScript代码
		try {
			Object o = engine.eval(js);
			if (o == null) {
				LOGGER.error(js);
				return null;
			}
			long day = Long.parseLong(o.toString());
			long t1 = day * 24 * 3600 * 1000;
			Date d = new Date(t1 + date.getTime());
			return d;
		} catch (ScriptException e) {
			LOGGER.error(e.getLocalizedMessage());
			LOGGER.error(js);
			return null;
		}
	}

	/**
	 * 获取 javascript引擎
	 * 
	 * @return
	 * @throws Exception @SuppressWarnings("removal")
	 */
	public static ScriptEngine getScriptEngine() throws Exception {
		ScriptEngineManager m = new ScriptEngineManager();
		// jdk11不再支持 Nashorn,使用 GraalJS代替
		// https://www.graalvm.org/
		// GraalVM removes the isolation between programming languages and enables
		// interoperability in a shared runtime. It can run either standalone or in the
		// context of OpenJDK, Node.js or Oracle Database.
		ScriptEngine engine = m.getEngineByName("graal.js");
		if (engine == null) {
			engine = m.getEngineByName("ECMAScript");
		}
		if (engine != null) {
			return engine;
		} else {
			throw new Exception("JavaScript/nashorn  引擎找不到");
		}
	}
}
