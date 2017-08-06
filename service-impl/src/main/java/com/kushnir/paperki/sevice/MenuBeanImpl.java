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
    private MenuDao menuDao;

    @Override
    public ArrayList<MenuItem> getAll(String nameMenu) {
        LOGGER.debug("getAll(nameMenu = {}) >>>", nameMenu);
        ArrayList<MenuItem> menuItems = menuDao.getAll(nameMenu);
        LOGGER.debug("{}", menuItems);
        return menuItems;
    }

    @Override
    public MenuItem getRootItem(String itemTName) {
        LOGGER.debug("getRootItem(itemTName = {}) >>>", itemTName);
        MenuItem menuItem = menuDao.getItemByTName("root", itemTName);
        LOGGER.debug("{}", menuItem);
        return menuItem;
    }

    @Override
    public MenuItem getItemByTName(String nameMenu, String itemTName) {
        LOGGER.debug("getItemByTName(nameMenu = {}, itemTName = {}) >>>", nameMenu, itemTName);
        MenuItem menuItem = menuDao.getItemByTName(nameMenu, itemTName);
        LOGGER.debug("{}", menuItem);
        return menuItem;
    }
}
