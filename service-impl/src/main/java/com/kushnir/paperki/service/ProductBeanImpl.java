package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.ProductDao;
import com.kushnir.paperki.model.Brand;
import com.kushnir.paperki.model.Discount;
import com.kushnir.paperki.model.Price;
import com.kushnir.paperki.model.category.CategorySimple;
import com.kushnir.paperki.model.product.*;
import com.kushnir.paperki.service.mail.Mailer;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("productBean")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Transactional
public class ProductBeanImpl implements ProductBean {

    private static final Logger LOGGER = LogManager.getLogger(ProductBeanImpl.class);

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CatalogBean catalogBean;

    @Autowired
    private BrandService brandService;

    @Autowired
    private Mailer mailer;

    @Value("${catalog.sclad}")
    private Integer idSclad;

    @Value("${catalog.url}")
    private String catalogURL;

    @Override
    public HashMap<Integer, ProductSimple> getAll() {
        LOGGER.debug("getAll() >>>");
        HashMap<Integer, ProductSimple> products = productDao.getAll();
        return products;
    }

    @Override
    public HashMap<Integer, Product> getProductListByCategoryTName(String categoryTName, Integer sortType) {
        LOGGER.debug("getProductListByCategoryTName() >>>");
        HashMap<Integer, Product> products =
                    productDao.getProductListByCategoryTName(categoryTName, sortType);
        return products;
    }

    @Override
    public HashMap<Integer, HashMap<Integer, Product>> getAllExtraTypeProducts() {
        LOGGER.debug("getAllExtraTypeProducts() >>>");
        HashMap<Integer, HashMap<Integer, Product>> products = productDao.getAllExtraTypeProducts();
        return products;
    }

