package com.leoman.wechat.index;


import com.leoman.common.controller.common.CommonController;
import com.leoman.common.core.ErrorType;
import com.leoman.common.core.Result;
import com.leoman.common.log.entity.Log;
import com.leoman.entity.Configue;
import com.leoman.entity.Constant;
import com.leoman.index.service.LoginService;
import com.leoman.pay.util.HttpClientUtil;
import com.leoman.permissions.admin.entity.Admin;
import com.leoman.permissions.module.entity.vo.ModuleVo;
import com.leoman.permissions.module.service.ModuleService;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.entity.UserLogin;
import com.leoman.user.service.UserLoginService;
import com.leoman.user.service.UserService;
import com.leoman.utils.*;
import com.sun.deploy.net.HttpUtils;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Daisy on 2016/9/18.
 */
@Controller
@RequestMapping(value = "wechat")
public class WechatIndexController extends CommonController {

    private static Logger logger = LoggerFactory.getLogger(WechatIndexController.class);

    private static final Map codeMap = new HashMap();

    @Autowired
    private UserService userService;

    @Autowired
    private UserLoginService loginService;

    /**
     * 首页 自定义，方便显示所有页面
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {
        return "wechat/index";
    }

    /**
     * 跳转至登录
     * @param request
     * @param response
     * @param error
     * @param model
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request,
                        HttpServletResponse response,
                        String error,
                        ModelMap model) {
        try {
            if (StringUtils.isNotBlank(error)) {
                model.addAttribute("error", error);
            }
            // 先读取cookies，如果存在，则将用户名回写到输入框
            Map<String, Object> params = CookiesUtils.ReadCookieMap(request);
            if (params != null && params.size() != 0) {
                String username = (String) params.get("username");
                model.put("username", username);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "wechat/login";
    }

    /**
     * 登录验证
     * @param username
     * @param password
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/login/check", method = RequestMethod.POST)
    @ResponseBody
    public Result checkLogin(String username,
                             String password,
                             HttpServletRequest request,
                             HttpServletResponse response,
                             ModelMap model) {
        try {
            UserInfo user = loginService.login(username,Md5Util.md5(password));
            if(user != null){
                request.getSession().setAttribute(Constant.SESSION_MEMBER_USER, user);

                int loginMaxAge = 30 * 24 * 60 * 60; // 定义cookies的生命周期，这里是一个月。单位为秒
                CookiesUtils.addCookie(response, "username", username, loginMaxAge);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
            WebUtil.printJson(response,new com.leoman.common.core.bean.Result(false).msg(e.getMessage()));
        }
        return Result.success();
    }

    @RequestMapping(value = "/toRegister1")
    public String toRegister1() {
        return "wechat/register1";
    }

    @RequestMapping(value = "/toRegister2")
    public String toRegister2(ModelMap model, String mobile, String code) {
        model.addAttribute("mobile",mobile);
        model.addAttribute("code",code);
        return "wechat/register2";
    }

    @RequestMapping(value = "/toAgree")
    public String toAgree() {
        return "wechat/agreement";
    }

    /**
     * 注册
     * @param request
     * @param response
     * @param mobile
     * @param password
     * @param code
     * @throws Exception
     */
    @RequestMapping("/register")
    public void register(HttpServletRequest request,
                         HttpServletResponse response,
                         @RequestParam(required = true) String mobile,
                         @RequestParam(required = true) String password,
                         @RequestParam(required = true) String code) throws Exception {

        String cacheCode = (String) codeMap.get("code");
        if(StringUtils.isBlank(cacheCode)||!cacheCode.equals(code)){
            WebUtil.printJson(response,new Result().failure(ErrorType.ERROR_CODE_0004));//验证码错误
            return;
        }

        UserInfo user = userService.findByMobile(mobile);
        if(user != null){
            WebUtil.printJson(response,new Result().failure(ErrorType.ERROR_CODE_0009));//手机号已被注册
            return;
        }

        //新增用户
        WebUtil.printJson(response,user);
    }

    /**
     * 获取验证码
     * @param request
     * @param response
     * @param mobile
     */
    @RequestMapping(value = "/sms/code")
    public void smsCode(HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestParam(required=true) String mobile){

        try {
            UserInfo user = userService.findByMobile(mobile);
            if(user != null){
                WebUtil.printJson(response,new Result().failure(ErrorType.ERROR_CODE_0009));//用户已存在
                return ;
            }

            //发送验证码
            String code = SeqNoUtils.getVerCode(6);
            logger.info("--------------------->"+code);

            //发送短信
            sendSms(mobile,code);
            codeMap.put("code",code);
            WebUtil.printJson(response,new Result().success(createMap("code",code)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 发送短信验证码
     * @param phone
     * @param code
     */
    public void sendSms(String phone,String code){
        String url = "https://sms.yunpian.com/v1/sms/send.json";
        String apikey = Configue.getSmsApiKey();
        Map<String,String> map = new HashMap<>();
        map.put("apikey",apikey);
        map.put("mobile",phone);
        map.put("text","【江城巴士】，您的验证码是："+code+",10分钟");
        HttpRequestUtil.sendPost(url,map);
    }


}
