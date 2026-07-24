package com.gdxsoft.web.log;

import java.util.ArrayList;
import java.util.List;

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
  log_ua varchar(2000) CHARACTER SET latin1  COMMENT 'useragent'
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
	 * 写入数据库的细节长度，默认4k，设为0则不限制，和表 log_detail.det_description对应
	 * 
	 * @deprecated Use DETAIL_MAX_SIZE_ATOM
	 */
	public static int DETAIL_MAX_SIZE = LogBase.getDetailMaxSize(); // 兼容旧代码

	private static final String SQL_MAIN;
	private static final String SQL_DETAIL;
	static {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO _ewa_log_main(LOG_DES, LOG_MSG, LOG_TIME, LOG_IP");
		sb.append(" , LOG_XMLNAME, LOG_ITEMNAME, LOG_RUNTIME");
		sb.append(" , adm_id, sup_id, LOG_QUERIES");
		sb.append(" , LOG_ACTION, LOG_URL, LOG_REFERER, log_ua");
		sb.append(")VALUES (");
		sb.append("   @__tmp_LOG_DES, @__tmp_LOG_MSG, @sys_date, @__tmp_LOG_IP");
		sb.append(" , @__tmp_LOG_XMLNAME, @__tmp_LOG_ITEMNAME, @__tmp_LOG_RUNTIME");
		sb.append(" , @__tmp_g_adm_id, @__tmp_g_sup_id, @__tmp_LOG_QUERIES ");
		sb.append(" , @__tmp_LOG_ACTION, @__tmp_LOG_URL, @__tmp_LOG_REFERER, @__tmp_log_ua");
		sb.append(")");
		SQL_MAIN = sb.toString();

		StringBuilder sb1 = new StringBuilder();
		sb1.append("INSERT INTO _ewa_log_detail (log_id, det_inc, det_run_ms");
		sb1.append(" , det_total_ms, det_event, det_description) VALUES");
		SQL_DETAIL = sb1.toString();
	}

	/**
	 * 设置写入数据库的细节无限制长度，根据数据库的限制来，例如：
	 * <ul>
	 * <li>SqlServer的NVARCHAR(max)是2GB，NTEXT是1GB</li>
	 * <li>MySQL的TEXT是64k，MEDIUMTEXT是16MB，LONGTEXT是4GB</li>
	 * <li>Oracle的CLOB是128TB</li>
	 * <li>PostgreSQL的TEXT是1GB</li>
	 * </ul>
	 */
	public static void setDetailUnlimitSize() {
		LogBase.setDetailUnlimitSize();
	}

	public static void setDetailMaxSize(int size) {
		LogBase.setDetailMaxSize(size);
	}

	public static void setConnConfigName(String name) {
		LogBase.setConnConfigName(name);
	}

	public static String getConnConfigName() {
		return LogBase.getConnConfigName();
	}

	/**
	 * 设置日志写入线程池的核心线程数，运行时动态生效。
	 *
	 * @param size 核心线程数
	 */
	public static void setExecutorCorePoolSize(int size) {
		LogBase.setExecutorCorePoolSize(size);
	}

	/**
	 * 获取日志写入线程池的核心线程数。
	 *
	 * @return 核心线程数
	 */
	public static int getExecutorCorePoolSize() {
		return LogBase.getDetailMaxSize();
	}

	/**
	 * 设置日志写入任务队列容量。
	 * <p>
	 * 注意：队列容量在 {@code EXECUTOR} 初始化时固定，运行时修改仅更新原子值， 不影响已创建的
	 * {@code LinkedBlockingQueue} 的实际容量。
	 *
	 * @param capacity 队列容量
	 */
	public static void setExecutorQueueCapacity(int capacity) {
		LogBase.setExecutorQueueCapacity(capacity);
	}

	/**
	 * 获取日志写入任务队列容量（配置值）。
	 *
	 * @return 队列容量配置值
	 */
	public static int getExecutorQueueCapacity() {
		return LogBase.getExecutorQueueCapacity();
	}

	@Override
	public void write() {
		Log log = super.getLog();
		String logMsg = log.getMsg();
		if (logMsg == null  ) {
			return;
		}

		// 在请求线程中提取必要数据，避免持有整个 HtmlCreator 引用导致内存占用过高
		final String description = log.getDescription();
		final String ip = log.getIp();
		final String xmlName = UserConfig.filterXmlNameByJdbc(log.getXmlName());
		final String itemName = log.getItemName();
		final Long runTime = log.getRunTime();
		final String actionName = log.getActionName();
		final String url = log.getUrl();
		final String refererUrl = log.getRefererUrl();
		final String queryAll = super.getCreator().getRequestValue().s("EWA_QUERY_ALL");
		final String admId = super.getCreator().getRequestValue().s("g_adm_id");
		final String supId = super.getCreator().getRequestValue().s("g_sup_id");
		final String userAgent = super.getCreator().getRequestValue().s(RequestValue.SYS_USER_AGENT);

		// 提取 DebugFrames 数据到数组（只保存必要字段，不持有对象引用）
		DebugFrames frames = super.getCreator().getDebugFrames();
		final long startTime = frames.getCurrentTime();
		final int frameCount = frames.size();
		final long[] frameTimes = new long[frameCount];
		final String[] frameEvents = new String[frameCount];
		final String[] frameDescriptions = new String[frameCount];
		for (int i = 0; i < frameCount; i++) {
			DebugFrame df = frames.get(i);
			frameTimes[i] = df.getCurrentTime();
			frameEvents[i] = df.getEventName();
			frameDescriptions[i] = df.getDesscription();
		}
		final int maxSize = LogBase.getDetailMaxSize();
		EXECUTOR.execute(() -> {
			RequestValue rv = new RequestValue();
			rv.addValueByTruncate("__tmp_LOG_QUERIES", queryAll, 2000);
			rv.addValueByTruncate("__tmp_LOG_DES", description, 200);
			rv.addValueByTruncate("__tmp_LOG_MSG", logMsg, 4096);
			rv.addValue("__tmp_LOG_IP", ip);
			rv.addValueByTruncate("__tmp_LOG_XMLNAME", xmlName, 200);
			rv.addValueByTruncate("__tmp_LOG_ITEMNAME", itemName, 200);
			rv.addValue("__tmp_LOG_RUNTIME", runTime);
			rv.addValueByTruncate("__tmp_LOG_ACTION", actionName, 233);
			rv.addValueByTruncate("__tmp_LOG_URL", url, 1500);
			rv.addValueByTruncate("__tmp_LOG_REFERER", refererUrl, 2000);
			rv.addOrUpdateValue("__tmp_g_adm_id", admId);
			rv.addOrUpdateValue("__tmp_g_sup_id", supId);
			rv.addValueByTruncate("__tmp_log_ua", userAgent, 2000);

			DataConnection cnn = new DataConnection(LogBase.getConnConfigName(), rv);
			
			long prevTime = startTime;
			try {
				List<String> sqls = new ArrayList<>();
				for (int i = 0; i < frameCount; i++) {
					StringBuilder sb2 = new StringBuilder();
					sb2.append(",").append(i);

					long runMs = frameTimes[i] - prevTime;
					prevTime = frameTimes[i];
					sb2.append(",").append(runMs);

					long totalMs = frameTimes[i] - startTime;
					sb2.append(",").append(totalMs);

					sb2.append(",").append(cnn.sqlParameterStringExp(frameEvents[i]));

					String des = frameDescriptions[i];

					if (maxSize > 0 && des.length() > maxSize) {
						des = des.substring(0, maxSize);
					}

					sb2.append(",").append(cnn.sqlParameterStringExp(des));
					sb2.append(")");
					sqls.add(sb2.toString());
				}

				this.writeToLog(cnn, sqls);
			} catch (Exception err) {
				LOGGER.error(err.getMessage());
			} finally {
				cnn.close();
			}
		});
	}

	private void writeToLog(DataConnection cnn, List<String> sqls) {
		Object autoInc = cnn.executeUpdateReturnAutoIncrementObject(SQL_MAIN);
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