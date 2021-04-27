package com.csxy.project.service.impl;

import com.csxy.core.common.ServiceException;
import com.csxy.core.util.JsonUtils;
import com.csxy.core.util.StringUtils;
import com.csxy.project.dao.SysUserMapper;
import com.csxy.project.dao.TbGrjlMapper;
import com.csxy.project.model.SysUser;
import com.csxy.project.model.TbGrjl;
import com.csxy.project.model.vo.SysUserVO;
import com.csxy.project.model.vo.TbGrjlVO;
import com.csxy.project.service.TbGrjlService;
import com.csxy.core.common.AbstractService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;


@Service
@Transactional
public class TbGrjlServiceImpl extends AbstractService<TbGrjl> implements TbGrjlService {
    @Resource
    private TbGrjlMapper tbGrjlMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public void saveGrjlXx(String grjlXxJsonData, SysUserVO sysUserVO) throws ServiceException {
        if (null == sysUserVO || StringUtils.isEmpty(sysUserVO.getId())) {
            throw new ServiceException("登录用户信息为空！");
        }
        TbGrjl tbGrjl = (TbGrjl) JsonUtils.getBeanFromJsonData(grjlXxJsonData, TbGrjl.class);
        if (null == tbGrjl || StringUtils.isEmpty(tbGrjl.getUserId())) {
            throw new ServiceException("未找到用户主键信息！");
        }
        String userId = sysUserVO.getId();
        TbGrjl oldGrjl = tbGrjlMapper.queryGrjlByUserId(userId);
        if ( null == oldGrjl || StringUtils.isEmpty(oldGrjl.getId())) {
            //新增
            String id = UUID.randomUUID().toString().replaceAll("-", "");
            tbGrjl.setId(id);
            tbGrjl.setYxzt("1");
            tbGrjlMapper.insertSelective(tbGrjl);
        } else {
            //修改
            BeanUtils.copyProperties(tbGrjl, oldGrjl);
            oldGrjl.setYxzt("1");
            tbGrjlMapper.updateByPrimaryKey(oldGrjl);
        }
    }

    @Override
    public TbGrjlVO queryGrjlByUserId(String userId) throws ServiceException {
        if (StringUtils.isEmpty(userId)) {
            throw new ServiceException("获取的用户主键为空！");
        }
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        if (null == sysUser || StringUtils.isEmpty(sysUser.getId())) {
            throw new ServiceException("未查询到用户信息！");
        }
        TbGrjlVO tbGrjlVO = tbGrjlMapper.queryGrjlByUserId(userId);
        if (null == tbGrjlVO) {
            tbGrjlVO = new TbGrjlVO();
        }
        tbGrjlVO.setName(sysUser.getName());
        tbGrjlVO.setSex(sysUser.getSex());
        tbGrjlVO.setSfz(sysUser.getSfz());
        tbGrjlVO.setLxdh(sysUser.getLxdh());
        return tbGrjlVO;
    }

    @Override
    public Boolean hasGrjlByUserId(String userId) throws ServiceException {
        if (StringUtils.isEmpty(userId)) {
            throw new ServiceException("获取的用户主键为空！");
        }
        TbGrjlVO tbGrjlVO = tbGrjlMapper.queryGrjlByUserId(userId);
        Boolean b;
        if (null == tbGrjlVO || StringUtils.isEmpty(tbGrjlVO.getId())) {
            b = false;
        } else {
            b = true;
        }
        return b;
    }
}
