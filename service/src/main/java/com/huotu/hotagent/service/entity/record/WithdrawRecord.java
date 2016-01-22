/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.entity.record;

import com.huotu.hotagent.service.common.AuditStatus;
import com.huotu.hotagent.service.entity.role.Agent;

import javax.persistence.*;
import java.util.Date;

/**
 * 代理商体现记录
 * Created by cwb on 2016/1/22.
 */
@Entity
@Table(name = "withdrawRecord")
public class WithdrawRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 体现金额
     */
    @Column(name = "sum")
    private int sum;

    /**
     * 审核状态
     */
    @Column(name = "auditStatus")
    private AuditStatus auditStatus;

    /**
     * 创建日期
     */
    @Column(name = "createTime")
    private Date createTime;

    /**
     * 代理商留言
     */
    @Column(name = "message")
    private String message;

    /**
     * 管理员回复
     */
    @Column(name = "reply")
    private String reply;

    /**
     * 所属代理商
     */
    @ManyToOne
    @JoinColumn(name = "agentId")
    private Agent agent;
}
