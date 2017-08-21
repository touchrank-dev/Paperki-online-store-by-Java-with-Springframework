package com.kushnir.paperki.service;

import com.kushnir.paperki.model.AddProductRequest;
import com.kushnir.paperki.model.Cart;
import com.kushnir.paperki.model.Product;

import org.springframework.beans.factory.annotation.Autowired;

public class CartBean {

    @Autowired
    ProductBean productBean;

    public Cart addToCart (Cart cart, AddProductRequest addProductRequest) {
        Product product = productBean.getProductBuPNT(addProductRequest.getPnt());
        return cart;
    }
}
