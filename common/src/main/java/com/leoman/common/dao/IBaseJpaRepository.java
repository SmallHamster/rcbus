package com.leoman.common.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public interface IBaseJpaRepository<T> extends JpaRepository<T,Serializable>,JpaSpecificationExecutor<T>{

}
