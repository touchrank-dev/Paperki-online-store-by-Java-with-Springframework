package com.kushnir.paperki.service;

import com.kushnir.paperki.model.LoginData;
import com.kushnir.paperki.model.RegistrateForm;
import com.kushnir.paperki.model.User;

public interface UserService {
    Object getUserByLoginPassword(LoginData loginData);
    Object registrateUser(RegistrateForm form);
}
