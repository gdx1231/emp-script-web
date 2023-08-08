package com.gdxsoft.project;

import java.util.ArrayList;
import java.util.List;

import com.gdxsoft.web.dao.OaReq;


/**
 * 项目节点数据
 * 
 * @author admin
 *
 */
public class ProjectNodeData {
	private OaReq oaReq_; // 节点数据
	private List<ProjectNodeData> children_; // 子节点
	private ProjectNodeData parent_; // 父节点

	private boolean isAlive_; //是否存活
	
	/**
	 * 是否存活
	 * @return
	 */
	public boolean isAlive() {
		return isAlive_;
	}

	/**
	 * 是否存活
	 * @param isAlive
	 */
	public void setAlive(boolean isAlive) {
		this.isAlive_ = isAlive;
	}

	public ProjectNodeData(OaReq oaReq) {
		this.oaReq_ = oaReq;
		this.children_ = new ArrayList<ProjectNodeData>();
	}

	/**
	 * 节点数据
	 * @return
	 */
	public OaReq getOaReq() {
		return oaReq_;
	}

	/**
	 * 获取所有 子节点
	 * @return
	 */
	public List<ProjectNodeData> getChildren() {
		return children_;
	}

	/**
	 * 获取  父节点
	 * @return
	 */
	public ProjectNodeData getParent() {
		return parent_;
	}

	/**
	 * 设置  父节点
	 * @param parent
	 */
	public void setParent(ProjectNodeData parent) {
		this.parent_ = parent;
		
	}

}
