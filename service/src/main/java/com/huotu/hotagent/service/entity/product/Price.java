package com.huotu.hotagent.service.entity.product;

import com.huotu.hotagent.service.entity.role.Agent;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 代理商产品价格
 * Created by cwb on 2016/1/22.
 */
@Entity
@Table(name = "age_price")
@Getter
@Setter
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 所属代理商
     */
    @ManyToOne
    @JoinColumn(name = "agentId")
    private Agent agent;

    /**
     * 代理的产品
     */
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    /**
     * 价格
     */
    @Column(name = "price")
    private double price;

}
