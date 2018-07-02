package com.boot.security.service;

import com.boot.common.RequestHolder;
import com.boot.exception.RestDymaicException;
import com.boot.exception.ResultEnum;
import com.boot.model.SysUser;
import com.boot.security.dao.SysUserMapper;
import com.boot.security.param.UserParam;
import com.boot.util.BeanValidator;
import com.boot.util.IpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysLogService sysLogService;

    public void save(UserParam param) throws Exception{
        BeanValidator.check(param);
        if(checkTelephoneExist(param.getTelephone(), param.getId())) {
            throw new RestDymaicException(ResultEnum.TELEPOHONENUM_EXITS);
        }
//        if(checkEmailExist(param.getMail(), param.getId())) {
//            throw new RestDymaicException(ResultEnum.EMAILNUM_EXITS);
//        }
//        String password = PasswordUtil.randomPassword();
        //TODO: 密码是否需要加密处理
//        String encryptedPassword = MD5Util.encrypt(password);
        String encryptedPassword = param.getPassword();
        SysUser user = SysUser.builder().username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
                .password(encryptedPassword).deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();

        user.setOperator(RequestHolder.getCurrentUser().getUsername());
        user.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        user.setOperateTime(new Date());

        // TODO: sendEmail

        sysUserMapper.insertSelective(user);
        sysLogService.saveUserLog(null, user);
    }

    public void update(UserParam param) throws Exception{
        BeanValidator.check(param);
        if(checkTelephoneExist(param.getTelephone(), param.getId())) {
            throw new RestDymaicException(ResultEnum.TELEPOHONENUM_EXITS);
        }
//        if(checkEmailExist(param.getMail(), param.getId())) {
//            throw new RestDymaicException(ResultEnum.EMAILNUM_EXITS);
//        }
        SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
        if(before == null ){
            throw new RestDymaicException(ResultEnum.NEEDUPDATEUSER_NOTEXITS);
        }
        String encryptedPassword = param.getPassword();
        SysUser after = SysUser.builder().id(param.getId()).username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
                .password(encryptedPassword).deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();


        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        sysUserMapper.updateByPrimaryKeySelective(after);
        sysLogService.saveUserLog(before, after);
    }

    public boolean checkEmailExist(String mail, Integer userId) {
        return sysUserMapper.countByMail(mail, userId) > 0;
    }

    public boolean checkTelephoneExist(String telephone, Integer userId) {
        return sysUserMapper.countByTelephone(telephone, userId) > 0;
    }
//
    public SysUser findByKeyword(String keyword) {
        return sysUserMapper.findByKeyword(keyword);
    }

//    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery page) {
//        BeanValidator.check(page);
//        int count = sysUserMapper.countByDeptId(deptId);
//        if (count > 0) {
//            List<SysUser> list = sysUserMapper.getPageByDeptId(deptId, page);
//            return PageResult.<SysUser>builder().total(count).data(list).build();
//        }
//        return PageResult.<SysUser>builder().build();
//    }



    public List<SysUser> getAllByDeptId(int deptId) throws Exception{
        List<SysUser> list = sysUserMapper.getAllByDeptId(deptId);
        return list;
    }


    public List<SysUser> getAll() {
        return sysUserMapper.getAll();
    }
}
