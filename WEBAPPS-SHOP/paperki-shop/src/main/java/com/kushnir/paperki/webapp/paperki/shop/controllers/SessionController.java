package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.model.Cart;
import com.kushnir.paperki.model.user.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

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
    public User setUser(HttpSession httpSession, HttpServletRequest req) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            user = new User();
            httpSession.setAttribute("user", user);
            LOGGER.debug("SET NEW EMPTY USER");
        }
        LOGGER.info("\n>>> requested_session_Id: {}, http_session_id: {} isValid: {}, isNew {}, headers: {}",
                    req.getRequestedSessionId(),
                    httpSession.getId(),
                    req.isRequestedSessionIdValid(),
                    httpSession.isNew(),
                    printHeaders(req.getHeaderNames(), req));
        return user;
    }

    @ModelAttribute("cart")
    public Cart setCart (HttpSession httpSession, HttpServletRequest req) {
        Cart cart = (Cart) httpSession.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            httpSession.setAttribute("cart", cart);
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

    private String printHeaders(Enumeration<String> headers, HttpServletRequest req) {
        StringBuilder sb = new StringBuilder('\n');
        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            sb.append(header).append(" - ").append(req.getHeader(header)).append('\n');
        }
        return sb.toString();
    }

}
