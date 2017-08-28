package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.model.MenuItem;
import com.kushnir.paperki.model.User;
import com.kushnir.paperki.service.CatalogBean;
import com.kushnir.paperki.service.MenuBean;

import com.kushnir.paperki.service.exceptions.ServiceException;
import com.kushnir.paperki.webapp.paperki.shop.exceptions.PageNotFound;

import com.kushnir.paperki.model.Cart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {

    private static final Logger LOGGER = LogManager.getLogger(MainController.class);
    private static final String MAIN_MENU_NAME = "main";

    @Autowired
    MenuBean menuBean;

    @Autowired
    CatalogBean catalogBean;

    @Value("${content.path}")
    String contentPath;

    // главная страница
    @GetMapping()
    public String mainPage(Model model) {
        model.addAttribute("templatePathName", contentPath + MAIN_MENU_NAME);
        model.addAttribute("fragmentName", MAIN_MENU_NAME);
        LOGGER.debug("mainPage() >>>");
        return "index";
    }

    // страницы главного меню
    @GetMapping("/{pageName}")
    public String mainMenu(@PathVariable String pageName, Model model) throws Exception {
        try {
            MenuItem menuItem = menuBean.getRootItem(pageName);
            pageName = menuItem.getTranslitName();
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
            throw new PageNotFound();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
        model.addAttribute("templatePathName", contentPath + pageName);
        model.addAttribute("fragmentName", pageName);
        LOGGER.debug("mainMenu(menuItem = {}) >>>", pageName);
        return "index";
    }

    // ================================================================================================================

    @ModelAttribute("mainmenu")
    public ArrayList getMainMenu () {
        return menuBean.getAll("root");
    }

    @ModelAttribute("mapcategories")
    public HashMap getCatalog () throws ServiceException {
        return catalogBean.getAll();
    }

}