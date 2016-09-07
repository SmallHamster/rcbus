package com.leoman.bussend.dao;

import com.leoman.bussend.entity.BusSend;
import com.leoman.common.dao.IBaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface BusSendDao extends IBaseJpaRepository<BusSend>{

    @Query("SELECT a FROM BusSend a WHERE a.contactId=?1 AND a.type='2' ")
    public List<BusSend> findRental(Long id);

}
