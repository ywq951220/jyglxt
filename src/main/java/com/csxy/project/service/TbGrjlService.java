package com.csxy.project.service;
import com.csxy.core.common.ServiceException;
import com.csxy.project.model.TbGrjl;
import com.csxy.core.common.Service;
import com.csxy.project.model.vo.SysUserVO;
import com.csxy.project.model.vo.TbGrjlVO;


public interface TbGrjlService extends Service<TbGrjl> {

    void saveGrjlXx(String grjlXxJsonData, SysUserVO sysUserVO) throws ServiceException;

    TbGrjlVO queryGrjlByUserId(String userId) throws ServiceException;

    Boolean hasGrjlByUserId(String userId) throws ServiceException;
}
