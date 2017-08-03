package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.sevice.CategoryBean;
import com.kushnir.paperki.sevice.ComponentBean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {

    private static final Logger LOGGER = LogManager.getLogger(MainController.class);

    @Autowired
    ComponentBean componentBean;

    @Autowired
    CategoryBean categoryBean;

    @Value("${content.path}")
    String contentPath;

    @GetMapping()
    public String mainPage(Model model) {
        LOGGER.debug("mainPage");
        model.addAttribute("mapcategories", categoryBean.getAll());
        model.addAttribute("templatePathName", contentPath+"main");
        model.addAttribute("fragmentName", "main");
        LOGGER.debug("model initiated parameters: >>> {} ", model);
        return "index";
    }

    /*@GetMapping("/{content}")
    public String getContent(@PathVariable String content, Model model) {
        LOGGER.debug("getContent() >>> {}", content);
        model.addAttribute("templatePathName", contentPath + content);
        model.addAttribute("fragmentName", content);
        LOGGER.debug("model initiated parameters: >>> {} ", model);
        return "index";
    }*/

}