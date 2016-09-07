package com.leoman.carrental.controller;

import com.leoman.bussend.entity.BusSend;
import com.leoman.bussend.service.BusSendService;
import com.leoman.carrental.entity.CarRental;
import com.leoman.carrental.service.CarRentalService;
import com.leoman.carrental.service.impl.CarRentalServiceImpl;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.common.service.Query;
import com.leoman.user.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/7.
 */
@Controller
@RequestMapping(value = "/admin/carRental")
public class CarRentalController extends GenericEntityController<CarRental,CarRental,CarRentalServiceImpl> {

    @Autowired
    private CarRentalService carRentalService;
    @Autowired
    private BusSendService busSendService;

    @RequestMapping(value = "/index")
    public String index(){
        return "carrental/list";
    }

    /**
     * 列表
     * @param draw
     * @param start
     * @param length
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(Integer draw, Integer start, Integer length, CarRental carRental) {
        int pagenum = getPageNum(start, length);
        Query query = Query.forClass(CarRental.class, carRentalService);
        query.setPagenum(pagenum);
        query.setPagesize(length);
        Page<CarRental> page = carRentalService.queryPage(query);
        return DataTableFactory.fitting(draw, page);
    }

    @RequestMapping(value = "/detail")
    public String detail(Long id, Model model){
        model.addAttribute("carRental",carRentalService.queryByPK(id));
        List<BusSend> list =  busSendService.findRental(id);
        model.addAttribute("busSend",list);
        return "carrental/detail";
    }

}
