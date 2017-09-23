package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.product.CartProduct;
import com.kushnir.paperki.model.order.Order;
import com.kushnir.paperki.model.order.OrderInfo;

import java.util.HashMap;

public interface OrderDao {
    Integer addOrder(Order order);
    Integer addOrderIfo(OrderInfo orderInfo, Integer idOrder);
    int[] addOrderItems(HashMap<Integer, CartProduct> items, Integer idOrder);
}
