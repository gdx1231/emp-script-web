package com.gdxsoft.web.user;

import org.json.JSONObject;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.Utils;

public class ValidBase {
	public static final String FP_UNID = "FP_UNID";
	public static final String FP_VALIDCODE = "FP_VALIDCODE";
	/**
	 * web_user 自动登录使用的 FP_TYPE,
	 */
	public static final String VALID_TYPE_WEB_USER_LOGIN = "WEIXIN_LOGIN";

	/**
	 * 短信登录使用的 FP_TYPE
	 */
	public static final String VALID_TYPE_USER_LOGIN = "SMS_WEB_USER_LOGIN";

	/**
	 * 管理员 登录验证 <br>
	 */
	public static final String VALID_TYPE_ADM_LOGIN = "B2B_ADM_LOGIN";

	/**
	 * 微信管理员登录<br>
	 */
	public static final String VALID_TYPE_ADM_WX_LOGIN = "WX_ADM_LOGIN";

	RequestValue rv_;

	public ValidBase(RequestValue rv) {
		rv_ = rv;
	}

	/**
	 * 获取随机数字
	 * 
	 * @param codeLength 长度(6-20)
	 * @return
	 */
	public String randomNumberCode(int codeLength) {
		if (codeLength <= 0) {
			codeLength = 6;
		}
		if (codeLength > 20) {
			codeLength = 20;
		}
		String r1 = Math.random() + "" + Math.random();
		r1 = r1.replace("0.", "");
		return r1.substring(0, codeLength);
	}

	/**
	 * 获取随机数字和字符
	 * 
	 * @param codeLength 长度(6-20)
	 * @return
	 */
	public String randomAlphaCode(int codeLength) {
		if (codeLength <= 0) {
			codeLength = 6;
		}
		if (codeLength > 20) {
			codeLength = 20;
		}

		String r1 = Utils.randomStr(codeLength);
		return r1;
	}

	/**
	 * 创建验证记录, 返回（USR_ID, FP_TYPE, FP_UNID, FP_IP, FP_AGENT, FP_LOG）
	 * 
	 * @param usrId
	 * @param validCode      验证码（最多20字符）
	 * @param validType      验证类型（最多20字符）
	 * @param maxWaitMinitus 最大等待分钟 (1分钟到365天)
	 * @param log
	 * @return
	 */
	public JSONObject createValidRecord(long usrId, String validCode, String validType, long maxWaitMinitus,
			String log) {
		JSONObject rst = this.createValidRecord(usrId, validCode, validType, maxWaitMinitus, log, null, null);

		return rst;
	}

