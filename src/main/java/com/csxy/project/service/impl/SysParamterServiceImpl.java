package com.csxy.project.service.impl;

import com.csxy.core.common.ServiceException;
import com.csxy.core.util.StringUtils;
import com.csxy.project.dao.SysParamterMapper;
import com.csxy.project.model.SysParamter;
import com.csxy.project.service.SysParamterService;
import com.csxy.core.common.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional
public class SysParamterServiceImpl extends AbstractService<SysParamter> implements SysParamterService {
    @Resource
    private SysParamterMapper sysParamterMapper;

    @Override
    public List<SysParamter> getParamterByTypeCode(String typeCode) throws ServiceException {
        if (StringUtils.isEmpty(typeCode)) {
            throw new ServiceException("获取的参数类型为空！");
        }
        List<SysParamter> list = sysParamterMapper.getParamterByTypeCode(typeCode);
        return list;
    }
}
