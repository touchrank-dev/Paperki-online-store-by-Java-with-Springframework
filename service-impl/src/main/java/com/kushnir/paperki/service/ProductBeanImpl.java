package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.ProductDao;
import com.kushnir.paperki.model.Category;
import com.kushnir.paperki.model.category.CategoryContainer;
import com.kushnir.paperki.model.product.AvailableProduct;
import com.kushnir.paperki.model.product.CSVProduct;
import com.kushnir.paperki.model.product.Product;
import com.kushnir.paperki.model.product.Attribute;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Service
@Transactional
public class ProductBeanImpl implements ProductBean {

    private static final Logger LOGGER = LogManager.getLogger(ProductBeanImpl.class);

    @Autowired
    ProductDao productDao;

    @Autowired
    CatalogBean catalogBean;

    @Override
    public HashMap<Integer, Product> getProductListByCategoryTName(String categoryTName) {
        LOGGER.debug("getProductListByCategoryTName({}) >>>", categoryTName);
        try {
            HashMap<Integer, Product> products =
                    productDao.getProductListByCategoryTName(categoryTName);
            return products;
        } catch (Exception e) {
            LOGGER.error("{} >>> {}", e, e.getMessage());
            return null;
        }
    }

    @Override
    public Product getProductByPNT(Integer pnt) {
        LOGGER.debug("getProductByPNT({}) >>>", pnt);
        Product product = productDao.getProductByPNT(pnt);
        return product;
    }

    @Override
    public Product getProductByTName(String TName) {
        LOGGER.debug("getProductByTName({}) >>>", TName);
        Product product = productDao.getProductByTName(TName);
        if(product != null) {
            product.setAttributes(getAttributesByPNT(product.getPnt()));
        }
        return product;
    }

    @Override
    public AvailableProduct getAvailableProductByPNT(Integer pnt) {
        LOGGER.debug("getAvailableProductByPNT({}) >>>", pnt);
        AvailableProduct availableProduct = productDao.getAvailableProductByPNT(pnt);
        return availableProduct;
    }

    @Override
    public ArrayList<Attribute> getAttributesByPNT(Integer pnt) {
        LOGGER.debug("getAttributesByPNT({}) >>>", pnt);
        ArrayList<Attribute> attributes = productDao.getAttributesByPNT(pnt);
        return attributes;
    }

    @Override
    public String updateProducts() {
        StringBuilder sb = new StringBuilder();

        // TODO unpublish allProducts

        try {

            CategoryContainer allCategories = catalogBean.getCategoriesToContainer();
            HashMap<Integer, Category> categories = (allCategories == null) ? null : allCategories.getChildren();
            Assert.notNull(categories, "categories = null");
            Assert.isTrue(categories.size() > 0, "categories size = 0");

            HashMap<Integer, CSVProduct> CSVProducts = getProductsFromCSV(sb);
            Assert.notNull(CSVProducts, "CSVProducts = null");
            Assert.isTrue(CSVProducts.size() > 0, "CSVProducts size = 0");

            // TODO get products.







            sb.append("========== UPDATE FINISHED ==========");

        } catch (Exception e) {
            sb.append("UPDATE FINISHED WITH ERROR: ").append(e).append(" >>> ").append(e.getMessage());
            LOGGER.error("");
        }
        return sb.toString();
    }

    private HashMap<Integer, CSVProduct> getProductsFromCSV(StringBuilder sb) {
        LOGGER.debug("getProductsFromCSV() >>>");
        try {
            return productDao.getProductsFromCSV(sb);
        } catch (IOException e) {
            LOGGER.error("getProductsFromCSV() ERROR >>> {}, {}", e, e.getMessage());
            return null;
        }
    }
}
