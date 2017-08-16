package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.RegistrateForm;
import com.kushnir.paperki.model.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    private static final String P_USER_ID = "p_user_id";
    private static final String P_USER_LOGIN = "p_user_login";
    private static final String P_USER_EMAIL = "p_user_email";
    private static final String P_USER_PASSWORD = "p_user_password";
    private static final String P_USER_NAME = "p_user_name";
    private static final String P_USER_PHONE = "p_user_phone";
    private static final String P_USER_SUBSCRIBE = "p_user_subscribe";
    private static final String P_USER_BIRTH_DAY = "p_birth_day";
    private static final String P_USER_TYPE = "p_user_type";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${user.getByLogin}")
    private String getUserByLoginSqlQuery;

    @Value("${user.getByLoginPassword}")
    private String getUserByLoginPasswordSqlQuery;

    @Value("${user.getById}")
    private String getUserByIdSqlQuery;

    @Value("${user.add}")
    private String addUserSqlQuery;

    @Override
    public User getUserByLoginPassword(String userName, String password) throws DataAccessException {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(P_USER_LOGIN, userName);
        parameterSource.addValue(P_USER_PASSWORD, password);
        try {
            User user = namedParameterJdbcTemplate
                    .queryForObject(getUserByLoginPasswordSqlQuery, parameterSource, new UserRowMapper());
            LOGGER.debug("getUserByLogin({}) >>> {}", userName, user);
            return user;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Пользователь {}, не найден {}",userName, e.getMessage());
            return null;
        }
    }

    public User getUserByLogin(String login) throws DataAccessException {
        LOGGER.debug("getUserByLogin({}) >>>", login);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_USER_LOGIN, login);
        try {
            User user = namedParameterJdbcTemplate
                    .queryForObject(getUserByLoginSqlQuery, parameterSource, new UserRowMapper());
            LOGGER.debug("Пользователь {} - найден! >>> {}", login, user);
            return user;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("Пользователь {} - не найден {}",login, e.getMessage());
            return null;
        } catch (Exception e) {
            LOGGER.error("В процессе выполнения запроса getUserByLogin, возникла ошибка >>>\n{}", e.getMessage());
            throw e;
        }
    }

    @Override
    public User getUserById(Integer userId) throws DataAccessException {
        LOGGER.debug("getUserById({}) >>>", userId);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_USER_ID, userId);
        try{
            User user = namedParameterJdbcTemplate
                    .queryForObject(getUserByIdSqlQuery, parameterSource, new UserRowMapper());
            LOGGER.debug("Пользователь id: {} - найден! >>> {}", userId, user);
            return user;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("Пользователь с id: {} - не найден {}",userId , e.getMessage());
            return null;
        } catch (Exception e) {
            LOGGER.error("В процессе выполнения запроса getUserById, возникла ошибка >>>\n{}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Integer addUser(RegistrateForm form) throws DataAccessException {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        parameterSource.addValue(P_USER_NAME,           form.getName());
        parameterSource.addValue(P_USER_LOGIN,          form.getEmail());
        parameterSource.addValue(P_USER_EMAIL,          form.getEmail());
        parameterSource.addValue(P_USER_SUBSCRIBE,      form.getSubscribe());
        parameterSource.addValue(P_USER_PASSWORD,       form.getPassword());
        parameterSource.addValue(P_USER_PHONE,          form.getPhone());
        // parameterSource.addValue(P_USER_BIRTH_DAY,      form.getBirthDate());
        try {
            namedParameterJdbcTemplate.update(addUserSqlQuery, parameterSource, keyHolder);
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            LOGGER.error("Не удалось добавить нового ползователя >>>\nERROR >>> {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Integer addUser(User user) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        parameterSource.addValue(P_USER_NAME,           user.getName());
        parameterSource.addValue(P_USER_LOGIN,          user.getEmail());
        parameterSource.addValue(P_USER_SUBSCRIBE,      user.getSubscribe());
        parameterSource.addValue(P_USER_PASSWORD,       user.getPassword());
        parameterSource.addValue(P_USER_PHONE,          user.getPhone());
        // parameterSource.addValue(P_USER_BIRTH_DAY,      user.getBirthDay().format(DateTimeFormatter.ISO_LOCAL_DATE));
        try {
            namedParameterJdbcTemplate.update(addUserSqlQuery, parameterSource, keyHolder);
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            LOGGER.error("Не удалось добавить нового ползователя >>>\nERROR >>> {}", e.getMessage());
            throw e;
        }
    }


    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(
                    rs.getInt("id_user"),
                    rs.getString("login_user"),
                    rs.getString("name_user"),
                    rs.getString("password")
            );
            return user;
        }
    }
}
