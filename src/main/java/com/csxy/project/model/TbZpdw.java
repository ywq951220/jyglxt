package com.csxy.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_zpdw")
public class TbZpdw {
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
     * 公司名称
     */
    @Column(name = "GSMC")
    private String gsmc;

    /**
     * 成立时间
     */
    @Column(name = "CLSJ")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date clsj;

    /**
     * 法人代表
     */
    @Column(name = "FRDB")
    private String frdb;

    /**
     * 座机电话
     */
    @Column(name = "ZJDH")
    private String zjdh;

    /**
     * 公司地址
     */
    @Column(name = "GSDZ")
    private String gsdz;

    /**
     * 公司福利
     */
    @Column(name = "GSFL")
    private String gsfl;

    /**
     * 公司简介
     */
    @Column(name = "GSJJ")
    private String gsjj;

    @Column(name = "SHZT")
    private String shzt;

    @Column(name = "SHYJ")
    private String shyj;

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
     * 获取公司名称
     *
     * @return GSMC - 公司名称
     */
    public String getGsmc() {
        return gsmc;
    }

    /**
     * 设置公司名称
     *
     * @param gsmc 公司名称
     */
    public void setGsmc(String gsmc) {
        this.gsmc = gsmc;
    }

    /**
     * 获取成立时间
     *
     * @return CLSJ - 成立时间
     */
    public Date getClsj() {
        return clsj;
    }

    /**
     * 设置成立时间
     *
     * @param clsj 成立时间
     */
    public void setClsj(Date clsj) {
        this.clsj = clsj;
    }

    /**
     * 获取法人代表
     *
     * @return FRDB - 法人代表
     */
    public String getFrdb() {
        return frdb;
    }

    /**
     * 设置法人代表
     *
     * @param frdb 法人代表
     */
    public void setFrdb(String frdb) {
        this.frdb = frdb;
    }

    /**
     * 获取座机电话
     *
     * @return ZJDH - 座机电话
     */
    public String getZjdh() {
        return zjdh;
    }

    /**
     * 设置座机电话
     *
     * @param zjdh 座机电话
     */
    public void setZjdh(String zjdh) {
        this.zjdh = zjdh;
    }

    /**
     * 获取公司地址
     *
     * @return GSDZ - 公司地址
     */
    public String getGsdz() {
        return gsdz;
    }

    /**
     * 设置公司地址
     *
     * @param gsdz 公司地址
     */
    public void setGsdz(String gsdz) {
        this.gsdz = gsdz;
    }

    /**
     * 获取公司福利
     *
     * @return GSFL - 公司福利
     */
    public String getGsfl() {
        return gsfl;
    }

    /**
     * 设置公司福利
     *
     * @param gsfl 公司福利
     */
    public void setGsfl(String gsfl) {
        this.gsfl = gsfl;
    }

    /**
     * 获取公司简介
     *
     * @return GSJJ - 公司简介
     */
    public String getGsjj() {
        return gsjj;
    }

    /**
     * 设置公司简介
     *
     * @param gsjj 公司简介
     */
    public void setGsjj(String gsjj) {
        this.gsjj = gsjj;
    }

    public String getShzt() {
        return shzt;
    }

    public void setShzt(String shzt) {
        this.shzt = shzt;
    }

    public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj;
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