package com.zfy.mp.doc.javaTutorial.designMode.proxy.springaop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 产品服务
 */
@Service
public class ProductService {

    @Transactional
    public void createProduct(String productId, String name, double price) {
        System.out.println("创建产品: " + name);
    }

    @Transactional
    public void updatePrice(String productId, double newPrice) {
        System.out.println("更新价格: " + newPrice);
    }
}
