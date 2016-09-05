package com.leoman.exception;

import com.leoman.permissions.admin.entity.Admin;
import com.leoman.common.log.entity.LogEntity;
import com.leoman.common.log.service.impl.LogServiceImpl;
import com.leoman.entity.Constant;
import com.leoman.utils.BeanUtils;
import com.leoman.utils.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wangbin on 2015/8/14.
 */
public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {

    private Logger LOGGER = LoggerFactory.getLogger(CustomSimpleMappingExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler,
                                         Exception ex) {
        // 记录错误日志
        LOGGER.info("============================================");
        Admin admin = (Admin) request.getSession().getAttribute(Constant.SESSION_MEMBER_GLOBLE);
        LOGGER.info(admin.getUsername());
        LOGGER.info(request.getQueryString());
        LOGGER.info(request.getRequestURI().toLowerCase());

        StringBuffer buffer = new StringBuffer();
        buffer.append(ex.toString());
        buffer.append("\n");
        StackTraceElement[] elements = ex.getStackTrace();
        for (StackTraceElement element : elements) {
            buffer.append("\t");
            buffer.append(element.toString() + "\n");
        }
        System.out.println("异常信息" + buffer.toString());

        LogServiceImpl logService = (LogServiceImpl) BeanUtils.getBean("logService");
        LogEntity logEntity = new LogEntity();
        logEntity.setMessage(buffer.toString());
        logEntity.setLogType(LogEntity.LOG_TYPE_ERROR);
        logEntity.setUrl(request.getRequestURI().toLowerCase());
        logEntity.setParams(request.getQueryString());
        logEntity.setUserType(LogEntity.USER_TYPE_USER);
        logService.save(logEntity);

        LOGGER.info("============================================");


        // Expose ModelAndView for chosen error view.
        String viewName = determineViewName(ex, request);

        System.out.println(request.getHeader("accept"));
        System.out.println(request.getHeader("X-Requested-With"));

        if (viewName != null) {// JSP格式返回
            if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
                    .getHeader("X-Requested-With")!= null && request
                    .getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
                // 如果不是异步请求
                // Apply HTTP status code for error views, if specified.
                // Only apply it if we're processing a top-level request.
                Integer statusCode = determineStatusCode(request, viewName);
                if (statusCode != null) {
                    applyStatusCodeIfPossible(request, response, statusCode);
                }
                return getModelAndView(viewName, ex, request);
            } else {// JSON格式返回
                WebUtil.print(response,new com.leoman.common.core.bean.Result(false).msg(ex.getMessage()));
                return null;
            }
        } else {
            return null;
        }
    }
}