package com.boot.controller;


import com.boot.exception.Result;
import com.boot.exception.ResultUtil;
import com.boot.model.SysRole;
import com.boot.security.param.AclParam;
import com.boot.security.service.SysAclService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys/acl")
@Slf4j
public class SysAclController {

    @Resource
    private SysAclService sysAclService;
//    @Resource
//    private SysRoleService sysRoleService;

    @RequestMapping("/save.json")
    @ResponseBody
    public Result saveAclModule(AclParam param) throws Exception{
        sysAclService.save(param);
        return ResultUtil.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public Result updateAclModule(AclParam param) throws Exception{
        sysAclService.update(param);
        return ResultUtil.success();
    }

//    @RequestMapping("/page.json")
//    @ResponseBody
//    public Result list(@RequestParam("aclModuleId") Integer aclModuleId, PageQuery pageQuery) {
//        return ResultUtil.success(sysAclService.getPageByAclModuleId(aclModuleId, pageQuery));
//    }

//    @RequestMapping("acls.json")
//    @ResponseBody
//    public Result acls(@RequestParam("aclId") int aclId) {
//        Map<String, Object> map = Maps.newHashMap();
//        List<SysRole> roleList = sysRoleService.getRoleListByAclId(aclId);
//        map.put("roles", roleList);
//        map.put("users", sysRoleService.getUserListByRoleList(roleList));
//        return ResultUtil.success(map);
//    }
}
