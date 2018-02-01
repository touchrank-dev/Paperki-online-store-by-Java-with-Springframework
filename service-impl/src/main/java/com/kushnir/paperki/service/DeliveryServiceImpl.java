package com.kushnir.paperki.service;

import com.kushnir.paperki.dao.DeliveryDao;
import com.kushnir.paperki.model.delivery.Delivery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service("deliveryService")
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

    private static final Logger LOGGER = LogManager.getLogger(DeliveryServiceImpl.class);

    @Autowired
    private DeliveryDao deliveryDao;

    @Override
    public HashMap<Integer, HashMap<Integer, Delivery>> getAll() {
        try {
            return deliveryDao.getAll();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public HashMap<Integer, Delivery> getByOrderIdType(Integer orderIdType) {
        LOGGER.debug("getByOrderIdType({})", orderIdType);
        return this.getAll().get(orderIdType);
    }

    @Override
    public Delivery getById(int idDelivery) {
        LOGGER.debug("getById({})", idDelivery);
        try {
            return deliveryDao.getById(idDelivery);
        } catch (Exception e) {
            return null;
        }
    }
}
