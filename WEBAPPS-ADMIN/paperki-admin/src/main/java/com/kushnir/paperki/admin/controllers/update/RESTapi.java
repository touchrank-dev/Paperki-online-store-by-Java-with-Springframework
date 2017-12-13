package com.kushnir.paperki.admin.controllers.update;

import com.kushnir.paperki.model.RestMessage;
import com.kushnir.paperki.service.CatalogBean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

}
