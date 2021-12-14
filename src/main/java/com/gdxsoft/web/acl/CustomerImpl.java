/**
 *
 */
package com.gdxsoft.web.acl;

import com.gdxsoft.easyweb.acl.IAcl;
import com.gdxsoft.easyweb.script.RequestValue;

/**
 * Customer module ACL
 */
public class CustomerImpl extends AclBase implements IAcl {
	/**
	 * Check whether the user is logged in
	 *
	 * @param rv the RequestValue
	 * @return yes or no
	 */
	public static boolean isLogined(RequestValue rv) {
		long usrId = AclBase.getLongId(rv, "G_USR_ID");

		if (usrId < 0) {
			return false;
		} else {
			return true;
		}
	}

	public boolean canRun() {
		RequestValue rv = super.getRequestValue();
		boolean isLogined = isLogined(rv);
		if (!isLogined) {
			String loginUrl = rv.s("EWA.CP") + "/customer/login-or-register";
			loginUrl = Login.gotoLogin(rv, loginUrl);
			super.setGoToUrl(loginUrl);
			return false;
		} else {
			return true;
		}
	}

}
