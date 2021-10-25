package com.gdxsoft.web.weixin;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.script.HtmlControl;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UDes;

/**
 * 微信与 ADM_USER的关系，绑定或验证登录 <br>
 * 关系表 WEB_USER_FPWD
 * 
 * @author 郭磊 2017-05-12
 *
 */
public class WeiXinBindAdm {

	private RequestValue rv_;
	private String wxCfgNo_; // 微信公众号
	private String roleType_; // 绑定类型 ERP/GUIDE/TEACHER ...
	private String weixinRequestHttpRoot_; // 微信调用的域名 例如"http://www.gyap.org"
	private HttpServletResponse httpResponse_;
	private int adm_id_;
	private int sup_id_;
	private String fp_unid_;
	private String fp_validcode_;
	private String fp_type_;

	/**
	 * 微信绑定ADM的绑定或登录方法
	 * 
	 * @param rv
	 *            RequestValue
	 * @param wxCfgNo
	 *            微信格致 gh_xxxxx
	 * @param roleType
	 *            绑定类型 ERP/GUIDE/TEACHER ...
	 * @param requestHttpRoot
	 *            微信调用的域名，例如 "http://www.gyap.org"要和公众号一致<br>
	 *            在微信公众平台，公众号设置->功能设置->网页授权域名设置
	 * @param response
	 *            执行登录，输出cookie
	 */
	public WeiXinBindAdm(RequestValue rv, String wxCfgNo, String roleType, String weixinRequestHttpRoot,
			HttpServletResponse response) {
		rv_ = rv;
		wxCfgNo_ = wxCfgNo;
		roleType_ = roleType;
		this.weixinRequestHttpRoot_ = weixinRequestHttpRoot;
		this.httpResponse_ = response;
	}

	/**
	 * 微信端调用
	 * 
	 * @param rv
	 * @param wxCfgNo
	 */
	public WeiXinBindAdm(RequestValue rv, String wxCfgNo) {
		rv_ = rv;
		wxCfgNo_ = wxCfgNo;
	}

	/**
	 * 微信端验证提交数据的合法性
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONObject weixinValidData() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("RST", false);

		RequestValue rv = rv_;
		String g1 = rv.s("g1");
		if (g1 == null || g1.trim().length() == 0) {
			obj.put("ERR", "参数传递错误！(G1?)");
			return obj;
		}
		// base64的符号在url里被替换为空格
		g1 = g1.replace(" ", "+");

		UDes des = new UDes();
		JSONObject objG1 = null;
		String ming = null;
		try {
			ming = des.getDesString(g1);
		} catch (Exception err) {
			obj.put("ERR", "参数传递错误！(G1 DES)");
			System.out.println(g1);
			return obj;
		}
		try {
			objG1 = new JSONObject(ming);
		} catch (Exception err) {
			obj.put("ERR", "参数传递错误！(G1 JSON)");
			System.out.println(ming);
			return obj;
		}

		this.roleType_ = "ERP";
		String AUTH_TYPE = "";
		try {
			this.fp_unid_ = objG1.getString("FP_UNID");
			this.fp_validcode_ = objG1.getString("FP_VALIDCODE");
			this.roleType_ = objG1.getString("ROLE_TYPE");
			AUTH_TYPE = objG1.getString("AUTH_TYPE");
			if (AUTH_TYPE.equals("LOGIN")) {
				this.fp_type_ = "WX_ADM_LOGIN";
			} else {
				this.fp_type_ = "WX_ADM_BIND";
			}
		} catch (Exception err) {
			obj.put("ERR", "解析参数错误！ ");
			System.out.println(objG1);
			return obj;
		}

		DTTable tbCheckFp = this.getVaildData(fp_unid_, fp_validcode_, fp_type_);

		if (tbCheckFp.getCount() == 0) {
			obj.put("ERR", "验证信息不存在" + fp_unid_ + ", " + fp_validcode_);
			System.out.println(objG1);
			return obj;
		}

		this.adm_id_ = tbCheckFp.getCell(0, "USR_ID").toInt();
		if (fp_type_.equals("WX_ADM_BIND")) {
			// 绑定时候验证管理员信息
			String sqlAdm = "select sup_id from adm_user where adm_id=" + adm_id_;
			tbCheckFp = DTTable.getJdbcTable(sqlAdm);
			if (tbCheckFp.getCount() == 0) {
				obj.put("ERR", "管理员不存在" + adm_id_);
				System.out.println("管理员不存在" + adm_id_);
				System.out.println(objG1);
				return obj;
			}

			this.sup_id_ = tbCheckFp.getCell(0, 0).toInt();
		}

		String code = rv.s("code");
		if (code == null || code.isEmpty()) {
			obj.put("ERR", "CODE参数为空！");
			return obj;
		}

		obj.put("RST", true);
		return obj;
	}

	/**
	 * 微信端执行
	 * 
	 * @param authWeiXinId
	 *            open_id
	 * @return
	 * @throws Exception
	 */
	public JSONObject weixinExecute(String authWeiXinId) throws Exception {
		String msg = "";
		JSONObject obj = new JSONObject();
		obj.put("RST", true);
		if (this.fp_type_.equals("WX_ADM_BIND")) { // 绑定
			this.createWxUserAndAdmUserRelationship(authWeiXinId);
			this.updateVaildDate(this.fp_unid_, this.fp_validcode_, this.fp_type_, null);
			msg = "关联用户完毕(BIND)";
		} else { // login
			DTTable tb = this.getWeixinAdmDataByAuthWeiXinId(authWeiXinId);
			int auth_adm_id = -1;
			if (tb.getCount() > 0) {
				auth_adm_id = tb.getCell(0, "adm_id").toInt();
				msg = "微信认证成功(LOGIN)";
			} else {
				msg = "此微信号未关联系统用户";
				obj.put("RST", false);
				obj.put("ERR", msg);
			}
			this.updateVaildDate(this.fp_unid_, this.fp_validcode_, this.fp_type_, auth_adm_id);

		}
		obj.put("MSG", msg);

		return obj;
	}

