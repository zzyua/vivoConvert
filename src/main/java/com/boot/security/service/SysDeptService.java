package com.boot.security.service;

import com.boot.common.RequestHolder;
import com.boot.exception.RestDymaicException;
import com.boot.exception.ResultEnum;
import com.boot.model.SysDept;
import com.boot.security.dao.SysDeptMapper;
import com.boot.security.dao.SysUserMapper;
import com.boot.security.param.DeptParam;
import com.boot.util.BeanValidator;
import com.boot.util.IpUtil;
import com.boot.util.LevelUtil;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class SysDeptService {

    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysUserMapper sysUserMapper;


//    @Resource
//    private SysLogService sysLogService;


    public void save(DeptParam param) throws Exception{
        //基础验证
        BeanValidator.check(param);
        if(checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new RestDymaicException(ResultEnum.EXITSSAMENAMESYSDEPY) ;
        }
        SysDept dept = SysDept.builder().name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).remark(param.getRemark()).build();

        dept.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));

        dept.setOperator(RequestHolder.getCurrentUser().getUsername());
        dept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));

//        dept.setOperator("ADMIN_insert");
//        dept.setOperateIp("127.0.0.1");
        dept.setOperateTime(new Date());
        sysDeptMapper.insertSelective(dept);
//        sysLogService.saveDeptLog(null, dept);
    }

    private boolean checkExist(Integer parentId, String deptName, Integer deptId) {
        return sysDeptMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;
    }

    private String getLevel(Integer deptId) {
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        if (dept == null) {
            return null;
        }
        return dept.getLevel();
    }


    public void update(DeptParam param) throws Exception{
        BeanValidator.check(param);
        if(checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new RestDymaicException(ResultEnum.EXITSSAMENAMESYSDEPY);
        }
        SysDept before = sysDeptMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的部门不存在");
        if(checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new RestDymaicException(ResultEnum.EXITSSAMENAMESYSDEPY);
        }

        SysDept after = SysDept.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).remark(param.getRemark()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));

        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));

//        after.setOperator("ADMIN_update");
//        after.setOperateIp("127.0.0.1");
        after.setOperateTime(new Date());

        updateWithChild(before, after);

//        sysLogService.saveDeptLog(before, after);
    }

    private void updateWithChild(SysDept before, SysDept after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {
            List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(before.getLevel()+".%" );
            if (CollectionUtils.isNotEmpty(deptList)) {
                for (SysDept dept : deptList) {
                    String level = dept.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                sysDeptMapper.batchUpdateLevel(deptList);
            }
        }
        sysDeptMapper.updateByPrimaryKey(after);
    }

    /**
     * 删除部门
     * @param deptId
     */
    public void delete(int deptId) throws Exception{
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        if(dept == null ){
            throw  new RestDymaicException(ResultEnum.NEEDDELETEDEPT_NOTEXITS);
        }
        if (sysDeptMapper.countByParentId(dept.getId()) > 0) {
            throw new RestDymaicException(ResultEnum.CHILDDEPTS_EXITS) ;
        }
        if(sysUserMapper.countByDeptId(dept.getId()) > 0) {
            throw new RestDymaicException(ResultEnum.THISDEPTHAHUSER_EXITS);
        }
        sysDeptMapper.deleteByPrimaryKey(deptId);
    }
}
