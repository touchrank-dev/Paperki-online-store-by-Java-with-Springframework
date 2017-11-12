package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.model.Cart;
import com.kushnir.paperki.model.user.User;

import com.kushnir.paperki.service.ImageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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

    @Autowired
    ImageService imageService;

    @ModelAttribute("user")
    public User setUser(HttpSession httpSession, HttpServletRequest req) {
        User user = (User)httpSession.getAttribute("user");
        if(user == null) {
            user = new User();
            httpSession.setAttribute("user", user);
            LOGGER.debug("SET NEW EMPTY USER");
        }
        LOGGER.debug("SessionId: {}, RequestedSessionID: {}, isValid: {}, isFromCookie: {}",
                httpSession.getId(),
                req.getRequestedSessionId(),
                req.isRequestedSessionIdValid(),
                req.isRequestedSessionIdFromCookie());
        return user;
    }

    @ModelAttribute("cart")
    public Cart setCart (HttpSession httpSession) {
        Cart cart = (Cart)httpSession.getAttribute("cart");
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

    @ModelAttribute("ProductImages")
    public HashMap<Integer, String> imageFinder() {
        LOGGER.debug("imageFinder()");

        HashMap<Integer, String> catImages = new HashMap<>();
        try {
            File[] files = new File("/home/paperki.by/www/images/catalog").listFiles();

            for (File file : files) {
                if (file.isFile()) {
                    try {
                        String fileName = file.getName();
                        Integer pnt = Integer.parseInt(fileName.replaceAll(".jpg", ""));

                        catImages.put(pnt, fileName);

                    } catch (Exception e) {

                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return catImages;
    }

    /*@ModelAttribute("oldImages")
    public HashMap<Integer, ArrayList<String>> getOldImages() {
        LOGGER.debug("getOldImages()");
        HashMap<Integer, ArrayList<String>> oldImages = imageService.getAllOldImages();
        // LOGGER.debug("OLD IMAGES:\n{}", oldImages);
        return oldImages;
    }*/

}
