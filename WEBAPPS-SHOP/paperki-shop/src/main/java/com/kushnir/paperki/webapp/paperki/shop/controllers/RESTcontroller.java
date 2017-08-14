package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.model.LoginData;
import com.kushnir.paperki.model.RegistrateForm;
import com.kushnir.paperki.model.User;
import com.kushnir.paperki.model.UserType;
import com.kushnir.paperki.sevice.UserService;
import com.kushnir.paperki.sevice.mail.Mailer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RESTcontroller {

    private static final Logger LOGGER = LogManager.getLogger(RESTcontroller.class);
    private static final String version = "1.0";

    @Autowired
    UserService userService;

    @Autowired
    Mailer mailer;

    @Value("${webapp.host}")
    String host;

    //curl -v [host]:8080/api
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage version() {
        LOGGER.debug("{} Rest api version() >>>", host);
        return new RestMessage(HttpStatus.OK, version);
    }

    //curl -H "Content-Type: application/json" -X POST -d '{"login":"xyz","password":"xyz"}' -v [host]:8088/api/login
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.FOUND)
    public @ResponseBody RestMessage postLogin(@RequestBody LoginData loginData, HttpSession httpSession) {
        LOGGER.debug("postLogin() >>> \n CREDENTIALS: {}, {}",  loginData.getLogin(), loginData.getPassword());
        try {
            User user = userService.getUserByLoginPassword(loginData.getLogin(), loginData.getPassword());
            httpSession.setAttribute("user", user);
            LOGGER.debug("Login successful >>> \n USER: {}", user);
            return new RestMessage(HttpStatus.FOUND, "login successful");
        } catch (Exception e) {
            LOGGER.error("login failed >>> {}", e.getMessage());
            return new RestMessage(HttpStatus.NOT_FOUND, "login failed >>> "+e.getMessage());
        }
    }

    //curl -H "Content-Type: application/json" -X POST -d '{"login":"xyz","password":"xyz"}' -v [host]:8088/api/login
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

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody RestMessage registerNewUser (@RequestBody RegistrateForm registrateForm,
                                                      HttpSession httpSession) {
        LOGGER.debug("NEW USER REGISTRATION >>>");
        try {
            userService.registrateUser(registrateForm);
        } catch (Exception e) {
            return new RestMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Register failed!", registrateForm);
        }
        return new RestMessage(HttpStatus.CREATED, "Register successful", registrateForm);
    }




    private class RestMessage {
        HttpStatus code;
        String message;
        Object object;

        public RestMessage() {
        }

        public RestMessage(HttpStatus code, String message) {
            this.code = code;
            this.message = message;
        }

        public RestMessage(HttpStatus code, String message, Object object) {
            this.code = code;
            this.message = message;
            this.object = object;
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

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RestMessage that = (RestMessage) o;

            if (code != that.code) return false;
            if (message != null ? !message.equals(that.message) : that.message != null) return false;
            return object != null ? object.equals(that.object) : that.object == null;
        }

        @Override
        public int hashCode() {
            int result = code != null ? code.hashCode() : 0;
            result = 31 * result + (message != null ? message.hashCode() : 0);
            result = 31 * result + (object != null ? object.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "RestMessage{" +
                    "code=" + code +
                    ", message='" + message + '\'' +
                    ", object=" + object +
                    '}';
        }
    }
}
