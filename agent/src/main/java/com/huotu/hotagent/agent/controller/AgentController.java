/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */
package com.huotu.hotagent.agent.controller;

import com.huotu.hotagent.common.constant.ApiResult;
import com.huotu.hotagent.common.constant.ResultCodeEnum;
import com.huotu.hotagent.common.constant.SysConstant;
import com.huotu.hotagent.service.common.AgentType;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.entity.role.agent.AgentLevel;
import com.huotu.hotagent.service.model.AgentSearch;
import com.huotu.hotagent.service.service.log.BalanceLogService;
import com.huotu.hotagent.service.service.role.agent.AgentLevelService;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import com.huotu.hotagent.service.service.role.agent.LoginService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * Created by chendeyu on 2016/1/25.
 */

@Controller
public class AgentController {

    private static final Log log = LogFactory.getLog(AgentController.class);

    @Autowired
    AgentService agentService;

    @Autowired
    AgentLevelService agentLevelService;

    @Autowired
    BalanceLogService balanceLogService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    LoginService loginService;



    /**
     *账户信息
     */
    @RequestMapping("/showAccount")
    public ModelAndView showAccount(@RequestParam(value = "id", defaultValue = "0") Long id) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        Agent agent = agentService.findById(id);
        modelAndView.setViewName("/views/agent/showAccount");
        modelAndView.addObject("agent",agent);
        return modelAndView;
    }



    /**
     * 下级代理商列表
     *
     * @return
     */
    @RequestMapping(value = "/agentList", method = RequestMethod.GET)
    public String AgentList(@AuthenticationPrincipal Agent agent,
            @RequestParam(required = false, defaultValue = "1") int pageIndex,
            AgentSearch agentSearch,
            Model model
    ) {
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("pageSize", SysConstant.DEFAULT_PAGE_SIZE);
        model.addAttribute("agentSearch", agentSearch);
        agentSearch.setParentAgent(Integer.parseInt(agent.getId().toString()));
        Page<Agent> agents = agentService.findAll(pageIndex, SysConstant.DEFAULT_PAGE_SIZE, agentSearch);
        model.addAttribute("totalRecord", agents.getTotalElements());
        model.addAttribute("agents", agents.getContent());
        List<AgentLevel> agentLevels = agentLevelService.agentLevelList();
        model.addAttribute("agentLevels", agentLevels);
        return "views/agent/agent_list";
    }



    /**
     *修改代理商
     */
    @RequestMapping("/editAgent")
    public ModelAndView editAgent(@RequestParam(value = "id", defaultValue = "0") Long id) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        Agent agent = agentService.findById(id);
        List<AgentLevel> agentLevels = agentLevelService.findAll();
        modelAndView.setViewName("views/agent/agent_edit");
        modelAndView.addObject("agent",agent);
        modelAndView.addObject("agentLevels",agentLevels);
        modelAndView.addObject("agentTypes",AgentType.values());
        return modelAndView;
    }


    /**
     *新增下级代理商
     */
    @RequestMapping("/addAgent")
    public ModelAndView addAgent(@AuthenticationPrincipal Agent agent) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("views/agent/agent_add");
        return modelAndView;
    }



    /**
     *删除下级代理商
     */
    @RequestMapping(value = "/delAgent",method = RequestMethod.POST)
    @ResponseBody
    public ApiResult delAgent(@RequestParam(value = "id") Long id) throws Exception{

        ApiResult apiResult =null;
        try {
            apiResult= agentService.delAgent(id);
        }catch (Exception ex){
            log.error(ex.getMessage());
            apiResult = ApiResult.resultWith(ResultCodeEnum.SYSTEM_BAD_REQUEST);
        }
        return apiResult;
    }


    /**
     *冻结下级代理商
     */
    @RequestMapping(value = "/lockAgent",method = RequestMethod.POST)
    @ResponseBody
    public ApiResult lockAgent(@RequestParam(value = "id") Long id) throws Exception{

        ApiResult apiResult =null;
        try {
            apiResult= agentService.lockAgent(id);
        }catch (Exception ex){
            log.error(ex.getMessage());
            apiResult = ApiResult.resultWith(ResultCodeEnum.SYSTEM_BAD_REQUEST);
        }
        return apiResult;
    }


    /**
     *修改代理商密码
     */
    @RequestMapping(value="/updatePw",method = RequestMethod.GET)
    public ModelAndView updatePw(@AuthenticationPrincipal Agent agent) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("views/agent/agentPw_edit");
        modelAndView.addObject("agent",agent);
        return modelAndView;
    }


    /**
     *保存修改代理商密码
     */
    @RequestMapping(value = "/savePw",method = RequestMethod.POST)
    @ResponseBody
    public ApiResult savePw(@AuthenticationPrincipal Agent agent,
                               @RequestParam(value = "password") String password,@RequestParam(value = "newPassword") String newPassword) throws Exception{

        ApiResult apiResult =null;
        try {
           Boolean bl = passwordEncoder.matches(password,agent.getPassword());
            if(bl){
                agent.setPassword(passwordEncoder.encode(newPassword));
                agentService.save(agent);
                apiResult= ApiResult.resultWith(ResultCodeEnum.SUCCESS);
            }
            else{
                apiResult = ApiResult.resultWith(ResultCodeEnum.SAVE_DATA_ERROR);
            }

        }catch (Exception ex){
            log.error(ex.getMessage());
            apiResult = ApiResult.resultWith(ResultCodeEnum.SAVE_DATA_ERROR);
        }
        return apiResult;
    }


    /**
     *保存下级代理商信息(新增)
     */
    @RequestMapping(value = "/saveAddLowerAg ",method = RequestMethod.POST)
    @ResponseBody
    public ApiResult saveAddLowerAg(@AuthenticationPrincipal Agent Higher,
                                 Agent agent,int agentType,int agentLevel,int money) throws Exception{

        ApiResult apiResult =null;
        try {
            Date date = new Date();
            AgentLevel aLevel = agentLevelService.findByLevel(agentLevel);
            AgentType type = AgentType.getAgentType(agentType);
            agent.setType(type);
            agent.setLevel(aLevel);
            agent.setParent(Higher);
            agent.setExpandable(false);
            agent.setCreateTime(date);
            Boolean bl = balanceLogService.importBl(agent, money);
            if (bl==true){
                loginService.newLogin(agent,agent.getPassword());
                apiResult= ApiResult.resultWith(ResultCodeEnum.SUCCESS);
            }
            else{
                apiResult = ApiResult.resultWith(ResultCodeEnum.IMPORT_ERROR);
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
            apiResult = ApiResult.resultWith(ResultCodeEnum.SAVE_DATA_ERROR);
        }
        return apiResult;
    }


    /**
     *保存下级代理商信息(修改)
     */
    @RequestMapping(value = "/saveEditLowerAg ",method = RequestMethod.POST)
    @ResponseBody
    public ApiResult saveEditLowerAg(@AuthenticationPrincipal Agent Higher,
                                 Agent agent,int agentLevel,int agentType) throws Exception{

        ApiResult apiResult =null;
        try {
            AgentLevel aLevel = agentLevelService.findByLevel(agentLevel);
            AgentType type = AgentType.getAgentType(agentType);
            agent.setCreateTime(agentService.findById(agent.getId()).getCreateTime());
            agent.setType(type);
            agent.setLevel(aLevel);
            agent.setParent(Higher);
            agent.setExpandable(false);
            loginService.newLogin(agent,agent.getPassword());
            apiResult= ApiResult.resultWith(ResultCodeEnum.SUCCESS);

        }catch (Exception ex){
            log.error(ex.getMessage());
            apiResult = ApiResult.resultWith(ResultCodeEnum.SAVE_DATA_ERROR);
        }
        return apiResult;
    }



    /**
     *给下级代理商充值
     */
    @RequestMapping(value = "/importBl",method = RequestMethod.POST)
    @ResponseBody
    public ApiResult importBl(@AuthenticationPrincipal Agent agent,Long id,double money) throws Exception{

        ApiResult apiResult = null;
        Agent lowAgent = agentService.findById(id);
        try {
            Boolean bl=balanceLogService.importBl(lowAgent,money);
            if(bl==true){
                apiResult =  ApiResult.resultWith(ResultCodeEnum.SUCCESS);
            }
            else {
                apiResult = ApiResult.resultWith(ResultCodeEnum.SAVE_DATA_ERROR);
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
            apiResult = ApiResult.resultWith(ResultCodeEnum.SAVE_DATA_ERROR);
        }
        return apiResult;

    }





}
