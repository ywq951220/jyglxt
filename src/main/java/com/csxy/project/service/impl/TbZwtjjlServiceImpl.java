package com.csxy.project.service.impl;

import com.csxy.core.common.ServiceException;
import com.csxy.core.util.JsonUtils;
import com.csxy.core.util.StringUtils;
import com.csxy.project.dao.SysUserMapper;
import com.csxy.project.dao.TbZwtjjlMapper;
import com.csxy.project.model.SysUser;
import com.csxy.project.model.TbZwtjjl;
import com.csxy.project.model.vo.TbZwtjjlVO;
import com.csxy.project.service.TbZwtjjlService;
import com.csxy.core.common.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class TbZwtjjlServiceImpl extends AbstractService<TbZwtjjl> implements TbZwtjjlService {
    @Resource
    private TbZwtjjlMapper tbZwtjjlMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public List<TbZwtjjlVO> queryZwtjjlListByUserId(TbZwtjjlVO tbZwtjjlVO) throws ServiceException {
        if (null == tbZwtjjlVO || StringUtils.isEmpty(tbZwtjjlVO.getTxUserId())) {
            throw new ServiceException("获取的登录用户主键为空！");
        }
        List<TbZwtjjlVO> list = tbZwtjjlMapper.queryZwtjjlListByUserId(tbZwtjjlVO);
        if (null == list) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public void saveZwtjjl(String zwtjjlJsonData) throws ServiceException {
        TbZwtjjl tbZwtjjl = (TbZwtjjl) JsonUtils.getBeanFromJsonData(zwtjjlJsonData, TbZwtjjl.class);
        if (null == tbZwtjjl) {
            throw new ServiceException("获取的职位推荐记录JSON串为空！");
        }
        if (StringUtils.isEmpty(tbZwtjjl.getZwId())) {
            throw new ServiceException("未获取到职位主键想信息！");
        }
        if (StringUtils.isEmpty(tbZwtjjl.getTjUserId())) {
            throw new ServiceException("未获取到推荐用户主键想信息！");
        }
        SysUser tjUser = sysUserMapper.selectByPrimaryKey(tbZwtjjl.getTjUserId());
        if (null == tjUser || StringUtils.isEmpty(tjUser.getId())) {
            throw new ServiceException("未查询到推荐用户信息！");
        }
        String txUserId = tbZwtjjl.getTxUserId();
        if (StringUtils.isEmpty(txUserId)) {
            throw new ServiceException("未获取到推向用户主键想信息！");
        }
        if (txUserId.indexOf(",") > 0) {
            String[] txUserIdArr = txUserId.split(",");
            for (String userId: txUserIdArr) {
                SysUser txUser = sysUserMapper.selectByPrimaryKey(userId);
                if (null != txUser && !StringUtils.isEmpty(txUser.getId())) {
                    TbZwtjjl zwtjjl = new TbZwtjjl();
                    String id = UUID.randomUUID().toString().replaceAll("-", "");
                    zwtjjl.setId(id);
                    zwtjjl.setZwId(tbZwtjjl.getZwId());
                    zwtjjl.setTjUserId(tbZwtjjl.getTjUserId());
                    zwtjjl.setTxUserId(userId);
                    zwtjjl.setYxzt("1");
                    zwtjjl.setSftd("0");
                    tbZwtjjlMapper.insertSelective(zwtjjl);
                }
            }
        } else {
            String id = UUID.randomUUID().toString().replaceAll("-", "");
            tbZwtjjl.setId(id);
            tbZwtjjl.setYxzt("1");
            tbZwtjjl.setSftd("0");
            tbZwtjjlMapper.insertSelective(tbZwtjjl);
        }
    }

    @Override
    public void delZwtjjl(String id) throws ServiceException {
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException("未获取到职位推荐记录主键信息！");
        }
        TbZwtjjl tbZwtjjl = tbZwtjjlMapper.selectByPrimaryKey(id);
        if (null == tbZwtjjl || StringUtils.isEmpty(tbZwtjjl.getId())) {
            throw new ServiceException("未查询到职位推荐记录信息！");
        }
        tbZwtjjl.setYxzt("0");
        tbZwtjjlMapper.updateByPrimaryKey(tbZwtjjl);
    }

    @Override
    public void updateZwtjjlSftdById(String id) throws ServiceException {
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException("获取职位推荐记录主键为空！");
        }
        TbZwtjjl tbZwtjjl = tbZwtjjlMapper.selectByPrimaryKey(id);
        if (null == tbZwtjjl || StringUtils.isEmpty(tbZwtjjl.getId())) {
            throw new ServiceException("未查询到职位推荐信息！");
        }
        tbZwtjjl.setSftd("1");
        tbZwtjjlMapper.updateByPrimaryKey(tbZwtjjl);
    }
}
