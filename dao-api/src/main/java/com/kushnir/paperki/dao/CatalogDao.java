package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.Category;
import com.kushnir.paperki.model.category.CategoryContainer;
import com.kushnir.paperki.model.category.CategorySimple;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface CatalogDao {

    HashMap<Integer, HashMap<Integer, Category>> getAll();
    HashMap<Integer, Category> getAllCategories();
    HashMap<Integer, CategorySimple> getAllChildrenWithPapIdKey();
    Category getCategoryByTName(String categoryTName);
    int[] addCategories(Object[] categories);
    int[] addCategoriesRef(Object[] categories);
    HashMap<Integer, Category> getAllCSVCategories(StringBuilder sb) throws IOException, DataAccessException;
    int addCategory(Category category);
    int addRefCategory(Category category);
    int[] updateCategories(Object[] categories);
    int[] updateCategoriesRef(Object[] categories);
}
