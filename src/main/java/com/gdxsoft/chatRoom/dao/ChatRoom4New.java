package com.gdxsoft.chatRoom.dao;

import com.gdxsoft.easyweb.datasource.ClassBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 表chat_room映射类
 * 
 * @author gdx 时间：Wed Jun 02 2021 11:30:07 GMT+0800 (中国标准时间)
 */
@ApiModel(value = "com.gdxsoft.chatRoom.dao.ChatRoom4New", description = "表chat_room映射类")
public class ChatRoom4New extends ClassBase {
	private String chtRomName_; // 名称
	private String chtRomNameEn_; // 名称英文
	private String chtRomMemo_; // 备注
	private String chtRomMemoEn_; // 备注英文
	private Long chtRomCreator_; // 创建人
	private Long chtRomOwner_; // 拥有人
	private String chtRomType_; // 类型
	private String chtRomRef_; // 来源
	private String chtRomRefId_; // 编号
	private String chtRomTag0_; // 标记0
	private String chtRomTag1_; // 标记1
	private String chtRomTag2_; // 标记2

	/**
	 * 获取 名称
	 *
	 * @return 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	public String getChtRomName() {
		return this.chtRomName_;
	}

	/**
	 * 赋值 名称
	 * 
	 * @param paraChtRomName 名称
	 */

	public void setChtRomName(String paraChtRomName) {
		super.recordChanged("cht_rom_name", this.chtRomName_, paraChtRomName);
		this.chtRomName_ = paraChtRomName;
	}

	/**
	 * 获取 名称英文
	 *
	 * @return 名称英文
	 */
	@ApiModelProperty(value = "名称英文", required = false)
	public String getChtRomNameEn() {
		return this.chtRomNameEn_;
	}

	/**
	 * 赋值 名称英文
	 * 
	 * @param paraChtRomNameEn 名称英文
	 */

	public void setChtRomNameEn(String paraChtRomNameEn) {
		super.recordChanged("cht_rom_name_en", this.chtRomNameEn_, paraChtRomNameEn);
		this.chtRomNameEn_ = paraChtRomNameEn;
	}

	/**
	 * 获取 备注
	 *
	 * @return 备注
	 */
	@ApiModelProperty(value = "备注", required = false)
	public String getChtRomMemo() {
		return this.chtRomMemo_;
	}

	/**
	 * 赋值 备注
	 * 
	 * @param paraChtRomMemo 备注
	 */

	public void setChtRomMemo(String paraChtRomMemo) {
		super.recordChanged("cht_rom_memo", this.chtRomMemo_, paraChtRomMemo);
		this.chtRomMemo_ = paraChtRomMemo;
	}

	/**
	 * 获取 备注英文
	 *
	 * @return 备注英文
	 */
	@ApiModelProperty(value = "备注英文", required = false)
	public String getChtRomMemoEn() {
		return this.chtRomMemoEn_;
	}

	/**
	 * 赋值 备注英文
	 * 
	 * @param paraChtRomMemoEn 备注英文
	 */

	public void setChtRomMemoEn(String paraChtRomMemoEn) {
		super.recordChanged("cht_rom_memo_en", this.chtRomMemoEn_, paraChtRomMemoEn);
		this.chtRomMemoEn_ = paraChtRomMemoEn;
	}

	/**
	 * 获取 创建人
	 *
	 * @return 创建人
	 */
	@ApiModelProperty(value = "创建人", required = true)
	public Long getChtRomCreator() {
		return this.chtRomCreator_;
	}

	/**
	 * 赋值 创建人
	 * 
	 * @param paraChtRomCreator 创建人
	 */

	public void setChtRomCreator(Long paraChtRomCreator) {
		super.recordChanged("cht_rom_creator", this.chtRomCreator_, paraChtRomCreator);
		this.chtRomCreator_ = paraChtRomCreator;
	}

	/**
	 * 获取 拥有人
	 *
	 * @return 拥有人
	 */
	@ApiModelProperty(value = "拥有人", required = true)
	public Long getChtRomOwner() {
		return this.chtRomOwner_;
	}

	/**
	 * 赋值 拥有人
	 * 
	 * @param paraChtRomOwner 拥有人
	 */

	public void setChtRomOwner(Long paraChtRomOwner) {
		super.recordChanged("cht_rom_owner", this.chtRomOwner_, paraChtRomOwner);
		this.chtRomOwner_ = paraChtRomOwner;
	}

	/**
	 * 获取 类型
	 *
	 * @return 类型
	 */
	@ApiModelProperty(value = "类型", required = true)
	public String getChtRomType() {
		return this.chtRomType_;
	}

	/**
	 * 赋值 类型
	 * 
	 * @param paraChtRomType 类型
	 */

	public void setChtRomType(String paraChtRomType) {
		super.recordChanged("cht_rom_type", this.chtRomType_, paraChtRomType);
		this.chtRomType_ = paraChtRomType;
	}

	/**
	 * 获取 来源
	 *
	 * @return 来源
	 */
	@ApiModelProperty(value = "来源", required = true)
	public String getChtRomRef() {
		return this.chtRomRef_;
	}

	/**
	 * 赋值 来源
	 * 
	 * @param paraChtRomRef 来源
	 */

	public void setChtRomRef(String paraChtRomRef) {
		super.recordChanged("cht_rom_ref", this.chtRomRef_, paraChtRomRef);
		this.chtRomRef_ = paraChtRomRef;
	}

	/**
	 * 获取 编号
	 *
	 * @return 编号
	 */
	@ApiModelProperty(value = "编号", required = true)
	public String getChtRomRefId() {
		return this.chtRomRefId_;
	}

	/**
	 * 赋值 编号
	 * 
	 * @param paraChtRomRefId 编号
	 */

	public void setChtRomRefId(String paraChtRomRefId) {
		super.recordChanged("cht_rom_ref_id", this.chtRomRefId_, paraChtRomRefId);
		this.chtRomRefId_ = paraChtRomRefId;
	}

	/**
	 * 获取 标记0
	 *
	 * @return 标记0
	 */
	@ApiModelProperty(value = "标记0", required = false)
	public String getChtRomTag0() {
		return this.chtRomTag0_;
	}

	/**
	 * 赋值 标记0
	 * 
	 * @param paraChtRomTag0 标记0
	 */
	public void setChtRomTag0(String paraChtRomTag0) {
		super.recordChanged("cht_rom_tag0", this.chtRomTag0_, paraChtRomTag0);
		this.chtRomTag0_ = paraChtRomTag0;
	}

	/**
	 * 获取 标记1
	 *
	 * @return 标记1
	 */
	@ApiModelProperty(value = "标记1", required = false)
	public String getChtRomTag1() {
		return this.chtRomTag1_;
	}

	/**
	 * 赋值 标记1
	 * 
	 * @param paraChtRomTag1 标记1
	 */
	public void setChtRomTag1(String paraChtRomTag1) {
		super.recordChanged("cht_rom_tag1", this.chtRomTag1_, paraChtRomTag1);
		this.chtRomTag1_ = paraChtRomTag1;
	}

	/**
	 * 获取 标记2
	 *
	 * @return 标记2
	 */
	@ApiModelProperty(value = "标记2", required = false)
	public String getChtRomTag2() {
		return this.chtRomTag2_;
	}

	/**
	 * 赋值 标记2
	 * 
	 * @param paraChtRomTag2 标记2
	 */
	public void setChtRomTag2(String paraChtRomTag2) {
		super.recordChanged("cht_rom_tag2", this.chtRomTag2_, paraChtRomTag2);
		this.chtRomTag2_ = paraChtRomTag2;
	}

}