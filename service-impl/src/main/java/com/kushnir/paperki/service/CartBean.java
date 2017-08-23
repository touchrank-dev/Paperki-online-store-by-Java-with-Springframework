package com.kushnir.paperki.service;

import com.kushnir.paperki.model.AddProductRequest;
import com.kushnir.paperki.model.Cart;
import com.kushnir.paperki.model.CartProduct;
import com.kushnir.paperki.model.Product;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public class CartBean {

    private static final Logger LOGGER = LogManager.getLogger(CartBean.class);

    @Autowired
    ProductBean productBean;

    public void addToCart (Cart cart, AddProductRequest addProductRequest) {
        LOGGER.debug("addToCart() >>>\nCART:{}\nADDPRODUCT: {}", cart, addProductRequest);
        CartProduct cartProduct = productBean.getCartProductByPNT(addProductRequest.getPnt());
        LOGGER.debug("CART PRODUCT: {}", cartProduct);


        /*CartProduct cartProduct = productBean.getCartProductByPNT(addProductRequest.getPnt());
        HashMap<Integer, CartProduct> items = cart.getItems();
        if(items != null) {
            if (items.get(cartProduct.getPnt()) == null) {
                items.put(cartProduct.getPnt(), cartProduct);
            } else {
                CartProduct pc = items.get(cartProduct.getPnt());
                // TODO check available quantity
                pc.setQuantity(pc.getQuantity() + addProductRequest.getQuantity());
                items.put(pc.getPnt(), pc);
            }
            calculate(cart);
        }
        return cart;*/
    }

    private void calculate(Cart cart) {

    }
}
