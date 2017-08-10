package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    private static final String P_USER_LOGIN = "p_user_login";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${user.getByLogin}")
    private String getUserByLoginSqlQuery;

    @Override
    public User getUserByLogin(String userName) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(P_USER_LOGIN, userName);
        User user = namedParameterJdbcTemplate
                .queryForObject(getUserByLoginSqlQuery, parameterSource, new UserRowMapper());
        LOGGER.debug("getUserByLogin({}) >>> {}", userName, user);
        return user;
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
