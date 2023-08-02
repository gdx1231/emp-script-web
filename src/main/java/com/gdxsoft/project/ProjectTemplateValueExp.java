package com.gdxsoft.project;

/**
 * 获取 模板数据的表达式
 * 
 * @author admin
 *
 */
public class ProjectTemplateValueExp {

	private String exp_;
	private String method_;
	private String expCompute_;
	private String rvKey_;
	private String refType_;
	private String refId_;
	private String refAttr_;
	private String specificVal_; // 具体值

	public ProjectTemplateValueExp(String exp) {
		this.exp_ = exp;
		this.init();
	}

	private void init() {
		String exp1 = exp_;
		int locPlus = exp_.indexOf("+");
		int locSubtraction = exp_.indexOf("-");
		int loc = -1;
		if (locPlus > 0 && locSubtraction == -1) {
			loc = locPlus;
		} else if (locSubtraction > 0 && locPlus == -1) {
			loc = locSubtraction;
		} else if (locPlus > 0 && locSubtraction > 0 && locPlus < locSubtraction) {
			loc = locPlus;
		} else if (locPlus > 0 && locSubtraction > 0 && locPlus > locSubtraction) {
			loc = locSubtraction;
		}
		if (loc > 0) {
			exp1 = exp_.substring(0, loc).trim();
			expCompute_ = exp_.substring(loc).trim();
		}

		if (exp1.startsWith("@")) {
			String key = exp1.replace("@", "");
			if (key.equals("SD")) {
				key = key.trim();
			}
			this.rvKey_ = key;

			this.method_ = "RV"; // 数据来源于 RV
			return;
		} else if (exp1.indexOf("#") > 0) {

			String[] exps = exp1.split("\\#");

			refType_ = exps[0].trim();
			refId_ = exps[1].trim();
			refAttr_ = exps[2].trim();

			this.method_ = refType_.toUpperCase(); // 数据来源于 其它Task值
		} else {
			this.method_ = "SPECIFIC"; // 例如具体值
			this.specificVal_ = exp1;
		}
	}

	/**
	 * 原始 表达式
	 * 
	 * @return
	 */
	public String getExp() {
		return exp_;
	}

	/**
	 * 获取数据的方式
	 * 
	 * @return
	 */
	public String getMethod() {
		return method_;
	}

	/**
	 * 后续的计算方式
	 * 
	 * @return
	 */
	public String getExpCompute() {
		return expCompute_;
	}

	/**
	 * 当 method = RV 时， 获取数据所用的 key
	 * 
	 * @return
	 */
	public String getRvKey() {
		return rvKey_;
	}

	/**
	 * method = Task
	 * 
	 * @return
	 */
	public String getRefType() {
		return refType_;
	}

	/**
	 * 当 refType = task时，其它Task的id
	 * 
	 * @return
	 */
	public String getRefId() {
		return refId_;
	}

	/**
	 * 当 refType = task时，其它Task的属性
	 * 
	 * @return
	 */
	public String getRefAttr() {
		return refAttr_;
	}

	/**
	 * 获取具体值
	 * 
	 * @return
	 */
	public String getSpecificVal() {
		return this.specificVal_;
	}

}
