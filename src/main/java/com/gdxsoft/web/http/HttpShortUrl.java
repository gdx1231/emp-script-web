package com.gdxsoft.web.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.utils.UJSon;
import com.gdxsoft.web.shortUrl.ShortUrl;
import com.gdxsoft.web.shortUrl.UrlShort;

public class HttpShortUrl implements IHttp {

	private String prefix;

	public HttpShortUrl(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public String response(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestValue rv = new RequestValue(request);
		String u = rv.s("u");
		if (u == null || u.trim().length() == 0) {
			return UJSon.rstFalse("地址未传递").toString();
		}

		ShortUrl su = new ShortUrl();
		int admId = rv.getInt("g_adm_id");
		int supId = rv.getInt("g_sup_id");
		UrlShort result = su.addUrl(u, admId, supId);

		result.getUrlFull();

		JSONObject rst = UJSon.rstTrue(null);
		rst.put("su", prefix + result.getUrlUid());
		rst.put("UrlShort", result.toJSON());

		return rst.toString();

	}

}
