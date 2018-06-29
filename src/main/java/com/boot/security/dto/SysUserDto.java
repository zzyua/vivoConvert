package com.boot.security.dto;

import com.boot.model.SysUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

/**
 * 描述：
 *
 * @author: zhangzy
 * @create: 2018-06-27
 */

@Getter
@Setter
@ToString
public class SysUserDto extends SysUser{

    private boolean selected ;


    public static SysUserDto adapt(SysUser sysUser) {
        SysUserDto dto = new SysUserDto();
        BeanUtils.copyProperties(sysUser, dto);
        return dto;
    }
}
