package com.csxy.project.web;
import com.csxy.core.common.BaseController;
import com.csxy.core.common.Result;
import com.csxy.core.common.ResultGenerator;
import com.csxy.core.util.JsonUtils;
import com.csxy.core.util.PageUtil;
import com.csxy.core.util.WebContextUtil;
import com.csxy.project.model.vo.SysUserVO;
import com.csxy.project.model.vo.TbZpdwVO;
import com.csxy.project.service.TbZpdwService;
import com.csxy.project.service.sys.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/tb/zpdw")
public class TbZpdwController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(TbZpdwController.class);

    @Resource
    private TbZpdwService tbZpdwService;

    @Resource
    private RedisService redisService;

    @RequestMapping("/getZpdwList.html")
    public Map<String, Object> getZpdwList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "1") Integer limit, TbZpdwVO tbZpdwVO) {
        try {
            PageInfo<Object> pageInfo = PageHelper.startPage(page, limit).doSelectPageInfo(() -> tbZpdwService.getZpdwList(tbZpdwVO));
            return PageUtil.getLayuiPageData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[TbZpdwController] -> getDdglList: " + e.getMessage());
            return PageUtil.getLayuiPageData(null);
        }
    }

    @RequestMapping("/savezpdwXx.html")
    public Result savezpdwXx(String zpxxJsonData) {
        try {
            String token = WebContextUtil.getToken();
            String userVOStr = redisService.get(token);
            SysUserVO sysUserVO = (SysUserVO) JsonUtils.getBeanFromJsonData(userVOStr, SysUserVO.class);
            tbZpdwService.savezpdwXx(zpxxJsonData, sysUserVO);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            logger.error("[TbZpdwController] -> savezpdwXx: " + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping("/dwsh.html")
    public Result dwsh(String dwshData) {
        try {
            tbZpdwService.dwsh(dwshData);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            logger.error("[TbZpdwController] -> dwsh: " + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping("/delZpdwXx.html")
    public Result delZpdwXx(String id) {
        try {
            tbZpdwService.delZpdwXx(id);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            logger.error("[TbZpdwController] -> delZpdwXx: " + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }
}
