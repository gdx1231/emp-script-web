/**
 * 
 */
package com.gdxsoft.web.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public class Modules {

	static Map<String, Module> MAP = new ConcurrentHashMap<>();

	public String name;
	public String memo;

	public void addModule(Module mod) {
		removeModule(mod.getName());
		MAP.put(mod.getName(), mod);
	}

	public Module getModule(String name) {
		return MAP.get(name);
	}

	public void removeModule(String name) {
		if (MAP.containsKey(name)) {
			MAP.remove(name);
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
