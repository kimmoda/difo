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
package com.zion.morphia.entity;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.PrePersist;
import org.mongodb.morphia.annotations.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
public abstract class BaseEntity implements Serializable {

	@Id
	@JsonIgnore
	private ObjectId entityId;

	/**
	 * We'll only provide getters for these attributes, setting is done in
	 * 
	 * @PrePersist.
	 */
	private Date creationDate;

	private Date lastChange;
	
	private Date publishAt;

	/**
	 * No getters and setters required, the version is handled internally.
	 */
	@Version
	@JsonIgnore
	private long version;

	private boolean enabled = true;

	public BaseEntity() {
		super();
	}

	public ObjectId getEntityId() {
		return entityId;
	}

	public void setEntityId(ObjectId entityId) {
		this.entityId = entityId;
	}

	public String getId() {
		return getEntityId() == null ? null : getEntityId().toString();
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getLastChange() {
		return lastChange;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getPublishAt() {
		return publishAt;
	}

	public void setPublishAt(Date publishAt) {
		this.publishAt = publishAt;
	}

	@PrePersist
	public void prePersist() {
		this.creationDate = (creationDate == null) ? new Date() : creationDate;
		this.lastChange = (lastChange == null) ? creationDate : new Date();
	}

	@Override
	public abstract String toString();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((entityId == null) ? 0 : entityId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		BaseEntity other = (BaseEntity) obj;
		if (entityId == null) {
			if (other.entityId != null) {
				return false;
			}
		} else if (!entityId.equals(other.entityId)) {
			return false;
		}

		return true;
	}
}

