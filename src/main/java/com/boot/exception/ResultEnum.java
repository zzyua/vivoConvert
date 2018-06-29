package com.boot.exception;

import com.boot.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * 统一定义 自定义异常的类型
 * @author zhangzy
 */
public enum  ResultEnum {



    TEST_ERROR(-111,"TEST_ERROR"),
    UNKNOW_ERROR(-1,"UNKNOW_ERROR"),
    REQUESTURLVAILD(-2, "REQUESTURLVAILD"),
    EXITSSAMENAMESYSDEPY(3, "EXITSSAMENAMESYSDEPY"),
    NEEDDELETEDEPT_NOTEXITS(4, "NEEDDELETEDEPT_NOTEXITS"),
    CHILDDEPTS_EXITS(5,"CHILDDEPTS_EXITS"),
    TELEPOHONENUM_EXITS(6,"TELEPOHONENUM_EXITS"),
    EMAILNUM_EXITS(7,"EMAILNUM_EXITS"),
    NEEDUPDATEUSER_NOTEXITS(8,"NEEDUPDATEUSER_NOTEXITS"),
    SAMEACLMODULNAME_EXITS(9,"SAMEACLMODULNAME_EXITS"),
    ACLMODULNAME_NOTEXITS(10,"ACLMODULNAME_NOTEXITS"),
    EXITSSAMENAMESYSACLMODUAL(11, "EXITSSAMENAMESYSACLMODUAL"),
    ACLMODUL_NOTEXITS(12,"ACLMODUL_NOTEXITS"),
    NEEDTODELETEACLMODUL_NOTEXITS(13,"NEEDTODELETEACLMODUL_NOTEXITS"),
    HASCHILDACLMODUL_EXITS(14,"HASCHILDACLMODUL_EXITS"),
    HASUSERACLMODUL_EXITS(15,"HASUSERACLMODUL_EXITS"),
    THISDEPTHAHUSER_EXITS(16,"THISDEPTHAHUSER_EXITS"),
    ROLENAME_EXITS(17,"ROLENAME_EXITS"),
    ROLTOUPDATE_NOTEXITS(18,"ROLTOUPDATE_NOTEXITS"),
    NOAUTHVIEW(19,"NOAUTHVIEW")



    ;



    private Integer code ;

    private String message ;

    ResultEnum(Integer code, String message) {
        if(messageSource == null ){
            messageSource =SpringUtil.getBean("messageSource", MessageSource.class);
        }
        this.code = code;
        this.message = message;
    }

    private MessageSource  messageSource  ;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(message, null, locale);
    }
}
