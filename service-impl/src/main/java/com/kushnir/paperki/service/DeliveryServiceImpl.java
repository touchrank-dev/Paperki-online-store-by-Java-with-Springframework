package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.DeliveryDao;
import com.kushnir.paperki.model.delivery.Delivery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public class DeliveryServiceImpl implements DeliveryService {

    private static final Logger LOGGER = LogManager.getLogger(DeliveryServiceImpl.class);

    @Autowired
    DeliveryDao deliveryDao;

    @Override
    public HashMap<Integer, HashMap<Integer, Delivery>> getAll() {
        return deliveryDao.getAll();
    }

    @Override
    public HashMap<Integer, Delivery> getByOrderIdType(Integer orderIdType) {
        return getAll().get(orderIdType);
    }
}
