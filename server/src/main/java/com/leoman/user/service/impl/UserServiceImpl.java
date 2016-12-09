package com.leoman.user.service.impl;

import com.leoman.bus.entity.RouteOrder;
import com.leoman.bus.service.RouteOrderService;
import com.leoman.carrental.entity.CarRental;
import com.leoman.carrental.service.CarRentalService;
import com.leoman.common.controller.common.CommonController;
import com.leoman.common.core.Result;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.coupon.entity.Coupon;
import com.leoman.coupon.service.CouponService;
import com.leoman.order.entity.Order;
import com.leoman.order.service.OrderService;
import com.leoman.pay.util.MD5Util;
import com.leoman.permissions.admin.service.AdminService;
import com.leoman.report.entity.Report;
import com.leoman.report.service.ReportService;
import com.leoman.system.enterprise.entity.Enterprise;
import com.leoman.system.enterprise.service.EnterpriseService;
import com.leoman.user.dao.UserInfoDao;
import com.leoman.user.entity.*;
import com.leoman.user.entity.vo.UserExportVo;
import com.leoman.user.service.*;
import com.leoman.utils.JsonUtil;
import com.leoman.utils.ReadExcelUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.persistence.Transient;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends GenericManagerImpl<UserInfo, UserInfoDao> implements UserService {

    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private UserCouponService userCouponService;
    @Autowired
    private NotUserCouponService notUserCouponService;
    @Autowired
    private CouponOrderService couponOrderService;
    @Autowired
    private CarRentalService carRentalService;
    @Autowired
    private ReportService reportService;

    @Autowired
    private RouteOrderService routeOrderService;

    @Autowired
    private OrderService orderService;


    @Override
    public UserInfo findByMobile(String mobile) {
        return userInfoDao.findByMobile(mobile);
    }

    @Override
    public Integer save(UserInfo userInfo, Long id, Long enterpriseId , String password) {
        Enterprise enterprise = enterpriseService.queryByPK(enterpriseId);
        UserLogin userLogin  = new UserLogin();

        UserInfo user = findByMobile(userInfo.getMobile());
        if(user!=null){
            return 2;
        }

        //新增一条用户登录
        userLogin.setUsername(userInfo.getMobile());
        userLogin.setPassword(MD5Util.MD5Encode(password,"UTF-8"));
        userLoginService.save(userLogin);

        //新增一条企业会员
        userInfo.setEnterprise(enterprise);
        userInfo.setUserId(userLogin.getId());
        userInfo.setType(1);
        save(userInfo);
        return 1;
    }

    public void delOrder(UserInfo userInfo){
        StringBuffer cr_ids = new StringBuffer();
        cr_ids.append("[");
        StringBuffer ro_ids = new StringBuffer();
        ro_ids.append("[");
        List<Order> orders = orderService.queryByProperty("userInfo.id",userInfo.getId());
        if(!orders.isEmpty()){
            for(Order o : orders){
                if(o.getType() == 2){
                    CarRental carRental = carRentalService.findOne(o.getId());
                    Long cr_id = carRental.getId();
                    cr_ids.append(cr_id+",");
                }else {
                    RouteOrder routeOrder = routeOrderService.findOne(o.getId());
                    Long ro_id = routeOrder.getId();
                    ro_ids.append(ro_id+",");
                }

            }

            String a = ro_ids.toString().substring(0,ro_ids.toString().length()-1);
            String b = cr_ids.toString().substring(0,cr_ids.toString().length()-1);
            a += "]";
            b += "]";
            carRentalService.del1(a,b);
        }
    }


    @Override
    @Transactional
    public Integer del(Long id, String ids) {

        if (id==null && StringUtils.isBlank(ids)){
            return 1;
        }
        UserInfo userInfo = null;
        try {
            if(id!=null){
                userInfo = queryByPK(id);
                this.delOrder(userInfo);
                delete(userInfo);
                //删除登录表信息
                if(userInfo.getType()==0){
                    adminService.delete(adminService.queryByPK(userInfo.getUserId()));
                }else {
                    userLoginService.delete(userLoginService.queryByPK(userInfo.getUserId()));
                }
                List<Report> reports = reportService.queryByProperty("userInfo.id",userInfo.getId());
                if(!reports.isEmpty()){
                    for(Report report : reports){
                        //删除举报信息
                        reportService.delete(report);
                    }
                }
            }else {
                Long[] ss = JsonUtil.json2Obj(ids,Long[].class);
                for (Long _id : ss) {
                    userInfo = queryByPK(_id);
                    this.delOrder(userInfo);
                    delete(userInfo);
                    //删除登录表信息
                    if(userInfo.getType()==0){
                        adminService.delete(adminService.queryByPK(userInfo.getUserId()));
                    }else {
                        userLoginService.delete(userLoginService.queryByPK(userInfo.getUserId()));
                    }
                    List<Report> reports = reportService.queryByProperty("userInfo.id",userInfo.getId());
                    if(!reports.isEmpty()){
                        for(Report report : reports){
                            //删除举报信息
                            reportService.delete(report);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    public Result readExcelInfo(MultipartRequest multipartRequest) {
        Result result = new Result();
        UserInfo userInfo = null;
        try{
            MultipartFile multipartFile = multipartRequest.getFile("feedback");
            List<UserExportVo> list = ReadExcelUtil.readExcel(multipartFile);
            if (null == list || list.size() == 0) {
                return Result.failure();
            }

            for (UserExportVo userExportVo : list) {
                if(StringUtils.isBlank(userExportVo.getMobile())){
                    result.setMsg("用户手机号不能为空");
                    result.setStatus(2);
                    return result;
                }
                if(StringUtils.isBlank(userExportVo.getEnterprise())){
                    result.setMsg("用户所在公司不能为空");
                    result.setStatus(2);
                    return result;
                }
                //判断账号是否存在
                UserLogin userLogins = userLoginService.findByUsername(userExportVo.getMobile());
                if (userLogins!=null) {
                    result.setMsg("用户［"+userLogins.getUsername()+"］已存在");
                    result.setStatus(2);
                    return result;
                }

            }

            for (UserExportVo userExportVo : list)  {
                //新建一条用户信息 存号码 固定密码 员工身份
                userInfo = new UserInfo();
                userInfo.setMobile(userExportVo.getMobile());
//                userInfo.setPassword(MD5Util.MD5Encode("888888","UTF-8"));
                userInfo.setType(1);

                //新增一条用户登录信息 存号码 固定密码
                UserLogin userLogin = new UserLogin();
                userLogin.setUsername(userExportVo.getMobile());
                userLogin.setPassword(MD5Util.MD5Encode("888888","UTF-8"));
                userLoginService.save(userLogin);

                //设置关联ID
                userInfo.setUserId(userLogin.getId());

                //判断公司是否存在
                List<Enterprise> enterprises = enterpriseService.queryByProperty("name",userExportVo.getEnterprise().trim());
                if(!enterprises.isEmpty() && enterprises.size()>0){
                    //公司存在 存公司ID
                    userInfo.setEnterprise(enterprises.get(0));
                }else {
                    //公司不存在 新增公司 存公司ID
                    Enterprise enterprise = new Enterprise();
                    enterprise.setName(userExportVo.getEnterprise().trim());
                    enterprise.setType(0);
                    enterpriseService.save(enterprise);

                    userInfo.setEnterprise(enterprise);
                }

                save(userInfo);

            }

            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public void saveUser(String mobile,String password, String ip){
        UserLogin userLogin = new UserLogin();
        userLogin.setUsername(mobile);
        userLogin.setPassword(password);
        userLogin.setLastLoginDate(System.currentTimeMillis());
        userLogin.setIp(ip);
        userLoginService.save(userLogin);

        UserInfo userInfo = new UserInfo();
        userInfo.setMobile(mobile);
        userInfo.setUserId(userLogin.getId());
        userInfo.setType(2);//普通会员
        userInfoDao.save(userInfo);

        Coupon _c = new Coupon();
        List<Coupon> coupons =  couponService.queryAll();
        for(Coupon coupon : coupons){
            //3-注册
            if(coupon.getGainWay()==3){
                _c = coupon;
            }
        }
        //新增注册优惠券
        UserCoupon userCoupon = new UserCoupon();
        CouponOrder couponOrder = new CouponOrder();

        //快照
        couponOrder.setName(_c.getName());
        couponOrder.setGainWay(_c.getGainWay());
        couponOrder.setCouponWay(_c.getCouponWay());
        couponOrder.setValidDateFrom(_c.getValidDateFrom());
        couponOrder.setValidDateTo(_c.getValidDateTo());
        couponOrder.setDiscountPercent(_c.getDiscountPercent());
        couponOrder.setDiscountTopMoney(_c.getDiscountTopMoney());
        couponOrder.setReduceMoney(_c.getReduceMoney());
        couponOrder.setIsLimit(_c.getIsLimit());
        couponOrder.setLimitMoney(_c.getLimitMoney());
        couponOrderService.save(couponOrder);

        userCoupon.setCoupon(couponOrder);
        userCoupon.setUserId(userInfo.getId());
        userCoupon.setIsUse(1);
        userCouponService.save(userCoupon);

        //非用户时领取了过优惠券
        List<NotUserCoupon> notUserCoupons = notUserCouponService.queryByProperty("mobile",mobile);
        if(!notUserCoupons.isEmpty() && notUserCoupons.size()>0){
            for(NotUserCoupon notUserCoupon : notUserCoupons){
                //新增之前领取的优惠券
                userCoupon = new UserCoupon();
                userCoupon.setUserId(userInfo.getId());
                userCoupon.setCoupon(couponOrderService.queryByPK(notUserCoupon.getCouponId()));
                userCoupon.setIsUse(1);
                userCouponService.save(userCoupon);
                //删除非用户优惠券表信息
                notUserCouponService.delete(notUserCoupon);
            }

        }


    }

    @Override
    public UserInfo findByWechatId(Integer WechatId) {
        return userInfoDao.findByWechatId(WechatId);
    }

}
