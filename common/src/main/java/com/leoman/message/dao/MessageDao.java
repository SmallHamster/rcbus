package com.leoman.message.dao;

import com.leoman.common.dao.IBaseJpaRepository;
import com.leoman.message.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface MessageDao extends IBaseJpaRepository<Message> {

    @Query("select a from Message a where a.toObject like '%0%' or a.toObject like ?1")
    public Page<Message> pageByUserType(String type, Pageable pageable);
}
