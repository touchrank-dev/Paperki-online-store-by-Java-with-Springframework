package com.kushnir.paperki.service;

import com.kushnir.paperki.model.AddProductRequest;
import com.kushnir.paperki.model.Cart;
import com.kushnir.paperki.service.exceptions.BadAttributeValueException;
import com.kushnir.paperki.service.exceptions.ProductUnavailableException;

public interface CartBean {

    Integer[] addToCart (Cart cart, AddProductRequest addProductRequest, Boolean isUpdate) throws BadAttributeValueException,
            ProductUnavailableException;
    void deleteFromCart(Cart cart, Integer pnt);
}
