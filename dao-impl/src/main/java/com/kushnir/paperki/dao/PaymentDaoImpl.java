package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.payment.Payment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class PaymentDaoImpl implements PaymentDao {

    private static final Logger LOGGER = LogManager.getLogger(PaymentDaoImpl.class);

    private final String P_ID_ORDER_TYPE = "p_id_order_type";
    private final String P_PAYMENT_ID = "p_id";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${payment.getAll}")
    private String getAllSqlQuery;

    @Value("${payment.getById}")
    private String getByIdSqlQuery;

    @Override
    public HashMap<Integer, HashMap<Integer, Payment>> getAll() {
        HashMap<Integer, HashMap<Integer, Payment>> payments =
                (HashMap<Integer, HashMap<Integer, Payment>>)
                        namedParameterJdbcTemplate.query(getAllSqlQuery, new PaymentRowSetExtractor());
        return payments;
    }

    @Override
    public Payment getById(int idPayment) {
        LOGGER.debug("getById({})", idPayment);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(P_PAYMENT_ID, idPayment);
        Payment payment = namedParameterJdbcTemplate.queryForObject(getByIdSqlQuery, parameterSource, new PaymentRowMapper());
        return payment;
    }

    private class PaymentRowSetExtractor implements ResultSetExtractor {

        @Override
        public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
            HashMap<Integer, HashMap<Integer, Payment>> payments =
                    new HashMap<Integer, HashMap<Integer, Payment>>();
            while (rs.next()) {
                int idPayment = rs.getInt("id_payment");
                String name = rs.getString("name");
                String shortDescription = rs.getString("short_description");
                String fullDescription = rs.getString("full_description");
                String link = rs.getString("link");
                String icon = rs.getString("icon");
                int orderType = rs.getInt("id_order_type");
                double minCartTotal = rs.getDouble("min_cart_total");
                double price = rs.getDouble("price");

                Payment payment = new Payment(
                        idPayment,
                        orderType,
                        name,
                        shortDescription,
                        fullDescription,
                        link,
                        icon,
                        minCartTotal,
                        price
                );

                HashMap<Integer, Payment> paymentsMap = payments.get(orderType);

                if (paymentsMap == null) {
                    paymentsMap = new HashMap<Integer, Payment>();
                    paymentsMap.put(idPayment, payment);
                    payments.put(orderType, paymentsMap);
                } else {
                    Payment p = paymentsMap.get(idPayment);
                    if (p == null) {
                        paymentsMap.put(idPayment, payment);
                    }
                }
            }
            return payments;
        }
    }

    private class PaymentRowMapper implements RowMapper<Payment> {

        @Override
        public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Payment payment = new Payment(
                    rs.getInt("id_payment"),
                    rs.getString("name"),
                    rs.getString("short_description"),
                    rs.getString("full_description"),
                    rs.getString("link"),
                    rs.getString("icon")
            );
            return payment;
        }
    }
}
