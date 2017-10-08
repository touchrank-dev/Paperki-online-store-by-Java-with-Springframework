package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.ProductDao;
import com.kushnir.paperki.model.Brand;
import com.kushnir.paperki.model.category.CategorySimple;
import com.kushnir.paperki.model.product.*;

import com.kushnir.paperki.service.mail.Mailer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    BrandService brandService;

    @Autowired
    Mailer mailer;

    @Value("${catalog.url}")
    String catalogURL;

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

            HashMap<Integer, Brand> brands = brandService.getAll();
            Assert.notNull(brands, "brands = null");
            Assert.isTrue(brands.size() > 0, "brands size = 0");

            HashMap<Integer, CSVProduct> CSVProducts = getProductsFromCSV(sb);
            Assert.notNull(CSVProducts, "CSVProducts = null");
            Assert.isTrue(CSVProducts.size() > 0, "CSVProducts size = 0");

            HashMap<Integer, ProductSimple> products =  getAll();
            Assert.notNull(products, "products = null");

            for (Map.Entry<Integer, CSVProduct> entry : CSVProducts.entrySet()) {
                try {
                    CSVProduct csvProduct = entry.getValue();
                    // PAP_ID
                    Integer papId = csvProduct.getGroupPapId();
                    Assert.notNull(papId, "papId = null");
                    Assert.isTrue(papId > 0, "papId <= 0");

                    // SET CATEGORY ID
                    CategorySimple categorySimple = categories.get(papId);
                    Assert.notNull(categorySimple, "Категория PapId: "+papId+
                            ", для pnt: "+csvProduct.getPnt()+" не найдена");
                    Assert.notNull(categorySimple.getId(), "Категория Id = null");
                    Assert.isTrue(categorySimple.getId() > 0, "Категория Id <= 0");
                    csvProduct.setCategoryId(categorySimple.getId());
                    // SET LINK
                    String catTranslitName = categorySimple.getTranslitName();
                    Assert.notNull(catTranslitName, "Имя категории = Null");
                    Assert.hasText(catTranslitName,"Имя категории пустое");
                    csvProduct.setLink(catalogURL+catTranslitName+"/"+csvProduct.getTranslitName());
                    // SET BRAND ID
                    Brand brand = brands.get(csvProduct.getBrandId());
                    Assert.notNull(brand, "Бренд papId("+csvProduct.getBrandId()+") не найден");
                    Assert.notNull(brand.getId(), "Идентификатор бренда = null");
                    Assert.isTrue(brand.getId() > 0, "Идентификатор бренда <= 0");
                    csvProduct.setBrandId(brand.getId());
                    // VALIDATE
                    validateProduct(csvProduct);

                    LOGGER.debug("csvProduct: {}",csvProduct);

                    ProductSimple product = products.get(csvProduct.getPnt());
                    if (product != null) {
                        Integer id = product.getId();
                        Assert.notNull(id, "Идентификатор продукта = null");
                        Assert.isTrue(id > 0, "Идентификатор продукта <= 0");
                        csvProduct.setId(id);
                        updProd.add(csvProduct);
                    } else {
                        int id = addProduct(csvProduct);
                        Assert.isTrue(id > 0, "Не удалось добавить новый продукт pnt: "+csvProduct.getPnt()+"");
                        csvProduct.setId(id);
                        addProductCatalogRef(csvProduct);
                        sb.append("NEW PRODUCT SUCCESSFULLY ADDED: id: ").append(id).append('\n');
                        LOGGER.debug("NEW PRODUCT SUCCESSFULLY ADDED: id: {}", id);
                    }
                } catch (Exception e) {
                    sb.append("ERROR :").append(e).append(", ").append(e.getMessage()).append('\n');
                    LOGGER.error("ERROR: {}, {}", e, e.getMessage());
                }
            }

            // UPDATE products ====
            if (!updProd.isEmpty() && updProd.size() > 0) {
                sb.append(batchUpdateProducts(updProd.toArray())).append('\n');
                sb.append(batchUpdateProductsCatalogRef(updProd.toArray())).append('\n');
            }
            sb.append("========== UPDATE FINISHED ==========");

        } catch (Exception e) {
            sb.append("UPDATE FINISHED WITH ERROR: ").append(e).append(" >>> ").append(e.getMessage());
            LOGGER.error("UPDATE FINISHED WITH ERROR: {}, {}", e, e.getMessage());
        }

        mailer.toSupportMail(sb.toString(), "UPDATE PRODUCTS REPORT");
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

    private String batchUpdateProducts(Object[] products) {
        LOGGER.debug("batchUpdateProducts(count: {}) >>>", products.length);
        int[] result = productDao.batchUpdateProducts(products);
        String report = "SUCCESSFUL UPDATED: "+result.length+"/"+products.length+" products";
        LOGGER.debug(report);
        return report;
    }

    private String batchUpdateProductsCatalogRef(Object[] products) {
        LOGGER.debug("batchUpdateProductsCatalogRef(count: {}) >>>", products.length);
        int[] result = productDao.batchUpdateProductsCatalogRef(products);
        String report = "SUCCESSFUL UPDATED: "+result.length+"/"+products.length+" products";
        LOGGER.debug(report);
        return report;
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
        Assert.notNull(product.getBrandId(), "brand = null");
        Assert.isTrue(product.getBrandId() > 0, "brand <= 0");
    }
}
