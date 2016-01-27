/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.entity.role.manager;

import com.huotu.hotagent.service.common.Authority;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by allan on 1/27/16.
 */
@Entity
@Table(name = "age_manager")
@Setter
@Getter
public class Manager implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 账号名称
     */
    @Column(length = 50)
    private String username;
    /**
     * 管理员姓名
     */
    @Column(length = 100)
    private String name;
    /**
     * 角色名称
     */
    @Column(length = 100)
    private String roleName;
    /**
     * 账号密码
     */
    private String password;
    /**
     * 账号是否被冻结
     */
    private boolean accountNonLocked = true;

    @Transient
    private Set<Authority> authorities = new HashSet<>();
    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        authorities.forEach(authority -> simpleGrantedAuthorities.add(new SimpleGrantedAuthority(authority.getValue())));
        return simpleGrantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
