package com.leoman.wechat.index;


import com.leoman.common.controller.common.CommonController;
import com.leoman.common.log.entity.Log;
import com.leoman.entity.Constant;
import com.leoman.index.service.LoginService;
import com.leoman.permissions.admin.entity.Admin;
import com.leoman.permissions.module.entity.vo.ModuleVo;
import com.leoman.permissions.module.service.ModuleService;
import com.leoman.utils.CookiesUtils;
import com.leoman.utils.Md5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Daisy on 2016/9/18.
 */
@Controller
@RequestMapping(value = "wechat")
public class WechatIndexController extends CommonController {

    /**
     * 首页 自定义，方便显示所有页面
     * @return
     */
    @RequestMapping(value = "/")
    public String index() {
        return "wechat/index";
    }

}
