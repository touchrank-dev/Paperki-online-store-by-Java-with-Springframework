package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.model.product.Product;
import com.kushnir.paperki.service.CatalogBean;
import com.kushnir.paperki.service.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/env")
public class EnvController {

    @Autowired
    CatalogBean catalogBean;

    private static final Logger LOGGER = LogManager.getLogger(EnvController.class);

    @GetMapping()
    public String envPage() {
        LOGGER.debug("envPage() >>>");
        return "env";
    }

    @ModelAttribute("product")
    public Product getTestProduct() throws ServiceException {
        return null;
    }

}
