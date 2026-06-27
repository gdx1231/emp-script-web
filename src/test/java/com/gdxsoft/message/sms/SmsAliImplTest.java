package com.gdxsoft.message.sms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.TreeMap;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class SmsAliImplTest {

	private static String invokePercentEncode(String value) throws Exception {
		Method m = SmsAliImpl.class.getDeclaredMethod("percentEncode", String.class);
		m.setAccessible(true);
		return (String) m.invoke(null, value);
	}

	private static String invokeHmacSha1(String data, String key) throws Exception {
		Method m = SmsAliImpl.class.getDeclaredMethod("hmacSha1", String.class, String.class);
		m.setAccessible(true);
		return (String) m.invoke(null, data, key);
	}

	private static String invokeBuildQueryString(SmsAliImpl instance, TreeMap<String, String> params) throws Exception {
		Method m = SmsAliImpl.class.getDeclaredMethod("buildQueryString", TreeMap.class);
		m.setAccessible(true);
		return (String) m.invoke(instance, params);
	}

	@SuppressWarnings("unchecked")
	private static TreeMap<String, String> invokeBuildCommonParams(SmsAliImpl instance) throws Exception {
		Method m = SmsAliImpl.class.getDeclaredMethod("buildCommonParams");
		m.setAccessible(true);
		return (TreeMap<String, String>) m.invoke(instance);
	}

	// ---- percentEncode ----

	@Test
	void percentEncode_null() throws Exception {
		assertEquals("", invokePercentEncode(null));
	}

	@Test
	void percentEncode_space() throws Exception {
		assertEquals("hello%20world", invokePercentEncode("hello world"));
	}

	@Test
	void percentEncode_asterisk() throws Exception {
		assertEquals("a%2Ab", invokePercentEncode("a*b"));
	}

	@Test
	void percentEncode_tilde() throws Exception {
		assertEquals("~home", invokePercentEncode("~home"));
	}

	@Test
	void percentEncode_chinese() throws Exception {
		assertEquals("%E4%B8%AD%E6%96%87", invokePercentEncode("中文"));
	}

	// ---- hmacSha1 ----

	@Test
	void hmacSha1_nonEmpty() throws Exception {
		String sig = invokeHmacSha1("testdata", "testkey&");
		assertNotNull(sig);
		assertFalse(sig.isEmpty());
	}

	@Test
	void hmacSha1_deterministic() throws Exception {
		String sig1 = invokeHmacSha1("POST&%2F&Action%3DSendSms", "secret&");
		String sig2 = invokeHmacSha1("POST&%2F&Action%3DSendSms", "secret&");
		assertEquals(sig1, sig2);
	}

	@Test
	void hmacSha1_differentKeysProduceDifferentSignatures() throws Exception {
		String sig1 = invokeHmacSha1("data", "key1&");
		String sig2 = invokeHmacSha1("data", "key2&");
		assertFalse(sig1.equals(sig2));
	}

	// ---- buildQueryString ----

	@Test
	void buildQueryString_sorted() throws Exception {
		TreeMap<String, String> params = new TreeMap<>();
		params.put("BBB", "2");
		params.put("AAA", "1");

		assertEquals("AAA=1&BBB=2", invokeBuildQueryString(new SmsAliImpl(), params));
	}

	@Test
	void buildQueryString_encoded() throws Exception {
		TreeMap<String, String> params = new TreeMap<>();
		params.put("PhoneNumbers", "13800138000");
		params.put("SignName", "test sign");

		assertEquals("PhoneNumbers=13800138000&SignName=test%20sign",
				invokeBuildQueryString(new SmsAliImpl(), params));
	}

	// ---- buildCommonParams ----

	@Test
	void buildCommonParams_hasRequiredKeys() throws Exception {
		SmsAliImpl sms = new SmsAliImpl();
		sms.setAccessKeyId("testAk");
		sms.setAccessKeySecret("testSk");

		TreeMap<String, String> params = invokeBuildCommonParams(sms);

		assertEquals("testAk", params.get("AccessKeyId"));
		assertEquals("JSON", params.get("Format"));
		assertEquals("HMAC-SHA1", params.get("SignatureMethod"));
		assertEquals("1.0", params.get("SignatureVersion"));
		assertEquals("2017-05-25", params.get("Version"));
		assertNotNull(params.get("SignatureNonce"));
		assertTrue(params.get("Timestamp").endsWith("Z"));
	}

	@Test
	void buildCommonParams_noSignatureBeforeSigning() throws Exception {
		SmsAliImpl sms = new SmsAliImpl();
		sms.setAccessKeyId("ak");
		sms.setAccessKeySecret("sk");

		TreeMap<String, String> params = invokeBuildCommonParams(sms);
		assertFalse(params.containsKey("Signature"));
	}

	// ---- 响应解析 ----

	@Test
	void getSendResponseResult_mapsFields() throws Exception {
		SmsAliImpl sms = new SmsAliImpl();
		sms.setSmsSignName("mySign");
		sms.setSmsTemplateCode("SMS_001");

		JSONObject apiResponse = new JSONObject();
		apiResponse.put("Code", "OK");
		apiResponse.put("Message", "success");
		apiResponse.put("BizId", "biz123");
		apiResponse.put("RequestId", "req456");

		Method m = SmsAliImpl.class.getDeclaredMethod("getSendResponseResult", JSONObject.class);
		m.setAccessible(true);
		JSONObject result = (JSONObject) m.invoke(sms, apiResponse);

		assertEquals("OK", result.getString("SEND_COCDE"));
		assertEquals("success", result.getString("SEND_MESSAGE"));
		assertEquals("biz123", result.getString("BIZ_ID"));
		assertEquals("req456", result.getString("SEND_REQUEST_ID"));
		assertEquals("mySign", result.getString("SIGN_NAME"));
		assertEquals("SMS_001", result.getString("TEMPLATE_CODE"));
	}

	// ---- 带假 AK 的 API 调用 (SMS 不抛异常，错误码在返回 JSON 中) ----

	@Test
	void sendSms_fakeAk_returnsErrorCode() throws Exception {
		SmsAliImpl sms = new SmsAliImpl();
		sms.setAccessKeyId("fakeAk");
		sms.setAccessKeySecret("fakeSk");
		sms.setSmsSignName("testSign");
		sms.setSmsTemplateCode("SMS_001");

		JSONObject templateParam = new JSONObject();
		templateParam.put("code", "1234");

		JSONObject result = sms.sendSms("13800138000", templateParam, null);
		assertNotNull(result);
		assertNotNull(result.optString("SEND_COCDE"));
		assertFalse("OK".equals(result.optString("SEND_COCDE")));
	}

	@Test
	void sendSmsArray_fakeAk_returnsErrorCode() throws Exception {
		SmsAliImpl sms = new SmsAliImpl();
		sms.setAccessKeyId("fakeAk");
		sms.setAccessKeySecret("fakeSk");
		sms.setSmsSignName("testSign");
		sms.setSmsTemplateCode("SMS_001");

		JSONObject templateParam = new JSONObject();
		templateParam.put("code", "1234");

		JSONObject result = sms.sendSms(new String[] { "13800138000" }, templateParam, null);
		assertNotNull(result);
		assertFalse("OK".equals(result.optString("SEND_COCDE")));
	}

	@Test
	void sendSmsAndGetResponse_fakeAk_returnsErrorCode() throws Exception {
		SmsAliImpl sms = new SmsAliImpl();
		sms.setAccessKeyId("fakeAk");
		sms.setAccessKeySecret("fakeSk");
		sms.setSmsSignName("testSign");
		sms.setSmsTemplateCode("SMS_001");

		JSONObject templateParam = new JSONObject();
		templateParam.put("code", "1234");

		// sendSmsAndGetResponse: Code != "OK" 时不会调 querySendDetails，直接返回
		JSONObject result = sms.sendSmsAndGetResponse("13800138000", templateParam, null);
		assertNotNull(result);
		assertFalse("OK".equals(result.optString("SEND_COCDE")));
	}

	@Test
	void provider_isALI() {
		assertEquals("ALI", new SmsAliImpl().getProvider());
	}

	@Test
	void phoneDedup_sameNumberCalledTwice() throws Exception {
		SmsAliImpl sms = new SmsAliImpl();
		sms.setAccessKeyId("fakeAk");
		sms.setAccessKeySecret("fakeSk");
		sms.setSmsSignName("testSign");
		sms.setSmsTemplateCode("SMS_001");

		JSONObject templateParam = new JSONObject();
		templateParam.put("code", "1234");

		// sendSms(String[],...) 内部去重 → 不因重复抛异常
		JSONObject result = sms.sendSms(new String[] { "13800138000", "13800138000" }, templateParam, null);
		assertNotNull(result);
	}
}
