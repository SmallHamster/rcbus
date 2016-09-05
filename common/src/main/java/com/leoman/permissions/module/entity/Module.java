package com.leoman.permissions.module.entity;

import com.leoman.entity.BaseEntity;
import com.sun.org.apache.xpath.internal.operations.Mod;

import javax.persistence.*;
import java.util.List;

/**
 *
 * 模块实体类
 * Created by yesong on 2016/8/29.
 */
@Entity
@Table(name = "t_module")
public class Module extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "module_icon")
    private String moduleIcon;

    @ManyToOne
    @JoinColumn(name = "pid")
    private Module superModule;

//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "pid")
//    private List<Module> subModuleList;

    @Column(name = "code")
    private Integer code;

    @Column(name = "description")
    private String description;

//    public List<Module> getSubModuleList() {
//        return subModuleList;
//    }
//
//    public void setSubModuleList(List<Module> subModuleList) {
//        this.subModuleList = subModuleList;
//    }

    public String getModuleIcon() {
        return moduleIcon;
    }

    public void setModuleIcon(String moduleIcon) {
        this.moduleIcon = moduleIcon;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Module getSuperModule() {
        return superModule;
    }

    public void setSuperModule(Module superModule) {
        this.superModule = superModule;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
