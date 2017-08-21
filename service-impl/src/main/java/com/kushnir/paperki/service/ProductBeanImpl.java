package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.ProductDao;
import com.kushnir.paperki.model.Product;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public class ProductBeanImpl implements ProductBean {

    private static final Logger LOGGER = LogManager.getLogger(ProductBeanImpl.class);

    @Autowired
    ProductDao productDao;

    @Override
    public HashMap<Integer, Product> getProductListByCategoryTName(String categoryTName) {
        HashMap<Integer, Product> products =
                productDao.getProductListByCategoryTName(categoryTName);
        LOGGER.debug("getProductListByCategoryTName({}) >>>\nPRODUCTS: {}", categoryTName, products);
        return products;
    }

    @Override
    public Product getProductBuPNT(Integer pnt) {
        Product product = productDao.getProductBuPNT(pnt);
        LOGGER.debug("getProductBuPNT({}) >>>\nPRODUCT: {}", pnt, product);
        return product;
    }

    @Override
    public Product getProductByTName(String TName) {
        Product product = productDao.getProductByTName(TName);
        LOGGER.debug("getProductByTName({}) >>>\nPRODUCT: {}", TName, product);
        return product;
    }
}
