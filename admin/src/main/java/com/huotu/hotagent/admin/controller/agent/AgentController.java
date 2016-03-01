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
import com.huotu.hotagent.common.utils.CommonUtils;
import com.huotu.hotagent.service.common.AgentType;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.entity.role.agent.AgentLevel;
import com.huotu.hotagent.service.model.AgentSearch;
import com.huotu.hotagent.service.service.role.agent.AgentLevelService;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import com.huotu.hotagent.service.service.role.agent.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.util.ArrayList;
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
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 代理商列表
     * @return
     */
    @RequestMapping(value = "/agents", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('MANAGER_ROOT','MANAGER_AGENT')")
    public String AgentList(
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            AgentSearch agentSearch,
            Model model
    ) {
        model.addAttribute("agentSearch", agentSearch);
        Page<Agent> agents = agentService.findAll(pageNo, SysConstant.DEFAULT_PAGE_SIZE, agentSearch);
        int totalPages = agents.getTotalPages();
        model.addAttribute("pageSize", agents.getSize());
        model.addAttribute("agents", agents.getContent());
        model.addAttribute("totalRecords", agents.getTotalElements());
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage", pageNo);
        List<AgentLevel> agentLevels = agentLevelService.agentLevelList();
        model.addAttribute("agentLevels", agentLevels);
        model.addAttribute("hasNext",agents.hasNext());
        model.addAttribute("hasPrevious",agents.hasPrevious());
        int pageBtnNum = totalPages > SysConstant.DEFAULT_PAGE_BUTTON_NUM ? SysConstant.DEFAULT_PAGE_BUTTON_NUM : totalPages;
        int startPageNo = CommonUtils.calculateStartPageNo(pageNo, pageBtnNum, totalPages);
        List<Integer> pageNos = new ArrayList<>();
        for(int i=1;i<=pageBtnNum;i++) {
            pageNos.add(startPageNo);
            startPageNo++;
        }
        model.addAttribute("pageNos", pageNos);
        return "agent/agent_list";
    }

    @RequestMapping(value = "/agents/{id}",method = RequestMethod.GET)
    public String showAgentDetail(@PathVariable Long id,Model model) throws Exception {
        Agent agent = agentService.findById(id);
        agent.setQualifyUri(staticResourceService.getResource(agent.getQualifyUri()).toString());
        model.addAttribute("agent",agent);
        return "agent/agent_detail";
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
        List<AgentLevel> agentLevels = agentLevelService.agentLevelList();
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
        return "redirect:/agents";
    }

    /**
     * 检测指定城市是否可设置独家代理
     * @param city
     * @return
     */
    @RequestMapping("/checkCity")
    @ResponseBody
    public ApiResult checkCity(String city) {
        List<Agent> agents = agentService.findByCity(city);
        if(agents.size()==0) {
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
        }
        if(agents.size()==1) {
            return ApiResult.resultWith(ResultCodeEnum.HAS_SOLE_ALREADY);
        }
        return ApiResult.resultWith(ResultCodeEnum.IS_NORMAL_AGENT_AREA);
    }

    @RequestMapping("/checkUsername")
    @ResponseBody
    public String checkUsername(String username) {
        Agent agent = agentService.findByUsername(username);
        if(agent==null) {
            return "true";
        }
        return "已经有同名的账号";
    }

    @RequestMapping("/uploadImg")
    @ResponseBody
    @SuppressWarnings("Duplicates")
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
