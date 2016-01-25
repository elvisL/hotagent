/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.agent.config.thymeleaf.processor;

import org.thymeleaf.dialect.IProcessorDialect;

/**
 * Created by allan on 1/24/16.
 */
public class ActiveChildProcessor extends ActiveMenuProcessor {
    private static final String ACTIVE_CLASSES = " active";
    private static final String ATTR_NAME = "active";

    public ActiveChildProcessor(IProcessorDialect dialect, String dialectPrefix) {
        super(dialect, dialectPrefix, ATTR_NAME);
    }

    @Override
    String hitClasses() {
        return ACTIVE_CLASSES;
    }
}
