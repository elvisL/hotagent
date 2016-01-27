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

import javax.persistence.*;

/**
 * 代理商等级
 * Created by allan on 1/25/16.
 */
@Entity
@Table(name = "age_agentLevel")
@Setter
@Getter
public class AgentLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long levelId;
    private String levelName;
    private String levelDesc;
    private int level;//代理商级别 从0开始0代表一级代理，1代表二级代理，依次类推
}
