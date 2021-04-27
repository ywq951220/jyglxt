package com.csxy.project.dao;

import com.csxy.core.common.Mapper;
import com.csxy.project.model.SysParamter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysParamterMapper extends Mapper<SysParamter> {

    List<SysParamter> getParamterByTypeCode(@Param("typeCode") String typeCode);
}