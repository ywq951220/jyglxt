package com.csxy.project.dao;

import com.csxy.core.common.Mapper;
import com.csxy.project.model.SysUser;
import com.csxy.project.model.vo.SysUserVO;
import com.csxy.project.model.vo.UserSjtjVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends Mapper<SysUser> {
    SysUserVO getSysUserByUP(@Param(value = "userName") String userName, @Param(value = "password") String password);

    List<SysUserVO> queryUserList(@Param(value = "role") String role, @Param(value = "yxzt") String yxzt);

    Integer getSysUserCountByUserName(@Param(value = "userName") String userName);

    List<SysUserVO> queryXsjyqkList();

    List<SysUserVO> queryQzzUserList(@Param(value = "role") String role, @Param(value = "zwId") String dwId);

    Integer queryUserCount(String role);

    List<SysUserVO> getYjyQzzList();

    List<UserSjtjVO> queryQzzJyqkSjtj();

    List<UserSjtjVO> queryXtyhfxSjtj();
}