package com.csxy.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "tb_grjl")
public class TbGrjl {
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
     * 出生日期
     */
    @Column(name = "CSRQ")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date csrq;

    /**
     * 身高
     */
    @Column(name = "SG")
    private Integer sg;

    /**
     * 民族
     */
    @Column(name = "MZ")
    private String mz;

    /**
     * 政治面貌
     */
    @Column(name = "ZZMM")
    private String zzmm;

    /**
     * 家庭住址
     */
    @Column(name = "JTZZ")
    private String jtzz;

    /**
     * 电子邮箱
     */
    @Column(name = "DZYX")
    private String dzyx;

    /**
     * 毕业院校
     */
    @Column(name = "BYYX")
    private String byyx;

    /**
     * 最高学历
     */
    @Column(name = "ZGXL")
    private String zgxl;

    /**
     * 意向职位
     */
    @Column(name = "YXZW")
    private String yxzw;

    /**
     * 所学专业
     */
    @Column(name = "SXZY")
    private String sxzy;

    /**
     * 主修课程
     */
    @Column(name = "ZXKC")
    private String zxkc;

    /**
     * 学校经历
     */
    @Column(name = "XXJL")
    private String xxjl;

    /**
     * 获奖情况
     */
    @Column(name = "HJQK")
    private String hjqk;

    /**
     * 自我评价
     */
    @Column(name = "ZWPJ")
    private String zwpj;

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
     * 获取出生日期
     *
     * @return CSRQ - 出生日期
     */
    public Date getCsrq() {
        return csrq;
    }

    /**
     * 设置出生日期
     *
     * @param csrq 出生日期
     */
    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    /**
     * 获取身高
     *
     * @return SG - 身高
     */
    public Integer getSg() {
        return sg;
    }

    /**
     * 设置身高
     *
     * @param sg 身高
     */
    public void setSg(Integer sg) {
        this.sg = sg;
    }

    /**
     * 获取民族
     *
     * @return MZ - 民族
     */
    public String getMz() {
        return mz;
    }

    /**
     * 设置民族
     *
     * @param mz 民族
     */
    public void setMz(String mz) {
        this.mz = mz;
    }

    /**
     * 获取政治面貌
     *
     * @return ZZMM - 政治面貌
     */
    public String getZzmm() {
        return zzmm;
    }

    /**
     * 设置政治面貌
     *
     * @param zzmm 政治面貌
     */
    public void setZzmm(String zzmm) {
        this.zzmm = zzmm;
    }

    /**
     * 获取家庭住址
     *
     * @return JTZZ - 家庭住址
     */
    public String getJtzz() {
        return jtzz;
    }

    /**
     * 设置家庭住址
     *
     * @param jtzz 家庭住址
     */
    public void setJtzz(String jtzz) {
        this.jtzz = jtzz;
    }

    /**
     * 获取电子邮箱
     *
     * @return DZYX - 电子邮箱
     */
    public String getDzyx() {
        return dzyx;
    }

    /**
     * 设置电子邮箱
     *
     * @param dzyx 电子邮箱
     */
    public void setDzyx(String dzyx) {
        this.dzyx = dzyx;
    }

    /**
     * 获取毕业院校
     *
     * @return BYYX - 毕业院校
     */
    public String getByyx() {
        return byyx;
    }

    /**
     * 设置毕业院校
     *
     * @param byyx 毕业院校
     */
    public void setByyx(String byyx) {
        this.byyx = byyx;
    }

    /**
     * 获取最高学历
     *
     * @return ZGXL - 最高学历
     */
    public String getZgxl() {
        return zgxl;
    }

    /**
     * 设置最高学历
     *
     * @param zgxl 最高学历
     */
    public void setZgxl(String zgxl) {
        this.zgxl = zgxl;
    }

    /**
     * 获取意向职位
     *
     * @return YXZW - 意向职位
     */
    public String getYxzw() {
        return yxzw;
    }

    /**
     * 设置意向职位
     *
     * @param yxzw 意向职位
     */
    public void setYxzw(String yxzw) {
        this.yxzw = yxzw;
    }

    /**
     * 获取所学专业
     *
     * @return SXZY - 所学专业
     */
    public String getSxzy() {
        return sxzy;
    }

    /**
     * 设置所学专业
     *
     * @param sxzy 所学专业
     */
    public void setSxzy(String sxzy) {
        this.sxzy = sxzy;
    }

    /**
     * 获取主修课程
     *
     * @return ZXKC - 主修课程
     */
    public String getZxkc() {
        return zxkc;
    }

    /**
     * 设置主修课程
     *
     * @param zxkc 主修课程
     */
    public void setZxkc(String zxkc) {
        this.zxkc = zxkc;
    }

    /**
     * 获取学校经历
     *
     * @return XXJL - 学校经历
     */
    public String getXxjl() {
        return xxjl;
    }

    /**
     * 设置学校经历
     *
     * @param xxjl 学校经历
     */
    public void setXxjl(String xxjl) {
        this.xxjl = xxjl;
    }

    /**
     * 获取获奖情况
     *
     * @return HJQK - 获奖情况
     */
    public String getHjqk() {
        return hjqk;
    }

    /**
     * 设置获奖情况
     *
     * @param hjqk 获奖情况
     */
    public void setHjqk(String hjqk) {
        this.hjqk = hjqk;
    }

    /**
     * 获取自我评价
     *
     * @return ZWPJ - 自我评价
     */
    public String getZwpj() {
        return zwpj;
    }

    /**
     * 设置自我评价
     *
     * @param zwpj 自我评价
     */
    public void setZwpj(String zwpj) {
        this.zwpj = zwpj;
    }

    public String getYxzt() {
        return yxzt;
    }

    public void setYxzt(String yxzt) {
        this.yxzt = yxzt;
    }
}