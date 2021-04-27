package com.csxy.project.web;

import com.csxy.core.common.BaseController;
import com.csxy.core.common.Result;
import com.csxy.core.common.ResultGenerator;
import com.csxy.core.util.JsonUtils;
import com.csxy.core.util.StringUtils;
import com.csxy.project.dao.RedisMapper;
import com.csxy.project.model.SysParamter;
import com.csxy.project.model.vo.SysUserVO;
import com.csxy.project.service.SysParamterService;
import com.csxy.project.service.SysUserService;
import com.csxy.project.service.sys.RedisService;
import com.csxy.project.token.TokenManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController //返回JSON
@RequestMapping("/index")
public class IndexController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private RedisMapper redisMapper;

    @Resource
    private RedisService redisService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysParamterService sysParamterService;

    @PostMapping("/ssoLogin.html")
    public Result ssoLogin(String userName, String password) {
        try {
            if (StringUtils.isEmpty(userName)) {
                return ResultGenerator.genFailResult("登录失败：【用户名为空】");
            }
            if (StringUtils.isEmpty(password)) {
                return ResultGenerator.genFailResult("登录失败：【密码为空】");
            }
            SysUserVO sysUserVO = sysUserService.getSysUserByUP(userName, password);
            String token = TokenManager.generateToken(sysUserVO.getSfz());
            sysUserVO.setToken(token);
            redisService.set(token, JsonUtils.getJsonData(sysUserVO));
            return ResultGenerator.genSuccessResult(sysUserVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[IndexController]->ssoLogin: " + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public  Result logout() {
        if (!StringUtils.isEmpty(this.getToken())) {
            redisMapper.del(this.getToken());
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/getParamterByTypeCode.html")
    public Result getParamterByTypeCode(String typeCode) {
        try {
            List<SysParamter> list = sysParamterService.getParamterByTypeCode(typeCode);
            return ResultGenerator.genSuccessResult(list);
        } catch (Exception e) {
            logger.error("[IndexController]->getParamterByTypeCode: " + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping("/queryHomeXtggList.html")
    public Result queryHomeXtggList() {
        try {
            List<String> list = sysUserService.queryHomeXtggList();
            return ResultGenerator.genSuccessResult(list);
        } catch (Exception e) {
            logger.error("[IndexController]->queryHomeXtggList: " + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }
}
