package com.leoman.permissions.module.entity.vo;

import com.leoman.permissions.module.entity.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * 模块VO
 * Created by yesong on 2016/8/29.
 */
public class ModuleVo {

    private Long id;

    private String name;

    private String url;

    private String moduleIcon;

    private List<ModuleVo> list = new ArrayList<ModuleVo>();

    public String getModuleIcon() {
        return moduleIcon;
    }

    public void setModuleIcon(String moduleIcon) {
        this.moduleIcon = moduleIcon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ModuleVo> getList() {
        return list;
    }

    public void setList(List<ModuleVo> list) {
        this.list = list;
    }

    public void addModule(Module module) {
        ModuleVo vo = new ModuleVo();
        vo.setId(module.getId());
        vo.setName(module.getName());
        vo.setUrl(module.getUrl());
        this.list.add(vo);
    }
}
