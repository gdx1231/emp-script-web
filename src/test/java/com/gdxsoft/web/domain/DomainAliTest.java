package com.gdxsoft.web.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class DomainAliTest {

	private static String invokePercentEncode(String value) throws Exception {
		Method m = DomainAli.class.getDeclaredMethod("percentEncode", String.class);
		m.setAccessible(true);
		return (String) m.invoke(null, value);
	}

	private static String invokeHmacSha1(String data, String key) throws Exception {
		Method m = DomainAli.class.getDeclaredMethod("hmacSha1", String.class, String.class);
		m.setAccessible(true);
		return (String) m.invoke(null, data, key);
	}

	private static String invokeBuildQueryString(TreeMap<String, String> params) throws Exception {
		Method m = DomainAli.class.getDeclaredMethod("buildQueryString", TreeMap.class);
		m.setAccessible(true);
		return (String) m.invoke(null, params);
	}

	@SuppressWarnings("unchecked")
	private static TreeMap<String, String> invokeBuildCommonParams(DomainAli instance) throws Exception {
		Method m = DomainAli.class.getDeclaredMethod("buildCommonParams");
		m.setAccessible(true);
		return (TreeMap<String, String>) m.invoke(instance);
	}

	private static JSONObject invokeCreateRecordJson(JSONObject record) throws Exception {
		Method m = DomainAli.class.getDeclaredMethod("createRecordJson", JSONObject.class);
		m.setAccessible(true);
		return (JSONObject) m.invoke(null, record);
	}

	// ---- percentEncode ----

	@Test
	void percentEncode_null() throws Exception {
		assertEquals("", invokePercentEncode(null));
	}

	@Test
	void percentEncode_plainAscii() throws Exception {
		assertEquals("abc123", invokePercentEncode("abc123"));
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
	void percentEncode_slash() throws Exception {
		// "/" in StringToSign 签名时由 percentEncode("/") 处理
		assertEquals("%2F", invokePercentEncode("/"));
	}

	@Test
	void percentEncode_chinese() throws Exception {
		assertEquals("%E4%B8%AD%E6%96%87", invokePercentEncode("中文"));
	}

	@Test
	void percentEncode_specialChars() throws Exception {
		String encoded = invokePercentEncode("a=b&c");
		assertEquals("a%3Db%26c", encoded);
	}

	// ---- hmacSha1 ----

	@Test
	void hmacSha1_deterministic() throws Exception {
		String sig1 = invokeHmacSha1("POST&%2F&Action%3DAdd", "secret&");
		String sig2 = invokeHmacSha1("POST&%2F&Action%3DAdd", "secret&");
		assertEquals(sig1, sig2);
	}

	@Test
	void hmacSha1_nonEmpty() throws Exception {
		String sig = invokeHmacSha1("testdata", "testkey&");
		assertNotNull(sig);
		assertFalse(sig.isEmpty());
	}

	@Test
	void hmacSha1_differentDataProduceDifferentSignatures() throws Exception {
		String sig1 = invokeHmacSha1("POST&%2F&Action%3DAdd", "secret&");
		String sig2 = invokeHmacSha1("POST&%2F&Action%3DDelete", "secret&");
		assertFalse(sig1.equals(sig2));
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
		params.put("CCC", "3");

		String qs = invokeBuildQueryString(params);
		assertEquals("AAA=1&BBB=2&CCC=3", qs);
	}

	@Test
	void buildQueryString_encoded() throws Exception {
		TreeMap<String, String> params = new TreeMap<>();
		params.put("Name", "hello world");

		String qs = invokeBuildQueryString(params);
		assertEquals("Name=hello%20world", qs);
	}

	@Test
	void buildQueryString_empty() throws Exception {
		TreeMap<String, String> params = new TreeMap<>();
		assertEquals("", invokeBuildQueryString(params));
	}

	// ---- buildCommonParams ----

	@Test
	void buildCommonParams_containsRequiredKeys() throws Exception {
		DomainAli da = new DomainAli();
		da.init("testAk", "testSk");

		TreeMap<String, String> params = invokeBuildCommonParams(da);

		assertEquals("testAk", params.get("AccessKeyId"));
		assertEquals("JSON", params.get("Format"));
		assertEquals("HMAC-SHA1", params.get("SignatureMethod"));
		assertEquals("1.0", params.get("SignatureVersion"));
		assertEquals("2015-01-09", params.get("Version"));
		assertNotNull(params.get("SignatureNonce"));
		assertFalse(params.get("SignatureNonce").isEmpty());
		assertNotNull(params.get("Timestamp"));
		assertTrue(params.get("Timestamp").endsWith("Z"), "Timestamp should be UTC ISO8601");
	}

	@Test
	void buildCommonParams_noSignatureBeforeSigning() throws Exception {
		DomainAli da = new DomainAli();
		da.init("ak", "sk");

		TreeMap<String, String> params = invokeBuildCommonParams(da);
		assertFalse(params.containsKey("Signature"), "buildCommonParams should not add Signature");
	}

	// ---- createRecordJson ----

	@Test
	void createRecordJson_mapsAllFields() throws Exception {
		JSONObject apiRecord = new JSONObject();
		apiRecord.put("RecordId", "12345");
		apiRecord.put("RR", "www");
		apiRecord.put("Value", "192.168.1.1");
		apiRecord.put("Locked", true);
		apiRecord.put("Status", "ENABLE");
		apiRecord.put("DomainName", "example.com");
		apiRecord.put("Type", "A");
		apiRecord.put("TTL", 600L);

		JSONObject result = invokeCreateRecordJson(apiRecord);

		assertEquals("12345", result.getString("RecordId"));
		assertEquals("www", result.getString("RR"));
		assertEquals("192.168.1.1", result.getString("Value"));
		assertTrue(result.getBoolean("Locked"));
		assertEquals("ENABLE", result.getString("Status"));
		assertEquals("example.com", result.getString("DomainName"));
		assertEquals("A", result.getString("DomainType"));
		assertEquals(600L, result.getLong("TTL"));
	}

	// ---- 签名集成：端到端签名一致性 ----

	@Test
	void signature_integration_deterministic() throws Exception {
		// 用固定时间+固定 nonce 验证签名可重现
		DomainAli da = new DomainAli();
		da.init("testAkId", "testAkSecret");

		TreeMap<String, String> params = invokeBuildCommonParams(da);
		// 固定 nonce 和时间
		params.put("SignatureNonce", "fixed-nonce-12345");
		params.put("Timestamp", "2025-01-01T00:00:00Z");
		// 去掉 UUID 生成的不确定性
		params.put("Action", "DescribeDomainRecords");
		params.put("DomainName", "example.com");

		String qs = invokeBuildQueryString(params);
		String stringToSign = "POST" + "&" + invokePercentEncode("/") + "&" + invokePercentEncode(qs);
		String sig = invokeHmacSha1(stringToSign, "testAkSecret&");

		assertNotNull(sig);
		assertFalse(sig.isEmpty());

		// 再次计算应得到相同结果
		String sig2 = invokeHmacSha1(stringToSign, "testAkSecret&");
		assertEquals(sig, sig2);
	}

	// ---- IDomain 接口方法 ----

	@Test
	void getInstance_storesCredentials() {
		DomainAli da = DomainAli.getInstance("myAk", "mySk");
		assertNotNull(da);
	}

	@Test
	void describeSubDomainRecords_noNetwork_doesNotThrow() {
		DomainAli da = DomainAli.getInstance("fakeAk", "fakeSk");
		JSONObject result = da.describeSubDomainRecords("example.com");
		assertNotNull(result);
		assertFalse(result.optBoolean("RST"));
		assertNotNull(result.optString("ERR"));
	}

	@Test
	void addSubDomainRecord_noNetwork_returnsError() {
		DomainAli da = DomainAli.getInstance("fake", "fake");
		JSONObject result = da.addSubDomainRecord("example.com", "www", "A", "1.2.3.4");
		assertNotNull(result);
		assertFalse(result.optBoolean("RST"));
	}

	@Test
	void deleteSubDomainRecord_noNetwork_returnsError() {
		DomainAli da = DomainAli.getInstance("fake", "fake");
		JSONObject result = da.deleteSubDomainRecord("12345");
		assertNotNull(result);
		assertFalse(result.optBoolean("RST"));
	}

	@Test
	void setSubDomainRecordStatus_noNetwork_returnsError() {
		DomainAli da = DomainAli.getInstance("fake", "fake");
		JSONObject result = da.setSubDomainRecordStatus("12345", true);
		assertNotNull(result);
		assertFalse(result.optBoolean("RST"));
	}

	@Test
	void getSubdomainInfo_noNetwork_returnsError() {
		DomainAli da = DomainAli.getInstance("fake", "fake");
		JSONObject result = da.getSubdomainInfo("example.com", "www", "A");
		assertNotNull(result);
		assertFalse(result.optBoolean("RST"));
	}
}
