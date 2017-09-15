package com.kushnir.paperki.webapp.paperki.shop.controllers.order;

import com.kushnir.paperki.model.Cart;
import com.kushnir.paperki.model.RestMessage;
import com.kushnir.paperki.model.User;
import com.kushnir.paperki.model.order.OrderForm;
import com.kushnir.paperki.service.OrderService;
import com.kushnir.paperki.service.mail.Mailer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping("/api/order")
public class RESTOrder {

    private static final Logger LOGGER = LogManager.getLogger(RESTOrder.class);

    @Autowired
    OrderService orderService;

    @Autowired
    Mailer mailer;

    @Value("${webapp.host}")
    String host;

    @PostMapping("/submit")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage submitOrder (@RequestBody OrderForm orderForm,
                                                    HttpSession session) {
        LOGGER.debug("REST ORDER SUBMIT >>>");
        RestMessage restMessage;
        try {
            Cart cart = (Cart) session.getAttribute("cart");
            User user = (User) session.getAttribute("user");
            if (user != null) orderForm.setUser(user);
            if (cart != null) cart.setOrderForm(orderForm);

            Object obj = orderService.submitOrder(cart);
            if(obj instanceof Integer) {
                restMessage = new RestMessage(HttpStatus.OK, "Заказ успешно создан", obj);
            } else {
                restMessage = new RestMessage(HttpStatus.BAD_REQUEST, "Ошибка создания заказа", obj);
            }

            return restMessage;
        } catch (Exception e) {
            LOGGER.error("ERROR REST ORDER SUBMIT >>>\nERROR MESSAGE{}", e.getMessage());
            restMessage = new RestMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
            mailer.toSupportMail(restMessage.toString(), "ERROR REST ORDER SUBMIT");
            return restMessage;
        }
    }
}
