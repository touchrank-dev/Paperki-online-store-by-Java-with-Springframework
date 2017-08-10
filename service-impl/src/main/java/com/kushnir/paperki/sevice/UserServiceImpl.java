package com.kushnir.paperki.sevice;

import com.kushnir.paperki.dao.UserDao;
import com.kushnir.paperki.model.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserServiceImpl implements UserService, UserDetailsService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    UserDao userDao;

    @Override
    public User getUserByLogin(String userName) {
        User user = userDao.getUserByLogin(userName);
        LOGGER.debug("getUserByLogin({}) >>> {}", userName, user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByLogin(username);
    }
}
