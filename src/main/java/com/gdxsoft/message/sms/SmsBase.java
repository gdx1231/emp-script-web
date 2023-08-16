package com.gdxsoft.message.sms;

public class SmsBase {
	private static String[] HZ = { "０", "１", "２", "３", "４", "５", "６", "７", "８", "９" };

	/**
	 * 替换手机号中的中文数字
	 * @param s
	 * @return
	 */
	public static String replacePhoneHz(String s) {
		if (s == null) {
			return s;
		}
		s = s.trim();
		if (s.length() == 0) {
			return s;
		}

		for (int i = 0; i < HZ.length; i++) {
			s = s.replace(HZ[i], i + "");
		}
		return s;
	}

	/**
	 * 中国手机号加+86，例如腾讯
	 * 
	 * @param phone
	 * @return
	 */
	public static String chinesePhoneAddPlus86(String phone) {
		phone = phone.replace(" ", "").replace("-","");
		phone = replacePhoneHz(phone);
		if (phone.startsWith("+")) {
			return phone;
		} else {
			return "+86" + phone;
		}
	}

	/**
	 * 中国手机号去除+86，例如阿里云
	 * 
	 * @param phone
	 * @return
	 */
	public static String chinesePhoneRemovePlus86(String phone) {
		phone = phone.replace(" ", "").replace("-","");
		phone = replacePhoneHz(phone);
		if (!phone.startsWith("+86")) {
			return phone;
		} else {
			return phone.substring(3);
		}
	}
	
	private String regionId;

	private String smsAppId;
	
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

	/**
	 * @return the smsAppId
	 */
	public String getSmsAppId() {
		return smsAppId;
	}

	/**
	 * @param smsAppId the smsAppId to set
	 */
	public void setSmsAppId(String smsAppId) {
		this.smsAppId = smsAppId;
	}

	/**
	 * @return the regionId
	 */
	public String getRegionId() {
		return regionId;
	}

	/**
	 * @param regionId the regionId to set
	 */
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
}
