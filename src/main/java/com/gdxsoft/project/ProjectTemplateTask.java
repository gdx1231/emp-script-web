package com.gdxsoft.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gdxsoft.easyweb.utils.UXml;

public class ProjectTemplateTask {
	private static Logger LOGGER = LoggerFactory.getLogger(ProjectTemplateTask.class);
	private int index_;
	private String id_;
	private String des_; // 描述
	private String desEn_; // 描述英文

	private String addr_; // 任务地点
	private String addrEn_; // 任务地点-英文

	private String begin_; // 任务开始时间
	private String beginDefault_; // 默认任务开始时间(当begin为空是，获取默认时间)

	private String end_; // 任务结束时间
	private String endDefault_; // 默认任务结束时间(当 end 为空是，获取默认时间)

	private String valInit_; // 初始化值得表达式
	private String valRunning_; // 执行值得表达式

	private String adm_; // 管理员表达式

	private String color_; // 颜色

	private String hiddenOn_; // 隐含 值为 1，true, YES

	private boolean repeat_;
	private String repeatKey_;
	private String dataSql_;
	private String dataRepeatSql_;
	// 进行判断是否进行数据更新的判断依据MAX_ID/MAX_DATE
	private String dataCheckSql_;
	private String xml_;

	private String link_;

	private List<ProjectTemplateTask> children_;
	private ProjectTemplateTask parent_;
	private Map<String, ProjectTemplateTask> childrenMap_;

	private ProjectNodeData currentNodeData_; // 当前对应的数据OA_REQ

	public ProjectTemplateTask() {
		children_ = new ArrayList<ProjectTemplateTask>();
		childrenMap_ = new HashMap<String, ProjectTemplateTask>();
	}

	/**
	 * 添加子任务
	 * 
	 * @param chd
	 */
	public void addChild(ProjectTemplateTask chd) {
		if (this.childrenMap_.containsKey(chd.getId())) {
			return;
		}
		this.childrenMap_.put(chd.getId(), chd);
		chd.parent_ = this;

		this.children_.add(chd);
	}

	/**
	 * 设置父节点
	 * 
	 * @param parent
	 */
	public void setParent(ProjectTemplateTask parent) {
		this.parent_ = parent;
		parent.addChild(this);
	}

	public void initData(Node nodeTask) {

		NamedNodeMap atts = nodeTask.getAttributes();
		for (int i = 0; i < atts.getLength(); i++) {
			Node att = atts.item(i);
			String name = att.getNodeName().toLowerCase();
			String val = att.getNodeValue();

			if ("id".equals(name)) {
				this.id_ = val;
			} else if ("des".equals(name)) {
				this.des_ = val;
			} else if ("des_en".equals(name)) {
				this.desEn_ = val;
			} else if ("addr".equals(name)) { // 任务地点
				this.addr_ = val;
			} else if ("addr_en".equals(name)) { // 任务地点-英文
				this.addrEn_ = val;
			} else if ("begin".equals(name)) { // 任务开始时间
				this.begin_ = val;
			} else if ("begin_default".equals(name)) { // 任务默认开始时间
				this.beginDefault_ = val;
			} else if ("end".equals(name)) { // 任务结束时间
				this.end_ = val;
			} else if ("end_default".equals(name)) {// 任务默认结束时间
				this.endDefault_ = val;
			} else if ("val_init".equals(name)) {
				this.valInit_ = val;
			} else if ("val_running".equals(name)) {
				this.valRunning_ = val;
			} else if ("adm".equals(name)) {
				this.adm_ = val;
			} else if ("color".equals(name)) {
				this.color_ = val;
			} else if ("repeat".equals(name)) {
				this.repeat_ = "true".equals(val);
			} else if ("repeat_key".equals(name)) {
				this.repeatKey_ = val;
			} else if ("hidden_on".equals(name)) {
				this.hiddenOn_ = val;
			} else if ("link".equals(name)) {
				this.link_ = val;
			} else {
				LOGGER.warn(" ???? " + name + " = " + val);
			}
		}
		Element eleTask = (Element) nodeTask;
		NodeList nl = eleTask.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node item = nl.item(i);
			if (item.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			if (item.getNodeName().equals("data_sql")) {
				dataSql_ = item.getTextContent();
			} else if (item.getNodeName().equals("data_repeat_sql")) {
				dataRepeatSql_ = item.getTextContent();
			} else if (item.getNodeName().equals("data_check_sql")) {
				// 进行判断是否进行数据更新的判断依据MAX_ID/MAX_DATE
				this.dataCheckSql_ = item.getTextContent();
			}
		}

		this.xml_ = UXml.asXml(nodeTask);
	}

	public String getId() {
		return id_;
	}

	public void setId(String id) {
		this.id_ = id;
	}

	public String getDes() {
		return des_;
	}

	public void setDes(String des) {
		this.des_ = des;
	}

	public String getDesEn() {
		return desEn_;
	}

	public void setDesEn(String desEn) {
		this.desEn_ = desEn;
	}

	public String getDataSql() {
		return dataSql_;
	}

	public void setDataSql(String dataSql) {
		this.dataSql_ = dataSql;
	}

	/**
	 * 任务开始时间
	 * 
	 * @return
	 */
	public String getBegin() {
		return begin_;
	}

	/**
	 * 任务开始时间
	 * 
	 * @param begin
	 */
	public void setBegin(String begin) {
		this.begin_ = begin;
	}

	/**
	 * 任务结束时间
	 * 
	 * @return
	 */
	public String getEnd() {
		return end_;
	}

