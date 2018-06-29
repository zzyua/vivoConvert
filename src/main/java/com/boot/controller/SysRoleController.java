package com.boot.controller;

import com.boot.exception.Result;
import com.boot.exception.ResultUtil;
import com.boot.model.SysUser;
import com.boot.security.dto.SysUserDto;
import com.boot.security.param.RoleParam;
import com.boot.security.service.*;
import com.boot.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sys/role")
@Slf4j
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysTreeService sysTreeService;

    @Resource
    private SysRoleAclService sysRoleAclService;

    @Resource
    private SysRoleUserService sysRoleUserService;
    @Resource
    private SysUserService sysUserService;

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

    @RequestMapping("/changeAcls.json")
    @ResponseBody
    public Result changeAcls(@RequestParam("roleId") int roleId, @RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds) {
        List<Integer> aclIdList = StrUtil.splitToListInt(aclIds);
        sysRoleAclService.changeRoleAcls(roleId, aclIdList);
        return ResultUtil.success();
    }



    @RequestMapping("/users.json")
    @ResponseBody
    public Result users(@RequestParam("roleId") int roleId) {
        List<SysUser> selectedUserList = sysRoleUserService.getListByRoleId(roleId);
        List<SysUser> allUserList = sysUserService.getAll();
        List<SysUserDto> userDtoList  = Lists.newArrayList();

        Set<Integer> selectedUserIdSet = selectedUserList.stream().map(sysUser -> sysUser.getId()).collect(Collectors.toSet());

        allUserList.forEach(sysUser -> {
            if(sysUser.getStatus() == 1 ){
                SysUserDto userDto =  SysUserDto.adapt(sysUser) ;
                if(selectedUserIdSet.contains(sysUser.getId())){
                    userDto.setSelected(true);
                }else {
                    userDto.setSelected(false);
                }
                userDtoList.add(userDto) ;
            }
        });
        return ResultUtil.success(userDtoList);
    }

    @RequestMapping("/changeUsers.json")
    @ResponseBody
    public Result changeUsers(@RequestParam("roleId") int roleId, @RequestParam(value = "userIds", required = false, defaultValue = "") String userIds) {
        List<Integer> userIdList = StrUtil.splitToListInt(userIds);
        sysRoleUserService.changeRoleUsers(roleId, userIdList);
        return ResultUtil.success();
    }

}
