package com.csxy.project.model;

import javax.persistence.*;

@Table(name = "tb_zwtjjl")
public class TbZwtjjl {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "DW_ID")
    private String dwId;

    /**
     * 职位主键
     */
    @Column(name = "ZW_ID")
    private String zwId;

    /**
     * 推荐用户主键
     */
    @Column(name = "TJ_USER_ID")
    private String tjUserId;

    /**
     * 推向有用户主键
     */
    @Column(name = "TX_USER_ID")
    private String txUserId;

    /**
     *  有效状态 1：有效、0：无效
     */
    @Column(name = "YXZT")
    private String yxzt;

    /**
     * 是否投递 1：已投递、0：未投递
     */
    @Column(name = "SFTD")
    private String sftd;

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

    public String getDwId() {
        return dwId;
    }

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
     * 获取推荐用户主键
     *
     * @return TJ_USER_ID - 推荐用户主键
     */
    public String getTjUserId() {
        return tjUserId;
    }

    /**
     * 设置推荐用户主键
     *
     * @param tjUserId 推荐用户主键
     */
    public void setTjUserId(String tjUserId) {
        this.tjUserId = tjUserId;
    }

    /**
     * 获取推向有用户主键
     *
     * @return TX_USER_ID - 推向有用户主键
     */
    public String getTxUserId() {
        return txUserId;
    }

    /**
     * 设置推向有用户主键
     *
     * @param txUserId 推向有用户主键
     */
    public void setTxUserId(String txUserId) {
        this.txUserId = txUserId;
    }

    /**
     * 获取 有效状态 1：有效、0：无效
     *
     * @return YXZT -  有效状态 1：有效、0：无效
     */
    public String getYxzt() {
        return yxzt;
    }

    /**
     * 设置 有效状态 1：有效、0：无效
     *
     * @param yxzt  有效状态 1：有效、0：无效
     */
    public void setYxzt(String yxzt) {
        this.yxzt = yxzt;
    }

    /**
     * 获取是否投递 1：已投递、0：未投递
     *
     * @return SFTD - 是否投递 1：已投递、0：未投递
     */
    public String getSftd() {
        return sftd;
    }

    /**
     * 设置是否投递 1：已投递、0：未投递
     *
     * @param sftd 是否投递 1：已投递、0：未投递
     */
    public void setSftd(String sftd) {
        this.sftd = sftd;
    }
}