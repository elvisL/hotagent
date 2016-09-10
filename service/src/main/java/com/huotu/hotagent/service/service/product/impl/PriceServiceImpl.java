/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotagent.service.service.product.impl;

import com.huotu.hotagent.service.common.ProductType;
import com.huotu.hotagent.service.entity.product.Price;
import com.huotu.hotagent.service.entity.product.Product;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.entity.role.agent.Customer;
import com.huotu.hotagent.service.model.AgentProduct;
import com.huotu.hotagent.service.model.ProductPrice;
import com.huotu.hotagent.service.repository.product.PriceRepository;
import com.huotu.hotagent.service.repository.product.ProductRepository;
import com.huotu.hotagent.service.repository.role.agent.CustomerRepository;
import com.huotu.hotagent.service.service.product.PriceService;
import com.huotu.hotagent.service.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by chendeyu on 2016/2/2.
 */
@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public List<AgentProduct> productList(List<Product> products, Long agentId) {
        List<AgentProduct> agentProductList = new ArrayList<>();
        for (Product product : products) {
            Price price =priceRepository.findByAgent_IdAndProduct_Id(agentId, product.getId());
            List<Customer> customerList = customerRepository.findByAgent_IdAndProductId(agentId, product.getId());
            int saleNum = 0 ;
            for(Customer customer:customerList)
            {
                saleNum +=customer.getSaleNum();
            }
            AgentProduct agentProduct = new AgentProduct();
            agentProduct.setProductId(product.getId());
            agentProduct.setProductName(product.getName());
            agentProduct.setProductPrice(price.getPrice());
            agentProduct.setAccount(price.getPrice()*saleNum);
            agentProductList.add(agentProduct);
        }
        return agentProductList;
    }

    @Override
    public Set<Price> setPrices(Agent agent, ProductPrice productPrice) {
        //1.伙伴商城
        Price huobanMall = new Price();
        huobanMall.setProduct(productRepository.findByProductType(ProductType.HUOBAN_MALL));
        huobanMall.setAgent(agent);
        huobanMall.setPrice(productPrice.getHuobanMall());
        //DSP广告
        Price dsp = new Price();
        dsp.setProduct(productRepository.findByProductType(ProductType.DSP));
        dsp.setAgent(agent);
        dsp.setPrice(productPrice.getDsp());
        //云商学院
        Price hotEdu = new Price();
        hotEdu.setProduct(productRepository.findByProductType(ProductType.HOT_EDU));
        hotEdu.setAgent(agent);
        hotEdu.setPrice(productPrice.getHotEdu());
        //代运营
        Price thirdPartnar = new Price();
        thirdPartnar.setProduct(productRepository.findByProductType(ProductType.THIRDPARTNAR));
        thirdPartnar.setAgent(agent);
        thirdPartnar.setPrice(productPrice.getThirdPartnar());
        Set<Price> priceSet = new HashSet<>();
        priceSet.add(huobanMall);
        priceSet.add(dsp);
        priceSet.add(hotEdu);
        priceSet.add(thirdPartnar);
        return  priceSet;
    }

    @Override
    @Transactional(value = "transactionManager")
    public Set<Price> updatePrices(Agent agent, ProductPrice productPrice) {
        Price HUOBAN_MALL =priceRepository.findByAgent_IdAndProduct_Id(agent.getId(), productRepository.findByProductType(ProductType.HUOBAN_MALL).getId());
        HUOBAN_MALL.setPrice(productPrice.getHuobanMall());
        Price DSP =priceRepository.findByAgent_IdAndProduct_Id(agent.getId(), productRepository.findByProductType(ProductType.DSP).getId());
        DSP.setPrice(productPrice.getDsp());
        Price HOT_EDU =priceRepository.findByAgent_IdAndProduct_Id(agent.getId(), productRepository.findByProductType(ProductType.HOT_EDU).getId());
        HOT_EDU.setPrice(productPrice.getHotEdu());
        Price THIRDPARTNAR =priceRepository.findByAgent_IdAndProduct_Id(agent.getId(), productRepository.findByProductType(ProductType.THIRDPARTNAR).getId());
        THIRDPARTNAR.setPrice(productPrice.getThirdPartnar());
        Set<Price> priceSet = new HashSet<>();
        priceSet.add(HUOBAN_MALL);
        priceSet.add(DSP);
        priceSet.add(HOT_EDU);
        priceSet.add(THIRDPARTNAR);

        return priceSet;
    }

    @Override
    public List<Price> findByAgentId(Long id) {
        List<Price> priceList = priceRepository.findByAgent_Id(id);
        return priceList;
    }

    @Override
    public Price findByAgentIdAndProductId(Long aid,Long pid) {
        Price price = priceRepository.findByAgent_IdAndProduct_Id(aid,pid);
        return price;
    }

    @Override
    public void save(Price price) {
        priceRepository.save(price);
    }

    @Override
    public Set<Price> getBasePrices() {
        Set<Price> prices = new HashSet<>();
        List<Product> products = productRepository.findAll();
//        for(Iterator iterator = products.iterator();iterator.hasNext();) {
//            Product product = (Product)iterator.next();
//            if(product.getChildren().size()!=0) {
//                iterator.remove();
//            }
//        }
        for(Product p :products) {
            Price price = new Price();
            price.setProduct(p);
            price.setPrice(p.getBasePrice());
            prices.add(price);
        }
        return prices;
    }

    @Override
    public List<Price> findByProduct(Product product) {
        return priceRepository.findByProduct(product);
    }
}