	/**
	 * 创建验证记录, 返回（USR_ID, FP_TYPE, FP_UNID, FP_IP, FP_AGENT, FP_LOG）
	 * 
	 * @param usrId
	 * @param validCode      验证码（最多20字符）
	 * @param validType      验证类型（最多20字符）
	 * @param maxWaitMinitus 最大等待分钟 (1分钟到365天)
	 * @param log
	 * @param refTable       来源表
	 * @param refId          来源id
	 * @return
	 */
	public JSONObject createValidRecord(long usrId, String validCode, String validType, long maxWaitMinitus, String log,
			String refTable, String refId) {
		JSONObject rst = new JSONObject();

		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO WEB_USER_FPWD(FP_UNID, USR_ID, FP_CDATE, FP_EDATE\n");
		sb.append("  , FP_VALIDCODE, FP_TYPE, FP_INC, FP_LOG, FP_IP, FP_AGENT, FP_REF_TABLE, FP_REF_ID)\n");
		sb.append("VALUES(@SYS_UNID, @USR_ID, @SYS_DATE, @FP_EDATE\n");
		sb.append("  , @FP_VALIDCODE, @FP_TYPE, 0, @FP_LOG, @FP_IP, @FP_AGENT, @FP_REF_TABLE, @FP_REF_ID)");

		long one_year = 365 * 24 * 60;
		if (maxWaitMinitus <= 0) {
			maxWaitMinitus = 30; // 30分钟
		} else if (maxWaitMinitus > one_year) { // 一天
			maxWaitMinitus = one_year;
		}
		// 结束时间
		java.util.Date end_date = new java.util.Date(System.currentTimeMillis() + maxWaitMinitus * 1000 * 60);

		RequestValue rv = new RequestValue();
		rv.addValue("USR_ID", usrId);
		rst.put("USR_ID", usrId);

		rv.addValue("FP_VALIDCODE", validCode);

		rv.addValue("FP_TYPE", validType);
		rst.put("FP_TYPE", validType);

		rv.addValue("FP_LOG", log);
		rv.addValue("FP_EDATE", end_date, "Date", 200);

		rv.addValue("FP_REF_TABLE", refTable);
		rv.addValue("FP_REF_ID", refId);
		if (rv_ != null) {
			rv.addValue("FP_IP", rv_.s("SYS_REMOTEIP"));
			rst.put("FP_AGENT", rv_.s("SYS_REMOTEIP"));

			String agent = rv_.s("SYS_USER_AGENT");
			if (agent != null) {
				rst.put("FP_AGENT", agent);
				if (agent.length() > 150) {
					agent = agent.substring(0, 150);
				}
				rv.addValue("FP_AGENT", agent);

			}
		}

		DataConnection.updateAndClose(sb, "", rv);
		String fpUnid = rv.s("sys_unid");
		rst.put("FP_UNID", fpUnid);

		DTTable tb = this.getValidRecord(fpUnid, validType);
		if (tb.getCount() == 0) {
			rst.put("RST", false);
			rst.put("ERR", "创建验证记录失败");

			return rst;
		}

		rst.put("RST", true);

		return rst;
	}

	/**
	 * 验证编码
	 * 
	 * @param fp_unid
	 * @param fp_type
	 * @param valid_code
	 * @param maxTry
	 * @return
	 */
	public JSONObject checkValidCode(String fpUnid, String validType, String validCode, int maxTry) {
		JSONObject rst = new JSONObject();
		if (fpUnid == null) {
			rst.put("RST", false);
			rst.put("ERR", "非法FPUNID");
			return rst;
		}
		if (validType == null) {
			rst.put("RST", false);
			rst.put("ERR", "非法FPTYPE");
			return rst;
		}
		if (validCode == null) {
			rst.put("RST", false);
			rst.put("ERR", "非法验证码");
			return rst;
		}

		DTTable tb = this.getValidRecord(fpUnid, validType);

		if (tb.getCount() == 0) {
			rst.put("RST", false);
			rst.put("ERR", "无效记录FPUNID");

			return rst;
		}

		rst = tb.getRow(0).toJson();

		String code = null;
		int valid_inc = 0; // 已经验证次数
		long endTime = 0; // 验证截至时间
		long ctime = 0;
		try {
			code = tb.getCell(0, "FP_VALIDCODE").toString();
			if (!tb.getCell(0, "FP_INC").isNull()) {
				valid_inc = tb.getCell(0, "FP_INC").toInt();
			}
			endTime = tb.getCell(0, "FP_EDATE").toTime();
			ctime = tb.getCell(0, "FP_CDATE").toTime();
		} catch (Exception e) {
			rst.put("RST", false);
			rst.put("ERR", e.getMessage());

			return rst;
		}
		boolean is_over_time = false;
		long current_time = System.currentTimeMillis();
		if (ctime == endTime) { // 创建记录时候，创建时间和结束时间一致，则限制在24销售内
			if (current_time > ctime + 24 * 60 * 1000) {
				is_over_time = true;
			}
		} else {
			if (current_time > endTime) {
				is_over_time = true;
			}
		}
		if (is_over_time) {
			rst.put("RST", false);
			rst.put("ERR", "验证超时");
			rst.put("ERR_CODE", "OVER_TIME");
			return rst;
		}
		if (valid_inc > maxTry) { // 超过最大验证次数
			rst.put("RST", false);
			rst.put("ERR", "验证次数超标");
			rst.put("ERR_CODE", "MAX_TRY");
			return rst;
		}
		if (code.equals(validCode)) {
			rst.put("RST", true);
		} else {
			rst.put("RST", false);
			rst.put("ERR", "验证码错误");
		}

		this.writeValidRecord(fpUnid, validType, rst.optBoolean("RST"), valid_inc + 1);
		return rst;
	}

	/**
	 * 填写记录
	 * 
	 * @param fpUnid
	 * @param validType
	 * @param validSucess 验证结果
	 * @param trys        当前验证次数
	 */
	public void writeValidRecord(String fpUnid, String validType, boolean validSucess, int trys) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE WEB_USER_FPWD SET\n");
		sb.append("   FP_UDATE = @SYS_DATE,\n");
		sb.append("   FP_INC = @FP_INC,\n");
		sb.append("   FP_RESULT = @FP_RESULT \n");
		sb.append("WHERE FP_UNID=@FP_UNID AND FP_TYPE = @FP_TYPE");

		RequestValue rv = new RequestValue();
		rv.addValue("FP_UNID", fpUnid);
		rv.addValue("FP_TYPE", validType);
		rv.addValue("FP_RESULT", validSucess ? "Y" : "N");
		rv.addValue("FP_INC", trys);

		DataConnection.updateAndClose(sb, "", rv);
	}

