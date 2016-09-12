package com.leoman.report.controller;

import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.report.entity.Report;
import com.leoman.report.service.Impl.ReportServiceImpl;
import com.leoman.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/9/12.
 */
@Controller
@RequestMapping(value = "/admin/report")
public class ReportController extends GenericEntityController<Report,Report,ReportServiceImpl> {

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/index")
    public String index(){
        return "/report/index";
    }

}
