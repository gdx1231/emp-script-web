package com.gdxsoft.web.weixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UXml;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.weixin.WeiXinMsg;
import com.gdxsoft.weixin.WxCardSign;

public class WeiXinLocal {

	private static HashMap<String, WeiXinLocal> MAP = new HashMap<String, WeiXinLocal>();

	/**
	 * 初始化微信服务器端配置
	 * 
	 * @param sup_unid
	 *            商户unid
	 * @param wx_cfg_no
	 *            商户微信号(gh_xxx)
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
			throw new Exception(w.lastErr_);
		}

	}

	private String sup_unid_;
	private String lastErr_;
	private String appId_;
	private String token_;
	private Integer sup_id_;
	private String wx_cfg_no_;

	private WeiXinLocal(String sup_unid, String wx_cfg_no) {
		this.sup_unid_ = sup_unid;
		this.wx_cfg_no_ = wx_cfg_no;

	}

	private boolean init() throws Exception {
		DataConnection cnn = new DataConnection();
		cnn.setConfigName("");
		String dbtype = cnn.getCurrentConfig().getType();
		String db_prefix = "oneworld_main_data.";
		if (dbtype.toLowerCase().equals("mssql")) {
			db_prefix = "oneworld_main_data..";
		}
		String sql = "select a.*,b.SUP_ID supid from " + db_prefix + "bas_wx_cfg a"
				+ " inner join sup_main b on a.rel_sup_unid=b.sup_unid" + " where a.rel_sup_unid='"
				+ sup_unid_.replace("'", "''") + "' and wx_cfg_no='" + wx_cfg_no_.replace("'", "''") + "'";
		DTTable tbSup = DTTable.getJdbcTable(sql);
		if (tbSup.getCount() == 0) {
			lastErr_ = "not this sup";
			return false;
		}
		appId_ = tbSup.getCell(0, "WX_APP_ID").toString();
		if (appId_ == null || appId_.trim().length() == 0) {
			lastErr_ = "WX_APP_ID not set";
			return false;
		}

		// 微信应用令牌
		token_ = tbSup.getCell(0, "WX_APP_TOKEN").toString();
		if (token_ == null || token_.trim().length() == 0) {
			lastErr_ = "WX_APP_TOKEN not set";
			return false;
		}

		this.sup_id_ = tbSup.getCell(0, "supid").toInt();

		return true;
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
		System.out.println(str);

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
		rv.addValue("sup_id", this.sup_id_);
		rv.addValue("M_XML", msg.getXml());
		rv.addValue("WX_CFG_NO", this.wx_cfg_no_);

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
		System.out.println("signature:" + signature);
		String timestamp = request.getParameter("timestamp");// 时间戳
		System.out.println("timestamp:" + timestamp);
		String nonce = request.getParameter("nonce");// 随机数
		System.out.println("nonce:" + nonce);
		String echostr = request.getParameter("echostr");// 随机字符串
		System.out.println("echostr:" + echostr);

		WxCardSign sign = new WxCardSign();
		sign.AddData(this.token_);
		sign.AddData(timestamp);
		sign.AddData(nonce);

		// 2. 将三个参数字符串拼接成一个字符串进行sha1加密
		String temp = sign.GetSignature();

		System.out.println("temp:" + temp);
		if (temp.equals(signature)) {
			System.out.println("echostr:" + echostr);
			return echostr;
		} else {
			return "error";
		}
	}
}
