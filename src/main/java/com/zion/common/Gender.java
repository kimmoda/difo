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
package com.zion.common;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public enum Gender {
	MALE("Male"),
	FEMALE("Female");
	private String displayName;
	private Gender(String name) {
		this.displayName = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public static List<CodeSet> getCodeSets() {
		List<CodeSet> codeSets = new ArrayList<>();
		for(Gender en: Gender.values()) {
			codeSets.add(new CodeSet(en.name(), en.getDisplayName()));
		}
		return codeSets;
	}
	
	public static Gender sanitize(CodeSet codeSet) {
		try{
			return Gender.valueOf(codeSet.getCode());
		}catch (Exception e) {
			throw new IllegalArgumentException("cannot find gender with codeSet: " + codeSet);
		}
	}
	
	public static CodeSet getCodeSet(String code) {
		if(StringUtils.isBlank(code)) {
			return null;
		}
		try{
			Gender gender = Gender.valueOf(code);
			return new CodeSet(gender.name(), gender.getDisplayName());
		}catch (Exception e) {
			return null;
		}
	}
}

