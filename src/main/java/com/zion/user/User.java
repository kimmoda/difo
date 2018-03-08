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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.zion.tag.Tag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonInclude(Include.NON_NULL)
public class User extends UserSummary {
	
	private boolean enabled;

	private List<UserService> service;
	
	private String registrationSocialMedias;
	
	private String requestedInfo;
	
	private Set<Tag> tags = new HashSet<>();

	@JsonIgnore
	private String password;
	
	private String walletAddress;
	
	public User() {}

	public User(String username, String password, Person person, Contact contact, List<UserService> service, boolean enabled, Set<String> userRoles, Set<String> preferredStyles) {
		this.setUsername(username);
		this.setPassword(password);
		this.setPerson(person);
		this.setContact(contact);
		this.setUserRoles(userRoles);
		this.setService(service);
		this.setPreferredStyles(preferredStyles);
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public List<UserService> getService() {
		return service;
	}

	public void setService(List<UserService> service) {
		this.service = service;
	}

    public String getRegistrationSocialMedias() {
        return registrationSocialMedias;
    }

    public void setRegistrationSocialMedias(String registrationSocialMedias) {
        this.registrationSocialMedias = registrationSocialMedias;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public String getRequestedInfo() {
        return requestedInfo;
    }

    public void setRequestedInfo(String requestedInfo) {
        this.requestedInfo = requestedInfo;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }
}

