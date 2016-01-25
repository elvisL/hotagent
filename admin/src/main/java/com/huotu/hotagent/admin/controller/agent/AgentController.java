/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.admin.controller.agent;

import com.huotu.hotagent.service.entity.role.Agent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 代理商相关
 * Created by allan on 1/25/16.
 */
@Controller
@RequestMapping("/agent")
public class AgentController {
    @RequestMapping(value = "/agentList", method = RequestMethod.GET)
    public String AgentList() {
        return null;
    }

    @RequestMapping(value = "/agentEdit", method = RequestMethod.GET)
    public String AgentEdit() {
        return "admin/agent/agent_edit";
    }

    @RequestMapping(value = "/agentEdit", method = RequestMethod.POST)
    public String AgentEdit(Agent agent) {
        
        return "redirect:/agent/agentEdit";
    }
}
