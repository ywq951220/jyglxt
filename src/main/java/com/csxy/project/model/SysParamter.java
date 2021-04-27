package com.csxy.project.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_paramter")
public class SysParamter {
    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 参数名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 参数类型编码
     */
    @Column(name = "TYPECODE")
    private String typecode;

    /**
     * 参数值
     */
    @Column(name = "VALUE")
    private String value;

    /**
     * 排序
     */
    @Column(name = "SEQ")
    private Integer seq;

    /**
     * 创建时间
     */
    @Column(name = "CREATEDATE")
    private Date createdate;

    /**
     * 有效状态（1表示有效、0表示无效）
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
     * 获取参数名称
     *
     * @return NAME - 参数名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置参数名称
     *
     * @param name 参数名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取参数类型编码
     *
     * @return TYPECODE - 参数类型编码
     */
    public String getTypecode() {
        return typecode;
    }

    /**
     * 设置参数类型编码
     *
     * @param typecode 参数类型编码
     */
    public void setTypecode(String typecode) {
        this.typecode = typecode;
    }

    /**
     * 获取参数值
     *
     * @return VALUE - 参数值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置参数值
     *
     * @param value 参数值
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取排序
     *
     * @return SEQ - 排序
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * 设置排序
     *
     * @param seq 排序
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     * 获取创建时间
     *
     * @return CREATEDATE - 创建时间
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * 设置创建时间
     *
     * @param createdate 创建时间
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    /**
     * 获取有效状态（1表示有效、0表示无效）
     *
     * @return YXZT - 有效状态（1表示有效、0表示无效）
     */
    public String getYxzt() {
        return yxzt;
    }

    /**
     * 设置有效状态（1表示有效、0表示无效）
     *
     * @param yxzt 有效状态（1表示有效、0表示无效）
     */
    public void setYxzt(String yxzt) {
        this.yxzt = yxzt;
    }
}