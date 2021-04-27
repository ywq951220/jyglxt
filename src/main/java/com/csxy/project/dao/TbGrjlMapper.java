package com.csxy.project.dao;

import com.csxy.core.common.Mapper;
import com.csxy.project.model.TbGrjl;
import com.csxy.project.model.vo.TbGrjlVO;

public interface TbGrjlMapper extends Mapper<TbGrjl> {

    TbGrjlVO queryGrjlByUserId(String userId);
}