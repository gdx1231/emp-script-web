package com.gdxsoft.web.weixin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.gdxsoft.easyweb.data.DTRow;
import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UFile;
import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.easyweb.utils.msnet.MStr;
import com.gdxsoft.web.dao.WebUser;
import com.gdxsoft.weixin.Config;
import com.gdxsoft.weixin.Html;
import com.gdxsoft.weixin.QyConfig;
import com.gdxsoft.weixin.QyHtml;
import com.gdxsoft.weixin.Ssl;
import com.gdxsoft.weixin.WeiXinButton;
import com.gdxsoft.weixin.WeiXinGroup;
import com.gdxsoft.weixin.WeiXinMaterial;
import com.gdxsoft.weixin.WeiXinMaterialArticle;
import com.gdxsoft.weixin.WeiXinSign;
import com.gdxsoft.weixin.WeiXinTicket;
import com.gdxsoft.weixin.WeiXinUser;
import com.gdxsoft.weixin.WeiXinUserList;
import com.gdxsoft.weixin.WxCard;
import com.gdxsoft.weixin.WxCardBaseInfo;
import com.gdxsoft.weixin.WxCardCash;
import com.gdxsoft.weixin.WxCardDiscount;
import com.gdxsoft.weixin.WxCardGeneralCoupon;
import com.gdxsoft.weixin.WxCardGift;

