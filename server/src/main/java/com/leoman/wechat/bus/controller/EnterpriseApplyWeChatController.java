package com.leoman.wechat.bus.controller;

import com.leoman.bus.entity.EnterpriseApply;
import com.leoman.bus.service.EnterpriseApplyService;
import com.leoman.bus.service.impl.EnterpriseApplyServiceImpl;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 企业报名信息
 * Created by Daisy on 2016/9/18.
 */
@Controller
@RequestMapping(value = "/wechat/enterprise/apply")
public class EnterpriseApplyWeChatController extends GenericEntityController<EnterpriseApply, EnterpriseApply, EnterpriseApplyServiceImpl> {

    @Autowired
    private EnterpriseApplyService enterpriseApplyService;

    /**
     * 报名页面
     */
    @RequestMapping(value = "/")
    public String index(Model model) {
        return "wechat/enterprise_apply";
    }

    /**
     * 提交报名信息
     * @param enterpriseApply
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(EnterpriseApply enterpriseApply) {
        try {
            enterpriseApplyService.save(enterpriseApply);
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

}
