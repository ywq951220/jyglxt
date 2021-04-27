package com.csxy.project.model;

import javax.persistence.*;

@Table(name = "tb_zpdw_zw")
public class TbZpdwZw {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 单位主键
     */
    @Column(name = "DW_ID")
    private String dwId;

    /**
     * 职位名称
     */
    @Column(name = "ZWMC")
    private String zwmc;

    /**
     * 职位职责
     */
    @Column(name = "ZWZZ")
    private String zwzz;

    /**
     * 职位学历要求
     */
    @Column(name = "ZWXLYQ")
    private String zwxlyq;

    /**
     * 工作经验要求
     */
    @Column(name = "GZJYYQ")
    private String gzjyyq;

    /**
     * 上班地址
     */
    @Column(name = "SBDZ")
    private String sbdz;

    /**
     * 有效状态
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
     * 获取职位名称
     *
     * @return ZWMC - 职位名称
     */
    public String getZwmc() {
        return zwmc;
    }

    /**
     * 设置职位名称
     *
     * @param zwmc 职位名称
     */
    public void setZwmc(String zwmc) {
        this.zwmc = zwmc;
    }

    /**
     * 获取职位职责
     *
     * @return ZWZZ - 职位职责
     */
    public String getZwzz() {
        return zwzz;
    }

    /**
     * 设置职位职责
     *
     * @param zwzz 职位职责
     */
    public void setZwzz(String zwzz) {
        this.zwzz = zwzz;
    }

    /**
     * 获取职位学历要求
     *
     * @return ZWXLYQ - 职位学历要求
     */
    public String getZwxlyq() {
        return zwxlyq;
    }

    /**
     * 设置职位学历要求
     *
     * @param zwxlyq 职位学历要求
     */
    public void setZwxlyq(String zwxlyq) {
        this.zwxlyq = zwxlyq;
    }

    /**
     * 获取工作经验要求
     *
     * @return GZJYYQ - 工作经验要求
     */
    public String getGzjyyq() {
        return gzjyyq;
    }

    /**
     * 设置工作经验要求
     *
     * @param gzjyyq 工作经验要求
     */
    public void setGzjyyq(String gzjyyq) {
        this.gzjyyq = gzjyyq;
    }

    /**
     * 获取上班地址
     *
     * @return SBDZ - 上班地址
     */
    public String getSbdz() {
        return sbdz;
    }

    /**
     * 设置上班地址
     *
     * @param sbdz 上班地址
     */
    public void setSbdz(String sbdz) {
        this.sbdz = sbdz;
    }

    /**
     * 获取有效状态
     *
     * @return YXZT - 有效状态
     */
    public String getYxzt() {
        return yxzt;
    }

    /**
     * 设置有效状态
     *
     * @param yxzt 有效状态
     */
    public void setYxzt(String yxzt) {
        this.yxzt = yxzt;
    }
}