package com.gdxsoft.spider;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;

/**
 * 用于 bas_esl_main 获取学校的新闻列表
 * 
 * @author admin
 *
 */
public class Esl {
	private static Logger LOOGER = LoggerFactory.getLogger(Esl.class);

	/**
	 * 预先处理添加数据库操作sql部分
	 * 
	 * @param cfg
	 * @return
	 */
	public static void attathSqlPart(JSONObject cfg, int cat_id, String esl_unid) {
		JSONObject json_doc_sql = new JSONObject();
		json_doc_sql.put("datasource", "spider");

		// 检查文件是否存在
		if (esl_unid.equals("96690751-6e3c-411e-84cb-ff4b1c91958f")) {
			// 芳草地，消息重复
			json_doc_sql.put("check", "select nws_id from nws_main where nws_ref0='" + esl_unid
					+ "' and ((nws_subject=@title and nws_ddate = @delived_time) or nws_guid=@hash_name)");
		} else {
			json_doc_sql.put("check", "select nws_id from nws_main where nws_guid=@hash_name and nws_auth2=@url");
		}

		// 新增操作，默认已经发布,创建日期是扫描日期,发布日期按照来源算
		// relative_img_0 是文档关联图片第一张（如果有的话）。在 Spider.executeSql中创建
		StringBuilder sb = new StringBuilder();
		sb.append("insert into nws_main(NWS_TAG, adm_id, nws_subject, nws_cnt, nws_cdate, nws_ddate \n");
		sb.append("   , nws_guid, sup_id, nws_auth2, nws_auth1, NWS_CNT_TXT \n");
		sb.append("   , nws_src1,nws_ref0, nws_ref1, nws_tag0, nws_tag1, nws_head_pic) \n");
		sb.append(" values('WEB_NWS_DLV', 0, ifnull(@title, @SPIDER_TITLE), @content, @sys_date \n");
		sb.append(", ifnull(@delived_time, @SPIDER_DELIVED_DATE) \n");
		sb.append("    , @hash_name, 3, @url, @author, @content_text \n");
		sb.append("    , @source,'" + esl_unid + "', 'BAS_ESL_MAIN', @__ADD_TAG0, @__ADD_TAG1, @relative_img_0);");

		// 插入到 nws_r_main_cat 发布的新闻目录
		sb.append("\n\n\n\nINSERT INTO nws_r_main_cat(NWS_CAT_ID, NWS_ID, NRM_ORD) SELECT  ");
		sb.append(cat_id);
		sb.append(", NWS_ID, 0 from nws_main where ");

		if (esl_unid.equals("96690751-6e3c-411e-84cb-ff4b1c91958f")) {
			// 芳草地，消息重复
			sb.append("  nws_ref0='" + esl_unid
					+ "' and ((nws_subject=@title and nws_ddate = @delived_time) or nws_guid=@hash_name)");
		} else {
			sb.append("  nws_guid=@hash_name and nws_auth2=@url");
		}
		json_doc_sql.put("insert", sb.toString());

		// 更新操作
		StringBuilder sb1 = new StringBuilder();
		sb1.append("update nws_main set nws_subject= ifnull(@title, @SPIDER_TITLE)\n");
		sb1.append("   , nws_cnt		= @content\n");
		sb1.append("   , nws_ddate		= ifnull(@delived_time, @SPIDER_DELIVED_DATE) \n");
		sb1.append("   , NWS_CNT_TXT	= @context_text\n");
		sb1.append("   , nws_src1		= @source\n");
		sb1.append("   , nws_ref0		= '" + esl_unid + "'\n");
		sb1.append("   , nws_ref1		= 'BAS_ESL_MAIN' \n");
		sb1.append("   , nws_tag0		= @__ADD_TAG0 \n");
		sb1.append("   , nws_tag1		= @__ADD_TAG1 \n");
		sb1.append("where nws_id		= @nws_id ");
		json_doc_sql.put("update", sb1.toString());

		cfg.put("doc_sql", json_doc_sql);
	}

	public static void attathContentRemovesPart(JSONObject cfg, String esl_unid) {

	}

	private static void doSchoolScans(String esl_unid, int cat_id, JSONObject cfg) {
		attathSqlPart(cfg, cat_id, esl_unid);

		Spider sp = new Spider(cfg);
		try {
			sp.doScans();
		} catch (IOException e) {
			LOOGER.error(e.getMessage());
		} catch (URISyntaxException e) {
			LOOGER.error(e.getMessage());
		}
	}

	/**
	 * 更新配置状态，最后时间和清除 retry标志
	 * 
	 * @param esl_unid
	 */
	private static void updateSpiderCfgStatus(String esl_unid) {
		// 更新本次刷新的时间
		StringBuilder sb = new StringBuilder();
		sb.append("update oneworld_main_data.BAS_SPIDER_CFG set SPIDER_LAST=now()");
		sb.append("   , SPIDER_CFG_PARA0 = null \n"); // 清除retry标志
		sb.append(" where ref_table='BAS_ESL_MAIN' and ref_id='");
		sb.append(esl_unid.replace("'", "''"));
		sb.append("'");
		String sql = sb.toString();

		DataConnection.updateAndClose(sql, "spider", null);

	}

	public static void main(String[] args) throws Exception {
		String sqlCatId = "select nws_cat_id from nws_cat where NWS_CAT_TAG='SPIDER_GYAP_ORG' ";
		DTTable tbCatId = DTTable.getJdbcTable(sqlCatId, "spider");
		if (tbCatId.getCount() == 0) {
			System.out.println("Not defined NWS_CAT_TAG with value 'SPIDER_GYAP_ORG'");
			return;
		} else if (tbCatId.getCount() > 1) {
			System.out.println("The defined NWS_CAT_TAG with value 'SPIDER_GYAP_ORG' are repeates");
			return;
		}

		int cat_id = tbCatId.getCell(0, 0).toInt();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT B.ESL_UNID, B.ESL_NAME_CN, B.ESL_NAME_EN, A.*");
		sb.append(" FROM oneworld_main_data.BAS_SPIDER_CFG A");
		sb.append(" INNER JOIN BAS_ESL_MAIN B ON A.REF_ID = B.ESL_UNID");
		sb.append(" AND A.REF_TABLE='BAS_ESL_MAIN'");

		String sqlTop = sb.toString();
		DTTable tb = DTTable.getJdbcTable(sqlTop, "spider");

		long currentMillis = System.currentTimeMillis();
		double hour12 = (11.5 * 60 * 60 * 1000);
		for (int i = 0; i < tb.getCount(); i++) {
			String esl_unid = tb.getCell(i, "ESL_UNID").toString();
			long last = tb.getCell(i, "SPIDER_LAST").toTime();

			// 11.5 小时内不重新扫描
			if (currentMillis - last < hour12) {
				LOOGER.info("NOT：" + tb.getCell(i, "ESL_NAME_CN"));
				continue;
			}

			String str = tb.getCell(i, "SPIDER_CFG").toString();
			String retry = tb.getCell(i, "SPIDER_CFG_PARA0").toString();
			LOOGER.info("扫描：" + tb.getCell(i, "ESL_NAME_CN"));
			JSONObject cfg;
			try {
				cfg = new JSONObject(str);
			} catch (Exception err) {
				LOOGER.error(err.getMessage());
				continue;
			}
			// 重新获取或覆盖数据库里的文章信息
			if (retry != null && retry.toLowerCase().equals("retry")) {
				cfg.getJSONObject("doc_parts").put("retry", true);
			}
			doSchoolScans(esl_unid, cat_id, cfg);
			updateSpiderCfgStatus(esl_unid);
		}
	}

}
