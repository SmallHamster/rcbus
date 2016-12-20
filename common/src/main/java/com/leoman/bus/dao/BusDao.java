package com.leoman.bus.dao;

import com.leoman.bus.entity.Bus;
import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.permissions.admin.entity.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * 班车
 * Created by Daisy on 2016/9/6.
 */
public interface BusDao extends IBaseJpaRepository<Bus> {

    @Query("select a from Bus a where a.uuid = ?1")
    public Bus findByUuid(String uuid);

    @Query("select a from Bus a where a.carNo = ?1")
    public List<Bus> findByCarNo(String carNo);

    @Query("select a from Bus a where a.carNo = ?1 and a.id != ?2")
    public List<Bus> findByCarNoAndId(String carNo, Long id);
}
