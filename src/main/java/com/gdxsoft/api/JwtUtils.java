package com.gdxsoft.api;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

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
		Algorithm algorithm = Algorithm.HMAC256(secret);
		Date signTime = new Date();
		Date expireAt = new Date(signTime.getTime() + expireInSeconds * 1000);
		String token = JWT.create().withIssuedAt(signTime) // 生成签名的时间
				.withExpiresAt(expireAt) // 生成签名的有效期
				.withClaim("apiKey", apiKey).sign(algorithm);

		return token;
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