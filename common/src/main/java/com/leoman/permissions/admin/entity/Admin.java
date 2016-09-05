package com.leoman.permissions.admin.entity;

import com.leoman.entity.BaseEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * Created by yesong on 2016/6/14 0014.
 */
@Table(name = "t_admin")
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Admin extends BaseEntity {

    @Column(name = "username")
    private String username = "";

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "password")
    private String password = "";


    @Column(name = "last_login_date")
    private Long lastLoginDate;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Long lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    @Override
    public String toString() {
        return "username :" + this.username + "password:" + this.password;
    }
}
