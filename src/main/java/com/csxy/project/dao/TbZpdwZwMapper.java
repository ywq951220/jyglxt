package com.csxy.project.dao;

import com.csxy.core.common.Mapper;
import com.csxy.project.model.TbZpdwZw;

import java.util.List;

public interface TbZpdwZwMapper extends Mapper<TbZpdwZw> {

    List<TbZpdwZw> queryZwxxByDwId(TbZpdwZw tbZpdwZw);
}