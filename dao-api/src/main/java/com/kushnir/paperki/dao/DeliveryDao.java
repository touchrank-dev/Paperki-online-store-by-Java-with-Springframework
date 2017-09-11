package com.kushnir.paperki.dao;

import com.kushnir.paperki.model.delivery.Delivery;

import java.util.HashMap;

public interface DeliveryDao {
    HashMap<Integer, HashMap<Integer, Delivery>> getAll();
}
