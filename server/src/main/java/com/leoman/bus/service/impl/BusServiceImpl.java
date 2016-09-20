package com.leoman.bus.service.impl;

import com.leoman.bus.dao.BusDao;
import com.leoman.bus.entity.Bus;
import com.leoman.bus.service.BusService;
import com.leoman.common.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daisy on 2016/9/6.
 */
@Service
public class BusServiceImpl extends GenericManagerImpl<Bus, BusDao> implements BusService {

    @Autowired
    private BusDao busDao;

    @Override
    public Page<Bus> page(Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");
        Page<Bus> page = busDao.findAll(new Specification<Bus>() {
            @Override
            public Predicate toPredicate(Root<Bus> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate result = null;
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (predicateList.size() > 0) {
                    result = cb.and(predicateList.toArray(new Predicate[]{}));
                }

                if (result != null) {
                    query.where(result);
                }
                return query.getGroupRestriction();
            }

        }, pageRequest);

        return page;
    }

    @Override
    public Bus findByUuid(String uuid) {
        return busDao.findByUuid(uuid);
    }
}
