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
    @Transactional(value = "transactionManager")
    public Set<Price> setProduct(Agent agent,ProductPrice productPrice) {
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
    public Set<Price> updateProduct(Agent agent, ProductPrice productPrice) {
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
}
