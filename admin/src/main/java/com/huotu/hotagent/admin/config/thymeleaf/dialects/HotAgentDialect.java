/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.admin.config.thymeleaf.dialects;

import com.huotu.hotagent.admin.config.thymeleaf.processor.ActiveChildProcessor;
import com.huotu.hotagent.admin.config.thymeleaf.processor.ActiveParentProcessor;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.dialect.IProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 代理商平台个性方言
 * Created by allan on 1/24/16.
 */
public class HotAgentDialect extends AbstractProcessorDialect {
    public static final String NAME = "Agent";
    public static final String PREFIX = "agent";
    public static final int PROCESSOR_PRECEDENCE = 100;

    public HotAgentDialect() {
        super(NAME, PREFIX, PROCESSOR_PRECEDENCE);
    }


    private Set<IProcessor> createHotAgentProcessors(final IProcessorDialect dialect, final String dialectPrefix) {
        final Set<IProcessor> processors = new LinkedHashSet<>();

        processors.add(new ActiveChildProcessor(dialect, dialectPrefix));
        processors.add(new ActiveParentProcessor(dialect, dialectPrefix));

        return processors;
    }

    @Override
    public Set<IProcessor> getProcessors(final String dialectPrefix) {
        return createHotAgentProcessors(this, dialectPrefix);
    }
}
