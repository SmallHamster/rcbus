package com.leoman.user.entity.vo;

import java.io.Serializable;

/**
 * 上传设置
 * Created by 史龙 on 2016/9/14.
 */
public class UserExportVo implements Serializable {

    private String mobile;

    private String enterprise;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }
}
