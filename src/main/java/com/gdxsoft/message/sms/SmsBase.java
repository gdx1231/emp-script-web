package com.gdxsoft.message.sms;

public class SmsBase {
	private String smsTemplateCode;
	private String smsSignName;

	// 开发者自己的AK(在阿里云访问控制台寻找)
	private String accessKeyId;
	private String accessKeySecret;

	/**
	 * 开发者自己的AK(在阿里云访问控制台寻找)
	 * 
	 * @return
	 */
	public String getAccessKeyId() {
		return accessKeyId;
	}

	/**
	 * 开发者自己的AK(在阿里云访问控制台寻找)
	 * 
	 * @param accessKeyId
	 */
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	/**
	 * 开发者自己的AK(在阿里云访问控制台寻找)
	 * 
	 * @return
	 */
	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	/**
	 * 开发者自己的AK(在阿里云访问控制台寻找)
	 * 
	 * @param accessKeySecret
	 */
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	/**
	 * 短信模板编码
	 * 
	 * @return 短信模板编码
	 */
	public String getSmsTemplateCode() {
		return smsTemplateCode;
	}

	/**
	 * 设置短信模板编码
	 * 
	 * @param smsTemplateCode 短信模板编码
	 */
	public void setSmsTemplateCode(String smsTemplateCode) {
		this.smsTemplateCode = smsTemplateCode;
	}

	/**
	 * 短信签名
	 * 
	 * @return 短信签名
	 */
	public String getSmsSignName() {
		return smsSignName;
	}

	/**
	 * 设置短信签名
	 * 
	 * @param smsSignName 短信签名
	 */
	public void setSmsSignName(String smsSignName) {
		this.smsSignName = smsSignName;
	}
}
