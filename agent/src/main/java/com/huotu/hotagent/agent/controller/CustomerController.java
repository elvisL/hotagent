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
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.entity.role.agent.Customer;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import com.huotu.hotagent.service.service.role.agent.CustomerService;
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
public class CustomerController {

    private static final Log log = LogFactory.getLog(CustomerController.class);

    @Autowired
    CustomerService customerService;

    @Autowired
    AgentService agentService;

    /**
    * �����ͻ�
   * */
    @RequestMapping("/addCustomer")
    public ModelAndView addCustomer(@RequestParam(value = "id", defaultValue = "0") Long id) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/views/addCustomer.html");
        return modelAndView;
    }



    /**
     *����ͻ�
     */
    @RequestMapping(value = "/saveCustomer",method = RequestMethod.POST)
    @ResponseBody
    public ApiResult saveCustomer(@RequestParam(value = "id", defaultValue = "0") Long id,
                                    Customer customer,int price) throws Exception{

        ApiResult apiResult =null;
        try {
            Agent agent = agentService.findById(id);
            customer.setAgent(agent);
            customerService.save(customer);
            apiResult= ApiResult.resultWith(ResultCodeEnum.SUCCESS);
        }catch (Exception ex){
            log.error(ex.getMessage());
            apiResult = ApiResult.resultWith(ResultCodeEnum.SAVE_DATA_ERROR);
        }
        return apiResult;
    }


}