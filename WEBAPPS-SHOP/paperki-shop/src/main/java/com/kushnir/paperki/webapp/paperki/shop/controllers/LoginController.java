package com.kushnir.paperki.webapp.paperki.shop.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    @GetMapping("/login")
    public String loginPage() {
        LOGGER.debug("loginPage() >>>");
        return "components/login/login";
    }

    @PostMapping("/login")
    public String postLoginPage() {
        LOGGER.debug("loginPage() >>>");
        return "components/login/login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        LOGGER.debug("logoutPage() >>>");
        return "components/login/logout";
    }

    @GetMapping("/faillogin")
    public String failloginPage(Model model) {
        LOGGER.debug("failloginPage() >>>");
        model.addAttribute("loginError", true);
        return "components/login/login";
    }
}
