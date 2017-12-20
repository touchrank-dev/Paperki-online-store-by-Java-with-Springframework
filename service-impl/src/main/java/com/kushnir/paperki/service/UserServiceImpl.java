package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.UserDao;
import com.kushnir.paperki.model.*;

import com.kushnir.paperki.model.password.NewPasswordErrorForm;
import com.kushnir.paperki.model.password.NewPasswordForm;
import com.kushnir.paperki.model.user.*;
import com.kushnir.paperki.service.exceptions.ServiceException;
import com.kushnir.paperki.service.mail.Mailer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.jws.soap.SOAPBinding;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
            user = getUserByLogin(loginData.getLogin());
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
            LOGGER.error("LOGIN FAILED!");
            return errorLoginData;
        } else {
            try {
                // TODO !!! HARDCODE !!! SET USER TYPE
                user.setUserType(UserType.CUSTOMER);
                LOGGER.debug("USER WAS VALIDATED SUCCESSFUL >>>");
                return user;
            } catch (Exception e) {
                LOGGER.error("LOGIN FAILED! >>>");
                throw e;
            }
        }
    }

    @Override
    public User getUserByLogin(String login) {
        LOGGER.debug("GET USER BY LOGIN >>>\nLOGIN: {}", login);
        return userDao.getUserByLogin(login);
    }

    @Override
    public User getUserById(Integer id) {
        LOGGER.debug("GET USER BY ID >>>\nID: {}", id);
        return userDao.getUserById(id);
    }

    @Override
    public Enterprise getEnterpriseByUserId(Integer userId) {
        LOGGER.debug("getEnterpriseByUserId ({})", userId);
        try {
            Enterprise enterprise = userDao.getEnterpriseByUserId(userId);
            return enterprise;
        } catch (Exception e) {
            LOGGER.error("ERROR: {}\nERROR MESSAGE: {}", e, e.getMessage());
            return null;
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
        } catch (IllegalArgumentException e) {
            errorRegistrateForm.setEmail(e.getMessage());
        }
        try {
            Assert.notNull(form.getName(), "Имя пользователя не должно быть пустым");
            Assert.hasText(form.getName(), "Имя пользователя не должно быть пустым");
        } catch (IllegalArgumentException e) {
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
                Assert.isTrue(form.getPassword().length() > 5,
                        "Длина пароля не должна быть меньше 6 символов");

                //TODO проверка на регулярное выражение (паттерн)
                /*Assert.isTrue(validatePassword(form.getPassword()),
                    "Пароль не соответствует регулярному выражению");*/
            } catch (IllegalArgumentException e) {
                errorRegistrateForm.setPassword(e.getMessage());
            }

            try {
                Assert.isTrue(form.getPasswordConfirm().equals(form.getPassword()), "Пароли не совпадают");
            } catch (IllegalArgumentException e) {
                errorRegistrateForm.setConfirmPassword(e.getMessage());
            }

        }
        // TODO проверка на юр-лицо form.enterprize
        if(form.getEnterprise()){
            try {
                Assert.notNull(form.getUNP(), "УНП не может быть пустым");
                Assert.hasText(form.getUNP(), "УНП не может быть пустым");
                Assert.isTrue(form.getUNP().length() == 9, "УНП должно быть 9 знаков");
                /*Assert.isTrue(form.getUNP().matches("^\\D*$"), "Введено недопустиаое значение УНП");*/
            } catch (IllegalArgumentException e) {
                errorRegistrateForm.setUNP(e.getMessage());
            }
            try {
                Assert.notNull(form.getEnterpriseName(), "Название организации не может быть пустым");
                Assert.hasText(form.getEnterpriseName(), "Название организации не может быть пустым");
                Assert.isNull(userDao.getEnterpriseByUNP(form.getUNP()), "Органзация с таким УНП уже присутствует");
            } catch (IllegalArgumentException e) {
                errorRegistrateForm.setEnterpriseName(e.getMessage());
            }
            try {
                Assert.notNull(form.getBillingAddress(), "Адрес организации не может быть пустым");
                Assert.hasText(form.getBillingAddress(), "Адрес организации не может быть пустым");
            } catch (IllegalArgumentException e) {
                errorRegistrateForm.setBillingAddress(e.getMessage());
            }
        }

        // END VALIDATING ================================================================
        if (errorRegistrateForm.isErrors()) {
            LOGGER.error("REGISTRATION FAILED! >>>\nERROR FORM: {}", errorRegistrateForm);
                return errorRegistrateForm;
        } else {
                form.setPassword(encoding(form.getPassword()));
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
        LOGGER.debug("addEnterpriseByUser({})", userId);
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

    @Override
    public Object addEnterpriseByUser (HashMap data, Integer userId) throws ServiceException {
        LOGGER.debug("addEnterpriseByUser({}, {})", data, userId);
        EnterpriseErrorForm enterpriseErrorForm = new EnterpriseErrorForm();
        try {
            Assert.notNull(data.get("name"), "Введите название организации");
            Assert.hasText((String) data.get("name"), "Введите название организации");
            Assert.isTrue(((String) data.get("name")).length() < 400,
                    "Длина названия не должна превышать 400 символов");
        } catch (Exception e) {
            enterpriseErrorForm.setName(e.getMessage());
        }

        try {
            Assert.notNull(data.get("unp"), "Введите УНП организации");
            Assert.hasText((String) data.get("unp"), "Введите УНП организации");
            Assert.isNull(userDao.getEnterpriseByUNP((String) data.get("unp")),
                    "Органзация с таким УНП уже присутствует");
            Assert.isTrue(((String) data.get("unp")).length() == 9, "УНП должно быть 9 знаков");
        } catch (Exception e) {
            enterpriseErrorForm.setUnp(e.getMessage());
        }

        if (enterpriseErrorForm.isErrors()) return enterpriseErrorForm;
        else {
            Enterprise enterprise = new Enterprise(
                    userId,
                    (String) data.get("unp"),
                    (String) data.get("name")
            );

            Integer newEnterpriseId = userDao.addEnterprise(enterprise);
            return newEnterpriseId;
        }
    }

    @Override
    public Object updateEnterpriseByUser (HashMap data, Integer userId) throws Exception {
        LOGGER.debug("updateEnterpriseByUser({}, {})", data, userId);
        EnterpriseErrorForm enterpriseErrorForm = new EnterpriseErrorForm();
        try {
            Assert.notNull(data.get("name"), "Введите название организации");
            Assert.hasText((String) data.get("name"), "Введите название организации");
            Assert.isTrue(((String) data.get("name")).length() < 400,
                    "Длина названия не должна превышать 400 символов");
        } catch (Exception e) {
            enterpriseErrorForm.setName(e.getMessage());
        }

        if (enterpriseErrorForm.isErrors()) return enterpriseErrorForm;
        else {
            Enterprise userEnterprise = userDao.getEnterpriseByUserId(userId);
            if (userEnterprise == null || userEnterprise.getId() == null) throw new Exception("USER HAVEN'T ENTERPRISE");
            userEnterprise.setEnterpriseName((String) data.get("name"));
            Integer numRowsUpdated = userDao.updateEnterprise(userEnterprise);
            return numRowsUpdated;
        }
    }

    @Override
    public Object changePassword(NewPasswordForm newPasswordForm, Integer userId, Boolean isRestore) {
        LOGGER.debug("changePassword({}, {})", newPasswordForm, userId);
        NewPasswordErrorForm errorForm = new NewPasswordErrorForm();
        User user = userDao.getUserById(userId);

        if (!isRestore) {
            try {
                Assert.notNull(newPasswordForm.getOldPassword(), "Введите текущий пароль");
                Assert.hasText(newPasswordForm.getOldPassword(), "Введите текущий пароль");
                Assert.isTrue(encoding(newPasswordForm.getOldPassword()).equals(user.getPassword()),
                        "Неверный текущий пароль");
            } catch (Exception e) {
                errorForm.setOldPassword(e.getMessage());
            }
        }

        try {
            Assert.notNull(newPasswordForm.getNewPassword(),"Введите новый пароль");
            Assert.hasText(newPasswordForm.getNewPassword(),"Введите новый пароль");
            Assert.isTrue(newPasswordForm.getNewPassword().length() > 5,
                    "Длина нового пароля не должна быть меньше 6 символов");
            Assert.isTrue(!encoding(newPasswordForm.getNewPassword()).equals(user.getPassword()),
                    "Новый пароль не должен быть равен текущему");
        } catch (Exception e) {
            errorForm.setNewPassword(e.getMessage());
        }

        try {
            Assert.isTrue(newPasswordForm.getNewPassword().equals(newPasswordForm.getNewPasswordConfirm()),
                    "Пароли не совпадают");
        } catch (Exception e) {
            if(!errorForm.isErrors()) errorForm.setNewPasswordConfirm(e.getMessage());
        }

        if (errorForm.isErrors()) return errorForm;

        else {
            String newPassword = encoding(newPasswordForm.getNewPassword());
            Integer count = userDao.updateUserPassword(newPassword, userId);
            return count;
        }
    }

    @Override
    public Object updateUser(UserUpdateRequest userUpdateRequest, Integer userId) {
        LOGGER.debug("updateUser({}, {})", userUpdateRequest, userId);
        UserUpdateErrorResponse userUpdateErrorResponse = new UserUpdateErrorResponse();

        try {
            Assert.notNull(userUpdateRequest.getName(),"Имя пользователя не должно быть пустым");
            Assert.hasText(userUpdateRequest.getName(),"Имя пользователя не должно быть пустым");
        } catch (Exception e) {
            userUpdateErrorResponse.setName(e.getMessage());
        }

        try {
            Assert.notNull(userUpdateRequest.getEmail(),
                    "Адрес электронной почты не может быть пустым");
            Assert.hasText(userUpdateRequest.getEmail(),
                    "Адрес электронной почты не может быть пустым");
            Assert.isTrue(validateEmail(userUpdateRequest.getEmail()),
                    "Введен некорректный адрес электронной почты");
        } catch (Exception e) {
            userUpdateErrorResponse.setEmail(e.getMessage());
        }

        try {
            Assert.notNull(userUpdateRequest.getPhone(),"Номер телефона не должен быть пустым");
            Assert.hasText(userUpdateRequest.getPhone(),"Номер телефона не должен быть пустым");
        } catch (Exception e) {
            userUpdateErrorResponse.setPhone(e.getMessage());
        }

        try {
            if(userUpdateRequest.getBirthday() != null
                    || !userUpdateRequest.getBirthday().isEmpty()
                    || !userUpdateRequest.getBirthday().equals("")) {
                String str = userUpdateRequest.getBirthday();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(str, formatter);
                userUpdateRequest.setBirthday(date.toString());
            }
        } catch (Exception e) {
            userUpdateErrorResponse.setBirthday("Ошибка в формате даты");
        }

        if (userUpdateErrorResponse.isErrors()) return userUpdateErrorResponse;
        else {
            Integer count = userDao.updateUser(userUpdateRequest, userId);
            return count;
        }
    }

    @Override
    @Transactional
    public Object addAddress(Address address, Integer userId) {
        LOGGER.debug("addAddress({}, {})", address, userId);
        AddressErrorResponse addressErrorResponse = new AddressErrorResponse();

        validateAddress(address, addressErrorResponse);

        if (addressErrorResponse.isErrors()) return addressErrorResponse;
        else {
            try {
                String value =
                        (address.getIndex() != null && !address.getIndex().equals("") ? address.getIndex():"")
                        +(address.getCity() != null && !address.getCity().equals("") ? " "+address.getCity():"")
                        +(address.getStreet() != null && !address.getStreet().equals("") ? " "+address.getStreet():"")
                        +(address.getHouse() != null && !address.getHouse().equals("") ? " "+address.getHouse():"")
                        +(address.getHousePart() != null && !address.getHousePart().equals("") ? "/"+address.getHousePart():"")
                        +(address.getHouseOffice() != null && !address.getHouseOffice().equals("") ? ", "+address.getHouseOffice():"");

                address.setValue(value);
                Integer count = userDao.addAddress(address, userId);
                return count;
            } catch (Exception e) {
                LOGGER.error("ERROR>>> {}, {}", e, e.getMessage());
                throw e;
            }
        }
    }

    @Override
    @Transactional
    public Object updateAddress(Address address, Integer userId) {
        LOGGER.debug("updateAddress({}, {})", address, userId);
        AddressErrorResponse addressErrorResponse = new AddressErrorResponse();

        Assert.notNull(address.getId(), "address id = null");
        Assert.isTrue(address.getId() > 0, "address id <= 0");

        // TODO check if id is from current user

        validateAddress(address, addressErrorResponse);

        if (addressErrorResponse.isErrors()) return addressErrorResponse;
        else {
            try {
                String value =
                        (address.getIndex() != null && !address.getIndex().equals("") ? address.getIndex():"")
                                +(address.getCity() != null && !address.getCity().equals("") ? " "+address.getCity():"")
                                +(address.getStreet() != null && !address.getStreet().equals("") ? " "+address.getStreet():"")
                                +(address.getHouse() != null && !address.getHouse().equals("") ? " "+address.getHouse():"")
                                +(address.getHousePart() != null && !address.getHousePart().equals("") ? "/"+address.getHousePart():"")
                                +(address.getHouseOffice() != null && !address.getHouseOffice().equals("") ? ", "+address.getHouseOffice():"");

                address.setValue(value);
                Integer count = userDao.updateAddress(address, userId);
                return count;
            } catch (Exception e) {
                LOGGER.error("ERROR>>> {}, {}", e, e.getMessage());
                throw e;
            }
        }
    }

    @Override
    public Integer deleteAddress(Integer idAddress, Integer userId) {
        LOGGER.debug("deleteAddress({}, {})", idAddress, userId);
        Assert.notNull(idAddress, "idAddress = null");
        Assert.isTrue(idAddress > 0, "idAddress <= 0");
        Assert.notNull(userId, "userId = null");
        Assert.isTrue(userId > 0, "userId <= 0");
        return userDao.deleteAddress(idAddress, userId);
    }


    @Override
    public Address getUserAddress(Integer id) {
        LOGGER.debug("getUserAddress({})", id);
        try {
            Assert.notNull(id, "Идентификатор адреса = null");
            Assert.isTrue(id > 0, "Идентификатор адреса <= 0");
            return userDao.getAddressById(id);
        } catch (Exception e) {
            LOGGER.error("ERROR >>> {}", e.getMessage());
            return null;
        }
    }

    @Override
    public HashMap<Integer,ArrayList<Address>> getUserAddresses(Integer userId) {
        LOGGER.debug("getUserAddresses({})", userId);
        HashMap<Integer,ArrayList<Address>> addresses = null;
        try {
            addresses = userDao.getUserAddresses(userId);
            return addresses;
        } catch (Exception e) {
            LOGGER.error("ERROR >>> {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Object addPasswordRecoveryRequest(PasswordRecoveryRequest passwordRecoveryRequest) {
        LOGGER.debug("addPasswordRecoveryRequest()");

        try {
            Assert.isTrue(!StringUtils.isBlank(passwordRecoveryRequest.getUserLogin()),
                    "Укажите свой логин или УНП при регистрации");
            User user = getUserByLogin(passwordRecoveryRequest.getUserLogin());
            if(user == null) {
                user = userDao.getUserByEnterpriseUNP(passwordRecoveryRequest.getUserLogin());
            }
            Assert.notNull(user, "Пользователь не найден");

            LOGGER.debug("Пользователь найден: {}", user);

            passwordRecoveryRequest.setUserId(user.getId());
            passwordRecoveryRequest.setEmail(user.getEmail());
            passwordRecoveryRequest.setToken(UUID.randomUUID().toString());

            passwordRecoveryRequest.setId(userDao.addPasswordRecoveryRequest(passwordRecoveryRequest));

            return passwordRecoveryRequest;

        } catch (IllegalArgumentException e) {
            LOGGER.error("VALIDATE EXCEPTION >>>\nERROR MESSAGE: {}", e.getMessage());
            return new ErrorMessages(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("addPasswordRecoveryRequest EXCEPTION >>>\nERROR MESSAGE: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public PasswordRecoveryRequest getPasswordRecoveryRequestById (Integer id) {
        LOGGER.debug("getPasswordRecoveryRequestById({})", id);
        try {
            Assert.notNull(id, "id = null");
            Assert.isTrue(id > 0, "id =< 0");
            return userDao.getPasswordRecoveryRequestById(id);
        } catch (Exception e) {
            LOGGER.debug("ERROR: {}", e);
            return null;
        }
    }

    @Override
    public PasswordRecoveryRequest getPasswordRecoveryRequestByToken(String token) {
        LOGGER.debug("getPasswordRecoveryRequestByToken({})", token);
        try {
            Assert.notNull(token, "token = null");
            Assert.hasText(token, "token is empty");
            return userDao.getPasswordRecoveryRequestByToken(token);
        } catch (Exception e) {
            LOGGER.debug("ERROR: {}", e);
            return null;
        }
    }

    @Override
    public void expireAllPasswordRecoveryRequestsByUserId(Integer userId) {
        LOGGER.debug("expireAllPasswordRecoveryRequestsByUserId({})", userId);
        try {
            userDao.expireAllPasswordRecoveryRequestsByUserId(userId);
        } catch (Exception e) {
            LOGGER.debug("ERROR: {}", e);
        }
    }

    @Override
    public void performPasswordRecoveryRequest(Integer id) {
        LOGGER.debug("performPasswordRecoveryRequest({})", id);
        try {
            userDao.performPasswordRecoveryRequest(id);
        } catch (Exception e) {
            LOGGER.debug("ERROR: {}", e);
        }
    }

    public String updateCustomers() {
        LOGGER.debug("updateCustomers()");
        StringBuilder sb = new StringBuilder();
        HashMap<String, User> CSVCustomers = getCustomersFromCSV();
        Assert.notEmpty(CSVCustomers);

        for (Map.Entry<String, User> userEntry: CSVCustomers.entrySet()) {
            try {
                Object respForm = registrateUser(userEntry.getValue());
                if (respForm instanceof ErrorRegistrateForm) {
                    LOGGER.error("ERROR: {}", respForm);
                }
            } catch (Exception e) {
                LOGGER.error("ERROR: {}", e.getMessage());
            }
        }
        return sb.toString();
    }

    private HashMap<String, User> getCustomersFromCSV() {
        return userDao.getCustomersFromCSV();
    }

    private Object registrateUser(User user) throws ServiceException {
        LOGGER.debug("NEW USER REGISTRATION >>>");
        ErrorRegistrateForm errorRegistrateForm = new ErrorRegistrateForm();

        try {
            Assert.notNull(user.getLogin(), "УНП используется в качестве логина и не может быть пустым");
            Assert.hasText(user.getLogin(), "УНП используется в качестве логина и не может быть пустым");
            Assert.isTrue(user.getLogin().length() == 9, "УНП должно быть 9 знаков");
            Assert.isNull(userDao.getUserByLogin(user.getLogin()),
                    "Пользователь под таким логином уже присутствует в базе даннных");
        } catch (IllegalArgumentException e) {
            errorRegistrateForm.setUNP(e.getMessage());
        }

        try {
            Assert.notNull(user.getEmail(),
                    "адрес электронной почты не может быть пустым");
            Assert.hasText(user.getEmail(),
                    "адрес электронной почты не может быть пустым");
            Assert.isTrue(validateEmail(user.getEmail()),
                    "некорректный адрес электронной почты");
        } catch (IllegalArgumentException e) {
            errorRegistrateForm.setEmail(e.getMessage());
        }
        try {
            Assert.notNull(user.getName(), "Имя пользователя не должно быть пустым");
            Assert.hasText(user.getName(), "Имя пользователя не должно быть пустым");
        } catch (IllegalArgumentException e) {
            errorRegistrateForm.setName(e.getMessage());
        }

        try {
            Assert.notNull(user.getPassword(), "Пароль не может быть пустым");
            Assert.hasText(user.getPassword(), "Пароль не может быть пустым");
            Assert.isTrue(user.getPassword().length() > 2,
                        "Длина пароля не должна быть меньше 2 символов");
        } catch (IllegalArgumentException e) {
            errorRegistrateForm.setPassword(e.getMessage());
        }

        // END VALIDATING ================================================================
        if (errorRegistrateForm.isErrors()) {
            LOGGER.error("REGISTRATION FAILED! >>>\nERROR FORM: {}", errorRegistrateForm);
            return errorRegistrateForm;
        } else {
            user.setPassword(encoding(user.getPassword()));
            try {
                Integer newUserId = userDao.addUserDoNotUseIt(user);

                LOGGER.debug("REGISTRATION SUCCESSFULLY! >>> \nNEW USER: {}", newUserId);
                return newUserId;
            } catch (Exception e) {
                LOGGER.error("REGISTRATION FAILED! >>>\nERROR MESSAGE: {}", e.getMessage());
                throw e;
            }
        }
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

    private void validateAddress(Address address, AddressErrorResponse addressErrorResponse) {

        try {
            Assert.isTrue(address.getIndex().length() < 10, "Слишком длинное значение (не более 10 символов)");
        } catch (Exception e) {
            addressErrorResponse.setIndex(e.getMessage());
        }

        try {
            Assert.notNull(address.getCity(), "Пожалуйста укажите город доставки");
            Assert.hasText(address.getCity(), "Пожалуйста укажите город доставки");
            Assert.isTrue(address.getCity().length() < 30, "Слишком длинное значение (не более 30 символов)");
        } catch (Exception e) {
            addressErrorResponse.setCity(e.getMessage());
        }

        try {
            Assert.notNull(address.getStreet(), "Пожалуйста укажите улицу");
            Assert.hasText(address.getStreet(), "Пожалуйста укажите улицу");
            Assert.isTrue(address.getStreet().length() < 100, "Слишком длинное значение (не более 100 символов)");
        } catch (Exception e) {
            addressErrorResponse.setStreet(e.getMessage());
        }

        try {
            Assert.notNull(address.getHouse(), "Пожалуйста укажите номер дома");
            Assert.hasText(address.getHouse(), "Пожалуйста укажите номер дома");
            Assert.isTrue(address.getHouse().length() < 5, "Слишком длинное значение (не более 5 символов)");

        } catch (Exception e) {
            addressErrorResponse.setHouse(e.getMessage());
        }

        try {
            Assert.isTrue(address.getHousePart().length() < 5, "Слишком длинное значение (не более 5 символов)");
        } catch (Exception e) {
            addressErrorResponse.setHousePart(e.getMessage());
        }

        try {
            Assert.isTrue(address.getHouseOffice().length() < 5, "Слишком длинное значение (не более 5 символов)");
        } catch (Exception e) {
            addressErrorResponse.setHouseOffice(e.getMessage());
        }

        try {
            Assert.isTrue(address.getDescription().length() < 1500, "Слишком длинное значение (не более 1500 символов)");
        } catch (Exception e) {
            addressErrorResponse.setDescription(e.getMessage());
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
