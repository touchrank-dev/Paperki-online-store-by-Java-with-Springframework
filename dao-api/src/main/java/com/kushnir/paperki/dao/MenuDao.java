package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.MenuItem;

import java.util.ArrayList;

public interface MenuDao {
    ArrayList<MenuItem> getAll(String nameMenu);
    MenuItem getItemByTName(String nameMenu, String translitName);
}
