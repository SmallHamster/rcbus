package com.leoman.wechat.index;


import com.leoman.common.controller.common.CommonController;
import com.leoman.common.core.ErrorType;
import com.leoman.common.core.Result;
import com.leoman.common.log.entity.Log;
import com.leoman.entity.Configue;
import com.leoman.entity.Constant;
import com.leoman.index.service.LoginService;
import com.leoman.pay.util.HttpClientUtil;
import com.leoman.pay.util.MD5Util;
import com.leoman.permissions.admin.entity.Admin;
import com.leoman.permissions.module.entity.vo.ModuleVo;
import com.leoman.permissions.module.service.ModuleService;
import com.leoman.user.entity.UserInfo;
import com.leoman.user.entity.UserLogin;
import com.leoman.user.service.UserLoginService;
import com.leoman.user.service.UserService;
import com.leoman.utils.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.naming.spi.DirStateFactory;
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

    /**
     * 跳转至注册页的第一步
     * @return
     */
    @RequestMapping(value = "/register1")
    public String toRegister1() {
        return "wechat/register1";
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
                        @RequestParam(required=true) String mobile,
                        @RequestParam(required=true) String type){

        try {

            UserInfo user = userService.findByMobile(mobile);

            //注册获取验证码
            if("register".equals(type)){
                if(user != null){
                    WebUtil.printJson(response,new Result().failure(ErrorType.ERROR_CODE_0009));//用户已存在
                    return ;
                }
            }
            //忘记密码获取验证码
            else if("findPwd".equals(type)){
                if(user == null){
                    WebUtil.printJson(response,new Result().failure(ErrorType.ERROR_CODE_0003));//用户已存在
                    return ;
                }
            }

            //发送验证码
            String code = SeqNoUtils.getVerCode(6);
            logger.info("--------------------->"+code);

            //发送短信
            sendSms(mobile,code);
            codeMap.put("CODE_"+mobile,code);
            codeMap.put("SENDTIME_"+mobile, System.currentTimeMillis());
            WebUtil.printJson(response,new Result().success(createMap("code",code)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下一步：校验验证码
     * @param request
     * @param response
     * @param mobile
     * @param code
     * @throws Exception
     */
    @RequestMapping("/check/code")
    public void checkCode(HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam(required = true) String mobile,
                          @RequestParam(required = true) String code,
                          @RequestParam(required=true) String type) throws Exception {

        UserInfo user = userService.findByMobile(mobile);

        //注册获取验证码
        if("register".equals(type)){
            if(user != null){
                WebUtil.printJson(response,new Result().failure(ErrorType.ERROR_CODE_0009));//用户已存在
                return ;
            }
        }
        //忘记密码获取验证码
        else if("findPwd".equals(type)){
            if(user == null){
                WebUtil.printJson(response,new Result().failure(ErrorType.ERROR_CODE_0003));//用户已存在
                return ;
            }
        }

        String cacheCode = (String) codeMap.get("CODE_"+mobile);
        Long cacheTime = (Long)codeMap.get("SENDTIME_"+mobile);

        if(StringUtils.isBlank(cacheCode)||!cacheCode.equals(code)){
            WebUtil.printJson(response,new Result().failure(ErrorType.ERROR_CODE_0004));//验证码错误
            return;
        }else{
            if(System.currentTimeMillis() - cacheTime > 10*60*1000){
                WebUtil.printJson(response,new Result().failure(ErrorType.ERROR_CODE_00028));//验证码超时
                return;
            }
        }
        //新增用户
        WebUtil.printJson(response,new Result().success());
    }

    /**
     * 跳转只注册页的第二步
     * @param model
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/register2")
    public String toRegister2(ModelMap model, String mobile, String type) {
        model.addAttribute("mobile",mobile);
        model.addAttribute("type",type);
        return "wechat/register2";
    }



    /**
     * 注册
     * @param request
     * @param response
     * @param mobile
     * @param password
     * @throws Exception
     */
    @RequestMapping("/register")
    public Result register(HttpServletRequest request,
                         HttpServletResponse response,
                         @RequestParam(required = true) String mobile,
                         @RequestParam(required = true) String password,
                           @RequestParam(required = true) String type) throws Exception {

        try {

            UserInfo user = userService.findByMobile(mobile);
            //注册
            if("register".equals(type)){
                if(user != null){
                    WebUtil.printJson(response,new Result().failure(ErrorType.ERROR_CODE_0009));//用户已存在
                }else{
                    //新增用户
                    userService.saveUser(mobile, Md5Util.md5(password),HttpRequestUtil.getUserIpByRequest(request));
                }
            }
            //忘记密码
            else if("findPwd".equals(type)){
                if(user == null){
                    WebUtil.printJson(response,new Result().failure(ErrorType.ERROR_CODE_0003));//用户已存在
                }else{
                    //修改密码
                    UserLogin login = loginService.findByUsername(mobile);
                    if(login != null){
                        login.setPassword(Md5Util.md5(password));
                        loginService.save(login);
                    }
                }
            }

            WebUtil.printJson(response,new Result().success());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }


    /**
     * 跳转至修改密码页面
     * @return
     */
    @RequestMapping(value = "/toUpdPwd")
    public String toUpdPwd() {

        return "wechat/update_pwd";
    }

    /**
     * 修改密码
     * @param request
     * @param response
     * @param oldPwd
     * @param newPwd
     * @return
     * @throws Exception
     */
    @RequestMapping("/updatePwd")
    public void updatePwd(HttpServletRequest request,
                           HttpServletResponse response,
                           String oldPwd,
                           String newPwd) throws Exception {

        try {

            UserInfo user = super.getSessionUser(request);
            if(user != null){
                UserLogin login = loginService.findByUsername(user.getMobile());
                if(!login.getPassword().equals(Md5Util.md5(oldPwd))){
                    WebUtil.printJson(response,new Result().failure(ErrorType.ERROR_CODE_0005));//旧密码错误
                    return;
                }

                //修改密码
                login.setPassword(Md5Util.md5(newPwd));
                loginService.save(login);
            }

            WebUtil.printJson(response,new Result().success());
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.printJson(response,new Result().failure());
        }
    }

    /**
     * 跳转至忘记密码页面
     * @return
     */
    @RequestMapping(value = "/toFindPwd")
    public String toFindPwd() {
        return "wechat/find_pwd";
    }

    /**
     * 跳转至服务协议
     * @return
     */
    @RequestMapping(value = "/agreement")
    public String toAgree() {
        return "wechat/agreement";
    }

    /**
     * 跳转至服务协议
     * @return
     */
    @RequestMapping(value = "/help")
    public String toHelp() {
        return "wechat/help";
    }

    @RequestMapping(value = "/error")
    public String toError(Model model,String errorMsg) {
        model.addAttribute("errorMsg",errorMsg);
        return "wechat/access_error";
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
        map.put("text","【江城巴士】亲爱的您好，您的验证码是"+code+"。有效期为10分钟，请尽快验证");
        HttpRequestUtil.sendPost(url,map);
    }


}
