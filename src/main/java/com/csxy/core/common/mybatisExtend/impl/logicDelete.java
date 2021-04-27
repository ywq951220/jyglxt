package com.csxy.core.common.mybatisExtend.impl;

import com.csxy.core.common.mybatisExtend.LogicDeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;

public interface logicDelete<T> {

    @InsertProvider(type = LogicDeleteProvider.class, method = "logicDelete")
    void logicDelete(String id);

}