package com.gdxsoft.web.weixin;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.Utils;
import com.gdxsoft.weixin.WeiXinRedPackageResult;

public class WeiXinRedPackage {

	/**
	 * 根据红包类型返回red_uid
	 * 
	 * @param red_type
	 *            红包类型
	 * @return
	 */
	public static String getRedPackageUid(String red_type,String wx_cfg_no) {
		RequestValue rv1 = new RequestValue();
		if (red_type == null || red_type.trim().length() == 0) {
			return null;
		}
		rv1.addValue("red_type", red_type);
		rv1.addValue("wx_cfg_no", wx_cfg_no);
		StringBuilder builder = new StringBuilder();

		if (red_type.equals("WX_RED_TYPE_BLOG")) {
			// 发游学圈 红包分解到每一天
			builder.append("SELECT B.RED_UID FROM WX_REDPACK_MAIN A");
			builder.append(" INNER JOIN WX_REDPACK_MAIN B ON A.RED_UID=B.RED_PUID AND B.RED_START=@EWA.DATE.STR ");
			builder.append(" WHERE A.RED_STATUS = 'COM_YES'");
			builder.append(" and A.RED_TYPE='WX_RED_TYPE_BLOG'"); // WX_RED_TYPE_BLOG发游学圈送红包
			builder.append(" AND A.RED_START <= @EWA.DATE.STR");
			builder.append(" AND A.RED_END >= @EWA.DATE.STR");
			builder.append(" AND A.WX_CFG_NO >= @wx_cfg_no");
			
			builder.append(" order by b.RED_UID");
		} else {
			builder.append("SELECT a.RED_UID FROM WX_REDPACK_MAIN A");
			builder.append(" WHERE A.RED_STATUS = 'COM_YES'");
			builder.append(" and A.RED_TYPE=@red_type"); // WX_RED_TYPE 红包类型
			builder.append(" AND A.RED_START <= @sys_date");
			builder.append(" AND A.RED_END >= @sys_date");
			builder.append(" AND A.WX_CFG_NO >= @wx_cfg_no");
			builder.append(" order by a.RED_UID");
		}
		String sqlCHD = builder.toString();

		DTTable tb = DTTable.getJdbcTable(sqlCHD, "", rv1);

		if (tb.getCount() == 0) {
			return null;
		} else {
			return tb.getCell(0, 0).toString();
		}

	}

	private RequestValue rv;
	private JSONObject obj;
	private WeiXin weixin_;
	private DTTable tbRed;
	private String dataBaseName_;

	/**
	 * 指定的数据库名称 console用
	 * 
	 * @return
	 */
	public String getDataBaseName() {
		return dataBaseName_;
	}

	/**
	 * 指定的数据库名称 console用
	 * 
	 * @param dataBaseName_
	 *            数据库名称
	 */
	public void setDataBaseName(String dataBaseName_) {
		this.dataBaseName_ = dataBaseName_;
	}

	private int redpack_user_idx; // 已经领取的ID，自增变量，也用于订单顺序号

	public WeiXinRedPackage(WeiXin weixin) {
		rv = new RequestValue();
		obj = new JSONObject();
		this.weixin_ = weixin;
		redpack_user_idx = -1;
	}

	private String replaceDatabaseName(String sql) {
		String db = this.dataBaseName_ == null ? "" : this.dataBaseName_.trim()
				+ ".dbo.";
		String sql2 = sql.replace("[DB]", db);

		return sql2;
	}

