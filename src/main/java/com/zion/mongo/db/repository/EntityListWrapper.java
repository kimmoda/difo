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
package com.zion.mongo.db.repository;


import com.zion.morphia.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class EntityListWrapper<E extends BaseEntity> {
	private List<E> entities = new ArrayList<>();
	private Long nextPageToken;
	public List<E> getEntities() {
		return entities;
	}
	public void setEntities(List<E> entities) {
		this.entities = entities;
	}
	public Long getNextPageToken() {
		return nextPageToken;
	}
	public void setNextPageToken(Long nextPageToken) {
		this.nextPageToken = nextPageToken;
	}
}

