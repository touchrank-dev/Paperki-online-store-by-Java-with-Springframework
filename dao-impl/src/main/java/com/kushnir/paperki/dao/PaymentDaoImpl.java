package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.payment.Payment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class PaymentDaoImpl implements PaymentDao {

    private static final Logger LOGGER = LogManager.getLogger(PaymentDaoImpl.class);

    private final String P_ID_ORDER_TYPE = "p_id_order_type";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${payment.getAll}")
    private String getAllSqlQuery;

    @Override
    public HashMap<Integer, HashMap<Integer, Payment>> getAll() {
        LOGGER.debug("getAll()");
        HashMap<Integer, HashMap<Integer, Payment>> payments =
                (HashMap<Integer, HashMap<Integer, Payment>>)
                        namedParameterJdbcTemplate.query(getAllSqlQuery, new PaymentRowSetExtractor());
        return payments;
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
}
