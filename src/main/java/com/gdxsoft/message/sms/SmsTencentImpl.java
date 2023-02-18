package com.gdxsoft.message.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.gdxsoft.easyweb.utils.UJSon;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;

public class SmsTencentImpl extends SmsBase implements ISms {
	public static final String PROVIDER = "Tencent";

	/**
	 * 华北地区(北京) ap-beijing<br>
	 * 华南地区(广州) ap-guangzhou<br>
	 * 华东地区(南京) ap-nanjing<br>
	 */
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
		return this.sendSmsInner(phoneNumbers.toArray(new String[phoneNumbers.size()]), templateParam, outId);
	}

	private String[] convertToTencentParameters(JSONObject templateParam) {
		// 模版参数，从前往后对应的是模版的{1}、{2}等
		List<String> params = new ArrayList<>();
		for (int i = 1; i < 100; i++) {
			String key = i + "";
			if (!templateParam.has(key)) {
				break;
			}
			String val = templateParam.optString(key);
			params.add(val);
		}

		String[] tenCentTemplateParam = params.toArray(new String[params.size()]);

		return tenCentTemplateParam;
	}

	private String[] convertToTencentPhones(String[] phoneNumbers) {
		Map<String, Boolean> map = new HashMap<>();
		List<String> al = new ArrayList<>();
		for (int i = 0; i < phoneNumbers.length; i++) {
			String phone = SmsBase.chinesePhoneAddPlus86(phoneNumbers[i]);
			if (map.containsKey(phone)) {
				continue;
			}

			map.put(phone, true);
			al.add(phone);
		}

		return al.toArray(new String[al.size()]);
	}

	private JSONObject sendSmsInner(String[] phoneNumbers, JSONObject templateParam, String outId) {
		String regionId = StringUtils.isBlank(super.getRegionId()) ? DEF_REGION_ID : super.getRegionId();

		JSONObject rst = UJSon.rstTrue();

		rst.put("PHONE_NUMBER", phoneNumbers);
		rst.put("TEMPLATE_PARAM", templateParam);

		// 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey
		Credential cred = new Credential(super.getAccessKeyId(), super.getAccessKeySecret());

		// 实例化要请求产品(以cvm为例)的client对象
		ClientProfile clientProfile = new ClientProfile();
		clientProfile.setSignMethod(ClientProfile.SIGN_TC3_256);

		SmsClient smsClient = new SmsClient(cred, regionId);
		SendSmsRequest req = new SendSmsRequest();

		rst.put("SmsSdkAppId", super.getSmsAppId());
		req.setSmsSdkAppId(super.getSmsAppId());// appId ,见《创建应用》小节

		// 发送短信的目标手机号，可填多个。
		String[] phones = convertToTencentPhones(phoneNumbers);
		req.setPhoneNumberSet(phones);
		rst.put("phones", phones);

		// 模版id
		// 1706663 工作：{1}，计划截至日期为{2}，请关注。
		req.setTemplateId(super.getSmsTemplateCode());
		rst.put("TemplateId", super.getSmsTemplateCode());

		// 签名内容，不是填签名id,见《创建短信签名和模版》小节
		req.setSignName(super.getSmsSignName());
		rst.put("SignName", super.getSmsSignName());

		// 模版参数，从前往后对应的是模版的{1}、{2}等
		String[] tencentTemplateParam = this.convertToTencentParameters(templateParam);
		req.setTemplateParamSet(tencentTemplateParam);
		rst.put("tenCentTemplateParam", tencentTemplateParam);

		req.setSessionContext(outId);
		rst.put("outId", outId);
		try {
			SendSmsResponse res = smsClient.SendSms(req); // 发送短信
			rst.put("RequestId", res.getRequestId());
			rst.put("StatusSet", res.getSendStatusSet());
		} catch (TencentCloudSDKException e) {
			e.printStackTrace();
			UJSon.rstSetFalse(rst, e.getErrorCode() + "," + e.getLocalizedMessage());
		}

		return rst;
	}

	@Override
	public String getProvider() {
		return PROVIDER;
	}

}
