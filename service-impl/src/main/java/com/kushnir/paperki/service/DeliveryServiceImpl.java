package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.DeliveryDao;
import com.kushnir.paperki.model.delivery.Delivery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Transactional
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
        return this.getAll().get(orderIdType);
    }
}