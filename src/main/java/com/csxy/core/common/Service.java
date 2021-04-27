package com.csxy.core.common;

import java.util.List;

public interface Service<T> {
    void save(T model);//持久化
    void deleteById(String id);//通过主鍵刪除
    void update(T model);//更新
    T findById(String id);//通过ID查找
    List<T> findAll();//获取所有
    List<T> find(T model);
    List<T> find();
}
