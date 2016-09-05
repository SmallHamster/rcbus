//package com.leoman.user.controller;
//
//import com.leoman.address.entity.Address;
//import com.leoman.address.service.AddressService;
//import com.leoman.common.controller.common.GenericEntityController;
//import com.leoman.common.factory.DataTableFactory;
//import com.leoman.creator.entity.Creator;
//import com.leoman.creator.service.CreatorService;
//import com.leoman.exception.GeneralExceptionHandler;
//import com.leoman.user.entity.UserInfo;
//import com.leoman.user.service.UserService;
//import com.leoman.user.service.impl.UserServiceImpl;
//import com.leoman.utils.JsonUtil;
//import com.leoman.utils.WebUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by Administrator on 2016/6/14 0014.
// */
//@Controller
//@RequestMapping(value = "admin/user")
//public class UserController extends GenericEntityController<UserInfo, UserInfo, UserServiceImpl> {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private AddressService addressService;
//
//    @Autowired
//    private CreatorService creatorService;
//
//    /**
//     * 列表页面
//     *
//     * @return
//     */
//    @RequestMapping(value = "/index")
//    public String index() {
//        return "user/list";
//    }
//
//    /**
//     * 查询列表
//     *
//     */
//    @RequestMapping(value = "/list")
//    public void list(HttpServletRequest request,
//                     HttpServletResponse response,
//                     String mobile,
//                     String nickname,
//                     Integer gender,
//                     Integer status,
//                     Integer draw,
//                     Integer start,
//                     Integer length) {
//        try {
//            int pageNum = getPageNum(start, length);
//
//            Page<UserInfo> page = userService.page(mobile, nickname, gender, status, 0, pageNum, length);
//
//            Map<String, Object> result = DataTableFactory.fitting(draw, page);
//            WebUtil.print(response, result);
//        } catch (Exception e) {
//            GeneralExceptionHandler.log(e);
//            WebUtil.print(response, emptyData);
//        }
//    }
//
//    /**
//     * 封禁
//     *
//     * @param response
//     * @param userId
//     * @return
//     */
//    @RequestMapping(value = "/banned")
//    @ResponseBody
//    public Integer banned(HttpServletResponse response, Long userId, Integer status) {
//
//        UserInfo userInfo = userService.queryByPK(userId);
//        userInfo.setStatus(status);
//        userService.update(userInfo);
//
//        return 1;
//    }
//
//    /**
//     * 设置为主创
//     *
//     * @param response
//     * @param userId
//     * @return
//     */
//    @RequestMapping(value = "/setCreator")
//    @ResponseBody
//    public Integer setCreator(HttpServletResponse response, Long userId, Integer status) {
//
//        UserInfo userInfo = userService.queryByPK(userId);
//        userInfo.setIsCreator(status);
//
//        Creator creator = new Creator();
//        creator.setStatus(0);
//        creatorService.save(creator);
//        userInfo.setCreator(creator);
//
//        userService.save(userInfo);
//        userService.update(userInfo);
//
//        return 1;
//    }
//
//    /**
//     * 送馒头确认
//     *
//     */
//    @RequestMapping(value = "/batchConfirm")
//    @ResponseBody
//    public Integer batchConfirm(Long userId, Long num) {
//
//        UserInfo userInfo = userService.queryByPK(userId);
//        userInfo.setCoin(userInfo.getCoin() + num);
//        userService.update(userInfo);
//
//        return 1;
//    }
//
//    /**
//     * 详情
//     *
//     * @param userId
//     * @param model
//     */
//    @RequestMapping(value = "/detail")
//    public String detail(Long userId, Model model) {
//
//        UserInfo userInfo = userService.queryByPK(userId);
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("id", userInfo.getId());
//        map.put("path", userInfo.getAvater());
//
//        List<Address> addressList = addressService.findByUserId(userInfo.getUserLogin().getId());
//
//        model.addAttribute("userInfo", userInfo);
//        model.addAttribute("addressList", addressList);
//
//        return "user/detail";
//    }
//
//    @RequestMapping(value = "/batchSet", method = RequestMethod.POST)
//    @ResponseBody
//    public Integer batchSet(String ids) {
//        Long[] arrayId = JsonUtil.json2Obj(ids, Long[].class);
//        for (Long id : arrayId) {
//            UserInfo userInfo = userService.queryByPK(id);
//            userInfo.setIsCreator(1);
//
//            Creator creator = new Creator();
//            creator.setStatus(0);
//            creatorService.save(creator);
//            userInfo.setCreator(creator);
//
//            userService.save(userInfo);
//            userService.update(userInfo);
//        }
//        return 1;
//    }
//
//}
