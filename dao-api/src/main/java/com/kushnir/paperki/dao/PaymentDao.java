package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.payment.Payment;

import java.util.HashMap;

public interface PaymentDao {
    HashMap<Integer, HashMap<Integer, Payment>> getAll();
    Payment getById(int idPayment);
}
