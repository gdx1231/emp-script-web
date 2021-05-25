

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gdxsoft.easyweb.data.DTTable;
import com.gdxsoft.easyweb.datasource.DataConnection;
import com.gdxsoft.easyweb.utils.UNet;

public class Test {

	public Test() {
	}

	public static void test0() throws Exception {
		int timeout = 100 * 1000; // 100 s

		String url = "http://www.3-3edu.com.cn/Media/News?NewsId=103096&t=636247608729889564";
		Document doc = Jsoup.connect(url).userAgent(UNet.AGENT).timeout(timeout).get();

		String jq = "div[style*='font-size:18px']";
		Elements eles = doc.select(jq);

		for (int i = 0; i < eles.size(); i++) {
			Element ele = eles.get(i);
			System.out.println(ele.outerHtml());
		}
	}

	public static void setNwsHeadPic() throws Exception {
		String sql = "SELECT * FROM nws_main WHERE NWS_REF1 = 'BAS_ESL_MAIN' AND NWS_TAG = 'WEB_NWS_DLV' "
				+ " and (nws_head_pic='' or nws_head_pic is null)";
		DTTable tb = DTTable.getJdbcTable(sql, "spider");

		for (int i = 0; i < tb.getCount(); i++) {
			String html = tb.getCell(i, "NWS_CNT").toString();
			if (html == null) {
				continue;
			}
			System.out.println(tb.getCell(i, "nws_subject"));
			Document doc = Jsoup.parse(html);
			Elements imgs = doc.select("img");
			if (imgs.size() == 0) {
				continue;
			}
			String src = imgs.get(0).attr("src");

			String sql1 = "update nws_main set nws_head_pic='" + src.replace("'", "''") + "' where nws_id="
					+ tb.getCell(i, "nws_id");

			DataConnection.updateAndClose(sql1, "spider", null);
		}

	}

	public static void main(String[] args) throws Exception {
		setNwsHeadPic();
	}
}
