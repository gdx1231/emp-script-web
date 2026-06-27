package com.gdxsoft.message.sms;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONArray;
import org.json.JSONObject;

import com.gdxsoft.easyweb.utils.UNet;

/**
 * 阿里云短信接口 (纯 HTTP 实现，零 SDK 依赖)
 *
 * @author admin
 */
public class SmsAliImpl extends SmsBase implements ISms {
	public static final String PROVIDER = "ALI";

	private static final String ENDPOINT = "https://dysmsapi.aliyuncs.com/";
	private static final String API_VERSION = "2017-05-25";
	private static final String SIGNATURE_METHOD = "HMAC-SHA1";
	private static final String SIGNATURE_VERSION = "1.0";

	@Override
	public JSONObject sendSms(String[] phoneNumbers, JSONObject templateParam, String outId) throws Exception {
		StringBuilder sb = new StringBuilder();
		Map<String, Boolean> map = new HashMap<>();
		for (int i = 0; i < phoneNumbers.length; i++) {
			String phone = SmsBase.chinesePhoneRemovePlus86(phoneNumbers[i]);
			if (map.containsKey(phone)) {
				continue;
			}
			if (sb.length() > 0) {
				sb.append(",");
			}
			sb.append(phone);
			map.put(phone, true);
		}

		String phones = sb.toString();
		JSONObject response = sendSmsAndReturnResponse(phones, templateParam, outId);
		JSONObject rst = getSendResponseResult(response);
		rst.put("PHONE_NUMBER", phones);
		rst.put("TEMPLATE_PARAM", templateParam);
		rst.put("OUT_ID", outId);

		return rst;
	}

	@Override
	public JSONObject sendSms(List<String> phoneNumbers, JSONObject templateParam, String outId) throws Exception {
		String[] phones = phoneNumbers.toArray(new String[phoneNumbers.size()]);
		return this.sendSms(phones, templateParam, outId);
	}

	/**
	 * 发送短信并获得发送明细
	 */
	public JSONObject sendSmsAndGetResponse(String phoneNumber, JSONObject templateParam, String outId)
			throws Exception {

		JSONObject response = sendSmsAndReturnResponse(phoneNumber, templateParam, outId);
		JSONObject rst = getSendResponseResult(response);

		rst.put("PHONE_NUMBER", phoneNumber);
		rst.put("TEMPLATE_PARAM", templateParam);
		rst.put("OUT_ID", outId);

		Thread.sleep(3000L);

		String code = response.optString("Code");
		if ("OK".equals(code)) {
			String bizId = response.optString("BizId");
			JSONObject detail = querySendDetails(bizId, phoneNumber);
			rst.put("RET_CODE", detail.optString("Code"));
			rst.put("RET_MESSAGE", detail.optString("Message"));

			JSONArray smsList = detail.optJSONArray("SmsSendDetailDTOs");
			if (smsList == null) {
				smsList = new JSONArray();
			}
			JSONObject smsWrapper = smsList.length() > 0 ? smsList.getJSONObject(0) : null;
			JSONArray dtoList = smsWrapper != null ? smsWrapper.optJSONArray("SmsSendDetailDTO") : null;
			if (dtoList == null) {
				dtoList = new JSONArray();
			}

			JSONArray arr = new JSONArray();
			for (int i = 0; i < dtoList.length(); i++) {
				JSONObject dto = dtoList.getJSONObject(i);
				JSONObject r = new JSONObject();
				r.put("Index", i);
				r.put("Content", dto.optString("Content"));
				r.put("ErrCode", dto.optString("ErrCode"));
				r.put("OutId", dto.optString("OutId"));
				r.put("PhoneNum", dto.optString("PhoneNum"));
				r.put("ReceiveDate", dto.optString("ReceiveDate"));
				r.put("SendDate", dto.optString("SendDate"));
				r.put("SendStatus", dto.optLong("SendStatus"));
				r.put("Template", dto.optString("TemplateCode"));
				arr.put(r);
			}
			if (arr.length() > 0) {
				rst.put("LIST", arr);
			}

			rst.put("RET_TOTAL_COUNT", detail.optString("TotalCount"));
			rst.put("RET_REQUEST_ID", detail.optString("RequestId"));
		}

		return rst;
	}

