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
import com.huotu.hotagent.common.utils.CommonUtils;
import com.huotu.hotagent.service.entity.product.Product;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.entity.role.agent.Customer;
import com.huotu.hotagent.service.model.CustomerSearch;
import com.huotu.hotagent.service.repository.product.PriceRepository;
import com.huotu.hotagent.service.service.product.ProductService;
import com.huotu.hotagent.service.service.role.agent.AgentService;
import com.huotu.hotagent.service.service.role.agent.CustomerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    * 添加客户(伙伴商城除外)
   * */
    @RequestMapping("/addCustomer")
    public ModelAndView addCustomer(@AuthenticationPrincipal Agent agent,Long id) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("views/customer/customer_add");
        Product product = productService.findOne(id);
        modelAndView.addObject("product",product);
//        Double balance = agentService.findById(agent.getId()).getBalance();//账户总额
//        int flag = agent.getLevel().getLevel();//0为一级，1为2级
//        List<Product> productList =  productService.findAll();
//        Double price = priceRepository.findByAgent_IdAndProduct_Id(agent.getId(), id).getPrice();
//        modelAndView.addObject("productList",productList);
//        modelAndView.addObject("price",price);
//        modelAndView.addObject("balance",balance);当有二级代理商时游泳
//        modelAndView.addObject("flag",flag);
        return modelAndView;
    }

    /**
     * 修改客户
     * */
    @RequestMapping("/editCustomer")
    public ModelAndView editCustomer(@AuthenticationPrincipal Agent agent,Long id) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("views/customer/customer_edit");
        Customer customer = customerService.findById(id);
        modelAndView.addObject("customer",customer);
        return modelAndView;
    }

    /**
     * 购买伙伴商城
     * */
    @RequestMapping("/huobanBuy")
    public ModelAndView huobanBuy(@AuthenticationPrincipal Agent agent,Long id) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("views/customer/huoban_buy");
        List<Product> productList  = productService.findByParentId(id);
        Product product = productService.findOne(id);
        modelAndView.addObject("product",product);
        modelAndView.addObject("productList",productList);
//        Customer customer = customerService.findById(id);
//        modelAndView.addObject("customer",customer);
        return modelAndView;
    }

    /**
     * 购买DSP
     * */
    @RequestMapping("/dspBuy")
    public ModelAndView dspBuy(@AuthenticationPrincipal Agent agent,Long id) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("views/customer/dsp_buy");
        Product product = productService.findOne(id);
        modelAndView.addObject("product",product);
//        Customer customer = customerService.findById(id);
//        modelAndView.addObject("customer",customer);
        return modelAndView;
    }

    /**
     * 购买云商学院
     * */
    @RequestMapping("/hoteduBuy")
    public ModelAndView hoteduBuy(@AuthenticationPrincipal Agent agent,Long id) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("views/customer/hotedu_buy");
        Product product = productService.findOne(id);
        modelAndView.addObject("product",product);
//        Customer customer = customerService.findById(id);
//        modelAndView.addObject("customer",customer);
        return modelAndView;
    }

    /**
     * 购买水土代运营
     * */
    @RequestMapping("/thirdpartnarBuy")
    public ModelAndView thirdpartnarBuy(@AuthenticationPrincipal Agent agent,Long id) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("views/customer/thirdpartnar_buy");
        Product product = productService.findOne(id);
        modelAndView.addObject("product",product);
