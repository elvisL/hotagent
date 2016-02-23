package com.huotu.hotagent.service.service.product.impl;

import com.huotu.hotagent.service.common.ProductType;
import com.huotu.hotagent.service.entity.product.Price;
import com.huotu.hotagent.service.entity.product.Product;
import com.huotu.hotagent.service.entity.role.agent.Agent;
import com.huotu.hotagent.service.model.AgentProduct;
import com.huotu.hotagent.service.model.ProductPrice;
import com.huotu.hotagent.service.repository.product.PriceRepository;
import com.huotu.hotagent.service.repository.product.ProductRepository;
import com.huotu.hotagent.service.service.product.PriceService;
import com.huotu.hotagent.service.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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


    @Override
    public List<AgentProduct> productList(List<Product> products, Long agentId) {
        List<AgentProduct> agentProductList = new ArrayList<>();
        for (Product product : products) {
            Price price =priceRepository.findByAgent_IdAndProduct_Id(agentId, product.getId());
            AgentProduct agentProduct = new AgentProduct();
            agentProduct.setProductId(product.getId());
            agentProduct.setProductName(product.getName());
            agentProduct.setProductPrice(price.getPrice());
            agentProductList.add(agentProduct);
        }
        return agentProductList;
    }

    @Override
    public Boolean setProduct(Agent agent,ProductPrice productPrice) {
        //1.伙伴商城
        Price huobanMall = new Price();
        huobanMall.setProduct(productRepository.findByProductType(ProductType.HUOBAN_MALL));
        huobanMall.setAgent(agent);
        huobanMall.setPrice(productPrice.getHuobanMall());
        priceRepository.save(huobanMall);
        //DSP广告
        Price dsp = new Price();
        dsp.setProduct(productRepository.findByProductType(ProductType.DSP));
        dsp.setAgent(agent);
        dsp.setPrice(productPrice.getDsp());
        priceRepository.save(dsp);
        //云商学院
        Price hotEdu = new Price();
        hotEdu.setProduct(productRepository.findByProductType(ProductType.HOT_EDU));
        hotEdu.setAgent(agent);
        hotEdu.setPrice(productPrice.getHotEdu());
        priceRepository.save(hotEdu);
        //代运营
        Price thirdPartnar = new Price();
        thirdPartnar.setProduct(productRepository.findByProductType(ProductType.THIRDPARTNAR));
        thirdPartnar.setAgent(agent);
        thirdPartnar.setPrice(productPrice.getThirdPartnar());
        priceRepository.save(thirdPartnar);
        return  true;
    }

    @Override
    public Boolean updateProduct(Agent agent, ProductPrice productPrice) {
        Price HUOBAN_MALL =priceRepository.findByAgent_IdAndProduct_Id(agent.getId(), productRepository.findByProductType(ProductType.HUOBAN_MALL).getId());
        HUOBAN_MALL.setPrice(productPrice.getHuobanMall());
        Price DSP =priceRepository.findByAgent_IdAndProduct_Id(agent.getId(), productRepository.findByProductType(ProductType.DSP).getId());
        DSP.setPrice(productPrice.getDsp());
        Price HOT_EDU =priceRepository.findByAgent_IdAndProduct_Id(agent.getId(), productRepository.findByProductType(ProductType.HOT_EDU).getId());
        HOT_EDU.setPrice(productPrice.getHotEdu());
        Price THIRDPARTNAR =priceRepository.findByAgent_IdAndProduct_Id(agent.getId(), productRepository.findByProductType(ProductType.THIRDPARTNAR).getId());
        THIRDPARTNAR.setPrice(productPrice.getThirdPartnar());
        priceRepository.save(HUOBAN_MALL);
        priceRepository.save(DSP);
        priceRepository.save(HOT_EDU);
        priceRepository.save(THIRDPARTNAR);

        return true;
    }
}
