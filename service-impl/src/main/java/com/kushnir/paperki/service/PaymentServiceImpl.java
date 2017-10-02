package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.PaymentDao;
import com.kushnir.paperki.model.payment.Payment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImpl.class);

    @Autowired
    PaymentDao paymentDao;

    @Override
    public HashMap<Integer, HashMap<Integer, Payment>> getAll() {
        return paymentDao.getAll();
    }

    @Override
    public Payment getById(int idPayment) {
        LOGGER.debug("getById({})", idPayment);
        return paymentDao.getById(idPayment);
    }
}
