package com.gdxsoft.web.websocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.gdxsoft.easyweb.utils.UFile;
import com.gdxsoft.easyweb.utils.UObjectValue;
import com.gdxsoft.easyweb.utils.UXml;

/**
 * 通过配置文件加载消息处理的方法
 * 
 * @author admin
 *
 */
public class LoadHandleMessage {
	public static String CFG_PATH = "ewa-websocket-handle-message.xml";
	private static Logger LOGGER = LoggerFactory.getLogger(LoadHandleMessage.class);
	private static Map<String, String> CLASS_MAP;
	private static Map<String, Integer> HASH_MAP = new ConcurrentHashMap<String, Integer>();

	static {
		try {
			String xmlContent = UFile.readFileText(CFG_PATH);
			initClassMap(xmlContent);
			HASH_MAP.put(CFG_PATH, xmlContent.hashCode());
		} catch (Exception e) {
			LOGGER.error("Init the ewa-websocket-handle-message error");
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * 获取方法对应的类
	 * 
	 * @param methodName
	 * @return
	 */
	public static IHandleMsg getInstance(String methodName, IndexWebSocket webSocket, JSONObject obj) {

		String name1 = methodName.trim().toUpperCase();
		if (!CLASS_MAP.containsKey(name1)) {
			return null;
		}

		String className = CLASS_MAP.get(name1);

		UObjectValue ov = new UObjectValue();

		Object[] constructorParameters = new Object[2];
		constructorParameters[0] = webSocket;
		constructorParameters[1] = obj;

		Object classLoaded = ov.loadClass(className, constructorParameters);

		if (classLoaded == null) {
			return null;
		}
		IHandleMsg instance = (IHandleMsg) classLoaded;

		return instance;
	}

	private synchronized static void initClassMap(String xmlContent) {
		Document doc = null;
		doc = UXml.asDocument(xmlContent);

		CLASS_MAP = new ConcurrentHashMap<String, String>();
		NodeList nl = doc.getElementsByTagName("handleMessage");
		for (int i = 0; i < nl.getLength(); i++) {
			Element ele = (Element) nl.item(i);
			String method = ele.getAttribute("method").toUpperCase().trim();
			String className = ele.getAttribute("class").trim();
			CLASS_MAP.put(method, className);
			LOGGER.info(method + ": " + className);
		}

	}
}
