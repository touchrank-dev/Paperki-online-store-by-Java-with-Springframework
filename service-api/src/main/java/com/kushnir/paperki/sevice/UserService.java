package com.kushnir.paperki.sevice;

import com.kushnir.paperki.model.RegistrateForm;
import com.kushnir.paperki.model.User;

public interface UserService {
    User getUserByLoginPassword(String userName, String password);
    User registrateUser(RegistrateForm form);
}
