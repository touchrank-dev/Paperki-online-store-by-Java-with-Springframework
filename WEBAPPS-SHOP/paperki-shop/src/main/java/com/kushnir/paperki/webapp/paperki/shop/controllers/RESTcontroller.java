package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kushnir.paperki.model.LoginData;
import com.kushnir.paperki.model.RegistrateForm;
import com.kushnir.paperki.model.User;
import com.kushnir.paperki.model.UserType;
import com.kushnir.paperki.sevice.UserService;
import com.kushnir.paperki.sevice.mail.Mailer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.Serializable;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RESTcontroller {

    private static final Logger LOGGER = LogManager.getLogger(RESTcontroller.class);

    @Autowired
    UserService userService;

    @Autowired
    Mailer mailer;

    //curl -v [host]:8080/api
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage version(HttpSession httpSession) {
        LOGGER.debug("version() >>>");
        User user = new User();
        user.setName("Артем");
        user.setLogin("kushnir");
        user.setUserType(UserType.CUSTOMER);
        user.setId(1);
        httpSession.setAttribute("user", user);
        return new RestMessage(HttpStatus.FOUND, httpSession.getAttribute("user").toString());
    }

    //curl -H "Content-Type: application/json" -X POST -d '{"login":"xyz","password":"xyz"}' -v [host]:8088/api/login
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.FOUND)
    public @ResponseBody RestMessage postLogin(@RequestBody LoginData loginData, HttpSession httpSession) {
        LOGGER.debug("postLogin() >>> \n CREDENTIALS: {}, {}",  loginData.getLogin(), loginData.getPassword());
        try {
            User user = userService.getUserByLoginPassword(loginData.getLogin(), loginData.getPassword());
            httpSession.setAttribute("user", user);
            LOGGER.error("Login successful >>> \n USER: {}", user);
            return new RestMessage(HttpStatus.FOUND, "login successful");
        } catch (Exception e) {
            LOGGER.error("login failed >>> {}", e.getMessage());
            return new RestMessage(HttpStatus.NOT_FOUND, "login failed >>> "+e.getMessage());
        }
    }

    @PostMapping("/test")
    @ResponseStatus(HttpStatus.FOUND)
    public @ResponseBody RestMessage postTest(@RequestBody String string, HttpSession httpSession) {
        User user = new User();
        user.setName(string);
        user.setLogin("kushnir");
        user.setUserType(UserType.CUSTOMER);
        user.setId(1);
        httpSession.setAttribute("user", user);
        return new RestMessage(HttpStatus.FOUND, "login successful");
    }

    /*//curl -H "Content-Type: application/json" -X POST -d '{...}' -v [host]:8088/api/registration
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public String postRegistration(@RequestBody RegistrateForm form, Model model
                                   *//*@RequestParam("your-name") String name,
                                   @RequestParam("your-email") String email,
                                   @RequestParam("check1") Boolean subscribe,
                                   @RequestParam("your-password")String password,
                                   @RequestParam("check2")Boolean UseAutoPassword,
                                   @RequestParam("your-phone")String phone,
                                   @RequestParam("datepicker")String birthDate,
                                   @RequestParam("check4")Boolean isEnterprise*//*) {
        LOGGER.info("postRegistration() >>> \n FORM: {}", form);
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
    }*/






    private class RestMessage {
        HttpStatus code;
        String message;

        public RestMessage() {
        }

        public RestMessage(HttpStatus code, String message) {
            this.code = code;
            this.message = message;
        }

        public HttpStatus getCode() {
            return code;
        }

        public void setCode(HttpStatus code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RestMessage that = (RestMessage) o;

            if (code != that.code) return false;
            return message != null ? message.equals(that.message) : that.message == null;
        }

        @Override
        public int hashCode() {
            int result = code != null ? code.hashCode() : 0;
            result = 31 * result + (message != null ? message.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "RestMessage{" +
                    "code=" + code +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
