package com.gdxsoft.api;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.web.dao.ApiMain;
import com.gdxsoft.web.dao.ApiMainDao;
import com.gdxsoft.web.dao.SupMain;
import com.gdxsoft.web.dao.SupMainDao;

public class Auth {
	private static String DEF_DatabaseName;

	/**
	 * 默认的数据库名称
	 * 
	 * @param defDatabaseName
	 */
	public static void setDefDatabaseName(String defDatabaseName) {
		DEF_DatabaseName = defDatabaseName;
	}

	/**
	 * 默认的数据库名称
	 * 
	 * @return 数据库名称
	 */
	public static String getDefDatabaseName() {
		return DEF_DatabaseName;
	}

	private static String DEF_ConnectName; // ewa_conf -> database

	/**
	 * 默认的数据库连接池名称
	 * 
	 * @return
	 */
	public static String getDefConnectName() {
		return DEF_ConnectName;
	}

	/**
	 * 默认的数据库连接池名称
	 * 
	 * @param defConnectName 据库连接池名称
	 */
	public static void setDefConnectName(String defConnectName) {
		DEF_ConnectName = defConnectName;
	}

	private String errorMessage;
	private SupMain supMain;
	private ApiMain apiMain;
	private DecodedJWT decodedJWT;
	private String jwtToken;

	private String databaseName;
	private String connectName;

	private long endTime;

	public Auth() {
		this.databaseName = DEF_DatabaseName;
		this.connectName = DEF_ConnectName;
	}

	/**
	 * Create a supply token (7200s)
	 * 
	 * @param supId
	 * @return
	 */
	public boolean createJwtToken(int supId) {
		long defLife = 7200; // 默认两个小时
		return this.createJwtToken(supId, defLife);
	}

	private void setDaoDefaults(IClassDao<?> d1) {
		if (StringUtils.isNotBlank(connectName)) {
			d1.setConfigName(this.connectName);
		}
		if (StringUtils.isNotBlank(databaseName)) {
			d1.setDatabase(databaseName);
		}
	}

	/**
	 * Create a supply token
	 * 
	 * @param supId
	 * @param lifeSeconds
	 * @return
	 */
	public boolean createJwtToken(int supId, long lifeSeconds) {
		RequestValue rv1 = new RequestValue();
		rv1.addOrUpdateValue("sup_id", supId);

		SupMainDao d1 = new SupMainDao();
		this.setDaoDefaults(d1);
		d1.setRv(rv1);

		ArrayList<SupMain> al1 = d1.getRecords("sup_id = @sup_id");
		if (al1.size() == 0) {
			errorMessage = "NO supmain info with sup_id. (" + supId + ")";
			return false;
		}

		this.supMain = al1.get(0);
		if ("DEL".equalsIgnoreCase(this.supMain.getSupState())) {
			errorMessage = "The supmain has deleted";
			return false;
		}

		ApiMainDao d = new ApiMainDao();
		this.setDaoDefaults(d);

		rv1.addOrUpdateValue("sup_unid", this.supMain.getSupUnid());
		d.setRv(rv1);

		ArrayList<ApiMain> al = d.getRecords(" sup_unid=@sup_unid ");
		if (al.size() == 0) {
			errorMessage = "The sup NOT register api. (" + this.supMain.getSupName() + ")";
			return false;
		}
		this.apiMain = al.get(0);
		String apiKey = this.apiMain.getApiKey();
		String apiSecret = this.apiMain.getApiSignCode();

		try {
			jwtToken = JwtUtils.createJwtToken(apiKey, apiSecret, lifeSeconds);
			// 结束时间早10秒
			endTime = System.currentTimeMillis() + (lifeSeconds - 10) * 1000;
			return true;
		} catch (IllegalArgumentException e) {
			errorMessage = e.getMessage();
			return false;
		} catch (UnsupportedEncodingException e) {
			errorMessage = e.getMessage();
			return false;
		}
	}

	/**
	 * Valid the token
	 * 
	 * @param token
	 * @return true/false
	 */
	public boolean validJwtToken(String token) {
		this.jwtToken = token;
		try {
			decodedJWT = JWT.decode(token);
		} catch (Exception e) {
			this.errorMessage = e.getMessage();
			return false;
		}

		String apiKey = decodedJWT.getClaim("apiKey").asString();
		Date expire = decodedJWT.getExpiresAt();

		// 过期检查
		long diff = expire.getTime() - System.currentTimeMillis();
		if (diff < 0) {
			this.errorMessage = "Expired. (" + (diff * -1) + "ms)";
			return false;
		}

		if (!this.loadApiAndSup(apiKey)) {
			return false;
		}

		String secret = apiMain.getApiSignCode();
		Algorithm algorithm;
		try {
			algorithm = Algorithm.HMAC256(secret);
			algorithm.verify(decodedJWT);

			return true;
		} catch (Exception e) {
			this.errorMessage = e.getMessage();
		}
		return false;

	}

	private boolean loadApiAndSup(String apiKey) {
		ApiMainDao d = new ApiMainDao();
		this.setDaoDefaults(d);

		RequestValue rv1 = new RequestValue();
		rv1.addOrUpdateValue("API_KEY", apiKey);

		d.setRv(rv1);
		ArrayList<ApiMain> al = d.getRecords(" API_KEY=@API_KEY ");
		if (al.size() == 0) {
			errorMessage = "Invalid apiKey : " + apiKey;
			return false;
		}
		this.apiMain = al.get(0);

		// 还未开始
		if (this.apiMain.getApiFrom().getTime() > System.currentTimeMillis()) {
			errorMessage = "Start time is not yet";
			return false;
		}
		// 过期
		if (this.apiMain.getApiTo().getTime() < System.currentTimeMillis()) {
			errorMessage = "Expried";
			return false;
		}

		rv1.addOrUpdateValue("sup_unid", apiMain.getSupUnid());

		SupMainDao d1 = new SupMainDao();
		this.setDaoDefaults(d1);
		d1.setRv(rv1);

		ArrayList<SupMain> al1 = d1.getRecords("sup_unid=@sup_unid");
		if (al1.size() == 0) {
			errorMessage = "NO supmain info with apikey";
			return false;
		}

		this.supMain = al1.get(0);
		if ("DEL".equalsIgnoreCase(this.supMain.getSupState())) {
			errorMessage = "The supmain has deleted";
			return false;
		}

		return true;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public SupMain getSupMain() {
		return supMain;
	}

	public ApiMain getApiMain() {
		return apiMain;
	}

	public DecodedJWT getDecodedJWT() {
		return decodedJWT;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * 指定查询的数据库名称
	 * 
	 * @param databaseName
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getConnectName() {
		return connectName;
	}

	public void setConnectName(String connectName) {
		this.connectName = connectName;
	}

}
