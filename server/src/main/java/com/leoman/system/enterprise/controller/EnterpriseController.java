package com.leoman.system.enterprise.controller;

import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.common.service.Query;
import com.leoman.pay.util.MD5Util;
import com.leoman.permissions.admin.entity.Admin;
import com.leoman.system.enterprise.dao.EnterpriseDao;
import com.leoman.system.enterprise.entity.Enterprise;
import com.leoman.system.enterprise.service.EnterpriseService;
import com.leoman.system.enterprise.service.impl.EnterpriseServiceImpl;
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
 * 系统设置--企业
 * Created by 史龙 on 2016/9/6.
 */
@Controller
@RequestMapping(value = "admin/enterprise")
public class EnterpriseController extends GenericEntityController<Enterprise,Enterprise,EnterpriseServiceImpl> {

    @Autowired
    private EnterpriseService enterpriseService;


    @RequestMapping(value = "/index")
    public String index(){
        return "system/enterprise/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(Integer draw, Integer start, Integer length) {
        int pagenum = getPageNum(start, length);
        Query query = Query.forClass(Enterprise.class, enterpriseService);
        query.setPagenum(pagenum);
        query.setPagesize(length);
        Page<Enterprise> page = enterpriseService.queryPage(query);
        return DataTableFactory.fitting(draw, page);
    }

    @RequestMapping(value = "/add")
    public String add(Long id, Model model){
        if(id != null){
            model.addAttribute("enterprise",enterpriseService.queryByPK(id));
        }
        return "system/enterprise/add";

    }

    /**
     * 保存
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(Long id,String name) {
        Enterprise enterprise = null;
        try {
            if(id!=null){
                enterprise = enterpriseService.queryByPK(id);
            }
            if(enterprise!=null){
                enterprise.setName(name);
            }else {
                enterprise = new Enterprise();
                enterprise.setName(name);
            }
            enterpriseService.save(enterprise);
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }


}
