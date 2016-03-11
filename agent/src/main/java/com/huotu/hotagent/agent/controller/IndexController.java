package com.huotu.hotagent.agent.controller;

import com.huotu.hotagent.agent.service.AdminService;
import com.huotu.hotagent.service.common.ProductType;
import com.huotu.hotagent.service.entity.product.Price;
import com.huotu.hotagent.service.entity.product.Product;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.service.product.PriceService;
import com.huotu.hotagent.service.service.product.ProductService;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by chendeyu on 2016/2/1.
 */
@Controller
public class IndexController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PriceService priceService;

    @Autowired
    private ProductService productService;
    @Autowired
    private AgentService agentService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "views/login";
    }

    @RequestMapping("/loginError")
    public String loginError(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("loginError", request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION"));
        request.getSession().removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        return "redirect:/login";
    }


    @RequestMapping(value = {"", "/", "/index","/loginSuccess"})
    public ModelAndView index(@AuthenticationPrincipal Agent agent) {
        ModelAndView modelAndView = new ModelAndView();

        List<Product> productList = productService.findTops();
        Product huobanMall = productService.findByProductTypeAndParent(ProductType.HUOBAN_MALL, null);
        boolean flag = true;
        List<Product> products = new ArrayList<>();

        for(Product product : productList) {
            Set<Price> prices = agent.getPrices();
            for(Price p:prices) {
                if(p.getProduct().getProductType()==product.getProductType()) {
                    if (product.getId() == huobanMall.getId()){
                        if(flag){
                            products.add(product);
                            flag = false;
                        }
                    }
                    else {
                        products.add(product);
                    }
                }
            }
        }


        modelAndView.addObject("balance", agentService.findById(agent.getId()).getBalance());
        modelAndView.addObject("products", products);
        modelAndView.setViewName("views/index");
        return  modelAndView;
    }
}
