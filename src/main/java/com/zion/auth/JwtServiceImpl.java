/*************************************************************************
 * 
 * Forward Thinking CONFIDENTIAL
 * __________________
 * 
 *  2013 - 2017 Forward Thinking Ltd
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Forward Thinking Ltd and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Forward Thinking Ltd
 * and its suppliers and may be covered by New Zealand and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Forward Thinking Ltd.
 */
package com.zion.auth;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zion.common.AppConfig;

public class JwtServiceImpl implements JwtService {

	@Override
	public String issueToken(JwtClaim claim) {
		try {
		    Algorithm algorithm = Algorithm.HMAC256(AppConfig.getInstance().getJwtSecreteKey());
		    return JWT.create()
		    	.withClaim("iss", claim.getIss())
		    	.withAudience(claim.getAud())
		        .withClaim("sub", claim.getSub())
		        .withClaim("iat", claim.getIat())
		        .withClaim("jti", claim.getJti())
		        .sign(algorithm);
		} catch (UnsupportedEncodingException exception){
			throw new RuntimeException(exception);
		} catch (JWTCreationException exception){
			throw new RuntimeException(exception);
		}
	}

	@Override
	public boolean isValidToken(String token) {
		try {
			this.getJwtClaim(token);
			return true;
		}catch (InvalidJwtToken exception){
		    return false;
		}
	}

	@Override
	public JwtClaim getJwtClaim(String token) throws InvalidJwtToken {
		try {
		    Algorithm algorithm = Algorithm.HMAC256(AppConfig.getInstance().getJwtSecreteKey());
		    JWTVerifier verifier = JWT.require(algorithm).build();
		    DecodedJWT jwt = verifier.verify(token);
		    JwtClaim jwtClaim = new JwtClaim();
		    Map<String, Claim> claims = jwt.getClaims();
		    jwtClaim.setAud(jwt.getAudience().toArray(new String[]{}));
			jwtClaim.setIss(claims.get("iss").asString());
			jwtClaim.setSub(claims.get("sub").asString());
			jwtClaim.setIat(claims.get("iat").asDate());
			jwtClaim.setJti(claims.get("jti").asString());
			return jwtClaim;
		} catch (UnsupportedEncodingException exception){
			throw new RuntimeException(exception);
		}catch (JWTVerificationException exception){
		    throw new InvalidJwtToken("JWT token is not valid");
		}
	}

	@Override
	public JwtClaim constructDefaultJwtClaim(String sub) {
		JwtClaim claim = new JwtClaim();
		AppConfig config = AppConfig.getInstance();
		claim.setAud(new String[] {config.getJwtAud()});
		claim.setIss(config.getJwtIss());
		claim.setSub(sub);
		claim.setJti(UUID.randomUUID().toString());
		claim.setIat(new Date());
		return claim;
	}

}

