package com.csxy.project.web;
import com.csxy.core.common.BaseController;
import com.csxy.core.common.Result;
import com.csxy.core.common.ResultGenerator;
import com.csxy.core.util.JsonUtils;
import com.csxy.core.util.PageUtil;
import com.csxy.core.util.WebContextUtil;
import com.csxy.project.model.vo.SysUserVO;
import com.csxy.project.service.SysUserService;
import com.csxy.project.service.sys.RedisService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Resource
    private SysUserService sysUserService;

    @Resource
    private RedisService redisService;

    @RequestMapping("/queryUserList.html")
    public Map<String, Object> getYhglList(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "1") Integer limit, String role, String yxzt) {
        try {
            PageInfo<Object> pageInfo = PageHelper.startPage(page, limit).doSelectPageInfo(
                    () -> sysUserService.queryUserList(role, yxzt));
            return PageUtil.getLayuiPageData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[SysUserController] -> getYhglList:" + e.getMessage());
            return PageUtil.getLayuiPageData(null);
        }
    }
    
    @RequestMapping("/saveYhxx.html")
    @ApiOperation(value = "新增用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userDataJson", value = "用户注册信息json串", dataType = "String")
    })
    public Result saveYhxx(String userDataJson) {
        try {
            sysUserService.saveYhxx(userDataJson);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            logger.error("[SysUserController] -> getYhglList:" + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping("getSysUserCountByUserName.html")
    @ApiOperation(value = "根据用户名获取用户数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", dataType = "String")
    })
    public Result getSysUserCountByUserName(String userName) {
        try {
            int count = sysUserService.getSysUserCountByUserName(userName);
            return ResultGenerator.genSuccessResult(count);
        } catch (Exception e) {
            logger.error("[SysUserController] -> getSysUserByUserName:" + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping("/queryLoginUser.html")
    @ApiOperation(value = "获取当前用户登录信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户主键", dataType = "String")
    })
    public Map<String, Object> queryLoginUser(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "1") Integer limit, String id) {
        try {
            PageInfo<Object> pageInfo = PageHelper.startPage(page, limit).doSelectPageInfo(() -> sysUserService.queryLoginUser(id));
            return PageUtil.getLayuiPageData(pageInfo);
        } catch (Exception e) {
            logger.error("[SysUserController] -> queryLoginUser:" + e.getMessage());
            return PageUtil.getLayuiPageData(null);
        }
    }

    @RequestMapping("/updatePassword.html")
    @ApiOperation(value = "修改个人密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户主键", dataType = "String"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", dataType = "String")
    })
    public Result updatePassword(String id, String newPassword) {
        try {
            String token = WebContextUtil.getToken();
            String sysUserVOStr = redisService.get(token);
            SysUserVO sysUserVO = (SysUserVO) JsonUtils.getBeanFromJsonData(sysUserVOStr, SysUserVO.class);
            sysUserService.updatePassword(id, newPassword, sysUserVO);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            logger.error("[SysUserController] -> updatePassword:" + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping("/delYhxxById.html")
    public Result delYhxxById(String id) {
        try {
            sysUserService.delYhxxById(id);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[SysUserController] -> delYhxxById:" + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping("/queryXsjyqkList.html")
    public Map<String, Object> queryXsjyqkList(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "1") Integer limit) {
        try {
            PageInfo<Object> pageInfo = PageHelper.startPage(page, limit).doSelectPageInfo(
                    () -> sysUserService.queryXsjyqkList());
            return PageUtil.getLayuiPageData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[SysUserController] -> queryXsjyqkList:" + e.getMessage());
            return PageUtil.getLayuiPageData(null);
        }
    }

    @RequestMapping("/zhDjQx.html")
    public Result zhDjQx(String id, String yxzt) {
        try {
            sysUserService.zhDjQx(id, yxzt);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[SysUserController] -> zhDjQx:" + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping("/queryQzzUserList.html")
    public Result queryQzzUserList(String role, String zwId) {
        try {
            List<SysUserVO> list = sysUserService.queryQzzUserList(role, zwId);
            return ResultGenerator.genSuccessResult(list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[SysUserController] -> queryQzzUserList:" + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping("/queryUserRoleSjtj.html")
    public Result queryUserRoleSjtj() {
        try {
            Map<String, Object> map = sysUserService.queryUserRoleSjtj();
            return ResultGenerator.genSuccessResult(map);
        } catch (Exception e) {
            logger.error("[IndexController]->queryUserRoleSjtj: " + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }
}
