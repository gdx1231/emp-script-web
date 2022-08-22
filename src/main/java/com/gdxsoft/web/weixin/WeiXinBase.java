package com.gdxsoft.web.weixin;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.script.RequestValue;

public class WeiXinBase implements Serializable {
	public static final String FUWUHAO = "WX_TYPE_FUWUHAO"; // 服务号
	public static final String GONGSIHAO = "WX_TYPE_GONGSIHAO"; // 公司号
	private static Logger LOGGER = LoggerFactory.getLogger(WeiXinBase.class);
	private static final long serialVersionUID = 6417149511433528521L;
	/**
	 * 连接名称
	 */
	private static String ConnStr = "";

	/**
	 * 设置的数据库连接名称
	 * 
	 * @param connStr 连接名称
	 */
	public synchronized static void setConnStr(String connStr) {
		ConnStr = connStr;
	}

	/**
	 * 获取数据库连接名称，例如 ewa_main_data
	 * 
	 * @return 连接名称
	 */
	public static String getConnStr() {
		return ConnStr;
	}

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
	 * 获取数据库前缀
	 * 
	 * @return
	 */
	public static String getDbPrefix() {
		return DbPrefix;
	}

	private String sup_unid_;

	private String lastErr_;
	private Integer sup_id_;
	private boolean isOk_;

	private DTTable tbSup;
	private String wxCfgType_;

	private boolean fwh_;
	private boolean gsh_;

	private String wx_cfg_no_;
	private String weiXinAppSecret_;
	private String weiXinToken_;
	private String weiXinShopId_;
	private String weiXinShopKey_;
	private String wxCfgName_;
	private String weiXinAppId_;

	public DTTable getTbSup() {
		return tbSup;
	}

	public void setTbSup(DTTable tbSup) {
		this.tbSup = tbSup;
	}

	WeiXinBase() {

	}

	WeiXinBase(String sup_unid, String wx_cfg_no) {
		this.sup_unid_ = sup_unid;
		this.wx_cfg_no_ = wx_cfg_no;

	}

	/**
	 * 初始化微信公众号数据
	 * 
	 * @param supUnid 商户
	 * @param wxCfgNo 微信号 gh_xxx
	 * @return
	 * @throws Exception
	 */
	public boolean initByUnidCfgNo(String supUnid, String wxCfgNo) throws Exception {

		String db_prefix = WeiXinBase.getDbPrefix();

		RequestValue rv = new RequestValue();
		rv.addOrUpdateValue("sup_unid", supUnid);
		rv.addOrUpdateValue("wx_cfg_no", wxCfgNo);

		StringBuilder sb = new StringBuilder();
		sb.append("select a.*,b.SUP_ID, b.sup_unid from ");
		sb.append(db_prefix);
		sb.append("bas_wx_cfg a");
		sb.append(" inner join sup_main b on a.rel_sup_unid=b.sup_unid");
		sb.append(" where a.rel_sup_unid=@sup_unid and wx_cfg_no=@wx_cfg_no");
		String sql = sb.toString();

		DTTable tbSup = DTTable.getJdbcTable(sql, getConnStr(), rv);
		if (tbSup.getCount() == 0) {
			lastErr_ = "not this sup";
			LOGGER.error(lastErr_);
			isOk_ = false;
			return false;
		}

		this.initParamters(tbSup);

		if (this.getWeiXinAppId() == null || getWeiXinAppId().trim().length() == 0) {
			lastErr_ = "WX_APP_ID not set";
			LOGGER.error(lastErr_);
			isOk_ = false;
			return false;
		}

		// 微信应用令牌
//		if (this.getWeiXinToken() == null || getWeiXinToken().trim().length() == 0) {
//			lastErr_ = "WX_APP_TOKEN not set";
//			LOGGER.error(lastErr_);
//			isOk_ = false;
//			return false;
//		}

		return true;
	}

