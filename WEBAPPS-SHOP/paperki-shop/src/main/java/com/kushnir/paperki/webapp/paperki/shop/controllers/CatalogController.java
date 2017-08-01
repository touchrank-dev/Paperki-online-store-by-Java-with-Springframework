package com.kushnir.paperki.webapp.paperki.shop.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @Value("${components.path}")
    String componentsPath;

    @GetMapping()
    public String catalogRoot(Model model) {

        return "catalog";
    }

}