	public JSONObject sendSms(String phoneNumber, JSONObject templateParam, String outId) throws Exception {
		JSONObject response = sendSmsAndReturnResponse(phoneNumber, templateParam, outId);
		JSONObject rst = getSendResponseResult(response);
		rst.put("PHONE_NUMBER", phoneNumber);
		rst.put("TEMPLATE_PARAM", templateParam);
		rst.put("OUT_ID", outId);

		return rst;
	}

	private JSONObject getSendResponseResult(JSONObject response) {
		JSONObject rst = new JSONObject();

		rst.put("SEND_COCDE", response.optString("Code"));
		rst.put("SEND_MESSAGE", response.optString("Message"));
		rst.put("SEND_REQUEST_ID", response.optString("RequestId"));
		rst.put("BIZ_ID", response.optString("BizId"));

		rst.put("SIGN_NAME", super.getSmsSignName());
		rst.put("TEMPLATE_CODE", super.getSmsTemplateCode());

		return rst;
	}

	private JSONObject sendSmsAndReturnResponse(String phoneNumber, JSONObject templateParam, String outId)
			throws Exception {
		TreeMap<String, String> params = buildCommonParams();
		params.put("Action", "SendSms");
		params.put("PhoneNumbers", phoneNumber);
		params.put("SignName", super.getSmsSignName());
		params.put("TemplateCode", super.getSmsTemplateCode());
		params.put("TemplateParam", templateParam.toString());

		if (outId != null && outId.trim().length() > 0) {
			params.put("OutId", outId);
		}

		String responseBody = doSignedPost(params);
		return new JSONObject(responseBody);
	}

	private JSONObject querySendDetails(String bizId, String phoneNumber) throws Exception {
		TreeMap<String, String> params = buildCommonParams();
		params.put("Action", "QuerySendDetails");
		params.put("PhoneNumber", phoneNumber);
		if (bizId != null) {
			params.put("BizId", bizId);
		}
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
		params.put("SendDate", ft.format(new Date()));
		params.put("PageSize", "10");
		params.put("CurrentPage", "1");

		String responseBody = doSignedPost(params);
		return new JSONObject(responseBody);
	}

	/**
	 * 构建公共参数
	 */
	private TreeMap<String, String> buildCommonParams() {
		TreeMap<String, String> params = new TreeMap<>();
		params.put("AccessKeyId", super.getAccessKeyId());
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

	/**
	 * 计算签名并发送 POST 请求
	 */
	private String doSignedPost(TreeMap<String, String> params) throws Exception {
		String queryString = buildQueryString(params);
		String stringToSign = "POST" + "&" + percentEncode("/") + "&" + percentEncode(queryString);
		String signature = hmacSha1(stringToSign, super.getAccessKeySecret() + "&");

		params.put("Signature", signature);

		UNet net = new UNet();
		HashMap<String, String> postParams = new HashMap<>(params);
		return net.doPost(ENDPOINT, postParams);
	}

	/**
	 * 按字母序拼接参数为 k1=v1&k2=v2...
	 */
	private String buildQueryString(TreeMap<String, String> params) throws UnsupportedEncodingException {
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

	/**
	 * 阿里云签名专用百分号编码（RFC 3986，大写十六进制）
	 */
	private static String percentEncode(String value) throws UnsupportedEncodingException {
		if (value == null) {
			return "";
		}
		String encoded = URLEncoder.encode(value, "UTF-8")
				.replace("+", "%20")
				.replace("*", "%2A")
				.replace("%7E", "~");
		return encoded;
	}

	/**
	 * HMAC-SHA1 签名并 Base64 编码
	 */
	private static String hmacSha1(String data, String key) throws Exception {
		Mac mac = Mac.getInstance("HmacSHA1");
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
		mac.init(keySpec);
		byte[] raw = mac.doFinal(data.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(raw);
	}

	@Override
	public String getProvider() {
		return PROVIDER;
	}
}
