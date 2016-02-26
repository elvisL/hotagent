package com.huotu.hotagent.admin.controller.product;

import com.huotu.hotagent.admin.common.AuthenticatedWebTest;
import com.huotu.hotagent.admin.common.LoginAs;
import com.huotu.hotagent.admin.controller.product.pages.ProductListPage;
import com.huotu.hotagent.service.common.ProductType;
import com.huotu.hotagent.service.entity.product.Product;
import com.huotu.hotagent.service.repository.product.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by admin on 2016/2/25.
 */
public class ProductControllerTest extends AuthenticatedWebTest {
    @Autowired
    private ProductRepository productRepository;
    //用于测试修改产品
    private Product mockProduct;

    @Before
    public void initData(){
        mockProduct = new Product();
        mockProduct.setName("产品1");
        mockProduct.setBasePrice(1000);
        mockProduct.setProductDesc("备注");
        mockProduct.setProductType(ProductType.HUOBAN_MALL);
//        mockProduct = productRepository.saveAndFlush(mockProduct);
    }

    //1.显示所有产品，验证界面数据和查询数据库数据是否一致
    //2.点击第一行 修改 控件，验证数据是否一致
    //3.修改产品名称，单价，描述，点击 保存 控件，验证第一行数据是否一致
    @Test
    @LoginAs(isRoot = true)
    public void testProducts() throws Exception {
        webDriver.get("http://localhost/showProducts");
        ProductListPage productListPage = initPage(ProductListPage.class);
        //直接读取数据库数据
        List<Product> products = productRepository.findAll();
        productListPage.setProducts(products);
        productListPage.setMockProduct(mockProduct);
        //与界面数据校验
        productListPage.validate();
    }
}