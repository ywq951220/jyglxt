package com.csxy.project.web;
import com.csxy.core.common.BaseController;
import com.csxy.core.common.Result;
import com.csxy.core.common.ResultGenerator;
import com.csxy.core.util.JsonUtils;
import com.csxy.core.util.WebContextUtil;
import com.csxy.project.model.vo.SysUserVO;
import com.csxy.project.model.vo.TbGrjlVO;
import com.csxy.project.service.TbGrjlService;
import com.csxy.project.service.sys.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/tb/grjl")
public class TbGrjlController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(TbGrjlController.class);

    @Resource
    private TbGrjlService tbGrjlService;

    @Resource
    private RedisService redisService;

    @RequestMapping("/saveGrjlXx.html")
    public Result saveGrjlXx(String grjlXxJsonData) {
        try {
            String token = WebContextUtil.getToken();
            String userVOStr = redisService.get(token);
            SysUserVO sysUserVO = (SysUserVO) JsonUtils.getBeanFromJsonData(userVOStr, SysUserVO.class);
            tbGrjlService.saveGrjlXx(grjlXxJsonData, sysUserVO);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            logger.error("[TbGrjlController] -> saveGrjlXx: " + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping("/queryGrjlByUserId.html")
    public Result queryGrjlByUserId(String userId) {
        try {
            TbGrjlVO tbGrjlVO =tbGrjlService.queryGrjlByUserId(userId);
            return ResultGenerator.genSuccessResult(tbGrjlVO);
        } catch (Exception e) {
            logger.error("[TbGrjlController] -> saveGrjlXx: " + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping("/hasGrjlByUserId.html")
    public Result hasGrjlByUserId(String userId) {
        try {
            Boolean b = tbGrjlService.hasGrjlByUserId(userId);
            return ResultGenerator.genSuccessResult(b);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[TbJltdjlController] -> delJltdjlById: " + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }
}