	/**
	 * 检查用户身份合法性
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean checkUser() throws Exception {
		String userCheck = "select * from [DB]wx_user where USR_UNID=@g_USR_UNID and WX_CFG_NO=@WX_CFG_NO";
		userCheck = this.replaceDatabaseName(userCheck);

		DTTable tbuserCheck = DTTable.getJdbcTable(userCheck, "", rv);

		if (tbuserCheck.getCount() == 0) {
			this.setError("用户信息丢失，请重新进入");
			return false;
		}

		// 是否关注微信号
		String IS_WEIXIN_SUBSCRIBE = tbuserCheck.getCell(0,
				"IS_WEIXIN_SUBSCRIBE").toString();

		if (IS_WEIXIN_SUBSCRIBE == null || !IS_WEIXIN_SUBSCRIBE.equals("1")) {
			this.setError("请先关注微信号<" + this.weixin_.getWxCfgName() + ">");
			return false;
		}

		// 用户的微信编号
		String open_id = tbuserCheck.getCell(0, "AUTH_WEIXIN_ID").toString();
		rv.addValue("open_id", open_id);

		return true;
	}

	/**
	 * 检查红包状态
	 * @return
	 * @throws Exception
	 */
	private boolean checkRedPackage() throws Exception {
		// 检查活动是否存在
		String sql = "SELECT * FROM [DB]WX_REDPACK_MAIN WHERE SUP_ID=@g_sup_id AND RED_UID=@red_uid";
		sql = this.replaceDatabaseName(sql);

		tbRed = DTTable.getJdbcTable(sql, "", rv);
		if (tbRed.getCount() == 0) {
			this.setError("指定的活动不存在[" + rv.s("red_uid") + "]");
			return false;
		}

		String red_status = tbRed.getCell(0, "red_status").toString();
		if (red_status == null || !red_status.equals("COM_YES")) {
			this.setError("此活动还未设定为开始状态:) ");
			return false;
		}

		Object redStart = tbRed.getCell(0, "RED_START").getValue();
		Object redEnd = tbRed.getCell(0, "RED_END").getValue();

		if (redStart == null || redEnd == null) {
			this.setError("开始和结束时间不明确");
			return false;
		}

		Date dtStart = (Date) redStart;
		Date dtEnd = (Date) redEnd;

		if (dtStart.getTime() > System.currentTimeMillis()) {
			String sStart = Utils.getDateString(dtStart, "yyyy-MM-dd HH:mm");
			this.setError("活动将于" + sStart + "开始");
			return false;
		}

		if (dtEnd.getTime() < System.currentTimeMillis()) {
			String sEnd = Utils.getDateString(dtEnd, "yyyy-MM-dd HH:mm");
			this.setError("活动于" + sEnd + "结束");
			return false;
		}

		return true;
	}

