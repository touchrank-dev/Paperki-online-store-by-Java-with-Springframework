package com.kushnir.paperki.webapp.paperki.shop.controllers.order;

import com.kushnir.paperki.model.RestMessage;
import com.kushnir.paperki.model.order.OrderForm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping("/api/order")
public class RESTOrder {

    private static final Logger LOGGER = LogManager.getLogger(RESTOrder.class);

    @Value("${webapp.host}")
    String host;

    @PostMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    RestMessage validateOrder (@RequestBody OrderForm orderForm, HttpSession session) {
        LOGGER.debug("REST ORDER VALIDATE >>>");
        RestMessage restMessage;
        restMessage = new RestMessage(HttpStatus.OK, "test", null);



        return restMessage;
    }
}
