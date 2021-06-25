import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.gdxsoft.easyweb.utils.Utils;

public class TestThreads extends Thread {
	public final static Map<Long, Map<String, Boolean>> TOPIC_USERS = new ConcurrentHashMap<>();
	static int inc = 0;

	public static void main(String... args) throws InterruptedException {
		for (int i = 0; i < 100; i++) {
			TestThreads t = new TestThreads();
			t.start();
		}
		Thread.sleep(5000);
		System.out.println(Thread.currentThread().getName() + ", " + inc);

		System.out.println("TOPIC_USERS=" + TOPIC_USERS.size());
		TOPIC_USERS.forEach((k, v) -> {
			System.out.println("aaaaaaRV：" + k + "=" + v.size());
		});
	}

	@Override
	public void run() {
		inc++;
		System.out.println(Thread.currentThread().getName() + ", " + inc);
		Long rv = new java.util.Random().nextLong();

		if (!TOPIC_USERS.containsKey(rv)) {
			TOPIC_USERS.put(rv, new ConcurrentHashMap<String, Boolean>());
		}
		Map<String, Boolean> map = TOPIC_USERS.get(rv);
		map.put(Utils.getGuid(), true);
		System.out.println("RV：" + rv + "=" + map.size());
	}

}