	/**
	 * 删除验证记录
	 * 
	 * @param fpUnid
	 * @param validType
	 */
	public void removeValidReocrd(String fpUnid, String validType) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from WEB_USER_FPWD where fp_unid='");
		sb.append(fpUnid.replace("'", "''"));
		sb.append("' and fp_type='");
		sb.append(validType.replace("'", "''"));
		sb.append("'");
		String sql = sb.toString();
		DataConnection.updateAndClose(sql, "", null);
	}

	/**
	 * 更改验证类型
	 * 
	 * @param fpUnid
	 * @param oldValidType
	 * @param newValidType
	 */
	public void changeValidRecordType(String fpUnid, String oldValidType, String newValidType) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE WEB_USER_FPWD SET fp_type='");
		sb.append(newValidType.replace("'", "''"));
		sb.append("' where fp_unid='");
		sb.append(fpUnid.replace("'", "''"));
		sb.append("' and fp_type='");
		sb.append(oldValidType.replace("'", "''"));
		sb.append("'");
		String sql = sb.toString();
		DataConnection.updateAndClose(sql, "", null);
	}

	/**
	 * 更改验证ext1 200长度
	 * 
	 * @param fpUnid
	 * @param oldValidType
	 * @param newValidType
	 */
	public void changeValidExt1(String fpUnid, String ext1) {
		StringBuilder sb = new StringBuilder();
		String val = ext1 == null ? "null" : ("'" + ext1.replace("'", "''") + "'");
		sb.append("UPDATE WEB_USER_FPWD SET FP_EXT1=");
		sb.append(val);
		sb.append(" where fp_unid='");
		sb.append(fpUnid.replace("'", "''"));
		sb.append("'");
		String sql = sb.toString();
		DataConnection.updateAndClose(sql, "", null);
	}

	/**
	 * 更改验证ext0 20长度
	 * 
	 * @param fpUnid
	 * @param oldValidType
	 * @param newValidType
	 */
	public void changeValidExt0(String fpUnid, String ext0) {
		StringBuilder sb = new StringBuilder();
		String val = ext0 == null ? "null" : ("'" + ext0.replace("'", "''") + "'");
		sb.append("UPDATE WEB_USER_FPWD SET FP_EXT0=");
		sb.append(val);
		sb.append(" where fp_unid='");
		sb.append(fpUnid.replace("'", "''"));
		sb.append("'");
		String sql = sb.toString();
		DataConnection.updateAndClose(sql, "", null);
	}

	/**
	 * 更改来源
	 * 
	 * @param fpUnid
	 * @param fpRefTable
	 * @param fpRefId
	 */
	public void changeValidRef(String fpUnid, String fpRefTable, String fpRefId) {
		StringBuilder sb = new StringBuilder();
		String val = fpRefTable == null ? "null" : ("'" + fpRefTable.replace("'", "''") + "'");
		String val1 = fpRefId == null ? "null" : ("'" + fpRefId.replace("'", "''") + "'");
		sb.append("UPDATE WEB_USER_FPWD SET FP_REF_TABLE=");
		sb.append(val);
		sb.append(", FP_REF_ID=" + val1 + " where fp_unid='");
		sb.append(fpUnid.replace("'", "''"));
		sb.append("'");
		String sql = sb.toString();
		DataConnection.updateAndClose(sql, "", null);
	}

	/**
	 * 更改用户编号
	 * 
	 * @param fpUnid
	 * @param userId
	 */
	public void changeValidUserId(String fpUnid, long userId) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE WEB_USER_FPWD SET USR_ID=");
		sb.append(userId);
		sb.append(", FP_UDATE = @sys_date where fp_unid='");
		sb.append(fpUnid.replace("'", "''"));
		sb.append("'");
		String sql = sb.toString();
		DataConnection.updateAndClose(sql, "", this.rv_);
	}

	/**
	 * 更新 log
	 * 
	 * @param fpUnid
	 * @param log
	 */
	public void changeValidLog(String fpUnid, String log) {
		StringBuilder sb = new StringBuilder();
		String val = log == null ? "null" : ("'" + log.replace("'", "''") + "'");
		sb.append("UPDATE WEB_USER_FPWD SET FP_log=");
		sb.append(val);
		sb.append(" where fp_unid='");
		sb.append(fpUnid.replace("'", "''"));
		sb.append("'");
		String sql = sb.toString();
		DataConnection.updateAndClose(sql, "", null);
	}

	/**
	 * 更改验证FP_URL 400长度
	 * 
	 * @param fpUnid
	 * @param oldValidType
	 * @param newValidType
	 */
	public void changeValidUrl(String fpUnid, String fpUrl) {
		StringBuilder sb = new StringBuilder();
		String val = fpUrl == null ? "null" : ("'" + fpUrl.replace("'", "''") + "'");
		sb.append("UPDATE WEB_USER_FPWD SET FP_URL=");
		sb.append(val);
		sb.append(" where fp_unid='");
		sb.append(fpUnid.replace("'", "''"));
		sb.append("'");
		String sql = sb.toString();
		DataConnection.updateAndClose(sql, "", null);
	}

	/**
	 * 获取验证记录
	 * 
	 * @param fpUnid    记录编号
	 * @param validType 类型
	 * @return
	 */
	public DTTable getValidRecord(String fpUnid, String validType) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from WEB_USER_FPWD where fp_unid='");
		sb.append(fpUnid.replace("'", "''"));
		sb.append("' and fp_type='");
		sb.append(validType.replace("'", "''"));
		sb.append("'");
		String sql = sb.toString();

		DTTable tb = DTTable.getJdbcTable(sql);

		return tb;
	}

	/**
	 * 获取验证记录
	 * 
	 * @param userId    用户编号
	 * @param validCode 验证码
	 * @param validType 类型
	 * @return
	 */
	public DTTable getValidRecord(long userId, String validCode, String validType) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from WEB_USER_FPWD where FP_VALIDCODE='");
		sb.append(validCode.replace("'", "''"));
		sb.append("' and fp_type='");
		sb.append(validType.replace("'", "''"));
		sb.append("' and usr_id=" + userId);
		String sql = sb.toString();

		DTTable tb = DTTable.getJdbcTable(sql);

		return tb;
	}

	/**
	 * 获取验证记录
	 * 
	 * @param validCode 验证码
	 * @param validType 类型
	 * @return
	 */
	public DTTable getValidRecords(String validCode, String validType) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from WEB_USER_FPWD where FP_VALIDCODE='");
		sb.append(validCode.replace("'", "''"));
		sb.append("' and fp_type='");
		sb.append(validType.replace("'", "''"));
		sb.append("' order by FP_CDATE desc");
		String sql = sb.toString();

		DTTable tb = DTTable.getJdbcTable(sql);

		return tb;
	}
}
