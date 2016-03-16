package com.huotu.hotagent.admin.controller.product;

import com.huotu.hotagent.admin.model.ProductTreeModel;
import com.huotu.hotagent.common.constant.ApiResult;
import com.huotu.hotagent.common.constant.ResultCodeEnum;
import com.huotu.hotagent.service.entity.product.Price;
import com.huotu.hotagent.service.entity.product.Product;
import com.huotu.hotagent.service.service.product.PriceService;
import com.huotu.hotagent.service.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/2/24.
 */
@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private PriceService priceService;

    /**
     * 产品列表
     */
    @RequestMapping(value = "/showProducts")
    @PreAuthorize("hasAnyAuthority('MANAGER_ROOT','MANAGER_PRODUCT')")
    public String showProducts(Model model) throws Exception {
        List<Product> products = productService.findSubs();
        model.addAttribute("products",products);
        return "product/product_list";
    }

    @RequestMapping("/products")
    @ResponseBody
    public List<Product> getProductTrees() {
        List<Product> products = productService.findTops();
        return products;
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
     * 修改产品价格
     * @param id
     * @param basePrice
     * @return
     */
    @RequestMapping("editProductPrice")
    @ResponseBody
    @Transactional
    public ApiResult editProductPrice(Long id,Double basePrice) {
        if(id == null || basePrice == null) {
            return ApiResult.resultWith(ResultCodeEnum.DATA_NULL);
        }
        Product product = productService.findOne(id);
        List<Price> prices = priceService.findByProduct(product);
        for(Price price : prices) {
            price.setPrice(basePrice);
            priceService.save(price);
        }
        product.setBasePrice(basePrice);
        productService.save(product);
        return ApiResult.resultWith(ResultCodeEnum.EDIT_SUCCESS);
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
