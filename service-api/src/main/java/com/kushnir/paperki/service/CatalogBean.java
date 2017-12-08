package com.kushnir.paperki.service;

import com.kushnir.paperki.model.Category;
import com.kushnir.paperki.model.category.CategoryContainer;
import com.kushnir.paperki.model.category.CategorySimple;
import com.kushnir.paperki.model.product.Product;
import com.kushnir.paperki.service.exceptions.ServiceException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface CatalogBean {

    HashMap<Integer, HashMap<Integer, Category>> getAll() throws ServiceException;
    HashMap<Integer, CategorySimple> getAllChildrenWithPapIdKey();
    Category getCategoryByTName (String categoryTName) throws ServiceException;
    HashMap<Integer ,Product> getProductsByCategoryTName(String categoryTName, Integer sortType) throws ServiceException;
    HashMap<String, List<Product>> getProductsByGroupView(String categoryTName, Integer sortType) throws ServiceException;
    Product getProductByTName (String productTName) throws ServiceException;
    Product getProductByPNT (Integer pnt) throws ServiceException;
    String updateCatalog() throws ServiceException, IOException;
    CategoryContainer getCategoriesToContainer();
    int addCategory(Category category);
    int addRefCategory(Category category);
    int[] addCategories(Object[] categories);
    int[] addCategoriesRef(Object[] categories);
    int updateCategory(Category category) throws Exception;
    int updateCategoryRef(Category category) throws Exception;
    String updateCategories(Object[] categories);
    String updateCategoriesRef(Object[] categories);
}
