package com.kushnir.paperki.webapp.paperki.shop.controllers.catalog;

import com.kushnir.paperki.model.Category;
import com.kushnir.paperki.model.product.Product;
import com.kushnir.paperki.service.CatalogBean;
import com.kushnir.paperki.service.ImageService;
import com.kushnir.paperki.service.MenuBean;
import com.kushnir.paperki.service.exceptions.ServiceException;

import com.kushnir.paperki.webapp.paperki.shop.exceptions.PageNotFound;
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

    @Autowired
    ImageService imageService;

    @Value("${content.path}")
    String contentPath;

    @GetMapping
    public String catalogPage(Model model) {
        LOGGER.debug("catalogPage() >>>");
        model.addAttribute("templatePathName", contentPath + CATALOG_MENU_NAME);
        model.addAttribute("fragmentName", CATALOG_MENU_NAME);

        model.addAttribute("title", "Каталог");

        return "index";
    }

    @GetMapping("/{catalogItemTranslitName}")
    public String catalogItemPage(@PathVariable String catalogItemTranslitName,
                                  HttpSession session, Model model) throws ServiceException, PageNotFound {
        LOGGER.debug("catalogItemPage() >>>");

        Integer type =      session.getAttribute("catview") == null ? 1:(Integer)session.getAttribute("catview");
        Integer sortType =  session.getAttribute("sortedby") == null ? 1:(Integer)session.getAttribute("sortedby");

        try {
            if (type == null || type == 1){
                model.addAttribute("products", catalogBean.getProductsByCategoryTName(catalogItemTranslitName, sortType));
                model.addAttribute("templatePathName", contentPath + "product-list");
                model.addAttribute("fragmentName", "product-list");
            } else if (type == 2) {
                model.addAttribute("products", catalogBean.getProductsByCategoryTName(catalogItemTranslitName, sortType));
                model.addAttribute("templatePathName", contentPath + "product-list-row");
                model.addAttribute("fragmentName", "product-list-row");
            } else if (type == 3) {
                model.addAttribute("products", catalogBean.getProductsByGroupView(catalogItemTranslitName, sortType));
                model.addAttribute("templatePathName", contentPath + "product-list-group");
                model.addAttribute("fragmentName", "product-list-group");
            }

            Category category = catalogBean.getCategoryByTName(catalogItemTranslitName);
            model.addAttribute("category", category);

            model.addAttribute("title", category.getName());

        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
            throw new PageNotFound();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }

        return "index";
    }

    @GetMapping("/{catalogItemTranslitName}/{productTranslitName}")
    public String productItemPage(@PathVariable String catalogItemTranslitName,
                                  @PathVariable String productTranslitName,
                                  Model model) throws ServiceException, PageNotFound {
        LOGGER.debug("productItemPage() >>>");
        try {

            Integer pnt = Integer.parseInt(productTranslitName.split("-")[0]);

            Product product = catalogBean.getProductByPNT(pnt);
            model.addAttribute("title", product.getFullName());
            model.addAttribute("product", product);
            model.addAttribute("category", catalogBean.getCategoryByTName(catalogItemTranslitName));

            model.addAttribute("templatePathName", contentPath + "product-details");
            model.addAttribute("fragmentName", "product-details");

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new PageNotFound();
        }
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

    @ModelAttribute("catview")
    public Integer viewType(HttpSession session) {
        Integer type = (Integer)session.getAttribute("catview");
        return type == null ? 1:type;
    }

    @ModelAttribute("sortedby")
    public Integer sortType(HttpSession session) {
        Integer type = (Integer)session.getAttribute("sortedby");
        return type == null ? 1:type;
    }


    @ModelAttribute("oldImages")
    public HashMap<Integer, ArrayList<String>> getOldImages() {
        LOGGER.debug("getOldImages()");
        HashMap<Integer, ArrayList<String>> oldImages = imageService.getAllOldImages();
        return oldImages;
    }


}