	public void initParamters(DTTable tbSup) throws Exception {
		this.tbSup = tbSup;

		this.sup_id_ = tbSup.getCell(0, "sup_id").toInt();
		this.sup_unid_ = tbSup.getCell(0, "sup_unid").toString();

		this.wx_cfg_no_ = tbSup.getCell(0, "wx_cfg_no").toString();
		this.weiXinAppId_ = tbSup.getCell(0, "WX_APP_ID").toString();
		// 微信应用密匙
		this.weiXinAppSecret_ = tbSup.getCell(0, "WX_APP_SECRET").toString();
		// 微信应用令牌
		this.weiXinToken_ = tbSup.getCell(0, "WX_APP_TOKEN").toString();
		// 微信商户编号（支付用）
		this.weiXinShopId_ = tbSup.getCell(0, "WX_PAY_ID").toString();
		// 微信商户key（支付用）
		this.weiXinShopKey_ = tbSup.getCell(0, "WX_PAY_KEY").toString();
		// 公众号名称
		this.wxCfgName_ = tbSup.getCell(0, "WX_CFG_NAME").toString();
		this.wxCfgType_ = tbSup.getCell(0, "WX_CFG_TYPE").toString();

		if (this.wxCfgType_ == null) {
			this.wxCfgType_ = "";
		}
		if (FUWUHAO.equalsIgnoreCase(wxCfgType_)) {
			this.fwh_ = true; // 不是服务号
		} else if (GONGSIHAO.equalsIgnoreCase(wxCfgType_)) {
			this.gsh_ = true; // 是公司号
		}

		isOk_ = true;
	}

	boolean init() throws Exception {
		return this.initByUnidCfgNo(sup_unid_, wx_cfg_no_);
	}

	public String getSupUnid() {
		return sup_unid_;
	}

	public void setSupUnid(String supUnid) {
		this.sup_unid_ = supUnid;
	}

	public String getLastErr() {
		return lastErr_;
	}

	public void setLastErr(String lastErr) {
		this.lastErr_ = lastErr;
	}

	public Integer getSupId() {
		return sup_id_;
	}

	public void setSupId(int supId) {
		this.sup_id_ = supId;
	}

	public String getWxCfgNo() {
		return wx_cfg_no_;
	}

	public void setWxCfgNo(String wxCfgNo) {
		this.wx_cfg_no_ = wxCfgNo;
	}

	public String getWxCfgType() {
		return wxCfgType_;
	}

	public void setWxCfgType(String wxCfgType) {
		this.wxCfgType_ = wxCfgType;
	}

	public boolean isFwh() {
		return fwh_;
	}

	public void setFwh(boolean fwh) {
		this.fwh_ = fwh;
	}

	public boolean isGsh() {
		return gsh_;
	}

	public void setGsh(boolean gsh) {
		this.gsh_ = gsh;
	}

	/**
	 * 公众号AppId
	 * 
	 * @return
	 */
	public String getWeiXinAppId() {
		return this.weiXinAppId_;
	}

	/**
	 * 公众号AppId
	 * 
	 * @param weiXinAppId
	 */
	public void setWeiXinAppId(String weiXinAppId) {
		this.weiXinAppId_ = weiXinAppId;
	}

	/**
	 * 微信应用密匙
	 * 
	 * @return
	 */
	public String getWeiXinAppSecret() {
		return weiXinAppSecret_;
	}

	/**
	 * 微信应用密匙
	 * 
	 * @param weiXinAppSecret 微信应用密匙
	 */
	public void setWeiXinAppSecret(String weiXinAppSecret) {
		this.weiXinAppSecret_ = weiXinAppSecret;
	}

	/**
	 * 微信应用令牌
	 * 
	 * @return
	 */
	public String getWeiXinToken() {
		return weiXinToken_;
	}

	/**
	 * 微信应用令牌
	 * 
	 * @param weiXinToken 微信应用令牌
	 */
	public void setWeiXinToken(String weiXinToken) {
		this.weiXinToken_ = weiXinToken;
	}

	/**
	 * 微信商户编号（支付用）
	 * 
	 * @return
	 */
	public String getWeiXinShopId() {
		return weiXinShopId_;
	}

	/**
	 * 微信商户编号（支付用）
	 * 
	 * @param weiXinShopId
	 */
	public void setWeiXinShopId(String weiXinShopId) {
		this.weiXinShopId_ = weiXinShopId;
	}

	/**
	 * 微信商户key（支付用）
	 * 
	 * @return
	 */
	public String getWeiXinShopKey() {
		return weiXinShopKey_;
	}

	/**
	 * 微信商户key（支付用）
	 * 
	 * @param weiXinShopKey
	 */
	public void setWeiXinShopKey(String weiXinShopKey) {
		this.weiXinShopKey_ = weiXinShopKey;
	}

	/**
	 * 公众号名称
	 * 
	 * @return
	 */
	public String getWxCfgName() {
		return wxCfgName_;
	}

	/**
	 * 公众号名称
	 * 
	 * @param wxCfgName
	 */
	public void setWxCfgName(String wxCfgName) {
		this.wxCfgName_ = wxCfgName;
	}

	/**
	 * 是否正确初始化
	 * 
	 * @param isOk_
	 */
	public void setOk(boolean isOk) {
		this.isOk_ = isOk;
	}

	public boolean isOk() {
		return isOk_;
	}
}
