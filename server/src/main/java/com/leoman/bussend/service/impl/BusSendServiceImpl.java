package com.leoman.bussend.service.impl;

import com.leoman.bussend.dao.BusSendDao;
import com.leoman.bussend.entity.BusSend;
import com.leoman.bussend.service.BusSendService;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
@Service
public class BusSendServiceImpl extends GenericManagerImpl<BusSend,BusSendDao> implements BusSendService{

    @Autowired
    private BusSendDao busSendDao;

    @Autowired
    private EntityManagerFactory factory;

    @Override
    public List<BusSend> findBus(Long id,Integer type) {
        return busSendDao.findBus(id,type);
    }

    @Override
    public List<Long> findIds(String carNo,String driverName) {
//        return busSendDao.findIds(ids);

        EntityManager em = factory.createEntityManager();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT");
        sql.append("   *               ");
        sql.append(" FROM                         ");
        sql.append("   t_bus_send a               ");
        sql.append(" WHERE a.type = '2'           ");
        sql.append("   AND a.bus_id IN            ");
        sql.append("   (SELECT                    ");
        sql.append("     b.id                     ");
        sql.append("   FROM                       ");
        sql.append("     t_Bus b                  ");
        sql.append("   WHERE 1=1 ");
        if(StringUtils.isNotBlank(carNo)){
            sql.append("   AND b.car_no LIKE '%"+carNo+"%') ");
        }
        if(StringUtils.isNotBlank(driverName)){
            sql.append("   AND b.driver_name LIKE '%"+driverName+"%') ");
        }

        Query query = em.createNativeQuery(sql.toString(),BusSend.class);

        List<BusSend> busSends = query.getResultList();

        List<Long> ids = new ArrayList<>();
        for(BusSend busSend : busSends){
            ids.add(busSend.getContactId());
        }
        em.close();
        return ids;
    }


}
