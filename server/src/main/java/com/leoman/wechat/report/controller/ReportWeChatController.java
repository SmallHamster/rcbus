package com.leoman.wechat.report.controller;

import com.leoman.common.controller.common.CommonController;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.core.Result;
import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.report.entity.Report;
import com.leoman.report.service.Impl.ReportServiceImpl;
import com.leoman.report.service.ReportService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 反馈意见
 *
 * Created by 史龙 on 2016/9/23.
 */
@RequestMapping(value = "/wechat/report")
@Controller
public class ReportWeChatController extends GenericEntityController<Report,Report,ReportServiceImpl>{

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/index")
    public String index(){
        return "wechat/report";
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public Result save(HttpServletRequest request,@RequestParam(value = "mobile" , required = false)String mobile, String msg){
        Report report = new Report();
        try{
            report.setUserInfo(new CommonController().getSessionUser(request));
            if(StringUtils.isNotBlank(mobile)){
                report.setMobile(mobile);
            }else {
                report.setMobile(report.getUserInfo().getMobile());
            }
            report.setContent(msg);
            reportService.save(report);

        }catch (Exception e){
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }


}
