package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.BillingAccount;
import com.kushnir.paperki.model.Enterprise;
import com.kushnir.paperki.model.RegistrateForm;
import com.kushnir.paperki.model.user.Address;
import com.kushnir.paperki.model.user.PasswordRecoveryRequest;
import com.kushnir.paperki.model.user.User;

import com.kushnir.paperki.model.user.UserUpdateRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

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

    private static final String P_ADDRESS_TYPE = "p_id_address_type";
    private static final String P_ADDRESS_ID = "p_address_id";
    private static final String P_OWNER_ID = "p_owner_id";
    private static final String P_POST_INDEX = "p_post_index";
    private static final String P_CITY = "p_city";
    private static final String P_STREET = "p_street";
    private static final String P_HOUSE = "p_house";
    private static final String P_HOUSE_PART = "p_house_part";
    private static final String P_HOUSE_OFFICE = "p_house_office";
    private static final String P_ADDRESS_VALUE = "p_value";
    private static final String P_DESCRIPTION = "p_description";

    private static final String P_ID = "p_id";
    private static final String P_TOKEN = "p_token";
    private static final String P_IP_ADDRESS = "p_ip_address";


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

    @Value("${user.update}")
    private String updateUserSqlQuery;

    @Value("${user.update.password}")
    private String updateUserPasswordSqlQuery;

    @Value("${enterprise.getByUNP}")
    private String getEnterpriseByUNPSqlQuery;

    @Value("${enterprise.getByUserId}")
    private String getEnterpriseByUserIdSqlQuery;

    @Value("${enterprise.add}")
    private String addEnterpriseByUserIdSqlQuery;

    @Value("${enterprise.update}")
    private String updateEnterpriseByUserIdSqlQuery;

    @Value("${payment.account.add}")
    private String addBillingAccountByEnterpriseIdSqlQuery;

    @Value("${address.add}")
    private String addAddressSqlQuery;

    @Value("${address.update}")
    private String updateAddressSqlQuery;

    @Value("${address.delete}")
    private String deleteAddressSqlQuery;

    @Value("${address.getById}")
    private String getAddressByIdSqlQuery;

    @Value("${address.getByUserId}")
    private String getAllUsersAddressesSqlQuery;

    @Value("${password.request.add}")
    private String addPasswordRequestSqlQuery;

    @Value("${password.request.getById}")
    private String getPasswordRequestByIdSqlQuery;

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
    public Enterprise getEnterpriseByUserId(Integer userId) {
        LOGGER.debug("getEnterpriseByUserId({}) >>>", userId);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_USER_ID, userId);
        try {
            Enterprise enterprise = namedParameterJdbcTemplate
                    .query(getEnterpriseByUserIdSqlQuery, parameterSource, new EnterpriseResultSetExtractor());
            return enterprise;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("Юридическое лицо для пользователя id {} - не найдено >>>\n ERROR: {}",
                    userId, e.getMessage());
            return null;
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
    public Integer updateEnterprise(Enterprise enterprise) {
        LOGGER.debug("updateEnterprise() >>>\nENTERPRISE DATA: {}", enterprise);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(P_USER_ID, enterprise.getUserId());
        parameterSource.addValue(P_ENTERPRISE_NAME, enterprise.getEnterpriseName());
        return namedParameterJdbcTemplate.update(updateEnterpriseByUserIdSqlQuery, parameterSource);
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
        parameterSource.addValue(P_USER_ID, userId);
        parameterSource.addValue(P_USER_PASSWORD, newPassword);
        return namedParameterJdbcTemplate.update(updateUserPasswordSqlQuery, parameterSource);
    }

    @Override
    public Integer updateUser (UserUpdateRequest userUpdateRequest, Integer userId) {
        LOGGER.debug("updateUser()");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(P_USER_ID, userId);
        parameterSource.addValue(P_USER_NAME, userUpdateRequest.getName());
        parameterSource.addValue(P_USER_EMAIL, userUpdateRequest.getEmail());
        parameterSource.addValue(P_USER_PHONE, userUpdateRequest.getPhone());
        parameterSource.addValue(P_USER_BIRTH_DAY, userUpdateRequest.getBirthday());
        return namedParameterJdbcTemplate.update(updateUserSqlQuery, parameterSource);
    }

    @Override
    public Integer addAddress(Address address, Integer userId) {
        LOGGER.debug("addAddress()");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(P_ADDRESS_TYPE, address.getType());
        parameterSource.addValue(P_OWNER_ID, address.getOwnerId());
        parameterSource.addValue(P_USER_ID, userId);
        parameterSource.addValue(P_POST_INDEX, address.getIndex());
        parameterSource.addValue(P_CITY, address.getCity());
        parameterSource.addValue(P_STREET, address.getStreet());
        parameterSource.addValue(P_HOUSE, address.getHouse());
        parameterSource.addValue(P_HOUSE_PART, address.getHousePart());
        parameterSource.addValue(P_HOUSE_OFFICE, address.getHouseOffice());
        parameterSource.addValue(P_ADDRESS_VALUE, address.getValue());
        parameterSource.addValue(P_DESCRIPTION, address.getDescription());
        return namedParameterJdbcTemplate.update(addAddressSqlQuery, parameterSource);
    }

    @Override
    public Integer updateAddress(Address address, Integer userId) {
        LOGGER.debug("addAddress()");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(P_POST_INDEX, address.getIndex());
        parameterSource.addValue(P_CITY, address.getCity());
        parameterSource.addValue(P_STREET, address.getStreet());
        parameterSource.addValue(P_HOUSE, address.getHouse());
        parameterSource.addValue(P_HOUSE_PART, address.getHousePart());
        parameterSource.addValue(P_HOUSE_OFFICE, address.getHouseOffice());
        parameterSource.addValue(P_ADDRESS_VALUE, address.getValue());
        parameterSource.addValue(P_DESCRIPTION, address.getDescription());
        parameterSource.addValue(P_ADDRESS_ID, address.getId());
        parameterSource.addValue(P_USER_ID, userId);
        return namedParameterJdbcTemplate.update(updateAddressSqlQuery, parameterSource);
    }

    @Override
    public Integer deleteAddress(Integer idAddress, Integer userId) {
        LOGGER.debug("deleteAddress()");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(P_ADDRESS_ID, idAddress);
        parameterSource.addValue(P_USER_ID, userId);
        return namedParameterJdbcTemplate.update(deleteAddressSqlQuery, parameterSource);
    }


    @Override
    public Address getAddressById(Integer addressId) {
        LOGGER.debug("getAddressById()");
        Address address;
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_ADDRESS_ID, addressId);
        address = namedParameterJdbcTemplate
                .queryForObject(getAddressByIdSqlQuery, parameterSource, new AddressRowMapper());

        return address;
    }

    @Override
    public HashMap<Integer,ArrayList<Address>> getUserAddresses(Integer userId) {
        LOGGER.debug("getUserAddresses()");
        HashMap<Integer,ArrayList<Address>> addresses = null;
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_USER_ID, userId);
        addresses = namedParameterJdbcTemplate.query(getAllUsersAddressesSqlQuery, parameterSource, new AddressesResultSetExtractor());
        return addresses;
    }

    @Override
    public Integer addPasswordRecoveryRequest(PasswordRecoveryRequest passwordRecoveryRequest) {
        LOGGER.debug("addPasswordRecoveryRequest()");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(P_TOKEN, passwordRecoveryRequest.getToken());
        parameterSource.addValue(P_USER_ID, passwordRecoveryRequest.getUserId());
        parameterSource.addValue(P_USER_LOGIN, passwordRecoveryRequest.getUserLogin());
        parameterSource.addValue(P_USER_EMAIL, passwordRecoveryRequest.getEmail());
        parameterSource.addValue(P_IP_ADDRESS, passwordRecoveryRequest.getIpAddress());
        namedParameterJdbcTemplate.update(addPasswordRequestSqlQuery, parameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public PasswordRecoveryRequest getPasswordRecoveryRequestById (Integer id) {
        LOGGER.debug("getPasswordRecoveryRequestById()");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_ID, id);
        return namedParameterJdbcTemplate.queryForObject(
                getPasswordRequestByIdSqlQuery,
                parameterSource,
                new PasswordRecoveryRequestRowMapper());
    }

    @Override
    public PasswordRecoveryRequest getPasswordRecoveryRequestByToken(String token) {
        return null;
    }


    private class PasswordRecoveryRequestRowMapper implements RowMapper<PasswordRecoveryRequest> {

        @Override
        public PasswordRecoveryRequest mapRow(ResultSet rs, int i) throws SQLException {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return new PasswordRecoveryRequest (
                    rs.getInt("id_request"),
                    rs.getString("token"),
                    rs.getInt("id_user"),
                    rs.getString("user_login"),
                    rs.getString("email"),
                    rs.getString("ip_address"),
                    LocalDateTime.parse(rs.getString("create_date"), formatter),
                    rs.getBoolean("is_expired"),
                    rs.getBoolean("is_performed")
            );
        }
    }


    private class AddressRowMapper implements RowMapper<Address> {

        @Override
        public Address mapRow(ResultSet rs, int i) throws SQLException {
            Integer ownerId =           rs.getInt("owner_id");
            Integer type =              rs.getInt("id_address_type");
            String index =              rs.getString("post_index");
            String city =               rs.getString("city");
            String street =             rs.getString("street");
            String house =              rs.getString("house");
            String housePart =          rs.getString("house_part");
            String houseOffice =        rs.getString("house_office");
            Integer userId =            rs.getInt("id_user");
            String value =              rs.getString("value");
            String description =        rs.getString("description");
            Integer id =                rs.getInt("id_address");

            return new Address(
                    ownerId,
                    type,
                    index,
                    city,
                    street,
                    house,
                    housePart,
                    houseOffice,
                    userId,
                    value,
                    description,
                    id
            );
        }
    }



    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDate birthDay = null;
            LocalDateTime createDate = null;
            LocalDateTime editDate = null;

            Date bd =               rs.getDate("birth_day");
            String createDateStr =  rs.getString("create_date");
            String editDateStr =    rs.getString("edit_date");

            try {
                birthDay =          bd.toLocalDate();
                createDate =        LocalDateTime.parse(createDateStr, formatter);
                editDate =          LocalDateTime.parse(editDateStr, formatter);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }

            User user = new User(
                    rs.getInt("id_user"),
                    rs.getString("login_user"),
                    rs.getString("password"),
                    rs.getString("name_user"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getBoolean("subscribe"),
                    birthDay,
                    createDate,
                    editDate
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

    private class EnterpriseResultSetExtractor implements ResultSetExtractor<Enterprise> {

        @Override
        public Enterprise extractData(ResultSet rs) throws SQLException, DataAccessException {
            Enterprise enterprise = null;
            while(rs.next()) {
                Integer id =                rs.getInt("id_enterprise");
                Integer userId =            rs.getInt("id_user");
                String unp =                rs.getString("unp");
                String enterpriseName =     rs.getString("name");

                Integer accId =             rs.getInt("id_payment_account");
                String bankName =           rs.getString("bank_name");
                String bankCode =           rs.getString("bank_code");
                String accountNumber =      rs.getString("account_number");
                Boolean isPrimary =         rs.getBoolean("is_primary");

                BillingAccount billingAccount = null;
                if (accId > 0) {
                    billingAccount = new BillingAccount(
                            accId,
                            id,
                            accountNumber,
                            bankName,
                            bankCode,
                            isPrimary
                    );
                }

                if(enterprise == null) {
                    enterprise = new Enterprise(
                            id,
                            userId,
                            unp,
                            enterpriseName
                    );
                    enterprise.getBillingAccount().add(billingAccount);
                } else {
                    enterprise.getBillingAccount().add(billingAccount);
                }

            }
            return enterprise;
        }
    }


    private class AddressesResultSetExtractor implements ResultSetExtractor<HashMap<Integer,ArrayList<Address>>> {

        @Override
        public HashMap<Integer, ArrayList<Address>> extractData(ResultSet rs) throws SQLException, DataAccessException {
            HashMap<Integer, ArrayList<Address>> addresses = new HashMap();
            while(rs.next()) {

                Integer id =                rs.getInt("id_address");
                Integer typeId =            rs.getInt("id_address_type");
                Integer ownerId =           rs.getInt("owner_id");
                Integer userId =            rs.getInt("id_user");

                String index =              rs.getString("post_index");
                String city =               rs.getString("city");
                String street =             rs.getString("street");
                String house =              rs.getString("house");
                String housePart =          rs.getString("house_part");
                String houseOffice =        rs.getString("house_office");
                String value =              rs.getString("value");
                String description =        rs.getString("description");

                Address address = new Address(
                        ownerId,
                        typeId,
                        index,
                        city,
                        street,
                        house,
                        housePart,
                        houseOffice,
                        userId,
                        value,
                        description,
                        id
                );

                ArrayList<Address> addressList = addresses.get(ownerId);
                if (addressList == null) {
                    addressList = new ArrayList();
                    addressList.add(address);
                    addresses.put(ownerId, addressList);
                } else {
                    addressList.add(address);
                }
            }
            return addresses;
        }
    }
}
