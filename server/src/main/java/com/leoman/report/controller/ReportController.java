package com.leoman.report.controller;

import com.leoman.carrental.entity.Commuting;
import com.leoman.common.controller.common.GenericEntityController;
import com.leoman.common.factory.DataTableFactory;
import com.leoman.common.service.Query;
import com.leoman.report.entity.Report;
import com.leoman.report.service.Impl.ReportServiceImpl;
import com.leoman.report.service.ReportService;
import com.leoman.utils.DateUtils;
import com.leoman.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.Map;

/**
 * 反馈controller
 * Created by 史龙 on 2016/9/12.
 */
@Controller
@RequestMapping(value = "/admin/report")
public class ReportController extends GenericEntityController<Report,Report,ReportServiceImpl> {

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/index")
    public String index(){
        return "/report/list";
    }


    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String, Object> list(Integer draw, Integer start, Integer length, String startDate, String endDate, Report report) throws ParseException {
        int pagenum = getPageNum(start, length);
        Query query = Query.forClass(Report.class, reportService);
        query.setPagenum(pagenum);
        query.setPagesize(length);

        if(report.getUserInfo()!=null){
            if(StringUtils.isNotBlank(report.getUserInfo().getMobile())){
                query.like("userInfo.mobile",report.getUserInfo().getMobile());
            }
        }
        if(StringUtils.isNotBlank(startDate)){
            query.ge("createDate", DateUtils.stringToLong(startDate,"yyyy-MM-dd"));
        }
        if( StringUtils.isNotBlank(endDate)){
            query.le("createDate",DateUtils.stringToLong(endDate,"yyyy-MM-dd"));
        }
        query.addOrder("id", "desc");

        Page<Report> page = reportService.queryPage(query);
        return DataTableFactory.fitting(draw, page);
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
                reportService.delete(reportService.queryByPK(id));
            }else {
                Long[] ss = JsonUtil.json2Obj(ids,Long[].class);
                for (Long _id : ss) {
                    reportService.delete(reportService.queryByPK(_id));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

}
