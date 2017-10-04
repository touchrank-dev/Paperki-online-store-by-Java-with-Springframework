package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.CatalogDao;
import com.kushnir.paperki.model.Category;

import com.kushnir.paperki.model.category.CategoryContainer;
import com.kushnir.paperki.model.product.Product;
import com.kushnir.paperki.service.exceptions.ServiceException;
import com.kushnir.paperki.service.util.Transliterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class CatalogBeanImpl implements CatalogBean {

    private static final Logger LOGGER = LogManager.getLogger(CatalogBeanImpl.class);

    @Autowired
    CatalogDao catalogDao;

    @Autowired
    ProductBean productBean;

    @Value("${catalog.url}")
    String catalogURL;

    @Override
    @Transactional
    public HashMap<Integer, HashMap<Integer, Category>> getAll() throws ServiceException {
        try {
            HashMap categories = catalogDao.getAll();
            return categories;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Category getCategoryByTName(String categoryTName) throws ServiceException {
        LOGGER.debug("getCategoryByTName({}) >>> ", categoryTName);
        try {
            Category category = catalogDao.getCategoryByTName(categoryTName);
            return category;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public HashMap<Integer, Product> getProductsByCategoryTName(String categoryTName) throws ServiceException {
        LOGGER.debug("getProductsByCategoryTName({}) >>> ", categoryTName);
        try {
            HashMap<Integer, Product> products = productBean.getProductListByCategoryTName(categoryTName);
            return products;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Product getProductByTName(String productTName) throws ServiceException {
        LOGGER.debug("getProductByTName({}) >>> ", productTName);
        try {
            Product product = productBean.getProductByTName(productTName);
            return product;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    private HashMap<Integer, HashMap<Integer, Category>> getCategoriesFromCSV() throws IOException, ServiceException {
        LOGGER.debug("getCategoriesFromCSV() >>> ");
        HashMap<Integer, HashMap<Integer, Category>> categories = catalogDao.getCategoriesFromCSV();
        return categories;
    }


    private String updateCatalog2() throws ServiceException, IOException {
        LOGGER.debug("updateCatalog() START PROCESS >>>");

        StringBuilder sb = new StringBuilder();

        HashMap<Integer, HashMap<Integer, Category>> CSVCategories = getCategoriesFromCSV();
        Assert.notNull(CSVCategories, "CSVCategories = null");
        HashMap<Integer, Category> parentCSXCategories = CSVCategories.get(0);
        Assert.notNull(parentCSXCategories, "parentCSXCategories = null");

        HashMap<Integer, HashMap<Integer, Category>> categories = getAll();
        Assert.notNull(categories, "categories = null");
        HashMap<Integer, Category> parentCategories = categories.get(0);
        Assert.notNull(parentCategories, "parentCategories = null");

        List<Category> updCat = new ArrayList<>();
        List<Category> addCat = new ArrayList<>();

        // ===== FOR PARENT CATEGORIES =================================================
        for (Map.Entry<Integer ,Category> CSVCAtEntry : parentCSXCategories.entrySet()) {
            try {

                Category CSVCategory = CSVCAtEntry.getValue();
                Assert.notNull(CSVCategory, "category = null");
                String translitName = Transliterator.cyr2lat(CSVCategory.getName());
                String link = catalogURL+translitName;
                CSVCategory.setTranslitName(translitName);
                CSVCategory.setLink(link);
                validateCategory(CSVCategory);

                for(Map.Entry<Integer, Category> catEntry : parentCategories.entrySet()) {
                    Category category = catEntry.getValue();

                    if(category.getPapId() != null) {
                        if (CSVCategory.getPapId().equals(category.getPapId())) {
                            CSVCategory.setId(category.getId());
                            updCat.add(CSVCategory);
                            break;
                        }
                    } else if (category.getTranslitName() != null){
                        if (CSVCategory.getTranslitName().equals(category.getTranslitName())) {
                            CSVCategory.setId(category.getId());
                            updCat.add(CSVCategory);
                            break;
                        }
                    }
                }

                if (CSVCategory.getId() == null) {
                    addCat.add(CSVCategory);
                }

                sb.append("SUCCESS >>>")
                        .append("papId: ")
                        .append(CSVCategory.getPapId())
                        .append(" - ")
                        .append(CSVCategory.getTranslitName())
                        .append('\n');
            } catch (Exception e) {
                LOGGER.error("ERROR PREPARING >>> {}", e.getMessage());
                sb.append("ERROR PREPARING >>> ").append(e.getMessage()).append('\n');
                continue;
            }
        }

        CSVCategories.remove(0);

        // ===== FOR CHILD CATEGORIES =================================================
        for (Map.Entry<Integer, HashMap<Integer, Category>> entry : CSVCategories.entrySet()) {

        }

        if (!addCat.isEmpty() && addCat.size() > 0) {
            try {
                addCategories(addCat.toArray());

                // addCategoriesRef(addCat.toArray());
                sb.append("CATEGORIES SUCCESSFUL ADDED >>>").append('\n');
                LOGGER.debug(sb);
            } catch (Exception e) {
                sb.append("ERROR ADDING NEW CATEGORIES >>> ").append(e.getMessage()).append('\n');
                LOGGER.debug(sb);
                throw e;
            }
        }
        return sb.toString();
    }

    @Override
    @Transactional
    public String updateCatalog() throws ServiceException, IOException {
        LOGGER.debug("updateCatalog() START PROCESS >>>");

        StringBuilder sb = new StringBuilder();
        CategoryContainer CSVCategories = getCategoriesFromCSVToContainer();
        Assert.notNull(CSVCategories, "CSVCategories = null");
        CategoryContainer categories = getCategoriesToContainer();
        Assert.notNull(categories, "categories = null");

        List<Category> updCat = new ArrayList<>();

        /*PARENTS*/
        for (Map.Entry<Integer, Category> entry : CSVCategories.getParents().entrySet()) {
            try {
                Category CSVParentCategory = entry.getValue();
                Assert.notNull(CSVParentCategory, "CSVCategory = null");
                String translitName = Transliterator.cyr2lat(CSVParentCategory.getName());
                String link = catalogURL + translitName;
                CSVParentCategory.setTranslitName(translitName);
                CSVParentCategory.setLink(link);
                validateCategory(CSVParentCategory);

                for(Map.Entry<Integer, Category> catEntry : categories.getParents().entrySet()) {
                    Category category = catEntry.getValue();

                    if(category.getPapId() != null) {
                        if (CSVParentCategory.getPapId().equals(category.getPapId())) {
                            CSVParentCategory.setId(category.getId());
                            updCat.add(CSVParentCategory);
                            break;
                        }
                    } else if (category.getTranslitName() != null){
                        if (CSVParentCategory.getTranslitName().equals(category.getTranslitName())) {
                            CSVParentCategory.setId(category.getId());
                            updCat.add(CSVParentCategory);
                            break;
                        }
                    }
                }

                if (CSVParentCategory.getId() == null) {
                    int id = addCategory(CSVParentCategory);
                    CSVParentCategory.setId(id);
                    addRefCategory(CSVParentCategory);
                }

            } catch (Exception e) {
                LOGGER.error("ERROR >>> {}", e.getMessage());
                sb.append("ERROR >>> ").append(e.getMessage()).append('\n');
                continue;
            }
        }


        /*CHILDREN*/
        for (Map.Entry<Integer, Category> entry : CSVCategories.getChildren().entrySet()) {}

        return sb.toString();
    }


    public CategoryContainer getCategoriesFromCSVToContainer() throws IOException {
        LOGGER.debug("getCategoriesFromCSVToContainer() >>>");
        return catalogDao.getCategoriesFromCSVToContainer();
    }

    public CategoryContainer getCategoriesToContainer() {
        LOGGER.debug("getCategoriesToContainer() >>>");
        return catalogDao.getCategoriesToContainer();
    }

    private int addCategory(Category category) {
        LOGGER.debug("addCategory() >>>");
        return catalogDao.addCategory(category);
    }

    private int addRefCategory(Category category) {
        LOGGER.debug("addRefCategory() >>>");
        return catalogDao.addRefCategory(category);
    }

    @Override
    public int[] addCategories(Object[] categories) {
        LOGGER.debug("addCategories() >>>");
        return catalogDao.addCategories(categories);
    }

    @Override
    public int[] addCategoriesRef(Object[] categories) {
        LOGGER.debug("addCategoriesRef() >>>");
        return catalogDao.addCategoriesRef(categories);
    }

    @Override
    public int updateCategory(Category category) {
        return 0;
    }

    private void validateCategory(Category category) {
        Assert.notNull(category, "Категория = null");
        Assert.notNull(category.getPapId(), "PapId = null");
        Assert.notNull(category.getName(), "Имя категории = null");
        Assert.hasText(category.getName() ,"Пустое имя категории");
        Assert.notNull(category.getTranslitName(), "translitName = null");
        Assert.hasText(category.getTranslitName(), "translitName is blank");
    }
}
