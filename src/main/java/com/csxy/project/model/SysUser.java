package com.csxy.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_user")
public class SysUser {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 姓名
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 性别、typecode=XB
     */
    @Column(name = "SEX")
    private String sex;

    /**
     * 身份证
     */
    @Column(name = "SFZ")
    private String sfz;

    @Column(name = "LXDH")
    private String lxdh;

    /**
     * 用户名
     */
    @Column(name = "USER_NAME")
    private String userName;

    /**
     * 密码
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * 角色
     */
    @Column(name = "ROLE")
    private String role;

    /**
     * 注册时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "ZCSJ")
    private Date zcsj;

    /**
     * 有效状态（1有效、0无效）
     */
    @Column(name = "YXZT")
    private String yxzt;

    /**
     * 获取主键
     *
     * @return ID - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取姓名
     *
     * @return NAME - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取性别、typecode=XB
     *
     * @return SEX - 性别、typecode=XB
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别、typecode=XB
     *
     * @param sex 性别、typecode=XB
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取身份证
     *
     * @return SFZ - 身份证
     */
    public String getSfz() {
        return sfz;
    }

    /**
     * 设置身份证
     *
     * @param sfz 身份证
     */
    public void setSfz(String sfz) {
        this.sfz = sfz;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    /**
     * 获取用户名
     *
     * @return USER_NAME - 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取密码
     *
     * @return PASSWORD - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取注册时间
     *
     * @return ZCSJ - 注册时间
     */
    public Date getZcsj() {
        return zcsj;
    }

    /**
     * 获取角色
     *
     * @return ROLE - 角色
     */
    public String getRole() {
        return role;
    }

    /**
     * 设置角色
     *
     * @param role 角色
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * 设置注册时间
     *
     * @param zcsj 注册时间
     */
    public void setZcsj(Date zcsj) {
        this.zcsj = zcsj;
    }

    /**
     * 获取有效状态（1有效、0无效）
     *
     * @return YXZT - 有效状态（1有效、0无效）
     */
    public String getYxzt() {
        return yxzt;
    }

    /**
     * 设置有效状态（1有效、0无效）
     *
     * @param yxzt 有效状态（1有效、0无效）
     */
    public void setYxzt(String yxzt) {
        this.yxzt = yxzt;
    }
}