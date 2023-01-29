package com.gdxsoft.web.doc;

import java.util.Date;
/**表DOC_TMP_GRP映射类
 * @author 蝈蝈 时间：Sat Feb 4 14:46:38 UTC+0800 2012*/
public class DocTmpGrp{
	private Integer _DocGrpId;	//模板组
	private Integer _DocTmpId;	//模板
	private String _DocGrpName;	//组名称
	private String _DocGrpMemo;	//备注
	private Date _DocGrpCdate;	//创建日期
	private Date _DocGrpMdate;	//修改日期
	private Integer _SupId;	//SUP_ID
/**
* 获取模板组
* 
* @return 模板组
*/
	public Integer getDocGrpId(){
		return this._DocGrpId;
	}
/**
* 设置模板组
* 
* @param paraDocGrpId
*            模板组
*/
	public void setDocGrpId(Integer paraDocGrpId){
		this._DocGrpId=paraDocGrpId;
	}
/**
* 获取模板
* 
* @return 模板
*/
	public Integer getDocTmpId(){
		return this._DocTmpId;
	}
/**
* 设置模板
* 
* @param paraDocTmpId
*            模板
*/
	public void setDocTmpId(Integer paraDocTmpId){
		this._DocTmpId=paraDocTmpId;
	}
/**
* 获取组名称
* 
* @return 组名称
*/
	public String getDocGrpName(){
		return this._DocGrpName;
	}
/**
* 设置组名称
* 
* @param paraDocGrpName
*            组名称
*/
	public void setDocGrpName(String paraDocGrpName){
		this._DocGrpName=paraDocGrpName;
	}
/**
* 获取备注
* 
* @return 备注
*/
	public String getDocGrpMemo(){
		return this._DocGrpMemo;
	}
/**
* 设置备注
* 
* @param paraDocGrpMemo
*            备注
*/
	public void setDocGrpMemo(String paraDocGrpMemo){
		this._DocGrpMemo=paraDocGrpMemo;
	}
/**
* 获取创建日期
* 
* @return 创建日期
*/
	public Date getDocGrpCdate(){
		return this._DocGrpCdate;
	}
/**
* 设置创建日期
* 
* @param paraDocGrpCdate
*            创建日期
*/
	public void setDocGrpCdate(Date paraDocGrpCdate){
		this._DocGrpCdate=paraDocGrpCdate;
	}
/**
* 获取修改日期
* 
* @return 修改日期
*/
	public Date getDocGrpMdate(){
		return this._DocGrpMdate;
	}
/**
* 设置修改日期
* 
* @param paraDocGrpMdate
*            修改日期
*/
	public void setDocGrpMdate(Date paraDocGrpMdate){
		this._DocGrpMdate=paraDocGrpMdate;
	}
/**
* 获取SUP_ID
* 
* @return SUP_ID
*/
	public Integer getSupId(){
		return this._SupId;
	}
/**
* 设置SUP_ID
* 
* @param paraSupId
*            SUP_ID
*/
	public void setSupId(Integer paraSupId){
		this._SupId=paraSupId;
	}
/**根据字段名称获取值，如果名称为空或字段未找到，返回空值 @param filedName 字段名称 @return 字段值*/
public Object getField(String filedName){
if(filedName==null){return null;}
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("DOC_GRP_ID")){return this._DocGrpId;}
if(n.equalsIgnoreCase("DOC_TMP_ID")){return this._DocTmpId;}
if(n.equalsIgnoreCase("DOC_GRP_NAME")){return this._DocGrpName;}
if(n.equalsIgnoreCase("DOC_GRP_MEMO")){return this._DocGrpMemo;}
if(n.equalsIgnoreCase("DOC_GRP_CDATE")){return this._DocGrpCdate;}
if(n.equalsIgnoreCase("DOC_GRP_MDATE")){return this._DocGrpMdate;}
if(n.equalsIgnoreCase("SUP_ID")){return this._SupId;}
return null;
}

}