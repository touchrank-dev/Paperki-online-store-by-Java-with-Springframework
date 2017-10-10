package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.UserDao;
import com.kushnir.paperki.model.*;

import com.kushnir.paperki.model.user.User;
import com.kushnir.paperki.model.user.UserType;
import com.kushnir.paperki.service.exceptions.ServiceException;
import com.kushnir.paperki.service.mail.Mailer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[A#S%]).{6,20})";

    private final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$;";

    private final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    @Autowired
    Mailer mailer;

    @Autowired
    UserDao userDao;

    @Autowired
    SubscribeService subscribeService;

    @Override
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
            if(user == null) {
                user = userDao.getUserByEnterpriseUNP(loginData.getLogin());
            }
        } catch (Exception e) {
            LOGGER.error("DAO EXCEPTION >>>\nERROR MESSAGE: {}", e.getMessage());
            throw e;
        }
        try {
            Assert.notNull(user, "Пользователь не найден, проверьте правильно ли Вы указали Логин!");
        } catch (Exception e) {
            if(!errorLoginData.isErrors()) errorLoginData.setLogin(e.getMessage());
        }
        try {
            Assert.notNull(loginData.getPassword(), "Пароль не должен быть пустым");
            Assert.hasText(loginData.getPassword(), "Пароль не должен быть пустым");
            Assert.isTrue(encoding(loginData.getPassword()).equals(user.getPassword()), "Неверный пароль");
        } catch (Exception e) {
            if(!errorLoginData.isErrors()) errorLoginData.setPassword(e.getMessage());
        }
        if(errorLoginData.isErrors()) {
            LOGGER.error("LOGIN FAILED! >>>\nERROR FORM: {}", errorLoginData);
            return errorLoginData;
        } else {
            try {
                // TODO !!! HARDCODE !!! SET USER TYPE
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
    public Object registrateUser(RegistrateForm form) throws ServiceException {
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
        // подписка на рассылку
        if(form.getSubscribe()) {
            try{
                subscribeService.subscribe(form.getEmail(), 1);
            } catch (Exception e) {
                LOGGER.error("Не удалось подписать регистрируемого пользователя {} на рассылку." +
                        "\nERROR MESSAGE: {}",form.getEmail() ,e.getMessage());
            }
        }

        if(form.getAutopass()) {
            //TODO !!!HARDCODE!!!
            form.setPassword(encoding("P8g4gh1C"));
        } else {
            try {
                Assert.notNull(form.getPassword(), "Пароль не может быть пустым");
                Assert.hasText(form.getPassword(), "Пароль не может быть пустым");
                //TODO проверка на регулярное выражение (паттерн)
                /*Assert.isTrue(validatePassword(form.getPassword()),
                    "Пароль не соответствует регулярному выражению");*/
                form.setPassword(encoding(form.getPassword()));
            } catch (Exception e) {
                errorRegistrateForm.setPassword(e.getMessage());
            }
        }
        // TODO проверка на юр-лицо form.enterprize
        if(form.getEnterprise()){
            try {
                Assert.notNull(form.getUNP(), "УНП не может быть пустым");
                Assert.hasText(form.getUNP(), "УНП не может быть пустым");
            } catch (Exception e) {
                errorRegistrateForm.setUNP(e.getMessage());
            }
            try {
                Assert.notNull(form.getEnterpriseName(), "Название организации не может быть пустым");
                Assert.hasText(form.getEnterpriseName(), "Название организации не может быть пустым");
                Assert.isNull(userDao.getEnterpriseByUNP(form.getUNP()), "Органзация с таким УНП уже присутствует");
            } catch (Exception e) {
                errorRegistrateForm.setEnterpriseName(e.getMessage());
            }
            try {
                Assert.notNull(form.getBillingAddress(), "Адрес организации не может быть пустым");
                Assert.hasText(form.getBillingAddress(), "Адрес организации не может быть пустым");
            } catch (Exception e) {
                errorRegistrateForm.setBillingAddress(e.getMessage());
            }
        }

        // END VALIDATING ================================================================
        if (errorRegistrateForm.isErrors()) {
            LOGGER.error("REGISTRATION FAILED! >>>\nERROR FORM: {}", errorRegistrateForm);
                return errorRegistrateForm;
        } else {
                User user = new User(form.getEmail(),
                        form.getPassword(),
                        form.getName(),
                        form.getEmail(),
                        form.getPhone(),
                        form.getSubscribe(),
                        form.getEnterprise());
            try {
                Integer newUserId = userDao.addUser(user);
                if (form.getEnterprise())
                    Assert.notNull(addEnterpriseByUser(form, newUserId), "Не удалось добавить организацию");

                user = userDao.getUserById(newUserId);
                Assert.notNull(user, "Не удалось получить данные пользователя");
                // TODO !!!HARDCODE !!!
                user.setUserType(UserType.CUSTOMER);
                LOGGER.debug("REGISTRATION SUCCESSFULLY! >>> \nNEW AUTH USER: {}", user);
                return user;
            } catch (Exception e) {
                LOGGER.error("REGISTRATION FAILED! >>>\nERROR MESSAGE: {}", e.getMessage());
                throw e;
            }
        }
    }

    @Override
    public Integer addEnterpriseByUser (RegistrateForm form, Integer userId) throws ServiceException {
        Enterprise enterprise = new Enterprise(
                userId,
                form.getUNP(),
                form.getEnterpriseName(),
                form.getBillingAddress()
        );
        Integer newEnterpriseId = userDao.addEnterprise(enterprise);

        if(newEnterpriseId < 1) throw new ServiceException("Не удалось создать организацию для пользователя");

        if (form.getBankName() != null && form.getAccountNumber() != null && form.getBankCode() != null) {
            BillingAccount billingAccount = new BillingAccount(
                    newEnterpriseId,
                    form.getAccountNumber(),
                    form.getBankName(),
                    form.getBankCode()
            );

            int billId = userDao.addBillingAccount(billingAccount);
            if(billId < 1) throw new ServiceException("Не удалось создать расчетный счет для организации");
        }
        return newEnterpriseId;
    }

    // UTIL's
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
    }

    public static String encoding(String input) {
        String str = null;
        if(input == null) return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());
            str = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("PASSWORD ENCODING ERROR: {}", e.getMessage());
        }
        return str;
    }
}
