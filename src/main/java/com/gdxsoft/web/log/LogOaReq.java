package com.gdxsoft.web.log;

import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.web.dao.LogMain;

public class LogOaReq extends LogBase {

	public LogOaReq(RequestValue rv) {
		super(rv);
		super.logSrc_ = "OA_REQ";
		super.logSrcId0_ = "REQ_ID";
		super.logSrcId1_ = "";
	}

	/**
	 * 创建一条记录
	 * 
	 * @param content 内容
	 * @param lang    语言 （zhcn/enus）
	 * @return 日志
	 */
	public LogMain log(String content, String lang) {
		LogMain l = new LogMain();

		// 设置日志的 默认参数 G_ADM_ID, G_SUP_ID, 来源, ip, 时间...
		super.setCommonParas(l);
		// 语言
		l.setLogLang(lang);
		// 内容
		String content1 = super.replaceParameters(content);
		l.setLogMemo(content1);

		// super.add(l);

		return l;
	}

}
