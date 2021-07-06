package com.csxy.project.model.vo;

import com.csxy.project.model.TbZpdw;

public class TbZpdwVO extends TbZpdw {
    private String role;

    private Integer lyzs = 0; //录用总数

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getLyzs() {
        return lyzs;
    }

    public void setLyzs(Integer lyzs) {
        this.lyzs = lyzs;
    }
}