	/**
	 * 绑定 微信和 AdmUser的关系
	 * 
	 * @param authWeiXinId
	 */
	private void createWxUserAndAdmUserRelationship(String authWeiXinId) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO WX_USER_ADM_USER(WX_CFG_NO, AUTH_WEIXIN_ID");
		sb.append(", ROLE_TYPE, ADM_ID, SUP_ID, CDATE)");
		sb.append("values('");
		sb.append(this.wxCfgNo_.replace("'", "''"));
		sb.append("', '");
		sb.append(authWeiXinId.replace("'", "''"));
		sb.append("','");
		sb.append(this.roleType_.replace("'", "''"));
		sb.append("', ");
		sb.append(this.adm_id_);
		sb.append(", ");
		sb.append(this.sup_id_);
		sb.append(", @sys_date )");
		String sql = sb.toString();

		DataConnection.updateAndClose(sql, "", rv_);
	}

	private DTTable getWeixinAdmDataByAuthWeiXinId(String authWeiXinId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from wx_user_adm_user where wx_cfg_no='");
		sb.append(wxCfgNo_.replace("'", "''"));
		sb.append("' and role_type='");
		sb.append(this.roleType_.replace("'", "''"));
		sb.append("' and AUTH_WEIXIN_ID='");
		sb.append(authWeiXinId.replace("'", "''"));
		sb.append("'");
		String sql = sb.toString();
		DTTable tb = DTTable.getJdbcTable(sql);
		return tb;
	}

