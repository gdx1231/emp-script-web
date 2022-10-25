package com.gdxsoft.web.dao;

import com.gdxsoft.easyweb.datasource.ClassBase;

/**
 * 表CITY映射类
 * 
 * @author gdx 时间：Sat Jul 11 2020 16:13:24 GMT+0800 (中国标准时间)
 */
public class City extends ClassBase {
	private Integer cityId_; // CITY_ID
	private String cityName_; // CITY_NAME
	private String cityNameEn_; // CITY_NAME_EN
	private Integer countryId_; // COUNTRY_ID
	private String cityCode_; // CITY_CODE
	private String cityPsotCode_; // CITY_PSOT_CODE
	private Integer cityLvl_; // CITY_LVL
	private String cityMemo_; // CITY_MEMO
	private String cityA_; // CITY_A
	private String cityTag_; // CITY_TAG
	private String cityUtc_; // CITY_UTC
	private String cityDstFrom_; // CITY_DST_FROM
	private String cityDstTo_; // CITY_DST_TO
	private Integer cityLng_; // CITY_LNG
	private Integer cityLat_; // CITY_LAT
	private Integer cityGpsDes_; // CITY_GPS_DES
	private Integer cityHot_; // CITY_HOT
	private Integer qCid_; // Q_CID
	private Integer countryCoin_; // COUNTRY_COIN
	private Integer stateId_; // STATE_ID
	private String cityPid_; // CITY_PID
	private Integer cityLvlNew_; // CITY_LVL_NEW
	private String cityStatus_; // CITY_STATUS
	private String countryTele_; // COUNTRY_TELE
	private String cityScode_; // CITY_SCODE

	/**
	 * 获取 CITY_ID
	 *
	 * @return CITY_ID
	 */
	public Integer getCityId() {
		return this.cityId_;
	}

	/**
	 * 赋值 CITY_ID
	 * 
	 * @param paraCityId CITY_ID
	 */

	public void setCityId(Integer paraCityId) {
		super.recordChanged("CITY_ID", this.cityId_, paraCityId);
		this.cityId_ = paraCityId;
	}

	/**
	 * 获取 CITY_NAME
	 *
	 * @return CITY_NAME
	 */
	public String getCityName() {
		return this.cityName_;
	}

	/**
	 * 赋值 CITY_NAME
	 * 
	 * @param paraCityName CITY_NAME
	 */

	public void setCityName(String paraCityName) {
		super.recordChanged("CITY_NAME", this.cityName_, paraCityName);
		this.cityName_ = paraCityName;
	}

	/**
	 * 获取 CITY_NAME_EN
	 *
	 * @return CITY_NAME_EN
	 */
	public String getCityNameEn() {
		return this.cityNameEn_;
	}

	/**
	 * 赋值 CITY_NAME_EN
	 * 
	 * @param paraCityNameEn CITY_NAME_EN
	 */

	public void setCityNameEn(String paraCityNameEn) {
		super.recordChanged("CITY_NAME_EN", this.cityNameEn_, paraCityNameEn);
		this.cityNameEn_ = paraCityNameEn;
	}

	/**
	 * 获取 COUNTRY_ID
	 *
	 * @return COUNTRY_ID
	 */
	public Integer getCountryId() {
		return this.countryId_;
	}

	/**
	 * 赋值 COUNTRY_ID
	 * 
	 * @param paraCountryId COUNTRY_ID
	 */

	public void setCountryId(Integer paraCountryId) {
		super.recordChanged("COUNTRY_ID", this.countryId_, paraCountryId);
		this.countryId_ = paraCountryId;
	}

	/**
	 * 获取 CITY_CODE
	 *
	 * @return CITY_CODE
	 */
	public String getCityCode() {
		return this.cityCode_;
	}

	/**
	 * 赋值 CITY_CODE
	 * 
	 * @param paraCityCode CITY_CODE
	 */

	public void setCityCode(String paraCityCode) {
		super.recordChanged("CITY_CODE", this.cityCode_, paraCityCode);
		this.cityCode_ = paraCityCode;
	}

	/**
	 * 获取 CITY_PSOT_CODE
	 *
	 * @return CITY_PSOT_CODE
	 */
	public String getCityPsotCode() {
		return this.cityPsotCode_;
	}

