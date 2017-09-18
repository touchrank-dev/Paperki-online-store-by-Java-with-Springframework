package com.kushnir.paperki.webapp.paperki.shop.controllers.order;

import com.kushnir.paperki.service.CatalogBean;
import com.kushnir.paperki.service.DeliveryService;
import com.kushnir.paperki.service.MenuBean;
import com.kushnir.paperki.service.exceptions.ServiceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final Logger LOGGER = LogManager.getLogger(OrderController.class);

    @Autowired
    MenuBean menuBean;

    @Autowired
    CatalogBean catalogBean;

    @Autowired
    DeliveryService deliveryService;

    @Value("${content.path}")
    String contentPath;

    @GetMapping
    public String orderPage(Model model) {
        LOGGER.debug("orderPage() >>>");
        model.addAttribute("templatePathName", contentPath + "order");
        model.addAttribute("fragmentName", "order");
        model.addAttribute("deliveries", deliveryService.getAll());
        return "index";
    }

    @GetMapping("/{token}")
    public String orderByToken(@PathVariable String token, Model model) {
        LOGGER.debug("orderByToken({}) >>>", token);
        model.addAttribute("templatePathName", contentPath + "order-details");
        model.addAttribute("fragmentName", "order-details");
        return "index";
    }

    @ModelAttribute("mainmenu")
    public ArrayList getMainMenu () {
        return menuBean.getAll("root");
    }

    @ModelAttribute("mapcategories")
    public HashMap getCatalog () throws ServiceException {
        return catalogBean.getAll();
    }

}
