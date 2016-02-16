/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.agent.controller;

import com.huotu.hotagent.service.entity.product.Product;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.model.AgentProduct;
import com.huotu.hotagent.service.service.product.PriceService;
import com.huotu.hotagent.service.service.product.ProductService;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by chendeyu on 2016/1/25.
 */
@Controller
public class ProductController {

    private static final Log log = LogFactory.getLog(ProductController.class);

    @Autowired
    ProductService productService;

    @Autowired
    PriceService priceService;

    @Autowired
    AgentService agentService;


    /**
     * 产品列表
     * */
    @RequestMapping("/showProducts")
    public ModelAndView showProducts(@AuthenticationPrincipal Agent agent) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        List<Product> productList = productService.findAll();
        List<AgentProduct> agentProductList = priceService.productList(productList,agent.getId());
        modelAndView.setViewName("views/product/product_List");
        modelAndView.addObject("agentProductList",agentProductList);
        return modelAndView;
    }
}
