package com.kushnir.paperki.service;

import com.kushnir.paperki.model.Cart;
import com.kushnir.paperki.model.CartProduct;
import com.kushnir.paperki.model.order.Order;
import com.kushnir.paperki.model.order.OrderInfo;

import java.util.HashMap;

public interface OrderService {
    Object submitOrder(Cart cart);

    Integer addOrder(Order order);
    Integer addOrderIfo(OrderInfo orderInfo, Integer idOrder);
    int[] addOrderItems(HashMap<Integer, CartProduct> items, Integer idOrder);
}