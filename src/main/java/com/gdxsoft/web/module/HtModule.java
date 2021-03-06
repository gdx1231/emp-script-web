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
	private HtmlControl htmlControl ;

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
		this.htmlControl = ht;
		
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
		this.htmlControl = ht;
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
	 * ?????????????????????
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * ?????????????????????
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ?????????????????????
	 * 
	 * @return
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * ?????????????????????
	 * 
	 * @param memo ?????????????????????
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * ???????????????XmlName
	 * 
	 * @return
	 */
	public String getXmlName() {
		return xmlName;
	}

	/**
	 * ???????????????XmlName
	 * 
	 * @param xmlName
	 */
	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}

	/**
	 * ???????????????ItemName
	 * 
	 * @return
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * ???????????????ItemName
	 * 
	 * @param itemName
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * ?????????????????????
	 * 
	 * @return
	 */
	public String getParameters() {
		return parameters;
	}

	/**
	 * ?????????????????????
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

	public HtmlControl getHtmlControl() {
		return htmlControl;
	}

}
