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
import com.huotu.hotagent.service.entity.product.Product;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.entity.role.agent.Customer;
import com.huotu.hotagent.service.repository.product.PriceRepository;
import com.huotu.hotagent.service.service.product.ProductService;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import com.huotu.hotagent.service.service.role.agent.CustomerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by chendeyu on 2016/1/25.
 */
@Controller
public class CustomerController {

    private static final Log log = LogFactory.getLog(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PriceRepository priceRepository;

    /**
    * 添加客户
   * */
    @RequestMapping("/addCustomer")
    public ModelAndView addCustomer(@AuthenticationPrincipal Agent agent,Long id) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("views/customer/customer_add");
        Double balance = agentService.findById(agent.getId()).getBalance();//账户总额
        int flag = agent.getLevel().getLevel();//0为一级，1为2级
        Product product = productService.findOne(id);
        Double price = priceRepository.findByAgent_IdAndProduct_Id(agent.getId(), id).getPrice();
        modelAndView.addObject("product",product);
        modelAndView.addObject("price",price);
        modelAndView.addObject("balance",balance);
        modelAndView.addObject("flag",flag);
        return modelAndView;
    }



    /**
     *保存商户
     */
    @RequestMapping(value = "/saveCustomer",method = RequestMethod.POST)
    @ResponseBody
    public ApiResult saveCustomer(@AuthenticationPrincipal Agent agent,
                                    Customer customer,int count) throws Exception{

        ApiResult apiResult =null;
        try {
            customer.setAgent(agent);
            customer.setSaleNum(count);
            apiResult=  customerService.addCustomer(agent.getId(),customer,count);

        }catch (Exception ex){
            log.error(ex.getMessage());
            apiResult = ApiResult.resultWith(ResultCodeEnum.SAVE_DATA_ERROR);
        }
        return apiResult;
    }


}
