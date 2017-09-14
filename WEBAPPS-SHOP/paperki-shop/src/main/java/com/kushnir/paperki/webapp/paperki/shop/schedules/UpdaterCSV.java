package com.kushnir.paperki.webapp.paperki.shop.schedules;

import com.kushnir.paperki.service.CatalogBean;

import com.kushnir.paperki.service.exceptions.ServiceException;
import com.kushnir.paperki.service.mail.Mailer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class UpdaterCSV {

    private static final Logger LOGGER = LogManager.getLogger(UpdaterCSV.class);

    @Autowired
    CatalogBean catalogBean;

    @Autowired
    Mailer mailer;

    public void catalogUpdate() throws IOException, ServiceException {
        LOGGER.debug("===== Started Catalog update ==== >>> ");
        catalogBean.updateCatalog();
    }

    public void productUpdate() {
        LOGGER.debug("===== Started Products update ==== >>> ");
    }
    public void pricesUpdate() {
        LOGGER.debug("===== Started Prices update ==== >>> ");
    }
    public void customersUpdate() {
        LOGGER.debug("===== Started Customers update ==== >>> ");
    }
    public void discountsUpdate() {
        LOGGER.debug("===== Started Discounts update ==== >>> ");
    }
    public void stockUpdate() {
        LOGGER.debug("===== Started Stock update ==== >>> ");
    }

}
