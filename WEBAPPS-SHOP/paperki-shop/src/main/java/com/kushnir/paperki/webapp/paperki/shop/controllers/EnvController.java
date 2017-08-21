package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.model.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/env")
public class EnvController {

    private static final Logger LOGGER = LogManager.getLogger(EnvController.class);

    @GetMapping()
    public String envPage(HttpSession httpSession, Model model) {
        LOGGER.debug("envPage() >>>");
        model.addAttribute("user", (User)httpSession.getAttribute("user"));
        return "env";
    }

    @PostMapping()
    public String setCartItem(@RequestParam("name") String name,
                              Model model,
                              HttpSession httpSession) {
        User user = (User)httpSession.getAttribute("user");
        user.setName(name);
        httpSession.setAttribute("user", user);
        model.addAttribute("user", (User)httpSession.getAttribute("user"));
        return "env";
    }

}
