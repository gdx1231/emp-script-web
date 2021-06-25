import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.gdxsoft.easyweb.websocket.*;

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
		EwaWebSocket iws = new EwaWebSocket();
		try {
			IHandleMsg a = LoadHandleMessage.getInstance("chat", iws, new JSONObject());
			System.out.println(a);
		} catch (Exception err) {
			System.out.println(err.getMessage());
		}
	}

}
