package com.zion.mongo.db.repository;

import com.zion.common.QueryCriteria;
import com.zion.morphia.entity.BaseEntity;

import java.util.List;

import org.bson.types.ObjectId;

public interface GenericRepository {
	
	<E extends BaseEntity> E persist(E entity);
	
	<E extends BaseEntity> E update(E entity);
	
	<E extends BaseEntity> E delete(E entity);

	<E extends BaseEntity> long count(Class<E> clazz);

	<E extends BaseEntity> E get(Class<E> clazz, ObjectId id);
	
	<E extends BaseEntity> List<E> get(Class<E> clazz, List<ObjectId> ids);
	
	<E extends BaseEntity> E getEnabled(Class<E> clazz, ObjectId id);
	
	<E extends BaseEntity> E enableEntity(Class<E> clazz, final ObjectId id);
	
	<E extends BaseEntity> E disableEntity(Class<E> clazz, final ObjectId id);
	
	<E extends BaseEntity> EntityListWrapper<E> search(QueryCriteria<E> criteria);
}
