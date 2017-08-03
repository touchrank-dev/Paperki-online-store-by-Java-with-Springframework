package com.kushnir.paperki.sevice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.util.HashMap;

public class ComponentBeanImpl implements ComponentBean {

    private static final Logger LOGGER = LogManager.getLogger(ComponentBeanImpl.class);

    @Autowired
    CategoryBean categoryBean;

    @Override
    public void initComponents(Model model) {
        HashMap mapCategories = categoryBean.getAll();
        model.addAttribute("mapCategories", mapCategories);
    }
}
