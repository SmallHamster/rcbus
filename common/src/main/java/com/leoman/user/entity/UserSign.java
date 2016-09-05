package com.leoman.user.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/12.
 */
@Table(name = "t_user_sign")
@Entity
public class UserSign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @Column(name = "sign_date")
    private String singDate;

    @Column(name = "days")
    private Integer days;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getSingDate() {
        return singDate;
    }

    public void setSingDate(String singDate) {
        this.singDate = singDate;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
