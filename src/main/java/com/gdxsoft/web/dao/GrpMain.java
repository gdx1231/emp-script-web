package com.gdxsoft.web.dao;

import java.util.Date;
import com.gdxsoft.easyweb.datasource.ClassBase;
/**表GRP_MAIN映射类
* @author gdx 时间：Thu Jul 14 2022 17:50:55 GMT+0800 (中国标准时间)*/
public class GrpMain extends ClassBase{private Integer grpId_; // 团编号
private String grpName_; // 团名称
private String grpCode_; // 团代码
private Integer grpWaitDays_; // 等待天数
private Double grpPrcSin_; // 单价
private Integer grpNum_; // 运行人数
private String grpMemo_; // 备注
private Integer grpNight_; // GRP_NIGHT
private Date grpCdate_; // 创建时间
private Date grpMdate_; // 修改时间
private Date grpSdate_; // 开团日期
private Date grpStop_; // 报名截止日期
private String grpSta_; // 状态
private Integer admId_; // 职员编号
private Integer supId_; // 供应商编号
private Integer opId_; // OP_ID
private String grpType_; // 团类型
private String grpState_; // 使用中、删除、停用
private String grpVisa_; // 签证类型
private String grpFlow_; // GRP_FLOW
private Integer grpCoin_; // 结算货币
private String grpHyCode_; // 华阳团号
private String grpRunModel_; // 运作模式
private String grpSimpleName_; // 名称简写
private String grpGuidePrice_; // 指导价
private Integer supDownId_; // SUP_DOWN_ID
private Double grpIncome_; // 团总收入
private Double grpDevote_; // 团总贡献
private Double grpBase_; // 团总成本
private Double grpRatio_; // 贡献收入比
private Integer grpIncountry_; // GRP_INCOUNTRY
private String grpT_; // 航站楼
private String grpPD_; // 集合时间及地点
private String grpAir_; // 航班
private String grpAirWay_; // 去程航班出发到达城市
private Date grpAirDate_; // 去程航班出发日期
private String grpPick_; // 接机牌
private String grpTelCh_; // 国内紧急联系电话
private String grpLeaderName_; // 领队姓名
private String grpLeaderTel_; // 领队电话
private String grpLinkTel1_; // 紧急联系人及电话1
private String grpLinkTel2_; // 紧急联系人及电话2
private String grpVisMemo1_; // GRP_VIS_MEMO1
private String grpVisMemo2_; // GRP_VIS_MEMO2
private String grpBackDate_; // 团队归国的日期和航班号
private String grpArvDate_; // 抵达国内的日期和事件
private String grpOpSta_; // GRP_OP_STA
private Date grpBackTime_; // 回团时间
private Integer grpFormId_; // GRP_FORM_ID
private String clacStatus_; // 结算状态
private String destineState_; // DESTINE_STATE
private Date grpCtlTime_; // GRP_CTL_TIME
private String grpSettlUnid_; // GRP_SETTL_UNID
private String grpRemark_; // GRP_REMARK
private String grpPushMoney_; // 已经提成
private String insure_; // 是否购买保险
private Integer grpOpenId_; // GRP_OPEN_ID
private Date destineTime_; // DESTINE_TIME
private Date destineEndTime_; // 最终预定时间
private Integer grpSqId_; // 外部报价映射关系
private Date arriveTime_; // 达到时间
private String grpUnid_; // GRP_UNID
private Integer srvId_; // SRV_ID
private Integer grpBackAdmId_; // GRP_BACK_ADM_ID
private String grpBackAir_; // GRP_BACK_AIR
private String grpBackAirWay_; // 回程航班出发到达城市
private Date grpBackAirDate_; // 回程航班日期
private String grpOutSettingOk_; // GRP_OUT_SETTING_OK
private Integer grpSendAdmId_; // GRP_SEND_ADM_ID
private String grpSendMemo_; // GRP_SEND_MEMO
private Date grpSendTime_; // GRP_SEND_TIME
private String grpSyncUid_; // 同步编号
private Integer lineId_; // LINE_ID
private String grpCancel_; // 放入历史询价
private String sync_; // _SYNC_
private Integer depId_; // DEP_ID
private Integer grpCampCountry_; // GRP_CAMP_COUNTRY
private Integer grpCampId_; // GRP_CAMP_ID
private Date grpCampBeginDate_; // GRP_CAMP_BEGIN_DATE
private Date grpCampEndDate_; // GRP_CAMP_END_DATE
private String grpCampAccommodationType_; // GRP_CAMP_ACCOMMODATION_TYPE
private String grpCampCourseType_; // GRP_CAMP_COURSE_TYPE
private String cpVisaStatus_; // CP_VISA_STATUS
private String cpStatus_; // CP_STATUS
private String cpAirTicket_; // CP_AIR_TICKET
private String grpCampSetStatus_; // GRP_CAMP_SET_STATUS
private String grpCampAge_; // GRP_CAMP_AGE
private String grpFirstHotel_; // GRP_FIRST_HOTEL
private String grpEmbassy_; // GRP_EMBASSY
private String grpCountrys_; // GRP_COUNTRYS
private Integer grpPid_; // GRP_PID
private String grpHomeState_; // GRP_HOME_STATE
private Integer grpHomeAdmId_; // GRP_HOME_ADM_ID
private Integer grpHomeOpId_; // GRP_HOME_OP_ID
private Date grpHomeEdate_; // GRP_HOME_EDATE
private Date grpHomeOpEdate_; // GRP_HOME_OP_EDATE
private String grpShowTag_; // GRP_SHOW_TAG
private String grpCampUnid_; // GRP_CAMP_UNID
private Double grpVisDef_; // GRP_VIS_DEF
private Double grpCampDef_; // GRP_CAMP_DEF
private Double grpAirDef_; // GRP_AIR_DEF
private String grpNameEn_; // GRP_NAME_EN
private Integer ausAdt_; // AUS_ADT
private Integer ausChd_; // AUS_CHD
private Double ausChdnbrate_; // AUS_CHDNBRATE
private Double ausChdrate_; // AUS_CHDRATE
private Integer ausFoc_; // AUS_FOC
private Double ausInfantrate_; // AUS_INFANTRATE
private Double ausMargin_; // AUS_MARGIN
private Double ausMarkup_; // AUS_MARKUP
private Integer ausSnl_; // AUS_SNL
private Double ausTourclass_; // AUS_TOURCLASS
private Integer ausTwn_; // AUS_TWN
private Integer ausUnder18_; // AUS_UNDER18
private Integer isAus_; // IS_AUS
private Double ausSeaters_; // AUS_SEATERS
private Double ausRating_; // AUS_RATING
private Integer examId_; // EXAM_ID
private Integer grpHeaderTeacher_; // GRP_HEADER_TEACHER
private Integer grpTeacherChinese_; // GRP_TEACHER_CHINESE
private Integer grpTeacherForeign_; // GRP_TEACHER_FOREIGN
private String grpSchoolName_; // GRP_SCHOOL_NAME
private String grpClassName_; // GRP_CLASS_NAME
private String grpClassroom_; // GRP_CLASSROOM
private String grpTechingMaterial_; // GRP_TECHING_MATERIAL
private String grpRefTable_; // GRP_REF_TABLE
private Integer grpRefId_; // GRP_REF_ID
private String univUnid_; // UNIV_UNID

/**
 * 获取 团编号
 *
* @return 团编号
*/
public Integer getGrpId() {return this.grpId_;}
/**
* 赋值 团编号

* @param paraGrpId
* 团编号
 */

public void setGrpId(Integer paraGrpId){
  super.recordChanged("GRP_ID", this.grpId_, paraGrpId);
  this.grpId_ = paraGrpId;
}


/**
 * 获取 团名称
 *
* @return 团名称
*/
public String getGrpName() {return this.grpName_;}
/**
* 赋值 团名称

* @param paraGrpName
* 团名称
 */

public void setGrpName(String paraGrpName){
  super.recordChanged("GRP_NAME", this.grpName_, paraGrpName);
  this.grpName_ = paraGrpName;
}


/**
 * 获取 团代码
 *
* @return 团代码
*/
public String getGrpCode() {return this.grpCode_;}
/**
* 赋值 团代码

* @param paraGrpCode
* 团代码
 */

public void setGrpCode(String paraGrpCode){
  super.recordChanged("GRP_CODE", this.grpCode_, paraGrpCode);
  this.grpCode_ = paraGrpCode;
}


/**
 * 获取 等待天数
 *
* @return 等待天数
*/
public Integer getGrpWaitDays() {return this.grpWaitDays_;}
/**
* 赋值 等待天数

* @param paraGrpWaitDays
* 等待天数
 */

public void setGrpWaitDays(Integer paraGrpWaitDays){
  super.recordChanged("GRP_WAIT_DAYS", this.grpWaitDays_, paraGrpWaitDays);
  this.grpWaitDays_ = paraGrpWaitDays;
}


/**
 * 获取 单价
 *
* @return 单价
*/
public Double getGrpPrcSin() {return this.grpPrcSin_;}
/**
* 赋值 单价

* @param paraGrpPrcSin
* 单价
 */

public void setGrpPrcSin(Double paraGrpPrcSin){
  super.recordChanged("GRP_PRC_SIN", this.grpPrcSin_, paraGrpPrcSin);
  this.grpPrcSin_ = paraGrpPrcSin;
}


/**
 * 获取 运行人数
 *
* @return 运行人数
*/
public Integer getGrpNum() {return this.grpNum_;}
/**
* 赋值 运行人数

* @param paraGrpNum
* 运行人数
 */

public void setGrpNum(Integer paraGrpNum){
  super.recordChanged("GRP_NUM", this.grpNum_, paraGrpNum);
  this.grpNum_ = paraGrpNum;
}


/**
 * 获取 备注
 *
* @return 备注
*/
public String getGrpMemo() {return this.grpMemo_;}
/**
* 赋值 备注

* @param paraGrpMemo
* 备注
 */

public void setGrpMemo(String paraGrpMemo){
  super.recordChanged("GRP_MEMO", this.grpMemo_, paraGrpMemo);
  this.grpMemo_ = paraGrpMemo;
}


/**
 * 获取 GRP_NIGHT
 *
* @return GRP_NIGHT
*/
public Integer getGrpNight() {return this.grpNight_;}
/**
* 赋值 GRP_NIGHT

* @param paraGrpNight
* GRP_NIGHT
 */

public void setGrpNight(Integer paraGrpNight){
  super.recordChanged("GRP_NIGHT", this.grpNight_, paraGrpNight);
  this.grpNight_ = paraGrpNight;
}


/**
 * 获取 创建时间
 *
* @return 创建时间
*/
public Date getGrpCdate() {return this.grpCdate_;}
/**
* 赋值 创建时间

* @param paraGrpCdate
* 创建时间
 */

public void setGrpCdate(Date paraGrpCdate){
  super.recordChanged("GRP_CDATE", this.grpCdate_, paraGrpCdate);
  this.grpCdate_ = paraGrpCdate;
}


/**
 * 获取 修改时间
 *
* @return 修改时间
*/
public Date getGrpMdate() {return this.grpMdate_;}
/**
* 赋值 修改时间

* @param paraGrpMdate
* 修改时间
 */

public void setGrpMdate(Date paraGrpMdate){
  super.recordChanged("GRP_MDATE", this.grpMdate_, paraGrpMdate);
  this.grpMdate_ = paraGrpMdate;
}


/**
 * 获取 开团日期
 *
* @return 开团日期
*/
public Date getGrpSdate() {return this.grpSdate_;}
/**
* 赋值 开团日期

* @param paraGrpSdate
* 开团日期
 */

public void setGrpSdate(Date paraGrpSdate){
  super.recordChanged("GRP_SDATE", this.grpSdate_, paraGrpSdate);
  this.grpSdate_ = paraGrpSdate;
}


/**
 * 获取 报名截止日期
 *
* @return 报名截止日期
*/
public Date getGrpStop() {return this.grpStop_;}
/**
* 赋值 报名截止日期

* @param paraGrpStop
* 报名截止日期
 */

public void setGrpStop(Date paraGrpStop){
  super.recordChanged("GRP_STOP", this.grpStop_, paraGrpStop);
  this.grpStop_ = paraGrpStop;
}


/**
 * 获取 状态
 *
* @return 状态
*/
public String getGrpSta() {return this.grpSta_;}
/**
* 赋值 状态

* @param paraGrpSta
* 状态
 */

public void setGrpSta(String paraGrpSta){
  super.recordChanged("GRP_STA", this.grpSta_, paraGrpSta);
  this.grpSta_ = paraGrpSta;
}


/**
 * 获取 职员编号
 *
* @return 职员编号
*/
public Integer getAdmId() {return this.admId_;}
/**
* 赋值 职员编号

* @param paraAdmId
* 职员编号
 */

public void setAdmId(Integer paraAdmId){
  super.recordChanged("ADM_ID", this.admId_, paraAdmId);
  this.admId_ = paraAdmId;
}


/**
 * 获取 供应商编号
 *
* @return 供应商编号
*/
public Integer getSupId() {return this.supId_;}
/**
* 赋值 供应商编号

* @param paraSupId
* 供应商编号
 */

public void setSupId(Integer paraSupId){
  super.recordChanged("SUP_ID", this.supId_, paraSupId);
  this.supId_ = paraSupId;
}


/**
 * 获取 OP_ID
 *
* @return OP_ID
*/
public Integer getOpId() {return this.opId_;}
/**
* 赋值 OP_ID

* @param paraOpId
* OP_ID
 */

public void setOpId(Integer paraOpId){
  super.recordChanged("OP_ID", this.opId_, paraOpId);
  this.opId_ = paraOpId;
}


/**
 * 获取 团类型
 *
* @return 团类型
*/
public String getGrpType() {return this.grpType_;}
/**
* 赋值 团类型

* @param paraGrpType
* 团类型
 */

public void setGrpType(String paraGrpType){
  super.recordChanged("GRP_TYPE", this.grpType_, paraGrpType);
  this.grpType_ = paraGrpType;
}


/**
 * 获取 使用中、删除、停用
 *
* @return 使用中、删除、停用
*/
public String getGrpState() {return this.grpState_;}
/**
* 赋值 使用中、删除、停用

* @param paraGrpState
* 使用中、删除、停用
 */

public void setGrpState(String paraGrpState){
  super.recordChanged("GRP_STATE", this.grpState_, paraGrpState);
  this.grpState_ = paraGrpState;
}


/**
 * 获取 签证类型
 *
* @return 签证类型
*/
public String getGrpVisa() {return this.grpVisa_;}
/**
* 赋值 签证类型

* @param paraGrpVisa
* 签证类型
 */

public void setGrpVisa(String paraGrpVisa){
  super.recordChanged("GRP_VISA", this.grpVisa_, paraGrpVisa);
  this.grpVisa_ = paraGrpVisa;
}


/**
 * 获取 GRP_FLOW
 *
* @return GRP_FLOW
*/
public String getGrpFlow() {return this.grpFlow_;}
/**
* 赋值 GRP_FLOW

* @param paraGrpFlow
* GRP_FLOW
 */

public void setGrpFlow(String paraGrpFlow){
  super.recordChanged("GRP_FLOW", this.grpFlow_, paraGrpFlow);
  this.grpFlow_ = paraGrpFlow;
}


/**
 * 获取 结算货币
 *
* @return 结算货币
*/
public Integer getGrpCoin() {return this.grpCoin_;}
/**
* 赋值 结算货币

* @param paraGrpCoin
* 结算货币
 */

public void setGrpCoin(Integer paraGrpCoin){
  super.recordChanged("GRP_COIN", this.grpCoin_, paraGrpCoin);
  this.grpCoin_ = paraGrpCoin;
}


/**
 * 获取 华阳团号
 *
* @return 华阳团号
*/
public String getGrpHyCode() {return this.grpHyCode_;}
/**
* 赋值 华阳团号

* @param paraGrpHyCode
* 华阳团号
 */

public void setGrpHyCode(String paraGrpHyCode){
  super.recordChanged("GRP_HY_CODE", this.grpHyCode_, paraGrpHyCode);
  this.grpHyCode_ = paraGrpHyCode;
}


/**
 * 获取 运作模式
 *
* @return 运作模式
*/
public String getGrpRunModel() {return this.grpRunModel_;}
/**
* 赋值 运作模式

* @param paraGrpRunModel
* 运作模式
 */

public void setGrpRunModel(String paraGrpRunModel){
  super.recordChanged("GRP_RUN_MODEL", this.grpRunModel_, paraGrpRunModel);
  this.grpRunModel_ = paraGrpRunModel;
}


/**
 * 获取 名称简写
 *
* @return 名称简写
*/
public String getGrpSimpleName() {return this.grpSimpleName_;}
/**
* 赋值 名称简写

* @param paraGrpSimpleName
* 名称简写
 */

public void setGrpSimpleName(String paraGrpSimpleName){
  super.recordChanged("GRP_SIMPLE_NAME", this.grpSimpleName_, paraGrpSimpleName);
  this.grpSimpleName_ = paraGrpSimpleName;
}


/**
 * 获取 指导价
 *
* @return 指导价
*/
public String getGrpGuidePrice() {return this.grpGuidePrice_;}
/**
* 赋值 指导价

* @param paraGrpGuidePrice
* 指导价
 */

public void setGrpGuidePrice(String paraGrpGuidePrice){
  super.recordChanged("GRP_GUIDE_PRICE", this.grpGuidePrice_, paraGrpGuidePrice);
  this.grpGuidePrice_ = paraGrpGuidePrice;
}


/**
 * 获取 SUP_DOWN_ID
 *
* @return SUP_DOWN_ID
*/
public Integer getSupDownId() {return this.supDownId_;}
/**
* 赋值 SUP_DOWN_ID

* @param paraSupDownId
* SUP_DOWN_ID
 */

public void setSupDownId(Integer paraSupDownId){
  super.recordChanged("SUP_DOWN_ID", this.supDownId_, paraSupDownId);
  this.supDownId_ = paraSupDownId;
}


/**
 * 获取 团总收入
 *
* @return 团总收入
*/
public Double getGrpIncome() {return this.grpIncome_;}
/**
* 赋值 团总收入

* @param paraGrpIncome
* 团总收入
 */

public void setGrpIncome(Double paraGrpIncome){
  super.recordChanged("grp_income", this.grpIncome_, paraGrpIncome);
  this.grpIncome_ = paraGrpIncome;
}


/**
 * 获取 团总贡献
 *
* @return 团总贡献
*/
public Double getGrpDevote() {return this.grpDevote_;}
/**
* 赋值 团总贡献

* @param paraGrpDevote
* 团总贡献
 */

public void setGrpDevote(Double paraGrpDevote){
  super.recordChanged("grp_devote", this.grpDevote_, paraGrpDevote);
  this.grpDevote_ = paraGrpDevote;
}


/**
 * 获取 团总成本
 *
* @return 团总成本
*/
public Double getGrpBase() {return this.grpBase_;}
/**
* 赋值 团总成本

* @param paraGrpBase
* 团总成本
 */

public void setGrpBase(Double paraGrpBase){
  super.recordChanged("grp_base", this.grpBase_, paraGrpBase);
  this.grpBase_ = paraGrpBase;
}


/**
 * 获取 贡献收入比
 *
* @return 贡献收入比
*/
public Double getGrpRatio() {return this.grpRatio_;}
/**
* 赋值 贡献收入比

* @param paraGrpRatio
* 贡献收入比
 */

public void setGrpRatio(Double paraGrpRatio){
  super.recordChanged("grp_ratio", this.grpRatio_, paraGrpRatio);
  this.grpRatio_ = paraGrpRatio;
}


/**
 * 获取 GRP_INCOUNTRY
 *
* @return GRP_INCOUNTRY
*/
public Integer getGrpIncountry() {return this.grpIncountry_;}
/**
* 赋值 GRP_INCOUNTRY

* @param paraGrpIncountry
* GRP_INCOUNTRY
 */

public void setGrpIncountry(Integer paraGrpIncountry){
  super.recordChanged("GRP_INCOUNTRY", this.grpIncountry_, paraGrpIncountry);
  this.grpIncountry_ = paraGrpIncountry;
}


/**
 * 获取 航站楼
 *
* @return 航站楼
*/
public String getGrpT() {return this.grpT_;}
/**
* 赋值 航站楼

* @param paraGrpT
* 航站楼
 */

public void setGrpT(String paraGrpT){
  super.recordChanged("GRP_T", this.grpT_, paraGrpT);
  this.grpT_ = paraGrpT;
}


/**
 * 获取 集合时间及地点
 *
* @return 集合时间及地点
*/
public String getGrpPD() {return this.grpPD_;}
/**
* 赋值 集合时间及地点

* @param paraGrpPD
* 集合时间及地点
 */

public void setGrpPD(String paraGrpPD){
  super.recordChanged("GRP_P_D", this.grpPD_, paraGrpPD);
  this.grpPD_ = paraGrpPD;
}


/**
 * 获取 航班
 *
* @return 航班
*/
public String getGrpAir() {return this.grpAir_;}
/**
* 赋值 航班

* @param paraGrpAir
* 航班
 */

public void setGrpAir(String paraGrpAir){
  super.recordChanged("GRP_AIR", this.grpAir_, paraGrpAir);
  this.grpAir_ = paraGrpAir;
}


/**
 * 获取 去程航班出发到达城市
 *
* @return 去程航班出发到达城市
*/
public String getGrpAirWay() {return this.grpAirWay_;}
/**
* 赋值 去程航班出发到达城市

* @param paraGrpAirWay
* 去程航班出发到达城市
 */

public void setGrpAirWay(String paraGrpAirWay){
  super.recordChanged("GRP_AIR_WAY", this.grpAirWay_, paraGrpAirWay);
  this.grpAirWay_ = paraGrpAirWay;
}


/**
 * 获取 去程航班出发日期
 *
* @return 去程航班出发日期
*/
public Date getGrpAirDate() {return this.grpAirDate_;}
/**
* 赋值 去程航班出发日期

* @param paraGrpAirDate
* 去程航班出发日期
 */

public void setGrpAirDate(Date paraGrpAirDate){
  super.recordChanged("GRP_AIR_DATE", this.grpAirDate_, paraGrpAirDate);
  this.grpAirDate_ = paraGrpAirDate;
}


/**
 * 获取 接机牌
 *
* @return 接机牌
*/
public String getGrpPick() {return this.grpPick_;}
/**
* 赋值 接机牌

* @param paraGrpPick
* 接机牌
 */

public void setGrpPick(String paraGrpPick){
  super.recordChanged("GRP_PICK", this.grpPick_, paraGrpPick);
  this.grpPick_ = paraGrpPick;
}


/**
 * 获取 国内紧急联系电话
 *
* @return 国内紧急联系电话
*/
public String getGrpTelCh() {return this.grpTelCh_;}
/**
* 赋值 国内紧急联系电话

* @param paraGrpTelCh
* 国内紧急联系电话
 */

public void setGrpTelCh(String paraGrpTelCh){
  super.recordChanged("GRP_TEL_CH", this.grpTelCh_, paraGrpTelCh);
  this.grpTelCh_ = paraGrpTelCh;
}


/**
 * 获取 领队姓名
 *
* @return 领队姓名
*/
public String getGrpLeaderName() {return this.grpLeaderName_;}
/**
* 赋值 领队姓名

* @param paraGrpLeaderName
* 领队姓名
 */

public void setGrpLeaderName(String paraGrpLeaderName){
  super.recordChanged("GRP_LEADER_NAME", this.grpLeaderName_, paraGrpLeaderName);
  this.grpLeaderName_ = paraGrpLeaderName;
}


/**
 * 获取 领队电话
 *
* @return 领队电话
*/
public String getGrpLeaderTel() {return this.grpLeaderTel_;}
/**
* 赋值 领队电话

* @param paraGrpLeaderTel
* 领队电话
 */

public void setGrpLeaderTel(String paraGrpLeaderTel){
  super.recordChanged("GRP_LEADER_TEL", this.grpLeaderTel_, paraGrpLeaderTel);
  this.grpLeaderTel_ = paraGrpLeaderTel;
}


/**
 * 获取 紧急联系人及电话1
 *
* @return 紧急联系人及电话1
*/
public String getGrpLinkTel1() {return this.grpLinkTel1_;}
/**
* 赋值 紧急联系人及电话1

* @param paraGrpLinkTel1
* 紧急联系人及电话1
 */

public void setGrpLinkTel1(String paraGrpLinkTel1){
  super.recordChanged("GRP_LINK_TEL1", this.grpLinkTel1_, paraGrpLinkTel1);
  this.grpLinkTel1_ = paraGrpLinkTel1;
}


/**
 * 获取 紧急联系人及电话2
 *
* @return 紧急联系人及电话2
*/
public String getGrpLinkTel2() {return this.grpLinkTel2_;}
/**
* 赋值 紧急联系人及电话2

* @param paraGrpLinkTel2
* 紧急联系人及电话2
 */

public void setGrpLinkTel2(String paraGrpLinkTel2){
  super.recordChanged("GRP_LINK_TEL2", this.grpLinkTel2_, paraGrpLinkTel2);
  this.grpLinkTel2_ = paraGrpLinkTel2;
}


/**
 * 获取 GRP_VIS_MEMO1
 *
* @return GRP_VIS_MEMO1
*/
public String getGrpVisMemo1() {return this.grpVisMemo1_;}
/**
* 赋值 GRP_VIS_MEMO1

* @param paraGrpVisMemo1
* GRP_VIS_MEMO1
 */

public void setGrpVisMemo1(String paraGrpVisMemo1){
  super.recordChanged("GRP_VIS_MEMO1", this.grpVisMemo1_, paraGrpVisMemo1);
  this.grpVisMemo1_ = paraGrpVisMemo1;
}


/**
 * 获取 GRP_VIS_MEMO2
 *
* @return GRP_VIS_MEMO2
*/
public String getGrpVisMemo2() {return this.grpVisMemo2_;}
/**
* 赋值 GRP_VIS_MEMO2

* @param paraGrpVisMemo2
* GRP_VIS_MEMO2
 */

public void setGrpVisMemo2(String paraGrpVisMemo2){
  super.recordChanged("GRP_VIS_MEMO2", this.grpVisMemo2_, paraGrpVisMemo2);
  this.grpVisMemo2_ = paraGrpVisMemo2;
}


/**
 * 获取 团队归国的日期和航班号
 *
* @return 团队归国的日期和航班号
*/
public String getGrpBackDate() {return this.grpBackDate_;}
/**
* 赋值 团队归国的日期和航班号

* @param paraGrpBackDate
* 团队归国的日期和航班号
 */

public void setGrpBackDate(String paraGrpBackDate){
  super.recordChanged("GRP_BACK_DATE", this.grpBackDate_, paraGrpBackDate);
  this.grpBackDate_ = paraGrpBackDate;
}


/**
 * 获取 抵达国内的日期和事件
 *
* @return 抵达国内的日期和事件
*/
public String getGrpArvDate() {return this.grpArvDate_;}
/**
* 赋值 抵达国内的日期和事件

* @param paraGrpArvDate
* 抵达国内的日期和事件
 */

public void setGrpArvDate(String paraGrpArvDate){
  super.recordChanged("GRP_ARV_DATE", this.grpArvDate_, paraGrpArvDate);
  this.grpArvDate_ = paraGrpArvDate;
}


/**
 * 获取 GRP_OP_STA
 *
* @return GRP_OP_STA
*/
public String getGrpOpSta() {return this.grpOpSta_;}
/**
* 赋值 GRP_OP_STA

* @param paraGrpOpSta
* GRP_OP_STA
 */

public void setGrpOpSta(String paraGrpOpSta){
  super.recordChanged("GRP_OP_STA", this.grpOpSta_, paraGrpOpSta);
  this.grpOpSta_ = paraGrpOpSta;
}


/**
 * 获取 回团时间
 *
* @return 回团时间
*/
public Date getGrpBackTime() {return this.grpBackTime_;}
/**
* 赋值 回团时间

* @param paraGrpBackTime
* 回团时间
 */

public void setGrpBackTime(Date paraGrpBackTime){
  super.recordChanged("GRP_BACK_TIME", this.grpBackTime_, paraGrpBackTime);
  this.grpBackTime_ = paraGrpBackTime;
}


/**
 * 获取 GRP_FORM_ID
 *
* @return GRP_FORM_ID
*/
public Integer getGrpFormId() {return this.grpFormId_;}
/**
* 赋值 GRP_FORM_ID

* @param paraGrpFormId
* GRP_FORM_ID
 */

public void setGrpFormId(Integer paraGrpFormId){
  super.recordChanged("GRP_FORM_ID", this.grpFormId_, paraGrpFormId);
  this.grpFormId_ = paraGrpFormId;
}


/**
 * 获取 结算状态
 *
* @return 结算状态
*/
public String getClacStatus() {return this.clacStatus_;}
/**
* 赋值 结算状态

* @param paraClacStatus
* 结算状态
 */

public void setClacStatus(String paraClacStatus){
  super.recordChanged("CLAC_STATUS", this.clacStatus_, paraClacStatus);
  this.clacStatus_ = paraClacStatus;
}


/**
 * 获取 DESTINE_STATE
 *
* @return DESTINE_STATE
*/
public String getDestineState() {return this.destineState_;}
/**
* 赋值 DESTINE_STATE

* @param paraDestineState
* DESTINE_STATE
 */

public void setDestineState(String paraDestineState){
  super.recordChanged("DESTINE_STATE", this.destineState_, paraDestineState);
  this.destineState_ = paraDestineState;
}


/**
 * 获取 GRP_CTL_TIME
 *
* @return GRP_CTL_TIME
*/
public Date getGrpCtlTime() {return this.grpCtlTime_;}
/**
* 赋值 GRP_CTL_TIME

* @param paraGrpCtlTime
* GRP_CTL_TIME
 */

public void setGrpCtlTime(Date paraGrpCtlTime){
  super.recordChanged("GRP_CTL_TIME", this.grpCtlTime_, paraGrpCtlTime);
  this.grpCtlTime_ = paraGrpCtlTime;
}


/**
 * 获取 GRP_SETTL_UNID
 *
* @return GRP_SETTL_UNID
*/
public String getGrpSettlUnid() {return this.grpSettlUnid_;}
/**
* 赋值 GRP_SETTL_UNID

* @param paraGrpSettlUnid
* GRP_SETTL_UNID
 */

public void setGrpSettlUnid(String paraGrpSettlUnid){
  super.recordChanged("GRP_SETTL_UNID", this.grpSettlUnid_, paraGrpSettlUnid);
  this.grpSettlUnid_ = paraGrpSettlUnid;
}


/**
 * 获取 GRP_REMARK
 *
* @return GRP_REMARK
*/
public String getGrpRemark() {return this.grpRemark_;}
/**
* 赋值 GRP_REMARK

* @param paraGrpRemark
* GRP_REMARK
 */

public void setGrpRemark(String paraGrpRemark){
  super.recordChanged("GRP_REMARK", this.grpRemark_, paraGrpRemark);
  this.grpRemark_ = paraGrpRemark;
}


/**
 * 获取 已经提成
 *
* @return 已经提成
*/
public String getGrpPushMoney() {return this.grpPushMoney_;}
/**
* 赋值 已经提成

* @param paraGrpPushMoney
* 已经提成
 */

public void setGrpPushMoney(String paraGrpPushMoney){
  super.recordChanged("GRP_PUSH_MONEY", this.grpPushMoney_, paraGrpPushMoney);
  this.grpPushMoney_ = paraGrpPushMoney;
}


/**
 * 获取 是否购买保险
 *
* @return 是否购买保险
*/
public String getInsure() {return this.insure_;}
/**
* 赋值 是否购买保险

* @param paraInsure
* 是否购买保险
 */

public void setInsure(String paraInsure){
  super.recordChanged("INSURE", this.insure_, paraInsure);
  this.insure_ = paraInsure;
}


/**
 * 获取 GRP_OPEN_ID
 *
* @return GRP_OPEN_ID
*/
public Integer getGrpOpenId() {return this.grpOpenId_;}
/**
* 赋值 GRP_OPEN_ID

* @param paraGrpOpenId
* GRP_OPEN_ID
 */

public void setGrpOpenId(Integer paraGrpOpenId){
  super.recordChanged("GRP_OPEN_ID", this.grpOpenId_, paraGrpOpenId);
  this.grpOpenId_ = paraGrpOpenId;
}


/**
 * 获取 DESTINE_TIME
 *
* @return DESTINE_TIME
*/
public Date getDestineTime() {return this.destineTime_;}
/**
* 赋值 DESTINE_TIME

* @param paraDestineTime
* DESTINE_TIME
 */

public void setDestineTime(Date paraDestineTime){
  super.recordChanged("DESTINE_TIME", this.destineTime_, paraDestineTime);
  this.destineTime_ = paraDestineTime;
}


/**
 * 获取 最终预定时间
 *
* @return 最终预定时间
*/
public Date getDestineEndTime() {return this.destineEndTime_;}
/**
* 赋值 最终预定时间

* @param paraDestineEndTime
* 最终预定时间
 */

public void setDestineEndTime(Date paraDestineEndTime){
  super.recordChanged("DESTINE_END_TIME", this.destineEndTime_, paraDestineEndTime);
  this.destineEndTime_ = paraDestineEndTime;
}


/**
 * 获取 外部报价映射关系
 *
* @return 外部报价映射关系
*/
public Integer getGrpSqId() {return this.grpSqId_;}
/**
* 赋值 外部报价映射关系

* @param paraGrpSqId
* 外部报价映射关系
 */

public void setGrpSqId(Integer paraGrpSqId){
  super.recordChanged("GRP_SQ_ID", this.grpSqId_, paraGrpSqId);
  this.grpSqId_ = paraGrpSqId;
}


/**
 * 获取 达到时间
 *
* @return 达到时间
*/
public Date getArriveTime() {return this.arriveTime_;}
/**
* 赋值 达到时间

* @param paraArriveTime
* 达到时间
 */

public void setArriveTime(Date paraArriveTime){
  super.recordChanged("ARRIVE_TIME", this.arriveTime_, paraArriveTime);
  this.arriveTime_ = paraArriveTime;
}


/**
 * 获取 GRP_UNID
 *
* @return GRP_UNID
*/
public String getGrpUnid() {return this.grpUnid_;}
/**
* 赋值 GRP_UNID

* @param paraGrpUnid
* GRP_UNID
 */

public void setGrpUnid(String paraGrpUnid){
  super.recordChanged("GRP_UNID", this.grpUnid_, paraGrpUnid);
  this.grpUnid_ = paraGrpUnid;
}


/**
 * 获取 SRV_ID
 *
* @return SRV_ID
*/
public Integer getSrvId() {return this.srvId_;}
/**
* 赋值 SRV_ID

* @param paraSrvId
* SRV_ID
 */

public void setSrvId(Integer paraSrvId){
  super.recordChanged("SRV_ID", this.srvId_, paraSrvId);
  this.srvId_ = paraSrvId;
}


/**
 * 获取 GRP_BACK_ADM_ID
 *
* @return GRP_BACK_ADM_ID
*/
public Integer getGrpBackAdmId() {return this.grpBackAdmId_;}
/**
* 赋值 GRP_BACK_ADM_ID

* @param paraGrpBackAdmId
* GRP_BACK_ADM_ID
 */

public void setGrpBackAdmId(Integer paraGrpBackAdmId){
  super.recordChanged("GRP_BACK_ADM_ID", this.grpBackAdmId_, paraGrpBackAdmId);
  this.grpBackAdmId_ = paraGrpBackAdmId;
}


/**
 * 获取 GRP_BACK_AIR
 *
* @return GRP_BACK_AIR
*/
public String getGrpBackAir() {return this.grpBackAir_;}
/**
* 赋值 GRP_BACK_AIR

* @param paraGrpBackAir
* GRP_BACK_AIR
 */

public void setGrpBackAir(String paraGrpBackAir){
  super.recordChanged("GRP_BACK_AIR", this.grpBackAir_, paraGrpBackAir);
  this.grpBackAir_ = paraGrpBackAir;
}


/**
 * 获取 回程航班出发到达城市
 *
* @return 回程航班出发到达城市
*/
public String getGrpBackAirWay() {return this.grpBackAirWay_;}
/**
* 赋值 回程航班出发到达城市

* @param paraGrpBackAirWay
* 回程航班出发到达城市
 */

public void setGrpBackAirWay(String paraGrpBackAirWay){
  super.recordChanged("GRP_BACK_AIR_WAY", this.grpBackAirWay_, paraGrpBackAirWay);
  this.grpBackAirWay_ = paraGrpBackAirWay;
}


/**
 * 获取 回程航班日期
 *
* @return 回程航班日期
*/
public Date getGrpBackAirDate() {return this.grpBackAirDate_;}
/**
* 赋值 回程航班日期

* @param paraGrpBackAirDate
* 回程航班日期
 */

public void setGrpBackAirDate(Date paraGrpBackAirDate){
  super.recordChanged("GRP_BACK_AIR_DATE", this.grpBackAirDate_, paraGrpBackAirDate);
  this.grpBackAirDate_ = paraGrpBackAirDate;
}


/**
 * 获取 GRP_OUT_SETTING_OK
 *
* @return GRP_OUT_SETTING_OK
*/
public String getGrpOutSettingOk() {return this.grpOutSettingOk_;}
/**
* 赋值 GRP_OUT_SETTING_OK

* @param paraGrpOutSettingOk
* GRP_OUT_SETTING_OK
 */

public void setGrpOutSettingOk(String paraGrpOutSettingOk){
  super.recordChanged("GRP_OUT_SETTING_OK", this.grpOutSettingOk_, paraGrpOutSettingOk);
  this.grpOutSettingOk_ = paraGrpOutSettingOk;
}


/**
 * 获取 GRP_SEND_ADM_ID
 *
* @return GRP_SEND_ADM_ID
*/
public Integer getGrpSendAdmId() {return this.grpSendAdmId_;}
/**
* 赋值 GRP_SEND_ADM_ID

* @param paraGrpSendAdmId
* GRP_SEND_ADM_ID
 */

public void setGrpSendAdmId(Integer paraGrpSendAdmId){
  super.recordChanged("GRP_SEND_ADM_ID", this.grpSendAdmId_, paraGrpSendAdmId);
  this.grpSendAdmId_ = paraGrpSendAdmId;
}


/**
 * 获取 GRP_SEND_MEMO
 *
* @return GRP_SEND_MEMO
*/
public String getGrpSendMemo() {return this.grpSendMemo_;}
/**
* 赋值 GRP_SEND_MEMO

* @param paraGrpSendMemo
* GRP_SEND_MEMO
 */

public void setGrpSendMemo(String paraGrpSendMemo){
  super.recordChanged("GRP_SEND_MEMO", this.grpSendMemo_, paraGrpSendMemo);
  this.grpSendMemo_ = paraGrpSendMemo;
}


/**
 * 获取 GRP_SEND_TIME
 *
* @return GRP_SEND_TIME
*/
public Date getGrpSendTime() {return this.grpSendTime_;}
/**
* 赋值 GRP_SEND_TIME

* @param paraGrpSendTime
* GRP_SEND_TIME
 */

public void setGrpSendTime(Date paraGrpSendTime){
  super.recordChanged("GRP_SEND_TIME", this.grpSendTime_, paraGrpSendTime);
  this.grpSendTime_ = paraGrpSendTime;
}


/**
 * 获取 同步编号
 *
* @return 同步编号
*/
public String getGrpSyncUid() {return this.grpSyncUid_;}
/**
* 赋值 同步编号

* @param paraGrpSyncUid
* 同步编号
 */

public void setGrpSyncUid(String paraGrpSyncUid){
  super.recordChanged("GRP_SYNC_UID", this.grpSyncUid_, paraGrpSyncUid);
  this.grpSyncUid_ = paraGrpSyncUid;
}


/**
 * 获取 LINE_ID
 *
* @return LINE_ID
*/
public Integer getLineId() {return this.lineId_;}
/**
* 赋值 LINE_ID

* @param paraLineId
* LINE_ID
 */

public void setLineId(Integer paraLineId){
  super.recordChanged("LINE_ID", this.lineId_, paraLineId);
  this.lineId_ = paraLineId;
}


/**
 * 获取 放入历史询价
 *
* @return 放入历史询价
*/
public String getGrpCancel() {return this.grpCancel_;}
/**
* 赋值 放入历史询价

* @param paraGrpCancel
* 放入历史询价
 */

public void setGrpCancel(String paraGrpCancel){
  super.recordChanged("GRP_CANCEL", this.grpCancel_, paraGrpCancel);
  this.grpCancel_ = paraGrpCancel;
}


/**
 * 获取 _SYNC_
 *
* @return _SYNC_
*/
public String getSync() {return this.sync_;}
/**
* 赋值 _SYNC_

* @param paraSync
* _SYNC_
 */

public void setSync(String paraSync){
  super.recordChanged("_SYNC_", this.sync_, paraSync);
  this.sync_ = paraSync;
}


/**
 * 获取 DEP_ID
 *
* @return DEP_ID
*/
public Integer getDepId() {return this.depId_;}
/**
* 赋值 DEP_ID

* @param paraDepId
* DEP_ID
 */

public void setDepId(Integer paraDepId){
  super.recordChanged("DEP_ID", this.depId_, paraDepId);
  this.depId_ = paraDepId;
}


/**
 * 获取 GRP_CAMP_COUNTRY
 *
* @return GRP_CAMP_COUNTRY
*/
public Integer getGrpCampCountry() {return this.grpCampCountry_;}
/**
* 赋值 GRP_CAMP_COUNTRY

* @param paraGrpCampCountry
* GRP_CAMP_COUNTRY
 */

public void setGrpCampCountry(Integer paraGrpCampCountry){
  super.recordChanged("GRP_CAMP_COUNTRY", this.grpCampCountry_, paraGrpCampCountry);
  this.grpCampCountry_ = paraGrpCampCountry;
}


/**
 * 获取 GRP_CAMP_ID
 *
* @return GRP_CAMP_ID
*/
public Integer getGrpCampId() {return this.grpCampId_;}
/**
* 赋值 GRP_CAMP_ID

* @param paraGrpCampId
* GRP_CAMP_ID
 */

public void setGrpCampId(Integer paraGrpCampId){
  super.recordChanged("GRP_CAMP_ID", this.grpCampId_, paraGrpCampId);
  this.grpCampId_ = paraGrpCampId;
}


/**
 * 获取 GRP_CAMP_BEGIN_DATE
 *
* @return GRP_CAMP_BEGIN_DATE
*/
public Date getGrpCampBeginDate() {return this.grpCampBeginDate_;}
/**
* 赋值 GRP_CAMP_BEGIN_DATE

* @param paraGrpCampBeginDate
* GRP_CAMP_BEGIN_DATE
 */

public void setGrpCampBeginDate(Date paraGrpCampBeginDate){
  super.recordChanged("GRP_CAMP_BEGIN_DATE", this.grpCampBeginDate_, paraGrpCampBeginDate);
  this.grpCampBeginDate_ = paraGrpCampBeginDate;
}


/**
 * 获取 GRP_CAMP_END_DATE
 *
* @return GRP_CAMP_END_DATE
*/
public Date getGrpCampEndDate() {return this.grpCampEndDate_;}
/**
* 赋值 GRP_CAMP_END_DATE

* @param paraGrpCampEndDate
* GRP_CAMP_END_DATE
 */

public void setGrpCampEndDate(Date paraGrpCampEndDate){
  super.recordChanged("GRP_CAMP_END_DATE", this.grpCampEndDate_, paraGrpCampEndDate);
  this.grpCampEndDate_ = paraGrpCampEndDate;
}


/**
 * 获取 GRP_CAMP_ACCOMMODATION_TYPE
 *
* @return GRP_CAMP_ACCOMMODATION_TYPE
*/
public String getGrpCampAccommodationType() {return this.grpCampAccommodationType_;}
/**
* 赋值 GRP_CAMP_ACCOMMODATION_TYPE

* @param paraGrpCampAccommodationType
* GRP_CAMP_ACCOMMODATION_TYPE
 */

public void setGrpCampAccommodationType(String paraGrpCampAccommodationType){
  super.recordChanged("GRP_CAMP_ACCOMMODATION_TYPE", this.grpCampAccommodationType_, paraGrpCampAccommodationType);
  this.grpCampAccommodationType_ = paraGrpCampAccommodationType;
}


/**
 * 获取 GRP_CAMP_COURSE_TYPE
 *
* @return GRP_CAMP_COURSE_TYPE
*/
public String getGrpCampCourseType() {return this.grpCampCourseType_;}
/**
* 赋值 GRP_CAMP_COURSE_TYPE

* @param paraGrpCampCourseType
* GRP_CAMP_COURSE_TYPE
 */

public void setGrpCampCourseType(String paraGrpCampCourseType){
  super.recordChanged("GRP_CAMP_COURSE_TYPE", this.grpCampCourseType_, paraGrpCampCourseType);
  this.grpCampCourseType_ = paraGrpCampCourseType;
}


/**
 * 获取 CP_VISA_STATUS
 *
* @return CP_VISA_STATUS
*/
public String getCpVisaStatus() {return this.cpVisaStatus_;}
/**
* 赋值 CP_VISA_STATUS

* @param paraCpVisaStatus
* CP_VISA_STATUS
 */

public void setCpVisaStatus(String paraCpVisaStatus){
  super.recordChanged("CP_VISA_STATUS", this.cpVisaStatus_, paraCpVisaStatus);
  this.cpVisaStatus_ = paraCpVisaStatus;
}


/**
 * 获取 CP_STATUS
 *
* @return CP_STATUS
*/
public String getCpStatus() {return this.cpStatus_;}
/**
* 赋值 CP_STATUS

* @param paraCpStatus
* CP_STATUS
 */

public void setCpStatus(String paraCpStatus){
  super.recordChanged("CP_STATUS", this.cpStatus_, paraCpStatus);
  this.cpStatus_ = paraCpStatus;
}


/**
 * 获取 CP_AIR_TICKET
 *
* @return CP_AIR_TICKET
*/
public String getCpAirTicket() {return this.cpAirTicket_;}
/**
* 赋值 CP_AIR_TICKET

* @param paraCpAirTicket
* CP_AIR_TICKET
 */

public void setCpAirTicket(String paraCpAirTicket){
  super.recordChanged("CP_AIR_TICKET", this.cpAirTicket_, paraCpAirTicket);
  this.cpAirTicket_ = paraCpAirTicket;
}


/**
 * 获取 GRP_CAMP_SET_STATUS
 *
* @return GRP_CAMP_SET_STATUS
*/
public String getGrpCampSetStatus() {return this.grpCampSetStatus_;}
/**
* 赋值 GRP_CAMP_SET_STATUS

* @param paraGrpCampSetStatus
* GRP_CAMP_SET_STATUS
 */

public void setGrpCampSetStatus(String paraGrpCampSetStatus){
  super.recordChanged("GRP_CAMP_SET_STATUS", this.grpCampSetStatus_, paraGrpCampSetStatus);
  this.grpCampSetStatus_ = paraGrpCampSetStatus;
}


/**
 * 获取 GRP_CAMP_AGE
 *
* @return GRP_CAMP_AGE
*/
public String getGrpCampAge() {return this.grpCampAge_;}
/**
* 赋值 GRP_CAMP_AGE

* @param paraGrpCampAge
* GRP_CAMP_AGE
 */

public void setGrpCampAge(String paraGrpCampAge){
  super.recordChanged("GRP_CAMP_AGE", this.grpCampAge_, paraGrpCampAge);
  this.grpCampAge_ = paraGrpCampAge;
}


/**
 * 获取 GRP_FIRST_HOTEL
 *
* @return GRP_FIRST_HOTEL
*/
public String getGrpFirstHotel() {return this.grpFirstHotel_;}
/**
* 赋值 GRP_FIRST_HOTEL

* @param paraGrpFirstHotel
* GRP_FIRST_HOTEL
 */

public void setGrpFirstHotel(String paraGrpFirstHotel){
  super.recordChanged("GRP_FIRST_HOTEL", this.grpFirstHotel_, paraGrpFirstHotel);
  this.grpFirstHotel_ = paraGrpFirstHotel;
}


/**
 * 获取 GRP_EMBASSY
 *
* @return GRP_EMBASSY
*/
public String getGrpEmbassy() {return this.grpEmbassy_;}
/**
* 赋值 GRP_EMBASSY

* @param paraGrpEmbassy
* GRP_EMBASSY
 */

public void setGrpEmbassy(String paraGrpEmbassy){
  super.recordChanged("GRP_EMBASSY", this.grpEmbassy_, paraGrpEmbassy);
  this.grpEmbassy_ = paraGrpEmbassy;
}


/**
 * 获取 GRP_COUNTRYS
 *
* @return GRP_COUNTRYS
*/
public String getGrpCountrys() {return this.grpCountrys_;}
/**
* 赋值 GRP_COUNTRYS

* @param paraGrpCountrys
* GRP_COUNTRYS
 */

public void setGrpCountrys(String paraGrpCountrys){
  super.recordChanged("GRP_COUNTRYS", this.grpCountrys_, paraGrpCountrys);
  this.grpCountrys_ = paraGrpCountrys;
}


/**
 * 获取 GRP_PID
 *
* @return GRP_PID
*/
public Integer getGrpPid() {return this.grpPid_;}
/**
* 赋值 GRP_PID

* @param paraGrpPid
* GRP_PID
 */

public void setGrpPid(Integer paraGrpPid){
  super.recordChanged("GRP_PID", this.grpPid_, paraGrpPid);
  this.grpPid_ = paraGrpPid;
}


/**
 * 获取 GRP_HOME_STATE
 *
* @return GRP_HOME_STATE
*/
public String getGrpHomeState() {return this.grpHomeState_;}
/**
* 赋值 GRP_HOME_STATE

* @param paraGrpHomeState
* GRP_HOME_STATE
 */

public void setGrpHomeState(String paraGrpHomeState){
  super.recordChanged("GRP_HOME_STATE", this.grpHomeState_, paraGrpHomeState);
  this.grpHomeState_ = paraGrpHomeState;
}


/**
 * 获取 GRP_HOME_ADM_ID
 *
* @return GRP_HOME_ADM_ID
*/
public Integer getGrpHomeAdmId() {return this.grpHomeAdmId_;}
/**
* 赋值 GRP_HOME_ADM_ID

* @param paraGrpHomeAdmId
* GRP_HOME_ADM_ID
 */

public void setGrpHomeAdmId(Integer paraGrpHomeAdmId){
  super.recordChanged("GRP_HOME_ADM_ID", this.grpHomeAdmId_, paraGrpHomeAdmId);
  this.grpHomeAdmId_ = paraGrpHomeAdmId;
}


/**
 * 获取 GRP_HOME_OP_ID
 *
* @return GRP_HOME_OP_ID
*/
public Integer getGrpHomeOpId() {return this.grpHomeOpId_;}
/**
* 赋值 GRP_HOME_OP_ID

* @param paraGrpHomeOpId
* GRP_HOME_OP_ID
 */

public void setGrpHomeOpId(Integer paraGrpHomeOpId){
  super.recordChanged("GRP_HOME_OP_ID", this.grpHomeOpId_, paraGrpHomeOpId);
  this.grpHomeOpId_ = paraGrpHomeOpId;
}


/**
 * 获取 GRP_HOME_EDATE
 *
* @return GRP_HOME_EDATE
*/
public Date getGrpHomeEdate() {return this.grpHomeEdate_;}
/**
* 赋值 GRP_HOME_EDATE

* @param paraGrpHomeEdate
* GRP_HOME_EDATE
 */

public void setGrpHomeEdate(Date paraGrpHomeEdate){
  super.recordChanged("GRP_HOME_EDATE", this.grpHomeEdate_, paraGrpHomeEdate);
  this.grpHomeEdate_ = paraGrpHomeEdate;
}


/**
 * 获取 GRP_HOME_OP_EDATE
 *
* @return GRP_HOME_OP_EDATE
*/
public Date getGrpHomeOpEdate() {return this.grpHomeOpEdate_;}
/**
* 赋值 GRP_HOME_OP_EDATE

* @param paraGrpHomeOpEdate
* GRP_HOME_OP_EDATE
 */

public void setGrpHomeOpEdate(Date paraGrpHomeOpEdate){
  super.recordChanged("GRP_HOME_OP_EDATE", this.grpHomeOpEdate_, paraGrpHomeOpEdate);
  this.grpHomeOpEdate_ = paraGrpHomeOpEdate;
}


/**
 * 获取 GRP_SHOW_TAG
 *
* @return GRP_SHOW_TAG
*/
public String getGrpShowTag() {return this.grpShowTag_;}
/**
* 赋值 GRP_SHOW_TAG

* @param paraGrpShowTag
* GRP_SHOW_TAG
 */

public void setGrpShowTag(String paraGrpShowTag){
  super.recordChanged("GRP_SHOW_TAG", this.grpShowTag_, paraGrpShowTag);
  this.grpShowTag_ = paraGrpShowTag;
}


/**
 * 获取 GRP_CAMP_UNID
 *
* @return GRP_CAMP_UNID
*/
public String getGrpCampUnid() {return this.grpCampUnid_;}
/**
* 赋值 GRP_CAMP_UNID

* @param paraGrpCampUnid
* GRP_CAMP_UNID
 */

public void setGrpCampUnid(String paraGrpCampUnid){
  super.recordChanged("GRP_CAMP_UNID", this.grpCampUnid_, paraGrpCampUnid);
  this.grpCampUnid_ = paraGrpCampUnid;
}


/**
 * 获取 GRP_VIS_DEF
 *
* @return GRP_VIS_DEF
*/
public Double getGrpVisDef() {return this.grpVisDef_;}
/**
* 赋值 GRP_VIS_DEF

* @param paraGrpVisDef
* GRP_VIS_DEF
 */

public void setGrpVisDef(Double paraGrpVisDef){
  super.recordChanged("GRP_VIS_DEF", this.grpVisDef_, paraGrpVisDef);
  this.grpVisDef_ = paraGrpVisDef;
}


/**
 * 获取 GRP_CAMP_DEF
 *
* @return GRP_CAMP_DEF
*/
public Double getGrpCampDef() {return this.grpCampDef_;}
/**
* 赋值 GRP_CAMP_DEF

* @param paraGrpCampDef
* GRP_CAMP_DEF
 */

public void setGrpCampDef(Double paraGrpCampDef){
  super.recordChanged("GRP_CAMP_DEF", this.grpCampDef_, paraGrpCampDef);
  this.grpCampDef_ = paraGrpCampDef;
}


/**
 * 获取 GRP_AIR_DEF
 *
* @return GRP_AIR_DEF
*/
public Double getGrpAirDef() {return this.grpAirDef_;}
/**
* 赋值 GRP_AIR_DEF

* @param paraGrpAirDef
* GRP_AIR_DEF
 */

public void setGrpAirDef(Double paraGrpAirDef){
  super.recordChanged("GRP_AIR_DEF", this.grpAirDef_, paraGrpAirDef);
  this.grpAirDef_ = paraGrpAirDef;
}


/**
 * 获取 GRP_NAME_EN
 *
* @return GRP_NAME_EN
*/
public String getGrpNameEn() {return this.grpNameEn_;}
/**
* 赋值 GRP_NAME_EN

* @param paraGrpNameEn
* GRP_NAME_EN
 */

public void setGrpNameEn(String paraGrpNameEn){
  super.recordChanged("GRP_NAME_EN", this.grpNameEn_, paraGrpNameEn);
  this.grpNameEn_ = paraGrpNameEn;
}


/**
 * 获取 AUS_ADT
 *
* @return AUS_ADT
*/
public Integer getAusAdt() {return this.ausAdt_;}
/**
* 赋值 AUS_ADT

* @param paraAusAdt
* AUS_ADT
 */

public void setAusAdt(Integer paraAusAdt){
  super.recordChanged("AUS_ADT", this.ausAdt_, paraAusAdt);
  this.ausAdt_ = paraAusAdt;
}


/**
 * 获取 AUS_CHD
 *
* @return AUS_CHD
*/
public Integer getAusChd() {return this.ausChd_;}
/**
* 赋值 AUS_CHD

* @param paraAusChd
* AUS_CHD
 */

public void setAusChd(Integer paraAusChd){
  super.recordChanged("AUS_CHD", this.ausChd_, paraAusChd);
  this.ausChd_ = paraAusChd;
}


/**
 * 获取 AUS_CHDNBRATE
 *
* @return AUS_CHDNBRATE
*/
public Double getAusChdnbrate() {return this.ausChdnbrate_;}
/**
* 赋值 AUS_CHDNBRATE

* @param paraAusChdnbrate
* AUS_CHDNBRATE
 */

public void setAusChdnbrate(Double paraAusChdnbrate){
  super.recordChanged("AUS_CHDNBRATE", this.ausChdnbrate_, paraAusChdnbrate);
  this.ausChdnbrate_ = paraAusChdnbrate;
}


/**
 * 获取 AUS_CHDRATE
 *
* @return AUS_CHDRATE
*/
public Double getAusChdrate() {return this.ausChdrate_;}
/**
* 赋值 AUS_CHDRATE

* @param paraAusChdrate
* AUS_CHDRATE
 */

public void setAusChdrate(Double paraAusChdrate){
  super.recordChanged("AUS_CHDRATE", this.ausChdrate_, paraAusChdrate);
  this.ausChdrate_ = paraAusChdrate;
}


/**
 * 获取 AUS_FOC
 *
* @return AUS_FOC
*/
public Integer getAusFoc() {return this.ausFoc_;}
/**
* 赋值 AUS_FOC

* @param paraAusFoc
* AUS_FOC
 */

public void setAusFoc(Integer paraAusFoc){
  super.recordChanged("AUS_FOC", this.ausFoc_, paraAusFoc);
  this.ausFoc_ = paraAusFoc;
}


/**
 * 获取 AUS_INFANTRATE
 *
* @return AUS_INFANTRATE
*/
public Double getAusInfantrate() {return this.ausInfantrate_;}
/**
* 赋值 AUS_INFANTRATE

* @param paraAusInfantrate
* AUS_INFANTRATE
 */

public void setAusInfantrate(Double paraAusInfantrate){
  super.recordChanged("AUS_INFANTRATE", this.ausInfantrate_, paraAusInfantrate);
  this.ausInfantrate_ = paraAusInfantrate;
}


/**
 * 获取 AUS_MARGIN
 *
* @return AUS_MARGIN
*/
public Double getAusMargin() {return this.ausMargin_;}
/**
* 赋值 AUS_MARGIN

* @param paraAusMargin
* AUS_MARGIN
 */

public void setAusMargin(Double paraAusMargin){
  super.recordChanged("AUS_MARGIN", this.ausMargin_, paraAusMargin);
  this.ausMargin_ = paraAusMargin;
}


/**
 * 获取 AUS_MARKUP
 *
* @return AUS_MARKUP
*/
public Double getAusMarkup() {return this.ausMarkup_;}
/**
* 赋值 AUS_MARKUP

* @param paraAusMarkup
* AUS_MARKUP
 */

public void setAusMarkup(Double paraAusMarkup){
  super.recordChanged("AUS_MARKUP", this.ausMarkup_, paraAusMarkup);
  this.ausMarkup_ = paraAusMarkup;
}


/**
 * 获取 AUS_SNL
 *
* @return AUS_SNL
*/
public Integer getAusSnl() {return this.ausSnl_;}
/**
* 赋值 AUS_SNL

* @param paraAusSnl
* AUS_SNL
 */

public void setAusSnl(Integer paraAusSnl){
  super.recordChanged("AUS_SNL", this.ausSnl_, paraAusSnl);
  this.ausSnl_ = paraAusSnl;
}


/**
 * 获取 AUS_TOURCLASS
 *
* @return AUS_TOURCLASS
*/
public Double getAusTourclass() {return this.ausTourclass_;}
/**
* 赋值 AUS_TOURCLASS

* @param paraAusTourclass
* AUS_TOURCLASS
 */

public void setAusTourclass(Double paraAusTourclass){
  super.recordChanged("AUS_TOURCLASS", this.ausTourclass_, paraAusTourclass);
  this.ausTourclass_ = paraAusTourclass;
}


/**
 * 获取 AUS_TWN
 *
* @return AUS_TWN
*/
public Integer getAusTwn() {return this.ausTwn_;}
/**
* 赋值 AUS_TWN

* @param paraAusTwn
* AUS_TWN
 */

public void setAusTwn(Integer paraAusTwn){
  super.recordChanged("AUS_TWN", this.ausTwn_, paraAusTwn);
  this.ausTwn_ = paraAusTwn;
}


/**
 * 获取 AUS_UNDER18
 *
* @return AUS_UNDER18
*/
public Integer getAusUnder18() {return this.ausUnder18_;}
/**
* 赋值 AUS_UNDER18

* @param paraAusUnder18
* AUS_UNDER18
 */

public void setAusUnder18(Integer paraAusUnder18){
  super.recordChanged("AUS_UNDER18", this.ausUnder18_, paraAusUnder18);
  this.ausUnder18_ = paraAusUnder18;
}


/**
 * 获取 IS_AUS
 *
* @return IS_AUS
*/
public Integer getIsAus() {return this.isAus_;}
/**
* 赋值 IS_AUS

* @param paraIsAus
* IS_AUS
 */

public void setIsAus(Integer paraIsAus){
  super.recordChanged("IS_AUS", this.isAus_, paraIsAus);
  this.isAus_ = paraIsAus;
}


/**
 * 获取 AUS_SEATERS
 *
* @return AUS_SEATERS
*/
public Double getAusSeaters() {return this.ausSeaters_;}
/**
* 赋值 AUS_SEATERS

* @param paraAusSeaters
* AUS_SEATERS
 */

public void setAusSeaters(Double paraAusSeaters){
  super.recordChanged("AUS_SEATERS", this.ausSeaters_, paraAusSeaters);
  this.ausSeaters_ = paraAusSeaters;
}


/**
 * 获取 AUS_RATING
 *
* @return AUS_RATING
*/
public Double getAusRating() {return this.ausRating_;}
/**
* 赋值 AUS_RATING

* @param paraAusRating
* AUS_RATING
 */

public void setAusRating(Double paraAusRating){
  super.recordChanged("AUS_RATING", this.ausRating_, paraAusRating);
  this.ausRating_ = paraAusRating;
}


/**
 * 获取 EXAM_ID
 *
* @return EXAM_ID
*/
public Integer getExamId() {return this.examId_;}
/**
* 赋值 EXAM_ID

* @param paraExamId
* EXAM_ID
 */

public void setExamId(Integer paraExamId){
  super.recordChanged("EXAM_ID", this.examId_, paraExamId);
  this.examId_ = paraExamId;
}


/**
 * 获取 GRP_HEADER_TEACHER
 *
* @return GRP_HEADER_TEACHER
*/
public Integer getGrpHeaderTeacher() {return this.grpHeaderTeacher_;}
/**
* 赋值 GRP_HEADER_TEACHER

* @param paraGrpHeaderTeacher
* GRP_HEADER_TEACHER
 */

public void setGrpHeaderTeacher(Integer paraGrpHeaderTeacher){
  super.recordChanged("GRP_HEADER_TEACHER", this.grpHeaderTeacher_, paraGrpHeaderTeacher);
  this.grpHeaderTeacher_ = paraGrpHeaderTeacher;
}


/**
 * 获取 GRP_TEACHER_CHINESE
 *
* @return GRP_TEACHER_CHINESE
*/
public Integer getGrpTeacherChinese() {return this.grpTeacherChinese_;}
/**
* 赋值 GRP_TEACHER_CHINESE

* @param paraGrpTeacherChinese
* GRP_TEACHER_CHINESE
 */

public void setGrpTeacherChinese(Integer paraGrpTeacherChinese){
  super.recordChanged("GRP_TEACHER_CHINESE", this.grpTeacherChinese_, paraGrpTeacherChinese);
  this.grpTeacherChinese_ = paraGrpTeacherChinese;
}


/**
 * 获取 GRP_TEACHER_FOREIGN
 *
* @return GRP_TEACHER_FOREIGN
*/
public Integer getGrpTeacherForeign() {return this.grpTeacherForeign_;}
/**
* 赋值 GRP_TEACHER_FOREIGN

* @param paraGrpTeacherForeign
* GRP_TEACHER_FOREIGN
 */

public void setGrpTeacherForeign(Integer paraGrpTeacherForeign){
  super.recordChanged("GRP_TEACHER_FOREIGN", this.grpTeacherForeign_, paraGrpTeacherForeign);
  this.grpTeacherForeign_ = paraGrpTeacherForeign;
}


/**
 * 获取 GRP_SCHOOL_NAME
 *
* @return GRP_SCHOOL_NAME
*/
public String getGrpSchoolName() {return this.grpSchoolName_;}
/**
* 赋值 GRP_SCHOOL_NAME

* @param paraGrpSchoolName
* GRP_SCHOOL_NAME
 */

public void setGrpSchoolName(String paraGrpSchoolName){
  super.recordChanged("GRP_SCHOOL_NAME", this.grpSchoolName_, paraGrpSchoolName);
  this.grpSchoolName_ = paraGrpSchoolName;
}


/**
 * 获取 GRP_CLASS_NAME
 *
* @return GRP_CLASS_NAME
*/
public String getGrpClassName() {return this.grpClassName_;}
/**
* 赋值 GRP_CLASS_NAME

* @param paraGrpClassName
* GRP_CLASS_NAME
 */

public void setGrpClassName(String paraGrpClassName){
  super.recordChanged("GRP_CLASS_NAME", this.grpClassName_, paraGrpClassName);
  this.grpClassName_ = paraGrpClassName;
}


/**
 * 获取 GRP_CLASSROOM
 *
* @return GRP_CLASSROOM
*/
public String getGrpClassroom() {return this.grpClassroom_;}
/**
* 赋值 GRP_CLASSROOM

* @param paraGrpClassroom
* GRP_CLASSROOM
 */

public void setGrpClassroom(String paraGrpClassroom){
  super.recordChanged("GRP_CLASSROOM", this.grpClassroom_, paraGrpClassroom);
  this.grpClassroom_ = paraGrpClassroom;
}


/**
 * 获取 GRP_TECHING_MATERIAL
 *
* @return GRP_TECHING_MATERIAL
*/
public String getGrpTechingMaterial() {return this.grpTechingMaterial_;}
/**
* 赋值 GRP_TECHING_MATERIAL

* @param paraGrpTechingMaterial
* GRP_TECHING_MATERIAL
 */

public void setGrpTechingMaterial(String paraGrpTechingMaterial){
  super.recordChanged("GRP_TECHING_MATERIAL", this.grpTechingMaterial_, paraGrpTechingMaterial);
  this.grpTechingMaterial_ = paraGrpTechingMaterial;
}


/**
 * 获取 GRP_REF_TABLE
 *
* @return GRP_REF_TABLE
*/
public String getGrpRefTable() {return this.grpRefTable_;}
/**
* 赋值 GRP_REF_TABLE

* @param paraGrpRefTable
* GRP_REF_TABLE
 */

public void setGrpRefTable(String paraGrpRefTable){
  super.recordChanged("GRP_REF_TABLE", this.grpRefTable_, paraGrpRefTable);
  this.grpRefTable_ = paraGrpRefTable;
}


/**
 * 获取 GRP_REF_ID
 *
* @return GRP_REF_ID
*/
public Integer getGrpRefId() {return this.grpRefId_;}
/**
* 赋值 GRP_REF_ID

* @param paraGrpRefId
* GRP_REF_ID
 */

public void setGrpRefId(Integer paraGrpRefId){
  super.recordChanged("GRP_REF_ID", this.grpRefId_, paraGrpRefId);
  this.grpRefId_ = paraGrpRefId;
}


/**
 * 获取 UNIV_UNID
 *
* @return UNIV_UNID
*/
public String getUnivUnid() {return this.univUnid_;}
/**
* 赋值 UNIV_UNID

* @param paraUnivUnid
* UNIV_UNID
 */

public void setUnivUnid(String paraUnivUnid){
  super.recordChanged("UNIV_UNID", this.univUnid_, paraUnivUnid);
  this.univUnid_ = paraUnivUnid;
}
}