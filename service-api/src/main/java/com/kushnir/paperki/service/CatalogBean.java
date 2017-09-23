package com.kushnir.paperki.service;

import com.kushnir.paperki.model.Category;
import com.kushnir.paperki.model.product.Product;
import com.kushnir.paperki.service.exceptions.ServiceException;

import java.io.IOException;
import java.util.HashMap;

public interface CatalogBean {

    HashMap<Integer, HashMap<Integer, Category>> getAll() throws ServiceException;
    Category getCategoryByTName (String categoryTName) throws ServiceException;
    HashMap<Integer ,Product> getProductsByCategoryTName(String categoryTName) throws ServiceException;
    Product getProductByTName (String productTName) throws ServiceException;
    void updateCatalog() throws ServiceException, IOException;
}
