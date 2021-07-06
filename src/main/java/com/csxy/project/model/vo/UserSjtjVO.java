package com.csxy.project.model.vo;

public class UserSjtjVO {
    private String jyqk; //就业情况
    private Integer jyqkzs = 0; //就业情况在总数

    private String roleName; //角色名称
    private Integer userzs = 0; //用户总数

    public String getJyqk() {
        return jyqk;
    }

    public void setJyqk(String jyqk) {
        this.jyqk = jyqk;
    }

    public Integer getJyqkzs() {
        return jyqkzs;
    }

    public void setJyqkzs(Integer jyqkzs) {
        this.jyqkzs = jyqkzs;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getUserzs() {
        return userzs;
    }

    public void setUserzs(Integer userzs) {
        this.userzs = userzs;
    }
}
