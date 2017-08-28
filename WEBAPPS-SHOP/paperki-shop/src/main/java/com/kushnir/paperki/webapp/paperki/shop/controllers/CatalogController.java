package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.model.Category;
import com.kushnir.paperki.model.Product;
import com.kushnir.paperki.model.User;
import com.kushnir.paperki.service.CatalogBean;
import com.kushnir.paperki.service.MenuBean;
import com.kushnir.paperki.model.Cart;
import com.kushnir.paperki.service.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    private static final Logger LOGGER = LogManager.getLogger(CatalogController.class);
    private static final String CATALOG_MENU_NAME = "catalog";

    @Autowired
    CatalogBean catalogBean;

    @Autowired
    MenuBean menuBean;

    @Value("${content.path}")
    String contentPath;

    @GetMapping
    public String catalogPage(Model model) {
        LOGGER.debug("catalogPage() >>>");
        model.addAttribute("templatePathName", contentPath + CATALOG_MENU_NAME);
        model.addAttribute("fragmentName", CATALOG_MENU_NAME);
        return "index";
    }

    @GetMapping("/{catalorItemTranslitName}")
    public String catalogItemPage(@PathVariable String catalorItemTranslitName, Model model) throws ServiceException {
        LOGGER.debug("catalogItemPage() >>>");
        HashMap<Integer, Product> products = catalogBean.getProductsByCategoryTName(catalorItemTranslitName);
        Category category = catalogBean.getCategoryByTName(catalorItemTranslitName);
        model.addAttribute("products", products);
        model.addAttribute("category", category);
        model.addAttribute("templatePathName", contentPath + "product-list");
        model.addAttribute("fragmentName", "product-list");
        return "index";
    }

    @GetMapping("/{catalorItemTranslitName}/{productTranslitName}")
    public String productItemPage(@PathVariable String catalorItemTranslitName,
                                  @PathVariable String productTranslitName, Model model) throws ServiceException {
        LOGGER.debug("productItemPage() >>>");
        Product product = catalogBean.getProductByTName(productTranslitName);
        Category category = catalogBean.getCategoryByTName(catalorItemTranslitName);
        model.addAttribute("product", product);
        model.addAttribute("category", category);
        model.addAttribute("templatePathName", contentPath + "product-details");
        model.addAttribute("fragmentName", "product-details");
        return "index";
    }

    @ModelAttribute("mainmenu")
    public ArrayList getMainMenu () {
        return menuBean.getAll("root");
    }

    @ModelAttribute("mapcategories")
    public HashMap getCatalog () throws ServiceException {
        return catalogBean.getAll();
    }

}
