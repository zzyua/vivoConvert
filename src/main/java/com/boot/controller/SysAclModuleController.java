package com.boot.controller;


import com.boot.exception.Result;
import com.boot.exception.ResultUtil;
import com.boot.security.param.AclModuleParam;
import com.boot.security.param.DeptParam;
import com.boot.security.service.SysAclModuleService;
import com.boot.security.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/sys/aclModule")
@Slf4j
public class SysAclModuleController {

    @Resource
    private SysAclModuleService sysAclModuleService;
    @Resource
    private SysTreeService sysTreeService;

    @RequestMapping("/acl.page")
    public String page(Model model) {
        model.addAttribute("pagekey" , "acl") ;
        return "vivo/acl" ;
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public Result saveAclModule(@RequestBody AclModuleParam param) throws Exception{
        sysAclModuleService.save(param);
        return ResultUtil.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public Result updateAclModule(@RequestBody AclModuleParam param) throws Exception{
        sysAclModuleService.update(param);
        return ResultUtil.success();
    }


    @RequestMapping("/delete.json")
    @ResponseBody
    public Result delete(@RequestBody DeptParam param) throws Exception{
        sysAclModuleService.delete(param.getId());
        return ResultUtil.success();
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public Result tree() {
        return ResultUtil.success(sysTreeService.aclModuleTree());
    }



}
