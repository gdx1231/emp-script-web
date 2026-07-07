package com.gdxsoft.api;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.json.JSONObject;

public class JwtUtils {

	/**
	 * Create a JWK token
	 *
	 * @param apiKey
	 * @param secret
	 * @param expireInSeconds
	 * @return
	 * @throws IllegalArgumentException
	 * @throws UnsupportedEncodingException
	 */
	public static String createJwtToken(String apiKey, String secret, long expireInSeconds)
			throws IllegalArgumentException, UnsupportedEncodingException {
		return createJwtToken(apiKey, secret, expireInSeconds, null);
	}

	/**
	 * Create a JWT token with extra payload claims
	 *
	 * @param apiKey
	 * @param secret
	 * @param expireInSeconds
	 * @param payload 额外的 claims（如 sup_id, cht_usr_id 等）
	 * @return
	 * @throws IllegalArgumentException
	 * @throws UnsupportedEncodingException
	 */
	public static String createJwtToken(String apiKey, String secret, long expireInSeconds, JSONObject payload)
			throws IllegalArgumentException, UnsupportedEncodingException {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		Date signTime = new Date();
		Date expireAt = new Date(signTime.getTime() + expireInSeconds * 1000);
		Builder builder = JWT.create().withIssuedAt(signTime).withExpiresAt(expireAt)
				.withClaim("apiKey", apiKey);

		if (payload != null) {
			Iterator<String> keys = payload.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				Object val = payload.get(key);
				if (val instanceof String) {
					builder.withClaim(key, (String) val);
				} else if (val instanceof Integer) {
					builder.withClaim(key, (Integer) val);
				} else if (val instanceof Long) {
					builder.withClaim(key, (Long) val);
				} else if (val instanceof Boolean) {
					builder.withClaim(key, (Boolean) val);
				} else if (val instanceof Double) {
					builder.withClaim(key, (Double) val);
				}
			}
		}

		return builder.sign(algorithm);
	}

	/**
	 * Verify the JWT token
	 *
	 * @param token
	 * @param secret
	 * @return
	 * @throws Exception
	 */
	public static DecodedJWT verifyJwtToken(String token, String secret) throws Exception {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		DecodedJWT jwt = JWT.decode(token);
		algorithm.verify(jwt);

		return jwt;
	}

}