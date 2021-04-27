package com.csxy.project.dao;

import com.csxy.core.common.Mapper;
import com.csxy.project.model.TbZpdw;
import com.csxy.project.model.vo.TbZpdwVO;

import java.util.List;

public interface TbZpdwMapper extends Mapper<TbZpdw> {

    List<TbZpdw> getZpdwList(TbZpdwVO tbZpdwVO);

    TbZpdw queryZpdwByUserId(String userId);
}