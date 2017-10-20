package com.kushnir.paperki.admin.controllers;

import com.kushnir.paperki.model.RestMessage;
import com.kushnir.paperki.service.CatalogBean;
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
@RequestMapping("/api")
public class RESTapi {

    private static final Logger LOGGER = LogManager.getLogger(RESTapi.class);
    private static final String version = "1.0";

    @Autowired
    CatalogBean catalogBean;

    @Value("${webapp.host}")
    String host;

    //curl -v [host]:8080/api
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage version() {
        LOGGER.debug("{} Rest api version() >>>", host);
        RestMessage restMessage = new RestMessage(HttpStatus.OK, "version", version);
        return restMessage;
    }

    //curl -v [host]:8080/api/update
    @GetMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage update() throws IOException, ServiceException {
        LOGGER.debug("{} Rest api update() >>>", host);

        String report = catalogBean.updateCatalog();

        RestMessage restMessage = new RestMessage(HttpStatus.OK, "successful updated" ,report);
        return restMessage;
    }

}
