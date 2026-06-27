package com.gdxsoft.message.sms;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONArray;
import org.json.JSONObject;

import com.gdxsoft.easyweb.utils.UNet;
import com.gdxsoft.easyweb.utils.UJSon;

/**
 * 腾讯云短信接口 (纯 HTTP 实现，零 SDK 依赖)
 */
public class SmsTencentImpl extends SmsBase implements ISms {
	public static final String PROVIDER = "Tencent";

	private static final String SERVICE = "sms";
	private static final String HOST = "sms.tencentcloudapi.com";
	private static final String ENDPOINT = "https://" + HOST + "/";
	private static final String API_VERSION = "2021-01-11";
	private static final String ALGORITHM = "TC3-HMAC-SHA256";

	public static final String DEF_REGION_ID = "ap-beijing";

	@Override
	public JSONObject sendSmsAndGetResponse(String phoneNumber, JSONObject templateParam, String outId)
			throws Exception {
		String[] phones = { phoneNumber };
		return this.sendSmsInner(phones, templateParam, outId);
	}

	@Override
	public JSONObject sendSms(String phoneNumber, JSONObject templateParam, String outId) throws Exception {
		String[] phones = { phoneNumber };
		return this.sendSmsInner(phones, templateParam, outId);
	}

	@Override
	public JSONObject sendSms(String[] phoneNumbers, JSONObject templateParam, String outId) throws Exception {
		return this.sendSmsInner(phoneNumbers, templateParam, outId);
	}

	@Override
	public JSONObject sendSms(List<String> phoneNumbers, JSONObject templateParam, String outId) throws Exception {
		return this.sendSmsInner(phoneNumbers.toArray(new String[0]), templateParam, outId);
	}

	private String[] convertToTencentParameters(JSONObject templateParam) {
		List<String> params = new ArrayList<>();
		for (int i = 1; i < 100; i++) {
			String key = i + "";
			if (!templateParam.has(key)) {
				break;
			}
			params.add(templateParam.optString(key));
		}
		return params.toArray(new String[0]);
	}

	private String[] convertToTencentPhones(String[] phoneNumbers) {
		Map<String, Boolean> map = new HashMap<>();
		List<String> al = new ArrayList<>();
		for (String pn : phoneNumbers) {
			String phone = SmsBase.chinesePhoneAddPlus86(pn);
			if (map.containsKey(phone)) {
				continue;
			}
			map.put(phone, true);
			al.add(phone);
		}
		return al.toArray(new String[0]);
	}

	private JSONObject sendSmsInner(String[] phoneNumbers, JSONObject templateParam, String outId) throws Exception {
		JSONObject rst = UJSon.rstTrue();

		String[] phones = convertToTencentPhones(phoneNumbers);
		String[] tencentTemplateParam = this.convertToTencentParameters(templateParam);

		rst.put("PHONE_NUMBER", phoneNumbers);
		rst.put("phones", phones);
		rst.put("TEMPLATE_PARAM", templateParam);
		rst.put("tenCentTemplateParam", tencentTemplateParam);
		rst.put("SmsSdkAppId", super.getSmsAppId());
		rst.put("TemplateId", super.getSmsTemplateCode());
		rst.put("SignName", super.getSmsSignName());
		rst.put("outId", outId);

		// 构建 JSON body
		JSONObject body = new JSONObject();
		body.put("PhoneNumberSet", new JSONArray(phones));
		body.put("SmsSdkAppId", super.getSmsAppId());
		body.put("SignName", super.getSmsSignName());
		body.put("TemplateId", super.getSmsTemplateCode());
		body.put("TemplateParamSet", new JSONArray(tencentTemplateParam));
		if (outId != null && !outId.isEmpty()) {
			body.put("SessionContext", outId);
		}

		String payload = body.toString();
		String region = super.getRegionId() != null && !super.getRegionId().isEmpty()
				? super.getRegionId() : DEF_REGION_ID;
		String action = "SendSms";

		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String date = sdf.format(new Date(Long.parseLong(timestamp) + "000"));

		// Step 1: Canonical Request
		String canonicalUri = "/";
		String canonicalQueryString = "";
		String canonicalHeaders = "content-type:application/json; charset=utf-8\n"
				+ "host:" + HOST + "\n"
				+ "x-tc-action:" + action.toLowerCase() + "\n";
		String signedHeaders = "content-type;host;x-tc-action";
		String hashedPayload = sha256Hex(payload);
		String canonicalRequest = "POST" + "\n"
				+ canonicalUri + "\n"
				+ canonicalQueryString + "\n"
				+ canonicalHeaders + "\n"
				+ signedHeaders + "\n"
				+ hashedPayload;

		// Step 2: String to Sign
		String credentialScope = date + "/" + SERVICE + "/tc3_request";
		String hashedCanonicalRequest = sha256Hex(canonicalRequest);
		String stringToSign = ALGORITHM + "\n"
				+ timestamp + "\n"
				+ credentialScope + "\n"
				+ hashedCanonicalRequest;

		// Step 3: Signature
		byte[] secretDate = hmac256(("TC3" + super.getAccessKeySecret()).getBytes(StandardCharsets.UTF_8), date);
		byte[] secretService = hmac256(secretDate, SERVICE);
		byte[] secretSigning = hmac256(secretService, "tc3_request");
		String signature = bytesToHex(hmac256(secretSigning, stringToSign));

		// Step 4: Authorization
		String authorization = ALGORITHM + " "
				+ "Credential=" + super.getAccessKeyId() + "/" + credentialScope + ", "
				+ "SignedHeaders=" + signedHeaders + ", "
				+ "Signature=" + signature;

		// HTTP request
		UNet net = new UNet();
		net.addHeader("Authorization", authorization);
		net.addHeader("Content-Type", "application/json; charset=utf-8");
		net.addHeader("Host", HOST);
		net.addHeader("X-TC-Action", action);
		net.addHeader("X-TC-Timestamp", timestamp);
		net.addHeader("X-TC-Version", API_VERSION);
		net.addHeader("X-TC-Region", region);

		String responseBody = net.doPost(ENDPOINT, payload);
		JSONObject response = new JSONObject(responseBody);
		JSONObject resp = response.optJSONObject("Response");
		if (resp == null) {
			UJSon.rstSetFalse(rst, "Empty response");
			return rst;
		}

		JSONObject error = resp.optJSONObject("Error");
		if (error != null) {
			UJSon.rstSetFalse(rst, error.optString("Code") + ": " + error.optString("Message"));
			return rst;
		}

		rst.put("RequestId", resp.optString("RequestId"));
		JSONArray statusSet = resp.optJSONArray("SendStatusSet");
		rst.put("StatusSet", statusSet != null ? statusSet : new JSONArray());

		return rst;
	}

	// ---- crypto helpers ----

	private static byte[] hmac256(byte[] key, String msg) throws Exception {
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(new SecretKeySpec(key, "HmacSHA256"));
		return mac.doFinal(msg.getBytes(StandardCharsets.UTF_8));
	}

	private static String sha256Hex(String s) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] d = md.digest(s.getBytes(StandardCharsets.UTF_8));
		return bytesToHex(d);
	}

	private static String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	@Override
	public String getProvider() {
		return PROVIDER;
	}
}
