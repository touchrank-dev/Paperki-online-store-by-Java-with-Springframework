package com.kushnir.paperki.service;

import com.kushnir.paperki.model.delivery.Delivery;

import java.util.HashMap;

public interface DeliveryService {
    HashMap<Integer, HashMap<Integer, Delivery>> getAll();
    HashMap<Integer, Delivery> getByOrderIdType(Integer orderIdType);
}
