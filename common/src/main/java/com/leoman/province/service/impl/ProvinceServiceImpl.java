package com.leoman.province.service.impl;

import com.leoman.city.dao.CityDao;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.province.dao.ProvinceDao;
import com.leoman.province.entity.Province;
import com.leoman.province.entity.vo.ProvinceVo;
import com.leoman.province.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
@Service
public class ProvinceServiceImpl extends GenericManagerImpl<Province, ProvinceDao> implements ProvinceService {

    @Autowired
    private ProvinceDao provinceDao;

    @Autowired
    private CityDao cityDao;

    @Override
    public List<Province> iFindList() {
        return provinceDao.findAll();
    }

    @Override
    public List<ProvinceVo> getProvinceList() {
        List<Province> provinceList = provinceDao.findAll();
        List<ProvinceVo> list = new ArrayList<ProvinceVo>();
        ProvinceVo provinceVo = null;

        for (Province province : provinceList) {
            provinceVo = new ProvinceVo();
            provinceVo.setId(province.getId());
            provinceVo.setName(province.getName());
            provinceVo.setCityList(cityDao.findById(province.getId()));

            list.add(provinceVo);
        }

        return list;
    }
}
