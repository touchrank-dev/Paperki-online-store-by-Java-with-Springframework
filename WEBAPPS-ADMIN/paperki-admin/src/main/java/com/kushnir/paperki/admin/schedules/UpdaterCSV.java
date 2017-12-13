package com.kushnir.paperki.admin.schedules;

import com.kushnir.paperki.service.CatalogBean;

import com.kushnir.paperki.service.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class UpdaterCSV {

    private static final Logger LOGGER = LogManager.getLogger(UpdaterCSV.class);

    @Autowired
    CatalogBean catalogBean;

    public void catalogUpdate() throws IOException, ServiceException {
        LOGGER.debug("===== START Catalog update ==== >>> ");
        // catalogBean.updateCatalog();
        LOGGER.debug("===== FINISH Catalog update ==== ");
    }

    public void productUpdate() {
        LOGGER.debug("===== START Products update ==== >>> ");
        LOGGER.debug("===== FINISH Products update ==== ");
    }
    public void pricesUpdate() {
        LOGGER.debug("===== START Prices update ==== >>> ");
        LOGGER.debug("===== FINISH Prices update ==== ");
    }
    public void customersUpdate() {
        LOGGER.debug("===== START Customers update ==== >>> ");
        LOGGER.debug("===== FINISH Customers update ==== ");
    }
    public void discountsUpdate() {
        LOGGER.debug("===== START Discounts update ==== >>> ");
        LOGGER.debug("===== FINISH Discounts update ==== ");
    }
    public void stockUpdate() {
        LOGGER.debug("===== START Stock update ==== >>> ");
        LOGGER.debug("===== FINISH Stock update ==== ");
    }

}
