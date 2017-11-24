package com.kushnir.paperki.webapp.paperki.shop.controllers.order;

import com.kushnir.paperki.model.user.Address;
import com.kushnir.paperki.model.user.User;
import com.kushnir.paperki.service.*;
import com.kushnir.paperki.service.exceptions.ServiceException;

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
        model.addAttribute("deliveries", deliveryService.getAll());
        model.addAttribute("payments", paymentService.getAll());
        model.addAttribute("addresses", getAddresses(httpSession));
        return "index";
    }

    @GetMapping("/{token}")
    public String orderByToken(@PathVariable String token, Model model) throws ServiceException {
        LOGGER.debug("orderByToken({}) >>>", token);
        model.addAttribute("templatePathName", contentPath + "order-details");
        model.addAttribute("fragmentName", "order-details");
        model.addAttribute("order", orderService.getOrderByToken(token));
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


    private HashMap<Integer, ArrayList<Address>> getAddresses(HttpSession httpSession) {
        User user = (User)httpSession.getAttribute("user");
        HashMap<Integer, ArrayList<Address>> addresses = null;
        if(user != null || user.getId() != null || user.getId() > 0) {
            addresses = userService.getUserAddresses(user.getId());
        }
        return addresses;
    }

}
