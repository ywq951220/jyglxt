package com.csxy.project.service.impl;

import com.csxy.core.common.ServiceException;
import com.csxy.core.util.ByUtils;
import com.csxy.core.util.JsonUtils;
import com.csxy.core.util.StringUtils;
import com.csxy.project.dao.SysUserMapper;
import com.csxy.project.model.SysUser;
import com.csxy.project.model.vo.SysUserVO;
import com.csxy.project.service.SysUserService;
import com.csxy.core.common.AbstractService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


@Service
@Transactional
public class SysUserServiceImpl extends AbstractService<SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUserVO getSysUserByUP(String userName, String password) throws Exception {
        if (StringUtils.isEmpty(userName)) {
            throw new Exception("获取的用户名为空！");
        }
        if (StringUtils.isEmpty(password)) {
            throw new Exception("获取的用户密码为空！");
        }
        SysUserVO sysUserVO = sysUserMapper.getSysUserByUP(userName, password);
        if (null == sysUserVO || StringUtils.isEmpty(sysUserVO.getId())) {
            throw new Exception("未获取到用户信息，用户信息为空！");
        }
        return sysUserVO;
    }

    @Override
    public List<SysUserVO> queryUserList(String role, String yxzt) {
        List<SysUserVO> list = sysUserMapper.queryUserList(role, yxzt);
        return list;
    }

    @Override
    public void saveYhxx(String userDataJson) throws ServiceException {
        SysUser sysUser = (SysUser) JsonUtils.getBeanFromJsonData(userDataJson, SysUser.class);
        String id = sysUser.getId();
        if (StringUtils.isEmpty(id)) {
            //新增
            id = UUID.randomUUID().toString().replaceAll("-", "");
            sysUser.setId(id);
            sysUser.setZcsj(new Date());
            sysUser.setYxzt("1");
            sysUserMapper.insert(sysUser);
        } else {
            SysUser oldUser = sysUserMapper.selectByPrimaryKey(id);
            if (null == oldUser || StringUtils.isEmpty(oldUser.getId())) {
                throw new ServiceException("未查询到用户信息");
            }
            BeanUtils.copyProperties(sysUser, oldUser, ByUtils.getNullPropertyNames(sysUser));
            sysUserMapper.updateByPrimaryKey(oldUser);
        }
    }

    @Override
    public void updatePassword(String id, String newPassword, SysUserVO sysUserVO) throws ServiceException {
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException("用户主键为空！");
        }
        if (StringUtils.isEmpty(newPassword)) {
            throw new ServiceException("用户新密码为空！");
        }
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
        if (null == sysUser || StringUtils.isEmpty(sysUser.getId())) {
            throw new ServiceException("未获取到用户信息！");
        }
        sysUser.setPassword(newPassword);
        sysUserMapper.updateByPrimaryKey(sysUser);
    }

    @Override
    public void delYhxxById(String id) throws ServiceException {
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException("用户主键为空！");
        }
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
        if (null == sysUser) {
            throw new ServiceException("未获取用户信息！");
        }
        sysUser.setYxzt("0");
        sysUserMapper.updateByPrimaryKey(sysUser);
    }

    @Override
    public Integer getSysUserCountByUserName(String userName) throws ServiceException {
        if (StringUtils.isEmpty(userName)) {
            throw new ServiceException("获取的用户名为空！");
        }
        int count = sysUserMapper.getSysUserCountByUserName(userName);
        return count;
    }

    @Override
    public SysUserVO queryLoginUser(String id) throws ServiceException {
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException("获取的用户主键信息为空");
        }
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
        if (null == sysUser || StringUtils.isEmpty(sysUser.getId())) {
            throw new ServiceException("未查询到用户信息！");
        }
        SysUserVO sysUserVO = new SysUserVO();
        BeanUtils.copyProperties(sysUser, sysUserVO);
        return sysUserVO;
    }

    @Override
    public List<SysUserVO> queryXsjyqkList() throws ServiceException {
        List<SysUserVO> list = sysUserMapper.queryXsjyqkList();
        if (null == list) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public void zhDjQx(String id, String yxzt) throws ServiceException {
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException("获取的用户主键为空！");
        }
        if (StringUtils.isEmpty(yxzt)) {
            throw new ServiceException("获取的有效状态为空！");
        }
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
        if (null == sysUser) {
            throw new ServiceException("未获取用户信息！");
        }
        sysUser.setYxzt(yxzt);
        sysUserMapper.updateByPrimaryKey(sysUser);
    }

    @Override
    public List<SysUserVO> queryQzzUserList(String role, String zwId) throws ServiceException {
        if (StringUtils.isEmpty(role)) {
            throw new ServiceException("获取用户角色为空！");
        }
        if (StringUtils.isEmpty(zwId)) {
            throw new ServiceException("获取的推荐职位主键为空！");
        }
        List<SysUserVO> list = sysUserMapper.queryQzzUserList(role, zwId);
        if (null == list) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public Map<String, Object> queryUserRoleSjtj() throws ServiceException {
        Map<String, Object> map = new HashMap<>();

        Integer qzzCount = sysUserMapper.queryUserCount("1");
        map.put("qzzCount", qzzCount);

        Integer qyHRCount = sysUserMapper.queryUserCount("2");
        map.put("qyHRCount", qyHRCount);

        Integer xxjyfzrCount = sysUserMapper.queryUserCount("3");
        map.put("xxjyfzrCount", xxjyfzrCount);

        Integer xtglyCount = sysUserMapper.queryUserCount("4");
        map.put("xtglyCount", xtglyCount);
        return map;
    }

    @Override
    public List<String> queryHomeXtggList() throws ServiceException {
        List<String> returnList = new ArrayList<>();
        String homeXtggStr = "";

        List<SysUserVO> yjyQzzList = sysUserMapper.getYjyQzzList();
        if (null != yjyQzzList && yjyQzzList.size() > 0) {
            for (SysUserVO temp: yjyQzzList) {
                if (null != temp) {
                    homeXtggStr = "求职者【"+ temp.getName() +"】已成功就业于【"+ temp.getGsmc() +"】的【"+ temp.getZwmc() +"】职位";
                    returnList.add(homeXtggStr);

                    homeXtggStr = "企业【"+ temp.getGsmc() +"】已成功录用【"+ temp.getName() +"】为【"+ temp.getZwmc() +"】";
                    returnList.add(homeXtggStr);
                }
            }
        }
        return returnList;
    }
}
