package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.delivery.Delivery;

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

public class DeliveryDaoImpl implements DeliveryDao {

    private static final Logger LOGGER = LogManager.getLogger(DeliveryDaoImpl.class);

    private final String P_ID_ORDER_TYPE = "p_id_order_type";

    @Value("${delivery.getAll}")
    private String getAllSqlQuery;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public HashMap<Integer, HashMap<Integer, Delivery>> getAll() {
        LOGGER.debug("getAll()");
        HashMap<Integer, HashMap<Integer, Delivery>> deliveries =
                (HashMap<Integer, HashMap<Integer, Delivery>>)
                namedParameterJdbcTemplate.query(
                        getAllSqlQuery,
                        new DeliveryRowSetExtractor());
        return deliveries;
    }

    private class DeliveryRowSetExtractor implements ResultSetExtractor {

        @Override
        public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
            HashMap<Integer, HashMap<Integer, Delivery>> deliveries =
                    new HashMap<Integer, HashMap<Integer, Delivery>>();
            while (rs.next()) {
                int idDelivery = rs.getInt("id_delivery");
                String name = rs.getString("name");
                String shortDescription = rs.getString("short_description");
                String fullDescription = rs.getString("full_description");
                String link = rs.getString("link");
                String icon = rs.getString("icon");
                int orderTypeId = rs.getInt("id_order_type");
                double minCartTotal = rs.getDouble("min_cart_total");
                double price = rs.getDouble("price");

                Delivery delivery = new Delivery(
                        idDelivery,
                        orderTypeId,
                        name,
                        shortDescription,
                        fullDescription,
                        link,
                        icon,
                        minCartTotal,
                        price
                );

                HashMap<Integer, Delivery> deliveriesMap = deliveries.get(orderTypeId);

                if(deliveriesMap == null) {
                    deliveriesMap = new HashMap<Integer, Delivery>();
                    deliveriesMap.put(idDelivery, delivery);
                    deliveries.put(orderTypeId, deliveriesMap);
                } else {
                    Delivery deliv = deliveriesMap.get(idDelivery);
                    if(deliv == null) {
                        deliveriesMap.put(idDelivery, delivery);
                    }
                }

            }
            return deliveries;
        }
    }
}
