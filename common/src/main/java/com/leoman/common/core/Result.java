package com.leoman.common.core;


import com.leoman.utils.BeanUtils;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yesong
 */
public final class Result {

    private Integer status;                                           // 状态 0:成功 1:失败（异常）
    private String msg;                                               // 错误信息
    private Map<String, Object> data = new HashMap<String, Object>();              // 数据内容

    private static final int SUCCESS = 0;                             // 成功
    private static final int ERROR = 1;                               // 失败(异常)

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public static Result success() {
        Result result = new Result();
        result.status = SUCCESS;
        result.msg = "";
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.status = SUCCESS;
        result.msg = "";
        if (data instanceof List) {
            result.data.put("list", BeanUtils.listToMap((List) data));
        } else if (data instanceof Page) {
            Page page = (Page) data;
            List list = page.getContent();
            Map<String, Object> pagemap = new HashMap<String, Object>();
            pagemap.put("totalNum", page.getTotalElements());
            pagemap.put("totalPage", page.getTotalPages());
            pagemap.put("currentPage", page.getNumber() + 1);
            result.data.put("page", pagemap);
            result.data.put("list", list);
        } else if (data instanceof Map) {
            result.data.put("object", data);
        } else {
            String objName = data.getClass().getSimpleName().substring(0, 1).toLowerCase() + data.getClass().getSimpleName().substring(1);
            result.data.put(objName, BeanUtils.beanToMap(data));
        }
        return result;
    }

    public static Result failure(ErrorType errorType) {
        Result result = new Result();
        result.status = errorType.getCode();
        result.msg = errorType.getName();
        return result;
    }

    public static Result failure() {
        Result result = new Result();
        result.status = ErrorType.ERROR_CODE_0001.getCode();
        result.msg = ErrorType.ERROR_CODE_0001.getName();
        return result;
    }
}