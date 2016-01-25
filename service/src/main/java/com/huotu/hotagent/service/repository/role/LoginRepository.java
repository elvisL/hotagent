package com.huotu.hotagent.service.repository.role;

import com.huotu.hotagent.service.entity.role.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 登录
 * Created by cwb on 2016/1/25.
 */
@Repository
public interface LoginRepository extends JpaRepository<Login,Long> {
    Login findByUsername(String username);
}
