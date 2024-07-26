package com.gdxsoft.web.log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.datasource.BatchInsert;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.debug.DebugFrame;
import com.gdxsoft.easyweb.debug.DebugFrames;
import com.gdxsoft.easyweb.log.ILog;
import com.gdxsoft.easyweb.log.Log;
import com.gdxsoft.easyweb.log.LogBase;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.script.userConfig.UserConfig;

/**
 * 写日志的类
 * 
 * <pre>
CREATE TABLE _ewa_log_main (
  log_id bigint NOT NULL AUTO_INCREMENT COMMENT '日志编号',
  adm_id int COMMENT '管理员',
  sup_id int COMMENT '商户',
  log_time datetime NOT NULL COMMENT '开始时间',
  log_msg varchar(4096) COMMENT '消息',
  log_xmlname varchar(200) CHARACTER SET latin1  NOT NULL COMMENT 'xmlname',
  log_itemname varchar(200) CHARACTER SET latin1  NOT NULL COMMENT 'itemname',
  log_runtime int COMMENT '时长',
  log_action varchar(233) COMMENT 'action',
  log_ip varchar(50) CHARACTER SET latin1  COMMENT 'ip',
  log_url varchar(1500) CHARACTER SET latin1  COMMENT '网址',
  log_queries varchar(2000) CHARACTER SET latin1  COMMENT '查询参数',
  log_referer varchar(1500) CHARACTER SET latin1  COMMENT '参考',
  log_des varchar(200) COMMENT '配置说明',
  PRIMARY KEY (log_id),
  key idx__ewa_log_main_xmlname_itemname(log_xmlname, log_itemname)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;


CREATE TABLE _ewa_log_detail (
  log_id bigint NOT NULL COMMENT '日志编号',
  det_inc int NOT NULL COMMENT '顺序',
  det_run_ms int COMMENT '本次执行时长',
  det_total_ms int COMMENT '总执行时长',
  det_event varchar(200) COMMENT '事件',
  det_description varchar(4096) COMMENT '内容',
  PRIMARY KEY (log_id,det_inc)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
 * </pre>
 * 
 * @author guolei
 *
 */
public class EwaScriptLog extends LogBase implements ILog {
	private static Logger LOGGER = LoggerFactory.getLogger(EwaScriptLog.class);

	/**
	 * 写入日志数据库的连接池名称
	 */
	public static String CONN_CONFIG_NAME = "";
	/**
	 * 写入数据库的细节长度，默认4k，设为0则不限制，和表 log_detail.det_description对应
	 */
	public static int DETAIL_MAX_SIZE = 4096; //

