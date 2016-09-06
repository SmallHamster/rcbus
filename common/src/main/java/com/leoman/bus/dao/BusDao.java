package com.leoman.bus.dao;

import com.leoman.bus.entity.Bus;
import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.permissions.admin.entity.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;

/**
 * Created by Daisy on 2016/9/6.
 */
public interface BusDao extends IBaseJpaRepository<Bus> {

}
