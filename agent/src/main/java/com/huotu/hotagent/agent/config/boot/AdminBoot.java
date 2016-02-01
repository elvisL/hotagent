/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.agent.config.boot;

import com.huotu.hotagent.service.common.ProductType;
import com.huotu.hotagent.service.entity.product.Product;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.entity.role.agent.AgentLevel;
import com.huotu.hotagent.service.service.product.ProductService;
import com.huotu.hotagent.service.service.role.agent.AgentLevelService;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import com.huotu.hotagent.service.service.role.agent.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * 工程启动时的一些必要操作
 * Created by allan on 1/26/16.
 */
@Component
public class AdminBoot {
    @Autowired
    private AgentLevelService agentLevelService;
    @Autowired
    private ProductService productService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private LoginService loginService;

    @PostConstruct
    public void adminBoot() {
        //初始化一个超级管理员
        Agent agent = agentService.findByUsername("admin");
        if (agent == null) {
            agent = new Agent();
        }
        agent.setUsername("admin");
        agent.setPassword("admin");
        agent.setName("测试供应商");
        agent.setCreateTime(new Date());
        loginService.newLogin(agent,agent.getPassword());


        //初始化两个等级,在没有的情况下创建
        //初始化一级代理商
        AgentLevel agentLevel1 = agentLevelService.findByLevel(0);
        if (agentLevel1 == null) {
            agentLevel1 = new AgentLevel();
        }
        agentLevel1.setLevelName("一级代理商");
        agentLevel1.setLevelDesc("一级代理商");
        agentLevel1.setLevel(0);
        agentLevelService.save(agentLevel1);
        //初始化二级代理商
        AgentLevel agentLevel2 = agentLevelService.findByLevel(1);
        if (agentLevel2 == null) {
            agentLevel2 = new AgentLevel();
        }
        agentLevel2.setLevelName("二级代理商");
        agentLevel2.setLevelDesc("二级代理商");
        agentLevel2.setLevel(1);
        agentLevelService.save(agentLevel2);


        //初始化商品,在没有的情况下创建
        if (!productService.exists()) {
            //1.伙伴商城
            Product huobanMall = new Product();
            huobanMall.setName("伙伴商城");
            huobanMall.setProductType(ProductType.HUOBAN_MALL);
            huobanMall.setProductDesc("伙伴商城");
            huobanMall.setBasePrice(5000);
            productService.save(huobanMall);
            //DSP广告
            Product dsp = new Product();
            dsp.setName("DSP广告");
            dsp.setProductType(ProductType.DSP);
            dsp.setProductDesc("DSP广告");
            dsp.setBasePrice(1000);
            productService.save(dsp);
            //云商学院
            Product hotEdu = new Product();
            hotEdu.setName("火聚教育-商学院");
            hotEdu.setProductType(ProductType.HOT_EDU);
            hotEdu.setProductDesc("火聚教育-商学院");
            hotEdu.setBasePrice(1000);
            productService.save(hotEdu);
            //代运营
            Product thirdpartnar = new Product();
            thirdpartnar.setName("代运营");
            thirdpartnar.setProductType(ProductType.THIRDPARTNAR);
            thirdpartnar.setProductDesc("代运营");
            thirdpartnar.setBasePrice(1000);
            productService.save(thirdpartnar);
        }
    }
}
