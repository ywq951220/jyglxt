package com.csxy.project.dao;

import com.csxy.core.common.Mapper;
import com.csxy.project.model.TbJltdjl;
import com.csxy.project.model.vo.TbJlsqjlVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbJltdjlMapper extends Mapper<TbJltdjl> {

    TbJltdjl queryTdjlBythridId(TbJltdjl tbJltdjl);

    List<TbJlsqjlVO> queryQzzTdjlByUserId(@Param("userId") String userId, @Param("spzt") String spzt, @Param("lyzt") String lyzt);

    List<TbJlsqjlVO> queryQymsjlByUserId(String userId);
}