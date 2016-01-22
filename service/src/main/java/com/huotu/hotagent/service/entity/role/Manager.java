/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.entity.role;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.*;

/**
 * 系统管理员
 * Created by cwb on 2016/1/21.
 */
@Entity
@Table(name = "age_manager")
public class Manager extends Login {

    /**
     * 管理员昵称（姓名）
     */
    @Column(name = "nickName",length = 50)
    private String nickName;
    /**
     * 角色名称
     */
    @Column(name = "roleName",length = 50)
    private String roleName;

    /**
     * 拥有的权限
     */
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Authority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> simpleGrantedAuthorityList = new HashSet<>();
        for(Authority authority:authorities) {
            simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return simpleGrantedAuthorityList;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(Authority authority) {
        if(authorities == null) {
            authorities = new HashSet<>();
        }
        authorities.add(authority);
    }
}
