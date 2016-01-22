package com.huotu.hotagent.service.entity.record;

import com.huotu.hotagent.service.common.AuditStatus;
import com.huotu.hotagent.service.entity.role.Agent;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 代理商体现记录
 * Created by cwb on 2016/1/22.
 */
@Entity
@Table(name = "withdrawRecord")
@Getter
@Setter
public class WithdrawRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 金额
     */
    @Column(name = "money")
    private double money;

    /**
     * 审核状态
     */
    @Column(name = "auditStatus")
    private AuditStatus auditStatus;

    /**
     * 创建时间
     */
    @Column(name = "createTime")
    @Temporal(TemporalType.TIMESTAMP)
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
