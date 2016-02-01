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
import com.huotu.hotagent.service.common.AgentType;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.entity.role.agent.AgentLevel;
import com.huotu.hotagent.service.service.log.BalanceLogService;
import com.huotu.hotagent.service.service.role.agent.AgentLevelService;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

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



    /**
     *账户信息
     */
    @RequestMapping("/showAccount")
    public ModelAndView showAccount(@RequestParam(value = "id", defaultValue = "0") Long id) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        Agent agent = agentService.findById(id);
        modelAndView.setViewName("/views/showAccount.html");
        modelAndView.addObject("agent",agent);
        return modelAndView;
    }



    /**
     *我的代理商列表
     */
    @RequestMapping("/myAgents")
    public ModelAndView showAgentList(@RequestParam(value = "id", defaultValue = "0") Long id) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        Agent agent = agentService.findById(id);
        modelAndView.setViewName("/views/myAgents.html");
        modelAndView.addObject("agent",agent);
        return modelAndView;
    }



    /**
     *个人代理商信息/修改代理商
     */
    @RequestMapping("/showAgent")
    public ModelAndView showAgent(@RequestParam(value = "id", defaultValue = "0") Long id) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        Agent agent = agentService.findById(id);
        modelAndView.setViewName("/views/showAgent.html");
        modelAndView.addObject("agent",agent);
        return modelAndView;
    }


    /**
     *新增下级代理商
     */
    @RequestMapping("/addAgent")
    public ModelAndView addAgent(@RequestParam(value = "id", defaultValue = "0") Long id) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/views/addAgent.html");
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
            apiResult= agentService.delAgent(id);
        }catch (Exception ex){
            log.error(ex.getMessage());
            apiResult = ApiResult.resultWith(ResultCodeEnum.SYSTEM_BAD_REQUEST);
        }
        return apiResult;
    }



    /**
     *修改代理商密码
     */
    @RequestMapping(value = "/updatePw",method = RequestMethod.POST)
    @ResponseBody
    public ApiResult updatePw(@RequestParam(value = "id") Long id,
                               @RequestParam(value = "password") String password,@RequestParam(value = "newPassword") String newPassword) throws Exception{

        ApiResult apiResult =null;
        try {
            Agent agent = agentService.findById(id);
            if(password.equals(agent.getPassword())){
                agent.setPassword(newPassword);
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
     *保存下级代理商信息
     */
    @RequestMapping(value = "/saveLowerAg ",method = RequestMethod.POST)
    @ResponseBody
    public ApiResult saveLowerAg(@RequestParam(value = "id") Long id,
                                 Agent agent,int agentType,int agentLevel,int price) throws Exception{

        ApiResult apiResult =null;
        try {
            Date date = new Date();
            Agent Higher = agentService.findById(id);
            AgentLevel aLevel = agentLevelService.findByLevel(agentLevel);
            AgentType agentType1 = AgentType.valueOf(agentType);
            agent.setType(agentType1);
            agent.setLevel(aLevel);
            agent.setParent(Higher);
            agent.setExpandable(false);
            agent.setCreateTime(date);
            agentService.save(agent);
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
    public ApiResult importBl(@RequestParam(value = "id") Long id,double money) throws Exception{

        ApiResult apiResult = null;
        try {
            Boolean bl=balanceLogService.importBl(id,money);
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
