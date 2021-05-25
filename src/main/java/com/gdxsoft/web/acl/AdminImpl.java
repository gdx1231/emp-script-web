/**
 *
 */
package com.gdxsoft.web.acl;

import com.gdxsoft.easyweb.acl.IAcl;
import com.gdxsoft.easyweb.script.RequestValue;

/**
 * @author Administrator
 *
 */
public class AdminImpl extends AclBase implements IAcl {
    /**
     * Check whether the user is logged in
     *
     * @param rv the RequestValue
     * @return yes or no
     */
    public static boolean isLogined(RequestValue rv) {
        int iAdmId = AclBase.getId(rv, "G_ADM_ID");
        int iSupId = AclBase.getId(rv, "G_SUP_ID");

        if (iAdmId < 0 || iSupId < 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean canRun() {
        RequestValue rv = super.getRequestValue();
        boolean isLogined = isLogined(rv);
        if (!isLogined) {
            String loginUrl = rv.s("EWA.CP") + "/login";
            loginUrl = Login.gotoLogin(rv, loginUrl);
            super.setGoToUrl(loginUrl);
            return false;
        } else {
            return true;
        }
    }


}
