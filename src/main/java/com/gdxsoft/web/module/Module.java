package com.gdxsoft.web.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdxsoft.easyweb.script.HtmlControl;
import com.gdxsoft.easyweb.script.RequestValue;

public class Module {
	private String xmlName;
	private String itemName;
	private String parameters;
	private String memo;
	private String name;

	public Module(String xmlName, String itemName ) {
		this.xmlName = xmlName;
		this.itemName = itemName;
	}
	
	public Module(String xmlName, String itemName, String parameters) {
		this.xmlName = xmlName;
		this.itemName = itemName;
		this.parameters = parameters;
	}

	public HtmlControl createHtmlControl(RequestValue rv, HttpServletResponse response) {
		HtmlControl ht = new HtmlControl();
		ht.init(xmlName, itemName, parameters, rv, response);

		return ht;
	}

	public HtmlControl createHtmlControl(HttpServletRequest request, HttpServletResponse response) {
		HtmlControl ht = new HtmlControl();
		ht.init(xmlName, itemName, parameters, request, request.getSession(), response);

		return ht;
	}

	/**
	 * 配置文件的名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 配置文件的名称
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 配置文件的备注
	 * 
	 * @return
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * 配置文件的备注
	 * 
	 * @param memo 配置文件的备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 配置文件的XmlName
	 * 
	 * @return
	 */
	public String getXmlName() {
		return xmlName;
	}

	/**
	 * 配置文件的XmlName
	 * 
	 * @param xmlName
	 */
	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}

	/**
	 * 配置文件的ItemName
	 * 
	 * @return
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * 配置文件的ItemName
	 * 
	 * @param itemName
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * 配置文件的参数
	 * 
	 * @return
	 */
	public String getParameters() {
		return parameters;
	}

	/**
	 * 配置文件的参数
	 * 
	 * @param parameters
	 */
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

}
