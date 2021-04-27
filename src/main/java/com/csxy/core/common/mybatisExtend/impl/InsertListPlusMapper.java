package com.csxy.core.common.mybatisExtend.impl;

import com.csxy.core.common.mybatisExtend.InsertListPlusProvider;
import org.apache.ibatis.annotations.InsertProvider;

import java.util.List;

public interface InsertListPlusMapper<T> {

    @InsertProvider(type = InsertListPlusProvider.class, method = "dynamicSQL")
    int insertList(List<T> recordList);
}