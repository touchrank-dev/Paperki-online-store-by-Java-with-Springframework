package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.MenuDao;
import com.kushnir.paperki.model.MenuItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;

@Service
@Transactional
public class MenuBeanImpl implements MenuBean {

    private static final Logger LOGGER = LogManager.getLogger(MenuBeanImpl.class);

    @Autowired
    private MenuDao menuDao;

    @Override
    public ArrayList<MenuItem> getAll(String nameMenu) {
        ArrayList<MenuItem> menuItems = menuDao.getAll(nameMenu);
        return menuItems;
    }

    @Override
    public MenuItem getRootItem(String itemTName) {
        Assert.notNull(itemTName, "Передан пустой параметр названия запрашиваемой страницы");
        LOGGER.debug("getRootItem(itemTName = {}) >>>", itemTName);
        MenuItem menuItem = menuDao.getItemByTName("root", itemTName);
        Assert.notNull(menuItem, "Запрашиваемая страница ("+itemTName+") не найдена!");
        Assert.notNull(menuItem.getTranslitName(), "Запрашиваемая страница ("+itemTName+") не найдена!");
        return menuItem;
    }

    @Override
    public MenuItem getItemByTName(String nameMenu, String itemTName) {
        LOGGER.debug("getItemByTName(nameMenu = {}, itemTName = {}) >>>", nameMenu, itemTName);
        MenuItem menuItem = menuDao.getItemByTName(nameMenu, itemTName);
        return menuItem;
    }
}
