package com.gdxsoft.web.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.web.shortUrl.ShortUrl;
import com.gdxsoft.web.shortUrl.UrlShort;

public class HttpShortUrlVerify implements IHttp {


	public HttpShortUrlVerify( ) {
	}

	@Override
	public String response(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestValue rv = new RequestValue(request);

		String uid = rv.s("uid");
		if (uid == null || uid.trim().length() == 0) {
			return "uid?";
		}
		UrlShort url = ShortUrl.getUrlShort(uid);
		if (url == null) {
			// 404
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		String u = url.getUrlFull();
		response.sendRedirect(u);
		
		return null;

	}

}
