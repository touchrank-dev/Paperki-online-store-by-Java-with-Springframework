package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.ProductDao;
import com.kushnir.paperki.model.category.CategorySimple;
import com.kushnir.paperki.model.product.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProductBeanImpl implements ProductBean {

    private static final Logger LOGGER = LogManager.getLogger(ProductBeanImpl.class);

    @Autowired
    ProductDao productDao;

    @Autowired
    CatalogBean catalogBean;

    @Override
    public HashMap<Integer, ProductSimple> getAll() {
        LOGGER.debug("getAll() >>>");
        HashMap<Integer, ProductSimple> products = productDao.getAll();
        return products;
    }

    @Override
    public HashMap<Integer, Product> getProductListByCategoryTName(String categoryTName) {
        LOGGER.debug("getProductListByCategoryTName({}) >>>", categoryTName);
        HashMap<Integer, Product> products =
                    productDao.getProductListByCategoryTName(categoryTName);
        return products;
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
        try {
            ArrayList<Attribute> attributes = productDao.getAttributesByPNT(pnt);
            return attributes;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("HAVE NO ATTRIBUTES FOR: {}", pnt);
            return null;
        }
    }

    @Override
    public String updateProducts() {
        StringBuilder sb = new StringBuilder();
        List<CSVProduct> updProd = new ArrayList<>();

        try {

            unpublishAllProducts();

            HashMap<Integer, CategorySimple> categories = catalogBean.getAllChildrenWithPapIdKey();
            Assert.notNull(categories, "categories = null");
            Assert.isTrue(categories.size() > 0, "categories size = 0");

            // TODO get All brands

            HashMap<Integer, CSVProduct> CSVProducts = getProductsFromCSV(sb);
            Assert.notNull(CSVProducts, "CSVProducts = null");
            Assert.isTrue(CSVProducts.size() > 0, "CSVProducts size = 0");

            HashMap<Integer, ProductSimple> products =  getAll();
            Assert.notNull(products, "products = null");

            for (Map.Entry<Integer, CSVProduct> entry : CSVProducts.entrySet()) {
                try {
                    CSVProduct csvProduct = entry.getValue();
                    CategorySimple categorySimple = categories.get(csvProduct.getGroupPapId());
                    Assert.notNull(categorySimple, "Категория PapId: "+csvProduct.getGroupPapId()+
                            ", для pnt: "+csvProduct.getPnt()+" не найдена");

                    // SET CATEGORY ID
                    csvProduct.setCategoryId(categorySimple.getId());
                    // SET LINK
                    csvProduct.setLink(categorySimple.getTranslitName()+"/"+csvProduct.getTranslitName());
                    // TODO SET BRAND ID
                    // TODO VALIDATE
                    validateProduct(csvProduct);

                    ProductSimple product = products.get(csvProduct.getPnt());
                    if (product != null) {


                        updProd.add(csvProduct);
                    } else {
                        int id = addProduct(csvProduct);
                        csvProduct.setId(id);
                        addProductCatalogRef(csvProduct);
                    }
                } catch (Exception e) {
                    sb.append("ERROR :").append(e).append(", ").append(e.getMessage()).append('\n');
                    LOGGER.error("ERROR: {}, {}", e, e.getMessage());
                }
            }

            sb.append("========== UPDATE FINISHED ==========");

        } catch (Exception e) {
            sb.append("UPDATE FINISHED WITH ERROR: ").append(e).append(" >>> ").append(e.getMessage());
            LOGGER.error("UPDATE FINISHED WITH ERROR: {}, {}", e, e.getMessage());
        }
        return sb.toString();
    }

    private void unpublishAllProducts() {
        LOGGER.debug("unpublishAllProducts() >>>");
        productDao.unpublishAllProducts();
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

    private int addProduct(CSVProduct product) {
        LOGGER.debug("addProduct({}) >>>", product.getPnt());
        int id = productDao.addProduct(product);
        return id;
    }

    private int addProductCatalogRef(CSVProduct product) {
        LOGGER.debug("addProductCatalogRef({}, {}) >>>", product.getPnt(), product.getCategoryId());
        int id = productDao.addProductCatalogRef(product);
        return id;
    }

    private void validateProduct(CSVProduct product) {
        Assert.notNull(product.getPnt(), "pnt = null");
        Assert.isTrue(product.getPnt() > 0, "pnt = 0");
        Assert.notNull(product.getFullName(), "Full name = null");
        Assert.hasText(product.getFullName(), "Full name is blank");
        Assert.notNull(product.getShortName(), "Short name = null");
        Assert.hasText(product.getShortName(), "Short name is blank");
        Assert.notNull(product.getTranslitName(), "Translit name = null");
        Assert.hasText(product.getTranslitName(), "Translit name is blank");
        Assert.notNull(product.getMeasure(), "measure = null");
        Assert.hasText(product.getMeasure(), "measure is blank");
        Assert.notNull(product.getBasePrice(), "base price = null");
        Assert.isTrue(product.getBasePrice() > 0d, "base price <= 0");
        Assert.notNull(product.getVAT(), "vat = null");
    }
}
