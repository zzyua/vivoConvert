package com.boot.security.service;

import com.boot.common.RequestHolder;
import com.boot.exception.RestDymaicException;
import com.boot.exception.ResultEnum;
import com.boot.model.SysAcl;
import com.boot.model.SysUser;
import com.boot.security.dao.SysAclMapper;
import com.boot.security.param.AclParam;
import com.boot.util.BeanValidator;
import com.boot.util.IpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SysAclService {

    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysLogService sysLogService;

    public void save(AclParam param) throws Exception{
        BeanValidator.check(param);
        if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
            throw new RestDymaicException(ResultEnum.SAMEACLMODULNAME_EXITS);
        }
        SysAcl acl = SysAcl.builder().name(param.getName()).aclModuleId(param.getAclModuleId()).url(param.getUrl())
                .type(param.getType()).status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
        acl.setCode(generateCode());
        acl.setOperator(RequestHolder.getCurrentUser().getUsername());
        acl.setOperateTime(new Date());
        acl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysAclMapper.insertSelective(acl);
        sysLogService.saveAclLog(null, acl);
    }

    public void update(AclParam param) throws Exception{
        BeanValidator.check(param);
        if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
            throw new RestDymaicException(ResultEnum.SAMEACLMODULNAME_EXITS);
        }
        SysAcl before = sysAclMapper.selectByPrimaryKey(param.getId());
        if(before == null ){
            throw new RestDymaicException(ResultEnum.ACLMODULNAME_NOTEXITS);
        }

        SysAcl after = SysAcl.builder().id(param.getId()).name(param.getName()).aclModuleId(param.getAclModuleId()).url(param.getUrl())
                .type(param.getType()).status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateTime(new Date());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));

        sysAclMapper.updateByPrimaryKeySelective(after);
        sysLogService.saveAclLog(before, after);
    }

    public boolean checkExist(int aclModuleId, String name, Integer id) {
        return sysAclMapper.countByNameAndAclModuleId(aclModuleId, name, id) > 0;
    }

    public String generateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date()) + "_" + (int)(Math.random() * 100);
    }

    public List<SysAcl> getAllByAclModulId(Integer aclModuleId){
        List<SysAcl> list = sysAclMapper.getAllByAclModulId(aclModuleId);
        return list ;
    }

//    public PageResult<SysAcl> getPageByAclModuleId(int aclModuleId, PageQuery page) {
//        BeanValidator.check(page);
//        int count = sysAclMapper.countByAclModuleId(aclModuleId);
//        if (count > 0) {
//            List<SysAcl> aclList = sysAclMapper.getPageByAclModuleId(aclModuleId, page);
//            return PageResult.<SysAcl>builder().data(aclList).total(count).build();
//        }
//        return PageResult.<SysAcl>builder().build();
//    }
}
