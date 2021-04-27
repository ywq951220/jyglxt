package com.csxy.core.common;


import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractService<T> implements Service<T> {

    @Autowired
    protected Mapper<T> mapper;

    @Autowired
    private ServletContext servletContext;

    private Class<T> modelClass;

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public void save(T model) {
        mapper.insertSelective(model);
    }


    @Override
    public void deleteById(String id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(T model) {
        mapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public T findById(String id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }

    @Override
    public List<T> find(T model) {
        return mapper.select(model);
    }

    @Override
    public List<T> find() {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField("flag");
            field.setAccessible(true);
            field.set(model, "1");
            return mapper.select(model);
        } catch (ReflectiveOperationException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
