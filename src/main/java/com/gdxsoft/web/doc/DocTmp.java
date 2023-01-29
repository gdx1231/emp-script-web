package com.gdxsoft.web.doc;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;

/**
 * 表DOC_TMP映射类
 * 
 * @author gdx 时间：Wed Aug 21 2019 10:10:40 GMT+0800 (中国标准时间)
 */
public class DocTmp extends ClassBase {
	private String docTmpUnid_; // DOC_TMP_UNID
	private Integer docTmpId_; // 模板编号
	private Integer docCatId_; // 目录编号
	private String docCatUnid_; // DOC_CAT_UNID
	private String docTmpName_; // 模板名称
	private String docTmpCnt_; // 内容
	private String docTmpSql_; // 脚本
	private Date docTmpCdate_; // 创建日期
	private Date docTmpMdate_; // 修改日期
	private Integer supId_; // SUP_ID
	private Integer docTmpOrd_; // 排序
	private String docTmpTag_; // 标记
	private String docTmpType_; // DOC_TMP_TYPE
	private String docTmpGroupby_; // DOC_TMP_GROUPBY
	private String docTmpOrderby_; // DOC_TMP_ORDERBY
	private String docTmpJs_; // DOC_TMP_JS

	/**
	 * 获取 DOC_TMP_UNID
	 *
	 * @return DOC_TMP_UNID
	 */
	public String getDocTmpUnid() {
		return this.docTmpUnid_;
	}

	/**
	 * 赋值 DOC_TMP_UNID
	 * 
	 * @param paraDocTmpUnid DOC_TMP_UNID
	 */

	public void setDocTmpUnid(String paraDocTmpUnid) {
		super.recordChanged("DOC_TMP_UNID", this.docTmpUnid_, paraDocTmpUnid);
		this.docTmpUnid_ = paraDocTmpUnid;
	}

	/**
	 * 获取 模板编号
	 *
	 * @return 模板编号
	 */
	public Integer getDocTmpId() {
		return this.docTmpId_;
	}

	/**
	 * 赋值 模板编号
	 * 
	 * @param paraDocTmpId 模板编号
	 */

	public void setDocTmpId(Integer paraDocTmpId) {
		super.recordChanged("DOC_TMP_ID", this.docTmpId_, paraDocTmpId);
		this.docTmpId_ = paraDocTmpId;
	}

	/**
	 * 获取 目录编号
	 *
	 * @return 目录编号
	 */
	public Integer getDocCatId() {
		return this.docCatId_;
	}

	/**
	 * 赋值 目录编号
	 * 
	 * @param paraDocCatId 目录编号
	 */

	public void setDocCatId(Integer paraDocCatId) {
		super.recordChanged("DOC_CAT_ID", this.docCatId_, paraDocCatId);
		this.docCatId_ = paraDocCatId;
	}

	/**
	 * 获取 DOC_CAT_UNID
	 *
	 * @return DOC_CAT_UNID
	 */
	public String getDocCatUnid() {
		return this.docCatUnid_;
	}

	/**
	 * 赋值 DOC_CAT_UNID
	 * 
	 * @param paraDocCatUnid DOC_CAT_UNID
	 */

	public void setDocCatUnid(String paraDocCatUnid) {
		super.recordChanged("DOC_CAT_UNID", this.docCatUnid_, paraDocCatUnid);
		this.docCatUnid_ = paraDocCatUnid;
	}

	/**
	 * 获取 模板名称
	 *
	 * @return 模板名称
	 */
	public String getDocTmpName() {
		return this.docTmpName_;
	}

	/**
	 * 赋值 模板名称
	 * 
	 * @param paraDocTmpName 模板名称
	 */

	public void setDocTmpName(String paraDocTmpName) {
		super.recordChanged("DOC_TMP_NAME", this.docTmpName_, paraDocTmpName);
		this.docTmpName_ = paraDocTmpName;
	}

	/**
	 * 获取 内容
	 *
	 * @return 内容
	 */
	public String getDocTmpCnt() {
		return this.docTmpCnt_;
	}

