package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.UserDao;
import com.kushnir.paperki.model.RegistrateForm;
import com.kushnir.paperki.model.User;

import com.kushnir.paperki.model.UserType;
import com.kushnir.paperki.service.mail.Mailer;
//import com.mifmif.common.regex.Generex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
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

    //@Autowired
    //BCryptPasswordEncoder bcp;

    @Override
    @Transactional
    public User getUserByLoginPassword(String userName, String password) {
        Assert.notNull(userName, "Значения логина не должно быть пустым");
        Assert.hasText(userName, "Значения логина не должно быть пустым");

        Assert.notNull(password, "Пароль не должен быть пустым");
        Assert.hasText(password, "Пароль не должен быть пустым");

        User user = userDao.getUserByLogin(userName);
        Assert.notNull(user, "Пользователь не найден, проверьте правильно ли Вы указали Логин!");
        // Assert.isTrue(bcp.matches(password, user.getPassword()),"Неверный пароль");
        user.setUserType(UserType.CUSTOMER);
        LOGGER.debug("getUserByLogin({}) >>> \nRETURNED USER: {}", userName, user);
        return user;
    }

    @Override
    @Transactional
    public User registrateUser(RegistrateForm form) {
        LOGGER.debug("NEW USER REGISTRATION >>>");
        Assert.notNull(form.getEmail(),
                "адрес электронной почты используется в качестве логина и не может быть пустым");
        Assert.hasText(form.getEmail(),
                "адрес электронной почты используется в качестве логина и не может быть пустым");
        /*Assert.isTrue(validateEmail(form.getEmail()),
                "Введеннйы адрес электронной почты не соответствует регулярному выражению");*/
        Assert.isNull(userDao.getUserByLogin(form.getEmail()),
                    "Пользователь под таким логином уже присутствует в базе даннных");

        Assert.notNull(form.getName(), "Имя пользователя не должно быть пустым");
        Assert.hasText(form.getName(),"Имя пользователя не должно быть пустым");

        Assert.notNull(form.getPhone(), "Пожалуйста, укажите номер Вашего телефона");

        // TODO проверка телефона

        // TODO проверка на юр-лицо form.enterprize

        if(form.getAutopass()) {
            /*password = generex.random();
            System.out.println(password); // TODO need to delete this
            password = bcp.encode(password);
            System.out.println(password); // TODO need to delete this
            form.setPassword(password);*/
            // password = null; // finalize
        } else {
            Assert.notNull(form.getPassword(), "Пароль не может быть пустым");
            /*Assert.isTrue(validatePassword(form.getPassword()),
                    "Пароль не соответствует регулярному выражению");*/
            //form.setPassword(bcp.encode(form.getPassword()));
        }
        try {
            User user = new User(form.getEmail(),
                                 form.getPassword(),
                                 form.getName(),
                                 form.getEmail(),
                                 form.getPhone(),
                                 form.getSubscribe(),

                                 form.getEnterprise());
            // Integer newUserId = userDao.addUser(form);
            Integer newUserId = userDao.addUser(user);
            user = userDao.getUserById(newUserId);
            user.setUserType(UserType.CUSTOMER);
            LOGGER.debug("REGISTRATION SUCCESSFULLY! >>> \nNEW AUTH USER: {}", user);
            return user;
        } catch (Exception e) {
            LOGGER.error("REGISTRATION FAILED! >>>\nERROR >>> {}", e.getMessage());
            return null;
        }
    }

    private boolean validatePassword(String password) {
        matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }

    private boolean validateEmail(String email) {
        matcher = emailPattern.matcher(email);
        return matcher.matches();
    }
}
