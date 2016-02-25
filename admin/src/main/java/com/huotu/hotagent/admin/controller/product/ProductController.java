package com.huotu.hotagent.admin.controller.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huotu.hotagent.common.constant.ApiResult;
import com.huotu.hotagent.common.constant.ResultCodeEnum;
import com.huotu.hotagent.common.ienum.EnumHelper;
import com.huotu.hotagent.common.ienum.ICommonEnum;
import com.huotu.hotagent.common.utils.StringUtil;
import com.huotu.hotagent.service.common.ProductType;
import com.huotu.hotagent.service.entity.product.Product;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.model.AgentProduct;
import com.huotu.hotagent.service.model.LogSearch;
import com.huotu.hotagent.service.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by admin on 2016/2/24.
 */
@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 产品列表
     */
    @RequestMapping(value = "/showProducts")
    @PreAuthorize("hasAnyAuthority('MANAGER_ROOT','MANAGER_PRODUCT')")
    public ModelAndView showProducts() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        List<Product> productList = productService.findAll();
        ProductType[] productTypeList = ProductType.values();
        modelAndView.setViewName("product/product_list");
        modelAndView.addObject("products", productList);
        modelAndView.addObject("productTypes", productTypeList);
        return modelAndView;
    }

    /**
     * 查看产品信息
     */
    @RequestMapping(value = "/showProduct/{id}", produces = "application/json")
    @PreAuthorize("hasAnyAuthority('MANAGER_ROOT','MANAGER_PRODUCT')")
    @ResponseBody
    public ApiResult showProduct(@PathVariable Long id) {
        ApiResult apiResult = ApiResult.resultWith(ResultCodeEnum.DATA_NULL);
        Product product = productService.findOne(id);
        if (product != null) {
            apiResult = ApiResult.resultWith(ResultCodeEnum.SUCCESS, product);
        }
        return apiResult;
    }

    /**
     * 修改产品信息
     */
    @RequestMapping("/editPrudcut")
    @PreAuthorize("hasAnyAuthority('MANAGER_ROOT','MANAGER_PRODUCT')")
    @ResponseBody
    public ApiResult updateProduct(Product product, HttpServletRequest request) {
        //若产品名称为空，则提示没有传输数据5000
        if (StringUtils.isEmpty(product.getName()) || product.getId() == null) {
            return ApiResult.resultWith(ResultCodeEnum.DATA_NULL);
        }
        product = productService.update(product);
        if(product != null){
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
        }else{
            return ApiResult.resultWith(ResultCodeEnum.SAVE_DATA_ERROR);
        }
    }
}