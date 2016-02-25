/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */
package com.huotu.hotagent.agent.controller;

import com.huotu.hotagent.agent.service.StaticResourceService;
import com.huotu.hotagent.common.constant.ApiResult;
import com.huotu.hotagent.common.constant.ResultCodeEnum;
import com.huotu.hotagent.common.constant.SysConstant;
import com.huotu.hotagent.service.common.AgentType;
import com.huotu.hotagent.service.common.ProductType;
import com.huotu.hotagent.service.entity.product.Price;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.entity.role.agent.AgentLevel;
import com.huotu.hotagent.service.model.AgentSearch;
import com.huotu.hotagent.service.model.ProductPrice;
import com.huotu.hotagent.service.repository.product.PriceRepository;
import com.huotu.hotagent.service.repository.product.ProductRepository;
import com.huotu.hotagent.service.service.log.BalanceLogService;
import com.huotu.hotagent.service.service.product.PriceService;
import com.huotu.hotagent.service.service.record.WithdrawRecordService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by chendeyu on 2016/1/25.
 */

@Controller
public class AgentController {

    private static final Log log = LogFactory.getLog(AgentController.class);

    @Autowired
    private AgentService agentService;

    @Autowired
    private AgentLevelService agentLevelService;

    @Autowired
    private BalanceLogService balanceLogService;

    @Autowired
    private PriceService priceService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WithdrawRecordService withdrawRecordService;

    @Autowired
    private StaticResourceService staticResourceService;

    @Autowired
    LoginService loginService;



    /**
     *个人账户信息
     */
    @RequestMapping("/agentDetail")
    public ModelAndView agentDetail(@AuthenticationPrincipal Agent agent) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/views/agent/agent_detail");
        modelAndView.addObject("agent",agent);
        return modelAndView;
    }

    /**
     *下级代理账户信息详情
     */
    @RequestMapping(value = "/lowAgentDetail/{id}",method = RequestMethod.GET)
    public String lowAgentDetai(@PathVariable Long id,Model model) throws Exception {
        Agent agent = agentService.findById(id);
        agent.setQualifyUri(staticResourceService.getResource(agent.getQualifyUri()).toString());
        model.addAttribute("agent", agent);
        return "/views/agent/agent_detail";
    }

    /**
     *代理商充值
     */
    @RequestMapping(value = "/agentImport/{id}",method = RequestMethod.GET)
    public ModelAndView agentImport(@AuthenticationPrincipal Agent agent,@PathVariable Long id) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/views/agent/agent_import");
        modelAndView.addObject("id",id);
        Double balance = agentService.findById(agent.getId()).getBalance();
        Double commission = agentService.findById(agent.getId()).getCommission();
        modelAndView.addObject("balance",balance);
        modelAndView.addObject("commission",commission);
        return  modelAndView;

    }


    /**
     *代理商提现
     */
    @RequestMapping(value = "/withdraw",method = RequestMethod.GET)
    public ModelAndView withdraw(@AuthenticationPrincipal Agent agent) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/views/withdraw/withdraw");
        modelAndView.addObject("commission",agent.getCommission());
        return  modelAndView;
    }





    /**
     * 下级代理商列表
     *
     * @return
     */
    @RequestMapping(value = "/agentList", method = RequestMethod.GET)
    public String AgentList(@AuthenticationPrincipal Agent agent,
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            AgentSearch agentSearch,
            Model model
    ) {

        model.addAttribute("agentSearch", agentSearch);
        agentSearch.setAgentLevel(agent.getLevel().getLevel()+1);
        agentSearch.setParentAgent(Integer.parseInt(agent.getId().toString()));
        Page<Agent> agents = agentService.findAll(pageNo, SysConstant.DEFAULT_PAGE_SIZE, agentSearch);
        model.addAttribute("pageSize", agents.getSize());
        model.addAttribute("agents", agents.getContent());
        model.addAttribute("totalRecords", agents.getTotalElements());
        model.addAttribute("totalPages",agents.getTotalPages());
        model.addAttribute("currentPage", pageNo);
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
        List<AgentLevel> agentLevels = agentLevelService.agentLevelList();
        Double huobanMall=priceRepository.findByAgent_IdAndProduct_Id(id,productRepository.findByProductType(ProductType.HUOBAN_MALL).getId()).getPrice();
        Double dsp=priceRepository.findByAgent_IdAndProduct_Id(id,productRepository.findByProductType(ProductType.DSP).getId()).getPrice();
        Double hotEdu=priceRepository.findByAgent_IdAndProduct_Id(id,productRepository.findByProductType(ProductType.HOT_EDU).getId()).getPrice();
        Double thirdPartnar=priceRepository.findByAgent_IdAndProduct_Id(id, productRepository.findByProductType(ProductType.THIRDPARTNAR).getId()).getPrice();
        modelAndView.setViewName("views/agent/agent_edit");
        modelAndView.addObject("agent",agent);
        modelAndView.addObject("agentLevels",agentLevels);
        modelAndView.addObject("agentTypes",AgentType.values());
        modelAndView.addObject("hotEdu",hotEdu);
        modelAndView.addObject("huobanMall",huobanMall);
        modelAndView.addObject("dsp",dsp);
        modelAndView.addObject("thirdPartnar",thirdPartnar);
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
     *冻结下级代理商/解冻
     */
    @RequestMapping(value = "/lockAgent",method = RequestMethod.POST)
    @ResponseBody
        public ApiResult lockAgent(@RequestParam(value = "id") Long id,int bl) throws Exception{

        ApiResult apiResult =null;
        try {
            apiResult= agentService.lockAgent(id,bl);
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
                                 Agent agent,int agentType,int agentLevel,int money,
                                  ProductPrice productPrice) throws Exception{

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
                Set<Price> priceSet = priceService.setProduct(agent,productPrice);
                agent.setPrices(priceSet);
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
                                 Agent agent,int agentLevel,int agentType,
                                     ProductPrice productPrice) throws Exception{

        ApiResult apiResult =null;
        try {
            AgentLevel aLevel = agentLevelService.findByLevel(agentLevel);
            AgentType type = AgentType.getAgentType(agentType);
            agent.setCreateTime(agentService.findById(agent.getId()).getCreateTime());
            agent.setType(type);
            agent.setLevel(aLevel);
            agent.setParent(Higher);
            agent.setExpandable(false);
            Set<Price> priceSet = priceService.updateProduct(agent,productPrice);
            agent.setPrices(priceSet);
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



    /**
     * 检测指定城市是否已经有独家代理
     * @param city
     * @return
     */
    @RequestMapping("/checkCity")
    @ResponseBody
    public ApiResult checkCity(String city) {
        Agent agent = agentService.findByCityAndType(city,AgentType.SOLE);
        if(agent==null) {
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
        }
        return ApiResult.resultWith(ResultCodeEnum.CITY_NOT_AVALIABLE);
    }



}
