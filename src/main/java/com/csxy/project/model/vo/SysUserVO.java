package com.csxy.project.model.vo;

import com.csxy.project.model.SysUser;

public class SysUserVO extends SysUser {

    private String token;
    private String xbmc;
    private String roleName;
    private String grjlId;

    private String jltdjlId;
    private String spzt;
    private String lyzt;

    private String gsmc;
    private String zwmc;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getXbmc() {
        return xbmc;
    }

    public void setXbmc(String xbmc) {
        this.xbmc = xbmc;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getGrjlId() {
        return grjlId;
    }

    public void setGrjlId(String grjlId) {
        this.grjlId = grjlId;
    }

    public String getJltdjlId() {
        return jltdjlId;
    }

    public void setJltdjlId(String jltdjlId) {
        this.jltdjlId = jltdjlId;
    }

    public String getSpzt() {
        return spzt;
    }

    public void setSpzt(String spzt) {
        this.spzt = spzt;
    }

    public String getLyzt() {
        return lyzt;
    }

    public void setLyzt(String lyzt) {
        this.lyzt = lyzt;
    }

    public String getGsmc() {
        return gsmc;
    }

    public void setGsmc(String gsmc) {
        this.gsmc = gsmc;
    }

    public String getZwmc() {
        return zwmc;
    }

    public void setZwmc(String zwmc) {
        this.zwmc = zwmc;
    }
}
