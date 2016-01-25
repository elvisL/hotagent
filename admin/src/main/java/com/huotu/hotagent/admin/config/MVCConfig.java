/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.admin.config;

import com.huotu.hotagent.admin.config.thymeleaf.dialects.HotAgentDialect;
import com.huotu.hotagent.common.constant.StringConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by allan on 1/22/16.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "com.huotu.hotagent.admin"
})
@Import(SecurityConfig.class)
public class MVCConfig extends WebMvcConfigurerAdapter {
    /**
     * 静态资源处理,加在这里
     */
    private static String[] STATIC_RESOURCE_PATH = {
            "css",
            "fonts",
            "holder.js",
            "images",
            "js",
            "_resources"
    };

    @Autowired
    private Environment environment;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @SuppressWarnings("Duplicates")
    public String[] staticResourcePathPatterns() {
        String[] ignoring;
        int startIndex = 0;
        if (environment.acceptsProfiles("development")) {
            ignoring = new String[MVCConfig.STATIC_RESOURCE_PATH.length + 2];
            ignoring[startIndex++] = "/**/*.html";
            ignoring[startIndex++] = "/mock/**";
        } else {
            ignoring = new String[MVCConfig.STATIC_RESOURCE_PATH.length];
        }
        for (String path : MVCConfig.STATIC_RESOURCE_PATH) {
            ignoring[startIndex++] = "/" + path + "/**";
        }
        return ignoring;
    }

    /**
     * 静态资源设置
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        for (String resourcePath : MVCConfig.STATIC_RESOURCE_PATH) {
            registry.addResourceHandler("/" + resourcePath + "/**").addResourceLocations("/" + resourcePath + "/");
        }
    }

    /**
     * thymeleaf解析器
     *
     * @return
     */
    @Bean
    @SuppressWarnings("Duplicates")
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();

        SpringResourceTemplateResolver springResourceTemplateResolver = new SpringResourceTemplateResolver();
        springResourceTemplateResolver.setOrder(1);
        springResourceTemplateResolver.setPrefix("/");
        springResourceTemplateResolver.setSuffix(".html");
        springResourceTemplateResolver.setApplicationContext(applicationContext);
        springResourceTemplateResolver.setCharacterEncoding(StringConstant.UTF8);
        //设置缓存
        if (environment.acceptsProfiles("development"))
            springResourceTemplateResolver.setCacheable(false);


        springTemplateEngine.setTemplateResolver(springResourceTemplateResolver);
        springTemplateEngine.setAdditionalDialects(new HashSet<>(Arrays.asList(
                new HotAgentDialect()
        )));

        resolver.setTemplateEngine(springTemplateEngine);
        resolver.setOrder(1);
        resolver.setCharacterEncoding(StringConstant.UTF8);
        return resolver;
    }

    /**
     * 注册视图解析器
     *
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(thymeleafViewResolver);
    }

    /**
     * for upload
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }
}
