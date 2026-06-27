package com.gdxsoft.web.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.utils.UNet;

/**
 * 基于阿里云DNS服务进行域名解析记录管理的工具类 (纯 HTTP 实现，零 SDK 依赖)
 */
public class DomainAli implements IDomain {
	public static final String PROVIDER = "ALI";

	private static final String ENDPOINT = "https://alidns.aliyuncs.com/";
	private static final String API_VERSION = "2015-01-09";
	private static final String SIGNATURE_METHOD = "HMAC-SHA1";
	private static final String SIGNATURE_VERSION = "1.0";

	private static final Logger LOGGER = LoggerFactory.getLogger(DomainAli.class);

	private String accessKeyId;
	private String accessKeySecret;

	public DomainAli() {
	}

	public void init(String accessKeyId, String accessKeySecret) {
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
	}

	public void init(String accessKeyId, String accessKeySecret, String regionId) {
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
	}

	public static DomainAli getInstance(String accessKeyId, String accessKeySecret) {
		DomainAli d = new DomainAli();
		d.init(accessKeyId, accessKeySecret);
		return d;
	}

	public static DomainAli getInstance(String accessKeyId, String accessKeySecret, String regionId) {
		DomainAli d = new DomainAli();
		d.init(accessKeyId, accessKeySecret, regionId);
		return d;
	}

	@Override
	public JSONObject addSubDomainRecord(String domainName, String rr, String type, String value) {
		JSONObject result = new JSONObject();
		TreeMap<String, String> params = buildCommonParams();
		params.put("Action", "AddDomainRecord");
		params.put("DomainName", domainName);
		params.put("RR", rr);
		params.put("Type", type);
		params.put("Value", value);

		try {
			LOGGER.debug("Attempting to add domain record: {} with RR: {}, Type: {}, Value: {}", domainName, rr, type, value);
			JSONObject response = doSignedPost(params);
			result.put("RST", true);
			result.put("RecordId", response.optString("RecordId"));
			LOGGER.info("Successfully added domain record with RecordId: {}", response.optString("RecordId"));
		} catch (Exception e) {
			result.put("RST", false);
			result.put("ERR", e.getMessage());
			LOGGER.error("Failed to add domain record due to error: {}", e.getMessage(), e);
		}
		return result;
	}

	@Override
	public JSONObject updateSubDomainRecord(String recordId, String rr, String type, String value) {
		JSONObject result = new JSONObject();
		TreeMap<String, String> params = buildCommonParams();
		params.put("Action", "UpdateDomainRecord");
		params.put("RecordId", recordId);
		params.put("RR", rr);
		params.put("Type", type);
		params.put("Value", value);

		try {
			LOGGER.debug("Attempting to update domain record with RecordId: {} to RR: {}, Type: {}, Value: {}",
					recordId, rr, type, value);
			JSONObject response = doSignedPost(params);
			result.put("RST", true);
			result.put("RecordId", response.optString("RecordId"));
			LOGGER.info("Successfully updated domain record with RecordId: {}", response.optString("RecordId"));
		} catch (Exception e) {
			result.put("RST", false);
			result.put("ERR", e.getMessage());
			LOGGER.error("Failed to update domain record due to error: {}", e.getMessage(), e);
		}
		return result;
	}

	@Override
	public JSONObject setSubDomainRecordStatusToPause(String recordId) {
		return this.setSubDomainRecordStatus(recordId, true);
	}

	@Override
	public JSONObject setSubDomainRecordStatusToEnable(String recordId) {
		return this.setSubDomainRecordStatus(recordId, false);
	}

