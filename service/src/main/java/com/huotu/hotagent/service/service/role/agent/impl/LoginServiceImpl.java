/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.service.role.agent.impl;

import com.huotu.hotagent.service.entity.role.agent.Login;
import com.huotu.hotagent.service.repository.role.agent.LoginRepository;
import com.huotu.hotagent.service.service.role.agent.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 登录服务
 * Created by cwb on 2016/1/25.
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public <T extends Login> T newLogin(T login, CharSequence password) {
        login.setPassword(passwordEncoder.encode(password));
        return loginRepository.saveAndFlush(login);
    }

    @Override
    public Login findLoginById(Long id) {
        return loginRepository.findOne(id);
    }

    @Override
    public Login saveLogin(Login login) {
        return loginRepository.saveAndFlush(login);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login login = loginRepository.findByUsername(username);
        if (login == null) {
            throw new UsernameNotFoundException("无效的用户名。");
        }
        return login;
    }
}
