package com.leoman.user.entity;

import com.leoman.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/6/12.
 */
@Table(name = "t_user_info")
@Entity
public class UserInfo extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserLogin userLogin;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname = "";

    @Column(name = "gender")
    private Integer gender = 0;

    @Column(name = "avater")
    private String avater = "";

    @Column(name = "status")
    private Integer status = 0;

    @Column(name = "level")
    private Integer level = 0;

    @Column(name = "funs")
    private Long funs = 0L;

    @Column(name = "focus")
    private Long focus = 0L;

    @Column(name = "posts")
    private Integer posts = 0;

    @Column(name = "praises")
    private Long praises = 0L;

    @Column(name = "coin")
    private Long coin = 0L;

    @Column(name = "integral")
    private Long integral = 0L;

    @Column(name = "all_integral")
    private Long allIntegral = 0L;

    @Column(name = "is_creator")
    private Integer isCreator = 0;


    @Column(name = "weixin")
    private String weixin;

    @Column(name = "weibo")
    private String weibo;

    @Transient
    private Integer num = 0;

    @Transient
    private Integer isAttention;

    public UserLogin getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getFuns() {
        return funs;
    }

    public void setFuns(Long funs) {
        this.funs = funs;
    }

    public Long getFocus() {
        return focus;
    }

    public void setFocus(Long focus) {
        this.focus = focus;
    }

    public Integer getPosts() {
        return posts;
    }

    public void setPosts(Integer posts) {
        this.posts = posts;
    }

    public Long getPraises() {
        return praises;
    }

    public void setPraises(Long praises) {
        this.praises = praises;
    }

    public Long getCoin() {
        return coin;
    }

    public void setCoin(Long coin) {
        this.coin = coin;
    }

    public Long getIntegral() {
        return integral;
    }

    public void setIntegral(Long integral) {
        this.integral = integral;
    }

    public Long getAllIntegral() {
        return allIntegral;
    }

    public void setAllIntegral(Long allIntegral) {
        this.allIntegral = allIntegral;
    }

    public Integer getIsCreator() {
        return isCreator;
    }

    public void setIsCreator(Integer isCreator) {
        this.isCreator = isCreator;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(Integer isAttention) {
        this.isAttention = isAttention;
    }
}
