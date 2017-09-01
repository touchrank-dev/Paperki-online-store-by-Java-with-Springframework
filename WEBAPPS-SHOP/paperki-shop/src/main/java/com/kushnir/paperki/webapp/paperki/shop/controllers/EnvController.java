package com.kushnir.paperki.webapp.paperki.shop.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/env")
public class EnvController {

    private static final Logger LOGGER = LogManager.getLogger(EnvController.class);

    @GetMapping()
    public String envPage() {
        LOGGER.debug("envPage() >>>");
        return "env";
    }

}
