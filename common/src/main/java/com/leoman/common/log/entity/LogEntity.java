package com.leoman.common.log.entity;


import com.leoman.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/5/22.
 */
@Entity
@Table(name = "t_log")
public class LogEntity extends BaseEntity {


    public static final int USER_TYPE_ADMIN = 0;
    public static final int USER_TYPE_USER = 1;

    public static final int LOG_TYPE_INFO = 0;
    public static final int LOG_TYPE_ERROR = 1;

    @Column(name = "message")
    private String message;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_type")
    private Integer userType;

    @Column(name = "url")
    private String url;

    @Column(name = "params")
    private String params;

    @Column(name = "log_type")
    private Integer logType;

    public String getMessage() {
        return message;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
