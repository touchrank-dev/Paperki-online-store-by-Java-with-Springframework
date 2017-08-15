package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.model.User;
import com.kushnir.paperki.service.CatalogBean;
import com.kushnir.paperki.service.MenuBean;
import com.kushnir.paperki.webapp.paperki.shop.session.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

@ControllerAdvice
public class ModelSetter {

    @Autowired
    MenuBean menuBean;

    @Autowired
    CatalogBean categoryBean;


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
        if(user == null) user = new User();
        return user;
    }

    @ModelAttribute("cart")
    public Cart setCart (HttpSession httpSession) {
        Cart cart = (Cart)httpSession.getAttribute("cart");
        if (cart == null) cart = new Cart();
        return cart;
    }
}