	/**
	 * 执行程序，外部调用
	 * 
	 * @return
	 * @throws Exception
	 */
	public JSONObject execute() throws Exception {
		JSONObject obj = new JSONObject();

		RequestValue g_rv = rv_;
		if (g_rv.s("createweixinlogin") != null) {
			// 创建登录
			obj = this.createVaildData("WX_ADM_LOGIN", -1);
			obj.put("AUTH_TYPE", "LOGIN");

			UDes des = new UDes();
			String code = des.getEncString(obj.toString());

			// 微信调用的验证URL
			String auth_url = this.getWeixinValidUrl(code);

			obj.put("CODE", code);
			obj.put("AUTH", auth_url);
		} else if (g_rv.s("weixinloginchecking") != null) {
			// 检查是否绑定成功
			DTTable tb = this.getVaildData(g_rv.s("FP_UNID"), g_rv.s("FP_VALIDCODE"), "WX_ADM_LOGIN");

			if (tb.getCount() == 0) {
				obj.put("RST", false);
				obj.put("ERR", "验证数据丢失！");
				return obj;
			}

			obj.put("RST", true);
			if (tb.getCell(0, "FP_UDATE").getValue() == null) {
				// 还未再微信上确认
				obj.put("BIND", false);
				obj.put("help", "用户还未扫描二维码并确认");
			} else {
				// 模拟登录，登录调用 WEB_USER_FPWD 数据，
				// 具体定义在 Login.Robert （|2014_rob|common|admin_users.xml）

				HtmlControl ht = new HtmlControl();
				String paras = "app=1&pop_login_main=1&ewa_action=OnPagePost&EWA_VALIDCODE_CHECK=NOT_CHECK";
				ht.init("|2014_rob|common|admin_users.xml", "Login.Robert", paras, rv_, httpResponse_);

				String s = ht.getHtml();

				obj.put("BIND", true);
				obj.put("RESPONSE", s);

				// 验证完登录删除请求数据
				this.removeValidData(g_rv.s("FP_UNID"), g_rv.s("FP_VALIDCODE"), "WX_ADM_LOGIN");
			}
		} else if (g_rv.s("bindweixin") != null) { // 发起绑定微信
			// 获取微信和ADM_USER的关系数据
			DTTable tb = this.getWeixinAdmData();

			if (tb.getCount() == 0) { // 微信未绑定
				obj = this.createVaildData("WX_ADM_BIND", rv_.getInt("G_ADM_ID"));
				obj.put("AUTH_TYPE", "BIND");
				UDes des = new UDes();
				String code = des.getEncString(obj.toString());

				// 微信验证用url
				String auth_url = this.getWeixinValidUrl(code);
				obj.put("CODE", code);
				obj.put("AUTH", auth_url);
			} else { // 已经绑定过了
				String AUTH_WEIXIN_ID = tb.getCell(0, "AUTH_WEIXIN_ID").toString();

				// 获取关联微信的WEB_USER的信息，主要是头像和名称
				DTTable tb1 = this.getWeixinUser(AUTH_WEIXIN_ID);
				obj.put("RST", false);
				obj.put("USER", tb1.toJSONArray());
			}

		} else if (g_rv.s("bindweixinchecking") != null) {// 检查是否绑定成功
			DTTable tb = this.getVaildData(g_rv.s("FP_UNID"), g_rv.s("FP_VALIDCODE"), "WX_ADM_BIND");
			if (tb.getCount() == 0) {
				obj.put("RST", false);
				obj.put("ERR", "验证数据丢失！");
				return obj;
			}

			obj.put("RST", true);
			if (tb.getCell(0, "FP_UDATE").getValue() == null) {
				obj.put("BIND", false);
			} else {
				tb = this.getWeixinAdmData();

				String AUTH_WEIXIN_ID = tb.getCell(0, "AUTH_WEIXIN_ID").toString();
				// 获取关联微信的WEB_USER的信息，主要是头像和名称
				DTTable tb1 = this.getWeixinUser(AUTH_WEIXIN_ID);

				obj.put("USER", tb1.toJSONArray());
				obj.put("BIND", true);

				// 验证完登录删除请求数据
				this.removeValidData(g_rv.s("FP_UNID"), g_rv.s("FP_VALIDCODE"), "WX_ADM_BIND");
			}
		} else if (g_rv.s("removeweixinbind") != null) { // 解除微信绑定
			this.removeBind();
			obj.put("RST", true);
		} else {
			obj.put("RST", false);
			obj.put("ERR", "未知的请求参数");

		}
		obj.put("wxCfgNo", wxCfgNo_);
		obj.put("roleType", roleType_);
		obj.put("fp_unid", fp_unid_);
		obj.put("fp_type", this.fp_type_);
		obj.put("weixinRequestHttpRoot", this.weixinRequestHttpRoot_);
		return obj;
	}

	/**
	 * 更新 验证信息的 FP_UDATE
	 * 
	 * @param fpUnid
	 * @param fpValidCode
	 * @param fpType
	 */
	private void updateVaildDate(String fpUnid, String fpValidCode, String fpType, Integer admId) {
		// 更新 fp_unid
		StringBuilder sb = new StringBuilder("update WEB_USER_FPWD set FP_UDATE=@sys_date ");
		if (admId != null) {
			sb.append(", usr_id = ");
			sb.append(admId);
		}
		sb.append(" where FP_UNID='");
		sb.append(fpUnid.replace("'", "''"));
		sb.append("' and FP_VALIDCODE='");
		sb.append(fpValidCode.replace("'", "''"));
		sb.append("' and FP_TYPE='");
		sb.append(fpType.replace("'", "''"));
		sb.append("'");
		DataConnection.updateAndClose(sb.toString(), "", rv_);
	}

	/**
	 * 删除绑定
	 */
	private void removeBind() {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from wx_user_adm_user where wx_cfg_no='");
		sb.append(wxCfgNo_.replace("'", "''"));
		sb.append("' and role_type='");
		sb.append(roleType_.replace("'", "''"));
		sb.append("' and adm_id=@g_adm_id");
		String sql = sb.toString();
		DataConnection.updateAndClose(sql, "", rv_);
	}

	/**
	 * 获取创建的微信调用URL
	 * 
	 * @param code
	 *            加密的json
	 * @return
	 * @throws Exception
	 */
	private String getWeixinValidUrl(String code) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(this.weixinRequestHttpRoot_);
		sb.append(rv_.getContextPath());
		sb.append("/app-2017/admin-auth-weixin.jsp?g1=");
		sb.append(URLEncoder.encode(code, "utf-8"));
		String auth_url = sb.toString();

