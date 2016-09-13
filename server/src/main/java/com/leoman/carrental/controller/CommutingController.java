package com.leoman.carrental.controller;

import com.leoman.carrental.entity.CarTravel;
import com.leoman.carrental.entity.Commuting;
import com.leoman.carrental.service.CommutingService;
import com.leoman.carrental.service.impl.CommutingServiceImpl;
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
import java.util.Map;

/**
 * 个人通勤Controller
 * Created by Administrator on 2016/9/12.
 */
@Controller
@RequestMapping(value = "/admin/commuting")
public class CommutingController extends GenericEntityController<Commuting,Commuting,CommutingServiceImpl> {

    @Autowired
    private CommutingService commutingService;

    @RequestMapping(value = "/index")
    public String index(){
        return "/commuting/list";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(Integer draw, Integer start, Integer length, Commuting commuting) throws ParseException {
        int pagenum = getPageNum(start, length);
        Query query = Query.forClass(Commuting.class, commutingService);
        query.setPagenum(pagenum);
        query.setPagesize(length);

        if(commuting!=null){
            if(StringUtils.isNotBlank(commuting.getUserName())){
                query.like("userName",commuting.getUserName());
            }
            if(StringUtils.isNotBlank(commuting.getMobile())){
                query.like("mobile",commuting.getMobile());
            }
            if(StringUtils.isNotBlank(commuting.getStartPoint())){
                query.like("startPoint",commuting.getStartPoint());
            }
            if(StringUtils.isNotBlank(commuting.getEndPoint())){
                query.like("endPoint",commuting.getEndPoint());
            }
        }

        Page<Commuting> page = commutingService.queryPage(query);
        return DataTableFactory.fitting(draw, page);
    }


}
