package com.kushnir.paperki.service;

import com.kushnir.paperki.model.Cart;
import com.kushnir.paperki.model.user.User;
import com.kushnir.paperki.model.order.Order;
import com.kushnir.paperki.service.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.HashMap;

public interface OrderService {
    Object submitOrder(HashMap<String, String> orderForm, Cart cart, User user) throws ServiceException;
    Order getOrderByToken(String token) throws ServiceException;
    HashMap<String, HashMap<Integer, Order>> getOrdersByUserId(Integer userId);
    ArrayList getAllNewOrders(String from, String to, Integer status);
    Integer getOrderStatusByToken(String orderToken) throws ServiceException;
    void updateOrderStatus(String orderToken, String papOrderNumber, Integer status) throws ServiceException;
}
