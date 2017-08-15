package com.kushnir.paperki.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

public class ComponentBeanImpl implements ComponentBean {

    private static final Logger LOGGER = LogManager.getLogger(ComponentBeanImpl.class);

    @Autowired
    CatalogBean categoryBean;

    @Override
    public void initComponents(Model model) {
        model.addAttribute("test", "test");
    }
}
