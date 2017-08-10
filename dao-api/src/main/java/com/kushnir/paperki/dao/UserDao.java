package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.User;

public interface UserDao {
    User getUserByLogin(String userName);
}
