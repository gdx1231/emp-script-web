package com.gdxsoft.web.job;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.data.DTRow;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.utils.UMail;
import com.gdxsoft.easyweb.utils.Mail.DKIMCfg;
import com.gdxsoft.easyweb.utils.Mail.DKIMSigner;
import com.gdxsoft.easyweb.utils.Mail.SmtpCfgs;

public class JobBase {
	private static Logger LOGGER = LoggerFactory.getLogger(JobBase.class);
	DataConnection _Conn;
	String _dbName;
	DTRow _RowSup;

	HashMap<String, DKIMSigner> _DKIMSignerMap = new HashMap<String, DKIMSigner>();

	/**
	 * 通过邮件获取 dkim
	 * 
	 * @param from
	 * @return
	 */
	public DKIMSigner getDKIMSignerByEmail(String from) {
		String domain = UMail.getEmailDomain(from);
		return getDKIMSigner(domain);
	}

	/**
	 * 获取邮件DKIM
	 * 
	 * @param domain 域名
	 * @param select 选择
	 * @return
	 */
	public DKIMSigner getDKIMSigner(String domain) {

		if (domain == null || domain.trim().length() == 0) {
			return null;
		}
		domain = domain.toLowerCase().trim();
		if (_DKIMSignerMap.containsKey(domain)) {
			return this._DKIMSignerMap.get(domain);
		}

		_DKIMSignerMap.put(domain, null);

		DKIMCfg cfg = SmtpCfgs.getDkim(domain);
		if (cfg == null) {
			return null;
		}
		DKIMSigner dkimSigner;
		try {
			dkimSigner = new DKIMSigner(cfg.getDomain(), cfg.getSelect(), cfg.getPrivateKeyPath());
			_DKIMSignerMap.remove(domain);
			_DKIMSignerMap.put(domain, dkimSigner);
			return dkimSigner;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	public DTRow getRowSup() {
		return _RowSup;
	}

	public void setRowSup(DTRow _RowSup) {
		this._RowSup = _RowSup;
	}

	public JobBase() {
	}

	public JobBase(DataConnection cnn, String dbName) {
		this._Conn = cnn;
		_dbName = dbName;
	}
	void log(String msg) {
		LOGGER.info(msg);
	}
}
