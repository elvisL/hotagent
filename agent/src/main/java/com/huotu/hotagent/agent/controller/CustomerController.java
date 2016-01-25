package com.huotu.hotagent.agent.controller;

import com.huotu.hotagent.common.constant.ApiResult;
import com.huotu.hotagent.common.constant.ResultCodeEnum;
import com.huotu.hotagent.service.entity.role.Customer;
import com.huotu.hotagent.service.service.CustomerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

    /**
    * 新增客户
   * */
    @RequestMapping("/addCustomer")
    public ModelAndView addCustomer(@RequestParam(value = "id", defaultValue = "0") Long id) throws Exception{
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/view/addCustomer.html");
        return modelAndView;
    }



    /**
     *保存客户
     */
    @RequestMapping("/saveCustomer")
    @ResponseBody
    public ApiResult saveCustomer(@RequestParam(value = "id", defaultValue = "0") Long id,
                                    Customer customer) throws Exception{

        ApiResult apiResult =null;
        try {
            customerService.save(customer);
            apiResult= ApiResult.resultWith(ResultCodeEnum.SUCCESS);
        }catch (Exception ex){
            log.error(ex.getMessage());
            apiResult = ApiResult.resultWith(ResultCodeEnum.SAVE_DATA_ERROR);
        }
        return apiResult;
    }


}
