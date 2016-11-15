package com.leoman.wechat.index;


import com.leoman.common.controller.common.CommonController;
import com.leoman.common.core.ErrorType;
import com.leoman.common.core.Result;
import com.leoman.common.core.UrlManage;
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
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.custombuilder.NewsBuilder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.naming.spi.DirStateFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpMessageRouter wxMpMessageRouter;

    /**
     * 首页 自定义，方便显示所有页面
     * @return
     */
    @RequestMapping(value = "/index")
    public String index(String signature, String timestamp, String nonce) {
//        SignUtil.checkSignature(signature,timestamp,nonce);
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
     * 注销
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute(Constant.SESSION_MEMBER_USER);
        request.getSession().removeAttribute(Constant.SESSION_WEIXIN_WXUSER);
        CookiesUtils.logoutCookie(request, response);
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
            return loginService.loginWeixin(request,response,username,password);
//            UserInfo user = loginService.login(username,Md5Util.md5(password));
//            if(user != null){
//                request.getSession().setAttribute(Constant.SESSION_MEMBER_USER, user);
//
//                int loginMaxAge = 30 * 24 * 60 * 60; // 定义cookies的生命周期，这里是一个月。单位为秒
//                CookiesUtils.addCookie(response, "username", username, loginMaxAge);
//            }
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.printJson(response,new com.leoman.common.core.bean.Result(false).msg(e.getMessage()));
            return Result.failure();
        }
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
    @ResponseBody
    public Result smsCode(HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestParam(required=true) String mobile,
                        @RequestParam(required=true) String type){


        UserInfo user = userService.findByMobile(mobile);

        //注册获取验证码
        if("register".equals(type)){
            if(user != null){
                return Result.failure(ErrorType.ERROR_CODE_0009);//用户已存在
            }
        }
        //忘记密码获取验证码
        else if("findPwd".equals(type)){
            if(user == null){
                return Result.failure(ErrorType.ERROR_CODE_0003);//找不到用户
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

        return Result.success();
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
    public static void sendSms(String phone,String code){
        String url = "https://sms.yunpian.com/v1/sms/send.json";
        String apikey = Configue.getSmsApiKey();
        Map<String,String> map = new HashMap<>();
        map.put("apikey",apikey);
        map.put("mobile",phone);
        map.put("text","【江城巴士】亲爱的您好，您的验证码是"+code+"。有效期为10分钟，请尽快验证");
        HttpRequestUtil.sendPost(url,map);
    }

    @PostConstruct
    protected void menuInit() {

        WxMenu menu = new WxMenu();

        //企业用户
        WxMenu.WxMenuButton button1 = new WxMenu.WxMenuButton();
        button1.setName("企业用户");

        WxMenu.WxMenuButton button1_1 = new WxMenu.WxMenuButton();
        button1_1.setType(WxConsts.BUTTON_VIEW);
        button1_1.setName("企业申请");
        button1_1.setUrl(UrlManage.getProUrl("wechat/enterprise/apply"));

        WxMenu.WxMenuButton button1_2 = new WxMenu.WxMenuButton();
        button1_2.setType(WxConsts.BUTTON_VIEW);
        button1_2.setName("通勤班车");
        button1_2.setUrl(UrlManage.getProUrl("wechat/route/index/0"));

        button1.getSubButtons().add(button1_1);
        button1.getSubButtons().add(button1_2);

        //个人用户
        WxMenu.WxMenuButton button2 = new WxMenu.WxMenuButton();
        button2.setName("个人用户");

        WxMenu.WxMenuButton button2_1 = new WxMenu.WxMenuButton();
        button2_1.setType(WxConsts.BUTTON_VIEW);
        button2_1.setName("永旺专线");
        button2_1.setUrl(UrlManage.getProUrl("wechat/route/index/1"));

        WxMenu.WxMenuButton button2_2 = new WxMenu.WxMenuButton();
        button2_2.setType(WxConsts.BUTTON_VIEW);
        button2_2.setName("租车预定");
        button2_2.setUrl(UrlManage.getProUrl("wechat/carrental/index"));

        button2.getSubButtons().add(button2_1);
        button2.getSubButtons().add(button2_2);

        //个人中心
        WxMenu.WxMenuButton button3 = new WxMenu.WxMenuButton();
        button3.setName("个人中心");

        WxMenu.WxMenuButton button3_1 = new WxMenu.WxMenuButton();
        button3_1.setType(WxConsts.BUTTON_VIEW);
        button3_1.setName("我的订单");
        button3_1.setUrl(UrlManage.getProUrl("wechat/order/myOrder/index"));

        WxMenu.WxMenuButton button3_2 = new WxMenu.WxMenuButton();
        button3_2.setType(WxConsts.BUTTON_VIEW);
        button3_2.setName("个人信息");
        button3_2.setUrl(UrlManage.getProUrl("wechat/user/index"));

        WxMenu.WxMenuButton button3_3 = new WxMenu.WxMenuButton();
        button3_3.setType(WxConsts.BUTTON_VIEW);
        button3_3.setName("反馈建议");
        button3_3.setUrl(UrlManage.getProUrl("wechat/report/index"));

        button3.getSubButtons().add(button3_1);
        button3.getSubButtons().add(button3_2);
        button3.getSubButtons().add(button3_3);

        menu.getButtons().add(button1);
        menu.getButtons().add(button2);
        menu.getButtons().add(button3);

        try {
            System.out.println("==============menuCreate()==================");
            wxMpService.menuCreate(menu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 微信接口验证
     *
     * @param request
     * @param response
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    public void handleGet(HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam(value = "signature") String signature,
                          @RequestParam(value = "timestamp") String timestamp,
                          @RequestParam(value = "nonce") String nonce,
                          @RequestParam(value = "echostr") String echostr) throws ServletException, IOException {
        if (wxMpService.checkSignature(timestamp, nonce, signature)) {
            WebUtil.print(response, echostr);
            return;
        }
        System.out.println(response);
        WebUtil.print(response, "非法请求");
    }

    /**
     * 微信获取用户请求
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.HEAD})
    public void handlePost(HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        String encryptType = me.chanjar.weixin.common.util.StringUtils.isBlank(request.getParameter("encrypt_type")) ? "raw" : request.getParameter("encrypt_type");
        if ("raw".equals(encryptType)) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
            System.out.println("-------------------");
            System.out.println("in:" + inMessage);
            System.out.println("-------------------");
            if ((inMessage.getEvent().equals("SCAN") || inMessage.getEvent().equals("subscribe")) && org.apache.commons.lang.StringUtils.isNotBlank(inMessage.getEventKey()) && org.apache.commons.lang.StringUtils.isNotBlank(inMessage.getTicket())) {
                System.out.println("==========================================:" + inMessage.getEventKey());
                String eventKey = inMessage.getEventKey().toString();
                eventKey = eventKey.replace("qrscene_", "");
                String[] eventKeys = eventKey.split(",");
                String productId = eventKeys[0];
                String salemanId = eventKeys[1];

                NewsBuilder news = WxMpCustomMessage.NEWS();
                news = news.toUser(inMessage.getFromUserName());
                WxMpCustomMessage.WxArticle article = new WxMpCustomMessage.WxArticle();
                article.setUrl("www.sina.com");
                article.setPicUrl("123.png");
                article.setDescription("测试desc");
                article.setTitle("测试title");
                news.addArticle(article);

                WxMpService wxMpService = (WxMpService) BeanUtils.getBean("wxMpService");
                wxMpService.customMessageSend(news.build());
            } else {
                WxMpXmlOutMessage outMessage = wxMpMessageRouter.route(inMessage);
                WebUtil.print(response, outMessage.toXml());
            }
            return;
        }
    }


}
