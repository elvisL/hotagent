/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.admin.config.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.dialect.IProcessorDialect;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IElementAttributes;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.processor.AbstractStandardExpressionAttributeTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 菜单选中与否class解析器
 * Created by allan on 1/24/16.
 */
public abstract class ActiveMenuProcessor extends AbstractStandardExpressionAttributeTagProcessor {
    private static final int PRECEDENCE = 200;
    //    private static final String ATTR_NAME = "active";
    private static final String HTML_ATTR_NAME = "class";

    public ActiveMenuProcessor(IProcessorDialect dialect, String dialectPrefix, String attrName) {
        super(
                dialect, TemplateMode.HTML, dialectPrefix,
                attrName,
                PRECEDENCE, true
        );
    }

    /**
     * 命中以后的class值
     *
     * @return
     */
    abstract String hitClasses();

    @SuppressWarnings("Duplicates")
    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName, String attributeValue, String attributeTemplateName, int attributeLine, int attributeCol, Object expressionResult, IElementTagStructureHandler structureHandler) {
        IElementAttributes elementAttributes = tag.getAttributes();
        String currentClass = elementAttributes.hasAttribute(HTML_ATTR_NAME) ? elementAttributes.getValue(HTML_ATTR_NAME) : "";
        String newClass = hitClasses();
        String activeMenus = (String) context.getVariable("activeMenu");
        String[] attributeValues = attributeValue.split(",");
        List<String> strs = new ArrayList<>();
        for(String attr :attributeValues) {
            if(attr.startsWith("'")){
                attr = attr.substring(1);
            }
            if(attr.endsWith("'")) {
                attr = attr.substring(0,attr.indexOf("'"));
            }
            strs.add(attr);
        }
        if (strs.contains(activeMenus)) {
            elementAttributes.setAttribute(HTML_ATTR_NAME, currentClass + newClass);
        } else {
            if (currentClass.equals("")) {
                elementAttributes.removeAttribute(HTML_ATTR_NAME);
            } else {
                elementAttributes.setAttribute(HTML_ATTR_NAME, currentClass);
            }
        }
    }
}
