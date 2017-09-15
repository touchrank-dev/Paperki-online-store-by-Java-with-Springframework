package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.CartProduct;
import com.kushnir.paperki.model.order.Order;
import com.kushnir.paperki.model.order.OrderInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.HashMap;

public class OrderDaoImpl implements OrderDao {

    private static final Logger LOGGER = LogManager.getLogger(OrderDaoImpl.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String P_ORDER_ID = "p_id_order";

    @Value("${order.add}")
    private String addOrderSqlQuery;

    private static final String P_ORDER_ID_ORDER_TYPE = "p_id_order_type";
    private static final String P_ORDER_TOKEN = "p_token_order";
    private static final String P_ORDER_NUMBER = "p_order_number";
    private static final String P_ORDER_ID_USER = "p_id_user";
    private static final String P_ORDER_TOTAL = "p_total";
    private static final String P_ORDER_TOTAL_WITH_VAT = "p_total_with_vat";
    private static final String P_ORDER_VAT_TOTAL = "p_vat_total";
    private static final String P_ORDER_TOTAL_DISCOUNT = "p_total_discount";
    private static final String P_ORDER_FINAL_TOTAL = "p_final_total";
    private static final String P_ORDER_FINAL_TOTAL_WITH_VAT = "p_final_total_with_vat";


    @Value("${order.addInfo}")
    private String addOrderInfoSqlQuery;

    private static final String P_CUSTOMER_NAME = "p_customer_name";
    private static final String P_ENTERPRISE_NAME = "p_enterprise_name";
    private static final String P_UNP = "p_unp";
    private static final String P_EMAIL = "p_email";
    private static final String P_PHONE = "p_phone";
    private static final String P_PAYMENT_NAME = "p_payment_name";
    private static final String P_PAYMENT_ACCOUNT = "p_payment_account";
    private static final String P_PAYMENT_BANK_NAME = "p_payment_bank_name";
    private static final String P_PAYMENT_BANK_CODE = "p_payment_bank_code";
    private static final String P_SHIPMENT_NAME = "p_shipment_name";
    private static final String P_SHIPMENT_ADDRESS = "p_shipment_address";
    private static final String P_USER_NOTES = "p_user_notes";


    @Value("${order.addItem}")
    private String addOrderItemSqlQuery;

    private static final String P_ITEM_ID_ORDER = "p_id_order";
    private static final String P_ITEM_ID_PRODUCT = "p_id_product";
    private static final String P_ITEM_PRODUCT_FULL_NAME = "p_product_full_name";
    private static final String P_ITEM_VAT = "p_VAT";
    private static final String P_ITEM_BASE_PRICE = "p_base_price";
    private static final String P_ITEM_BASE_PRICE_WITH_VAT = "p_base_price_with_vat";
    private static final String P_ITEM_DISCOUNTED_PRICE = "p_discounted_price";
    private static final String P_ITEM_DISCOUNTED_PRICE_WITH_VAT = "p_discounted_price_with_vat";
    private static final String P_ITEM_QUANTITY = "p_quantity";
    private static final String P_ITEM_TOTAL = "p_total";
    private static final String P_ITEM_TOTAL_WITH_VAT = "p_total_with_vat";



    @Override
    public Integer addOrder(Order order) {
        LOGGER.debug("addOrder({}) >>>", order);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        parameterSource.addValue(P_ORDER_ID_ORDER_TYPE, order.getIdOrderType());
        parameterSource.addValue(P_ORDER_TOKEN, order.getTokenOrder());
        parameterSource.addValue(P_ORDER_NUMBER, order.getOrderNumber());
        parameterSource.addValue(P_ORDER_ID_USER, order.getIdUser());
        parameterSource.addValue(P_ORDER_TOTAL, order.getTotal());
        parameterSource.addValue(P_ORDER_TOTAL_WITH_VAT, order.getTotalWithVAT());
        parameterSource.addValue(P_ORDER_VAT_TOTAL, order.getVATtotal());
        parameterSource.addValue(P_ORDER_TOTAL_DISCOUNT, order.getTotalDiscount());
        parameterSource.addValue(P_ORDER_FINAL_TOTAL, order.getFinalTotal());
        parameterSource.addValue(P_ORDER_FINAL_TOTAL_WITH_VAT, order.getFinalTotalWithVAT());

        namedParameterJdbcTemplate.update(addOrderSqlQuery, parameterSource, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public Integer addOrderIfo(OrderInfo orderInfo, Integer idOrder) {
        LOGGER.debug("addOrderIfo({}, {}) >>>", orderInfo, idOrder);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        parameterSource.addValue(P_ORDER_ID, idOrder);
        parameterSource.addValue(P_CUSTOMER_NAME, orderInfo.getCustomerName());
        parameterSource.addValue(P_ENTERPRISE_NAME, orderInfo.getEnterpriseName());
        parameterSource.addValue(P_UNP, orderInfo.getUNP());
        parameterSource.addValue(P_EMAIL, orderInfo.getEmail());
        parameterSource.addValue(P_PHONE, orderInfo.getPhone());
        parameterSource.addValue(P_PAYMENT_NAME, orderInfo.getPaymentName());
        parameterSource.addValue(P_PAYMENT_ACCOUNT, orderInfo.getPaymentAccount());
        parameterSource.addValue(P_PAYMENT_BANK_NAME, orderInfo.getPaymentBankName());
        parameterSource.addValue(P_PAYMENT_BANK_CODE, orderInfo.getPaymentBankCode());
        parameterSource.addValue(P_SHIPMENT_NAME, orderInfo.getShipmentName());
        parameterSource.addValue(P_SHIPMENT_ADDRESS, orderInfo.getShipmentAddress());
        parameterSource.addValue(P_USER_NOTES, orderInfo.getUserNotes());

        namedParameterJdbcTemplate.update(addOrderInfoSqlQuery, parameterSource, keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public int[] addOrderItems(HashMap<Integer, CartProduct> items, Integer idOrder) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(items.values().toArray());
        return namedParameterJdbcTemplate.batchUpdate(addOrderItemSqlQuery, batch);
    }
}
