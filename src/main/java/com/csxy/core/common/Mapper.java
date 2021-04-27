package com.csxy.core.common;

import com.csxy.core.common.mybatisExtend.impl.InsertListPlusMapper;
import com.csxy.core.common.mybatisExtend.impl.logicDelete;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;

public interface Mapper<T>
        extends
        BaseMapper<T>,
        ConditionMapper<T>,
        IdsMapper<T>,
        InsertListPlusMapper<T>,
        logicDelete<T>{
}
