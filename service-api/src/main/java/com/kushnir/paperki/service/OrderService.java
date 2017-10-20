package com.kushnir.paperki.service;

import com.kushnir.paperki.model.Cart;
import com.kushnir.paperki.model.user.User;
import com.kushnir.paperki.model.order.Order;
import com.kushnir.paperki.service.exceptions.ServiceException;

import java.util.HashMap;

public interface OrderService {
    Object submitOrder(HashMap<String, String> orderForm, Cart cart, User user) throws ServiceException;
    Order getOrderByToken(String token) throws ServiceException;
}
