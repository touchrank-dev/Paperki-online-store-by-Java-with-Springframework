package com.kushnir.paperki.service;

import com.kushnir.paperki.model.payment.Payment;

import java.util.HashMap;

public interface PaymentService {
    HashMap<Integer, HashMap<Integer, Payment>> getAll();
}
