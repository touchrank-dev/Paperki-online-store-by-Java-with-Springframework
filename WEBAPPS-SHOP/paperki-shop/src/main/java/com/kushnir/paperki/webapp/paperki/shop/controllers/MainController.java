package com.kushnir.paperki.webapp.paperki.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping()
    public String mainRoot(Model model) {
        return "index";
    }

    @GetMapping("/about")
    public String aboutRoot(Model model) {
        return "about";
    }

}