	/**
	 * 赋值 内容
	 * 
	 * @param paraDocTmpCnt 内容
	 */

	public void setDocTmpCnt(String paraDocTmpCnt) {
		super.recordChanged("DOC_TMP_CNT", this.docTmpCnt_, paraDocTmpCnt);
		this.docTmpCnt_ = paraDocTmpCnt;
	}

	/**
	 * 获取 脚本
	 *
	 * @return 脚本
	 */
	public String getDocTmpSql() {
		return this.docTmpSql_;
	}

	/**
	 * 赋值 脚本
	 * 
	 * @param paraDocTmpSql 脚本
	 */

	public void setDocTmpSql(String paraDocTmpSql) {
		super.recordChanged("DOC_TMP_SQL", this.docTmpSql_, paraDocTmpSql);
		this.docTmpSql_ = paraDocTmpSql;
	}

	/**
	 * 获取 创建日期
	 *
	 * @return 创建日期
	 */
	public Date getDocTmpCdate() {
		return this.docTmpCdate_;
	}

	/**
	 * 赋值 创建日期
	 * 
	 * @param paraDocTmpCdate 创建日期
	 */

	public void setDocTmpCdate(Date paraDocTmpCdate) {
		super.recordChanged("DOC_TMP_CDATE", this.docTmpCdate_, paraDocTmpCdate);
		this.docTmpCdate_ = paraDocTmpCdate;
	}

	/**
	 * 获取 修改日期
	 *
	 * @return 修改日期
	 */
	public Date getDocTmpMdate() {
		return this.docTmpMdate_;
	}

	/**
	 * 赋值 修改日期
	 * 
	 * @param paraDocTmpMdate 修改日期
	 */

	public void setDocTmpMdate(Date paraDocTmpMdate) {
		super.recordChanged("DOC_TMP_MDATE", this.docTmpMdate_, paraDocTmpMdate);
		this.docTmpMdate_ = paraDocTmpMdate;
	}

	/**
	 * 获取 SUP_ID
	 *
	 * @return SUP_ID
	 */
	public Integer getSupId() {
		return this.supId_;
	}

	/**
	 * 赋值 SUP_ID
	 * 
	 * @param paraSupId SUP_ID
	 */

	public void setSupId(Integer paraSupId) {
		super.recordChanged("SUP_ID", this.supId_, paraSupId);
		this.supId_ = paraSupId;
	}

	/**
	 * 获取 排序
	 *
	 * @return 排序
	 */
	public Integer getDocTmpOrd() {
		return this.docTmpOrd_;
	}

	/**
	 * 赋值 排序
	 * 
	 * @param paraDocTmpOrd 排序
	 */

	public void setDocTmpOrd(Integer paraDocTmpOrd) {
		super.recordChanged("DOC_TMP_ORD", this.docTmpOrd_, paraDocTmpOrd);
		this.docTmpOrd_ = paraDocTmpOrd;
	}

	/**
	 * 获取 标记
	 *
	 * @return 标记
	 */
	public String getDocTmpTag() {
		return this.docTmpTag_;
	}

	/**
	 * 赋值 标记
	 * 
	 * @param paraDocTmpTag 标记
	 */

	public void setDocTmpTag(String paraDocTmpTag) {
		super.recordChanged("DOC_TMP_TAG", this.docTmpTag_, paraDocTmpTag);
		this.docTmpTag_ = paraDocTmpTag;
	}

	/**
	 * 获取 DOC_TMP_TYPE
	 *
	 * @return DOC_TMP_TYPE
	 */
	public String getDocTmpType() {
		return this.docTmpType_;
	}

	/**
	 * 赋值 DOC_TMP_TYPE
	 * 
	 * @param paraDocTmpType DOC_TMP_TYPE
	 */

	public void setDocTmpType(String paraDocTmpType) {
		super.recordChanged("DOC_TMP_TYPE", this.docTmpType_, paraDocTmpType);
		this.docTmpType_ = paraDocTmpType;
	}

