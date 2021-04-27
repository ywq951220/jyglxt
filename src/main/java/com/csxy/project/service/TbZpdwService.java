package com.csxy.project.service;
import com.csxy.core.common.ServiceException;
import com.csxy.project.model.TbZpdw;
import com.csxy.core.common.Service;
import com.csxy.project.model.vo.SysUserVO;
import com.csxy.project.model.vo.TbZpdwVO;

import java.util.List;


public interface TbZpdwService extends Service<TbZpdw> {

    List<TbZpdw> getZpdwList(TbZpdwVO tbZpdwVO) throws ServiceException;

    void savezpdwXx(String zpxxJsonData, SysUserVO sysUserVO) throws ServiceException;

    void dwsh(String dwshData) throws ServiceException;

    void delZpdwXx(String id) throws ServiceException;
}
