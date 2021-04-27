package com.csxy.project.service;
import com.csxy.core.common.ServiceException;
import com.csxy.project.model.TbZwtjjl;
import com.csxy.core.common.Service;
import com.csxy.project.model.vo.TbZwtjjlVO;

import java.util.List;


public interface TbZwtjjlService extends Service<TbZwtjjl> {

    List<TbZwtjjlVO> queryZwtjjlListByUserId(TbZwtjjlVO tbZwtjjlVO) throws ServiceException;

    void saveZwtjjl(String zwtjjlJsonData) throws ServiceException;

    void delZwtjjl(String id) throws ServiceException;

    void updateZwtjjlSftdById(String id) throws ServiceException;
}
