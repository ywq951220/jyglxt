package com.csxy.project.web;
import com.csxy.core.common.BaseController;
import com.csxy.core.common.Result;
import com.csxy.core.common.ResultGenerator;
import com.csxy.core.util.PageUtil;
import com.csxy.project.model.vo.TbJlsqjlVO;
import com.csxy.project.service.TbJltdjlService;
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
@RequestMapping("/tb/jltdjl")
public class TbJltdjlController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(TbJltdjlController.class);

    @Resource
    private TbJltdjlService tbJltdjlService;

    @RequestMapping("/saveUserTdjlxx.html")
    public Result saveUserTdjlxx(String saveTdjlJsonData) {
        try {
            String title = tbJltdjlService.saveUserTdjlxx(saveTdjlJsonData);
            return ResultGenerator.genSuccessResult(title);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[TbJltdjlController] -> saveUserTdjlxx: " + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping("/queryJltdjlByUserId.html")
    public Map<String, Object> queryJltdjlByUserId(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "1") Integer limit, TbJlsqjlVO tbJlsqjlVO) {
        try {
            PageInfo<Object> pageInfo = PageHelper.startPage(page, limit).doSelectPageInfo(
                    () -> tbJltdjlService.queryJltdjlByUserId(tbJlsqjlVO)
            );
            return PageUtil.getLayuiPageData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[TbJltdjlController] -> queryJltdjlByUserId: " + e.getMessage());
            return PageUtil.getLayuiPageData(null);
        }
    }

    @RequestMapping("/delJltdjlById.html")
    public Result delJltdjlById(String id, String zxyy) {
        try {
            tbJltdjlService.delJltdjlById(id, zxyy);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[TbJltdjlController] -> delJltdjlById: " + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping("/saveJlshXx.html")
    public Result jlSh(String shDataJson) {
        try {
            tbJltdjlService.jlSh(shDataJson);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[TbJltdjlController] -> jlSh: " + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping("/hasUserSflyByUserId.html")
    public Result hasUserSflyByUserId(String userId, String spzt, String lyzt) {
        try {
            TbJlsqjlVO tbJlsqjlVO = tbJltdjlService.hasUserSflyByUserId(userId, spzt, lyzt);
            return ResultGenerator.genSuccessResult(tbJlsqjlVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[TbJltdjlController] -> hasUserSflyByUserId: " + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping("/saveJlfkXx.html")
    public Result saveJlfkXx(String fkDataJson) {
        try {
            tbJltdjlService.jlFk(fkDataJson);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[TbJltdjlController] -> saveJlfkXx: " + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }
}
