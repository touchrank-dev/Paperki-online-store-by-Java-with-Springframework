package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.CatalogDao;
import com.kushnir.paperki.model.Category;

import com.kushnir.paperki.model.product.Product;
import com.kushnir.paperki.service.exceptions.ServiceException;
import com.kushnir.paperki.service.util.Transliterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class CatalogBeanImpl implements CatalogBean {

    private static final Logger LOGGER = LogManager.getLogger(CatalogBeanImpl.class);

    @Autowired
    CatalogDao catalogDao;

    @Autowired
    ProductBean productBean;

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


    private HashMap<String, Category> getAllCategories() {
        LOGGER.debug("getAllCategories() >>> ");
        HashMap<String, Category> categoryes = catalogDao.getAllCategories();
        return categoryes;
    }

    @Override
    @Transactional
    public String updateCatalog() throws ServiceException, IOException {
        LOGGER.debug("updateCatalog() START PROCESS >>>");

        StringBuilder sb = new StringBuilder();

        ArrayList<Category> CSVCategories = getCategoriesFromCSV();
        HashMap<String, Category> categories = getAllCategories();

        List<Category> catAdd = new ArrayList<>();
        List<Category> catUpd = new ArrayList<>();

        if(CSVCategories != null && categories != null) {
            for (Category CSVCategory : CSVCategories) {
                try {

                    String translitName = Transliterator.cyr2lat(CSVCategory.getName());
                    CSVCategory.setTranslitName(translitName);
                    Category category = categories.get(translitName);

                    validateCategory(CSVCategory);

                    if (category == null) {
                        //TODO add
                        catAdd.add(CSVCategory);
                    } else {
                        //TODO update
                        catUpd.add(CSVCategory);
                    }
                } catch (Exception e) {
                    sb.append("Ошибка обновления категории: ").append(e).append(" MSG >>> ").append(e.getMessage()).append('\n');
                }
            }

            addCategories(catAdd);

        }
        return sb.toString();
    }

    @Override
    public int[] addCategories(List<Category> categories) {
        LOGGER.debug("addCategories() >>>");
        return catalogDao.addCategories(categories);
    }

    @Override
    public int updateCategory(Category category) {
        return 0;
    }

    private void unPublishAllCategories() {
        //TODO
    }

    private ArrayList<Category> getCategoriesFromCSV() throws IOException, ServiceException {
        LOGGER.debug("getCategoriesFromCSV() >>> ");
        ArrayList<Category> categories = catalogDao.getCategoriesFromCSV();
        return categories;
    }

    private void validateCategory(Category category) {
        Assert.notNull(category, "Категория = null");
        Assert.notNull(category.getName(), "Имя категории = null");
        Assert.hasText(category.getName() ,"Пустое имя категории");
        Assert.notNull(category.getTranslitName(), "translitName = null");
        Assert.hasText(category.getTranslitName(), "translitName is blank");
    }
}
