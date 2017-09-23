package com.kushnir.paperki.service;

import com.kushnir.paperki.model.payment.Payment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImpl.class);

    @Override
    public HashMap<Integer, HashMap<Integer, Payment>> getAll() {
        LOGGER.debug("getAll()");
        return null;
    }
}
