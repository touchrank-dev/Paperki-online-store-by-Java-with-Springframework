package com.kushnir.paperki.sevice;

import com.kushnir.paperki.dao.CategoryDao;
import com.kushnir.paperki.model.Category;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CategoryBeanImpl implements CategoryBean {

    private static final Logger LOGGER = LogManager.getLogger(CategoryBeanImpl.class);

    @Autowired
    CategoryDao categoryDao;

    @Override
    public HashMap<Integer, HashMap<Integer, Category>> getAll() {
        HashMap categories = categoryDao.getAll();
        LOGGER.debug("getAll() >>> {}", categories);
        return categories;
    }

    @Override
    public ArrayList<Category> getCategoriesFromCSV() throws IOException {
        LOGGER.debug("getCategoriesFromCSV() >>> ");
        return categoryDao.getCategoriesFromCSV();
    }
}
