package com.csxy.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_jltdjl")
public class TbJltdjl {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 用户主键
     */
    @Column(name = "USER_ID")
    private String userId;

    /**
     * 单位主键
     */
    @Column(name = "DW_ID")
    private String dwId;

    /**
     * 职位主键
     */
    @Column(name = "ZW_ID")
    private String zwId;

    /**
     * 审批状态 0：未审核、1：邀请面试、-1：拒绝面试
     */
    @Column(name = "SPZT")
    private String spzt;

    @Column(name = "SPSJ")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date spsj;

    /**
     * 审批意见
     */
    @Column(name = "SPYJ")
    private String spyj;

    /**
     * 面试时间
     */
    @Column(name = "MSSJ")
    private Date mssj;

    /**
     * 面试地址
     */
    @Column(name = "MSDZ")
    private String msdz;

    @Column(name = "lyzt")
    private String lyzt;

    @Column(name = "LYSJ")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lysj;

    /**
     * 有效状态 1：有效、0：无效
     */
    @Column(name = "YXZT")
    private String yxzt;

    @Column(name = "ZXYY")
    private String zxyy;

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
     * 获取用户主键
     *
     * @return USER_ID - 用户主键
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户主键
     *
     * @param userId 用户主键
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取单位主键
     *
     * @return DW_ID - 单位主键
     */
    public String getDwId() {
        return dwId;
    }

    /**
     * 设置单位主键
     *
     * @param dwId 单位主键
     */
    public void setDwId(String dwId) {
        this.dwId = dwId;
    }

    /**
     * 获取职位主键
     *
     * @return ZW_ID - 职位主键
     */
    public String getZwId() {
        return zwId;
    }

    /**
     * 设置职位主键
     *
     * @param zwId 职位主键
     */
    public void setZwId(String zwId) {
        this.zwId = zwId;
    }

    /**
     * 获取审批状态 0：未审核、1：邀请面试、-1：拒绝面试
     *
     * @return SPZT - 审批状态 0：未审核、1：邀请面试、-1：拒绝面试
     */
    public String getSpzt() {
        return spzt;
    }

    /**
     * 设置审批状态 0：未审核、1：邀请面试、-1：拒绝面试
     *
     * @param spzt 审批状态 0：未审核、1：邀请面试、-1：拒绝面试
     */
    public void setSpzt(String spzt) {
        this.spzt = spzt;
    }

    public Date getSpsj() {
        return spsj;
    }

    public void setSpsj(Date spsj) {
        this.spsj = spsj;
    }

    /**
     * 获取审批意见
     *
     * @return SPYJ - 审批意见
     */
    public String getSpyj() {
        return spyj;
    }

    /**
     * 设置审批意见
     *
     * @param spyj 审批意见
     */
    public void setSpyj(String spyj) {
        this.spyj = spyj;
    }

    /**
     * 获取面试时间
     *
     * @return MSSJ - 面试时间
     */
    public Date getMssj() {
        return mssj;
    }

    /**
     * 设置面试时间
     *
     * @param mssj 面试时间
     */
    public void setMssj(Date mssj) {
        this.mssj = mssj;
    }

    /**
     * 获取面试地址
     *
     * @return MSDZ - 面试地址
     */
    public String getMsdz() {
        return msdz;
    }

    /**
     * 设置面试地址
     *
     * @param msdz 面试地址
     */
    public void setMsdz(String msdz) {
        this.msdz = msdz;
    }

    public String getLyzt() {
        return lyzt;
    }

    public void setLyzt(String lyzt) {
        this.lyzt = lyzt;
    }

    public Date getLysj() {
        return lysj;
    }

    public void setLysj(Date lysj) {
        this.lysj = lysj;
    }

    /**
     * 获取有效状态 1：有效、0：无效
     *
     * @return YXZT - 有效状态 1：有效、0：无效
     */
    public String getYxzt() {
        return yxzt;
    }

    /**
     * 设置有效状态 1：有效、0：无效
     *
     * @param yxzt 有效状态 1：有效、0：无效
     */
    public void setYxzt(String yxzt) {
        this.yxzt = yxzt;
    }

    public String getZxyy() {
        return zxyy;
    }

    public void setZxyy(String zxyy) {
        this.zxyy = zxyy;
    }
}