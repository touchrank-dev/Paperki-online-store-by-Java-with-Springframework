package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.order.Attribute;
import com.kushnir.paperki.model.order.Order;
import com.kushnir.paperki.model.product.CartProduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface OrderDao {
    Order getOrderByToken(String token);
    HashMap<String, HashMap<Integer, Order>> getOrdersByUserId(Integer userId);

    ArrayList getAllNewOrders();

    List<Attribute> getOrderAttributes (int idOrder);
    List<CartProduct> getOrderItems(int idOrder);
    Integer addOrder(Order order);
    int[] addOrderAttributes(List<Attribute> attributes);
    int[] addOrderItems(Object[] items);
}
