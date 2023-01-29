package com.gdxsoft.web.doc;

import java.util.Date;
/**表DOC_VAL_MAIN映射类
 * @author 蝈蝈 时间：Sat Feb 4 14:49:29 UTC+0800 2012*/
public class DocValMain{
	private Integer _DocValMid;	//数据主编号
	private Integer _DocGrpId;	//模板组编号
	private Integer _DocTmpId;	//模板编号
	private Date _DocValCdate;	//创建日期
	private Date _DocValMdate;	//修改日期
	private Integer _SupId;	//公司
	private Integer _AdmId;	//管理员
	private String _DocValMtag;	//DOC_VAL_MTAG
/**
* 获取数据主编号
* 
* @return 数据主编号
*/
	public Integer getDocValMid(){
		return this._DocValMid;
	}
/**
* 设置数据主编号
* 
* @param paraDocValMid
*            数据主编号
*/
	public void setDocValMid(Integer paraDocValMid){
		this._DocValMid=paraDocValMid;
	}
/**
* 获取模板组编号
* 
* @return 模板组编号
*/
	public Integer getDocGrpId(){
		return this._DocGrpId;
	}
/**
* 设置模板组编号
* 
* @param paraDocGrpId
*            模板组编号
*/
	public void setDocGrpId(Integer paraDocGrpId){
		this._DocGrpId=paraDocGrpId;
	}
/**
* 获取模板编号
* 
* @return 模板编号
*/
	public Integer getDocTmpId(){
		return this._DocTmpId;
	}
/**
* 设置模板编号
* 
* @param paraDocTmpId
*            模板编号
*/
	public void setDocTmpId(Integer paraDocTmpId){
		this._DocTmpId=paraDocTmpId;
	}
/**
* 获取创建日期
* 
* @return 创建日期
*/
	public Date getDocValCdate(){
		return this._DocValCdate;
	}
/**
* 设置创建日期
* 
* @param paraDocValCdate
*            创建日期
*/
	public void setDocValCdate(Date paraDocValCdate){
		this._DocValCdate=paraDocValCdate;
	}
/**
* 获取修改日期
* 
* @return 修改日期
*/
	public Date getDocValMdate(){
		return this._DocValMdate;
	}
/**
* 设置修改日期
* 
* @param paraDocValMdate
*            修改日期
*/
	public void setDocValMdate(Date paraDocValMdate){
		this._DocValMdate=paraDocValMdate;
	}
/**
* 获取公司
* 
* @return 公司
*/
	public Integer getSupId(){
		return this._SupId;
	}
/**
* 设置公司
* 
* @param paraSupId
*            公司
*/
	public void setSupId(Integer paraSupId){
		this._SupId=paraSupId;
	}
/**
* 获取管理员
* 
* @return 管理员
*/
	public Integer getAdmId(){
		return this._AdmId;
	}
/**
* 设置管理员
* 
* @param paraAdmId
*            管理员
*/
	public void setAdmId(Integer paraAdmId){
		this._AdmId=paraAdmId;
	}
/**
* 获取DOC_VAL_MTAG
* 
* @return DOC_VAL_MTAG
*/
	public String getDocValMtag(){
		return this._DocValMtag;
	}
/**
* 设置DOC_VAL_MTAG
* 
* @param paraDocValMtag
*            DOC_VAL_MTAG
*/
	public void setDocValMtag(String paraDocValMtag){
		this._DocValMtag=paraDocValMtag;
	}
/**根据字段名称获取值，如果名称为空或字段未找到，返回空值 @param filedName 字段名称 @return 字段值*/
public Object getField(String filedName){
if(filedName==null){return null;}
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("DOC_VAL_MID")){return this._DocValMid;}
if(n.equalsIgnoreCase("DOC_GRP_ID")){return this._DocGrpId;}
if(n.equalsIgnoreCase("DOC_TMP_ID")){return this._DocTmpId;}
if(n.equalsIgnoreCase("DOC_VAL_CDATE")){return this._DocValCdate;}
if(n.equalsIgnoreCase("DOC_VAL_MDATE")){return this._DocValMdate;}
if(n.equalsIgnoreCase("SUP_ID")){return this._SupId;}
if(n.equalsIgnoreCase("ADM_ID")){return this._AdmId;}
if(n.equalsIgnoreCase("DOC_VAL_MTAG")){return this._DocValMtag;}
return null;
}

}