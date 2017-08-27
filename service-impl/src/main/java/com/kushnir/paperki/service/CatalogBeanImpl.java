package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.CatalogDao;
import com.kushnir.paperki.model.Category;

import com.kushnir.paperki.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CatalogBeanImpl implements CatalogBean {

    private static final Logger LOGGER = LogManager.getLogger(CatalogBeanImpl.class);

    @Autowired
    CatalogDao catalogDao;

    @Autowired
    ProductBean productBean;

    @Override
    public HashMap<Integer, HashMap<Integer, Category>> getAll() {
        HashMap categories = catalogDao.getAll();
        LOGGER.debug("getAll() >>> {}", categories);
        return categories;
    }

    @Override
    public ArrayList<Category> getCategoriesFromCSV() throws IOException {
        LOGGER.debug("getCategoriesFromCSV() >>> ");
        ArrayList<Category> categories = catalogDao.getCategoriesFromCSV();
        return categories;
    }

    @Override
    public Category getCategoryByTName(String categoryTName) {
        LOGGER.debug("getCategoryByTName({}) >>> ", categoryTName);
        Category category = catalogDao.getCategoryByTName(categoryTName);
        LOGGER.debug("\nCATEGORY: {}", category);
        return  category;
    }

    @Override
    public HashMap<Integer, Product> getProductsByCategoryTName(String categoryTName) {
        LOGGER.debug("getProductsByCategoryTName({}) >>> ", categoryTName);
        HashMap<Integer, Product> products = productBean.getProductListByCategoryTName(categoryTName);
        LOGGER.debug("\nPRODUCTS: {}", products);
        return products;
    }

    @Override
    public Product getProductByTName(String productTName) {
        LOGGER.debug("getProductByTName({}) >>> ", productTName);
        Product product = productBean.getProductByTName(productTName);
        LOGGER.debug("\nPRODUCT: {}", product);
        return product;
    }
}
