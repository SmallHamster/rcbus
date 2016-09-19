package com.leoman.wechat.carrental.controller;

import com.leoman.bus.service.CarTypeService;
import com.leoman.carrental.entity.CarRental;
import com.leoman.carrental.entity.vo.CarRentalVo;
import com.leoman.carrental.service.CarRentalService;
import com.leoman.carrental.service.impl.CarRentalServiceImpl;
import com.leoman.city.entity.City;
import com.leoman.city.service.CityService;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.system.banner.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String line(Long carTypeId,HttpServletRequest request,Model model,Integer index,String city){
        HttpSession session = request.getSession();
        CarRentalVo carRentalVo = new CarRentalVo();
        if(index!=null){
            if(carTypeId != null){
                carRentalVo.setCarTypeId(carTypeId);
            }
            session.setAttribute("carRentalVo",carRentalVo);
            model.addAttribute("carRentalVo",carRentalVo);
        }else {
            carRentalVo = (CarRentalVo) session.getAttribute("carRentalVo");
            carRentalVo.setCity(city);
            model.addAttribute("carRentalVo",carRentalVo);
        }
        return "wechat/input_line";
    }

    /**
     * 保存
     * @param city
     * @param from
     * @param to
     * @param stype
     * @param time1
     * @param time2
     * @param number
     * @param amount
     * @param ticket
     * @param title
     * @param linkman
     * @param mobile
     * @param carTypeId
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Result save(String city,String from,String to,Integer stype,String time1,String time2, Integer number,Integer amount,Integer ticket,String title,String linkman,String mobile,Long carTypeId){
        try{
            carRentalService.save(city,from,to,stype,time1,time2,number,amount,ticket,title,linkman,mobile,carTypeId);
        }catch (Exception e){
            e.printStackTrace();
            return Result.failure();
        }
            return Result.success();
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

    /**
     * 保存页面信息
     * @param request
     * @param from
     * @param to
     * @param stype
     * @param time1
     * @param time2
     * @param number
     * @param amount
     * @param ticket
     * @param title
     * @param linkman
     * @param mobile
     * @param carTypeId
     * @return
     */
    @RequestMapping(value = "/citySession")
    @ResponseBody
    public Result citySession(HttpServletRequest request,String from,String to,Integer stype,String time1,String time2, Integer number,Integer amount,Integer ticket,String title,String linkman,String mobile,Long carTypeId){
        try{
            HttpSession session = request.getSession();
            CarRentalVo carRentalVo = new CarRentalVo();
            carRentalVo.setFrom(from);
            carRentalVo.setTo(to);
            carRentalVo.setStype(stype);
            carRentalVo.setTime1(time1);
            carRentalVo.setTime2(time2);
            carRentalVo.setNumber(number);
            carRentalVo.setAmount(amount);
            carRentalVo.setTicket(ticket);
            carRentalVo.setTitle(title);
            carRentalVo.setLinkm(linkman);
            carRentalVo.setMobile(mobile);
            carRentalVo.setCarTypeId(carTypeId);
            session.setAttribute("carRentalVo",carRentalVo);
        }catch (Exception e){
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }

}
