package com.kushnir.paperki.webapp.paperki.shop.controllers.user;

import com.kushnir.paperki.model.*;
import com.kushnir.paperki.model.password.NewPasswordErrorForm;
import com.kushnir.paperki.model.password.NewPasswordForm;
import com.kushnir.paperki.model.user.*;
import com.kushnir.paperki.service.UserService;
import com.kushnir.paperki.service.mail.Mailer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class RESTUser {

    private static final Logger LOGGER = LogManager.getLogger(RESTUser.class);
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
        RestMessage restMessage = new RestMessage(HttpStatus.OK, version);
        return restMessage;
    }

    //curl -H "Content-Type: application/json" -X POST -d '{"login":"xyz","password":"xyz"}' -v [host]:8088/api/user/login
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    RestMessage postLogin(@RequestBody LoginData loginData, HttpSession httpSession) {
        LOGGER.debug("LOGIN >>> ");
        RestMessage restMessage;
        try {
            Object user = userService.getUserByLoginPassword(loginData);
            if(user instanceof User) {
                httpSession.setAttribute("user", (User)user);
                LOGGER.debug("LOGIN SUCCESSFUL >>> \n USER: {}", user);
                restMessage = new RestMessage(HttpStatus.FOUND, "LOGIN SUCCESSFUL", null);
                return restMessage;
            } else {
                LOGGER.debug("LOGIN FAILED >>>\nERROR FORM: {}", (ErrorLoginData)user);
                restMessage = new RestMessage(HttpStatus.NOT_FOUND, "LOGIN FAILED", (ErrorLoginData) user);
                return restMessage;
            }
        } catch (Exception e) {
            LOGGER.error("LOGIN FAILED >>>\nERROR MESSAGE{}", e.getMessage());
            restMessage = new RestMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
            mailer.toSupportMail(restMessage.toString(), "ERROR REST LOGIN");
            return restMessage;
        }
    }

    //curl -H "Content-Type: application/json" -X POST -d '"string":"xyz"' -v [host]:8088/api/user/logout
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage postLogout(@RequestBody String string, HttpSession httpSession) {
        LOGGER.debug("LOGOUT >>> ");
        RestMessage restMessage;
        try {
            httpSession.invalidate();
            LOGGER.debug("LOGOUT SUCCESSFUL");
            restMessage = new RestMessage(HttpStatus.OK, "LOGOUT SUCCESSFUL");
            return restMessage;
        } catch (Exception e) {
            LOGGER.error("LOGOUT FAILED");
            restMessage = new RestMessage(HttpStatus.INTERNAL_SERVER_ERROR, "LOGOUT FAILED", e.getMessage());
            mailer.toSupportMail(restMessage.toString(), "ERROR REST LOGOUT");
            return restMessage;
        }
    }

    // curl -H "Content-Type: application/json" -d '{...}' -v localhost:8080/api/user/registration
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage registerNewUser (@RequestBody RegistrateForm registrateForm,
                                                      HttpSession httpSession) {
        LOGGER.debug("REGISTRATION >>>\nFORM RECEIVED: {}", registrateForm);
        RestMessage restMessage;
        try {
            Object user = userService.registrateUser(registrateForm);
            if(user instanceof User) {
                httpSession.setAttribute("user", (User)user);
                LOGGER.debug("REGISTRATION AND AUTHORIZATION WAS FINISHED SUCCESSFUL! >>>\nUSER:{}", user);
                restMessage = new RestMessage(HttpStatus.CREATED, "REGISTRATION SUCCESSFUL!", null);
                // TODO email
                return restMessage;
            } else {
                LOGGER.debug("REGISTRATION FAILED >>>\nERROR FORM: {}", (ErrorRegistrateForm)user);
                restMessage = new RestMessage(HttpStatus.NOT_ACCEPTABLE, "REGISTRATION FAILED", (ErrorRegistrateForm)user);
                return restMessage;
            }
        } catch (Exception e) {
            LOGGER.error("REGISTRATION FAILED >>>\nERROR MESSAGE: {}", e.getMessage());
            restMessage = new RestMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
            mailer.toSupportMail(restMessage.toString(), "ERROR REST REGISTRATION");
            return restMessage;
        }
    }

    @PostMapping("/changepassword")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage changePassword(@RequestBody NewPasswordForm newPasswordForm,
                                                    HttpSession httpSession) {
        LOGGER.debug("PASSWORD CHANGE >>>\nFORM RECEIVED: {}", newPasswordForm);
        RestMessage restMessage;
        try {

            User user = (User) httpSession.getAttribute("user");
            if(user == null) throw new Exception("EMPTY USER SESSION");
            if(user.getId() == null || user.getId() < 1) throw new Exception("USER IS UNREGISTERED");

            Object obj = userService.changePassword(newPasswordForm, user.getId());

            if(obj instanceof Integer) {
                httpSession.setAttribute("user", new User(UserType.ANONIMUS));
                LOGGER.debug("PASSWORD CHANGED SUCCESSFUL! >>>\n:{}", obj);
                restMessage = new RestMessage(HttpStatus.OK, "PASSWORD CHANGED SUCCESSFUL!", obj);
                // TODO email
                return restMessage;
            } else {
                LOGGER.debug("PASSWORD CHANGE FAILED >>>\nERROR FORM: {}", (NewPasswordErrorForm) obj);
                restMessage = new RestMessage(HttpStatus.BAD_REQUEST, "PASSWORD CHANGE FAILED", (NewPasswordErrorForm) obj);
                return restMessage;
            }
        } catch (Exception e) {
            LOGGER.error("PASSWORD CHANGE FAILED >>>\nERROR MESSAGE: {}", e.getMessage());
            restMessage = new RestMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
            mailer.toSupportMail(restMessage.toString(), "PASSWORD CHANGE FAILED");
            return restMessage;
        }
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage update(@RequestBody UserUpdateRequest userUpdateRequest,
                                                    HttpSession httpSession) {
        LOGGER.debug("USER UPDATE >>>\nFORM RECEIVED: {}", userUpdateRequest);
        RestMessage restMessage;
        try {
            User user = (User) httpSession.getAttribute("user");
            if(user == null) throw new Exception("EMPTY USER SESSION");
            if(user.getId() == null || user.getId() < 1) throw new Exception("USER IS UNREGISTERED");

            Object obj = userService.updateUser(userUpdateRequest, user.getId());

            if(obj instanceof Integer) {
                LOGGER.debug("USER UPDATED SUCCESSFUL! >>>\n:{}", obj);
                restMessage = new RestMessage(HttpStatus.OK, "USER UPDATED SUCCESSFUL!", obj);
                // TODO email
                return restMessage;
            } else {
                LOGGER.debug("USER UPDATE FAILED >>>\nERROR FORM: {}", (UserUpdateErrorResponse) obj);
                restMessage = new RestMessage(HttpStatus.BAD_REQUEST, "USER UPDATE FAILED", (UserUpdateErrorResponse) obj);
                return restMessage;
            }
        } catch (Exception e) {
            LOGGER.error("USER UPDATE FAILED >>>\nERROR MESSAGE: {}", e.getMessage());
            restMessage = new RestMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
            mailer.toSupportMail(restMessage.toString(), "USER UPDATE FAILED");
            return restMessage;
        }
    }

    @PostMapping("/addressadd")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage addAddress(@RequestBody Address address, HttpSession httpSession) {
        LOGGER.debug("ADD ADDRESS >>>\nADDRESS RECEIVED: {}", address);
        RestMessage restMessage;
        try {
            User user = (User) httpSession.getAttribute("user");
            if(user == null) throw new Exception("EMPTY USER SESSION");
            if(user.getId() == null || user.getId() < 1) throw new Exception("USER IS UNREGISTERED");

            Object obj = userService.addAddress(address, user.getId());

            if(obj instanceof Integer) {
                LOGGER.debug("ADDRESS ADDED SUCCESSFUL! >>>\n:{}", obj);
                restMessage = new RestMessage(HttpStatus.OK, "ADDRESS ADDED SUCCESSFUL!", obj);
                // TODO email
                return restMessage;
            } else {
                LOGGER.debug("ADD ADDRESS FAILED >>>\nERROR FORM: {}", (AddressErrorResponse) obj);
                restMessage = new RestMessage(HttpStatus.BAD_REQUEST, "ADD ADDRESS FAILED", (AddressErrorResponse) obj);
                return restMessage;
            }
        } catch (Exception e) {
            LOGGER.error("ADD ADDRESS FAILED >>>\nERROR MESSAGE: {}", e.getMessage());
            restMessage = new RestMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
            mailer.toSupportMail(restMessage.toString(), "ADD ADDRESS FAILED");
            return restMessage;
        }
    }

    @PostMapping("/addenterprise")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage addEnterprise(@RequestBody HashMap<String, String> enterprise, HttpSession httpSession) {
        LOGGER.debug("ADD ENTERPRISE >>>\nDATA RECEIVED: {}", enterprise);
        RestMessage restMessage = null;
        try {
            User user = (User) httpSession.getAttribute("user");
            if(user == null) throw new Exception("EMPTY USER SESSION");
            if(user.getId() == null || user.getId() < 1) throw new Exception("USER IS UNREGISTERED");

            Object obj = userService.addEnterpriseByUser(enterprise, user.getId());

            if(obj instanceof Integer) {
                LOGGER.debug("ENTERPRISE ADDED SUCCESSFUL! >>>\n:{}", obj);
                restMessage = new RestMessage(HttpStatus.OK, "ENTERPRISE ADDED SUCCESSFUL!", obj);
                // TODO email
                return restMessage;
            } else {
                LOGGER.debug("ADD ENTERPRISE FAILED >>>\nERROR FORM: {}", (EnterpriseErrorForm) obj);
                restMessage = new RestMessage(HttpStatus.BAD_REQUEST, "ADD ENTERPRISE FAILED", (EnterpriseErrorForm) obj);
                return restMessage;
            }
        } catch (Exception e) {
            LOGGER.error("ADD ENTERPRISE FAILED >>>\nERROR MESSAGE: {}", e.getMessage());
            restMessage = new RestMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
            mailer.toSupportMail(restMessage.toString(), "ADD ENTERPRISE FAILED");
            return restMessage;
        }
    }

    @PostMapping("/updateenterprise")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage updateEnterprise(@RequestBody HashMap<String, String> enterprise, HttpSession httpSession) {
        LOGGER.debug("UPDATE ENTERPRISE >>>\nDATA RECEIVED: {}", enterprise);
        RestMessage restMessage = null;
        try {
            User user = (User) httpSession.getAttribute("user");
            if(user == null) throw new Exception("EMPTY USER SESSION");
            if(user.getId() == null || user.getId() < 1) throw new Exception("USER IS UNREGISTERED");

            Object obj = userService.updateEnterpriseByUser(enterprise, user.getId());

            if(obj instanceof Integer) {
                LOGGER.debug("ENTERPRISE UPDATED SUCCESSFUL! >>>\n:{}", obj);
                restMessage = new RestMessage(HttpStatus.OK, "ENTERPRISE UPDATED SUCCESSFUL!", obj);
                // TODO email
                return restMessage;
            } else {
                LOGGER.debug("UPDATE ENTERPRISE FAILED >>>\nERROR FORM: {}", (EnterpriseErrorForm) obj);
                restMessage = new RestMessage(HttpStatus.BAD_REQUEST, "UPDATE ENTERPRISE FAILED", (EnterpriseErrorForm) obj);
                return restMessage;
            }
        } catch (Exception e) {
            LOGGER.error("UPDATE ENTERPRISE FAILED >>>\nERROR MESSAGE: {}", e.getMessage());
            restMessage = new RestMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
            mailer.toSupportMail(restMessage.toString(), "UPDATE ENTERPRISE FAILED");
            return restMessage;
        }
    }

    @GetMapping("/getaddress")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage getAddressById(@RequestParam Integer id, HttpSession httpSession) throws Exception {
        LOGGER.debug("getAddressById ({}) >>>", id);
        User user = (User) httpSession.getAttribute("user");
        if(user == null) throw new Exception("EMPTY USER SESSION");
        if(user.getId() == null || user.getId() < 1) throw new Exception("USER IS UNREGISTERED");
        Address address = userService.getUserAddress(id);
        RestMessage restMessage = new RestMessage(HttpStatus.OK, version, address);
        return restMessage;
    }
}
