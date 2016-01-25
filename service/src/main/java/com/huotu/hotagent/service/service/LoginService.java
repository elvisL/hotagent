package com.huotu.hotagent.service.service;

import com.huotu.hotagent.service.entity.role.Login;
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
