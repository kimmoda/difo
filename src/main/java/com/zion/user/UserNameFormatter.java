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
package com.zion.user;

import org.apache.commons.lang3.StringUtils;

public class UserNameFormatter {
	public static String formatUserName(Person person) {
		String defaultDisplayName = "Anonymous";
		String displayName = "";
		if (person == null){
			return defaultDisplayName;
		}

		if (StringUtils.isNotBlank(person.getPreferName())){
			return person.getPreferName();
		}

		if (StringUtils.isNotBlank(person.getFirstName())){
			displayName += person.getFirstName();
		}
		if (StringUtils.isNotBlank(person.getMiddleName())){
			displayName += " ";
			displayName += person.getMiddleName();
		}
		displayName = displayName.trim();
		if (StringUtils.isNotBlank(person.getLastName())){
			displayName += " ";
			displayName += person.getLastName();
		}
		displayName = displayName.trim();
		if (StringUtils.isNotBlank(displayName)){
			return displayName;
		}else {
			return defaultDisplayName;
		}
	}
}

