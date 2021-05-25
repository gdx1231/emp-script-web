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
public class BackAdminImpl extends AclBase implements IAcl {
    /**
     * Check whether the user is logged in
     *
     * @param rv the RequestValue
     * @return yes or no
     */
    public static boolean isLogined(RequestValue rv) {

        int iAdmId = AclBase.getId(rv, "G_ADM_ID");
        int iSupId = AclBase.getId(rv, "G_SUP_ID");


        if (iAdmId < 0 && iSupId != 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean canRun() {
        boolean login = isLogined(super.getRequestValue());
        if (!login) {
            String loginUrl = super.getRequestValue().s("EWA.CP") + "/back_admin/login";
            loginUrl = Login.gotoLogin(super.getRequestValue(), loginUrl);
            super.setGoToUrl(loginUrl);
            return false;
        } else {
            return true;
        }
    }


}