//        Customer customer = customerService.findById(id);
//        modelAndView.addObject("customer",customer);
        return modelAndView;
    }





    /**
     * 客户详情
     * */
    @RequestMapping("/customerDetail/{id}")
    public ModelAndView customerDetail(@PathVariable Long id) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("views/customer/customer_detail");
        Customer customer = customerService.findById(id);
        Product product = productService.findOne(customer.getProduct().getId());
        modelAndView.addObject("customer",customer);
        modelAndView.addObject("product",product);
        return modelAndView;
    }

    /**
     * 客户列表
     *
     * @return
     */
    @RequestMapping(value = "/customerList", method = RequestMethod.GET)
    public String AgentList(@AuthenticationPrincipal Agent agent,
                            @RequestParam(required = true) Long id,
                            @RequestParam(required = false, defaultValue = "1") int pageNo,
                            CustomerSearch customerSearch,
                            Model model
    ) {

        model.addAttribute("customerSearch", customerSearch);
        List<Product>  productList = productService.findByParentId(id);
        customerSearch.setProductId(id);
        if (productList.size()==0){//如果true则为普通产品，false为伙伴商城
            customerSearch.setABoolean(false);
        }
        else{
            customerSearch.setABoolean(true);
        }
        customerSearch.setAgentId(agent.getId());
        Page<Customer> customers = customerService.findAll(pageNo, SysConstant.DEFAULT_PAGE_SIZE, customerSearch);
        int totalPages = customers.getTotalPages();
        model.addAttribute("pageSize", customers.getSize());
        model.addAttribute("customers", customers.getContent());
        model.addAttribute("totalRecords", customers.getTotalElements());
        model.addAttribute("totalPages",customers.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("hasNext",customers.hasNext());
        model.addAttribute("hasPrevious",customers.hasPrevious());
        model.addAttribute("cid",id);
        int pageBtnNum = totalPages > SysConstant.DEFAULT_PAGE_BUTTON_NUM ? SysConstant.DEFAULT_PAGE_BUTTON_NUM : totalPages;
        int startPageNo = CommonUtils.calculateStartPageNo(pageNo, pageBtnNum, totalPages);
        List<Integer> pageNos = new ArrayList<>();
        for(int i=1;i<=pageBtnNum;i++) {
            pageNos.add(startPageNo);
            startPageNo++;
        }
        model.addAttribute("pageNos", pageNos);
        return "views/customer/customer_list";
    }


    /**
     *保存商户（修改）
     */
    @RequestMapping(value = "/updateCustomer",method = RequestMethod.POST)
    @ResponseBody
    public ApiResult updateCustomer(@AuthenticationPrincipal Agent agent,
                                  Customer newCustomer) throws Exception{

        ApiResult apiResult =null;
        try {
            Customer customer = customerService.findById(newCustomer.getCustomerId());
            customer.setCompany(newCustomer.getCompany());
            customer.setContacts(newCustomer.getContacts());
            customer.setLoginName(newCustomer.getLoginName());
            customer.setName(newCustomer.getName());
            customer.setPhoneNo(newCustomer.getPhoneNo());
            customerService.save(customer);
            apiResult=  ApiResult.resultWith(ResultCodeEnum.SUCCESS);

        }catch (Exception ex){
            log.error(ex.getMessage());
            apiResult = ApiResult.resultWith(ResultCodeEnum.SAVE_DATA_ERROR);
        }
        return apiResult;
    }



    /**
     *保存商户(新增)
     */
    @RequestMapping(value = "/saveCustomer",method = RequestMethod.POST)
    @ResponseBody
    public ApiResult saveCustomer(@AuthenticationPrincipal Agent agent,
                                    Customer customer,int count) throws Exception{

        ApiResult apiResult =null;
        try {
            customer.setAgent(agent);
            customer.setCreateTime(new Date());
            customer.setSaleNum(count);
            apiResult=  customerService.addCustomer(agent.getId(),customer,count);

        }catch (Exception ex){
            log.error(ex.getMessage());
            apiResult = ApiResult.resultWith(ResultCodeEnum.SAVE_DATA_ERROR);
        }
        return apiResult;
    }

    /**
     *保存商户(伙伴商城购买)
     */
    @RequestMapping(value = "/saveHuuoban",method = RequestMethod.POST)
    @ResponseBody
    public ApiResult saveHuuoban(@AuthenticationPrincipal Agent agent,
                                  Customer customer) throws Exception{

        ApiResult apiResult =null;
        try {
            customer.setAgent(agent);
            customer.setCreateTime(new Date());
            customer.setSaleNum(1);
            apiResult=  customerService.addCustomer(agent.getId(),customer,1);

        }catch (Exception ex){
            log.error(ex.getMessage());
            apiResult = ApiResult.resultWith(ResultCodeEnum.SAVE_DATA_ERROR);
        }
        return apiResult;
    }



}
