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

public interface JwtService {
	/**
	 * issue a jwt token with given claim
	 * @param claim
	 * @return jwt token
	 */
	String issueToken(JwtClaim claim);
	
	/**
	 * verified the token
	 * @param token
	 * @return true when token is issuer by us. Otherwise return false
	 */
	boolean isValidToken(String token);
	
	/**
	 * get the JWT Claim
	 * @param token
	 * @return Jwt claim when token is valid. Otherwise, return null
	 */
	JwtClaim getJwtClaim(String token ) throws InvalidJwtToken;
	
	JwtClaim constructDefaultJwtClaim(String sub);
}

