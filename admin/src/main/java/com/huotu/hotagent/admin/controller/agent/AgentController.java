/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.admin.controller.agent;

import com.huotu.hotagent.service.common.AgentType;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.service.role.agent.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

/**
 * 代理商相关
 * Created by allan on 1/25/16.
 */
@Controller
public class AgentController {

    @Autowired
    private LoginService loginService;

    /**
     * 代理商列表
     * @return
     */
    @RequestMapping(value = "/agents", method = RequestMethod.GET)
    public String AgentList() {
        return "agent/agent-list";
    }

    /**
     * 代理商新增（修改）页面
     * @return
     */
    @RequestMapping(value = "/agentEditForm", method = RequestMethod.GET)
    public String AgentEdit(Model model) {
        model.addAttribute("agentTypes", AgentType.values());
        return "agent/agent_edit";
    }

    /**
     * 新增（修改）代理商
     * @param agent
     * @return
     */
    @RequestMapping(value = "/agents", method = RequestMethod.POST)
    public String AgentEdit(Agent agent,MultipartFile qualify) {
        if(agent.getId() == null) {

        }else {

        }
        return "redirect:/agent/agentEdit";
    }
}
