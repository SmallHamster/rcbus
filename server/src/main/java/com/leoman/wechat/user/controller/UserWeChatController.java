package com.leoman.wechat.user.controller;

import com.leoman.common.controller.common.CommonController;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信  个人信息
 * Created by 史龙 on 2016/9/21.
 */
@Controller
@RequestMapping(value = "/wechat/user")
public class UserWeChatController extends GenericEntityController<UserInfo, UserInfo, UserServiceImpl> {

    @RequestMapping(value = "/index")
    public String index(Model model, HttpServletRequest request){
        model.addAttribute("user",new CommonController().getSessionUser(request));
        return "wechat/personal_details";
    }

}