    @Override
    public Product getProductByPNT(Integer pnt) {
        LOGGER.debug("getProductByPNT({}) >>>", pnt);
        Product product = productDao.getProductByPNT(pnt);
        if(product != null) {
            product.setAttributes(getAttributesByPNT(product.getPnt()));
        }
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
        try {
            AvailableProduct availableProduct = productDao.getAvailableProductByPNT(pnt);
            return availableProduct;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("Prodict pnt: {}, is unavailable", pnt);
            return null;
        }
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

            unpublishAllProducts();

            for (Map.Entry<Integer, CSVProduct> entry : CSVProducts.entrySet()) {
                try {
                    CSVProduct csvProduct = entry.getValue();

                    // pnt
                    Integer csvPnt = csvProduct.getPnt();
                    Assert.notNull(csvPnt, "csvPnt = null");
                    Assert.isTrue(csvPnt > 0, "csvPnt <= 0");

                    // SET CATEGORY ID
                    Integer groupPapId = csvProduct.getGroupPapId();
                    Assert.notNull(groupPapId, "groupPapId = null");
                    Assert.isTrue(groupPapId > 0, "groupPapId <= 0");

                    CategorySimple categorySimple = categories.get(groupPapId);
                    Assert.notNull(categorySimple, "Категория PapId: "+groupPapId+
                            ", для pnt: "+csvPnt+" не найдена");
                    Integer categoryId = categorySimple.getId();
                    Assert.notNull(categoryId, "Категория Id = null");
                    Assert.isTrue(categoryId > 0, "Категория Id <= 0");
                    csvProduct.setCategoryId(categoryId);

                    // SET LINK
                    String catTranslitName = categorySimple.getTranslitName();
                    String csvTranslitName = csvProduct.getTranslitName();
                    Assert.notNull(csvTranslitName, "csvTranslitName = Null");
                    Assert.hasText(csvTranslitName,"csvTranslitName пустое");
                    Assert.notNull(catTranslitName, "Имя категории = Null");
                    Assert.hasText(catTranslitName,"Имя категории пустое");
                    csvProduct.setLink(catalogURL+catTranslitName+"/"+csvTranslitName);

                    // SET BRAND ID
                    Integer csvPapBrandId = csvProduct.getBrandId();
                    Assert.notNull(csvPapBrandId, "csvPapBrandId = null");
                    Assert.isTrue(csvPapBrandId > 0, "csvPapBrandId <= 0");

                    Brand brand = brands.get(csvPapBrandId);
                    Assert.notNull(brand, "Бренд papId("+csvPapBrandId+") не найден");

                    Integer brandId = brand.getId();
                    Assert.notNull(brandId, "Идентификатор бренда = null");
                    Assert.isTrue(brandId > 0, "Идентификатор бренда <= 0");
                    csvProduct.setBrandId(brandId);

                    // VALIDATE
                    validateProduct(csvProduct);

                    ProductSimple product = products.get(csvPnt);
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

    @Override
    public String updateStock() {
        StringBuilder sb = new StringBuilder();
        List<StockItem> addItm = new ArrayList<>();

        try {

            HashMap<Integer, ProductSimple> products = getAll();
            Assert.notNull(products, "products = null");
            Assert.isTrue(products.size() > 0, "products <= 0");

            HashMap<Integer, StockItem> items = productDao.getStockItemsFromCSV(sb);
            Assert.notNull(items, "items = null");
            Assert.isTrue(items.size() > 0, "items <= 0");

            productDao.clearStock(idSclad);

            for (Map.Entry<Integer, StockItem> entry : items.entrySet()) {
                try {
                    StockItem item = entry.getValue();
                    Integer pnt = item.getPnt();
                    ProductSimple product = products.get(pnt);

                    if (product != null) {
                        Integer id = product.getId();
                        Assert.notNull(id, "id = null");
                        Assert.isTrue(id > 0, "id <= 0");

                        item.setId(id);
                        item.setIsStock(idSclad);

                        addItm.add(item);
                    }
                } catch (Exception e) {
                    sb.append("ERROR :").append(e).append(", ").append(e.getMessage()).append('\n');
                    LOGGER.error("ERROR: {}, {}", e, e.getMessage());
                }
            }

            int addedCount = productDao.addItemsToStock(addItm.toArray()).length;

            sb.append("ADDED IN STOCK ITEMS: ").append(addItm.size()).append("/")
                    .append(addedCount).append('\n');

            sb.append("========== UPDATE FINISHED ==========");
        } catch (Exception e) {
            sb.append("UPDATE FINISHED WITH ERROR: ").append(e).append(" >>> ").append(ExceptionUtils.getStackTrace(e));
            LOGGER.error("UPDATE FINISHED WITH ERROR: {}, {}", e, e.getMessage());
            mailer.toSupportMail(sb.toString(), "ERROR UPDATE STOCK");
        }
        return sb.toString();
    }

    @Override
    public String updateProductAttributes() {
        StringBuilder sb = new StringBuilder();
        List<Attribute> addAttr = new ArrayList<>();

        try {

            List<Attribute> attributes = productDao.getAttributesFromCSV(sb);
            Assert.notNull(attributes, "attributes = null");
            Assert.isTrue(attributes.size() > 0, "attributes.size = 0");

            productDao.deleteAllAttributes();

            for (Attribute attribute : attributes) {
                try {

                    validateAttribute(attribute);
                    addAttr.add(attribute);

                } catch (Exception e) {
                    sb.append("ERROR :").append(e).append(", ").append(e.getMessage()).append('\n');
                    LOGGER.error("ERROR: {}, {}", e, e.getMessage());
                }
            }

            if (addAttr.size() > 0) {
                int addedCount = productDao.batchAddAttributes(addAttr.toArray()).length;
                sb.append("ADDED ATTRIBUTES: ").append(attributes.size()).append("/")
                        .append(addedCount).append('\n');
            } else {
                sb.append("ERROR addAttr = null").append('\n');
            }

            sb.append("========== UPDATE FINISHED ==========");

        } catch (Exception e) {
            sb.append("UPDATE FINISHED WITH ERROR: ").append(e).append(" >>> ").append(e.getMessage());
            LOGGER.error("UPDATE FINISHED WITH ERROR: {}, {}", e, e.getMessage());
        }

        mailer.toSupportMail(sb.toString(), "UPDATE PRODUCTS ATTRIBUTES REPORT");
        return sb.toString();
    }


    @Override
    public String updateProductPrices() {
        LOGGER.debug("updateProductPrices() >>>");
        StringBuilder sb = new StringBuilder();

        try {

            ArrayList<Price> prices = productDao.getQuantityPricesFromCSV(sb);
            productDao.deleteAllQuantityPrices();

            if (prices != null && prices.size() > 0) {
                int[] count = productDao.batchAddQuantityPrices(prices.toArray());
                sb.append("ADDED QUANTITY PRICES: ").append(prices.size()).append("/")
                        .append(count.length).append('\n');
            } else {
                sb.append("NO QUANTITY PRICES").append('\n');
            }

            sb.append("========== UPDATE FINISHED ==========");

        } catch (Exception e) {
            sb.append('\n').append("UPDATE FINISHED WITH ERROR: ").append(e).append(" >>> ").append(e.getMessage());
            LOGGER.error("UPDATE FINISHED WITH ERROR: {}, {}", e, e.getMessage());
        }

        mailer.toSupportMail(sb.toString(), "UPDATE PRODUCTS QUANTITY PRICES REPORT");
        return sb.toString();
    }


    @Override
    @Transactional
    public String updateProductDescriptions() {
        LOGGER.debug("updateProductDescriptions() >>>");
        StringBuilder sb = new StringBuilder();
        try {

            Map<Integer, Description> CSVDescriptions = productDao.getProductDescriptionsFromCSV(sb);
            Assert.notNull(CSVDescriptions, "CSVDescriptions is null");

            productDao.deleteAllDescriptions();
            sb.append("ADDED PRODUCT DESCRIPTIONS: ").append(CSVDescriptions.size()).append("/")
                    .append(productDao.batchAddDescriptions(CSVDescriptions.values().toArray()).length).append('\n');

            sb.append("========== UPDATE FINISHED ==========");

        } catch (Exception e) {
            sb.append('\n').append("UPDATE FINISHED WITH ERROR: ").append(e).append(" >>> ").append(e.getMessage());
            LOGGER.error("UPDATE FINISHED WITH ERROR: {}, {}", e, e.getMessage());
        }

        mailer.toSupportMail(sb.toString(), "UPDATE PRODUCTS DESCRIPTIONS REPORT");
        return sb.toString();
    }

    @Override
    public String updateProductDiscounts() {
        LOGGER.debug("updateProductDiscounts() >>>");
        StringBuilder sb = new StringBuilder();
        ArrayList<Discount> discounts = new ArrayList<>();
        try {
            ArrayList<Discount> CSVDiscounts = productDao.getDiscountsFromCSV(sb);
            Assert.notNull(CSVDiscounts, "CSVDiscounts is null");
            HashMap<Integer, ProductSimple> products = getAll();
            Assert.notNull(products, "products is null");

            for (Discount discount: CSVDiscounts) {
                ProductSimple product = products.get(discount.getPnt());
                if (product != null && product.getId() != null) {
                    discount.setProductId(product.getId());
                    discounts.add(discount);
                }
            }

            productDao.deleteAllDiscounts();
            productDao.batchAddDiscounts(discounts.toArray());

            sb.append("========== UPDATE FINISHED ==========");

        } catch (Exception e) {
            sb.append('\n').append("UPDATE FINISHED WITH ERROR: ").append(e).append(" >>> ").append(e.getMessage());
            LOGGER.error("UPDATE FINISHED WITH ERROR: {}, {}", e, e.getMessage());
        }
        mailer.toSupportMail(sb.toString(), "UPDATE PRODUCTS DISCOUNTS REPORT");
        return sb.toString();
    }


    @Override
    public ArrayList<AvailableProduct> searchProducts(String str) {
        LOGGER.debug("searchProducts({}) >>>", str);

        if (isPNT(str)) return productDao.searchProducts(str);
        else return smartSearch(str);
    }

    private Boolean isPNT(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private ArrayList<AvailableProduct> smartSearch(String str) {
        try {
            String[] words = str.split(" ");
            return productDao.smartSearch(words);
        } catch (Exception e) {
            LOGGER.error(e);
            return null;
        }
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
        try {
            int id = productDao.addProductCatalogRef(product);
            return id;
        } catch (Exception e) { return 0;}
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
        String report = "products ref: "+result.length+"/"+products.length;
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

    private void validateAttribute(Attribute attribute) {
        Assert.notNull(attribute,"attribute = null");
        Assert.notNull(attribute.getPnt(), "attribute.pnt = null");
        Assert.isTrue(attribute.getPnt() > 0, "attribute.pnt <= 0");
        Assert.notNull(attribute.getName(), "attribute.name = null");
        Assert.hasText(attribute.getName(), "attribute.name is blank");
        Assert.notNull(attribute.getValue(), "attribute.value = null");
        Assert.notNull(attribute.getOrder(), "attribute.order = null");
    }
}
