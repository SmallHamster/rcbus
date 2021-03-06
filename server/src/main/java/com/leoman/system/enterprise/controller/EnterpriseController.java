package com.leoman.system.enterprise.controller;

import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.common.service.Query;
import com.leoman.system.enterprise.entity.Enterprise;
import com.leoman.system.enterprise.service.EnterpriseService;
import com.leoman.system.enterprise.service.impl.EnterpriseServiceImpl;
import com.leoman.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 系统设置--企业
 * Created by 史龙 on 2016/9/6.
 */
@Controller
@RequestMapping(value = "admin/enterprise")
public class EnterpriseController extends GenericEntityController<Enterprise, Enterprise, EnterpriseServiceImpl> {

    @Autowired
    private EnterpriseService enterpriseService;

    @RequestMapping(value = "/index")
    public String index() {
        return "system/enterprise/list";
    }

    /**
     * 列表
     *
     * @param draw
     * @param start
     * @param length
     * @return
     */
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

    /**
     * 跳转新增页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Long id, Model model) {
        if (id != null) {
            model.addAttribute("enterprise", enterpriseService.queryByPK(id));
        }
        return "system/enterprise/add";

    }

    /**
     * 保存
     *
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(Long id, String name, String userName, Integer type) {
        try {
            enterpriseService.save(id, name, userName, type);
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    /**
     * 删除
     *
     * @param id
     * @param ids
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public Integer del(Long id, String ids) {
        if (id == null && StringUtils.isBlank(ids)) {
            return 1;
        }
        try {
            if (id != null) {
                enterpriseService.delete(enterpriseService.queryByPK(id));
            } else {
                System.out.println();
                Long[] ss = JsonUtil.json2Obj(ids, Long[].class);
                for (Long _id : ss) {
                    enterpriseService.delete(enterpriseService.queryByPK(_id));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }


    /**
     * 刷新邀请码
     */
    @RequestMapping(value = "/refreshCode", method = RequestMethod.POST)
    @ResponseBody
    public String refreshCode(Long id) {
        if (null == id) {
            return "";
        }

        return enterpriseService.refreshInviteCode(id);
    }
}
