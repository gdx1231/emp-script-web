package com.gdxsoft.web.message.email;


import java.io.File;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.easyweb.utils.Mail.Addr;
import com.gdxsoft.web.dao.AdmUser;
import com.gdxsoft.web.dao.AdmUserDao;
import com.gdxsoft.web.dao.WebUser;
import com.gdxsoft.web.dao.WebUserDao;

public class MsgUtils {

	/**
	 * 从 adm_user获取邮件地址
	 * 
	 * @param admId
	 * @param isEnglish
	 * @return
	 */
	public static Addr getAdmAddr(int admId, boolean isEnglish) {
		AdmUserDao d = new AdmUserDao();
		AdmUser o = d.getRecord(admId);

		if (o == null) {
			return null;
		}

		String name = isEnglish ? o.getAdmNameEn() : o.getAdmName();
		if (name == null || name.trim().length() == 0) {
			name = o.getAdmName();
		}
		if (name == null || name.trim().length() == 0) {
			name = o.getAdmEmail();
		}

		Addr addr = new Addr(o.getAdmEmail(), name);

		return addr;
	}

	/**
	 * 从 web_user获取邮件地址
	 * 
	 * @param usrId
	 * @param isEnglish
	 * @return
	 */
	public static Addr getUserAddr(int usrId, boolean isEnglish) {
		WebUserDao d = new WebUserDao();
		WebUser o = d.getRecord(usrId);

		if (o == null) {
			return null;
		}
		String name = isEnglish ? o.getUsrNameEn() : o.getUsrName();
		if (name == null || name.trim().length() == 0) {
			name = o.getUsrName();
		}
		if (name == null || name.trim().length() == 0) {
			name = o.getUsrEmail();
		}

		Addr addr = new Addr(o.getUsrEmail(), name);

		return addr;
	}

	/**
	 * 替换邮件地址非法字符
	 * 
	 * @param to
	 * @return
	 */
	public static String replaceMailAddrs(String to) {
		String toaddString = to.replace("；", ";");
		toaddString = toaddString.replace("　", " ");
		toaddString = toaddString.replace("  ", " ");
		toaddString = toaddString.replace("  ", " ");
		toaddString = toaddString.replace("  ", " ");
		toaddString = toaddString.replace("  ", " ");

		toaddString = toaddString.replace(" ", ";");
		toaddString = toaddString.replace(",", ";");
		toaddString = toaddString.replace("，", ";");

		toaddString = toaddString.replace(";;", ";");
		toaddString = toaddString.replace(";;", ";");
		toaddString = toaddString.replace(";;", ";");
		toaddString = toaddString.replace(";;", ";");
		return toaddString;
	}

	/**
	 * 获取附件文件物理地址
	 * 
	 * @param atts
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<String> attachments(String atts) throws Exception {
		// 有附件发送邮件
		ArrayList<String> attFiles = new ArrayList<String>();
		if(StringUtils.isBlank(atts)) {
			return attFiles;
		}
		JSONArray attsJson = new JSONArray(atts);
		String p1 = UPath.getPATH_UPLOAD(); // 上传物理路径
		String rep_p1 = UPath.getPATH_UPLOAD_URL(); // 物理路径对应的URL
		for (int i = 0; i < attsJson.length(); i++) {
			JSONObject att = attsJson.getJSONObject(i);
			JSONObject para = new JSONObject();
			if (att.has("UP_URL")) {// upload
				File f1 = new File(att.getString("UP_URL").replace(rep_p1, p1));
				para.put("url", f1.getAbsolutePath());

				if (att.has("UP_LOCAL_NAME")) {
					para.put("name", att.getString("UP_LOCAL_NAME"));
				} else {
					para.put("name", f1.getName());
				}
				attFiles.add(para.toString());
			} else if (att.has("URL")) {// upload
				File f1 = new File(att.getString("URL").replace(rep_p1, p1));
				para.put("url", f1.getAbsolutePath());

				if (att.has("UP_LOCAL_NAME")) {
					para.put("name", att.getString("UP_LOCAL_NAME"));
				} else {
					para.put("name", f1.getName());
				}
				attFiles.add(para.toString());
			} else if (att.has("path")) {// upload
				File f1 = new File(att.getString("path").replace(rep_p1, p1));
				para.put("url", f1.getAbsolutePath());
				if (att.has("name")) {
					para.put("name", att.getString("name"));
				} else {
					para.put("name", f1.getName());
				}
				attFiles.add(para.toString());
			} else {
				throw new Exception("SENDMAIL, UnKnow Att JSON: " + att);
			}
		}

		return attFiles;
	}
}
