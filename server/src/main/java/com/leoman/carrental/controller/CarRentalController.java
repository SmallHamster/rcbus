package com.leoman.carrental.controller;

import com.leoman.bus.entity.CarType;
import com.leoman.bus.service.BusService;
import com.leoman.bus.service.CarTypeService;
import com.leoman.bussend.entity.BusSend;
import com.leoman.bussend.service.BusSendService;
import com.leoman.carrental.entity.CarRental;
import com.leoman.carrental.entity.CarRentalOffer;
import com.leoman.carrental.service.CarRentalOfferService;
import com.leoman.carrental.service.CarRentalService;
import com.leoman.carrental.service.impl.CarRentalServiceImpl;
import com.leoman.city.entity.City;
import com.leoman.city.service.CityService;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.common.service.Query;
import com.leoman.user.entity.UserInfo;
import com.leoman.utils.DateUtils;
import com.leoman.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired
    private CarRentalOfferService carRentalOfferService;
    @Autowired
    private CityService cityService;
    @Autowired
    private CarTypeService carTypeService;
    @Autowired
    private BusService busService;

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
        //租车
        model.addAttribute("carRental",carRentalService.queryByPK(id));
        //巴士
        model.addAttribute("busSend",busSendService.findBus(id,2));
        //报价
        model.addAttribute("carRentalOffer",carRentalOfferService.queryByProperty("rentalId",id));

        return "carrental/detail";
    }

    @RequestMapping(value = "/edit")
    private String edit(Long id, Model model,Integer status){
        //租车
        model.addAttribute("carRental",carRentalService.queryByPK(id));

        model.addAttribute("city",cityService.queryAll());

        model.addAttribute("carType",carTypeService.queryAll());
        //报价
        model.addAttribute("carRentalOffer",carRentalOfferService.queryByProperty("rentalId",id));
        //巴士
        model.addAttribute("busSend",busSendService.findBus(id,2));
        if(status==0){
            return "carrental/edit1";
        }else{
            return "carrental/edit2";
        }
    }

    /**
     * 保存
     * @param id
     * @param cityId
     * @param rwType
     * @param startPoint
     * @param endPoint
     * @param startDate
     * @param endDate
     * @param carTypeId
     * @param totalNumber
     * @param busNum
     * @param isInvoice
     * @param invoice
     * @param dispatch
     * @param offter_name
     * @param offter_amount
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Integer save(Long id,
                        Long cityId,
                        Integer rwType,
                        String startPoint,
                        String endPoint,
                        String startDate,
                        String endDate,
                        Long carTypeId,
                        Integer totalNumber,
                        Integer busNum,
                        Integer isInvoice,
                        String invoice,
                        String dispatch,
                        String offter_name,
                        String offter_amount){

        return carRentalService.save(id,cityId,rwType,startPoint,endPoint,startDate,endDate,carTypeId,totalNumber,busNum,isInvoice,invoice,dispatch,offter_name,offter_amount);

    }

    @RequestMapping(value = "/saveDispatch")
    @ResponseBody
    public Integer saveDispatch(Long id,String dispatch){

        return 1;

    }

}
