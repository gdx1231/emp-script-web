
import org.junit.jupiter.api.Test;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.gdxsoft.api.JwtUtils;

public class TestJWT {

	public static void main(String... args) {

		TestJWT t = new TestJWT();
		t.test();

	}

	@Test
	public void test() {
		this.test1();
	}

	public void test1() {
		String apiKey = "uHplhnprYwMh5V3vsw15s4LptSWQmLWYzmedUy4rXDCOlXWjk4";

		String secret = "PmHV5sG1JB6ZbPJhox9kYIdOfavFc3yuhbLqt4tUeROeXiC1LeFD2nu8rzA915PZva0rS0T3tKbQ79RoKdTKwTQV8Or4yLTh5CJqSJvKxrXq1O0jXrTo3urxRsp6NjWD";
		try {
			String token = JwtUtils.createJwtToken(apiKey, secret, 100000);
			System.out.println(token);

			DecodedJWT jwt = JwtUtils.verifyJwtToken(token, secret);

			System.out.println(jwt.getAlgorithm());
			System.out.println(jwt.getHeader());
			System.out.println(jwt.getPayload());
			jwt.getClaims().forEach((k, v) -> {
				System.out.print(k);
				System.out.print("=");

				System.out.println(v.asString());
			});

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
