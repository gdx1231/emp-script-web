package com.gdxsoft.web.log;

import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.web.dao.LogMain;

public class LogOaPrj extends LogBase {

	public LogOaPrj(RequestValue rv) {
		super(rv);
		super.logSrc_ = "OA_PRJ";
		super.logSrcId0_ = "PRJ_ID";
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
