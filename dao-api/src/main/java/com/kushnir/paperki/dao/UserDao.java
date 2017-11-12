package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.BillingAccount;
import com.kushnir.paperki.model.Enterprise;
import com.kushnir.paperki.model.RegistrateForm;
import com.kushnir.paperki.model.user.Address;
import com.kushnir.paperki.model.user.User;
import com.kushnir.paperki.model.user.UserUpdateRequest;

import java.util.ArrayList;
import java.util.HashMap;

public interface UserDao {
    User getUserByLoginPassword(String userName, String password);
    User getUserByLogin(String login);
    User getUserByEnterpriseUNP(String UNP);
    User getUserById(Integer userId);
    Integer addUser(RegistrateForm form);
    Integer addUser(User user);
    Enterprise getEnterpriseByUNP(String unp);
    Enterprise getEnterpriseByUserId(Integer userId);
    Integer addEnterprise(Enterprise enterprise);
    Integer updateEnterprise(Enterprise enterprise);
    Integer addBillingAccount(BillingAccount billingAccount);
    Integer updateUserPassword(String newPassword, Integer userId);
    Integer updateUser (UserUpdateRequest userUpdateRequest, Integer userId);
    Integer addAddress (Address address, Integer userId);
    HashMap<Integer,ArrayList<Address>> getUserAddresses(Integer userId);
}
