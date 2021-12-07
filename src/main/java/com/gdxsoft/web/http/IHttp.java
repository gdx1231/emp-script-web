package com.gdxsoft.web.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IHttp {
	 String response(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
