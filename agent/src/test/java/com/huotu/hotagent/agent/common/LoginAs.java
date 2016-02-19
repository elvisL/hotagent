/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.agent.common;

import com.huotu.hotagent.service.common.Authority;

import java.lang.annotation.*;

/**
 * Created by allan on 1/29/16.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LoginAs {
    /**
     * 设置登录权限
     *
     * @return
     */
    Authority[] value() default {};

    /**
     * 是否是超级管理员登录
     *
     * @return
     */
    boolean isRoot() default false;
}
