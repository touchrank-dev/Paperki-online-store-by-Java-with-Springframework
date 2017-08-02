package com.kushnir.paperki.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

public class ComponentBeanImpl implements ComponentBean {

    @Autowired
    CategoryBean categoryBean;

    @Override
    public void initComponents(Model model) {
        model.addAttribute("mapCategories", categoryBean.getAll());
    }
}
