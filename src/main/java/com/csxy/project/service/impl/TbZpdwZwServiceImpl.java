package com.csxy.project.service.impl;

import com.csxy.core.common.ServiceException;
import com.csxy.core.util.ByUtils;
import com.csxy.core.util.JsonUtils;
import com.csxy.core.util.StringUtils;
import com.csxy.project.dao.TbZpdwMapper;
import com.csxy.project.dao.TbZpdwZwMapper;
import com.csxy.project.model.TbZpdw;
import com.csxy.project.model.TbZpdwZw;
import com.csxy.project.service.TbZpdwZwService;
import com.csxy.core.common.AbstractService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class TbZpdwZwServiceImpl extends AbstractService<TbZpdwZw> implements TbZpdwZwService {
    @Resource
    private TbZpdwZwMapper tbZpdwZwMapper;

    @Resource
    private TbZpdwMapper tbZpdwMapper;

    @Override
    public List<TbZpdwZw> queryZwxxByDwId(TbZpdwZw tbZpdwZw) throws ServiceException {
        if (null == tbZpdwZw || StringUtils.isEmpty(tbZpdwZw.getDwId())) {
            throw new ServiceException("获取的单位信息主键为空！");
        }
        List<TbZpdwZw> list = tbZpdwZwMapper.queryZwxxByDwId(tbZpdwZw);
        if (null == list) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public void saveZwxx(String zwxxJsonData) throws ServiceException {
        TbZpdwZw tbZpdwZw = (TbZpdwZw) JsonUtils.getBeanFromJsonData(zwxxJsonData, TbZpdwZw.class);
        if (null == tbZpdwZw || StringUtils.isEmpty(tbZpdwZw.getDwId())) {
            throw new ServiceException("获取的单位主键信息为空！");
        }
        TbZpdw tbZpdw = tbZpdwMapper.selectByPrimaryKey(tbZpdwZw.getDwId());
        if (null == tbZpdw || StringUtils.isEmpty(tbZpdw.getId())) {
            throw new ServiceException("未找到单位信息！");
        }
        String id = tbZpdwZw.getId();
        if (StringUtils.isEmpty(id)) {
            //新增
            id = UUID.randomUUID().toString().replaceAll("-", "");
            tbZpdwZw.setId(id);
            tbZpdwZw.setYxzt("1");
            tbZpdwZwMapper.insertSelective(tbZpdwZw);
        } else {
            //修改
            TbZpdwZw oldZpdwZw = tbZpdwZwMapper.selectByPrimaryKey(tbZpdwZw.getId());
            if (null == oldZpdwZw || StringUtils.isEmpty(oldZpdwZw.getId())) {
                throw new ServiceException("未查询到相关职位信息！");
            }
            BeanUtils.copyProperties(tbZpdwZw, oldZpdwZw, ByUtils.getNullPropertyNames(tbZpdwZw));
            tbZpdwZwMapper.updateByPrimaryKey(oldZpdwZw);
        }
    }

    @Override
    public void delZwXx(String id) throws ServiceException {
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException("获取的职位主键为空！");
        }
        TbZpdwZw tbZpdwZw = tbZpdwZwMapper.selectByPrimaryKey(id);
        if (null == tbZpdwZw || StringUtils.isEmpty(tbZpdwZw.getId())) {
            throw new ServiceException("未查询到相关职位信息！");
        }
        tbZpdwZw.setYxzt("0");
        tbZpdwZwMapper.updateByPrimaryKey(tbZpdwZw);
    }
}
