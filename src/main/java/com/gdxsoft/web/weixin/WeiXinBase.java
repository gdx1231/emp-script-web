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
	private String appId_;
	private String token_;
	private Integer sup_id_;
	private String wx_cfg_no_;
	private boolean isOk_;

	private DTTable tbSup;
	private String wxCfgType_;

	private boolean fwh_;
	private boolean gsh_;
	private String supWeiXinAppId_;
	private String supWeiXinAppSecret_;
	private String supWeiXinToken_;
	private String supWeiXinShopId_;
	private String supWeiXinShopKey_;
	private String wxCfgName_;

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
		sb.append("select a.*,b.SUP_ID supid from ");
		sb.append(db_prefix);
		sb.append("bas_wx_cfg a");
		sb.append(" inner join sup_main b on a.rel_sup_unid=b.sup_unid");
		sb.append(" where a.rel_sup_unid=@sup_unid and wx_cfg_no=@wx_cfg_no");
		String sql = sb.toString();

		DTTable tbSup = DTTable.getJdbcTable(sql, rv);
		if (tbSup.getCount() == 0) {
			lastErr_ = "not this sup";
			LOGGER.error(lastErr_);
			isOk_ = false;
			return false;
		}

		if (appId_ == null || appId_.trim().length() == 0) {
			lastErr_ = "WX_APP_ID not set";
			LOGGER.error(lastErr_);
			isOk_ = false;
			return false;
		}

		// 微信应用令牌
		if (token_ == null || token_.trim().length() == 0) {
			lastErr_ = "WX_APP_TOKEN not set";
			LOGGER.error(lastErr_);
			isOk_ = false;
			return false;
		}

		return true;
	}

	public void initParamters(DTTable tbSup) throws Exception {
		this.appId_ = tbSup.getCell(0, "WX_APP_ID").toString();
		// 微信应用令牌
		this.token_ = tbSup.getCell(0, "WX_APP_TOKEN").toString();

		this.sup_id_ = tbSup.getCell(0, "supid").toInt();
		this.tbSup = tbSup;

		this.sup_unid_ = tbSup.getCell(0, "sup_unid").toString();
		this.wx_cfg_no_ = tbSup.getCell(0, "wx_cfg_no").toString();

		this.wxCfgType_ = tbSup.getCell(0, "WX_CFG_TYPE").toString();
		if (this.wxCfgType_ == null) {
			this.wxCfgType_ = "";
		}
		if (FUWUHAO.equalsIgnoreCase(wxCfgType_)) {
			this.fwh_ = true; // 不是服务号
		} else if (GONGSIHAO.equalsIgnoreCase(wxCfgType_)) {
			this.gsh_ = true; // 是公司号
		}

		this.supWeiXinAppId_ = tbSup.getCell(0, "WX_APP_ID").toString();

		// 微信应用密匙
		this.supWeiXinAppSecret_ = tbSup.getCell(0, "WX_APP_SECRET").toString();
		// 微信应用令牌
		this.supWeiXinToken_ = tbSup.getCell(0, "WX_APP_TOKEN").toString();

		// 微信商户编号（支付用）
		this.supWeiXinShopId_ = tbSup.getCell(0, "WX_PAY_ID").toString();
		// 微信商户key（支付用）
		this.supWeiXinShopKey_ = tbSup.getCell(0, "WX_PAY_KEY").toString();

		// 公众号名称
		this.wxCfgName_ = tbSup.getCell(0, "WX_CFG_NAME").toString();

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

	public String getAppId() {
		return appId_;
	}

	public void setAppId(String appId) {
		this.appId_ = appId;
	}

	public String getToken() {
		return token_;
	}

	public void setToken(String token) {
		this.token_ = token;
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

	public String getSupWeiXinAppId() {
		return supWeiXinAppId_;
	}

	public void setSupWeiXinAppId(String supWeiXinAppId) {
		this.supWeiXinAppId_ = supWeiXinAppId;
	}

	/**
	 * 微信应用密匙
	 * 
	 * @return
	 */
	public String getSupWeiXinAppSecret() {
		return supWeiXinAppSecret_;
	}

	/**
	 * 微信应用密匙
	 * 
	 * @param supWeiXinAppSecret 微信应用密匙
	 */
	public void setSupWeiXinAppSecret(String supWeiXinAppSecret) {
		this.supWeiXinAppSecret_ = supWeiXinAppSecret;
	}

	/**
	 * 微信应用令牌
	 * 
	 * @return
	 */
	public String getSupWeiXinToken() {
		return supWeiXinToken_;
	}

	/**
	 * 微信应用令牌
	 * 
	 * @param supWeiXinToken 微信应用令牌
	 */
	public void setSupWeiXinToken(String supWeiXinToken) {
		this.supWeiXinToken_ = supWeiXinToken;
	}

	/**
	 * 微信商户编号（支付用）
	 * 
	 * @return
	 */
	public String getSupWeiXinShopId() {
		return supWeiXinShopId_;
	}

	/**
	 * 微信商户编号（支付用）
	 * 
	 * @param supWeiXinShopId
	 */
	public void setSupWeiXinShopId(String supWeiXinShopId) {
		this.supWeiXinShopId_ = supWeiXinShopId;
	}

	/**
	 * 微信商户key（支付用）
	 * 
	 * @return
	 */
	public String getSupWeiXinShopKey() {
		return supWeiXinShopKey_;
	}

	/**
	 * 微信商户key（支付用）
	 * 
	 * @param supWeiXinShopKey
	 */
	public void setSupWeiXinShopKey(String supWeiXinShopKey) {
		this.supWeiXinShopKey_ = supWeiXinShopKey;
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
