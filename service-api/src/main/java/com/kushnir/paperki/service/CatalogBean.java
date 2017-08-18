package com.kushnir.paperki.service;

import com.kushnir.paperki.model.Category;
import com.kushnir.paperki.model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface CatalogBean {

    HashMap<Integer, HashMap<Integer, Category>> getAll();
    ArrayList<Category> getCategoriesFromCSV () throws IOException;
    Category getCategoryByTName (String categoryTName);
    HashMap<Integer ,Product> getProductsByCategoryTName(String categoryTName);
    Product getProductByCategoryTName (String categoryTName);
}
