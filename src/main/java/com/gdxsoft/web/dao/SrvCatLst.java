package com.gdxsoft.web.dao;

import java.util.Date;
/**表SRV_CAT_LST映射类
 * @author 蝈蝈 时间：Thu Aug 11 17:56:05 UTC+0800 2011*/
public class SrvCatLst{
	private Integer _SrvCatId;	//目录编号
	private Integer _SrvCatLstId;	//目录明细编号
	private String _BasTag;	//标记
	private String _SrvTypTag;	//服务类型
	private String _SrvCatLstName;	//目录明细名称
	private Date _SrvCatLstCdate;	//创建日期
	private Date _SrvCatLstMdate;	//修改日期
	private String _SrvCatLstMemo;	//备注
	private Integer _SrvCatLstOrd;	//排序
/**
* 获取目录编号
* 
* @return 目录编号
*/
	public Integer getSrvCatId(){
		return this._SrvCatId;
	}
/**
* 设置目录编号
* 
* @param paraSrvCatId
*            目录编号
*/
	public void setSrvCatId(Integer paraSrvCatId){
		this._SrvCatId=paraSrvCatId;
	}
/**
* 获取目录明细编号
* 
* @return 目录明细编号
*/
	public Integer getSrvCatLstId(){
		return this._SrvCatLstId;
	}
/**
* 设置目录明细编号
* 
* @param paraSrvCatLstId
*            目录明细编号
*/
	public void setSrvCatLstId(Integer paraSrvCatLstId){
		this._SrvCatLstId=paraSrvCatLstId;
	}
/**
* 获取标记
* 
* @return 标记
*/
	public String getBasTag(){
		return this._BasTag;
	}
/**
* 设置标记
* 
* @param paraBasTag
*            标记
*/
	public void setBasTag(String paraBasTag){
		this._BasTag=paraBasTag;
	}
/**
* 获取服务类型
* 
* @return 服务类型
*/
	public String getSrvTypTag(){
		return this._SrvTypTag;
	}
/**
* 设置服务类型
* 
* @param paraSrvTypTag
*            服务类型
*/
	public void setSrvTypTag(String paraSrvTypTag){
		this._SrvTypTag=paraSrvTypTag;
	}
/**
* 获取目录明细名称
* 
* @return 目录明细名称
*/
	public String getSrvCatLstName(){
		return this._SrvCatLstName;
	}
/**
* 设置目录明细名称
* 
* @param paraSrvCatLstName
*            目录明细名称
*/
	public void setSrvCatLstName(String paraSrvCatLstName){
		this._SrvCatLstName=paraSrvCatLstName;
	}
/**
* 获取创建日期
* 
* @return 创建日期
*/
	public Date getSrvCatLstCdate(){
		return this._SrvCatLstCdate;
	}
/**
* 设置创建日期
* 
* @param paraSrvCatLstCdate
*            创建日期
*/
	public void setSrvCatLstCdate(Date paraSrvCatLstCdate){
		this._SrvCatLstCdate=paraSrvCatLstCdate;
	}
/**
* 获取修改日期
* 
* @return 修改日期
*/
	public Date getSrvCatLstMdate(){
		return this._SrvCatLstMdate;
	}
/**
* 设置修改日期
* 
* @param paraSrvCatLstMdate
*            修改日期
*/
	public void setSrvCatLstMdate(Date paraSrvCatLstMdate){
		this._SrvCatLstMdate=paraSrvCatLstMdate;
	}
/**
* 获取备注
* 
* @return 备注
*/
	public String getSrvCatLstMemo(){
		return this._SrvCatLstMemo;
	}
/**
* 设置备注
* 
* @param paraSrvCatLstMemo
*            备注
*/
	public void setSrvCatLstMemo(String paraSrvCatLstMemo){
		this._SrvCatLstMemo=paraSrvCatLstMemo;
	}
/**
* 获取排序
* 
* @return 排序
*/
	public Integer getSrvCatLstOrd(){
		return this._SrvCatLstOrd;
	}
/**
* 设置排序
* 
* @param paraSrvCatLstOrd
*            排序
*/
	public void setSrvCatLstOrd(Integer paraSrvCatLstOrd){
		this._SrvCatLstOrd=paraSrvCatLstOrd;
	}
/**根据字段名称获取值，如果名称为空或字段未找到，返回空值 @param filedName 字段名称 @return 字段值*/
public Object getField(String filedName){
if(filedName==null){return null;}
String n=filedName.trim().toUpperCase();
if(n.equalsIgnoreCase("SRV_CAT_ID")){return this._SrvCatId;}
if(n.equalsIgnoreCase("SRV_CAT_LST_ID")){return this._SrvCatLstId;}
if(n.equalsIgnoreCase("BAS_TAG")){return this._BasTag;}
if(n.equalsIgnoreCase("SRV_TYP_TAG")){return this._SrvTypTag;}
if(n.equalsIgnoreCase("SRV_CAT_LST_NAME")){return this._SrvCatLstName;}
if(n.equalsIgnoreCase("SRV_CAT_LST_CDATE")){return this._SrvCatLstCdate;}
if(n.equalsIgnoreCase("SRV_CAT_LST_MDATE")){return this._SrvCatLstMdate;}
if(n.equalsIgnoreCase("SRV_CAT_LST_MEMO")){return this._SrvCatLstMemo;}
if(n.equalsIgnoreCase("SRV_CAT_LST_ORD")){return this._SrvCatLstOrd;}
return null;
}

}