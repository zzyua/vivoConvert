package com.boot.security.service;

import com.boot.common.RequestHolder;
import com.boot.exception.RestDymaicException;
import com.boot.exception.ResultEnum;
import com.boot.model.SysRole;
import com.boot.security.dao.SysRoleMapper;
import com.boot.security.param.RoleParam;
import com.boot.util.BeanValidator;
import com.boot.util.IpUtil;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    public void save(RoleParam param) throws Exception{
        BeanValidator.check(param);
        if (checkExist(param.getName(), param.getId())) {
            throw new RestDymaicException(ResultEnum.ROLENAME_EXITS);
        }
        SysRole role = SysRole.builder().name(param.getName()).status(param.getStatus()).type(param.getType())
                .remark(param.getRemark()).build();
        role.setOperator(RequestHolder.getCurrentUser().getUsername());
        role.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        role.setOperateTime(new Date());
        sysRoleMapper.insertSelective(role);
//        sysLogService.saveRoleLog(null, role);
    }

    public void update(RoleParam param) throws Exception{
        BeanValidator.check(param);
        if (checkExist(param.getName(), param.getId())) {
            throw new RestDymaicException(ResultEnum.ROLENAME_EXITS);
        }
        SysRole before = sysRoleMapper.selectByPrimaryKey(param.getId());
        if(before == null ){
            throw new RestDymaicException(ResultEnum.ROLTOUPDATE_NOTEXITS);
        }

        SysRole after = SysRole.builder().id(param.getId()).name(param.getName()).status(param.getStatus()).type(param.getType())
                .remark(param.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        sysRoleMapper.updateByPrimaryKeySelective(after);
//        sysLogService.saveRoleLog(before, after);
    }

    private boolean checkExist(String name, Integer id) {
        return sysRoleMapper.countByName(name, id) > 0;
    }

    public List<SysRole> getAll() {
        return sysRoleMapper.getAll();
    }
}
