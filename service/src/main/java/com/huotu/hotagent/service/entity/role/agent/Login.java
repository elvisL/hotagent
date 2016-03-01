/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.entity.role.agent;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by allan on 1/27/16.
 */
@Entity
@Table(name = "age_login",uniqueConstraints = @UniqueConstraint(columnNames ={"username"}))
@Setter
@Getter
@Inheritance
public abstract class Login implements UserDetails, Serializable {

    private static final long serialVersionUID = -4886674511959360750L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 账号名称
     */
    @Column(length = 50)
    private String username;
    /**
     * 账号密码
     */
    private String password;
    /**
     * 账号是否被冻结
     */
    private boolean accountNonLocked = true;

    /**
     * 账号是否过期
     */
    private boolean accountNonExpired = true;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
