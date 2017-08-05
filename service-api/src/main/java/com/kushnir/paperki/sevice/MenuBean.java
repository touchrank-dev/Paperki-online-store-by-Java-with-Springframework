package com.kushnir.paperki.sevice;

import com.kushnir.paperki.model.MenuItem;

import java.util.ArrayList;

public interface MenuBean {
    ArrayList<MenuItem> getAll();
    MenuItem getItemByTName(String translitName);
}
