/**
 * 
 */
package com.gdxsoft.message.sms;

import org.json.JSONObject;

/**
 * @author admin
 *
 */
public interface ISms {
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
}
