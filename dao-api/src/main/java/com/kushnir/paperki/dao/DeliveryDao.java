package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.delivery.Delivery;
import com.kushnir.paperki.model.payment.Payment;

import java.util.HashMap;

public interface DeliveryDao {
    HashMap<Integer, HashMap<Integer, Delivery>> getAll();
    Delivery getById(int idDelivery);
}
