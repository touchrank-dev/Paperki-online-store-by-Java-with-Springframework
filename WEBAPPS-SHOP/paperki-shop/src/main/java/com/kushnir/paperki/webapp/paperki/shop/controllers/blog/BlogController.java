package com.kushnir.paperki.webapp.paperki.shop.controllers.blog;

import com.kushnir.paperki.service.CatalogBean;
import com.kushnir.paperki.service.MenuBean;
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

import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private static final Logger LOGGER = LogManager.getLogger(BlogController.class);
    private static final String ARTICLE_FOLDER = "blog/";
    private static final String MENU_NAME = "blog";

    @Autowired
    CatalogBean catalogBean;

    @Autowired
    MenuBean menuBean;

    @Value("${content.path}")
    String contentPath;

    @GetMapping
    public String blogPage(Model model) {
        LOGGER.debug("helpPage() >>>");
        model.addAttribute("templatePathName", contentPath + MENU_NAME);
        model.addAttribute("fragmentName", MENU_NAME);

        model.addAttribute("title", "Блог");

        return "index";
    }

    @GetMapping("/{articleName}")
    public String article(@PathVariable String articleName, Model model) throws ServiceException {
        LOGGER.debug("article() >>>");
        model.addAttribute("templatePathName", contentPath + ARTICLE_FOLDER + articleName);
        model.addAttribute("fragmentName", "article");

        model.addAttribute("title", "Блог");

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
