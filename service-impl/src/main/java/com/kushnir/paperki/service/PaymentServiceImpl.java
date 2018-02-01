package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.PaymentDao;
import com.kushnir.paperki.model.payment.Payment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentDao paymentDao;

    @Override
    public HashMap<Integer, HashMap<Integer, Payment>> getAll() {
        try {
            return paymentDao.getAll();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Payment getById(int idPayment) {
        LOGGER.debug("getById({})", idPayment);
        try {
            return paymentDao.getById(idPayment);
        } catch (Exception e) {
            return null;
        }
    }
}
