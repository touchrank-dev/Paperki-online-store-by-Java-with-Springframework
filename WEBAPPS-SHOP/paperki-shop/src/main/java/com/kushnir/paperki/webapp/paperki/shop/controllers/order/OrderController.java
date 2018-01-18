package com.kushnir.paperki.webapp.paperki.shop.controllers.order;

import com.kushnir.paperki.model.Enterprise;
import com.kushnir.paperki.model.order.Order;
import com.kushnir.paperki.model.user.Address;
import com.kushnir.paperki.model.user.User;
import com.kushnir.paperki.service.*;
import com.kushnir.paperki.service.exceptions.ServiceException;
import com.kushnir.paperki.webapp.paperki.shop.exceptions.PageNotFound;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final Logger LOGGER = LogManager.getLogger(OrderController.class);

    @Autowired
    MenuBean menuBean;

    @Autowired
    CatalogBean catalogBean;

    @Autowired
    PaymentService paymentService;

    @Autowired
    DeliveryService deliveryService;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Value("${content.path}")
    String contentPath;

    @GetMapping
    public String orderPage(Model model, HttpSession httpSession) {
        LOGGER.debug("orderPage() >>>");
        model.addAttribute("templatePathName", contentPath + "order");
        model.addAttribute("fragmentName", "order");

        model.addAttribute("title", "Оформление заказа");

        model.addAttribute("deliveries", deliveryService.getAll());
        model.addAttribute("payments", paymentService.getAll());
        model.addAttribute("addresses", getAddresses(httpSession));
        model.addAttribute("enterprise", getEnterprise(httpSession));
        return "index";
    }

    @GetMapping("/{token}")
    public String orderByToken(@PathVariable String token, Model model) throws ServiceException, PageNotFound {
        LOGGER.debug("orderByToken({}) >>>", token);
        model.addAttribute("templatePathName", contentPath + "order-details");
        model.addAttribute("fragmentName", "order-details");

        try {
            Order order = orderService.getOrderByToken(token);
            model.addAttribute("order", order);
            model.addAttribute("title", order == null ? "Заказ не найден":"Заказ № "+ order.getOrder_number());
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
            throw new PageNotFound();
        }
        return "index";
    }

    @ModelAttribute("mainmenu")
    public ArrayList getMainMenu () {
        return menuBean.getAll("root");
    }

    @ModelAttribute("mapcategories")
    public HashMap getCatalog () throws ServiceException {
        return catalogBean.getAll();
    }

    private Enterprise getEnterprise(HttpSession httpSession) {
        User user = (User)httpSession.getAttribute("user");
        if(user != null || user.getId() != null || user.getId() > 0) {
            return userService.getEnterpriseByUserId(user.getId());
        }
        return null;
    }

    private HashMap<Integer, ArrayList<Address>> getAddresses(HttpSession httpSession) {
        User user = (User)httpSession.getAttribute("user");
        if(user != null || user.getId() != null || user.getId() > 0) {
            return userService.getUserAddresses(user.getId());
        }
        return null;
    }

}
