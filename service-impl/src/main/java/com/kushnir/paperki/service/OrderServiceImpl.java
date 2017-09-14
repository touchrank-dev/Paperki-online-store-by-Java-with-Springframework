package com.kushnir.paperki.service;

import com.kushnir.paperki.model.Cart;
import com.kushnir.paperki.model.order.OrderErrorForm;
import com.kushnir.paperki.model.order.OrderForm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {

    @Override
    public Object submitOrder(Cart cart) {
        validateOrder(cart);
        return createOrder();
    }

    private OrderErrorForm validateOrder (Cart cart) {
        OrderErrorForm orderErrorForm;
        if (cart != null) {
            OrderForm orderForm = cart.getOrderForm();
            if (orderForm != null) {










            }
        }
        return null;
    }

    private String createOrder() {
        return generateToken()+generateOrderNumber();
    }

    private String generateToken() {
        /*String token = UUID.randomUUID().toString() + ":" + System.currentTimeMillis();
        String[] t = token.split(":");
        String token_time = t[1];*/
        return UUID.randomUUID().toString();
    }

    private String generateOrderNumber() {
        return new SimpleDateFormat("yyyyMMdd-hhmmss").format(new Date());
    }
}
