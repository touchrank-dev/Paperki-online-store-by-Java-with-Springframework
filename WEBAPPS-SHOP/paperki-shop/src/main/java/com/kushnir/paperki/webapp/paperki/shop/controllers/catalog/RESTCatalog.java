package com.kushnir.paperki.webapp.paperki.shop.controllers.catalog;

import com.kushnir.paperki.model.RestMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping("/api/catalog")
public class RESTCatalog {

    private static final Logger LOGGER = LogManager.getLogger(RESTCatalog.class);
    private static final String version = "1.0";

    @Value("${webapp.host}")
    String host;

    //curl -v [host]:8080/api/catalog
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    RestMessage version() {
        LOGGER.debug("{} Rest version() >>>", host);
        RestMessage restMessage = new RestMessage(HttpStatus.OK, version);
        return restMessage;
    }

    //curl -v [host]:8080/api/catalog/viewtype
    @PostMapping("/viewtype")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage setViewType(@RequestBody HashMap<String, Integer> type, HttpSession session) {
        LOGGER.debug("{} Rest api setViewType({}) >>>", host, type);

        session.setAttribute("catview", type.get("type"));

        RestMessage restMessage = new RestMessage(HttpStatus.OK, "");
        return restMessage;
    }

    //curl -v [host]:8080/api/catalog/sortedby
    @PostMapping("/sortedby")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage setSortType(@RequestBody HashMap<String, Integer> type, HttpSession session) {
        LOGGER.debug("{} Rest api setSortType({}) >>>", host, type);

        session.setAttribute("sortedby", type.get("type"));

        RestMessage restMessage = new RestMessage(HttpStatus.OK, "");
        return restMessage;
    }
}
