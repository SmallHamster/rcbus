package com.leoman.bus.controller;

import com.leoman.bus.entity.Bus;
import com.leoman.bus.service.BusService;
import com.leoman.bus.service.impl.BusServiceImpl;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.common.service.Query;
import com.leoman.entity.Configue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 班车
 * Created by Daisy on 2016/9/6.
 */
@Controller
@RequestMapping(value = "/admin/bus")
public class BusController extends GenericEntityController<Bus, Bus, BusServiceImpl> {

    @Autowired
    private BusService busService;

    /**
     * 列表页面
     */
    @RequestMapping(value = "/index")
    public String index(Model model) {
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
    public Map<String, Object> list(Bus bus, Integer draw, Integer start, Integer length) {
        int pageNum = getPageNum(start, length);
        Query query = Query.forClass(Bus.class, busService);
        query.setPagenum(pageNum);
        query.setPagesize(length);
        query.like("carNo",bus.getCarNo());
        query.like("modelNo",bus.getModelNo());
        query.like("driverName",bus.getDriverName());
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
            model.addAttribute("bus", busService.queryByPK(id));
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
            String [] idArr = ids.split("\\,");
            for (String id:idArr) {
                Integer busId = Integer.valueOf(id);
                busService.deleteByPK(busId);
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
        query.like("carNo",bus.getCarNo());
        query.like("modelNo",bus.getModelNo());
        query.like("driverName",bus.getDriverName());

        List list = new ArrayList();
        if(!StringUtils.isEmpty(busIds)){
            String [] busIdArr = busIds.split("\\,");
            for (String busId:busIdArr) {
                Long id = Long.valueOf(busId);
                list.add(id);
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

}
