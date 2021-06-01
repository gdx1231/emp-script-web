import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.gdxsoft.web.websocket.IHandleMsg;
import com.gdxsoft.web.websocket.IndexWebSocket;
import com.gdxsoft.web.websocket.LoadHandleMessage;

public class TestWs {

	public static void main(String... args) {

		TestWs t = new TestWs();
		t.test();

	}

	@Test
	public void test() {
		this.test1();
	}

	public void test1() {
		IndexWebSocket iws=new IndexWebSocket();
		try {
			IHandleMsg a = LoadHandleMessage.getInstance("chat", iws, new JSONObject());
			System.out.println(a);
		} catch (Exception err) {
			System.out.println(err.getMessage());
		}
	}

}
