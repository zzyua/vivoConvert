package com.boot.controller;

import com.boot.exception.Result;
import com.boot.exception.ResultUtil;
import com.boot.security.param.RoleParam;
import com.boot.security.service.SysRoleService;
import com.boot.security.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/sys/role")
@Slf4j
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysTreeService sysTreeService;

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

    @RequestMapping("/roleTree.json")
    @ResponseBody
    public Result roleTree(@RequestParam("roleId") int roleId) {
        return ResultUtil.success(sysTreeService.roleTree(roleId));
    }

}
