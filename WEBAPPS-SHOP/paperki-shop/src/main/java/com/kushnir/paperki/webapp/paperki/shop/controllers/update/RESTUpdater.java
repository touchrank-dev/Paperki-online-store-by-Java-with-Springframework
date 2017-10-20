package com.kushnir.paperki.webapp.paperki.shop.controllers.update;

import com.kushnir.paperki.model.RestMessage;
import com.kushnir.paperki.service.BrandService;
import com.kushnir.paperki.service.CatalogBean;
import com.kushnir.paperki.service.ProductBean;
import com.kushnir.paperki.service.exceptions.ServiceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/update")
public class RESTUpdater {

    private static final Logger LOGGER = LogManager.getLogger(RESTUpdater.class);
    private static final String version = "1.0";

    @Value("${webapp.host}")
    String host;

    @Autowired
    CatalogBean catalogBean;

    @Autowired
    ProductBean productBean;

    @Autowired
    BrandService brandService;

    //curl -v [host]:8080/update
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    RestMessage version() {
        LOGGER.debug("{} Rest update version() >>>", host);
        RestMessage restMessage = new RestMessage(HttpStatus.OK, version);
        return restMessage;
    }

    //curl -v [host]:8080/update/catalog
    @GetMapping("/catalog")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage updateCatalog() throws IOException, ServiceException {
        LOGGER.debug("{} Rest update updateCatalog() >>>", host);

        String report = catalogBean.updateCatalog();

        RestMessage restMessage = new RestMessage(HttpStatus.OK, "update Catalog" ,report);
        return restMessage;
    }

    //curl -v [host]:8080/update/products
    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage updateProducts() throws IOException, ServiceException {
        LOGGER.debug("{} Rest update updateProducts() >>>", host);

        String report = productBean.updateProducts();

        RestMessage restMessage = new RestMessage(HttpStatus.OK, "update Products" ,report);
        return restMessage;
    }

    //curl -v [host]:8080/update/products/attributes
    @GetMapping("/products/attributes")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage updateProductAttributes() throws IOException, ServiceException {
        LOGGER.debug("{} Rest update updateProductAttributes() >>>", host);

        String report = productBean.updateProductAttributes();

        RestMessage restMessage = new RestMessage(HttpStatus.OK, "update Product Attributes" ,report);
        return restMessage;
    }

    //curl -v [host]:8080/update/products/prices
    @GetMapping("/products/prices")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage updateProductPrices() throws IOException, ServiceException {
        LOGGER.debug("{} Rest update updateProductPrices() >>>", host);

        String report = productBean.updateProductPrices();

        RestMessage restMessage = new RestMessage(HttpStatus.OK, "update Product Prices" ,report);
        return restMessage;
    }

    //curl -v [host]:8080/update/brands
    @GetMapping("/brands")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage updateBrands() throws IOException, ServiceException {
        LOGGER.debug("{} Rest update updateBrands() >>>", host);

        String report = brandService.updateBrands();

        RestMessage restMessage = new RestMessage(HttpStatus.OK, "update Products" ,report);
        return restMessage;
    }

    //curl -v [host]:8080/update/stock
    @GetMapping("/stock")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage updateStock() throws IOException, ServiceException {
        LOGGER.debug("{} Rest update updateStock() >>>", host);

        String report = productBean.updateStock();

        RestMessage restMessage = new RestMessage(HttpStatus.OK, "update Products" ,report);
        return restMessage;
    }

}
