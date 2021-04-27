package com.csxy.project.web;
import com.csxy.core.common.BaseController;
import com.csxy.core.common.Result;
import com.csxy.core.common.ResultGenerator;
import com.csxy.core.util.PageUtil;
import com.csxy.project.model.TbZpdwZw;
import com.csxy.project.service.TbZpdwZwService;
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
@RequestMapping("/tb/zpdw/zw")
public class TbZpdwZwController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(TbZpdwZwController.class);

    @Resource
    private TbZpdwZwService tbZpdwZwService;

    @RequestMapping("/queryZwxxByDwId.html")
    public Map<String, Object> queryZwxxByDwId(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "1") Integer limit, TbZpdwZw tbZpdwZw) {
        try {
            PageInfo<Object> pageInfo = PageHelper.startPage(page, limit).doSelectPageInfo(
                    () -> tbZpdwZwService.queryZwxxByDwId(tbZpdwZw));
            return PageUtil.getLayuiPageData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[TbZpdwZwController] -> queryZwxxByDwId: " + e.getMessage());
            return PageUtil.getLayuiPageData(null);
        }
    }

    @RequestMapping("/saveZwxx.html")
    public Result saveZwxx(String zwxxJsonData) {
        try {
            tbZpdwZwService.saveZwxx(zwxxJsonData);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[TbZpdwZwController] -> queryZwxxByDwId: " + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @RequestMapping("/delZwXx.html")
    public Result delZwXx(String id) {
        try {
            tbZpdwZwService.delZwXx(id);
            return ResultGenerator.genSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[TbZpdwZwController] -> delZwXx: " + e.getMessage());
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }
}
