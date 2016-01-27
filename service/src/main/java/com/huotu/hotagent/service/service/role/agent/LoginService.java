/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.service.role.agent;

import com.huotu.hotagent.service.entity.role.agent.Login;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 登录
 * Created by cwb on 2016/1/25.
 */
public interface LoginService extends UserDetailsService {

    /**
     * 新建一个可登陆用户
     *
     * @param login    准备新建的可登陆用户
     * @param password 明文密码
     */
    <T extends Login> T newLogin(T login, CharSequence password);

    Login findLoginById(Long id);

    Login saveLogin(Login login);
}
