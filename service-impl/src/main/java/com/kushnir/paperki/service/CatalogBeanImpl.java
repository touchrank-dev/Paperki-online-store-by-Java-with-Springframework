package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.CatalogDao;
import com.kushnir.paperki.model.Category;

import com.kushnir.paperki.model.product.Product;
import com.kushnir.paperki.service.exceptions.ServiceException;
import com.kushnir.paperki.service.util.Transliterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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

        ArrayList<Category> CSVcategories = getCategoriesFromCSV();
        HashMap<String, Category> categories = getAllCategories();

        if(CSVcategories != null && categories != null) {
            for (Category CSVCategory : CSVcategories) {
                try {
                    String translitName = Transliterator.cyr2lat(CSVCategory.getName());
                    CSVCategory.setTranslitName(translitName);

                    Category category = categories.get(translitName);
                    if (category == null) {
                        //TODO add
                        addCategory(CSVCategory);
                        sb.append("Категория добавлена: ").append(CSVCategory.getName()).append('\n');
                    } else {
                        //TODO update
                        updateCategory(CSVCategory);
                        sb.append("Категория обновлена: ").append(CSVCategory.getName()).append('\n');
                    }
                } catch (Exception e) {
                    sb.append("Ошибка обновления категории: ").append(e).append(" MSG >>> ").append(e.getMessage()).append('\n');
                }
            }
        }
        return sb.toString();
    }

    @Override
    public int addCategory(Category category) {
        return 0;
    }

    @Override
    public int updateCategory(Category category) {
        return 0;
    }

    private ArrayList<Category> getCategoriesFromCSV() throws IOException, ServiceException {
        LOGGER.debug("getCategoriesFromCSV() >>> ");
        ArrayList<Category> categories = catalogDao.getCategoriesFromCSV();
        return categories;
    }
}
