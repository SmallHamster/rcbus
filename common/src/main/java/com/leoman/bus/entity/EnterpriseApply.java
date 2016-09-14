package com.leoman.bus.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.image.entity.Image;

import javax.persistence.*;

/**
 *
 * 企业报名信息实体类
 * Created by Daisy on 2016/9/14.
 */
@Entity
@Table(name = "t_enterprise_apply")
public class EnterpriseApply extends BaseEntity{

    @Column(name = "username")
    private String username;//联系人

    @Column(name = "mobile")
    private String mobile;//联系人电话

    @Column(name = "enterprise_name")
    private String enterpriseName;//企业名称

    @Column(name = "enterprise_address")
    private String enterpriseAddress;//企业地址

    @Column(name = "remark")
    private String remark;//备注

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseAddress() {
        return enterpriseAddress;
    }

    public void setEnterpriseAddress(String enterpriseAddress) {
        this.enterpriseAddress = enterpriseAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
