package com.kushnir.paperki.service;

import com.kushnir.paperki.model.LoginData;
import com.kushnir.paperki.model.RegistrateForm;
import com.kushnir.paperki.model.User;
import com.kushnir.paperki.service.exceptions.ServiceException;

public interface UserService {
    Object getUserByLoginPassword(LoginData loginData);
    Object registrateUser(RegistrateForm form) throws ServiceException;
}