	/**
	 * 获取 DOC_TMP_GROUPBY
	 *
	 * @return DOC_TMP_GROUPBY
	 */
	public String getDocTmpGroupby() {
		return this.docTmpGroupby_;
	}

	/**
	 * 赋值 DOC_TMP_GROUPBY
	 * 
	 * @param paraDocTmpGroupby DOC_TMP_GROUPBY
	 */

	public void setDocTmpGroupby(String paraDocTmpGroupby) {
		super.recordChanged("DOC_TMP_GROUPBY", this.docTmpGroupby_, paraDocTmpGroupby);
		this.docTmpGroupby_ = paraDocTmpGroupby;
	}

	/**
	 * 获取 DOC_TMP_ORDERBY
	 *
	 * @return DOC_TMP_ORDERBY
	 */
	public String getDocTmpOrderby() {
		return this.docTmpOrderby_;
	}

	/**
	 * 赋值 DOC_TMP_ORDERBY
	 * 
	 * @param paraDocTmpOrderby DOC_TMP_ORDERBY
	 */

	public void setDocTmpOrderby(String paraDocTmpOrderby) {
		super.recordChanged("DOC_TMP_ORDERBY", this.docTmpOrderby_, paraDocTmpOrderby);
		this.docTmpOrderby_ = paraDocTmpOrderby;
	}

	/**
	 * 获取 DOC_TMP_JS
	 *
	 * @return DOC_TMP_JS
	 */
	public String getDocTmpJs() {
		return this.docTmpJs_;
	}

	/**
	 * 赋值 DOC_TMP_JS
	 * 
	 * @param paraDocTmpJs DOC_TMP_JS
	 */

	public void setDocTmpJs(String paraDocTmpJs) {
		super.recordChanged("DOC_TMP_JS", this.docTmpJs_, paraDocTmpJs);
		this.docTmpJs_ = paraDocTmpJs;
	}

	/**
	 * 根据字段名称获取值，如果名称为空或字段未找到，返回空值
	 * 
	 * @param filedName 字段名称
	 * @return 字段值
	 */
	public Object getField(String filedName) {
		if (filedName == null) {
			return null;
		}
		String n = filedName.trim().toUpperCase();
		if (n.equalsIgnoreCase("DOC_TMP_UNID")) {
			return this.docTmpUnid_;
		}
		if (n.equalsIgnoreCase("DOC_TMP_ID")) {
			return this.docTmpId_;
		}
		if (n.equalsIgnoreCase("DOC_CAT_ID")) {
			return this.docCatId_;
		}
		if (n.equalsIgnoreCase("DOC_CAT_UNID")) {
			return this.docCatUnid_;
		}
		if (n.equalsIgnoreCase("DOC_TMP_NAME")) {
			return this.docTmpName_;
		}
		if (n.equalsIgnoreCase("DOC_TMP_CNT")) {
			return this.docTmpCnt_;
		}
		if (n.equalsIgnoreCase("DOC_TMP_SQL")) {
			return this.docTmpSql_;
		}
		if (n.equalsIgnoreCase("DOC_TMP_CDATE")) {
			return this.docTmpCdate_;
		}
		if (n.equalsIgnoreCase("DOC_TMP_MDATE")) {
			return this.docTmpMdate_;
		}
		if (n.equalsIgnoreCase("SUP_ID")) {
			return this.supId_;
		}
		if (n.equalsIgnoreCase("DOC_TMP_ORD")) {
			return this.docTmpOrd_;
		}
		if (n.equalsIgnoreCase("DOC_TMP_TAG")) {
			return this.docTmpTag_;
		}
		if (n.equalsIgnoreCase("DOC_TMP_TYPE")) {
			return this.docTmpType_;
		}
		if (n.equalsIgnoreCase("DOC_TMP_GROUPBY")) {
			return this.docTmpGroupby_;
		}
		if (n.equalsIgnoreCase("DOC_TMP_ORDERBY")) {
			return this.docTmpOrderby_;
		}
		if (n.equalsIgnoreCase("DOC_TMP_JS")) {
			return this.docTmpJs_;
		}
		return null;
	}
}