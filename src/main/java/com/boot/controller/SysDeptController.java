package com.boot.controller;

import com.boot.exception.Result;
import com.boot.exception.ResultUtil;
import com.boot.security.dto.DeptLevelDto;
import com.boot.security.param.DeptParam;
import com.boot.security.service.SysDeptService;
import com.boot.security.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {

    @Resource
    private SysDeptService sysDeptService;

    @Resource
    private SysTreeService sysTreeService;

    @RequestMapping("/dept.page")
    public String page(Model model) {
        model.addAttribute("pagekey" , "dept") ;
        return "vivo/dept" ;
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public Result tree() {
        List<DeptLevelDto> dtoList = sysTreeService.deptTree();
        return ResultUtil.success(dtoList);
    }


    @RequestMapping("/save.json")
    @ResponseBody
    public Result saveDept(@RequestBody  DeptParam param) throws Exception {
        sysDeptService.save(param);
        return ResultUtil.success();
    }



    @RequestMapping("/update.json")
    @ResponseBody
    public Result updateDept( @RequestBody  DeptParam param)  throws Exception{
        sysDeptService.update(param);
        return ResultUtil.success();
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public Result delete(@RequestBody  DeptParam param) throws Exception{
        sysDeptService.delete(param.getId());
        return ResultUtil.success();
    }


}