	public JSONObject setSubDomainRecordStatus(String recordId, boolean disable) {
		JSONObject result = new JSONObject();
		TreeMap<String, String> params = buildCommonParams();
		params.put("Action", "SetDomainRecordStatus");
		params.put("RecordId", recordId);
		params.put("Status", disable ? "DISABLE" : "ENABLE");

		try {
			LOGGER.debug("Attempting to set domain record status, RecordId: {}, disable: {}", recordId, disable);
			JSONObject response = doSignedPost(params);
			result.put("RST", true);
			result.put("RecordId", response.optString("RecordId"));
			LOGGER.info("Successfully set domain record status, RecordId: {}", response.optString("RecordId"));
		} catch (Exception e) {
			result.put("RST", false);
			result.put("ERR", e.getMessage());
			LOGGER.error("Failed to set domain record status due to error: {}", e.getMessage(), e);
		}
		return result;
	}

	@Override
	public JSONObject deleteSubDomainRecord(String recordId) {
		JSONObject result = new JSONObject();
		TreeMap<String, String> params = buildCommonParams();
		params.put("Action", "DeleteDomainRecord");
		params.put("RecordId", recordId);

		try {
			LOGGER.debug("Attempting to delete domain record with RecordId: {}", recordId);
			JSONObject response = doSignedPost(params);
			result.put("RST", true);
			result.put("RequestId", response.optString("RequestId"));
			LOGGER.info("Successfully deleted domain record with RequestId: {}", response.optString("RequestId"));
		} catch (Exception e) {
			result.put("RST", false);
			result.put("ERR", e.getMessage());
			LOGGER.error("Failed to delete domain record due to error: {}", e.getMessage(), e);
		}
		return result;
	}

	public JSONObject getSubdomainInfo(String domainName, String rr, String type) {
		JSONObject result = new JSONObject();
		TreeMap<String, String> params = buildCommonParams();
		params.put("Action", "DescribeDomainRecords");
		params.put("DomainName", domainName);
		params.put("RRKeyWord", rr);
		params.put("TypeKeyWord", type);

		try {
			LOGGER.debug("Attempting to get subdomain info for: {}.{} with Type: {}", rr, domainName, type);
			JSONObject response = doSignedPost(params);
			JSONObject domainRecords = response.optJSONObject("DomainRecords");
			JSONArray recordList = domainRecords != null ? domainRecords.optJSONArray("Record") : null;

			if (recordList != null && recordList.length() > 0) {
				JSONObject record = recordList.getJSONObject(0);
				JSONObject subdomainInfoJson = createRecordJson(record);
				subdomainInfoJson.put("RST", true);
				LOGGER.info("Successfully retrieved subdomain info for {}.{}", rr, domainName);
				return subdomainInfoJson;
			} else {
				result.put("RST", false);
				result.put("ERR", "No matching records found.");
				LOGGER.warn("No matching records found for {}.{} with Type: {}", rr, domainName, type);
			}
		} catch (Exception e) {
			result.put("RST", false);
			result.put("ERR", e.getMessage());
			LOGGER.error("Failed to get subdomain info for {}.{} due to error: {}", rr, domainName, e.getMessage(), e);
		}
		return result;
	}

	@Override
	public JSONObject describeSubDomainRecords(String domainName) {
		JSONObject result = new JSONObject();
		TreeMap<String, String> params = buildCommonParams();
		params.put("Action", "DescribeDomainRecords");
		params.put("DomainName", domainName);

		try {
			LOGGER.debug("Attempting to query domain records for domain: {}", domainName);
			JSONObject response = doSignedPost(params);
			JSONObject domainRecords = response.optJSONObject("DomainRecords");
			JSONArray recordList = domainRecords != null ? domainRecords.optJSONArray("Record") : new JSONArray();

			JSONArray recordsArray = new JSONArray();
			if (recordList != null) {
				for (int i = 0; i < recordList.length(); i++) {
					recordsArray.put(createRecordJson(recordList.getJSONObject(i)));
				}
			}
			result.put("RST", true);
			result.put("Records", recordsArray);
			LOGGER.info("Successfully queried domain records for domain: {}", domainName);
		} catch (Exception e) {
			result.put("RST", false);
			result.put("ERR", e.getMessage());
			LOGGER.error("Failed to query domain records due to error: {}", e.getMessage(), e);
		}
		return result;
	}

