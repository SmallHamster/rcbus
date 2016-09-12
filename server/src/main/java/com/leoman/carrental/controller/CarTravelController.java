package com.leoman.carrental.controller;

import com.leoman.carrental.entity.CarRental;
import com.leoman.carrental.entity.CarTravel;
import com.leoman.carrental.service.CarTravelService;
import com.leoman.carrental.service.impl.CarTravelServiceImpl;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.common.service.Query;
import com.leoman.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by Administrator on 2016/9/12.
 */
@Controller
@RequestMapping(value = "/admin/carTravel")
public class CarTravelController extends GenericEntityController<CarTravel,CarTravel,CarTravelServiceImpl> {

    @Autowired
    private CarTravelService carTravelService;

    @RequestMapping(value = "/index")
    public String index(){
        return "/cartravel/list";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(Integer draw, Integer start, Integer length, String Dstart, String Dend,CarTravel carTravel) throws ParseException {
        int pagenum = getPageNum(start, length);
        Query query = Query.forClass(CarTravel.class, carTravelService);
        query.setPagenum(pagenum);
        query.setPagesize(length);

        if(carTravel!=null){
            if(StringUtils.isNotBlank(carTravel.getUserName())){
                query.like("userName",carTravel.getUserName());
            }
            if(StringUtils.isNotBlank(carTravel.getTravelName())){
                query.like("travelName",carTravel.getTravelName());
            }
        }

        if(StringUtils.isNotBlank(Dstart)){
            query.ge("travelTime",DateUtils.stringToLong(Dstart,"yyyy-MM-dd"));
        }
        if( StringUtils.isNotBlank(Dend)){
            query.le("travelTime",DateUtils.stringToLong(Dend,"yyyy-MM-dd"));
        }

        Page<CarTravel> page = carTravelService.queryPage(query);
        return DataTableFactory.fitting(draw, page);
    }


}