public class WeiXin implements Serializable {
	private static Logger LOGGER = Logger.getLogger(WeiXin.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 4885379068747410119L;
	/**
	 * 所有微信公众号配置的缓存， ConcurrentHashMap 解决同步的问题
	 */
	private static Map<String, WeiXin> MAP = new ConcurrentHashMap<String, WeiXin>();

	private static String DB_TYPE;

	/**
	 * 访问数据库的前缀
	 */
	private static String DbPrefix = "";

	/**
	 * 设置访问数据库的前缀
	 * 
	 * @param dbPrefix
	 */
	public synchronized static void setDbPrefix(String dbPrefix) {
		DbPrefix = dbPrefix;
	}

	/**
	 * 清除所有配置缓存
	 */
	public static void clear() {
		LOGGER.info("清除微信配置");
		MAP.clear();
	}

	/**
	 * 删除指定的缓存
	 * 
	 * @param wxCfgNo 微信号内码
	 * @return
	 */
	public static boolean remove(String wxCfgNo) {

		if (MAP.containsKey(wxCfgNo)) {
			MAP.remove(wxCfgNo);
			LOGGER.info("删除微信配置：" + wxCfgNo);
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 是否存在微信
	 * 
	 * @param wxCfgNo 微信号内码
	 * @return
	 */
	public static boolean hasWeiXin(String wxCfgNo) {
		return MAP.containsKey(wxCfgNo);
	}

	/**
	 * 获取已经存在微信
	 * 
	 * @param wxCfgNo 微信号内码
	 * @return
	 */
	public static WeiXin getWeiXin(String wxCfgNo) {
		if (MAP.containsKey(wxCfgNo)) {
			return MAP.get(wxCfgNo);
		} else {
			return null;
		}
	}

	/**
	 * 获取数据库前缀
	 * 
	 * @return
	 */
	public static String getDbPrefix() {
		return DbPrefix;
	}

	/**
	 * 获取数据库类型
	 * 
	 * @return
	 */
	public static String getDbType() {
		if (DB_TYPE == null) {
			DataConnection cnn = new DataConnection();
			cnn.setConfigName("");
			String dbtype = cnn.getCurrentConfig().getType();
			DB_TYPE = dbtype.toLowerCase();
			cnn.close();
		}
		return DB_TYPE;
	}

	/**
	 * 根据appid获取微信配置
	 * 
	 * @param appId
	 * @return
	 */
	public static WeiXin instanceFromAppId(String appId) {

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT a.*,b.sup_unid FROM ");
		sb.append(getDbPrefix());
		sb.append("BAS_WX_CFG A");
		sb.append(" inner join sup_main b on a.rel_sup_unid=b.sup_unid WHERE a.WX_APP_ID='");
		sb.append(appId.replace("'", "''"));
		// sb.append("' and WX_CFG_TYPE
		// in('WX_TYPE_FUWUHAO','WX_TYPE_DINGYUEHAO')");
		sb.append("'");

		// 获取服务号或订阅号的配置
		String sql = sb.toString();
		DTTable tbSup = DTTable.getJdbcTable(sql, "");
		if (tbSup == null) {
			return null;
		}
		String sup_unid = null;
		String wx_cfg_no = null;
		for (int i = 0; i < tbSup.getCount(); i++) {
			try {
				String is_def = tbSup.getCell(i, "WX_CFG_IS_DEFAULT").toString();
				if (tbSup.getCount() == 1 || (is_def != null && is_def.equals("COM_YES"))) {
					// 一条记录或默认设置
					sup_unid = tbSup.getCell(i, "SUP_UNID").toString();
					wx_cfg_no = tbSup.getCell(i, "wx_cfg_no").toString();
					break;
				}
			} catch (Exception e) {

			}
		}

		if (sup_unid == null || wx_cfg_no == null) {
			return null;
		}
		return instance(sup_unid, wx_cfg_no);
	}

	/**
	 * 根据sup_id获取微信参数配置
	 * 
	 * @param mySupId
	 * @return
	 */
	public static WeiXin instance(int mySupId) {

		// 获取服务号或订阅号的配置
		String sql = "SELECT a.*,b.sup_unid FROM " + getDbPrefix() + "BAS_WX_CFG A"
				+ " inner join sup_main b on a.rel_sup_unid=b.sup_unid WHERE b.SUP_ID=" + mySupId
				+ " and WX_CFG_TYPE in('WX_TYPE_FUWUHAO','WX_TYPE_DINGYUEHAO')";
		DTTable tbSup = DTTable.getJdbcTable(sql, "");
		if (tbSup == null) {
			return null;
		}
		String sup_unid = null;
		String wx_cfg_no = null;
		for (int i = 0; i < tbSup.getCount(); i++) {
			try {
				String is_def = tbSup.getCell(i, "WX_CFG_IS_DEFAULT").toString();
				if (tbSup.getCount() == 1 || (is_def != null && is_def.equals("COM_YES"))) {
					// 一条记录或默认设置
					sup_unid = tbSup.getCell(i, "SUP_UNID").toString();
					wx_cfg_no = tbSup.getCell(i, "wx_cfg_no").toString();
					break;
				}
			} catch (Exception e) {

			}
		}

		if (sup_unid == null || wx_cfg_no == null) {
			return null;
		}

		return instance(sup_unid, wx_cfg_no);
		// String key = "SUP_ID=" + mySupId;
		// if (MAP.containsKey(key)) {
		// WeiXin w = MAP.get(key);
		// if (!w.getWeiXinCfg().checkIsExpired()) {
		// return w;
		// }
		// }
		// WeiXin w = new WeiXin();
		// w.init(mySupId);
		// MAP.put(key, w);
		// return w;
	}

	/**
	 * 根据 wx_cfg_no 获取微信定义
	 * 
	 * @param wx_cfg_no 指定微信号参数(gh_xxxxx)
	 * @return
	 */
	public static WeiXin instance(String wx_cfg_no) {
		StringBuilder sb = new StringBuilder();
		sb.append("select REL_SUP_UNID from  ");
		sb.append(getDbPrefix());
		sb.append("bas_wx_cfg where wx_cfg_no='");
		sb.append(wx_cfg_no.replace("'", "''"));
		sb.append("'");
		String sqlSupUnid = sb.toString();

		DTTable tbSupUnid = DTTable.getJdbcTable(sqlSupUnid);
		if (tbSupUnid.getCount() > 0) {
			String sup_unid = tbSupUnid.getCell(0, 0).toString();
			return instance(sup_unid, wx_cfg_no);
		} else {
			System.out.println("公众号信息不存在: " + wx_cfg_no.replace("<", ""));
			return null;
		}

	}

	/**
	 * 检查微信配置是否合法
	 * 
	 * @param w
	 * @return
	 */
	public static String checkWeiXinValid(WeiXin w) {
		if (w == null) {
			// 初始化失败的微信
			return "微信配置为null";
		}

		if (!w.isOk()) {
			return "微信配置初始化不成功 isOk )";
		}
		if (w.isGsh_) { // 企业号
			if (w.getQyCfg() == null) {
				return " 微信配置QyCfg为null";
			}

			if (w.getQyCfg().checkIsExpired()) {
				return "微信配置超时  ";
			}
		} else {
			if (w.getWeiXinCfg() == null) {
				return "微信配置 WeiXinCfg 为null   ";
			}
			if (w.getWeiXinCfg().checkIsExpired()) {

				return "微信配置超时";
			}
		}

		return null;
	}

	/**
	 * 获取微信定义，避免多次定义，在获取时首先判断已存在的合法性，然后在获取
	 * 
	 * @param sup_unid  商户的sup_unid
	 * @param wx_cfg_no 微信内码
	 * @return
	 */
	private synchronized static WeiXin instanceSynchronized(String sup_unid, String wx_cfg_no) {
		if (hasWeiXin(wx_cfg_no)) {
			WeiXin w = getWeiXin(wx_cfg_no);
			String error = checkWeiXinValid(w);
			if (error != null) {
				// 初始化失败的微信
				LOGGER.error(error + " (" + wx_cfg_no + ")");
				remove(wx_cfg_no);
			} else {
				LOGGER.info("同步获取已存在配置 (" + wx_cfg_no + ")");
				return w;
			}
		}

		WeiXin w = new WeiXin();
		LOGGER.info("创建新的配置 (" + wx_cfg_no + ")");

		w.init(sup_unid, wx_cfg_no);
		if (!w.isOk()) {
			LOGGER.error("创建配置失败 (" + wx_cfg_no + ")");
			return null;
		}
		MAP.put(wx_cfg_no, w);

		DataConnection cnn = new DataConnection();
		try {
			// cache到缓存中，利于console调用
			JSONObject obj = w.toJson();

			RequestValue rv1 = new RequestValue();
			rv1.addValue("tag", wx_cfg_no);
			rv1.addValue("v", obj.toString());
			rv1.addValue("sup_unid", sup_unid);

			String dbName = "";
			if (getDbType().equals("mssql")) {
				// 一下部分便于Console调用，避免WeiXin access token 重复创建
				// 获取所属数据库名称
				String sql1 = "select INIT_DATABASE from " + getDbPrefix() + "BAS_SUP_MAIN where sup_unid=@sup_unid";

				cnn.setConfigName("");
				cnn.setRequestValue(rv1);

				DTTable tb = DTTable.getJdbcTable(sql1, cnn);
				if (tb.isOk()) {
					dbName = tb.getCell(0, 0).toString();
					if (getDbType().equals("mssql")) {
						dbName += "..";
					} else {
						dbName += ".";
					}
				}
			}
			// 保存到目标数据库的sys_default
			String sqlup = "delete from " + dbName + "sys_default where tag=@tag and sup_id=-1";
			String sqlup1 = "INSERT INTO " + dbName + "SYS_DEFAULT(TAG,DEFAULT_VALUE, sup_id)VALUES(@tag, @V, -1)";

			cnn.setConfigName("");
			cnn.setRequestValue(rv1);

			cnn.executeUpdate(sqlup);
			cnn.executeUpdate(sqlup1);

		} catch (Exception e) {
			System.out.println("WeiXin instance: " + e.getMessage());
		} finally {
			cnn.close();
		}

		return w;
	}

	/**
	 * 获取微信定义
	 * 
	 * @param sup_unid  商户的sup_unid
	 * @param wx_cfg_no 微信内码
	 * @return
	 */
	public static WeiXin instance(String sup_unid, String wx_cfg_no) {
		if (hasWeiXin(wx_cfg_no)) {
			WeiXin w = getWeiXin(wx_cfg_no);
			String error = checkWeiXinValid(w);
			if (error != null) {
				// 初始化失败的微信
				LOGGER.error(error + " (" + wx_cfg_no + ")");
				remove(wx_cfg_no);
			} else {
				return w;
			}
		}

		// 同步获取
		WeiXin w = instanceSynchronized(sup_unid, wx_cfg_no);

		return w;
	}

	public static WeiXin fromJson(JSONObject obj) throws Exception {
		WeiXin w = new WeiXin();
		w.SUP_WEIXIN_APPID = obj.getString("SUP_WEIXIN_APPID");
		w.SUP_WEIXIN_APPSECRET = obj.getString("SUP_WEIXIN_APPSECRET");
		w.SUP_WEIXIN_TOKEN = obj.getString("SUP_WEIXIN_TOKEN");
		w.SUP_WEIXIN_SHOP_ID = obj.getString("SUP_WEIXIN_SHOP_ID");
		w.SUP_WEIXIN_SHOP_KEY = obj.getString("SUP_WEIXIN_SHOP_KEY");
		w.WX_CFG_NO = obj.getString("WX_CFG_NO");
		w.WX_CFG_NAME = obj.getString("WX_CFG_NAME");

		w.WX_CFG_TYPE_ = obj.getString("WX_CFG_TYPE");
		w.sup_id_ = obj.getInt("sup_id");
		w.sup_unid_ = obj.getString("sup_unid");

		w.isOk_ = obj.optBoolean("isOk");
		w.isWeiXinPay_ = obj.optBoolean("isWeiXinPay");
		w.isFwh_ = obj.optBoolean("isFwh");

		Config cfg = Config.fromJson(obj.getJSONObject("cfg"));

		w.cfg_ = cfg;

		return w;
	}

	private String SUP_WEIXIN_APPID;
	private String SUP_WEIXIN_APPSECRET;
	private String SUP_WEIXIN_TOKEN;
	private String SUP_WEIXIN_SHOP_ID;
	private String SUP_WEIXIN_SHOP_KEY;
	private String WX_CFG_NO; // 微信内码
	private String WX_CFG_NAME; // 公众号名称

	private boolean isOk_;

	private Html html_;
	private Config cfg_;
	private int sup_id_;
	private String sup_unid_;
	private boolean isWeiXinPay_; // 是否开通微信支付
	private String WX_CFG_TYPE_;
	private boolean isFwh_; // 是否为服务号
	private QyHtml qyHtml_;
	private QyConfig qyCfg_;
	private boolean isGsh_;

	public JSONObject toJson() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("SUP_WEIXIN_APPID", SUP_WEIXIN_APPID);
		obj.put("SUP_WEIXIN_APPSECRET", SUP_WEIXIN_APPSECRET);
		obj.put("SUP_WEIXIN_TOKEN", SUP_WEIXIN_TOKEN);
		obj.put("SUP_WEIXIN_SHOP_ID", SUP_WEIXIN_SHOP_ID);
		obj.put("SUP_WEIXIN_SHOP_KEY", SUP_WEIXIN_SHOP_KEY);
		obj.put("WX_CFG_NO", WX_CFG_NO);
		obj.put("WX_CFG_NAME", WX_CFG_NAME);

		obj.put("isOk", isOk_);
		obj.put("sup_id", sup_id_);
		obj.put("sup_unid", sup_unid_);
		obj.put("isWeiXinPay", isWeiXinPay_);
		obj.put("WX_CFG_TYPE", WX_CFG_TYPE_);

		obj.put("isFwh", isFwh_);
		obj.put("isGsh", this.isGsh_);

		if (this.isGsh_) { // 公司号
			JSONObject cfg = this.qyCfg_.toJson();
			obj.put("qyCfg", cfg);
		} else {
			JSONObject cfg = this.cfg_.toJson();
			obj.put("cfg", cfg);
		}
		return obj;
	}

	/**
	 * 是否为服务号
	 * 
	 * @return
	 */
	public boolean isFwh() {
		return isFwh_;
	}

	/**
	 * 是否开通微信支付
	 * 
	 * @return
	 */
	public boolean isWeiXinPay() {
		return isWeiXinPay_;
	}

	public WeiXinUser authFromCode(String code) {
		if (this.html_.getAccessToken(code)) {
			WeiXinUser u = this.html_.getWeiXinUserInfo();
			return u;
		} else {
			System.out.println(html_.getLastErr());
			return null;
		}
	}

	/**
	 * 发送微信红包
	 * 
	 * @param usr_unid
	 * @param red_uid
	 * @return
	 */
	public JSONObject sendRedPackage(String usr_unid, String red_uid, String userip) {
		WeiXinRedPackage pkg = new WeiXinRedPackage(this);
		JSONObject rst = pkg.sendRedPackage(usr_unid, red_uid, userip);
		return rst;
	}

	/**
	 * 获取指定日期的账单
	 * 
	 * @param bill_date 八位yyyymmdd格式，例如 20150527
	 * @param skipExits 是否跳过已存在记录
	 * 
	 * @return null无错误，非空为错误提示
	 * @throws Exception
	 */
	public String fetchWeiXinBill(String bill_date, boolean skipExits) throws Exception {
		// 20150527
		bill_date = bill_date.replace("-", "");
		int ext_id = -1;

		String sqlExists = "select EXT_ID from sys_ext where ext_ref_tag='WX_BILL' and EXT_TAG='"
				+ this.WX_CFG_NO.replace("'", "''") + "' and EXT_CDATE='" + bill_date + "' and sup_id=" + this.sup_id_;
		DTTable tbExists = DTTable.getJdbcTable(sqlExists);
		// 数据是否已经下载
		if (tbExists.getCount() > 0) {
			if (skipExits) { // 跳过已经下载数据
				return null;
			}
			ext_id = tbExists.getCell(0, 0).toInt();
		}

		String rst = this.cfg_.downloadWeiXinBill(bill_date);

		if (rst.length() == 0) {
			return null;
		}

		DataConnection cnn = new DataConnection();
		cnn.setConfigName("");

		try {
			if (rst.startsWith("<xml")) {// 有错误
				if (rst.indexOf("No Bill Exist") < 0) {
					return rst;
				} else {
					// 没有数据 提示
					this.recordDownloadBillLog(bill_date, ext_id, rst, cnn);
					return null;
				}
			}

			String[] lines = rst.split("\n");

			// 获取名称与字段之间的映射关系
			// 交易时间,公众账号ID,商户号,子商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,
			// 付款银行,货币种类,总金额,企业红包金额,微信退款单号,商户退款单号,退款金额,企业红包退款金额,退款类型,退款状态,商品名称,商户数据包,手续费,费率
			String sql = "select * from bas_tag where bas_tag_grp='WX_BILL_FIELD'";
			DTTable tb = DTTable.getJdbcTable(sql, cnn);
			HashMap<String, String> map = new HashMap<String, String>();

			for (int i = 0; i < tb.getCount(); i++) {
				String BAS_TAG_NAME = tb.getCell(i, "BAS_TAG_NAME").toString();
				String BAS_TAG_PARA1 = tb.getCell(i, "BAS_TAG_PARA1").toString();
				map.put(BAS_TAG_NAME.trim(), BAS_TAG_PARA1.trim());
			}
			String[] names = lines[0].split(",");
			ArrayList<String> fields = new ArrayList<String>();

			for (int i = 0; i < names.length; i++) {
				String name = names[i].trim().replace("\n", "").replace("\r", "");
				if (map.containsKey(name)) {
					fields.add(map.get(name));
				} else {

					if (name.indexOf("交易时间") >= 0) {
						// 返回字段交易时间是5个字符，第0个字符不显示，需要单独处理
						fields.add(map.get("交易时间"));
					} else {
						// 字段映射不存在
						fields.add("");
					}
				}
			}

			for (int i = 1; i < lines.length - 2; i++) {
				this.newOrUpdateBill(fields, lines[i], cnn);
			}
			// 记录下载账单Log
			this.recordDownloadBillLog(bill_date, ext_id, rst, cnn);
			return null;
		} catch (Exception err) {
			return err.getMessage();
		} finally {
			cnn.close();
		}

	}

	/**
	 * 记录下载账单日志
	 * 
	 * @param bill_date
	 * @param ext_id
	 * @param log
	 * @param cnn
	 */
	private void recordDownloadBillLog(String bill_date, int ext_id, String log, DataConnection cnn) {
		RequestValue rv = new RequestValue();
		rv.addValue("EXT_VAL", log);
		rv.addValue("bill_date", bill_date);
		rv.addValue("WX_CFG_NO", this.WX_CFG_NO);
		rv.addValue("G_SUP_ID", this.sup_id_);

		cnn.setRequestValue(rv);
		if (ext_id == -1) {

			// 记录下载日志
			String newSql = "INSERT INTO SYS_EXT( EXT_REF_TAG, EXT_VAL, EXT_CDATE, EXT_MDATE,EXT_TAG, SUP_ID, ADM_ID)"
					+ " VALUES( 'WX_BILL', @EXT_VAL, @bill_date, getdate(), @WX_CFG_NO, @G_SUP_ID, @G_ADM_ID)";
			cnn.executeUpdate(newSql);
		} else {
			String update = "updae sys_ext set EXT_VAL=@EXT_VAL,EXT_MDATE=getdate() where ext_id= " + ext_id;
			cnn.executeUpdate(update);
		}
	}

	private void newOrUpdateBill(ArrayList<String> fields, String line, DataConnection cnn) {
		String[] vals = line.split(",\\`");
		int hash = line.hashCode();
		String wx_ord_id = null;
		String trade_status = null;
		for (int i = 0; i < vals.length; i++) {
			if (fields.get(i).equals("WX_ORD_ID")) {
				wx_ord_id = vals[i];
			}
			if (fields.get(i).equals("TRADE_STATUS")) {
				trade_status = vals[i];
			}
		}

		// 检查是否存在数据
		String checkSql = "select BILL_ID, BILL_HASH from WX_BILL where WX_ORD_ID='"
				+ wx_ord_id.trim().replace("'", "''") + "' and trade_status='" + trade_status.replace("'", "''")
				+ "' and sup_id=" + this.sup_id_;
		DTTable tb = DTTable.getJdbcTable(checkSql, cnn);
		boolean isnew = false;
		int BILL_ID = -1;
		if (tb.getCount() == 0) {
			isnew = true;
		} else {
			BILL_ID = tb.getCell(0, 0).toInt();
			int BILL_HASH = tb.getCell(0, 1).toInt();

			if (BILL_HASH == hash) {// 数据没有变化
				return;
			}
		}

		RequestValue rv = new RequestValue();
		rv.addValue("BILL_SOURCE", line);
		rv.addValue("BILL_HASH", hash);

		if (isnew) { // 新建数据
			StringBuilder newSql = new StringBuilder();
			newSql.append("INSERT INTO WX_BILL(WX_CFG_NO,BILL_CDATE, BILL_HASH, BILL_SOURCE, SUP_ID");
			StringBuilder newSql1 = new StringBuilder();
			newSql1.append(") values('" + this.WX_CFG_NO.replace("'", "''") + "', getdate(),@BILL_HASH, @BILL_SOURCE, "
					+ sup_id_);
			for (int i = 0; i < vals.length; i++) {
				String field = fields.get(i);
				if (field.trim().length() == 0) {
					continue;
				}
				String val = vals[i].replace("`", "").replace("\n", "").replace("\r", "");
				rv.addValue(field, val);
				newSql.append("\n ,");
				newSql.append(field);

				newSql1.append("\n ,@");
				newSql1.append(field);

			}
			newSql1.append(")\n");
			String sql = newSql.toString() + newSql1.toString();
			cnn.setRequestValue(rv);
			cnn.executeUpdate(sql);
		} else {// 更新数据
			StringBuilder upSql = new StringBuilder();
			upSql.append("update WX_BILL set BILL_MDATE=getdate(), BILL_HASH=@BILL_HASH, BILL_SOURCE=@BILL_SOURCE");

			for (int i = 0; i < vals.length; i++) {
				String field = fields.get(i);
				if (field.trim().length() == 0) {
					continue;
				}

				String val = vals[i].replace("`", "").replace("\n", "").replace("\r", "");

				rv.addValue(field, val);

				upSql.append("\n ,");
				upSql.append(field);
				upSql.append("=@");
				upSql.append(field);
			}
			upSql.append(" where bill_id=" + BILL_ID);
			cnn.setRequestValue(rv);
			cnn.executeUpdate(upSql.toString());
		}
	}

	/**
	 * 获取微信分组信息，并放到数据库中
	 * 
	 * @throws Exception
	 */
	public void fetchWeiXinGroups() throws Exception {
		List<WeiXinGroup> grps = this.cfg_.getWeiXinGroups();
		String sql = "SELECT * FROM WX_GROUP WHERE WX_CFG_NO='" + this.WX_CFG_NO.replace("'", "''") + "' and SUP_ID="
				+ this.sup_id_;
		DTTable tb = DTTable.getJdbcTable(sql);
		HashMap<Integer, DTRow> map = new HashMap<Integer, DTRow>();
		for (int i = 0; i < tb.getCount(); i++) {
			DTRow r = tb.getRow(i);
			int grpId = r.getCell("WX_GRP_ID").toInt();
			map.put(grpId, r);
		}
		MStr s = new MStr();
		for (int i = 0; i < grps.size(); i++) {
			WeiXinGroup g = grps.get(i);
			int grpId = g.getId();
			if (map.containsKey(grpId)) {
				DTRow r = map.get(grpId);
				String name = r.getCell("WX_GRP_NAME").toString();

				int count = r.getCell("WX_GRP_COUNT").getValue() == null ? 0 : r.getCell("WX_GRP_COUNT").toInt();

				if (!g.getName().equals(name) || g.getCount() != count) {
					s.al("update wx_group set WX_GRP_NAME='" + g.getName().replace("'", "''") + "',WX_GRP_COUNT="
							+ g.getCount() + ",WX_GRP_MDATE=getdate() where WX_GRP_ID=" + grpId + " and sup_id="
							+ this.sup_id_);
				}
			} else {
				s.al("INSERT INTO WX_GROUP(WX_GRP_ID, WX_GRP_NAME, WX_GRP_COUNT, SUP_ID, WX_GRP_CDATE,wx_cfg_no)"
						+ "VALUES(" + grpId + ", '" + g.getName().replace("'", "''") + "', " + g.getCount() + ", "
						+ this.sup_id_ + ", getdate(),'" + this.WX_CFG_NO.replace("'", "''") + "');");
			}
		}
		if (s.length() > 0) {
			DataConnection cnn = new DataConnection();
			cnn.setConfigName("");

			if (getDbType().equals("mssql")) {
				cnn.executeUpdate(s.toString());
			} else {
				String[] sqls = s.toString().split(";");
				for (int i = 0; i < sqls.length; i++) {
					String sqla = sqls[i];
					if (sqla.trim().length() == 0) {
						continue;
					}
					cnn.executeUpdate(sqla);
				}
			}
			cnn.close();
		}
	}

	/**
	 * 获取所有卡券列表
	 * 
	 * @return
	 */
	public List<String> fetchCards() {
		List<String> totals = new ArrayList<String>();
		List<String> al = this.cfg_.getCard().getCards(0, 50);
		totals.addAll(al);
		int inc = 0;
		while (al.size() == 50) {
			inc++;
			al = this.cfg_.getCard().getCards(inc * 50, 50);
			totals.addAll(al);
		}

		return totals;
	}

	/**
	 * 获取卡券信息
	 * 
	 * @param card_id
	 * @return
	 * @throws Exception
	 */
	public boolean getCard(String card_id) throws Exception {
		Object o = this.cfg_.getCard().getCard(card_id);
		if (o == null) {
			return false;
		}
		String colors = "select bas_tag_name,bas_tag_para1 from bas_tag where bas_tag_grp='WX_CARD_COLOR'";
		DTTable tbcolors = DTTable.getJdbcTable(colors);

		HashMap<String, String> colorsMap = new HashMap<String, String>();
		for (int i = 0; i < tbcolors.getCount(); i++) {
			String name = tbcolors.getCell(i, 0).toString();
			String val = tbcolors.getCell(i, 1).toString();
			colorsMap.put(val, name);
		}
		WxCard card = (WxCard) o;
		String sql = "SELECT * FROM WX_CARD_MAIN WHERE wx_cfg_no='" + this.WX_CFG_NO.replace("'", "''") + "' and ";

		if (getDbType().equals("mssql")) {
			// alter table WX_CARD_MAIN alter COLUMN WX_CARD_ID varchar(100)
			// COLLATE Chinese_PRC_CS_AI
			sql += "WX_MEDIA_ID = '" + card_id.replace("'", "''") + "'";
		} else {
			// AUTH_WEIXIN_ID 大小写敏感，mysql 设置字段未 utf8bin类型
			sql += "WX_MEDIA_ID = '" + card_id.replace("'", "''") + "'";
		}

		DTTable tb = DTTable.getJdbcTable(sql);
		RequestValue rv = new RequestValue();
		rv.addValue("SUP_ID", this.sup_id_);
		rv.addValue("wx_cfg_no", this.WX_CFG_NO);
		rv.addValue("CARD_TYPE", card.getCardType());

		WxCardBaseInfo bi = card.getBaseInfo();
		rv.addValue("CARD_BRAND_NAME", bi.getBrandName());
		rv.addValue("CARD_TITLE", bi.getTitle());
		rv.addValue("CARD_SUB_TITLE", bi.getSubTitle());
		rv.addValue("CARD_NOTICE", bi.getNotice());

		int date_type = bi.getDateInfo().getInt("type");
		rv.addValue("CARD_DATE_TYPE", date_type);

		// 时间
		if (date_type == 1) {
			long begin_timestamp = bi.getDateInfo().getLong("begin_timestamp");
			long end_timestamp = bi.getDateInfo().getLong("end_timestamp");
			Date sd = new Date();
			sd.setTime(begin_timestamp * 1000);
			Date ed = new Date();
			ed.setTime(end_timestamp * 1000);
			rv.addValue("CARD_DATE_FROM", Utils.getDateString(sd, "yyyy-MM-dd HH:mm:ss"));
			rv.addValue("CARD_DATE_TO", Utils.getDateString(ed, "yyyy-MM-dd HH:mm:ss"));

		} else {
			int CARD_DATE_SPAN = bi.getDateInfo().getInt("fixed_term");
			rv.addValue("CARD_DATE_SPAN", CARD_DATE_SPAN);
		}
		// 将卡券的颜色值转换为名称
		if (bi.getColor() != null) {
			if (colorsMap.containsKey(bi.getColor())) {
				rv.addValue("CARD_COLOR", colorsMap.get(bi.getColor()));
			} else {
				rv.addValue("CARD_COLOR", bi.getColor());
			}
		}

		rv.addValue("CARD_DESCRIPTION", bi.getDescription());
		rv.addValue("CARD_SKU_QUANTITY", bi.getQuantity());
		rv.addValue("CARD_IS_USE_CUSTOM_CODE", bi.getUseCustomCode() ? "COM_YES" : "COM_NO");
		rv.addValue("CARD_IS_BIND_OPENID", bi.getBindOpenid() ? "COM_YES" : "COM_NO");
		rv.addValue("CARD_IS_CAN_SHARE", bi.getCanShare() ? "COM_YES" : "COM_NO");
		rv.addValue("CARD_IS_CAN_GIVE_FRIEND", bi.getCanGiveFriend() ? "COM_YES" : "COM_NO");
		rv.addValue("WX_CARD_STATUS", bi.getStatus());

		rv.addValue("CARD_GET_LIMIT", bi.getGetLimit());
		rv.addValue("CARD_SERVICE_PHONE", bi.getServicePhone());
		rv.addValue("CARD_SOURCE", bi.getSource());
		rv.addValue("CARD_CUSTOM_URL_NAME", bi.getCustomUrlName());
		rv.addValue("CARD_CUSTOM_URL", bi.getCustomUrl());
		rv.addValue("CARD_CUSTOM_URL_SUB_TITLE", bi.getCustomUrlSubTitle());
		rv.addValue("CARD_PROMOTION_URL_NAME", bi.getPromotionUrlName());
		rv.addValue("CARD_PROMOTION_URL_SUB_TITLE", bi.getPromotionUrlSubTitle());

		rv.addValue("WX_CARD_ID", card_id);
		rv.addValue("CARD_CODE_TYPE", bi.getCodeType());
		rv.addValue("CARD_IMG_URL", bi.getLogoUrl());
		if (bi.getLogoUrl() != null) {
			try {
				byte[] buf = Ssl.download(bi.getLogoUrl());
				rv.addValue("CARD_IMG", buf, "binary", buf.length);
			} catch (Exception err) {
				System.out.println(err.getMessage());
			}
		}

		JSONObject data = card.getData();
		if (data.has("deal_detail")) {
			rv.addValue("EXT_DEAL_DETAIL", data.optString("deal_detail"));
		}
		if (data.has("least_cost")) {
			rv.addValue("ext_least_cost", data.optInt("least_cost"));
		}
		if (data.has("reduce_cost")) {
			rv.addValue("ext_reduce_cost", data.optInt("reduce_cost"));
		}
		if (data.has("discount")) {
			rv.addValue("EXT_DISCOUNT", data.optInt("discount"));
		}
		if (data.has("default_detail")) {
			rv.addValue("EXT_DEFAULT_DETAIL", data.optString("default_detail"));
		}
		if (data.has("gift")) {
			rv.addValue("EXT_GIFT", data.optString("gift"));
		}

		DataConnection cnn = new DataConnection();
		cnn.setConfigName("");
		cnn.setRequestValue(rv);
		StringBuilder sb = new StringBuilder();
		if (tb.getCount() == 0) {

			sb.append("INSERT INTO WX_CARD_MAIN(CARD_UID, CARD_TYPE, CARD_BRAND_NAME, CARD_TITLE"
					+ ", CARD_SUB_TITLE, CARD_NOTICE, CARD_DATE_TYPE, CARD_DATE_FROM, CARD_DATE_TO"
					+ ", CARD_DATE_SPAN, CARD_COLOR, CARD_DESCRIPTION, CARD_SKU_QUANTITY"
					+ ", CARD_IS_USE_CUSTOM_CODE, CARD_IS_BIND_OPENID, CARD_IS_CAN_SHARE, CARD_IS_CAN_GIVE_FRIEND"
					+ ", CARD_GET_LIMIT, CARD_SERVICE_PHONE, CARD_SOURCE, CARD_CUSTOM_URL_NAME, CARD_CUSTOM_URL"
					+ ", CARD_CUSTOM_URL_SUB_TITLE, CARD_PROMOTION_URL_NAME, CARD_PROMOTION_URL, CARD_PROMOTION_URL_SUB_TITLE"
					+ ", CARD_STATUS, WX_CARD_ID, CARD_CDATE, SUP_ID, CARD_CODE_TYPE"
					+ ", CARD_IMG_URL, EXT_DEFAULT_DETAIL, EXT_DEAL_DETAIL, EXT_GIFT, EXT_LEAST_COST, EXT_REDUCE_COST"
					+ ", EXT_DISCOUNT, WX_CFG_NO, CARD_GRP_UID, CARD_IMG, WX_CARD_STATUS)\n");
			sb.append("  VALUES(@sys_UnID, @CARD_TYPE, @CARD_BRAND_NAME, @CARD_TITLE, @CARD_SUB_TITLE"
					+ ", @CARD_NOTICE, @CARD_DATE_TYPE, @CARD_DATE_FROM, @CARD_DATE_TO"
					+ ", @CARD_DATE_SPAN, @CARD_COLOR, @CARD_DESCRIPTION, @CARD_SKU_QUANTITY"
					+ ", @CARD_IS_USE_CUSTOM_CODE, @CARD_IS_BIND_OPENID, @CARD_IS_CAN_SHARE, @CARD_IS_CAN_GIVE_FRIEND"
					+ ", @CARD_GET_LIMIT, @CARD_SERVICE_PHONE, @CARD_SOURCE, @CARD_CUSTOM_URL_NAME, @CARD_CUSTOM_URL"
					+ ", @CARD_CUSTOM_URL_SUB_TITLE, @CARD_PROMOTION_URL_NAME, @CARD_PROMOTION_URL, @CARD_PROMOTION_URL_SUB_TITLE"
					+ ", 'COM_YES', @WX_CARD_ID, @sys_date, @SUP_ID, @CARD_CODE_TYPE"
					+ ", @CARD_IMG_URL, @EXT_DEFAULT_DETAIL, @EXT_DEAL_DETAIL, @EXT_GIFT, @EXT_LEAST_COST"
					+ ", @EXT_REDUCE_COST, @EXT_DISCOUNT, @WX_CFG_NO, @CARD_GRP_UID,@CARD_IMG,@WX_CARD_STATUS)\n");

		} else {
			String card_uid = tb.getCell(0, "CARD_UID").toString();
			rv.addValue("card_uid", card_uid);

			sb.append("UPDATE WX_CARD_MAIN SET\n");
			sb.append("   CARD_TYPE = @CARD_TYPE,\n");
			sb.append("   CARD_BRAND_NAME = @CARD_BRAND_NAME,\n");
			sb.append("   CARD_TITLE = @CARD_TITLE,\n");
			sb.append("   CARD_SUB_TITLE = @CARD_SUB_TITLE,\n");
			sb.append("   CARD_NOTICE = @CARD_NOTICE,\n");
			sb.append("   CARD_DATE_TYPE = @CARD_DATE_TYPE,\n");
			sb.append("   CARD_DATE_FROM = @CARD_DATE_FROM,\n");
			sb.append("   CARD_DATE_TO = @CARD_DATE_TO,\n");
			sb.append("   CARD_DATE_SPAN = @CARD_DATE_SPAN,\n");
			sb.append("   CARD_COLOR = @CARD_COLOR,\n");
			sb.append("   CARD_DESCRIPTION = @CARD_DESCRIPTION,\n");
			sb.append("   CARD_SKU_QUANTITY = @CARD_SKU_QUANTITY,\n");
			sb.append("   CARD_IS_USE_CUSTOM_CODE = @CARD_IS_USE_CUSTOM_CODE,\n");
			sb.append("   CARD_IS_BIND_OPENID = @CARD_IS_BIND_OPENID,\n");
			sb.append("   CARD_IS_CAN_SHARE = @CARD_IS_CAN_SHARE,\n");
			sb.append("   CARD_IS_CAN_GIVE_FRIEND = @CARD_IS_CAN_GIVE_FRIEND,\n");
			sb.append("   CARD_GET_LIMIT = @CARD_GET_LIMIT,\n");
			sb.append("   CARD_SERVICE_PHONE = @CARD_SERVICE_PHONE,\n");
			sb.append("   CARD_SOURCE = @CARD_SOURCE,\n");
			sb.append("   CARD_CUSTOM_URL_NAME = @CARD_CUSTOM_URL_NAME,\n");
			sb.append("   CARD_CUSTOM_URL = @CARD_CUSTOM_URL,\n");
			sb.append("   CARD_CUSTOM_URL_SUB_TITLE = @CARD_CUSTOM_URL_SUB_TITLE,\n");
			sb.append("   CARD_PROMOTION_URL_NAME = @CARD_PROMOTION_URL_NAME,\n");
			sb.append("   CARD_PROMOTION_URL = @CARD_PROMOTION_URL,\n");
			sb.append("   CARD_PROMOTION_URL_SUB_TITLE = @CARD_PROMOTION_URL_SUB_TITLE,\n");
			sb.append("   CARD_STATUS = 'COM_YES',\n");
			sb.append("   wx_CARD_STATUS = @wx_CARD_STATUS,\n");
			sb.append("   WX_CARD_ID = @WX_CARD_ID,\n");
			sb.append("   CARD_MDATE = @sys_date,\n");
			sb.append("   CARD_CODE_TYPE = @CARD_CODE_TYPE,\n");
			sb.append("   CARD_IMG_URL = @CARD_IMG_URL,\n");
			sb.append("   EXT_DEFAULT_DETAIL = @EXT_DEFAULT_DETAIL,\n");
			sb.append("   EXT_DEAL_DETAIL = @EXT_DEAL_DETAIL,\n");
			sb.append("   EXT_GIFT = @EXT_GIFT,\n");
			sb.append("   EXT_LEAST_COST = @EXT_LEAST_COST, CARD_IMG=@CARD_IMG, \n");
			sb.append("   EXT_REDUCE_COST = @EXT_REDUCE_COST,\n");
			sb.append("   EXT_DISCOUNT = @EXT_DISCOUNT ");
			sb.append("WHERE CARD_UID=@CARD_UID\n");
		}

		cnn.executeUpdate(sb.toString());
		cnn.close();

		return true;
	}

	/**
	 * 创建卡片到微信
	 * 
	 * @param rv
	 * @return
	 * @throws Exception
	 */
	public String createCard(RequestValue rv) throws Exception {
		String sql = "SELECT * FROM WX_CARD_MAIN WHERE CARD_UID=@CARD_UID AND SUP_ID=@G_SUP_ID";
		DTTable tb = DTTable.getJdbcTable(sql, "", rv);
		JSONObject rst = new JSONObject();

		if (tb.getCount() == 0) {
			rst.put("RST", false);
			rst.put("ERR", "没有数据");
			return rst.toString();
		}

		if (tb.getCell(0, "WX_CARD_ID").getValue() != null
				&& tb.getCell(0, "WX_CARD_ID").toString().trim().length() > 0) {
			rst.put("RST", false);
			rst.put("ERR", "已经上传了，您可以新建一个");
			return rst.toString();
		}
		// 上传图片

		Object img = tb.getCell(0, "CARD_IMG").getValue();
		if (img == null) {
			rst.put("RST", false);
			rst.put("ERR", "没有指定图片");
			return rst.toString();
		}
		String filePath = UPath.getPATH_IMG_CACHE() + "/weixin/" + rv.s("CARD_UID") + ".jpg";
		UFile.createBinaryFile(filePath, (byte[]) img, true);

		String imgurl = this.cfg_.getCard().uploadLogo(filePath);
		if (imgurl == null) {
			rst.put("RST", false);
			rst.put("ERR", "上传图片失败");
			return rst.toString();
		}
		String sqlUpdateImg = "update wx_card_main set CARD_IMG_URL='" + imgurl.replace("'", "''")
				+ "' where CARD_UID=@CARD_UID AND SUP_ID=@G_SUP_ID";
		DataConnection cnn = new DataConnection();
		cnn.setConfigName("");
		cnn.setRequestValue(rv);

		cnn.executeUpdate(sqlUpdateImg);
		cnn.close();

		String CARD_TYPE = tb.getCell(0, "CARD_TYPE").toString();

		if (CARD_TYPE.equals("GENERAL_COUPON")) { // 通用券
			WxCardGeneralCoupon c = new WxCardGeneralCoupon();
			WxCardBaseInfo baseInfo = c.getBaseInfo();
			this.setCardBaseInfo(baseInfo, tb.getRow(0), imgurl);
			String default_detail = tb.getCell(0, "EXT_DEFAULT_DETAIL").toString();

			c.setDefaultDetail(default_detail);

			return this.createCard1(c, cnn);

		} else if (CARD_TYPE.equals("CASH")) {// 现金卡
			WxCardCash c = new WxCardCash();
			WxCardBaseInfo baseInfo = c.getBaseInfo();
			this.setCardBaseInfo(baseInfo, tb.getRow(0), imgurl);

			// 消费启用金额
			Object EXT_LEAST_COST = tb.getCell(0, "EXT_LEAST_COST").getValue();
			if (EXT_LEAST_COST != null) {
				int iEXT_LEAST_COST = Integer.parseInt(EXT_LEAST_COST.toString());
				c.setLeastCost(iEXT_LEAST_COST);
			}

			Object oEXT_LEAST_COST = tb.getCell(0, "EXT_LEAST_COST").getValue();
			if (oEXT_LEAST_COST == null) {
				throw new Exception("折扣抵用金额未定义");
			}

			// 抵用金额
			int EXT_REDUCE_COST = tb.getCell(0, "EXT_REDUCE_COST").toInt();
			c.setReduceCost(EXT_REDUCE_COST);

			return this.createCard1(c, cnn);

		} else if (CARD_TYPE.equals("DISCOUNT")) {// 打折卡
			WxCardDiscount c = new WxCardDiscount();
			WxCardBaseInfo baseInfo = c.getBaseInfo();
			this.setCardBaseInfo(baseInfo, tb.getRow(0), imgurl);
			Object EXT_DISCOUNT = tb.getCell(0, "EXT_DISCOUNT").getValue();
			if (EXT_DISCOUNT != null) {
				int EXT_DISCOUNT1 = Integer.parseInt(EXT_DISCOUNT.toString());
				c.setDiscount(EXT_DISCOUNT1);
			} else {
				throw new Exception("折扣未定义");
			}

			return this.createCard1(c, cnn);

		} else if (CARD_TYPE.equals("GIFT")) {// 礼品卡
			WxCardGift c = new WxCardGift();
			WxCardBaseInfo baseInfo = c.getBaseInfo();
			this.setCardBaseInfo(baseInfo, tb.getRow(0), imgurl);
			String EXT_GIFT = tb.getCell(0, "EXT_GIFT").toString();
			if (EXT_GIFT == null || EXT_GIFT.trim().length() == 0) {
				throw new Exception("礼品名称没定义");

			}
			c.setGift(EXT_GIFT);
			return this.createCard1(c, cnn);

		} else {
			rst.put("RST", false);
			rst.put("ERR", "此卡券类型程序还没定义");

			return rst.toString();
		}
	}

	private String createCard1(WxCard c, DataConnection cnn) throws JSONException {
		System.out.println(c.toJsonString());

		JSONObject rst = new JSONObject();

		String card_id = cfg_.getCard().createCard(c);
		if (card_id != null && card_id.trim().length() > 0) {
			String sqlUpdateId = "update wx_card_main set WX_CARD_ID='" + card_id.replace("'", "''")
					+ "' where CARD_UID=@CARD_UID AND SUP_ID=@G_SUP_ID";
			cnn.executeUpdate(sqlUpdateId);
			cnn.close();
			rst.put("RST", true);
			return rst.toString();
		} else {
			rst.put("RST", false);
			rst.put("ERR", cfg_.getLastErr());

			return rst.toString();
		}
	}

	private WxCardBaseInfo setCardBaseInfo(WxCardBaseInfo info, DTRow row, String imgurl) throws Exception {
		info.setLogoUrl(imgurl);

		String title = row.getCell("CARD_TITLE").toString();
		info.setTitle(title);

		String subTitle = row.getCell("CARD_SUB_TITLE").toString();
		if (subTitle != null && subTitle.trim().length() > 0) {
			info.setSubTitle(subTitle);
		}
		String des = row.getCell("CARD_DESCRIPTION").toString();
		info.setDescription(des);

		String CARD_BRAND_NAME = row.getCell("CARD_BRAND_NAME").toString();
		info.setBrandName(CARD_BRAND_NAME);

		String color = row.getCell("CARD_COLOR").toString();
		info.setColor(color);

		// code 码展示类型
		int CARD_CODE_TYPE = row.getCell("CARD_CODE_TYPE").toInt();
		info.setCodeType(CARD_CODE_TYPE);

		// 1：固定日期区间，2：固定时长（自领取后按天算）
		int CARD_DATE_TYPE = row.getCell("CARD_DATE_TYPE").toInt();
		if (CARD_DATE_TYPE == 1) {
			String sd = row.getCell("CARD_DATE_FROM").toString();
			String ed = row.getCell("CARD_DATE_TO").toString();
			Date dsd = Utils.getDate(sd);
			Date ded = Utils.getDate(ed);
			info.setDateInfoTimeRange(dsd, ded);
		} else if (CARD_DATE_TYPE == 2) {
			// 自领取后多少天开始生效
			int CARD_DATE_SPAN = row.getCell("CARD_DATE_SPAN").toInt();
			info.setDateInfoFixTerm(CARD_DATE_SPAN);
		}

		// 是否自定义code 码
		String CARD_IS_USE_CUSTOM_CODE = row.getCell("CARD_IS_USE_CUSTOM_CODE").toString();
		if (CARD_IS_USE_CUSTOM_CODE != null && CARD_IS_USE_CUSTOM_CODE.equals("COM_YES")) {
			info.setUseCustomCode(true);
		} else {
			info.setUseCustomCode(false);
		}

		// 是否可分享
		String CARD_IS_CAN_SHARE = row.getCell("CARD_IS_CAN_SHARE").toString();
		if (CARD_IS_CAN_SHARE == null || CARD_IS_CAN_SHARE.equals("COM_NO")) {
			info.setCanShare(false);
		} else {
			info.setCanShare(true);
		}

		// 卡券是否可转赠
		String CARD_IS_CAN_GIVE_FRIEND = row.getCell("CARD_IS_CAN_GIVE_FRIEND").toString();
		if (CARD_IS_CAN_GIVE_FRIEND == null || CARD_IS_CAN_GIVE_FRIEND.equals("COM_NO")) {
			info.setCanGiveFriend(false);
		} else {
			info.setCanGiveFriend(true);
		}

		// 是否指定用户领取
		String CARD_IS_BIND_OPENID = row.getCell("CARD_IS_BIND_OPENID").toString();
		if (CARD_IS_BIND_OPENID != null && CARD_IS_BIND_OPENID.equals("COM_YES")) {
			info.setBindOpenid(true);
		}

		// 每人最大领取次数
		int CARD_GET_LIMIT = row.getCell("CARD_GET_LIMIT").toInt();
		info.setGetLimit(CARD_GET_LIMIT);

		// 卡券库存的数量
		int CARD_SKU_QUANTITY = row.getCell("CARD_SKU_QUANTITY").toInt();
		info.setQuantity(CARD_SKU_QUANTITY);

		// 使用提醒
		String CARD_NOTICE = row.getCell("CARD_NOTICE").toString();
		info.setNotice(CARD_NOTICE); // "请出示二维码核销"

		// 客服电话
		String CARD_SERVICE_PHONE = row.getCell("CARD_SERVICE_PHONE").toString();
		if (CARD_SERVICE_PHONE != null && CARD_SERVICE_PHONE.trim().length() > 0) {
			info.setServicePhone(CARD_SERVICE_PHONE); // "13910409333"
		}

		return info;
	}

	/**
	 * 设置用户的微信分组
	 * 
	 * @param ids
	 * @param wx_grp_id
	 * @return
	 * @throws Exception
	 */
	public boolean setWeiXinUsersGroup(String ids, int wx_grp_id) throws Exception {
		if (ids.indexOf("'") >= 0 || ids.indexOf(")") >= 0) {
			return false;
		}
		String sql = "select USR_ID,AUTH_WEIXIN_ID,IS_WEIXIN_SUBSCRIBE from web_user where usr_id in(" + ids
				+ ") and (wx_grp is null or wx_grp!=" + wx_grp_id + ")";
		DTTable tbUser = DTTable.getJdbcTable(sql);
		DataConnection cnn = new DataConnection();
		cnn.setConfigName("");

		for (int i = 0; i < tbUser.getCount(); i++) {
			String openId = tbUser.getCell(i, "AUTH_WEIXIN_ID").toString();
			boolean isok = this.getWeiXinCfg().changeWeiXinUserGroup(wx_grp_id, openId);
			if (isok) {
				String sql1 = "update web_user set wx_grp=" + wx_grp_id + " where usr_id="
						+ tbUser.getCell(i, "usr_id").toInt();
				cnn.executeUpdateNoParameter(sql1);
			}

		}
		cnn.close();
		return true;
	}

	/**
	 * 获取关注用户信息，并导入到Web_User表中
	 * 
	 * @return
	 * @throws Exception
	 */
	public int fetchWeiXinUsers() throws Exception {
		DataConnection cnn = new DataConnection();
		cnn.setConfigName("");
		int inc = 0;
		String sql = "SELECT * FROM SYS_DEFAULT WHERE TAG='SYS_WEIXIN_NEXT_OPEN_ID' AND SUP_ID=" + this.sup_id_;

		DTTable tbCfg = DTTable.getJdbcTable(sql, "");

		// 获取用户列表的最后一个open_id,减少检索次数
		String next_open_id = "";
		if (tbCfg.getCount() == 0) {
			String sqlNew = "INSERT INTO SYS_DEFAULT(TAG, DEFAULT_VALUE, SUP_ID, CDATE)"
					+ "VALUES('SYS_WEIXIN_NEXT_OPEN_ID', '', " + this.sup_id_ + ", getDATE())";
			cnn.executeUpdate(sqlNew);
			cnn.close();
		} else {
			try {
				next_open_id = tbCfg.getCell(0, "DEFAULT_VALUE").toString();
			} catch (Exception e) {
				next_open_id = "";
			}
			if (next_open_id == null) {
				next_open_id = "";
			}
		}
		// 重置微信关注数据
		this.cfg_.resetUserList();

		// 获取用户列表(open_id);
		WeiXinUserList users = this.cfg_.getWeiXinUserList(next_open_id);
		for (String key : users.getUsers().keySet()) {
			String code = users.getUsers().get(key);
			WeiXinUser user = this.cfg_.getWeiXinUserInfo(code);

			this.newOrGetUser(user);
			inc++;

		}
		if (users.getNextOpenid() != null && users.getNextOpenid().trim().length() > 0) {
			// 更新 next_open_id数据
			String sql1 = "update SYS_DEFAULT set DEFAULT_VALUE='" + users.getNextOpenid().replace("'", "''")
					+ "' where TAG='SYS_WEIXIN_NEXT_OPEN_ID' and SUP_ID=" + this.sup_id_;
			cnn.executeUpdate(sql1);
			cnn.close();
		}
		return inc;
	}

	/**
	 * 获取自定义菜单
	 * 
	 * @return
	 */
	public boolean fetchWeiXinButtonsSelf() {

		WeiXinButton butRoot = this.cfg_.getWeiXinButtonsSelf();
		if (butRoot == null) {
			return false;
		}
		DataConnection cnn = new DataConnection();
		cnn.setConfigName("");

		try {
			for (int i = 0; i < butRoot.getSubButtons().size(); i++) {
				WeiXinButton but = butRoot.getSubButtons().get(i);
				String thisKey = this.writeButton(but, null, i, cnn);
				if (but.getSubButtons() != null) {
					for (int m = 0; m < but.getSubButtons().size(); m++) {
						WeiXinButton butSub = but.getSubButtons().get(m);
						this.writeButton(butSub, thisKey, m, cnn);
					}
				}
			}
			return true;
		} catch (Exception err) {
			System.err.println(err.getMessage());
			return false;
		} finally {
			cnn.close();
		}

	}

	private String writeButton(WeiXinButton but, String pkey, int index, DataConnection cnn) {
		String key = but.getKey();
		if (key == null || key.trim().length() == 0) {
			String type = but.getType();
			if (type == null) {
				type = "-----";
			}
			key = but.getName() + "/" + type + "/" + this.WX_CFG_NO;
			key = key.hashCode() + "";
			key = key.replace("-", "f");
			while (key.length() < 36) {
				key = "a" + key;
			}
		}
		RequestValue rv = new RequestValue();
		rv.addValue("wx_cfg_no", this.WX_CFG_NO);
		rv.addValue("SUP_ID", this.sup_id_);
		rv.addValue("BUT_UID", key);

		rv.addValue("BUT_NAME", but.getName());
		rv.addValue("BUT_PUID", pkey);
		rv.addValue("BUT_TYPE", but.getType());
		rv.addValue("BUT_URL", but.getUrl());
		rv.addValue("BUT_MEDIA_ID", but.getMediaId());
		rv.addValue("BUT_LVL", pkey == null ? 0 : 1);
		rv.addValue("BUT_MEMO", but.getJson().toString());
		rv.addValue("BUT_ORD", index);
		cnn.setRequestValue(rv);

		String sqlExists = "select 1 a from WX_BUT where BUT_UID=@but_uid and WX_CFG_NO=@WX_CFG_NO";
		DTTable tbExist = DTTable.getJdbcTable(sqlExists, cnn);
		if (tbExist.getCount() == 0) {
			String sqlNew = "INSERT INTO WX_BUT(BUT_UID, BUT_NAME, BUT_PUID, BUT_TYPE, BUT_URL"
					+ ", BUT_MEDIA_ID, BUT_ORD, BUT_LVL, SUP_ID, BUT_CDATE" + ", BUT_MEMO, WX_CFG_NO)"
					+ " VALUES(@BUT_UID, @BUT_NAME, @BUT_PUID, @BUT_TYPE, @BUT_URL"
					+ ", @BUT_MEDIA_ID, @BUT_ORD, @BUT_LVL, @SUP_ID, @sys_CDATE" + ", @BUT_MEMO, @WX_CFG_NO)";
			cnn.executeUpdate(sqlNew);
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE WX_BUT SET\n");
			sb.append("   BUT_NAME = @BUT_NAME,\n");
			sb.append("   BUT_PUID = @BUT_PUID,\n");
			sb.append("   BUT_TYPE = @BUT_TYPE,\n");
			sb.append("   BUT_URL = @BUT_URL,\n");
			sb.append("   BUT_MEDIA_ID = @BUT_MEDIA_ID,\n");
			sb.append("   BUT_ORD = @BUT_ORD,\n");
			sb.append("   BUT_LVL = @BUT_LVL,\n");
			sb.append("   BUT_MDATE = @sys_MDATE,\n");
			sb.append("   BUT_MEMO = @BUT_MEMO \n");
			sb.append("WHERE BUT_UID=@BUT_UID and WX_CFG_NO = @WX_CFG_NO");

			cnn.executeUpdate(sb.toString());
		}
		return key;
	}

	public int fetchWeiXinArticles() {
		List<WeiXinMaterialArticle> al = this.cfg_.getWeiXinMaterialArticles();
		DataConnection cnn = new DataConnection();
		cnn.setConfigName("");
		try {
			for (int i = 0; i < al.size(); i++) {
				WeiXinMaterialArticle item = al.get(i);
				this.writeArticle(item, cnn);

			}
			return al.size();
		} catch (Exception err) {
			System.out.println(err.getMessage());
			return -1;
		} finally {
			cnn.close();
		}
	}

	public int fetchWeiXinMaterials(String type) {
		List<WeiXinMaterial> al = this.cfg_.getWeiXinMaterials(type);
		DataConnection cnn = new DataConnection();
		cnn.setConfigName("");
		try {
			for (int i = 0; i < al.size(); i++) {
				WeiXinMaterial item = al.get(i);
				this.writeMaterial(item, cnn);

			}
			return al.size();
		} catch (Exception err) {
			System.out.println(err.getMessage());
			return -1;
		} finally {
			cnn.close();
		}
	}

	/**
	 * 将材料数据写入数据库
	 * 
	 * @param item
	 * @param cnn
	 */
	private void writeMaterial(WeiXinMaterial item, DataConnection cnn) {
		String mid = item.getMediaId();
		String sql = "SELECT * FROM WX_MATAERIAL WHERE ";
		if (getDbType().equals("mssql")) {
			// alter table WX_MATAERIAL ALTER COLUMN WX_MEDIA_ID varchar(100)
			// COLLATE Chinese_PRC_CS_AI
			sql += "WX_MEDIA_ID = '" + mid.replace("'", "''") + "'";
		} else {
			// AUTH_WEIXIN_ID 大小写敏感，mysql 设置字段未 utf8bin类型
			sql += "WX_MEDIA_ID = '" + mid.replace("'", "''") + "'";
		}
		DTTable tb = DTTable.getJdbcTable(sql, cnn);
		if (tb.getCount() > 0) {
			return;
		}
		RequestValue rv = new RequestValue();
		rv.addValue("META_NAME", item.getName());
		rv.addValue("WX_MEDIA_ID", item.getMediaId());
		rv.addValue("WX_UPDATE_TIME", item.getUpdateTime());
		rv.addValue("META_TYPE", item.getMaterialType());

		rv.addValue("wx_cfg_no", this.WX_CFG_NO);
		rv.addValue("SUP_ID", this.sup_id_);
		String sqlInsert = "INSERT INTO WX_MATAERIAL(META_TYPE,META_NAME, META_DES, WX_MEDIA_ID"
				+ ", WX_UPDATE_TIME, META_SYNC_STATUS, CDATE, SUP_ID,wx_cfg_no)"
				+ "\n VALUES(@META_TYPE,@META_NAME, @META_DES, @WX_MEDIA_ID"
				+ ", @WX_UPDATE_TIME, 'COM_YES', @sys_DATE, @SUP_ID,@wx_cfg_no)";
		cnn.setRequestValue(rv);

		cnn.executeUpdate(sqlInsert);

	}

	public void writeArticle(WeiXinMaterialArticle item, DataConnection cnn) {
		String mid = item.getMediaId();
		String sql = "SELECT * FROM WX_ARTICLE WHERE ART_PIDX is null and ";

		if (getDbType().equals("mssql")) {
			// alter table WX_MATAERIAL ALTER COLUMN WX_MEDIA_ID varchar(100)
			// COLLATE Chinese_PRC_CS_AI
			sql += "WX_MEDIA_ID = '" + mid.replace("'", "''") + "'";
		} else {
			// AUTH_WEIXIN_ID 大小写敏感，mysql 设置字段未 utf8bin类型
			sql += "WX_MEDIA_ID = '" + mid.replace("'", "''") + "'";
		}
		DTTable tb = DTTable.getJdbcTable(sql, cnn);
		if (tb.getCount() > 0) {
			return;
		}

		int idx = this.insertWxArticle(item, null, 0, cnn);
		if (item.isMulti()) {
			for (int i = 0; i < item.getRelated().size(); i++) {
				WeiXinMaterialArticle r = item.getRelated().get(i);
				this.insertWxArticle(r, idx + "", i + 1, cnn);
			}
		}

	}

	private int insertWxArticle(WeiXinMaterialArticle item, String ART_PIDX, int ART_ORD, DataConnection cnn) {
		StringBuilder sb = new StringBuilder();
		RequestValue rv = new RequestValue();
		rv.addValue("ART_PIDX", ART_PIDX);
		rv.addValue("ART_ORD", ART_ORD);
		rv.addValue("WX_MEDIA_ID", item.getMediaId());
		rv.addValue("WX_TITLE", item.getTitle());
		rv.addValue("WX_THUMB_MEDIA_ID", item.getThumbMediaId());
		rv.addValue("WX_AUTHOR", item.getAuthor());
		rv.addValue("WX_DIGEST", item.getDigest());
		rv.addValue("WX_SHOW_COVER_PIC", item.getShowCoverPic());
		rv.addValue("WX_CONTENT", item.getContent());
		rv.addValue("WX_CONTENT_SOURCE_URL", item.getContentSourceUrl());
		rv.addValue("SUP_ID", this.sup_id_);
		// 指定微信号
		rv.addValue("wx_cfg_no", this.WX_CFG_NO);
		cnn.setRequestValue(rv);

		sb.append("INSERT INTO WX_ARTICLE(ART_PIDX, ART_ORD, WX_MEDIA_ID, WX_TITLE, WX_THUMB_MEDIA_ID"
				+ ", WX_AUTHOR, WX_DIGEST, WX_SHOW_COVER_PIC, WX_CONTENT, WX_CONTENT_SOURCE_URL"
				+ ", SUP_ID, CDATE, ART_SYNC_STATUS,wx_cfg_no)\n");
		sb.append("  VALUES(@ART_PIDX, @ART_ORD, @WX_MEDIA_ID, @WX_TITLE, @WX_THUMB_MEDIA_ID"
				+ ", @WX_AUTHOR, @WX_DIGEST, @WX_SHOW_COVER_PIC, @WX_CONTENT, @WX_CONTENT_SOURCE_URL"
				+ ", @SUP_ID, getdate(),  'COM_YES', @wx_cfg_no)");

		cnn.executeUpdate(sb.toString());

		if (ART_PIDX == null) {
			String mid = item.getMediaId();
			String sql = "SELECT ART_IDX FROM WX_ARTICLE WHERE  ART_PIDX is null and ";

			if (getDbType().equals("mssql")) {
				// alter table WX_MATAERIAL ALTER COLUMN WX_MEDIA_ID
				// varchar(100) COLLATE Chinese_PRC_CS_AI
				sql += "WX_MEDIA_ID = '" + mid.replace("'", "''") + "'";
			} else {
				// AUTH_WEIXIN_ID 大小写敏感，mysql 设置字段未 utf8bin类型
				sql += "WX_MEDIA_ID = '" + mid.replace("'", "''") + "'";
			}
			DTTable tb = DTTable.getJdbcTable(sql, cnn);
			return tb.getCell(0, 0).toInt();

		} else {
			return -1;
		}
	}

	/**
	 * 根据 union_id 获取以前其它小程序/公众号注册的用户
	 * 一个用户虽然对多个公众号和应用有多个不同的openid，但他对所有这些同一开放平台账号下的公众号和应用，只有一个unionid
	 * 
	 * @param u
	 * @param cnn
	 * @return
	 */
	private DTRow getWebUserByUnionId(WeiXinUser u, DataConnection cnn) {
		if (u.getUnionid() == null || u.getUnionid().trim().length() == 0) {
			return null;
		}

		// 一个用户虽然对多个公众号和应用有多个不同的openid，但他对所有这些同一开放平台账号下的公众号和应用，只有一个unionid
		String sql = "select USR_UNID from wx_user where WX_UNION_ID=@WX_UNION_ID order by CDATE";

		DTTable tb = DTTable.getJdbcTable(sql, cnn);

		if (tb.getCount() == 0) {
			return null;
		}

		String usrUnid = tb.getCell(0, 0).toString();

		String sql1 = "select * from web_user where usr_unid='" + usrUnid.replace("'", "''") + "' order by usr_id";
		DTTable tb1 = DTTable.getJdbcTable(sql1, cnn);

		if (tb1.getCount() == 0) {
			return null;
		}

		return tb1.getRow(0);
	}

	/**
	 * 新增或获取用户信息(字段 NEW_USER =1 新用户，0-老用户)
	 * 
	 * @param u
	 * @return
	 * @throws Exception
	 */
	public DTRow newOrGetUser(WeiXinUser u) throws Exception {
		RequestValue rv = new RequestValue();
		DataConnection cnn = new DataConnection();
		cnn.setConfigName("");
		cnn.setRequestValue(rv);

		rv.addValue("WX_CFG_NO", this.WX_CFG_NO);
		rv.addValue("sup_id", this.sup_id_);

		String sex = "";
		if (u.getSex() == 1) {
			sex = "M";
		} else if (u.getSex() == 2) {
			sex = "F";
		}
		rv.addValue("USR_SEX", sex); // 用户性别

		String addr = "";
		if (u.getCountry() != null) {
			addr = u.getCountry();
		}
		if (u.getProvince() != null) {
			addr += " " + u.getProvince();
		}
		if (u.getCity() != null) {
			addr += " " + u.getCity();
		}
		rv.addValue("USR_addr", addr); // 用户地址
		rv.addValue("IS_WEIXIN_SUBSCRIBE", u.getSubscribe());
		rv.addValue("USR_NAME", u.getNickname());
		rv.addValue("USR_PIC", u.getHeadimgurl());
		rv.addValue("AUTH_WEIXIN_ID", u.getOpenid());
		rv.addValue("AUTH_WEIXIN_JSON", u.toString());

		// 微信的 unionid， open.weixin.qq.com
		/*
		 * 这里的不同应用是指在同一微信开发平台下的不同应用
		 * 
		 * 为了识别用户，每个用户针对每个公众号会产生一个安全的openid。
		 * 
		 * 如果需要在多公众号、移动应用之间做用户共通，则需要前往微信开放平台，将这些公众号和应用绑定到一个开放平台账号下，绑定后，
		 * 一个用户虽然对多个公众号和应用有多个不同的openid，但他对所有这些同一开放平台账号下的公众号和应用，只有一个unionid。
		 * 一个微信开放平台只可以绑定10个公众号。
		 * 
		 */
		rv.addValue("WX_UNION_ID", u.getUnionid());

		// 微信分组信息
		// int grp_id = this.cfg_.getWeiXinUserGroup(u.getOpenid());
		int grp_id = u.getGroupId();
		if (grp_id >= 0) {
			String sweixn_grp_id = String.valueOf(grp_id);
			rv.addOrUpdateValue("sweixn_grp_id", sweixn_grp_id);
		}

		StringBuilder builder3 = new StringBuilder();
		builder3.append("SELECT * FROM WX_USER  where WX_CFG_NO=@WX_CFG_NO and AUTH_WEIXIN_ID = @AUTH_WEIXIN_ID");
		String sql = builder3.toString();

		DTTable tbUser = DTTable.getJdbcTable(sql, cnn);

		rv.addValue("USR_LID", u.getOpenid());
		rv.addValue("USR_PWD", u.getOpenid());

		DTRow rWebUser;
		try {
			if (tbUser.getCount() == 0) {// not register
				rWebUser = this.wxUserNew(u, cnn);
			} else {
				String uid = tbUser.getCell(0, "usr_unid").toString();
				rv.addOrUpdateValue("uid", uid);
				rWebUser = this.wxUserUpdate(u, cnn);
			}
			return rWebUser;
		} catch (Exception err) {
			LOGGER.error(err);
			throw err;
		} finally {
			cnn.close();
		}
	}

	/**
	 * 更新微信用户信息
	 * 
	 * @param u
	 * @param cnn
	 * @return
	 * @throws Exception
	 */
	private DTRow wxUserUpdate(WeiXinUser u, DataConnection cnn) throws Exception {
		// OATH2 不提供SUBSCRIBE参数时候，不更新 IS_WEIXIN_SUBSCRIBE 字段
		String IS_WEIXIN_SUBSCRIBE_para;
		if (u.getSubscribe() == null) {
			IS_WEIXIN_SUBSCRIBE_para = "0";
		} else {
			IS_WEIXIN_SUBSCRIBE_para = u.getSubscribe() + "";
		}

		String sqlCheck = "select usr_id from web_user where usr_unid = @uid";
		DTTable tbCheck = DTTable.getJdbcTable(sqlCheck, cnn);
		StringBuilder builder = new StringBuilder();

		String tagNewUser = "0";
		if (tbCheck.getCount() == 0) {
			// 当数据不完整时候，出现有微信用户但没有web_user，需要创建 web_user
			LOGGER.warn("出现有微信用户但没有web_user，需要创建 web_user(" + u.getNickname() + ", " + u.getOpenid() + ")");

			String uid = cnn.getRequestValue().getString("uid");
			this.webUserNew(uid, cnn);

			tagNewUser = "1"; // 新用户标识
		} else {
			String usr_id = tbCheck.getCell(0, 0).toString();
			
			builder.append("UPDATE WEB_USER SET  ");
			builder.append("   USR_PIC			= @USR_PIC");
			builder.append(" , USR_ADDR			= @USR_ADDR");
			builder.append(" , USR_SEX			= @USR_SEX");
			builder.append(" where usr_id=");
			builder.append(usr_id);

			String sqlUp = builder.toString();
			cnn.executeUpdate(sqlUp);
		}

		StringBuilder builder2 = new StringBuilder();
		builder2.append("UPDATE WX_USER SET IS_WEIXIN_SUBSCRIBE ='");
		builder2.append(IS_WEIXIN_SUBSCRIBE_para);
		builder2.append("', AUTH_WEIXIN_JSON = @AUTH_WEIXIN_JSON");
		builder2.append(" , MDATE = @sys_DATE ");
		builder2.append(" , WX_UNION_ID = @WX_UNION_ID ");
		builder2.append(" , WX_GRP = @sweixn_grp_id ");
		builder2.append(" WHERE WX_CFG_NO=@WX_CFG_NO ");
		builder2.append(" 		AND AUTH_WEIXIN_ID = @AUTH_WEIXIN_ID ");
		builder2.append(" 		AND USR_UNID=@uid ");
		String sqlUp1 = builder2.toString();

		cnn.executeUpdate(sqlUp1);

		String sql1 = "SELECT A.*, " + tagNewUser + " AS NEW_USER FROM WEB_USER A where usr_unid=@uid";
		DTTable tbUser = DTTable.getJdbcTable(sql1, cnn);

		return tbUser.getRow(0);
	}

	/**
	 * 创建微信用户信息
	 * 
	 * @param u
	 * @param cnn
	 * @return
	 * @throws Exception
	 */
	private DTRow wxUserNew(WeiXinUser u, DataConnection cnn) throws Exception {
		// 以前注册的微信用户(union_id)
		// 一个用户虽然对多个公众号和应用有多个不同的openid，但他对所有这些同一开放平台账号下的公众号和应用，只有一个unionid
		DTRow exitsWebUser = this.getWebUserByUnionId(u, cnn);

		StringBuilder sb = new StringBuilder();
		String usr_unid;
		if (exitsWebUser == null) {
			usr_unid = cnn.getRequestValue().s("SYS_UNID");
			this.webUserNew(usr_unid, cnn);

		} else {
			usr_unid = exitsWebUser.getCell("usr_unid").toString();
		}

		cnn.getRequestValue().addOrUpdateValue("temp_usr_unid", usr_unid);

		sb.setLength(0);
		sb.append("INSERT INTO WX_USER(WX_CFG_NO, AUTH_WEIXIN_ID, USR_UNID, IS_WEIXIN_SUBSCRIBE");
		sb.append("    , USER_AGENT,  BIND_IP, WX_UNION_ID ");
		sb.append(", AUTH_WEIXIN_JSON, CDATE, SUP_ID, wx_grp)");

		sb.append(" VALUES(@WX_CFG_NO, @AUTH_WEIXIN_ID, @temp_usr_unid, @IS_WEIXIN_SUBSCRIBE");
		sb.append("    ,@SYS_USER_AGENT, @SYS_REMOTEIP, @WX_UNION_ID ");
		sb.append(", @AUTH_WEIXIN_JSON, @sys_date, @SUP_ID,@sweixn_grp_id)");

		cnn.executeUpdate(sb.toString());

		String sql1 = "SELECT A.*, 1 AS NEW_USER FROM WEB_USER A where USR_UNID=@temp_usr_unid";
		DTTable tbUser = DTTable.getJdbcTable(sql1, cnn);

		return tbUser.getRow(0);
	}

	/**
	 * 创建web_user信息
	 * 
	 * @param usrUnid 用户的unid
	 * @param cnn
	 */
	private void webUserNew(String usrUnid, DataConnection cnn) {
		cnn.getRequestValue().addOrUpdateValue("temp_usr_unid", usrUnid);

		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO WEB_USER (USR_LID, USR_PWD, USR_NAME, USR_PIC \n");
		sb.append(" , USR_SEX, USR_ADDR , USR_UNID, \n");
		sb.append("  SUP_ID \n");
		sb.append(" ,USR_CDATE, USR_MDATE,USR_ID) \n");

		sb.append("values(  @USR_LID, @USR_PWD, @USR_NAME, @USR_PIC \n");
		sb.append(" , @USR_SEX, @USR_ADDR, @temp_usr_unid, ");
		sb.append(this.sup_id_);
		sb.append(" , @SYS_DATE, @SYS_DATE, ewa_func.snowflake() )");
		cnn.executeUpdate(sb.toString());
	}

	/**
	 * 所有的JS接口只能在公众号绑定的域名下调用
	 * 
	 * @param rv
	 * @return
	 */
	public String getWeiXinJs(RequestValue rv) {

		String thisUrl = rv.s("SYS_REMOTE_URL");
		if (rv.s("EWA_QUERY_ALL") != null) {
			thisUrl += "?" + rv.s("EWA_QUERY_ALL");
		}
		return this.getWeiXinJs(thisUrl);
	}

	/**
	 * 所有的JS接口只能在公众号绑定的域名下调用
	 * 
	 * @param thisUrl 当前页面的URL
	 * @return
	 */
	public String getWeiXinJs(String thisUrl) {
		WeiXinTicket ticket = null;
		ticket = this.cfg_.getWeiXinTicketJsapi();

		Map<String, String> map = WeiXinSign.sign(ticket.getTicket(), thisUrl);

		StringBuilder sb = new StringBuilder();
		sb.append("	<script id='微信JS-SDK' src=\"//res.wx.qq.com/open/js/jweixin-1.2.0.js\"></script>\n");
		sb.append("	<script style='text/javascript'>\n");
		sb.append("	wx.ready(function () {\n");
		sb.append("		// 1 判断当前版本是否支持指定 JS 接口，支持批量判断\n");
		sb.append("		is_wx_ready=true;\n");
		sb.append("		if(window.wx_ready){window.wx_ready();}\n");
		sb.append("	});\n");

		sb.append("	var weixin____cfg={\n");
		sb.append("	debug : window.wx_debug || false,\n");
		sb.append("	appId : '" + this.getWeiXinAppId() + "',\n");
		sb.append("	timestamp : " + map.get("timestamp") + ",\n");
		sb.append("	nonceStr : '" + map.get("nonceStr") + "',\n");
		sb.append("	signature : '" + map.get("signature") + "',\n");
		sb.append(
				"	jsApiList : [ 'checkJsApi', 'onMenuShareTimeline', 'onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareWeibo',\n");
		sb.append(
				"		'hideMenuItems', 'showMenuItems', 'hideAllNonBaseMenuItem', 'showAllNonBaseMenuItem', 'translateVoice',\n");
		sb.append(
				"		'startRecord', 'stopRecord', 'onRecordEnd', 'playVoice', 'pauseVoice', 'stopVoice', 'uploadVoice',\n");
		sb.append(
				"		'downloadVoice', 'chooseImage', 'previewImage', 'uploadImage', 'downloadImage', 'getNetworkType',\n");
		sb.append(
				"		'openLocation', 'getLocation', 'hideOptionMenu', 'showOptionMenu', 'closeWindow', 'scanQRCode',\n");
		sb.append("		'chooseWXPay', 'openProductSpecificView', 'addCard', 'chooseCard', 'openCard' ]\n");
		sb.append("	};\n");
		sb.append("	wx.config(weixin____cfg);\n");
		sb.append("wx.error(function(res){console.log(res)});");
		sb.append("	</script>\n");

		return sb.toString();
	}

	private WeiXin() {
	}

	/**
	 * 从 BAS_WX_CFG 表中获取微信配置
	 * 
	 * @param sup_unid
	 * @param wx_cfg_no
	 */
	private void init(String sup_unid, String wx_cfg_no) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT A.*,b.sup_id FROM ");
		sb.append(getDbPrefix());
		sb.append("BAS_WX_CFG A");
		sb.append(" inner join SUP_MAIN b on a.rel_sup_unid=b.sup_unid WHERE rel_sup_unid ='");
		sb.append(sup_unid.replace("'", "''"));
		sb.append("' and wx_cfg_no='");
		sb.append(wx_cfg_no.replace("'", "''"));
		sb.append("'");
		String sql = sb.toString();
		DTTable tbSup = DTTable.getJdbcTable(sql, "");

		if (tbSup.getCount() == 0) {
			LOGGER.error("获取配置为空 (" + wx_cfg_no + ")");
			LOGGER.error(sql);
			this.isOk_ = false;
			return;
		}

		this.WX_CFG_NO = wx_cfg_no;
		this.sup_unid_ = sup_unid;

		// 微信应用ID
		try {
			this.sup_id_ = tbSup.getCell(0, "sup_id").toInt();
			this.WX_CFG_TYPE_ = tbSup.getCell(0, "WX_CFG_TYPE").toString();
			if (this.WX_CFG_TYPE_ == null) {
				this.WX_CFG_TYPE_ = "";
			}
			if (this.WX_CFG_TYPE_.equals("WX_TYPE_FUWUHAO")) {
				this.isFwh_ = true; // 不是服务号
			} else if (this.WX_CFG_TYPE_.equals("WX_TYPE_GONGSIHAO")) {
				this.isGsh_ = true; // 是公司号
			}

			this.SUP_WEIXIN_APPID = tbSup.getCell(0, "WX_APP_ID").toString();

			// 微信应用密匙
			this.SUP_WEIXIN_APPSECRET = tbSup.getCell(0, "WX_APP_SECRET").toString();
			// 微信应用令牌
			this.SUP_WEIXIN_TOKEN = tbSup.getCell(0, "WX_APP_TOKEN").toString();

			// 微信商户编号（支付用）
			this.SUP_WEIXIN_SHOP_ID = tbSup.getCell(0, "WX_PAY_ID").toString();
			// 微信商户key（支付用）
			this.SUP_WEIXIN_SHOP_KEY = tbSup.getCell(0, "WX_PAY_KEY").toString();

			// 公众号名称
			this.WX_CFG_NAME = tbSup.getCell(0, "WX_CFG_NAME").toString();

			if (SUP_WEIXIN_APPID != null && SUP_WEIXIN_APPID.trim().length() > 0 && SUP_WEIXIN_APPSECRET != null
					&& SUP_WEIXIN_APPSECRET.trim().length() > 0 && SUP_WEIXIN_TOKEN != null
					&& SUP_WEIXIN_TOKEN.trim().length() > 0) {

				if (this.WX_CFG_TYPE_.equals("WX_TYPE_GONGSIHAO")) {// 企业号

					if (tbSup.getCell(0, "WX_AGENT_ID").getValue() != null) {
						// 外部定义的调用
						int agentId = tbSup.getCell(0, "WX_AGENT_ID").toInt();
						this.qyCfg_ = QyConfig.instance(SUP_WEIXIN_APPID, SUP_WEIXIN_APPSECRET, agentId);
						this.qyHtml_ = new QyHtml(SUP_WEIXIN_APPID, this.qyCfg_.getAccessToken(), agentId);
					} else {
						// 企业号内部调用的接口，例如 组织结构
						this.qyCfg_ = QyConfig.instance(SUP_WEIXIN_APPID, SUP_WEIXIN_APPSECRET);
						this.qyHtml_ = new QyHtml(SUP_WEIXIN_APPID, this.qyCfg_.getAccessToken(), -1);
					}

				} else {

					// 用于获取微信用户认证
					this.html_ = new Html(SUP_WEIXIN_APPID, SUP_WEIXIN_APPSECRET);
					this.cfg_ = Config.instance(SUP_WEIXIN_APPID, SUP_WEIXIN_APPSECRET, SUP_WEIXIN_TOKEN);

					if (SUP_WEIXIN_SHOP_ID == null || SUP_WEIXIN_SHOP_ID.trim().length() == 0
							|| SUP_WEIXIN_SHOP_KEY == null || SUP_WEIXIN_SHOP_KEY.trim().length() == 0) {

					} else {
						this.cfg_.setShopKey(SUP_WEIXIN_SHOP_KEY.trim());
						this.cfg_.setShopId(SUP_WEIXIN_SHOP_ID);
					}
				}
				this.isOk_ = true;
			}
			// 微信支付数字证书
			if (tbSup.getCell(0, "WX_PAY_P12").getValue() != null) {
				byte[] p12 = (byte[]) tbSup.getCell(0, "WX_PAY_P12").getValue();
				if (this.cfg_.initSslContext(p12)) {
					System.out.println("SSL 初始化成功");
					this.isWeiXinPay_ = true;
				}

			}
		} catch (Exception e) {
			LOGGER.error("创建配置失败 (" + wx_cfg_no + ")");
			LOGGER.error(e);
		}
	}

	/**
	 * 初始化对象
	 * 
	 * @param mySupId
	 */
	public void init(int mySupId) {
		this.sup_id_ = mySupId;
		String sql = "SELECT * FROM " + getDbType() + "BAS_SUP_MAIN WHERE SUP_UNID IN ("
				+ "SELECT SUP_UNID FROM SUP_MAIN WHERE SUP_ID=" + mySupId + ")";
		DTTable tbSup = DTTable.getJdbcTable(sql, "");
		if (tbSup.getCount() == 0) {
			return;
		}
		// 微信应用ID
		try {
			this.SUP_WEIXIN_APPID = tbSup.getCell(0, "SUP_WEIXIN_APPID").toString();

			// 微信应用密匙
			this.SUP_WEIXIN_APPSECRET = tbSup.getCell(0, "SUP_WEIXIN_APPSECRET").toString();
			// 微信应用令牌
			this.SUP_WEIXIN_TOKEN = tbSup.getCell(0, "SUP_WEIXIN_TOKEN").toString();

			// 微信商户编号（支付用）
			this.SUP_WEIXIN_SHOP_ID = tbSup.getCell(0, "SUP_WEIXIN_SHOP_ID").toString();
			// 微信商户key（支付用）
			this.SUP_WEIXIN_SHOP_KEY = tbSup.getCell(0, "SUP_WEIXIN_SHOP_KEY").toString();

			if (SUP_WEIXIN_APPID != null && SUP_WEIXIN_APPID.trim().length() > 0 && SUP_WEIXIN_APPSECRET != null
					&& SUP_WEIXIN_APPSECRET.trim().length() > 0 && SUP_WEIXIN_TOKEN != null
					&& SUP_WEIXIN_TOKEN.trim().length() > 0) {

				// 用于获取微信用户认证
				this.html_ = new Html(SUP_WEIXIN_APPID, SUP_WEIXIN_APPSECRET);
				this.cfg_ = Config.instance(SUP_WEIXIN_APPID, SUP_WEIXIN_APPSECRET, SUP_WEIXIN_TOKEN);

				if (SUP_WEIXIN_SHOP_ID == null || SUP_WEIXIN_SHOP_ID.trim().length() == 0 || SUP_WEIXIN_SHOP_KEY == null
						|| SUP_WEIXIN_SHOP_KEY.trim().length() == 0) {

				} else {
					this.cfg_.setShopKey(SUP_WEIXIN_SHOP_KEY.trim());
					this.cfg_.setShopId(SUP_WEIXIN_SHOP_ID);
				}
				this.isOk_ = true;
			}
			if (tbSup.getCell(0, "WEIXIN_SHOP_P12").getValue() != null) {
				byte[] p12 = (byte[]) tbSup.getCell(0, "WEIXIN_SHOP_P12").getValue();
				if (this.cfg_.initSslContext(p12)) {
					System.out.println("SSL 初始化成功");
				}

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 微信应用ID
	 * 
	 * @return
	 */
	public String getWeiXinAppId() {
		return SUP_WEIXIN_APPID;
	}

	/**
	 * 微信应用密匙
	 * 
	 * @return
	 */
	public String getWeiXinAppSecret() {
		return SUP_WEIXIN_APPSECRET;
	}

	/**
	 * 微信应用令牌
	 * 
	 * @return
	 */
	public String getWeiXinToken() {
		return SUP_WEIXIN_TOKEN;
	}

	/**
	 * 微信商户编号（支付用）
	 * 
	 * @return
	 */
	public String getWeiXinShopId() {
		return SUP_WEIXIN_SHOP_ID;
	}

	/**
	 * 微信商户key（支付用）
	 * 
	 * @return
	 */
	public String getWeiXinShopKey() {
		return SUP_WEIXIN_SHOP_KEY;
	}

	/**
	 * 是否初始化成功
	 * 
	 * @return
	 */
	public boolean isOk() {
		return isOk_;
	}

	/**
	 * 获取H5用户验证对象
	 * 
	 * @return
	 */
	public Html getWeiXinHtml() {
		return html_;
	}

	/**
	 * 获取微信操作对象
	 * 
	 * @return
	 */
	public Config getWeiXinCfg() {
		return cfg_;
	}

	/**
	 * 微信内码
	 * 
	 * @return
	 */
	public String getWxCfgNo() {
		return WX_CFG_NO;
	}

	public int getSupId() {
		return sup_id_;
	}

	public String getSupUnid() {
		return sup_unid_;
	}

	/**
	 * 公众号名称
	 * 
	 * @return
	 */
	public String getWxCfgName() {
		return WX_CFG_NAME;
	}

	/**
	 * 序列化表
	 * 
	 * @return
	 * @throws IOException
	 */
	public byte[] toSerialize() throws IOException {
		// Serialize
		ByteArrayOutputStream fos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		oos.writeObject(this);
		oos.close();

		byte[] buf = fos.toByteArray();
		fos.close();

		return buf;
	}

	/**
	 * 从序列化二进制中获取表
	 * 
	 * @param buf
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static WeiXin fromSerialize(byte[] buf) throws IOException, ClassNotFoundException {
		// Serialize

		ByteArrayInputStream fis = new ByteArrayInputStream(buf);
		ObjectInputStream ois = new ObjectInputStream(fis);
		WeiXin tb = (WeiXin) ois.readObject();
		ois.close();
		fis.close();
		return tb;
	}

	/**
	 * 企业号的用于认证OAuth
	 * 
	 * @return the qyHtml_
	 */
	public QyHtml getQyHtml() {
		return qyHtml_;
	}

	/**
	 * 企业号的通讯主调用
	 * 
	 * @return the qyCfg_
	 */
	public QyConfig getQyCfg() {
		return qyCfg_;
	}

	/**
	 * 是否为企业号
	 * 
	 * @return
	 */
	public boolean isQyh() {
		return isGsh_;
	}
}
