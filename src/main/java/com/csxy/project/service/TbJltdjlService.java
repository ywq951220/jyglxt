package com.csxy.project.service;
import com.csxy.core.common.ServiceException;
import com.csxy.project.model.TbJltdjl;
import com.csxy.core.common.Service;
import com.csxy.project.model.vo.TbJlsqjlVO;

import java.util.List;


public interface TbJltdjlService extends Service<TbJltdjl> {

    String saveUserTdjlxx(String saveTdjlJsonData) throws ServiceException;

    List<TbJlsqjlVO> queryJltdjlByUserId(TbJlsqjlVO tbJlsqjlVO) throws ServiceException;

    void delJltdjlById(String id, String zxyy) throws ServiceException;

    void jlSh(String shDataJson) throws ServiceException;

    TbJlsqjlVO hasUserSflyByUserId(String userId, String spzt, String lyzt) throws ServiceException;

    void jlFk(String fkDataJson) throws ServiceException;
}
