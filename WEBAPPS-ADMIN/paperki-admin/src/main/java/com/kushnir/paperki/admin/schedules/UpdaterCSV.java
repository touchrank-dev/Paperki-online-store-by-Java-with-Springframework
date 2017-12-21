package com.kushnir.paperki.admin.schedules;

import com.kushnir.paperki.service.BrandService;
import com.kushnir.paperki.service.CatalogBean;

import com.kushnir.paperki.service.ProductBean;
import com.kushnir.paperki.service.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class UpdaterCSV {

    private static final Logger LOGGER = LogManager.getLogger(UpdaterCSV.class);

    @Autowired
    CatalogBean catalogBean;

    @Autowired
    ProductBean productBean;

    @Autowired
    BrandService brandService;

    public void catalogUpdate() throws IOException, ServiceException {
        LOGGER.debug("===== START Catalog update ==== >>> ");
        catalogBean.updateCatalog();
        LOGGER.debug("===== FINISH Catalog update ==== ");
    }
    public void brandsUpdate() {
        LOGGER.debug("===== START Brands update ==== >>> ");
        brandService.updateBrands();
        LOGGER.debug("===== FINISH Brands update ==== ");
    }
    public void productUpdate() {
        LOGGER.debug("===== START Products update ==== >>> ");
        productBean.updateProducts();
        productBean.updateProductAttributes();
        LOGGER.debug("===== FINISH Products update ==== ");
    }
    public void pricesUpdate() {
        LOGGER.debug("===== START Prices update ==== >>> ");
        productBean.updateProductPrices();
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
        productBean.updateStock();
        LOGGER.debug("===== FINISH Stock update ==== ");
    }

}
