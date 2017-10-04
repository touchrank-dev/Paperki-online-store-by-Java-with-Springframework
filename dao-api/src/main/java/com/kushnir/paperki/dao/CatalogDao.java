package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.Category;
import com.kushnir.paperki.model.category.CategoryContainer;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface CatalogDao {

    HashMap<Integer, HashMap<Integer, Category>> getAll();
    Category getCategoryByTName(String categoryTName);
    HashMap<Integer, HashMap<Integer, Category>> getCategoriesFromCSV () throws IOException;
    HashMap<Integer, Category> getAllCategories();
    public int[] addCategories(Object[] categories);
    int[] addCategoriesRef(Object[] categories);
    CategoryContainer getCategoriesFromCSVToContainer() throws IOException, DataAccessException;
    CategoryContainer getCategoriesFromToContainer();
}
