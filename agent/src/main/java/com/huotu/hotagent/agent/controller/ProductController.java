package com.huotu.hotagent.agent.controller;

import com.huotu.hotagent.service.entity.product.Price;
import com.huotu.hotagent.service.service.ProductService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by chendeyu on 2016/1/25.
 */
@Controller
public class ProductController {

    private static final Log log = LogFactory.getLog(CustomerController.class);

    @Autowired
    ProductService productService;


    /**
     * 产品列表
     * */
    @RequestMapping("/showProducts")
    public ModelAndView showProducts(@RequestParam(value = "id", defaultValue = "0") Long id) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        List<Price> productList = productService.showProducts(id);
        modelAndView.setViewName("/view/showProducts.html");
        modelAndView.addObject("productList",productList);
        return modelAndView;
    }
}
