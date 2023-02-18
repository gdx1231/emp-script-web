/**
 * 
 */
package com.gdxsoft.message.sms;

import java.util.List;

import org.json.JSONObject;

/**
 * @author admin
 *
 */
public interface ISms {

	public String getSmsAppId();

	public void setSmsAppId(String smsAppId);

	/**
	 * 短信服务节点
	 * 
	 * @return
	 */
	public String getRegionId();

	/**
	 * 短信服务节点
	 * 
	 * @param regionId the regionId to set
	 */
	public void setRegionId(String regionId);

	/**
	 * 获取供应商名称
	 * 
	 * @return
	 */
	public String getProvider();

	/**
	 * 开发者自己的AK(阿里云在访问控制台寻找)
	 * 
	 * @return
	 */
	public String getAccessKeyId();

	/**
	 * 开发者自己的AK(阿里云在访问控制台寻找)
	 * 
	 * @param accessKeyId
	 */
	public void setAccessKeyId(String accessKeyId);

	/**
	 * 开发者自己的AK(阿里云在访问控制台寻找)
	 * 
	 * @return
	 */
	public String getAccessKeySecret();

	/**
	 * 开发者自己的AK(阿里云在访问控制台寻找)
	 * 
	 * @param accessKeySecret
	 */
	public void setAccessKeySecret(String accessKeySecret);

	/**
	 * 短信模板编码
	 * 
	 * @return 短信模板编码
	 */
	public String getSmsTemplateCode();

	/**
	 * 设置短信模板编码
	 * 
	 * @param smsTemplateCode 短信模板编码
	 */
	public void setSmsTemplateCode(String smsTemplateCode);

	/**
	 * 短信签名
	 * 
	 * @return 短信签名
	 */
	public String getSmsSignName();

	/**
	 * 设置短信签名
	 * 
	 * @param smsSignName 短信签名
	 */
	public void setSmsSignName(String smsSignName);

	/**
	 * 发送短信并获得发送结果
	 * 
	 * @param phoneNumber   电话
	 * @param templateParam 模板参数
	 * @param outId         传入的编号
	 * @return
	 * @throws Exception
	 */
	public JSONObject sendSmsAndGetResponse(String phoneNumber, JSONObject templateParam, String outId)
			throws Exception;

	/**
	 * 发送短信
	 * 
	 * @param phoneNumber   电话
	 * @param templateParam 模板参数
	 * @param outId         传入的编号
	 * @return
	 * @throws Exception
	 */
	public JSONObject sendSms(String phoneNumber, JSONObject templateParam, String outId) throws Exception;
	
	public JSONObject sendSms(String[] phoneNumbers, JSONObject templateParam, String outId) throws Exception;
	
	public JSONObject sendSms(List<String> phoneNumbers, JSONObject templateParam, String outId) throws Exception;
}
