package com.kushnir.paperki.service;

import com.kushnir.paperki.model.Cart;

public interface OrderService {
    Object submitOrder(Cart cart);
}
