package com.kushnir.paperki.sevice;

import com.kushnir.paperki.dao.MenuDao;
import com.kushnir.paperki.model.MenuItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class MenuBeanImpl implements MenuBean {

    private static final Logger LOGGER = LogManager.getLogger(MenuBeanImpl.class);

    @Autowired
    private MenuDao mainMenuDao;

    @Override
    public ArrayList<MenuItem> getAll() {
        ArrayList<MenuItem> menuItems = mainMenuDao.getAll();
        LOGGER.debug("getAll() >>> {}", menuItems);
        return menuItems;
    }

    @Override
    public MenuItem getItemByTName(String translitName) {
        MenuItem menuItem = mainMenuDao.getItemByTName(translitName);
        LOGGER.debug("getItemByTName() >>> {}", menuItem);
        return menuItem;
    }
}
