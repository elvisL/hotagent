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
@Table(name = "age_product",uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
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
    @Column(name = "desc")
    private String desc;
}
