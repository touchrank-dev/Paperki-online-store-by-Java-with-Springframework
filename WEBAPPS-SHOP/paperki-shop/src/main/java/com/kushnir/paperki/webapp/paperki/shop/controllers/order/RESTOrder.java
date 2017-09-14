package com.kushnir.paperki.webapp.paperki.shop.controllers.order;

import com.kushnir.paperki.model.Cart;
import com.kushnir.paperki.model.RestMessage;
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
            if (cart != null) cart.setOrderForm(orderForm);
            restMessage = new RestMessage(HttpStatus.OK, "test", orderService.submitOrder(cart));
            return restMessage;
        } catch (Exception e) {
            restMessage = new RestMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
            mailer.toSupportMail(restMessage.toString(), "ERROR REST ORDER SUBMIT");
            return restMessage;
        }
    }
}
