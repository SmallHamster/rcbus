package com.leoman.user.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.system.enterprise.entity.Enterprise;

import javax.persistence.*;

/**
 * 用户
 * Created by 史龙 on 2016/9/6.
 */
@Table(name = "t_user_info")
@Entity
public class UserInfo extends BaseEntity {

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "wechat_id")
    private WeChatUser weChatUser;

    @Column(name = "mobile")
    private String mobile;

//    @Column(name = "password")
//    private String password;

    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    //会员类型 0:企业管理 1:员工 2:普通会员
    @Column(name = "type")
    private Integer type;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public WeChatUser getWeChatUser() {
        return weChatUser;
    }

    public void setWeChatUser(WeChatUser weChatUser) {
        this.weChatUser = weChatUser;
    }
}
