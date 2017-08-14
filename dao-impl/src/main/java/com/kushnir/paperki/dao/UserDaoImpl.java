package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    private static final String P_USER_LOGIN = "p_user_login";
    private static final String P_USER_PASSWORD = "p_user_password";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${user.getByLogin}")
    private String getUserByLoginSqlQuery;

    @Value("${user.getByLoginPassword}")
    private String getUserByLoginPasswordSqlQuery;

    @Override
    public User getUserByLoginPassword(String userName, String password) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(P_USER_LOGIN, userName);
        parameterSource.addValue(P_USER_PASSWORD, password);
        try {
            User user = namedParameterJdbcTemplate
                    .queryForObject(getUserByLoginPasswordSqlQuery, parameterSource, new UserRowMapper());
            LOGGER.debug("getUserByLoginPassword({}) >>> {}", userName, user);
            return user;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Пользователь {}, не найден {}",userName, e.getMessage());
            return null;
        }
    }

    public User getUserByLogin(String login){
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(P_USER_LOGIN, login);
        try {
            User user = namedParameterJdbcTemplate
                    .queryForObject(getUserByLoginSqlQuery, parameterSource, new UserRowMapper());
            LOGGER.debug("getUserByLogin({}) >>> {}", login, user);
            return user;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Пользователь {}, не найден {}",login, e.getMessage());
            return null;
        }
    }






    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(
                    rs.getInt("id_user"),
                    rs.getString("login_user"),
                    rs.getNString("name_user"),
                    rs.getString("password")
            );
            return user;
        }
    }
}
