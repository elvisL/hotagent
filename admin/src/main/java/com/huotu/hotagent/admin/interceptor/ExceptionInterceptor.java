/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.admin.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 统一异常处理
 * Created by cwb on 2016/1/25.
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
