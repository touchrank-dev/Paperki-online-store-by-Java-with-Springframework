package com.kushnir.paperki.service;

import com.kushnir.paperki.model.Category;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface CatalogBean {

    HashMap<Integer, HashMap<Integer, Category>> getAll();
    ArrayList<Category> getCategoriesFromCSV () throws IOException;
}