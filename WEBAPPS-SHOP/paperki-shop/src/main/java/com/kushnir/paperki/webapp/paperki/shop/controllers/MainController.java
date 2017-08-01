package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.sevice.ComponentBean;

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

    @Autowired
    ComponentBean componentBean;

    @Value("${content.path}")
    String contentPath;

    @GetMapping()
    public String mainPage(Model model) {
        componentBean.initComponents(model);
        model.addAttribute("templatePathName", contentPath+"main");
        model.addAttribute("fragmentName", "main");
        return "index";
    }

    @GetMapping("/{content}")
    public String getContent(@PathVariable String content, Model model) {
        model.addAttribute("templatePathName", contentPath + content);
        model.addAttribute("fragmentName", content);
        return "index";
    }

}