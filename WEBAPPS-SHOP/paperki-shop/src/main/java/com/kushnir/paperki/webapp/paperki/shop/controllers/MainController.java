package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.sevice.CategoryBean;
import com.kushnir.paperki.sevice.ComponentBean;
import com.kushnir.paperki.sevice.MenuBean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    private static final Logger LOGGER = LogManager.getLogger(MainController.class);

    @Autowired
    ComponentBean componentBean;

    @Autowired
    CategoryBean categoryBean;

    @Autowired
    MenuBean mainMenuBean;

    @Value("${content.path}")
    String contentPath;

    @GetMapping()
    public String mainPage(Model model) {
        LOGGER.debug("mainPage");
        model.addAttribute("mainmenu", mainMenuBean.getAll());
        model.addAttribute("mapcategories", categoryBean.getAll());
        model.addAttribute("templatePathName", contentPath+"main");
        model.addAttribute("fragmentName", "main");
        LOGGER.debug("model initiated parameters: >>> {} ", model);
        return "index";
    }
    // главное меню
    @GetMapping("/{menuItem}")
    public String mainMenu(@PathVariable String menuItem, Model model) throws Exception {
        LOGGER.debug("getContent() >>> {}", menuItem);
        try{
            menuItem = mainMenuBean.getItemByTName(menuItem).getTranslitName();
        } catch (Exception e) {
            LOGGER.error("Пункт меню ({}) не найден: >>> {} ",menuItem , e.getMessage());
            return "redirect:error";
        }
        model.addAttribute("mapcategories", categoryBean.getAll());
        model.addAttribute("templatePathName", contentPath + menuItem);
        model.addAttribute("fragmentName", menuItem);
        LOGGER.debug("model initiated parameters: >>> {} ", model);
        return "index";
    }

    // остальные меню
    @GetMapping("/{menu}/{content}")
    public String menu(@PathVariable String menu, @PathVariable String content, Model model) throws Exception {
        LOGGER.debug("getContent() >>> {}", content);
        model.addAttribute("mapcategories", categoryBean.getAll());
        model.addAttribute("templatePathName", contentPath + content);
        model.addAttribute("fragmentName", content);
        LOGGER.debug("model initiated parameters: >>> {} ", model);
        return "index";
    }
}