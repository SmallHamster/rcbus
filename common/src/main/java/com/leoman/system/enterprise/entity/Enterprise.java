package com.leoman.system.enterprise.entity;

import com.leoman.entity.BaseEntity;
import com.leoman.utils.RandomUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by 史龙 on 2016/9/6.
 */
@Entity
@Table(name = "t_enterprise")
public class Enterprise extends BaseEntity {

    //名称
    @Column(name = "name")
    private String name;

    //0: 企业 1:专线
    @Column(name = "type")
    private Integer type;

    //邀请码
    @Column(name = "invite_code")
    private String inviteCode = RandomUtil.getCode();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}
