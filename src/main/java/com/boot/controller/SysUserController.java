package com.boot.controller;

import com.boot.exception.Result;
import com.boot.exception.ResultUtil;
import com.boot.model.SysUser;
import com.boot.security.param.UserParam;
import com.boot.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;
//    @Resource
//    private SysTreeService sysTreeService;
//    @Resource
//    private SysRoleService sysRoleService;

    @RequestMapping("/noAuth.page")
    public ModelAndView noAuth() {
        return new ModelAndView("noAuth");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public Result saveUser(@RequestBody UserParam param) throws Exception{
        sysUserService.save(param);
        return ResultUtil.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public Result updateUser(@RequestBody UserParam param) throws Exception{
        sysUserService.update(param);
        return ResultUtil.success();
    }

//    @RequestMapping("/page.json")
//    @ResponseBody
//    public Result page(@RequestParam("deptId") int deptId, PageQuery pageQuery) {
//        PageResult<SysUser> result =sysUserService.getPageByDeptId(deptId, pageQuery);
//        return ResultUtil.success(result);
//    }


    @RequestMapping("/depyuser_page.json")
    @ResponseBody
    public Result page(@RequestParam("deptId") int deptId) throws Exception{
        List<SysUser> result = sysUserService.getAllByDeptId(deptId);
        return ResultUtil.success(result);
    }

//    @RequestMapping("/acls.json")
//    @ResponseBody
//    public JsonData acls(@RequestParam("userId") int userId) {
//        Map<String, Object> map = Maps.newHashMap();
//        map.put("acls", sysTreeService.userAclTree(userId));
//        map.put("roles", sysRoleService.getRoleListByUserId(userId));
//        return JsonData.success(map);
//    }
}
