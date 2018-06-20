package com.boot.controller;

import com.boot.exception.Result;
import com.boot.exception.ResultUtil;
import com.boot.security.param.RoleParam;
import com.boot.security.service.SysRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;


    @RequestMapping("/role.page")
    public String page(Model model) {
        model.addAttribute("pagekey" , "role") ;
        return "vivo/role" ;
    }


    @RequestMapping("/save.json")
    @ResponseBody
    public Result saveRole(@RequestBody RoleParam param) throws Exception{
        sysRoleService.save(param);
        return ResultUtil.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public Result updateRole(@RequestBody RoleParam param) throws Exception{
        sysRoleService.update(param);
        return ResultUtil.success();
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public Result list() {
        return ResultUtil.success(sysRoleService.getAll());
    }

}
