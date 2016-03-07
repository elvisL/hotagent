/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.entity.role.agent;

import com.huotu.hotagent.service.entity.product.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 客户
 * Created cwb on 2016/1/22.
 */
@Entity
@Table(name = "age_customer")
@Getter
@Setter
public class Customer {

    @Id
    @Column(name = "customerId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    /**
     * 名字
     */
    @Column(name = "name")
    private String name;

    /**
     * 公司
     */
    @Column(name = "company")
    private String company;

    /**
     * 产品id
     */
    @OneToOne
    @JoinColumn(name = "productId")
    private Product product;

    /**
     * 所属代理商
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agentId")
    private Agent agent;

    /**
     * 联系人
     */
    @Column(name = "contacts")
    private String contacts;

    /**
     * 手机号
     */
    @Column(name = "phoneNo")
    private String phoneNo;

    /**
     * 伙伴商城登录帐号
     */
    @Column(name = "loginName")
    private String loginName;

    /**
     * 伙伴商城登录密码
     */
    @Column(name = "loginPassword")
    private String loginPassword;

    /**
     * 销售数量
     */
    @Column(name = "saleNum")
    private int saleNum;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;


}
