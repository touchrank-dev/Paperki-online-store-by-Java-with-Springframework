package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.Category;

import java.util.ArrayList;
import java.util.HashMap;

public interface CategoryDao {
    HashMap <Integer, Category> getAll();
}
