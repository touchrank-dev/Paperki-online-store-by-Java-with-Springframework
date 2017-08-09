package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.sevice.CategoryBean;
import com.kushnir.paperki.sevice.MenuBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    private static final Logger LOGGER = LogManager.getLogger(CatalogController.class);
    private static final String CATALOG_MENU_NAME = "catalog";

    @Autowired
    CategoryBean categoryBean;

    @Autowired
    MenuBean menuBean;

    @Value("${components.path}")
    String componentsPath;

    @Value("${content.path}")
    String contentPath;

    @GetMapping()
    public String catalogPage(Model model) {
        LOGGER.debug("catalogPage() >>>");
        model.addAttribute("mainmenu", menuBean.getAll("root"));
        model.addAttribute("mapcategories", categoryBean.getAll());
        model.addAttribute("templatePathName", contentPath + CATALOG_MENU_NAME);
        model.addAttribute("fragmentName", CATALOG_MENU_NAME);
        return "index";
    }

}
