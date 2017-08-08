package com.kushnir.paperki.webapp.paperki.shop.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class ErrorController {

    private static final Logger LOGGER = LogManager.getLogger(ErrorController.class);

    @GetMapping()
    public void errorPage() {
        LOGGER.debug("error() >>>");
    }

}

