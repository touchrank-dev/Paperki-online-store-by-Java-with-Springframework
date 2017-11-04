package com.kushnir.paperki.webapp.paperki.shop.controllers.search;

import com.kushnir.paperki.model.RestMessage;
import com.kushnir.paperki.service.ProductBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/api/search")
public class RESTSearch {

    private static final Logger LOGGER = LogManager.getLogger(RESTSearch.class);

    @Autowired
    ProductBean productBean;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RestMessage search(@RequestParam String str) {
        LOGGER.debug("REST search({})", str);
        RestMessage restMessage;

        ArrayList products = productBean.searchProducts(str);
        restMessage = new RestMessage(HttpStatus.FOUND, str, products);


        return restMessage;
    }

}
