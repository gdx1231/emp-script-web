package com.gdxsoft.web.acl;

import com.gdxsoft.easyweb.script.PageValue;
import com.gdxsoft.easyweb.script.PageValueTag;
import com.gdxsoft.easyweb.script.RequestValue;

public class AclBase {
    public static int getId(RequestValue rv, String key) {
        PageValue pvAdmId = rv.getPageValues().getPageValue(key);
        if (pvAdmId == null) {
            return -1;
        }
        if (!(pvAdmId.getPVTag() == PageValueTag.COOKIE_ENCYRPT || pvAdmId.getPVTag() == PageValueTag.SESSION)) {
            return -1;
        }
        try {
            return Integer.parseInt(pvAdmId.getStringValue());
        } catch (Exception eee) {
            return -1;
        }
    }


    private RequestValue _RequestValue;
    private String _XmlName;
    private String _ItemName;
    private String _GoToUrl;

    
    public String getDenyMessage() {
		return null;
	}


    /*
     * (non-Javadoc)
     *
     * @see com.gdxsoft.easyweb.acl.IAcl#getGoToUrl()
     */
    public String getGoToUrl() {
        return _GoToUrl;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.gdxsoft.easyweb.acl.IAcl#getItemName()
     */
    public String getItemName() {
        return this._ItemName;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.gdxsoft.easyweb.acl.IAcl#getRequestValue()
     */
    public RequestValue getRequestValue() {
        return this._RequestValue;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.gdxsoft.easyweb.acl.IAcl#getXmlName()
     */
    public String getXmlName() {

        return _XmlName;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.gdxsoft.easyweb.acl.IAcl#setGoToUrl(java.lang.String)
     */
    public void setGoToUrl(String url) {
        _GoToUrl = url;

    }

    /*
     * (non-Javadoc)
     *
     * @see com.gdxsoft.easyweb.acl.IAcl#setItemName(java.lang.String)
     */
    public void setItemName(String itemName) {
        _XmlName = itemName;

    }

    /*
     * (non-Javadoc)
     *
     * @see com.gdxsoft.easyweb.acl.IAcl#setRequestValue(com.gdxsoft.easyweb.script. RequestValue)
     */
    public void setRequestValue(RequestValue requestValue) {
        _RequestValue = requestValue;

    }

    /*
     * (non-Javadoc)
     *
     * @see com.gdxsoft.easyweb.acl.IAcl#setXmlName(java.lang.String)
     */
    public void setXmlName(String xmlName) {
        this._XmlName = xmlName;

    }
}
