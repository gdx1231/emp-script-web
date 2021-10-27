package com.gdxsoft.web.site;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UObjectValue;
import com.gdxsoft.web.dao.NwsCat;
import com.gdxsoft.web.dao.NwsCatDao;
import com.gdxsoft.web.dao.NwsMain;
import com.gdxsoft.web.dao.NwsMainDao;

import java.util.ArrayList;
import java.util.List;

public class SiteNews {
	private RequestValue rv;

	private Site site;
	private NwsCat nwsCat;
	private NwsMain nwsMain;

	public SiteNews(RequestValue rv) {
		this.rv = rv;
	}

	public Site initSite(String siteUnid) {
		if (this.rv.s("new") == null) {
			this.site = Site.getInstance(siteUnid, 3600 * 1000); // 3600秒
		} else {
			UObjectValue.GLOBL_CACHED.clear();
			this.site = Site.getInstance(siteUnid, 0); // 3600秒
		}
		return this.site;
	}

	public void getNwsCatAndDocByNwsCatUnid(String nwsCatUnid, String nwsGuid) throws Exception {
		rv.addOrUpdateValue("nws_cat_Unid", nwsCatUnid);
		rv.addOrUpdateValue("site_id", this.getSite().getSiteMain().getSitId());
		String where = "nws_cat_Unid = @nws_cat_Unid and SIT_ID = @site_id";

		this.nwsCat = getNwsCat(where);

		String errcontent = "";
		if (this.nwsCat == null) {
			if (isEn()) {
				errcontent = "找不到对应的目录编号 ";
			} else {
				errcontent = "Can't found the dictory id by the cat unid";
			}
			throw new Exception(errcontent);
		}

		long catId = this.nwsCat.getNwsCatId();

		this.getNwsCatAndDocByCatId(catId, nwsGuid);

	}

	public  NwsCat getNwsCatByUnid(String nwsCatUnid){
		rv.addOrUpdateValue("nwsCatUnid", nwsCatUnid);
		String where =" nws_cat_unid= @nwsCatUnid";

		this.nwsCat = this.getNwsCat(where);
		return this.nwsCat;
	}

	private NwsCat getNwsCat(String where) {
		NwsCatDao nwsCatDao = new NwsCatDao();
		nwsCatDao.setRv(rv);
		ArrayList<NwsCat> al = nwsCatDao.getRecords(where);
		if (al.size() == 0) {
			return null;
		} else {
			return al.get(0);
		}

	}

	/**
	 * 根据 NWS_CAT_LOG获取目录对象
	 * 
	 * @param nwsCatTag
	 * @return
	 */
	public NwsCat getNwsCatByTag(String nwsCatTag) {
		rv.addOrUpdateValue("nws_cat_tag", nwsCatTag);
		rv.addOrUpdateValue("site_id", this.getSite().getSiteMain().getSitId());

		String where = "nws_cat_tag=@nws_cat_tag and SIT_ID = @site_id";

		this.nwsCat = getNwsCat(where);
		return this.nwsCat;
	}

	/**
	 * 获取文章列表和指定的文章内容
	 * 
	 * @param nwsCatTag
	 * @param nwsGuid
	 * @return
	 * @throws Exception
	 */
	public void getNwsCatAndDoc(String nwsCatTag, String nwsGuid) throws Exception {
		this.getNwsCatByTag(nwsCatTag);

		String errcontent = "";
		if (this.nwsCat == null) {
			if (isEn()) {
				errcontent = "找不到对应的目录编号 ";
			} else {
				errcontent = "Can't found the dictory id by the cat unid";
			}
			throw new Exception(errcontent);
		}
		this.getNwsCatAndDocByCatId(this.nwsCat.getNwsCatId(), nwsGuid);
	}

	/**
	 * 获取目录下的第一篇文章
	 * 
	 * @param catId
	 * @return
	 * @throws Exception
	 */
	public NwsMain getFirstDocGuidOfCatalog(long catId) throws Exception {
		// 找到第一篇文章
		List<NwsMain> al = getNwsMainList(catId, 1, 1, false);
		if (al.size() == 0) {
			this.nwsMain = null;
		} else {
			this.nwsMain = al.get(0);
		}
		return nwsMain;
	}

	/**
	 * 获取目录下的所有文章
	 * 
	 * @param catId
	 * @return
	 * @throws Exception
	 */
	public List<NwsMain> getNwsMainList(long catId, int pageSize, int currentPage, boolean mustHaveHeadPic) throws Exception {
		// 找到第一篇文章
		NwsMainDao d = new NwsMainDao();
		StringBuilder sb = new StringBuilder();
		sb.append("select a.* from nws_main a inner join nws_r_main_cat b on a.nws_id = b.nws_id where ");
		sb.append(" b.NWS_CAT_ID=");
		sb.append(catId);
		sb.append("  AND a.NWS_TAG = 'WEB_NWS_DLV'");
		if (this.isEn()) {
			sb.append(" and nws_auth1 = 'en'");
		} else {
			sb.append(" and nws_auth1 != 'en'");
		}
		if(mustHaveHeadPic){
			sb.append(" and nws_head_pic is not null");
		}
		sb.append(" order by (1 + ifnull( b.NRM_ORD, 999999)), nws_id desc");

		DTTable tb = DTTable.getJdbcTable(sb.toString(), "nws_id", pageSize, currentPage, "");
		List<NwsMain> al = d.parseFromDTTable(tb);

		return al;
	}

	/**
	 * 获取目录下的第一篇文章
	 * 
	 * @param catId
	 * @return
	 */
	public NwsMain getNwsMain(long catId, String nwsUnid) {
		// 找到第一篇文章
		NwsMainDao d = new NwsMainDao();

		String where = "NWS_ID IN (SELECT NWS_ID FROM nws_r_main_cat WHERE NWS_CAT_ID =" + catId
				+ ") AND NWS_TAG = 'WEB_NWS_DLV' ";
		where += " and NWS_GUID = '" + nwsUnid.replace("'", "").replace("\\", "") + "'";

		ArrayList<NwsMain> al = d.getRecords(where, "nws_id", 1, 1);
		if (al.size() == 0) {
			this.nwsMain = null;
		} else {
			this.nwsMain = al.get(0);
		}
		return nwsMain;
	}

	public void getNwsCatAndDocByCatId(long catId, String nwsGuid) throws Exception {
		String errcontent = "";
		if (nwsGuid == null) {
			// 找到第一篇文章
			NwsMain nwsMain = this.getFirstDocGuidOfCatalog(catId);

			if (nwsMain == null) {
				errcontent = (isEn() ? "Can't found the frist documet by the cat id" : "找不到第一篇文章") + ", nws_cat_id="
						+ catId;
				throw new Exception(errcontent);
			}
		} else {
			NwsMain nwsMain = this.getNwsMain(catId, nwsGuid);
			if (nwsMain == null) {
				errcontent = (isEn() ? "Can't found the documet by the nwsGuid" : "找不到文章") + ", nws_cat_id=" + catId;
				throw new Exception(errcontent);
			}
		}

	}

	public boolean isEn() {
		return this.rv.getLang().equals("enus");
	}

	/**
	 * @return the rv
	 */
	public RequestValue getRv() {
		return rv;
	}

	/**
	 * @param rv the rv to set
	 */
	public void setRv(RequestValue rv) {
		this.rv = rv;
	}

	public Site getSite() {
		return site;
	}

	public NwsCat getNwsCat() {
		return nwsCat;
	}

	public NwsMain getNwsMain() {
		return nwsMain;
	}
}
