package com.leoman.bus.controller;

import com.leoman.bus.entity.Bus;
import com.leoman.bus.entity.CarType;
import com.leoman.bus.service.BusService;
import com.leoman.bus.service.CarTypeService;
import com.leoman.bus.service.impl.BusServiceImpl;
import com.leoman.bus.util.GpxUtil;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.common.core.bean.Response;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.common.service.Query;
import com.leoman.entity.Configue;
import com.leoman.utils.HttpRequestUtil;
import com.leoman.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 班车
 * Created by Daisy on 2016/9/6.
 */
@Controller
@RequestMapping(value = "/admin/bus")
public class BusController extends GenericEntityController<Bus, Bus, BusServiceImpl> {

    @Autowired
    private BusService busService;

    @Autowired
    private CarTypeService carTypeService;

    /**
     * 列表页面
     */
    @RequestMapping(value = "/index")
    public String index(Model model) {
        List<CarType> typeList = carTypeService.queryAll();
        model.addAttribute("typeList",typeList);
        return "bus/bus_list";
    }

    /**
     * 获取列表
     * @param bus
     * @param draw
     * @param start
     * @param length
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(Bus bus, Integer draw, Integer start, Integer length, @RequestParam(value = "carType1" , required = false)Long carType) {
        int pageNum = getPageNum(start, length);
        Query query = Query.forClass(Bus.class, busService);
        query.setPagenum(pageNum);
        query.setPagesize(length);
        query.like("carNo",bus.getCarNo());
        query.like("modelNo",bus.getModelNo());
        query.like("driverName",bus.getDriverName());
        query.eq("carType.id",carType);
        Page<Bus> page = busService.queryPage(query);
        return DataTableFactory.fitting(draw, page);
    }

    /**
     * 添加车辆
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Long id, Model model) {
        if (id != null) {
            Bus bus = busService.queryByPK(id);
            if(bus.getImage() != null){
                bus.getImage().setPath(Configue.getUploadUrl()+bus.getImage().getPath());
            }

            model.addAttribute("bus", bus);
        }
        List<CarType> typeList = carTypeService.queryAll();
        model.addAttribute("typeList",typeList);
        return "bus/bus_add";
    }

    /**
     * 保存
     * @param bus
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(Bus bus) {
        try {
            busService.save(bus);
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    /**
     * 详情
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String info(Long id, Model model) {
        if (id != null) {
            Bus bus = busService.queryByPK(id);
            if(bus.getImage() != null){
                bus.getImage().setPath(Configue.getUploadUrl()+bus.getImage().getPath());
            }
            model.addAttribute("bus", bus);
        }
        return "bus/bus_info";
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(String ids) {
        try {
            String[] idArr = JsonUtil.json2Obj(ids, String[].class);
            for (String id:idArr) {
                if(!StringUtils.isEmpty(id)){
                    busService.deleteByPK(id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    /**
     * 获取弹出框列表
     * @param bus
     * @param busIds
     * @param inOrNot
     * @param draw
     * @param start
     * @param length
     * @return
     */
    @RequestMapping(value = "/getSelect", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getSelect(Bus bus,String busIds,String inOrNot, Integer draw, Integer start, Integer length) {
        int pageNum = getPageNum(start, length);
        Query query = Query.forClass(Bus.class, busService);
        query.setPagenum(pageNum);
        query.setPagesize(length);
        query.eq("carType.id",1);//只过滤通勤班车
        query.like("carNo",bus.getCarNo());
        query.like("modelNo",bus.getModelNo());
        query.like("driverName",bus.getDriverName());

        List list = new ArrayList();
        if(!StringUtils.isEmpty(busIds)){
            String [] busIdArr = busIds.split("\\,");
            for (String busId:busIdArr) {
                if(!StringUtils.isEmpty(busId)){
                    Long id = Long.valueOf(busId);
                    list.add(id);
                }
            }
            if("not".endsWith(inOrNot)){
                query.notIn("id", list);
            }
        }

        if("in".endsWith(inOrNot)){
            list.add(-1l);
            query.in("id", list);
        }
        Page<Bus> page = busService.queryPage(query);
        return DataTableFactory.fitting(draw, page);
    }

    /**
     * 批量导入gpx系统的所有车辆信息，存到本地数据库
     * @return
     */
    @RequestMapping(value = "/multiImport", method = RequestMethod.POST)
    @ResponseBody
    public Result multiImport() {
        List<Map> groups = GpxUtil.getGroupsBus();
        for (Map group:groups) {
            List<Map> busList = (List<Map>)group.get("vehicles");
            for (Map map:busList) {
                Bus bus = new Bus();
                bus.setUuid(String.valueOf(map.get("id")));//id
                bus.setCarNo((String) map.get("name"));//车牌号
                bus.setVkey((String) map.get("vKey"));//车辆授权码
                busService.save(bus);
            }
        }
        return Result.success();
    }

}
