package com.huotu.hotagent.service.entity.role;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 客户
 * Created cwb on 2016/1/22.
 */
@Entity
@Table(name = "customer")
@Getter
@Setter
public class Customer {

    @Id
    @Column(name = "customerId")
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
    @Column(name = "productId")
    private Long productId;

    /**
     * 所属代理商
     */
    @ManyToOne
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

}
