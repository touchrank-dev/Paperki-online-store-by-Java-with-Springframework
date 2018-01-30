package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.order.Attribute;
import com.kushnir.paperki.model.order.Order;
import com.kushnir.paperki.model.product.CartProduct;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface OrderDao {
    Order getOrderByToken(String token);
    HashMap<String, HashMap<Integer, Order>> getOrdersByUserId(Integer userId);

    ArrayList getAllNewOrders(LocalDate from, LocalDate to, Integer[] statuses);

    List<Attribute> getOrderAttributes (int idOrder);
    List<CartProduct> getOrderItems(int idOrder);
    Integer addOrder(Order order);
    int[] addOrderAttributes(List<Attribute> attributes);
    int[] addOrderItems(Object[] items);

    void updateOrderStatus(String orderToken, Integer status);
    void updateOrderPapNumber(String orderToken, String papOrderNumber);
}
