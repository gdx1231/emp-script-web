package com.gdxsoft.message.sms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * 阿里云短信接口
 * 
 * @author admin
 *
 */
public class SmsAliImpl extends SmsBase implements ISms {
	public static final String PROVIDER = "ALI";

	// 产品名称:云通信短信API产品,开发者无需替换
	public static final String PRODUCT = "Dysmsapi";
	// 产品域名,开发者无需替换
	public static final String DOMAIN = "dysmsapi.aliyuncs.com";
	public static final String DEF_REGION_ID = "cn-hangzhou";
	static {
		// 可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
	}

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
		SendSmsResponse response = sendSmsAndReturnResponse(phones, templateParam, outId);
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

	private IAcsClient getIAcsClient() throws ClientException {
		String regionId = StringUtils.isBlank(super.getRegionId()) ? DEF_REGION_ID : super.getRegionId();

		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile(regionId, super.getAccessKeyId(),
				super.getAccessKeySecret());
		// DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		DefaultProfile.addEndpoint(regionId, PRODUCT, DOMAIN);
		IAcsClient acsClient = new DefaultAcsClient(profile);

		return acsClient;
	}

	/**
	 * 发送短信并获得发送结果
	 * 
	 * @param phoneNumber
	 * @param signName
	 * @param templateCode
	 * @param templateParam
	 * @param outId
	 * @return
	 * @throws Exception
	 */
	public JSONObject sendSmsAndGetResponse(String phoneNumber, JSONObject templateParam, String outId)
			throws Exception {

		// 发短信
		SendSmsResponse response = sendSmsAndReturnResponse(phoneNumber, templateParam, outId);
		JSONObject rst = getSendResponseResult(response);

		rst.put("PHONE_NUMBER", phoneNumber);
		rst.put("TEMPLATE_PARAM", templateParam);
		rst.put("OUT_ID", outId);

		Thread.sleep(3000L);

		// 查明细
		if (response.getCode() != null && response.getCode().equals("OK")) {
			QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId(), phoneNumber);
			// System.out.println("短信明细查询接口返回数据----------------");
			rst.put("RET_CODE", querySendDetailsResponse.getCode());
			rst.put("RET_MESSAGE", querySendDetailsResponse.getMessage());
			int i = 0;
			JSONArray arr = new JSONArray();
			for (QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse
					.getSmsSendDetailDTOs()) {
				JSONObject r = new JSONObject();
				r.put("Index", i);
				r.put("Content", smsSendDetailDTO.getContent());
				r.put("ErrCode", smsSendDetailDTO.getErrCode());
				r.put("OutId", smsSendDetailDTO.getOutId());
				r.put("PhoneNum", smsSendDetailDTO.getPhoneNum());
				r.put("ReceiveDate", smsSendDetailDTO.getReceiveDate());
				r.put("SendDate", smsSendDetailDTO.getSendDate());
				r.put("SendStatus", smsSendDetailDTO.getSendStatus());
				r.put("Template", smsSendDetailDTO.getTemplateCode());

				arr.put(r);
				i++;
			}
			if (arr.length() > 0) {
				rst.put("LIST", arr);
			}

			rst.put("RET_TOTAL_COUNT", querySendDetailsResponse.getTotalCount());
			rst.put("RET_REQUEST_ID", querySendDetailsResponse.getRequestId());
		}

		return rst;
	}

	/**
	 * 
	 */
	public JSONObject sendSms(String phoneNumber, JSONObject templateParam, String outId) throws Exception {
		SendSmsResponse response = sendSmsAndReturnResponse(phoneNumber, templateParam, outId);
		JSONObject rst = getSendResponseResult(response);
		rst.put("PHONE_NUMBER", phoneNumber);

		rst.put("TEMPLATE_PARAM", templateParam);
		rst.put("OUT_ID", outId);

		return rst;
	}

	private JSONObject getSendResponseResult(SendSmsResponse response) {
		JSONObject rst = new JSONObject();

		rst.put("SEND_COCDE", response.getCode());
		rst.put("SEND_MESSAGE", response.getMessage());
		rst.put("SEND_REQUEST_ID", response.getRequestId());
		rst.put("BIZ_ID", response.getBizId());

		String signName = super.getSmsSignName();
		String templateCode = super.getSmsTemplateCode();

		rst.put("SIGN_NAME", signName);
		rst.put("TEMPLATE_CODE", templateCode);

		return rst;
	}

	/**
	 * 发送短信 并返回发送对象，用于获取结果
	 * 
	 * @param phoneNumber
	 * @param templateParam
	 * @param outId
	 * @return
	 * @throws ClientException
	 */
	private SendSmsResponse sendSmsAndReturnResponse(String phoneNumber, JSONObject templateParam, String outId)
			throws ClientException {

		IAcsClient acsClient = getIAcsClient();

		// 组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();
		// 必填:待发送手机号
		request.setPhoneNumbers(phoneNumber);
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName(super.getSmsSignName());
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(super.getSmsTemplateCode());
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		request.setTemplateParam(templateParam.toString()); // "{\"name\":\"
															// 湖南省长沙市外国语学校\"}"

		// 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");

		if (outId != null && outId.trim().length() > 0) {
			// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			request.setOutId(outId);
		}
		// hint 此处可能会抛出异常，注意catch
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

		return sendSmsResponse;
	}

	/**
	 * 获取发送短信的结果
	 * 
	 * @param bizId
	 * @return
	 * @throws ClientException
	 */
	private QuerySendDetailsResponse querySendDetails(String bizId, String phoneNumber) throws ClientException {
		IAcsClient acsClient = getIAcsClient();
		// 组装请求对象
		QuerySendDetailsRequest request = new QuerySendDetailsRequest();
		// 必填-号码
		request.setPhoneNumber(phoneNumber);
		// 可选-流水号
		request.setBizId(bizId);
		// 必填-发送日期 支持30天内记录查询，格式yyyyMMdd
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
		request.setSendDate(ft.format(new Date()));
		// 必填-页大小
		request.setPageSize(10L);
		// 必填-当前页码从1开始计数
		request.setCurrentPage(1L);

		// hint 此处可能会抛出异常，注意catch
		QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

		return querySendDetailsResponse;
	}

	@Override
	public String getProvider() {
		return PROVIDER;
	}

}
