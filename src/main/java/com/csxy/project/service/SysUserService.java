package com.csxy.project.service;
import com.csxy.core.common.ServiceException;
import com.csxy.project.model.SysUser;
import com.csxy.core.common.Service;
import com.csxy.project.model.vo.SysUserVO;

import java.util.List;
import java.util.Map;


public interface SysUserService extends Service<SysUser> {

    SysUserVO getSysUserByUP(String userName, String password) throws Exception;

    List<SysUserVO> queryUserList(String role, String yxzt);

    void saveYhxx(String userDataJson) throws ServiceException;

    void updatePassword(String id, String newPassword, SysUserVO sysUserVO) throws ServiceException;

    void delYhxxById(String id) throws ServiceException;

    Integer getSysUserCountByUserName(String userName) throws ServiceException;

    SysUserVO queryLoginUser(String id) throws ServiceException;

    List<SysUserVO> queryXsjyqkList() throws ServiceException;

    void zhDjQx(String id, String yxzt) throws ServiceException;

    List<SysUserVO> queryQzzUserList(String role, String zwId) throws ServiceException;

    Map<String, Object> queryUserRoleSjtj() throws ServiceException;

    List<String> queryHomeXtggList() throws ServiceException;
}
