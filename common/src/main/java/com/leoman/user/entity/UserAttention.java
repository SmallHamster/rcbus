package com.leoman.user.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/8.
 */
@Table(name = "t_user_attention")
@Entity
public class UserAttention extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserInfo user;

    @OneToOne
    @JoinColumn(name = "source_id")
    private UserInfo sourceUser;

    @Column(name = "status")
    private Integer status;

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public UserInfo getSourceUser() {
        return sourceUser;
    }

    public void setSourceUser(UserInfo sourceUser) {
        this.sourceUser = sourceUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
