package com.gdxsoft.web.weixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UXml;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.weixin.WeiXinMsg;
import com.gdxsoft.weixin.WxCardSign;

public class WeiXinLocal extends WeiXinBase {
	private static final long serialVersionUID = -8933564154514124953L;
	private static Logger LOGGER = LoggerFactory.getLogger(WeiXinLocal.class);
	private static HashMap<String, WeiXinLocal> MAP = new HashMap<String, WeiXinLocal>();

	/**
	 * 处理微信消息，例如验证服务器或记录微信推送的消息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String handleWxMessage(javax.servlet.http.HttpServletRequest request) throws Exception {
		/**
		 * 用于微信验证 （服务器配置(已启用)） URL(服务器地址) http 所在配置页面
		 * https://mp.weixin.qq.com/advanced/advanced?action=dev&t=advanced/dev&token=1790916964&lang=zh_CN
		 **/

		String sup_unid = request.getParameter("sup_unid");
		if (sup_unid == null || sup_unid.trim().length() == 0) {
			LOGGER.warn("not sup_unid");
			return "not sup_unid";
		}
		String wx_cfg_no = request.getParameter("wx_cfg_no");
		if (wx_cfg_no == null || wx_cfg_no.trim().length() == 0) {
			LOGGER.warn("not wx_cfg_no");
			return "not wx_cfg_no";
		}
		// 微信服务器将发送GET请求到填写的URL上,这里需要判定是否为GET请求
		boolean isGet = request.getMethod().toLowerCase().equals("get");
		WeiXinLocal wxLocal = WeiXinLocal.instance(sup_unid.trim(), wx_cfg_no.trim());
		LOGGER.info("获得微信请求:" + request.getMethod() + " 方式");
		if (isGet) {
			// 验证身份消息，通常只有一次
			String rst = wxLocal.valid(request);
			return rst;
		}

		// 处理微信推送的消息
		WeiXinMsg msg = wxLocal.getMsg(request);
		wxLocal.recordMsg(msg);

		WeiXin weixin = WeiXin.instance(sup_unid.trim(), wx_cfg_no.trim());
		WeiXinPostMsgHandle handle = new WeiXinPostMsgHandle(weixin, null);
		handle.handle(msg);

		return "success";

	}

	/**
	 * 初始化微信服务器端配置
	 * 
	 * @param sup_unid  商户unid
	 * @param wx_cfg_no 商户微信号(gh_xxx)
	 * @return
	 * @throws Exception
	 */
	public static WeiXinLocal instance(String sup_unid, String wx_cfg_no) throws Exception {
		if (sup_unid == null || sup_unid.trim().length() == 0) {
			throw new Exception("sup_unid not setting");
		}
		String key = sup_unid + "---" + wx_cfg_no;

		if (MAP.containsKey(key)) {
			return MAP.get(key);
		}
		WeiXinLocal w = new WeiXinLocal(sup_unid, wx_cfg_no);
		boolean rst = w.init();
		if (rst) {
			MAP.put(key, w);
			return w;
		} else {
			throw new Exception(w.getLastErr());
		}

	}

	WeiXinLocal(String sup_unid, String wx_cfg_no) {
		super(sup_unid, wx_cfg_no);

	}

	/**
	 * 获取微信对送的消息
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public WeiXinMsg getMsg(HttpServletRequest request) throws IOException {
		BufferedReader reader = request.getReader();
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			if (sb.length() > 0) {
				sb.append("\n");
			}
			sb.append(line);
		}
		// 将微信返回的消息解码
		byte[] buf = sb.toString().getBytes("iso8859-1");
		String str = new String(buf, "utf-8");
		LOGGER.info(str);

		WeiXinMsg m = new WeiXinMsg(str);
		return m;
	}

	public void recordMsg(WeiXinMsg msg) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO WX_MSG_RECV( M_MsgID, M_TOUSERNAME, M_FROMUSERNAME, M_MSGTYPE, M_CREATETIME"
				+ ", M_CONTENT, M_PicUrl, M_MediaId, M_Format, M_ThumbMediaId, M_Location_X, M_Location_Y, M_Scale"
				+ ", M_Label, M_Title, M_Description, M_Url, M_Event, M_EventKey, M_Ticket, M_Latitude"
				+ ", M_Longitude, M_Precision, SUP_ID, RDATE, M_XML, WX_CFG_NO)");
		sb.append("\n");
		sb.append("  VALUES(  @M_MsgID, @M_TOUSERNAME, @M_FROMUSERNAME, @M_MSGTYPE, @M_CREATETIME"
				+ ", @M_CONTENT, @M_PicUrl, @M_MediaId, @M_Format, @M_ThumbMediaId, @M_Location_X, @M_Location_Y, @M_Scale"
				+ ", @M_Label, @M_Title, @M_Description, @M_Url, @M_Event, @M_EventKey, @M_Ticket, @M_Latitude"
				+ ", @M_Longitude, @M_Precision, @SUP_ID, @sys_DATE, @M_XML,@WX_CFG_NO)");

		Document doc = UXml.asDocument(msg.getXml());

		RequestValue rv = new RequestValue();
		NodeList nl = doc.getFirstChild().getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);
			if (n.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			Element ele = (Element) n;
			Object val = ele.getTextContent();

			String name = ele.getNodeName();
			if (name.equals("CreateTime")) {
				Date d = new Date();
				long t = Long.parseLong(val.toString()) * 1000;
				d.setTime(t);

				val = Utils.getDateString(d, "yyyy-MM-dd HH:mm:ss");
			}
			rv.addValue("M_" + name, val.toString());

		}
		rv.addValue("sup_id", this.getSupId());
		rv.addValue("M_XML", msg.getXml());
		rv.addValue("WX_CFG_NO", this.getWxCfgNo());

		DataConnection cnn = new DataConnection();
		cnn.setConfigName("");
		cnn.setRequestValue(rv);

		cnn.executeUpdate(sb.toString());

		cnn.close();
	}

	/**
	 * 验证消息真实性
	 * 
	 * @param request
	 * @return
	 */
	public String valid(HttpServletRequest request) {
		// 验证URL真实性
		String signature = request.getParameter("signature");// 微信加密签名
		LOGGER.info("signature:" + signature);
		String timestamp = request.getParameter("timestamp");// 时间戳
		LOGGER.info("timestamp:" + timestamp);
		String nonce = request.getParameter("nonce");// 随机数
		LOGGER.info("nonce:" + nonce);
		String echostr = request.getParameter("echostr");// 随机字符串
		LOGGER.info("echostr:" + echostr);

		WxCardSign sign = new WxCardSign();
		sign.AddData(this.getWeiXinToken());
		sign.AddData(timestamp);
		sign.AddData(nonce);

		// 2. 将三个参数字符串拼接成一个字符串进行sha1加密
		String temp = sign.GetSignature();

		LOGGER.info("temp:" + temp);
		if (temp.equals(signature)) {
			LOGGER.info("echostr:" + echostr);
			return echostr;
		} else {
			return "error";
		}
	}
}
