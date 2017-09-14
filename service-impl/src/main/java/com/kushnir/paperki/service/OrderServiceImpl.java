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
        OrderErrorForm orderErrorForm = validateOrder(cart);
        if (orderErrorForm.isErrors()) {
            return orderErrorForm;
        } else {
            return createOrder();
        }
    }

    private OrderErrorForm validateOrder (Cart cart) {
        OrderErrorForm orderErrorForm;
        if (cart != null) {
            OrderForm orderForm = cart.getOrderForm();
            if (orderForm != null) {





            //TODO validate





            }
        }
        return null;
    }

    private String createOrder() {
        String orderNumber = generateOrderNumber();
        String orderToken = generateToken();




        // TODO create order




        return orderToken;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private String generateOrderNumber() {
        return new SimpleDateFormat("yyyyMMdd-hhmmss").format(new Date());
    }
}
