package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.model.RegistrateForm;
import com.kushnir.paperki.model.User;
import com.kushnir.paperki.sevice.UserService;
import com.kushnir.paperki.sevice.mail.Mailer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    HttpSession httpSession;

    @Autowired
    Mailer mailer;

    @GetMapping()
    public String loginPage() {
        LOGGER.debug("loginPage() >>>");
        return "components/login/login";
    }

    @PostMapping()
    public String postLogin(@RequestParam("login") String login,
                            @RequestParam("password") String password,
                            Model model) {
        LOGGER.debug("postLogin() >>>");
        try {
            User user = userService.getUserByLoginPassword(login, password);
            putUserToSession(user);
            return "components/login/login";
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            model.addAttribute("errormessage", e.getMessage());
            return "components/login/login-fail";
        }
    }

    @PostMapping("/registration")
    public String postRegistration(@RequestBody RegistrateForm form, Model model
                                   /*@RequestParam("your-name") String name,
                                   @RequestParam("your-email") String email,
                                   @RequestParam("check1") Boolean subscribe,
                                   @RequestParam("your-password")String password,
                                   @RequestParam("check2")Boolean UseAutoPassword,
                                   @RequestParam("your-phone")String phone,
                                   @RequestParam("datepicker")String birthDate,
                                   @RequestParam("check4")Boolean isEnterprise*/) {
        LOGGER.info("postRegistration() >>> {}", form);
        try {
            User user = userService.registrateUser(form);
            putUserToSession(user);
            return "components/login/registration-success";
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            model.addAttribute("errormessage", e.getMessage());
            return "components/login/registration-fail";
        }
    }

    @PutMapping("/logout")
    public String logoutPage() {
        LOGGER.debug("logoutPage() >>>");
        return "components/login/logout";
    }

    private void putUserToSession(User user) {
        httpSession.setAttribute("user", user);
    }
}
