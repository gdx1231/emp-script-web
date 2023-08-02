package com.gdxsoft.project;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gdxsoft.easyweb.utils.UFile;
import com.gdxsoft.easyweb.utils.UXml;

/**
 * 项目模板
 * 
 * @author admin
 *
 */
public class ProjectTemplate {
	private static Logger LOGGER = LoggerFactory.getLogger(ProjectTemplate.class);

	private List<ProjectTemplateTask> taskRootLst_;
	private Map<String, ProjectTemplateTask> tasksMap_;
	private List<ProjectTemplateMark> markLst_;
	private String mainDataSql_;
	private String name_;
	private String des_;
	private String desEn_;
	private int cfgFileHash_; // 配置文件的hash，用于判断配置文件是否改变

	public ProjectTemplate() {
		this.taskRootLst_ = new ArrayList<ProjectTemplateTask>();
		this.tasksMap_ = new HashMap<String, ProjectTemplateTask>();
		this.markLst_ = new ArrayList<ProjectTemplateMark>();
	}

	/**
	 * 根据ID获取Task
	 * 
	 * @param id
	 * @return
	 */
	public ProjectTemplateTask getTask(String id) {
		return this.tasksMap_.get(id);
	}

	public boolean loadTemplate(String xmlName) {
		File f1 = new File(xmlName);
		if (!f1.exists()) {
			LOGGER.error(xmlName + " ????");
			return false;
		}
		this.name_ = f1.getName();

		try {
			String content = UFile.readFileText(f1.getAbsolutePath());

			// 配置文件的hash，用于判断配置文件是否改变
			this.cfgFileHash_ = content.hashCode();

			Document doc = UXml.asDocument(content);
			Node projectNode = doc.getFirstChild();

			NamedNodeMap atts = projectNode.getAttributes();
			for (int i = 0; i < atts.getLength(); i++) {
				Node att = atts.item(i);
				String name = att.getNodeName().toLowerCase();
				String val = att.getNodeValue();

				if ("des".equals(name)) {
					this.des_ = val;
				} else if ("des_en".equals(name)) {
					this.desEn_ = val;
				} else {
					LOGGER.warn(" ???? " + name + " = " + val);
				}
			}

			this.addTasks(projectNode, null);

			this.addMarks(doc);

		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return false;
		}

		return true;
	}

	/**
	 * 添加时间标记
	 * 
	 * @param doc
	 */
	private void addMarks(Document doc) {
		NodeList nl = doc.getElementsByTagName("mark");
		for (int i = 0; i < nl.getLength(); i++) {
			Node item = nl.item(i);
			ProjectTemplateMark mark = new ProjectTemplateMark();
			mark.initData(item);

			this.markLst_.add(mark);
		}

	}

	private void addTasks(Node parentNode, ProjectTemplateTask parentTask) {
		NodeList nl = parentNode.getChildNodes();
		int index = 0;
		for (int i = 0; i < nl.getLength(); i++) {
			Node item = nl.item(i);
			if (item.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			if (item.getNodeName().equals("main_data_sql")) {
				mainDataSql_ = item.getTextContent();
				continue;
			}

			if (!item.getNodeName().equals("task")) {
				continue;
			}

			ProjectTemplateTask task = new ProjectTemplateTask();
			task.initData(item);
			task.setIndex(index);
			index++;
			if (parentTask != null) {
				parentTask.addChild(task);

				if (task.getColor() == null || task.getColor().trim().length() == 0) {
					// 采用父节点的颜色
					task.setColor(parentTask.getColor());
				}

			} else {
				this.taskRootLst_.add(task);
			}

			this.tasksMap_.put(task.getId(), task);

			this.addTasks(item, task);
		}
	}

	public Map<String, ProjectTemplateTask> getTasksMap() {
		return tasksMap_;
	}

	public String getMainDataSql() {
		return mainDataSql_;
	}

	public String getName() {
		return name_;
	}

	/**
	 * 第一层节点列表
	 * 
	 * @return
	 */
	public List<ProjectTemplateTask> getTaskRootLst() {
		return taskRootLst_;
	}

	/**
	 * 时间Mark标记列表
	 * 
	 * @return
	 */
	public List<ProjectTemplateMark> getMarkLst() {
		return markLst_;
	}

	/**
	 * 描述
	 * 
	 * @return
	 */
	public String getDes() {
		return des_;
	}

	/**
	 * 描述英文
	 * 
	 * @return
	 */
	public String getDesEn() {
		return desEn_;
	}

	/**
	 * 配置文件的hash，用于判断配置文件是否改变
	 * 
	 * @return
	 */
	public int getCfgFileHash() {
		return cfgFileHash_;
	}
}
