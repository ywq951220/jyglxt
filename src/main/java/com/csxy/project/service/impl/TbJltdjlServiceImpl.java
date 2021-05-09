package com.csxy.project.service.impl;

import com.csxy.core.common.ServiceException;
import com.csxy.core.util.DateUtils;
import com.csxy.core.util.JsonUtils;
import com.csxy.core.util.SendMail;
import com.csxy.core.util.StringUtils;
import com.csxy.project.dao.SysUserMapper;
import com.csxy.project.dao.TbGrjlMapper;
import com.csxy.project.dao.TbJltdjlMapper;
import com.csxy.project.dao.TbZpdwMapper;
import com.csxy.project.model.SysUser;
import com.csxy.project.model.TbJltdjl;
import com.csxy.project.model.TbZpdw;
import com.csxy.project.model.vo.TbGrjlVO;
import com.csxy.project.model.vo.TbJlsqjlVO;
import com.csxy.project.service.TbJltdjlService;
import com.csxy.core.common.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class TbJltdjlServiceImpl extends AbstractService<TbJltdjl> implements TbJltdjlService {
    @Resource
    private TbJltdjlMapper tbJltdjlMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private TbGrjlMapper tbGrjlMapper;

    @Resource
    private TbZpdwMapper tbZpdwMapper;

    @Override
    public String saveUserTdjlxx(String saveTdjlJsonData) throws ServiceException {
        TbJltdjl tbJltdjl = (TbJltdjl) JsonUtils.getBeanFromJsonData(saveTdjlJsonData, TbJltdjl.class);
        if (null == tbJltdjl) {
            throw new ServiceException("获取的投递简历记录为空！");
        }
        if (StringUtils.isEmpty(tbJltdjl.getUserId())) {
            throw new ServiceException("获取的用户主键为空！");
        }
        if (StringUtils.isEmpty(tbJltdjl.getDwId())) {
            throw new ServiceException("获取的单位主键为空！");
        }
        if (StringUtils.isEmpty(tbJltdjl.getZwId())) {
            throw new ServiceException("获取的职位主键为空！");
        }
        if (StringUtils.isEmpty(tbJltdjl.getSpzt())) {
            throw new ServiceException("获取的审批状态为空！");
        }
        if (StringUtils.isEmpty(tbJltdjl.getLyzt())) {
            throw new ServiceException("获取的录用状态为空！");
        }
        TbJltdjl oldTbJltdjl = tbJltdjlMapper.queryTdjlBythridId(tbJltdjl);
        if (null != oldTbJltdjl && !StringUtils.isEmpty(oldTbJltdjl.getId())) {
            return "该职位已被申请，请等待企业HR的反馈！";
        }
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        tbJltdjl.setId(id);
        tbJltdjl.setYxzt("1");
        tbJltdjlMapper.insertSelective(tbJltdjl);
        return "职位申请成功，请耐心等待企业HR的反馈！";
    }

    @Override
    public List<TbJlsqjlVO> queryJltdjlByUserId(TbJlsqjlVO tbJlsqjlVO) throws ServiceException {
        if (null == tbJlsqjlVO || StringUtils.isEmpty(tbJlsqjlVO.getUserId()) || StringUtils.isEmpty(tbJlsqjlVO.getSqr_role())) {
            throw new ServiceException("获取的申请用户住进或申请用户角色为空！");
        }
        String sqrRole = tbJlsqjlVO.getSqr_role();
        List<TbJlsqjlVO> list;
        switch (sqrRole) {
            case "1":
                list = tbJltdjlMapper.queryQzzTdjlByUserId(tbJlsqjlVO.getUserId(), "", "");
                break;
            case "2":
                list = tbJltdjlMapper.queryQymsjlByUserId(tbJlsqjlVO.getUserId());
                break;
            default:
                throw new ServiceException("获取用户角色有误！");
        }
        return list;
    }

    @Override
    public void delJltdjlById(String id, String zxyy) throws ServiceException {
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException("获取的简历投递记录主键为空！");
        }
        TbJltdjl tbJltdjl = tbJltdjlMapper.selectByPrimaryKey(id);
        if (null == tbJltdjl || StringUtils.isEmpty(tbJltdjl.getId())) {
            throw new ServiceException("未查询到简历投递就信息！");
        }
        tbJltdjl.setYxzt("0");
        tbJltdjl.setZxyy(zxyy);
        tbJltdjlMapper.updateByPrimaryKey(tbJltdjl);
    }

    @Override
    public void jlSh(String shDataJson) throws ServiceException {
        TbJltdjl tbJltdjl = (TbJltdjl) JsonUtils.getBeanFromJsonData(shDataJson, TbJltdjl.class);
        if (null == tbJltdjl || StringUtils.isEmpty(tbJltdjl.getId())) {
            throw new ServiceException("获取的简历投递记录主键为空！");
        }
        if (StringUtils.isEmpty(tbJltdjl.getSpzt())) {
            throw new ServiceException("获取的审批状态为空！");
        }
        TbJltdjl oldJltdjl = tbJltdjlMapper.selectByPrimaryKey(tbJltdjl.getId());
        if (null == oldJltdjl || StringUtils.isEmpty(oldJltdjl.getId())) {
            throw new ServiceException("未查询到简历投递记录信息！");
        }
        oldJltdjl.setSpzt(tbJltdjl.getSpzt());
        oldJltdjl.setSpsj(new Date());
        oldJltdjl.setMssj(tbJltdjl.getMssj());
        oldJltdjl.setMsdz(tbJltdjl.getMsdz());
        oldJltdjl.setSpyj(tbJltdjl.getSpyj());
        tbJltdjlMapper.updateByPrimaryKey(oldJltdjl);

        //邮件发送
        String userId = oldJltdjl.getUserId();
        if (StringUtils.isEmpty(userId)) {
            throw new ServiceException("获取的用户主键信息为空！");
        }
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        if (null == sysUser || StringUtils.isEmpty(sysUser.getId())) {
            throw new ServiceException("未查询到求职者个人信息！");
        }
        TbGrjlVO grjlVO = tbGrjlMapper.queryGrjlByUserId(userId);
        if (null == grjlVO || StringUtils.isEmpty(grjlVO.getId())) {
            throw new ServiceException("未查询到求职者个人简历信息！");
        } else {
            if(StringUtils.isEmpty(grjlVO.getDzyx())) {
                throw new ServiceException("求职者电子邮箱为空！");
            } else {
                //获取单位信息
                TbZpdw zpdw = tbZpdwMapper.selectByPrimaryKey(oldJltdjl.getDwId());
                if (null == zpdw || StringUtils.isEmpty(zpdw.getId())) {
                    throw new ServiceException("未查询到单位信息！");
                }

                String mailTitle = "【"+ zpdw.getGsmc() +"】简历投递反馈通知";
                String mailText = "";
                if ("1".equals(tbJltdjl.getSpzt())) {
                    mailText =
                            "恭喜您，" +
                                    sysUser.getName() +
                                    "。您的简历审核通过，特邀请您在" +
                                    tbJltdjl.getMsdz() + "于" +
                                    DateUtils.formatDateByFormat(tbJltdjl.getMssj(), "yyyy-MM-dd HH:mm:ss") +
                                    "参加面试。请知晓！";
                } else {
                    mailText =
                            "很遗憾！" +
                                    sysUser.getName() +
                                    "。您的简历审核未通过，原因：" +
                                    tbJltdjl.getSpyj() + "。期待下次与您合作。谢谢！";
                }
                SendMail.sendMail(grjlVO.getDzyx(), mailText, mailTitle);
            }
        }
    }

    @Override
    public TbJlsqjlVO hasUserSflyByUserId(String userId, String spzt, String lyzt) throws ServiceException {
        if (StringUtils.isEmpty(userId)) {
            throw new ServiceException("获取用户主键信息为空！");
        }
        if (StringUtils.isEmpty(spzt)) {
            spzt = "1";
        }
        if (StringUtils.isEmpty(lyzt)) {
            lyzt = "1";
        }
        List<TbJlsqjlVO> list = tbJltdjlMapper.queryQzzTdjlByUserId(userId, spzt, lyzt);
        TbJlsqjlVO tbJlsqjlVO;
        if (null != list && list.size() > 0) {
            tbJlsqjlVO = list.get(0);
        } else {
            tbJlsqjlVO = new TbJlsqjlVO();
        }
        return tbJlsqjlVO;
    }

    @Override
    public void jlFk(String fkDataJson) throws ServiceException {
        TbJltdjl tbJltdjl = (TbJltdjl) JsonUtils.getBeanFromJsonData(fkDataJson, TbJltdjl.class);
        if (null == tbJltdjl || StringUtils.isEmpty(tbJltdjl.getId())) {
            throw new ServiceException("获取的简历投递记录主键为空！");
        }
        if (StringUtils.isEmpty(tbJltdjl.getLyzt())) {
            throw new ServiceException("获取的录用状态为空！");
        }
        TbJltdjl oldJltdjl = tbJltdjlMapper.selectByPrimaryKey(tbJltdjl.getId());
        if (null == oldJltdjl || StringUtils.isEmpty(oldJltdjl.getId())) {
            throw new ServiceException("未查询到简历投递记录信息！");
        }
        oldJltdjl.setLyzt(tbJltdjl.getLyzt());
        oldJltdjl.setLysj(new Date());
        tbJltdjlMapper.updateByPrimaryKey(oldJltdjl);

        //邮件发送
        String userId = oldJltdjl.getUserId();
        if (StringUtils.isEmpty(userId)) {
            throw new ServiceException("获取的用户主键信息为空！");
        }
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        if (null == sysUser || StringUtils.isEmpty(sysUser.getId())) {
            throw new ServiceException("未查询到求职者个人信息！");
        }
        TbGrjlVO grjlVO = tbGrjlMapper.queryGrjlByUserId(userId);
        if (null == grjlVO || StringUtils.isEmpty(grjlVO.getId())) {
            throw new ServiceException("未查询到求职者个人简历信息！");
        } else {
            if(StringUtils.isEmpty(grjlVO.getDzyx())) {
                throw new ServiceException("求职者电子邮箱为空！");
            }
            //获取单位信息
            TbZpdw zpdw = tbZpdwMapper.selectByPrimaryKey(oldJltdjl.getDwId());
            if (null == zpdw || StringUtils.isEmpty(zpdw.getId())) {
                throw new ServiceException("未查询到单位信息！");
            }
            String mailTitle = "【"+ zpdw.getGsmc() +"】面试结果通知";
            String mailText = "";
            if ("1".equals(tbJltdjl.getSpzt())) {
                mailText =
                        "恭喜您，" +
                                sysUser.getName() +
                                "。您的面试已通过，请在收到邮件信息时间的十日内来我公司报到。";
            } else {
                mailText =
                        "很遗憾！" +
                                sysUser.getName() +
                                "。您的面试未通过。期待下次与您合作。谢谢！";
            }
            SendMail.sendMail(grjlVO.getDzyx(), mailText, mailTitle);
        }
    }
}
