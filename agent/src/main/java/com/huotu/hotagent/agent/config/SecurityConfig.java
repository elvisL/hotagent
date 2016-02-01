/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.agent.config;

import com.huotu.hotagent.service.service.role.agent.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 安全配置
 * Created by cwb on 1/22/16.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    public static final String loginPage = "/login";
    public static final String loginSuccessURL = "/loginSuccess";
    public static final String loginFailedURL = "/loginFailed";
    public static final String logoutSuccessURL = "/";

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LoginService loginService;

    private static String[] STATIC_RESOURCE_PATH = {
            "/assets/**",
            "/views/**",
            "/loginError"
    };

    @Autowired
    public void registerSharedAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(STATIC_RESOURCE_PATH);
    }





        @Override
        protected void configure(HttpSecurity http) throws Exception {
            SecurityFailureHandler securityFailureHandler = new SecurityFailureHandler(loginFailedURL);
            http
                    .headers().frameOptions().sameOrigin()
                    .and()
                    .authorizeRequests()
                    .antMatchers(STATIC_RESOURCE_PATH)
                    .permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .csrf().disable()
                    .formLogin()
                    .loginPage(loginPage)
                    .defaultSuccessUrl(loginSuccessURL, true)
                    .failureUrl(loginFailedURL)
                    .permitAll()
                    .and()
                    .logout()
                    .logoutSuccessUrl(logoutSuccessURL);

        }

}
