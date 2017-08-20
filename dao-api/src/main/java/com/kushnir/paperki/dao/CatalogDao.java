package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.Category;
import com.kushnir.paperki.model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface CatalogDao {

    HashMap<Integer, HashMap<Integer, Category>> getAll();
    Category getCategoryByTName(String categoryTName);
    ArrayList<Category> getCategoriesFromCSV () throws IOException;
    HashMap<Integer ,Product> getProductListByCategoryTName(String categoryTName);
    Product getProductByTName(String productTName);
}
