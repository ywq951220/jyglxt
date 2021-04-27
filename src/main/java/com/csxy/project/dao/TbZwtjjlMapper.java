package com.csxy.project.dao;

import com.csxy.core.common.Mapper;
import com.csxy.project.model.TbZwtjjl;
import com.csxy.project.model.vo.TbZwtjjlVO;

import java.util.List;

public interface TbZwtjjlMapper extends Mapper<TbZwtjjl> {

    List<TbZwtjjlVO> queryZwtjjlListByUserId(TbZwtjjlVO tbZwtjjlVO);
}