package com.kushnir.paperki.webapp.paperki.shop.controllers.knowledgebase;

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
@RequestMapping("/knowledgebase")
public class KnowledgeBaseController {

    private static final Logger LOGGER = LogManager.getLogger(KnowledgeBaseController.class);
    private static final String ARTICLE_FOLDER = "knowledgebase/";
    private static final String MENU_NAME = "knowledgebase";

    @Autowired
    CatalogBean catalogBean;

    @Autowired
    MenuBean menuBean;

    @Value("${content.path}")
    String contentPath;

    @GetMapping
    public String knowledgeBasePage(Model model) {
        LOGGER.debug("knowledgeBasePage() >>>");
        model.addAttribute("templatePathName", contentPath + MENU_NAME);
        model.addAttribute("fragmentName", MENU_NAME);
        return "index";
    }

    @GetMapping("/{articleName}")
    public String article(@PathVariable String articleName, Model model) throws ServiceException {
        LOGGER.debug("article() >>>");
        model.addAttribute("templatePathName", contentPath + ARTICLE_FOLDER + articleName);
        model.addAttribute("fragmentName", "article");
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
