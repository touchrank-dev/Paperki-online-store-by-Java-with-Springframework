package com.kushnir.paperki.service;

import com.kushnir.paperki.model.AddProductRequest;
import com.kushnir.paperki.model.Cart;
import com.kushnir.paperki.model.CartProduct;
import com.kushnir.paperki.model.Product;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public class CartBean {

    @Autowired
    ProductBean productBean;

    public Cart addToCart (Cart cart, AddProductRequest addProductRequest) {
        CartProduct cartProduct = productBean.getCartProductByPNT(addProductRequest.getPnt());
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
        return cart;
    }

    private void calculate(Cart cart) {

    }
}
