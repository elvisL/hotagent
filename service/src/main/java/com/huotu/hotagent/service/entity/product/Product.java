/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.entity.product;

import com.huotu.hotagent.service.common.ProductType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 产品
 * Createy cwb on 2016/1/22.
 */
@Entity
@Table(name = "age_product", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 产品名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 产品类型
     */
    @Column(name = "productType")
    private ProductType productType;

    /**
     * 产品描述
     */
    @Column(name = "productDesc")
    private String productDesc;

    /**
     * 一级代理商统计成本价
     */
    @Column(precision = 2)
    private double basePrice;


    /**
     * 父级产品
     */
    @ManyToOne
    @JoinColumn(name = "parentId")
    private Product parent;
}
