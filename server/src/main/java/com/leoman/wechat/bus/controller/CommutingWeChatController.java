package com.leoman.wechat.bus.controller;

import com.leoman.carrental.entity.Commuting;
import com.leoman.carrental.service.CommutingService;
import com.leoman.common.controller.common.CommonController;
import com.leoman.common.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 个人通勤申请
 * Created by Daisy on 2016/9/23.
 */
@Controller
@RequestMapping(value = "/wechat/commuting")
public class CommutingWeChatController extends CommonController {

    @Autowired
    private CommutingService commutingService;

    /**
     * 跳转至个人通勤申请页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/apply")
    public String toCommuting(Model model) {
        return "wechat/commuting_apply";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result saveCommuting(HttpServletRequest request,
                                HttpServletResponse response,
                                Commuting commuting) {
        try {
            commutingService.save(commuting);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }

}
