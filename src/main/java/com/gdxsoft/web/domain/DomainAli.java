package com.gdxsoft.web.domain;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.*;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse.Record;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

/**
 * 基于阿里云DNS服务进行域名解析记录管理的工具类。
 */
public class DomainAli implements IDomain {
	public static final String PROVIDER = "ALI";
	/**
	 * 阿里云默认区域ID
	 */
	public static final String DEF_REGION_ID = "cn-hangzhou";

	private static final Logger LOGGER = LoggerFactory.getLogger(DomainAli.class);
	private IAcsClient client;

	/**
	 * 返回实例(regionId = cn-hangzhou)。
	 *
	 * @param accessKeyId     阿里云AccessKey ID
	 * @param accessKeySecret 阿里云AccessKey Secret
	 */
	public static DomainAli getInstance(String accessKeyId, String accessKeySecret) {
		DomainAli d = new DomainAli();
		d.init(accessKeyId, accessKeySecret);
		return d;
	}

	/**
	 * 返回实例
	 *
	 * @param accessKeyId     阿里云AccessKey ID
	 * @param accessKeySecret 阿里云AccessKey Secret
	 * @param regionId        阿里云区域ID，例如"cn-hangzhou"
	 */
	public static DomainAli getInstance(String accessKeyId, String accessKeySecret, String regionId) {
		DomainAli d = new DomainAli();
		d.init(accessKeyId, accessKeySecret, regionId);
		return d;
	}

	/**
	 * 构造函数初始化客户端
	 */
	public DomainAli() {
	}

	/**
	 * 初始化客户端(regionId = cn-hangzhou)。
	 *
	 * @param accessKeyId     阿里云AccessKey ID
	 * @param accessKeySecret 阿里云AccessKey Secret
	 */
	public void init(String accessKeyId, String accessKeySecret) {
		this.init(accessKeyId, accessKeySecret, DEF_REGION_ID);
	}

