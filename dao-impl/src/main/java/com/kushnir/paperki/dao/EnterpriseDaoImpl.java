package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.Enterprise;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EnterpriseDaoImpl implements EnterpriseDao {

    private static final String P_USER_ID = "p_user_id";

    private static final Logger LOGGER = LogManager.getLogger(EnterpriseDaoImpl.class);

    @Value("${enterprise.getByUserId}")
    private String getEnterpriseByUserId;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Enterprise getEnterpriseByUserId(int UserId) {
        LOGGER.debug("getEnterpriseByUserId({}) >>>", UserId);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_USER_ID, UserId);
        try {
            Enterprise enterprise = (Enterprise) namedParameterJdbcTemplate.query(
                    getEnterpriseByUserId, parameterSource, new EnterpriseResultSetExtractor()
            );
            return enterprise;
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            LOGGER.error("Не удалось выполнить getEnterpriseByUserId >>>\nERROR MESSAGE: {}", e.getMessage());
            throw e;
        }
    }

    private class EnterpriseResultSetExtractor implements ResultSetExtractor {

        @Override
        public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
            Enterprise enterprise = null;
            while (rs.next()) {


                
            }
            return enterprise;
        }
    }
}
