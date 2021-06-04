package com.gdxsoft.api;

import java.util.ArrayList;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.web.dao.ApiMain;
import com.gdxsoft.web.dao.ApiMainDao;
import com.gdxsoft.web.dao.SupMain;
import com.gdxsoft.web.dao.SupMainDao;

public class Auth {
	private String errorMessage;
	private SupMain supMain;
	private ApiMain apiMain;

	public boolean validJwtToken(String token) {
		DecodedJWT jwt = null;
		try {
			jwt = JWT.decode(token);
		} catch (Exception e) {
			this.errorMessage = e.getMessage();
			return false;
		}

		String apiKey = jwt.getClaim("apiKey").asString();

		if (!this.loadApiAndSup(apiKey)) {
			return false;
		}

		String secret = apiMain.getApiSignCode();
		Algorithm algorithm;
		try {
			algorithm = Algorithm.HMAC256(secret);
			algorithm.verify(jwt);

			return true;
		} catch (Exception e) {
			this.errorMessage = e.getMessage();
		}
		return false;

	}

	private boolean loadApiAndSup(String apiKey) {
		ApiMainDao d = new ApiMainDao();
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

		SupMainDao d1 = new SupMainDao();
		rv1.addOrUpdateValue("sup_unid", apiMain.getSupUnid());
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

}
