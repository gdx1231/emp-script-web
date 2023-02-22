/**
 * 
 */
package com.gdxsoft.message.sms;

import java.util.HashMap;

import org.json.JSONObject;

import com.gdxsoft.easyweb.utils.UNet;
import com.gdxsoft.easyweb.utils.UPath;

/**
 * 使用Android 手机的 myMobKit 程序发送短信 http://www.mymobkit.com/
 * 
 * @author admin
 *
 */
public class SmsMyMobKit {
	/**
	 * 发送短信
	 * 
	 * @param phoneNumber
	 * @param signName
	 * @param message
	 * @return
	 */
	public static JSONObject sendSms(String phoneNumber, String signName, String message, boolean showLog) {
		// To The destination number or contact name.

		// Message Message content.

		// DeliveryReport Optional parameter. 0 for no delivery report, any other values
		// indicate delivery report is required. Default to 1 to receive delivery
		// report.

		// ScAddress Optional parameter to specify the service center address to be used
		// to send SMS. For dual SIMs with different providers, you can provide this
		// address to use a specific SIM card to send SMS.

		// Slot Optional parameter to specify the SIM card to be used to send SMS. Only
		// applicable for dual SIM phone.

		String myMobKitHost = UPath.getInitPara("myMobKitHost");
		String smsUrl = myMobKitHost + "/services/api/messaging/";

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("To", phoneNumber);

		if (signName != null && signName.trim().length() > 0) {
			message = "【" + signName.trim() + "】" + message.trim();
		} else {
			message = message.trim();
		}

		params.put("Message", message);

		UNet net = new UNet();
		net.setIsShowLog(showLog); // 是否显示Debug信息
		
		String rst = net.doPost(smsUrl, params);
		return new JSONObject(rst);
	}
}
