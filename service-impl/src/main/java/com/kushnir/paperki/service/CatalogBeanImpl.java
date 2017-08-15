package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.CatalogDao;
import com.kushnir.paperki.model.Category;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CatalogBeanImpl implements CatalogBean {

    private static final Logger LOGGER = LogManager.getLogger(CatalogBeanImpl.class);

    @Autowired
    CatalogDao catalogDao;

    @Override
    public HashMap<Integer, HashMap<Integer, Category>> getAll() {
        HashMap categories = catalogDao.getAll();
        LOGGER.debug("getAll() >>> {}", categories);
        return categories;
    }

    @Override
    public ArrayList<Category> getCategoriesFromCSV() throws IOException {
        LOGGER.debug("getCategoriesFromCSV() >>> ");
        return catalogDao.getCategoriesFromCSV();
    }
}
