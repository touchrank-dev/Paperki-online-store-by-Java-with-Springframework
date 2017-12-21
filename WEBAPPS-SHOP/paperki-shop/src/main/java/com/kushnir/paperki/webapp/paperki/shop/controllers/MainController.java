package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.model.MenuItem;
import com.kushnir.paperki.service.CatalogBean;
import com.kushnir.paperki.service.ImageService;
import com.kushnir.paperki.service.MenuBean;
import com.kushnir.paperki.service.ProductBean;
import com.kushnir.paperki.service.exceptions.ServiceException;
import com.kushnir.paperki.webapp.paperki.shop.exceptions.PageNotFound;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.h2.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("/")
public class MainController {

    private static final Logger LOGGER = LogManager.getLogger(MainController.class);
    private static final String MAIN_MENU_NAME = "main";

    @Autowired
    MenuBean menuBean;

    @Autowired
    CatalogBean catalogBean;

    @Autowired
    ProductBean productBean;

    @Autowired
    ImageService imageService;

    @Value("${content.path}")
    String contentPath;

    // главная страница
    @GetMapping()
    public String mainPage(Model model) throws ServiceException {
        LOGGER.debug("mainPage() >>>");
        model.addAttribute("mainmenu", menuBean.getAll("root"));
        model.addAttribute("extraProducts", productBean.getAllExtraTypeProducts());
        model.addAttribute("templatePathName", contentPath + MAIN_MENU_NAME);
        model.addAttribute("fragmentName", MAIN_MENU_NAME);
        model.addAttribute("mapcategories", catalogBean.getAll());
        model.addAttribute("oldImages", imageService.getAllOldImages());
        model.addAttribute("title", "Главная страница");
        model.addAttribute("description", "Бумага канцелярские товары и офисные принадлежности с доставкой");
        return "index";
    }

    @GetMapping("/favicon.ico")
    public void favicon (HttpServletResponse response) throws IOException, PageNotFound {
        LOGGER.debug("favicon()");

        try {
            getFile("/papsource/WEBAPPS-SHOP/paperki-shop/src/main/webapp/WEB-INF/view/resources/img/favicons/favicon.ico",
                    response);
        } catch (Exception e) {
            LOGGER.error("ERROR: ", e);
            throw new PageNotFound();
        }
    }

    @GetMapping("/robots.txt")
    public void robots (HttpServletResponse response) throws PageNotFound {
        LOGGER.debug("robots()");
        try {
            getFile("/papsource/WEBAPPS-SHOP/paperki-shop/src/main/webapp/WEB-INF/view/templates/robots.txt",
                    response);
        } catch (Exception e) {
            LOGGER.error("ERROR: ", e);
            throw new PageNotFound();
        }
    }

    private void getFile(String filePathToBeServed, HttpServletResponse response) throws IOException {
        InputStream is = new FileInputStream(new File(filePathToBeServed));
        IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
        is.close();
    }

    // страницы главного меню
    @GetMapping("/{pageName}")
    public String mainMenu(@PathVariable String pageName, Model model) throws Exception {
        LOGGER.debug("mainMenu({})", pageName);
        MenuItem menuItem;
        try {
            menuItem = menuBean.getRootItem(pageName);
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

        model.addAttribute("title", menuItem.getName());

        model.addAttribute("mainmenu", menuBean.getAll("root"));
        model.addAttribute("mapcategories", catalogBean.getAll());
        model.addAttribute("oldImages", imageService.getAllOldImages());
        return "index";
    }

    @GetMapping({"/katalog", "/callback"})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void badRequest () {

    }

}