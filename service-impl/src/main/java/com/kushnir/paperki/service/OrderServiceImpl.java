package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.OrderDao;
import com.kushnir.paperki.model.Cart;
import com.kushnir.paperki.model.product.CartProduct;
import com.kushnir.paperki.model.User;
import com.kushnir.paperki.model.order.*;
import static com.kushnir.paperki.model.order.OrderAttributes.*;
import com.kushnir.paperki.service.exceptions.ServiceException;
import com.kushnir.paperki.service.mail.HtmlMailer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderDao orderDao;

    @Autowired
    PaymentService paymentService;

    @Autowired
    DeliveryService deliveryService;

    @Autowired
    HtmlMailer htmlMailer;

    @Override
    public Object submitOrder(HashMap<String, String> orderForm, Cart cart, User user) throws ServiceException {
        try {
            OrderErrorForm orderErrorForm = validateOrder(orderForm, cart);
            if (orderErrorForm.isErrors()) {
                return orderErrorForm;
            } else {
                return createOrder(orderForm, cart, user);
            }
        } catch (Exception e) {
            LOGGER.error("ERROR SUBMIT ORDER >>> ERROR {}, MESSAGE {}", e, e.getMessage());
            throw e;
        }
    }

    @Override
    public Order getOrderByToken(String token) throws ServiceException {
        LOGGER.debug("getOrderByToken({})", token);
        Order order = orderDao.getOrderByToken(token);
        order.setItems(getOrderItems(order.getId()));
        return order;
    }

    private List<Attribute> getOrderAttributes(int idOrder) {
        LOGGER.debug("getOrderAttributes({})", idOrder);
        List<Attribute> attributes = orderDao.getOrderAttributes(idOrder);
        return attributes;
    }

    private List<CartProduct> getOrderItems(int idOrder) {
        LOGGER.debug("getOrderItems({})", idOrder);
        List<CartProduct> items = orderDao.getOrderItems(idOrder);
        return items;
    }

    private OrderErrorForm validateOrder (HashMap<String, String> orderForm, Cart cart) {
        LOGGER.debug("START ORDER VALIDATE >>");
        OrderErrorForm orderErrorForm = new OrderErrorForm();
        if (cart != null) {
            if (orderForm != null) {
                if(orderForm.get("type") != null) {

                    int orderType = getInt(orderForm.get("type"));

                    if(orderType == 1) {
                        String name = orderForm.get("name");
                        String email = orderForm.get("email");
                        String phone = orderForm.get("phone");
                        String shipment = orderForm.get("shipment_id");
                        String shipmentAddress = orderForm.get("shipment_address");
                        String payment = orderForm.get("payment_id");

                        try {
                            Assert.notNull(name, "Пожалуйста, укажите Ваше Имя");
                            Assert.hasText(name, "Пожалуйста, укажите Ваше Имя");
                        } catch (Exception e) {
                            orderErrorForm.setName(e.getMessage());
                        }

                        try {
                            Assert.notNull(email, "Пожалуйста, укажите Ваш адрес электронной почты");
                            Assert.hasText(email, "Пожалуйста, укажите Ваш адрес электронной почты");
                            Assert.isTrue(validateEmail(email), "Введен некорректный адрес электронной почты");
                        } catch (Exception e) {
                            orderErrorForm.setEmail(e.getMessage());
                        }

                        try {
                            Assert.notNull(phone, "Пожалуйста, укажите контактный номер телефона");
                            Assert.hasText(phone, "Пожалуйста, укажите контактный номер телефона");
                        } catch (Exception e) {
                            orderErrorForm.setPhone(e.getMessage());
                        }

                        try {
                            Assert.notNull(shipment, "Пожалуйста, укажите способ получения заказа");
                            Assert.hasText(shipment, "Пожалуйста, укажите способ получения заказа");
                            int shipmentId = Integer.parseInt(shipment);
                            if (shipmentId > 1) {
                                Assert.notNull(shipmentAddress, "Пожалуйста, укажите адрес доставки");
                                Assert.hasText(shipmentAddress, "Пожалуйста, укажите адрес доставки");
                            }
                        } catch (Exception e) {
                            orderErrorForm.setShipment(e.getMessage());
                        }

                        try {
                            Assert.notNull(payment, "Пожалуйста, укажите способ оплаты заказа");
                            Assert.hasText(payment, "Пожалуйста, укажите способ оплаты заказа");
                        } catch (Exception e) {
                            orderErrorForm.setPayment(e.getMessage());
                        }

                    } else if(orderType == 2) {
                        String name = orderForm.get("enterprise-name");
                        String email = orderForm.get("email");
                        String phone = orderForm.get("phone");
                        String unp = orderForm.get("unp");
                        String address = orderForm.get("address");
                        String shipment = orderForm.get("shipment_id");
                        String shipmentAddress = orderForm.get("shipment_address");
                        String payment = orderForm.get("payment_id");

                        try{
                            Assert.notNull(name, "Пожалуйста, укажите название Вашей организации");
                            Assert.hasText(name, "Пожалуйста, укажите название Вашей организации");
                        } catch (Exception e) {
                            orderErrorForm.setName(e.getMessage());
                        }

                        try {
                            Assert.notNull(email, "Пожалуйста, укажите Ваш адрес электронной почты");
                            Assert.hasText(email, "Пожалуйста, укажите Ваш адрес электронной почты");
                            Assert.isTrue(validateEmail(email), "Введен некорректный адрес электронной почты");
                        } catch (Exception e) {
                            orderErrorForm.setEmail(e.getMessage());
                        }

                        try {
                            Assert.notNull(phone, "Пожалуйста, укажите контактный номер телефона");
                            Assert.hasText(phone, "Пожалуйста, укажите контактный номер телефона");
                        } catch (Exception e) {
                            orderErrorForm.setPhone(e.getMessage());
                        }

                        try {
                            Assert.notNull(unp, "Пожалуйста, укажите УНП");
                            Assert.hasText(unp, "Пожалуйста, укажите УНП");
                            Assert.isTrue((unp.length() > 9) && (unp.length() < 9),
                                    "УНП должно быть 9 знаков");
                        } catch (Exception e) {
                            orderErrorForm.setUnp(e.getMessage());
                        }

                        try {
                            Assert.notNull(address, "Пожалуйста, укажите Юридический адрес организации");
                            Assert.hasText(address, "Пожалуйста, укажите Юридический адрес организации");
                        } catch (Exception e) {
                            orderErrorForm.setAddress(e.getMessage());
                        }

                        try {
                            Assert.notNull(shipment, "Пожалуйста, укажите способ получения заказа");
                            Assert.hasText(shipment, "Пожалуйста, укажите способ получения заказа");
                            int shipmentId = Integer.parseInt(shipment);
                            if (shipmentId > 1) {
                                Assert.notNull(shipmentAddress, "Пожалуйста, укажите адрес доставки");
                                Assert.hasText(shipmentAddress, "Пожалуйста, укажите адрес доставки");
                            }
                        } catch (Exception e) {
                            orderErrorForm.setShipment(e.getMessage());
                        }

                        try {
                            Assert.notNull(payment, "Пожалуйста, укажите способ оплаты заказа");
                            Assert.hasText(payment, "Пожалуйста, укажите способ оплаты заказа");
                        } catch (Exception e) {
                            orderErrorForm.setPayment(e.getMessage());
                        }

                    } else orderErrorForm.setErrors("Тип заказа не определен");

                } else orderErrorForm.setErrors("Не удалось получить тип заказа");

            } else orderErrorForm.setErrors("Не удалось получить форму заказа");

            if(cart.getItems().isEmpty() || cart.getItems().size() == 0) orderErrorForm.setErrors("Корзина пуста");

        } else orderErrorForm.setErrors("Не удалось получить данные корзины");

        return orderErrorForm;
    }

    private String createOrder(HashMap<String, String> orderForm, Cart cart, User user) throws ServiceException {
        LOGGER.debug("createOrder() >>>");
        String orderNumber = generateOrderNumber();
        String orderToken = generateToken();

        int orderTypeId = getInt(orderForm.get("type"));

        Order order = new Order();

        order.setId_order_type(orderTypeId);
        order.setToken_order(orderToken);
        order.setOrder_number(orderNumber);
        order.setId_user((user.getId() == null)? 0:user.getId());
        order.setTotal(cart.getTotal());
        order.setTotal_with_vat(cart.getTotalWithVAT());
        order.setVat_total(cart.getTotalVAT());
        order.setTotal_discount(cart.getTotalDiscount());
        order.setFinal_total(cart.getFinalTotal());
        order.setFinal_total_with_vat(cart.getFinalTotalWithVAT());

        order.setComments(orderForm.get("comments"));

        order.setPayment(paymentService.getById(getInt(orderForm.get("payment_id"))));
        order.setDelivery(deliveryService.getById(getInt(orderForm.get("shipment_id"))));

        // =============================================================
        order.setId(addOrder(order));
        // =============================================================
        addOrderAttributes(orderForm , order);
        // =============================================================
        addOrderItems(cart.getItems(), order.getId());
        // =============================================================
        order.setItems(getListItems(cart.getItems()));

        try {
            htmlMailer.sendOrderConfirmEmail(order, orderForm.get("email"));
        } catch (MessagingException e) {
            LOGGER.error("Не удалось отрправить email сообщение о заказе\n{} >>> {}", e, e.getMessage());
        }

        return orderToken;
    }

    private Integer addOrder(Order order) {
        LOGGER.debug("addOrder()");
        int idOrder = orderDao.addOrder(order);
        return idOrder;
    }

    private int[] addOrderAttributes(HashMap<String, String> orderForm, Order order) throws ServiceException {
        LOGGER.debug("addOrderAttributes()");
        List<Attribute> attributes = new ArrayList<>();

        int idOrder = order.getId();
        int orderTypeId = order.getId_order_type();

        if(orderTypeId == 1) {
            attributes.add(new Attribute(idOrder, CONTACT_NAME, orderForm.get("name")));
            attributes.add(new Attribute(idOrder, CONTACT_PHONE, orderForm.get("phone")));
            attributes.add(new Attribute(idOrder, EMAIL, orderForm.get("email")));
            attributes.add(new Attribute(idOrder, SHIPMENT_NAME, order.getDelivery().getName()));
            attributes.add(new Attribute(idOrder, PAYMENT_NAME, order.getPayment().getName()));

        } else if (orderTypeId == 2){
            attributes.add(new Attribute(idOrder, ENTERPRISE_NAME, orderForm.get("enterprise-name")));
            attributes.add(new Attribute(idOrder, UNP, orderForm.get("unp")));
            attributes.add(new Attribute(idOrder, CONTACT_PHONE, orderForm.get("phone")));
            attributes.add(new Attribute(idOrder, EMAIL, orderForm.get("email")));
            attributes.add(new Attribute(idOrder, SHIPMENT_NAME, order.getDelivery().getName()));
            attributes.add(new Attribute(idOrder, PAYMENT_NAME, order.getPayment().getName()));

        } else throw new ServiceException("Тип заказа не существует");

        return orderDao.addOrderAttributes(attributes);
    }

    private int[] addOrderItems(HashMap<Integer, CartProduct> items, Integer idOrder) {
        LOGGER.debug("addOrderItems()");
        for (Map.Entry<Integer, CartProduct> item :items.entrySet()) {
            item.getValue().setIdOrder(idOrder.intValue());
        }
        return orderDao.addOrderItems(getArrayItems(items));
    }

    private List<CartProduct> getListItems(HashMap<Integer, CartProduct> items) {
        return new ArrayList<CartProduct>(items.values());
    }

    private Object[] getArrayItems(HashMap<Integer, CartProduct> items) {
        return items.values().toArray();
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private String generateOrderNumber() {
        return new SimpleDateFormat("yyyyMMdd-HHmmssSSS").format(new Date());
    }

    private int getInt(String orderTypeParameter) {
        try {
            return Integer.parseInt(orderTypeParameter);
        } catch (Exception e) {
            LOGGER.error("ERROR PARSE ORDER TYPE >>> ERROR {}, MESSAGE {}", e, e.getMessage());
            return 0;
        }
    }

    private boolean validateEmail(String email) {
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            return true;
        } catch (AddressException e) {
            return false;
        }
    }
}
