package com.csxy.project.service;
import com.csxy.core.common.ServiceException;
import com.csxy.project.model.SysParamter;
import com.csxy.core.common.Service;

import java.util.List;


public interface SysParamterService extends Service<SysParamter> {

    List<SysParamter> getParamterByTypeCode(String typeCode) throws ServiceException;
}
