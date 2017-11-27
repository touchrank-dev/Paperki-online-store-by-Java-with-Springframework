package com.kushnir.paperki.webapp.paperki.shop.controllers.user;

import com.kushnir.paperki.model.user.PasswordRecoveryRequest;
import com.kushnir.paperki.service.CatalogBean;
import com.kushnir.paperki.service.MenuBean;
import com.kushnir.paperki.service.UserService;
import com.kushnir.paperki.service.exceptions.ServiceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/password")
public class PasswordController {

    private static final Logger LOGGER = LogManager.getLogger(PasswordController.class);

    @Autowired
    CatalogBean catalogBean;

    @Autowired
    MenuBean menuBean;

    @Autowired
    UserService userService;

    @Value("${content.path}")
    String contentPath;

    @Value("${password.recovery.page}")
    String passwordRecoveryPage;


    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public String passwordRecoveryPage(Model model, HttpSession httpSession) {
        LOGGER.debug("passwordRecoveryPage() >>>");

        Integer id = (Integer) httpSession.getAttribute("passwordRequestId");
        httpSession.removeAttribute("passwordRequestId");

        if (id != null) {
            model.addAttribute("passRequest", userService.getPasswordRecoveryRequestById(id));
        }
        model.addAttribute("templatePathName", contentPath + "password");
        model.addAttribute("fragmentName", "password");
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
}
