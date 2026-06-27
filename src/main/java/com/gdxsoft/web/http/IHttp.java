package com.gdxsoft.web.http;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IHttp {
	 String response(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