		return auth_url;
	}

	/**
	 * 创建验证数据
	 **/
	private JSONObject createVaildData(String fpType, int admId) {
		JSONObject obj = new JSONObject();
		String FP_VALIDCODE = "" + Math.random() + "-" + Math.random();
		if (FP_VALIDCODE.length() > 20) {
			FP_VALIDCODE = FP_VALIDCODE.substring(0, 20);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO WEB_USER_FPWD(FP_UNID, USR_ID, FP_CDATE, FP_EDATE");
		sb.append(", FP_VALIDCODE, FP_TYPE) \n");
		sb.append("values(@sys_unid, " + admId + ", @sys_date, @sys_date,'");
		sb.append(FP_VALIDCODE);
		sb.append("', '");
		sb.append(fpType.replace("'", "''"));
		sb.append("')");
		String sql = sb.toString();
		DataConnection.updateAndClose(sql, "", rv_);

		obj.put("RST", true);
		obj.put("FP_VALIDCODE", FP_VALIDCODE);
		obj.put("FP_UNID", rv_.s("sys_unid"));
		obj.put("ROLE_TYPE", roleType_);
		return obj;
	}

	/**
	 * 移除验证数据
	 **/
	private void removeValidData(String fpUnid, String fpValidCode, String fpType) {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from WEB_USER_FPWD where FP_UNID='");
		sb.append(fpUnid.replace("'", "''"));
		sb.append("' and FP_VALIDCODE='");
		sb.append(fpValidCode.replace("'", "''"));
		sb.append("' AND FP_TYPE='");
		sb.append(fpType.replace("'", "''"));
		sb.append("' ");
		String sql = sb.toString();
		// DataConnection.updateAndClose(sql, "", null);
	}

	/**
	 * 获取验证数据
	 **/
	private DTTable getVaildData(String fpUnid, String fpValidCode, String fpType) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from WEB_USER_FPWD where FP_UNID='");
		sb.append(fpUnid.replace("'", "''"));
		sb.append("' and FP_VALIDCODE='");
		sb.append(fpValidCode.replace("'", "''"));
		sb.append("' AND FP_TYPE='");
		sb.append(fpType.replace("'", "''"));
		sb.append("' ");
		String sql = sb.toString();
		DTTable tb = DTTable.getJdbcTable(sql);
		return tb;
	}

	/**
	 * 获取微信和ADM_USER的关系数据
	 **/
	private DTTable getWeixinAdmData() {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from wx_user_adm_user where wx_cfg_no='");
		sb.append(wxCfgNo_.replace("'", "''"));
		sb.append("' and role_type='");
		sb.append(roleType_.replace("'", "''"));
		sb.append("' and adm_id=@g_adm_id");
		String sql = sb.toString();

		DTTable tb = DTTable.getJdbcTable(sql, rv_);
		return tb;
	}

	private DTTable getWeixinUser(String authWeixinId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a.USR_PIC, a.USR_NAME, b.* from web_user a ");
		sb.append(" inner join wx_user b on a.usr_unid = b.usr_unid ");
		sb.append(" where  b.wx_cfg_no='");
		sb.append(wxCfgNo_.replace("'", "''"));
		sb.append("' and b.AUTH_WEIXIN_ID='");
		sb.append(authWeixinId.replace("'", "''"));
		sb.append("'");
		String sqlUser = sb.toString();
		DTTable tb1 = DTTable.getJdbcTable(sqlUser);
		return tb1;
	}

	/**
	 * @return the rv_
	 */
	public RequestValue getRv() {
		return rv_;
	}

	/**
	 * @return the wxCfgNo_
	 */
	public String getWxCfgNo() {
		return wxCfgNo_;
	}

	/**
	 * @return the roleType_
	 */
	public String getRoleType() {
		return roleType_;
	}

	/**
	 * @return the weixinRequestHttpRoot_
	 */
	public String getWeixinRequestHttpRoot() {
		return weixinRequestHttpRoot_;
	}

	/**
	 * @return the adm_id_
	 */
	public int getAdmId() {
		return adm_id_;
	}

	/**
	 * @return the sup_id_
	 */
	public int getSupId() {
		return sup_id_;
	}

	/**
	 * @return the fp_unid_
	 */
	public String getFpUnid() {
		return fp_unid_;
	}

	/**
	 * @return the fp_validcode_
	 */
	public String getFpValidCode() {
		return fp_validcode_;
	}

	/**
	 * @return the fp_type_
	 */
	public String getFpType() {
		return fp_type_;
	}
}
