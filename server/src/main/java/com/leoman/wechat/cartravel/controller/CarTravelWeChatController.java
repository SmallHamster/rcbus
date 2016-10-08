package com.leoman.wechat.cartravel.controller;

import com.leoman.carrental.entity.CarTravel;
import com.leoman.carrental.service.CarTravelService;
import com.leoman.carrental.service.impl.CarTravelServiceImpl;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 租车出行
 * Created by 史龙 on 2016/9/23.
 */
@Controller
@RequestMapping(value = "/wechat/cartravel")
public class CarTravelWeChatController extends GenericEntityController<CarTravel,CarTravel,CarTravelServiceImpl>{

    @Autowired
    private CarTravelService carTravelService;

    @RequestMapping(value = "/index")
    public String index(String travelName, Model model){
        model.addAttribute("travelName",travelName);
        return "wechat/car_travel";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public Result save(CarTravel carTravel,String content,String time){
        try{
            carTravel.setTravelTime(DateUtils.stringToLong(time,"yyyy-MM-dd HH:mm"));
            carTravel.setContent(content);
            carTravelService.save(carTravel);
        }catch (Exception e){
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }


}
