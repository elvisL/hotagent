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
import com.huotu.hotagent.admin.interceptor.AgentModelResolver;
import com.huotu.hotagent.admin.interceptor.CustomMethodArgumentResolver;
import com.huotu.hotagent.common.constant.StringConstant;
import com.huotu.hotagent.common.utils.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.util.ArrayUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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
            "assets"
    };

    @Autowired
    private Environment environment;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ViewResolver htmlViewResolver;
    @Autowired
    private ViewResolver javascriptViewResolver;
    @Autowired
    private AgentModelResolver agentModelResolver;
    @Autowired
    private CustomMethodArgumentResolver customMethodArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(agentModelResolver);
        argumentResolvers.add(customMethodArgumentResolver);
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
     * 注册视图解析器
     *
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(htmlViewResolver);
        registry.viewResolver(javascriptViewResolver);
    }

    /**
     * html解析器
     *
     * @return
     */
    @Bean
    @SuppressWarnings("Duplicates")
    public ViewResolver htmlViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine(htmlTemplateResolver()));
        resolver.setOrder(1);
        resolver.setContentType("text/html;charset=utf-8");
        resolver.setCharacterEncoding(StringConstant.UTF8);
        return resolver;
    }

    /**
     * javascript 解析器
     * @return
     */
    @Bean
    @SuppressWarnings("Duplicates")
    public ViewResolver javascriptViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine(javascriptTemplateResolver()));
        resolver.setContentType("application/javascript");
        resolver.setCharacterEncoding(StringConstant.UTF8);
        resolver.setViewNames(ArrayUtil.array("*.js"));
        return resolver;
    }
    private ITemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setAdditionalDialects(new HashSet<>(Arrays.asList(
                new HotAgentDialect(),
                new SpringSecurityDialect()
        )));
        engine.setTemplateResolver(templateResolver);
        return engine;
    }
    private ITemplateResolver htmlTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("/views/");
        resolver.setSuffix(".html");
        resolver.setApplicationContext(applicationContext);
        resolver.setCharacterEncoding(StringConstant.UTF8);
        //设置缓存
        if (environment.acceptsProfiles("development")) {
            resolver.setCacheable(false);
        }
        return resolver;
    }
    private ITemplateResolver javascriptTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setCharacterEncoding(StringConstant.UTF8);
        resolver.setTemplateMode(TemplateMode.JAVASCRIPT);
        //设置缓存
        if (environment.acceptsProfiles("development")) {
            resolver.setCacheable(false);
        }
        return resolver;
    }
    /**
     * for upload
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));
        converters.add(converter);
    }
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.extendMessageConverters(converters);
    }

}