	/**
	 * 赋值 CITY_PSOT_CODE
	 * 
	 * @param paraCityPsotCode CITY_PSOT_CODE
	 */

	public void setCityPsotCode(String paraCityPsotCode) {
		super.recordChanged("CITY_PSOT_CODE", this.cityPsotCode_, paraCityPsotCode);
		this.cityPsotCode_ = paraCityPsotCode;
	}

	/**
	 * 获取 CITY_LVL
	 *
	 * @return CITY_LVL
	 */
	public Integer getCityLvl() {
		return this.cityLvl_;
	}

	/**
	 * 赋值 CITY_LVL
	 * 
	 * @param paraCityLvl CITY_LVL
	 */

	public void setCityLvl(Integer paraCityLvl) {
		super.recordChanged("CITY_LVL", this.cityLvl_, paraCityLvl);
		this.cityLvl_ = paraCityLvl;
	}

	/**
	 * 获取 CITY_MEMO
	 *
	 * @return CITY_MEMO
	 */
	public String getCityMemo() {
		return this.cityMemo_;
	}

	/**
	 * 赋值 CITY_MEMO
	 * 
	 * @param paraCityMemo CITY_MEMO
	 */

	public void setCityMemo(String paraCityMemo) {
		super.recordChanged("CITY_MEMO", this.cityMemo_, paraCityMemo);
		this.cityMemo_ = paraCityMemo;
	}

	/**
	 * 获取 CITY_A
	 *
	 * @return CITY_A
	 */
	public String getCityA() {
		return this.cityA_;
	}

	/**
	 * 赋值 CITY_A
	 * 
	 * @param paraCityA CITY_A
	 */

	public void setCityA(String paraCityA) {
		super.recordChanged("CITY_A", this.cityA_, paraCityA);
		this.cityA_ = paraCityA;
	}

	/**
	 * 获取 CITY_TAG
	 *
	 * @return CITY_TAG
	 */
	public String getCityTag() {
		return this.cityTag_;
	}

	/**
	 * 赋值 CITY_TAG
	 * 
	 * @param paraCityTag CITY_TAG
	 */

	public void setCityTag(String paraCityTag) {
		super.recordChanged("CITY_TAG", this.cityTag_, paraCityTag);
		this.cityTag_ = paraCityTag;
	}

	/**
	 * 获取 CITY_UTC
	 *
	 * @return CITY_UTC
	 */
	public String getCityUtc() {
		return this.cityUtc_;
	}

	/**
	 * 赋值 CITY_UTC
	 * 
	 * @param paraCityUtc CITY_UTC
	 */

	public void setCityUtc(String paraCityUtc) {
		super.recordChanged("CITY_UTC", this.cityUtc_, paraCityUtc);
		this.cityUtc_ = paraCityUtc;
	}

	/**
	 * 获取 CITY_DST_FROM
	 *
	 * @return CITY_DST_FROM
	 */
	public String getCityDstFrom() {
		return this.cityDstFrom_;
	}

	/**
	 * 赋值 CITY_DST_FROM
	 * 
	 * @param paraCityDstFrom CITY_DST_FROM
	 */

	public void setCityDstFrom(String paraCityDstFrom) {
		super.recordChanged("CITY_DST_FROM", this.cityDstFrom_, paraCityDstFrom);
		this.cityDstFrom_ = paraCityDstFrom;
	}

	/**
	 * 获取 CITY_DST_TO
	 *
	 * @return CITY_DST_TO
	 */
	public String getCityDstTo() {
		return this.cityDstTo_;
	}

	/**
	 * 赋值 CITY_DST_TO
	 * 
	 * @param paraCityDstTo CITY_DST_TO
	 */

	public void setCityDstTo(String paraCityDstTo) {
		super.recordChanged("CITY_DST_TO", this.cityDstTo_, paraCityDstTo);
		this.cityDstTo_ = paraCityDstTo;
	}

	/**
	 * 获取 CITY_LNG
	 *
	 * @return CITY_LNG
	 */
	public Integer getCityLng() {
		return this.cityLng_;
	}

	/**
	 * 赋值 CITY_LNG
	 * 
	 * @param paraCityLng CITY_LNG
	 */

	public void setCityLng(Integer paraCityLng) {
		super.recordChanged("CITY_LNG", this.cityLng_, paraCityLng);
		this.cityLng_ = paraCityLng;
	}

