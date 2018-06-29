package com.boot.security.service;


import com.boot.common.RequestHolder;

import com.boot.exception.RestDymaicException;
import com.boot.exception.ResultEnum;
import com.boot.model.SysAclModule;
import com.boot.security.dao.SysAclMapper;
import com.boot.security.dao.SysAclModuleMapper;
import com.boot.security.param.AclModuleParam;
import com.boot.util.BeanValidator;
import com.boot.util.IpUtil;
import com.boot.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SysAclModuleService {

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;
    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysLogService sysLogService;

    public void save(AclModuleParam param) throws Exception{
        BeanValidator.check(param);
        if(checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new RestDymaicException(ResultEnum.EXITSSAMENAMESYSACLMODUAL);
        }
        SysAclModule aclModule = SysAclModule.builder().name(param.getName()).parentId(param.getParentId()).seq(param.getSeq())
                .status(1).remark(param.getRemark()).build();
        aclModule.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        aclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
        aclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        aclModule.setOperateTime(new Date());
        sysAclModuleMapper.insertSelective(aclModule);
        sysLogService.saveAclModuleLog(null, aclModule);
    }

    public void update(AclModuleParam param) throws Exception{
        BeanValidator.check(param);
        if(checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new RestDymaicException(ResultEnum.EXITSSAMENAMESYSACLMODUAL);
        }
        SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(param.getId());
        if(before == null){
            throw new RestDymaicException(ResultEnum.ACLMODUL_NOTEXITS);
        }

        SysAclModule after = SysAclModule.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId()).seq(param.getSeq())
                 .remark(param.getRemark()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        updateWithChild(before, after);
        sysLogService.saveAclModuleLog(before, after);
    }


    private void updateWithChild(SysAclModule before, SysAclModule after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {
            List<SysAclModule> aclModuleList = sysAclModuleMapper.getChildAclModuleListByLevel(before.getLevel()+".%");
            if (CollectionUtils.isNotEmpty(aclModuleList)) {
                for (SysAclModule aclModule : aclModuleList) {

                    String level = aclModule.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        aclModule.setLevel(level);
                    }
                }
                sysAclModuleMapper.batchUpdateLevel(aclModuleList);
            }
        }
        sysAclModuleMapper.updateByPrimaryKeySelective(after);
    }

    private boolean checkExist(Integer parentId, String aclModuleName, Integer deptId) {
        return sysAclModuleMapper.countByNameAndParentId(parentId, aclModuleName, deptId) > 0;
    }

    private String getLevel(Integer aclModuleId) {
        SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        if (aclModule == null) {
            return null;
        }
        return aclModule.getLevel();
    }

    public void delete(int aclModuleId) throws Exception{
        SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        if(aclModule == null ){
            throw new RestDymaicException(ResultEnum.NEEDTODELETEACLMODUL_NOTEXITS);

        }
        if(sysAclModuleMapper.countByParentId(aclModule.getId()) > 0) {
            throw new RestDymaicException(ResultEnum.HASCHILDACLMODUL_EXITS);
        }
        if (sysAclMapper.countByAclModuleId(aclModule.getId()) > 0) {
            throw new RestDymaicException(ResultEnum.HASUSERACLMODUL_EXITS);
        }
        sysAclModuleMapper.deleteByPrimaryKey(aclModuleId);
    }
}
