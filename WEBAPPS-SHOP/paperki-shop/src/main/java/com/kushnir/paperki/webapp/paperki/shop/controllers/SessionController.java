package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.model.Cart;
import com.kushnir.paperki.model.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

@ControllerAdvice
public class SessionController {

    private static final Logger LOGGER = LogManager.getLogger(SessionController.class);

    @Value("${catalog.url}")
    String catalogURL;
    @Value("${product.image.path}")
    String productImagePath;
    @Value("${brand.image.path}")
    String brandImagePath;
    @Value("${product.image.prefix}")
    String imgPref;

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

    @ModelAttribute("pip")
    public String productImagePath() {
        return productImagePath;
    }

    @ModelAttribute("bip")
    public String brandImagePath() {
        return brandImagePath;
    }

    @ModelAttribute("catalogurl")
    public String catalogUrl() {
        return catalogURL;
    }

    @ModelAttribute("imgPref")
    public String imgPref () {
        return imgPref;
    }
}