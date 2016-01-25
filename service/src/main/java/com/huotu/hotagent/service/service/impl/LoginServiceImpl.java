package com.huotu.hotagent.service.service.impl;

import com.huotu.hotagent.service.entity.role.Login;
import com.huotu.hotagent.service.repository.LoginRepository;
import com.huotu.hotagent.service.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
