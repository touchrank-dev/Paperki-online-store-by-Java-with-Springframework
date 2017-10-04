package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.Category;

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
}
