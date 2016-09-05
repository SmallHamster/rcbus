package com.leoman.utils;

import com.leoman.entity.BaseEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class EntityListener {

	@PrePersist
	public void prePersist(BaseEntity entity) {
		entity.setCreateDate(System.currentTimeMillis());
		entity.setUpdateDate(System.currentTimeMillis());
	}
	
	@PreUpdate
	public void preUpdate(BaseEntity entity) {
		entity.setUpdateDate(System.currentTimeMillis());
	}
}
