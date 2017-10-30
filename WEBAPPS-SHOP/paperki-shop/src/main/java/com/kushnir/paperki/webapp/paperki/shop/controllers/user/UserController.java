package com.kushnir.paperki.webapp.paperki.shop.controllers.user;

import com.kushnir.paperki.model.Enterprise;
import com.kushnir.paperki.model.order.Order;
import com.kushnir.paperki.model.user.Address;
import com.kushnir.paperki.model.user.User;
import com.kushnir.paperki.model.user.UserType;
import com.kushnir.paperki.service.CatalogBean;
import com.kushnir.paperki.service.MenuBean;
import com.kushnir.paperki.service.OrderService;
import com.kushnir.paperki.service.UserService;
import com.kushnir.paperki.service.exceptions.ServiceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/profile")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    private static final String PROFILE_MENU_NAME = "profile";

    @Autowired
    CatalogBean catalogBean;

    @Autowired
    MenuBean menuBean;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Value("${content.path}")
    String contentPath;

    @GetMapping
    public String personalUserPage(Model model, HttpSession httpSession) {
        LOGGER.debug("personalUserPage() >>>");
        User user = (User) httpSession.getAttribute("user");
        if (user != null || !user.getUserType().equals(UserType.ANONIMUS)) {
            model.addAttribute("templatePathName", contentPath + PROFILE_MENU_NAME);
            model.addAttribute("fragmentName", PROFILE_MENU_NAME);
            return "index";
        } else return "redirect:/";
    }

    @ModelAttribute("userProfile")
    public User getUserProfile (HttpSession httpSession) {
        User sessionUser = (User)httpSession.getAttribute("user");
        User user = null;
        if (sessionUser != null || sessionUser.getId() != null || sessionUser.getId() > 0) {
            Integer id = sessionUser.getId();
            user = userService.getUserById(id);
        }
        return user;
    }

    @ModelAttribute("userEnterprise")
    public Enterprise getEnterprise(HttpSession httpSession) {
        User user = (User)httpSession.getAttribute("user");
        Enterprise enterprise = null;
        if(user != null || user.getId() != null || user.getId() > 0) {
            enterprise = userService.getEnterpriseByUserId(user.getId());
        }
        return enterprise;
    }

    @ModelAttribute("orders")
    public HashMap<String, HashMap<Integer, Order>> getOrders(HttpSession httpSession) {
        User user = (User)httpSession.getAttribute("user");
        HashMap<String, HashMap<Integer, Order>> orders = null;
        if(user != null || user.getId() != null || user.getId() > 0) {
            orders = orderService.getOrdersByUserId(user.getId());
        }
        return orders;
    }

    @ModelAttribute("addresses")
    public HashMap<Integer, ArrayList<Address>> getAddresses(HttpSession httpSession) {
        User user = (User)httpSession.getAttribute("user");
        HashMap<Integer, ArrayList<Address>> addresses = null;
        if(user != null || user.getId() != null || user.getId() > 0) {
            addresses = userService.getUserAddresses(user.getId());
        }
        return addresses;
    }

    @ModelAttribute("mainmenu")
    public ArrayList getMainMenu () {
        return menuBean.getAll("root");
    }

    @ModelAttribute("mapcategories")
    public HashMap getCatalog () throws ServiceException {
        return catalogBean.getAll();
    }

}
