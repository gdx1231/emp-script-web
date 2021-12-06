package com.gdxsoft.web.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdxsoft.easyweb.script.HtmlControl;
import com.gdxsoft.easyweb.script.RequestValue;

public class HtModule implements Cloneable {
	private String xmlName;
	private String itemName;
	private String parameters;
	private String memo;
	private String name;

	/**
	 * Initialize the module
	 * 
	 * @param name       the module name
	 * @param xmlName    the htmlControl's xmlName
	 * @param itemName   the htmlControl's itemName
	 * @param parameters the htmlControl's parameters
	 */
	public HtModule(String name, String xmlName, String itemName, String parameters) {
		this.name = name;
		this.xmlName = xmlName;
		this.itemName = itemName;
		this.parameters = parameters;
	}

	/**
	 * Initialize the module
	 * 
	 * @param xmlName  the htmlControl's xmlName
	 * @param itemName the htmlControl's itemName
	 */
	public HtModule(String xmlName, String itemName) {
		this.xmlName = xmlName;
		this.itemName = itemName;
	}

	/**
	 * Initialize the module
	 * 
	 * @param xmlName    the htmlControl's xmlName
	 * @param itemName   the htmlControl's itemName
	 * @param parameters the htmlControl's parameters
	 */
	public HtModule(String xmlName, String itemName, String parameters) {
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

	public String executeHtmlControl(HttpServletRequest request, HttpServletResponse response) {
		HtmlControl ht = this.createHtmlControl(request, response);
		String ajaxType = ht.getHtmlCreator().getAjaxCallType();
		if (ajaxType != null) {
			if ("ValidCode".equalsIgnoreCase(ajaxType)) {
				return null;
			}
			return ht.getHtml();
		} else {
			return ht.getAllHtml();
		}
	}

	public String executeHtmlControl(RequestValue rv, HttpServletResponse response) {
		HtmlControl ht = this.createHtmlControl(rv, response);
		String ajaxType = ht.getHtmlCreator().getAjaxCallType();
		if (ajaxType != null) {
			if ("ValidCode".equalsIgnoreCase(ajaxType)) {
				return null;
			}
			return ht.getHtml();
		} else {
			return ht.getAllHtml();
		}
	}

	@Override
	protected HtModule clone() throws CloneNotSupportedException {
		return (HtModule) super.clone();
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

	public String toString() {
		return "name=" + this.name + ", xmlName=" + xmlName + ", itemName=" + itemName + ", parameter="
				+ this.parameters;
	}

}