	private static final String SQL_MAIN;
	private static final String SQL_DETAIL;
	static {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO _ewa_log_main(LOG_DES, LOG_MSG, LOG_TIME, LOG_IP");
		sb.append(" , LOG_XMLNAME, LOG_ITEMNAME, LOG_RUNTIME");
		sb.append(" , adm_id, sup_id, LOG_QUERIES");
		sb.append(" , LOG_ACTION, LOG_URL, LOG_REFERER");
		sb.append(")VALUES (");
		sb.append("   @__tmp_LOG_DES, @__tmp_LOG_MSG, @sys_date, @__tmp_LOG_IP");
		sb.append(" , @__tmp_LOG_XMLNAME, @__tmp_LOG_ITEMNAME, @__tmp_LOG_RUNTIME");
		sb.append(" , @__tmp_g_adm_id, @__tmp_g_sup_id, @__tmp_LOG_QUERIES ");
		sb.append(" , @__tmp_LOG_ACTION, @__tmp_LOG_URL, @__tmp_LOG_REFERER");
		sb.append(")");
		SQL_MAIN = sb.toString();

		StringBuilder sb1 = new StringBuilder();
		sb1.append("INSERT INTO _ewa_log_detail (log_id, det_inc, det_run_ms");
		sb1.append(" , det_total_ms, det_event, det_description) VALUES");
		SQL_DETAIL = sb1.toString();
	}

	private RequestValue rv = new RequestValue();
	private long startTime;
	private List<String> sqls;

	@Override
	public void write() {

		DataConnection cnn = new DataConnection(CONN_CONFIG_NAME, rv);
		this.preprocessingData(cnn);

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Callable<String> task = () -> {
			// 异步写入，不阻碍主线程
			try {
				this.writeToLog(cnn);
				return "OK";
			} catch (Exception err) {
				LOGGER.error(err.getMessage());
				return "ERR:" + err.getMessage();
			} finally {
				cnn.close();
			}

		};
		Future<String> future = executorService.submit(task);

		try {
			// 设置超时时间 5秒）
			String result = future.get(5, TimeUnit.SECONDS);
			LOGGER.debug(result);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			LOGGER.error(e.getLocalizedMessage());
			// 取消任务
			future.cancel(true);
		} finally {
			// 关闭ExecutorService
			executorService.shutdownNow();
			cnn.close();
		}
	}

	/**
	 * 预处理数据
	 * 
	 * @param cnn
	 */
	private void preprocessingData(DataConnection cnn) {
		Log log = super.getLog();
		String logMsg = log.getMsg();
		if (logMsg == null || logMsg.trim().length() == 0) {
			// return;
		}
		startTime = super.getCreator().getDebugFrames().getCurrentTime();

		// 查询字符串
		String EWA_QUERY_ALL = super.getCreator().getRequestValue().s("EWA_QUERY_ALL");
		rv.addValueByTruncate("__tmp_LOG_QUERIES", EWA_QUERY_ALL, 2000);

		rv.addValueByTruncate("__tmp_LOG_DES", log.getDescription(), 200);
		rv.addValueByTruncate("__tmp_LOG_MSG", logMsg, 4096);
		rv.addValue("__tmp_LOG_IP", log.getIp());

		rv.addValueByTruncate("__tmp_LOG_XMLNAME", UserConfig.filterXmlNameByJdbc(log.getXmlName()), 200);
		rv.addValueByTruncate("__tmp_LOG_ITEMNAME", log.getItemName(), 200);

		rv.addValue("__tmp_LOG_RUNTIME", log.getRunTime());

		rv.addValueByTruncate("__tmp_LOG_ACTION", log.getActionName(), 233);
		rv.addValueByTruncate("__tmp_LOG_URL", log.getUrl(), 1500);
		rv.addValueByTruncate("__tmp_LOG_REFERER", log.getRefererUrl(), 2000);

		rv.addOrUpdateValue("__tmp_g_adm_id", super.getCreator().getRequestValue().s("g_adm_id"));
		rv.addOrUpdateValue("__tmp_g_sup_id", super.getCreator().getRequestValue().s("g_sup_id"));

		long prevTime = startTime;
		sqls = new ArrayList<>();
		DebugFrames frames = super.getCreator().getDebugFrames();
		for (int i = 0; i < frames.size(); i++) {
			DebugFrame df = frames.get(i);
			StringBuilder sb2 = new StringBuilder();
			// 不包含id，后面处理
			sb2.append(",").append(i);

			long runMs = df.getCurrentTime() - prevTime;
			prevTime = df.getCurrentTime();
			sb2.append(",").append(runMs);

			long totalMs = df.getCurrentTime() - startTime;
			sb2.append(",").append(totalMs);

			sb2.append(",").append(cnn.sqlParameterStringExp(df.getEventName()));

			String des = df.getDesscription();
			// 限制细节长度
			if (DETAIL_MAX_SIZE > 0 && des.length() > DETAIL_MAX_SIZE) {
				des = des.substring(0, DETAIL_MAX_SIZE);
			}

			sb2.append(",").append(cnn.sqlParameterStringExp(des));
			sb2.append(")");
			sqls.add(sb2.toString());
		}

	}

	public void writeToLog(DataConnection cnn) {
		Object autoInc = cnn.executeUpdateReturnAutoIncrementObject(SQL_MAIN);
		cnn.close();
		List<String> sqls1 = new ArrayList<>();

		for (int i = 0; i < sqls.size(); i++) {
			// 包含id
			String sql = "(" + autoInc + sqls.get(i);
			sqls1.add(sql);
		}

		BatchInsert batchInsert = new BatchInsert(cnn, false);

		batchInsert.insertBatch(SQL_DETAIL, sqls1);
	}
}
