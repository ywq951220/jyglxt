package com.csxy.project.web;
import com.csxy.core.common.BaseController;
import com.csxy.core.common.Result;
import com.csxy.core.common.ResultGenerator;
import com.csxy.core.util.PageUtil;
import com.csxy.project.model.TbZpdwZw;
import com.csxy.project.model.TbZwtjjl;
import com.csxy.project.model.vo.TbZwtjjlVO;
import com.csxy.project.service.TbZwtjjlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tb/zwtjjl")
public class TbZwtjjlController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(TbZwtjjlController.class);

    @Resource
    private TbZwtjjlService tbZwtjjlService;

    @RequestMapping("/queryZwtjjlListByUserId.html")
    public Map<String, Object> queryZwtjjlListByUserId(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "1") Integer limit, TbZwtjjlVO tbZwtjjlVO) {
        try {
            PageInfo<Object> pageInfo = PageHelper.startPage(page, limit).doSelectPageInfo(
                    () -> tbZwtjjlService.queryZwtjjlListByUserId(tbZwtjjlVO));
            return PageUtil.getLayuiPageData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[TbZwtjjlController] -> queryZwtjjlListByUserId: " + e.getMessage());
            return PageUtil.getLayuiPageData(null);
        }
    }

    @RequestMapping("/saveZwtjjl.html")
    public Result saveZwtjjl(String zwtjjlJsonData) {
        try {
            tbZwtjjlService.saveZwtjjl(zwtjjlJsonData);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[TbZwtjjlController] -> saveZwtjjl: " + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping("/delZwtjjl.html")
    public Result delZwtjjl(String id) {
        try {
            tbZwtjjlService.delZwtjjl(id);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[TbZwtjjlController] -> delZwtjjl: " + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping("/updateZwtjjlSftdById.html")
    public Result updateZwtjjlSftdById(String id) {
        try {
            tbZwtjjlService.updateZwtjjlSftdById(id);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[TbZwtjjlController] -> updateZwtjjlSftdById: " + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }
}
