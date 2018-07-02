package com.boot.controller;

import com.boot.entity.PageQuery;
import com.boot.exception.Result;
import com.boot.exception.ResultUtil;
import com.boot.security.param.SearchLogParam;
import com.boot.security.service.SysLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 描述：
 *
 * @author: zhangzy
 * @create: 2018-06-29
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {

    @Resource
    private SysLogService sysLogService;


    @RequestMapping("/log.page")
    public String page(Model model) {
        model.addAttribute("pagekey" , "log") ;
        return "vivo/log";
    }

    @RequestMapping("/recover.json")
    @ResponseBody
    public Result recover(@RequestParam("id") int id) {
        sysLogService.recover(id);
        return ResultUtil.success();
    }


    @RequestMapping("/page.json")
    @ResponseBody
    public Result searchPage(SearchLogParam param, PageQuery page) throws Exception{
        return ResultUtil.success(sysLogService.searchPageList(param, page));
    }
}
