package com.leoman.bus.controller;

import com.leoman.bus.entity.EnterpriseApply;
import com.leoman.bus.service.EnterpriseApplyService;
import com.leoman.bus.service.impl.EnterpriseApplyServiceImpl;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.common.service.Query;
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

import java.util.Map;

/**
 * 企业报名信息
 * Created by Daisy on 2016/9/14.
 */
@Controller
@RequestMapping(value = "/admin/enterprise/apply")
public class EnterpriseApplyController extends GenericEntityController<EnterpriseApply, EnterpriseApply, EnterpriseApplyServiceImpl> {

    @Autowired
    private EnterpriseApplyService enterpriseApplyService;

    /**
     * 列表页面
     */
    @RequestMapping(value = "/index")
    public String index(Model model) {
        return "bus/apply_list";
    }

    /**
     * 获取列表
     * @param enterpriseApply
     * @param draw
     * @param start
     * @param length
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(EnterpriseApply enterpriseApply, Integer draw, Integer start, Integer length, @RequestParam(value = "carType1" , required = false)Long carType) {
        int pageNum = getPageNum(start, length);
        Query query = Query.forClass(EnterpriseApply.class, enterpriseApplyService);
        query.setPagenum(pageNum);
        query.setPagesize(length);
        query.like("username",enterpriseApply.getUsername());
        query.like("mobile",enterpriseApply.getMobile());
        query.like("enterpriseName",enterpriseApply.getEnterpriseName());
        query.addOrder("id","desc");

        Page<EnterpriseApply> page = enterpriseApplyService.queryPage(query);
        return DataTableFactory.fitting(draw, page);
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
                    enterpriseApplyService.deleteByPK(Long.valueOf(id));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

}
