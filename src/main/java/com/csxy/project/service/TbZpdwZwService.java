package com.csxy.project.service;
import com.csxy.core.common.ServiceException;
import com.csxy.project.model.TbZpdwZw;
import com.csxy.core.common.Service;

import java.util.List;


public interface TbZpdwZwService extends Service<TbZpdwZw> {

    List<TbZpdwZw> queryZwxxByDwId(TbZpdwZw tbZpdwZw) throws ServiceException;

    void saveZwxx(String zwxxJszwxxJsonDataonStr) throws ServiceException;

    void delZwXx(String id) throws ServiceException;
}
