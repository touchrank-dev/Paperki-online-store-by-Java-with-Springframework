package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.callback.Callback;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class CallBackDaoImpl implements CallBackDao {

    private static final Logger LOGGER = LogManager.getLogger(CallBackDaoImpl.class);

    private final String P_NAME = "p_name";
    private final String P_PHONE = "p_phone";
    private final String P_COMMENT = "p_comment";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${callback.add}")
    private String addCallbackSqlQuery;

    @Override
    public int addCallback(Callback callback) throws DataAccessException {
        LOGGER.debug("addCallback({})", callback);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(P_NAME, callback.getName());
        parameterSource.addValue(P_PHONE, callback.getPhone());
        parameterSource.addValue(P_COMMENT, callback.getComment());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try{
            namedParameterJdbcTemplate.update(addCallbackSqlQuery, parameterSource, keyHolder);
            LOGGER.debug("Запрос на обратный звонок успешно записан");
            return keyHolder.getKey().intValue();
        } catch (Exception e) {
            LOGGER.error("Не удалось записать запроc на обратный звонок!," +
                    "\nError message: {}", e.getMessage());
            throw e;
        }
    }
}
