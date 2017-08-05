package com.kushnir.paperki.sevice;

import com.kushnir.paperki.model.Category;

import java.util.HashMap;

public interface CategoryBean {

    HashMap<Integer, HashMap<Integer, Category>> getAll();
}
