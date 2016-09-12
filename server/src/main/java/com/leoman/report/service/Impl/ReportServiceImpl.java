package com.leoman.report.service.Impl;

import com.leoman.common.service.impl.GenericManagerImpl;
import com.leoman.report.dao.ReportDao;
import com.leoman.report.entity.Report;
import com.leoman.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/9/12.
 */
@Service
public class ReportServiceImpl extends GenericManagerImpl<Report,ReportDao> implements ReportService{

    @Autowired
    private ReportDao reportDao;

}