	/**
	 * 初始化客户端。
	 *
	 * @param accessKeyId     阿里云AccessKey ID
	 * @param accessKeySecret 阿里云AccessKey Secret
	 * @param regionId        阿里云区域ID，例如"cn-hangzhou"
	 */
	public void init(String accessKeyId, String accessKeySecret, String regionId) {
		this.client = new DefaultAcsClient(DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret));
	}

	/**
	 * 添加新的域名解析记录。
	 *
	 * @param domainName 域名
	 * @param rr         主机记录（如"www"）
	 * @param type       记录类型（如"A", "CNAME"）
	 * @param value      记录值（如IP地址或域名）
	 * @return 包含操作结果的JSONObject对象，成功时RST为true，失败时RST为false且包含错误信息
	 */
	@Override
	public JSONObject addSubDomainRecord(String domainName, String rr, String type, String value) {
		JSONObject result = new JSONObject();
		AddDomainRecordRequest request = new AddDomainRecordRequest();
		request.setDomainName(domainName);
		request.setRR(rr);
		request.setType(type);
		request.setValue(value);

		try {
			LOGGER.debug("Attempting to add domain record: {} with RR: {}, Type: {}, Value: {}", domainName, rr, type,
					value);
			AddDomainRecordResponse response = client.getAcsResponse(request);
			result.put("RST", true);
			result.put("RecordId", response.getRecordId());
			LOGGER.info("Successfully added domain record with RecordId: {}", response.getRecordId());
		} catch (ClientException e) {
			result.put("RST", false);
			result.put("ERR", e.getMessage());
			LOGGER.error("Failed to add domain record due to error: {}", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 修改已有的域名解析记录。
	 *
	 * @param recordId 解析记录ID
	 * @param rr       主机记录
	 * @param type     记录类型
	 * @param value    记录值
	 * @return 包含操作结果的JSONObject对象
	 */
	@Override
	public JSONObject updateSubDomainRecord(String recordId, String rr, String type, String value) {
		JSONObject result = new JSONObject();
		UpdateDomainRecordRequest request = new UpdateDomainRecordRequest();
		request.setRecordId(recordId);
		request.setRR(rr);
		request.setType(type);
		request.setValue(value);

		try {
			LOGGER.debug("Attempting to update domain record with RecordId: {} to RR: {}, Type: {}, Value: {}",
					recordId, rr, type, value);
			UpdateDomainRecordResponse response = client.getAcsResponse(request);
			result.put("RST", true);
			result.put("RecordId", response.getRecordId());
			LOGGER.info("Successfully updated domain record with RecordId: {}", response.getRecordId());
		} catch (ClientException e) {
			result.put("RST", false);
			result.put("ERR", e.getMessage());
			LOGGER.error("Failed to update domain record due to error: {}", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 暂停特定的域名解析记录。
	 *
	 * @param recordId 解析记录ID
	 * @return 包含操作结果的JSONObject对象
	 */
	@Override
	public JSONObject setSubDomainRecordStatusToPause(String recordId) {
		return this.setSubDomainRecordStatus(recordId, true);
	}

	/**
	 * 开通特定的域名解析记录。
	 *
	 * @param recordId 解析记录ID
	 * @return 包含操作结果的JSONObject对象
	 */
	@Override
	public JSONObject setSubDomainRecordStatusToEnable(String recordId) {
		return this.setSubDomainRecordStatus(recordId, false);
	}

	/**
	 * 设定域名的使用状态 disable/enable
	 * 
	 * @param recordId
	 * @param disable
	 * @return
	 */
	public JSONObject setSubDomainRecordStatus(String recordId, boolean disable) {
		JSONObject result = new JSONObject();
		SetDomainRecordStatusRequest request = new SetDomainRecordStatusRequest();
		request.setRecordId(recordId);
		request.setStatus(disable ? "DISABLE" : "ENABLE");

		try {
			LOGGER.debug("Attempting to pause domain record with RecordId: {}", recordId);
			SetDomainRecordStatusResponse response = client.getAcsResponse(request);
			result.put("RST", true);
			result.put("RecordId", response.getRecordId());
			LOGGER.info("Successfully paused domain record with RecordId: {}", response.getRecordId());
		} catch (ClientException e) {
			result.put("RST", false);
			result.put("ERR", e.getMessage());
			LOGGER.error("Failed to pause domain record due to error: {}", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 删除指定的域名解析记录。
	 *
	 * @param recordId 解析记录ID
	 * @return 包含操作结果的JSONObject对象
	 */
	@Override
	public JSONObject deleteSubDomainRecord(String recordId) {
		JSONObject result = new JSONObject();
		DeleteDomainRecordRequest request = new DeleteDomainRecordRequest();
		request.setRecordId(recordId);

		try {
			LOGGER.debug("Attempting to delete domain record with RecordId: {}", recordId);
			DeleteDomainRecordResponse response = client.getAcsResponse(request);
			result.put("RST", true);
			result.put("RequestId", response.getRequestId());
			LOGGER.info("Successfully deleted domain record with RequestId: {}", response.getRequestId());
		} catch (ClientException e) {
			result.put("RST", false);
			result.put("ERR", e.getMessage());
			LOGGER.error("Failed to delete domain record due to error: {}", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 获取单个子域名的详细信息。
	 *
	 * @param domainName 主域名
	 * @param rr         子域名前缀（如"www"）
	 * @param type       记录类型（如"A", "CNAME"）
	 * @return 包含操作结果的JSONObject对象，成功时RST为true，失败时RST为false且包含错误信息
	 */
	public JSONObject getSubdomainInfo(String domainName, String rr, String type) {
		JSONObject result = new JSONObject();
		DescribeDomainRecordsRequest request = new DescribeDomainRecordsRequest();
		request.setDomainName(domainName);
		request.setRRKeyWord(rr); // 使用RRKeyWord来过滤主机记录
		request.setTypeKeyWord(type); // 使用TypeKeyWord来过滤记录类型

		try {
			LOGGER.debug("Attempting to get subdomain info for: {}.{} with Type: {}", rr, domainName, type);
			DescribeDomainRecordsResponse response = client.getAcsResponse(request);

			if (response.getDomainRecords() != null && !response.getDomainRecords().isEmpty()) {
				Record record = response.getDomainRecords().get(0); // 假设只取第一个匹配项
				JSONObject subdomainInfoJson = this.createRecord(record);
				subdomainInfoJson.put("RST", true);

				LOGGER.info("Successfully retrieved subdomain info for {}.{}", rr, domainName);

				return subdomainInfoJson;
			} else {
				result.put("RST", false);
				result.put("ERR", "No matching records found.");
				LOGGER.warn("No matching records found for {}.{} with Type: {}", rr, domainName, type);
			}
		} catch (ClientException e) {
			result.put("RST", false);
			result.put("ERR", e.getMessage());
			LOGGER.error("Failed to get subdomain info for {}.{} due to error: {}", rr, domainName, e.getMessage(), e);
		}
		return result;
	}

	private JSONObject createRecord(Record record) {
		JSONObject recordJson = new JSONObject();
		recordJson.put("RecordId", record.getRecordId());
		recordJson.put("RR", record.getRR());
		recordJson.put("Value", record.getValue());
		recordJson.put("Locked", record.getLocked());
		recordJson.put("Status", record.getStatus());
		recordJson.put("DomainName", record.getDomainName());
		recordJson.put("DomainType", record.getType());
		recordJson.put("TTL", record.getTTL());

		return recordJson;
	}

	/**
	 * 查询域名解析记录。
	 *
	 * @param domainName 域名
	 * @return 包含操作结果的JSONObject对象
	 */
	@Override
	public JSONObject describeSubDomainRecords(String domainName) {
		JSONObject result = new JSONObject();
		DescribeDomainRecordsRequest request = new DescribeDomainRecordsRequest();
		request.setDomainName(domainName);

		try {
			LOGGER.debug("Attempting to query domain records for domain: {}", domainName);
			DescribeDomainRecordsResponse response = client.getAcsResponse(request);
			JSONArray recordsArray = new JSONArray();
			response.getDomainRecords().forEach(record -> {
				JSONObject recordJson = this.createRecord(record);
				recordsArray.put(recordJson);
			});
			result.put("RST", true);
			result.put("Records", recordsArray);
			LOGGER.info("Successfully queried domain records for domain: {}", domainName);
		} catch (ClientException e) {
			result.put("RST", false);
			result.put("ERR", e.getMessage());
			LOGGER.error("Failed to query domain records due to error: {}", e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 获取单个域名的详细信息。
	 *
	 * @param domainName 要查询的域名
	 * @return 包含操作结果的JSONObject对象，成功时RST为true，失败时RST为false且包含错误信息
	 */
	public JSONObject getDomainInfo(String domainName) {
		JSONObject result = new JSONObject();
		DescribeDomainInfoRequest request = new DescribeDomainInfoRequest();
		request.setDomainName(domainName);

		try {
			LOGGER.debug("Attempting to get domain info for: {}", domainName);
			DescribeDomainInfoResponse response = client.getAcsResponse(request);
			JSONObject domainInfoJson = new JSONObject();
			domainInfoJson.put("DomainName", response.getDomainName());
			domainInfoJson.put("InstanceId", response.getInstanceId());
			domainInfoJson.put("RegistrationDate", response.getCreateTime());
			domainInfoJson.put("JsonStr", response.getRecordLineTreeJson());
			// 根据需要添加更多字段

			result.put("RST", true);
			result.put("DomainInfo", domainInfoJson);
			LOGGER.info("Successfully retrieved domain info for: {}", domainName);
		} catch (ClientException e) {
			result.put("RST", false);
			result.put("ErrorMessage", e.getMessage());
			LOGGER.error("Failed to get domain info for {} due to error: {}", domainName, e.getMessage(), e);
		}
		return result;
	}
}