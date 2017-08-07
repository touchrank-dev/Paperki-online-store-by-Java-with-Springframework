package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.sevice.CategoryBean;
import com.kushnir.paperki.sevice.ComponentBean;
import com.kushnir.paperki.sevice.MenuBean;
import com.kushnir.paperki.webapp.paperki.shop.exceptions.AppException;
import com.kushnir.paperki.webapp.paperki.shop.exceptions.PageNotFound;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


public class ExceptionController {

    private static final Logger LOGGER = LogManager.getLogger(ExceptionController.class);

    @Autowired
    ComponentBean componentBean;

    @Autowired
    CategoryBean categoryBean;

    @Autowired
    MenuBean menuBean;

    @Value("${content.path}")
    String contentPath;

    // @ExceptionHandler({PageNotFound.class,AppException.class,Exception.class})
    public String pageNotFoundHandler(Model model, HttpServletRequest req, Exception e) {
        LOGGER.error("Request: " + req.getRequestURL() + " raised " + e);
        model.addAttribute("mainmenu", menuBean.getAll("root"));
        model.addAttribute("mapcategories", categoryBean.getAll());
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("templatePathName", contentPath + "error");
        model.addAttribute("fragmentName", "error");
        LOGGER.debug("{}", model);
        return "index";
    }

}
