package com.kushnir.paperki.webapp.paperki.shop.controllers;


import com.kushnir.paperki.webapp.paperki.shop.session.Cart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/env")
@SessionAttributes("cart")
public class EnvController {

    private static final Logger LOGGER = LogManager.getLogger(EnvController.class);

    @GetMapping()
    public String envPage() {
        LOGGER.debug("envPage() >>>");
        return "env";
    }

}
