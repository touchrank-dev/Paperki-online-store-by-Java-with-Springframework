package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.payment.Payment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class PaymentDaoImpl implements PaymentDao {

    private static final Logger LOGGER = LogManager.getLogger(PaymentDaoImpl.class);

    @Override
    public HashMap<Integer, HashMap<Integer, Payment>> getAll() {
        LOGGER.debug("getAll()");
        return null;
    }
}
