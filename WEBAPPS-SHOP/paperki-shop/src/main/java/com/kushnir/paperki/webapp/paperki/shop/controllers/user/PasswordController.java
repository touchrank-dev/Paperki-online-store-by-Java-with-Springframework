package com.kushnir.paperki.webapp.paperki.shop.controllers.user;

import com.kushnir.paperki.model.user.PasswordRecoveryRequest;
import com.kushnir.paperki.service.CatalogBean;
import com.kushnir.paperki.service.MenuBean;
import com.kushnir.paperki.service.UserService;
import com.kushnir.paperki.service.exceptions.ServiceException;

import com.kushnir.paperki.webapp.paperki.shop.exceptions.PageNotFound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
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
    public String passwordRecoveryPage(Model model, HttpSession httpSession) throws PageNotFound {
        LOGGER.debug("passwordRecoveryPage() >>>");

        Integer id = (Integer) httpSession.getAttribute("passwordRequestId");
        httpSession.removeAttribute("passwordRequestId");

        if (id != null) {
            model.addAttribute("passRequest", userService.getPasswordRecoveryRequestById(id));
        } else throw new PageNotFound();

        model.addAttribute("templatePathName", contentPath + "password");
        model.addAttribute("fragmentName", "password");

        model.addAttribute("title", "Восстановление пароля");

        return "index";
    }

    @GetMapping("/{token}")
    public String changePasswordPage(@PathVariable String token,
                                     Model model) {
        LOGGER.debug("changePasswordPage({}) >>>", token);

        PasswordRecoveryRequest recoveryRequest = userService.getPasswordRecoveryRequestByToken(token);

        model.addAttribute("event", getRequestEvent(recoveryRequest));
        model.addAttribute("templatePathName", contentPath + "change-password");
        model.addAttribute("fragmentName", "change-password");

        model.addAttribute("title", "Восстановление пароля");

        return "index";
    }

    private Integer getRequestEvent(PasswordRecoveryRequest recoveryRequest) {
        Integer event = 0;

        // request wasn't found
        if (recoveryRequest == null) event = 1;
        // request already performed
        else if (recoveryRequest.getPerformed()) event = 2;
        // request is expired
        else if (recoveryRequest.getExpired() | ifExpiredByTime(recoveryRequest.getCreateDate())) event = 3;

        return event;
    }

    private Boolean ifExpiredByTime(LocalDateTime createDateTime) {
        return createDateTime.plusHours(1).isBefore(LocalDateTime.now());
    }

    @PostMapping("/{token}")
    public String changePassword(@PathVariable String token,
                                 @RequestParam HashMap<String, String> attributes,
                                 Model model) {
        LOGGER.debug("changePassword({}, {}) >>>", token, attributes);

        PasswordRecoveryRequest recoveryRequest = userService.getPasswordRecoveryRequestByToken(token);

        model.addAttribute("templatePathName", contentPath + "change-password");
        model.addAttribute("fragmentName", "change-password");

        model.addAttribute("title", "Восстановление пароля");

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
