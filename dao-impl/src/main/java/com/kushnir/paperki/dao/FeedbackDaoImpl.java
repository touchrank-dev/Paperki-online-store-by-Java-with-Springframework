package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.feedback.Feedback;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository("feedbackDao")
public class FeedbackDaoImpl implements FeedbackDao {

    private static final Logger LOGGER = LogManager.getLogger(FeedbackDaoImpl.class);

    private final String P_ID_USER = "p_id_user";
    private final String P_USER_NAME = "p_user_name";
    private final String P_EMAIL = "p_email";
    private final String P_IP_ADDRESS = "p_ip_address";
    private final String P_TEXT = "p_text";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${feedback.add}")
    private String addFeedbackSqlQuery;

        @Value("${feedback.getAllApprove}")
    private String getAllApproveSqlQuery;

    @Override
    public Integer addFeedback(Feedback feedback) throws DataAccessException {
        LOGGER.debug("addFeedback({})", feedback);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(P_ID_USER, feedback.getIdUser());
        parameterSource.addValue(P_USER_NAME, feedback.getUserName());
        parameterSource.addValue(P_EMAIL, feedback.getEmail());
        parameterSource.addValue(P_IP_ADDRESS, feedback.getIpAddress());
        parameterSource.addValue(P_TEXT, feedback.getText());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(addFeedbackSqlQuery, parameterSource, keyHolder);
        LOGGER.debug("Отзыв успешно записан");
        return keyHolder.getKey().intValue();
    }

    @Override
    public ArrayList<Feedback> getAllFeedback() {
        LOGGER.debug("getAllFeedback()");
        ArrayList<Feedback> feedbacks;
        feedbacks = (ArrayList<Feedback>)
                namedParameterJdbcTemplate.query(getAllApproveSqlQuery, new FeedbackRowMapper());
        return feedbacks;
    }


    private class FeedbackRowMapper implements RowMapper<Feedback> {

        @Override
        public Feedback mapRow(ResultSet rs, int i) throws SQLException {
            Feedback feedback = new Feedback(
                    rs.getInt("id_user"),
                    rs.getString("user_name"),
                    rs.getString("email"),
                    rs.getString("ip_address"),
                    rs.getInt("rate"),
                    rs.getString("text"),
                    rs.getDate("create_date").toLocalDate(),
                    rs.getBoolean("approve"),
                    rs.getBoolean("is_published")
            );
            return feedback;
        }
    }
}
