package com.kushnir.paperki.webapp.paperki.shop.controllers.cart;

import com.kushnir.paperki.model.AddProductRequest;
import com.kushnir.paperki.model.Cart;
import com.kushnir.paperki.model.DeleteRequest;
import com.kushnir.paperki.model.RestMessage;
import com.kushnir.paperki.service.CartBean;
import com.kushnir.paperki.service.exceptions.NotEnoughQuantityAvailableException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping("/api/cart")
public class RESTCart {

    private static final Logger LOGGER = LogManager.getLogger(RESTCart.class);

    @Autowired
    CartBean cartBean;

    // curl -H "Content-Type: application/json" -d '{"pnt":9491,"quantity":1}' -v localhost:8080/api/addtocart
    @PostMapping("/addtocart")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    RestMessage addToCart(@RequestBody AddProductRequest addProductRequest,
                          HttpSession httpSession) {
        LOGGER.debug("REST ADD TO CART >>>\nREQUEST DATA: {}", addProductRequest);
        RestMessage restMessage;
        try {
            Cart cart = (Cart)httpSession.getAttribute("cart");
            // LOGGER.debug("CART FROM SESSION BEFORE ADDING: {}", cart);

            cartBean.addToCart(cart, addProductRequest);

            // LOGGER.debug("CART AFTER ADDING: {}", cart);
            httpSession.setAttribute("cart", cart);
            restMessage = new RestMessage(HttpStatus.OK, "ADDED TO CART", cart);
            return restMessage;
        }catch (NotEnoughQuantityAvailableException e) {
            restMessage = new RestMessage(HttpStatus.NOT_FOUND, e.getMessage(), null);
            return restMessage;
        } catch (Exception e) {
            LOGGER.error("FAILED ADD PRODUCT TO CART >>>\nERROR MESSAGE: {}", e.getMessage());
            restMessage = new RestMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
            return restMessage;
        }
    }

    @PostMapping("/deletefromcart")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage deleteFromCart(@RequestBody DeleteRequest pnt, HttpSession httpSession) {
        LOGGER.debug("REST DELETE PRODUCT FROM CART >>>\nREQUEST PNT: {}", pnt);
        RestMessage restMessage;
        try{
            Cart cart = (Cart)httpSession.getAttribute("cart");
            // LOGGER.debug("CART FROM SESSION BEFORE DELETE: {}", cart);

            cartBean.deleteFromCart(cart, pnt.getPnt());

            // LOGGER.debug("CART AFTER DELETE: {}", cart);
            httpSession.setAttribute("cart", cart);
            restMessage = new RestMessage(HttpStatus.OK, "PRODUCT SUCCESSFULLY DELETED", cart);
            return restMessage;
        } catch (Exception e) {
            LOGGER.error("FAILED DELETE FROM CART >>>\nERROR MESSAGE: {}", e.getMessage());
            restMessage = new RestMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
            return restMessage;
        }
    }
}