	/**
	 * 任务结束时间
	 * 
	 * @param end
	 */
	public void setEnd(String end) {
		this.end_ = end;
	}

	/**
	 * 默认任务开始时间
	 * 
	 * @return
	 */
	public String getBeginDefault() {
		return beginDefault_;
	}

	/**
	 * 默认任务开始时间
	 * 
	 * @param beginDefault
	 */
	public void setBeginDefault(String beginDefault) {
		this.beginDefault_ = beginDefault;
	}

	/**
	 * 默认任务结束时间
	 * 
	 * @return
	 */
	public String getEndDefault() {
		return endDefault_;
	}

	/**
	 * 默认任务结束时间
	 * 
	 * @param endDefault
	 */
	public void setEndDefault(String endDefault) {
		this.endDefault_ = endDefault;
	}

	public String getXml() {
		return xml_;
	}

	public void setXml(String xml) {
		this.xml_ = xml;
	}

	public List<ProjectTemplateTask> getChildren() {
		return children_;
	}

	public ProjectTemplateTask getParent() {
		return parent_;
	}

	public Map<String, ProjectTemplateTask> getChildrenMap() {
		return childrenMap_;
	}

	/**
	 * 当前等级下的排序
	 * 
	 * @return
	 */
	public int getIndex() {
		return index_;
	}

	/**
	 * 当前等级下的排序
	 * 
	 * @param index
	 */
	public void setIndex(int index) {
		this.index_ = index;
	}

	/**
	 * 初始化值得表达式
	 * 
	 * @return
	 */
	public String getValInit() {
		return valInit_;
	}

	/**
	 * 初始化值得表达式
	 * 
	 * @param valInit
	 */
	public void setValInit(String valInit) {
		this.valInit_ = valInit;
	}

	/**
	 * 执行值得表达式
	 * 
	 * @return
	 */
	public String getValRunning() {
		return valRunning_;
	}

	/**
	 * 执行值得表达式
	 * 
	 * @param valRunning
	 */
	public void setValRunning(String valRunning) {
		this.valRunning_ = valRunning;
	}

	/**
	 * 管理员表达式
	 * 
	 * @return
	 */
	public String getAdm() {
		return adm_;
	}

	/**
	 * 管理员表达式
	 * 
	 * @param adm
	 */
	public void setAdm(String adm) {
		this.adm_ = adm;
	}

	/**
	 * 颜色
	 * 
	 * @return
	 */
	public String getColor() {
		return color_;
	}

	/**
	 * 颜色
	 * 
	 * @return
	 */
	public void setColor(String color) {
		this.color_ = color;
	}

	/**
	 * 是否重复数据（根据 data_repeat_sql执行）
	 * 
	 * @return
	 */
	public boolean isRepeat() {
		return repeat_;
	}

	public void setRepeat(boolean repeat) {
		this.repeat_ = repeat;
	}

	/**
	 * 当 repeat = true时，重复执行的数据 （SQL）
	 * 
	 * @return
	 */
	public String getDataRepeatSql() {
		return dataRepeatSql_;
	}

	public void setDataRepeatSql(String dataRepeatSql) {
		this.dataRepeatSql_ = dataRepeatSql;
	}

	/**
	 * 当 repeat = true时, 标记每行数据的唯一表示
	 * 
	 * @return
	 */
	public String getRepeatKey() {
		return repeatKey_;
	}

	public void setRepeatKey(String repeatKey) {
		this.repeatKey_ = repeatKey;
	}

	/**
	 * 隐含 值为 1，true, YES
	 * 
	 * @return
	 */
	public String getHiddenOn() {
		return hiddenOn_;
	}

	/**
	 * 隐含 值为 1，true, YES
	 * 
	 * @param hiddenOn
	 */
	public void setHiddenOn(String hiddenOn) {
		this.hiddenOn_ = hiddenOn;
	}

	/**
	 * 进行判断是否进行数据更新的判断依据MAX_ID/MAX_DATE
	 * 
	 * @return
	 */
	public String getDataCheckSql() {
		return dataCheckSql_;
	}

	/**
	 * 进行判断是否进行数据更新的判断依据MAX_ID/MAX_DATE
	 * 
	 * @param dataCheckSql
	 */
	public void setDataCheckSql(String dataCheckSql) {
		this.dataCheckSql_ = dataCheckSql;
	}

	/**
	 * 任务连接地址
	 * 
	 * @return
	 */
	public String getLink() {
		return link_;
	}

	/**
	 * 任务连接地址
	 * 
	 * @param link 地址
	 */
	public void setLink(String link) {
		this.link_ = link;
	}

	/**
	 * 当前对应的数据OA_REQ
	 * 
	 * @return
	 */
	public ProjectNodeData getCurrentNodeData() {
		return currentNodeData_;
	}

	/**
	 * 当前对应的数据OA_REQ
	 * 
	 * @param currentNodeData_
	 */
	public void setCurrentNodeData(ProjectNodeData currentNodeData) {
		this.currentNodeData_ = currentNodeData;
	}

	/**
	 * 任务地点
	 * 
	 * @return
	 */
	public String getAddr() {
		return addr_;
	}

	/**
	 * 任务地点
	 * 
	 * @param addr
	 */
	public void setAddr(String addr) {
		this.addr_ = addr;
	}

	/**
	 * 任务地点-英文
	 * 
	 * @return
	 */
	public String getAddrEn() {
		return addrEn_;
	}

	/**
	 * 任务地点-英文
	 * 
	 * @param addrEn
	 */
	public void setAddrEn(String addrEn) {
		this.addrEn_ = addrEn;
	}
}