	/**
	 * 获取 CITY_LAT
	 *
	 * @return CITY_LAT
	 */
	public Integer getCityLat() {
		return this.cityLat_;
	}

	/**
	 * 赋值 CITY_LAT
	 * 
	 * @param paraCityLat CITY_LAT
	 */

	public void setCityLat(Integer paraCityLat) {
		super.recordChanged("CITY_LAT", this.cityLat_, paraCityLat);
		this.cityLat_ = paraCityLat;
	}

	/**
	 * 获取 CITY_GPS_DES
	 *
	 * @return CITY_GPS_DES
	 */
	public Integer getCityGpsDes() {
		return this.cityGpsDes_;
	}

	/**
	 * 赋值 CITY_GPS_DES
	 * 
	 * @param paraCityGpsDes CITY_GPS_DES
	 */

	public void setCityGpsDes(Integer paraCityGpsDes) {
		super.recordChanged("CITY_GPS_DES", this.cityGpsDes_, paraCityGpsDes);
		this.cityGpsDes_ = paraCityGpsDes;
	}

	/**
	 * 获取 CITY_HOT
	 *
	 * @return CITY_HOT
	 */
	public Integer getCityHot() {
		return this.cityHot_;
	}

	/**
	 * 赋值 CITY_HOT
	 * 
	 * @param paraCityHot CITY_HOT
	 */

	public void setCityHot(Integer paraCityHot) {
		super.recordChanged("CITY_HOT", this.cityHot_, paraCityHot);
		this.cityHot_ = paraCityHot;
	}

	/**
	 * 获取 Q_CID
	 *
	 * @return Q_CID
	 */
	public Integer getQCid() {
		return this.qCid_;
	}

	/**
	 * 赋值 Q_CID
	 * 
	 * @param paraQCid Q_CID
	 */

	public void setQCid(Integer paraQCid) {
		super.recordChanged("Q_CID", this.qCid_, paraQCid);
		this.qCid_ = paraQCid;
	}

	/**
	 * 获取 COUNTRY_COIN
	 *
	 * @return COUNTRY_COIN
	 */
	public Integer getCountryCoin() {
		return this.countryCoin_;
	}

	/**
	 * 赋值 COUNTRY_COIN
	 * 
	 * @param paraCountryCoin COUNTRY_COIN
	 */

	public void setCountryCoin(Integer paraCountryCoin) {
		super.recordChanged("COUNTRY_COIN", this.countryCoin_, paraCountryCoin);
		this.countryCoin_ = paraCountryCoin;
	}

	/**
	 * 获取 STATE_ID
	 *
	 * @return STATE_ID
	 */
	public Integer getStateId() {
		return this.stateId_;
	}

	/**
	 * 赋值 STATE_ID
	 * 
	 * @param paraStateId STATE_ID
	 */

	public void setStateId(Integer paraStateId) {
		super.recordChanged("STATE_ID", this.stateId_, paraStateId);
		this.stateId_ = paraStateId;
	}

	/**
	 * 获取 CITY_PID
	 *
	 * @return CITY_PID
	 */
	public String getCityPid() {
		return this.cityPid_;
	}

	/**
	 * 赋值 CITY_PID
	 * 
	 * @param paraCityPid CITY_PID
	 */

	public void setCityPid(String paraCityPid) {
		super.recordChanged("CITY_PID", this.cityPid_, paraCityPid);
		this.cityPid_ = paraCityPid;
	}

	/**
	 * 获取 CITY_LVL_NEW
	 *
	 * @return CITY_LVL_NEW
	 */
	public Integer getCityLvlNew() {
		return this.cityLvlNew_;
	}

	/**
	 * 赋值 CITY_LVL_NEW
	 * 
	 * @param paraCityLvlNew CITY_LVL_NEW
	 */

	public void setCityLvlNew(Integer paraCityLvlNew) {
		super.recordChanged("CITY_LVL_NEW", this.cityLvlNew_, paraCityLvlNew);
		this.cityLvlNew_ = paraCityLvlNew;
	}

	/**
	 * 获取 CITY_STATUS
	 *
	 * @return CITY_STATUS
	 */
	public String getCityStatus() {
		return this.cityStatus_;
	}

