package com.huotu.hotagent.agent.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by Administrator on 2015/12/18.
 */
@ControllerAdvice
public class ExceptionInterceptor {

    private static final Log log = LogFactory.getLog(ExceptionInterceptor.class);

    @ExceptionHandler
    public String catchException(Throwable e) {
        e.printStackTrace();
        return "assets/email";
    }
}
