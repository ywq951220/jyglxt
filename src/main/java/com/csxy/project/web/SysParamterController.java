package com.csxy.project.web;
import com.csxy.core.common.BaseController;
import com.csxy.core.common.Result;
import com.csxy.core.common.ResultGenerator;
import com.csxy.project.model.SysParamter;
import com.csxy.project.service.SysParamterService;
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

@RestController
@RequestMapping("/sys/paramter")
public class SysParamterController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysParamterController.class);

    @Resource
    private SysParamterService sysParamterService;

    @PostMapping("/save")
    public Result add(SysParamter sysParamter) {
        sysParamterService.save(sysParamter);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam String id) {
        sysParamterService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(SysParamter sysParamter) {
        sysParamterService.update(sysParamter);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam String id) {
        SysParamter sysParamter = sysParamterService.findById(id);
        return ResultGenerator.genSuccessResult(sysParamter);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer current, @RequestParam(defaultValue = "0") Integer pageSize) {
        PageHelper.startPage(current, pageSize);
        List<SysParamter> list = sysParamterService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/all")
    public Result all() {
        List<SysParamter> list = sysParamterService.findAll();
        return ResultGenerator.genSuccessResult(list);
    }
}
