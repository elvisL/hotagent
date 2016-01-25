/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.entity.role;

import javax.persistence.*;

/**
 *  权限
 * Created by cwb on 2016/1/21.
 */
@Entity
@Table(name = "age_authority",uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 权限名字,以"ROLE_"开头的字符串
     */
    @Column(name = "name")
    private String name;
    /**
     * 权限描述，前端页面显示的具体描述
     */
    @Column(name = "desc")
    private String desc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
