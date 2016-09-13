package com.leoman.coupon.controller;

import com.leoman.coupon.entity.Coupon;
import com.leoman.coupon.service.CouponService;
import com.leoman.coupon.service.impl.CouponServiceImpl;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.common.service.Query;
import com.leoman.entity.Configue;
import com.leoman.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 礼券
 * Created by Daisy on 2016/9/13.
 */
@Controller
@RequestMapping(value = "/admin/coupon")
public class CouponController extends GenericEntityController<Coupon, Coupon, CouponServiceImpl> {

    @Autowired
    private CouponService couponService;

    /**
     * 列表页面
     */
    @RequestMapping(value = "/index")
    public String index(Model model) {
        return "coupon/coupon_list";
    }

    /**
     * 获取列表
     * @param draw
     * @param start
     * @param length
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(Integer draw, Integer start, Integer length) {
        int pageNum = getPageNum(start, length);
        Query query = Query.forClass(Coupon.class, couponService);
        query.setPagenum(pageNum);
        query.setPagesize(length);
        Page<Coupon> page = couponService.queryPage(query);
        return DataTableFactory.fitting(draw, page);
    }

    /**
     * 添加礼券
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Long id, Model model) {
        if (id != null) {
            Coupon coupon = couponService.queryByPK(id);
            model.addAttribute("coupon", coupon);
        }
        return "coupon/coupon_add";
    }

    /**
     * 保存
     * @param coupon
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(Coupon coupon) {
        try {
            couponService.save(coupon);
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(String ids) {
        try {
            String[] idArr = JsonUtil.json2Obj(ids, String[].class);
            for (String id:idArr) {
                if(!StringUtils.isEmpty(id)){
                    couponService.deleteByPK(id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

}
