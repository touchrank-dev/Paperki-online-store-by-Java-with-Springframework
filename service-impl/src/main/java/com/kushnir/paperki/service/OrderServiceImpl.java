package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.OrderDao;
import com.kushnir.paperki.model.Cart;
import com.kushnir.paperki.model.product.CartProduct;
import com.kushnir.paperki.model.User;
import com.kushnir.paperki.model.order.Order;
import com.kushnir.paperki.model.order.OrderErrorForm;
import com.kushnir.paperki.model.order.OrderForm;
import com.kushnir.paperki.model.order.OrderInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderDao orderDao;

    @Override
    public Object submitOrder(OrderForm orderForm, Cart cart, User user) {
        try {
            OrderErrorForm orderErrorForm = validateOrder(cart);
            if (orderErrorForm.isErrors()) {
                return orderErrorForm;
            } else {
                return createOrder(cart);
            }
        } catch (Exception e) {
            LOGGER.error("ERROR SUBMIT ORDER >>> ERROR {}, MESSAGE {}", e, e.getMessage());
            throw e;
        }
    }


    private OrderErrorForm validateOrder (Cart cart) {
        OrderErrorForm orderErrorForm = new OrderErrorForm();
        if (cart != null) {
            OrderForm orderForm = cart.getOrderForm();
            if (orderForm != null) {





            //TODO validate





            } else orderErrorForm.setErrors("Не удалось получить форму заказа");

            if(cart.getItems().isEmpty() || cart.getItems().size() == 0) orderErrorForm.setErrors("Корзина пуста");

        } else orderErrorForm.setErrors("Не удалось получить данные корзины");

        return orderErrorForm;
    }

    private String createOrder(Cart cart) {
        String orderNumber = generateOrderNumber();
        String orderToken = generateToken();

        Order order = new Order();
        order.setIdOrderType(cart.getOrderForm().getOrderType() == null?
                1:cart.getOrderForm().getOrderType().getI() == 0?
                    1: cart.getOrderForm().getOrderType().getI());
        order.setTokenOrder(orderToken);
        order.setOrderNumber(orderNumber);
        order.setIdUser(cart.getOrderForm().getUser().getId() == null? 0:cart.getOrderForm().getUser().getId());
        order.setTotal(cart.getTotal());
        order.setTotalWithVAT(cart.getTotalWithVAT());
        order.setVATtotal(cart.getTotalVAT());
        order.setTotalDiscount(cart.getTotalDiscount());
        order.setFinalTotal(cart.getFinalTotal());
        order.setFinalTotalWithVAT(cart.getFinalTotalWithVAT());

        addOrder(order);

        return orderToken;
    }



    @Override
    public Integer addOrder(Order order) {
        return orderDao.addOrder(order);
    }

    @Override
    public Integer addOrderIfo(OrderInfo orderInfo, Integer idOrder) {
        return orderDao.addOrderIfo(orderInfo, idOrder);
    }



    @Override
    public int[] addOrderItems(HashMap<Integer, CartProduct> items, Integer idOrder) {
        return new int[0];
    }








    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private String generateOrderNumber() {
        return new SimpleDateFormat("yyyyMMdd-hhmmss").format(new Date());
    }
}
