package com.gdxsoft.web.app;

import java.util.ArrayList;
import java.util.Date;

import com.gdxsoft.web.dao.*;
import org.json.JSONObject;

import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UAes;
import com.gdxsoft.easyweb.utils.UJSon;

public class AppMini  {
	JSONObject sceneInitCfg;
	String sceneInitCfgString;
	 
	String[] scenes;
	String sceneMethod;

	JSONObject sceneResult;
	JSONObject sceneParams;

	String miniName;
	String miniNameEn;
	boolean isEn;

	App app;

	public AppMini(RequestValue rv) {
		app = new App(rv);
		sceneResult = new JSONObject();
		sceneParams = new JSONObject();
		sceneResult.put("WEB_PARAMS", sceneParams);

		this.isEn = "enus".equals(rv.getLang());
	}

	public JSONObject initScenes() {
		String ss = app.getRv().s("INIT_CFG");
		this.sceneInitCfgString = ss;
		
		if (sceneInitCfgString == null || sceneInitCfgString.trim().length() == 0) {
			sceneResult = UJSon.rstFalse("INIT_CFG参数为空");
			return sceneResult;
		}

		try {
			this.sceneInitCfg = new JSONObject(sceneInitCfgString);
		} catch (Exception err) {
			sceneResult = UJSon.rstFalse(err.getMessage());
			return sceneResult;
		}

		String scene = sceneInitCfg.optString("scene");
		if (scene == null || scene.trim().length() == 0) {
			scene = "default,sceneisblank";
		}

		// 小程序传递的场景
		this.scenes = scene.split(",");
		this.sceneMethod = scenes[0].trim(); // 模式

		this.sceneParams.put("scenemethod", this.sceneMethod);
		this.sceneResult.put("METHOD", this.sceneMethod); // 模式

		UJSon.rstSetTrue(sceneResult, null);
		return sceneResult;
	}

	public WebUserLevel checkWebUserLevel(int usrId, int supId) {
		WebUser wu = app.getWebUser(usrId);
		String usrUnid = wu.getUsrUnid();

		WebUserLevelDao d = new WebUserLevelDao();
		String whereString = "usr_id=" + usrId;
		ArrayList<WebUserLevel> al = d.getRecords(whereString);
		if (al.size() == 0) {
			WebUserLevel o = new WebUserLevel();
			o.setCreateDate(new Date());
			o.setSupId(supId);
			o.setUsrId(usrId);
			o.setUsrLvl(1);
			o.setUsrUnid(usrUnid);
			o.setUsrPid(0);
			o.setUsrPunid("");

			d.newRecord(o);
			return o;
		} else {
			return al.get(0);
		}

	}

	public JSONObject getWebUserLevel(int usrId, int supId) {
		WebUser wu = app.getWebUser(usrId);
		if (wu == null) {
			UJSon.rstSetFalse(this.sceneResult, "无效的用户信息，指定参数错误：" + this.sceneInitCfgString);
			return sceneResult;
		}

		WebUserLevel wul = this.checkWebUserLevel(usrId, supId);
		int usrLvlId = wul.getUsrLvlId();
		String usrUnid = wu.getUsrUnid();

		this.sceneParams.put("REFUSRUNID", usrUnid);
		this.sceneParams.put("REFUSRLVLID", usrLvlId);

		return sceneResult;
	}

	public JSONObject checkAgentInfo() {
		// agent_enroll代理专用报名
		int supId = -1;
		try {
			supId = Integer.parseInt(scenes[1]);
		} catch (Exception err) {
			UJSon.rstSetFalse(this.sceneResult, "指定参赛解析错误：" + this.sceneInitCfgString);
			return sceneResult;
		}

		SupMain o = app.getSupMain(supId);

		if (o == null) {
			UJSon.rstSetFalse(sceneResult, "无效的代理，指定参赛错误：" + sceneInitCfgString);
			return sceneResult;
		}
		this.sceneParams.put("AGUNID", o.getSupUnid());

		return sceneResult;
	}

	void checkExistsUserByOpenId() throws Exception {
		String openId = app.getRv().s("open_id");
		if (openId == null) {
			return;
		}

		WxUserDao d = new WxUserDao();
		String wxCfgNo = app.getRv().s("wx_cfg_no");
		if (wxCfgNo == null) {
			return;
		}
		wxCfgNo = wxCfgNo.trim().replace("'", "");
		String w = " wx_cfg_no='" + wxCfgNo + "' and AUTH_WEIXIN_ID = '" + openId + "'";
		ArrayList<WxUser> al = d.getRecords(w);
		if (al.size() == 0) {
			return;
		}
		WxUser wxUser = al.get(0);
		String usr_unid = wxUser.getUsrUnid();
		WebUser wu = app.getWebUser(usr_unid);

		if (wu == null) {
			return;
		}
		long usrId = wu.getUsrId();
		String usr_name = wu.getUsrName();

		String encode = this.createEncode(usrId);
		this.sceneParams.put("ENCODE", encode);
		this.sceneParams.put("USR_NAME", usr_name);
		this.sceneParams.put("USR_ID", usrId);

	}

	private String createEncode(long usrId) throws Exception {
		JSONObject codeJson = new JSONObject();
		// b2b 的用户编号
		codeJson.put("B2B_USR_ID", usrId);
		// 混淆加密
		codeJson.put("T", System.currentTimeMillis());
		String encode = UAes.getInstance().encode(codeJson.toString());

		return encode;
	}

	/**
	 * @return the sceneInitCfg
	 */
	public JSONObject getSceneInitCfg() {
		return sceneInitCfg;
	}

	/**
	 * @return the scenes
	 */
	public String[] getScenes() {
		return scenes;
	}

	/**
	 * @return the sceneMethod
	 */
	public String getSceneMethod() {
		return sceneMethod;
	}

	/**
	 * @return the sceneResult
	 */
	public JSONObject getSceneResult() {
		return sceneResult;
	}

	/**
	 * @return the sceneParams
	 */
	public JSONObject getSceneParams() {
		return sceneParams;
	}

	/**
	 * @return the miniName
	 */
	public String getMiniName() {
		return miniName;
	}

	/**
	 * @param miniName the miniName to set
	 */
	public void setMiniName(String miniName) {
		this.miniName = miniName;
	}

	/**
	 * @return the miniNameEn
	 */
	public String getMiniNameEn() {
		return miniNameEn;
	}

	/**
	 * @param miniNameEn the miniNameEn to set
	 */
	public void setMiniNameEn(String miniNameEn) {
		this.miniNameEn = miniNameEn;
	}

	/**
	 * @return the isEn
	 */
	public boolean isEn() {
		return isEn;
	}

	/**
	 * @param isEn the isEn to set
	 */
	public void setEn(boolean isEn) {
		this.isEn = isEn;
	}

	/**
	 * @return the sceneInitCfgString
	 */
	public String getSceneInitCfgString() {
		return sceneInitCfgString;
	}

	/**
	 * @return the app
	 */
	public App getApp() {
		return app;
	}

	/**
	 * @param app the app to set
	 */
	public void setApp(App app) {
		this.app = app;
	}
}
