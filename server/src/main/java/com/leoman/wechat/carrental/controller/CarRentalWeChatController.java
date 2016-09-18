package com.leoman.wechat.carrental.controller;

import com.leoman.bus.service.CarTypeService;
import com.leoman.carrental.entity.CarRental;
import com.leoman.carrental.service.CarRentalService;
import com.leoman.carrental.service.impl.CarRentalServiceImpl;
import com.leoman.city.entity.City;
import com.leoman.city.service.CityService;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.system.banner.entity.Banner;
import com.leoman.system.banner.service.BannerService;
import com.leoman.utils.InitialUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 租车
 * Created by 史龙 on 2016/9/18.
 */
@Controller
@RequestMapping(value = "/wechat/carrental")
public class CarRentalWeChatController extends GenericEntityController<CarRental,CarRental,CarRentalServiceImpl> {

    @Autowired
    private CarRentalService carRentalService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private CarTypeService carTypeService;

    @Autowired
    private CityService cityService;

    /**
     * 用车预定
     * @param model
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(Model model){
        model.addAttribute("banner",bannerService.findList(2));
        model.addAttribute("carType",carTypeService.findRentalType());
        return "wechat/car_booking";
    }

    /**
     * 输入路线
     * @return
     */
    @RequestMapping(value = "/add")
    public String line(Long carTypeId,Model model){
        model.addAttribute("carTypeId",carTypeId);
        return "wechat/input_line";
    }

    /**
     * 城市选择
     * @param model
     * @return
     */
    @RequestMapping(value = "/city")
    public String city(Model model){
        Sort sort = new Sort();
        List<String> list = new ArrayList<>();
        List<City> cities = cityService.queryAll();
        for(City city : cities){
            list.add(city.getName());
        }
        Map map = sort.sort(list);
        model.addAttribute("city",map);
        return "wechat/city";
    }

//    @RequestMapping(value = "/back")
//    public Result back(String name, HttpServletRequest request){
//
//
//
//    }

}
