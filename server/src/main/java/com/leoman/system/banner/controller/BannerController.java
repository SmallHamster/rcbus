package com.leoman.system.banner.controller;

import com.leoman.bus.entity.Bus;
import com.leoman.carrental.entity.CarTravel;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.common.service.Query;
import com.leoman.image.entity.Image;
import com.leoman.image.service.ImageService;
import com.leoman.system.banner.entity.Banner;
import com.leoman.system.banner.service.BannerService;
import com.leoman.system.banner.service.Impl.BannerServiceImpl;
import com.leoman.utils.DateUtils;
import com.leoman.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.Map;

/**
 * BannerController
 * Created by 史龙 on 2016/9/13.
 */
@Controller
@RequestMapping(value = "/admin/banner")
public class BannerController extends GenericEntityController<Banner,Banner,BannerServiceImpl>{

    @Autowired
    private BannerService bannerService;
    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/index")
    public String index(){
        return "/system/banner/list";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(Integer draw, Integer start, Integer length){
        int pagenum = getPageNum(start, length);
        Query query = Query.forClass(Banner.class, bannerService);
        query.setPagenum(pagenum);
        query.setPagesize(length);
        Page<Banner> page = bannerService.queryPage(query);
        return DataTableFactory.fitting(draw, page);
    }

    @RequestMapping(value = "/add")
    public String add(Long id, Model model){

        if(id!=null){
            model.addAttribute("banner",bannerService.queryByPK(id));
        }
        return "/system/banner/add";
    }

    /**
     * 保存
     * @param banner
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(Banner banner, @RequestParam(value = "imageId" ,required = false) Integer imageId) {
        try {
            Image image = imageService.getById(imageId);
            banner.setImage(image);
            bannerService.save(banner);
        } catch (Exception e) {
            e.printStackTrace();
            Result.failure();
        }
        return Result.success();
    }

    /**
     * 删除
     * @param id
     * @param ids
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public Integer del(Long id,String ids) {
        if (id==null && StringUtils.isBlank(ids)){
            return 1;
        }
        try {
            if(id!=null){
                Banner banner = bannerService.queryByPK(id);
                bannerService.delete(banner);
                imageService.deleteById(banner.getImage().getId());
            }else {
                Long[] ss = JsonUtil.json2Obj(ids,Long[].class);
                for (Long _id : ss) {
                    Banner banner = bannerService.queryByPK(_id);
                    bannerService.delete(banner);
                    imageService.deleteById(banner.getImage().getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

}