	/**
	 * 检查是否已经领取过
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean checkGetStatus() throws Exception {
		// 检查是否已经领取
		String sqlExists = "select idx,SEND_RST from [DB]WX_REDPACK_USER where RED_UID=@red_uid and USR_UNID=@g_USR_UNID";
		sqlExists = this.replaceDatabaseName(sqlExists);

		DTTable tbExists = DTTable.getJdbcTable(sqlExists, "", rv);
		if (tbExists.getCount() > 0) {
			String sendRst = tbExists.getCell(0, "SEND_RST").toString();
			if (sendRst == null || sendRst.trim().length() == 0) {
				sendRst = "SUCCESS";// 非法参数，认为成功
			}
			if (tbExists.getCell(0, "SEND_RST").toString().toUpperCase()
					.equals("SUCCESS")) {
				obj.put("RST", false);
				this.setError("您已经领过红包了");
				return false;
			} else {
				// 领取失败
				// // 已经领取的ID，自增变量，也用于订单顺序号
				redpack_user_idx = tbExists.getCell(0, "IDX").toInt();
			}
		} else {
			StringBuilder sb = new StringBuilder();
			// 生成一条新记录 @open_id 在checkUser中生成
			sb.append("INSERT INTO [DB]WX_REDPACK_USER(RED_UID, USR_UNID, AUTH_WEIXIN_ID"
					+ ", SEND_MONEY, SEND_NO, SEND_TIME, SEND_RST, SEND_IP)\n");
			sb.append("VALUES( @RED_UID, @G_USR_UNID, @OPEN_ID, 0"
					+ ", @SEND_NO, GETDATE(), 'NEW', @userip)");

			DataConnection cnn = new DataConnection(rv);
			cnn.executeUpdate(this.replaceDatabaseName(sb.toString()));

			String sqlIdx = "select idx from [DB]WX_REDPACK_USER where RED_UID=@RED_UID and USR_UNID=@g_USR_UNID";
			sqlIdx = this.replaceDatabaseName(sqlIdx);

			DTTable tbIdx = DTTable.getJdbcTable(sqlIdx, cnn);
			cnn.close();

			redpack_user_idx = tbIdx.getCell(0, 0).toInt();
		}
		return true;
	}

	/**
	 * 获取随机金额（单位：分）
	 * 
	 * @return
	 * @throws Exception
	 */
	private int createRandomMoney() throws Exception {

		// 活动设定的总金额
		double total = tbRed.getCell(0, "RED_TOTAL").toDouble();

		// 获取已经发放的总额
		String sqlTotal = "SELECT SUM(SEND_MONEY) TOTAL FROM [DB]WX_REDPACK_USER WHERE RED_UID=@RED_UID AND SEND_RST='SUCCESS'";
		sqlTotal = this.replaceDatabaseName(sqlTotal);

		DTTable tbRedTotal = DTTable.getJdbcTable(sqlTotal, "", rv);
		double totalSend = 0;
		if (tbRedTotal.getCell(0, 0).getValue() != null) {
			totalSend = tbRedTotal.getCell(0, 0).toDouble();
		}

		if (totalSend - total >= 0) {
			this.setError("活动结束了:<");
			return -1;
		}

		double redMax = tbRed.getCell(0, "RED_MONEY_MAX").toDouble();
		double redMin = tbRed.getCell(0, "RED_MONEY_MIN").toDouble();

		if (redMin < 1) { // 微信规定，最小值要求大于1元
			redMin = 1;
		}
		if (redMax > 100) {// 微信规定，最大值不超过100元
			redMax = 2;
		}
		if (redMax < redMin) { // 如果最大值小余最小值，则交换
			double tmp = redMin;
			redMin = redMax;
			redMax = tmp;
		}

		// 生成随机金额
		double sendMoney = Math.random() * redMax;
		if (sendMoney < redMin) {
			sendMoney = redMin;
		}

		// 如果已发送+本次发送>规定的总额度，
		if (totalSend + sendMoney > total) {
			// 发送值=可用额度
			sendMoney = total - totalSend;
		}

		if (sendMoney > redMax || sendMoney < redMin) {
			sendMoney = redMin;
		}
		// 活动金额转化为分，微信要求
		String sSendMoney = sendMoney * 100 + "";
		sSendMoney = sSendMoney.split("\\.")[0];
		int isendMoney = Integer.parseInt(sSendMoney);

		// 活动金额, 单位元
		sendMoney = isendMoney / 100.0;

		rv.addValue("SEND_MONEY", sendMoney);

		return isendMoney;
	}

	private void setError(String err) {
		try {
			obj.put("RST", false);
			obj.put("ERR", err);
		} catch (Exception e1) {

		}
	}

