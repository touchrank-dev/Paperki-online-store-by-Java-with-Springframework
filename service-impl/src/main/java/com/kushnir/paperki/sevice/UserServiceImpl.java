package com.kushnir.paperki.sevice;

import com.kushnir.paperki.dao.UserDao;
import com.kushnir.paperki.model.RegistrateForm;
import com.kushnir.paperki.model.User;

import com.kushnir.paperki.sevice.mail.Mailer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[A#S%]).{6,20})";
    private final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
    private Matcher matcher;

    @Autowired
    Mailer mailer;

    @Autowired
    UserDao userDao;

    @Autowired
    BCryptPasswordEncoder bcp;

    @Override
    public User getUserByLoginPassword(String userName, String password) {
        Assert.notNull(userName, "Значения логина не должно быть пустым");
        Assert.hasText(userName, "Значения логина не должно быть пустым");

        Assert.notNull(password, "Пароль не должен быть пустым");
        Assert.hasText(password, "Пароль не должен быть пустым");

        User user = userDao.getUserByLoginPassword(userName, password);
        Assert.notNull(user, "Пользователь не найден, неверный логин или пароль!");
        Assert.isTrue(bcp.matches(password, user.getPassword()),"Неверный пароль");
        LOGGER.debug("getUserByLogin({}) >>> \n RETURNED USER: {}", userName, user);
        return user;
    }

    private boolean validatePassword(String password) {
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    @Override
    public User registrateUser(RegistrateForm form) {
        Assert.isTrue(validatePassword(form.getPassword()),
                "Пароль не соответствует регулярному выражению");
        String encodedPassword = bcp.encode(form.getPassword());
        // bcp.encode(password);
        /* Assert.notNull(userDao.getUserByLogin(userName),
                "Пользователь под таким логином уже присутствует в базе даннных"); */
        return null;
    }
}
