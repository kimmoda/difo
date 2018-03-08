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

import java.util.Date;

/**
 * Claims for issue Jwt token see: https://self-issued.info/docs/draft-ietf-oauth-json-web-token.html#rfc.section.4.1.2
 */
public class JwtClaim {

	/**
	 * Issuer
	 */
	private String iss;
	
	/**
	 * subject
	 */
	private String sub;
	
	/**
	 * audience recipients
	 */
	private String[] aud;
	
	/**
	 * Issue at
	 */
	private Date iat;
	
	/**
	 * JWT id
	 */
	private String jti;
	
	public JwtClaim() {}

	public String getIss() {
		return iss;
	}

	public void setIss(String iss) {
		this.iss = iss;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String[] getAud() {
		return aud;
	}

	public void setAud(String[] aud) {
		this.aud = aud;
	}

	public Date getIat() {
		return iat;
	}

	public void setIat(Date iat) {
		this.iat = iat;
	}

	public String getJti() {
		return jti;
	}

	public void setJti(String jti) {
		this.jti = jti;
	}
}

