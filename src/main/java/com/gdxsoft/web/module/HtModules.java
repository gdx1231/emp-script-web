/**
 * 
 */
package com.gdxsoft.web.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class HtModules {
	private static Logger LOGGER = LoggerFactory.getLogger(HtModules.class);
	static Map<String, HtModule> MAP = new ConcurrentHashMap<>();

	public String name;
	public String memo;

	public void addModule(HtModule mod) {
		removeModule(mod.getName());
		MAP.put(mod.getName(), mod);
		LOGGER.debug("add new module ", mod.toString());
	}

	public HtModule getModule(String name) {
		if (!MAP.containsKey(name)) {
			return null;
		}
		HtModule m = MAP.get(name);

		try {
			return m.clone();
		} catch (CloneNotSupportedException e) {
			LOGGER.error(e.getLocalizedMessage());
			return null;
		}

	}

	public void removeModule(String name) {
		if (MAP.containsKey(name)) {
			HtModule mod = MAP.remove(name);
			LOGGER.debug("remove the module {}", mod.toString());
		}
	}

	public List<String> getNames() {
		Set<String> keys = MAP.keySet();
		List<String> lst = new ArrayList<>();
		keys.forEach(key -> {
			lst.add(key);
		});
		return lst;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
