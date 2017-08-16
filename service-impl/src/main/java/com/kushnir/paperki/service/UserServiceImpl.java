package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.UserDao;
import com.kushnir.paperki.model.*;

import com.kushnir.paperki.service.mail.Mailer;
//import com.mifmif.common.regex.Generex;
import jdk.nashorn.internal.runtime.ECMAException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[A#S%]).{6,20})";

    private final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$;";

    private final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    //Generex generex = new Generex(PASSWORD_PATTERN);

    @Autowired
    Mailer mailer;

    @Autowired
    UserDao userDao;

    @Autowired
    BCryptPasswordEncoder bcp;

    /*@Autowired
    ErrorLoginData errorLoginData;*/
    /*@Autowired
    ErrorRegistrateForm errorRegistrateForm;*/

    @Override
    @Transactional
    public Object getUserByLoginPassword(LoginData loginData) {
        LOGGER.debug("GET USER BY LOGIN AND PASSWORD >>>\nLOGIN DATA: {}", loginData);
        ErrorLoginData errorLoginData = new ErrorLoginData();
        User user;
        try {
            Assert.notNull(loginData.getLogin(), "Значения логина не должно быть пустым");
            Assert.hasText(loginData.getLogin(), "Значения логина не должно быть пустым");
        } catch (Exception e) {
            errorLoginData.setLogin(e.getMessage());
        }
        try {
            user = userDao.getUserByLogin(loginData.getLogin());
        } catch (Exception e) {
            LOGGER.error("DAO EXCEPTION >>>\nERROR MESSAGE: {}", e.getMessage());
            throw e;
        }
        try {
            Assert.notNull(user, "Пользователь не найден, проверьте правильно ли Вы указали Логин!");
        } catch (Exception e) {
            errorLoginData.setLogin(e.getMessage());
        }
        try {
            Assert.notNull(loginData.getPassword(), "Пароль не должен быть пустым");
            Assert.hasText(loginData.getPassword(), "Пароль не должен быть пустым");
            // TODO !!! HARDCODE !!! PASSWORD VALIDATION
            Assert.isTrue(loginData.getPassword().equals(user.getPassword()), "Неверный пароль");
            // Assert.isTrue(bcp.matches(loginData.getPassword(), user.getPassword()),"Неверный пароль");
            // TODO !!! HARDCODE !!! SET USER TYPE
        } catch (Exception e) {
            errorLoginData.setPassword(e.getMessage());
        }
        if(errorLoginData.isErrors()) {
            LOGGER.error("LOGIN FAILED! >>>\nERROR FORM: {}", errorLoginData);
            return errorLoginData;
        } else {
            try {
                user.setUserType(UserType.CUSTOMER);
                LOGGER.debug("USER WAS VALIDATED SUCCESSFUL >>> \nRETURNED USER: {}", user);
                return user;
            } catch (Exception e) {
                LOGGER.error("LOGIN FAILED! >>>\nERROR MESSAGE: {}", e.getMessage());
                throw e;
            }
        }
    }

    @Override
    @Transactional
    public Object registrateUser(RegistrateForm form) {
        LOGGER.debug("NEW USER REGISTRATION >>>");
        ErrorRegistrateForm errorRegistrateForm = new ErrorRegistrateForm();
        try {
            Assert.notNull(form.getEmail(),
                    "адрес электронной почты используется в качестве логина и не может быть пустым");
            Assert.hasText(form.getEmail(),
                    "адрес электронной почты используется в качестве логина и не может быть пустым");
            Assert.isTrue(validateEmail(form.getEmail()),
                    "Введен некорректный адрес электронной почты");
            Assert.isNull(userDao.getUserByLogin(form.getEmail()),
                    "Пользователь под таким логином уже присутствует в базе даннных");
        } catch (Exception e) {
            errorRegistrateForm.setEmail(e.getMessage());
        }
        try {
            Assert.notNull(form.getName(), "Имя пользователя не должно быть пустым");
            Assert.hasText(form.getName(), "Имя пользователя не должно быть пустым");
        } catch (Exception e) {
            errorRegistrateForm.setName(e.getMessage());
        }

        // Assert.notNull(form.getPhone(), "Пожалуйста, укажите номер Вашего телефона");
        // TODO проверка телефона
        // TODO проверка на юр-лицо form.enterprize

        if(form.getAutopass()) {
            //TODO !!!HARDCODE!!!
            form.setPassword("P8g4gh1C");
            // form.setPassword(bcp.encode("P8g4gh1C"));
        } else {
            try {
                Assert.notNull(form.getPassword(), "Пароль не может быть пустым");
                Assert.hasText(form.getPassword(), "Пароль не может быть пустым");
                /*Assert.isTrue(validatePassword(form.getPassword()),
                    "Пароль не соответствует регулярному выражению");*/
                //form.setPassword(bcp.encode(form.getPassword()));
            } catch (Exception e) {
                errorRegistrateForm.setPassword(e.getMessage());
            }
        }
        if (errorRegistrateForm.isErrors()) {
            LOGGER.error("REGISTRATION FAILED! >>>\nERROR FORM: {}", errorRegistrateForm);
                return errorRegistrateForm;
        } else {
                // String password = bcp.encode(form.getPassword());
                User user = new User(form.getEmail(),
                        form.getPassword(),
                        form.getName(),
                        form.getEmail(),
                        form.getPhone(),
                        form.getSubscribe(),
                        form.getEnterprise());
            try {
                Integer newUserId = userDao.addUser(user);
                user = userDao.getUserById(newUserId);
                user.setUserType(UserType.CUSTOMER);
                LOGGER.debug("REGISTRATION SUCCESSFULLY! >>> \nNEW AUTH USER: {}", user);
                return user;
            } catch (Exception e) {
                LOGGER.error("REGISTRATION FAILED! >>>\nERROR MESSAGE: {}", e.getMessage());
                throw e;
            }
        }
    }

    private boolean validatePassword(String password) {
        matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }

    private boolean validateEmail(String email) {
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            return true;
        } catch (AddressException e) {
            return false;
        }
        /*matcher = emailPattern.matcher(email);
        return matcher.matches();*/
    }
}
