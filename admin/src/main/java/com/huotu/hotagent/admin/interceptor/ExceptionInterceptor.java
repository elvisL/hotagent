package com.huotu.hotagent.admin.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * �쳣ͳһ����
 * Created by cwb on 2016/1/25.
 */
@ControllerAdvice
public class ExceptionInterceptor {

    private static final Log log = LogFactory.getLog(ExceptionInterceptor.class);

    @ExceptionHandler
    public String catchException(Throwable e) {
        e.printStackTrace();
        log.error("�쳣����",e);
        return "assets/email";
    }
}
