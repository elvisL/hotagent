/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.admin.controller.agent;

import com.huotu.hotagent.admin.service.StaticResourceService;
import com.huotu.hotagent.common.constant.ApiResult;
import com.huotu.hotagent.common.constant.ResultCodeEnum;
import com.huotu.hotagent.common.constant.SysConstant;
import com.huotu.hotagent.common.model.DataTableRequest;
import com.huotu.hotagent.common.model.DataTableResponse;
import com.huotu.hotagent.service.common.AgentType;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.entity.role.agent.AgentLevel;
import com.huotu.hotagent.service.model.AgentSearch;
import com.huotu.hotagent.service.service.role.agent.AgentLevelService;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import com.huotu.hotagent.service.service.role.agent.LoginService;
import com.sun.jndi.toolkit.url.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * 代理商相关
 * Created by allan on 1/25/16.
 */
@Controller
public class AgentController {

    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentLevelService agentLevelService;
    @Autowired
    private StaticResourceService staticResourceService;
    @Autowired
    private LoginService loginService;

    /**
     * 代理商列表
     * @return
     */
    @RequestMapping(value = "/agents", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('MANAGER_ROOT','MANAGER_AGENT')")
    public String AgentList(
            @RequestParam(required = false, defaultValue = "1") int pageIndex,
            AgentSearch agentSearch,
            Model model
    ) {
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("pageSize", SysConstant.DEFAULT_PAGE_SIZE);
        model.addAttribute("agentSearch", agentSearch);
        Page<Agent> agents = agentService.findAll(pageIndex, SysConstant.DEFAULT_PAGE_SIZE, agentSearch);
        model.addAttribute("totalRecord", agents.getTotalElements());
        model.addAttribute("agents", agents.getContent());
        List<AgentLevel> agentLevels = agentLevelService.agentLevelList();
        model.addAttribute("agentLevels", agentLevels);
        return "agent/agent_list";
    }

    /**
     * 代理商新增（修改）页面
     *
     * @return
     */
    @RequestMapping(value = "/agentEditForm", method = RequestMethod.GET)
    public String AgentEdit(Model model,Long id) {
        if(id != null) {
            Agent agent = agentService.findById(id);
            model.addAttribute("agent",agent);
        }
        List<AgentLevel> agentLevels = agentLevelService.findAll();
        model.addAttribute("agentLevels",agentLevels);
        model.addAttribute("agentTypes",AgentType.values());
        return "agent/agent_edit";
    }

    /**
     * 新增（修改）代理商
     *
     * @param agent
     * @return
     */
    @RequestMapping(value = "/agents", method = RequestMethod.POST)
    public String AgentEdit(Agent agent) throws Exception{
        loginService.newLogin(agent,agent.getPassword());
        return "redirect:/agentEditForm";
    }

    @RequestMapping("/uploadImg")
    @ResponseBody
    public ApiResult uploadImg(MultipartFile qualify,String qualifyUri) throws Exception {
        //delete img
        if(qualify.getSize()==0) {
            staticResourceService.deleteResource(qualifyUri);
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS,"");
        }
        if (ImageIO.read(qualify.getInputStream()) == null) {
            return ApiResult.resultWith(ResultCodeEnum.NOT_IMG);
        }
        //change img
        if(!StringUtils.isEmpty(qualifyUri)) {
            staticResourceService.deleteResource(qualifyUri);
        }
        String fileName = qualify.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String path = StaticResourceService.AGENT_IMG + UUID.randomUUID().toString() + "." + suffix;
        staticResourceService.uploadResource(path, qualify.getInputStream());
        String fullPath = staticResourceService.getResource(path).toString();
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS,Arrays.asList(path,fullPath).toArray());
    }

}
