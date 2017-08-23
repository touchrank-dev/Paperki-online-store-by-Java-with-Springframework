package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.model.User;
import com.kushnir.paperki.service.CatalogBean;
import com.kushnir.paperki.service.MenuBean;
import com.kushnir.paperki.model.Cart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/profile")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    private static final String PROFILE_MENU_NAME = "profile";

    @Autowired
    CatalogBean catalogBean;

    @Autowired
    CatalogBean categoryBean;

    @Autowired
    MenuBean menuBean;

    @Value("${components.path}")
    String componentsPath;

    @Value("${content.path}")
    String contentPath;

    @GetMapping
    public String personalUserPage(Model model) {
        LOGGER.debug("personalUserPage() >>>");
        model.addAttribute("templatePathName", contentPath + PROFILE_MENU_NAME);
        model.addAttribute("fragmentName", PROFILE_MENU_NAME);
        return "index";
    }


    @ModelAttribute("mainmenu")
    public ArrayList getMainMenu () {
        return menuBean.getAll("root");
    }

    @ModelAttribute("mapcategories")
    public HashMap getCatalog () {
        return categoryBean.getAll();
    }

    @ModelAttribute("user")
    public User setUser(HttpSession httpSession) {
        User user = (User)httpSession.getAttribute("user");
        if(user == null) {
            user = new User();
            httpSession.setAttribute("user", user);
            LOGGER.debug("SET NEW EMPTY USER: {}", user);
        }
        return user;
    }

    @ModelAttribute("cart")
    public Cart setCart (HttpSession httpSession) {
        Cart cart = (Cart)httpSession.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            httpSession.setAttribute("cart", cart);
            LOGGER.debug("SET NEW EMPTY CART: {}", cart);
        }
        return cart;
    }

}
