package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 表api_main映射类
 * 
 * @author gdx 时间：Wed Jun 02 2021 13:41:27 GMT+0800 (中国标准时间)
 */
@ApiModel(value = "api_main", description = "表api_main映射类")
public class ApiMain extends ClassBase {
	private String apiKey_; // ApiKey
	private String apiSignCode_; // 加密用Code（128位）
	private String supUnid_; // 供应商编号
	private Date apiCdate_; // 创建时间
	private Date apiMdate_; // 修改时间
	private Date apiFrom_; // 开通时间
	private Date apiTo_; // 截至时间
	private String apiValidIps_; // 合法IP地址
	private String apiMemo_; // 备注信息
	private String apiSupplyUrl_; // 供应商回调地址

	/**
	 * 获取 ApiKey
	 *
	 * @return ApiKey
	 */
	@ApiModelProperty(value = "ApiKey", required = true)
	public String getApiKey() {
		return this.apiKey_;
	}

	/**
	 * 赋值 ApiKey
	 * 
	 * @param paraApiKey ApiKey
	 */

	public void setApiKey(String paraApiKey) {
		super.recordChanged("API_KEY", this.apiKey_, paraApiKey);
		this.apiKey_ = paraApiKey;
	}

	/**
	 * 获取 加密用Code（128位）
	 *
	 * @return 加密用Code（128位）
	 */
	@ApiModelProperty(value = "加密用Code（128位）", required = true)
	public String getApiSignCode() {
		return this.apiSignCode_;
	}

	/**
	 * 赋值 加密用Code（128位）
	 * 
	 * @param paraApiSignCode 加密用Code（128位）
	 */

	public void setApiSignCode(String paraApiSignCode) {
		super.recordChanged("API_SIGN_CODE", this.apiSignCode_, paraApiSignCode);
		this.apiSignCode_ = paraApiSignCode;
	}

	/**
	 * 获取 供应商编号
	 *
	 * @return 供应商编号
	 */
	@ApiModelProperty(value = "供应商编号", required = true)
	public String getSupUnid() {
		return this.supUnid_;
	}

	/**
	 * 赋值 供应商编号
	 * 
	 * @param paraSupUnid 供应商编号
	 */

	public void setSupUnid(String paraSupUnid) {
		super.recordChanged("SUP_UNID", this.supUnid_, paraSupUnid);
		this.supUnid_ = paraSupUnid;
	}

	/**
	 * 获取 创建时间
	 *
	 * @return 创建时间
	 */
	@ApiModelProperty(value = "创建时间", required = false)
	public Date getApiCdate() {
		return this.apiCdate_;
	}

	/**
	 * 赋值 创建时间
	 * 
	 * @param paraApiCdate 创建时间
	 */

	public void setApiCdate(Date paraApiCdate) {
		super.recordChanged("API_CDATE", this.apiCdate_, paraApiCdate);
		this.apiCdate_ = paraApiCdate;
	}

	/**
	 * 获取 修改时间
	 *
	 * @return 修改时间
	 */
	@ApiModelProperty(value = "修改时间", required = false)
	public Date getApiMdate() {
		return this.apiMdate_;
	}

	/**
	 * 赋值 修改时间
	 * 
	 * @param paraApiMdate 修改时间
	 */

	public void setApiMdate(Date paraApiMdate) {
		super.recordChanged("API_MDATE", this.apiMdate_, paraApiMdate);
		this.apiMdate_ = paraApiMdate;
	}

	/**
	 * 获取 开通时间
	 *
	 * @return 开通时间
	 */
	@ApiModelProperty(value = "开通时间", required = false)
	public Date getApiFrom() {
		return this.apiFrom_;
	}

	/**
	 * 赋值 开通时间
	 * 
	 * @param paraApiFrom 开通时间
	 */

	public void setApiFrom(Date paraApiFrom) {
		super.recordChanged("API_FROM", this.apiFrom_, paraApiFrom);
		this.apiFrom_ = paraApiFrom;
	}

	/**
	 * 获取 截至时间
	 *
	 * @return 截至时间
	 */
	@ApiModelProperty(value = "截至时间", required = false)
	public Date getApiTo() {
		return this.apiTo_;
	}

	/**
	 * 赋值 截至时间
	 * 
	 * @param paraApiTo 截至时间
	 */

	public void setApiTo(Date paraApiTo) {
		super.recordChanged("API_TO", this.apiTo_, paraApiTo);
		this.apiTo_ = paraApiTo;
	}

	/**
	 * 获取 合法IP地址
	 *
	 * @return 合法IP地址
	 */
	@ApiModelProperty(value = "合法IP地址", required = false)
	public String getApiValidIps() {
		return this.apiValidIps_;
	}

	/**
	 * 赋值 合法IP地址
	 * 
	 * @param paraApiValidIps 合法IP地址
	 */

	public void setApiValidIps(String paraApiValidIps) {
		super.recordChanged("API_VALID_IPS", this.apiValidIps_, paraApiValidIps);
		this.apiValidIps_ = paraApiValidIps;
	}

	/**
	 * 获取 备注信息
	 *
	 * @return 备注信息
	 */
	@ApiModelProperty(value = "备注信息", required = false)
	public String getApiMemo() {
		return this.apiMemo_;
	}

	/**
	 * 赋值 备注信息
	 * 
	 * @param paraApiMemo 备注信息
	 */

	public void setApiMemo(String paraApiMemo) {
		super.recordChanged("API_MEMO", this.apiMemo_, paraApiMemo);
		this.apiMemo_ = paraApiMemo;
	}

	/**
	 * 获取 供应商回调地址
	 *
	 * @return 供应商回调地址
	 */
	@ApiModelProperty(value = "供应商回调地址", required = false)
	public String getApiSupplyUrl() {
		return this.apiSupplyUrl_;
	}

	/**
	 * 赋值 供应商回调地址
	 * 
	 * @param paraApiSupplyUrl 供应商回调地址
	 */

	public void setApiSupplyUrl(String paraApiSupplyUrl) {
		super.recordChanged("API_SUPPLY_URL", this.apiSupplyUrl_, paraApiSupplyUrl);
		this.apiSupplyUrl_ = paraApiSupplyUrl;
	}
}