	/**
	 * 赋值 CITY_STATUS
	 * 
	 * @param paraCityStatus CITY_STATUS
	 */

	public void setCityStatus(String paraCityStatus) {
		super.recordChanged("CITY_STATUS", this.cityStatus_, paraCityStatus);
		this.cityStatus_ = paraCityStatus;
	}

	/**
	 * 获取 COUNTRY_TELE
	 *
	 * @return COUNTRY_TELE
	 */
	public String getCountryTele() {
		return this.countryTele_;
	}

	/**
	 * 赋值 COUNTRY_TELE
	 * 
	 * @param paraCountryTele COUNTRY_TELE
	 */

	public void setCountryTele(String paraCountryTele) {
		super.recordChanged("COUNTRY_TELE", this.countryTele_, paraCountryTele);
		this.countryTele_ = paraCountryTele;
	}

	/**
	 * 获取 CITY_SCODE
	 *
	 * @return CITY_SCODE
	 */
	public String getCityScode() {
		return this.cityScode_;
	}

	/**
	 * 赋值 CITY_SCODE
	 * 
	 * @param paraCityScode CITY_SCODE
	 */

	public void setCityScode(String paraCityScode) {
		super.recordChanged("CITY_SCODE", this.cityScode_, paraCityScode);
		this.cityScode_ = paraCityScode;
	}

	/**
	 * 根据字段名称获取值，如果名称为空或字段未找到，返回空值
	 * 
	 * @param filedName 字段名称
	 * @return 字段值
	 */
	public Object getField(String filedName) {
		if (filedName == null) {
			return null;
		}
		String n = filedName.trim().toUpperCase();
		if (n.equalsIgnoreCase("CITY_ID")) {
			return this.cityId_;
		}
		if (n.equalsIgnoreCase("CITY_NAME")) {
			return this.cityName_;
		}
		if (n.equalsIgnoreCase("CITY_NAME_EN")) {
			return this.cityNameEn_;
		}
		if (n.equalsIgnoreCase("COUNTRY_ID")) {
			return this.countryId_;
		}
		if (n.equalsIgnoreCase("CITY_CODE")) {
			return this.cityCode_;
		}
		if (n.equalsIgnoreCase("CITY_PSOT_CODE")) {
			return this.cityPsotCode_;
		}
		if (n.equalsIgnoreCase("CITY_LVL")) {
			return this.cityLvl_;
		}
		if (n.equalsIgnoreCase("CITY_MEMO")) {
			return this.cityMemo_;
		}
		if (n.equalsIgnoreCase("CITY_A")) {
			return this.cityA_;
		}
		if (n.equalsIgnoreCase("CITY_TAG")) {
			return this.cityTag_;
		}
		if (n.equalsIgnoreCase("CITY_UTC")) {
			return this.cityUtc_;
		}
		if (n.equalsIgnoreCase("CITY_DST_FROM")) {
			return this.cityDstFrom_;
		}
		if (n.equalsIgnoreCase("CITY_DST_TO")) {
			return this.cityDstTo_;
		}
		if (n.equalsIgnoreCase("CITY_LNG")) {
			return this.cityLng_;
		}
		if (n.equalsIgnoreCase("CITY_LAT")) {
			return this.cityLat_;
		}
		if (n.equalsIgnoreCase("CITY_GPS_DES")) {
			return this.cityGpsDes_;
		}
		if (n.equalsIgnoreCase("CITY_HOT")) {
			return this.cityHot_;
		}
		if (n.equalsIgnoreCase("Q_CID")) {
			return this.qCid_;
		}
		if (n.equalsIgnoreCase("COUNTRY_COIN")) {
			return this.countryCoin_;
		}
		if (n.equalsIgnoreCase("STATE_ID")) {
			return this.stateId_;
		}
		if (n.equalsIgnoreCase("CITY_PID")) {
			return this.cityPid_;
		}
		if (n.equalsIgnoreCase("CITY_LVL_NEW")) {
			return this.cityLvlNew_;
		}
		if (n.equalsIgnoreCase("CITY_STATUS")) {
			return this.cityStatus_;
		}
		if (n.equalsIgnoreCase("COUNTRY_TELE")) {
			return this.countryTele_;
		}
		if (n.equalsIgnoreCase("CITY_SCODE")) {
			return this.cityScode_;
		}
		return null;
	}
}