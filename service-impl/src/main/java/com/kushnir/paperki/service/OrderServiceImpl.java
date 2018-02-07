package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.OrderDao;
import com.kushnir.paperki.model.Cart;
import com.kushnir.paperki.model.product.CartProduct;
import com.kushnir.paperki.model.user.User;
import com.kushnir.paperki.model.order.*;
import com.kushnir.paperki.service.exceptions.ServiceException;
import com.kushnir.paperki.service.mail.HtmlMailer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service("orderService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
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
        try {
            Order order = orderDao.getOrderByToken(token);
            order.setItems(getOrderItems(order.getId()));
            return order;
        } catch (Exception e) {
            LOGGER.error("ERROR getOrderByToken >>>", e.getMessage());
            return null;
        }
    }

    @Override
    public HashMap<String, HashMap<Integer, Order>> getOrdersByUserId(Integer userId) {
        LOGGER.debug("getOrdersByUserId({})", userId);
        try {
            HashMap<String, HashMap<Integer, Order>> userOrders = orderDao.getOrdersByUserId(userId);
            return userOrders;
        } catch (Exception e) {
            LOGGER.error("ERROR >>> {}", ExceptionUtils.getStackTrace(e));
            return null;
        }
    }

    @Override
    public ArrayList getAllNewOrders(String from, String to, Integer status) {
        LOGGER.debug("getAllNewOrders(from: {}, to: {}, status:{})", from ,to, status);
        LocalDate fromDate = null;
        LocalDate toDate = null;

        if (from != null) {
            try {
                fromDate = LocalDate.parse(from);
            } catch (Exception e) {}
        }
        if (fromDate == null) fromDate = LocalDate.parse("2018-01-01");

        if (to != null) {
            try {
                toDate = LocalDate.parse(to);
            } catch (Exception e) {}
        }
        if (toDate == null) toDate = LocalDate.now().plusDays(1);

        return orderDao.getAllNewOrders(fromDate, toDate, status == null? new Integer[]{1, 2, 3, 4 ,5 ,6}:new Integer[] {status});
    }

    @Override
    public Integer getOrderStatusByToken(String orderToken) throws ServiceException {
        LOGGER.debug("getOrderStatusByToken({})", orderToken);
        Order order = getOrderByToken(orderToken);
        if (order == null) return null;
        return order.getId_order_status();
    }

    @Override
    public void updateOrderStatus(String orderToken, String papOrderNumber, Integer status) throws ServiceException {
        LOGGER.debug("updateOrderStatus({}, {}, {})", orderToken, papOrderNumber, status);
        Assert.hasText(orderToken, "orderToken is empty");
        Assert.notNull(status, "status is null");
        Assert.isTrue(status > 0, "Статус <= 0");

        if (!StringUtils.isBlank(papOrderNumber)) orderDao.updateOrderPapNumber(orderToken, papOrderNumber);

        orderDao.updateOrderStatus(orderToken, status);
        addOrderStatusHistory(orderToken, status);
        // TODO mail to customer
    }

    private void addOrderStatusHistory(String orderToken, Integer status) {
        try {
            orderDao.addOrderStatusHistory(orderToken, status);
        } catch (Exception e) {
            LOGGER.error(">>> ERROR: {}", e);
        }
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
                            Assert.isTrue((unp.length() == 9),"УНП должно быть 9 знаков");
                        } catch (Exception e) {
                            orderErrorForm.setUnp(e.getMessage());
                        }

                        /*try {
                            Assert.notNull(address, "Пожалуйста, укажите Юридический адрес организации");
                            Assert.hasText(address, "Пожалуйста, укажите Юридический адрес организации");
                        } catch (Exception e) {
                            orderErrorForm.setAddress(e.getMessage());
                        }*/

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
        order.setComments(StringUtils.isBlank(orderForm.get("comment"))? null:orderForm.get("comment"));
        order.setPayment(paymentService.getById(getInt(orderForm.get("payment_id"))));
        order.setDelivery(deliveryService.getById(getInt(orderForm.get("shipment_id"))));

        // =============================================================
        order.setId(addOrder(order));
        // =============================================================
        order.setAttributes(generateAttributes(orderForm , order));

        addOrderAttributes(order.getAttributes());
        // =============================================================
        addOrderItems(cart.getItems(), order.getId());
        // =============================================================
        order.setItems(getListItems(cart.getItems()));

        try {
            htmlMailer.sendOrderConfirmEmail(order, orderForm.get("email"));
        } catch (MessagingException e) {
            LOGGER.error("Не удалось отрправить email сообщение о заказе\n{} >>> {}", e, e.getMessage());
        }

        addOrderStatusHistory(orderToken, 1);

        return orderToken;
    }

    private Integer addOrder(Order order) {
        LOGGER.debug("addOrder({})", order);
        int idOrder = orderDao.addOrder(order);
        return idOrder;
    }

    private List<Attribute> generateAttributes(HashMap<String, String> orderForm, Order order) throws ServiceException {
        List<Attribute> attributes = new ArrayList<>();

        int idOrder = order.getId();
        int orderTypeId = order.getId_order_type();

        attributes.add(createAttribute(idOrder, AttributeType.EMAIL,          orderForm.get("email"),         3));
        attributes.add(createAttribute(idOrder, AttributeType.CONTACT_PHONE,  orderForm.get("phone"),         4));
        attributes.add(createAttribute(idOrder, AttributeType.PAYMENT_NAME,   order.getPayment().getName(),   5));
        attributes.add(createAttribute(idOrder, AttributeType.SHIPMENT_NAME,  order.getDelivery().getName(),  6));

        attributes.add(createAttribute(idOrder, AttributeType.ORDER_TYPE_ID,  String.valueOf(orderTypeId),    8));
        attributes.add(createAttribute(idOrder, AttributeType.PAYMENT_ID, String.valueOf(order.getPayment().getId()), 9));
        attributes.add(createAttribute(idOrder, AttributeType.SHIPMENT_ID, String.valueOf(order.getDelivery().getId()), 10));

        switch (order.getDelivery().getId()) {
            case 1: break;
            case 2: attributes.add(createAttribute(idOrder, AttributeType.SHIPMENT_ADDRESS, orderForm.get("shipment_address"), 7)); break;
            default: break;
        }

        if(orderTypeId == 1) {
            attributes.add(createAttribute(idOrder, AttributeType.CONTACT_NAME, orderForm.get("name"), 1));

        } else if (orderTypeId == 2){
            attributes.add(createAttribute(idOrder, AttributeType.ENTERPRISE_NAME, orderForm.get("enterprise-name"), 1));
            attributes.add(createAttribute(idOrder, AttributeType.UNP, orderForm.get("unp"), 2));

        } else throw new ServiceException("Тип заказа не существует");

        return attributes;
    }


    private int[] addOrderAttributes(List<Attribute> attributes) throws ServiceException {
        LOGGER.debug("addOrderAttributes()");
        return orderDao.addOrderAttributes(attributes);
    }

    private Attribute createAttribute(int idOrder, AttributeType type, String value, int order) {
        return new Attribute(
                idOrder,
                type.getDescription(),
                value,
                order,
                type.name(),
                type.getType()
        );
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
