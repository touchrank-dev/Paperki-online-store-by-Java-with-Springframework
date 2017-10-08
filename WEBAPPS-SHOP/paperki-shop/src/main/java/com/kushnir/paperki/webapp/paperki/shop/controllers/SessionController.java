package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.model.Cart;
import com.kushnir.paperki.model.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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

    @ModelAttribute("ProductImages")
    public HashMap<Integer, String> imageFinder() {
        HashMap<Integer, String> catImages = new HashMap<>();
        try {
            File[] files = new File("/home/paperki.by/www/images/catalog").listFiles();

            for (File file : files) {
                if (file.isFile()) {
                    try {
                        String fileName = file.getName();
                        Integer pnt = Integer.parseInt(fileName.replaceAll(".jpg", ""));

                        catImages.put(pnt, fileName);

                        /*ArrayList<String> imgList = catImages.get(pnt);
                        if (imgList != null) {
                            imgList.add(fileName);
                            Collections.sort(imgList);
                        } else {
                            imgList = new ArrayList<String>();
                            imgList.add(fileName);
                            catImages.put(pnt, imgList);
                        }*/

                    } catch (Exception e) {

                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return catImages;
    }

}