	/**
	 * 发送微信红包
	 * 
	 * @param g_USR_UNID
	 *            用户的Usr_unid
	 * @param red_uid
	 *            红包编号
	 * @param userip
	 *            用户ip地址
	 * @return
	 * @throws JSONException
	 */
	public JSONObject sendRedPackage(String g_USR_UNID, String red_uid,
			String userip) throws JSONException {

		// 微信红包
		if (red_uid == null || red_uid.trim().length() == 0) {
			obj.put("RST", false);
			obj.put("ERR", "活动还未开始:)");
			return obj;
		}

		rv.addValue("g_USR_UNID", g_USR_UNID);
		rv.addValue("G_SUP_ID", this.weixin_.getSupId());
		rv.addValue("WX_CFG_NO", this.weixin_.getWxCfgNo());
		rv.addValue("RED_UID", red_uid);
		rv.addValue("userip", userip);

		int isendMoney = -1;
		boolean isok;
		try {
			isok = this.checkUser();
			if (!isok) {
				return obj;
			}
			isok = this.checkRedPackage();
			if (!isok) {
				return obj;
			}
			isok = this.checkGetStatus();
			if (!isok) {
				return obj;
			}
			isendMoney = this.createRandomMoney();
			if (isendMoney <= 0) {
				return obj;
			}

		} catch (Exception err1) {
			obj.put("RST", false);
			obj.put("ERR", err1.getMessage());
			return obj;
		} finally {
		}

		String client_ip = "127.0.0.1"; // 发送红包服务器地址
		DataConnection cnn = null; // 因为发红包延时严重，因此数据库连接必须在发送后连接，否则会造成数据库连接池不够
		try {
			String wishing = tbRed.getCell(0, "RED_WISHING").toString();
			String act_name = tbRed.getCell(0, "RED_name").toString();
			String remark = tbRed.getCell(0, "RED_remark").toString();
			String nick_name = tbRed.getCell(0, "RED_nick_name").toString();
			String send_name = tbRed.getCell(0, "RED_send_name").toString();

			String open_id = rv.s("open_id"); // 在check_user中创建

			// 发送微信红包
			WeiXinRedPackageResult result = this.weixin_.getWeiXinCfg()
					.sendRegPackage(this.weixin_.getSupWeiXinShopId(), open_id,
							isendMoney, redpack_user_idx, wishing, client_ip,
							act_name, remark, nick_name, send_name);

			String return_code = result.getReturnCode();
			String bill_no = result.getMchBillno();
			String return_xml = result.getXml();

			rv.addValue("return_code", return_code);
			rv.addValue("bill_no", bill_no);
			rv.addValue("return_xml", return_xml);

			// 记录执行结果
			StringBuilder builder = new StringBuilder();
			// @SEND_MONEY 在 createRandomMoney创建单位元
			builder.append("UPDATE [DB]WX_REDPACK_USER SET SEND_RST=@return_code, SEND_IP=@userip ");
			builder.append(" ,RETRY_INC=isnull(RETRY_INC,0)+1, SEND_MONEY=@SEND_MONEY ");
			builder.append(" ,SEND_TIME=getdate(), RETURN_XML=@return_xml, SEND_NO=@bill_no where idx=");
			builder.append(redpack_user_idx);

			// 因为发红包延时严重，因此数据库连接必须在发送后连接，否则会造成数据库连接池不够
			cnn = new DataConnection(rv);
			cnn.executeUpdate(this.replaceDatabaseName(builder.toString()));
			if (result.getReturnCode().equals("SUCCESS")) {
				obj.put("RST", true);
				obj.put("MSG", "恭喜您您获得红包，稍后微信通知您，点击关闭");
			} else {
				this.setError(result.getReturnMsg());
			}
			return obj;
		} catch (Exception err) {
			rv.addValue("Exception", err.toString());
			String sqlResult = "UPDATE [DB]WX_REDPACK_USER SET SEND_RST='SYSERROR', SEND_TIME=getdate(),RETRY_INC=isnull(RETRY_INC,0)+1, RETURN_XML=@Exception , SEND_NO='?' where idx="
					+ redpack_user_idx;

			sqlResult = this.replaceDatabaseName(sqlResult);

			// 因为发红包延时严重，因此数据库连接必须在发送后连接，否则会造成数据库连接池不够
			if (cnn == null) {
				cnn = new DataConnection(rv);
			}
			cnn.executeUpdate(sqlResult);
			this.setError(err.getMessage());
			return obj;
		} finally {
			if (cnn != null) {
				cnn.close();
			}
		}
	}
}
