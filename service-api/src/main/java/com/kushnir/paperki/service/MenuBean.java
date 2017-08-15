package com.kushnir.paperki.service;

import com.kushnir.paperki.model.MenuItem;

import java.util.ArrayList;

public interface MenuBean {
    ArrayList<MenuItem> getAll(String nameMenu);
    MenuItem getRootItem(String translitName);
    MenuItem getItemByTName(String nameMenu, String translitName);
}