	public JSONObject getDomainInfo(String domainName) {
		JSONObject result = new JSONObject();
		TreeMap<String, String> params = buildCommonParams();
		params.put("Action", "DescribeDomainInfo");
		params.put("DomainName", domainName);

		try {
			LOGGER.debug("Attempting to get domain info for: {}", domainName);
			JSONObject response = doSignedPost(params);
			JSONObject domainInfoJson = new JSONObject();
			domainInfoJson.put("DomainName", response.optString("DomainName"));
			domainInfoJson.put("InstanceId", response.optString("InstanceId"));
			domainInfoJson.put("RegistrationDate", response.optString("CreateTime"));
			domainInfoJson.put("JsonStr", response.optString("RecordLineTreeJson"));

			result.put("RST", true);
			result.put("DomainInfo", domainInfoJson);
			LOGGER.info("Successfully retrieved domain info for: {}", domainName);
		} catch (Exception e) {
			result.put("RST", false);
			result.put("ErrorMessage", e.getMessage());
			LOGGER.error("Failed to get domain info for {} due to error: {}", domainName, e.getMessage(), e);
		}
		return result;
	}

	private static JSONObject createRecordJson(JSONObject record) {
		JSONObject recordJson = new JSONObject();
		recordJson.put("RecordId", record.optString("RecordId"));
		recordJson.put("RR", record.optString("RR"));
		recordJson.put("Value", record.optString("Value"));
		recordJson.put("Locked", record.optBoolean("Locked"));
		recordJson.put("Status", record.optString("Status"));
		recordJson.put("DomainName", record.optString("DomainName"));
		recordJson.put("DomainType", record.optString("Type"));
		recordJson.put("TTL", record.optLong("TTL"));
		return recordJson;
	}

	// ---- 签名 & HTTP ----

	private TreeMap<String, String> buildCommonParams() {
		TreeMap<String, String> params = new TreeMap<>();
		params.put("AccessKeyId", accessKeyId);
		params.put("Format", "JSON");
		params.put("SignatureMethod", SIGNATURE_METHOD);
		params.put("SignatureNonce", UUID.randomUUID().toString());
		params.put("SignatureVersion", SIGNATURE_VERSION);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		params.put("Timestamp", sdf.format(new Date()));
		params.put("Version", API_VERSION);
		return params;
	}

	private JSONObject doSignedPost(TreeMap<String, String> params) throws Exception {
		String queryString = buildQueryString(params);
		String stringToSign = "POST" + "&" + percentEncode("/") + "&" + percentEncode(queryString);
		String signature = hmacSha1(stringToSign, accessKeySecret + "&");

		params.put("Signature", signature);

		UNet net = new UNet();
		HashMap<String, String> postParams = new HashMap<>(params);
		String body = net.doPost(ENDPOINT, postParams);
		JSONObject json = new JSONObject(body);

		// 阿里云 API 错误响应包含 Code 字段，成功响应不含
		if (json.has("Code")) {
			throw new RuntimeException(json.optString("Code") + ": " + json.optString("Message"));
		}
		return json;
	}

	private static String buildQueryString(TreeMap<String, String> params)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if (sb.length() > 0) {
				sb.append("&");
			}
			sb.append(percentEncode(entry.getKey()));
			sb.append("=");
			sb.append(percentEncode(entry.getValue()));
		}
		return sb.toString();
	}

	private static String percentEncode(String value) throws UnsupportedEncodingException {
		if (value == null) {
			return "";
		}
		return URLEncoder.encode(value, "UTF-8")
				.replace("+", "%20")
				.replace("*", "%2A")
				.replace("%7E", "~");
	}

	private static String hmacSha1(String data, String key) throws Exception {
		Mac mac = Mac.getInstance("HmacSHA1");
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
		mac.init(keySpec);
		byte[] raw = mac.doFinal(data.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(raw);
	}
}
