package com.gdxsoft.api;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.script.RequestValue;

public class AuthUser {
	private String errorMessage;
	private long userId;
	private int supId;
	private String userName;

	public boolean loadUser(String token) {
		String sql = "select * from chat_user_token where cht_token=@token";
		RequestValue rv1 = new RequestValue();
		rv1.addOrUpdateValue("token", token);

		DTTable tb = DTTable.getJdbcTable(sql, "", rv1);

		if (tb.getCount() == 0) {
			errorMessage = "Invalid token : " + token;
			return false;
		}

		try {
			long expireTime = tb.getCell(0, "cht_token_expire").toTime();
			if (expireTime < System.currentTimeMillis()) {
				errorMessage = "The token had expired";
				return false;
			}
			String sql1 = "select * from chat_user where cht_usr_id=" + tb.getCell(0, "cht_usr_id");

			DTTable tb1 = DTTable.getJdbcTable(sql1, "", rv1);

			if (tb1.getCount() == 0) {
				errorMessage = "The user not found";
				return false;
			}

			if ("DEL".equalsIgnoreCase(tb1.getCell(0, "cht_usr_status").toString())) {
				errorMessage = "The user had removed";
				return false;
			}
			this.userId = tb1.getCell(0, "cht_usr_id").toLong();
			this.supId = tb1.getCell(0, "cht_usr_sup_id").toInt();
			this.userName= tb1.getCell(0, "cht_usr_name").toString();
			
			return true;
		} catch (Exception e) {
			errorMessage = e.getLocalizedMessage();
			return false;
		}

		
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getSupId() {
		return supId;
	}

	public void setSupId(int supId) {
		this.supId = supId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
