package com.kushnir.paperki.sevice;

import com.kushnir.paperki.model.User;

public interface UserService {
    User getUserByLogin(String userName);
}
