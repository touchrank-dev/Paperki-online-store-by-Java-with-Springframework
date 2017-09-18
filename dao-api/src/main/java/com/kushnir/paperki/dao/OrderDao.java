package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.CartProduct;
import com.kushnir.paperki.model.order.Order;
import com.kushnir.paperki.model.order.OrderInfo;

import java.util.HashMap;

public interface OrderDao {
    Order getOrderByToken(String token);
    Integer addOrder(Order order);
    Integer addOrderIfo(OrderInfo orderInfo, Integer idOrder);
    int[] addOrderAttributes(HashMap<String, String> orderForm, Integer idOrder);
    int[] addOrderItems(HashMap<Integer, CartProduct> items, Integer idOrder);
}
