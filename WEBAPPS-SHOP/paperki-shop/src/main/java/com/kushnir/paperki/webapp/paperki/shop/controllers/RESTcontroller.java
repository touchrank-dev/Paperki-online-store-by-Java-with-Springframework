package com.kushnir.paperki.webapp.paperki.shop.controllers;

import com.kushnir.paperki.model.*;
import com.kushnir.paperki.service.UserService;
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
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage postLogin(@RequestBody LoginData loginData, HttpSession httpSession) {
        LOGGER.debug("LOGIN >>> ");
        try {
            Object user = userService.getUserByLoginPassword(loginData);
            if(user instanceof User) {
                httpSession.setAttribute("user", (User)user);
                LOGGER.debug("LOGIN SUCCESSFUL >>> \n USER: {}", user);
                return new RestMessage(HttpStatus.FOUND, "LOGIN SUCCESSFUL", null);
            } else {
                LOGGER.debug("LOGIN FAILED >>>\nERROR FORM: {}", (ErrorLoginData)user);
                return new RestMessage(HttpStatus.NOT_FOUND, "LOGIN FAILED", (ErrorLoginData) user);
            }
        } catch (Exception e) {
            LOGGER.error("LOGIN FAILED >>>\nERROR MESSAGE{}", e.getMessage());
            return new RestMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
        }
    }

    //curl -H "Content-Type: application/json" -X POST -d '"string":"xyz"' -v [host]:8088/api/logout
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage postLogout(@RequestBody String string, HttpSession httpSession) {
        LOGGER.debug("REST LOGOUT >>> ");
        try {
            httpSession.invalidate();
            LOGGER.debug("LOGOUT SUCCESSFUL");
            return new RestMessage(HttpStatus.OK, "LOGOUT SUCCESSFUL");
        } catch (Exception e) {
            LOGGER.error("LOGOUT FAILED");
            return new RestMessage(HttpStatus.INTERNAL_SERVER_ERROR, "LOGOUT FAILED", e.getMessage());
        }
    }

    // curl -H "Content-Type: application/json" -d '{"name":"kushnir","email":"a-kush@mail.ru", "subscribe":true, "password":"42Kush6984", "autopass":false, "phone":"426984", "birthDate":"19-05-1987", "enterprise":false}' -v localhost:8080/api/registration
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage registerNewUser (@RequestBody RegistrateForm registrateForm,
                                                      HttpSession httpSession) {
        LOGGER.debug("REST REGISTRATION >>>\nFORM RECEIVED: {}", registrateForm);
        try {
            Object user = userService.registrateUser(registrateForm);
            if(user instanceof User) {
                httpSession.setAttribute("user", (User)user);
                LOGGER.debug("REGISTRATION AND AUTHORIZATION WAS FINISHED SUCCESSFUL! >>>\nUSER:{}", user);
                return new RestMessage(HttpStatus.CREATED, "REGISTRATION SUCCESSFUL!", null);
            } else {
                LOGGER.debug("REGISTRATION FAILED >>>\nERROR FORM: {}", (ErrorRegistrateForm)user);
                return new RestMessage(HttpStatus.NOT_ACCEPTABLE, "REGISTRATION FAILED", (ErrorRegistrateForm)user);
            }
        } catch (Exception e) {
            LOGGER.error("REGISTRATION FAILED >>>\nERROR MESSAGE: {}", e.getMessage());
            mailer.toSupportMail(e.getMessage(), "ERROR REST REGISTRATION");
            return new RestMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
        }
    }

    @PostMapping("/changepassword")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage changePassword(@RequestBody String email, HttpSession httpSession) {
        LOGGER.debug("REST PASSWORD CHANGE >>>\nEMAIL RECEIVED: {}", email);
        try{
            return new RestMessage(HttpStatus.OK, "PASSWORD CHANGE ACCEPTED!", null);
        } catch (Exception e) {
            LOGGER.error("PASSWORD CHANGE FAILED >>>\nERROR MESSAGE: {}", e.getMessage());
            mailer.toSupportMail(e.getMessage(), "ERROR REST PASSWORD CHANGE");
            return new RestMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
        }
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
