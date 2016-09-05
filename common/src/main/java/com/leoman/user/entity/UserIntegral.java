package com.leoman.user.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/12.
 */
@Table(name = "t_user_integral")
@Entity
public class UserIntegral extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @Column(name = "content")
    private String content;

    @Column(name = "count")
    private Integer count;

    @Column(name = "type")
    private Integer type;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
