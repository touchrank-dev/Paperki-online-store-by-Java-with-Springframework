package com.kushnir.paperki.service;

import com.kushnir.paperki.model.Enterprise;
import com.kushnir.paperki.model.LoginData;
import com.kushnir.paperki.model.RegistrateForm;
import com.kushnir.paperki.model.password.NewPasswordForm;
import com.kushnir.paperki.model.user.Address;
import com.kushnir.paperki.model.user.PasswordRecoveryRequest;
import com.kushnir.paperki.model.user.User;
import com.kushnir.paperki.model.user.UserUpdateRequest;
import com.kushnir.paperki.service.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.HashMap;

public interface UserService {
    Object getUserByLoginPassword(LoginData loginData);
    User getUserByLogin(String login);
    User getUserById(Integer id);
    Enterprise getEnterpriseByUserId(Integer userId);
    Object registrateUser(RegistrateForm form) throws ServiceException;
    Integer addEnterpriseByUser (RegistrateForm form, Integer userId) throws ServiceException;
    Object addEnterpriseByUser (HashMap data, Integer userId) throws ServiceException;
    Object updateEnterpriseByUser (HashMap data, Integer userId) throws Exception;
    Object changePassword(NewPasswordForm newPasswordForm, Integer UserId);
    Object updateUser(UserUpdateRequest userUpdateRequest, Integer UserId);
    Object addAddress(Address address, Integer userId);
    Object updateAddress(Address address, Integer userId);
    Integer deleteAddress(Integer idAddress, Integer userId);
    Address getUserAddress(Integer id);
    HashMap<Integer,ArrayList<Address>> getUserAddresses(Integer userId);

    Object addPasswordRecoveryRequest(PasswordRecoveryRequest passwordRecoveryRequest);
    PasswordRecoveryRequest getPasswordRecoveryRequestById (Integer id);
    PasswordRecoveryRequest getPasswordRecoveryRequestByToken(String token);
}
