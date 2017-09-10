package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.ProductDao;
import com.kushnir.paperki.model.AvailableProduct;
import com.kushnir.paperki.model.CartProduct;
import com.kushnir.paperki.model.Product;

import com.kushnir.paperki.model.product.Attribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductBeanImpl implements ProductBean {

    private static final Logger LOGGER = LogManager.getLogger(ProductBeanImpl.class);

    @Autowired
    ProductDao productDao;

    @Override
    public HashMap<Integer, Product> getProductListByCategoryTName(String categoryTName) {
        LOGGER.debug("getProductListByCategoryTName({}) >>>", categoryTName);
        HashMap<Integer, Product> products =
                productDao.getProductListByCategoryTName(categoryTName);
        LOGGER.debug("PRODUCTS: {}", products);
        return products;
    }

    @Override
    public Product getProductByPNT(Integer pnt) {
        LOGGER.debug("getProductByPNT({}) >>>", pnt);
        Product product = productDao.getProductByPNT(pnt);
        LOGGER.debug("PRODUCT: {}", product);
        return product;
    }

    @Override
    public Product getProductByTName(String TName) {
        LOGGER.debug("getProductByTName({}) >>>", TName);
        Product product = productDao.getProductByTName(TName);
        if(product != null) {
            product.setAttributes(getAttributesByPNT(product.getPnt()));
        }
        LOGGER.debug("PRODUCT: {}", product);
        return product;
    }

    @Override
    public AvailableProduct getAvailableproductByPNT(Integer pnt) {
        LOGGER.debug("getAvailableproductByPNT({}) >>>", pnt);
        AvailableProduct availableProduct = productDao.getAvailableProductByPNT(pnt);
        LOGGER.debug("AVAILABLE PRODUCT: {}", availableProduct);
        return availableProduct;
    }

    @Override
    public ArrayList<Attribute> getAttributesByPNT(Integer pnt) {
        LOGGER.debug("getAttributesByPNT({}) >>>", pnt);
        ArrayList<Attribute> attributes = productDao.getAttributesByPNT(pnt);
        LOGGER.debug("PRODUCT ATTRIBUTES: {}", attributes);
        return attributes;
    }
}
