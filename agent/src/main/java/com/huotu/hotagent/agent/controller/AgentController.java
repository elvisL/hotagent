package com.huotu.hotagent.agent.controller;

import com.huotu.hotagent.common.constant.ApiResult;
import com.huotu.hotagent.common.constant.ResultCodeEnum;
import com.huotu.hotagent.service.common.AgentType;
import com.huotu.hotagent.service.entity.role.Agent;
import com.huotu.hotagent.service.entity.role.AgentLevel;
import com.huotu.hotagent.service.service.AgentLevelService;
import com.huotu.hotagent.service.service.AgentService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
     *保存代理商信息
     */
    @RequestMapping(value = "/saveAgent",method = RequestMethod.POST)
    @ResponseBody
    public ApiResult saveAgent(@RequestParam(value = "id") Long id,
                                       @RequestParam(value = "newPassword") String newPassword) throws Exception{

        ApiResult apiResult =null;
        try {
        Agent agent = agentService.findById(id);
        agent.setPassword(newPassword);
        agentService.save(agent);
            apiResult= ApiResult.resultWith(ResultCodeEnum.SUCCESS);
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
            Agent Higher = agentService.findById(id);
            AgentLevel aLevel = agentLevelService.findByLevel(agentLevel);
            AgentType agentType1 = AgentType.valueOf(agentType);
            agent.setType(agentType1);
            agent.setLevel(aLevel);
            agent.setParent(Higher);
            agentService.save(agent);
            apiResult= ApiResult.resultWith(ResultCodeEnum.SUCCESS);
        }catch (Exception ex){
            log.error(ex.getMessage());
            apiResult = ApiResult.resultWith(ResultCodeEnum.SAVE_DATA_ERROR);
        }
        return apiResult;
    }





}
