package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.BillingAccount;
import com.kushnir.paperki.model.Enterprise;
import com.kushnir.paperki.model.RegistrateForm;
import com.kushnir.paperki.model.user.User;

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

    private static final String P_ENTERPRISE_ID = "p_id_enterprise";
    private static final String P_ENTERPRISE_UNP = "p_enterprise_unp";
    private static final String P_ENTERPRISE_NAME = "p_enterprise_name";

    private static final String P_BILLING_ACCOUNT_BANK_NAME = "p_bank_name";
    private static final String P_BILLING_ACCOUNT_BANK_CODE = "p_bank_code";
    private static final String P_BILLING_ACCOUNT_NUMBER = "p_account_number";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${user.getByLogin}")
    private String getUserByLoginSqlQuery;

    @Value("${user.getByLoginPassword}")
    private String getUserByLoginPasswordSqlQuery;

    @Value("${user.getById}")
    private String getUserByIdSqlQuery;

    @Value("${user.getByUNP}")
    private String getUserByUNPSqlQuery;

    @Value("${user.add}")
    private String addUserSqlQuery;

    @Value("${user.update.password}")
    private String updateUserPasswordSqlQuery;

    @Value("${enterprise.getByUNP}")
    private String getEnterpriseByUNPSqlQuery;

    @Value("${enterprise.add}")
    private String addEnterpriseByUserIdSqlQuery;

    @Value("${payment.account.add}")
    private String addBillingAccountByEnterpriseIdSqlQuery;

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
        } catch (Exception e) {
            LOGGER.error("В процессе выполнения запроса getUserByLoginPassword, возникла ошибка >>>\n{}", e.getMessage());
            throw e;
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
    public User getUserByEnterpriseUNP(String UNP) throws DataAccessException {
        LOGGER.debug("getUserByEnterpriseUNP({}) >>>", UNP);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_ENTERPRISE_UNP, UNP);
        try {
            User user = namedParameterJdbcTemplate
                    .queryForObject(getUserByUNPSqlQuery, parameterSource, new UserRowMapper());
            return user;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("Пользователь (Enterprise) UNP: {} - не найден {}", UNP, e.getMessage());
            return null;
        } catch (Exception e) {
            LOGGER.error("В процессе выполнения запроса getUserByEnterpriseUNP, возникла ошибка >>>\n{}", e.getMessage());
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
        LOGGER.debug("addUser({}) >>>", form);
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
            LOGGER.debug("USER SUCCESSFULLY ADDED!");
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            LOGGER.error("Не удалось добавить нового ползователя >>>\nERROR: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Integer addUser(User user) throws DataAccessException {
        LOGGER.debug("addUser() >>>\nUSER DATA: {}", user);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        parameterSource.addValue(P_USER_NAME,           user.getName());
        parameterSource.addValue(P_USER_LOGIN,          user.getEmail());
        parameterSource.addValue(P_USER_EMAIL,          user.getEmail());
        parameterSource.addValue(P_USER_SUBSCRIBE,      user.getSubscribe());
        parameterSource.addValue(P_USER_PASSWORD,       user.getPassword());
        parameterSource.addValue(P_USER_PHONE,          user.getPhone());
        // parameterSource.addValue(P_USER_BIRTH_DAY,      user.getBirthDay().format(DateTimeFormatter.ISO_LOCAL_DATE));
        try {
            namedParameterJdbcTemplate.update(addUserSqlQuery, parameterSource, keyHolder);
            LOGGER.debug("USER SUCCESSFULLY ADDED!");
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            LOGGER.error("Не удалось добавить нового ползователя >>>\nERROR: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Enterprise getEnterpriseByUNP(String unp) throws DataAccessException {
        LOGGER.debug("getEnterpriseByUNP({}) >>>", unp);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_ENTERPRISE_UNP, unp);
        try{
            Enterprise enterprise = namedParameterJdbcTemplate
                    .queryForObject(getEnterpriseByUNPSqlQuery, parameterSource, new EnterpriseRowMapper());
            LOGGER.debug("Юридическое лицо УНП: {} - найдено! >>> {}", unp, enterprise);
            return enterprise;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("Юридическое лицо УНП: {} - не найдено >>>\n ERROR: {}",unp , e.getMessage());
            return null;
        } catch (Exception e) {
            LOGGER.error("В процессе выполнения запроса getEnterpriseByUNP, возникла ошибка >>>\nERROR MESSAGE: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Integer addEnterprise(Enterprise enterprise) throws DataAccessException {
        LOGGER.debug("addEnterprise() >>>\nENTERPRISE DATA: {}", enterprise);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(P_USER_ID, enterprise.getUserId());
        parameterSource.addValue(P_ENTERPRISE_UNP, enterprise.getUNP());
        parameterSource.addValue(P_ENTERPRISE_NAME, enterprise.getEnterpriseName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            namedParameterJdbcTemplate.update(addEnterpriseByUserIdSqlQuery, parameterSource, keyHolder);
            LOGGER.debug("ENTERPRISE SUCCESSFULLY ADDED!");
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            LOGGER.error("Не удалось добавить новую организацию >>>\nERROR MESSAGE: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Integer addBillingAccount(BillingAccount billingAccount) throws DataAccessException {
        LOGGER.debug("addBillingAccount() >>>\nBILLING ACCOUNT DATA: {}", billingAccount);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(P_ENTERPRISE_ID, billingAccount.getEnterpriseId());
        parameterSource.addValue(P_BILLING_ACCOUNT_BANK_NAME, billingAccount.getBankName());
        parameterSource.addValue(P_BILLING_ACCOUNT_BANK_CODE, billingAccount.getBankCode());
        parameterSource.addValue(P_BILLING_ACCOUNT_NUMBER, billingAccount.getAccountNumber());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            namedParameterJdbcTemplate.update(addBillingAccountByEnterpriseIdSqlQuery, parameterSource, keyHolder);
            LOGGER.debug("BILLING ACCOUNT SUCCESSFULLY ADDED!");
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            LOGGER.error("Не удалось добавить расчетный счет для организации >>>\nERROR MESSAGE: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Integer updateUserPassword(String newPassword, Integer userId) {
        LOGGER.debug("updateUserPassword()");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        parameterSource.addValue(P_USER_ID, userId);
        parameterSource.addValue(P_USER_PASSWORD, newPassword);
        namedParameterJdbcTemplate.update(updateUserPasswordSqlQuery, parameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }



    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(
                    rs.getInt("id_user"),
                    rs.getString("login_user"),
                    rs.getString("password"),
                    rs.getString("name_user"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getBoolean("subscribe")
            );
            return user;
        }
    }

    private class EnterpriseRowMapper implements RowMapper<Enterprise> {

        @Override
        public Enterprise mapRow(ResultSet rs, int rowNum) throws SQLException {
            Enterprise enterprise = new Enterprise(
                    rs.getInt("id_enterprise"),
                    rs.getInt("id_user"),
                    rs.getString("unp"),
                    rs.getString("name")
            );
            return enterprise;
        }
    }
}
