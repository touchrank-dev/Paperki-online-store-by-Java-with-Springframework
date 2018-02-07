package com.kushnir.paperki.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository("subscribeDao")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SubscribeDaoImpl implements SubscribeDao {

    private static final Logger LOGGER = LogManager.getLogger(SubscribeDaoImpl.class);

    private final String P_EMAIL = "p_email";
    private final String P_ID_EMAIL_LIST = "p_id_mail_list";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${subscribe.add}")
    private String addSubscribeSqlQuery;

    @Override
    public int subscribe(String email, int idEmailList) throws Exception {
        LOGGER.debug("subscribe()");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(P_ID_EMAIL_LIST, idEmailList);
        parameterSource.addValue(P_EMAIL, email);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(addSubscribeSqlQuery, parameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }
}
