package com.gdxsoft.web.domain;

import org.json.JSONObject;

public interface IDomain {

	/**
     * 获取单个域名的详细信息。
     *
     * @param domainName 要查询的域名
     * @return 包含操作结果的JSONObject对象，成功时RST为true，失败时RST为false且包含错误信息
     */
    public JSONObject getDomainInfo(String domainName) ;
    
    /**
	 * 获取单个子域名的详细信息。
	 *
	 * @param domainName 主域名
	 * @param rr         子域名前缀（如"www"）
	 * @param type       记录类型（如"A", "CNAME"）
	 * @return 包含操作结果的JSONObject对象，成功时RST为true，失败时RST为false且包含错误信息
	 */
	public JSONObject getSubdomainInfo(String domainName, String rr, String type);
	
	/**
	 * 添加新的域名解析记录。
	 *
	 * @param domainName 域名
	 * @param rr 主机记录（如"www"）
	 * @param type 记录类型（如"A", "CNAME"）
	 * @param value 记录值（如IP地址或域名）
	 * @return 包含操作结果的JSONObject对象，成功时RST为true，失败时RST为false且包含错误信息
	 */
	JSONObject addSubDomainRecord(String domainName, String rr, String type, String value);

	/**
	 * 修改已有的域名解析记录。
	 *
	 * @param recordId 解析记录ID
	 * @param rr 主机记录
	 * @param type 记录类型
	 * @param value 记录值
	 * @return 包含操作结果的JSONObject对象
	 */
	JSONObject updateSubDomainRecord(String recordId, String rr, String type, String value);

	/**
	 * 暂停特定的域名解析记录。
	 *
	 * @param recordId 解析记录ID
	 * @return 包含操作结果的JSONObject对象
	 */
	JSONObject setSubDomainRecordStatusToPause(String recordId);

	/**
	 * 开通特定的域名解析记录。
	 *
	 * @param recordId 解析记录ID
	 * @return 包含操作结果的JSONObject对象
	 */
	public JSONObject setSubDomainRecordStatusToEnable(String recordId);
	
	
	
	/**
	 * 删除指定的域名解析记录。
	 *
	 * @param recordId 解析记录ID
	 * @return 包含操作结果的JSONObject对象
	 */
	JSONObject deleteSubDomainRecord(String recordId);

	/**
	 * 查询域名解析记录。
	 *
	 * @param domainName 域名
	 * @return 包含操作结果的JSONObject对象
	 */
	JSONObject describeSubDomainRecords(String domainName);

}