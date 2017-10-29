package com.kushnir.paperki.webapp.paperki.shop.controllers.search;

import com.kushnir.paperki.model.RestMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/search")
public class RESTSearch {

    private static final Logger LOGGER = LogManager.getLogger(RESTSearch.class);

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    RestMessage search(@RequestParam String str) {
        LOGGER.debug("REST search({})", str);
        RestMessage restMessage = new RestMessage(HttpStatus.OK ,str);

        return restMessage;
    }

}
