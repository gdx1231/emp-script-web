package com.gdxsoft.chatRoom;

import org.apache.commons.lang3.StringUtils;

import com.gdxsoft.api.Auth;
import com.gdxsoft.api.AuthUser;
import com.gdxsoft.easyweb.acl.IAcl;
import com.gdxsoft.easyweb.acl.SampleAcl;
import com.gdxsoft.easyweb.script.restful.RestfulResult;
import com.gdxsoft.easyweb.utils.Utils;

public class EwaAcl extends SampleAcl implements IAcl {
	private RestfulResult<Void> rst;

	public boolean canRun() {
		String restful = super.getRequestValue() == null ? null : super.getRequestValue().s("ewa_restful");
		if (Utils.cvtBool(restful)) {
			return this.canRunRestful();
		} else {
			return true;
		}
	}

	public String getDenyMessage() {
		if (rst != null) {
			return rst.toJson().toString();
		} else {
			return null;
		}
	}

	public boolean canRunRestful() {
		this.rst = new RestfulResult<>();

		if (super.getRequestValue() == null || super.getRequestValue().getRequest() == null) {
			return false;
		}
		String authorization = super.getRequestValue().getRequest().getHeader("authorization");

		if (StringUtils.isBlank(authorization)) {
			// 下载图片
			String ewaAjax = super.getRequestValue().s("ewa_ajax");
			if ("download".equalsIgnoreCase(ewaAjax) || "download-inline".equalsIgnoreCase(ewaAjax)) {
				authorization = super.getRequestValue().s("authorization");
			}
		}
		if (StringUtils.isBlank(authorization)) {
			rst.setCode(124);
			rst.setSuccess(false);
			rst.setMessage("Invalid access token.");
			return false;
		}
		if (authorization.startsWith("Bearer ")) {
			// jwt
			return this.handleAuthorizationSup(authorization);
		} else {

			return this.handleAuthorizationUser(authorization);
		}

	}

	/**
	 * 加载用户的权限
	 * 
	 * @param authorization
	 * @return
	 */
	private boolean handleAuthorizationUser(String authorization) {
		AuthUser user = new AuthUser();
		if (!user.loadUser(authorization)) {
			rst.setCode(122);
			rst.setSuccess(false);
			rst.setMessage(user.getErrorMessage());
			return false;
		}

		super.getRequestValue().addOrUpdateValue("g_sup_id", user.getSupId());
		super.getRequestValue().addOrUpdateValue("g_usr_id", user.getUserId());
		super.getRequestValue().addOrUpdateValue("g_usr_name", user.getUserName());

		// 用户授权
		super.getRequestValue().addOrUpdateValue("authorization_type", "user");

		return true;
	}

	/**
	 * 加载商户的权限
	 * 
	 * @param authorization
	 * @return
	 */
	private boolean handleAuthorizationSup(String authorization) {
		String token = authorization.substring(7);
		Auth auth = new Auth();
		if (!auth.validJwtToken(token.trim())) {
			rst.setCode(122);
			rst.setSuccess(false);
			rst.setMessage(auth.getErrorMessage());
			return false;
		}

		super.getRequestValue().addOrUpdateValue("g_sup_id", auth.getSupMain().getSupId());
		super.getRequestValue().addOrUpdateValue("g_sup_unid", auth.getSupMain().getSupUnid());

		// 商户授权
		super.getRequestValue().addOrUpdateValue("authorization_type", "supply");
		return true;
	}

}
