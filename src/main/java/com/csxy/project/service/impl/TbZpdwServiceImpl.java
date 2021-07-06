package com.csxy.project.service.impl;

import com.csxy.core.common.ServiceException;
import com.csxy.core.util.ByUtils;
import com.csxy.core.util.JsonUtils;
import com.csxy.core.util.StringUtils;
import com.csxy.project.dao.TbZpdwMapper;
import com.csxy.project.dao.TbZpdwZwMapper;
import com.csxy.project.model.TbZpdw;
import com.csxy.project.model.TbZpdwZw;
import com.csxy.project.model.vo.SysUserVO;
import com.csxy.project.model.vo.TbZpdwVO;
import com.csxy.project.service.TbZpdwService;
import com.csxy.core.common.AbstractService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class TbZpdwServiceImpl extends AbstractService<TbZpdw> implements TbZpdwService {
    @Resource
    private TbZpdwMapper tbZpdwMapper;

    @Resource
    private TbZpdwZwMapper tbZpdwZwMapper;

    @Override
    public List<TbZpdw> getZpdwList(TbZpdwVO tbZpdwVO) throws ServiceException {
        if (null == tbZpdwVO || StringUtils.isEmpty(tbZpdwVO.getUserId()) || StringUtils.isEmpty(tbZpdwVO.getRole())) {
            throw new ServiceException("获取的用户主键为空或用户角色为空！");
        }
        List<TbZpdw> list = tbZpdwMapper.getZpdwList(tbZpdwVO);
        if (null == list) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public void savezpdwXx(String zpxxJsonData, SysUserVO sysUserVO) throws ServiceException {
        if (null == sysUserVO || StringUtils.isEmpty(sysUserVO.getId())) {
            throw new ServiceException("用户登录信息为空！");
        }
        TbZpdw tbZpdw = (TbZpdw) JsonUtils.getBeanFromJsonData(zpxxJsonData, TbZpdw.class);
        if (null == tbZpdw || StringUtils.isEmpty(tbZpdw.getUserId())) {
            throw new ServiceException("未找到用户主键信息！");
        }
        String id = tbZpdw.getId();
        if (StringUtils.isEmpty(id)) {
            //新增
            id = UUID.randomUUID().toString().replaceAll("-", "");
            tbZpdw.setId(id);
            tbZpdw.setShzt("0");
            tbZpdw.setYxzt("1");
            tbZpdwMapper.insertSelective(tbZpdw);
        } else {
            //修改
            TbZpdw oldZpdw = tbZpdwMapper.selectByPrimaryKey(id);
            if (null == oldZpdw || StringUtils.isEmpty(oldZpdw.getId())) {
                throw new ServiceException("为查到招聘单位信息！");
            }
            BeanUtils.copyProperties(tbZpdw, oldZpdw, ByUtils.getNullPropertyNames(tbZpdw));
            oldZpdw.setYxzt("1");
            tbZpdwMapper.updateByPrimaryKey(oldZpdw);
        }
    }

    @Override
    public void dwsh(String dwshData) throws ServiceException {
        TbZpdw tbZpdw = (TbZpdw) JsonUtils.getBeanFromJsonData(dwshData, TbZpdw.class);
        if (null == tbZpdw || StringUtils.isEmpty(tbZpdw.getId())) {
            throw new ServiceException("获取的招聘单位主键信息为空！");
        }
        if (StringUtils.isEmpty(tbZpdw.getShzt())) {
            throw new ServiceException("获取的审核状态为空！");
        }
        TbZpdw oldZpdw = tbZpdwMapper.selectByPrimaryKey(tbZpdw.getId());
        if (null == oldZpdw || StringUtils.isEmpty(oldZpdw.getId())) {
            throw new ServiceException("未查询到单位信息！");
        }
        oldZpdw.setShzt("1");
        oldZpdw.setShyj(tbZpdw.getShyj());
        tbZpdwMapper.updateByPrimaryKey(oldZpdw);
    }

    @Override
    public void delZpdwXx(String id) throws ServiceException {
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException("获取的单位主键信息为空！");
        }
        TbZpdw tbZpdw = tbZpdwMapper.selectByPrimaryKey(id);
        if (null == tbZpdw || StringUtils.isEmpty(tbZpdw.getId())) {
            throw new ServiceException("未查询单位信息！");
        }
        tbZpdw.setYxzt("0");
        tbZpdwMapper.updateByPrimaryKey(tbZpdw);

        TbZpdwZw tbZpdwZw = new TbZpdwZw();
        tbZpdwZw.setDwId(id);
        List<TbZpdwZw> zwList = tbZpdwZwMapper.queryZwxxByDwId(tbZpdwZw);
        if (null != zwList && zwList.size() > 0) {
            for (TbZpdwZw temp: zwList) {
                if (null != temp && !StringUtils.isEmpty(temp.getId())) {
                    temp.setYxzt("0");
                    tbZpdwZwMapper.updateByPrimaryKey(temp);
                }
            }
        }
    }

    @Override
    public List<TbZpdwVO> loadQylyrsSjtj() throws ServiceException {
        List<TbZpdwVO> list = tbZpdwMapper.loadQylyrsSjtj();
        if (null == list) {
            list = new ArrayList<>();
        }
        return list;
    }
}
